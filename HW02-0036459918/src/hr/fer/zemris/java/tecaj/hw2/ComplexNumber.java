package hr.fer.zemris.java.tecaj.hw2;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Implementacija podrške za rad sa kompleksnim brojevima
 * 
 * @author Marko Gudelj
 * @version 1.0
 */
public class ComplexNumber {
	/** Realni dio kompleksnog broja */
	double real;
	/** Imaginarni dio kompleksnog broja */
	double imaginary;

	/**
	 * Konstruktor, postavlja kompleksni broj na zadane realne i imaginarne
	 * vrijednosti
	 * 
	 * @param real realni dio
	 * @param imaginary imaginarni dio
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Konstruktor, postavlja kompleksni broj sa realnom vrijednošću, bez
	 * imaginarne
	 * 
	 * @param real realni dio
	 * @return referencu na stvoreni kompleksni broj
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Konstruktor, postavlja kompleksni broj sa imaginarnom vrijednošću, bez
	 * realne
	 * 
	 * @param imaginary imaginarni dio
	 * @return referencu na stvoreni kompleksni broj
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Konstruktor, postavlja kompleksni broj iz polarnih koordinata
	 * 
	 * @param magnitude apsolutna vrijednost
	 * @param angle kut u radijanima
	 * @return referencu na stvoreni kompleksni broj
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude * Math.cos(angle);
		double imag = magnitude * Math.sin(angle);
		return new ComplexNumber(real, imag);
	}

	/**
	 * Parsira zadani string i pretvara ga u kompleksni broj
	 * 
	 * @param s kompleksni broj u obliku stringa
	 * @return referencu na stvoreni kompleksni broj
	 * 
	 * @throws IllegalArgumentException u slučaju unošenja stringa koji nije kompleksni broj
	 */
	public static ComplexNumber parse(String s) {
		double pomReal ;
		double pomImag ;
		try {
			String[] parser = s.split("(?=[-+])");
			if (parser.length == 1) {
				String string1 = parser[0];

				if (string1.charAt(string1.length() - 1) == 'i') {
					pomImag = Double.parseDouble(string1.substring(0, string1.length() - 1));
					return fromImaginary(pomImag);
				} else {
					pomReal = Double.parseDouble(string1.substring(0, string1.length()));
					return fromReal(pomReal);
				}
			}

			else if (parser.length == 2) {
				String string1 = parser[0];
				String string2 = parser[1];

				pomReal = Double.parseDouble(string1.substring(0, string1.length()));
				pomImag = Double.parseDouble(string2.substring(0, string2.length() - 1));
				return new ComplexNumber(pomReal, pomImag);
			}

			else {
				throw new IllegalArgumentException();
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Dohvaća realni dio kompleksnog broj
	 * 
	 * @return realni dio
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Dohvaća imaginarni dio kompleksnog broja.
	 * 
	 * @return imaginarni dio
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Dohvaća apsolutnu vrijednost kompleksnog broja.
	 * 
	 * @return apsolutna vrijednost kompleksnog broja
	 */
	public double getMagnitude() {
		return (Math.sqrt((Math.pow(real, 2) + Math.pow(imaginary, 2))));
	}

	/**
	 * Dohvaća kut kompleksnog broja.
	 * 
	 * @return kut u radijanima
	 */
	public double getAngle() {
		return (Math.atan2(imaginary, real));
	}

	/**
	 * Zbraja pozivajući kompleksni broj sa zadanim
	 * 
	 * @param c kompleksni broj
	 * @return referencu na zbroj
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Oduzima zadani kompleksni broj od pozivajućeg
	 * 
	 * @param c kompleksni broj
	 * @return referencu na razliku
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Množi dva kompleksna broja
	 * 
	 * @param c kompleksni broj
	 * @return referenca na umnožak
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double pomReal;
		double pomImag;
		pomReal = (this.real * c.real) - (this.imaginary * c.imaginary);
		pomImag = (this.imaginary * c.real) + (this.real * c.imaginary);
		return new ComplexNumber(pomReal, pomImag);
	}

	/**
	 * Djeli dva kompleksna broja
	 * 
	 * @param c kompleksni broj
	 * @return referenca na količnik
	 * 
	 * @throws IllegalArgumentException u slučaju pokušaja djeljenja sa nulom
	 */
	public ComplexNumber div(ComplexNumber c) {
		double pomReal;
		double pomImag;
		double djelitelj;
		djelitelj = Math.pow(c.real, 2) + Math.pow(c.imaginary, 2);
		if (djelitelj != 0) {
			pomReal = (this.real * c.real) + (this.imaginary * c.imaginary);
			pomImag = (this.imaginary * c.real) - (this.real * c.imaginary);
			return new ComplexNumber(pomReal / djelitelj, pomImag / djelitelj);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Potencira kompleksni broj
	 * 
	 * @param n cjelobrojni eksponent
	 * @return referenca na potenciju
	 * 
	 * @throws IllegalArgumentException u slučaju potencije manje od nula
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		double pomReal;
		double pomImag;
		pomReal = Math.pow(this.getMagnitude(), n) * Math.cos(n * this.getAngle());
		pomImag = Math.pow(this.getMagnitude(), n) * Math.sin(n * this.getAngle());
		return new ComplexNumber(pomReal, pomImag);
	}

	/**
	 * Metoda za izračunavanje svih korijena kompleksnog broja
	 * 
	 * @param n cjelobrojni korijenski eksponent
	 * @return referencu na polje korijena kompleksnih brojeva
	 * 
	 * @throws IllegalArgumentException u slučaju korijena manjeg od 1
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		ComplexNumber[] polje = new ComplexNumber[n];
		double pomReal = 0;
		double pomImag = 0;
		double razlika = 2 * Math.PI / n;

		for (int i = 0; i < n; i++) {

			pomReal = (Math.pow(getMagnitude(), (double) 1 / n) * Math.cos((getAngle() / n) + i * razlika));
			pomImag = Math.pow(getMagnitude(), (double) 1 / n) * Math.sin((getAngle() / n) + i * razlika);
			polje[i] = new ComplexNumber(pomReal, pomImag);
		}
		return polje;
	}

	/**
	 * Stvaranje stringa iz kompleksnog broja
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		NumberFormat formatter = new DecimalFormat("#0.0000");
		if(getReal() != 0 ){
			string.append(formatter.format(getReal()));
		}
		if (getImaginary() > 0) {
			string.append("+").append(formatter.format(getImaginary())).append("i");
		} else if (getImaginary() < 0) {
			string.append(formatter.format(getImaginary())).append("i");
		}
		return string.toString();
	}
}
