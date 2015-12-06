package com.xhcms.lottery.account.web.action.repeat;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.account.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.repeat.RepeatRecord;
import com.xhcms.lottery.service.RepeatService;

/**
 * @desc 查询用户的追号记录
 * @createTime 2013-8-27
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class RepeatRecordAction extends BaseListAction {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RepeatService repeatService;
	
	private int planStatus = -1;
	
	private String lotteryId;
	
	private List<RepeatRecord> repeatResultList;
	
	private long planId;
	
	private Data data;
	
	@Override
	public String execute() {
		init();
		paging.setMaxResults(10);
		try {
			long userId = getUserId();
			if(userId >= 0) {
				repeatResultList = repeatService.findRepeatPlan(lotteryId, userId, from, to, planStatus, paging);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**中止指定计划ID的追号计划*/
	public String stopRepeatPlan() {
		long userId = getUserId();
		boolean stopResult = false;
		if(userId > 0) {
			stopResult = repeatService.stopRepeatPlan(planId, userId);
		}
		data = Data.success(stopResult);
		return SUCCESS;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public int getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(int planStatus) {
		this.planStatus = planStatus;
	}

	public List<RepeatRecord> getRepeatResultList() {
		return repeatResultList;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public Data getData() {
		return data;
	}
}
