package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw5.db.Filters.*;

/**
 * Main class of the program. Represents student database, where all the student
 * records are kept.
 * 
 * @author Marko
 *
 */
public class StudentDB {

	/** Hash table for the student database */
	public static SimpleHashtable<String, StudentRecord> mapa = new SimpleHashtable<>();
	/** List of records stored in this database */
	public static ArrayList<StudentRecord> DataBase = new ArrayList<>();

	/**
	 * Constructor for the StudentDB class. This constructor reads all lines
	 * from the given path and stores each line as a list node in List
	 * 
	 * @param lines
	 * 
	 * @throws IOException
	 */
	public StudentDB(List<String> lines) throws IOException {
		String[] linija;
		for (String line : lines) {
			linija = line.split("\\s+");
			StudentRecord student = new StudentRecord(linija[0], linija[1], linija[2], linija[3]);
			DataBase.add(student);
			mapa.put(linija[0], student);
		}
	}

	/**
	 * Entry point in program
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("database/datoteka.txt"), StandardCharsets.UTF_8);
		StudentDB baza = new StudentDB(lines);

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String[] string;
		IFilter filter;
		List<StudentRecord> queryList;
		while (true) {
			StringBuilder sb = new StringBuilder();
			String strLine = in.readLine();

			if (strLine.trim().equals("exit")) {
				System.out.println("Goodbye");
				break;
			}

			string = strLine.split("\\s+");

			for (int i = 1, length = string.length; i < length; i++) {
				sb.append(string[i]);
				sb.append(' ');
			}

			if (string[0].toLowerCase().equals("query")) {
				filter = new QueryFilter(sb.toString());
			} else if (string[0].toLowerCase().equals("indexquery")) {
				filter = new IndexQueryFilter(sb.toString());
			} else {
				System.err.println("You should request query or indexquery");
				continue;
			}

			queryList = baza.filter(filter);
			baza.ispis(queryList);

		}
	}

	/**
	 * Returns student with provided jmbag. Complexity O(1).
	 * 
	 * @param jmbag
	 * @return student record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return mapa.get(jmbag);
	}

	/**
	 * The method filter loops through all student records in its internal list,
	 * calls accepts method on given filter-object with current record. Each
	 * record for which accepts returns true is added to temporary list and this
	 * list is then returned by the filter method.
	 * 
	 * @param filters
	 *            QueryFilter or IndexQueryFilter
	 * @return List<StudentRecord>
	 */
	public List<StudentRecord> filter(IFilter filters) {
		ArrayList<StudentRecord> tempList = new ArrayList<>();

		if (filters instanceof IndexQueryFilter) {
			Parser parser = new Parser();
			ArrayList<ConditionalExpression> expressions = parser.parse(((IndexQueryFilter) (filters)).getQuery());
			tempList.add(forJMBAG(expressions.get(0).getStringLiteral()));
		} else {
			for (StudentRecord student : DataBase) {
				if (filters.accepts(student)) {
					tempList.add(student);
				}
			}
		}
		return tempList;
	}

	/**
	 * Helper method for formatted print
	 * 
	 * @param lista
	 */
	public void ispis(List<StudentRecord> lista) {
		int maxIme = 0;
		int maxPre = 0;
		for (StudentRecord student : lista) {
			if (student.getfirstName().length() > maxIme) {
				maxIme = student.getfirstName().length();
			}
			if (student.getlastName().length() > maxPre) {
				maxPre = student.getlastName().length();
			}
		}

		ispisZagrada(maxIme, maxPre);

		for (StudentRecord student : lista) {
			System.out.printf("| " + student.getJmbag() + " | " + student.getlastName());
			ispisPraznina(Math.abs(student.getlastName().length() - maxPre));
			System.out.printf(" | " + student.getfirstName());
			ispisPraznina(Math.abs(student.getfirstName().length() - maxIme));
			System.out.printf(" | " + student.getfinalGrade() + " |");
			System.out.println();
		}

		ispisZagrada(maxIme, maxPre);

	}

	/**
	 * Method for print formatting
	 * 
	 * @param broj
	 */
	private void ispisPraznina(int broj) {
		for (int i = 0; i < broj; i++) {
			System.out.printf(" ");
		}
	}

	/**
	 * helper method for print formatting
	 * 
	 * @param maxIme
	 * @param maxPre
	 */
	private void ispisZagrada(int maxIme, int maxPre) {
		System.out.printf("+============+==");
		for (int i = 0; i < maxPre; i++) {
			System.out.printf("=");
		}
		System.out.printf("+==");
		for (int i = 0; i < maxIme; i++) {
			System.out.printf("=");
		}
		System.out.println("+===+");
	}
}
