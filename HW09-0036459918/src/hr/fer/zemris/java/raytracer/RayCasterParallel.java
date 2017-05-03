package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Class that represents simplification of a ray-tracer for rendering of 3D
 * scenes. It is actually just a ray caster
 * 
 * @author Marko Gudelj
 *
 */
public class RayCasterParallel {
	/**
	 * Entry point to program
	 * 
	 * @param args takes no arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Producer class that implements IRayTracerProducer determining color for
	 * each pixel
	 * 
	 * @return producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D OG = view.sub(eye).normalize();
				Point3D VUV = viewUp.normalize();
				Point3D j = VUV.sub(OG.scalarMultiply(OG.scalarProduct(VUV)));
				j.modifyNormalize();
				Point3D i = OG.vectorProduct(j);
				i.modifyNormalize();

				Point3D yAxis = j;
				Point3D xAxis = i;
				Point3D screenCorner = view.sub(i.scalarMultiply(horizontal / 2)).add(j.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Posao(height, width, horizontal, vertical, xAxis, yAxis, screenCorner, eye, red, green,
						blue, scene, 0, width - 1));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}

	/**
	 * Class that is used for parallelization of jobs using fork join framework
	 * 
	 * @author Marko Gudelj
	 *
	 */
	public static class Posao extends RecursiveAction {

		/** Default serial version uid */
	private static final long serialVersionUID = 1L;
	/** Treshold that determines maximal iterations */
	private static final int TRESHOLD = 256;
		/** Height of column */
		private int height;
		/** Width of row */
		private int width;
		/** Horizontal width of picture */
		private double horizontal;
		/** Vertical height of picture */
		private double vertical;
		/** Vector representation of xAxis */
		private Point3D xAxis;
		/** Vector representation of yAxis */
		private Point3D yAxis;
		/** Referent point of screen corner */
		private Point3D screenCorner;
		/** Observer */
		private Point3D eye;
		/** Field for red color */
		private short[] red;
		/** Field for green color */
		private short[] green;
		/** Field for blue color */
		private short[] blue;
		/** Scene */
		private Scene scene;
		/** Determines lower border for calculation for y */
		private int yMin;
		/** Determines upper border for calculation for y */
		private int yMax;

		/**
		 * Constructor
		 * 
		 * @param height of column
		 * @param width of row
		 * @param horizontal width of picture
		 * @param vertical height of picture
		 * @param xAxis x
		 * @param yAxis y
		 * @param screenCorner referent point of screen
		 * @param eye observer
		 * @param red field
		 * @param green field
		 * @param blue field
		 * @param scene scene
		 * @param yMin index of first row
		 * @param yMax index of last row
		 */
		public Posao(int height, int width, double horizontal, double vertical, Point3D xAxis, Point3D yAxis,
				Point3D screenCorner, Point3D eye, short[] red, short[] green, short[] blue, Scene scene, int yMin,
				int yMax) {
			super();
			this.height = height;
			this.width = width;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.eye = eye;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.scene = scene;
			this.yMin = yMin;
			this.yMax = yMax;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= TRESHOLD) {
				short[] rgb = new short[3];
				for (int y = yMin; y <= yMax; y++) {
					for (int x = 0; x < width; x++) {
						int offset = y * width + x;
						Point3D left = xAxis.scalarMultiply(horizontal * (double) x / (double) (width - 1));
						Point3D right = yAxis.scalarMultiply(vertical * (double) y / (double) (height - 1));
						
						Point3D screenPoint = screenCorner.add(left)
								.sub(right);

						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					}
				} 
				return;
			}
			invokeAll(
					new Posao(height, width, horizontal, vertical, xAxis, yAxis, screenCorner, eye, red, green, blue,
							scene, yMin, yMin + (yMax - yMin) / 2),
					new Posao(height, width, horizontal, vertical, xAxis, yAxis, screenCorner, eye, red, green, blue,
							scene, yMin + (yMax - yMin) / 2 + 1, yMax));

		}
		/**
		 * Method used for tracing certain ray to object
		 * @param scene scene
		 * @param ray that is traced
		 * @param rgb color
		 */
		private static void tracer(Scene scene, Ray ray, short[] rgb) {

			RayIntersection S = determineIntersection(scene, ray);
			if (S == null) {
				rgb[0] = rgb[1] = rgb[2] = 0;
				return;
			}
			determineColorFor(S, scene, ray, rgb);

		}

		/**
		 * Method for determining closest intersection
		 * 
		 * @param scene
		 *            scene
		 * @param ray
		 *            ray
		 * @return RayIntersection
		 */
		private static RayIntersection determineIntersection(Scene scene, Ray ray) {
			RayIntersection returnI = null;
			RayIntersection helperI;
			List<GraphicalObject> objekti = scene.getObjects();
			for (GraphicalObject objekt : objekti) {
				helperI = objekt.findClosestRayIntersection(ray);
				if (helperI != null) {
					if (returnI == null) {
						returnI = helperI;
					} else {
						if (helperI.getDistance() < returnI.getDistance()) {
							returnI = helperI;
						}
					}
				}
			}
			return returnI;
		}

		/**
		 * Helper method for determining color for pixel
		 * 
		 * @param S
		 *            RayIntersection
		 * @param scene
		 *            scene
		 * @param ray
		 *            ray
		 * @param rgb
		 *            field of colors
		 */
		private static void determineColorFor(RayIntersection S, Scene scene, Ray ray, short[] rgb) {
			rgb[0] = rgb[1] = rgb[2] = 15;

			for (LightSource source : scene.getLights()) {
				Ray r = Ray.fromPoints(source.getPoint(), S.getPoint());
				RayIntersection rI = determineIntersection(scene, r);
				if (rI != null) {
					double sDist = S.getPoint().sub(source.getPoint()).norm();
					double rIDist = rI.getPoint().sub(source.getPoint()).norm();
					if (Math.abs(sDist - rIDist) < 0.0001) {
						Point3D nor = rI.getNormal().normalize();
						Point3D lor = source.getPoint().sub(rI.getPoint()).normalize();
						double ln;
						if (!((ln = nor.scalarProduct(lor.normalize())) > 0)) {
							ln = 0;
						}

						rgb[0] += source.getR() * rI.getKdr() * ln;
						rgb[1] += source.getG() * rI.getKdg() * ln;
						rgb[2] += source.getB() * rI.getKdb() * ln;
						
						Point3D ror = nor.scalarMultiply(lor.scalarProduct(nor));
						Point3D nrt = ror.add(ror.negate().add(lor).scalarMultiply(-1));
						Point3D v = ray.start.sub(rI.getPoint()).normalize();
						double ir = nrt.normalize().scalarProduct(v);
						if (ir >= 0) {
							ir = Math.pow(ir, rI.getKrn());

							rgb[0] += source.getR() * rI.getKrr() * ir;
							rgb[1] += source.getG() * rI.getKrg() * ir;
							rgb[2] += source.getB() * rI.getKrb() * ir;
						}
					}
				}
			}

		}
	}
}
