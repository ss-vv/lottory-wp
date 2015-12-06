package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.AjaxResult;
import com.unison.lottery.weibo.msg.service.CommentService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 删除一条评论.
 * 
 * @author Yang Bo
 */
public class DeleteCommentAction extends BaseAction {
	private static final long serialVersionUID = -3063658912109871263L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CommentService commentService;
	
	// in  ========
	private long cid;	// 被删除的评论id
	
	// out ========
	private AjaxResult<Long> data;	// 成功删除的评论
	
	/**
	 * 列出“评论我的”
	 */
	public String execute(){
		long uid = getUserLaicaiWeiboId();
		if (uid == 0){
			return LOGIN;
		}
		try{
			commentService.delete(cid, uid);
			data = AjaxResult.successResult(new Long(cid));
		}catch(Exception e){
			data = AjaxResult.failResult("-1", e.getMessage());
			logger.error("不能删除评论！", e);
		}
		return SUCCESS;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public AjaxResult<Long> getData() {
		return data;
	}

	public void setData(AjaxResult<Long> data) {
		this.data = data;
	}
}
