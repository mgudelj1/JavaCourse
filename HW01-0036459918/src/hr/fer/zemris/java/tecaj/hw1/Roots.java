package hr.fer.zemris.java.tecaj.hw1;
import java.lang.Math;

/**
	* Program koji raèuna n-ti korijen iz zadanog
	* kompleksnog broja.
	* @author Marko Gudelj
	* @version 1.0
	*/

public class Roots {

	/**
 	* Metoda od koje kreæe izvoðenje programa. 
 	* Prima 3 cjelobrojna argumenta.
 	* @param args realni dio kompleksnog broja,
 	* imaginarni dio kompleknog broj i broj korijena.
 	*/
	
	public static void main(String[] args) {
		
		if(args.length != 3){
			System.out.println("You have to enter a valid number of arguments(3)");
		}
		else if( Integer.parseInt(args[2]) <= 1){
			System.out.println("You have to enter 3rd argument greater than 1 ( n > 1)");
		}
		else{
		double real = 0,imag = 0,root = 0, razlika = 0;
		double r = 0.0,angle = 0.0;
		
		
		real = Double.parseDouble(args[0]);
		imag = Double.parseDouble(args[1]);
		root = Double.parseDouble(args[2]);
		
		//Izraèunavanje modula r = korijen(x^2+y^2) i kuta §
		r = Math.sqrt((Math.pow(real,2) + Math.pow(imag,2)));
		angle = (Math.atan2(imag,real));
		
		razlika= 2*Math.PI / root ;
				
		
		for(int i = 0; i < root; i++){
			// realni dio = r^(1/n) * cos( (§/n) + i * razlika u kutu)
			real = Math.pow(r,1/root) * Math.cos(  (angle/root) + i*razlika );
			imag = Math.pow(r,1/root) * Math.sin(  (angle/root) + i*razlika );
			
			if(imag > 0)
				System.out.printf("%d) %.2f + %.2fi%n",i+1,real,Math.abs(imag));
			else
				System.out.printf("%d) %.2f - %.2fi%n",i+1,real,Math.abs(imag));
		}
		
		
		}
	}

}
