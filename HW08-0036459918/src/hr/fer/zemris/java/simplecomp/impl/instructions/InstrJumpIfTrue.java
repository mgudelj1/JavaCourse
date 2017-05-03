package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja instrukciju jumpIfTrue
 * Format instrukcije: jumpIfTrue lokacija
 * Instrukcija postavlja pc na vrijednost lokacija
 * ako je zastavica postavljena na true (flag = 1)
 * 
 * @author Marko Gudelj
 *
 */
public class InstrJumpIfTrue implements Instruction {
	/** Vrijednost programskog brojila */
	int pc;

	/**
	 * Konstruktor za instrukciju jumpIfTrue. Instrukcija prima 1 argument.
	 * 
	 * @param arguments
	 *            nova lokacija
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Instrukcija jumpIfTrue prima 1 argument");
		}
		pc = (Integer) arguments.get(0).getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(pc);
		}
		return false;
	}

}
