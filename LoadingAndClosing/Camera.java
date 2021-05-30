import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Camera implements KeyListener, MouseListener{
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public boolean left, right, forward, back;
	public final double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;
	boolean checkGold;
	int clickX, clickY;
	public Camera(double x, double y, double xd, double yd, double xp, double yp) {
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
		clickX = -1; clickY = -1;
	}
	public void keyPressed(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_LEFT))
			left = true;
		if((key.getKeyCode() == KeyEvent.VK_RIGHT))
			right = true;
		if((key.getKeyCode() == KeyEvent.VK_UP))
			forward = true;
		if((key.getKeyCode() == KeyEvent.VK_DOWN))
			back = true;
		if ((key.getKeyCode()== KeyEvent.VK_SPACE)) {
			checkGold = true;
		}
	}
	public void keyReleased(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_LEFT))
			left = false;
		if((key.getKeyCode() == KeyEvent.VK_RIGHT))
			right = false;
		if((key.getKeyCode() == KeyEvent.VK_UP))
			forward = false;
		if((key.getKeyCode() == KeyEvent.VK_DOWN))
			back = false;
		if ((key.getKeyCode()== KeyEvent.VK_SPACE)) {
			checkGold = false;
		}
	}
	
	public boolean ifCheckGold() {
		return checkGold;
	}
	
	public double getDistance(int x, int y) 
	{
		return (yPos - y) * (yPos - y) + (xPos - x) * (xPos - x);
	}
	
	public void update(int[][] map) {
		if(forward) {
			if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0 || map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 7 || map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 8) {
				xPos+=xDir*MOVE_SPEED;
			}
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0 || map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 7 || map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 8)
				yPos+=yDir*MOVE_SPEED;
		}
		if(back) {
			if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0 || map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 7 || map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 8)
				xPos-=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0 || map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==7 || map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==8)
				yPos-=yDir*MOVE_SPEED;
		}
		if(right) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
			yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
		}
		if(left) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
			yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
		}
	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		clickX = e.getX();
		clickY = e.getY();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}