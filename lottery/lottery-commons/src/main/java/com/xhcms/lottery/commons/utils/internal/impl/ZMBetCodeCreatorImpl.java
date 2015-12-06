package com.xhcms.lottery.commons.utils.internal.impl;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.utils.internal.PlatformBetCodeCreator;

/**
 * 大V彩自定义的投注格式内容；以便转换给“尊傲出票接口”使用
 * 
 * @author lei.li@davcai.com
 */
public class ZMBetCodeCreatorImpl extends PlatformBetCodeCreator {

	@Override
	public String getPlatformCode(BetMatch betMatch) {
		return betMatch.getCode();
	}

}