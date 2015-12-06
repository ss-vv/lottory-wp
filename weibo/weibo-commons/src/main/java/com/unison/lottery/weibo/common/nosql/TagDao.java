package com.unison.lottery.weibo.common.nosql;

import java.util.List;
import java.util.Set;
import com.unison.lottery.weibo.data.WeiboTag;

public interface TagDao extends BaseDao<WeiboTag> {
	
	List<WeiboTag> listByTagId(Set<String> tagIdSet);

	WeiboTag findByName(String tagName);
	
	/**
	 * 将未达到最终状态的动态标签加入到集合中
	 * @param weiboTag
	 * @return
	 */
	Long addTagToDynamicList(WeiboTag weiboTag);
	
	Long removeTagToDynamicList(String tagId);
	
	Set<String> findDynamicTagList(String min, String max);
	
	List<WeiboTag> getTagListByWeiboId(long weiboId);
	
	Long delTagListByWeiboId(long weiboId);
}