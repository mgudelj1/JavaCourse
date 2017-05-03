package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for practicing stream manipulation
 * 
 * @author Marko Gudelj
 *
 */
public class StudentDemo {
	/**
	 * Entry point to program
	 * 
	 * @param args
	 *            not used
	 * @throws IOException
	 *             when trying to read bad file
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"));
		List<StudentRecord> records = convert(lines);

		// 1.

		long broj = records.stream().
				filter(z -> z.getBodMI() + z.getBodLAB() + z.getBodZI() > 25).
				count();

		// 2.

		long broj5 = records.stream().filter(z -> z.getOcjena() > 4).count();;

		// 3.

		List<StudentRecord> odlikasi = records.stream().
				filter(z -> z.getOcjena() > 4).
				collect(Collectors.toList());

		// 4.

		List<StudentRecord> odlikasiSortirano = records.stream().filter(z -> z.getOcjena() > 4)
				.sorted((z2, z1) -> Double.compare(z1.getBodLAB() + z1.getBodMI() + z1.getBodZI(),
						z2.getBodLAB() + z2.getBodMI() + z2.getBodZI()))
				.collect(Collectors.toList());
		
		// 5.

		List<String> nepolozeniJMBAGovi = records.stream().filter(z -> z.getOcjena() < 2)
				.sorted((z1, z2) -> String.CASE_INSENSITIVE_ORDER.compare(z1.getJmbag(), z2.getJmbag()))
				.map(z -> z.getJmbag()).collect(Collectors.toList());

		// 6.

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
				.collect(Collectors.groupingBy(z -> z.getOcjena(), 
						Collectors.mapping(z -> z, Collectors.toList())));

		
		// 7.

		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.toMap(StudentRecord::getOcjena, st -> 1, Integer::sum));

		// 8.
	
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream().
				collect(Collectors.partitioningBy(z->z.getOcjena()>1));
						
	}

	/**
	 * Helper method for printing lists to output
	 * 
	 * @param lista
	 *            List
	 */
	@SuppressWarnings("unused")
	private static void ispis(List<StudentRecord> lista) {
		for (StudentRecord line : lista) {
			System.out.println(line);
		}
	}

	/**
	 * Method for converting input list from file to the list with student
	 * records
	 * 
	 * @param lines
	 *            list of strings
	 * @return list<StudentRecord>
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		String[] linija;
		List<StudentRecord> lista = new ArrayList<>();
		for (String line : lines) {
			if (line.isEmpty()) {
				break;
			}
			linija = line.split("\\s+");
			StudentRecord student = new StudentRecord(linija[0], linija[1], linija[2], Double.parseDouble(linija[3]),
					Double.parseDouble(linija[4]), Double.parseDouble(linija[5]), Integer.parseInt(linija[6]));
			lista.add(student);
		}
		return lista;
	}

}