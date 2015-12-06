package com.xhcms.lottery.commons.persist.service;

import java.util.HashMap;
import java.util.Map;

public class BetOptionUtil {

	public static Map<String, String> JZ_BF_OPTION = new HashMap<String, String>();
	public static Map<String, String> JZ_BQC_OPTION = new HashMap<String, String>();
	
	public static Map<String, String> JL_SFC_OPTION = new HashMap<String, String>();
	
	static {
		//竞彩足球比分选项
		JZ_BF_OPTION.put("10","1:0"); JZ_BF_OPTION.put("20","2:0");
		JZ_BF_OPTION.put("21","2:1"); JZ_BF_OPTION.put("30","3:0");
		JZ_BF_OPTION.put("31","3:1"); JZ_BF_OPTION.put("32","3:2");
		JZ_BF_OPTION.put("40","4:0"); JZ_BF_OPTION.put("41","4:1");
		JZ_BF_OPTION.put("42","4:2"); JZ_BF_OPTION.put("43","4:3");
		JZ_BF_OPTION.put("50","5:0"); JZ_BF_OPTION.put("51","5:1");
		JZ_BF_OPTION.put("52","5:2"); JZ_BF_OPTION.put("90","胜其他");
		JZ_BF_OPTION.put("00","0:0"); JZ_BF_OPTION.put("11","1:1");
		JZ_BF_OPTION.put("22","2:2"); JZ_BF_OPTION.put("33","3:3");
		JZ_BF_OPTION.put("99","平其他"); JZ_BF_OPTION.put("01","0:1");
		JZ_BF_OPTION.put("02","0:2"); JZ_BF_OPTION.put("12","1:2");
		JZ_BF_OPTION.put("03","0:3"); JZ_BF_OPTION.put("13","1:3");
		JZ_BF_OPTION.put("23","2:3"); JZ_BF_OPTION.put("04","0:4");
		JZ_BF_OPTION.put("14","1:4"); JZ_BF_OPTION.put("24","2:4");
		JZ_BF_OPTION.put("34","3:4"); JZ_BF_OPTION.put("05","0:5");
		JZ_BF_OPTION.put("15","1:5"); JZ_BF_OPTION.put("25","2:5");
		JZ_BF_OPTION.put("09","负其他");
		
		//竞彩足球半全场选项
		JZ_BQC_OPTION.put("33", "胜胜"); JZ_BQC_OPTION.put("31", "胜平");
		JZ_BQC_OPTION.put("30", "胜负"); JZ_BQC_OPTION.put("13", "平胜");
		JZ_BQC_OPTION.put("11", "平平"); JZ_BQC_OPTION.put("10", "平负");
		JZ_BQC_OPTION.put("03", "负胜"); JZ_BQC_OPTION.put("01", "负平");
		JZ_BQC_OPTION.put("00", "负负");
		
		//竞彩篮球胜分差
		JL_SFC_OPTION.put("01", "主胜1-5");
		JL_SFC_OPTION.put("02", "主胜6-10");
		JL_SFC_OPTION.put("03", "主胜11-15"); 
		JL_SFC_OPTION.put("04", "主胜16-20");
		JL_SFC_OPTION.put("05", "主胜21-25");
		JL_SFC_OPTION.put("06", "主胜26+");
		JL_SFC_OPTION.put("11", "客胜1-5");
		JL_SFC_OPTION.put("12", "客胜6-10");
		JL_SFC_OPTION.put("13", "客胜11-15");
		JL_SFC_OPTION.put("14", "客胜16-20");
		JL_SFC_OPTION.put("15", "客胜21-25");
		JL_SFC_OPTION.put("16", "客胜26+");
	}
	
	public static String getJZ_BF_OPTION(String key) {
		return JZ_BF_OPTION.get(key);
	}
	
	public static String getJZ_BQC_OPTION(String key) {
		return JZ_BQC_OPTION.get(key);
	}
	
	public static String getJL_SFC_OPTION(String key) {
		return JL_SFC_OPTION.get(key);
	}
}