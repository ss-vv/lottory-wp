/**
 * 
 */
package com.xhcms.lottery.lang;

import java.util.HashMap;
import java.util.Map;

/**
 * @author langhsu
 * 
 */
public class EnumMap {
	private static Map<String, String> zcOptMap = new HashMap<String, String>();
	private static Map<String, String> lcOptMap = new HashMap<String, String>();
	
	// 足球胜平负
	private static Map<String, Integer> zc_01 = new HashMap<String, Integer>();
	// 足球比分
	private static Map<String, Integer> zc_02 = new HashMap<String, Integer>();
	// 足球总进球
	private static Map<String, Integer> zc_03 = new HashMap<String, Integer>();
	// 足球半全
	private static Map<String, Integer> zc_04 = new HashMap<String, Integer>();
	
	static {
		initZCOpts();
		initLCOpts();
		initZC01();
		initZC02();
		initZC03();
		initZC04();
	}

	private static void initZC01() {
		zc_01.put("胜", 1);
		zc_01.put("平", 2);
		zc_01.put("负", 3);
	}
	
	private static void initZC02() {
		zc_02.put("1:0", 1);
		zc_02.put("2:0", 2);
		zc_02.put("2:1", 3);
		zc_02.put("3:0", 4);
		zc_02.put("3:1", 5);
		zc_02.put("3:2", 6);
		zc_02.put("4:0", 7);
		zc_02.put("4:1", 8);
		zc_02.put("4:2", 9);
		zc_02.put("5:0", 10);
		zc_02.put("5:1", 11);
		zc_02.put("5:2", 12);
		zc_02.put("胜其他", 13);
		
		zc_02.put("0:0", 14);
		zc_02.put("1:1", 15);
		zc_02.put("2:2", 16);
		zc_02.put("3:3", 17);
		zc_02.put("平其他", 18);
		
		zc_02.put("0:1", 19);
		zc_02.put("0:2", 20);
		zc_02.put("1:2", 21);
		zc_02.put("0:3", 22);
		zc_02.put("1:3", 23);
		zc_02.put("2:3", 24);
		zc_02.put("0:4", 25);
		zc_02.put("1:4", 26);
		zc_02.put("2:4", 27);
		zc_02.put("0:5", 28);
		zc_02.put("1:5", 29);
		zc_02.put("2:5", 30);
		zc_02.put("负其他", 31);
	}

	private static void initZC03() {
		zc_03.put("0", 1);
		zc_03.put("1", 2);
		zc_03.put("2", 3);
		zc_03.put("3", 4);
		zc_03.put("4", 5);
		zc_03.put("5", 6);
		zc_03.put("6", 7);
		zc_03.put("7+", 8);
	}

	private static void initZC04() {
		zc_04.put("胜胜", 1);
		zc_04.put("胜平", 2);
		zc_04.put("胜负", 3);
		zc_04.put("平胜", 4);
		zc_04.put("平平", 5);
		zc_04.put("平负", 6);
		zc_04.put("负胜", 7);
		zc_04.put("负平", 8);
		zc_04.put("负负", 9);
	}

	private static void initZCOpts() {
		zcOptMap.put("胜", "3");
		zcOptMap.put("平", "1");
		zcOptMap.put("负", "0");

		zcOptMap.put("胜胜", "33");
		zcOptMap.put("胜平", "31");
		zcOptMap.put("胜负", "30");

		zcOptMap.put("平胜", "13");
		zcOptMap.put("平平", "11");
		zcOptMap.put("平负", "10");

		zcOptMap.put("负胜", "03");
		zcOptMap.put("负平", "01");
		zcOptMap.put("负负", "00");
		
		zcOptMap.put("1:0", "10");
		zcOptMap.put("2:0", "20");
		zcOptMap.put("2:1", "21");
		zcOptMap.put("3:0", "30");
		zcOptMap.put("3:1", "31");
		zcOptMap.put("3:2", "32");
		zcOptMap.put("4:0", "40");
		zcOptMap.put("4:1", "41");
		zcOptMap.put("4:2", "42");
		zcOptMap.put("5:0", "50");
		zcOptMap.put("5:1", "51");
		zcOptMap.put("5:2", "52");
		zcOptMap.put("胜其他", "90");
		
		zcOptMap.put("0:0", "00");
		zcOptMap.put("1:1", "11");
		zcOptMap.put("2:2", "22");
		zcOptMap.put("3:3", "33");
		zcOptMap.put("平其他", "99");
		
		zcOptMap.put("0:1", "01");
		zcOptMap.put("0:2", "02");
		zcOptMap.put("1:2", "12");
		zcOptMap.put("0:3", "03");
		zcOptMap.put("1:3", "13");
		zcOptMap.put("2:3", "23");
		zcOptMap.put("0:4", "04");
		zcOptMap.put("1:4", "14");
		zcOptMap.put("2:4", "24");
		zcOptMap.put("0:5", "05");
		zcOptMap.put("1:5", "15");
		zcOptMap.put("2:5", "25");
		zcOptMap.put("负其他", "09");
	}
	
	private static void initLCOpts() {
		lcOptMap.put("主胜", "1");
		lcOptMap.put("主负", "2");
		
		lcOptMap.put("让分主胜", "1");
		lcOptMap.put("让分主负", "2");
		
		lcOptMap.put("大", "1");
		lcOptMap.put("小", "2");
		
		lcOptMap.put("主胜1-5", "01");
		lcOptMap.put("主胜6-10", "02");
		lcOptMap.put("主胜11-15", "03");
		lcOptMap.put("主胜16-20", "04");
		lcOptMap.put("主胜21-25", "05");
		lcOptMap.put("主胜26+", "06");
		
		lcOptMap.put("客胜1-5", "11");
		lcOptMap.put("客胜6-10", "12");
		lcOptMap.put("客胜11-15", "13");
		lcOptMap.put("客胜16-20", "14");
		lcOptMap.put("客胜21-25", "15");
		lcOptMap.put("客胜26+", "16");
	}
	
	public static String getZCOptKey(String desc) {
		if (desc == null || desc == "") {
			return "";
		}
		return zcOptMap.get(desc);
	}
	
	public static String getLCOptKey(String desc) {
		if (desc == null || desc == "") {
			return "";
		}
		return lcOptMap.get(desc);
	}
	
	public static Map<String, Integer> getZC01Map(){
		return zc_01;
	}
	
	public static Map<String, Integer> getZC02Map() {
		return zc_02;
	}
	
	public static Map<String, Integer> getZC03Map(){
		return zc_03;
	}
	
	public static Map<String, Integer> getZC04Map(){
		return zc_04;
	}

}
