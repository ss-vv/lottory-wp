package com.unison.lottery.weibo.uc.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.data.FollowCommand;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.AsyncService;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.common.service.impl.BaseServiceImpl;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.data.SpecialUser;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.persist.RelationshipDao;
import com.unison.lottery.weibo.uc.service.RelationshipService;

@Service
public class RelationshipServiceImpl extends BaseServiceImpl implements RelationshipService {

	@Override
	protected BaseDao<?> getBaseDao() {
		return relationshipDao;
	}
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private RelationshipDao relationshipDao;
	
	@Autowired
	private AsyncService asyncService;
	
	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Override
	public Relationship findFollowersByUid(Long uid) {
		Relationship relationship = new Relationship();
		relationship.setUid(uid);
		String[] followers = relationshipDao.findFollowersByUid(uid);
		relationship.setFollowers(followers);
		return relationship;
	}

	@Override
	public Relationship findFollowingByUid(Long uid) {
		Relationship relationship = new Relationship();
		relationship.setUid(uid);
		String[] followings = relationshipDao.findFollowingByUid(uid);
		relationship.setFollowings(followings);
		return relationship;
	}
	
	@Override
	public Relationship findFollowersByUid(Long uid, PageRequest pageRequest) {
		Relationship r = this.findFollowersByUid(uid);
		r.setFollowers(findUids(Keys.getFollowerKey(uid), pageRequest));
		pageRequest.setRecordCount(relationshipDao.zcard(Keys.getFollowerKey(uid)).intValue());
		return r;
	}

	@Override
	public Relationship findFollowingByUid(Long uid, PageRequest pageRequest) {
		Relationship r = new Relationship();
		r.setFollowings(findUids(Keys.getFollowingKey(uid), pageRequest));
		pageRequest.setRecordCount(relationshipDao.zcard(Keys.getFollowingKey(uid)).intValue());
		return r;
	}
	
	/**根据关注key或粉丝key返回uid集合*/
	@SuppressWarnings("unchecked")
	private String[] findUids(String key,PageRequest pageRequest){
		if(StringUtils.isBlank(key)){
			return new String[0];
		}
		PageResult<String> result = (PageResult<String>) descListSortedSetByPageRequest(key, pageRequest);
		String[] uids = new String[result.getResults().size()];
		int i = 0;
		for(String id : result.getResults()){
			uids[i] = id;
			i++;
		}
		return uids;
	}
	
	@Override
	public void saveFollower(Long followerUid, String... beFollowUids) {
		relationshipDao.saveFollower(followerUid, beFollowUids);
		
		//通知被关注者有新的粉丝
		notificationService.addNewFollowers(followerUid, beFollowUids);
		
		for(String uid : beFollowUids){
			if(StringUtils.isBlank(uid)){
				continue;
			}
			asyncService.followLotteryUser(followerUid, Long.parseLong(uid));
		}
	}

	@Override
	public void deleteFollower(Long followerUid, String... beFollowUids) {
		relationshipDao.deleteFollower(followerUid, beFollowUids);
		
		//通知被关注者粉丝已经取消关注
		notificationService.cancelFollow(followerUid, beFollowUids);
		
		for(String uid : beFollowUids){
			if(StringUtils.isBlank(uid)){
				continue;
			}
			asyncService.unFollowLotteryUser(followerUid, Long.parseLong(uid));
		}
	}

	@Override
	public List<WeiboUser> isFollowing(String weiboUserId, List<WeiboUser> weiboUsers) {
		if(null == weiboUsers || weiboUsers.size() < 1 || null == weiboUserId){
			return null;
		}
		for (WeiboUser weiboUser : weiboUsers) {
			if (relationshipDao.isFollowing(weiboUserId, weiboUser.getWeiboUserId().toString())) {
				weiboUser.setBeFollowed(true);
			} else {
				weiboUser.setBeFollowed(false);
			}
		}
		return weiboUsers;
	}

	@Override
	public WeiboUser isFollowing(String weiboUserId, WeiboUser weiboUser) {
		if(null == weiboUser || null == weiboUserId){
			return null;
		}
		List<WeiboUser> weiboUsers = new ArrayList<WeiboUser>();
		weiboUsers.add(weiboUser);
		weiboUsers = this.isFollowing(weiboUserId, weiboUsers);
		return weiboUsers.get(0);
	}

	@Override
	public List<WeiboUser> isFollower(String weiboUserId,
			List<WeiboUser> weiboUsers) {
		if(null == weiboUsers || weiboUsers.size() < 1 || null == weiboUserId){
			return null;
		}
		for (WeiboUser weiboUser : weiboUsers) {
			if (relationshipDao.isFollowing(weiboUser.getWeiboUserId().toString() ,weiboUserId)) {
				weiboUser.setBeFollower(true);
			} else {
				weiboUser.setBeFollower(false);
			}
		}
		return weiboUsers;
	}

	@Override
	public WeiboUser isFollower(String weiboUserId, WeiboUser weiboUser) {
		if(null == weiboUser || null == weiboUserId){
			return null;
		}
		List<WeiboUser> weiboUsers = new ArrayList<WeiboUser>();
		weiboUsers.add(weiboUser);
		weiboUsers = this.isFollower(weiboUserId, weiboUsers);
		return weiboUsers.get(0);
	}

	@Override
	public void processAsyncFollowCommand(FollowCommand followCommand) {
		
		String followerUid = followCommand.getFollowerId()+"";
		String followingUid = followCommand.getFollowingId() + "";
		
		boolean isFollowingLotteryUser = 
				lotteryService.isSpecialUser(followingUid, SpecialUser.LOTTERY_USER);
		
		if (followCommand.isFollowing()){
			// 关注
			messageDao.addMyAllPostFromUser(Keys.userMainTimeline(followerUid), 
					Keys.myPostKey(followingUid), followingUid);
			
			//将我关注的人发布是所有微博添加到我的讨论时间线中
			messageDao.addMyAllPostFromUserWithDiscuss(Keys.userDiscussTimeline(followerUid), 
					Keys.myPostKey(followingUid), followingUid);
			
			messageDao.addRecomRealsFromUser(Keys.userRecomRealsTimelineKey(followerUid), 
					Keys.userSelfRecomRealsKey(followingUid));
			
			
			// 关注彩种用户需要特殊处理
			if (isFollowingLotteryUser){
				lotteryService.followingLottery(followerUid, followingUid);
			}
		}else{
			// 取消关注
			messageDao.delMyallPostFromUser(Keys.userMainTimeline(followerUid), 
					Keys.myPostKey(followingUid),  followingUid);

			//将我关注的人发布的所有微博从我的讨论时间线中删除
			messageDao.delMyallPostFromUser(Keys.userDiscussTimeline(followerUid), 
					Keys.myPostKey(followingUid),  followingUid);
			
			messageDao.delRecomRealsFromUser(Keys.userRecomRealsTimelineKey(followerUid), 
					Keys.userSelfRecomRealsKey(followingUid));
			
			// 取消对彩种用户的关注
			if (isFollowingLotteryUser){
				lotteryService.unFollowingLottery(followerUid, followingUid);
			}
		}
	}

	@Override
	public List<Long> myFollowers(Long uid) {
		Relationship relationship = findFollowersByUid(uid);
		List<Long> followerIds = new ArrayList<Long>();
		if (null != relationship && relationship.getFollowers() != null
				&& relationship.getFollowers().length > 0) {
			for(String fid : relationship.getFollowers()) {
				followerIds.add(Long.valueOf(fid));
			}
		}
		return followerIds;
	}
}
