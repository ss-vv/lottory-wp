package com.unison.lottery.weibo.bot.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.unison.lottery.weibo.bot.model.SinaWeiboUser;
import com.unison.lottery.weibo.bot.model.WeiboContent;
import com.unison.lottery.weibo.bot.service.RobotService;

public class RobotServiceImpl implements RobotService {
	
	private static Logger log = Logger.getLogger(RobotServiceImpl.class.getName());
	
	@Override
	public void saveSinaWeiboUser(SinaWeiboUser sinaWeiboUser) {
		log.info(sinaWeiboUser.getUid());
		log.info(sinaWeiboUser.getAccessToken());
		log.info(sinaWeiboUser.getExpires());
		log.info(sinaWeiboUser.getCreateTime());
	}

	@Override
	public List<SinaWeiboUser> getAllSinaWeiboUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveWeiboContent(WeiboContent weiboContent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveWeiboContent(List<WeiboContent> weiboContents) {
		// TODO Auto-generated method stub

	}

}
