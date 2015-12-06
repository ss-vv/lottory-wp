package com.xhcms.ucenter.sso.service.impl;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.SinaWeiboService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.uc.service.WeixinService;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.ucenter.persist.redis.dao.IOpenUserDao;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.principal.OpenIdCredentials;
import com.xhcms.ucenter.sso.service.IOpenIdAuthService;
import com.xhcms.ucenter.sso.service.OpenUserProfile;
import com.xhcms.ucenter.sso.service.UserProfile;

public class WeixinOpenIdAuthService implements IOpenIdAuthService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WeixinService weixinService;
	@Autowired
	IOpenUserDao openUserDao;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	UserDao userDao;
	@Autowired
	private IUserService userService;
	
	@Override
	@Transactional
	public UserProfile authenticate(OpenIdCredentials openIdCredentials) {
		String code = openIdCredentials.getCode();
		logger.info("weixin code:{}", code);
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=wx8a684cf1f5b6a359&"
				+ "secret=1b280c408a8a3e111f593cd77ca02558&"
				+ "code=%s&grant_type=authorization_code";
		tokenUrl = String.format(tokenUrl, code);
		String response = getResponeBodyByUrl(tokenUrl);
		if(StringUtils.isBlank(response)){
			return null;
		} else {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(response);
				String accessToken = jsonObject.getString("access_token");
				String openid = jsonObject.getString("openid");
				if(StringUtils.isBlank(accessToken) ||
						StringUtils.isBlank(openid)){
					return null;
				}
				logger.info("微信登录成功，accessToken:{}，openUid:{}", accessToken,openid);
				String username = openUserDao.getLotteryUsername(openid, openIdCredentials.getAuthSrc());
				if(StringUtils.isBlank(username)){//为空情况下，继续查找weixin unionid
					WeiboUser weiboUser = new WeiboUser();
					weiboUser.setWeixinPCUid(openid);
					weiboUser.setWeixinToken(accessToken);
					String unionid = weixinService.getWeixinUser(weiboUser).getString("unionid");
					UserPO userPO = userDao.findByOpenUid(unionid,"weixinUid");//客户端存到weixinUid的是unionId
					if(null != userPO){
						username = userPO.getUsername();
						userPO.setWeixinPCUid(openid);
						userPO.setWeixinUid(unionid);
						userPO.setWeixinToken(accessToken);
						userPO.setWeixinUnionId(unionid);
						userAccountService.updateOpenInfo("weixin", userPO);
					} else {//依然为空,继续查是否匹配weixinUid
						userPO = userDao.findByOpenUid(openid,"weixinUid");
						if(null != userPO){//说明weixinUid保存了PCopenId，需要更新到weixinPCUid
							userPO.setWeixinPCUid(openid);
							userPO.setWeixinUid(unionid);//客户端存到weixinUid的是unionId
							userPO.setWeixinToken(accessToken);
							userPO.setWeixinUnionId(unionid);
							userAccountService.updateOpenInfo("weixin", userPO);
							username = userPO.getUsername();
						}
					}
				}
				return makeUserProfile(username, accessToken ,openid, openIdCredentials);
			} catch (JSONException e) {
				logger.error("转换微信登录响应到json对象失败",e);
			}
			return null;
		}
	}
	
	private OpenUserProfile makeUserProfile(String username, String accessToken,String openId,OpenIdCredentials openIdCredentials){
		OpenUserProfile openUserProfile = new OpenUserProfile();
		if(null == username){//未注册/绑定的第三方帐号
			openUserProfile.setOpenUid(openId);
			openUserProfile.setToken(accessToken);
			openUserProfile.setAuthSrc(openIdCredentials.getAuthSrc());
			openUserProfile.setRegist(false);
			return openUserProfile;
		}
		
		//已绑定大V彩帐号的第三方帐号
		User user = userService.getUserByUsername(username);
		if(null == user){
			logger.error("微信用户：uid={} 登录异常，" +
				"在redis数据库中找到绑定帐号的用户名：username={};" +
				"但未在大V彩lt_user帐号中查到该用户信息");
			return null;
		}
		userService.updateToken(username,"weixinToken",accessToken);
		openUserProfile.setId(user.getId());
		openUserProfile.setUsername(user.getUsername());
		openUserProfile.setTruename(user.getRealname());
		openUserProfile.setLastLoginIp(user.getLastLoginIp());
		openUserProfile.setLastLoginTime(user.getLastLoginTime());
		openUserProfile.setRefresh(false);
		
		openUserProfile.setOpenUid(openId);
		openUserProfile.setToken(accessToken);
		openUserProfile.setAuthSrc(openIdCredentials.getAuthSrc());
		openUserProfile.setRegist(true);
		return openUserProfile;
	}
	
	private String getResponeBodyByUrl(String url){
		HttpClientParams clientParams = new HttpClientParams();
		// 忽略cookie 避免 Cookie rejected 警告
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		HttpClient client = new HttpClient(clientParams,new MultiThreadedHttpConnectionManager());
		try {
			GetMethod getMethod = new GetMethod(url);
			client.executeMethod(getMethod);
			if(getMethod.getStatusCode() == HttpStatus.SC_OK){
				return getMethod.getResponseBodyAsString();
			}
		} catch (HttpException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
		return "";
	}
}
