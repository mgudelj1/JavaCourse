package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Razred koji predstavlja kraj izvršavanja naredbi procesora
 * 
 * @author Marko Gudelj
 *
 */
public class InstrHalt implements Instruction {
	/**
	 * Konstruktor naredbe za kraj
	 * @param arguments argumenti
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		if(arguments.size() != 0){
			throw new IllegalArgumentException("Halt ne bi trebao imati argumente");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
