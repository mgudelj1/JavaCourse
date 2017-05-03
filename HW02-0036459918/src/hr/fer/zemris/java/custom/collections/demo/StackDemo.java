package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Testni razred koji prihvaća izraze, koji će biti izračunati, u postfix
 * notaciji preko komandne linije
 * 
 * @author Marko Gudelj
 * @version 1.0
 */
public class StackDemo {
	/**
	 * Ulazna metoda razreda
	 * 
	 * @param args brojevi i operatori
	 *         
	 * @throws IllegalArgumentException ako je više od jednog argumenta, te ako je zadan
	 * znak koji nije ni operator ni broj
	 * @throws Error ispisiva Error ako je zadan ne odgovarajući redoslijed brojeva i operatora
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			throw new IllegalArgumentException();
		}

		ObjectStack demo = new ObjectStack();

		String[] parser = args[0].split("\\s+");

		int pom;
		Object var1, var2;
		for (String item : parser) {

			if (item.matches("-?\\d+")) {
				pom = Integer.parseInt(item);
			} else {
				pom = (int) item.charAt(0);
			}
			if ((int) pom > 32 && (int) pom < 48) {
				var2 = demo.pop();
				var1 = demo.pop();
				switch ((int) pom) {
				case 37:
					demo.push((int) var1 % (int) var2);
					break;
				case 42:
					demo.push((int) var1 * (int) var2);
					break;
				case 43:
					demo.push((int) var1 + (int) var2);
					break;
				case 45:
					demo.push((int) var1 - (int) var2);
					break;
				case 47:
					if ((int) var2 == 0) {
						System.out.println("Dividing with zero is not supported");
						System.exit(0);
					}
					demo.push((int) var1 / (int) var2);
					break;
				default:
					throw new IllegalArgumentException();
				}
			} else {
				demo.push(pom);
			}
		}
		if (demo.size() != 1) {
			throw new Error("Expression invalid");
		} else {
			System.out.printf("Expression evaluates to %d", demo.pop());
		}
	}
}
