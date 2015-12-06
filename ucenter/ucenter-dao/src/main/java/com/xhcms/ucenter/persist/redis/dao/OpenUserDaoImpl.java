package com.xhcms.ucenter.persist.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.ucenter.lang.Constant;


public class OpenUserDaoImpl implements IOpenUserDao {
	@Autowired
	private UserDao userDao;
	
	@Override
	@Transactional
	public String getLotteryUsername(final String openUid, final String authSrc) {
		if (null != openUid) {
			UserPO userPO = null;
			// 新浪微博
			if(Constant.SINA_OPEN_SERVICE_NAME.equals(authSrc)){
				userPO = userDao.findByOpenUid(openUid,"sinaWeiboUid");
			}
			// 微信
			if(Constant.WEIXIN_OPEN_SERVICE_NAME.equals(authSrc)){
				userPO = userDao.findByOpenUid(openUid,"weixinPCUid");
			}
			//QQ 互联
			if(Constant.QQ_CONNECT_SERVICE_NAME.equals(authSrc)){
				userPO = userDao.findByOpenUid(openUid,"qqConnectUid");
			}
			//支付宝
			if(Constant.ALIPAY_OPEN_SERVICE_NAME.equals(authSrc)){
				userPO = userDao.findByOpenUid(openUid,"alipayUid");
			}
			 return null == userPO ? null:userPO.getUsername();
		}
		return null;
	}

}
