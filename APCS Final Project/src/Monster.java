public class Monster //Monster class that represents the enemy on the board
	extends Object //Makes it clear that this is a child of the object
	implements Sprite //Shows that this is a sprite for grouping and polymorphism
{ //Monster start
	
	private double x; //Double that refers to the x position on the board (can be in-between points on the array)
	private double y; //Double that refers to the x position on the board (can be in-between points on the array)
	
	private static final SpriteTexture texture1 = SpriteTexture.monster1; //Establishes it's first possible texture (See SpriteTexure for more details), which is for all Monsters and can not be changed
	private static final SpriteTexture texture2 = SpriteTexture.monster2; //Establishes it's second possible texture (See SpriteTexure for more details), which is for all Monsters and can not be changed
	private static final SpriteTexture texture3 = SpriteTexture.monster3; //Establishes it's third possible texture (See SpriteTexure for more details), which is for all Monsters and can not be changed
	protected SpriteTexture curTexture; //Has a reference to whatever the current texture of the monster is
	private int textureCount; //Keeps track of where they are in the texture animation cycle
	
	/**
	 * Default constructor for the monster
	 * @param none
	 * @return nothing
	 * @throws nothing
	 */
	public Monster() //Default monster constructor
	{ //Default constructor start
		super(); //Calls upon the default constructor to get things started
		
		x = 0; //Sets the x to zero
		y = 0; //Sets the y to zero
		textureCount = 0; //Starts the cycle at zero
		curTexture = texture1; //Starts the monster at its default texture
	} //Default constructor end
	
	/**
	 * Two parameter constructor for the monster
	 * @param x is the valid x coordinate
	 * @param y is the valid y coordinate
	 * @return nothing
	 * @throws nothing
	 */
	public Monster(double x, double y) //2 Parameter constructor for the monster
	{ //2 Parameter constructor start
		this(); //Uses the default to start things off
		
		this.x = x; //Sets the X to the user input
		this.y = y; //Sets the Y to the user input
	} //2 Parameter constructor end
	
	//We did not include an all parameter constructor because we did not want the user to influence graphics

	/**
	 * Standard getter for the X position
	 * @param none
	 * @return the approximate X position of the Monster
	 * @throws nothing
	 */
	public int getX() //Getter for the x
	{ //X getter start
		return (int) Math.round(x); // Returns the approximation of x
	} //X getter end

	/**
	 * Standard setter for the X
	 * @param x is the new x value
	 * @returns nothing
	 * @throws nothing
	 */
	public void setX(int x) //Setter for the x
	{ //X setter start
		this.x = (int) x; //Sets the x to the user variable
	} //X setter end
	
	/**
	 * Standard getter for the y
	 * @param none
	 * @return the approximate Y position of the Monster
	 * @throws nothing
	 */
	public int getY() //Getter for the Y
	{ //Y getter start
		return (int) Math.round(y); //Returns the approximate y
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
	
	//We had to include additional more specific getters and setters to be precise
	
	/**
	 * Accurate X getter
	 * @param none
	 * @return a double for the x
	 * @throws nothing
	 */
	public double getTrueX() //Accurate X getter
	{ //Accurate x getter start
		return x; //Returns the X
	} //Accurate X getter end
	
	/**
	 * Accurate Y getter
	 * @param none
	 * @return the accurate y position
	 * @throws nothing
	 */
	public double getTrueY() //Accurate Y getter
	{ //Accurate Y getter start
		return y;
	} //Accurate Y getter end //Returns the Y
	
	/**
	 * Accurate X setter
	 * @param x is the new X position
	 * @return This to chain
	 * @throws nothing
	 */
	public Monster setTrueX(double x) //Accurate X setter
	{ //Accurate X setter start
		this.x = x; //Replaces the X
		return this; //Returns this to chain
	} //Accurate X setter end
	
	/**
	 * Accurate Y setter
	 * @param y is the new y position
	 * @return this to chain
	 * @throws nothing
	 */
	public Monster setTrueY(double y) //Accurate Y setter
	{ //Accurate Y setter start
		this.y = y; //Replaces the Y
		return this; //Returns this to chain
	} //Accurate Y setter end
	
	/**
	 * Getter for the Texture Stage in the Cycle
	 * @param none
	 * @return The number corresponding to the stage in the cycle
	 * @throws nothing
	 */
	public int getTextureCount() //Texture count getter
	{ //Texture count getter start
		return textureCount; //Returns the textureCount
	} //Texture count getter end
	
	//We did not include all getters and setters for graphics because the client shouldn't have access to them
	
	/**
	 * Standard equals method for the Monster
	 * @param other is another valid monster object
	 * @return true if they are in the same position and same stage of the cycle, false if otherwise
	 * @throws NullPointerException if other is not initialized
	 */
	public boolean equals(Monster other) //Standard equals for the gold
	{ //Equals start
		return this.getX() == other.getX() && this.getY() == other.getY() && this.textureCount == other.getTextureCount(); //Returns if the X's and Y's are equal
	} //Equals end
	
	/**
	 * Standard toString for the Monster
	 * @param none
	 * @return a textual representation of the Monster
	 * @throws nothing
	 */
	public String toString() //Standard to string start
	{ //To string start
		return "Monster at (" + x + "," + y + "), at stage " + textureCount; //Returns a textual representation of the Monster
	} //To string end
	
	/**
	 * Method to move the monster in the animation cycle
	 * @param none
	 * @return nothing
	 * @throws nothing
	 */
	public void nextTexture() //Moves the monster in the animation cycle
	{ //Next texture start
		
		switch (textureCount) { //Switches the texture count, meaning for each possibility
		
		case 0: curTexture = texture2; break; //If they are in stage 0, go to the right foot in front
		case 1: curTexture = texture1; break; //If they are in stage 1, put the right foot in back
		case 2: curTexture = texture3; break; //If they are in stage 2, but the left foot in back
		
		} //End of switch statement for animation cycle
		textureCount++; //Go to the next stage in the cycle
		if (textureCount == 3) { textureCount = 0; } //If you got to the end of the cycle, start over again
		
	} //Next texture end
	
} //Monster end