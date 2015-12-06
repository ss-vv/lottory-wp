package com.unison.lottery.weibo.msg.persist.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.NoSQLTestBase;
import com.unison.lottery.weibo.msg.service.ContentManageService;
import com.unison.lottery.weibo.msg.service.MessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-nosql.xml")
public class ContentManageServiceTest extends NoSQLTestBase{

	@Autowired
	private ContentManageService contentManageService;
	
	@Autowired
	private MessageService msgService;
	
	@Test
	public void testAddDailyTopic() {
		
		setupWeibos();
		Date today = new Date();
		contentManageService.addDailyTopic(today, "1");
		contentManageService.addDailyTopic(today, "2");
		contentManageService.addDailyTopic(today, "3");
		
		List<WeiboMsgVO> topics = contentManageService.listDailyTopics(today);
		assertEquals(3, topics.size());
	}

	private void setupWeibos() {
		WeiboMsg weiboMsg = new WeiboMsg();
		weiboMsg.setTitle("test 1");
		msgService.publish(weiboMsg);
		
		weiboMsg = new WeiboMsg();
		weiboMsg.setTitle("test 1");
		msgService.publish(weiboMsg);

		weiboMsg = new WeiboMsg();
		weiboMsg.setTitle("test 1");
		msgService.publish(weiboMsg);
	}

	@Test
	public void testDeleteDailyTopic() {
		Date today = new Date();
		contentManageService.addDailyTopic(today, "1");
		contentManageService.addDailyTopic(today, "2");
		contentManageService.addDailyTopic(today, "3");
		
		List<WeiboMsgVO> topics = contentManageService.listDailyTopics(today);
		assertEquals(3, topics.size());
		
		contentManageService.deleteDailyTopic(today, "3");
		
		topics = contentManageService.listDailyTopics(today);
		assertEquals(2, topics.size());
	}

	@Test
	public void testListDailyTopics() {
	}

}
