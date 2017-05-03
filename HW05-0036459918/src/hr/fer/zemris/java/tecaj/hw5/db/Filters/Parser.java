package hr.fer.zemris.java.tecaj.hw5.db.Filters;

import java.util.ArrayList;

/**
 * Helper parser method for the creation of ConditionalExpressions
 * 
 * @author Marko
 *
 */
public class Parser {

	/**
	 * Parse method responsible for creating array list of conditional
	 * expressions. Delegates job to Lexer
	 * 
	 * @param query
	 * @return ArrayList
	 */
	public ArrayList<ConditionalExpression> parse(String query) {
		Lexer lexer = new Lexer(query);
		ArrayList<ConditionalExpression> vrati = new ArrayList<>();
		for (int i = 0; i < lexer.length; i++) {
			vrati.add(lexer.nextExpression());
		}
		return vrati;
	}
}
