package com.unison.lottery.api.login.hx.httpclient.vo;


import java.net.URL;

import com.unison.lottery.api.login.hx.comm.Constants;
import com.unison.lottery.api.login.hx.httpclient.utils.HTTPClientUtils;

/**
 * HTTPClient EndPoints
 * 
 * @author Lynch 2014-09-15
 *
 */
public interface EndPoints {

	public static final URL ROOT_URL = HTTPClientUtils.getURL("");

	public static final URL MANAGEMENT_URL = HTTPClientUtils.getURL("/management");

	public static final URL TOKEN_ORG_URL = HTTPClientUtils.getURL("/management/token");

	public static final URL APPLICATION_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/"));

	public static final URL TOKEN_APP_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/token");

	public static final URL USERS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users");

	public static final URL MESSAGES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/messages");

	public static final URL CHATMESSAGES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
			+ "/chatmessages");

	public static final URL CHATGROUPS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups");

	public static final URL CHATFILES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatfiles");

}
