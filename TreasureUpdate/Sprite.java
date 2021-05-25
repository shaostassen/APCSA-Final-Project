
public class Sprite {


	protected int x;
	protected int y;
	public static final Texture texture = Texture.gold;
	
	public Sprite() {
		x = 0;
		y = 0;
	}
	
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
