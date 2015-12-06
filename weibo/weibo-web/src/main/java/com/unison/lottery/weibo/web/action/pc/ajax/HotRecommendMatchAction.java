package com.unison.lottery.weibo.web.action.pc.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.Top5GuessMatchService;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
/**
 * 中奖喜报页 最红赛事
 * @author haohao
 *
 */
public class HotRecommendMatchAction extends BaseAction{

	private static final long serialVersionUID = 3433421506368918595L;
	@Autowired
	private Top5GuessMatchService top5GuessMatchService;
	private PageResult<HotAndRecommendMatch> data=new PageResult<HotAndRecommendMatch>();
	public String execute(){
		data.setResults(top5GuessMatchService.findTop5GuessMatch());
		return SUCCESS;
	}
	public PageResult<HotAndRecommendMatch> getData() {
		return data;
	}
	public void setData(PageResult<HotAndRecommendMatch> data) {
		this.data = data;
	}
	
	
}
