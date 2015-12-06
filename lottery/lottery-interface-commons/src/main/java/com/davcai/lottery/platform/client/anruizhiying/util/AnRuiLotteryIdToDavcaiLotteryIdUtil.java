package com.davcai.lottery.platform.client.anruizhiying.util;

import java.util.HashMap;
import java.util.Map;

import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingLotteryId;
import com.xhcms.lottery.lang.Constants;

public class AnRuiLotteryIdToDavcaiLotteryIdUtil {

	private static Map<String,String> map=new HashMap<String,String>();
	static{
		map.put(AnRuiZhiYingLotteryId.JZ_RQSPF,Constants.PLAY_01_ZC);
		map.put(AnRuiZhiYingLotteryId.JZ_SPF,Constants.PLAY_80_ZC);
		map.put(AnRuiZhiYingLotteryId.JZ_BF,Constants.PLAY_02_ZC);
		map.put(AnRuiZhiYingLotteryId.JZ_JQS,Constants.PLAY_03_ZC);
		map.put(AnRuiZhiYingLotteryId.JZ_BQC,Constants.PLAY_04_ZC);
		map.put(AnRuiZhiYingLotteryId.JZ_HH,Constants.PLAY_05_ZC);
		
		map.put(AnRuiZhiYingLotteryId.JL_SFC,Constants.PLAY_08_LC);
		map.put(AnRuiZhiYingLotteryId.JL_RFSF,Constants.PLAY_06_LC);
		map.put(AnRuiZhiYingLotteryId.JL_SF,Constants.PLAY_07_LC);
		map.put(AnRuiZhiYingLotteryId.JL_DXF,Constants.PLAY_09_LC);
		map.put(AnRuiZhiYingLotteryId.JL_HH,Constants.PLAY_10_LC);
	}

	public static String translate(String playId) {
		return map.get(playId);
	}
}
