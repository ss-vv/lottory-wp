package com.davcai.lottery.platform.client;

public interface ILotteryPlatformQueryMatcheOddsClient extends ILotteryPlatformClient{
	public abstract LotteryPlatformBaseResponse getOddsByLotteryId(String lotteryId);
}
