import java.util.ArrayList; //Imports the ArrayList Class so we can make dynamic arrays
import java.awt.Color; //Imports the color class so we can have colors

public class Screen //Screen class to do the actual raycasting and create a appealing visual
	extends Object //Makes it clear that this extends the object
{ //Screen start
	
	private int[][] map; //Has a 2-D Array which corresponds to the walls
	private int mapWidth, mapHeight, width, height; //Values for the size of the map, and the size of the frame
	private ArrayList<Texture> textures; //An arraylist of textures for the walls
	private double[] ZBuffer; //A Z-Buffer which keeps track of the perpendicular distance of each column in the frame
	private ArrayList<Sprite> sprites; //An arraylist of sprites to be rendered
	private int[][] buffer; //A buffer array which will contain the sprites to be put on top of the walls
	private Monster monster; //A monster to keep track of the animations
	
	/**
	 * All parameter constructor for the Screen
	 * @param m is the map
	 * @param mapW is the width of the map
	 * @param mapH is the height of the map
	 * @param tex is the arrayList of textures that will be used
	 * @param w is the width of the screen
	 * @param h is the height of the screen
	 * @param monster is the monster on the board
	 * @return nothing
	 * @throws nothing
	 */
	public Screen(int[][] m, int mapW, int mapH, ArrayList<Texture> tex, int w, int h, Monster monster) //All parameter constructor 
	{ //Constructor start 
		map = m; //Sets the map to the user variable
		mapWidth = mapW; //Sets the width to the user variable
		mapHeight = mapH; //Sets the height to the user variable
		textures = tex; //Sets the textures to the user variable
		width = w; //Sets the frame width to the user variable
		height = h; //Sets the frame height to the user variable
		ZBuffer = new double[640]; //Sets the Z buffer to a new array with the width of the screen
		sprites = new ArrayList<Sprite>(5); //Sets the sprite arraylist to a new arraylist with the 
		buffer = new int[480][640]; //Sets the buffer to a new array that is the size of the screen
		this.monster = monster; //Sets the monster to the user's reference
	} //Constructor end
	
	//We did not include a default constructor, toString, equals, and some getters/setters because there is a specific way we want the screen to be used
	
	/**
	 * Getter for the buffer
	 * @param none
	 * @return the buffer that represents the sprites
	 * @throws nothing
	 */
	public int[][] getBuffer() //Buffer getter 
	{ //Buffer getter start
		return buffer; //Returns the buffer
	} //Buffer getter end

	/**
	 * Helper method to reset the buffer to an empty state
	 * @param none
	 * @return nothing
	 * @throws NullPointerException if the buffer is null
	 */
	protected void resetBuffer() //Buffer reseter
	{ //Reset start
		for (int i = 0; i < buffer.length; i++) { //Iterates through the columns in a column major fashion
			for (int j = 0; j < buffer[i].length; j++) { //Iterates through the rows
				buffer[i][j] = 0; //Sets each value to zero
			} //Column traversal
		} //Row traversal
	} //Reset end
	
	/**
	 * Helper method to swap elements in an array list for when we sort the list
	 * @param sprites2 is the arraylist of the sprites
	 * @param one is the index of the first element
	 * @param two is the index of the second element
	 */
	private static void swap(ArrayList<Sprite> sprites2, int one, int two) //Swap method
	{ //Swap start
		Sprite temp = sprites2.get(one); //Sets the first element to a temp
		sprites2.set(one, sprites2.get(two)); //Replaces the first element with the second one
		sprites2.set(two, temp); //Replaces the second element with the temp
	} //Swap end
	
	/** 
	 * Setter for the sprites
	 * @param sprites is the new arraylist of sprites
	 * @return nothing
	 * @throws nothing
	 */
	public void updateSprites(ArrayList<Sprite> sprites) //Sprite setter
	{ //Sprite setter start
		this.sprites = sprites; //Sets the sprites to the user defined array list
	} //Sprite setter end
	
	/**
	 * Method to update the screen with the game and cameras variables
	 * @param camera is a camera object with properly initialized variables
	 * @param pixels is the array of pixels that is to be drawn
	 * @throws NullPointerException, ArrayIndexOutOfBoundsException, ArrayListIndexOutOfBoundsException, ConcurentModificationException, and maybe a couple other things if the variables are not initialized properly. This is a very complex method
	 */
	public void update(Camera camera, int[] pixels) //Method to update the screen
	{ //Update start
		
		for(int n=0; n<pixels.length; n++) { //For each pixel in the pixel array
			if(pixels[n] != Color.DARK_GRAY.getRGB()) pixels[n] = Color.DARK_GRAY.getRGB(); //If that pixel is not gray, make it gray (reset it)
		} //End of loop to reset pixels
	    
		
		/*
		 * The following code is adapted from https://lodev.org/cgtutor/raycasting.html
		 */
	    for(int x=0; x<width; x=x+1) { //For each column of pixels on the screen
	    	
	    	//1) Establish rays
			double cameraX = 2 * x / ((double)(width)) -1; //Obtains the position of the camera on the map
		    double rayDirX = camera.getxDir() + camera.getxPlane() * cameraX; //Sends one ray to the left
		    double rayDirY = camera.getyDir() + camera.getyPlane() * cameraX; //Sends one ray to the right
		    
		    //2) Obtain Map position
		    int mapX = (int)camera.getxPos(); //Gets the X position
		    int mapY = (int)camera.getyPos(); //Gets the Y position
		    
		    //3) find the length of ray from current position to next x or y-side
		    //3a) Find the Direction to go in x and y
		    double sideDistX; //Double for the Y distance
		    double sideDistY; //Double for the X distance
		    double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX)); //Amount to increment to get to x
		    double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY)); //Amount to increment to get to y
		    double perpWallDist; //Length of the wall		
		    int stepX, stepY; //Creates the steps that will be followed till we hit the wall
		    boolean hit = false;//was a wall hit
		    int side=0;//was the wall vertical or horizontal
		    //3b) Figure out the step direction and initial distance to each side
		    if (rayDirX < 0) { //If the direction is less than zero
		    	stepX = -1; //Move backward
		    	sideDistX = (camera.getxPos() - mapX) * deltaDistX; //Adjust the distance accordingly
		    } else { //If the direction is greater than or equal to zero
		    	stepX = 1; //Move forward
		    	sideDistX = (mapX + 1.0 - camera.getxPos()) * deltaDistX; //Adjust the distance accordingly
		    } //End of back or front for loop for x
		    if (rayDirY < 0) { //We do the same thing for Y, if it is less than zero
		    	stepY = -1; //Move back
		        sideDistY = (camera.getyPos() - mapY) * deltaDistY; //Adjust the distance
		    } else { //If it is greater than zero
		    	stepY = 1; //Move forward
		        sideDistY = (mapY + 1.0 - camera.getyPos()) * deltaDistY; //Adjust the distance
		    } //End of back or front loop for y
		    //3c) Loop to find where the ray hits a wall
		    while(!hit) { //While you haven't hit a wall
		    	//Jump to next square
		    	if (sideDistX < sideDistY) {//If you are moving to the left
		    		sideDistX += deltaDistX; //Add to the x distance
		    		mapX += stepX; //Increment the counter
		    		side = 0; //Set the side option to 0
		        } else { //If you are moving to the right
		        	sideDistY += deltaDistY; //Add to the y distance
		        	mapY += stepY; //Increment the counter
		        	side = 1; //Set the side option to 1
		        } //End of side out conditional
		    	//Check if ray has hit a wall
		    	if(map[mapX][mapY] != 0 && map[mapX][mapY] != 7 && map[mapX][mapY] != 8) hit = true; //If you hit a wall, say so
		    } //End of finding the times to increment
		    //3d) Calculate distance to the point of impact
		    if(side==0) //If you end on a 0
		    	perpWallDist = Math.abs((mapX - camera.getxPos() + (1 - stepX) / 2) / rayDirX); //Calculate the distance with the X
		    else //If you finished on a 1
		    	perpWallDist = Math.abs((mapY - camera.getyPos() + (1 - stepY) / 2) / rayDirY); //Calculate the distance with the Y
		  
		    //4) Now calculate the height of the wall based on the distance from the camera
		    int lineHeight; //Variable for the line height
		    if(perpWallDist > 0) lineHeight = Math.abs((int)(height / perpWallDist)); //If it is smaller than the screen, do the math to find it
		    else lineHeight = height; //The line height is equal to the height of the screen
		    
		    //5) calculate lowest and highest pixel to fill in current stripe
		    int drawStart = -lineHeight/2+ height/2; //Starts at the lowest distance
		    if(drawStart < 0) //If it is below the map
		    	drawStart = 0; //Start at the map
		    int drawEnd = lineHeight/2 + height/2; //Start at the highest distance
		    if(drawEnd >= height)  //If it is above the map
		    	drawEnd = height - 1; //Start at the top of the map
		    
		    //6) add a texture
		    int texNum = map[mapX][mapY] - 1; //Gets the texture number of the wall based on the map
		    double wallX;//Exact position of where wall was hit
		    if(side==1) {//If its a y-axis wall
		    	wallX = (camera.getxPos() + ((mapY - camera.getyPos() + (1 - stepY) / 2) / rayDirY) * rayDirX); //Chooses the wall to find the texture of 
		    } else {//X-axis wall
		    	wallX = (camera.getyPos() + ((mapX - camera.getxPos() + (1 - stepX) / 2) / rayDirX) * rayDirY); //Chooses the wall to find the texture of 
		    } //End of X or Y choosing
		    wallX-=Math.floor(wallX); //Finds the decimal of that wall
		    //6a) x coordinate on the texture
		    int texX = (int)(wallX * (textures.get(texNum).getSize())); //Finds the position in the texture to take the pixel from
		    if((side == 0) && rayDirX > 0) texX = textures.get(texNum).getSize() - texX - 1; //If it is a x side, it chooses the coordinate from the x side
		    if(side == 1 && rayDirY < 0) texX = textures.get(texNum).getSize() - texX - 1; //If it is a y side, it chooses the coordinate from the y side
		    //6b) calculate y coordinate on texture
		    for(int y=drawStart; y<drawEnd; y++) { //For each pixel in the strip
		    	int texY = (((y*2 - height + lineHeight) << 6) / lineHeight) / 2; //Were not sure how this syntax works, but it uses the heights to find the Y cordinate on the texture
		    	int color; //Declares the color
		    	if(side==0) color = textures.get(texNum).getPixels()[texX + (texY * textures.get(texNum).getSize())]; //Gets the texture from the image
		    	else color = (textures.get(texNum).getPixels()[texX + (texY * textures.get(texNum).getSize())]>>1) & 8355711;//Make y sides darker on the same pixel, but were not sure how because the syntax is new
		    	pixels[x + y*(width)] = color; //Sets the pixel array to that color
		    } //End of iterating though every strip
		    
		    ZBuffer[x] = perpWallDist; //Records the length of the wall because we need that for raycasting sprites
		    
		} //End of raycasting the walls
	    
	    //PART II: Raycasting Sprites
	    
	    //First we sort the sprites from biggest to smallest so we don't hide sprites by accident and draw them in the right order
	    //I used an insertion sort because there would be max 5 elements
	    for (int i = 1; i < sprites.size(); i++) { //For each sprite
			int spot = i; //Keeps track of their spot
			while (spot > 0 && camera.getDistance(sprites.get(spot).getX(), sprites.get(spot).getY()) > camera.getDistance(sprites.get(spot-1).getX(), sprites.get(spot-1).getY())) { //If they are further away than the element before it
				swap(sprites,spot,spot-1); //Swaps the element with the one before it
				spot--; //Decreases from the spot to find the next element
			} //End of inner loop to position each sprite
		} //End of loop to sort the sprite
	    
	    resetBuffer(); //Resets the buffer 
	    
	    final int w = 640, h = 480; //Sets to variables to the size of the frame
	    
	    for (Sprite sprite: sprites) { //For each sprite in the arraylist
	    	
	    	double spriteX = 0, spriteY = 0; //Starts their positions off at zero
	    	
	    	if (sprite instanceof Gold) { //If that sprite is a gold
	    		spriteX = sprite.getX() - camera.getxPos(); //Sets the position of the element to its rough X
	    		spriteY = sprite.getY() - camera.getyPos(); //Sets the position of the element to its rough Y
	    	} else { //If the element is the Monster
	    		spriteX = ((Monster) sprite).getTrueX() - camera.getxPos(); //Sets the position of the element to it's exact X
	    		spriteY = ((Monster) sprite).getTrueY() - camera.getyPos()+1; //Sets the position of the element to it's exact Y
	    	} //End of finding the positions of the sprites
	    	
	    	double invDet = 1.0 / (camera.getxPlane() * camera.getyDir() - camera.getxDir() * camera.getyPlane()); //Finds the constant that will help us slew the sprite to look natural
	    	
	    	double transformX = invDet * (camera.getyDir() * spriteX - camera.getxDir() * spriteY); //Transforms the X position with the skew
	    	double transformY = invDet * ((camera.getyPlane() * -1) * spriteX + camera.getxPlane() * spriteY); //Transforms the Y position with the Skew
	    	
	    	int spriteScreenX = (int)((w / 2) * (1 + transformX / transformY)); //Finds the X position of the sprite on the screen
	    	
	    	int spriteHeight = Math.abs((int)(h / (transformY))); //Gets the height of the sprite and alters it depending on the distance
	    	
	        int drawStartY = (-1* spriteHeight) / 2 + h / 2; //Finds the starting position of the sprite
	        if (drawStartY < 0) {drawStartY = 0;} //If it is below the screen, start at the screen
	        int drawEndY = spriteHeight / 2 + h / 2; //Finds the ending position of the sprite
	        if (drawEndY >= h) {drawEndY = h - 1;} //If it is above the screen, end at the top of the screen
	        
	        int spriteWidth = Math.abs((int) (h / (transformY))); // Find the width of the sprite and alter it according to the distance
	        int drawStartX = (-1 * spriteWidth) / 2 + spriteScreenX; //Find the starting position of the sprite
	        if (drawStartX < 0) {drawStartX = 0;} //If it is below the screen, start at the screen
	        int drawEndX = spriteWidth / 2 + spriteScreenX; //Find the ending position of the sprite
	        if (drawEndX >= w) {drawEndX = w - 1;} //If it is above the screen, end at the top of the screen
	        
	        int texWidth = 0, texHeight = 0; //Declares the height and with of the textures
	        if (sprite instanceof Gold) { texWidth = 880; texHeight = 522; } //If it is a gold, initializes the values for the gold image
	        else { texWidth = 626; texHeight = 565; } //If it is a monster, initialize the values for the monster image
	        
	        for (int stripe = drawStartX; stripe < drawEndX; stripe++) { //For each horizontal stripe in the image
	        	int texX = (int) (256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * texWidth / spriteWidth) / 256; //Gets the X position on the image
	        	if (transformY >= 0 && stripe >= 0 && stripe <= w && transformY <= ZBuffer[stripe]) { //If that strip should be drawn
	        		for(int y = drawStartY; y < drawEndY; y++) { //For each y position in that strip
	        			 int d = (y) * 256 - h * 128 + spriteHeight * 128; //Skews the Y position depending on the distance
	        			 int texY = ((d * texHeight) / spriteHeight) / 256; //Finds the position on the image that corresponds to the Y position
	        			 try { //Try's to do this, knowing there might be an error
	        				 int color; //Establishes the color
	        				 if (sprite instanceof Gold) { //if the sprite is a gold
	        					 color = SpriteTexture.gold.getPixels()[texX][texY]; //Gets the color from the gold image
	        				 } else { color = monster.curTexture.getPixels()[texX][texY]; } //If the image is the monster, it gets the texture from the current texture in the cycle
	        				 if((color & 0x00FFFFFF) != 0) {buffer[y][stripe] = color;} //If the color is not black, it is added to the buffer
	        			 } catch (ArrayIndexOutOfBoundsException e) { //If there is an arrayIndexOutOfBoundsError
	        				 continue; //Continues and ignores the problem
	        			 } //End of try catch for the adding the color
	        		} //End of for loop for the individual strip
	        	} //End of condition for if the strip should be drawn
	        } //End of iterating through the strips
	    } //End of iterating through the sprites
	    
	} //Update end
	
} //Screen end