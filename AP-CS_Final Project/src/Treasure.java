import java.awt.Point; //Imports the point class which will corespond to location
import java.util.ArrayList; //Imports the arrayList to contain information about it's graphics

public class Treasure //Treasure class
	implements Entity //Uses the getGraphicInfo() and getLocation from Entity
{ //Treasure start

	public Point location; //Point location containing the X and Y of the locations
	
	public boolean isFound; //Boolean is found for if it still exists
	
	public Treasure() //Default constructor 
	 { //Default constructor start
		 location = new Point(0,0); //Puts it at the top left of the maze
		 isFound = true; //Sets it to being found so it is not displayed
	 } //Default constructor end
	
	public Treasure(Point location, boolean isFound) //All paramater constructor
	{ //All paramater constructor start
		this.location = location; //Sets the location to the client object
		this.isFound = isFound; //Sets wether it is found to the user input
	} //All paramater constructor end

	public ArrayList<Object> getGraphicsInfo() //Method to get the graphics info
	{ //Get graphics start
		return null; //Returns null becasuse I dont know how to use graphics
	} //Get graphics end

	public Point getLocation() //Getter for location
	{ //Getter start
		return location; //Returns the location
	} //Getter end

	public void setLocation(Point location) //Setter for the location
	{ //Setter for the location start
		this.location = location; //Sets the location to the user defined variable
 	} //Location setter end

	public boolean isFound() //Getter for the isfound
	{ //IsFound getter start
		return isFound; //Returns the boolean
	} //IsFound getter end

	public void setFound(boolean isFound) //Setter for the isFound
	{ //Setter start
		this.isFound = isFound; //Sets it to the user defined variable
	} //Setter end
	
	public void find() //Method to set the found to true in a code appealing way
	{ //Find start
		this.isFound = true; //Sets the isfound to true
	} //Find end

} //Treasure end