import java.awt.BasicStroke; //Imports the Basic Stroke to make lines of varying thicknesses
import java.awt.Color; // Imports the color class to add colors to the game
import java.awt.Font; //Imports the font class to have text
import java.awt.Graphics; //Imports the graphics class to draw
import java.awt.Graphics2D; //Imports the graphics2D object which can draw more complex structures
import java.awt.image.BufferStrategy; //Imports the BufferStrategy which allows us to draw directly on a JFram without any ContentPanels
import java.awt.image.BufferedImage; //Imports the BufferedImage class to have different images
import java.awt.image.DataBufferInt; //Imports the DataBufferInt which will be used by the BufferImage class to make our own pictures

import java.io.File; //Imports the File class to reference to pictures and text files
import java.io.IOException; //Imports a special exception required to use files in case it is not there

import java.util.ArrayList; //Imports the ArrayList class to have arrays

import javax.imageio.ImageIO; //Imports the imageIO class to read images

import javax.swing.JFrame; //Imports the JFrame Class so we do not need to use the system

public class Game //This class is where the game is hosted and where the main method is 
	extends JFrame //This class is a child of JFrame and has all of its functionalities
	implements Runnable //This class implements runnable, so we can start the program right from here
{ //Game class start
	
	private static final long serialVersionUID = 1L; //Im not sure what this is, but we get an error if we don't include it
	
	protected int score; //Int for the players score
	protected int gamePhase; //Int to progress through the game
	protected int mapWidth; //Int for the width of the map
	protected int mapHeight; //Int for the height of the map
	protected Thread thread; //Int for the thread of this game (so we can run things simultaneously)
	protected boolean running; //Boolean for if the game is running
	protected BufferedImage image; //Image for what we are drawing
	protected int[] pixels; //Array of pixels for the raycasting
	protected ArrayList<Texture> textures; //Array list of texture possibilities for the walls
	protected ArrayList<Sprite> sprites; //Arraylist for sprites on the map
	protected Camera camera; //The camera that reads the user input
	protected double MONSTER_SPEED = .1; //The speed of the monster that will not change initially, only as the game progresses
	protected Screen screen; //The screen that will perform raycasting
	protected Monster monster; //The monster that will be smart enough to move around
	protected int[][] map = 
		{
			{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
			{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
			{1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
			{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
			{1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
			{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
			{1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
			{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
			{1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
			{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
			{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
			{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
			{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
			{1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
		}; //Currently, we have a fixed map. If we have time, we will make it auto-generated
	
	//We did not include getters, setters, toStrings, extra constructors, or equals because we are treating this class like the Driver
	
	/** 
	 * Default constructor for the game
	 * @param none
	 * @return nothing
	 * @throws IOException if the file is not found
	 */
	public Game() //Default constructor
			throws IOException //Throws a special exception if the file is not found
	{ //Default constructor start
		super(); //Calls upon the parent constructor to start things off
		
		setSize(640, 480); //Sets the size of the frame to 640x480
		setResizable(false); //Makes the frame unresizable so players can't break things
		setTitle("Super Gold Hunters"); //Sets the name of the frame to Super Gold Hunters
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes it so the program closes when the game does
		setBackground(Color.black); //Sets the background to black
		setLocationRelativeTo(null); //Puts it in the middle of the computer
		setVisible(true); //Makes it able to be seen
		showStart(); //Shows the starting screen while everything else loads
		thread = new Thread(this); //Makes a new thread which is being used by this program
		score = 0; //Starts the score off at zero
		gamePhase = 0; //Starts the game phase off at zero
		mapWidth = 15; //Starts the width of the map off at 15
		mapHeight = 15; // starts the height of the map at 15
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB); //Makes the image into a new image that can be modified by us
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //Connects the pixels array to the image array, so when the pixels change, so does the image
		textures = new ArrayList<Texture>(); //Creates the new texture array
		textures.add(Texture.WOOD); //Adds wood to the texture array
		textures.add(Texture.BRICK); //Adds brick to the texture array
		textures.add(Texture.BLUESTONE); //Adds bluestone to the texture array
		textures.add(Texture.STONE); //Adds stone to the texture array
		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66); //Makes a new camera and positions the user in it in the starting room
		monster = new Monster(6,11); //Creates a new monster and adds it to the maze
		screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480, monster); //Creates a new screen with the necessary variables
		addKeyListener(camera); //Adds a key listener, the camera, so we can recognize keys
		addMouseListener(camera); //Adds a mouse listener, the camera, so we can recognize clicks
		sprites = new ArrayList<Sprite>(6); //Creates an array list of sprites with the initial capacity of 6 objects
		addGold(5); //Uses a helper method to add 5 gold to the maze
		sprites.add(monster); //Adds the monster to the sprites
		start(); //Starts the game
		
	} //Default constructor end
	
	/**
	 * Getter for the map
	 * @param none
	 * @return the map
	 * @throws nothing
	 */
	public int[][] getMap() //Map getter
	{ //Map getter start
		return map; //Returns the map
	} //Map getter end

	/**
	 * Incrementer for the score
	 * @param none
	 * @return nothing
	 * @throws nothing
	 */
	public void updateScore() //Incrementer for the score
	{//Incrementer for the score start
		score++; //Increments the score
	} //Incrementer for the score end
	
	/**
	 * Helper method to add gold to the maze
	 * @param gold is the number of gold to be added
	 * @return nothing
	 * @throws nothing
	 */
	protected void addGold(int gold) //Gold adder
	{ // Gold adder start
		for (int i = 0; i < gold; i++) { //For each of the gold that needs to be added
			int x = 0, y = 0; //Declares the x and y 
			while(map[x][y] != 0 && map[x][y] != 8 && map[x][y] != 7) { //While it does not have a valid x and y
				x = 1+ ((int) (Math.random() * map.length-2)); //Gets a random x
				y = 1+ ((int) (Math.random() * map.length-2)); //Gets a random y
			} //End of finding a valid point
			map[x][y] = 7; //Fills that position on the map
			sprites.add(new Gold(x,y)); //Adds that position and a gold to the sprite array
		} //End of for loop to add gold
		
	} // Gold adder end
	
	/**
	 * Method to start the game
	 * @param none
	 * @return nothing
	 * @throws nothing
	 */
	protected synchronized void start() //Game starter. It being synchronized means it is the only thing that can be run at the time
	{ //Start start
		running = true; //Sets running to true
		thread.start(); //Starts up the run method
	} //Start end
	
	/**
	 * Method to end the game
	 * @param none
	 * @return nothing
	 * @throws InterruptedException if it is interrupted
	 */
	protected synchronized void stop() //Game stopper It being synchronized means it is the only thing that can be run at the time
	{ //Stop start
		running = false; //sets running to false
		try { //Try to do this thing
			thread.join(); //Stop the run method
		} catch(InterruptedException e) { //If it is interrupted
			e.printStackTrace(); //Stops the program anyway and shows the error
		} //End of stopping the program
	} //Stop end
	
	/**
	 * Method to show the starting page
	 * @param none
	 * @returns nothing
	 * @throws IOException if the file is not found
	 */
	protected void showStart() //Method to show the starting page
			throws IOException //throws an io exception if the file is not found
	{ //Show start start
		
		BufferedImage img = ImageIO.read(new File("open.jfif")); //Creates a new buffered image pointing to the startup screen
		
		BufferStrategy bs = getBufferStrategy(); //Makes a buffer strategy that allows us to paint on Jframes
		if(bs == null) { //If no buffer strategy exists
			createBufferStrategy(3); //Make a buffer strategy
			return; //Returns to avoid problems
		} //End of initializing the buffer strategy
		Graphics g = bs.getDrawGraphics(); //Creates the graphics object
		
		g.drawImage(img,0,10,640,480,null); //Draws the loading screen
		bs.show(); //Shows what happened
		
	} //Show start end
	
	/**
	 * Method to end the program
	 * @param none
	 * @return nothing
	 * @throws IOException if the file is not found
	 */
	protected void end() //Method to end the program
			throws IOException //Throws this exception if the file is not found
	{ //end start
		BufferedImage img = ImageIO.read(new File("close.jfif")); //Imports a picture that is the closing screen
		
		BufferStrategy bs = getBufferStrategy(); //Makes a buffer strategy that allows us to paint on Jframes
		if(bs == null) { //If no buffer strategy exists
			createBufferStrategy(3); //Make a buffer strategy
			return; //Returns to avoid problems
		} //End of initializing the buffer strategy
		Graphics g = bs.getDrawGraphics(); //Creates the graphics object
		
		g.drawImage(img,0,15,640,480,null); //Draws the closing screen
		
		g.setFont(new Font("Serif",Font.BOLD, 50)); //Gets a big font
		g.setColor(Color.YELLOW); //Sets the color to yellow
		g.drawString("Final Score: " + score, 175, 430); //Displays the score
		
		bs.show(); //Shows the graphics
	} //end end

	/**
	 * Method to render the majority of the game
	 * @param none
	 * @return nothing
	 * @throws NullPointerException or ArrayIndexOutOfBoundsException if the variables are not handled properly
	 */
	public void render() //Render method
	{ //Render start
		
		BufferStrategy bs = getBufferStrategy(); //Makes a buffer strategy that allows us to paint on Jframes
		if(bs == null) { //If no buffer strategy exists
			createBufferStrategy(3); //Make a buffer strategy
			return; //Returns to avoid problems
		} //End of initializing the buffer strategy
		Graphics g = bs.getDrawGraphics(); //Creates the graphics object
	
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null); //Draws the walls created by raycasting
		
		int[][] buffer = screen.getBuffer(); //Gets the cumalative addition of the sprites from the buffer in the screen
		
		for (int x = 0; x < buffer.length; x++) { //For each column
			for (int y = 0; y < buffer[x].length; y++) { //For each row in each column
				if (buffer[x][y] != 0) { //If the pixel there is not black
					g.setColor(new Color(buffer[x][y])); //Set the color to what is shown
					g.fillRect(y,x,1,1); //Draw that pixel
				} //End of black condition
			} //End of traversing rows
		} //End of traversing columns
		
		
		//Now we draw the minimap
	
		for(int i = 0;i<map[0].length;i++) { //For each column in the map
	    	for (int j= 0;j<map.length;j++) { //For each row in the map
	    		if(map[i][j]==0) { //If that row is zero
	    			g.setColor(Color.white); //Set the color to white
	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length); //Draws that rectangle
	    		} //End of zero condition
	    		if(map[i][j]==1 || map[i][j] == 2 || map[i][j] == 3 || map[i][j] == 4) { // If that square is a wall
	    			g.setColor(Color.black); //Set the color to black
	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length); //Draw that rectangle
	    		} //End of wall check
	    		if(map[i][j]==7) { //If that box is 7
	    			g.setColor(Color.white); //Set the color to white
	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length); //Draw a backround
	    			//We took this part out but we can add gold
	//    			g.setColor(new Color(242,252,26)); //Set the color to gold
	//    			g.fillOval(i*180/map[0].length+3,30+j*180/map.length+3,180/map[0].length-3,180/map.length-3); //Draw a little circle
	    		} // End of the if conditions
	    		
	    	} // end of the row traversal
	    } //End of the column traversal
		
		g.setColor(Color.RED); //Sets the color to red
		g.fillOval((int) (monster.getTrueX()*180/map[0].length+3),(int) (30+monster.getTrueY()*180/map.length+3),180/map[0].length-3,180/map.length-3); //Draws the monster
		
	    g.setColor(Color.green); //Sets the color to green
		g.fillOval((int)(camera.getxPos()*180/map[0].length-3),(int)(30+camera.getyPos()*180/map.length-3),180/map[0].length-3,180/map.length-3); //Draws the person
	
		Graphics2D g2 = (Graphics2D) g; //Makes a new graphics object
		g2.setColor(Color.ORANGE); //Sets the color to orange
		g2.setStroke(new BasicStroke(5)); //Increases the size of the lines
		
		int xPos = (int) (camera.getxPos()*180/map[0].length-3); //Gets the X position on the frame
		int yPos = (int) (30+camera.getyPos()*180/map.length-3); //Gets the y position on the frame

		g2.drawLine(5 + xPos,3 + yPos,5 + ((int) (xPos + (5*camera.getxDir()))), 3 + ((int) (yPos + (5*camera.getyDir())))); //Draws the headlights to show the angle on the minimap
		
		g.setFont(new Font("Serif",Font.BOLD, 20)); //Makes a new font
		g.setColor(Color.RED); //Sets the color to red
		g.drawString(new String("Score: " + score), 550, 55); //Displays the score
			
	    if (gamePhase == 2) { //If the monster got you
			g.setColor(new Color(255,0,0,125)); //Set the color to red, but translucent
			g.fillRect(0,0,640,480); //Give the screen a red tint
	    } // End of game conditional
			
		bs.show(); //Show the graphics changes
		
	} // Render end
	
	/**
	 * Method to run and process the game
	 * @param none
	 * @return nothing
	 * @throws nothing
	 */
	public void run() //Run method
	{ //Run start
	
		long lastTime = System.nanoTime(); //Gets the time of the processing speed
		final double ns = 1000000000.0 / 60.0;//60 times per second, will be used in the FPS
		double delta = 0; //Declares a incrementer, or tracker
		requestFocus(); //Uses the focus from the thread
		screen.updateSprites(sprites); //Gives the reference to the arraylist to the screen so it knows what to do
		int monsterTimer = 0; //Starts the monster timer at 0
		
		while(running) { //While the program is running
			
			if (gamePhase == 0) { // If we are in the first stage
				
				try { //We try to show the loading screen
					showStart(); //Shows the loading screen
				} catch (IOException e) { //If we have an error with the loading screen
					e.printStackTrace(); //We print the problem
				} //End of showing the loading screen
				
				if ((camera.clickX >= 223 && camera.clickX <= 397) && (camera.clickY >= 275 && camera.clickY <= 339)) { //If the user clicked on the button to start
					gamePhase = 1; //Progress through the game cycle
				} //End of ready condition
				
			} else if (gamePhase == 1) { //If we are already in the game
				
				long now = System.nanoTime(); //Get the processing speed
				delta = delta + ((now-lastTime) / ns); //Increment delta
				lastTime = now; //Update the last time
				
				while (delta >= 1) { //Make sure update is only happening 60 times a second
					
					if (monsterTimer == 10) { monsterTimer = 0; monster.nextTexture(); moveMonster(); MONSTER_SPEED+=.000005;} //If it is time for the monster to move, reset the timer, move it, change the texture, and increase the speed
					monsterTimer++; //Increment the monster timer
					
					if (Math.abs(monster.getTrueX() - camera.xPos) <= 1 && Math.abs(monster.getTrueY() - camera.yPos) <= 1) { // If the monster caught the user
						gamePhase = 2; //Progress through the game
						
//						for (int i = 0; i < 10000; i++) {
//							
//							int[][] buffery = screen.getBuffer();
//							
//							for (int j = 0; j < buffery.length; j++) {
//								if (buffery[320][j] != 0) { return; }
//							}
//							
//							System.out.println(i);
//							
//							double oldxDir=camera.getxDir();
//							
//							camera.setxDir( camera.getxDir()*Math.cos(.045) - camera.getyDir()*Math.sin(.045) );
//							camera.setyDir( oldxDir*Math.sin(.045) + camera.getyDir()*Math.cos(.045) );
//							double oldxPlane = camera.getxPlane();
//							
//							camera.setxPlane( camera.getxPlane()*Math.cos(.045) - camera.getyPlane()*Math.sin(.045) );
//							camera.setyPlane( oldxPlane*Math.sin(.045) + camera.getyPlane()*Math.cos(.045) );
//							
//							screen.update(camera, pixels); //Update the screen
//							camera.update(map); //Update the game
//							
//						}				

						
						screen.update(camera, pixels); //Update the screen
						camera.update(map); //Update the game
						render(); //render the graphics
						render(); //You have to do it twice for the BufferStrategy to set up

						try { //Tries to do this
							Thread.sleep(3000); //wait for dramatic affect
						} catch (InterruptedException e) { //If there is a problem
							e.printStackTrace(); //Show the problem
						} //End of dramatic wait
						
						try { //Trys to do this
							end(); //Show the ending screen
							end(); //You need to do it twice for the buffered image to set up
						} catch (IOException e) { //If you cant find the file
							e.printStackTrace(); //Show the problem
						} //End of showing end screen
						
						try { //trys to do do this
							Thread.sleep(10000); //Wait for the user to exit the game while showing their score
						} catch (InterruptedException e){ //If there is a problem
								e.printStackTrace(); //Show the problem
						} //End of dramatic wait
						
						System.exit(0); //If they havent closed the game, closes it for them. 
						
					} //End of checking if the monster is close
					
					if (camera.isCheckGold()) { //If there is gold near by
							
							for (int i = 0; i < sprites.size(); i++) { //For each sprite
								if ((sprites.get(i).getX() == ((int) camera.xPos) && sprites.get(i).getY() == ((int) camera.yPos)) || (sprites.get(i).getX()-1 == ((int) camera.xPos) && sprites.get(i).getY() == ((int) camera.yPos)) || (sprites.get(i).getX()+1 == ((int) camera.xPos) && sprites.get(i).getY() == ((int) camera.yPos)) || (sprites.get(i).getX() == ((int) camera.xPos) && sprites.get(i).getY()-1 == ((int) camera.yPos)) || (sprites.get(i).getX() == ((int) camera.xPos) && sprites.get(i).getY()+1 == ((int) camera.yPos)) || (sprites.get(i).getX()+1 == ((int) camera.xPos) && sprites.get(i).getY()+1 == ((int) camera.yPos)) || (sprites.get(i).getX()+1 == ((int) camera.xPos) && sprites.get(i).getY()-1 == ((int) camera.yPos)) || (sprites.get(i).getX()-1 == ((int) camera.xPos) && sprites.get(i).getY()+1 == ((int) camera.yPos)) || (sprites.get(i).getX()-1 == ((int) camera.xPos) && sprites.get(i).getY()-1 == ((int) camera.yPos))) { //If that sprite is within picking up distance
									if (sprites.get(i) instanceof Gold) { //if that sprite is a gold
										map[sprites.get(i).getX()][sprites.get(i).getY()] = 0; //Sets that sprites position to zero
										sprites.remove(i); //Removes it from the sprite list
										addGold(1); //Adds another gold in a different spot
										score++; //Increases the score
										camera.checkGold =false; //Makes the check if gold false
										break; //Breaks from the loop
									} //End of if gold condition
								} //End of in vicinity condition
							} //End of for loop for the gold
					} //End of the check if gold
					
					
					//handles all of the logic restricted time
					screen.update(camera, pixels);
					camera.update(map);
					delta--;
					
				}
				render();//displays to the screen unrestricted time
				
			} //End of the for loop for the game option
			
		 
		} //End of the while running loop
		
	} //Run end
	
	protected void moveMonster() //Move monster method
	{ //Move monster start
		
		double bestLeft = camera.getDistance(monster.getTrueX()-1, monster.getTrueY()); //Gets the distance from the player if the monster were to move left
		double bestRight = camera.getDistance(monster.getTrueX()+1, monster.getTrueY()); //Gets the distance from the player if the monster were to move right
		double bestUp = camera.getDistance(monster.getTrueX(), monster.getTrueY()-1); //Gets the distance from the player if the monster were to move up
		double bestDown = camera.getDistance(monster.getTrueX(), monster.getTrueY()+1); //Gets the distance from the player if the monster were to move down
			
		if (bestUp <= bestRight && bestUp <= bestDown && bestUp <= bestLeft) { //If its best to go up
			if (map[monster.getX()][monster.getY()-1] == 0) { //If you can go up
				monster.setTrueY(monster.getTrueY() -MONSTER_SPEED); //Go up
			} else if (bestRight <= bestLeft) { //If you cant go up, and its best to move right
				if (map[monster.getX()+1][monster.getY()] == 0) { //If you can more right
					monster.setTrueX(monster.getTrueX()+MONSTER_SPEED); // Move right
				} else if (map[monster.getX()-1][monster.getY()] == 0) { //If you cant move right and can move left
					monster.setTrueX(monster.getTrueX()-MONSTER_SPEED); //Move left
				} else { //If you cant move up, left, or right
					monster.setTrueY(monster.getTrueY()+MONSTER_SPEED); // move down
				} //End of up right checks
			} else { //If its best to move left
				if (map[monster.getX()-1][monster.getY()] == 0) { // If you can move left
					monster.setTrueX(monster.getTrueX()-MONSTER_SPEED); // move left
				} else if (map[monster.getX()+1][monster.getY()] == 0) { //if you can move right
					monster.setTrueX(monster.getTrueX()+MONSTER_SPEED); //Move right
				} else { //If all else fails
					monster.setTrueY(monster.getTrueY()+MONSTER_SPEED); // Move down
				} //End of left up checks
			} // End of up checks
		} else if (bestRight <= bestUp && bestRight <= bestLeft && bestRight <= bestDown) { //if its best to move right
			if (map[monster.getX()+1][monster.getY()] == 0) { // If you can move right
				monster.setTrueX(monster.getTrueX() +MONSTER_SPEED); //Move right
			} else if (bestUp <= bestDown) { //If you canr move right and its best to move up
				if (map[monster.getX()][monster.getY()-1] == 0) { //If you can move up
					monster.setTrueY(monster.getTrueY()-MONSTER_SPEED); // Move up
				} else if (map[monster.getX()][monster.getY()+1] == 0) { //If you can move down
					monster.setTrueY(monster.getTrueY()+MONSTER_SPEED); //Move down
				} else { //If all else fails
					monster.setTrueX(monster.getTrueX()-MONSTER_SPEED); //Move left
				} //End of right up checks
			} else { //If its better to go down
				if (map[monster.getX()][monster.getY()+1] == 0) { //If you can go down
					monster.setTrueY(monster.getTrueY()+MONSTER_SPEED); //Gp down
				} else if (map[monster.getX()][monster.getY()-1] == 0) { //If you can go up
					monster.setTrueY(monster.getTrueY()-MONSTER_SPEED); //Go up
				} else { //If all else fails
					monster.setTrueX(monster.getTrueX()-MONSTER_SPEED); //Move left
				} //End of right down checks
			} //End of right checks
		} else if (bestDown <= bestUp && bestDown <= bestRight && bestDown <= bestLeft) { //If its best to move down
			if (map[monster.getX()][monster.getY()+1] == 0) { //If you can move down
				monster.setTrueY(monster.getTrueY() +MONSTER_SPEED); //Move down
			} else if (bestRight <= bestLeft) { //If its best to move right
				if (map[monster.getX()+1][monster.getY()] == 0) { //If you can move right
					monster.setTrueX(monster.getTrueX()+MONSTER_SPEED); //Move right
				} else if (map[monster.getX()-1][monster.getY()] == 0) { //If you can move left
					monster.setTrueX(monster.getTrueX()-MONSTER_SPEED); //Move left
				} else { //If all else failed
					monster.setTrueY(monster.getTrueY()-MONSTER_SPEED); //Move up
				} //End of down right checks
			} else { //If it was best to go left
				if (map[monster.getX()-1][monster.getY()] == 0) { //If you can go left
					monster.setTrueX(monster.getTrueX()-MONSTER_SPEED); //Go left
				} else if (map[monster.getX()+1][monster.getY()] == 0) { //If you can go right
					monster.setTrueX(monster.getTrueX()+MONSTER_SPEED); //Go right
				} else { //If all else fails
					monster.setTrueY(monster.getTrueY()-MONSTER_SPEED); //Go up
				} //End of down left checks
			} //End of down checks
		} else { //If its best to go left
			if (map[monster.getX()-1][monster.getY()] == 0) { //If you can go left
				monster.setTrueX(monster.getTrueX() -MONSTER_SPEED); //go left
			} else if (bestUp <= bestDown) { //if its best to go up
			if (map[monster.getX()][monster.getY()-1] == 0) { //if you can go up
					monster.setTrueY(monster.getTrueY()-MONSTER_SPEED); //Go up
				} else if (map[monster.getX()][monster.getY()+1] == 0) { //If you can go down
					monster.setTrueY(monster.getTrueY()+MONSTER_SPEED); //Go down
				} else { //If all else fails
					monster.setTrueX(monster.getTrueX()+MONSTER_SPEED); //Go right
				} //End of left up conditions
			} else { //If it was best to go down
				if (map[monster.getX()][monster.getY()+1] == 0) { //If you can go down
					monster.setTrueY(monster.getTrueY()+MONSTER_SPEED); //Go down
				} else if (map[monster.getX()][monster.getY()-1] == 0) { //If you can go up
					monster.setTrueY(monster.getTrueY()-MONSTER_SPEED); //Go up
				} else { //If all else fails
					monster.setTrueX(monster.getTrueX()+MONSTER_SPEED); //Go right
				} //End of down left checks
			} //End of down checks
		} //End of best direction checker and mover	
		
	} //Move monster end
	
	/**
	 * Main method where the program is run from
	 * @param I'm still not really sure what args is
	 * @return nothing
	 * @throws IOException if a file is not found
	 */
	@SuppressWarnings("unused") //Gets rid of an annoying warning
	public static void main(String [] args) //Main method
			throws IOException //Tells java it might not find the files above
	{ //Main method start
		Game game = new Game(); //Now, because the game starts in the constructor, we can run the whole thing with one line of code, by making a game
	} //Main method end
	
} //Game class end