package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja povratak iz potprograma pozvanog instrukcijom call. S
 * vrha stoga skida adresu i postavlja je kao vrijednost registra PC (program
 * counter).
 * 
 * @author Marko Gudelj
 *
 */
public class InstrRet implements Instruction {
	/**
	 * Konstruktor za instrukciju ret. Instrukcija ne prima argumente
	 * 
	 * @param arguments N/A
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		if (arguments.size() != 0) {
			throw new IllegalArgumentException("Instrukcija ret ne prima argumente");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		int vrhStoga = (Integer) computer.getRegisters().getRegisterValue(15);
		vrhStoga++;
		Object value1 = computer.getMemory().getLocation(vrhStoga);

		computer.getRegisters().setRegisterValue(15, vrhStoga);

		computer.getRegisters().setProgramCounter((Integer) value1);
		return false;
	}

}
