package com.xhcms.ucenter.web.action.reg;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.sso.service.IUserLoginService;

public class BindWeiboAction extends BaseAction {

	private static final long serialVersionUID = 7401713793180417723L;
	
	private String referer;
	private String failReturnURL;
	private String username;
	private String password;
	private WeiboUser weiboUser;
	private AccountDealResult data;
	@Autowired
	@Qualifier("userLoginService")
	private IUserLoginService userLoginService;
	
	public String execute(){
		Object o = request.getSession().getAttribute(Constant.WEIBO_USER_KEY);
		if(null == o){
			return ERROR;
		}
		WeiboUser weiboUserInSession = (WeiboUser)o;
		if(StringUtils.isBlank(failReturnURL)){
			failReturnURL = "http://www.davcai.com/";
		}
		if(StringUtils.isBlank(referer)){
			referer = "http://www.davcai.com/";
		}
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return ERROR;
		}
		makeWeiboUser(this.weiboUser, weiboUserInSession);
		AccountDealResult accountDealResult = userLoginService.bindAccount(username, password, this.weiboUser);
		data = accountDealResult;
		return "ajax";
	}
	
	private void makeWeiboUser(WeiboUser weiboUser,WeiboUser weiboUserInSession){
		weiboUser.setHeadImageURL(weiboUserInSession.getHeadImageURL());
		weiboUser.setSinaWeiboUid(weiboUserInSession.getSinaWeiboUid());
		weiboUser.setSinaWeiboToken(weiboUserInSession.getSinaWeiboToken());
		weiboUser.setQqConnectToken(weiboUserInSession.getQqConnectToken());
		weiboUser.setQqConnectUid(weiboUserInSession.getQqConnectUid());
		weiboUser.setWeixinToken(weiboUserInSession.getWeixinToken());
		weiboUser.setWeixinPCUid(weiboUserInSession.getWeixinPCUid());
		weiboUser.setAlipayToken(weiboUserInSession.getAlipayToken());
		weiboUser.setAlipayUid(weiboUserInSession.getAlipayUid());
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}

	public AccountDealResult getData() {
		return data;
	}

	public void setData(AccountDealResult data) {
		this.data = data;
	}
}
