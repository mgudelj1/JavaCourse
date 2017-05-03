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
public class StringRasterView implements RasterView{
	
	/** Character for displaying turned on pixel */
	private char turnedOn;
	/** Character for displaying turned off pixel */
	private char turnedOff;
	
	/**
	 * First Constructor
	 * @param turnedOn char
	 * @param turnedOff char
	 */
	public StringRasterView(char turnedOn, char turnedOff) {
		this.turnedOn = turnedOn;
		this.turnedOff = turnedOff;
	}
	/**
	 * Default Constructor. Sets both turned
	 * on and turned off characters to default
	 */
	public StringRasterView() {
		this('*','.');
	}
	
	/**
	 * Produces string as representation of raster
	 * and returns it
	 * @param raster raster
	 * @return string as representation of raster
	 */
	@Override
	public Object produce(BWRaster raster) {
		StringBuilder prikaz = new StringBuilder();
		for (int j = 0; j < raster.getHeight(); j++) {
			for (int i = 0; i < raster.getWidth(); i++) {
				if (raster.isTurnedOn(i, j)){
					prikaz.append(turnedOn);
				}else{
					prikaz.append(turnedOff);
				}
			}
			prikaz.append("\n");
		}
		return prikaz.toString();
	}

}
