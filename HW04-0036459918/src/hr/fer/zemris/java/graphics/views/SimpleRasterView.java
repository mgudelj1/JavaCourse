package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;
/**
 * Implementation for RasterView interface.
 * This class defines two constructors.
 * First accepts two char parameters for displaying
 * turned on and turned off pixel.
 * Second is default, it sets '*' as turned on and
 * '.' as turned off.
 * @author Marko
 *
 */
public class SimpleRasterView implements RasterView {
/** Character for displaying turned on pixel */
	private char turnedOn;
	/** Character for displaying turned off pixel */
	private char turnedOff;
	
	/**
	 * First Constructor
	 * @param turnedOn char
	 * @param turnedOff char
	 */
	public SimpleRasterView(char turnedOn, char turnedOff) {
		this.turnedOn = turnedOn;
		this.turnedOff = turnedOff;
	}
	/**
	 * Default Constructor. Sets both turned
	 * on and turned off characters to default
	 */
	public SimpleRasterView() {
		this('*','.');
	}
	
	/**
	 * Produce the textual representation of 
	 * image to standard output and returns null as result.
	 * @param raster raster
	 * @return null
	 */
	@Override
	public Object produce(BWRaster raster) {
		for (int j = 0; j < raster.getHeight(); j++) {
			for (int i = 0; i < raster.getWidth(); i++) {
				if (raster.isTurnedOn(i, j)){
					System.out.print(turnedOn);
				}else{
					System.out.print(turnedOff);
				}
			}
			System.out.println();
		}
		return null;
	}

}
