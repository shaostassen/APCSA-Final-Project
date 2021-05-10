import java.awt.Point;//import library
import java.util.ArrayList;
import java.util.Arrays;

public class Maze {
	//declare all the require variables
	private static final int width = 60;
	private static final int height= 40;
	private int[][] board;
	private Point door;
	private boolean isSolved;
	private User user;
	private ArrayList<Enemy> enemies;
	private ArrayList<Treasure> treasure;
	private static int level;
	//constructors
	public Maze(Point door, boolean isSolved, User user, ArrayList<Enemy> enemies,
			ArrayList<Treasure> treasure) {
		super();
		this.door = door;
		this.isSolved = isSolved;
		this.user = user;
		this.enemies = enemies;
		this.treasure = treasure;
	}
	
	public Maze() {
		super();
		
	}

	//make a 2D array, random generate a maze
	public boolean generateMaze() {
		int[][] board = new int[height][width];
		for (int i = 0;i<board[0].length;i+=board.length-1) {
			for (int j=0; j<board.length;j++) {
				 board[j][i]=1;
			}
		}
		for (int i = 0;i<board.length;i+=board[0].length-1) {
			for (int j=0; j<board[0].length;j++) {
				 board[j][i]=1;
			}
		}
		System.out.println(Arrays.deepToString(board));
		
	}
	public boolean checkIfSolved() {
		
		
	}
    //getters and setters
	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public Point getDoor() {
		return door;
	}

	public void setDoor(Point door) {
		this.door = door;
	}

	public boolean isSolved() {
		return isSolved;
	}

	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public ArrayList<Treasure> getTreasure() {
		return treasure;
	}

	public void setTreasure(ArrayList<Treasure> treasure) {
		this.treasure = treasure;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		Maze.level = level;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	
	
}



