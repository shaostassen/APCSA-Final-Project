import java.awt.Color;

import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame();
		frame.setSize(800,400);
		
		
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MazeGenerator x = new MazeGenerator(10,10);
		x.updateGrid();
		x.draw();
		int[][] arr2D = x.getGrid();
		int[][] arr2D2 = new int[arr2D[0].length][arr2D.length];
		for(int i=0;i<arr2D.length;i++) {
			for(int j=0; j<arr2D[0].length;j++) {
			    arr2D2[j][i]=arr2D[i][j];
			}
		}
		ContentPanel myPanel = new ContentPanel(arr2D2, new User());
		
		
		frame.getContentPane().add(myPanel);
		frame.setVisible(true);
//		for (int i =0;i<100000; i++) {
//			try {
//				Thread.sleep(100);
//				frame.repaint();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		frame.repaint();
		

	}

}
