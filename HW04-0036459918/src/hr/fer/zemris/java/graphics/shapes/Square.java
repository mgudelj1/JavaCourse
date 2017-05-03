package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class represents Square, which is defined
 * by the coordinates of the top left corner, and
 * size
 * @author Marko
 *
 */
public class Square extends GeometricShape {

	/** Square size */
	private int size;

	/**
	 * Constructor for Square.Accepts 3 arguments. x and y are the coordinates
	 * of the top left corner. X and y can not be less than 0. Size can not be
	 * less than 1
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param size
	 *            of square
	 */
	public Square(int x, int y, int size) {
		if (size < 1) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
		this.size = size;
	}
	/**
	 * Method containsPoint checks if given point belongs to specified geometric shape. 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return return false only if the location is outside of the geometric shape.
	 * Returns true otherwise
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		if ( x > this.x + size || x < this.x) return false;
		if ( y > this.x + size || y < this.y) return false;
		return true;
	}
	/**
	 * Draws square of size "size" on the raster
	 * 
	 * @param r Raster
	 */
	@Override
	public void draw(BWRaster r) {
		for (int j = y; j < this.size+y; j++) {
			for (int i = x; i < this.size+x; i++) {
				r.turnOn(i, j);
			}
		}
	}
	/**
	 * Getter for size
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Setter for size
	 * 
	 * @param size of square
	 */
	public void setSize(int size) {
		this.size = size;
	}

}