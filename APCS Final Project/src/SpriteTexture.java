/*
 * The way we made this class work is different from traditional classes. 
 * This class contains static references to itself that represent the options for textures, as shown below.
 * This means no texture objects will be created anywhere else but here.
 * For that reason it is also immutable
 */

import java.awt.image.BufferedImage; //Imports the BufferedImage class which allows us to store images

import java.io.File; //Imports the file class so the BufferedImage objects can reference a file
import java.io.IOException; //Imports a special exception related to files in the case they are not present

import javax.imageio.ImageIO; //Imports a special class that allows us to process image files and put them into BufferedImages

public class SpriteTexture //Sprite Texture class that is similar to the texture class, but approaches the pixles array differently
	extends Object //Makes it clear that this class extends the universal parent, object
{ //Sprite texture start
	
	private int[][] pixels; //Instead of a 1-D array to hold the pixels like the Texture, the SpriteTexture is much more complex so it must be held in a 2-D Array
	private String loc; //String that points to the location of the image
	private int size; //int representing the total size of the picture
	
	/**
	 * Default constructor for the SpriteTexure
	 * @param none
	 * @returns nothing
	 * @throws nothing
	 */
	public SpriteTexture() //Default constructor for the Sprite Texture
	{ //Default constructor start
		super(); //Calls upon the parent class to get things started
		
		pixels = new int[0][0]; //Initializes the pixels to an empty array
		loc = ""; //Sets the location to an empty string
		size = -1; //Sets the size to a meaningless number representing 0
	} //Default constructor ened
	
	/**
	 * 1-Parameter constructor for the SpriteTexture
	 * @param location is a valid file name 
	 * @return nothing
	 * @throws ImageIO Exception if the file does not exist or NullPointerException if the loc is not initialized
	 */
	public SpriteTexture(String location) //1-Parameter constructor that serves in the place of the all parameter constructor
	{ //1 Parameter constructor start
		this(); //Uses the default constructor to start things off
		
		loc = location; //Sets the location to the user defined variable
		
		load(); //Uses a private load method to initialize the pixels array
	} //One parameter constructor end
	
	//We did not include an all parameter constructor because the client shouldn't influence the size or pixels
	
	/**
	 * Helper method to load in the image
	 * @param none
	 * @return nothing
	 * @throws ImageIO exception if an invalid file location is inputed or an NullPointerException if the loc is not initialized
	 */
	protected void load() //Private load helper method
	{ //Load start
	
		try { //Trys to do this because there might be an exception
			BufferedImage image = ImageIO.read(new File(loc)); //Creates a buffered image that accesses the file at the given location
			int w = image.getWidth(); //Sets the width to the width of the image
			int h = image.getHeight(); //Sets the height to the height of the image
			pixels = new int[w][h]; //Makes the pixels array with the specified length
			size = w*h; //Sets the size to the width times length
			for (int x = 0; x < w; x++) { //Goes through the columns of pixels
				for (int y = 0; y < h; y++) { //Goes through the y positions at each x position
					pixels[x][y] = image.getRGB(x, y); //Initializes the pixel array to the color in the position
				} // //Vertical strip for loop ended
			} //Horizontal strip for loop end
			//We couldn't use the getRGB method because that only creates a 1-D array
		} catch (IOException e) { //If the file is not found
			e.printStackTrace(); //Prints the problem
		} //End of try catch

	} //Load end

	/**
	 * toString method to get a textual representation of the object
	 * @param none
	 * @return the textual representation of the object
	 * @throws nothing
	 */
	public String toString() //Standard override toString for the Texture
	{ //ToString Start
		return "Texture at: " + loc + " with a size of " + size + " pixles, and an array of pixles at " + pixels + "."; //Returns a textual representation of the texture
	} //ToString end
	
	/**
	 * Equals method to determine whether two objects are the same
	 * @param Other is a valid Texture object
	 * @return true if they are the same, false if not
	 * @throws NullPointerException if other is not initialized, or if its location is not initialized
	 */
	public boolean equals(Texture other) //Simple equals method for the texture
	{ //Equals start
		return this.loc.equals(other.getLocation()); //Returns true if they are pointing to the same file, false if not. 
	} //Equals end
	
	/**
	 * Getter for the pixel array
	 * @param none
	 * @return a reference to the pixel array
	 * @throws nothing
	 */
	public int[][] getPixels() //Standard getter for the pixels
	{ //Pixels getter start
		return pixels; //Returns the pixels array
	} //Pixels getter end
	
	/**
	 * Getter for the string location
	 * @param none
	 * @return a reference to the location
	 * @throws nothing
	 */
	public String getLocation() //Standard getter for the string location
	{ //Loc getter start
		return loc; //Returns the location
	} //Loc getter end
	
	/**
	 * Getter for the size
	 * @param none
	 * @return the size of one of the side lengths
	 * @throws nothing
	 */
	public int getSize() //Standard getter for the size
	{ //Size getter start
		return size; //Returns the size
	} //Size getter end
	
	public static SpriteTexture gold = new SpriteTexture("assets/gold.jfif"); //Creates a sprite texture for the gold
	//There are three monsters to make an animation
	public static SpriteTexture monster1 = new SpriteTexture("assets/monster1.jfif"); //Creates the first monster sprite which is both legs in front
	public static SpriteTexture monster2 = new SpriteTexture("assets/monster2.jfif"); //Creates the second monster with the left leg in back and right in front
	public static SpriteTexture monster3 = new SpriteTexture("assets/monster3.jfif"); //Creates the third monster with the right leg in back and the left in front

	//The textures are public for code clarity
	
} //Sprite texture end