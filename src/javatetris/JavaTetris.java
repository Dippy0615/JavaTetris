package javatetris;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class JavaTetris {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Java Tetris");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		GamePanel panel = new GamePanel(frame);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		Keyboard keys = new Keyboard(panel);
		frame.addKeyListener(keys);
		
		panel.startGameThread();
	}

}
