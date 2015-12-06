package com.unison.lottery.weibo.msg.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.weibo.data.Comment;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.persist.dao.CommentDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-nosql-test.xml")
public class CommentServiceTest extends MsgTestBase {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private MessageService msgService;
	
	@Test
	public void testCreateComment() {
		Comment comment = createComment();
		assertNotNull(comment);
		assertNotNull(comment.getCreateTime());
		assertTrue(comment.getAuthorId()>0);
		assertTrue(comment.getWeiboMsgId()>0);
	}

	private Comment createComment() {
		WeiboMsgVO msg = msgService.publish(uid, "测试评论的微博标题", "测试评论的微博内容abc123!");
		long pid = msg.getId();
		long cid = 0;
		boolean forward = false;
		Comment comment = commentService.create(uid, pid, cid, forward, "评论内容");
		return comment;
	}

	@Test
	public void createShouldSave() {
		Comment comment = createComment();
		Comment loadedComment = commentDao.get(comment.getId());
		assertNotNull(loadedComment);
	}
}
