package javatetris;

import java.awt.Color;
import java.awt.Graphics2D;

public class Grid {
	private GamePanel panel;
	private int width;
	private int height;
	private int array[][];
	
	public Grid(GamePanel _gp, int _w, int _h) {
		panel = _gp;
		width = _w;
		height = _h;
		array = new int[height][width];
		initGrid();
	};
	
	public void draw(Graphics2D g2) {
		//blocks
		g2.setColor(new Color(255, 0, 0));
		for(int x=0;x<getWidth();x++) {
			for(int y=0; y<getHeight(); y++) {
				if(array[y][x]>0) {
					g2.fillRect(x*panel.CELL_SIZE, y*panel.CELL_SIZE, panel.CELL_SIZE, panel.CELL_SIZE);
				}
			}
		}
		
		//grid lines
		g2.setColor(new Color(0, 0, 0));
		for(int x=0;x<getWidth();x++) {
			g2.drawLine(x*panel.CELL_SIZE, 0, x*panel.CELL_SIZE, getHeight()*panel.CELL_SIZE);
			for(int y=0; y<getHeight(); y++) {
				g2.drawLine(0, y*panel.CELL_SIZE, getWidth()*panel.CELL_SIZE, y*panel.CELL_SIZE);
			}
		}
	}
	
	public void setGridPos(int _x, int _y, int _val) {
		if(_x < 0 || _x > width-1 || _y < 0 || _y > height-1)
			return;
		array[_y][_x] = _val;
	}
	
	public int getGridPos(int _x, int _y) {
		if(_x < 0 || _x > width-1 || _y < 0 || _y > height-1)
			return -1;
		return array[_y][_x];
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void initGrid() {
		for(int y=0;y<array.length;y++) {
			for(int x=0; x<array[y].length;x++) {
				array[y][x] = 0;
			}
		}
	};
	
}
