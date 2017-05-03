package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;

/**
 * Class that represents stack like implementation combined with maps. Each
 * element is stored in map like pair key-value, every other element with same
 * key is stored in a list implemented like stack.
 * 
 * @author Marko Gudelj
 *
 */
public class ObjectMultistack {
	/** Map used in this class */
	private HashMap<String, MultistackEntry> mapa;

	/**
	 * Class that represents single node in a stack like list
	 * 
	 * @author Marko Gudelj
	 *
	 */
	private static class MultistackEntry {
		/** ValueWrapper of element in stack */
		private ValueWrapper valueWrapper;
		/** Next element in stack */
		private MultistackEntry next;

		/**
		 * Constructor for MultistackEntry
		 * 
		 * @param valueWrapper of element
		 * @param next element in stack
		 */
		public MultistackEntry(ValueWrapper valueWrapper, MultistackEntry next) {
			this.valueWrapper = valueWrapper;
			this.next = next;
		}
	}

	/**
	 * Constructor for ObjectMultistack
	 */
	public ObjectMultistack() {
		mapa = new HashMap<>();
	}

	/**
	 * Stack like implementation of push method. Adds element to the map with a
	 * key (name), if it is present adds it to the stack associated with that
	 * key
	 * 
	 * @param name key
	 * @param valueWrapper of the element
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry pomEntry;
		mapa.putIfAbsent(name, new MultistackEntry(valueWrapper, null));
		if (mapa.containsKey(name)) {
			pomEntry = mapa.get(name);
			mapa.put(name, new MultistackEntry(valueWrapper, pomEntry));
		}
	}

	/**
	 * Returns last element of the stack and removes it from the stack
	 * 
	 * 
	 * @param name key
	 * @return ValueWrapper
	 * @throws EmptyStackException if the stack is empty
	 */
	public ValueWrapper pop(String name) {
		MultistackEntry pomEntry;
		if (!mapa.containsKey(name)) {
			throw new EmptyStackException();
		}
		pomEntry = mapa.get(name);
		if (pomEntry.next != null) {
			mapa.put(name, pomEntry.next);
		} else {
			mapa.remove(name);
		}
		return pomEntry.valueWrapper;
	}

	/**
	 * Returns last element of the stack, but does not remove it
	 * 
	 * 
	 * @param name key
	 * @return ValueWrapper
	 * @throws EmptyStackException if the stack is empty
	 */
	public ValueWrapper peek(String name) {
		if (!mapa.containsKey(name)) {
			throw new EmptyStackException();
		}
		return mapa.get(name).valueWrapper;
	}

	/**
	 * Checks if the stack for the given key is empty
	 * 
	 * @param name key
	 * @return true if it is empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		return (!mapa.containsKey(name));
	}
}
