package com.unison.lottery.weibo.common.service.impl;

import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.unison.lottery.weibo.common.redis.RedisException;
import com.unison.lottery.weibo.common.service.PrivateMessageService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.PrivateMessage;

public class PrivateMessageServiceTest {

	private static ApplicationContext ctx;

	private PrivateMessageService privateService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@BeforeClass
	public static void setUpBeforeClass() {
		ctx = new ClassPathXmlApplicationContext(
				new String[] { "test-db-spring.xml" });
	}

	@Before
	public void setUp() {
		privateService = ctx.getBean(PrivateMessageService.class);
		Assert.assertNotNull(privateService);
	}

	@Ignore
	@Test
	public void testSendPrivateLetter() {
		try {
			PrivateMessage letter = new PrivateMessage();
			letter.setOwnerId(115L);
			letter.setPeer(116L);
			letter.setContent("1.发送一条私信...");
			letter.setCreateTime(new Date());
			boolean isSucc = privateService.sendPrivateMessage(letter);
			Assert.assertTrue(isSucc);
		} catch (RedisException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testReplyPrivateLetter() {
		try {
			long userId = 116L;
			long responseId = 115L;
			String replyCont = "回复：1.发送一条私信...";
			boolean isSucc = privateService.replyPrivateMessage(userId,
					responseId, replyCont);
			Assert.assertTrue(isSucc);
		} catch (RedisException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testShowSelfPrivateLetters() {
		try {
			long userId = 116L;
			logger.info("开始加载用户ID:{}的个人私信列表：", userId);
			PageRequest pageRequest = new PageRequest();
			PageResult<PrivateMessage> pageResult = privateService
					.showSelfPrivateMessageList(userId, pageRequest);
			List<PrivateMessage> letterList = pageResult.getResults();
			if (null != letterList) {
				for (PrivateMessage letter : letterList) {
					System.out.println(letter);
				}
			}
		} catch (RedisException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testShowSessionWithUser() {
		try {
			long userId = 116L;
			long peerId = 115L;
			logger.info("显示用户:{}和用户:{}的私信对话内容", new Object[] { userId, peerId });
			PageRequest pageRequest = new PageRequest();
			PageResult<PrivateMessage> pageResult = privateService.showSessionWithUser(
					userId, peerId, pageRequest);
			List<PrivateMessage> letterList = pageResult.getResults();
			if (null != letterList) {
				for (PrivateMessage letter : letterList) {
					System.out.println(letter);
				}
			}
		} catch (RedisException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testCalPrivateCountWithUser() {
		try {
			long userId = 116L;
			long peerId = 115L;
			logger.info("用户:{}和用户:{}的私信对话数量", new Object[] { userId, peerId });
			long sessionCountWithUser = privateService.calPrivateCountWithUser(
					userId, peerId);
			System.out.println(sessionCountWithUser);
		} catch (RedisException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDeleteById() {
		try {
			long userId = 115L;
			long peerId = 116L;
			long privateLetterId = 25;
			logger.info("删除用户:{}和用户:{}之间的私信：{}", new Object[] { userId, peerId,
					privateLetterId });
			privateService.deleteById(userId, peerId, privateLetterId);
		} catch (RedisException e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testDeleteSession() {
		try {
			long userId = 116L;
			long peerId = 115L;
			logger.info("删除用户:{}和用户:{}之间的私信", new Object[] { userId, peerId });
			int ret = privateService.deleteAllSession(userId, peerId);
			System.out.println(ret);
		} catch (RedisException e) {
			e.printStackTrace();
		}
	}
}