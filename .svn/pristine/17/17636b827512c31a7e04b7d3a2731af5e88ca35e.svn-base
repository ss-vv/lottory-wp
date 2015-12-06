package com.unison.lottery.weibo.web.action.pc.marrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.HotRecommendService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * @desc 热门推荐
 * @author lei.li@unison.net.cn
 * @createTime 2013-12-25
 * @version 1.0
 */
public class HotRecommendAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	Logger log = LoggerFactory.getLogger(getClass());

	private int page = 1;
	
	@Autowired
	private HotRecommendService hotRecommendService;
	
	public HotRecommendAction() {
		pageRequest.setPageSize(5);
	}
	
	public String show() {
		return SUCCESS;
	}
	
	@Override
	public String execute() {
		pageRequest.setPageIndex(page);
		pageResult = hotRecommendService.query(getUserLaicaiWeiboId(), pageRequest);
		pageResult.setUserId(getUserLaicaiWeiboId());
		return SUCCESS;
	}

	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
}