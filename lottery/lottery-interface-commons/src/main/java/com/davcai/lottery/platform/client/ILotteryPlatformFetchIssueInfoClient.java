package com.davcai.lottery.platform.client;

import com.xhcms.lottery.lang.PlayType;


public interface ILotteryPlatformFetchIssueInfoClient extends ILotteryPlatformClient{
	public abstract LotteryPlatformBaseResponse postByPlayType(PlayType playType);
}
