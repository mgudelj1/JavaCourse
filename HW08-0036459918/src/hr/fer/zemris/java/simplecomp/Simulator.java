package hr.fer.zemris.java.simplecomp;

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Demonstration program
 * 
 * @author Marko Gudelj
 *
 */
public class Simulator {

	/**
	 * Ulazna točka u program
	 * @param args putanja do datoteke
	 * @throws Exception iznimka
	 */
	public static void main(String[] args) throws Exception {
		String put = null;
		if (args.length == 0) {
			try {
				BufferedInputStream in = new BufferedInputStream(System.in);
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(in);
				System.out.println("Upisite putanju do datoteke:");
				// Primjerak putanje .\examples\asmProgram1.txt
				// Namjerno nije napravljen sc.close()
				put = sc.nextLine();
			} catch (InvalidPathException e) {
				throw new InvalidPathException("Given path is not valid", put.toString());
			}
		} else {
				put = args[0];
		}

		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);

		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl("hr.fer.zemris.java.simplecomp.impl.instructions");

		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			if(!Files.exists(Paths.get(put))){
				System.out.println("Datoteka ne postoji");
				System.exit(0);
			}
			ProgramParser.parse(put, comp, creator);
		

		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		exec.go(comp);
		} catch (ClassCastException e) {
			System.err.println("Some of the given arguments were not valid");
		} catch (Exception e){
			System.out.println("Tijekom izvođenja programa iz datoteke "+ Paths.get(put).getFileName() +
					" došlo do iznimke " + e +" \n");
			e.printStackTrace();
		}
	}
}
