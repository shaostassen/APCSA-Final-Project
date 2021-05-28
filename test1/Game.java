import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends JFrame implements Runnable{
	
	private int score = 0;
	
	private static final long serialVersionUID = 1L;
	public int mapWidth = 15;
	public int mapHeight = 15;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public int[] pixels;
	public ArrayList<Texture> textures;
	public ArrayList<Sprite> sprites;
	public Camera camera;
	public Screen screen;
	public static int[][] map = 
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
		};
	public Game() {
		thread = new Thread(this);
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
		addKeyListener(camera);
		setSize(640, 480);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		sprites = new ArrayList<Sprite>(5);
		addGold(5);
		start();
	}
	
	public int[][] getMap()
	{
		return map;
	}
	
	public void updateScore()
	{
		score++;
	}
	
	public void addGold(int gold)
	{
		for (int i = 0; i < gold; i++) {
			int x = 0, y = 0;
			while(map[x][y] != 0) {
				x = 1+ ((int) (Math.random() * map.length-2));
				y = 1+ ((int) (Math.random() * map.length-2));
			}
			map[x][y] = 7;
			sprites.add(new Sprite(x,y));
		}
		
		for (int[] arr: map) {
			for (int elem: arr) {
				System.out.print(elem + " ");
			}
			System.out.println();
		}
	}
	
	private synchronized void start() {
		running = true;
		thread.start();
	}
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		
		int[][] buffer = screen.getBuffer();
		
//		for (int i = 0; i < buffer.length; i++) { 
//			for (int j = 0; j < buffer[i].length; j++) {
//				System.out.print(buffer[i][j]+" ");
//			}
//			System.out.println();
//		}
		
//		for(int i = 0;i<map[0].length;i++) {
//	    	for (int j= 0;j<map.length;j++) {
//	    		if(map[i][j]==0) {
//	    			g.setColor(Color.white);
//	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
//	    		}
//	    		if(map[i][j]==1) {
//	    			g.setColor(Color.black);
//	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
//	    		}
//	    		if(map[i][j]==2) {
//	    			g.setColor(Color.black);
//	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
//	    		}
//	    		if(map[i][j]==3) {
//	    			g.setColor(Color.black);
//	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
//	    		}
//	    		if(map[i][j]==4) {
//	    			g.setColor(Color.black);
//	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
//	    		}
//	    		if(map[i][j]==7) {
//	    			g.setColor(Color.white);
//	    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
//	    			g.setColor(new Color(242,252,26));
//	    			g.fillOval(i*180/map[0].length+3,30+j*180/map.length+3,180/map[0].length-3,180/map.length-3);
//	    			
//	    		}
//	    	}
//	    }
//	    g.setColor(Color.green);
//	    int xP = (int) Math.round(camera.xPos);
//	    int yP = (int) Math.round(camera.yPos);
//		g.fillOval(xP*180/map[0].length-3,30+yP*180/map.length-3,180/map[0].length-3,180/map.length-3);
//		
//		int angle= (int) Math.asin(camera.yDir);
//		
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setColor(Color.orange);
//		g2.setStroke(new BasicStroke(1));
//              System.out.println(xP+" "+yP);
		//System.out.println((int) (Math.cos(angle-30)*3) +xB+ " " + (int)(Math.sin(angle-30)*3)+yB);
		//System.out.println((int) (Math.cos(angle+30)*3) +xB + " "+(int)(Math.sin(angle+30)*3)+yB);

		g2.drawPolygon(new int[]{xB,(int) (Math.cos(360-angle-30)*6)+xB,(int) (Math.cos(angle+30)*6) +xB}, new int[] {yB,(int)(Math.sin(angle+30)*6)+yB,(int)(Math.sin(angle-30)*6)+yB},3 );
		

		

		
		g.setFont(new Font("Serif",Font.BOLD, 20));
		g.setColor(Color.RED);
		g.drawString(new String("Score: " + score), 550, 55);
		
		for (int x = 0; x < buffer.length; x++) {
			for (int y = 0; y < buffer[x].length; y++) {
				if (buffer[x][y] != 0) {
					g.setColor(new Color(buffer[x][y]));
					g.fillRect(y,x,1,1);
				}
			}
		}
		
		bs.show();
	}
	

	
	public void run() {
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		requestFocus();
		screen.updateSprites(sprites);
		while(running) {
			
			long now = System.nanoTime();
			delta = delta + ((now-lastTime) / ns);
			lastTime = now;
			while (delta >= 1)//Make sure update is only happening 60 times a second
			{
				
				if (camera.ifCheckGold()) {
					if (7 == map[(int) camera.xPos][(int) camera.yPos]) {
						map[(int) camera.xPos][(int) camera.yPos] = 0;
						for (int i = 0; i < sprites.size(); i++) {
							if (sprites.get(i).getX() == camera.xPos && sprites.get(i).getY() == camera.yPos) {
								sprites.remove(i);
							}
						}
						score++;
						System.out.println();
						addGold(1);
					}
				}
				//handles all of the logic restricted time
				screen.update(camera, pixels);
				camera.update(map);
				delta--;
			}
			render();//displays to the screen unrestricted time
			
			
		
		}
	}

	
	public static void main(String [] args) {
		Game game = new Game();
	}
}
