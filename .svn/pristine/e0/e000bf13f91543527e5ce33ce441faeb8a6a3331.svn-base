package com.xhcms.lottery.admin.web.action.promote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.ActivityNotify;
import com.xhcms.lottery.commons.persist.entity.ActivityNotifyPO;
import com.xhcms.lottery.service.ActivityNotifyService;

public class ActivityNotifyAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private Paging paging;
	
	private String activityName;
	
	private String activityDesc;
	
	private int status;
	
	private Long sequenceNumber;
	
	private Long activityNotifyId;
	
	private int pageNo;
	
	private Data data;
	
	@Autowired
    private ActivityNotifyService notifyService;
    
    public String list() {
    	paging = new Paging();
    	if(0 != pageNo) {
    		paging.setPageNo(pageNo);
    	}
    	List<ActivityNotifyPO> list = notifyService.listActivity(paging, activityName);
    	paging.setResults(list);
    	data = Data.success("succ");
    	
        return SUCCESS;
    }
    
    public String add() {
    	ActivityNotifyPO notify = new ActivityNotifyPO();
    	notify.setActivityName(activityName);
    	notify.setActivityDesc(activityDesc);
    	notify.setStatus(status);
    	notify.setSequenceNumber(sequenceNumber);
    	notify.setPromotionId(-1L);
    	notifyService.add(notify);
    	data = Data.success("succ");
    	
    	return SUCCESS;
    }
    
    public String modify() {
    	ActivityNotify notify = new ActivityNotify();
    	notify.setId(activityNotifyId);
    	notify.setActivityName(activityName);
    	notify.setActivityDesc(activityDesc);
    	notify.setStatus(status);
    	notify.setSequenceNumber(sequenceNumber);
    	notify.setPromotionId(-1L);
    	notifyService.modify(notify);
    	data = Data.success("succ");
    	
    	return SUCCESS;
    }
    
    public String remove() {
    	notifyService.remove(activityNotifyId);
    	data = Data.success("succ");
    	
    	return SUCCESS;
    }

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Long getActivityNotifyId() {
		return activityNotifyId;
	}

	public void setActivityNotifyId(Long activityNotifyId) {
		this.activityNotifyId = activityNotifyId;
	}

	public Paging getPaging() {
		return paging;
	}
	
	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Data getData() {
		return data;
	}
}