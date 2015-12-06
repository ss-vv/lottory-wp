package com.unison.lottery.weibo.web.action.pc.ajax;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.AjaxResult;
import com.unison.lottery.weibo.data.vo.CommentVO;
import com.unison.lottery.weibo.msg.service.CommentService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 提交评论及回复.
 * 
 * @author Yang Bo
 */
public class PostCommentAction extends BaseAction {
	private static final long serialVersionUID = -3063658912109871263L;
	
	@Autowired
	private CommentService commentService;
	
	// in  ========
	private long pid;			// 被评论微博id
	private long cid;			// 被回复的评论id，0 表示没有
	private boolean forward;	// 是否转发到我的首页
	private String comment;		// 评论内容
	
	// out ========
	private AjaxResult<CommentVO> data;		// 新生成的评论
	
	/**
	 * 列出“评论我的”
	 */
	public String execute(){
		long uid = getUserLaicaiWeiboId();
		if (uid == 0){
			return LOGIN;
		}
		CommentVO cmnt = commentService.create(uid, pid, cid, forward, comment);
		data = AjaxResult.successResult(cmnt);
		return SUCCESS;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public boolean isForward() {
		return forward;
	}

	public void setForward(boolean forward) {
		this.forward = forward;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public AjaxResult<CommentVO> getData() {
		return data;
	}

	public void setData(AjaxResult<CommentVO> data) {
		this.data = data;
	}
}
