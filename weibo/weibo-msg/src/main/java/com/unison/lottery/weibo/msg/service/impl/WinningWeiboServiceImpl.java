package com.unison.lottery.weibo.msg.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.TagDao;
import com.unison.lottery.weibo.common.nosql.WinningNewDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.impl.BaseServiceImpl;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboTag;
import com.unison.lottery.weibo.data.WinningNew;
import com.unison.lottery.weibo.msg.service.WinningWeiboService;
import com.unison.lottery.weibo.uc.service.RelationshipService;

@Service
public class WinningWeiboServiceImpl extends BaseServiceImpl implements
		WinningWeiboService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WinningNewDao winningNewDao;
	
	@Autowired
	RelationshipService relationshipService;
	
	@Autowired
	private TagDao tagDao;
	
	@Override
	public PageResult<WeiboMsgVO> queryWinWeibo(PageRequest pageRequest,
			RecentDateType recentDateType) {
		log.info("查询中奖喜报对应微博，日期类型={}", recentDateType.getName());
		
		String key = Keys.WINNINGNEWS;
		PageResult<WinningNew> modelResult = 
				listSortedSetDescByScore(key, WinningNew.class, pageRequest, recentDateType);
		
		PageResult<WeiboMsg> rs = new PageResult<WeiboMsg>();
		
		List<WeiboMsg> msgList = new ArrayList<WeiboMsg>();
		if(modelResult.getResults() != null) {
			for(WinningNew win : modelResult.getResults()) {
				long winWeiboId = win.getWeiboId();
				if(winWeiboId > 0) {
					WeiboMsg weiboMsg = winningNewDao.get(WeiboMsg.class, winWeiboId);
					weiboMsg.setSchameId(win.getSchemeId());
					msgList.add(weiboMsg);
				}
			}
			rs.setResults(msgList);
			rs.setPageRequest(modelResult.getPageRequest());
			rs.setTotalResults(modelResult.getTotalResults());
		}
		PageResult<WeiboMsgVO> voResult = fillVOPageResult(rs,
				WeiboMsgVO.class);
		
		if(modelResult.getResults() != null && 
				null != voResult && voResult.getResults() != null) {
			for(int i=0; i<voResult.getResults().size(); i++) {
				if(i < modelResult.getResults().size()) {
					long postId = modelResult.getResults().get(i).getShowWeiboId();
					List<WeiboTag> tagList = tagDao.getTagListByWeiboId(postId);
					voResult.getResults().get(i).setTags(tagList);
				}
			}
		}
		
		return voResult;
	}

	@Override
	public PageResult<WeiboMsgVO> queryMyFollowingWinnings(
			long weiboUserId, PageRequest pageRequest) {
		Set<String> winIdSet = new HashSet<String>();
		String[] followings = relationshipService.findFollowingByUid(weiboUserId)
				.getFollowings();
		if(null != followings && followings.length > 0) {
			for(int i=0; i<followings.length; i++) {
				String uid = followings[i];
				LinkedHashSet<String> winIdList = winningNewDao.zrange(Keys.userWinning(uid), 0, -1);
				if(null != winIdList && winIdList.size() > 0) {
					winIdSet.addAll(winIdList);
				}
			}
		}
		PageResult<WeiboMsg> pageResult = new PageResult<WeiboMsg>();
		List<WeiboMsg> msgList = new ArrayList<WeiboMsg>();
		PageResult<WeiboMsgVO> voResult = null;
		if(winIdSet.size() > 0) {
			Object[] winArray = winIdSet.toArray();
			Arrays.sort(winArray, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					long e1 = Long.valueOf(o1.toString());
					long e2 = Long.valueOf(o2.toString());
					if(e1 > e2) return -1;
					else if(e1 < e2) return 1;
					return 0;
				}
			});
			List<String> result = new ArrayList<String>();
			int offset = pageRequest.getOffset();
			int size = pageRequest.getPageSize() + offset;
			for(int i=offset; i<size; i++) {
				if(i <= winArray.length - 1) {
					result.add(winArray[i].toString());
				}
			}
			List<WinningNew> winList = getBaseDao().get(WinningNew.class, result.iterator());
			
			if(winList != null && winList.size() > 0) {
				for(WinningNew win : winList) {
					long winWeiboId = win.getWeiboId();
					if(winWeiboId > 0) {
						WeiboMsg weiboMsg = winningNewDao.get(WeiboMsg.class, winWeiboId);
						weiboMsg.setSchameId(win.getSchemeId());
						msgList.add(weiboMsg);
					}
				}
				pageResult.setPageRequest(pageRequest);
				pageResult.setTotalResults(winIdSet.size());
				pageResult.setResults(msgList);
			}
			voResult = fillVOPageResult(pageResult,	WeiboMsgVO.class);
			
			if(winList != null && null != voResult && voResult.getResults() != null) {
				for(int i=0; i<voResult.getResults().size(); i++) {
					if(i < winList.size()) {
						long postId = winList.get(i).getShowWeiboId();
						List<WeiboTag> tagList = tagDao.getTagListByWeiboId(postId);
						voResult.getResults().get(i).setTags(tagList);
					}
				}
			}
		}
		return voResult;
	}
	
	@Override
	protected BaseDao<?> getBaseDao() {
		return winningNewDao;
	}
}