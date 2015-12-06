package com.xhcms.ucenter.sso.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.Action;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.persist.UserAccountDao;
import com.unison.lottery.weibo.uc.service.QQConnectService;
import com.unison.lottery.weibo.uc.service.SinaWeiboService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.uc.service.WeixinService;
import com.unison.lottery.weibo.utils.HttpRequestDeviceUtils;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.lang.EnumLoginStatus;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.discuz.DiscuzSynchronizer;
import com.xhcms.ucenter.sso.service.AlipayOpenUserProfile;
import com.xhcms.ucenter.sso.service.IAuthenticationManager;
import com.xhcms.ucenter.sso.service.IUserLoginService;
import com.xhcms.ucenter.sso.service.OpenUserProfile;
import com.xhcms.ucenter.sso.service.UserProfile;
import com.xhcms.ucenter.sso.ticket.IService;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.ServiceTicket;
import com.xhcms.ucenter.sso.web.util.GrantingTicketCookieGenerator;

public class UserLoginService implements IUserLoginService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IUserService userServiceCache;
	@Autowired
	private IUserManager userManager;
	@Autowired
	private GrantingTicketCookieGenerator cookieGenerator;
	@Autowired
	private DiscuzSynchronizer discuzSynchronizer;

	@Autowired
	private IAuthenticationManager authenticationManager;

	@Autowired
	private WeixinService weixinService;
	@Autowired
	private SinaWeiboService sinaWeiboService;
	@Autowired
	private QQConnectService qqConnectService;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService commonUserService;
	@Override
	public String onLogin(UserProfile profile, String referer,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (null == profile) {
			logger.error("profile is null");
			return Action.SUCCESS;
		}

		if (profile instanceof OpenUserProfile) {
			OpenUserProfile openUserProfile = (OpenUserProfile) profile;
			if (false == openUserProfile.isRegist()) {
				return notRegistUserLogin(openUserProfile, referer, request,
						response);
			} else {
				return registedUserLogin(openUserProfile, referer, request,
						response);
			}
		}

		logger.error("profile 数据非法，请求未处理，直接跳转至 referer");
		return Action.SUCCESS;
	}

	private String notRegistUserLogin(OpenUserProfile profile, String referer,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(Constant.OPEN_USER_KEY, profile);
		request.getSession().setAttribute(Constant.WEIBO_USER_KEY,
				makeWeiboUser(profile));
		request.setAttribute("bindReferer", referer);
		referer = "http://www.davcai.com/upload-head-img";
		request.setAttribute("referer", referer);
		if (HttpRequestDeviceUtils.isMobileDevice(request)) {
			return "toRegistPageByOpen";
		} else {
			return "toRegistPageByOpen";
			// return "toRegistPage";
		}
	}

	private WeiboUser makeWeiboUser(OpenUserProfile profile) {
		WeiboUser weiboUser = new WeiboUser();
		if (null != profile) {
			if (Constant.SINA_OPEN_SERVICE_NAME.equals(profile.getAuthSrc())) {
				weiboUser.setSinaWeiboToken(profile.getToken());
				weiboUser.setSinaWeiboUid(profile.getOpenUid());
				return sinaWeiboService.getUserInfoFromSinaWeibo(weiboUser);
			} else if(Constant.QQ_CONNECT_SERVICE_NAME.equals(profile.getAuthSrc())){
				weiboUser.setQqConnectToken(profile.getToken());
				weiboUser.setQqConnectUid(profile.getOpenUid());
				return qqConnectService.getUserInfoFromQQ(weiboUser);
			}else if(Constant.ALIPAY_OPEN_SERVICE_NAME.equals(profile.getAuthSrc())){
				if (profile instanceof AlipayOpenUserProfile) {
					AlipayOpenUserProfile a = (AlipayOpenUserProfile)profile;
					weiboUser.setAlipayUid(a.getOpenUid());
					weiboUser.setAlipayToken(a.getOpenUid());//alipay没有token，直接用用户openid
					weiboUser.setNickName(a.getTruename());
					weiboUser.setHeadImageURL("http://www.davcai.com/images/default_face.jpg");
				}
				return weiboUser;	
			}else if(Constant.WEIXIN_OPEN_SERVICE_NAME.equals(profile.getAuthSrc())){
				weiboUser.setWeixinPCUid(profile.getOpenUid());
				weiboUser.setWeixinToken(profile.getToken());
				return weixinService.getUserInfoFromWeixin(weiboUser);	
			}
		}
		return null;
	}

	private String registedUserLogin(OpenUserProfile openUserProfile,
			String referer, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		UserProfile profile = new UserProfile();
		profile.setId(openUserProfile.getId());
		profile.setLastLoginIp(openUserProfile.getLastLoginIp());
		profile.setLastLoginTime(openUserProfile.getLastLoginTime());
		profile.setNickname(openUserProfile.getNickname());
		profile.setRefresh(openUserProfile.isRefresh());
		profile.setTruename(openUserProfile.getTruename());
		profile.setUsername(openUserProfile.getUsername());

		request.getSession().setAttribute(Constant.USER_KEY, profile);
		request.getSession().setAttribute(Constant.USER_LASTLOGINTIME,
				profile.getLastLoginTime()); 
		// 更新用户登录信息
		userManager.updateUserLoginStatus(profile.getId(),
				request.getHeader("X-Real-IP"), new Date());

		User user = userServiceCache.getUser(profile.getId());

		// 记录cookie
		GrantingTicket grantingTicket = authenticationManager
				.createGrantingTicket(profile);
		cookieGenerator.addCookie(request, response, grantingTicket.getId());

		discuzSynchronizer.syncLoginStatus(request, response, user);

		// 登录成功后台的处理流程
		IService service = (IService) request.getSession().getAttribute(
				Constant.SERVICE_KEY_IN_SESSION);

		// 更新token
		if (Constant.SINA_OPEN_SERVICE_NAME
				.equals(openUserProfile.getAuthSrc())) {
			updateSinaWeiboToken(openUserProfile);
		}
		if (Constant.WEIXIN_OPEN_SERVICE_NAME
				.equals(openUserProfile.getAuthSrc())) {
			updateWeixinToken(openUserProfile);
		}
		if (Constant.QQ_CONNECT_SERVICE_NAME
				.equals(openUserProfile.getAuthSrc())) {
			updateQQConnectToken(openUserProfile);
		}
		if (Constant.ALIPAY_OPEN_SERVICE_NAME
				.equals(openUserProfile.getAuthSrc())) {
			
		}

		if (service != null) {
			grantingTicket.addSerice(service);
			ServiceTicket serviceTicket = authenticationManager
					.createServiceTicket(grantingTicket, service);
			response.sendRedirect(constructServiceUrl(service,
					serviceTicket.getId()));

			return Action.NONE;
		}

		if (StringUtils.isNotEmpty(referer) && referer.startsWith("http://")) {
			return "referer";
		}

		return Action.SUCCESS;
	}
	private void updateQQConnectToken(OpenUserProfile openUserProfile) {
		WeiboUser weiboUser = new WeiboUser();
		String weiboUserId = userAccountService.getWeiboUidByLotteryUid(openUserProfile.getId() + "");
		weiboUser.setWeiboUserId(Long.parseLong(weiboUserId));
		userAccountDao.updateQQConnectToken(weiboUser,
				openUserProfile.getToken());
	}

	private void updateWeixinToken(OpenUserProfile openUserProfile) {
		WeiboUser weiboUser = new WeiboUser();
		String weiboUserId = userAccountService.getWeiboUidByLotteryUid(openUserProfile.getId() + "");
		weiboUser.setWeiboUserId(Long.parseLong(weiboUserId));
		userAccountDao.updateWeixinToken(weiboUser,
				openUserProfile.getToken());
	}

	private void updateSinaWeiboToken(OpenUserProfile openUserProfile){
		WeiboUser weiboUser = new WeiboUser();
		String weiboUserId = userAccountService.getWeiboUidByLotteryUid(openUserProfile.getId() + "");
		weiboUser.setWeiboUserId(Long.parseLong(weiboUserId));
		userAccountDao.updateSinaToken(weiboUser,
				openUserProfile.getToken());
	}
	private String constructServiceUrl(IService service, String ticket) {
		String backUrl = service.getId();
		return new StringBuilder(256).append(backUrl)
				.append(backUrl.indexOf('?') > 0 ? '&' : '?').append("ticket=")
				.append(ticket).toString();
	}

	@Override
	@Transactional
	public AccountDealResult bindAccount(String username, String password,
			WeiboUser weiboUser) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		UserPO userpo = userDao.getUserByUsername(username);
		if (null == userpo) {
			accountDealResult.setDesc("用户不存在");
			return accountDealResult;
		}
		String pwd = Text.MD5Encode(password);
		// 判断用户密码 和 状态
		if (!pwd.equals(userpo.getPassword())
				|| userpo.getStatus() == EnumLoginStatus.STATUS_DISABLE
						.getValue()) {
			accountDealResult.setDesc("无效账户或密码错误");
			return accountDealResult;
		}

		WeiboUser newUser = userAccountService.findWeiboUserByLotteryUid(""+userpo.getId());
		if(null == newUser){
			newUser = new WeiboUser();
			newUser.setId(userpo.getId());
			newUser.setUsername(userpo.getUsername());
			newUser.setHeadImageURL(weiboUser.getHeadImageURL());
			newUser.setNickName("");
			if(StringUtils.isNotBlank(weiboUser.getSinaWeiboUid()) &&
					StringUtils.isNotBlank(weiboUser.getSinaWeiboToken())){
				newUser.setSinaWeiboUid(weiboUser.getSinaWeiboUid());
				newUser.setSinaWeiboToken(weiboUser.getSinaWeiboToken());
			}
			if(StringUtils.isNotBlank(weiboUser.getQqConnectToken()) &&
					StringUtils.isNotBlank(weiboUser.getQqConnectUid())){
				newUser.setQqConnectToken(weiboUser.getQqConnectToken());
				newUser.setQqConnectUid(weiboUser.getQqConnectUid());
			}
			if(StringUtils.isNotBlank(weiboUser.getWeixinToken()) &&
					StringUtils.isNotBlank(weiboUser.getWeixinPCUid())){
				newUser.setWeixinToken(weiboUser.getWeixinToken());
				newUser.setWeixinPCUid(weiboUser.getWeixinPCUid());
			}
			if(StringUtils.isNotBlank(weiboUser.getAlipayToken()) &&
					StringUtils.isNotBlank(weiboUser.getAlipayUid())){
				newUser.setAlipayToken(weiboUser.getAlipayToken());
				newUser.setAlipayUid(weiboUser.getAlipayUid());
			}
			userAccountDao.saveWeiboUser(newUser);
			commonUserService.update(newUser);
		} else {
			if(StringUtils.isNotBlank(weiboUser.getSinaWeiboUid()) &&
					StringUtils.isNotBlank(weiboUser.getSinaWeiboToken())){
				newUser.setSinaWeiboUid(weiboUser.getSinaWeiboUid());
				newUser.setSinaWeiboToken(weiboUser.getSinaWeiboToken());
			}
			if(StringUtils.isNotBlank(weiboUser.getQqConnectToken()) &&
					StringUtils.isNotBlank(weiboUser.getQqConnectUid())){
				newUser.setQqConnectToken(weiboUser.getQqConnectToken());
				newUser.setQqConnectUid(weiboUser.getQqConnectUid());
			}
			if(StringUtils.isNotBlank(weiboUser.getWeixinToken()) &&
					StringUtils.isNotBlank(weiboUser.getWeixinPCUid())){
				newUser.setWeixinToken(weiboUser.getWeixinToken());
				newUser.setWeixinPCUid(weiboUser.getWeixinPCUid());
			}
			if(StringUtils.isNotBlank(weiboUser.getAlipayToken()) &&
					StringUtils.isNotBlank(weiboUser.getAlipayUid())){
				newUser.setAlipayToken(weiboUser.getAlipayToken());
				newUser.setAlipayUid(weiboUser.getAlipayUid());
			}
			userAccountDao.updateAll(newUser);
			commonUserService.update(newUser);
		}
		logger.info("绑定帐号成功：大V彩微博uid={},彩票uid={}", weiboUser.getWeiboUserId(),
				weiboUser.getId());
		accountDealResult.setDesc("绑定帐号成功");
		accountDealResult.setSuccess(true);
		return accountDealResult;
	}

}
