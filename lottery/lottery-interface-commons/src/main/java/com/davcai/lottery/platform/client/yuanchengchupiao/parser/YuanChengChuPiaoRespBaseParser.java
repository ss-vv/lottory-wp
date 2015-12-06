package com.davcai.lottery.platform.client.yuanchengchupiao.parser;

import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

public class YuanChengChuPiaoRespBaseParser implements IYuanChengChuPiaoRespParser{

	
	
	
	@Override
	public YuanChengChuPiaoResponse parseResp(String resp) {
		return new YuanChengChuPiaoResponse();
	}
	
}
