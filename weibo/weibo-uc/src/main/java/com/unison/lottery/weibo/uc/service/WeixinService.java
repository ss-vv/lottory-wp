package com.unison.lottery.weibo.uc.service;

import weibo4j.org.json.JSONObject;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.WeixinTokenModel;

public interface WeixinService {

	WeiboUser getUserInfoFromWeixin(WeiboUser weiboUser);
	boolean isTokenValid(WeiboUser weiboUser);
	WeixinTokenModel getTokenByCode(String code);
	JSONObject getWeixinUser(WeiboUser weiboUser);
}
