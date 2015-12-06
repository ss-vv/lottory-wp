package com.unison.lottery.weibo.uc.service;

import weibo4j.http.AccessToken;
import weibo4j.model.User;

import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.data.WeiboUser;

/**
 * @Description:新浪微博相关服务 
 * @author 江浩翔   
 * @date 2013-10-23 下午3:09:31 
 * @version V1.0
 */
public interface SinaWeiboService {
	/**
	 * 获取用户的新浪微博信息
	 * @param weiboUser
	 * @return
	 */
	WeiboUser getUserInfoFromSinaWeibo(WeiboUser weiboUser);
	
	/**
	 * 检查微博授权有效性
	 * @param weiboUser
	 * @return
	 */
	boolean checkAuthorization(WeiboUser weiboUser);
	
	/**
	 * 使用新浪微博token和新浪微博uid获取用户
	 * @param sinaWeiboToken
	 * @param sinaWeiboUserId
	 * @return 新浪微博stack中的user对象
	 */
	User getSinaWeiboUser(String sinaWeiboToken, String sinaWeiboUserId);
	
	AccessToken getAccessTokenByCode(String code);
	
	Result shareWeiboContent(WeiboUser user,String content);
}
