package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja instrukciju add. 
 * Format instrukcije add: add rx, ry, rz 
 * Registri ry i rz se spremaju te pohranjunju u rx
 * 
 * @author Marko Gudelj
 *
 */
public class InstrAdd implements Instruction {
	/** Index registra rx */
	private int rX;
	/** Index registra ry */
	private int rY;
	/** Index registra rz */
	private int rZ;

	/**
	 * Konstruktor za instrukciju add. Prihvaća 3 argumenta
	 * 
	 * @param arguments
	 *            indexi registara rx,ry,rz
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Instrukcija add prima 3 argumenta");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Argument je krivog tipa " + i + "!");
			}
		}
		rX = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		rY = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		rZ = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(rY);
		Object value2 = computer.getRegisters().getRegisterValue(rZ);
		computer.getRegisters().setRegisterValue(rX, Integer.valueOf((Integer) value1 + (Integer) value2));
		return false;
	}
}
