package com.unison.weibo.admin.action.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.TopicService;
import com.unison.lottery.weibo.data.Topic;
import com.unison.lottery.weibo.data.TopicType;
import com.unison.weibo.admin.action.BaseAction;
import com.xhcms.commons.lang.Data;

public class TopicEditAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String topicIdList;
	private Topic topic;

	@Autowired
	private TopicService topicService;

	public String deleteTopic() {
		List<Long> list = new ArrayList<Long>();
		if(StringUtils.isNotBlank(topicIdList)) {
			List<String> idList = Arrays.asList(topicIdList.split(","));
			for(String topicId : idList) {
				if(StringUtils.isNotBlank(topicId)) {
					list.add(Long.parseLong(topicId));
				}
			}
			try {
				if(list.size() > 0)
				topicService.deleteTopicAndRemoveFromList(list, TopicType.DAILY_TOPIC);
			} catch (Throwable th) {
				super.setData(Data.failure(null));
				th.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public String updateTopic() {
		if(null != topic) {
			topicService.update(topic);
		} else {
			super.setData(Data.failure(null));
		}
		return SUCCESS;
	}
	
	public void setTopicIdList(String topicIdList) {
		this.topicIdList = topicIdList;
	}
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Topic getTopic() {
		return topic;
	}
}