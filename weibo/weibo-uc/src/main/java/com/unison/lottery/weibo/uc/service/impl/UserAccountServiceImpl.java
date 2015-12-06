package com.unison.lottery.weibo.uc.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map; 
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weibo4j.org.json.JSONException;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.solrj.SolrJ;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.lang.ReservedKey;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.data.SinaWeiboLoginModel;
import com.unison.lottery.weibo.uc.data.UserQueryCondition;
import com.unison.lottery.weibo.uc.data.WeixinTokenModel;
import com.unison.lottery.weibo.uc.persist.UserAccountDao;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.uc.service.WeixinService;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.utils.BeanUtilsTools;
import com.xhcms.ucenter.exception.UCException;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.persistent.service.exception.LoginNameNotFoundException;
import com.xhcms.ucenter.persistent.service.exception.LoginNameOrPasswordBlankException;
import com.xhcms.ucenter.persistent.service.exception.PasswordWrongException;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public UserAccountServiceImpl() {
		BeanUtilsTools.fixBeanUtilsNullProblem();
	}

	@Autowired
	WeixinService weixinService;
	@Autowired
	private IUserManager userManager;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAccountDao userAccountDao;

	@Autowired
	private SolrJ solrJ;

	/** 注册结果初始化 */
	private AccountDealResult initReigstResult(WeiboUser weiboUser) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		accountDealResult
				.setResultCode(Constant.AccountCode.RegistResultCode.REGIST_ERROR);
		accountDealResult.setDesc("注册失败");
		accountDealResult.setWeiboUser(weiboUser);
		return accountDealResult;
	}

	/** 登录结果初始化 */
	private AccountDealResult initLoginResult(WeiboUser weiboUser) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		accountDealResult
				.setResultCode(Constant.AccountCode.LoginResultCode.LOGIN_ERROR);
		accountDealResult.setDesc("登录失败");
		accountDealResult.setWeiboUser(weiboUser);
		return accountDealResult;
	}

	/** 初始化注册用户信息 */
	private void initRegistUserInfo(WeiboUser weiboUser) {
		Date createDate = new Date();
		weiboUser.setCreatedTime(createDate);
		weiboUser.setLastLoginTime(createDate);
		weiboUser.setWeiboUserCreateTime(createDate);
		weiboUser.setLocked_time(new Date(0));
		weiboUser.setPid(Constant.WeiboChannel);

		if (null == weiboUser.getHeadImageURL()) {
			weiboUser.setHeadImageURL("http://www.davcai.com/images/default_face.jpg");
		}
		if (null == weiboUser.getIdNumber()) {
			weiboUser.setIdNumber("");
		}
		if (null == weiboUser.getQuestion()) {
			weiboUser.setQuestion("");
		}
		if (null == weiboUser.getAnswer()) {
			weiboUser.setAnswer("");
		}
		if (null == weiboUser.getMobile()) {
			weiboUser.setMobile("");
		}
	}

	/** 向lottery-user注册 */
	private AccountDealResult registInLotteryUser(WeiboUser weiboUser) {
		AccountDealResult accountDealResult = initReigstResult(weiboUser);
		try {
			userManager.regist(weiboUser);
			accountDealResult.setDesc("注册大V彩账户成功");
			accountDealResult.setResultCode(Constant.AccountCode.RegistResultCode.REGIST_SUCCESS);
			accountDealResult.setSuccess(true);
			accountDealResult.setWeiboUser(weiboUser);
			return accountDealResult;
		} catch (UCException ucException) {
			accountDealResult.setDesc("用户名已存在");
			accountDealResult.setResultCode(Constant.AccountCode.RegistResultCode.REGIST_USERNAME_NOT_UNIQUE);
			logger.info("用户名已存在", ucException);
		} catch (Exception e) {
			logger.error("注册失败", e);
		}
		return accountDealResult;
	}

	@Override
	@Transactional
	public AccountDealResult regist(WeiboUser weiboUser) {
		return doRegist(weiboUser, false);
	}
	
	private AccountDealResult doRegist(WeiboUser weiboUser, boolean isSpecial) {
		AccountDealResult accountDealResult = initReigstResult(weiboUser);
		if (false == checkWeiboInfo(weiboUser)) {
			accountDealResult.setDesc("用户名或昵称为空");
			return accountDealResult;
		}
		if (!isNicknameUnique(weiboUser.getNickName())) {
			accountDealResult.setDesc("昵称重复");
			accountDealResult
					.setResultCode(Constant.AccountCode.RegistResultCode.REGIST_NICKNAME_NOT_UNIQUE);
			return accountDealResult;
		}
		if (!isUserNameUnique(weiboUser.getUsername())) {
			accountDealResult.setDesc("用户名重复");
			accountDealResult
					.setResultCode(Constant.AccountCode.RegistResultCode.REGIST_USERNAME_NOT_UNIQUE);
			return accountDealResult;
		}
		if (!isMobileUnique(weiboUser.getMobile())) {
			accountDealResult.setDesc("手机号码重复");
			return accountDealResult;
		}
		if (!isEmailUnique(weiboUser.getEmail())) {
			accountDealResult.setDesc("邮箱重复");
			return accountDealResult;
		}
		if (StringUtils
				.isNotBlank(ReservedKey.getValue(weiboUser.getUsername()))) {
			accountDealResult.setDesc("非法用户名");
			return accountDealResult;
		}
		if (!isSpecial && StringUtils
				.isNotBlank(ReservedKey.getValue(weiboUser.getNickName()))) {
			accountDealResult.setDesc("非法昵称");
			return accountDealResult;
		}

		initRegistUserInfo(weiboUser);
		accountDealResult = registInLotteryUser(weiboUser);
		if (true == accountDealResult.isSuccess()) {
			// 保存weiboUser扩展信息 到 大V彩redis数据库
			userAccountDao.saveWeiboUser(weiboUser);
			addUserIndex(String.valueOf(weiboUser.getWeiboUserId()),
					weiboUser.getNickName());
			accountDealResult
					.setResultCode(Constant.AccountCode.RegistResultCode.REGIST_SUCCESS);
			accountDealResult.setWeiboUser(weiboUser);
			accountDealResult.setSuccess(true);
		}
		return accountDealResult;
	}

	/** 添加user的nickName索引 */
	private void addUserIndex(String id, String nickName) {
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "user:" + id);
		doc1.addField("name", nickName);
		doc1.addField("contentType", Constant.SolrConfig.SEARCH_TYPE_USER);
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);
		try {
			solrJ.add(docs);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 检查用户名,昵称 是否为空,数据是否合法 */
	private boolean checkWeiboInfo(WeiboUser weiboUser) {
		if (null == weiboUser.getUsername() || null == weiboUser.getNickName()) {
			return false;
		}
		Pattern nickNamePattern = Pattern.compile("^[a-zA-Z0-9_\\-\u4e00-\u9fa5]+$");
		Pattern usernamePattern = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$");
		if(!usernamePattern.matcher(weiboUser.getUsername()).matches()){
			return false;
		}
		if(!nickNamePattern.matcher(weiboUser.getNickName()).matches()){
			return false;
		}
		if(weiboUser.getNickName().length() < 2 || weiboUser.getNickName().length() > 24){
			return false;
		}
		if(weiboUser.getUsername().length() < 4 || weiboUser.getUsername().length() > 20){
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public AccountDealResult login(WeiboUser weiboUser)
			throws LoginNameNotFoundException, PasswordWrongException,
			LoginNameOrPasswordBlankException {

		AccountDealResult accountDealResult = initLoginResult(weiboUser);
		UserPO userPO = userManager.loginByNameAndPasswordMD5(
				weiboUser.getUsername(), weiboUser.getPassword());
		try {
			BeanUtils.copyProperties(weiboUser, userPO);
		} catch (IllegalAccessException e) {
			logger.error("属性复制出错");
			return accountDealResult;
		} catch (InvocationTargetException e) {
			logger.error("属性复制出错");
			return accountDealResult;
		}
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		userQueryCondition.setLotteryUserUid(weiboUser.getId());
		List<WeiboUser> weiboUserList = userAccountDao
				.querryWeiboUser(userQueryCondition);
		if (weiboUserList != null && weiboUserList.size() > 0) {
			WeiboUser weiboUserExtendInfo = weiboUserList.get(0);
			this.copyProperties(weiboUser, weiboUserExtendInfo);
		}
		accountDealResult.setSuccess(true);
		accountDealResult
				.setResultCode(Constant.AccountCode.LoginResultCode.LOGIN_SUCCESS);
		accountDealResult.setDesc("登录成功");
		accountDealResult.setWeiboUser(weiboUser);
		return accountDealResult;
	}

	/**
	 * 复制WeiboUser属性
	 * 
	 * @param dest
	 * @param orig
	 */
	private void copyProperties(WeiboUser dest, WeiboUser orig) {
		dest.setWeiboUserId(orig.getWeiboUserId());
		dest.setNickName(orig.getNickName());
		dest.setHeadImageURL(orig.getHeadImageURL());
		dest.setIndividualResume(orig.getIndividualResume());
		dest.setFamiliarLottery(orig.getFamiliarLottery());
		dest.setSinaWeiboUid(orig.getSinaWeiboUid());
		dest.setSinaWeiboToken(orig.getSinaWeiboToken());
		dest.setWeiboUserCreateTime(orig.getWeiboUserCreateTime());
	}

	/**
	 * 检查绑定
	 * 
	 */
	private AccountDealResult checkBind(UserQueryCondition userQueryCondition) {
		List<WeiboUser> weiboUsers = new ArrayList<WeiboUser>();

		weiboUsers = userAccountDao.querryWeiboUser(userQueryCondition);
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);

		if (null != weiboUsers && weiboUsers.size() > 0) {
			WeiboUser weiboUser = weiboUsers.get(0);

			if (null != weiboUser.getId()) {
				User user = getUserByLotteryUserId(weiboUser.getId());
				try {
					BeanUtils.copyProperties(weiboUser, user);
					accountDealResult.setWeiboUser(weiboUser);
					accountDealResult.setSuccess(true);
				} catch (IllegalAccessException e) {
					logger.error("复制User属性到WeiboUser出错", e);
				} catch (InvocationTargetException e) {
					logger.error("复制User属性到WeiboUser出错", e);
				}
				accountDealResult.setWeiboUser(weiboUser);
				accountDealResult.setSuccess(true);
				return accountDealResult;
			}
		}
		return accountDealResult;
	}

	/**
	 * 通过lottery user id 获取user信息
	 * 
	 * @param id
	 * @return
	 */
	private User getUserByLotteryUserId(Long lotteryUserId) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(lotteryUserId);
		List<UserPO> userPOs = userDao.find(ids);
		if (userPOs.size() > 0) {
			UserPO uPO = userPOs.get(0);
			User u = new User();
			try {
				BeanUtils.copyProperties(u, uPO);
				return u;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.error("复制UserPO属性到User出错", e);
				return null;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				logger.error("复制UserPO属性到User出错", e);
				return null;
			}
		}
		return null;
	}

	public AccountDealResult isBindSinaWeibo(String sinaWeiboUid) {
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		userQueryCondition.setSinaWeiboUid(sinaWeiboUid);
		return checkBind(userQueryCondition);
	}

	public AccountDealResult isBindQQWeibo(String qqWeiboUid) {
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		userQueryCondition.setQqWeiboUid(qqWeiboUid);
		return checkBind(userQueryCondition);
	}

	@Transactional
	public AccountDealResult loginBySinaWeibo(
			SinaWeiboLoginModel sinaWeiboLoginModel) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult = isBindSinaWeibo(sinaWeiboLoginModel
				.getSinaWeiboUid());
		if (true == accountDealResult.isSuccess()) {
			this.updateSinaToken(sinaWeiboLoginModel,
					accountDealResult.getWeiboUser());
			String code = Constant.AccountCode.LoginResultCode.LOGIN_SUCCESS_BY_SINAWEIBO;
			accountDealResult.setResultCode(code);
			accountDealResult.setDesc("新浪微博登录成功");
			accountDealResult.setSuccess(true);
			return accountDealResult;
		} else {
			WeiboUser weiboUser = new WeiboUser();
			weiboUser.setSinaWeiboUid(sinaWeiboLoginModel.getSinaWeiboUid());
			weiboUser
					.setSinaWeiboToken(sinaWeiboLoginModel.getSinaWeiboToken());
			accountDealResult
					.setResultCode(Constant.AccountCode.LoginResultCode.LOGIN_SUCCESS_BY_SINAWEIBO_NOT_BIND);
			accountDealResult.setDesc("新浪微博登录成功，未绑定大V彩帐号");
			accountDealResult.setWeiboUser(weiboUser);
			accountDealResult.setSuccess(true);
			return accountDealResult;
		}
	}

	/**
	 * 更新新浪微博token
	 */
	private void updateSinaToken(SinaWeiboLoginModel sinaWeiboLoginModel,
			WeiboUser weiboUser) {
		if (!sinaWeiboLoginModel.getSinaWeiboToken().equals(
				weiboUser.getSinaWeiboToken())) {

			userAccountDao.updateSinaToken(weiboUser,
					sinaWeiboLoginModel.getSinaWeiboToken());
			weiboUser
					.setSinaWeiboToken(sinaWeiboLoginModel.getSinaWeiboToken());
		}
	}

	@Transactional
	public AccountDealResult updateAccountAllInfo(WeiboUser weiboUser) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		accountDealResult
				.setResultCode(Constant.AccountCode.UpdateResultCode.UPDATE_ERROR);
		UserPO userPO = new UserPO();
		try {
			BeanUtils.copyProperties(userPO, weiboUser);
		} catch (IllegalAccessException e) {
			logger.error("更新账户信息-复制weiboUser2userPO失败", e);
		} catch (InvocationTargetException e) {
			logger.error("更新账户信息-复制weiboUser2userPO失败", e);
			e.printStackTrace();
		}
		userManager.updateInfo(weiboUser);
		userAccountDao.updateAll(weiboUser);
		addUserIndex(String.valueOf(weiboUser.getWeiboUserId()),
				weiboUser.getNickName());
		accountDealResult
				.setResultCode(Constant.AccountCode.UpdateResultCode.UPDATE_SUCCESS);
		accountDealResult.setDesc("更新成功！");
		accountDealResult.setSuccess(true);
		accountDealResult.setWeiboUser(weiboUser);
		return accountDealResult;
	}

	@Override
	public AccountDealResult updateNickName(WeiboUser weiboUser) {
		userAccountDao.updateNickname(weiboUser);
		addUserIndex(String.valueOf(weiboUser.getWeiboUserId()),
				weiboUser.getNickName());
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setDesc("更新成功！");
		accountDealResult.setSuccess(true);
		accountDealResult
				.setResultCode(Constant.AccountCode.UpdateResultCode.UPDATE_SUCCESS);
		accountDealResult.setWeiboUser(weiboUser);
		return accountDealResult;
	}

	@Override
	public AccountDealResult updateHeadImage(WeiboUser weiboUser) {
		userAccountDao.updateHeadImage(weiboUser);
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setDesc("更新成功！");
		accountDealResult.setSuccess(true);
		accountDealResult
				.setResultCode(Constant.AccountCode.UpdateResultCode.UPDATE_SUCCESS);
		accountDealResult.setWeiboUser(weiboUser);
		return accountDealResult;
	}

	@Override
	@Transactional
	public boolean isUserNameUnique(String username) {
		UserPO userPO = userDao.getUserByUsername(username);
		return null == userPO ? true : false;
	}

	@Override
	@Transactional
	public boolean isMobileUnique(String mobile) {
		try {
			UserPO userPO = userDao.getUserByVerifyedMobile(mobile);
			return null == userPO ? true : false;
		} catch (NonUniqueResultException e) {
			logger.warn("发现数据库记录有重复的手机号码(已验证),mobile='{}'",mobile,e);
			return false;
		}
	}

	@Override
	@Transactional
	public boolean isEmailUnique(String email) {
		try {
			List<UserPO> userPOs = userDao.getValidUsersByBindedEmail(email);
			return null == userPOs || userPOs.size() < 1 ? true : false;
		} catch (NonUniqueResultException e) {
			logger.warn("发现数据库记录有重复的邮箱名(已验证),email='{}'",email,e);
			return false;
		}
	}

	@Override
	public boolean isNicknameUnique(String nickname) {
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		userQueryCondition.setNickName(nickname);
		List<WeiboUser> weiboUsers = userAccountDao
				.querryWeiboUser(userQueryCondition);
		return null != weiboUsers && weiboUsers.size() > 0 ? false : true;
	}

	@Override
	@Transactional
	public AccountDealResult bindSinaWeibo(WeiboUser weiboUser,
			SinaWeiboLoginModel sinaWeiboLoginModel) {
		AccountDealResult a = new AccountDealResult();
		if (null == weiboUser || null == sinaWeiboLoginModel) {
			a.setSuccess(false);
			a.setDesc("参数不能为空");
			return a;
		}
		weiboUser.setSinaWeiboToken(sinaWeiboLoginModel.getSinaWeiboToken());
		weiboUser.setSinaWeiboUid(sinaWeiboLoginModel.getSinaWeiboUid());
		boolean isBind = checkIsAlreadyBind("sina",sinaWeiboLoginModel.getSinaWeiboUid(),weiboUser.getId());
		if(isBind){
			a.setDesc("此新浪微博帐号已绑定另一个大V彩帐号，如果要继续绑定，请先登录原帐号取消绑定。");
			a.setSuccess(false);
		} else {
			userAccountDao.updateAll(weiboUser);
			userDao.updateSinaInfo(weiboUser.getId(), weiboUser.getSinaWeiboUid(), weiboUser.getSinaWeiboToken());
			a.setDesc("绑定成功");
			a.setSuccess(true);
		}
		return a;
	}
	
	/**
	 * 检查第三方帐号是否绑定
	 * @param src "sina","qq","weixin"
	 * @param uid
	 * @return
	 */
	private boolean checkIsAlreadyBind(String src, String uid,long userId) {
		if("sina".endsWith(src)){
			UserPO userPO = userDao.findByOpenUid(uid,"sinaWeiboUid"); 
			if(null != userPO && userPO.getId() != userId){
				return true;
			}
		} else if("weixin".endsWith(src)){
			UserPO userPO = userDao.findByOpenUid(uid,"weixinUnionId");
			if(null != userPO && userPO.getId() != userId){
				return true;
			}
		} else if("qq".endsWith(src)){
			UserPO userPO = userDao.findByOpenUid(uid,"qqConnectUid");
			if(null != userPO  && userPO.getId() != userId){
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public AccountDealResult bindQQ(WeiboUser weiboUser,WeiboUser qqUserInfo) {
		AccountDealResult a = new AccountDealResult();
		if (null == weiboUser || null == qqUserInfo) {
			a.setSuccess(false);
			a.setDesc("参数不能为空");
			return a;
		}
		weiboUser.setQqConnectToken(qqUserInfo.getQqConnectToken());
		weiboUser.setQqConnectUid(qqUserInfo.getQqConnectUid());
		boolean isBind = checkIsAlreadyBind("qq",qqUserInfo.getQqConnectUid(),weiboUser.getId());
		if(isBind){
			a.setDesc("此QQ帐号已绑定另一个大V彩帐号，如果要继续绑定，请先登录原帐号取消绑定。");
			a.setSuccess(false);
		} else {
			userAccountDao.updateAll(weiboUser);
			userDao.updateQQInfo(weiboUser.getId(), weiboUser.getQqConnectUid(), weiboUser.getQqConnectToken());
			a.setDesc("绑定成功");
			a.setSuccess(true);
		}
		return a;
	}

	@Override
	@Transactional
	public List<WeiboUser> findWeiboUserByLotteryUids(String[] uids) {
		if (null == uids) {
			return new ArrayList<WeiboUser>();
		}
		List<WeiboUser> userList = new ArrayList<WeiboUser>();
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		for (String uid : uids) {
			userQueryCondition.setLotteryUserUid(Long.parseLong(uid));
			List<WeiboUser> user = userAccountDao
					.querryWeiboUser(userQueryCondition);
			if (user != null && user.size() > 0) {
				WeiboUser weiboUser = user.get(0);
				UserPO userPO = userDao.findById(Long.parseLong(uid));
				if (null == userPO) {
					continue;
				}
				try {
					BeanUtils.copyProperties(weiboUser, userPO);
				} catch (IllegalAccessException e) {
					logger.error("属性复制错误", e);
				} catch (InvocationTargetException e) {
					logger.error("属性复制错误", e);
				}
				userList.add(weiboUser);
			}
		}
		return userList;
	}

	@Override
	@Transactional
	public WeiboUser findWeiboUserByLotteryUid(String uid) {
		String[] uids = new String[] { uid };
		List<WeiboUser> weiboUsers = this.findWeiboUserByLotteryUids(uids);
		if (weiboUsers.size() > 0) {
			return weiboUsers.get(0);
		} else {
			return null;
		}
	}
	
	@Transactional
	@Override
	public WeiboUser findWeiboUserByLotteryUidsNotLoadUser(String uid) {
		WeiboUser weiboUser = new WeiboUser();
		if (StringUtils.isBlank(uid)) {
			return weiboUser;
		}
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		userQueryCondition.setLotteryUserUid(Long.parseLong(uid));
		List<WeiboUser> user = userAccountDao
				.querryWeiboUser(userQueryCondition);
		if (user != null && user.size() > 0) {
			weiboUser = user.get(0);
		}
		return weiboUser;
	}

	@Override
	@Transactional
	public String[] findWeiboUserIdsByNickNames(Set<String> nicknames) {
		String[] uids = new String[nicknames.size()];
		List<WeiboUser> weiboUsers = this.findWeiboUserByNickNames(nicknames);
		for (int i = 0; i < weiboUsers.size() && i < nicknames.size(); i++) {
			uids[i] = weiboUsers.get(i).getWeiboUserId() + "";
		}
		return uids;
	}

	@Override
	@Transactional
	public List<WeiboUser> findWeiboUserByNickNames(Set<String> nicknames) {
		List<WeiboUser> weiboUserList = new ArrayList<WeiboUser>();

		UserQueryCondition userQueryCondition = new UserQueryCondition();
		for (String nickname : nicknames) {
			userQueryCondition.setNickName(nickname);
			List<WeiboUser> tmpList = userAccountDao
					.querryWeiboUser(userQueryCondition);
			if (null != tmpList && tmpList.size() > 0) {
				weiboUserList.add(tmpList.get(0));
			}
		}
		return weiboUserList;
	}

	@Override
	@Transactional
	public List<WeiboUser> findWeiboUserByWeiboUids(String[] uids) {
		if (null == uids) {
			return new ArrayList<WeiboUser>();
		}
		List<WeiboUser> userList = new ArrayList<WeiboUser>();
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		for (String uid : uids) {
			if (null == uid || "null".equals(uid)) {
				continue;
			}
			try {
				userQueryCondition.setWeiboUid(Long.parseLong(uid));
				List<WeiboUser> user = userAccountDao
						.querryWeiboUser(userQueryCondition);
				if (user != null && user.size() > 0) {
					userList.add(user.get(0));
				}
			} catch (NumberFormatException e) {
				logger.error("uid转换错误，非法的uid={}", uid);
				continue;
			}
		}
		return userList;
	}

	@Override
	@Transactional
	public Map<Long, WeiboUser> findWeiboUserByWeiboUids(Set<Long> uids) {
		Map<Long, WeiboUser> users = new HashMap<Long, WeiboUser>();
		if (null == uids) {
			return users;
		}
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		for (long uid : uids) {
			userQueryCondition.setWeiboUid(uid);
			List<WeiboUser> user = userAccountDao
					.querryWeiboUser(userQueryCondition);
			if (user != null && user.size() > 0) {
				users.put(uid, user.get(0));
			}
		}
		return users;
	}

	@Override
	@Transactional
	public WeiboUser findWeiboUserByWeiboUid(String uid) {
		String[] uids = new String[] { uid };
		List<WeiboUser> weiboUsers = this.findWeiboUserByWeiboUids(uids);
		if (weiboUsers.size() > 0) {
			return weiboUsers.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public AccountDealResult addUserInfo(WeiboUser weiboUser) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		accountDealResult.setWeiboUser(weiboUser);

		if (!isNicknameUnique(weiboUser.getNickName())) {
			accountDealResult.setDesc("昵称重复");
			return accountDealResult;
		}

		userAccountDao.saveWeiboUser(weiboUser);
		addUserIndex(String.valueOf(weiboUser.getWeiboUserId()),
				weiboUser.getNickName());
		accountDealResult.setSuccess(true);
		accountDealResult.setDesc("用户信息补充成功");
		return accountDealResult;
	}

	@Override
	@Transactional
	public AccountDealResult updateUserInfo(WeiboUser weiboUser) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		accountDealResult.setWeiboUser(weiboUser);

		// 根据昵称查用户id
		Set<String> nicknames = new HashSet<String>();
		nicknames.add(weiboUser.getNickName());
		String[] uids = this.findWeiboUserIdsByNickNames(nicknames);

		// id不为空 且 id != 正在操作的用户id , 判定为昵称重复
		if (uids.length > 0) {
			if (null != uids[0]
					&& !(uids[0].equals(weiboUser.getWeiboUserId().toString()))) {
				accountDealResult.setDesc("昵称重复");
				return accountDealResult;
			}
		}
		if (StringUtils
				.isNotBlank(ReservedKey.getValue(weiboUser.getUsername()))) {
			accountDealResult.setDesc("非法用户名");
			return accountDealResult;
		}
		if (StringUtils
				.isNotBlank(ReservedKey.getValue(weiboUser.getNickName()))) {
			accountDealResult.setDesc("非法昵称");
			return accountDealResult;
		}
		// 更新mysql lt_user表
		userManager.updateInfo(weiboUser);
		// 更新redis数据库
		userAccountDao.updateAll(weiboUser);
		addUserIndex(String.valueOf(weiboUser.getWeiboUserId()),
				weiboUser.getNickName());
		accountDealResult.setSuccess(true);
		accountDealResult.setDesc("保存成功");
		accountDealResult.setWeiboUser(weiboUser);
		return accountDealResult;
	}

	@Override
	public String getWeiboUidByLotteryUid(String lotteryUid) {
		if (null == lotteryUid) {
			return null;
		}
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		userQueryCondition.setLotteryUserUid(Long.parseLong(lotteryUid));
		List<WeiboUser> user = userAccountDao
				.querryWeiboUser(userQueryCondition);
		if (user != null && user.size() > 0) {
			return user.get(0).getWeiboUserId() + "";
		}
		return null;
	}

	@Override
	@Transactional
	public AccountDealResult cancelSinaWeiboBind(WeiboUser weiboUser) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		userAccountDao.hdel(Keys.getUserKey(weiboUser.getWeiboUserId()),
				"sinaWeiboUid");
		userAccountDao.hdel(Keys.getUserKey(weiboUser.getWeiboUserId()),
				"sinaWeiboToken");
		userAccountDao.delete(Keys.getSinaWeiboUidKey(weiboUser.getSinaWeiboUid()));
		userDao.updateSinaInfo(weiboUser.getId(), null, null);
		accountDealResult.setDesc("取消新浪微博绑定成功");
		accountDealResult.setSuccess(true);
		return accountDealResult;
	}
	@Override
	@Transactional
	public AccountDealResult cancelQQBind(WeiboUser weiboUser) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		userAccountDao.hdel(Keys.getUserKey(weiboUser.getWeiboUserId()),
				"qqConnectUid");
		userAccountDao.hdel(Keys.getUserKey(weiboUser.getWeiboUserId()),
				"qqConnectToken");
		userAccountDao.delete(Keys.getQQConnectUidKey(weiboUser.getQqConnectUid()));
		userDao.updateQQInfo(weiboUser.getId(), null, null);
		accountDealResult.setDesc("取消QQ绑定成功");
		accountDealResult.setSuccess(true);
		return accountDealResult;
	}

	@Override
	@Transactional
	public WeiboUser getWeiboUserByEmail(String email) {
		List<UserPO> lists = userDao.getValidUsersByBindedEmail(email);
		if (lists.size() > 0) {
			UserPO userPO = lists.get(0);
			WeiboUser weiboUser = new WeiboUser();
			try {
				BeanUtils.copyProperties(weiboUser, userPO);
				return weiboUser;
			} catch (IllegalAccessException e) {
				logger.error("属性复制出错");
				return null;
			} catch (InvocationTargetException e) {
				logger.error("属性复制出错");
				return null;
			}
		}

		return null;
	}

	@Override
	public String findWeiboUserIdByNickName(String nickname) {
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		userQueryCondition.setNickName(nickname);
		List<WeiboUser> tmpList = userAccountDao
				.querryWeiboUser(userQueryCondition);
		if (null != tmpList && tmpList.size() > 0) {
			return tmpList.get(0).getWeiboUserId() + "";
		} else {
			return null;
		}
	}

	@Override
	public WeiboUser findWeiboUserByNickName(String nickName) {
		UserQueryCondition userQueryCondition = new UserQueryCondition();
		userQueryCondition.setNickName(nickName);
		List<WeiboUser> tmpList = userAccountDao
				.querryWeiboUser(userQueryCondition);
		return null != tmpList && tmpList.size() > 0 ? tmpList.get(0) : null;
	}

	@Override
	@Transactional
	public AccountDealResult registSpecialUser(WeiboUser weiboUser) {
		return doRegist(weiboUser, true);
	}

	@Override
	@Transactional
	public WeiboUser findWeiboUserByUsername(String username) {
		UserPO u = userDao.getUserByUsername(username);
		if(null != u){
			return this.findWeiboUserByLotteryUid(u.getId()+"");
		}
		return new WeiboUser();
	}

	@Override
	@Transactional
	public boolean isIdCardUnique(String idcard) {
		try {
			List<UserPO> userPOs = userDao.getValidUsersByIdNumber(idcard);
			return userPOs.size() < 1 ? true : false;
		} catch (NonUniqueResultException e) {
			logger.warn("发现数据库记录有重复的身份证号码,idcard='{}'",idcard,e);
			return false;
		}
	}

	@Override
	@Transactional
	public AccountDealResult bindWeixin(WeiboUser weiboUser,
			WeixinTokenModel weixinTokenModel) {
		AccountDealResult a = new AccountDealResult();
		if (null == weiboUser || null == weixinTokenModel) {
			a.setSuccess(false);
			a.setDesc("参数不能为空");
			return a;
		}
		weiboUser.setWeixinToken(weixinTokenModel.getAccessToken());
		weiboUser.setWeixinPCUid(weixinTokenModel.getOpenId());
		try {
			weiboUser.setWeixinUnionId(weixinService.getWeixinUser(weiboUser).getString("unionid"));
		} catch (JSONException e) {
			logger.error("获取微信unionId异常,userId={},weiboUserId={},pcOpenId={}",
					new Object[]{weiboUser.getId(),weiboUser.getWeiboUserId(),weiboUser.getWeixinPCUid()});
		}
		boolean isBind = checkIsAlreadyBind("weixin",weiboUser.getWeixinUnionId(),weiboUser.getId());
		if(isBind){
			a.setDesc("此微信帐号已绑定另一个大V彩帐号，如果要继续绑定，请先登录原帐号取消绑定。");
			a.setSuccess(false);
		} else {
			userAccountDao.updateAll(weiboUser);
			//weixinUid入参传入weiboUser.getWeixinUnionId()，因为客户端weixinUid存的是WeixinUnionId
			userDao.updateWeixinInfo(weiboUser.getId(),weiboUser.getWeixinUnionId(),
					weiboUser.getWeixinToken(), weiboUser.getWeixinUnionId(), weiboUser.getWeixinPCUid());
			a.setDesc("微信绑定成功");
			a.setSuccess(true);
		}
		return a;
	}

	@Override
	@Transactional
	public AccountDealResult cancelWeixinBind(WeiboUser user) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		userAccountDao.hdel(Keys.getUserKey(user.getWeiboUserId()),
				"weixinUid");
		userAccountDao.hdel(Keys.getUserKey(user.getWeiboUserId()),
				"weixinPCUid");
		userAccountDao.hdel(Keys.getUserKey(user.getWeiboUserId()),
				"weixinUnionId");
		userAccountDao.hdel(Keys.getUserKey(user.getWeiboUserId()),
				"weixinToken");
		userAccountDao.delete(Keys.getWeixinUidKey(user.getWeixinPCUid()));
		userDao.updateWeixinInfo(user.getId(), null, null, null, null);
		accountDealResult.setDesc("取消微信绑定成功");
		accountDealResult.setSuccess(true);
		return accountDealResult;
	}
	@Override
	@Transactional
	public AccountDealResult cancelAlipayBind(WeiboUser user) {
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		userAccountDao.hdel(Keys.getUserKey(user.getWeiboUserId()),
				"alipayUid");
		userAccountDao.hdel(Keys.getUserKey(user.getWeiboUserId()),
				"alipayToken");
		userAccountDao.delete(Keys.getAlipayUidKay(user.getAlipayUid()));
		userDao.updateAlipayInfo(user.getId(), null, null);
		accountDealResult.setDesc("取消支付宝绑定成功");
		accountDealResult.setSuccess(true);
		return accountDealResult;
	}

	@Override
	public WeiboUser getWeiboUserByUserId(Long weiboUserId) {
		return userAccountDao.get(weiboUserId);
	}
	
	@Override
	@Transactional
	public void updateOpenInfo(String src,UserPO userPO) {
		if("weixin".equals(src)){
			userDao.updateWeixinInfo(userPO.getId(), 
					userPO.getWeixinUid(), userPO.getWeixinToken(),
					userPO.getWeixinUnionId(),userPO.getWeixinPCUid());
		} else if("qq".equals(src)){
			userDao.updateQQInfo(userPO.getId(), userPO.getQqConnectUid(), userPO.getQqConnectToken());
		} else if("weibo".equals(src)){
			userDao.updateSinaInfo(userPO.getId(), userPO.getSinaWeiboUid(), userPO.getSinaWeiboToken());
		}
	}
}
