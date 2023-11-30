package javatetris;

import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.reflect.Array;

public class Tetromino {
	private GamePanel panel;
	private Shape shape;
	private int x;
	private int y;
	
	
	public Tetromino(GamePanel _gp, Shape _shape, int _x, int _y) {
		panel = _gp;
		shape = _shape;
		x = _x;
		y = _y;
		if(!tryPieceMove(0,0)) {
			//game over
			panel.gameOver();
		}
	}
	
	public int[][] rotate90(int a[][])
    {
		int N = shape.getArray().length;
		int [][] arr = a;
        // Traverse each cycle
        for (int i = 0; i < N / 2; i++) {
            for (int j = i; j < N - i - 1; j++) {
 
                // Swap elements of each cycle
                // in clockwise direction
                int temp = arr[i][j];
                arr[i][j] = arr[N - 1 - j][i];
                arr[N - 1 - j][i] = arr[N - 1 - i][N - 1 - j];
                arr[N - 1 - i][N - 1 - j] = arr[j][N - 1 - i];
                arr[j][N - 1 - i] = temp;
            }
        }
        return arr;
    }
	
	public void update() {
		if(panel.frames % 30 == 0) {
			tryPieceMove(0, 1);
		}
	}
	
	public void placePiece() {
		int arr[][] = shape.getArray();
		for (int _y=0;_y<arr.length;_y++) {
			for(int _x=0;_x<arr[_y].length;_x++) {
				if(arr[_y][_x]==0) continue;
				panel.grid.setGridPos(x+_x, y+_y, 1);
			}
		}
		panel.current_tetromino = null;
		panel.checkLines();
	}
	
	public boolean tryPieceRotate() {
		int[][] prevArray = shape.getArray();
		int[][] newArray = rotate90(shape.getArray());
		shape.setArray(newArray);
		
		int arr[][] = shape.getArray();
		for (int _y=0;_y<arr.length;_y++) {
			for(int _x=0;_x<arr[_y].length;_x++) {
				if((arr[_y][_x]>0) && (x+_x < 0 || x+_x > panel.grid.getWidth()-1 || y+_y > panel.grid.getHeight()-1)) {
					shape.setArray(prevArray);
					if(!tryPieceMove(-1,0)) {
						if(!tryPieceMove(1,0)) {
							if(!tryPieceMove(-2,0)) {
								tryPieceMove(2,0);
							}
						}
					}
					return false;
				}
				
				if((arr[_y][_x]>0) && (panel.grid.getGridPos(x+_x, y+_y)>0)) {
					if(!tryPieceMove(-1,0)) {
						if(!tryPieceMove(1,0)) {
							if(!tryPieceMove(-2,0)) {
								tryPieceMove(2,0);
							}
						}
					}
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean tryPieceMove(int _xdir, int _ydir) {
		x += _xdir;
		y += _ydir;
		int arr[][] = shape.getArray();
		for (int _y=0;_y<arr.length;_y++) {
			for(int _x=0;_x<arr[_y].length;_x++) {
				if((arr[_y][_x]>0) && (x+_x < 0 || x+_x > panel.grid.getWidth()-1 || y+_y > panel.grid.getHeight()-1)) {
					x -= _xdir;
					y -= _ydir;
					if(_ydir>0) {
						//place piece on grid
						placePiece();
					}
					return false;
				}
				
				if((arr[_y][_x]>0) && (panel.grid.getGridPos((x+_x), (y+_y))>-1)) {
					if(panel.grid.getGridPos(x+_x, y+_y)>0) {
						x -= _xdir;
						y -= _ydir;
						if(_ydir>0) {
							//place piece on grid
							placePiece();
						}
						return false;
					}
				}
				
			}
		}
		
		return true;
	}
	
	public void draw(Graphics2D g2) {
		int arr[][] = shape.getArray();
		g2.setColor(new Color(255, 0, 0));
		for(int _y=0;_y<arr.length;_y++) {
			for(int _x=0;_x<arr[_y].length;_x++) {
				int block = arr[_y][_x];
				if(block==0) continue;
				int x1 = (x+_x)*panel.CELL_SIZE;
				int y1 = (y+_y)*panel.CELL_SIZE;
				g2.fillRect(x1, y1, panel.CELL_SIZE, panel.CELL_SIZE);
			}
		}
	}
	
	public Shape getShape() {
		return shape;
	}
}
