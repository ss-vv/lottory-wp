package com.unison.lottery.weibo.web.action.pc.ajax;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.AjaxResult;
import com.unison.lottery.weibo.msg.service.CommentService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 评论的赞服务。
 * 
 * @author Yang Bo
 */
public class CommentPraiseAction extends BaseAction {

	private static final long serialVersionUID = -2736837862168918060L;

	@Autowired
	private CommentService commentService;
	
	// ----------------- in -------------------
	private long cid;		// comment id
	private boolean add;	// true赞，false 取消赞
	
	private AjaxResult<Boolean> prsCmntResult;
	
	/**
	 * @return true uid用户赞过评论cid.
	 */
	public String praiseComment() {
		long uid = getUserLaicaiWeiboId();
		if (uid==0){
			prsCmntResult = AjaxResult.failResult("0", "未登录");
		}else{
			commentService.praise(cid, uid, add);
			prsCmntResult = AjaxResult.successResult(true);
		}
		return SUCCESS;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public AjaxResult<Boolean> getPrsCmntResult() {
		return prsCmntResult;
	}

	public void setPrsCmntResult(AjaxResult<Boolean> prsCmntResult) {
		this.prsCmntResult = prsCmntResult;
	}
}
