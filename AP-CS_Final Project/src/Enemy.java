import java.util.ArrayList; //Imports arraylists to store graphics data

public class Enemy //Enemy class
	extends MovableEntity //Takes the methods from the movable entity abstract class
{ //Enemy start
	
	int speed; //Int for how fast it is going
	
	public Enemy() //Enemy defualt constructor
	{ //Default start
		this.speed = 0; //Sets the speed to zero
	} //Default end
	
	public Enemy(int speed) //All paramater constructor
	{ //All paramater constructor start
		this.speed = speed; //
	} //All paramater constructor end

	public ArrayList<Object> getGraphicsInfo() //Getter for the graphics info
	{ //Getter start
		return null; //Returns null because I don't know how to do that
	} //Getter end

	public int getSpeed() //Speed getter
	{ //Getter start
		return speed; //Returns the speed
	} //Getter end

	public void setSpeed(int speed) //Speed setter
	{ //Setter start
		this.speed = speed; //Updates the speed
	} //Seter end

} //Enemy end
