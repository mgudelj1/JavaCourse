package hr.fer.zemris.java.graphics.raster;

/**
 * Public interface used to model Black and White raster devices. BWRaster
 * interface represents abstraction for all raster devices of fixed width and
 * height for which each pixel can be painted with only two colors: black (when
 * pixel is turned off) and white (when pixel is turned on).
 * 
 * @author Marko Gudelj
 *
 */
public interface BWRaster {
	
	/**
	 * Getter method for width
	 * 
	 * @return should be integer width
	 */
	public int getWidth();

	/**
	 * Getter method for height
	 * 
	 * @return should be integer height
	 */
	public int getHeight();

	/**
	 * This method should turn of all pixels in raster
	 */
	public void clear();

	/**
	 * Turns on pixel on coordinates (x,y)
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public void turnOn(int x, int y);

	/**
	 * Turns off pixel on coordinates (x,y)
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public void turnOff(int x, int y);

	/**
	 * Enables flip mode, in flip mode when turnOn is called , it will flip
	 * pixel at given coordinate
	 */
	public void enableFlipMode();

	/**
	 * Disables flip mode, in flip mode when turnOn is called , it will flip
	 * pixel at given coordinate
	 */
	public void disableFlipMode();

	/**
	 * Checks if the pixel at given coordinate is turned on
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return true if it is turned on, else false
	 */
	public boolean isTurnedOn(int x, int y);

}
