package com.xhcms.lottery.admin.web.action.user;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.NewRegistUser;
import com.xhcms.lottery.commons.persist.service.UserService;

public class NewRegUsersStatAction extends BaseListAction {

    private static final long serialVersionUID = 8668189806299243579L;
    
    @Autowired
    private UserService userService;
 
    private Collection<NewRegistUser> data;
    
    private String channel;
    
    @Override
    public String execute(){
    	try {
    		initSearchCondition();
			data = userService.findNewRegUserGroupByPid(from, to, channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return SUCCESS;
    }

	private void initSearchCondition() {
		List<Date> beginEnd = com.xhcms.lottery.utils.DateUtils.getDayBeginAndEnd();
		if(from == null) {
			from = DateUtils.addDays(beginEnd.get(0), -7);
		}
		if(to == null) {
			to = beginEnd.get(1);
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(to);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			to = cal.getTime();
		}
	}
	
	public Collection<NewRegistUser> getData() {
		return data;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}