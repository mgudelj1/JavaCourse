package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * Demonstration class
 * 
 * @author Marko Gudelj
 *
 */
public class MedianDemo1 {
	/**
	 * Entry point
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));
		Optional<Integer> result = likeMedian.get();
		System.out.println(result);
		for (Integer elem : likeMedian) {
			System.out.println(elem);
		}
	}
}
