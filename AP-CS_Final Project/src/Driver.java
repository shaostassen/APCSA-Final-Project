import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;//import library

public class ContentPanel extends JPanel{
	//declare all the variables
    private MazeGenerator maze;
    
    //constructor
    public ContentPanel(MazeGenerator maze) {
    	super();
    	this.setBackground(Color.BLUE);
    	this.maze = maze;
    	
    }
    
    //paintComponet that would take care all the drawing 
    public void paintComponent(Graphics2D g) {
    	System.out.println("Yay");
    	super.paintComponent(g);
    	drawBirdView(g);
    	
    	
    }
    
    // draw the small 2D map represent map
    public void drawBirdView(Graphics2D g) {
    	
    	System.out.println("hi");
        Color c1 = Color.DARK_GRAY;
        Color c2 = Color.white;
        Color c3 = Color.BLUE;
    	g.setColor(Color.ORANGE);
    	g.fillRect(0, 0, 400, 200);
    	System.out.println("lo");
    	for (int i=0; i<maze.getGridDimensionY();i++) {
    		for (int j=0; j<maze.getGridDimensionX();j++) {
    			if (maze.getGrid()[i][j]==1) {
    				g.setColor(c1);
    			}
    			if (maze.getGrid()[i][j]==0) {
    				g.setColor(c2);
    			}
    			if (maze.getGrid()[i][j]==2) {
    				g.setColor(c3);
    			}
    			if (maze.getGrid()[i][j]==3) {
    				g.setColor(c3);
    			}
    			
    		    g.fillRect(j*maze.getGridDimensionX(), i*maze.getGridDimensionY(), 400/maze.getGridDimensionX(), 200/maze.getGridDimensionY());
    		}
    	}
    }
}
