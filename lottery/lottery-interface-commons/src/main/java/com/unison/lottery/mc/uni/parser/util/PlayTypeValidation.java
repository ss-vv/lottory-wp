package com.unison.lottery.mc.uni.parser.util;

public class PlayTypeValidation {
	
	public static boolean typeIsValid(String type, String[] typeArray) {
		String[] validTypes = typeArray;
		for (String theType : validTypes) {
			if (theType.equals(type)) {
				return true;
			}
		}
		return false;
	}
}