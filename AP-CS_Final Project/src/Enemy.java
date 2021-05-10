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

	public ArrayList<Object> getGraphicsInfo()
	{
		return null;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

} //Enemy end
