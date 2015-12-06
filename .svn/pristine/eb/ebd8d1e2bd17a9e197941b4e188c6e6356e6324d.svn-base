package com.unison.lottery.weibo.common.service;

import java.util.List;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.Topic;
import com.unison.lottery.weibo.data.TopicType;
import com.unison.lottery.weibo.data.vo.TopicVO;

/**
 * 主题相关服务。
 * 
 * @author Yang Bo
 */
public interface TopicService {
	/** 
	 * 创建但不会自动添加到对应类型的 list 中去。
	 * 比如：今日话题就添加到 dailyTopics list中。
	 */
	void create(Topic topic);
	
	/**
	 * @return null 如果id无对应topic。
	 */
	TopicVO get(long id);
	
	/**
	 * 分页列出带关系的topic;不会去解析此话题对应的微博当前用户是否“收藏”和“赞”
	 * @param type
	 * @param pageRequest
	 * @return
	 */
	PageResult<TopicVO> findByType(TopicType type, PageRequest pageRequest);
	
	/**
	 * 分页列出带关系的topic.
	 */
	PageResult<TopicVO> findByType(long uid, TopicType type, PageRequest pageRequest);

	/**
	 * 删除id对应的topic对象，只是删除hash key。
	 * @param id of topic
	 */
	void delete(long id);
	
	/**
	 * 从对应的列表中删除，如今日话题就从 dailyTopics 中删除topic的id。
	 */
	void deleteFromList(Topic topic);
	
	/**
	 * 只会更新 topic 内容，不会更新 reference 属性的内容.
	 */
	void update(Topic topic);
	
	void addToDailyTopics(Topic topic);
	
	/**
	 * 创建并添加到对应的 list 中去。
	 * @param topic 话题。
	 */
	void createAddToList(Topic topic);
	
	/**
	 * 删除id对应的topic对象,并从对应的话题类型列表中删除
	 * @param list
	 */
	void deleteTopicAndRemoveFromList(List<Long> list, TopicType topicType);
}
