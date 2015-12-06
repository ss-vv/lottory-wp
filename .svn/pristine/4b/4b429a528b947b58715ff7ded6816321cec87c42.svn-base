package com.unison.lottery.weibo.web.action.pc;

import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.common.service.PrivateMessageService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.vo.PrivateMessageVO;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseAction;

public class PrivateMsgAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private PageResult<PrivateMessageVO> pageResult;
	
	private int page;
	
	@Autowired
	private PrivateMessageService privateMessageService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private NotificationService notificationService;
	
	public String execute(){
		if(page < 1){
			page =1;
		}
		pageRequest.setPageSize(10);
		pageRequest.setPageIndex(page);
		
		long weiboUserId = getUserLaicaiWeiboId();
		pageResult = privateMessageService.showMyReceivedPrivateMsg(weiboUserId+"", pageRequest);
		for(PrivateMessageVO p : pageResult.getResults()) {
			p.setOwnerUser(userAccountService.findWeiboUserByWeiboUid(p.getOwnerId()+""));
		}
		pageResult.setSuccess(true);
		
		notificationService.clearUnreadPrivateMsgsTimeline(weiboUserId);
		return SUCCESS;
	}

	public PageResult<PrivateMessageVO> getPageResult() {
		return pageResult;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPage() {
		return page;
	}	
}
