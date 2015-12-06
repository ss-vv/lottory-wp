package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.Top5RecommendService;
import com.xhcms.lottery.commons.data.Top5RecommendVo;

public class SPFShengLvAndYingLvAction extends BaseAction{

	private static final long serialVersionUID = -6860480200870462307L;
	@Autowired
	private Top5RecommendService top5RecommendService;
    private PageResult<Top5RecommendVo> data=new PageResult<Top5RecommendVo>();
	//7天胜率
	public String day7ShengLv(){
		
		List<Top5RecommendVo> list=top5RecommendService.findTop5Rcommend(Keys.DAY_7_SHENGLV, "shenglv", "7day");
		data.setResults(list);
		 return SUCCESS;
	}
	//50场胜率
	public String match50ShengLv(){
		List<Top5RecommendVo> list=top5RecommendService.findTop5Rcommend(Keys.MATCH_50_SHENGLV, "shenglv", "50");
		data.setResults(list);
		return SUCCESS;
	}
	//7天盈利
	public String day7YingLi(){
		List<Top5RecommendVo> list=top5RecommendService.findTop5Rcommend(Keys.DAY_7_YINGLV, "yinglilv", "7day");
		data.setResults(list);
		return SUCCESS;
	}
	//50场盈利
	public String match50YingLv(){
		List<Top5RecommendVo> list=top5RecommendService.findTop5Rcommend(Keys.MATCH_50_YINGLV, "yinglilv", "50");
		data.setResults(list);
		return SUCCESS;
	}
	public PageResult<Top5RecommendVo> getData() {
		return data;
	}
	public void setData(PageResult<Top5RecommendVo> data) {
		this.data = data;
	}
	

	
	
}
