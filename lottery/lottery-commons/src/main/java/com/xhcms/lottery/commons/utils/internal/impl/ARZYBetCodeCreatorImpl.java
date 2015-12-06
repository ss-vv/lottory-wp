package com.xhcms.lottery.commons.utils.internal.impl;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.exception.JXRuntimeException;
import com.xhcms.lottery.commons.utils.internal.PlatformBetCodeCreator;

/**
 * 大V彩自定义的投注格式内容；以便转换给“安瑞智赢出票接口”使用
 * 
 * @author lei.li@davcai.com
 */
public class ARZYBetCodeCreatorImpl extends PlatformBetCodeCreator {

	@Override
	public String getPlatformCode(BetMatch betMatch) {
		if(null != betMatch && null != betMatch.getJcOfficialMatchId() &&
				betMatch.getJcOfficialMatchId() > 0) {
			//Long jcOfficialMatchId = betMatch.getJcOfficialMatchId();
			return betMatch.getCode();
		}
		throw new JXRuntimeException("无法获取投注赛事信息！");
	}
}