package com.unison.lottery.weibo.web.service;

import java.util.List;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WeiboUserStatis;

public interface RecomendUserService {
	
	List<WeiboUser> getRecWeiboUser(); 
	List<WeiboUser> getLotteryWeiboUser();
	List<WeiboUserStatis> getGuoDanLvTop10WeiboUser(String weiboUserId);
	List<WeiboUserStatis> getBonusTop10WeiboUser(String weiboUserId);
	List<WeiboUserStatis> getActiveWeiboUser(String weiboUserId);
	PageResult<WeiboUserStatis> getActiveWeiboUser(String weiboUserId,PageRequest pageRequest); 
}
