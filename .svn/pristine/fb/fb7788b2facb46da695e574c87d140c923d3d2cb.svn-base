package com.unison.lottery.weibo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.LatestPublishDao;
import com.unison.lottery.weibo.common.nosql.TagDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.LatestPublishService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;

@Service
public class LatestPublishServiceImpl extends BaseServiceImpl implements
		LatestPublishService {

	@Autowired
	private LatestPublishDao publishDao;
	
	@Autowired
	private TagDao tagDao;

	@Override
	protected BaseDao<?> getBaseDao() {
		return publishDao;
	}

	@Override
	public PageResult<WeiboMsgVO> query(long uid, PageRequest pageRequest, 
			RecentDateType recentDateType) {
		String key = Keys.GLOBAL_TIMELINE;
		PageResult<WeiboMsg> modelResult = listSortedSetDescByScore(key,
				WeiboMsg.class, pageRequest, recentDateType);
		PageResult<WeiboMsgVO> voResult = fillVOPageResult(modelResult,
				WeiboMsgVO.class);
		
		if(null != voResult && voResult.getResults() != null) {
			for(WeiboMsgVO vo : voResult.getResults()) {
				vo.setTags(tagDao.getTagListByWeiboId(vo.getId()));
			}
		}
		
		//检查话题对应的微博是否被自己“收藏”和“赞”过
		voResult = checkFavoriateAndLike(uid, voResult);
		return voResult;
	}
}
