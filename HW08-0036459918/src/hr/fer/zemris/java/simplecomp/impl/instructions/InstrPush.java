package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji implementira instrukciju push. Instrukcija push prihvaća jedan
 * argument te na lokaciju definiranu sa @stackTop koja se pohranjuje u r15
 * pohranjiva vrijednost zadanog registra 
 * push rX 
 * [r15] <- 
 * rX r15--;
 * 
 * @author Marko
 *
 */
public class InstrPush implements Instruction {
	/** Index registra rX */
	private int rX;

	/**
	 * Konstruktor za instrukciju InstrPush. Instrukcija push prihvaća jedan
	 * argument
	 * 
	 * @param arguments registar
	 */
	public InstrPush(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Instrukcija push prihvaća samo 1 argument");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Argument bi trebao biti registar");
		}

		rX = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		int vrhStoga = (Integer) computer.getRegisters().getRegisterValue(15);
		Object value1 = computer.getRegisters().getRegisterValue(rX);

		computer.getMemory().setLocation(vrhStoga, value1);

		vrhStoga--;
		computer.getRegisters().setRegisterValue(15, vrhStoga);
		return false;
	}

}
