package hr.fer.zemris.java.cstr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Class CStringTests is examination of class CString.
 *
 */
@SuppressWarnings("javadoc")
public class CStringTests {

	@Test
	public void testCreate() {
		CString string = new CString(String.valueOf("Antonio").toCharArray(), 2, 4);
		assertEquals("Invalid string.", "toni", string.toString());
		assertEquals("Invalid length.", 4, string.length());
		assertEquals("Invalid character.", 'o', string.charAt(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInputConstructor() {
		// must throw!
		new CString(null, 1, 1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testMargins() {
		// must throw!
		new CString(String.valueOf("Rijeka").toCharArray(), 2, 6);
	}

	@Test
	public void testStartEnd() {
		CString string = new CString(String.valueOf("Trči jelen po polju").toCharArray(), 0, 19);
		assertEquals("Expected Trči jelen po polju", "Trči jelen po polju", string.toString());
		assertEquals("Expected 19.", 19, string.length());

		assertEquals("1 'true'", true, string.startsWith(new CString(String.valueOf("Trči").toCharArray(), 0, 4)));
		assertEquals("2 'false'", false, string.startsWith(new CString(String.valueOf("Hoda").toCharArray(), 0, 4)));
		assertEquals("3 'true'", true, string.endsWith(new CString(String.valueOf("polju").toCharArray(), 0, 5)));
		assertEquals("4 'true'", true, string.endsWith(new CString(String.valueOf("olju").toCharArray(), 0, 4)));
	}

	@Test
	public void testLeftandRight() {
		CString string = new CString(String.valueOf("Trči jelen po polju").toCharArray(), 0, 19);

		assertEquals("1 Trč", "Trč", string.left(3).toString());
		assertEquals("2 Štefica", "Trči jelen", string.left(10).toString());
		assertEquals("3 T", "", string.left(0).toString());
		assertEquals("4 ju", "ju", string.right(2).toString());
		assertEquals("5 polju", "polju", string.right(5).toString());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInvalidIndex() {
		// must throw!
		new CString(String.valueOf("Trči jelen po polju").toCharArray(), 0, 23).left(24);
	}

	@Test
	public void testContains() {
		CString string = new CString(String.valueOf(" Trči jelen po polju  ").toCharArray());

		CString proba1 = CString.fromString("Trči");
		CString proba2 = CString.fromString("po");
		CString proba3 = CString.fromString("ol");
		CString proba4 = CString.fromString("eli");

		assertEquals("1 true", true, string.contains(proba1));
		assertEquals("2 true", true, string.contains(proba2));
		assertEquals("3 true", true, string.contains(proba3));
		assertEquals("4 false", false, string.contains(proba4));

	}

	@Test
	public void testSubstring() {
		CString string = CString.fromString(" Trči jelen po polju ");

		assertEquals("Should be the same", 0, " Trči".compareTo(string.substring(0, 5).toString()));
		assertEquals("Should be the same", 0, "jelen ".compareTo(string.substring(6, 12).toString()));
	}

	@Test
	public void testAdd() {
		CString string = CString.fromString(" Trči jelen po polju ");
		CString string2 = CString.fromString("nosi papagaja");

		assertEquals("Pjesmica", 0, " Trči jelen po polju nosi papagaja".compareTo(string.add(string2).toString()));
	}

	@Test
	public void testReplaceAllChar() {
		CString string1 = CString.fromString(" Trči jelen po polju ");
		CString string2 = string1.replaceAll('p', 'd');
		CString string3 = string2.replaceAll('o', 'r');

		assertEquals(" Trči jelen do dolju ", string2.toString());
		assertEquals(" Trči jelen dr drlju ", string3.toString());
	}

	@Test
	public void testReplaceAllString() {
		CString string1 = CString.fromString("zeko pjesmica, bio jednom jedan mali zeko kojeg nitko"
				+ "nije volio. zeko je bio jako usamljena životinjica. Dosta o zekoslavu");
		CString result1 = string1.replaceAll(CString.fromString("zeko"), CString.fromString("aligator"));
		CString result2 = string1.replaceAll(CString.fromString("zeko"), CString.fromString("vuk"));
		CString compare1 = CString.fromString("aligator pjesmica, bio jednom jedan mali aligator kojeg nitko"
				+ "nije volio. aligator je bio jako usamljena životinjica. Dosta o aligatorslavu");
		CString compare2 = CString.fromString("vuk pjesmica, bio jednom jedan mali vuk kojeg nitko"
				+ "nije volio. vuk je bio jako usamljena životinjica. Dosta o vukslavu");
		assertEquals("zeko->aligator", 0, result1.toString().compareTo(compare1.toString()));
		assertEquals("zeko->vuk", 0, result2.toString().compareTo(compare2.toString()));
	}

}