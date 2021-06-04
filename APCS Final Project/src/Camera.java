import java.awt.event.KeyEvent; //Imports the Key Event, for if a key is pressed
import java.awt.event.KeyListener; //Imports the Key Listener, to listen for Key Events
import java.awt.event.MouseEvent; //Imports the Mouse Event, for if the mouse is pressed
import java.awt.event.MouseListener; //Imports the Mouse Listener, to listen for Mouse Events

public class Camera //Camera class which uses input to store information about the position of the user in the game 
	extends Object //Makes it clear that this is a child of the object class
	implements KeyListener, MouseListener //It can listen for keys and mouse presses
{ //Camera start
	
	private double xPos, yPos, xDir, yDir, xPlane, yPlane; //See below
	/*
	 * xPos and yPos = the physical location of the player on the map
	 * xDir and yDir = the direction that the player is facing
	 * xPlane and yPlane is what the player can see from his perspective, like the FOV
	 */
	private boolean left, right, forward, back, stepLeft, stepRight; //Declares booleans that will be used to see if the user wants to go areas
	private final double MOVE_SPEED = .08; //Finalizes the move speed to a certain double
	private final double ROTATION_SPEED = .045; //Finalizes the rotation speed to a certain double
	private boolean checkGold; //This will be used to see if the user is checking for gold
	private int clickX, clickY; //This will be used to store the location of the users clicks
	
	//We did not include a default parameter constructor, toString, equals, and some getters/setters because there is a specific way we want the camera to be used
	
	/**
	 * All parameter, all parameter constructor for the Camera
	 * @param x is the x position
	 * @param y is the y position
	 * @param xd is the x direction
	 * @param yd is the y direction
	 * @param xp is the x plane
	 * @param yp is the y plane
	 * @return nothing
	 * @throws nothing
	 */
	public Camera(double x, double y, double xd, double yd, double xp, double yp) //All parameter constructor
	{ //Default constructor start
		xPos = x; //Sets the x position to the user input
		yPos = y; //Sets the y position to the user input
		xDir = xd; //Sets the x direction to the user input
		yDir = yd; //Sets the y direction to the user input
		xPlane = xp; //Sets the x plane to the user input
		yPlane = yp; //Sets the y plane to the user input
		clickX = -1; clickY = -1; //Sets the mouse clicks to -1 showing nothing has been clicked
		checkGold = false; //Sets the check if gold variable to false, showing the user isn't checking for gold
	} //Default constructor end
	
	//The following getters and setters are straight forward, and do not require comments:

	public double getxPos()
	{
		return xPos;
	}

	public void setxPos(double xPos)
	{
		this.xPos = xPos;
	}

	public double getyPos()
	{
		return yPos;
	}

	public void setyPos(double yPos)
	{
		this.yPos = yPos;
	}

	public double getxDir()
	{
		return xDir;
	}

	public void setxDir(double xDir)
	{
		this.xDir = xDir;
	}

	public double getyDir()
	{
		return yDir;
	}

	public void setyDir(double yDir)
	{
		this.yDir = yDir;
	}

	public double getxPlane()
	{
		return xPlane;
	}

	public void setxPlane(double xPlane)
	{
		this.xPlane = xPlane;
	}

	public double getyPlane()
	{
		return yPlane;
	}

	public void setyPlane(double yPlane)
	{
		this.yPlane = yPlane;
	}

	public boolean isLeft()
	{
		return left;
	}

	public void setLeft(boolean left)
	{
		this.left = left;
	}

	public boolean isRight()
	{
		return right;
	}

	public void setRight(boolean right)
	{
		this.right = right;
	}

	public boolean isForward()
	{
		return forward;
	}

	public void setForward(boolean forward)
	{
		this.forward = forward;
	}

	public boolean isBack()
	{
		return back;
	}

	public void setBack(boolean back)
	{
		this.back = back;
	}
	
	public boolean isStepRight()
	{
		return stepRight;
	}

	public void setStepRight(boolean stepRight)
	{
		this.stepRight = stepRight;
	}
	
	public boolean isStepLeft()
	{
		return stepLeft;
	}

	public void setStepLeft(boolean stepLeft)
	{
		this.stepLeft = stepLeft;
	}

	public boolean isCheckGold()
	{
		return checkGold;
	}

	public void setCheckGold(boolean checkGold)
	{
		this.checkGold = checkGold;
	}

	public int getClickX()
	{
		return clickX;
	}

	public void setClickX(int clickX)
	{
		this.clickX = clickX;
	}

	public int getClickY()
	{
		return clickY;
	}

	public void setClickY(int clickY)
	{
		this.clickY = clickY;
	}

	//-------------------------------------------------------------------------------------------------------------
	
	/**
	 * Method to process a key being pressed
	 * @param key is a valid KeyEvent
	 * @return nothing
	 * @throws NullPointerException if the key is null
	 */
	public void keyPressed(KeyEvent key) //Method to process key presses
	{ //If the key is pressed start
		
		if((key.getKeyCode() == KeyEvent.VK_LEFT)) //If a left is pressed,
			left = true; //Turn left
		if((key.getKeyCode() == KeyEvent.VK_RIGHT)) //If a right is pressed,
			right = true; //Turn right
		if((key.getKeyCode() == KeyEvent.VK_W)) //If a up is pressed,
			forward = true; //Move forward
		if((key.getKeyCode() == KeyEvent.VK_S)) //If a down is pressed,
			back = true; //Move backward
		if ((key.getKeyCode() == KeyEvent.VK_A)) //If a A is pressed
			stepLeft = true; //Move left
		if ((key.getKeyCode() == KeyEvent.VK_D)) //If a D is pressed
			stepRight = true; //Move right
		if ((key.getKeyCode()== KeyEvent.VK_SPACE)) //If a space is pressed
			checkGold = true; //Check to see if there is gold

		
	} //If the key is pressed end
	
	/**
	 * Method to process a key being released
	 * @param key is a valid KeyEvent
	 * @return nothing
	 * @throws NullPointerException if the key is null
	 */
	public void keyReleased(KeyEvent key) //Method to process key releases
	{ //Key release start
		
		if((key.getKeyCode() == KeyEvent.VK_LEFT)) //If left was released,
			left = false; //Stop turning left
		if((key.getKeyCode() == KeyEvent.VK_RIGHT)) //If right was released
			right = false; //Stop turning right
		if((key.getKeyCode() == KeyEvent.VK_W)) //If up was released
			forward = false; //Stop moving up
		if((key.getKeyCode() == KeyEvent.VK_S)) //If down was released
			back = false; //Stop moving back
		if ((key.getKeyCode() == KeyEvent.VK_A)) //If a A is released
			stepLeft = false; //Stop moving left
		if ((key.getKeyCode() == KeyEvent.VK_D)) //If a D is released
			stepRight = false; //Stop moving right
		if ((key.getKeyCode()== KeyEvent.VK_SPACE)) //If space was released
			checkGold = false; //Stop checking for gold
		
	} //Key release end
	
	/**
	 * Method to calculate the distance between two points
	 * @param x is the valid x position
	 * @param y is the valid y position
	 * @return the distance between the two
	 * @throws nothing
	 */
	public double getDistance(double x, double y) //Get distance method
	{ //Get distance start
		return (yPos - y) * (yPos - y) + (xPos - x) * (xPos - x); //Uses the distance formula but does not check the square root because it is only for comparison
	} //Get distance end
	
	/**
	 * Method to update the variables depending on user input
	 * @param map is the 2-D map that contains the obstacles the user can't cross
	 * @return nothing
	 * @throws NullPointerException, ArrayIndexOutOfBoundsException if the map is invalid
	 */
	public void update(int[][] map) //Mathod to update the variables
	{ //Update start
		
		if(forward) { //If the user is moving forward
			if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0 || map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 7 || map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 8) { //If there is free space
				xPos+=xDir*MOVE_SPEED; //Moves forward
			} //End of forward condition
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0 || map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 7 || map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 8) //If there is space forward
				yPos+=yDir*MOVE_SPEED; //Moves the Y dir forward
		} //End of forward condition
		if(back) { //If we are moving backwards
			if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0 || map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 7 || map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 8) //If there is free space
				xPos-=xDir*MOVE_SPEED; //Move backward
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0 || map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==7 || map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==8) //If there is free space
				yPos-=yDir*MOVE_SPEED; //Move backward
		} //End of back condition
		if(right) { //If the user is moving right
			double oldxDir=xDir; //Keeps track of the old direction
			xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED); //Uses math to move the x direction
			yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED); //Uses math to move the y direction
			double oldxPlane = xPlane; //Keeps track of the old x plane
			xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED); //Uses math to move the x plane
			yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED); //Uses math to move the y plane
		} //End of right condition
		if(left) { //If the user is moving left
			double oldxDir=xDir;  //Keeps track of the old direction
			xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED); //Uses math to move the x direction
			yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED); //Uses math to move the y direction
			double oldxPlane = xPlane; //Keeps track of the old x plane
			xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED); //Uses math to move the x plane
			yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED); //Uses math to move the y plane
		} //End of left condition
		if (stepLeft) {
			if (map[(int) (xPos)][(int) (yPos + xDir * MOVE_SPEED)] == 0)
				yPos += xDir * MOVE_SPEED;
			if (map[(int) (xPos - yDir * MOVE_SPEED)][(int) (yPos)] == 0)
				xPos -= yDir * MOVE_SPEED;
		}
		if (stepRight) {
			if (map[(int) (xPos)][(int) (yPos - xDir * MOVE_SPEED)] == 0)
				yPos -= xDir * MOVE_SPEED;
			if (map[(int) (xPos + yDir * MOVE_SPEED)][(int) (yPos)] == 0)
				xPos += yDir * MOVE_SPEED;
		}
		
	} //Update end
	
	/**
	 * Method to process mouse clicks
	 * @param none
	 * @return nothing
	 * @throws NullPointerException if e is null
	 */
	public void mouseClicked(MouseEvent e) //If a mouse is clicked
	{ //Mouse click start
		clickX = e.getX(); //Sets the x click to where it was clicked
		clickY = e.getY(); //Sets the y click to where it was clicked
	} //Mouse click end
	
	public void keyTyped(KeyEvent arg0) {} //This method was required to avoid compile time errors, but not used

	public void mousePressed(MouseEvent e) {} //This method was required to avoid compile time errors, but not used
	
	public void mouseReleased(MouseEvent e) {} //This method was required to avoid compile time errors, but not used

	public void mouseEntered(MouseEvent e) {} //This method was required to avoid compile time errors, but not used

	public void mouseExited(MouseEvent e) {} //This method was required to avoid compile time errors, but not used
	
} //Camera end