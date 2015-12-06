package com.unison.lottery.weibo.web.action.pc;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.msg.service.RealWeiboService;
import com.unison.lottery.weibo.web.action.BaseAction;

public class RealAndFollowWeiboAction extends BaseAction {
	private static final long serialVersionUID = -6612931213371678611L;
	private String lottery;
	private String timeSort;
	private String buyAmountSort;
	private String followCountSort;
	private String rateOfReturnSort;
	private int recentDays;
	
	@Autowired
	private RealWeiboService realWeiboService;
	@Autowired
	private MessageService messageService;
	private int page = 1;
	
	public String category() {
		if(StringUtils.isNotBlank(lottery)){
			if(!lottery.equals("ALL") &&
					!lottery.equals("JCLQ") &&
					!lottery.equals("JCZQ") &&
					!lottery.equals("BJDC")){
				return ERROR;
			}
		} else {
			return ERROR;
		}
		dealSortData();
		return SUCCESS;
	}
	
	public String ajax() {
		if(StringUtils.isNotBlank(lottery)){
			if(!lottery.equals("ALL") &&
					!lottery.equals("JCLQ") &&
					!lottery.equals("JCZQ") &&
					!lottery.equals("BJDC")){
				return ERROR;
			}
		} else {
			return ERROR;
		}
		dealSortData();
		pageResult = realWeiboService.findRealWeiboByRecentDaysAndSort(pageRequest,recentDays, followCountSort, buyAmountSort, rateOfReturnSort, timeSort,lottery);
		pageResult.setUserId(getUserLaicaiWeiboId());
		return SUCCESS;
	}

	/**
	 * 处理排序数据
	 */
	private void dealSortData(){
		if(StringUtils.isBlank(followCountSort) &&
				StringUtils.isBlank(timeSort) &&
				StringUtils.isBlank(buyAmountSort) &&
				StringUtils.isBlank(rateOfReturnSort)){//排序选项全为空
			timeSort = PageRequest.SORT_ORDER_DESC;//默认时间降序
		}
		if(recentDays != 3 && recentDays != 7 && recentDays != 30 && recentDays != 1){
			recentDays = 1;
		}
		pageRequest.setPageIndex(page);
	}
	
	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public String getTimeSort() {
		return timeSort;
	}

	public void setTimeSort(String timeSort) {
		this.timeSort = timeSort;
	}

	public String getBuyAmountSort() {
		return buyAmountSort;
	}

	public void setBuyAmountSort(String buyAmountSort) {
		this.buyAmountSort = buyAmountSort;
	}

	public String getFollowCountSort() {
		return followCountSort;
	}

	public void setFollowCountSort(String followCountSort) {
		this.followCountSort = followCountSort;
	}

	public String getRateOfReturnSort() {
		return rateOfReturnSort;
	}

	public void setRateOfReturnSort(String rateOfReturnSort) {
		this.rateOfReturnSort = rateOfReturnSort;
	}

	public int getRecentDays() {
		return recentDays;
	}

	public void setRecentDays(int recentDays) {
		this.recentDays = recentDays;
	}
}