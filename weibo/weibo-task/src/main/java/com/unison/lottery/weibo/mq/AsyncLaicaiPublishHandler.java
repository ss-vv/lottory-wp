package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.service.WeiboSchemeService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.SchemeHandle;

/**
 * 大V彩用户异步发新微博（完整功能 ）
 * @author Wang Lei
 *
 */
public class AsyncLaicaiPublishHandler implements MessageHandler<SchemeHandle>{

	private static final long serialVersionUID = -3305196252848895594L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WeiboSchemeService weiboSchemeService;
	
	@Override
	public void handle(SchemeHandle schemeHandle) {
		if(schemeHandle == null){
			return;
		}
		if(schemeHandle.getSchemeId() < 1){
			logger.error("方案id错误，id = {}！",schemeHandle.getSchemeId());
			return;
		}
		weiboSchemeService.publish(schemeHandle);
	}
}