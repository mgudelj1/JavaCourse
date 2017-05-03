package hr.fer.zemris.java.tecaj.hw5.db.Filters;

import java.util.ArrayList;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.Getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.Operators.IComparisonOperator;

/**
 * Filter for query input
 * 
 * @author Marko
 *
 */
public class QueryFilter implements IFilter {
	/** List of conditional expressions */
	ArrayList<ConditionalExpression> expressions;

	/**
	 * Constructor for QueryFilter
	 * 
	 * @param query
	 */
	public QueryFilter(String query) {
		System.out.println(query);
		Parser parser = new Parser();
		expressions = parser.parse(query);
	}

	/**
	 * Method that determines is the provided record acceptable for the given
	 * conditional expressions
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		IComparisonOperator operator;
		IFieldValueGetter fieldGetter;
		String literal;

		for (ConditionalExpression izraz : expressions) {
			operator = izraz.comparisonOperator;
			fieldGetter = izraz.fieldGetter;
			literal = izraz.stringLiteral;

			if (operator.satisfied(fieldGetter.get(record), literal)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
}

/*
 * 
 * Create a class QueryFilter which implements IFilter. It has a single public
 * constructor which receives one argument: query string (everything user
 * entered after query keyword). For query parsing you can write additional
 * classes. I would highly recommend writing a simple lexer and parser: now you
 * now how to do it.
 * 
 */