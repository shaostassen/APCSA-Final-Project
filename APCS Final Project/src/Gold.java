public class Gold //Gold class that represents a gold on the map
	extends Object //Makes it clear that this extends the object
	implements Sprite //Makes it known this is a sprite to use polymorphism
{ //Gold start

	private int x; //Contains an x position
	private int y; //Contains a y position
	
	public static final SpriteTexture texture = SpriteTexture.gold; //Sets the unchangeable texture for all gold to the sprite texture for gold
	
	/**
	 * Default constructor for gold
	 * @param none
	 * @return nothing
	 * @throws nothing
	 */
	public Gold() //Default constructor for Gold
	{ //Default constructor start
		super(); //Calls upon the parent constructor to get started
		
		x = 0; //Sets the x to zero
		y = 0; //Sets the y to zero 
	} //Default constructor end
	
	/**
	 * All parameter constructor for the gold
	 * @param X is the valid x coordinate
	 * @param Y is the valid y coordinate
	 * @return nothing
	 * @throws nothing
	 */
	public Gold(int x, int y) //All parameter constructor for Gold
	{ //All parameter constructor start
		this(); //Calls upon the default constructor to get started
		
		this.x = x; //Initialized the x to the user variable
		this.y = y; //Initialized the y to the user variable
	} //All parameter constructor end

	/**
	 * Standard getter for the X position
	 * @param none
	 * @return the X position of the gold
	 * @throws nothing
	 */
	public int getX() //Getter for the x
	{ //X getter start
		return x; // Returns the x
	} //X getter end

	/**
	 * Standard setter for the X
	 * @param x is the new x value
	 * @returns nothing
	 * @throws nothing
	 */
	public void setX(int x) //Setter for the x
	{ //X setter start
		this.x = x; //Sets the x to the user variable
	} //X setter end

	/**
	 * Standard getter for the y
	 * @param none
	 * @return the Y position of the gold
	 * @throws nothing
	 */
	public int getY() //Getter for the Y
	{ //Y getter start
		return y; //Returns the y
	} //Y getter end

	/**
	 * Standard setter for the Y
	 * @param y is the new y position
	 * @return nothing
	 * @throws nothing
	 */
	public void setY(int y) //Setter for the Y
	{ //y setter start
		this.y = y; //Sets the y to the user defined variable
	} //Y setter end
	
	/**
	 * Standard toString for the Gold
	 * @param none
	 * @return a textual representation of the gold
	 * @throws nothing
	 */
	public String toString() //Standard to string start
	{ //To string start
		return "Gold at (" + x + "," + y + ")"; //Returns a textual representation of the gold
	} //To string end

	/**
	 * Standard equals method for the gold
	 * @param other is another valid gold object
	 * @return true if they are in the same position, false if otherwise
	 * @throws NullPointerException if other is not initialized
	 */
	public boolean equals(Gold other) //Standard equals for the gold
	{ //Equals start
		return this.x == other.getX() && this.y == other.getY(); //Returns if the X's and Y's are equal
	} //Equals end
	
} //Gold end