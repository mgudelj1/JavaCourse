package hr.fer.zemris.java.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import hr.fer.zemris.java.graphics.raster.*;
import hr.fer.zemris.java.graphics.shapes.*;
import hr.fer.zemris.java.graphics.views.*;

/**
 * Testing class for Raster drawing
 * @author Marko
 *
 */
public class Demo {
/**
 * Entry point to the program
 * @param args width and height
 * @throws IOException throws if entered invalid sequence
 */
	public static void main(String[] args) throws IOException {
		BWRaster raster;
		if (args.length < 1 || args.length > 2) {
			throw new IllegalArgumentException("Mora biti zadan jedan ili dva argumenta");
		}
		try {
			int width = Integer.parseInt(args[0]);
			if (args.length == 1) {
				if (width < 1 || width > 1920) {
					throw new Exception();
				}
				raster = new BWRasterMem(width,width);
			} else{
				int height = Integer.parseInt(args[1]);
				if (width < 1 || width > 1920 || height < 1 || height > 1920) {
					throw new Exception();
				}
				
				raster = new BWRasterMem(width, height);
			}
		
		
		
		}catch(Exception e){
			throw new IllegalArgumentException("Zadani argumenti moraju biti brojevi od [1,1920]");
		}
		int br = 0;
		GeometricShape[] likovi;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    try {
			String strLine = in.readLine();
			br = Integer.parseInt(strLine);
			likovi = new GeometricShape[br];
		} catch (IOException e) {
			throw new IllegalArgumentException("Morate zadati broj");
		}
	    
			

		for(int i= 0; i < br; i++){
			
		    try {
				String strLine = in.readLine();
				String[] novi = strLine.split("\\s+");

				switch (novi[0]) {
				case "RECTANGLE":
					likovi[i] = new Rectangle(Integer.parseInt(novi[1]), Integer.parseInt(novi[2]),
							Integer.parseInt(novi[3]), Integer.parseInt(novi[4]));
					break;
				case "SQUARE":
					likovi[i] = new Square(Integer.parseInt(novi[1]), Integer.parseInt(novi[2]),
							Integer.parseInt(novi[3]));
					break;
				case "ELLIPSE":
					likovi[i] = new Ellipse(Integer.parseInt(novi[1]), Integer.parseInt(novi[2]),
							Integer.parseInt(novi[3]), Integer.parseInt(novi[4]));
					break;
				case "CIRCLE":
					likovi[i] = new Circle(Integer.parseInt(novi[1]), Integer.parseInt(novi[2]),
							Integer.parseInt(novi[3]));
					break;
				case "FLIP":
					likovi[i] = null;
					break;
				}
		    
		    } catch (Exception e) {
				throw new IllegalArgumentException("Ne možete to utipkati");
			}
		}

		in.close();

		boolean flip = false;
		for (int i = 0; i < br; i++) {
			if (likovi[i] != null) {
				likovi[i].draw(raster);
				continue;
			}
			if (flip == false) {
				flip = true;
				raster.enableFlipMode();
			}
			else {
				flip = false;
				raster.disableFlipMode();
			}
		}

		RasterView view = new SimpleRasterView();
		view.produce(raster);

		
		
		
	}
}

