import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridLayout;







public class DrawFrame extends JFrame {

	private JLabel statusLabel;	//label display mouse coordinates
	private DrawPanel panel; //draw panel for the shapes
	
	private JButton undo;	// button to undo last drawn shape
	private JButton clear;	// button to clear panel
	
	private JComboBox<String> colors;
	private JComboBox<String> shapes;
	private JCheckBox filled;	//checkbox to select whether shape is filled or not
	private JPanel widgetJPanel; //holds the widgets: buttons, comboboxes and checkbox
    private JPanel widgetPadder; //encapsulates widgetJPanel and adds padding around the edges 


	private final static String[] shapeOptions = { "Line", "Oval", "Rectangle" };
	private final static String[] colorOptions = { "Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray",
			"Magneta", "Orange", "Pink", "Red", "White", "Yellow" };
	private final static Color colorArray[] = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
			Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE,
			Color.YELLOW };

	public DrawFrame() {

			super("Java Drawing");	//sets the name of DrawFrame

		// Initialize Draw Panel and lower status label
		statusLabel = new JLabel("");
		
		panel = new DrawPanel(statusLabel);	//create draw panel and pass in JLabel

		//create buttons
        undo = new JButton( "Undo" );
        clear = new JButton( "Clear" );
        
        //create comboboxes
        colors = new JComboBox( colorOptions );
        shapes = new JComboBox( shapeOptions );
        
        //create checkbox
        filled = new JCheckBox( "Filled" );
        
        //JPanel object, widgetJPanel, with grid layout for widgets
        widgetJPanel = new JPanel();
        widgetJPanel.setLayout( new GridLayout( 1, 6, 10, 10 ) ); //sets padding between widgets in gridlayout
        
        //JPanel object, widgetPadder, with flowlayout to encapsulate and pad the widgetJPanel
        widgetPadder = new JPanel();
        widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5)); //sets padding around the edges
            
        // add widgets to widgetJPanel
        widgetJPanel.add( undo );
        widgetJPanel.add( clear );
        widgetJPanel.add( colors );
        widgetJPanel.add( shapes );                 
        widgetJPanel.add( filled );
        // add widgetJPanel to widgetPadder
        widgetPadder.add( widgetJPanel );
        
        //add widgetPadder and panel to JFrame
        add( widgetPadder, BorderLayout.NORTH);
        add( panel, BorderLayout.CENTER);
        
        // create new ButtonHandler for button event handling
        ButtonHandler buttonHandler = new ButtonHandler();
        undo.addActionListener( buttonHandler );
        clear.addActionListener( buttonHandler );
        
        //create handlers for combobox and checkbox
        ItemListenerHandler handler = new ItemListenerHandler();
        colors.addItemListener( handler );
        shapes.addItemListener( handler );
        filled.addItemListener( handler );
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 500, 500 );
        setVisible( true );
        
    } // end DrawFrame constructor
    
    /**
     * private inner class for button event handling
     */
    private class ButtonHandler implements ActionListener
    {
        // handles button events
        public void actionPerformed( ActionEvent event )
        {
            if (event.getActionCommand().equals("Undo")){
                panel.clearLastShape();
            }
            else if (event.getActionCommand().equals("Clear")){
                panel.clearDrawing();
            }
             
        } // end method actionPerformed
    } // end private inner class ButtonHandler
    
    /**
     * private inner class for checkbox and combobox event handling
     */
    private class ItemListenerHandler implements ItemListener
    {
        public void itemStateChanged( ItemEvent event )
        {
            // process filled checkbox events
            if ( event.getSource() == filled )
            {
                boolean checkFill=filled.isSelected() ? true : false; //
                panel.setCurrentShapeFilled(checkFill);
            }
            
            // determine whether combo box selected
            if ( event.getStateChange() == ItemEvent.SELECTED )
            {
                //if event source is combo box colors pass in colorArray at index selected.
                if ( event.getSource() == colors)
                {
                    panel.setCurrentShapeColor
                        (colorArray[colors.getSelectedIndex()]);
                }
                
                //else if event source is combo box shapes pass in index selected
                else if ( event.getSource() == shapes)
                {
                    panel.setCurrentShapeType(shapes.getSelectedIndex());
                }
            }
            
        } // end method itemStateChanged
    }
    
} // end class DrawFrame