import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends JFrame implements Runnable{
	
	private int score = 0;
	
	private int gamePhase = 0;
	
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
	public double monsterSpeed = .1;
	public Screen screen;
	private Monster monster;
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
	public Game() throws IOException {
		setSize(640, 480);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		showStart();
		thread = new Thread(this);
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		monster = new Monster(6,11);
		screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480, monster);
		addKeyListener(camera);
		addMouseListener(camera);
		sprites = new ArrayList<Sprite>(5);
		addGold(5);
		//map[10][2] = 8;
		sprites.add(monster);
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
			while(map[x][y] != 0 && map[x][y] != 8 && map[x][y] != 7) {
				x = 1+ ((int) (Math.random() * map.length-2));
				y = 1+ ((int) (Math.random() * map.length-2));
			}
			map[x][y] = 7;
			sprites.add(new Gold(x,y));
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
	
	public void showStart() throws IOException {
		
		BufferedImage img = ImageIO.read(new File("open.jfif"));
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(img,0,10,640,480,null);
		bs.show();
		
	}
	
	public void end() throws IOException {
		BufferedImage img = ImageIO.read(new File("close.jfif"));
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(img,0,15,640,480,null);
		
		g.setFont(new Font("Serif",Font.BOLD, 50));
		g.setColor(Color.YELLOW);
		g.drawString("Final Score: " + score, 175, 430);
		
		bs.show();
		

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
		
		for (int x = 0; x < buffer.length; x++) {
			for (int y = 0; y < buffer[x].length; y++) {
				if (buffer[x][y] != 0) {
					g.setColor(new Color(buffer[x][y]));
					g.fillRect(y,x,1,1);
				}
			}
		}
		
//		for (int i = 0; i < buffer.length; i++) { 
//		for (int j = 0; j < buffer[i].length; j++) {
//			System.out.print(buffer[i][j]+" ");
//		}
//		System.out.println();
//	}
	
	for(int i = 0;i<map[0].length;i++) {
    	for (int j= 0;j<map.length;j++) {
    		if(map[i][j]==0) {
    			g.setColor(Color.white);
    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
    		}
    		if(map[i][j]==1) {
    			g.setColor(Color.black);
    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
    		}
    		if(map[i][j]==2) {
    			g.setColor(Color.black);
    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
    		}
    		if(map[i][j]==3) {
    			g.setColor(Color.black);
    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
    		}
    		if(map[i][j]==4) {
    			g.setColor(Color.black);
    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
    		}
    		if(map[i][j]==7) {
    			g.setColor(Color.white);
    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
    			g.setColor(new Color(242,252,26));
    			g.fillOval(i*180/map[0].length+3,30+j*180/map.length+3,180/map[0].length-3,180/map.length-3);
    			
    		}
//    		if (map[i][j] == 8) {
//    			g.setColor(Color.WHITE);
//    			g.fillRect(i*180/map[0].length,30+j*180/map.length,180/map[0].length,180/map.length);
//    			g.setColor(Color.RED);
//    			g.fillOval(i*180/map[0].length+3,30+j*180/map.length+3,180/map[0].length-3,180/map.length-3);
//    		}
    	}
    	

    }
	
	g.setColor(Color.RED);
	g.fillOval((int) (monster.getTrueX()*180/map[0].length+3),(int) (30+monster.getTrueY()*180/map.length+3),180/map[0].length-3,180/map.length-3);
	
    g.setColor(Color.green);
   // int xP = (int) Math.round(camera.xPos);
   // int yP = (int) Math.round(camera.yPos);
	g.fillOval((int)(camera.xPos*180/map[0].length-3),(int)(30+camera.yPos*180/map.length-3),180/map[0].length-3,180/map.length-3);
	
	//int angle= (int) Math.asin(camera.yDir);
	
	Graphics2D g2 = (Graphics2D) g;
	g2.setColor(Color.orange);
	g2.setStroke(new BasicStroke(1));
          //System.out.println(xP+" "+yP);

	//g2.drawPolygon(new int[]{xP,(int) (Math.cos(360-angle-30)*6)+xP,(int) (Math.cos(angle+30)*6) +xP}, new int[] {yP,(int)(Math.sin(angle+30)*6)+yP,(int)(Math.sin(angle-30)*6)+yP},3 );
		

		
		g.setFont(new Font("Serif",Font.BOLD, 20));
		g.setColor(Color.RED);
		g.drawString(new String("Score: " + score), 550, 55);
		
    	if (gamePhase == 2) {
    		//System.out.println(gamePhase);
			g.setColor(new Color(255,0,0,125));
			g.fillRect(0,0,640,480);
    	}
		
		bs.show();
	}
	

	
	public void run() {
		
		
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;//60 times per second
		double delta = 0;
		requestFocus();
		screen.updateSprites(sprites);
		int monsterTimer = 0;
		while(running) {
			
			if (gamePhase == 0) {
				
				try {
					showStart();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if ((camera.clickX >= 223 && camera.clickX <= 397) && (camera.clickY >= 275 && camera.clickY <= 339)) {
					gamePhase = 1;
				}
				
			} else if (gamePhase == 1) {
				long now = System.nanoTime();
				delta = delta + ((now-lastTime) / ns);
				lastTime = now;
				
				while (delta >= 1)//Make sure update is only happening 60 times a second
				{
					
					if (monsterTimer == 10) { monsterTimer = 0; monster.nextTexture(); moveMonster(); monsterSpeed+=.000005;}
					monsterTimer++;
					
					if (Math.abs(monster.getTrueX() - camera.xPos) <= 1 && Math.abs(monster.getTrueY() - camera.yPos) <= 1) {
						gamePhase = 2;
						//camera.xDir = /* make it point twords enemy*/
						screen.update(camera, pixels);
						camera.update(map);
						render();
						render();

						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							end();
							end();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						System.exit(0);
						
						
					}
					
					if (camera.ifCheckGold()) {
						//if (7 == map[(int) camera.xPos][(int) camera.yPos] || 7 == map[(int) camera.xPos-1][(int) camera.yPos] || 7 == map[(int) camera.xPos+1][(int) camera.yPos] || 7 == map[(int) camera.xPos][(int) camera.yPos+1] || 7 == map[(int) camera.xPos][(int) camera.yPos-1] || 7 == map[(int) camera.xPos+1][(int) camera.yPos-1] || 7 == map[(int) camera.xPos+1][(int) camera.yPos+1] || 7 == map[(int) camera.xPos-1][(int) camera.yPos+1] || 7 == map[(int) camera.xPos-1][(int) camera.yPos-1]) {
							
							for (int i = 0; i < sprites.size(); i++) {
								if ((sprites.get(i).getX() == ((int) camera.xPos) && sprites.get(i).getY() == ((int) camera.yPos)) || (sprites.get(i).getX()-1 == ((int) camera.xPos) && sprites.get(i).getY() == ((int) camera.yPos)) || (sprites.get(i).getX()+1 == ((int) camera.xPos) && sprites.get(i).getY() == ((int) camera.yPos)) || (sprites.get(i).getX() == ((int) camera.xPos) && sprites.get(i).getY()-1 == ((int) camera.yPos)) || (sprites.get(i).getX() == ((int) camera.xPos) && sprites.get(i).getY()+1 == ((int) camera.yPos)) || (sprites.get(i).getX()+1 == ((int) camera.xPos) && sprites.get(i).getY()+1 == ((int) camera.yPos)) || (sprites.get(i).getX()+1 == ((int) camera.xPos) && sprites.get(i).getY()-1 == ((int) camera.yPos)) || (sprites.get(i).getX()-1 == ((int) camera.xPos) && sprites.get(i).getY()+1 == ((int) camera.yPos)) || (sprites.get(i).getX()-1 == ((int) camera.xPos) && sprites.get(i).getY()-1 == ((int) camera.yPos))) {
									if (sprites.get(i) instanceof Gold) {
										map[sprites.get(i).getX()][sprites.get(i).getY()] = 0;
										sprites.remove(i);
										addGold(1);
										score++;
										camera.checkGold =false;
										break;
									}
								}
							}
							
	
						//}
					}
					
					
					//handles all of the logic restricted time
					screen.update(camera, pixels);
					camera.update(map);
					delta--;
					
				}
				render();//displays to the screen unrestricted time
				
			} else {
				
			}
			
		 
		}
	}
	
	public void moveMonster()
	{
		
		
		//map[(int) Math.round(monster.getTrueX())][(int) Math.round(monster.getTrueY())] = 0;
		
//		char[] direction = new char[] {'l','r','u','d'};
//		double[] distance = new double[4];
		
		double bestLeft = camera.getDistance(monster.getX()-1, monster.getY());
		double bestRight = camera.getDistance(monster.getX()+1, monster.getY());
		double bestUp = camera.getDistance(monster.getX(), monster.getY()-1);
		double bestDown = camera.getDistance(monster.getX(), monster.getY()+1);
		
//		for (int i = 0; i < 4; i++) {
//			double smallest = distance[i];
//			int smallestI = i;
//			for (int j = i; j < 4; j++) {
//				if (distance[j] < smallest) { smallest = distance[j]; smallestI = j; }
//			}
//			if (i != smallestI) { 
//				double tempD = distance[i]; distance[i] = distance[smallestI]; distance[smallestI] = tempD;
//				char tempC = direction[i]; direction[i] = direction[smallestI]; distance[smallestI] = tempC;
//			}
//		}
		
		
		
		//System.out.println(bestLeft + "" + bestRight + "" + bestUp + "" + bestDown);
		
//		
//		int bestUp = getBestRoute(monster.getX(), monster.getY(), (int) Math.round(camera.xPos), (int) Math.round(camera.yPos), 1, 0);
//		int bestRight = getBestRoute(monster.getX(), monster.getY(), (int) Math.round(camera.xPos), (int) Math.round(camera.yPos), 2, 0);
//		int bestDown = getBestRoute(monster.getX(), monster.getY(), (int) Math.round(camera.xPos), (int) Math.round(camera.yPos), 3, 0);
//		int bestLeft = getBestRoute(monster.getX(), monster.getY(), (int) Math.round(camera.xPos), (int) Math.round(camera.yPos), 4, 0);
//		
//		
		if (bestUp <= bestRight && bestUp <= bestDown && bestUp <= bestLeft) {
			if (map[monster.getX()][monster.getY()-1] == 0) {
				monster.setTrueY(monster.getTrueY() -monsterSpeed);
			} else if (bestRight <= bestLeft) {
				if (map[monster.getX()+1][monster.getY()] == 0) {
					monster.setTrueX(monster.getTrueX()+monsterSpeed);
				} else if (map[monster.getX()-1][monster.getY()] == 0) {
					monster.setTrueX(monster.getTrueX()-monsterSpeed);
				} else {
					monster.setTrueY(monster.getTrueY()+monsterSpeed);
				}
			} else {
				if (map[monster.getX()-1][monster.getY()] == 0) {
					monster.setTrueX(monster.getTrueX()-monsterSpeed);
				} else if (map[monster.getX()+1][monster.getY()] == 0) {
					monster.setTrueX(monster.getTrueX()+monsterSpeed);
				} else {
					monster.setTrueY(monster.getTrueY()+monsterSpeed);
				}
			}
		} else if (bestRight <= bestUp && bestRight <= bestLeft && bestRight <= bestDown) {
			if (map[monster.getX()+1][monster.getY()] == 0) {
				monster.setTrueX(monster.getTrueX() +monsterSpeed);
			} else if (bestUp <= bestDown) {
				if (map[monster.getX()][monster.getY()-1] == 0) {
					monster.setTrueY(monster.getTrueY()-monsterSpeed);
				} else if (map[monster.getX()][monster.getY()+1] == 0) {
					monster.setTrueY(monster.getTrueY()+monsterSpeed);
				} else {
					monster.setTrueX(monster.getTrueX()-monsterSpeed);
				}
			} else {
				if (map[monster.getX()][monster.getY()+1] == 0) {
					monster.setTrueY(monster.getTrueY()+monsterSpeed);
				} else if (map[monster.getX()][monster.getY()-1] == 0) {
					monster.setTrueY(monster.getTrueY()-monsterSpeed);
				} else {
					monster.setTrueX(monster.getTrueX()-monsterSpeed);
				}
			}
		} else if (bestDown <= bestUp && bestDown <= bestRight && bestDown <= bestLeft) {
			if (map[monster.getX()][monster.getY()+1] == 0) {
				monster.setTrueY(monster.getTrueY() +monsterSpeed);
			} else if (bestRight <= bestLeft) {
				if (map[monster.getX()+1][monster.getY()] == 0) {
					monster.setTrueX(monster.getTrueX()+monsterSpeed);
				} else if (map[monster.getX()-1][monster.getY()] == 0) {
					monster.setTrueX(monster.getTrueX()-monsterSpeed);
				} else {
					monster.setTrueY(monster.getTrueY()-monsterSpeed);
				}
			} else {
				if (map[monster.getX()-1][monster.getY()] == 0) {
					monster.setTrueX(monster.getTrueX()-monsterSpeed);
				} else if (map[monster.getX()+1][monster.getY()] == 0) {
					monster.setTrueX(monster.getTrueX()+monsterSpeed);
				} else {
					monster.setTrueY(monster.getTrueY()-monsterSpeed);
				}
			}
		} else {
			if (map[monster.getX()-1][monster.getY()] == 0) {
				monster.setTrueX(monster.getTrueX() -monsterSpeed);
			} else if (bestUp <= bestDown) {
				if (map[monster.getX()][monster.getY()-1] == 0) {
					monster.setTrueY(monster.getTrueY()-monsterSpeed);
				} else if (map[monster.getX()][monster.getY()+1] == 0) {
					monster.setTrueY(monster.getTrueY()+monsterSpeed);
				} else {
					monster.setTrueX(monster.getTrueX()+monsterSpeed);
				}
			} else {
				if (map[monster.getX()][monster.getY()+1] == 0) {
					monster.setTrueY(monster.getTrueY()+monsterSpeed);
				} else if (map[monster.getX()][monster.getY()-1] == 0) {
					monster.setTrueY(monster.getTrueY()-monsterSpeed);
				} else {
					monster.setTrueX(monster.getTrueX()+monsterSpeed);
				}
			}
		}
				
		
//		for (int i = 0; i < 4; i++) {
//			if (tryAndMove(direction[i])) { break; }
//		}
		
		//map[(int) Math.round(monster.getTrueX())][(int) Math.round(monster.getTrueY())] = 8;

		
		
	}
	
//	public boolean tryAndMove(char direction) {
//		switch (direction) {
//		
//		case 'l':
//			if ((int) Math.round(monster.getTrueX()) <=0 || map[-1 + ((int) Math.round(monster.getTrueX()))][(int) Math.round(monster.getTrueY())] != 0) { return false; }
//			monster.setTrueX(monster.getTrueX()-.1);
//			break;
//		case 'd': 
//			if ((int) Math.round(monster.getTrueX()) >= map.length-1 || map[((int) Math.round(monster.getTrueX()))][1 +(int) Math.round(monster.getTrueY())] != 0) { return false; }
//			monster.setTrueY(monster.getTrueY() +.1);
//			break;
//		case 'u':
//			if ((int) Math.round(monster.getTrueX()) <=0 || map[((int) Math.round(monster.getTrueX()))][-1 + (int) Math.round(monster.getTrueY())] != 0) { return false; }
//			monster.setTrueY(monster.getTrueY() -.1);
//			break;
//		case 'r':
//			if ((int) Math.round(monster.getTrueX()) >= map[0].length-1 || map[1 + ((int) Math.round(monster.getTrueX()))][(int) Math.round(monster.getTrueY())] != 0) { return false; }
//			monster.setTrueX(monster.getTrueX() +.1);
//			break;
//		}
//		return false;
//	}

//	public int getBestRoute(int x1, int y1, int x2, int y2, int direction, int depth) {
//		
//		if (depth > 20) { return 0; }
//		
//		int bestUp;
//		int bestRight;
//		int bestDown;
//		int bestLeft; 
//		
//		switch (direction) {
//		
//		case 1: 
//			if (y1 <= 0) { return 0; }
//			if (x1 == x2 && y1 == y2) { return 0; }
//			
//			bestUp = getBestRoute(x1,y1-1,x2,y2,1, depth+1);
//			bestRight = getBestRoute(x1,y1-1,x2,y2,2, depth+1);
//			bestDown = getBestRoute(x1,y1-1,x2,y2,3, depth+1);
//			bestLeft = getBestRoute(x1,y1-1,x2,y2,4, depth+1);
//			
//			if (bestUp >= bestRight && bestUp >= bestDown && bestUp >= bestLeft) {
//				return 1 + bestUp;
//			} else if (bestRight >= bestUp && bestRight >= bestDown && bestRight >= bestLeft) {
//				return 1+ bestRight;
//			} else if (bestDown >= bestUp && bestDown >= bestLeft && bestDown >= bestRight) {
//				return 1 + bestDown;
//			} else {
//				return 1+bestLeft;
//			}
//			
//		case 2:
//			
//			if (x2 >= map[0].length-1) { return 0; }
//			if (x1 == x2 && y1 == y2) { return 0; }
//			
//			bestUp = getBestRoute(x1+1,y1,x2,y2,1, depth+1);
//			bestRight = getBestRoute(x1+1,y1,x2,y2,2, depth+1);
//			bestDown = getBestRoute(x1+1,y1-1,x2,y2,3, depth+1);
//			bestLeft = getBestRoute(x1+1,y1-1,x2,y2,4, depth+1);
//			
//			if (bestUp >= bestRight && bestUp >= bestDown && bestUp >= bestLeft) {
//				return 1 + bestUp;
//			} else if (bestRight >= bestUp && bestRight >= bestDown && bestRight >= bestLeft) {
//				return 1+ bestRight;
//			} else if (bestDown >= bestUp && bestDown >= bestLeft && bestDown >= bestRight) {
//				return 1 + bestDown;
//			} else {
//				return 1+bestLeft;
//			}
//			
//		case 3: 
//			
//			if (y1 >= map.length) { return 0; }
//			if (x1 == x2 && y1 == y2) { return 0; }
//			
//			bestUp = getBestRoute(x1,y1+1,x2,y2,1, depth+1);
//			bestRight = getBestRoute(x1,y1+1,x2,y2,2, depth+1);
//			bestDown = getBestRoute(x1,y1+1,x2,y2,3, depth+1);
//			bestLeft = getBestRoute(x1,y1+1,x2,y2,4, depth+1);
//			
//			if (bestUp >= bestRight && bestUp >= bestDown && bestUp >= bestLeft) {
//				return 1 + bestUp;
//			} else if (bestRight >= bestUp && bestRight >= bestDown && bestRight >= bestLeft) {
//				return 1+ bestRight;
//			} else if (bestDown >= bestUp && bestDown >= bestLeft && bestDown >= bestRight) {
//				return 1 + bestDown;
//			} else {
//				return 1+bestLeft;
//			}
//			
//		case 4: 
//			
//			if (x2 >= map[0].length-1) { return 0; }
//			if (x1 == x2 && y1 == y2) { return 0; }
//			
//			bestUp = getBestRoute(x1-1,y1,x2,y2,1, depth+1);
//			bestRight = getBestRoute(x1-1,y1,x2,y2,2, depth+1);
//			bestDown = getBestRoute(x1-1,y1-1,x2,y2,3, depth+1);
//			bestLeft = getBestRoute(x1-1,y1-1,x2,y2,4, depth+1);
//			
//			if (bestUp >= bestRight && bestUp >= bestDown && bestUp >= bestLeft) {
//				return 1 + bestUp;
//			} else if (bestRight >= bestUp && bestRight >= bestDown && bestRight >= bestLeft) {
//				return 1+ bestRight;
//			} else if (bestDown >= bestUp && bestDown >= bestLeft && bestDown >= bestRight) {
//				return 1 + bestDown;
//			} else {
//				return 1+bestLeft;
//			}
//		}
//		
//		return 0;
//	}
	
	public static void main(String [] args) throws IOException {
		Game game = new Game();
	}
}