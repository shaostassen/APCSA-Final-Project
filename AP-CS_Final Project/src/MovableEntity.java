import java.awt.Point; //Imports the Point class which corresponds to the X and Y points on the Maze

public abstract class MovableEntity //This is a template abstract class that can not be initialized
	implements Entity //Must include the methods in the Entity Interface
{ //Movable entity start

	protected Point location; //Contains the location of where it is on the graph with public instances X and Y
	
	public boolean move(Direction dir, int distance) //Implemented method to move the location
	{
		
		switch (dir) { //Switch the direction for where they choose to go
		
		case UP: // If the user wants to go up
			
			if (this.location.y == 0) { return false; } //If they are at the top of the map returns false and doesnt move to avoid an ArrayIndexOutOfBoundsException
			
			this.location.y = this.location.y-1; // Moves the place up
			break; //Breaks so it doesnt run the other options
			
		case DOWN: //If the user wants to move down
			
			if (this.location.y == Maze.getHeight()-1) { return false; } //If they are at the bottom of the map return false and doesnt move to avoid an ArrayIndexOutOfBoundsException
			
			this.location.y = this.location.y+1; //Moves the location down
			break; //Breaks so it does not run the other case scenarios
			
		case LEFT: //If they user wants to move to the left
			
			if (this.location.x == 0) { return false; } //If they are at the left most position, return false to avoid an ArrayIndexOutOfBounds Exception
			
			this.location.x = this.location.x-1; //Moves left
			break; //Breaks to not run the other scenarios
			 
		case RIGHT: //If the user wants to move right
			
			if (this.location.x == Maze.getWidth()-1) { return false; } //If they are in the right most position, then it returns false to avoid going out of bounds
			
			this.location.x = this.location.x+1; //Moves right
			break; // Breaks to not run other case senarios
		
		} //End of switch
		
		return true; //If we get to this point, we moved sucsesfully, so we return true
		
	} //End of move method
	
	public Point getLocation() //Location getter
	{ //Location getter start
		return location; //Returns the location
	} //Location getter end
	
	public MovableEntity setLocation(Point newPoint) //Location setter
	{ //setter start
		location = newPoint; //Sets the location to the new point
		return this; //Returns this to chain
	} //Setter end
	
} //Movable entity end
