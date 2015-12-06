package com.unison.lottery.weibo.common.persist.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.weibo.common.nosql.impl.TopicDaoImpl;
import com.unison.lottery.weibo.data.Topic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-db-spring.xml")
public class BaseDaoImplTest {

	@Autowired
	private TopicDaoImpl topicDao;
	
	@Test
	public void testCreate() {
		Topic topic = makeTopic();
		topicDao.create(topic);
		assertTrue(topic.getId()>0);
		
		Topic readed = topicDao.get(Topic.class, topic.getId());
		assertEquals(readed.getId(), topic.getId());
		assertEquals(readed.getDescription(), topic.getDescription());
		assertEquals(readed.getTitle(), topic.getTitle());
	}

	@Test
	public void testUpdate() {
		Topic topic = makeTopic();
		topicDao.create(topic);
		assertTrue(topic.getId()>0);
		
		Topic readed = topicDao.get(Topic.class, topic.getId());
		assertEquals(readed.getId(), topic.getId());
		assertEquals(readed.getDescription(), topic.getDescription());
		assertEquals(readed.getTitle(), topic.getTitle());
		
		readed.setTitle("Updated Version!");
		boolean updateRet = topicDao.update(readed);
		assertTrue(updateRet);
		Topic updated = topicDao.get(Topic.class, topic.getId());
		assertEquals(updated.getTitle(), readed.getTitle());
	}
	
	private Topic makeTopic() {
		Topic topic = new Topic();
		topic.setCreateTime(new Date());
		topic.setDescription("descript, 描述");
		topic.setTitle("title");
		return topic;
	}

	@Test
	public void testObjectKeyAnnotation(){
		TestUser testUser = new TestUser();
		testUser.setName(""+Math.random());
		topicDao.create(testUser);
		TestUser readed = topicDao.get(TestUser.class, testUser.getIdNotNormal());
		assertEquals(readed.getName(), testUser.getName());
	}
	
	@Test
	public void testObjectIdAnnotation(){
		TestUser testUser = new TestUser();
		testUser.setName(""+Math.random());
		topicDao.create(testUser);
		
		TestUser readed = topicDao.get(TestUser.class, testUser.getIdNotNormal());
		assertEquals(readed.getName(), testUser.getName());
		readed.setName(""+Math.random());
		topicDao.update(readed);
		
		TestUser updated = topicDao.get(TestUser.class, readed.getIdNotNormal());
		assertFalse(updated.getName().equals(testUser.getName()));
		assertEquals(updated.getName(), readed.getName());
	}
}
