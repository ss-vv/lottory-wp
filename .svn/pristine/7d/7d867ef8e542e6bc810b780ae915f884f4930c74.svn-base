package com.unison.lottery.weibo.web.action.pc.ajax;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.TopicService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.TopicType;
import com.unison.lottery.weibo.data.vo.TopicVO;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 今日话题 ajax.
 * 
 * @author Yang Bo
 */
public class DailyTopicAction extends BaseAction {

	private static final long serialVersionUID = -8350680874355472758L;

	@Autowired
	private TopicService topicService;
		
	private PageResult<TopicVO> data;
	
	public String execute(){
		data = topicService.findByType(TopicType.DAILY_TOPIC, new PageRequest());
		return SUCCESS;
	}

	public PageResult<TopicVO> getData() {
		return data;
	}

	public void setData(PageResult<TopicVO> data) {
		this.data = data;
	}
}
