package hr.fer.zemris.java.raytracer;

import java.util.List;

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
 * Class that represents simplification of a ray-tracer for rendering of 3D scenes.
 * It is actually just a ray caster
 * @author Marko Gudelj
 *
 */
public class RayCaster {
/**
 * Entry point to program
 * @param args no arguments
 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}
/**
 * Producer class that implements IRayTracerProducer
 * Determining color for each pixel
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
//				Point3D yAxis = j;
//				Point3D xAxis = i;
				Point3D screenCorner = view.sub(i.scalarMultiply(horizontal / 2)).add(j.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D xCoordinate = i.scalarMultiply(horizontal).scalarMultiply((double) x / (width - 1));
						Point3D yCoordinate = j.scalarMultiply(vertical).scalarMultiply((double) y / (height - 1));
						Point3D screenPoint = screenCorner.add(xCoordinate).sub(yCoordinate);
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			private void tracer(Scene scene, Ray ray, short[] rgb) {

				RayIntersection S = determineIntersection(scene, ray);
				if (S == null) {
					rgb[0] = rgb[1] = rgb[2] = 0;
					return;
				}
				determineColorFor(S, scene, ray, rgb);

			}

		};
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
