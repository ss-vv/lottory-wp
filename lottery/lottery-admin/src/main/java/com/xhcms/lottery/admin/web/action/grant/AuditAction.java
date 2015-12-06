/**
 * 
 */
package com.xhcms.lottery.admin.web.action.grant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.GrantService;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * @author langhsu
 *
 */
public class AuditAction extends BaseAction {
	private static final long serialVersionUID = 4123033010426210823L;
	@Autowired
	private GrantService grantService;
	
	private List<Long> ids;
	
	public String execute() {
		grantService.batchPass(ids, getMyId());
		request.setAttribute("targetPage", "/grant/list.do");
		return SUCCESS;
	}
	
	public String reject() {
		grantService.batchReject(ids, getMyId());
		request.setAttribute("targetPage", "/grant/list.do");
		return SUCCESS;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

}
