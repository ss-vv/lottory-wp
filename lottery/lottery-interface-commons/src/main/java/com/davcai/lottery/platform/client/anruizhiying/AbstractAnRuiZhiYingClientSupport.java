package com.davcai.lottery.platform.client.anruizhiying;

import java.util.Map;

import com.davcai.lottery.platform.client.AbstractClientSupport;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingUrlUtil;

public abstract class AbstractAnRuiZhiYingClientSupport extends AbstractClientSupport {
	
	private String channelId;//由出票平台分配的channelId
	private String key;//由出票平台分配的key

	@Override
	protected String makeFinalParams(Map<String, Object> params) {
		return AnRuiZhiYingUrlUtil.makeFinalParamStringWithSign(getTransCode(),getChannelId(),params,getParamsShouldSign(),getKey());
	}

	/**
	 * 获取接口的transcode值 
	 * @return
	 */
	protected abstract String getTransCode() ;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
