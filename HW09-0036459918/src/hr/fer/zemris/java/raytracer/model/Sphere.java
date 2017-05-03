package hr.fer.zemris.java.raytracer.model;
/**
 * Representation of Sphere graphical object
 * @author Marko Gudelj
 *
 */
public class Sphere extends GraphicalObject {
	/** Center of sphere */
	Point3D center;
	/** Radius of sphere */
	double radius;
	/** Coefficient for diffuse component for red color */
	double kdr;
	/** Coefficient for diffuse component for green color */
	double kdg;
	/** Coefficient for diffuse component for blue color */
	double kdb;
	/** Coefficient for reflective component for red color */
	double krr;
	/** Coefficient for reflective component for green color */
	double krg;
	/** Coefficient for reflective component for blue color */
	double krb;
	/** Coefficient for reflective component */
	double krn;
	/**
	 * Constructor for Sphere
	 * @param center center of sphere
	 * @param radius radius of sphere
	 * @param kdr kdr
	 * @param kdg kdg
	 * @param kdb kdb
	 * @param krr krr
	 * @param krg krg
	 * @param krb krb
	 * @param krn krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

/**
 * Method used for finding closest ray intersection
 * @param ray ray
 * @return RayIntersection
 * 
 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		 
        Point3D L = ray.start.sub(center); 
        double a = ray.direction.scalarProduct(ray.direction); 
        double b = ray.direction.scalarProduct(L) * 2;
        double c = L.scalarProduct(L)- Math.pow(radius, 2); 
        double[] t;
    	
        if ((t=riješiKvadratnu(a, b, c)) == null) return null; 
        
        if (t[0] < 0) { 
            t[0] = t[1]; 
            if (t[0] < 0) {return null; }
       } 
        if (t[0] > t[1]){
        	t[0] = t[1];
        }
        
       Point3D intersection = ray.start.add(ray.direction.scalarMultiply(t[0]));
   		double distance = intersection.sub(ray.start).norm();
   		boolean outer = intersection.sub(center).norm() > radius;
   		
   		RayIntersection rI = new RayIntersection(intersection,distance,outer) {
			
			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}
			
			@Override
			public double getKrr() {
				return krr;
			}
			
			@Override
			public double getKrn() {
				return krn;
			}
			
			@Override
			public double getKrg() {
				return krg;
			}
			
			@Override
			public double getKrb() {
				return krb;
			}
			
			@Override
			public double getKdr() {
				return kdr;
			}
			
			@Override
			public double getKdg() {
				return kdg;
			}
			
			@Override
			public double getKdb() {
				return kdb;
			}
		};
   		return rI;
			
	}
	/**
	 * Helper method for solving quadratic equation
	 * @param a component
	 * @param b component
	 * @param c component
	 * @return results t0 and t1
	 */
	private double[] riješiKvadratnu(Double a,Double b,Double c){
		 double disc = b * b - 4 * a * c; 
		 double q;
		 double[] rjesenja = new double[2];
		 double t0;
		 double t1;
		    if (disc < 0) return null; 
		    else if (disc == 0){
		    	t0 = - 0.5 * b / a; 
		    	t1 = t0;
		    }
		    else { 
		        q = (b > 0) ? -0.5 * (b + Math.sqrt(disc)) : -0.5 * (b - Math.sqrt(disc)); 
		        t0 = q / a; 
		        t1 = c / q; 
		    } 
		    if (t0 > t1) {
		    	q = t0;
		    	t0 = t1;
		    	t1 = q;
		    }
		    rjesenja[0] = t0;
		    rjesenja[1] = t1;
		    return rjesenja;
	}

/**
 * Getter for Center
 * @return center
 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Getter for radius
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Getter for kdr
	 * @return kdr
	 */
	public double getKdr() {
		return kdr;
	}

	/**
	 * Getter for kdg
	 * @return kdg
	 */
	public double getKdg() {
		return kdg;
	}

	/**
	 * Getter for kdb
	 * @return kdb
	 */
	public double getKdb() {
		return kdb;
	}

	/**
	 * Getter for krr
	 * @return krr
	 */
	public double getKrr() {
		return krr;
	}

	/**
	 * Getter for krg
	 * @return krg
	 */
	public double getKrg() {
		return krg;
	}

	/**
	 * Getter for krn
	 * @return krn
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * Getter for krn
	 * @return krn
	 */
	public double getKrn() {
		return krn;
	}
	
	
	
	

}
