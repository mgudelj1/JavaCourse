package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;


import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja instrukciju koja uzima sadržaj registra rX i ispisuje
 * ga na ekran, ova instrukcija podržava indirektno adresiranje (legalan oblik
 * instrukcije je i echo [rX+offset])
 * 
 * @author Marko Gudelj
 *
 */
public class InstrEcho implements Instruction {
	/** Vrijednost registra ili memorijske lokacije za ispis */
	private int rX;
	/** Zastavica za oznaku je li registar sa offsetom */
	private boolean offset = false;
	/** Offset za dani registar */
	private int rXOffset;
	/**
	 * Konstruktor za InstrEcho. Naredba prima samo jedan argument.
	 * 
	 * @param arguments
	 *            registar
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Instrukcija prima samo jedan argument");
		}
		if (arguments.get(0).isRegister()){
			rX = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
			if(RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())){
				rXOffset = RegisterUtil.getRegisterOffset((Integer) arguments.get(0).getValue());
				offset = true;
			}
		}else{
			throw new IllegalArgumentException("Argument mora biti registar sa ili bez offseta");
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(rX);
		if (offset) {
			System.out.print(computer.getMemory().getLocation((Integer) value1+rXOffset));
		} else {
			System.out.print(value1);
		}
		return false;
	}

}
