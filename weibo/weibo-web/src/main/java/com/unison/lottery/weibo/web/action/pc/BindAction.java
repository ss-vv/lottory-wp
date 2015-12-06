package com.unison.lottery.weibo.web.action.pc;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import weibo4j.http.AccessToken;
import weibo4j.model.User;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.data.SinaWeiboLoginModel;
import com.unison.lottery.weibo.uc.data.WeixinTokenModel;
import com.unison.lottery.weibo.uc.service.QQConnectService;
import com.unison.lottery.weibo.uc.service.SinaWeiboService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.uc.service.WeixinService;
import com.unison.lottery.weibo.web.action.BaseAction;

public class BindAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WeixinService weixinService;
	@Autowired
	private SinaWeiboService sinaWeiboService;
	@Autowired
	private QQConnectService qqConnectService;
	@Autowired
	private UserAccountService userAccountService;

	private String code;
	private String openid;
	private String openkey;
	private AccountDealResult data;
	
	private long weiboUserId;
	
	private String access_token;
	
	private boolean showCancelBindBtn = false;
	
	public String toBind() {
		user = getUser();
		weiboUserId = user.getWeiboUserId();
		WeiboUser weiboUser = userAccountService.findWeiboUserByWeiboUid( weiboUserId + "" );
		if (StringUtils.isNotBlank(weiboUser.getSinaWeiboToken())
				&& StringUtils.isNotBlank(weiboUser.getSinaWeiboUid())) {
			User sinaWeiboUser = sinaWeiboService.getSinaWeiboUser(
					weiboUser.getSinaWeiboToken(), weiboUser.getSinaWeiboUid());
			if (null != sinaWeiboUser) {
				request.setAttribute("sinaWeiboUser", sinaWeiboUser);
			}
		}
		if (StringUtils.isNotBlank(weiboUser.getWeixinToken())
				&& StringUtils.isNotBlank(weiboUser.getWeixinPCUid())) {
			WeiboUser weixinUser = weixinService.getUserInfoFromWeixin(weiboUser);
			if (null != weixinUser) {
				request.setAttribute("weixinUser", weixinUser);
			}
		}
		if (StringUtils.isNotBlank(weiboUser.getQqConnectToken())
				&& StringUtils.isNotBlank(weiboUser.getQqConnectUid())) {
			WeiboUser qqConnectUser = qqConnectService.getUserInfoFromQQ(weiboUser);
			if (null != qqConnectUser) {
				request.setAttribute("qqConnectUser", qqConnectUser);
			}
		}
		if(!user.getUsername().matches("\\d{15,17}")){
			showCancelBindBtn = true;
		}
		return SUCCESS;
	}
	
	public String bindSinaWeibo() {
		data = new AccountDealResult();
		user = getUser();
		AccessToken accessToken = sinaWeiboService.getAccessTokenByCode(code);
		if (null == accessToken) {
			data.setDesc("授权失败");
			return SUCCESS;
		}
		SinaWeiboLoginModel sinaWeiboLoginModel = new SinaWeiboLoginModel();
		sinaWeiboLoginModel.setSinaWeiboToken(accessToken.getAccessToken());
		sinaWeiboLoginModel.setSinaWeiboUid(accessToken.getUid());
		WeiboUser weiboUser = userAccountService.findWeiboUserByWeiboUid(user
				.getWeiboUserId() + "");
		data = userAccountService.bindSinaWeibo(weiboUser, sinaWeiboLoginModel);
		if(data.isSuccess()){
			User sinaWeiboUser = sinaWeiboService.getSinaWeiboUser(
					weiboUser.getSinaWeiboToken(), weiboUser.getSinaWeiboUid());
			if (null != sinaWeiboUser) {
				request.setAttribute("sinaWeiboUser", sinaWeiboUser);
			}
			user.setSinaWeiboToken(weiboUser.getSinaWeiboToken());
			user.setSinaWeiboUid(weiboUser.getSinaWeiboUid());
			updateUserInSession(user);
		}
		return SUCCESS;
	}
	
	public String bindWeixin() {
		data = new AccountDealResult();
		user = getUser();
		WeixinTokenModel w = weixinService.getTokenByCode(code);
		if(null == w){
			data.setDesc("授权失败");
			return SUCCESS;
		}
		WeiboUser weiboUser = userAccountService.findWeiboUserByWeiboUid(user.getWeiboUserId() + "");
		data = userAccountService.bindWeixin(weiboUser, w);
		if(data.isSuccess()){
			WeiboUser weixinUser = weixinService.getUserInfoFromWeixin(weiboUser);
			if (null != weixinUser) {
				request.setAttribute("weixinUser", weixinUser);
			}
			user.setWeixinToken(weiboUser.getWeixinToken());
			user.setWeixinPCUid(weiboUser.getWeixinPCUid());
			updateUserInSession(user);
		}
		return SUCCESS;
	}
	public String cancelBindSinaWeibo(){
		user = getUser();
		data = userAccountService.cancelSinaWeiboBind(user);
		user.setSinaWeiboToken(null);
		user.setSinaWeiboUid(null);
		updateUserInSession(user);
		return SUCCESS;
	}
	public String cancelBindAlipay(){
		user = getUser();
		data = userAccountService.cancelAlipayBind(user);
		user.setAlipayToken(null);
		user.setAlipayUid(null);
		updateUserInSession(user);
		return SUCCESS;
	}
	public String cancelBindWeixin(){
		user = getUser();
		data = userAccountService.cancelWeixinBind(user);
		user.setWeixinToken(null);
		user.setWeixinUid(null);
		user.setWeixinPCUid(null);
		user.setWeixinUnionId(null);
		updateUserInSession(user);
		return SUCCESS;
	}
	
	public String bindQQ(){
		data = new AccountDealResult();
		user = getUser();
		if(StringUtils.isBlank(access_token)){
			return INPUT;
		}
		try {
			WeiboUser qqUser = new WeiboUser();
			qqUser.setQqConnectToken(access_token);
			qqUser = qqConnectService.getUserInfoFromQQ(qqUser);
		    WeiboUser weiboUser = userAccountService.findWeiboUserByWeiboUid(user
					.getWeiboUserId() + "");
		    data = userAccountService.bindQQ(weiboUser, qqUser);
		    if(data.isSuccess()){
		    	logger.info("QQ互联绑定信息，accessToken:{}，openUid:{}", qqUser.getQqConnectToken(),
				    		qqUser.getQqConnectUid());
				request.setAttribute("qqConnectUser", qqUser);
				user.setQqConnectToken(qqUser.getQqConnectToken());
				user.setQqConnectUid(qqUser.getQqConnectUid());
				updateUserInSession(user);
		    }
			return SUCCESS;
		} catch (Exception e) {
			data.setDesc("授权失败");
			return SUCCESS;
		}
	}
	public String cancelBindQQ(){
		user = getUser();
		data = userAccountService.cancelQQBind(user);
		user.setQqConnectToken(null);
		user.setQqConnectUid(null);
		updateUserInSession(user);
		return SUCCESS;
	}
	
	private void updateUserInSession(WeiboUser user){
		request.getSession().setAttribute(Constant.User.USER, user);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SinaWeiboService getSinaWeiboService() {
		return sinaWeiboService;
	}

	public void setSinaWeiboService(SinaWeiboService sinaWeiboService) {
		this.sinaWeiboService = sinaWeiboService;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOpenkey() {
		return openkey;
	}

	public void setOpenkey(String openkey) {
		this.openkey = openkey;
	}

	public long getWeiboUserId() {
		return weiboUserId;
	}

	public void setWeiboUserId(long weiboUserId) {
		this.weiboUserId = weiboUserId;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public boolean isShowCancelBindBtn() {
		return showCancelBindBtn;
	}

	public void setShowCancelBindBtn(boolean showCancelBindBtn) {
		this.showCancelBindBtn = showCancelBindBtn;
	}

	public AccountDealResult getData() {
		return data;
	}

	public void setData(AccountDealResult data) {
		this.data = data;
	}
}
