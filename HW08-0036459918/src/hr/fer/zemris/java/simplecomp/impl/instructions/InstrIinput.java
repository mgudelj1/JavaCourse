package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja unos redka s tipkovnice. Sadržaj tumači kao Integer i
 * njega zapisuje na zadanu memorijsku lokaciju. Dodatno postavlja zastavicu
 * flag na true ako je sve u redu, odnosno na false ako konverzija nije uspjela
 * ili je drugi problem s čitanjem.
 * 
 * @author Marko Gudelj
 *
 */
public class InstrIinput implements Instruction {
	/** Vrijednost adrese */
	private int adresa;

	/**
	 * Konstruktor za instrukciju iinput. Instrukcija prihvaća jedan argument
	 * 
	 * @param arguments
	 *            adresa
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Instrukcija iinput prihvaća jedan argument");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Argument mora biti memorijska lokacija");
		}

		adresa = (Integer) arguments.get(0).getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		Integer broj;
		BufferedInputStream in = new BufferedInputStream(System.in);
		@SuppressWarnings("resource")
		// Multiple input may be possible
		Scanner sc = new Scanner(in);
		try {
			System.out.printf("Unesite početni broj: ");
			broj = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Unos nije moguće pročitati kao cijeli broj");
			computer.getRegisters().setFlag(false);
			return false;
		}

		computer.getRegisters().setFlag(true);
		computer.getMemory().setLocation(adresa, broj);
		return false;
	}

}
