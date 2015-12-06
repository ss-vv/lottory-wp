package com.davcai.data.statistic;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.davcai.data.statistic.task.model.PlayOption;



/**
 * Unit test for simple App.
 */
public class PlayOptionTest {
   
	@Test
	public void testComputeOptionAppear(){
		PlayOption playOption=new PlayOption();
		playOption.setAllOptionAppearCode(0b111);
		playOption.setMinCountOfChar(1);
		Map<String, Integer> optionCodeMap=new HashMap<String, Integer>();
		optionCodeMap.put("3", 0b001);
		optionCodeMap.put("1", 0b010);
		optionCodeMap.put("0", 0b100);
		playOption.setOptionCodeMap(optionCodeMap);
		int optionAppearCode=playOption.computeOptionAppear("1");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b010);
		optionAppearCode=playOption.computeOptionAppear("3");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b001);
		optionAppearCode=playOption.computeOptionAppear("0");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b100);
		optionAppearCode=playOption.computeOptionAppear("01");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b110);
		optionAppearCode=playOption.computeOptionAppear("03");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b101);
		optionAppearCode=playOption.computeOptionAppear("10");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b110);
		optionAppearCode=playOption.computeOptionAppear("13");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b011);
		optionAppearCode=playOption.computeOptionAppear("30");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b101);
		optionAppearCode=playOption.computeOptionAppear("31");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b011);
		optionAppearCode=playOption.computeOptionAppear("310");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b111);
		optionAppearCode=playOption.computeOptionAppear("301");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b111);
		optionAppearCode=playOption.computeOptionAppear("130");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b111);
		optionAppearCode=playOption.computeOptionAppear("103");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b111);
		optionAppearCode=playOption.computeOptionAppear("013");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b111);
		optionAppearCode=playOption.computeOptionAppear("031");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b111);
		optionAppearCode=playOption.computeOptionAppear("5");
		System.out.println("optionAppearCode="+optionAppearCode);
		assertTrue(optionAppearCode==0b0);
	}
}
