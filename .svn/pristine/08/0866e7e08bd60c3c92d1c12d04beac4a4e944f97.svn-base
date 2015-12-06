/**
 * 
 */
package com.xhcms.lottery.dc.feed.web.action.follow;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.persist.service.ShowSchemeService;
import com.xhcms.lottery.dc.feed.web.action.BaseListAction;

/**
 * @author Bean
 *
 */
public class AjaxRecommendList extends BaseListAction {
	private static final long serialVersionUID = -7348846174541693546L;

	@Autowired
	private ShowSchemeService showSchemeService;
	
	private Data data = Data.failure("failed!");
	
	@Override
	public String execute() throws Exception {
		wrapPaging();
		Date endTime = Calendar.getInstance().getTime();
		Date startTime = DateUtils.addDays(endTime, -3);
		paging.setMaxResults(10);
		paging.setCount(false);
		showSchemeService.pagingRecommendFollowShcemes(startTime, endTime, paging);
		data = Data.success(paging.getResults());
		
		return SUCCESS;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
