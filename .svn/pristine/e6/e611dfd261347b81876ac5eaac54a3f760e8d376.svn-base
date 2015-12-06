package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.RecomendUserService;

public class BonusListAction extends BaseAction{

	private static final long serialVersionUID = 1967982619287318378L;

	@Autowired
	private RecomendUserService recomendUserService;
	private String currentUserId;
	private PageResult<WeiboUserStatis> data=new PageResult<WeiboUserStatis>();
	public String execute(){
		currentUserId = getUserLaicaiWeiboId()+"";
		List<WeiboUserStatis> bonus=recomendUserService.getBonusTop10WeiboUser(currentUserId);
		data.setResults(bonus);
		return SUCCESS;
	}
	public String getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
	public PageResult<WeiboUserStatis> getData() {
		return data;
	}
	public void setData(PageResult<WeiboUserStatis> data) {
		this.data = data;
	}
	
}
