package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Upravljački sklop računala. Ovaj razred "izvodi" program odnosno predstavlja
 * impulse takta za sam procesor.
 * 
 * @author Marko Gudelj
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean go(Computer computer) {
		Registers registri = computer.getRegisters();
		registri.setProgramCounter(0);
		Instruction instrukcija;
		while(true){
			instrukcija = (Instruction) computer.getMemory().getLocation(registri.getProgramCounter());
			registri.incrementProgramCounter();
			if(instrukcija.execute(computer)){
				break;
			}
		}
		return true;
	}

}