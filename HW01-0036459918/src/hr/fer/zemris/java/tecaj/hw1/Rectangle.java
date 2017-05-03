package hr.fer.zemris.java.tecaj.hw1;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 	* Program od korisnika tra�i unos
 	* �irine i visine. Program ra�una
 	* povr�inu i �irinu zadanog pravokutnika.
 	* @author Marko Gudelj
 	* @version 1.0
 	*/

public class Rectangle {
	
		/**
		 	* Metoda od koje kre�e izvo�enje programa.
		 	* @param args argumenti naredbenog retka.
		 	* Argumenti su �irina i visina.
		 	*/
	
	public static void main(String[] args) throws IOException {
		
		double width;
		double height;
		int brojArgumenata = args.length;
	
		if(brojArgumenata == 1 || brojArgumenata > 2)
			System.out.println("Invalid number of arguments provided.");
		
		else if(brojArgumenata == 0){
		
		//Unos �irine i visine dok nije unesena ispravna vrijednost
			width = unos("Width");
			
			height = unos("Height");
			
			izracun(width,height);
		}
		//Ako su argumenti ispravno zadani poziva se funkcija za izracun
		else{
			izracun(Double.parseDouble(args[0]),Double.parseDouble(args[1]) );
		}
		
		
	}
	/**
	 	* Metoda za unos podataka za �irinu ili visinu
	 	* preko standardnog ulaza.
	 	* Vra�a unesenu vrijednost (ako nije praznina)
	 	* bez vode�ih praznina.
	 	* @return
	 	* @throws IOException
	 	*/
	public static double unos(String dimenzija) throws IOException {
		double unos;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in))
			);
		
		
		
		while(true){
			System.out.printf("Please provide %s: ",dimenzija);
			String redak = reader.readLine();
			
			if(redak == null){
				System.out.println("Nothing was given");
				continue;
			}
		
			redak=redak.trim();
			if(redak.isEmpty()){
				System.out.println("Nothing was given");
				continue;
			}
			
			unos = Double.parseDouble(redak);
			
		//Provjeravanje vrijednosti unosa
			if(unos < 0){
				System.out.printf("%s is negative%n",dimenzija);
				continue;
			}
			
		
			if(unos > 0)
				break;
	
		}
		return unos;

	}
	/**
	 	* Metoda koja za argumente prima �irinu i visinu, te
	 	* na zaslon ispisiva �irinu,visinu,povr�inu i opseg
	 	* pravokutnika.
	 	* @param width
	 	* @param height
	 	*/
	public static void izracun(double width,double height) {
		System.out.printf("You have specified rectangle with width %.1f and height %.1f. Its"
				+ " area is %.1f and its perimeter is %.1f",width,height,width*height,2*height+2*width);
		
	}

}
