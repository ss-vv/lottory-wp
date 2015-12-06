package com.unison.lottery.weibo.common.service;

import java.util.List;

import com.unison.lottery.weibo.data.WeiboTag;

public interface SchemeToWeiboService {

	public long getWeiboIdByShowSchemeId(long schemeId);
	
	List<WeiboTag> getWeiboTagByPostId(long postId);
}