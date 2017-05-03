package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja instrukciju testIfEquals 
 * Format instrukcije: testIfEquls rx, ry 
 * Instrukcija postavlja flag na 1 ako su vrijednosti unutar
 * tih registara jednake, false ako nisu
 * 
 * @author Marko Gudelj
 *
 */
public class InstrTestEquals implements Instruction {
	/** Index registra rX */
	private int rX;
	/** Index registra rY */
	private int rY;

	/**
	 * Konstruktor za instrukciju testEquals. Instrukcija prihvaća dva argumenta
	 * 
	 * @param arguments
	 *            dva registra
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Instrukcija testIfEquals prihvaća dva argumenta");
		}

		if (!(arguments.get(0).isRegister() && arguments.get(1).isRegister())) {
			throw new IllegalArgumentException("Oba argumenta moraju biti registri");
		}

		rX = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		rY = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(rX);
		Object value2 = computer.getRegisters().getRegisterValue(rY);

		if (value1.equals(value2)) {
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}
