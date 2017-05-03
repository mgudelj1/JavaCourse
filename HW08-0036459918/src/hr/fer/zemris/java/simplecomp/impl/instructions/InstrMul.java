package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavla instrukciju mul r0, r1, r2 koja uzima sadržaje
 * registara r1 i r2, množi ih i rezultat pohranjuje u r0
 * 
 * @author Marko Gudelj
 *
 */
public class InstrMul implements Instruction {
	/** Vrijednost indexa registra 1 */
	private int indexRegistra1;
	/** Vrijednost indexa registra 2 */
	private int indexRegistra2;
	/** Vrijednost indexa registra 3 */
	private int indexRegistra3;

	/**
	 * Konstruktor za instrukciju mul.
	 * 
	 * @param arguments
	 *            argumenti
	 * @throws IllegalArgumentException
	 *             u slučaju netočnog broja ili neispravnih argumenata
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegistra3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(indexRegistra1, Integer.valueOf((Integer) value1 * (Integer) value2));
		return false;
	}
}
