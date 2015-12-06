package com.xhcms.lottery.admin.web.action.promote;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.SysMessage;
import com.xhcms.lottery.commons.persist.entity.SysMessagePO;
import com.xhcms.lottery.commons.persist.service.MessageService;
import com.xhcms.lottery.lang.UCMessageStatus;

public class MessageAction extends BaseListAction {

	private static final long serialVersionUID = -817903227826491879L;
	
	private String subject;
	
	private String note;
	
	private int status;
	
	private Long sysMessageId;
	
	private Data data;
	
	@Autowired
    private MessageService messageService;
    
    public String list() {
    	paging = wrapPaging();
    	
    	messageService.listSysMessage(paging, subject);

    	data = Data.success("succ");
    	
        return SUCCESS;
    }
    
    public String add() {
    	SysMessagePO smPO = new SysMessagePO();
    	smPO.setSubject(subject);
    	smPO.setNote(note);
    	smPO.setAuthorid(new Long(getAdmin().getId()));
    	smPO.setAuthor(UCMessageStatus.AUTHOR);
    	smPO.setCreatedTime(new Date());
    	smPO.setStatus(status);
    	
    	messageService.add(smPO);
    	data = Data.success("succ");
    	
    	return SUCCESS;
    }
    
    public String modify() {
    	SysMessage sm = new SysMessage();
    	sm.setId(sysMessageId);
    	sm.setSubject(subject);
    	sm.setNote(note);
    	sm.setAuthorid(new Long(getAdmin().getId()));
    	sm.setAuthor(getAdmin().getUsername());
    	sm.setStatus(status);
    	
    	messageService.modify(sm);
    	data = Data.success("succ");
    	
    	return SUCCESS;
    }
    
    public String remove() {
    	messageService.remove(sysMessageId);
    	data = Data.success("succ");
    	
    	return SUCCESS;
    }


	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getSysMessageId() {
		return sysMessageId;
	}

	public void setSysMessageId(Long sysMessageId) {
		this.sysMessageId = sysMessageId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Data getData() {
		return data;
	}
}