package hr.fer.zemris.java.fractals;

import java.io.BufferedInputStream;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Class that is used to draw fractal images: fractals derived from
 * Newton-Raphson iteration This class will accept multiple complex roots and
 * based on them draw fractal image
 * 
 * @author Marko Gudelj
 *
 */
public class Newton {
	/** Value that represents complex polynom */
	private static ComplexPolynomial polynomial;
	/** Value that represents rooted complex polynom */
	private static ComplexRootedPolynomial rootPolynomial;

	/**
	 * Entry point to the program.
	 * 
	 * @param args
	 *            takes no arguments
	 */
	public static void main(String[] args) {

		String unos;
		Complex[] helper;
		List<Complex> lista = new ArrayList<>();
		Scanner sc;
		System.out.printf("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
				+ "Please enter at least two roots, one root per line. Enter 'done' when done.\n");
		while (true) {
			BufferedInputStream in = new BufferedInputStream(System.in);
			sc = new Scanner(in);
			System.out.printf("> ");
			unos = sc.nextLine();
			if (unos.toLowerCase().equals("done")) {
				break;
			}
			if (!unos.matches("((-?\\d+([.]\\d+)?)?\\s*)(([+-]?\\s*[i]\\d+([.]\\d+)?)|i|-i)?$")) {
				sc.close();
				throw new IllegalArgumentException(
						"Given root is not in needed syntax form. General syntax form is a+ib");
			}

			lista.add(parse(unos));

		}
		sc.close();
		helper = new Complex[lista.size()];
		helper = lista.toArray(helper);
		rootPolynomial = new ComplexRootedPolynomial(helper);
		polynomial = rootPolynomial.toComplexPolynom();
		FractalViewer.show(new Producer());

	}

	/**
	 * Class that is implementing IFractalProducer This class uses
	 * parallelization for speeding up the process. Job is split into
	 * 8*available_processors parts
	 */
	public static class Producer implements IFractalProducer {
		/** Pool for threads */
		ExecutorService pool;

		/**
		 * Constructor for producer
		 */
		public Producer() {
			super();
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			short[] data = new short[width * height];

			class PosaoIzracuna implements Callable<Void> {
				int yMin;
				int yMax;

				public PosaoIzracuna(int yMin, int yMax) {
					super();
					this.yMax = yMax;
					this.yMin = yMin;
				}

				@Override
				public Void call() {
					ComplexPolynomial derived = polynomial.derive();
					Complex numerator;
					Complex denominator;
					Complex fraction;
					Complex zn1;
					double checkZn;

					for (int y = yMin; y < yMax + 1; y++) {
						for (int x = 0; x < width; x++) {
							double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
							double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
							Complex zn = new Complex(cre, cim);
							int iters = 0;
							do {
								numerator = polynomial.apply(zn);
								denominator = derived.apply(zn);
								fraction = numerator.divide(denominator);
								zn1 = zn.sub(fraction);
								checkZn = (zn1.sub(zn)).module();
								zn = zn1;
								iters++;
							} while (iters < 256 && checkZn > 0.001);
							int index = rootPolynomial.indexOfClosestRootFor(zn1, 0.002);
							if (index == -1) {
								data[x + y * width] = 0;
							} else {
								data[x + y * width] = Short.valueOf(Integer.toString(index));
							}
						}
					}
					return null;
				}
			}
			System.out.println("Zapocinjem izracun...");
			List<Future<Void>> rezultati = new ArrayList<>();
			final int brojPoslova = 8 * Runtime.getRuntime().availableProcessors();
			int brojYPoPoslu = width / brojPoslova;

			for (int i = 0; i < brojPoslova; i++) {
				int yMin = i * brojYPoPoslu;
				int yMax = (i + 1) * brojYPoPoslu - 1;
				if (i == brojPoslova - 1) {
					yMax = width - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(yMin, yMax);
				rezultati.add(pool.submit(posao));
			}
			for (Future<Void> posao : rezultati) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);

		}
	}
	
	/**
	 * Helper method use to parse given string to complex number
	 * 
	 * @param s
	 *            string to parse
	 * @return complex number
	 */
	public static Complex parse(String s) {
		double pomReal;
		double pomImag;
		try {
			String[] parser = s.split("(?=[-+])");
			if (parser.length == 1) {
				String string1 = parser[0];
				if (string1.contains("i")) {
					if (string1.endsWith("i")) {
						pomImag = 1;
					} else {
						pomImag = Double.parseDouble(string1.substring(string1.indexOf('i') + 1, string1.length()));
					}
					if (string1.contains("-")) {
						pomImag *= -1;
					}
					return new Complex(0, pomImag);

				} else {
					pomReal = Double.parseDouble(string1.substring(0, string1.length()));
					return new Complex(pomReal, 0);
				}
			}

			else if (parser.length == 2) {
				String string1 = parser[0];
				String string2 = parser[1];

				if (string2.endsWith("i")) {
					pomImag = 1;
				} else {
					pomImag = Double.parseDouble(string2.substring(string2.indexOf('i') + 1, string2.length()));
				}
				if (string2.contains("-")) {
					pomImag *= -1;
				}
				pomReal = Double.parseDouble(string1.substring(0, string1.length()));
				return new Complex(pomReal, pomImag);
			}

			else {
				throw new IllegalArgumentException();
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}


	/**
	 * Class that represents polynomial complex
	 *
	 */
	public static class ComplexPolynomial {
		/** Values of Complex polynom , index represents power of z */
		Complex[] faktori;
		/**
		 * Constructor for complex polynomial
		 * @param factors faktori
		 */
		public ComplexPolynomial(Complex... factors) {
			faktori = factors;

		}

		/**
		 * Returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
		 * 
		 * @return order of polynom
		 */
		public short order() {
			short order = 0;
			for(int i=0 ; i<faktori.length;i++){
				if(faktori[i] == null || (faktori[i].real == 0 && faktori[i].imaginary == 0)){
					continue;
				}
				order = Short.parseShort(Integer.toString(i));
			}
			return order;
		}

		/**
		 * Computes a new polynomial this*p
		 * 
		 * @param p
		 *            polynomial
		 * @return new polynomial multiplied with p
		 */
		public ComplexPolynomial multiply(ComplexPolynomial p) {
			Complex[] newFaktori = new Complex[faktori.length + p.faktori.length];
			for (int i = 0; i < faktori.length; i++) {
				if (faktori[i] == null) { continue; }
				for (int j = 0; j < p.faktori.length; j++) {
					if (p.faktori[j] == null ) { continue; }
					if (newFaktori[i + j] == null) {
						newFaktori[i + j] = faktori[i].multiply(p.faktori[j]);
					} else {
						newFaktori[i + j] = newFaktori[i + j].add(faktori[i].multiply(p.faktori[j]));
					}
				}
			}
			return new ComplexPolynomial(newFaktori);
		}

		/**
		 * Computes first derivative of this polynomial; for example, for
		 * 7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
		 * 
		 * @return first derivative of polynomial
		 */
		public ComplexPolynomial derive() {
			Complex[] newFaktori = new Complex[faktori.length];
			for (int i = 1; i < faktori.length; i++) {
				if (faktori[i] == null) { continue; }
				newFaktori[i - 1] = faktori[i].multiply(new Complex(i, 0));
			}
			return new ComplexPolynomial(newFaktori);
		}

		/**
		 * Computes polynomial value at given point z
		 * 
		 * @param z point to calculate polynom around
		 * @return computed polynom value
		 */
		public Complex apply(Complex z) {
			Complex newValue = new Complex(0, 0);
			for (int i = 0; i < faktori.length; i++) {
				if (faktori[i] == null) {
					continue;
				}
				newValue = newValue.add(faktori[i].multiply(z.power(i)));
			}
			return newValue;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			StringBuilder string = new StringBuilder();
			for (int i = faktori.length-1; i > -1; i--) {

				if (faktori[i] == null || (faktori[i].real == 0&& faktori[i].imaginary == 0)) {
					continue;
				}
				if (faktori[i].real != 0 && faktori[i].imaginary != 0) {
					string.append('(');
				}
				string.append(faktori[i].toString());

				if (faktori[i].real != 0 && faktori[i].imaginary != 0) {
					string.append(')');
				}
				if (i != 0) {
					string.append("z^" + i);
				}
			}
			return string.toString();
		}
	}

	/**
	 * Class that represents rooted polynomial
	 * 
	 */
	public static class ComplexRootedPolynomial {
		/** Array of root complex numbers */
		Complex[] rootedPolynomial;

		/**
		 * Constructor for ComplexRootedPolynomial
		 * 
		 * @param roots
		 *            presented as complex number
		 */
		public ComplexRootedPolynomial(Complex... roots) {
			rootedPolynomial = roots;
		}

		/**
		 * Computes polynomial value at given point z
		 * 
		 * @param z
		 *            given point
		 * @return polynomial value at z
		 */
		public Complex apply(Complex z) {
			Complex result = Complex.ONE;
			
			for (int i= 0; i<rootedPolynomial.length;i++) {
				result = result.multiply(z.sub(rootedPolynomial[i]));
			}
			
			return result;
		}

		/**
		 * Converts ComplexRootedPolynomial to ComplexPolynomial representation
		 * 
		 * @return ComplexPolynomial representation
		 */
		public ComplexPolynomial toComplexPolynom() {
			ComplexPolynomial cp = new ComplexPolynomial(rootedPolynomial[0].negate(), Complex.ONE);
			for (int i = 1; i < rootedPolynomial.length; i++) {
				cp = cp.multiply(new ComplexPolynomial(rootedPolynomial[i].negate(), Complex.ONE));
			}
			return cp;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			StringBuilder string = new StringBuilder();
			for (Complex root : rootedPolynomial) {
				string.append("[z-(");
				string.append(root.toString());
				string.append(")]");
			}
			return string.toString();
		}

		/**
		 * Finds index of closest root for given complex number z that is within
		 * treshold
		 * 
		 * @param z
		 *            complex number
		 * @param treshold
		 *            value of treshold
		 * @return index of closest root if there is, else -1
		 */
		public int indexOfClosestRootFor(Complex z, double treshold) {
			int closestRootIndex = -1;
			double smallest = 100;
			double possibleSmallest;

			for (int i = 0; i < rootedPolynomial.length; i++) {
				possibleSmallest = z.sub(rootedPolynomial[i]).module();
				if (possibleSmallest < treshold && possibleSmallest < smallest) {
					smallest = possibleSmallest;
					closestRootIndex = i+1;
				}
			}
			return closestRootIndex;
		}
	}

	/**
	 * Class that represents complex number
	 *
	 */
	public static class Complex {
		/** Real part of Complex number */
		private double real;
		/** Imaginary part of Complex number */
		private double imaginary;

		/** Value of complex number Zero represented as 0 + i0 */
		public static final Complex ZERO = new Complex(0, 0);
		/** Value of complex number Zero represented as 1 + i0 */
		public static final Complex ONE = new Complex(1, 0);
		/** Value of complex number Zero represented as -1 + i0 */
		public static final Complex ONE_NEG = new Complex(-1, 0);
		/** Value of complex number Zero represented as 0 + i1 */
		public static final Complex IM = new Complex(0, 1);
		/** Value of complex number Zero represented as 0 - i1 */
		public static final Complex IM_NEG = new Complex(0, -1);

		/**
		 * Default Constructor
		 */
		public Complex() {
			this(0, 0);
		}

		/**
		 * Constructor for Complex number
		 * 
		 * @param re
		 *            real part
		 * @param im
		 *            imaginary part
		 */
		public Complex(double re, double im) {
			real = re;
			imaginary = im;
		}

		/**
		 * returns module of complex number
		 * 
		 * @return module
		 */
		public double module() {
			return (Math.sqrt((Math.pow(real, 2) + Math.pow(imaginary, 2))));
		}

		/**
		 * Returns this complex number multiplied with c
		 * 
		 * @param c
		 *            multiplier
		 * @return result of operation
		 */
		public Complex multiply(Complex c) {
			double pomReal = (this.real * c.real) - (this.imaginary * c.imaginary);
			double pomImag = (this.imaginary * c.real) + (this.real * c.imaginary);
			return new Complex(pomReal, pomImag);
		}

		/**
		 * Divides this complex number with c
		 * 
		 * @param c
		 *            given dividor complex
		 * @return result from this/c
		 */
		public Complex divide(Complex c) {
			double djelitelj = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
			if (djelitelj != 0) {
				double pomReal = (this.real * c.real) + (this.imaginary * c.imaginary);
				double pomImag = (this.imaginary * c.real) - (this.real * c.imaginary);
				return new Complex(pomReal / djelitelj, pomImag / djelitelj);
			} else {
				throw new IllegalArgumentException("Dijelenje sa nulom nije moguće");
			}
		}

		/**
		 * Adds c to this complex number
		 * 
		 * @param c
		 *            given addition complex
		 * @return result from this+c
		 */
		public Complex add(Complex c) {
			return new Complex(this.real + c.real, this.imaginary + c.imaginary);
		}

		/**
		 * Subs c from this complex number
		 * 
		 * @param c
		 *            given subtraction complex
		 * @return result from this-c
		 */
		public Complex sub(Complex c) {
			return new Complex(this.real - c.real, this.imaginary - c.imaginary);
		}

		/**
		 * Negates complex number
		 * 
		 * @return negated complex number
		 */
		public Complex negate() {
			return new Complex(this.real, -this.imaginary);
		}

		/**
		 * Calculates this^n
		 * 
		 * @param n
		 *            is non-negative integer
		 * @return result of this^n
		 */
		public Complex power(int n) {
			if (n < 0) {
				throw new IllegalArgumentException();
			}
			double pomReal = Math.pow(this.module(), n) * Math.cos(n * (Math.atan2(imaginary, real)));
			double pomImag = Math.pow(this.module(), n) * Math.sin(n * (Math.atan2(imaginary, real)));
			return new Complex(pomReal, pomImag);
		}

		/**
		 * Calculates n roots from this complex number
		 * 
		 * @param n
		 *            root
		 * @return list of roots
		 */
		public List<Complex> root(int n) {
			if (n <= 0) {
				throw new IllegalArgumentException();
			}
			List<Complex> lista = new ArrayList<>();
			double pomReal = 0;
			double pomImag = 0;
			double razlika = 2 * Math.PI / n;

			for (int i = 0; i < n; i++) {

				pomReal = (Math.pow(module(), (double) 1 / n)
						* Math.cos(((Math.atan2(imaginary, real)) / n) + i * razlika));
				pomImag = Math.pow(module(), (double) 1 / n)
						* Math.sin(((Math.atan2(imaginary, real)) / n) + i * razlika);
				lista.add(new Complex(pomReal, pomImag));
			}
			return lista;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			StringBuilder string = new StringBuilder();
			NumberFormat formatter = new DecimalFormat("#0.0");
			if (real != 0) {
				string.append(formatter.format(real));
			}
			if (imaginary > 0) {
				string.append("+").append(formatter.format(imaginary)).append("i");
			} else if (imaginary < 0) {
				string.append(formatter.format(imaginary)).append("i");
			}
			if (real == 0 && imaginary == 0) {
				string.append(0);
			}
			return string.toString();
		}
	}
}