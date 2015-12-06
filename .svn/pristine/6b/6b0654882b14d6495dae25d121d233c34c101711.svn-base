package com.unison.lottery.weibo.web.action.pc.marrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.TopicService;
import com.unison.lottery.weibo.data.TopicType;
import com.unison.lottery.weibo.web.action.BaseAction;

public class TopicAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TopicService topicService;
	
	private int page;
	
	public TopicAction() {
		pageRequest.setPageSize(5);
	}
	
	public String show() {
		return SUCCESS;
	}
	
	@Override
	public String execute() {
		if (page == 0){
			page = 1;
		}
		try {
			pageRequest.setPageIndex(page);
			pageResult = topicService.findByType(getUserLaicaiWeiboId(), TopicType.DAILY_TOPIC, pageRequest);
			pageResult.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			log.error("今日话题列表查询失败：{}", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
}