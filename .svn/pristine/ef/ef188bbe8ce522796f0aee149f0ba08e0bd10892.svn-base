package com.unison.lottery.weibo.common.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.weibo.common.nosql.LotteryDao;
import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.data.SpecialUser;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.xhcms.lottery.lang.LotteryId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-db-spring.xml")
public class LotteryServiceTest {

	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private LotteryDao lotteryDao;
	
	@Autowired
	private MessageDao msgDao;

	private WeiboMsg makeMsg() {
		WeiboMsg msg = new WeiboMsg();
		msg.setId(100);
		msg.setTitle("这场比赛出3");
		msg.setContent("看好这场比赛$周四001 阿贾克斯 VS 切尔西 (JZ1203061001)$出3，非常不错！");
		msg.setCreateTime(System.currentTimeMillis());
		return msg;
	}

	/**
	 * 扫描比赛串应该加入到对应彩种的LM时间线中。
	 */
	@Test
	public void scanMatchesShouldAddLM() {
		WeiboMsg weibo = makeMsg();
		msgDao.create(weibo);
		lotteryService.scanMatches(weibo);
		long lotteryUid = lotteryService.lotteryUserOf(LotteryId.JCZQ);
		Long rank = lotteryDao.zrank(Keys.userMatchesKey(""+lotteryUid), ""+weibo.getId());
		Assert.assertNotNull(rank);
	}
	
	@Test
	public void scanMultipleMatches() {
		WeiboMsg weibo = makeMsg();
		weibo.setContent("看好这场比赛$周四001 阿贾克斯 VS 切尔西 (JZ1203061001)$$周四001 阿贾克斯 VS 切尔西 (JL1203061002)$出3，非常不错！");
		msgDao.create(weibo);
		lotteryService.scanMatches(weibo);
		long lotteryUid = lotteryService.lotteryUserOf(LotteryId.JCZQ);
		Long rank = lotteryDao.zrank(Keys.userMatchesKey(""+lotteryUid), ""+weibo.getId());
		Assert.assertNotNull(rank);
		lotteryUid = lotteryService.lotteryUserOf(LotteryId.JCLQ);
		rank = lotteryDao.zrank(Keys.userMatchesKey(lotteryUid+""), ""+weibo.getId());
		Assert.assertNotNull(rank);
	}
	
	//@Test
	public void testIsSpecialUser(){
		long weiboUserId = 888;
		lotteryService.addLotteryUser(weiboUserId+"", LotteryId.JCZQ);
		boolean isSpecial = lotteryService.isSpecialUser(weiboUserId+"", SpecialUser.LOTTERY_USER);
		assertTrue(isSpecial);
		
		LotteryId lotteryId = lotteryService.lotteryIdOfSpecialUser(weiboUserId+"");
		assertEquals(lotteryId, LotteryId.JCZQ);
		
		long uid = lotteryService.lotteryUserOf(LotteryId.JCZQ);
		assertEquals(uid, weiboUserId);
		
		lotteryService.deleteLotteryUser(weiboUserId+"");
		uid = lotteryService.lotteryUserOf(LotteryId.JCZQ);
		assertEquals(uid, 0);

		isSpecial = lotteryService.isSpecialUser(weiboUserId+"", SpecialUser.LOTTERY_USER);
		assertFalse(isSpecial);

		lotteryId = lotteryService.lotteryIdOfSpecialUser(weiboUserId+"");
		assertEquals(lotteryId, LotteryId.UNKNOWN);
	}
	
	@Test
	public void testBeforePostForLotteryUser(){
		WeiboMsg msg = makeMsg();
		String mark = "";
		
		msg.setContent("#推荐#肯定中...");
		mark = lotteryService.beforePostForLotteryUser(msg, true);
		assertEquals(mark, "#推荐#");
		
		msg.setContent("#中奖喜报(12345)#肯定中...");
		mark = lotteryService.beforePostForLotteryUser(msg, true);
		assertEquals(mark, "#中奖喜报(12345)#");
		
		msg.setContent("网友发的#中奖喜报(12345)#肯定中...");
		mark = lotteryService.beforePostForLotteryUser(msg, true);
		assertEquals(mark, "");
	}
	
	@Test
	public void beforePostShouldCutMark(){
		WeiboMsg msg = makeMsg();
		
		msg.setContent("#推荐#肯定中...");
		lotteryService.beforePostForLotteryUser(msg, true);
		assertEquals("肯定中...", msg.getContent());
		
		msg.setContent("");
		lotteryService.beforePostForLotteryUser(msg, true);
		assertEquals("", msg.getContent());
		
		msg.setContent("#推荐#\n"+
			"$周三306 洛杉矶湖人 VS 迈阿密热火(JL20140123306)$\n"+
			"看好热火！");
		
		String mark = lotteryService.beforePostForLotteryUser(msg, true);
		assertEquals("#推荐#", mark);
		assertEquals("$周三306 洛杉矶湖人 VS 迈阿密热火(JL20140123306)$\n"+
				"看好热火！", msg.getContent());
	}
}
