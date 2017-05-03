package hr.fer.zemris.java.tecaj.hw1;
/**
 	* Program koji rekurzivno ra�una i
 	* ispisiva i-ti broj HofstadterQ niza.
 	* @author Marko Gudelj
 	* @version 1.0
 	*/
public class HofstadterQ {
	/**
	 * Metoda koja je ulazna to�ka programa.
	 * Kao argument se prihva�a broj.
	 * @param args broj do kojeg se ra�una HofstadterQ niz.
	 */
	public static void main(String[] args) {
		
		//Provjera jel unesen ispravan broj arguemanata
		if(args.length != 1){
			System.out.println("You have not entered a valid number of arguments");
		}
		else if(Integer.parseInt(args[0]) <= 0){
			System.out.println("You have to enter positive number");
		}
		else{
			int i = Integer.parseInt(args[0]);
			System.out.printf("You requested calculation of %d. number of "
			+ "Hofstadter's Q-sequence. The requested number is %d.",i,izracun(i));
			}
		}
	


/**
 * Metoda za izra�un i-tog broja HofstadterQ niza
 * @param i �eljeni broj niza
 * @return broj iz niza
 */
	public static int izracun(int i) {
		if (i == 1 || i == 2)
			return 1;
		else {
			//Rekurzivni poziv funkcije Q(i) = Q(i - Q(i-1)) + Q(i - Q(i-2))
			return (izracun(i-izracun(i-1)) + izracun(i-izracun(i-2)));
		}
	}
}