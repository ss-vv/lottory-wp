package com.unison.lottery.weibo.common.nosql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.common.nosql.TagDao;
import com.unison.lottery.weibo.common.redis.RedisConstant;
import com.unison.lottery.weibo.data.WeiboTag;

@Repository
public class TagDaoImpl extends BaseDaoImpl<WeiboTag> implements TagDao {

	@Override
	public List<WeiboTag> listByTagId(Set<String> tagIdSet) {
		List<WeiboTag> tags = new ArrayList<WeiboTag>();
		for(String tagId : tagIdSet) {
			WeiboTag tag = hashGet(Keys.tag(tagId));
			tags.add(tag);
		}
		return tags;
	}

	@Override
	public WeiboTag findByName(String tagName) {
		String tagNameKey = Keys.tagNameKey(tagName);
		String tagId = getString(tagNameKey);
		WeiboTag tag = hashGet(Keys.tag(tagId));
		return tag;
	}

	@Override
	public Long addTagToDynamicList(WeiboTag weiboTag) {
		return zadd(weiboTag.getCreateTime(), Keys.DYNAMIC_TAG, weiboTag.getId()+"");
	}

	@Override
	public Long removeTagToDynamicList(String tagId) {
		return zrem(Keys.DYNAMIC_TAG, tagId);
	}

	@Override
	public Set<String> findDynamicTagList(String min, String max) {
		return zrangeByScore(Keys.DYNAMIC_TAG, min, max);
	}

	@Override
	public List<WeiboTag> getTagListByWeiboId(long weiboId) {
		Set<String> tagIdSet = zrangeByScore(Keys.WeiboTags(weiboId), 
				RedisConstant.N_INFINIT, RedisConstant.INFINIT);
		if(null != tagIdSet && tagIdSet.size() > 0) {
			List<WeiboTag> tags = listByTagId(tagIdSet);
			return tags;
		}
		return null;
	}
	
	@Override
	public Long delTagListByWeiboId(long weiboId) {
		return delete(Keys.WeiboTags(weiboId));
	}
}