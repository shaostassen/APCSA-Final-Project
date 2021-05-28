import java.util.ArrayList;
import java.awt.Color;

public class Screen {
	public int[][] map;
	public int mapWidth, mapHeight, width, height;
	public ArrayList<Texture> textures;
	public double[] ZBuffer;
	ArrayList<Sprite> sprites;
	protected int[][] buffer = new int[480][640];
	
	public Screen(int[][] m, int mapW, int mapH, ArrayList<Texture> tex, int w, int h) {
		map = m;
		mapWidth = mapW;
		mapHeight = mapH;
		textures = tex;
		width = w;
		height = h;
		ZBuffer = new double[640];
		//int[][] otherBuffer = new int[640][480];
		sprites = new ArrayList<Sprite>(5);
	}
	
	public void/*int[]*/ update(Camera camera, int[] pixels) {
		for(int n=0; n<pixels.length; n++) {
			if(pixels[n] != Color.DARK_GRAY.getRGB()) pixels[n] = Color.DARK_GRAY.getRGB();
		}
	    
	    for(int x=0; x<width; x=x+1) {
			double cameraX = 2 * x / (double)(width) -1;
		    double rayDirX = camera.xDir + camera.xPlane * cameraX;
		    double rayDirY = camera.yDir + camera.yPlane * cameraX;
		    //Map position
		    int mapX = (int)camera.xPos;
		    int mapY = (int)camera.yPos;
		    //length of ray from current position to next x or y-side
		    double sideDistX;
		    double sideDistY;
		    //Length of ray from one side to next in map
		    double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
		    double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
		    double perpWallDist;
		    //Direction to go in x and y
		    int stepX, stepY;
		    boolean hit = false;//was a wall hit
		    int side=0;//was the wall vertical or horizontal
		    //Figure out the step direction and initial distance to a side
		    if (rayDirX < 0)
		    {
		    	stepX = -1;
		    	sideDistX = (camera.xPos - mapX) * deltaDistX;
		    }
		    else
		    {
		    	stepX = 1;
		    	sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
		    }
		    if (rayDirY < 0)
		    {
		    	stepY = -1;
		        sideDistY = (camera.yPos - mapY) * deltaDistY;
		    }
		    else
		    {
		    	stepY = 1;
		        sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
		    }
		    //Loop to find where the ray hits a wall
		    while(!hit) {
		    	//Jump to next square
		    	if (sideDistX < sideDistY)
		        {
		    		sideDistX += deltaDistX;
		    		mapX += stepX;
		    		side = 0;
		        }
		        else
		        {
		        	sideDistY += deltaDistY;
		        	mapY += stepY;
		        	side = 1;
		        }
		    	//Check if ray has hit a wall
		    	//System.out.println(mapX + ", " + mapY + ", " + map[mapX][mapY]);
		    	if(map[mapX][mapY] != 0 && map[mapX][mapY] != 7 && map[mapX][mapY] != 8) hit = true;
		    }
		    //Calculate distance to the point of impact
		    if(side==0)
		    	perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
		    else
		    	perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);	
		    //Now calculate the height of the wall based on the distance from the camera
		    int lineHeight;
		    if(perpWallDist > 0) lineHeight = Math.abs((int)(height / perpWallDist));
		    else lineHeight = height;
		    //calculate lowest and highest pixel to fill in current stripe
		    int drawStart = -lineHeight/2+ height/2;
		    if(drawStart < 0)
		    	drawStart = 0;
		    int drawEnd = lineHeight/2 + height/2;
		    if(drawEnd >= height) 
		    	drawEnd = height - 1;
		    //add a texture
		    int texNum = map[mapX][mapY] - 1;
		    double wallX;//Exact position of where wall was hit
		    if(side==1) {//If its a y-axis wall
		    	wallX = (camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
		    } else {//X-axis wall
		    	wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
		    }
		    wallX-=Math.floor(wallX);
		    //x coordinate on the texture
		    int texX = (int)(wallX * (textures.get(texNum).SIZE));
		    if((side == 0) && rayDirX > 0) texX = textures.get(texNum).SIZE - texX - 1;
		    if(side == 1 && rayDirY < 0) texX = textures.get(texNum).SIZE - texX - 1;
		    //calculate y coordinate on texture
		    for(int y=drawStart; y<drawEnd; y++) {
		    	int texY = (((y*2 - height + lineHeight) << 6) / lineHeight) / 2;
		    	int color;
		    	if(side==0) color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)];
		    	else color = (textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)]>>1) & 8355711;//Make y sides darker
		    	pixels[x + y*(width)] = color;
		    }
		    //----------------------------------------------------
		    ZBuffer[x] = perpWallDist;
		    
		   
		    
		}
		//return pixels;
	    
	    for (int i = 1; i < sprites.size(); i++) {
			int spot = i;
			while (spot > 0 && camera.getDistance(sprites.get(spot).getX(), sprites.get(spot).getY()) > camera.getDistance(sprites.get(spot-1).getX(), sprites.get(spot-1).getY())) {
				swap(sprites,spot,spot-1);
				spot--;
			}
		}
	    
	    resetBuffer();
	    
	    final int w = 640, h = 480;
	    
	    for (Sprite sprite: sprites) {
	    	
	    	
	    	
	    	double spriteX = sprite.getX() - camera.xPos;
	    	double spriteY = sprite.getY() - camera.yPos;
	    	
	    	double invDet = 1.0 / (camera.xPlane * camera.yDir - camera.xDir * camera.yPlane);
	    	
	    	double transformX = invDet * (camera.yDir * spriteX - camera.xDir * spriteY);
	    	double transformY = invDet * ((camera.yPlane * -1) * spriteX + camera.xPlane * spriteY);
	    	
	    	int spriteScreenX = (int)((w / 2) * (1 + transformX / transformY));
	    	
	    	int spriteHeight = Math.abs((int)(h / (transformY)));
	    	
	        int drawStartY = (-1* spriteHeight) / 2 + h / 2;
	        if (drawStartY < 0) {drawStartY = 0;}
	        int drawEndY = spriteHeight / 2 + h / 2;
	        if (drawEndY >= h) {drawEndY = h - 1;}
	        
	        int spriteWidth = Math.abs( (int) (h / (transformY)));
	        int drawStartX = (-1 * spriteWidth) / 2 + spriteScreenX;
	        if (drawStartX < 0) {drawStartX = 0;}
	        int drawEndX = spriteWidth / 2 + spriteScreenX;
	        if (drawEndX >= w) {drawEndX = w - 1;}
	        
	        int texWidth = 0, texHeight = 0;
	        if (sprite instanceof Gold) { texWidth = 880; texHeight = 552; }
	        else { texWidth = 626; texHeight = 565; }
	        
	        for (int stripe = drawStartX; stripe < drawEndX; stripe++) {
	        	int texX = (int) (256 * (stripe - ((-1 * spriteWidth) / 2 + spriteScreenX)) * texWidth / spriteWidth) / 256;
	        	if (transformY > 0 && stripe > 0 && stripe < w && transformY < ZBuffer[stripe]) {
	        		for(int y = drawStartY; y < drawEndY; y++) {
	        			 int d = (y) * 256 - h * 128 + spriteHeight * 128;
	        			 int texY = ((d * texHeight) / spriteHeight) / 256;
	        			 try {
	        				 int color;
	        				 if (sprite instanceof Gold) {
	        					 color = SpriteTexture.gold.pixels[texX][texY];
	        				 }
	        				 else { color = SpriteTexture.gold.pixels[texX][texY]; }
	        				 if((color & 0x00FFFFFF) != 0) {buffer[y][stripe] = color;}
	        			 } catch (ArrayIndexOutOfBoundsException e) {
	        				 continue;
	        			 }
	        		} 
	        	}
	        }
	    	
	    }
	    
	}
	
	public int[][] getBuffer()
	{
		return buffer;
	}

	protected void resetBuffer()
	{
		for (int i = 0; i < buffer.length; i++) {
			for (int j = 0; j < buffer[i].length; j++) {
				buffer[i][j] = 0;
			}
		}
	}
	
	private static void swap(ArrayList<Sprite> sprites2, int one, int two) {
		Sprite temp = sprites2.get(one);
		sprites2.set(one, sprites2.get(two));
		sprites2.set(two, temp);
	}
	
	public void updateSprites(ArrayList<Sprite> sprites) {
		this.sprites = sprites;
	}
}