package com.unison.weibo.admin.action.topic;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.TopicService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.Topic;
import com.unison.lottery.weibo.data.TopicType;
import com.unison.lottery.weibo.data.vo.TopicVO;
import com.unison.weibo.admin.action.BaseAction;
import com.xhcms.commons.lang.Data;

public class TopicAction extends BaseAction {

	private static final long serialVersionUID = 4815567240828824050L;

	Logger log = LoggerFactory.getLogger(getClass());

	private Topic topic;
	
	@Autowired
	private TopicService topicService;
	
	private PageRequest pageRequest = new PageRequest();
	private int page;
	
	private PageResult<TopicVO> pageResult;
	
	public TopicAction() {
		pageRequest.setPageSize(5);
	}
	
	public String createTopic() {
		if(!isValidTopic(topic)) {
			super.setData(Data.failure(null));
		} else {
			try {
				topic.setCreateTime(new Date());
				topicService.createAddToList(topic);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String topicQuery() {
		if (page == 0){
			page = 1;
		}
		pageRequest.setPageIndex(page);
		try {
			pageResult = topicService.findByType(TopicType.DAILY_TOPIC, pageRequest);
		} catch (Exception e) {
			log.error("今日话题列表查询失败：{}", e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getTopicById() {
		if(null != topic && topic.getId() > 0) {
			TopicVO topicVO = topicService.get(topic.getId());
			super.setData(topicVO);
		}
		return SUCCESS;
	}
	
	private boolean isValidTopic(Topic topic) {
		boolean ret = true;
		if(null == topic || topic.getWeiboId() < 0 ||
			StringUtils.isBlank(topic.getTitle()) ||
			StringUtils.isBlank(topic.getDescription()) ||
			StringUtils.isBlank(topic.getTag()) ||
			null == topic.getType() && topic.getType() != TopicType.DAILY_TOPIC) {
			ret = false;
		}
		return ret;
	}

	public Topic getTopic() {
		return topic;
	}
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public PageResult<TopicVO> getPageResult() {
		return pageResult;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}