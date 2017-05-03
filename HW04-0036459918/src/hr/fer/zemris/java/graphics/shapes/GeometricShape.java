package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.*;

/**
 * Representation for the model of the actual geometric shape. Any Geometric
 * shape should have these methods and values
 * 
 * @author Marko
 *
 */
public abstract class GeometricShape {
	/** Coordinate x of the geometric shape */
	protected int x;
	/** Coordinate y of the geometric shape */
	protected int y;

	/**
	 * Draws geometric shape on the raster
	 * 
	 * @param r
	 *            Raster
	 */
	public void draw(BWRaster r) {
		for (int j = 0; j < r.getHeight(); j++) {
			for (int i = 0; i < r.getWidth(); i++) {
				if (containsPoint(i, j)) {
					r.turnOn(i, j);
				}
			}
		}
	}

	/**
	 * Method containsPoint checks if given point belongs to specified geometric
	 * shape. Here implemented to return false;
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return return false only if the location is outside of the geometric
	 *         shape. Returns true otherwise
	 */
	public abstract boolean containsPoint(int x, int y);

	/**
	 * Getter for variable x
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for variable y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for variable x
	 * 
	 * @param x
	 *            x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Setter for variable y
	 * 
	 * @param y
	 *            y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
}
