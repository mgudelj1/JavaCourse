package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja poziv potprograma. Trenutni sadržaj registra PC
 * (program counter) pohranjuje na stog; potom u taj registar upisuje predanu
 * adresu čime definira sljedeću instrukciju koja će biti izvedena.
 * 
 * @author Marko Gudelj
 *
 */
public class InstrCall implements Instruction {
	/** Vrijednost adrese potprograma */
	private int adresa;

	/**
	 * Konstruktor za instrukciju call. Instrukcija prihvaća jedan argument.
	 * 
	 * @param arguments
	 *            adresa
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Instrukcija call prihvaća jedan argument");
		}

		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Argument instrukcije mora biti adresa");
		}

		adresa = (Integer) arguments.get(0).getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {

		int vrhStoga = (Integer) computer.getRegisters().getRegisterValue(15);

		computer.getMemory().setLocation(vrhStoga, computer.getRegisters().getProgramCounter());

		vrhStoga--;
		computer.getRegisters().setRegisterValue(15, vrhStoga);

		computer.getRegisters().setProgramCounter(adresa);

		return false;
	}

}
