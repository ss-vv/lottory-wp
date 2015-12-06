package com.davcai.lottery.platform.client.yuanchengchupiao;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davcai.lottery.platform.client.AbstractClientSupport;
import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryGetInfoListResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.MessageIdGenerator;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.YjBase64Util;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.YuanChengChuPiaoUrlUtil;

public abstract class AbstractYuanChengChuPiaoClientSupport extends AbstractClientSupport {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String contentKey;
	private String apiId;
	private String secret;
	
	@Override
	protected YuanChengChuPiaoResponse parseResponse(String responseStr) {
		
			responseStr = YjBase64Util.decode(responseStr.replace("_data=", ""), contentKey);
			logger.debug("响应原文：{}",responseStr);
			return parseOrignalResponse(responseStr);
	}
	
	protected abstract YuanChengChuPiaoResponse parseOrignalResponse(String orignalResponse);
	
	@Override
	protected String makeFinalParams(Map<String, Object> params) {
		return YuanChengChuPiaoUrlUtil.makeFinalParamStringWithSign(getApiId(),getMessageId(),getTimestamp(),params,getSecret(), contentKey);
	}

	private String getSecret() {
		return secret;
	}

	private String getTimestamp() {
		//Unix时间戳为 10位
		return Long.toString(new Date().getTime()/1000);
	}

	private String getMessageId() {
		
		return MessageIdGenerator.generateId(apiId);
	}

	private String getApiId() {
		return apiId;
	}

	public void setApiId(String appId) {
		this.apiId = appId;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getContentKey() {
		return contentKey;
	}

	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
}
