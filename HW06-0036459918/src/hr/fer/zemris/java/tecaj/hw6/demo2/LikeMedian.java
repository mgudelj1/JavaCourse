package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Class for determining median element of the input elements
 * 
 * @author Marko Gudelj
 *
 * @param <T>
 *            Type of the elements
 */
public class LikeMedian<T> implements Iterable<T> {
	/** List in which are stored elements */
	List<T> list;

	/**
	 * Constructor for the LikeMedian
	 */
	public LikeMedian() {
		list = new ArrayList<>();
	}

	/**
	 * Method for adding element o to the list
	 * 
	 * @param o
	 *            element to add
	 */
	public void add(T o) {
		list.add(o);
	}

	/**
	 * Method to get median element of the input elements
	 * 
	 * @return median element
	 */
	public Optional<T> get() {
		List<T> pomList = new ArrayList<>();
		pomList.addAll(list);
		pomList.sort(null);

		if (pomList.size() == 0) {
			return Optional.empty();
		}
		if (pomList.size() % 2 == 0) {
			return Optional.of(pomList.get(pomList.size() / 2 - 1));
		} else {
			return Optional.of(pomList.get(pomList.size() / 2));
		}
	}

	/**
	 * Implementation of iterator for this class
	 */
	
	
	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = list.iterator();
			return it;
	}

}
