package com.unison.weibo.admin.action.match;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.weibo.admin.action.BaseAction;
import com.unison.weibo.admin.service.BetMatchRecForRecommendService;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetMatchRecVo;


public class MatchAction extends BaseAction{

	@Autowired
	private BetMatchRecForRecommendService betMatchRecService;
	//private PageResult<BetMatchRecVo> betMatchRecVo;
	private List<BetMatchRecVo> betMatchRecVo;
	private Paging matchs;
	private String matchType;
	private int page;
	private List<Long> recommend;

	public MatchAction(){
		if(matchs==null){
			
			matchs=new Paging();
		}
		matchs.setMaxResults(5);

	}
	//获取待推荐
	public String  getPreparedMatchs(){
		if(page>0){	
			matchs.setPageNo(page);	
		}
		matchs=betMatchRecService.getBetMatchRec(matchs,matchType);
		return SUCCESS;
	}
	//推荐
	public String addRecommend(){
		betMatchRecService.addRecommendMatch(recommend);
		return SUCCESS;
	}

	public List<BetMatchRecVo> getBetMatchRecVo() {
		return betMatchRecVo;
	}

	public void setBetMatchRecVo(List<BetMatchRecVo> betMatchRecVo) {
		this.betMatchRecVo = betMatchRecVo;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public Paging getMatchs() {
		return matchs;
	}
	public void setMatchs(Paging matchs) {
		this.matchs = matchs;
	}
	public List<Long> getRecommend() {
		return recommend;
	}
	public void setRecommend(List<Long> recommend) {
		this.recommend = recommend;
	}
	
	
	
	
}
