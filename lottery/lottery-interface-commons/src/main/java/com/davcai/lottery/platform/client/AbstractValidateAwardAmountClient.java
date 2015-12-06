package com.davcai.lottery.platform.client;

import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

public  abstract class AbstractValidateAwardAmountClient implements ILotteryPlatformClient{
	public  abstract YuanChengChuPiaoResponse validateAmount(String orderId , String awardAmount );
}
