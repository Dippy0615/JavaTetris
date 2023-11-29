package javatetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	private static JFrame frame;
	private static final int WIDTH = 320;
	private static final int HEIGHT = 640;
	private static final int FPS = 60;
	private static final double frame_length = 1000/FPS;
	public static final int CELL_SIZE = 32;
	
	
	private Thread thread;
	public long frames;
	
	public Grid grid = new Grid(this, WIDTH/CELL_SIZE, HEIGHT/CELL_SIZE);
	
	int L[][] = {
		{1, 0, 0},
		{1, 0, 0},
		{1, 1, 0},
	};
	
	int J[][] = {
		{0, 0, 1},
		{0, 0, 1},
		{0, 1, 1},
	};
	
	int S[][] = {
		{0, 1, 1},
		{1, 1, 0},
		{0, 0, 0},
	};
	
	int Z[][] = {
		{1, 1, 0},
		{0, 1, 1},
		{0, 0, 0},
	};
	
	int T[][] = {
		{0, 1, 0},
		{1, 1, 1},
		{0, 0, 0},
	};
	
	int O[][] = {
		{1, 1},
		{1, 1},
	};
	
	int I[][] = {
		{0, 1, 0, 0},
		{0, 1, 0, 0},
		{0, 1, 0, 0},
		{0, 1, 0, 0},
	};
	
	private Shape shape_L = new Shape(this, L);
	private Shape shape_J = new Shape(this, J);
	private Shape shape_S = new Shape(this, S);
	private Shape shape_Z = new Shape(this, Z);
	private Shape shape_T = new Shape(this, T);
	private Shape shape_O = new Shape(this, O);
	private Shape shape_I = new Shape(this, I);
	
	private ArrayList<Shape> SHAPES = new ArrayList<Shape>();
	
	public Tetromino current_tetromino = null;
	
	private Random rand = new Random();
	
	public GamePanel(JFrame _frame) {
		frame = _frame;
		Thread thread = null;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(new Color(0,0,0));
		setDoubleBuffered(true);
		
		SHAPES.add(shape_L);
		SHAPES.add(shape_J);
		SHAPES.add(shape_S);
		SHAPES.add(shape_Z);
		SHAPES.add(shape_T);
		SHAPES.add(shape_O);
		SHAPES.add(shape_I);
		
		frames = 0;
	}
	
	public void startGameThread() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void update() {
		if(current_tetromino!=null) {
			current_tetromino.update();
		}
		else {
			//choose new tetromino
			current_tetromino = new Tetromino(this, SHAPES.get(rand.nextInt(SHAPES.size())), 2, 0);
		}
	}
	
	// Function for print matrix
    static void printMatrix(int arr[][])
    {
    	int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(arr[i][j] + " ");
            System.out.println();
        }
        System.out.println("--------------");
    }
	
    public void gameOver() {
    	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
    
    public void checkLines() {
    	for(int y=0;y<grid.getHeight();y++) {
    		for(int x=0;x<grid.getWidth();x++) {
    			if (grid.getGridPos(x,y)==0) break;
    			
    			if(x==grid.getWidth()-1) {
    				//clear this line
    				for(int dx=0;dx<grid.getWidth();dx++) {
    					grid.setGridPos(dx, y, 0);
    				}
    				//apply gravity to all blocks above
    				for(int gy=y-1;gy>0;gy--) {
    					for(int gx=0;gx<grid.getWidth();gx++) {
    						grid.setGridPos(gx, gy+1, grid.getGridPos(gx, gy));
    					}
    				}
    			}
    		}
    	}
    }
    
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.clearRect(0, 0, WIDTH, HEIGHT);
		
		//draw tetromino
		if(current_tetromino!=null) {
			current_tetromino.draw(g2);
		}
		
		//draw grid
		grid.draw(g2);
		
		g2.dispose();
		
	}
	
	@Override
	public void run() {
		long len = 0;
		long timer_current_time;
		long timer_last_time = System.currentTimeMillis();
		long start_time;
		long end_time;
		
		
		while(thread!=null)
		{
			frames++;
			start_time = System.currentTimeMillis();
			
			update();
			repaint();
			
			timer_current_time = System.currentTimeMillis();
			end_time = System.currentTimeMillis();
			len += (timer_current_time - timer_last_time);
			
			timer_last_time = timer_current_time;
			
			if(len>=1000) {
				System.out.println("FPS: "+frames);
				len = 0;
				frames = 0;
			}
			try {
				Thread.sleep((long) (frame_length - (end_time-start_time)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

}
