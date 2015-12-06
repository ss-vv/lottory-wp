package com.unison.lottery.weibo.common.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.TopicDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.TopicService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.Topic;
import com.unison.lottery.weibo.data.TopicType;
import com.unison.lottery.weibo.data.vo.TopicVO;
import com.unison.lottery.weibo.exception.ServiceException;

@Service
public class TopicServiceImpl extends BaseServiceImpl implements TopicService {

	@Autowired
	private TopicDao topicDao;

	@Override
	public void create(Topic topic) {
		topicDao.create(topic);
		Long createdTime = topic.getCreateTime().getTime();
		topic.setSortIndex(createdTime);
	}

	@Override
	public void delete(long id) {
		topicDao.destroy(id, Topic.class);
	}

	@Override
	public void update(Topic topic) {
		Topic tp = topicDao.get(topic.getId());
		if(null != tp) {
			topic.setSortIndex(tp.getSortIndex() <= 0 ? 
					tp.getCreateTime().getTime() : 
						tp.getSortIndex());
			topicDao.update(topic);
			topicDao.zadd(topic.getSortIndex(), Keys.dailyTopicsKey(),
					"" + topic.getId());
		}
	}

	@Override
	public void addToDailyTopics(Topic topic) {
		topic.setType(TopicType.DAILY_TOPIC);
		topicDao.zadd(topic.getSortIndex(), Keys.dailyTopicsKey(),
				"" + topic.getId());
	}

	@Override
	public void createAddToList(Topic topic) {
		create(topic);
		addToDailyTopics(topic);
	}

	@Override
	public PageResult<TopicVO> findByType(TopicType type,
			PageRequest pageRequest) {
		String key = null;
		switch (type) {
		case DAILY_TOPIC:
			key = Keys.dailyTopicsKey();
			break;
		default:
			break;
		}
		PageResult<Topic> modelResult = listSortedSetDesc(key, Topic.class,
				pageRequest);
		PageResult<TopicVO> voResult = fillVOPageResult(modelResult,
				TopicVO.class);
		return voResult;
	}
	
	@Override
	public PageResult<TopicVO> findByType(long uid, TopicType type,
			PageRequest pageRequest) {
		
		PageResult<TopicVO> voResult = findByType(type, pageRequest);
		//检查话题对应的微博是否被自己“收藏”和“赞”过
		if(null != voResult && voResult.getResults() != null) {
			List<TopicVO> list = voResult.getResults();
			for(TopicVO topicVO : list) {
				topicVO.setWeibo(checkFavoriateAndLike(uid, topicVO.getWeibo()));
			}
		}
		return voResult;
	}
	
	@Override
	protected BaseDao<?> getBaseDao() {
		return topicDao;
	}

	@Override
	public TopicVO get(long id) {
		Topic topic = topicDao.get(Topic.class, id);
		if (topic == null) {
			return null;
		}
		return fillVO(topic, TopicVO.class);
	}

	@Override
	public void deleteFromList(Topic topic) {
		switch (topic.getType()) {
		case DAILY_TOPIC:
			topicDao.zrem(Keys.dailyTopicsKey(), "" + topic.getId());
			break;
		default:
			throw new ServiceException("遗漏的 Topic type: " + topic.getType());
		}
	}

	@Override
	public void deleteTopicAndRemoveFromList(List<Long> list, 
			TopicType topicType) {
		if(null != topicType) {
			for(Long topicId : list) {
				Topic topic = new Topic();
				topic.setId(topicId);
				topic.setType(topicType);
				deleteFromList(topic);
				
				delete(topicId);
			}
		}
	}
}
