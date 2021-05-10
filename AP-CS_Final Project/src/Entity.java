import java.awt.Point; //Imports the point class which contains a X and Y locations on the Maze
import java.util.ArrayList; //Imports a dynamic list object that will store 

public interface Entity //Entity Interface which will be implemented by Abstract class and Treasure
{ //Entity Start

	public ArrayList<Object> getGraphicsInfo(); //Gets the resources for how to draw the object. I made it an object array because it may contain many different data types
	
	public Point getLocation(); //Gives the location on the board in X and Y format
	
} //Entity end
