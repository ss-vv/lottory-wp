package com.unison.lottery.weibo.data.vo;

import com.unison.lottery.weibo.common.redis.Reference;
import com.unison.lottery.weibo.data.Topic;
import com.unison.lottery.weibo.data.WeiboMsgVO;

/**
 * Topic VO.
 * 
 * @author Yang Bo
 */
public class TopicVO extends Topic {

	private static final long serialVersionUID = -2805057514164357307L;

	@Reference
	private WeiboMsgVO weibo;

	public WeiboMsgVO getWeibo() {
		return weibo;
	}

	public void setWeibo(WeiboMsgVO weibo) {
		this.weibo = weibo;
	}
}
