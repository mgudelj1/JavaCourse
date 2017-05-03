package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;
/**
 * Classes which implement this interface will be 
 * responsible for visualization of created images.
 * @author Marko
 *
 */
public interface RasterView {
	
	/**
	 * Should produce the textual representation of 
	 * image to standard output and returns null as result.
	 * @param raster raster
	 * @return null
	 */
	public Object produce(BWRaster raster);

}
