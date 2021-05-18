import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;//import library

public class ContentPanel extends JPanel {
	// declare all the variables
	private int[][] maze;
	private User user;

	// constructor
	public ContentPanel(int[][] maze, User user) {
		super();
		this.setBackground(Color.ORANGE);
		this.maze = maze;
		this.user = user;
		addKeyListener(new Controls());

		setFocusable(true);
		requestFocusInWindow();

	}

	// paintComponet that would take care all the drawing
	public void paintComponent(Graphics g) {
		
		maze[user.getLocation().x][user.getLocation().y] = 2; 
		
		//System.out.println("Yay");
		super.paintComponent(g);
		drawBirdView(g);

	}

	private class Controls implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			System.out.println("bingo");
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				System.out.println("tango");
				user.move(Direction.RIGHT);
				repaint();
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				user.move(Direction.LEFT);
				repaint();
			}

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				user.move(Direction.UP);
				repaint();
			}

			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				user.move(Direction.DOWN);
				repaint();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}

	// draw the small 2D map represent map
	public void drawBirdView(Graphics g) {

		//System.out.println("hi");
		Color c1 = Color.DARK_GRAY;
		Color c2 = Color.white;
		Color c3 = Color.BLUE;
		g.setColor(Color.ORANGE);
		// g.fillRect(800, 600, 400, 200);
		//System.out.println("lo");
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (maze[j][i] == 1) {
					g.setColor(c1);
				}
				if (maze[j][i] == 0) {
					g.setColor(c2);
				}
				if (maze[j][i] == 2) {
					g.setColor(c3);
				}
				if (maze[j][i] == 3) {
					g.setColor(c3);
				}

				g.fillRect(i * maze.length, j * maze[0].length / 2, 800 / maze[0].length, 400 / maze.length);
			}
		}
	}
}
