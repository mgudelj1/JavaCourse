package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class represents number wrapper. Each object can be type integer, double
 * or string
 * 
 * @author Marko Gudelj
 *
 */
public class ValueWrapper {
	/** object value */
	private Object value;

	/**
	 * Constructor for ValueWrapeper
	 * 
	 * @param value to instantiate
	 */
	public ValueWrapper(Object value) {
		if (value == null) {
			this.value = new Integer(0);
		}else if (value instanceof Integer || value instanceof Double || value instanceof String) {
			this.value = processObject(value);
		} else {
			throw new RuntimeException("Value of this type can not be instantiated");
		}
	}

	/**
	 * Getter for value
	 * 
	 * @return object value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for value
	 * 
	 * @param value object value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Method for incrementing current value with given.
	 * 
	 * @param incValue value for increment
	 */

	public void increment(Object incValue) {
		Object pomVal = this.value;
		value = Double.parseDouble(value.toString()) + Double.parseDouble(processObject(incValue).toString());
		checkInteger(pomVal, incValue);
	}

	/**
	 * Method for decrementing current value with given.
	 * 
	 * @param decValue value for decrement
	 */

	public void decrement(Object decValue) {
		Object pomVal = this.value;
		value = Double.parseDouble(value.toString()) - Double.parseDouble(processObject(decValue).toString());
		checkInteger(pomVal, decValue);
	}

	/**
	 * Method for multiplying current value with given.
	 * 
	 * @param mulValue value for multiplication with
	 */

	public void multiply(Object mulValue) {
		Object pomVal = this.value;
		value = Double.parseDouble(value.toString()) * Double.parseDouble(processObject(mulValue).toString());
		checkInteger(pomVal, mulValue);
	}

	/**
	 * Method for dividing current value with given. Division with 0 is not
	 * supported
	 * 
	 * @param divValue value for division with
	 * @throws ArithmeticException if divided with 0
	 */
	public void divide(Object divValue) {
		Object pomVal = this.value;
		if (divValue.equals(0)) {
			throw new ArithmeticException("Division with 0 not possible");
		}
		value = Double.parseDouble(value.toString()) / Double.parseDouble(processObject(divValue).toString());
		checkInteger(pomVal, divValue);
	}

	/**
	 * Method for number comparison
	 * 
	 * @param withValue
	 *            object to compare with
	 * @return integer less than zero if currently stored value is smaller than
	 *         argument, an integer greater than zero if currently stored value
	 *         is larger than argument or an integer 0 if they are equal
	 */
	public int numCompare(Object withValue) {
		if (this.value.equals(null) && withValue.equals(null)) {
			return 0;
		}
		if (withValue.equals(null) && value instanceof Integer) {
			if ((Integer) value == 0) {
				return 0;
			} else {
				return 1;
			}
		}
		if (value.equals(null) && processObject(withValue) instanceof Integer) {
			if ((Integer) processObject(withValue) == 0) {
				return 0;
			} else {
				return -1;
			}
		}
		if (Double.parseDouble(value.toString()) > Double.parseDouble(processObject(withValue).toString())) {
			return 1;
		}else if(Double.parseDouble(value.toString()) < Double.parseDouble(processObject(withValue).toString())){
			return -1;
		}
		return 0;
	}

	/**
	 * Helper method for checking if both values are Integer
	 * 
	 * @param value
	 *            first value
	 * @param value2
	 *            second value
	 */
	private void checkInteger(Object value, Object value2) {
		if (value instanceof Integer && value2 instanceof Integer) {
			double d = (Double) this.value;
			this.value = (int) d;
		}
	}

	/**
	 * Helper method for determining what number is in Object
	 * 
	 * @param value
	 *            object
	 * @return object
	 */
	private Object processObject(Object value) {
		if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof String) {
			try {
				return Double.parseDouble((String) value);
			} catch (Exception e) {
				throw new RuntimeException("Given type is not allowed");
			}
		}
		throw new RuntimeException("Given type is not allowed");
	}
}
