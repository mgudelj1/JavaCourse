package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class represents Rectangle, which is defined
 * by the coordinates of the top left corner, width and
 * height. 
 * @author Marko
 *
 */
public class Rectangle extends GeometricShape {
	/** Rectangle width */
	private int width;
	/** Rectangle height */
	private int height;

	/**
	 * Constructor for Rectangle.Accepts 4 arguments. x and y are the
	 * coordinates of the top left corner. X and y can not be less than 0.
	 * Width and Height can not be less than 1
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param width
	 *            of rectangle
	 * @param height
	 *            of rectangle
	 */
	public Rectangle(int x, int y, int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
		if ( x >= (this.x + width) || x < this.x) return false;
		if ( y >= (this.x + height) || y < this.y) return false;
		return true;
	}
	/**
	 * Draws geometric shape on the raster
	 * 
	 * @param r Raster
	 */
	@Override
	public void draw(BWRaster r) {
		for (int j = y; j < this.height+y; j++) {
			for (int i = x; i < this.width+x; i++) {
				r.turnOn(i, j);
			}
		}
	}
	/**
	 * Getter for width
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Setter for width
	 * 
	 * @param width of rectangle
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Getter for height
	 * 
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setter for height
	 * 
	 * @param height of rectangle
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}
