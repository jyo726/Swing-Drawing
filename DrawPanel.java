import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;



public class DrawPanel extends JPanel {

	private LinkedList<MyShape> myShapes; //dynamic stack of shapes
    private LinkedList<MyShape> clearedShapes; //dynamic stack of cleared shapes from undo
    
    //current Shape variables
    private int currentShapeType; //0 for line, 1 for rect, 2 for oval
    private MyShape currentShapeObject; //stores the current shape object
    private Color currentShapeColor; //current shape color
    private boolean currentShapeFilled; //determine whether shape is filled or not
    
    JLabel statusLabel; //status label for mouse coordinates

	/**
	 * Create the panel.
	 */
	public DrawPanel(JLabel statusLabel) {
		myShapes = new LinkedList<MyShape>(); //initialize myShapes dynamic stack
        clearedShapes = new LinkedList<MyShape>(); //initialize clearedShapes dynamic stack
        
        //Initialize current Shape variables
        currentShapeType=0;
        currentShapeObject=null;
        currentShapeColor=Color.BLACK;
        currentShapeFilled=false;
        
        this.statusLabel = statusLabel; //Initialize statusLabel
        
        setLayout(new BorderLayout()); //sets layout to border layout; default is flow layout
        setBackground( Color.WHITE ); //sets background color of panel to white
        add( statusLabel, BorderLayout.SOUTH );  //adds a statuslabel to the south border
		
		MouseHandler handler = new MouseHandler();
		addMouseListener(handler);
		addMouseMotionListener(handler);
	}

	// Set methods for drawing
	public void setCurrentShapeType(int type) {
		currentShapeType = type;
	}

	public void setCurrentShapeColor(Color color) {
		this.currentShapeColor = color;
	}

	public void setCurrentShapeFilled(boolean filled) {
		currentShapeFilled = filled;
	}

	// Clearing methods removing last or all shapes
	public void clearLastShape()
    {
        if (! myShapes.isEmpty())
        {
            clearedShapes.addFront(myShapes.removeFront());
            repaint();
        }
	}
	public void clearDrawing()
    {
        myShapes.makeEmpty();
        clearedShapes.makeEmpty();
        repaint();
    }
	// Paint component redraws all shapes that were drawn earlier and current
	// shape
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ArrayList<MyShape> shapeArray=myShapes.getArray();
        for ( int counter=shapeArray.size()-1; counter>=0; counter-- )
           shapeArray.get(counter).draw(g);
        
        //draws the current Shape Object if it is not null
        if (currentShapeObject!=null)
            currentShapeObject.draw(g);
	}

	// Mouse handler for drawing shapes
	private class MouseHandler extends MouseAdapter {

		// Start to draw shape
		public void mousePressed( MouseEvent event )
        {
            switch (currentShapeType) //0 for line, 1 for rect, 2 for oval
            {
                case 0:
                    currentShapeObject= new MyLine( event.getX(), event.getY(), 
                                                   event.getX(), event.getY(), currentShapeColor);
                    break;
                case 1:
                    currentShapeObject= new MyRectangle( event.getX(), event.getY(), 
                                                        event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                    break;
                case 2:
                    currentShapeObject= new MyOval( event.getX(), event.getY(), 
                                                   event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                    break;
                    
            }
		}
		// End drawing shape
		
		public void mouseReleased(MouseEvent event) {
			currentShapeObject.setX2(event.getX());
			currentShapeObject.setY2(event.getY());
			
			myShapes.addFront(currentShapeObject); //addFront currentShapeObject onto myShapes
            
            currentShapeObject=null; //sets currentShapeObject to null
            clearedShapes.makeEmpty(); //clears clearedShapes
            repaint();
		}

		// Update shape as it is being drawn
		
		public void mouseDragged(MouseEvent event) {
			currentShapeObject.setX2(event.getX());
			currentShapeObject.setY2(event.getY());
			
			statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d",event.getX(),event.getY()));
			
			repaint();

		}

		// Update mouse coordinates in label

		public void mouseMoved(MouseEvent event) {
			statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d",event.getX(),event.getY()));

		}

	}
}