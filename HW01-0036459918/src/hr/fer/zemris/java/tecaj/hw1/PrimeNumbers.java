package hr.fer.zemris.java.tecaj.hw1;

/**
	* Program koji raèuna, preko naredbenog
	* retka zadanih, prvih n prostih brojeva
	* @author Marko Gudelj
	* @version 1.0
	*/

public class PrimeNumbers {

	/**
	 	* Metoda od koje kreæe izvoðenje programa. 
	 	* @param args argumenat naredbenog retka
	 	*/
	
	public static void main(String[] args) {
		
		if(args.length != 1 || Integer.parseInt(args[0]) <= 0)
				System.out.println("You have to enter a valid argument (n>0)");
		else{
			
			Integer n = Integer.parseInt(args[0]);
			int broj = 3;
			int pombr = 1;
			boolean prost = true;
		
		//S obzirom da zadani argument mora biti veæi od nula 2 je sigurno prvi prosti broj
			System.out.printf("You requested calculation of %d prime numbers. Here they are:%n",n);
			System.out.printf("%d. 2%n",pombr);
			
		//Provjera je li odreðeni broj prost kreæe se od 3 i provjerava se svako drugi broj zbog parnosti
			while(pombr < n){
				prost = true;
				for(int i = 3; i < broj; i++)
					if(broj % i == 0){
						prost = false;
						break;
					}
	
				if(prost){
					pombr +=1;
					System.out.printf("%d. %d%n",pombr,broj);
				}
				broj +=2;
			}
		
		}

	}
}
