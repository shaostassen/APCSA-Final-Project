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

public class Texture //The texture class will hold and process the texture of walls and the images associated with them.
	extends Object //Makes it clear that this is a child of the object class
{ //Texture class start 
	
	private int[] pixels; //This class contains an array of pixels that will be accessed in order to draw the textures in the screen and game class.
	private String loc; //This class also contains a string that refers to the name of the file that contains the image
	private int size; //This class contains a int that stores the size of the image
	
	/**
	 * No parameter constructor for the Texture class
	 * @param none
	 * @return nothing
	 * @throws nothing
	 */
	public Texture() //Default, no parameter constructor
	{ //Default constructor start
		
		super(); //Calls upon the constructor of the object class to start
		
		pixels = new int[0]; //Sets the pixels to a new array of 0 length
		loc = ""; //Sets the location to an empty string
		size = -1; //Sets the size to -1 to symbolize nothingness
		
	} //Default constructor end
	
	/**
	 * 2-Parameter constructor for the Texture class
	 * @param location is the name of the file and size is the size of one side length
	 * @return nothing
	 * @throws ImageIO exception if an invalid file is passed through, or NullPointerException if the loc has not been initialized
	 */
	public Texture(String location, int size) //Standard, most used, two-parameter constructor
	{ //Two parameter constructor start
		this(); //Uses the default constructor to start off
		
		loc = location; //Sets the location to the user defined variable
		this.size = size; //Sets the size to the user defined variable (this is the side length of the texture, because it must be a square
		pixels = new int[size * size]; //Creates a new array with all the pixels in the image
		
		load(); //Uses a private helper method to read and process the file to initialize pixels
	} //Two parameter constructor end

	//We did not include an all parameter constructor because the client should not be able to modify the pixels
	
	/**
	 * Helper method to load in the image
	 * @param none
	 * @return nothing
	 * @throws ImageIO exception if an invalid file location is inputed or an NullPointerException if the loc is not initialized
	 */
	protected void load() //Helper method to load in the image and initialize the pixels array with it
	{ //Load method start
		
		try { //We surrounded this in a try catch in case the file can't be read, so it does not stop the program
			BufferedImage image = ImageIO.read(new File(loc)); //Creates a buffered image which can will load in the texture using the ImageIO static methods
			final int w = image.getWidth(); //Declare a final local variable to the width from the image with which we will use (It will not change)
			final int h = image.getHeight(); //Declare a final local variable to the height from the image which we will use (It will not change)
			image.getRGB(0, 0, w, h, pixels, 0, w); //Uses a method from the buffered image class which can initialize a 1-D array to contain all of the pixel colors in an image. This is why we used a 1-D array instead of a 2-D one
		} catch (IOException e) { //In the event the file can not be read
			e.printStackTrace(); //We print out the error
		} //End of the try catch in case we can't load in the file
		
	} //Load method end
	
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
	public int[] getPixels() //Standard getter for the pixels
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
	
	public static Texture WOOD = new Texture("assets/1.jfif", 64); //Creates a 64x64 texture that looks like wood
	public static Texture BRICK = new Texture("assets/2.jfif", 64); //Creates a 64x64 texture that looks like bricks
	public static Texture BLUESTONE = new Texture("assets/3.jfif", 64); //Creates a 64x64 texture that looks like blue stone
	public static Texture STONE = new Texture("assets/4.jfif", 64); //Creates a 64x64 texture that looks like stone
	
	//The textures are public for code clarity

} //Texture class end