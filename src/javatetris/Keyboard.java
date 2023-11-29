package javatetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	GamePanel panel;
	
	public Keyboard(GamePanel _gp) {
		panel = _gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		if(panel.current_tetromino!=null) {
			switch(e.getKeyCode()){
				case 37:
					//left
					panel.current_tetromino.tryPieceMove(-1, 0);
					break;
				case 39:
					//right
					panel.current_tetromino.tryPieceMove(1, 0);
					break;
				case 40:
					//down
					panel.current_tetromino.tryPieceMove(0, 1);
					break;
				case 90:
					//try to rotate
					panel.current_tetromino.tryPieceRotate();
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
