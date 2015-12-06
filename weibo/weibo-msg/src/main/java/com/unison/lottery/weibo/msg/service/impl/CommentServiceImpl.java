package com.unison.lottery.weibo.msg.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.filefilter.MagicNumberFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.common.service.impl.BaseServiceImpl;
import com.unison.lottery.weibo.data.AjaxResult;
import com.unison.lottery.weibo.data.Comment;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.vo.CommentVO;
import com.unison.lottery.weibo.exception.DaoException;
import com.unison.lottery.weibo.exception.ServiceException;
import com.unison.lottery.weibo.msg.persist.dao.CommentDao;
import com.unison.lottery.weibo.msg.service.CommentService;
import com.unison.lottery.weibo.msg.service.LcowOrder;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.persist.RelationshipDao;
import com.unison.lottery.weibo.uc.persist.UserAccountDao;
import com.unison.lottery.weibo.uc.service.UserAccountService;

/**
 * 评论服务
 * 
 * @author Yang Bo
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl implements CommentService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private MessageService msgService;
	
	@Autowired
	private UserAccountDao userDao;
	
	@Autowired
	private RelationshipDao relDao;
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private NotificationService notificationService;

	@Override
	public void update(Comment comment) {
		commentDao.update(comment);
	}

	@Override
	public void praise(long commentId, long uid, boolean isAdd) {
		commentDao.praise(""+commentId, ""+uid, isAdd);
	}

	@Override
	public PageResult<CommentVO> listAllCommentMe(long uid, PageRequest page) {
		int start = page.getOffset();
		int limit = page.getPageSize();
		List<Comment> comments = commentDao.allCommentsOfUser(""+uid, start, limit);
		long total = commentDao.countAllComments(uid);
		return composePageResult(comments, total, page);
	}

	private PageResult<CommentVO> composePageResult(
			List<Comment> comments, long total, PageRequest pageRequest) {
		List<CommentVO> commentsVO = new LinkedList<>();
		for (Comment theComment : comments) {
			commentsVO.add(fillVO(theComment, CommentVO.class));
		}
		PageResult<CommentVO> result = new PageResult<>(pageRequest, commentsVO);
		result.setTotalResults(total);
		return result;
	}

	private CommentVO loadCommentVO(Comment theComment) {
		CommentVO vo = new CommentVO();
		try {
			BeanUtils.copyProperties(vo, theComment);
			loadComment(vo);
		} catch (Exception e) {
			logger.error("Can not copy Comment to CommentVO!\n {}",
					theComment, e);
		}
		return vo;
	}

	private void loadComment(CommentVO vo) {
		WeiboUser author = userDao.get(vo.getAuthorId());
		vo.setAuthor(author);
	}

	@Override
	public PageResult<CommentVO> listDirectReplies(long uid, PageRequest page) {
		int start = page.getOffset();
		int limit = page.getPageSize();
		List<Comment> comments = commentDao.directRepliesOfUser(""+uid, start, limit);
		long total = commentDao.countDirectReplies(uid);
		return composePageResult(comments, total, page);
	}

	@Override
	public PageResult<CommentVO> listCommentsOfMyInterested(long uid, PageRequest page) {
		int start = page.getOffset();
		int limit = page.getPageSize();
		List<Comment> comments = commentDao.followingCommentsOfUser(""+uid, start, limit);
		long total = commentDao.countFollowingComments(uid);
		return composePageResult(comments, total, page);
	}

	@Override
	protected BaseDao<?> getBaseDao() {
		return commentDao;
	}

	@Override
	public CommentVO create(long authorId, long pid, long cid, boolean forward, String commentContent) {
		if(StringUtils.isBlank(commentContent)){
			return new CommentVO(); 
		}
		Comment comment = new Comment();
		comment.setContent(commentContent);
		comment.setWeiboMsgId(pid);
		comment.setRepliedCommentId(cid);
		comment.setCreateTime(new Date());
		comment.setAuthorId(authorId);
		Comment repliedComment = null;
		WeiboMsgVO post = messageService.getWeiboVoById(pid);
		if (cid > 0) {
			repliedComment = commentDao.get(cid);
			comment.setRepliedUserId(repliedComment.getAuthorId());
		} else {
			comment.setRepliedUserId(post.getOwnerId());
		}
		create(comment);
		if (forward){
			forwordComment(comment,repliedComment,post);
		}
		CommentVO vo = loadCommentVO(comment);
		return vo;
	}

	private void forwordComment(Comment comment,Comment repliedComment,WeiboMsgVO post) {
		WeiboMsg weiboMsg = new WeiboMsg();
		weiboMsg.setOwnerId(comment.getAuthorId());
		weiboMsg.setPostId(comment.getWeiboMsgId());
		String commentContent = comment.getContent();
		if(null != repliedComment){
			WeiboUser repliedUser = userAccountService.findWeiboUserByWeiboUid(repliedComment.getAuthorId()+"");
			commentContent += "//@" + repliedUser.getNickName() + ":" + repliedComment.getContent();
		}
		if(post.getPostId() > 1 && StringUtils.isNotBlank(post.getContent())){
			commentContent += "//@" + post.getUser().getNickName() + ":" + post.getContent();
		}
		weiboMsg.setContent(commentContent);
		msgService.forward(weiboMsg);
	}

	private void create(Comment comment) throws DaoException {
		// 创建评论、回复
		long commentId = commentDao.create(comment).getId();
		// 添加到微博的评论列表
		commentDao.addToCommentsOfPost(""+comment.getWeiboMsgId(), ""+commentId);
		// 增加微博评论数
		msgService.increaseCommentCount(comment.getWeiboMsgId());
		// 添加到被评论者的"评论我的"列表
		long commentedUserId = comment.getRepliedUserId();
		commentId = comment.getId();
		commentDao.addToAllComments(""+commentedUserId, ""+commentId);
		// 添加到“直接回复”列表. 即对我的评论进行的回复
		if (comment.getRepliedCommentId() > 0){
			commentDao.addToDirectReplies(commentedUserId, commentId);
		}
		// 添加到“我关注的人”列表
		if (relDao.isFollowing(""+comment.getAuthorId(), ""+commentedUserId)){
			commentDao.addToFollowingComments(commentedUserId, commentId);
		}
		// 添加到“我的评论”列表
		commentDao.addToMyComments(comment.getAuthorId(), commentId);
		
		//添加通知到“评论我的”列表
		//A->B的微博->通知B被评论(user:B:comments->commentList)
		//A->B的微博中C的评论->要通知C
		if(comment.getAuthorId() != comment.getRepliedUserId()){//不是评论自己，才通知
			notificationService.addUnreadCommentions(comment.getRepliedUserId(), commentId);
		}
	}

	@Override
	public void delete(long commentId, long uid) throws ServiceException {
		Comment cmnt = commentDao.get(commentId);
		if (cmnt.getAuthorId() != uid){
			throw new ServiceException("只能删除自己的评论！");
		}
		// 从微博的评论列表中删除
		commentDao.deleteFromCommentsOfPost(""+cmnt.getWeiboMsgId(), ""+commentId);
		// 减少微博评论数
		msgService.decreaseCommentCount(cmnt.getWeiboMsgId());
		// 从被评论者的"评论我的"列表中删除
		long commentedUserId = cmnt.getRepliedUserId();
		commentId = cmnt.getId();
		commentDao.deleteFromAllComments(""+commentedUserId, ""+commentId);
		// 从“直接回复”列表删除
		if (cmnt.getRepliedCommentId() > 0){
			commentDao.deleteFromDirectReplies(commentedUserId, commentId);
		}
		// 从“我关注的人”列表删除
		if (relDao.isFollowing(""+cmnt.getAuthorId(), ""+commentedUserId)){
			commentDao.deleteFollowingComments(commentedUserId, commentId);
		}
		// 从“我的评论”列表删除
		commentDao.deleteFromMyComments(cmnt.getAuthorId(), commentId);
		
		// 删除评论的同时, 将评论的通知从对应微博用户的“未读评论我的”时间线中删除
		notificationService.deleteUnreadComment(cmnt.getWeiboMsgId(), commentId);
		
		// 删除评论对象
		commentDao.destroy(commentId);
	}
	
	@Override
	public List<CommentVO> listCommentsOfWeiboMsg(long postId, LcowOrder order, long currentUserId) {
		List<CommentVO> cmnts = getAllCommentsOfWeiboMsg(postId, currentUserId);
		fillPraiseInfo(cmnts, currentUserId);
		if (order == null){
			order = LcowOrder.MOST_RECENT;
		}
		Comparator<CommentVO> sorter = order.comparator();
		Collections.sort(cmnts, sorter);
		return cmnts;
	}

	private void fillPraiseInfo(List<CommentVO> cmnts, long currentUserId) {
		if (currentUserId>0){
			for (CommentVO cmntVo : cmnts){
				cmntVo.setPraised(isUserPraisedComment(currentUserId, cmntVo.getId()));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<CommentVO> getAllCommentsOfWeiboMsg(long postId, long currentUserId) {
		if (postId == 0){
			return Collections.EMPTY_LIST;
		}
		String postIdStr = ""+postId;
		List<String> commentIds = commentDao.getCommentsOfPost(postIdStr);
		
		List<Comment> cmnts = commentDao.get(commentIds.iterator());
		List<CommentVO> cmntsVo = copyComments(cmnts, currentUserId);
		return cmntsVo;
	}

	private List<CommentVO> copyComments(List<Comment> cmnts, long uid) {
		LinkedList<CommentVO> cmntsVo = new LinkedList<>();
		for(Comment comment : cmnts) {
			CommentVO filled = fillVO(comment, CommentVO.class);
			long commentId = filled.getId();
			filled.setPraised(isUserPraisedComment(uid, commentId));
			filled.setPraisedCount(commentDao.scard(Keys.praiseCommentUserKey(""+commentId)));
			cmntsVo.add(filled);
		}
		return cmntsVo;
	}

	@Override
	public boolean isUserPraisedComment(long uid, long cid) {
		return commentDao.sismember(Keys.praiseCommentUserKey(""+cid), ""+uid);
	}

	@Override
	public PageResult<CommentVO> listMyComments(long uid, PageRequest page) {
		int start = page.getOffset();
		int length = page.getPageSize();
		List<Comment> comments = commentDao.myComments(""+uid, start, length);
		long total = commentDao.countMyComments(uid);
		return composePageResult(comments, total, page);
	}
}
