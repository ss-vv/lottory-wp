package com.xhcms.lottery.pb.model;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.pb.util.MD5Util;

public class VerificationModel {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String userName;
	private String key;
	private String contentXml;
	private String signature;

	public VerificationModel(String partnerId, String contentXml, Map<String, String> map) {
		this.userName = partnerId;
		this.key = map.get(Constants.PARTNER_KEYS.get(partnerId));
		this.contentXml = contentXml;
		this.signature = map.get(Constants.SIGNATURE);
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setContentXml(String contentXml) {
		this.contentXml = contentXml;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean verify() {
		String signatureContent = this.userName + this.key + this.contentXml.trim();
		String sig = MD5Util.md5(signatureContent);
		try {
			logger.info("被签名的响应消息体长度:{}", signatureContent.getBytes("iso-8859-1").length);
			logger.info("来彩系统生成签名:{}", sig);
			logger.info("响应消息头签名:{}", this.signature);
		} catch (UnsupportedEncodingException e) {
			logger.error("计算消息体长度getBytes('iso-8859-1')出错", e);
		}
		return this.signature.equals(sig);
	}
}
