package com.xhcms.lottery.dc.platform.parser;

import java.util.List;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

public interface IssueInfoStoreParser {
	/**
	 * 将多平台响应对象转换成期信息
	 * @param resp
	 * @param platformId
	 * @param lotteryId
	 * @return
	 */
	List<IssueInfo> parseToIssueInfo(LotteryPlatformBaseResponse resp,String platformId,PlayType playType);
	
	void checkIsExistsIssueInfo(List<IssueInfo> list);
}
