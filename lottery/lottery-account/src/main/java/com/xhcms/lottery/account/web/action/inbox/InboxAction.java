package com.xhcms.lottery.account.web.action.inbox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.Message;
import com.xhcms.lottery.commons.persist.service.MessageService;

/**
 * 
 * <p>Title: 收件箱 </p>
 * @author zhuyongli
 * @version 1.0.0
 */
public class InboxAction extends BaseListAction {

	private static final long serialVersionUID = 133807831180246558L;
	
	@Autowired
	private MessageService messageService;
	
	private Long mid;
	
	private Message message;
	
	private List<Long> ids;

	@Override
	public String execute() {		
		paging = wrapPaging();
		paging.setCount(true);
		messageService.syncAndListMyMessages(paging, getUserId(), getUsername());
		
		return SUCCESS;
	}
	
	public String read() {
		message = messageService.read(mid);
		
		return SUCCESS;
	}
	
	public String remove() {
		messageService.removeMyMessage(ids);
		
		paging = wrapPaging();
		paging.setCount(true);
		messageService.listMyMessages(paging, getUserId());
		
		return SUCCESS;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
}
