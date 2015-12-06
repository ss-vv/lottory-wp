package com.unison.lottery.weibo.msg.service.johm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.johm.JOhm;

import com.unison.lottery.weibo.NoSQLTestBase;
import com.unison.lottery.weibo.msg.model.UserRelationship;
import com.unison.lottery.weibo.msg.model.WeiboUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-nosql.xml")
public class WeiboServiceImplTest extends NoSQLTestBase{
	
	@Autowired WeiboService weiboService;

	@Test
	public void testCreateWeiboUser() {
		WeiboUser weiboUser = makeWeiboUser();
		weiboService.createWeiboUser(weiboUser);
		assertTrue(JOhm.getId(weiboUser).equals("1"));
		
		WeiboUser readed = weiboService.findWeiboUser(weiboUser.getWeiboUid());
		assertEquals(Long.valueOf(1), readed.getWeiboUid());
		assertEquals(weiboUser, readed);
		
		weiboUser = makeWeiboUser();
		weiboService.createWeiboUser(weiboUser);
		assertTrue(JOhm.getId(weiboUser).equals("2"));
	}

	private WeiboUser makeWeiboUser() {
		WeiboUser weiboUser = new WeiboUser();
		weiboUser.setAddress("北京");
		weiboUser.setAnswer("答案");
		weiboUser.setBirthday(new Date());
		weiboUser.setCity("北京");
		weiboUser.setCreatedTime(new Date());
		weiboUser.setEmail("bob.yang@unison.net.cn");
		weiboUser.setFamiliarLottery("杨");
		weiboUser.setGender(1);
		weiboUser.setHeadImageURL("http://photo.xueqiu.com/community/20137/1377081650697.png!100x100.png");
		weiboUser.setNickName("微风好飞行");
		weiboUser.setIndividualResume("个人简介");
		weiboUser.setFamiliarLottery("双色球");
		weiboUser.setWeiboUserCreateTime(new Date());
		weiboUser.setSinaWeiboUid("1234");
		return weiboUser;
	}

	@Test
	public void testUpdateWeiboUser() {
		WeiboUser weiboUser = makeWeiboUser();
		weiboService.createWeiboUser(weiboUser);
		Long uid = weiboUser.getWeiboUid();
		WeiboUser readed = weiboService.findWeiboUser(uid);
		readed.setNickName("new nick");
		weiboService.updateWeiboUser(readed);
		WeiboUser updated = weiboService.findWeiboUser(uid);
		assertFalse(weiboUser.equals(updated));
		assertEquals("new nick", updated.getNickName());
	}

	// tested in other tests
	public void testFindWeiboUser() {
	}

	@Test
	public void testFindRelationship() {
		WeiboUser me = makeWeiboUser();
		WeiboUser followed = makeWeiboUser();
		followed.setNickName("Xiao Ming");
		weiboService.createWeiboUser(me);
		weiboService.createWeiboUser(followed);
		weiboService.following(me, followed);	// 我关注小明
		UserRelationship myRelation = weiboService.findRelationship(me);  
		UserRelationship followedRelation = weiboService.findRelationship(followed);
//		assertEquals(myRelation.getFollowedUsers().get(0), followed);
//		assertEquals(followedRelation.getFans().get(0), me);
		// TODO
	}

	public void testFollowing() {
		fail("Not yet implemented");
	}

	public void testUnFollowing() {
		fail("Not yet implemented");
	}

	public void testCreatePost() {
		fail("Not yet implemented");
	}

	public void testUpdatePost() {
		fail("Not yet implemented");
	}

	public void testDeletePost() {
		fail("Not yet implemented");
	}

	public void testFindPost() {
		fail("Not yet implemented");
	}

	public void testForwardPost() {
		fail("Not yet implemented");
	}

	public void testFindUserPosts() {
		fail("Not yet implemented");
	}

	public void testCreateComment() {
		fail("Not yet implemented");
	}

	public void testCreateReply() {
		fail("Not yet implemented");
	}

	public void testUpdateComment() {
		fail("Not yet implemented");
	}

	public void testDeleteComment() {
		fail("Not yet implemented");
	}

	public void testFindPostComments() {
		fail("Not yet implemented");
	}

	public void testFindUserComments() {
		fail("Not yet implemented");
	}

}
