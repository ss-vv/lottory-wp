package com.xhcms.lottery.commons.data.jx11;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 测试 Digits.
 *
 * @author yangbo
 */
public class DigitsTest {

	@Test
	public void testDigitsString() {
		for (int i=1; i<16; i++){
			Digits digits = new Digits(""+i);
			assertEquals(i, digits.getBits());
		}
	}

	@Test
	public void testDigitsListOfString() {
		Digits digits = new Digits(new String[]{
				"1","2"
		});
		assertEquals(3, digits.getBits());
	}

	@Test
	public void testDigitsShort() {
		for (int i=1; i<16; i++){
			Digits digits = new Digits((short)i);
			assertEquals(i, digits.getBits());
		}
	}

	@Test
	public void testRemoveInt() {
		Digits digits = new Digits(new String[]{
				"01","02","03","04","05","06", "07", "08","09","10","11"
		});
		Digits removed = digits.remove(2);
		assertEquals(Integer.parseInt("11111111101",2), removed.getBits());
		
		removed = removed.remove(3);
		assertEquals(Integer.parseInt("11111111001",2), removed.getBits());

		removed = removed.remove(5);
		assertEquals(Integer.parseInt("11111101001",2), removed.getBits());
	}

	@Test
	public void testRemoveDigits() {
		Digits digits = new Digits(new String[]{
				"1","2","3"
		});
		Digits removed = digits.remove(new Digits((short)2));
		assertEquals(5, removed.getBits());
	}

	@Test
	public void testCountNumbers() {
		Digits digits = new Digits(new String[]{
				"1","2","3"
		});
		assertEquals(3, digits.countNumbers());
	}

	@Test
	public void testGetNumbers() {
		Digits digits = new Digits(new String[]{
				"1","2","3"
		});
		int[] numbers = digits.getNumbers();
		assertArrayEquals(new int[]{1,2,3}, numbers);
	}

}
