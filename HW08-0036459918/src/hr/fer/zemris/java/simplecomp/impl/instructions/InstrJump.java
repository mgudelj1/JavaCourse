package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja instrukciju jump
 * Format instrukcije: jump lokacija
 * Instrukcija postavlja pc na vrijednost lokacija
 * 
 * @author Marko Gudelj
 *
 */
public class InstrJump implements Instruction {
	/** Vrijednost programskog brojila */
	int pc;

	/**
	 * Konstruktor za instrukciju jump. Instrukcija prima 1 argument.
	 * 
	 * @param arguments
	 *            nova lokacija
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Instrukcija jump prima 1 argument");
		}
		pc = (Integer) arguments.get(0).getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(pc);
		return false;
	}

}
