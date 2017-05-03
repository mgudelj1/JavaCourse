package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;

import hr.fer.zemris.java.simplecomp.models.*;

/**
 * Instrukcija koja uzima sadržaj memorijske lokacije (dobit će to kao broj u
 * drugom argumentu) i pohranjuje taj sadržaj u registar rX (index će dobiti kao
 * broj u prvom argumentu); instrukcija ne dozvoljava indirektno adresiranje
 * 
 * @author Marko Gudelj
 *
 */
public class InstrLoad implements Instruction {
	/** Index registra u koji se pohranjiva vrijednost */
	private int indexRegistra1;
	/** Memorijska lokacija sa koje se uzima vrijednost */
	private int memoryLocation;
/**
 * Konstruktor za instrukciju load
 * @param arguments argumenti
 * @throws IllegalArgumentException u slučaju netočnog broja ili neispravnih
 * argumenata
 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Očekivana su 2 argumenta!");
		}
		if (!arguments.get(0).isRegister() || !arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Tip jednog argumenta ne odgovara instrukciji");
		}
		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.memoryLocation = (int) arguments.get(1).getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean execute(Computer computer) {
		Object value1 = computer.getMemory().getLocation(this.memoryLocation);
		computer.getRegisters().setRegisterValue(indexRegistra1, value1);
		return false;
	}

}
