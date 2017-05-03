package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class represents Circle, which is defined by the coordinates of the center,
 * and radius. Circle with radius smaller than 1 are not allowed
 * 
 * @author Marko
 *
 */
public class Circle extends GeometricShape {

	/** Vale of the radius */
	private int radius;

	/**
	 * Constructor for Circle.Accepts 3 arguments. x and y are the coordinates
	 * of the center of circle. X and y can not be less than 0. Radius can not
	 * be less than 1
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param radius
	 *            radius of circle
	 */

	public Circle(int x, int y, int radius) {
		if (radius < 1) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	/**
	 * Method containsPoint checks if given point belongs to specified geometric
	 * shape.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return return false only if the location is outside of the geometric
	 *         shape. Returns true otherwise
	 */
	@Override
	public boolean containsPoint(int x, int y) {
		if (radius == 1){
			if(x == this.x && y == this.y){
				return true;
			}else{
				return false;
			}
		}
		if ((double) (Math.pow(x - this.x, 2)) / ((radius-1) * (radius-1))
				+ (double) (Math.pow(y - this.y, 2)) / ((radius -1)* (radius-1)) <= 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Draws geometric shape on the raster
	 * 
	 * @param r Raster
	 */
	@Override
	public void draw(BWRaster r) {
		if( radius == 1){
			r.turnOn(x, y);
			return;
		}else if(radius == 2){
			r.turnOn(x, y);
			r.turnOn(x+1, y);
			r.turnOn(x, y+1);
			r.turnOn(x-1, y);
			r.turnOn(x, y-1);
			return;
		}
		for (int j = 0; j < r.getHeight(); j++) {
			for (int i = 0; i < r.getWidth(); i++) {
				if (containsPoint(i, j)) {
					r.turnOn(i, j);
				}
			}
		}
	}
	/**
	 * Getter for radius
	 * 
	 * @return radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Setter for radius
	 * 
	 * @param radius of ellipse
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}
}
