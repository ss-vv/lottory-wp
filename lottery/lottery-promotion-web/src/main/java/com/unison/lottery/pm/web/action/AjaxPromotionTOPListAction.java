package com.unison.lottery.pm.web.action;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.pm.data.PromotionWinResult;
import com.unison.lottery.pm.service.PromotionService;
import com.xhcms.commons.lang.Data;

/**
 * 奖上奖TOP榜单
 * @author Wang Lei
 *
 */
public class AjaxPromotionTOPListAction extends BaseAction{
	
	private static final long serialVersionUID = 1733210540172612534L;
	
	@Autowired
	private PromotionService promotionService;
	
	/**
	 * 排名列表大小
	 */
	private int top=10;
	
	/**
	 * 活动id
	 */
	private Long promotionId;
	
	/** 排行榜开始时间 */
	private Date startTime;
	/** 排行榜结束时间 */
	private Date endTime;
	/** 彩种id */
	private String lotteryId;
	/** 方案状态 */
	private int status;
	
	private Data data;

	@Override
    public String execute() throws Exception {
		if(promotionId==null)
			promotionId=1L;
		List<PromotionWinResult> results = promotionService.findPromotionWinTOP(top,promotionId);
		data = Data.success(results);
        return SUCCESS;
	}
	
	public String winTop() throws Exception{
		List<PromotionWinResult> results = promotionService.findWinTOP(top, startTime, endTime, lotteryId,status);
		data = Data.success(results);
        return SUCCESS;
	}
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public Long getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
