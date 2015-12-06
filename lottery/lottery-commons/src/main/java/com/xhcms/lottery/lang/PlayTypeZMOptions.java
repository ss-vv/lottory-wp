package com.xhcms.lottery.lang;

import java.util.Hashtable;
import java.util.Map;

/**
 * @desc 玩法类型对应选项值定义
 * @createTime 2014-7-28
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class PlayTypeZMOptions {

	// key为PlayType，值为 options（投注选项）且必须和《中民接口文档》中的赔率顺序一致。
	public static final Map<PlayType, String> playTypeZMOptionsMap = new Hashtable<PlayType, String>();
	static {
		// 北京单场，让球胜平负
		playTypeZMOptionsMap.put(PlayType.BJDC_SPF, "3,1,0");
		// 北京单场，总进球数
		playTypeZMOptionsMap.put(PlayType.BJDC_JQS, "0,1,2,3,4,5,6,7");
		// 北京单场，比分
		playTypeZMOptionsMap.put(PlayType.BJDC_BF,
				"10,20,21,30,31,32,40,41,42,90," + "00,11,22,33,99,"
						+ "01,02,12,03,13,23,04,14,24,09");
		// 北京单场，半全场
		playTypeZMOptionsMap.put(PlayType.BJDC_BQC,
				"33,31,30,13,11,10,03,01,00");
		// 北京单场，上下单双11,12,01,02
		playTypeZMOptionsMap.put(PlayType.BJDC_SXDS,
				"11,12,01,02");
		// 北京单场，胜负
		playTypeZMOptionsMap.put(PlayType.BJDC_SF,
				"3,0");
	}

}