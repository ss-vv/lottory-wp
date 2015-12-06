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
public class CustomMadeListAction extends BaseListAction {
	private static final long serialVersionUID = 7413422113189331598L;
	private Integer type;
	
	@Autowired
	private CustomMadeService customMadeService;
	
	@Override
	public String execute() throws Exception {
		if(type != null && type == 1) {
			return listCustomeMadeMe();
		} else {
			return listMyCustomMade();
		}
	}

	private String listCustomeMadeMe() {
		paging = wrapPaging();
		paging.setCount(true);
		customMadeService.listCustomMades(1, getUserId(), paging);
		return "cmmelist";
	}

	private String listMyCustomMade() {
		paging = wrapPaging();
		paging.setCount(true);
		customMadeService.listCustomMades(0, getUserId(), paging);
		return "mycmlist";
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
