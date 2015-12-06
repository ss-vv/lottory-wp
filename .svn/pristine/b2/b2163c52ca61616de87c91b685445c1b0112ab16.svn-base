package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.vo.MatchRecommendVo;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.MatchRecommendService;
/**
 * 异步获取右侧专家推荐比赛
 * @author haohao
 *
 */
public class RecommendMatchAction extends BaseAction{

	private List<MatchRecommendVo> data=new ArrayList<MatchRecommendVo>();
	private static final long serialVersionUID = -761719322104217275L;
	@Autowired
	private MatchRecommendService matchRecommendService;
	public String execute(){
		//false  不取过期的推荐
		matchRecommendService.getMatchRecommend(data, data, data, false);
		return SUCCESS;
	}

	public List<MatchRecommendVo> getData() {
		return data;
	}

	public void setData(List<MatchRecommendVo> data) {
		this.data = data;
	}
	

}
