package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Razred koji predstavlja instrukciju move. Instrukcija premješta vrijednost
 * drugog argumenta u prvi. Drugi argument može biti registar, broj ili registar
 * sa offsetom. Prvi argument je registar sa ili bez offseta. Formati
 * instrukcije: 1) move rx, ry rx <- ry 2) move rx, broj rx <- broj (Zadana
 * cjelobrojna vrijednost) 3) move [rx+o1],[ry+o2] (rx+o1) <- (ry+o2)
 * 
 * 
 * @author Marko Gudelj
 *
 */
public class InstrMove implements Instruction {
	/** Index registra rX */
	private int rX;
	/** Vrijednost Offseta za registar rX */
	private int rXOffset;
	/** Index registra rY */
	private int rY;
	/** Vrijednost Offseta za registar rY */
	private int rYOffset;
	/** Vrijednost broja */
	private int broj = 0;
	/**
	 * Polje sa vrijednostima zastavica koje oznacavaju jesu li registri sa
	 * offsetima ili bez offseta, te je li drugi argument broj. Svaki argument
	 * ima svoje polje. 
	 * 1) 0 ako je registar, 1 ako je registar sa offset 
	 * 2) 0 ako je registar, 1 ako je registar sa offset 
	 * 3) 1 ako je broj, 0 ako nije
	 */
	private boolean[] brojT = new boolean[3];

	/**
	 * Konstruktor za instrukciju move. Instrukcija prihvaća 2 argumenta
	 * 
	 * @param arguments
	 *            argumenti
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Instrukcija move prima 2 argumenta");
		}

		int desc;
		if (arguments.get(0).isRegister()) {
			desc = (Integer) arguments.get(0).getValue();
			if (RegisterUtil.isIndirect(desc)) {
				brojT[0] = true;
				rXOffset = RegisterUtil.getRegisterOffset(desc);
			}
			rX = RegisterUtil.getRegisterIndex(desc);
		} else {
			throw new IllegalArgumentException("Prvi argument mora biti registar");
		}
		if (arguments.get(1).isRegister()) {
			desc = (Integer) arguments.get(1).getValue();
			if (RegisterUtil.isIndirect(desc)) {
				brojT[1] = true;
				rYOffset = RegisterUtil.getRegisterOffset(desc);
			}
			rY = RegisterUtil.getRegisterIndex(desc);
		} else if (arguments.get(1).isNumber()) {
			brojT[2] = true;
			broj = (Integer) arguments.get(1).getValue();
		} else {
			throw new IllegalArgumentException("Drugi argument mora biti registar, indirektna adresa ili broj");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Computer computer) {
		Registers registri = computer.getRegisters();
		Memory memorija = computer.getMemory();
		Object value1;
		Object value2;

		if (brojT[0]) {
			value1 = (Integer) registri.getRegisterValue(rX) + rXOffset;
			if (brojT[2]) {
				memorija.setLocation((Integer) value1, broj);
			} else if (brojT[1]) {
				value2 = (Integer) registri.getRegisterValue(rY) + rYOffset;
				memorija.setLocation((Integer) value1, memorija.getLocation((Integer) value2));
			} else {
				value2 = registri.getRegisterValue(rY);
				memorija.setLocation((Integer) value1, value2);
			}
		} else {
			if (brojT[2]) {
				registri.setRegisterValue(rX, broj);
			} else if (brojT[1]) {
				value2 = (Integer) registri.getRegisterValue(rY) + rYOffset;
				registri.setRegisterValue(rX, memorija.getLocation((Integer) value2));
			} else {
				value2 = registri.getRegisterValue(rY);
				registri.setRegisterValue(rX, value2);
			}
		}

		return false;
	}

}
