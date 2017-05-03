package hr.fer.zemris.java.simplecomp;


/**
 * Razred za pomoć u manipuliranju argumentima descriptora
 * 
 * @author Marko Gudelj
 *
 */
public class RegisterUtil {
	/**
	 * Metoda getRegisterIndex iz predanog deskriptora vadi indeks registra.
	 * 
	 * @param registerDescriptor deskriptor
	 * @return index registra
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return (Integer) (registerDescriptor & 0x00000FF);
	}

	/**
	 * Metoda isIndirect provjerava radi li se o indirektnom adresiranju (vraća
	 * true) ili ne (vraća false)
	 * 
	 * @param registerDescriptor deskriptor
	 * @return true ako je indirektrno adresiranje, false inače
	 */
	public static boolean isIndirect(int registerDescriptor) {
		if((Integer) (registerDescriptor & 0x1000000) > 0){
			return true;
		}
		return false;
	}

	/**
	 * a metoda getRegisterOffset vadi pomak koji je potrebno koristiti.
	 * 
	 * @param registerDescriptor deskriptor
	 * @return pomak registra
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		return (short) ((registerDescriptor & 0x0FFFF00) >> 8);
		
	}
}
