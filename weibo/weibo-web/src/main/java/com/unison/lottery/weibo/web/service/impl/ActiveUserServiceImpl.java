package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.nosql.ActiveUserDao;
import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.ActiveUserService;
import com.xhcms.lottery.commons.data.WeiboForwardCommentLikeCountVo;
import com.xhcms.lottery.utils.DateUtils;

@Service
public class ActiveUserServiceImpl implements ActiveUserService{
 
	@Autowired
	private ActiveUserDao activeUserDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private UserAccountService userAccountService;
	@Override
	@Transactional
	public List<WeiboUser> findActiveUser() {
		
		Long beginTime=DateUtils.yesterdaybeginTime().getTime();
		Long endTime=DateUtils.yesterdayEndTime().getTime();
		
		List<WeiboUser> weiboUsers=new ArrayList<WeiboUser>();
		Set<String> postIds=activeUserDao.zrangeByScore(Keys.GLOBAL_TIMELINE,beginTime+"",endTime+"");
		List<WeiboForwardCommentLikeCountVo> weiboTotalCount=new ArrayList<WeiboForwardCommentLikeCountVo>();
		Map<Long,Integer> weiboCountMap=new HashMap<Long,Integer>();
		//将每个用户发微博+转发+赞+评论的总和计算出来
		for(String s:postIds){
			WeiboMsg m=messageDao.get(s);
		    Long weiboUserId=m.getOwnerId();//weiboUserId
		    Integer total=m.getCommentCount()+m.getForwardCount()+m.getLikeCount()+1;
		    Integer count=weiboCountMap.get(weiboUserId);
		    if(count!=null){
		    	count+=total;
		    	weiboCountMap.put(weiboUserId, count);
		    }else{
		    	weiboCountMap.put(weiboUserId, total);
		    }
		}
		//将map转换成list
		if(weiboCountMap.size()>0){
			Set<Long> keysSet=weiboCountMap.keySet();
			Iterator<Long> keys=keysSet.iterator();
			while(keys.hasNext()){
				Long key=keys.next();
				Integer count=weiboCountMap.get(key);
				WeiboForwardCommentLikeCountVo c=new WeiboForwardCommentLikeCountVo();
				c.setCount(count);
				c.setWeiboUserId(key);
				weiboTotalCount.add(c);
				
			}
		}
		//排序 
		Collections.sort(weiboTotalCount, new Comparator<WeiboForwardCommentLikeCountVo>(){
			public int compare(WeiboForwardCommentLikeCountVo o1, WeiboForwardCommentLikeCountVo o2){
				return o2.getCount()-o1.getCount();
			}
		});
		//取前五名活跃用户
		int count=0;
		if(weiboTotalCount.size()<=5 && weiboTotalCount.size()>0){
			count=weiboTotalCount.size();
		}else{
			count=5;
		}
		//将前五名活跃用户的信息填充
		if(weiboTotalCount.size()>0){
			for(int i=0;i<count;i++){
				WeiboForwardCommentLikeCountVo vo=weiboTotalCount.get(i);
				WeiboUser user=userAccountService.findWeiboUserByWeiboUid(vo.getWeiboUserId()+"");
				user.setWeiboUserId(vo.getWeiboUserId());
				user.setActiveUser(1+"");//标示当前展示的是活跃用户
				weiboUsers.add(user);
			}
		}
		
		return weiboUsers;
	}

}
