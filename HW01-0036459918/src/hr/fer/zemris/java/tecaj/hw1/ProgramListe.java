package hr.fer.zemris.java.tecaj.hw1;

/**
	* Program koji ubaciva podatke u listu,
	* zatim ih ispisuje, sortira te ponovo
	* ispisuje.Na kraju se ispisiva broj
	* elemenata liste.
	* @author Marko Gudelj
	* @version 1.0
	*/

class ProgramListe {
		/**
		 * Klasa koja sadrži referencu na samu sebe
		 * te String podatak.
		 *
		 */
	static class CvorListe {
			CvorListe sljedeci;
			String podatak;
	}
		/**
		 * Metoda koja je ulazna toèka programa.
		 * @param args se ne koriste.
		 */
public static void main(String[] args) {
		CvorListe cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
}

/**
 * Metoda za izraèun broja elemenata liste.
 * @param cvor poèetni èvor liste
 * @return vraæa velièinu liste
 */
private static int velicinaListe(CvorListe cvor) {
		int velicina = 0;
		
		while(true){
			velicina++;
			if(cvor.sljedeci == null)
					break;
			else{
				cvor = cvor.sljedeci;
			}
		}
		return velicina;
}

/**
 * Metoda za ubacivanje cvora u listu. Cvor
 * se dodaje na pocetak liste
 * @param prvi oznaèava prvi element liste
 * @param podatak oznaèava podatak koji se ubacuje u listu
 * @return vraæa novo stvoreni element liste
 */
private static CvorListe ubaci(CvorListe prvi, String podatak) {
		CvorListe pomcvor = new CvorListe();
		
		pomcvor.sljedeci = prvi;
		pomcvor.podatak = podatak;
		
		return pomcvor;
}

/**
 * Metoda koja ispisiva sve elemente liste.
 * @param cvor poèetni èvor liste
 */
private static void ispisiListu(CvorListe cvor) {
		
		while(true){
			System.out.printf("%s%n",cvor.podatak);
			if(cvor.sljedeci == null)
				break;
			else{
				cvor = cvor.sljedeci;
			}
		}
}

/**
 * Metoda za sortiranje liste prema abecednom redu slova.
 * Koristi bubble sort algoritam. Usporeðuju se dva èlana
 * liste te ih se zamjenjiva ako je prvi veæi od drugoga.
 * Ponavlja se sve dok ne bude niti jedne zamjene.
 * @param cvor poèetni èvor liste
 * @return vraæa poèetni èvor sortirane liste
 */
private static CvorListe sortirajListu(CvorListe cvor) {
		CvorListe prvicvor = new CvorListe();
		String podzamjena;
		boolean sortirano = true;
		int j=0;
		
		//Ako je lista velicine manje od 2 onda je veæ sortirana
		int velicina = velicinaListe(cvor);
		if(velicina < 2){
			return cvor;
		}
		//Zadržava se prvi element liste zbog potreba ponovne iteracije
		prvicvor = cvor;
		while(sortirano){
			cvor = prvicvor;
			sortirano = false;
			j++;
				for(int i=0; i < velicina-j; i++){
					//Provjerava jel postoji sljedeæi èlan i jel sljedeæi podatak manji od trenutnog 
					if( (cvor.sljedeci != null) && (cvor.podatak.compareTo(cvor.sljedeci.podatak) > 0 ) ){
						//Zamjena podataka
								podzamjena = cvor.podatak;
								cvor.podatak = cvor.sljedeci.podatak;
								cvor.sljedeci.podatak = podzamjena;
								
								sortirano = true;
					}
					cvor=cvor.sljedeci;
				}
		}
		return prvicvor;
		
}
}