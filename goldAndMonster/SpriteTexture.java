import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteTexture {
	public int[][] pixels;
	private String loc;
	int SIZE;
	
	public SpriteTexture(String location) {
		loc = location;
		load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(new File(loc));
			int w = image.getWidth();
			int h = image.getHeight();
			pixels = new int[w][h];
			SIZE = w*h;
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					pixels[x][y] = image.getRGB(x, y);
				}
			}
			//image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	public static SpriteTexture gold = new SpriteTexture("gold.jfif");
	public static SpriteTexture monster1 = new SpriteTexture("monster1.jfif");
	public static SpriteTexture monster2 = new SpriteTexture("monster2.jfif");
	public static SpriteTexture monster3 = new SpriteTexture("monster3.jfif");

}