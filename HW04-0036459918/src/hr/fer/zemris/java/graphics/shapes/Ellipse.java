package hr.fer.zemris.java.graphics.shapes;

/**
 * Class represents Ellipse, which is defined by the coordinates of the center,
 * and vertical and horizontal radius. Ellipse with radius smaller than 1 are
 * not allowed
 * 
 * @author Marko
 *
 */
public class Ellipse extends GeometricShape {

	/** Value of horizontal radius */
	private int a;
	/** Value of vertical radius */
	private int b;

	/**
	 * Constructor for Ellipse.Accepts 4 arguments. x and y are the coordinates
	 * of the center of ellipse. X and y can not be less than 0. Radius can not
	 * be less than 1
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param a
	 *            horizontal radius
	 * @param b
	 *            vertical radius
	 */
	public Ellipse(int x, int y, int a, int b) {
		if (a < 1 || b < 1) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
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
		if ((double) (Math.pow(x-this.x, 2)) / (a * a) + (double) (Math.pow(y-this.y, 2)) / (b * b) <= 1) {
			return true;
		}
		return false;
	}
	/**
	 * Getter for horizontal radius
	 * 
	 * @return a
	 */
	public int getA() {
		return a;
	}

	/**
	 * Setter for horizontal radius
	 * 
	 * @param a of ellipse
	 */
	public void setA(int a) {
		this.a = a;
	}

	/**
	 * Getter for vertical radius
	 * 
	 * @return b
	 */
	public int getb() {
		return b;
	}

	/**
	 * Setter for vertical radius
	 * 
	 * @param b of ellipse
	 */
	public void setb(int b) {
		this.b = b;
	}
}