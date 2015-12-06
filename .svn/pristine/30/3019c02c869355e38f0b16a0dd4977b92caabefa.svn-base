package com.unison.weibo.admin.action.match;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.weibo.admin.action.BaseAction;
import com.unison.weibo.admin.service.RecommendAdminService;
import com.xhcms.commons.lang.Paging;

public class RecommendAction extends BaseAction{

	private static final long serialVersionUID = -6316703034517228074L;
	@Autowired
	private RecommendAdminService recommendService;
	private String matchType;
	private Paging recommend;
	private int page;
	private List<Long> reId;
	
	public RecommendAction(){
		if(recommend==null){
			
			recommend=new Paging();
		}
		recommend.setMaxResults(5);
	}
	public String displayRecommends(){
		if(page>0){
			
			recommend.setPageNo(page);
		}
		recommend=recommendService.getRecommendMatch(recommend, matchType);
		return SUCCESS;
	}
	//上架
	public String putOn(){
		recommendService.putOnRecommend(reId);
		return SUCCESS;
	}
	
	//下架
	public String getOff(){
		recommendService.getOffRecommend(reId);
		return SUCCESS;
		
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List<Long> getReId() {
		return reId;
	}
	public void setReId(List<Long> reId) {
		this.reId = reId;
	}
	public void setRecommend(Paging recommend) {
		this.recommend = recommend;
	}
	public RecommendAdminService getRecommendService() {
		return recommendService;
	}
	public void setRecommendService(RecommendAdminService recommendService) {
		this.recommendService = recommendService;
	}
	public Paging getRecommend() {
		return recommend;
	}
	
	
	
}
