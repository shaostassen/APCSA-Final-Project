import java.awt.Point;
import java.util.ArrayList; //Imports arraylists to store graphics data

public class User //User class
	extends MovableEntity //Takes the methods from the movable entity abstract class
{ //Enemy start
	
	int speed; //Int for how fast it is going
	int angle; //Which way the enemy is facing
	
	public User() //User defualt constructor
	{ //Default start
		super();
		this.location = new Point(0,0);
		this.speed = 0; //Sets the speed to zero
		this.angle = 0; //Sets the angle to strait ahead
		
	} //Default end
	
	public User(int speed, int angle) //All paramater constructor
	{ //All paramater constructor start
		this();
		this.speed = speed; // Sets the speed to the nput
		this.angle = angle; //Sets the angle to the user defined input
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
  
  	public int getAngle() //Angle getter
	{ //Getter start
		return angle; //Returns the angle
	} //Getter end

	public void setAngle(int angle) //Angle setter
	{ //Setter start
		this.angle = angle; //Updates the angle
	} //Seter end
	
	

} //User end
