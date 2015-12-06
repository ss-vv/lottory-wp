package com.unison.lottery.weibo.uc.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.SinaWeiboService;
import com.unison.lottery.weibo.uc.util.LoadUrlImageUtil;

@Service
public class SinaWeiboServiceImpl implements SinaWeiboService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public WeiboUser getUserInfoFromSinaWeibo(WeiboUser weiboUser) {
		Users um = new Users(weiboUser.getSinaWeiboToken());
		try {
			User user = um.showUserById(weiboUser.getSinaWeiboUid());
			logger.info("新浪微博用户信息：{}",user.toString());
			File f = LoadUrlImageUtil.getUrlImage(user.getAvatarLarge());
			weiboUser.setHeadImageURL(LoadUrlImageUtil.saveHeadImageFile(f));
			weiboUser.setNickName(user.getName());
			weiboUser.setSinaWeiboUid(user.getId());
			weiboUser.setGender("f".equals(user.getGender()) ? 1:0);
			return weiboUser;
		} catch (WeiboException e) {
			logger.error("获取新浪微博用户信息失败",e);
			return null;
		} catch (IOException e) {
			logger.error("获取新浪微博用户头像失败",e);
			return null;
		}
	}

	@Override
	public boolean checkAuthorization(WeiboUser weiboUser) {
		String token = weiboUser.getSinaWeiboToken();
		Account weiboAccount = new Account(token);
		try {
			weiboAccount.getUid();
			return true;
		} catch (WeiboException e) {
			logger.info("微博授权超期,需要重新授权",e);
			return false;
		}
	}
	
	@Override
	public User getSinaWeiboUser(String sinaWeiboToken, String sinaWeiboUserId){
		Users um = new Users(sinaWeiboToken);
		try {
			return um.showUserById(sinaWeiboUserId);
		} catch (WeiboException e) {
			logger.error("获取新浪微博用户信息出错", e);
			return null;
		}
	}

	@Override
	public AccessToken getAccessTokenByCode(String code) {
		if(null == code){
			return null;
		}
		logger.info("sina weibo code:{}", code);
		try {
			Oauth oauth = new Oauth();
			return oauth.getAccessTokenByCode(code);
		} catch ( WeiboException e) {
			logger.error("非法code!");
			return null;
		}
	}

	@Override
	public Result shareWeiboContent(WeiboUser user, String content) {
		Result result = new Result();
		result.setSuccess(false);
		String sinaWeiboUid = user.getSinaWeiboUid();
		String sinaWeiboToken = user.getSinaWeiboToken();
		if(StringUtils.isBlank(sinaWeiboUid) ||
				StringUtils.isBlank(sinaWeiboToken) ||
				StringUtils.isBlank(content) ){
			result.setDesc("非法访问");
			return result;
		}
		if(content.length() > 140){
			result.setDesc("分享内容过长");
			return result;
		}
		Timeline tm = new Timeline(sinaWeiboToken);
		try {
			Status status = tm.updateStatus(content);
			logger.info("分享至新浪微博成功，内容：{}",status.toString());
			result.setDesc("分享成功");
			result.setSuccess(true);
			return result;
		} catch (WeiboException e) {
			logger.warn("分享微博内容至新浪微博失败",e);
			if(e.getErrorCode() == 20019){
				result.setDesc("分享内容重复");
			} else if(e.getErrorCode() == 10024){
				result.setDesc("分享频率过快");
			}
			else{
				result.setDesc("分享失败");
			}
			return result;
		}	
	}
}
