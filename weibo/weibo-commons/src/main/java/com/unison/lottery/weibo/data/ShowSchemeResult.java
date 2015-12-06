package com.unison.lottery.weibo.data;

import java.io.Serializable;

/**
 * 对方案晒单后返回的结果对象
 * @author lei.li@davcai.com
 */
public class ShowSchemeResult implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	private String desc;
	
	private boolean status;
	
	private String weiboUrl;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getWeiboUrl() {
		return weiboUrl;
	}

	public void setWeiboUrl(String weiboUrl) {
		this.weiboUrl = weiboUrl;
	}
}