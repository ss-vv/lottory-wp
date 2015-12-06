package com.unison.lottery.weibo.msg.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.persist.dao.ContentManageDao;
import com.unison.lottery.weibo.msg.service.ContentManageService;


@Service
public class ContentManageServiceImpl implements ContentManageService {

	@Autowired
	private ContentManageDao contentManageDao;
	
	@Override
	public List<WeiboMsgVO> listDailyTopics(Date day) {
		return contentManageDao.listDailyTopics(day);
	}

	@Override
	public void addDailyTopic(Date dayOfTopics, String topicId) {
		contentManageDao.addDailyTopic(dayOfTopics, topicId);
	}

	@Override
	public void deleteDailyTopic(Date dayOfTopics, String topicId) {
		contentManageDao.deleteDailyTopic(dayOfTopics, topicId);
	}
}
