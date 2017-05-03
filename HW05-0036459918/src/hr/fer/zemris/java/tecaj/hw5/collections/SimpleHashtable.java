package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents hash table that contains pairs of key and value.
 * Parameter K is type of the key, V is type of the value
 * 
 * @author Marko
 *
 * @param <K>
 *            type of the key
 * @param <V>
 *            type of the value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	/** Field of references to slots of the table */
	private TableEntry<K, V>[] table;
	/** Size of the table */
	private int size;
	/** Variable for counting modifications made to the collection */
	private int modificationCount;
	/**
	 * Nested class that represents a entry in a slot of the table. Each entry
	 * is made as pare of key and a value. Slot of the table is made as a list
	 * of entries.
	 * 
	 * @param <K>
	 * @param <V>
	 */
	public static class TableEntry<K, V> {
		/** Key of a pair */
		private K key;
		/** Value of a pair */
		private V value;
		/** Reference to the next entry in a slot */
		private TableEntry<K, V> next;

		/**
		 * Getter for the key
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Getter for the value
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for the value
		 * 
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * toString method for this class
		 * 
		 * @return string
		 */
		public String toString() {
			String string = this.key + "=" + this.value;
			return string;
		}
	}

	/**
	 * Default Constructor for SimpleHashtable. Creates a table with 16 slots.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[]) new TableEntry[16];
	}

	/**
	 * Constructor for SimpleHashtable. Creates a table with capacity slots
	 * 
	 * @param capacity
	 *            of table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}
		while (true) {
			if (capacity % 2 == 0) {
				break;
			}
			capacity++;
		}
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}

	/**
	 * Method for inserting Entry in table. Method chooses slot in a table using
	 * hashcode(). Method makes a list for each slot in the table and new
	 * entries are added at the end of the list. If there is exact key already
	 * in the list, its value is updated.
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key of the pair can not be null");
		}
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> Entry = new TableEntry<K, V>();
		Entry.key = key;
		Entry.value = value;
		Entry.next = null;

		if (table[slot] == null) {
			table[slot] = Entry;
		} else {
			TableEntry<K, V> pom = table[slot];
			while (pom.next != null) {
				if (pom.key.equals(key)) {
					pom.value = value;
					return;
				}
				pom = pom.next;
			}
			pom.next = Entry;
		}
		modificationCount++;
		size++;
		float full = 0;
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				full++;
			}
		}
		if ((full / table.length) >= 0.75) {
			resize();
		}
	}
/**
 * Method for resizing this hashTable
 * If table becomes more than 75% full,
 * this method doubles it size
 */
	@SuppressWarnings("unchecked")
	private void resize(){
		modificationCount++;
		int oldLength = table.length;
		TableEntry<K,V> pom;
		TableEntry<K,V>[] subTable = (TableEntry<K, V>[]) new TableEntry[2*oldLength];
		for(int i=0; i< oldLength; i++){
			if(table[i] == null){
				continue;
			}
			pom = table[i];
			int slot = Math.abs(pom.key.hashCode()) % (2*oldLength);
			subTable[slot] = pom;
		}
		this.table = subTable;
	}
	/**
	 * Method that for a key of pair returns its value. If key is null returns
	 * null, if there is no such key in a list also returns null.Values can also
	 * be null.
	 * 
	 * @param key
	 * @return value of pair
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		if (searchForKey(key) == null) {
			return null;
		}
		return searchForKey(key).value;
	}

	/**
	 * Returns number of the pairs
	 * stored in the table
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}

	/**
	 * Method determines does the table contains given key. If it does returns
	 * true, else false.
	 * 
	 * @param key
	 * @return true if contains, else false
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		if (searchForKey(key) == null) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method for searching for a key
	 * 
	 * @param key
	 * @return entry in table
	 */
	private TableEntry<K, V> searchForKey(Object key) {
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> pom = table[slot];

		while (!pom.key.equals(key) && pom.next != null) {
			pom = pom.next;
		}
		if (pom.next == null && !pom.key.equals(key)) {
			return null;
		}
		return pom;
	}

	/**
	 * * Method determines does the table contains given value in any of its
	 * slots. If it does contain returns true, else false.
	 * 
	 * @param value
	 * @return true if contains, else false
	 */
	public boolean containsValue(Object value) {
		TableEntry<K, V> pom;

		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				continue;
			}
			pom = table[i];
			while (!pom.value.equals(value) && pom.next != null) {
				pom = pom.next;
			}
			if (pom.value.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method removes entry from the list that has the given key. If there is no
	 * such entry, does nothing. If it is given null does nothing.
	 * 
	 * @param key
	 *            of pair
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> pom = table[slot];
		TableEntry<K, V> searched;

		searched = searchForKey(key);
		if (searched == null) {
			return;
		}
		modificationCount++;
		if (pom == searched) {
			table[slot] = searched.next;
			return;
		}
		while (!pom.next.equals(searched)) {
			pom = pom.next;
		}
		pom = searched.next;
		size--;
	}

	/**
	 * If all slots are empty returns false, else returns true
	 * 
	 * @return true if empty, else false
	 */
	public boolean isEmpty() {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns String representation of the table
	 * 
	 * @return string
	 */
	public String toString() {
		TableEntry<K, V> pom;
		StringBuilder string = new StringBuilder();
		string.append("[");
		boolean firstAppended = false;

		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				continue;
			}
			pom = table[i];
			while (pom != null) {
				if (firstAppended == false) {
					string.append(pom.toString());
					firstAppended = true;
					pom = pom.next;
					continue;
				}
				string.append(", " + pom.toString());
				pom = pom.next;
			}
		}
		string.append("]");
		return string.toString();
	}
	/**
	 * Clear all elements from the table
	 */
	public void clear(){
		for(int i=0 ; i < table.length; i++){
			table[i] = null;
		}
	}
	/**
	 * Factory method for Iterator
	 * @return new IteratorImp
	 */
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
/**
 * Iterator class that has standard iterator
 * methods : next(),hasNext(),remove().
 *
 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/** Number of the elements left in the iterator */
		private int left = size;
		/** Last element that is returned */
		private TableEntry<K, V> current;
		/** Last element that will be returned */
		private TableEntry<K, V> currentRemove;
		/** Has remove been called? */
		private boolean removeCalled = true;
		/** Variable for counting modifications made to the collection */
		private int modCntr = modificationCount;
		/**
		 * Method returns true if there is next element
		 * 
		 * @return true if there is next element, else otherwise
		 */
		public boolean hasNext() {
			if(modCntr != modificationCount){
				throw new ConcurrentModificationException();
			}
			if (left > 0) {
				return true;
			}
			return false;
		}

		/**
		 * Every time it is called returns next
		 * element of the hash table
		 * @return TableEntry 
		 */
		public SimpleHashtable.TableEntry<K,V> next() {
			if(modCntr != modificationCount){
				throw new ConcurrentModificationException();
			}
			if (left == 0) {
				throw new NoSuchElementException("There is no more elements in table");
			}
			removeCalled = false;
			if(current == null){
				current = currentRemove;
				return current;
			}
			if ((size - left) == 0) {
				for (int i = 0; i < table.length; i++) {
					if (table[i] == null) {
						continue;
					}
					current = table[i];
					left--;
					return current;
				}
			}
			if (current.next != null) {
				current = current.next;
				left--;
				return current;
			}
			int slot = Math.abs(current.key.hashCode()) % table.length;
			for (int i = slot + 1; i < table.length; i++) {
				if (table[i] == null) {
					continue;
				}
				current = table[i];
				left--;
				return current;
			}
			return null;
		}

		/**
		 * Removes from the underlying collection the last element returned by
		 * this iterator . This method can be called only once per call to
		 * next().
		 */
		public void remove() {
			if(modCntr != modificationCount){
				throw new ConcurrentModificationException();
			}
			if (removeCalled == true) {
				throw new IllegalStateException("Remove method can only be called once for each next()");
			}
			modCntr++;
			currentRemove = this.next();
			SimpleHashtable.this.remove(current.key);
			current = null;
			removeCalled = true;
		}

	}
	
}
