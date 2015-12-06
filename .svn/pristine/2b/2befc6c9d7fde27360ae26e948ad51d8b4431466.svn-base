/**
 * 
 */
package com.xhcms.lottery.dc.feed.web.action.groupbuy;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.persist.service.PlayService;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.dc.feed.persist.service.GroupBuySchemeService;
import com.xhcms.lottery.dc.feed.web.action.BaseListAction;

/**
 * @author Bean
 *
 */
public class AjaxRecommendList extends BaseListAction {
	private static final long serialVersionUID = 4570575937301306036L;

	@Autowired
	private GroupBuySchemeService groupBuyService;
	
	private Data data = Data.failure("failed!");
	
	@Override
	public String execute() throws Exception {
		wrapPaging();
		paging.setMaxResults(10);
		
		Date endTime = Calendar.getInstance().getTime();
		Date startTime = DateUtils.addDays(endTime, -3);
		
		groupBuyService.pagingRecommendGroupBuyShcemes(null, null, null, 
				"createdTime", false, startTime, endTime, paging);
		
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
