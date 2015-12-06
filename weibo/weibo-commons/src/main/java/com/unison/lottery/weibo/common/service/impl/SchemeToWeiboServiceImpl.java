package com.unison.lottery.weibo.common.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.service.SchemeToWeiboService;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboTag;

@Service
public class SchemeToWeiboServiceImpl implements SchemeToWeiboService {

	@Autowired
	private MessageDao messageDao;

	@Override
	public long getWeiboIdByShowSchemeId(long schemeId) {
		long postId = -1;
		if (schemeId > 0) {
			String postIdStr = messageDao.getWeiboIdByShowSchemeId(schemeId
					+ "");
			if (StringUtils.isNotBlank(postIdStr)) {
				postId = Long.valueOf(postIdStr);
			}
		}
		return postId;
	}

	@Override
	public List<WeiboTag> getWeiboTagByPostId(long postId) {
		WeiboMsgVO vo = messageDao.getVO(postId + "");
		if(null != vo) {
			return vo.getTags();
		}
		return null;
	}
}