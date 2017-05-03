package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;


/**
 * Razred koji predstavlja instrukciju pop. Instrukcija pop prihvaća 1 argument
 * i to registar. Pop dohvaća sa vrha stoga jedan element i pohranjiva ga u
 * registar. 
 * pop rX 
 * 	rX <- [r15+1] 
 * 	r15++;
 * 
 * @author Marko Gudelj
 *
 */
public class InstrPop implements Instruction{
	/** Index registra rX */
	private int rX;

	/**
	 * Konstruktor za instrukciju pop.
	 * 
	 * @param arguments registar
	 */
	public InstrPop(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Instrukcija pop prihvaća samo jedan argument");
		}

		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Argument za instrukciju pop mora biti registar");
		}
		rX = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		int vrhStoga = (Integer) computer.getRegisters().getRegisterValue(15);
		Object value1 = computer.getMemory().getLocation(vrhStoga + 1);

		computer.getRegisters().setRegisterValue(rX, value1);

		vrhStoga++;
		computer.getRegisters().setRegisterValue(15, vrhStoga);
		return false;
	}

}
