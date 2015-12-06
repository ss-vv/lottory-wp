package com.unison.lottery.weibo.web.action.pc;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.HeadImageData;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.UserStatisService;
import com.unison.lottery.weibo.web.util.OperateImage;
import com.unison.lottery.weibo.web.util.WeiboWebConfig;
import com.xhcms.commons.util.Text;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.ucenter.persistent.service.IUserManager;

/**
 * @Description:用户资料
 * @author 江浩翔
 * @date 2013-11-25 下午5:32:33
 * @version V1.0
 */
public class UserProfileAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = -1824135599930553608L;

	private String weiboUserId;
	private String weiboNickName;

	private WeiboUser userProfile;
	private long followersNum;
	private long followingsNum;
	private long allWeiboNum;

	/** 编辑以后的对象 */
	private WeiboUser newUserProfile;
	private HeadImageData headImageData;
	private int imageWidth = Integer.parseInt(WeiboWebConfig
			.getValue("head.image.width"));
	private int imageHeight = Integer.parseInt(WeiboWebConfig
			.getValue("head.image.height"));

	/** 修改密码 */
	private String oldPassword;
	private String newPassword;
	
	private WeiboUserStatis weiboUserStatis;
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private RelationshipService relationshipService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private UserStatisService userStatisService;
	
	private AccountDealResult data = new AccountDealResult();

	public String toEditProfile() {
		user = getUser();
		return SUCCESS;
	}

	public String toEditHeadImage() {
		user = getUser();
		return SUCCESS;
	}

	public String saveProfile() {
		user = getUser();
		try {
			WeiboUser oldWeiboUser = userAccountService
					.findWeiboUserByLotteryUid(user.getId().toString());
			WeiboUser newWeiboUser = setNewProfile(oldWeiboUser);
			data = userAccountService.updateUserInfo(newWeiboUser);
			// 更新session信息
			context.getSession().put(Constant.User.USER, newWeiboUser);
			return SUCCESS;
		} catch (Exception e) {
			logger.error("保存用户信息时发生错误", e);
			data.fail("保存失败，请重试");
			return ERROR;
		}
	}

	public String saveFace() {
		user = getUser();
		if (null == headImageData
				|| null == headImageData.getHeadImageURL()
				|| headImageData.getHeadImageURL().indexOf("default_face.jpg") != -1) {
			return SUCCESS;
		}
		String rootPath = WeiboWebConfig.getValue("head.image.rootPath");
		// 截取头像地址位置
		int index = headImageData.getHeadImageURL().indexOf("head/");
		try {
			File cutImageFile = cutImage(rootPath
					+ headImageData.getHeadImageURL().substring(index));
			Thumbnails.of(cutImageFile).forceSize(imageWidth, imageHeight)
					.outputQuality(0.9f).toFile(cutImageFile);
			user.setHeadImageURL(headImageData.getHeadImageURL());
			data = userAccountService.updateHeadImage(user);
			WeiboUser newWeiboUser = userAccountService.findWeiboUserByLotteryUid(user.getId().toString());
			// 更新session信息
			context.getSession().put(Constant.User.USER, newWeiboUser);
		} catch (IOException e) {
			logger.error("头像剪切失败", e);
			return SUCCESS;
		} catch (Exception e) {
			logger.error("头像保存失败", e);
			return SUCCESS;
		}

		return SUCCESS;
	}
	public String saveHeadImageAfterRegist(){
		saveProfile();
		saveFace();
		return SUCCESS;
	}
	private File cutImage(String srcImagePath) throws IOException {
		int x = Integer.parseInt(headImageData.getX1());
		int y = Integer.parseInt(headImageData.getY1());
		int width = Integer.parseInt(headImageData.getX2()) - x;
		int height = Integer.parseInt(headImageData.getY2()) - y;

		OperateImage operateImage = new OperateImage(x, y, width, height);
		operateImage.setSrcpath(srcImagePath);
		operateImage.setSubpath(srcImagePath);
		operateImage.cut();
		return new File(srcImagePath);
	}

	private WeiboUser setNewProfile(WeiboUser oldWeiboUser) {
		if(StringUtils.isNotBlank(newUserProfile.getNickName())){
			oldWeiboUser.setNickName(newUserProfile.getNickName());
		}
		if(StringUtils.isNotBlank(newUserProfile.getProvince())){
			oldWeiboUser.setProvince(newUserProfile.getProvince());
		}
		if(StringUtils.isNotBlank(newUserProfile.getCity())){
			oldWeiboUser.setCity(newUserProfile.getCity());
		}
		if(StringUtils.isNotBlank(newUserProfile.getIndividualResume())){
			oldWeiboUser.setIndividualResume(newUserProfile.getIndividualResume());
		}
		oldWeiboUser.setGender(newUserProfile.getGender());
		return oldWeiboUser;
	}

	public String toModifyPwd() {
		user = getUser();
		return SUCCESS;
	}
	
	/**微博user浮框卡片*/
	public String userCard() {
		if(StringUtils.isBlank(weiboUserId)){
			weiboUserStatis = new WeiboUserStatis();
			return SUCCESS;
		}
		weiboUserStatis = userStatisService.getWeiboUserStatisByWeiboUserId(weiboUserId);
		loadFollowersNumAndFollowingsNum();
		weiboUserStatis.setFollowerCount((int)followersNum);
		weiboUserStatis.setFollowingCount((int)followingsNum);
		this.allWeiboNum = messageService.weiboCount(weiboUserId);
		weiboUserStatis.setWeiboCount((int)allWeiboNum);
		
		long curWeiboUserId = getUserLaicaiWeiboId();
		if(curWeiboUserId > 0){//登录
			if(weiboUserId.equals(curWeiboUserId+"")){
				weiboUserStatis.setSelf(true);//是自己
			} else {
				//判断自己是否关注了weiboUserId
				relationshipService.isFollowing(curWeiboUserId+"", weiboUserStatis);
			}
		} else {//未登录
			weiboUserStatis.setSelf(false);//不是自己
			weiboUserStatis.setBeFollowed(false);//不是粉丝
		}
		return SUCCESS;
	}

	public String modifyPwd() {
		user = getUser();
		if (!validatePasswordUpdateInput()) {
			return SUCCESS;
		}
		try {
			userManager.updatePasswd(user.getId(), oldPassword, newPassword);
		} catch (XHRuntimeException e) {
			data.fail("修改失败");
			logger.error("修改密码失败：用户uid：{}", user.getId(), e);
		}
		data.setDesc("修改成功");
		// 更新session中 user 对象的密码
		user.setPassword(Text.MD5Encode(newPassword));
		request.getSession().setAttribute(Constant.User.USER, user);
		return SUCCESS;
	}

	/**
	 * 验证用户密码更新输入的有效性
	 * 
	 * @return 有效返回true，否则返回false
	 */
	private boolean validatePasswordUpdateInput() {
		if (null == newPassword) {
			data.fail("新密码不能为空");
			return false;
		}
		if (-1 != newPassword.indexOf(" ")) {
			data.fail("密码中包含非法字符,推荐使用数字加字母组合");
			return false;
		}

		if (newPassword.length() < 6 || newPassword.length() > 15) {
			data.fail("密码长度错误：6-15个字符");
			return false;
		}

		if (!Text.MD5Encode(oldPassword).equals(user.getPassword())) {
			data.fail("输入的旧密码错误");
			return false;
		}
		return true;
	}

	/** 默认访问个人主页 不需要登录 */
	public String execute() {
		if (null == weiboUserId && null == weiboNickName) {
			return ERROR;
		}
		user = getUser();
		WeiboUser w = null;
		if (null != weiboUserId) {
			try {
				Long.parseLong(weiboUserId);
			} catch (NumberFormatException e) {
				logger.info("非法访问，weiboUserId={} 不是数值型", weiboUserId);
				return ERROR;
			}
			w = userAccountService
					.findWeiboUserByWeiboUid(weiboUserId);
		} else {
			w = userAccountService.findWeiboUserByNickName(weiboNickName);
		} 
		
		if (null == w || null == w.getId()) {
			logger.info("查询用户信息失败，weiboUserId={} 用户不存在", weiboUserId);
			return ERROR;
		}
		weiboUserId = "" + w.getWeiboUserId();
		userProfile = userAccountService.findWeiboUserByLotteryUid(w
				.getId().toString());
		
		if (null != user) {// 如果是登录用户，判断登录用户是否关注了该用户
			relationshipService.isFollowing(user.getWeiboUserId()
					.toString(), userProfile);
		}
		loadFollowersNumAndFollowingsNum();
		this.allWeiboNum = messageService.weiboCount(weiboUserId);
		return SUCCESS;
	}

	private void loadFollowersNumAndFollowingsNum() {
		Relationship relationship = relationshipService.findFollowersByUid(Long
				.parseLong(weiboUserId));
		this.followersNum = relationship.getFollowers().length;
		relationship = relationshipService.findFollowingByUid(Long
				.parseLong(weiboUserId));
		this.followingsNum = relationship.getFollowings().length;
	}

	public String getWeiboUserId() {
		return weiboUserId;
	}

	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}

	public WeiboUser getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(WeiboUser userProfile) {
		this.userProfile = userProfile;
	}

	public long getFollowersNum() {
		return followersNum;
	}

	public void setFollowersNum(long followersNum) {
		this.followersNum = followersNum;
	}

	public long getFollowingsNum() {
		return followingsNum;
	}

	public void setFollowingsNum(long followingsNum) {
		this.followingsNum = followingsNum;
	}

	public long getAllWeiboNum() {
		return allWeiboNum;
	}

	public void setAllWeiboNum(long allWeiboNum) {
		this.allWeiboNum = allWeiboNum;
	}

	public WeiboUser getNewUserProfile() {
		return newUserProfile;
	}

	public void setNewUserProfile(WeiboUser newUserProfile) {
		this.newUserProfile = newUserProfile;
	}

	public AccountDealResult getData() {
		return data;
	}

	public void setData(AccountDealResult data) {
		this.data = data;
	}

	public HeadImageData getHeadImageData() {
		return headImageData;
	}

	public void setHeadImageData(HeadImageData headImageData) {
		this.headImageData = headImageData;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setWeiboNickName(String weiboNickName) {
		this.weiboNickName = weiboNickName;
	}

	public WeiboUserStatis getWeiboUserStatis() {
		return weiboUserStatis;
	}

	public void setWeiboUserStatis(WeiboUserStatis weiboUserStatis) {
		this.weiboUserStatis = weiboUserStatis;
	}
}
