package javatetris;

public class Shape {
	private int array[][];
	
	public Shape(GamePanel _gp, int _array[][]) {
		array = _array;
	}
	
	public void setArray(int[][] arr) {
		array = arr;
	}
	
	public int[][] getArray(){
		return array;
	}
}
