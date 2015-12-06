package com.unison.lottery.weibo.msg.service;

import java.util.List;

import com.unison.lottery.weibo.data.Comment;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.vo.CommentVO;
import com.unison.lottery.weibo.exception.ServiceException;



/**
 * 关于微博评论及回复的服务。
 * 
 * @author Yang Bo
 */
public interface CommentService {
	
	/**
	 * 创建一条评论或回复。
	 * @param authorId 
	 * @param pid 被评论的微博id
	 * @param cid 被回复的评论id
	 * @param forward true转发到我的首页；false不转发
	 * @param comment 
	 * 
	 * @return 新生成的评论
	 */
	public CommentVO create(long authorId, long pid, long cid, boolean forward, String comment);
	
	/**
	 * 修改评论
	 * @param comment
	 */
	public void update(Comment comment);
	
	/**
	 * 删除评论、回复
	 * @param commentId 被删除的评论、回复的id
	 * @param uid 
	 */
	public void delete(long commentId, long uid) throws ServiceException; 

	/**
	 * 列出某微博的所有评论
	 * @param postId 微博id
	 * @return 评论列表，按时间排序，新的在前。
	 */
	//public PageResult<Comment> listCommentsOfPost(long postId, PageRequest page);
	
	/**
	 * 赞某评论
	 * @param commentId 评论id
	 * @param uid 
	 * @param isAdd 
	 */
	public void praise(long commentId, long uid, boolean isAdd);
	
	/**
	 * 列出某用户的“评论我的”所有评论和回复
	 * @param uid 用户uid
	 * @return 评论、回复列表
	 */
	public PageResult<CommentVO> listAllCommentMe(long uid, PageRequest page);
	
	/**
	 * 列出某用户的“评论我的”所有评论和回复
	 * @param uid 用户uid
	 * @return 评论、回复列表
	 */
	public PageResult<CommentVO> listMyComments(long uid, PageRequest page);
	
	/**
	 * 列出某用户的直接回复。
	 * @param uid
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResult<CommentVO> listDirectReplies(long uid, PageRequest page);
	
	/**
	 * 列出我关注的人给我的评论和回复。
	 * @param uid
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResult<CommentVO> listCommentsOfMyInterested(long uid, PageRequest page);

	/**
	 * 列出某微博消息的所有评论。
	 * @param currentUserId 
	 * @param weiboMsg
	 * @param LcowOrder List Comments Of WeiboMsg order.
	 * @return 评论列表
	 */
	public List<CommentVO> listCommentsOfWeiboMsg(long postId, LcowOrder order, long currentUserId);

	public boolean isUserPraisedComment(long uid, long cid);
}
