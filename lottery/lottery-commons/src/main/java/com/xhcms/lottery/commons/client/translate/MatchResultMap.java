package com.xhcms.lottery.commons.client.translate;

import java.util.HashMap;
import java.util.Map;

public class MatchResultMap {

	private static Map<String, String>[] zmResultMaps;
	private static Map<String, String>[] arzyResultMaps;
	private static Map<String/*an rui play option*/,String/*dav play name BRQSPF*/> anRuiJCZQPlayOption2DavPlayName = new HashMap<String, String>();
	
	private static Map<String/*an rui play option*/,String/*dav play name DX*/> anRuiJCLQPlayOption2DavPlayName = new HashMap<String, String>();
	
	private static Map<String/*安瑞竞彩篮球让分胜负*/,String/*dav 竞彩篮球让分胜负*/> anRuiJCLQ_RFSF_SF_SFC2DavMap = new HashMap<String, String>();
	
	private static Map<String/*安瑞比分格式，10*/,String/*dav 比分格式*/> anRuiJCZQ_BF2DavMap = new HashMap<String, String>();

	static {
		anRuiJCZQPlayOption2DavPlayName.put("胜", "BRQSPF");
		anRuiJCZQPlayOption2DavPlayName.put("平", "BRQSPF");
		anRuiJCZQPlayOption2DavPlayName.put("负", "BRQSPF");
		anRuiJCZQPlayOption2DavPlayName.put("胜胜", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("平胜", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("负胜", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("负负", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("平负", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("胜负", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("胜平", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("负平", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("平平", "BQC");
		anRuiJCZQPlayOption2DavPlayName.put("00", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("11", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("22", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("33", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("平其他", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("10", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("20", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("21", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("21", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("21", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("21", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("30", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("31", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("32", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("40", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("41", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("42", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("50", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("51", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("52", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("胜其他", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("01", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("02", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("12", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("03", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("13", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("23", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("04", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("14", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("24", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("05", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("15", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("25", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("负其他", "BF");
		anRuiJCZQPlayOption2DavPlayName.put("0", "JQS");
		anRuiJCZQPlayOption2DavPlayName.put("1", "JQS");
		anRuiJCZQPlayOption2DavPlayName.put("2", "JQS");
		anRuiJCZQPlayOption2DavPlayName.put("3", "JQS");
		anRuiJCZQPlayOption2DavPlayName.put("4", "JQS");
		anRuiJCZQPlayOption2DavPlayName.put("5", "JQS");
		anRuiJCZQPlayOption2DavPlayName.put("6", "JQS");
		anRuiJCZQPlayOption2DavPlayName.put("7", "JQS");
		
		//安瑞比分转换大V比分map
		anRuiJCZQ_BF2DavMap.put("10", "1:0");
		anRuiJCZQ_BF2DavMap.put("20", "2:0");
		anRuiJCZQ_BF2DavMap.put("21", "2:1");
		anRuiJCZQ_BF2DavMap.put("30", "3:0");
		anRuiJCZQ_BF2DavMap.put("31", "3:1");
		anRuiJCZQ_BF2DavMap.put("32", "3:2");
		anRuiJCZQ_BF2DavMap.put("40", "4:0");
		anRuiJCZQ_BF2DavMap.put("41", "4:1");
		anRuiJCZQ_BF2DavMap.put("42", "4:2");
		anRuiJCZQ_BF2DavMap.put("50", "5:0");
		anRuiJCZQ_BF2DavMap.put("51", "5:1");
		anRuiJCZQ_BF2DavMap.put("52", "5:2");
		anRuiJCZQ_BF2DavMap.put("00", "0:0");
		anRuiJCZQ_BF2DavMap.put("11", "1:1");
		anRuiJCZQ_BF2DavMap.put("22", "2:2");
		anRuiJCZQ_BF2DavMap.put("33", "3:3");
		anRuiJCZQ_BF2DavMap.put("01", "0:1");
		anRuiJCZQ_BF2DavMap.put("02", "0:2");
		anRuiJCZQ_BF2DavMap.put("12", "1:2");
		anRuiJCZQ_BF2DavMap.put("03", "0:3");
		anRuiJCZQ_BF2DavMap.put("13", "1:3");
		anRuiJCZQ_BF2DavMap.put("23", "2:3");
		anRuiJCZQ_BF2DavMap.put("04", "0:4");
		anRuiJCZQ_BF2DavMap.put("14", "1:4");
		anRuiJCZQ_BF2DavMap.put("24", "2:4");
		anRuiJCZQ_BF2DavMap.put("05", "0:5");
		anRuiJCZQ_BF2DavMap.put("15", "1:5");
		anRuiJCZQ_BF2DavMap.put("25", "2:5");
		anRuiJCZQ_BF2DavMap.put("让球胜", "胜");
		anRuiJCZQ_BF2DavMap.put("让球平", "平");
		anRuiJCZQ_BF2DavMap.put("让球负", "负");
		anRuiJCZQ_BF2DavMap.put("平其他", "平其他");
		anRuiJCZQ_BF2DavMap.put("胜其他", "胜其他");
		anRuiJCZQ_BF2DavMap.put("负其他", "负其他");
		
		
		anRuiJCLQPlayOption2DavPlayName.put("让分主胜", "RFSF");
		anRuiJCLQPlayOption2DavPlayName.put("让分主负", "RFSF");
		anRuiJCLQPlayOption2DavPlayName.put("主胜", "SF");
		anRuiJCLQPlayOption2DavPlayName.put("主负", "SF");
		anRuiJCLQPlayOption2DavPlayName.put("小", "DXF");
		anRuiJCLQPlayOption2DavPlayName.put("大", "DXF");
		anRuiJCLQPlayOption2DavPlayName.put("主胜1-5", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("主胜6-10", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("主胜11-15", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("主胜16-20", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("主胜21-25", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("主胜26", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("客胜1-5", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("客胜6-10", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("客胜11-15", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("客胜16-20", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("客胜21-25", "FC");
		anRuiJCLQPlayOption2DavPlayName.put("客胜26", "FC");
		
		

		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("主胜", "主胜");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("主负", "客胜");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("让分主胜", "主胜");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("让分主负", "客胜");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("大", "大");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("小", "小");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("主胜1-5", "胜1-5分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("主胜6-10", "胜6-10分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("主胜11-15", "胜11-15分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("主胜16-20", "胜16-20分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("主胜21-25", "胜21-25分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("主胜26", "胜26分以上");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("客胜1-5", "负1-5分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("客胜6-10", "负6-10分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("客胜11-15", "负11-15分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("客胜16-20", "负16-20分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("客胜21-25", "负21-25分");
		anRuiJCLQ_RFSF_SF_SFC2DavMap.put("客胜26", "负26分以上");
		
		
		/**************大V彩自定义格式****************/
		// 竞彩足球胜平负，让球
		String[][] jz_rqspf_result = new String[][] { { "3", "胜" },
				{ "1", "平" }, { "0", "负" } };
		// 竞彩足球胜平负，不让球
		String[][] jz_brqspf_result = new String[][] { { "3", "胜" },
				{ "1", "平" }, { "0", "负" } };

		// 竞彩足球比分
		String[][] jz_bf_result = new String[][] {
				// 平：00、11、22、33、99
				{ "00", "0:0" },
				{ "11", "1:1" },
				{ "22", "2:2" },
				{ "33", "3:3" },
				{ "99", "平其他" },
				// 主胜：10、20、21、30、31、32、40、41、42、50、51、52、90
				{ "10", "1:0" }, { "20", "2:0" }, { "21", "2:1" },
				{ "30", "3:0" }, { "31", "3:1" }, { "32", "3:2" },
				{ "40", "4:0" }, { "41", "4:1" },
				{ "42", "4:2" },
				{ "50", "5:0" },
				{ "51", "5:1" },
				{ "52", "5:2" },
				{ "90", "胜其他" },
				// 客胜：01、02、12、03、13、23、04、14、24、05、15、25、09
				{ "01", "0:1" }, { "02", "0:2" }, { "12", "1:2" },
				{ "03", "0:3" }, { "13", "1:3" }, { "23", "2:3" },
				{ "04", "0:4" }, { "14", "1:4" }, { "24", "2:4" },
				{ "05", "0:5" }, { "15", "1:5" }, { "25", "2:5" },
				{ "09", "负其他" } };

		// 竞彩足球总进球数
		String[][] jz_jqs_result = new String[][] { { "0", "0" }, { "1", "1" },
				{ "2", "2" }, { "3", "3" }, { "4", "4" }, { "5", "5" },
				{ "6", "6" }, { "7", "7+" } };

		// 竞彩足球半全场, 00、01、10、11、03、30、13、31、33
		// 注意：出票赔率格式中是“胜胜”形式，和投注内容不一样。
		String[][] jz_bqc_result = new String[][] { { "00", "负-负" },
				{ "01", "负-平" }, { "10", "平-负" }, { "11", "平-平" },
				{ "03", "负-胜" }, { "30", "胜-负" }, { "13", "平-胜" },
				{ "31", "胜-平" }, { "33", "胜-胜" } };

		// 竞彩篮球让分胜负
		String[][] jl_rfsf_result = new String[][] { { "1", "主胜" },
				{ "2", "客胜" } };

		// 竞彩篮球胜负
		String[][] jl_sf_result = new String[][] { { "1", "主胜" }, { "2", "客胜" } };

		// 篮球胜分差, 主胜01、02、03、04、05、06，客胜11、12、13、14、15、16
		String[][] jl_sfc_result = new String[][] {
				// 主胜
				{ "01", "胜1-5" }, { "02", "胜6-10" }, { "03", "胜11-15" },
				{ "04", "胜16-20" }, { "05", "胜21-25" }, { "06", "胜26+" },
				// 客胜
				{ "11", "负1-5" }, { "12", "负6-10" }, { "13", "负11-15" },
				{ "14", "负16-20" }, { "15", "负21-25" }, { "16", "负26+" } };

		// 篮球大小分
		String[][] jl_dxf_result = new String[][] { { "1", "大" }, { "2", "小" } };

		// 北京单场胜负平
		String[][] bjdc_spf_result = new String[][] { { "3", "胜" },
				{ "1", "平" }, { "0", "负" } };
		// 北京单场进球数
		String[][] bjdc_jqs_result = new String[][] { { "0", "0" },
				{ "1", "1" }, { "2", "2" }, { "3", "3" }, { "4", "4" },
				{ "5", "5" }, { "6", "6" }, { "7", "7+" } };
		// 北京单场上下单双
		String[][] bjdc_sxds_result = new String[][] { { "01", "下+单" },
				{ "02", "下+双" }, { "11", "上+单" }, { "12", "上+双" } };

		// 北京单场比分
		String[][] bjdc_bf_result = new String[][] {
				// 平：00、11、22、33、99
				{ "00", "0:0" },
				{ "11", "1:1" },
				{ "22", "2:2" },
				{ "33", "3:3" },
				{ "99", "平其他" },
				// 主胜：10、20、21、30、31、32、40、41、42、50、51、52、90
				{ "10", "1:0" }, { "20", "2:0" }, { "21", "2:1" },
				{ "30", "3:0" }, { "31", "3:1" }, { "32", "3:2" },
				{ "40", "4:0" }, { "41", "4:1" },
				{ "42", "4:2" },
				{ "50", "5:0" },
				{ "51", "5:1" },
				{ "52", "5:2" },
				{ "90", "胜其他" },
				// 客胜：01、02、12、03、13、23、04、14、24、05、15、25、09
				{ "01", "0:1" }, { "02", "0:2" }, { "12", "1:2" },
				{ "03", "0:3" }, { "13", "1:3" }, { "23", "2:3" },
				{ "04", "0:4" }, { "14", "1:4" }, { "24", "2:4" },
				{ "05", "0:5" }, { "15", "1:5" }, { "25", "2:5" },
				{ "09", "负其他" } };
		// 北京单场半全场
		String[][] bjdc_bqc_result = new String[][] { { "00", "负-负" },
				{ "01", "负-平" }, { "10", "平-负" }, { "11", "平-平" },
				{ "03", "负-胜" }, { "30", "胜-负" }, { "13", "平-胜" },
				{ "31", "胜-平" }, { "33", "胜-胜" } };
		// 北京单场胜负
		String[][] bjdc_sf_result = new String[][] { { "3", "胜" }, { "0", "负" } };
		
		/**************尊傲投注格式****************/
		// 尊傲投注格式
		String[][][] zmResultMapArray = new String[PlatformBetContent.MAX_PLAY_TYPE][][];
		zmResultMapArray[1] = jz_rqspf_result;
		zmResultMapArray[80] = jz_brqspf_result;
		zmResultMapArray[2] = jz_bf_result;
		zmResultMapArray[3] = jz_jqs_result;
		zmResultMapArray[4] = jz_bqc_result;
		
		zmResultMapArray[6] = jl_rfsf_result;
		zmResultMapArray[7] = jl_sf_result;
		zmResultMapArray[8] = jl_sfc_result;
		zmResultMapArray[9] = jl_dxf_result;
		
		zmResultMapArray[91] = bjdc_spf_result;
		zmResultMapArray[92] = bjdc_jqs_result;
		zmResultMapArray[93] = bjdc_sxds_result;
		zmResultMapArray[94] = bjdc_bf_result;
		zmResultMapArray[95] = bjdc_bqc_result;
		zmResultMapArray[96] = bjdc_sf_result;
		
		zmResultMaps = initZMResultMaps(zmResultMapArray);
		
		/**************安瑞智赢投注格式****************/
		String[][] jz_arzy_rqspf_result = new String[][] { { "3", "让球胜" },
				{ "1", "让球平" }, { "0", "让球负" } };
		String[][] jz_arzy_bqc_result = new String[][] { { "00", "负负" },
				{ "01", "负平" }, { "10", "平负" }, { "11", "平平" },
				{ "03", "负胜" }, { "30", "胜负" }, { "13", "平胜" },
				{ "31", "胜平" }, { "33", "胜胜" } };
		String[][] jl_arzy_sf_result = new String[][] { { "1", "主胜" }, { "2", "主负" } };
		String[][] jl_arzy_rfsf_result = new String[][] { { "1", "让分主胜" },
				{ "2", "让分主负" } };
		String[][] jl_arzy_sfc_result = new String[][] {
				// 主胜
				{ "01", "主胜1-5" }, { "02", "主胜6-10" }, { "03", "主胜11-15" },
				{ "04", "主胜16-20" }, { "05", "主胜21-25" }, { "06", "主胜26+" },
				// 客胜
				{ "11", "客胜1-5" }, { "12", "客胜6-10" }, { "13", "客胜11-15" },
				{ "14", "客胜16-20" }, { "15", "客胜21-25" }, { "16", "客胜26+" } };
		
		/**暂时并未定义“安瑞智赢”北单玩法投注选项值，需要时另外补充**/
		
		String[][][] arzyResultMapArray = new String[PlatformBetContent.MAX_PLAY_TYPE][][];
		//足球
		arzyResultMapArray[1] = jz_arzy_rqspf_result;
		arzyResultMapArray[4] = jz_arzy_bqc_result;
		arzyResultMapArray[80] = jz_brqspf_result;
		arzyResultMapArray[2] = jz_bf_result;
		arzyResultMapArray[3] = jz_jqs_result;
		//篮球
		arzyResultMapArray[6] = jl_arzy_rfsf_result;
		arzyResultMapArray[7] = jl_arzy_sf_result;
		arzyResultMapArray[8] = jl_arzy_sfc_result;
		arzyResultMapArray[9] = jl_dxf_result;
		//北单
		arzyResultMapArray[91] = bjdc_spf_result;
		arzyResultMapArray[92] = bjdc_jqs_result;
		arzyResultMapArray[93] = bjdc_sxds_result;
		arzyResultMapArray[94] = bjdc_bf_result;
		arzyResultMapArray[95] = bjdc_bqc_result;
		arzyResultMapArray[96] = bjdc_sf_result;
		
		arzyResultMaps = initZMResultMaps(arzyResultMapArray);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, String>[] initZMResultMaps(String[][][] zmResultMapArray) {
		Map[] resultMaps = new Map[zmResultMapArray.length];
		int iType = 0;
		for (String[][] resultMapForOneType : zmResultMapArray) {
			if (resultMapForOneType != null) {
				resultMaps[iType] = new HashMap<String, String>();
				for (int iEntry = 0; iEntry < resultMapForOneType.length; iEntry++) {
					resultMaps[iType].put(resultMapForOneType[iEntry][0],
							resultMapForOneType[iEntry][1]);
				}
			}
			iType++;
		}
		return resultMaps;
	}

	public static Map<String, String>[] getZmResultMaps() {
		return zmResultMaps;
	}

	public static Map<String, String>[] getArzyResultMaps() {
		return arzyResultMaps;
	}
	
	public static Map<String, String> getAnRuiJCZQ2DavPlayOptions() {
		return anRuiJCZQPlayOption2DavPlayName;
	}
	public static Map<String, String> getAnRuiJCZQBF2DavMap() {
		return anRuiJCZQ_BF2DavMap;
	}
	public static Map<String, String> getAnRuiJCLQ2DavPlayOptions() {
		return anRuiJCLQPlayOption2DavPlayName;
	}
	public static Map<String, String> getAnRuiJCLQ_RFSF_SF_SFC2DavMap() {
		return anRuiJCLQ_RFSF_SF_SFC2DavMap;
	}
	
 }