/**
 * 
 */
package com.xhcms.ucenter.web.action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.sso.principal.OpenIdCredentials;
import com.xhcms.ucenter.sso.service.IAuthenticationManager;
import com.xhcms.ucenter.sso.service.IUserLoginService;
import com.xhcms.ucenter.sso.service.UserProfile;

/**
 * 第三方登陆的回调action。
 */
public class OpenLoginAction extends BaseAction {
	
	private static final long serialVersionUID = -1343735868969997767L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("userLoginService")
	private IUserLoginService userLoginService;
	@Autowired
	private IAuthenticationManager authenticationManager;
	@Autowired
	private com.xhcms.lottery.conf.SystemConf systemConf;
	
	private String code;
	private String authSrc;
	private String referer;
	private String failReturnURL;
	
	//QQ weibo Login need parameter--
	private String openid;
	private String openkey;
	//QQ weibo Login need parameter --
	
	//QQ connect Login 
	private String accessToken;
	//QQ connect Login
	
	private boolean needInviteCode;
	
	private boolean allowSelfRegiste() {
		return systemConf.isAllowSelfRegiste();
	}
	/**
	 * 总是GET，从第三方微博网页回跳过来的。
	 */
	@Override
	public String execute() throws Exception {
		if(StringUtils.isBlank(code) 
				|| StringUtils.isBlank(authSrc)
				|| StringUtils.isBlank(referer)) {
			logger.info("code/authSrc/referer 参数不完整");
			return SUCCESS;
		}
		OpenIdCredentials credentials;
		
		if(StringUtils.isNotBlank(openid) && StringUtils.isNotBlank(openkey)){
			credentials = new OpenIdCredentials(code, authSrc, openid, openkey);
		} else {
			credentials = new OpenIdCredentials(code, authSrc);
		}
		credentials.setAccessToken(accessToken);
		UserProfile profile = authenticationManager.authenticateByOpenId(credentials);
		needInviteCode = !allowSelfRegiste();
		return userLoginService.onLogin(profile, referer, request, response);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAuthSrc() {
		return authSrc;
	}

	public void setAuthSrc(String authSrc) {
		this.authSrc = authSrc;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getFailReturnURL() {
		return failReturnURL;
	}

	public void setFailReturnURL(String failReturnURL) {
		this.failReturnURL = failReturnURL;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setOpenkey(String openkey) {
		this.openkey = openkey;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public boolean isNeedInviteCode() {
		return needInviteCode;
	}
}
