package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.AjaxResult;
import com.unison.lottery.weibo.data.vo.CommentVO;
import com.unison.lottery.weibo.msg.service.CommentService;
import com.unison.lottery.weibo.msg.service.LcowOrder;
import com.unison.lottery.weibo.web.action.BaseAction;

@SuppressWarnings("serial")
public class ListCommentsAction extends BaseAction {

	@Autowired
	private CommentService commentService;
	
	// -------- in ---------
	private long pid;
	private LcowOrder order;
	
	// -------- out --------
	private AjaxResult<List<CommentVO>> data;
	
	@Override
	public String execute() throws Exception {
		long currentUserId = getUserLaicaiWeiboId();
		List<CommentVO> comments = commentService.listCommentsOfWeiboMsg(pid, order, currentUserId);
		data = AjaxResult.successResult(comments);
		data.setUserId(currentUserId);
		return SUCCESS;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public LcowOrder getOrder() {
		return order;
	}

	public void setOrder(LcowOrder order) {
		this.order = order;
	}

	public AjaxResult<List<CommentVO>> getData() {
		return data;
	}

	public void setData(AjaxResult<List<CommentVO>> data) {
		this.data = data;
	}

}
