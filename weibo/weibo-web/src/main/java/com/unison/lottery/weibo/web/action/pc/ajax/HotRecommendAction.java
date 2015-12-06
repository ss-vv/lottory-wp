package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.HotRecommendMatchService;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
/**
 * 热门赛事
 * @author Administrator
 *
 */
public class HotRecommendAction extends BaseAction{

	private static final long serialVersionUID = 259814939387333376L;
	@Autowired
	private  HotRecommendMatchService hrms;
	private  List<HotAndRecommendMatch> data;
	
	/**
	 * 推荐页面 热门推荐
	 * @return
	 */
	public String showHotMatch(){
		data=hrms.getTop5HotRecommend();
		return SUCCESS;
	}
	/**
	 * 热门推荐 最新发表 页面热门赛事
	 * @return
	 */
	public String showHotRecommendWeiboMatch(){
		data=hrms.getTop5ShowWeiboRecommend();
		return SUCCESS;
	}


	public HotRecommendMatchService getHrms() {
		return hrms;
	}


	public void setHrms(HotRecommendMatchService hrms) {
		this.hrms = hrms;
	}


	public List<HotAndRecommendMatch> getData() {
		return data;
	}


	public void setData(List<HotAndRecommendMatch> data) {
		this.data = data;
	}
	
	
}
