import java.awt.Point;//import library
import java.util.ArrayList;

public class Maze {
	//declare all the require variables
	private static final int width = 100;
	private static final int height= 500;
	private int[][] board;
	private Point door;
	private boolean isSolved;
	private User user;
	private ArrayList<Enemy> enemies;
	private ArrayList<Treasure> treasure;
	private static int level;
	//constructor
	public Maze(int[][] board, Point door, boolean isSolved, User user, ArrayList<Enemy> enemies,
			ArrayList<Treasure> treasure) {
		super();
		this.board = board;
		this.door = door;
		this.isSolved = isSolved;
		this.user = user;
		this.enemies = enemies;
		this.treasure = treasure;
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
