package com.unison.lottery.weibo.msg.persist.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.ContentManageService;
import com.unison.lottery.weibo.msg.service.MessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-nosql.xml")
public class ContentManageTestData {

	@Autowired
	private ContentManageService cmService;
	
	@Autowired
	private MessageService msgService;
	
	@Test
	public void addAllPostsIntoTodayTopics(){
		Date dayOfTopics = new Date();
		PageRequest pageRequest = new PageRequest();
		PageResult<WeiboMsgVO> result = msgService.findAllPost(18, pageRequest);
		for (WeiboMsgVO msg : result.getResults()){
			cmService.addDailyTopic(dayOfTopics, msg.getId()+"");
		}
		System.out.println("添加了"+result.getResults().size()+"条话题");
	}
}
