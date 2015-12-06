package com.unison.lottery.weibo.task;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.persist.UserAccountDao;
import com.unison.lottery.weibo.uc.util.LoadUrlImageUtil;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.lang.Constants;

public class LoadWeiboNicknameAndImageUrlTask extends Job {
	private static final Logger logger = LoggerFactory.getLogger(LoadWeiboNicknameAndImageUrlTask.class);
	
	private UserService userService;
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Override
	protected void execute() throws Exception {
		int start = 0;
		int end = 0;
		int size = 100;
		long count = userAccountDao.zcount(Keys.WEIBO_USERS, 0, 5777351607613440L);
		int pages = (int)(count/100) + (count%100==0? 0 :1); 
		for (int i = 1; i <= pages; i++) {
			start = (i-1)*100;
			end = start + size-1;
			LinkedHashSet<String> weiboUserIds = userAccountDao.zrange(Keys.WEIBO_USERS, start, end);
			logger.info("开始处理用户头像和昵称，start={},end={}",start,end);
			dealUser(weiboUserIds);
		}
		logger.info("处理用户头像和昵称完成100%..............");
	}
	private void dealUser(LinkedHashSet<String> weiboUserIds){
		for (String wid : weiboUserIds) {
			long weiboUid = Long.parseLong(wid);
			WeiboUser weiboUser = userAccountDao.hashGet(Keys.getUserKey(weiboUid));
			User user = userService.getUser(weiboUser.getId());
			if(StringUtils.isNotBlank(user.getNickName())){
				continue;
			}
			logger.info("开始处理user id={}, username={}",user.getId(),user.getUsername());
			user.setSinaWeiboUid(weiboUser.getSinaWeiboUid());
			user.setSinaWeiboToken(weiboUser.getSinaWeiboToken());
			user.setQqConnectUid(weiboUser.getQqConnectUid());
			user.setQqConnectToken(weiboUser.getQqConnectToken());
			user.setWeixinUid(weiboUser.getWeixinUid());
			user.setWeixinPCUid(weiboUser.getWeixinPCUid());
			user.setWeixinUnionId(weiboUser.getWeixinUnionId());
			user.setWeixinToken(weiboUser.getWeixinToken());
			user.setAlipayUid(weiboUser.getAlipayUid());
			user.setAlipayToken(weiboUser.getAlipayToken());
			user.setNickName(weiboUser.getNickName());
			if(null == weiboUser.getHeadImageURL()){
				user.setHeadImageURL(Constants.DEFAULT_HEAD_IMG);
			} else if(weiboUser.getHeadImageURL().indexOf("davcai") != -1){//davcai图片
				user.setHeadImageURL(weiboUser.getHeadImageURL());
			} else {//外部图片
				try {
					File imgFile = LoadUrlImageUtil.getUrlImage(weiboUser.getHeadImageURL());
					String url = LoadUrlImageUtil.saveHeadImageFile(imgFile);
					logger.info("添加新的头像地址：{}",url);
					user.setHeadImageURL(url);
				} catch (IOException e) {
					logger.error("转换微博图片失败！",e);
				}
			}
			userService.update(user);
			weiboUser.setHeadImageURL(user.getHeadImageURL());
			userAccountDao.updateHeadImage(weiboUser);
		}
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
