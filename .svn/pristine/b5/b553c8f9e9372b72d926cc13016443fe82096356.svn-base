package com.unison.lottery.weibo.msg.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;

/**
 * 获取用户登录信息的SessionBean
 * @author lei.li@davcai.com
 */
@Service
public class UserSessionBean {
	private Logger log = LoggerFactory.getLogger(getClass());
	public WeiboUser getLoginUser() {
		try {
			Object obj = ServletActionContext.getRequest().getSession()
					.getAttribute(Constant.User.USER);
			return obj == null ? null : (WeiboUser) obj;
		} catch (Exception e) {
			log.error("非web容器中使用异常");
		}
		return null;
	}
	
	public long getLcUserId() {
		long userId = -1L;
		WeiboUser weiboUser = getLoginUser();
		if(null != weiboUser) {
			String idStr = weiboUser.toRedisHashValue().get("id");
			if(StringUtils.isNotBlank(idStr)) {
				userId = Long.valueOf(idStr);
			}
		}
		return userId;
	}
}