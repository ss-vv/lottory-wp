package com.unison.lottery.weibo.uc.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
import org.springframework.stereotype.Service;

import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.WeixinTokenModel;
import com.unison.lottery.weibo.uc.service.WeixinService;
import com.unison.lottery.weibo.uc.util.LoadUrlImageUtil;
import com.xhcms.lottery.utils.BeanUtilsTools;

@Service
public class WeixinServiceImpl implements WeixinService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public WeiboUser getUserInfoFromWeixin(WeiboUser w) {
		WeiboUser weiboUser = new WeiboUser();
		BeanUtilsTools.copyNotNullProperties(w, weiboUser,null);
		String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
		userInfoUrl = String.format(userInfoUrl, weiboUser.getWeixinToken(),weiboUser.getWeixinPCUid());
		if(!isTokenValid(weiboUser)){
			return null;
		}
		String response = getResponeBodyByUrl(userInfoUrl);
		if(StringUtils.isBlank(response)){
			return null;
		} else {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(response);
				String openid = jsonObject.getString("openid");
				String nickname = jsonObject.getString("nickname");
				String headimgurl = jsonObject.getString("headimgurl");
				weiboUser.setWeixinPCUid(openid);
				weiboUser.setNickName(nickname);
				File f = LoadUrlImageUtil.getUrlImage(headimgurl);
				weiboUser.setHeadImageURL(LoadUrlImageUtil.saveHeadImageFile(f));
				return weiboUser;
			} catch (JSONException e) {
				logger.error("转换微信登录响应到json对象失败",e);
			} catch (IOException e) {
				logger.error("获取微信用户头像失败",e);
			}
			return null;
		}
	}
	@Override
	public boolean isTokenValid(WeiboUser weiboUser) {
		String checkTokenUrl = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
		checkTokenUrl = String.format(checkTokenUrl, weiboUser.getWeixinToken(),weiboUser.getWeixinPCUid());
		String response = getResponeBodyByUrl(checkTokenUrl);
		if(StringUtils.isBlank(response)){
			return false;
		} else {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(response);
				String errcode = jsonObject.getString("errcode");
				String errmsg = jsonObject.getString("errmsg");
				logger.debug("检查token过期结果:{}",errmsg);
				if("0".equals(errcode)){
					return true;
				} else {
					return false;
				}
			} catch (JSONException e) {
				logger.error("转换微信登录响应到json对象失败",e);
			}
			return false;
		}
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
				return convertStreamToString(getMethod.getResponseBodyAsStream());
			}
		} catch (HttpException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
		return "";
	}
	
	public static String convertStreamToString(InputStream is) {      
        StringBuilder sb1 = new StringBuilder();      
        byte[] bytes = new byte[4096];    
        int size = 0;    
          
        try {      
            while ((size = is.read(bytes)) > 0) {    
                String str = new String(bytes, 0, size, "UTF-8");    
                sb1.append(str);    
            }    
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb1.toString();      
    }
	@Override
	public WeixinTokenModel getTokenByCode(String code) {
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
				WeixinTokenModel w = new WeixinTokenModel();
				w.setAccessToken(accessToken);
				w.setOpenId(openid);
				return w;
			} catch (JSONException e) {
				logger.error("转换微信登录响应到json对象失败",e);
			}
		}
		return null;
	}
	@Override
	public JSONObject getWeixinUser(WeiboUser weiboUser) {
		String tokenUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
		tokenUrl = String.format(tokenUrl, weiboUser.getWeixinToken(),weiboUser.getWeixinPCUid());
		String response = getResponeBodyByUrl(tokenUrl);
		if(StringUtils.isBlank(response)){
			return null;
		} else {
			try {
				return new JSONObject(response);
			} catch (JSONException e) {
				logger.error("转换微信用户信息响应到json对象失败",e);
			}
		}
		return null;
	}  
}
