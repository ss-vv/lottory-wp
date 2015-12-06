package com.xhcms.ucenter.web.action.reg;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import weibo4j.org.json.JSONException;

import com.opensymphony.xwork2.ActionContext;
import com.unison.lottery.weibo.common.service.IdGenerator;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.uc.service.WeixinService;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.weibo.CertificationUserNoticeHandler;
import com.xhcms.lottery.commons.persist.service.RegistrationCodeService;
import com.xhcms.lottery.lang.MQMessageType;
import com.xhcms.lottery.lang.RegistCodeStatus;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.exception.UCException;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.sso.service.OpenUserProfile;

public class RegistByWeiboAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	private String referer;
	private String failReturnURL;
	private WeiboUser weiboUser;
	private String username;
	private String password;
	private String openRegist;
	
	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private WeixinService weixinService;
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private MessageSender messageSender;
	private long sendMessageUserId;
	
	private String inviteCode="";
	
	@Autowired
	RegistrationCodeService registrationCodeService;
	@Autowired
	private com.xhcms.lottery.conf.SystemConf systemConf;
	
	private boolean needInviteCode;
	
	public String execute(){
		String result = execute0();
		if("toRegistPage".equals(result) && StringUtils.isNotBlank(openRegist)){
			return "toRegistPageByOpen";
		}
		return result;
	}
	private boolean allowSelfRegiste() {
		return systemConf.isAllowSelfRegiste();
	}
	public String execute0() {
		needInviteCode = !allowSelfRegiste();
		if (null == weiboUser) {
			return "toRegistPage";
		}
		RegistCodeStatus codeStatus = registrationCodeService.findAndUpdateRegistrationCodeStatus(inviteCode);
		//需要邀请码或邀请码不是有效的
		if(needInviteCode 
				&& RegistCodeStatus.VALID.getStatus() != codeStatus.getStatus()){
			AccountDealResult accountDealResult = new AccountDealResult();
			accountDealResult.fail("抱歉，停售期间暂停注册！");
			ActionContext.getContext().getValueStack().set("resultMessage", accountDealResult);
			if(StringUtils.isNotBlank(openRegist)){
				return "toRegistPageByOpen";
			}
			return "toRegistPage";
//			AccountDealResult accountDealResult = new AccountDealResult();
//			accountDealResult.fail("邀请码"+codeStatus.getMeaning());
//			ActionContext.getContext().getValueStack().set("resultMessage", accountDealResult);
//			if(StringUtils.isNotBlank(openRegist)){
//				return "toRegistPageByOpen";
//			} else {
//				return "toRegistPage";
//			}
		}
		if(StringUtils.isNotBlank(openRegist)){ 
			//如果是通过第三方账户注册，需要额外添加第三方授权信息,并生成用户名和密码
			addAuthInfo(weiboUser);
			this.username = idGenerator.nextId()+"";//生成用户名
			this.password = idGenerator.nextId()+"";//生成密码
			//@author haoxiang.jiang
			//注册成功后，会使用用户名和密码做自动登录（chain方式）
			//TODO 如果值栈key为“username”和“password” ，用户名密码值可能不能通过chain传到LoginAction里面，原因待查，
			//所以这里使用“username1”和“password1”。
			ActionContext.getContext().getValueStack().set("password1", this.password);
			ActionContext.getContext().getValueStack().set("username1", this.username);
		}
		weiboUser.setUsername(username);
		weiboUser.setPassword(password);
		logger.info("========注册信息=========");
		logger.info("用户名：{}", weiboUser.getUsername());
		logger.info("密码：{}", weiboUser.getPassword());
		logger.info("昵称：{}", weiboUser.getNickName());
		logger.info("真实姓名：{}", weiboUser.getRealname());
		logger.info("性别：{}", weiboUser.getGender());
		logger.info("手机号：{}", weiboUser.getMobile());
		logger.info("头像地址：{}", weiboUser.getHeadImageURL());
		logger.info("新浪微博uid:{}", weiboUser.getSinaWeiboUid());
		logger.info("新浪微博token:{}", weiboUser.getSinaWeiboToken());
		logger.info("QQ互联uid:{}", weiboUser.getQqConnectUid());
		logger.info("QQ互联token:{}", weiboUser.getQqConnectToken());
		logger.info("支付宝uid:{}", weiboUser.getAlipayUid());
		logger.info("支付宝token:{}", weiboUser.getAlipayToken());
		logger.info("微信pcopenid:{}", weiboUser.getWeixinPCUid());
		logger.info("微信unionid:{}", weiboUser.getWeixinUnionId());
		logger.info("微信token:{}", weiboUser.getWeixinToken());
		logger.info("=======================");
		
		try {
			AccountDealResult accountDealResult = userAccountService.regist(weiboUser);
			logger.info("注册结果标识码：{}", accountDealResult.getResultCode());
			logger.info("注册结果描述：{}", accountDealResult.getDesc());
			weiboUser = accountDealResult.getWeiboUser();
			if (accountDealResult.isSuccess()) {
				pushCertificationUser(weiboUser);
				registrationCodeService.updateRegistrationCodeStatus(inviteCode, RegistCodeStatus.USED, weiboUser.getId());
				return SUCCESS;
			} else {
				ActionContext.getContext().getValueStack()
						.set("resultMessage", accountDealResult);
				return "toRegistPage";
			}

		} catch (UCException e) {
			logger.info("注册失败：{}", e);
			return "toRegistPage";
		} catch (Exception e) {
			logger.info("注册失败：{}", e);
			return "toRegistPage";
		}
	}

	private void addAuthInfo(WeiboUser weiboUser) {
		OpenUserProfile openUserProfile = (OpenUserProfile) request
				.getSession().getAttribute(Constant.OPEN_USER_KEY);
		if (null != openUserProfile) {
			if (Constant.SINA_OPEN_SERVICE_NAME.equals(openUserProfile
					.getAuthSrc())) {
				weiboUser.setSinaWeiboUid(openUserProfile.getOpenUid());
				weiboUser.setSinaWeiboToken(openUserProfile.getToken());
			}
			if (Constant.QQ_CONNECT_SERVICE_NAME.equals(openUserProfile
					.getAuthSrc())) {
				weiboUser.setQqConnectUid(openUserProfile.getOpenUid());
				weiboUser.setQqConnectToken(openUserProfile.getToken());
			}
			if (Constant.WEIXIN_OPEN_SERVICE_NAME.equals(openUserProfile
					.getAuthSrc())) {
				weiboUser.setWeixinPCUid((openUserProfile.getOpenUid()));
				weiboUser.setWeixinToken(openUserProfile.getToken());
				try {
					weiboUser.setWeixinUnionId(weixinService.getWeixinUser(weiboUser).getString("unionid"));
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
			}
			if (Constant.ALIPAY_OPEN_SERVICE_NAME.equals(openUserProfile
					.getAuthSrc())) {
				weiboUser.setAlipayUid(openUserProfile.getOpenUid());
				weiboUser.setAlipayToken(openUserProfile.getToken());
			}
		}
	}

	private void pushCertificationUser(WeiboUser weiboUser) {
		if(null != weiboUser && weiboUser.getWeiboUserId() > 0 && sendMessageUserId > 0) {
			CertificationUserNoticeHandler certificationUserNotice = new CertificationUserNoticeHandler();
			long ownerId = sendMessageUserId;
			long peer = weiboUser.getWeiboUserId();
			String contentTpl = "%s，欢迎您加入大V彩！强烈建议您关注以下大V彩牛人。";
			
			certificationUserNotice.setOwnerId(ownerId);
			certificationUserNotice.setPeer(peer);
			certificationUserNotice.setContent(String.format(contentTpl, weiboUser.getNickName()));
			certificationUserNotice.setMessageType(MQMessageType.ANALYSIS_EXPERT);
			certificationUserNotice.setCreateTime(new Date());
			
			logger.info("为新注册用户推送“认证用户”通知，消息内容={}", certificationUserNotice);
			messageSender.send(certificationUserNotice);
		} else {
			logger.info("新注册用户推送“认证用户”通知失败，weiboUser={}, sendMessageUserId={}",
					new Object[]{weiboUser, sendMessageUserId});
		}
	}

	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
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

	public String getOpenRegist() {
		return openRegist;
	}

	public void setOpenRegist(String openRegist) {
		this.openRegist = openRegist;
	}
	public long getSendMessageUserId() {
		return sendMessageUserId;
	}
	public void setSendMessageUserId(long sendMessageUserId) {
		this.sendMessageUserId = sendMessageUserId;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public boolean isNeedInviteCode() {
		return needInviteCode;
	}
}
