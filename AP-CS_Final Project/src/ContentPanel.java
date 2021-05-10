import java.awt.Graphics;

import javax.swing.JPanel;//import library

public class ContentPanel extends JPanel{
	//declare all the variables
    private Maze maze;
    
    //constructor
    public ContentPanel(Maze maze) {
    	super();
    	this.maze = maze;
    }
    
    //paintComponet that would take care all the drawing 
    public void paintComponet(Graphics g) {
    	super.paintComponents(g);
    }
}
