package com.unison.lottery.weibo.uc.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.api.weibo.Weibo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.javabeans.weibo.WeiboBean;
import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.QQConnectService;
import com.unison.lottery.weibo.uc.util.LoadUrlImageUtil;
import com.xhcms.lottery.utils.BeanUtilsTools;

@Service
public class QQConnectServiceImpl implements QQConnectService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	public WeiboUser getUserInfoFromQQ(WeiboUser w) {
		WeiboUser weiboUser = new WeiboUser();
		BeanUtilsTools.copyNotNullProperties(w, weiboUser,null);
		try {
			OpenID openIDObj =  new OpenID(weiboUser.getQqConnectToken());
			String openID = openIDObj.getUserOpenID();
			UserInfo qzoneUserInfo = new UserInfo(weiboUser.getQqConnectToken(),openID);
			UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
			File f = LoadUrlImageUtil.getUrlImage(userInfoBean.getAvatar().getAvatarURL100());
			weiboUser.setHeadImageURL(LoadUrlImageUtil.saveHeadImageFile(f));
			weiboUser.setGender(userInfoBean.getGender().equals("男")? 0:1);
			weiboUser.setQqConnectUid(openID);
			weiboUser.setNickName(userInfoBean.getNickname());
			return weiboUser;
		} catch (QQConnectException e) {
			logger.error("获取QQ互联用户信息失败",e);
			return weiboUser;
		} catch (IOException e) {
			logger.error("获取QQ互联用户头像失败",e);
			return null;
		}
	}
	@Override
	public Result shareWeiboContent(WeiboUser weiboUser, String weiboContent) {
		Result result = new Result();
		result.setSuccess(false);
		String uid = weiboUser.getQqConnectUid();
		String token = weiboUser.getQqConnectToken();
		if (StringUtils.isBlank(uid) || StringUtils.isBlank(token)
				|| StringUtils.isBlank(weiboContent)) {
			result.setDesc("非法访问");
			return result;
		}
		if (weiboContent.length() > 140) {
			result.setDesc("分享内容过长");
			return result;
		}
		try {
			Weibo weibo = new Weibo(token, uid);
			WeiboBean weiboBean = weibo.addWeibo(weiboContent);
			logger.info("weiboBean:{}",weiboBean);
			if (0 == weiboBean.getRet()) {
				result.setSuccess(true);
				result.setDesc("分享成功");
				return result;
			} else {
				String msg = weiboBean.getMsg();
				result.setDesc(msg);
				return result;
			}
		} catch (Exception e) {
			logger.error("分享至QQ空间出错,weiboUserId={},weiboContent={}",weiboUser.getWeiboUserId(),weiboContent,e);
			result.setDesc("分享至QQ空间失败");
			return result;
		}
	}
}
