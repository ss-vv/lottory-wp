/**
 * 
 */
package com.xhcms.lottery.account.web.action.custommade;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.CustomMadeService;

/**
 * @author Bean.Long
 *
 */
public class CustomMadeDetailListAction extends BaseListAction {
	private static final long serialVersionUID = -6553082486929031884L;
	
	private Long fuid;
	
	@Autowired
	private CustomMadeService customMadeService;
	
	@Override
	public String execute() throws Exception {
		paging = wrapPaging();
		paging.setCount(true);
		
		if(fuid == null)
			fuid = 0L;
		
		customMadeService.listCustomMadeDetails(fuid, getUserId(), paging);
		return SUCCESS;
	}	
	
	public String followed() throws Exception {
		paging = wrapPaging();
		paging.setCount(true);
		if(fuid == null)
			fuid = 0L;
		
		customMadeService.listCustomMadeDetails(getUserId(), fuid, paging);
		
		return SUCCESS;
	}

	public Long getFuid() {
		return fuid;
	}

	public void setFuid(Long fuid) {
		this.fuid = fuid;
	}
}
