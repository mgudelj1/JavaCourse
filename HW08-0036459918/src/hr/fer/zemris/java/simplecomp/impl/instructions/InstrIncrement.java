package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja instrukciju increment. 
 * Format instrukcije: increment rx. 
 * Instrukcija povećava vrijednost u registru rx za 1
 * 
 * @author Marko Gudelj
 *
 */
public class InstrIncrement implements Instruction {
	/** Index registra rx */
	private int rX;

	/**
	 * Konstruktor za instrukciju increment. Prihvaća 1 argument
	 * 
	 * @param arguments
	 *            index registra rx
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Instrukcija increment prima 1 argument");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Argument je krivog tipa!");
		}

		rX = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(rX);
		computer.getRegisters().setRegisterValue(rX, Integer.valueOf((Integer) value1 + 1));
		return false;
	}
}
