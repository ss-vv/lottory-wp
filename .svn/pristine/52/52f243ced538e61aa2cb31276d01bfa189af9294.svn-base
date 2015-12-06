package com.unison.lottery.weibo.web.action.pc.marrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.LatestPublishService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * @desc 最新发表
 * @author lei.li@unison.net.cn
 * @createTime 2013-12-25
 * @version 1.0
 */
public class LatestPublishAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	Logger log = LoggerFactory.getLogger(getClass());
	private int page = 1;

	@Autowired
	private LatestPublishService lastestPublishService;
	@Autowired
	private MessageService messageService;
	public LatestPublishAction() {
		pageRequest.setPageSize(5);
	}
	
	public String show() {
		return SUCCESS;
	}

	@Override
	public String execute() {
		pageRequest.setPageIndex(page);
		
		PageResult<WeiboMsgVO> result = lastestPublishService.query(getUserLaicaiWeiboId(), pageRequest,RecentDateType.MONTH);
		//messageService.addMyFollowingData(getUserLaicaiWeiboId(), result);
		
		pageResult = result;
		pageResult.setUserId(getUserLaicaiWeiboId());
		for (Object o : pageResult.getResults()) {
			WeiboMsgVO weiboMsgVO = (WeiboMsgVO)o;
			weiboMsgVO.setLikeUsers(messageService.findLikeWeiboUser(""+weiboMsgVO.getId()));
			messageService.loadWeiboSchemeInfo(weiboMsgVO);
			messageService.loadSourceWeiboSchemeInfo(weiboMsgVO);
		}
		return SUCCESS;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}