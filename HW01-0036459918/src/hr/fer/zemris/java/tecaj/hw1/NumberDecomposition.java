package hr.fer.zemris.java.tecaj.hw1;

/**
 	* Program koji rastavlja broj, zadan
 	* preko naredbenog retka, na proste faktore.
 	* @author Marko Gudelj
 	* @version 1.0
 	*/

public class NumberDecomposition {
	
/**
 	* Metoda od koje kreæe izvoðenje programa. 
 	* @param args broj koji treba rastaviti, zadan
 	* preko naredbenog retka
 	*/
	public static void main(String args[]){
		
		if(args.length != 1 || Integer.parseInt(args[0]) <= 1){
			System.out.println("You have to enter a valid argument ( n > 1)");
		}
		
		int broj = Integer.parseInt(args[0]);
		int redni = 1;
		int pocbroj = broj;
		
		System.out.printf("You requested decomposition of number %d onto prime factors. Here they are:%n",broj);
		for(int i = 2; i < pocbroj; i++){
			
			//Broj se dijeli koliko je god moguæe puta zbog prostosti brojeva, prekida se kad je 1
			if(broj % i == 0){
				System.out.printf("%d. %d%n",redni,i);
				broj /= i;
				redni++;
				i--;
				
				if(broj == 1)
					break;
			}
			
		}
		
		if(redni == 1)
			System.out.printf("%d is prime number",broj);
		
	}
}
