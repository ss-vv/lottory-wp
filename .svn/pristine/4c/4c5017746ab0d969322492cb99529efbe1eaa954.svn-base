package com.unison.lottery.weibo.common.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.service.TopicService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.Topic;
import com.unison.lottery.weibo.data.TopicType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.vo.TopicVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-db-spring.xml")
public class TopicServiceTest {

	@Autowired
	private TopicService topicService;
	
	@Autowired
	private MessageDao msgDao;

	private WeiboMsg makeMsg() {
		WeiboMsg msg = new WeiboMsg();
		msg.setTitle("标题党");
		msg.setContent("微博内容abc!");
		msg.setCreateTime(System.currentTimeMillis());
		return msg;
	}

	public Topic makeTopic(){
		Topic topic = new Topic();
		topic.setTitle("测试主题");
		topic.setTag("竞彩足球");
		topic.setType(TopicType.DAILY_TOPIC);
		return topic;
	}

	@Test
	public void fillVOShouldBeRecursive(){
		PageResult<TopicVO> topics = topicService.findByType(TopicType.DAILY_TOPIC, new PageRequest());
		List<TopicVO> results = topics.getResults();
		TopicVO topic = results.get(0);
		assertNotNull(topic.getWeibo());
		assertNotNull(topic.getWeibo().getUser());
		assertNotNull(topic.getWeibo().getUser().getNickName());
	}
	
	@Test
	public void testCreate() {
		Topic topic = makeTopic();
		topicService.create(topic);
		assertTrue(topic.getId()>0);
		
		TopicVO readed = topicService.get(topic.getId());
		assertEquals(topic.getTitle(), readed.getTitle());
	}

	@Test
	public void getShouldLoadReference(){
		WeiboMsg msg = makeMsg();
		msgDao.create(msg);
		
		Topic topic = makeTopic();
		topic.setWeiboId(msg.getId());
		topicService.create(topic);

		TopicVO readed = topicService.get(topic.getId());
		assertEquals(topic.getTitle(), readed.getTitle());
		WeiboMsg refWeibo = readed.getWeibo();
		assertNotNull(refWeibo);
		assertEquals(refWeibo.getTitle(), msg.getTitle());
	}
	
	@Test
	public void testFind(){
		WeiboMsg msg = makeMsg();
		msgDao.create(msg);
		
		Topic topic = makeTopic();
		topic.setWeiboId(msg.getId());
		
		topicService.create(topic);
		topicService.addToDailyTopics(topic);

		PageRequest pageRequest = new PageRequest();
		
		PageResult<TopicVO> result = topicService.findByType(TopicType.DAILY_TOPIC, pageRequest);
		
		assertNotNull(result.getPageRequest());
		assertTrue(result.getResults().size()>0);
		TopicVO topicVO = result.getResults().get(0);
		assertNotNull(topicVO.getWeibo());
		assertNotNull(topicVO.getWeibo().getTitle());
	}
	
	@Test
	public void findShouldOrderDesc(){
		WeiboMsg msg = makeMsg();
		msgDao.create(msg);
		
		Topic topic = makeTopic();
		topic.setWeiboId(msg.getId());
		
		topicService.create(topic);
		topicService.addToDailyTopics(topic);
		
		msg = makeMsg();
		msgDao.create(msg);
		
		topic = makeTopic();
		topic.setWeiboId(msg.getId());
		
		topicService.create(topic);
		topicService.addToDailyTopics(topic);
		
		PageRequest pageRequest = new PageRequest();
		PageResult<TopicVO> result = topicService.findByType(TopicType.DAILY_TOPIC, pageRequest);
		List<TopicVO> topics = result.getResults();
		assertTrue(topics.get(0).getId()>topics.get(1).getId());
	}
	
	@Test
	public void testDelete(){
		WeiboMsg msg = makeMsg();
		msgDao.create(msg);
		
		Topic topic = makeTopic();
		topic.setWeiboId(msg.getId());
		
		topicService.create(topic);
		topicService.addToDailyTopics(topic);
		
		topicService.delete(topic.getId());
		
		TopicVO deleted = topicService.get(topic.getId());
		assertNull(deleted);
		
		PageResult<TopicVO> topics = topicService.findByType(TopicType.DAILY_TOPIC, new PageRequest());
		assertNotIn(topic, topics.getResults());
	}

	private void assertNotIn(Topic topic, List<TopicVO> topics) {
		for (Topic aTopic:topics){
			if (topic.getId() == aTopic.getId()){
				fail("Topic id 不应该在本集合中！");
			}
		}
	}
	
	@Test
	public void testDeleteFromList(){
		WeiboMsg msg = makeMsg();
		msgDao.create(msg);
		
		Topic topic = makeTopic();
		topic.setWeiboId(msg.getId());
		
		topicService.create(topic);
		topicService.addToDailyTopics(topic);
		
		topicService.deleteFromList(topic);
		
		TopicVO readed = topicService.get(topic.getId());
		assertNotNull(readed);
		
		PageResult<TopicVO> topics = topicService.findByType(TopicType.DAILY_TOPIC, new PageRequest());
		assertNotIn(topic, topics.getResults());
	}
	
	@Test
	public void addToDailyTopicsShouldSetTopicType(){
		Topic topic = makeTopic();
		topic.setType(TopicType.UNKNOWN);
		
		topicService.addToDailyTopics(topic);
		
		assertEquals(topic.getType(), TopicType.DAILY_TOPIC);
	}
	
	@Test
	public void testUpdate(){
		Topic topic = makeTopic();
		topicService.create(topic);
		long initId = topic.getId();
		String initTitle = topic.getTitle();
		
		WeiboMsg msg = makeMsg();
		msgDao.create(msg);
		topic.setWeiboId(msg.getId());
		topic.setTitle("修改了的标题123456");
		topicService.update(topic);
		TopicVO readed = topicService.get(initId);
		
		assertEquals(readed.getId(), initId);
		assertNotNull(readed.getWeibo());
		assertNotEquals(readed.getTitle(), initTitle);
	}

	private void assertNotEquals(Object actual, Object expected) {
		if (actual.equals(expected)){
			fail(String.format("Should not equal, expected: %s, actual: %s", expected, actual));
		}
	}
}
