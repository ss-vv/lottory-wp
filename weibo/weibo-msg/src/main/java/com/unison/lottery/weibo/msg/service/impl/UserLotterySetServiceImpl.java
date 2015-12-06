package com.unison.lottery.weibo.msg.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.LotteryDao;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.data.UserSetLottery;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.UserLotterySetService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;

@Service
public class UserLotterySetServiceImpl implements UserLotterySetService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LotteryDao lotterySetDao;
	
	@Autowired
	private UserAccountService accountService;
	
	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private RelationshipService relationshipService;

	@Override
	public void save(String uid, String lottery, double score) {
		lotterySetDao.saveLotterySet(uid, lottery, score);
	}

	@Override
	public void save(String uid, List<String> lotteryList,
			List<Double> scoreList) {
		if (StringUtils.isNotBlank(uid) && 
				lotteryList.size() > 0 && 
				scoreList.size() > 0) {
			followMatchs(uid, lotteryList);
			lotterySetDao.removeLotterySetForUser(uid);
			for (int i = 0; i < lotteryList.size(); i++) {
				String lottery = lotteryList.get(i);
				Double score = scoreList.get(i);
				lotterySetDao.saveLotterySet(uid, lottery, score);
			}
		}
	}

	@Override
	public List<UserSetLottery> loadAllLotterySetForUser(String uid, long start, long end) {
		return lotterySetDao.loadLotterySetForUser(uid, start, end);
	}
	
	@Override
	/** 将用户关注的彩种微博加入用户微博列表 */
	public void followMatchs(String uid, List<String> lotteryList){
		if(StringUtils.isBlank(uid) || Long.parseLong(uid) < 1 || lotteryList==null || lotteryList.isEmpty()){
			logger.info("取消保存用户={}彩种微博，参数不正确。",uid);
			return;
		}
		List<UserSetLottery> lotterys = loadAllLotterySetForUser(uid, 0, -1);
		List<String> userLotterys = new ArrayList<String>();
		for(UserSetLottery usl : lotterys){
			userLotterys.add(usl.getName());
		}
		List<String> delLotterys = diff(lotteryList , userLotterys);
		List<String> addLotterys = diff(userLotterys, lotteryList);
		
	}
	
	private List<String> diff(List<String> ls, List<String> ls2) {   
        List<String> list = new ArrayList<String>();   
        for(String str : ls2){
        	if(!ls.contains(str)){
        		list.add(str);
        	}
        }
        return list;   
	}

	@Override
	public List<WeiboUser> listUserLotteryRelation(long weiboUserId) {
		Set<String> weiboUserIdSet = lotteryService.findWeiboUserByLottery();
		List<WeiboUser> list = new ArrayList<WeiboUser>();
		if(null != weiboUserIdSet && weiboUserIdSet.size() > 0) {
			int size = weiboUserIdSet.size();
			String[] userIdSet = weiboUserIdSet.toArray(new String[size]);
			list = accountService.findWeiboUserByWeiboUids(userIdSet);
			list = relationshipService.isFollowing(weiboUserId+"", list);
		}
		return list;
	}
}