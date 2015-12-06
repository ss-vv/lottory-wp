package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.RecomendUserDao;
import com.unison.lottery.weibo.common.nosql.StatisticUserDataDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.RecomendUserService;

@Service
public class RecomendUserServiceImpl implements RecomendUserService {

	@Autowired
	RecomendUserDao recomendUserDao;
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	StatisticUserDataDao statisticUserDataDao;
	@Autowired
	RelationshipService relationshipService;
	@Override
	public List<WeiboUser> getRecWeiboUser() {
		Set<String> ids = recomendUserDao.getRecWeiboUserId();
		return getWeiboUsers(ids);
	}

	@Override
	public List<WeiboUser> getLotteryWeiboUser() {
		Set<String> ids = recomendUserDao.getLotteryUserId();
		return getWeiboUsers(ids);
	}
	private List<WeiboUser> getWeiboUsers(Set<String> ids){
		List<WeiboUser> list = new ArrayList<WeiboUser>();
		if(ids.size() > 0){
			String[] idsArray = new String[ids.size()];
			int i=0;
			for (String string : ids) {
				idsArray[i] = string;
				i++;
			}
			list = userAccountService.findWeiboUserByWeiboUids(idsArray);
		}
		return list;
	}

	@Override
	public List<WeiboUserStatis> getGuoDanLvTop10WeiboUser(String weiboUserId) {
		LinkedHashSet<String> l = recomendUserDao.zrevrange(Keys.Last7GuoDanLvTop10, 0, -1);
		return getWeiboUserStatis(weiboUserId,l);
	}

	@Override
	public List<WeiboUserStatis> getBonusTop10WeiboUser(String weiboUserId) {
		LinkedHashSet<String> l = recomendUserDao.zrevrange(Keys.Last7TotalBonusTop10, 0, -1);
		return getWeiboUserStatis(weiboUserId,l);
	}
	
	private List<WeiboUserStatis> getWeiboUserStatis(String weiboUserId,Set<String> ids){
		List<WeiboUserStatis> list = new ArrayList<WeiboUserStatis>();
		int i=0;
		for (String id : ids) {
			if(i >=10){
				break;
			}
			WeiboUser u = userAccountService.findWeiboUserByWeiboUid(id);
			WeiboUserStatis w = statisticUserDataDao.hashGet(Keys.getWeiboUserStatisKey(id));
			if(null != u && w != null ){
				w.setNickName(u.getNickName());
				w.setHeadImageURL(u.getHeadImageURL());
				w.setWeiboUserId(u.getWeiboUserId());
				w.setCertificationType(u.getCertificationType());
				w.setFollowerCount(relationshipService.findFollowersByUid(Long.parseLong(id)).getFollowers().length);
				w.setFollowingCount(relationshipService.findFollowingByUid(Long.parseLong(id)).getFollowings().length);
				relationshipService.isFollowing(weiboUserId, w);
			} else {
				continue;
			}
			if(w.getRealWeibo7OpenCount() != 0){
				w.setGuodanlv(w.getRealWeibo7GainCount()*100/w.getRealWeibo7OpenCount());
			} else {
				w.setGuodanlv(0);
			}
			list.add(w);
			i++;
		}
		return list;
	}

	@Override
	public List<WeiboUserStatis> getActiveWeiboUser(String weiboUserId) {
		LinkedHashSet<String> ids = recomendUserDao.zrevrange(Keys.WeiboCountRankList, 0, 8);
		return getWeiboUserStatis(weiboUserId,ids);
	}

	@Override
	public PageResult<WeiboUserStatis> getActiveWeiboUser(String weiboUserId,
			PageRequest pageRequest) {
		if (null == pageRequest) {
			pageRequest = new PageRequest();
		}
		int startIndex = pageRequest.getOffset();
		int endIndex = pageRequest.getPageIndex() * pageRequest.getPageSize() - 1;
		PageResult<WeiboUserStatis> pageResult = new PageResult<WeiboUserStatis>();
		pageRequest.setRecordCount((statisticUserDataDao.zcard(Keys.WeiboCountRankList).intValue()));
		LinkedHashSet<String> ids = recomendUserDao.zrevrange(Keys.WeiboCountRankList, startIndex, endIndex);
		pageResult.setResults(getWeiboUserStatis(weiboUserId,ids));
		pageResult.setPageRequest(pageRequest);
		pageResult.setSuccess(true);
		return pageResult;
	}
}
