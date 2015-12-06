package com.unison.lottery.weibo.web.action.pc;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.laicai.lotteryCategory.LotteryCategoryEnum;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.msg.service.RecommendService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.lottery.lang.LotteryId;

public class RecommendAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private MessageService messageService;
	
	private String lottery;
	
	private int page = 1;
	
	private String sortTime;
	
	public String getSortTime() {
		return sortTime;
	}

	public void setSortTime(String sortTime) {
		this.sortTime = sortTime;
	}

	private List<WeiboUser> analyzeTalentUsers;
	
	@Autowired
	private RelationshipService relationshipService;
	
	public RecommendAction() {
		pageRequest.setPageSize(10);
	}
	
	public String show() {
		return SUCCESS;
	}
	
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
		if(!PageRequest.SORT_ORDER_DESC.equals(sortTime) && 
			!PageRequest.SORT_ORDER_ASC.equals(sortTime)){
			sortTime = PageRequest.SORT_ORDER_DESC;
		}
		try {
			pageRequest.setPageIndex(page);
			pageRequest.setSortOrder(sortTime);
			pageResult = messageService.findRecommendListByLotteryCategory(lottery, 
					pageRequest);
			pageResult.setUserId(getUserLaicaiWeiboId());
			pageResult.setTotalResults(pageResult.getPageRequest().getRecordCount());
		} catch (Exception e) {
			log.error("查询彩种={},推荐列表查询失败：{}", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getAnalyzeTalentUser(){
		analyzeTalentUsers = recommendService.findAnalyzeTalent();
		analyzeTalentUsers = relationshipService.isFollowing(""+getUserLaicaiWeiboId(), analyzeTalentUsers);
		return SUCCESS;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}

	public List<WeiboUser> getAnalyzeTalentUsers() {
		return analyzeTalentUsers;
	}
}