package hr.fer.zemris.java.graphics.raster;

/**
 * The class BWRasterMem is an implementation of the interface BWRaster which
 * keeps all of its data in computer memory. On creation of new objects of this
 * class all pixels will be initially turned off. This class must provides a
 * single public constructor which accepts raster width and raster height. Both
 * must be at least 1.
 * 
 * @author Marko
 * 
 *
 */
public class BWRasterMem implements BWRaster {
	/** Value of raster width */
	int width;
	/** Value of raster height */
	int height;
	/**
	 * Flip option, if true , turnOn() will
	 * flip pixel
	 */
	private boolean flipped = false;
	/**
	 * 2D character array that represents Raster
	 */
	private boolean[][] Raster;

	/**
	 * Constructor that creates Raster, throw {@link IllegalArgumentException}
	 * if given width or height less than 1
	 * 
	 * @param width
	 *            width of raster
	 * @param height
	 *            height of raster
	 * 
	 */
	public BWRasterMem(int width, int height) {
		if(height < 1 || width < 1){
			throw new IllegalArgumentException();
		}
		this.width = width;
		this.height = height;
		Raster = new boolean[height][width];
//		for (int j = 0; j < height; j++) {
//			for (int i = 0; i < width; i++) {
//				Raster[j][i] = false;
//			}
//		}
	}
	/**
	 * Getter method for width
	 * 
	 * @return integer width
	 */
	@Override
	public int getWidth() {
		return width;
	}
	/**
	 * Getter method for height
	 * 
	 * @return integer height
	 */
	@Override
	public int getHeight() {
		return height;
	}
	/**
	 * This method turns of all pixels in raster
	 */
	@Override
	public void clear() {
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				turnOff(i, j);
			}
		}

	}

	/**
	 * Turns on pixel on coordinates (x,y) if they
	 * are within raster
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @throws IllegalArgumentException if (x,y) is invalid 
	 * with respect to raster dimensions.
	 */
	@Override
	public void turnOn(int x, int y) {
		ifLegalArgument(x, y);
		if (flipped) {
			if (Raster[y][x] == true) {
				Raster[y][x] = false;
				return;
			}
		}
		Raster[y][x] = true;
	}
	/**
	 * Turns off pixel on coordinates (x,y) if they
	 * are within raster
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @throws IllegalArgumentException if (x,y) is invalid 
	 * with respect to raster dimensions.
	 */
	@Override
	public void turnOff(int x, int y) {
		ifLegalArgument(x, y);
		Raster[y][x] = false;
	}
	/**
	 * Enables flip mode, in flip mode when turnOn is called , it will flip
	 * pixel at given coordinate
	 */
	@Override
	public void enableFlipMode() {
		flipped = true;
	}
	/**
	 * Disables flip mode, in flip mode when turnOn is called , it will flip
	 * pixel at given coordinate
	 */
	@Override
	public void disableFlipMode() {
		flipped = false;
	}
	/**
	 * Checks if the pixel at given coordinate is turned on
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return true if it is turned on, else false
	 * 
	 * @throws IllegalArgumentException if (x,y) is invalid 
	 * with respect to raster dimensions.
	 */
	@Override
	public boolean isTurnedOn(int x, int y) {
		ifLegalArgument(x, y);
		return Raster[y][x];
	}
	/**
	 * Helper method, checks if x and y are valid
	 * coordinates, throws new {@link IllegalArgumentException}
	 * if not.
	 * @param x x coordinate
	 * @param y y coordinate
	 * 
	 * @throws IllegalArgumentException if coordinates not valid
	 */
	private void ifLegalArgument(int x, int y){
		if (x > width || y > height || x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
	}

	
}
