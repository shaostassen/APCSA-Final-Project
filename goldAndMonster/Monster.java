
public class Monster implements Sprite {
	
	protected double x;
	protected double y;
	public static final SpriteTexture texture1 = SpriteTexture.monster1;
	public static final SpriteTexture texture2 = SpriteTexture.monster2;
	public static final SpriteTexture texture3 = SpriteTexture.monster3;
	public SpriteTexture curTexture = texture1;
	private int textureCount = 0;
	
	public Monster() {
		x = 0;
		y = 0;
	}
	
	public Monster(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public double getTrueX() {
		return x;
	}
	
	public double getTrueY() {
		return y;
	}
	
	public void setTrueX(double x) {
		this.x = x;
	}
	
	public void setTrueY(double y) {
		this.y = y;
	}
	
	public void nextTexture() {
		switch (textureCount) {
		
		case 0: curTexture = texture2; break;
		case 1: curTexture = texture1; break;
		case 2: curTexture = texture3; break;
		}
		textureCount++;
		if (textureCount == 3) { textureCount = 0; }
	}

	
}
