package com.unison.lottery.weibo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.DiscussDao;
import com.unison.lottery.weibo.common.nosql.TagDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.DiscussService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;

@Service
public class DiscussServiceImpl extends BaseServiceImpl implements
		DiscussService {

	@Autowired
	private DiscussDao discussDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Override
	public PageResult<WeiboMsgVO> findHotDiscussByDate(long uid, RecentDateType recentDateType,
			PageRequest pageRequest) {
		String key = Keys.discussKey(recentDateType);
		PageResult<WeiboMsg> modelResult = listSortedSetDesc(key,
				WeiboMsg.class, pageRequest);
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

	
	@Override
	protected BaseDao<?> getBaseDao() {
		return discussDao;
	}
}
