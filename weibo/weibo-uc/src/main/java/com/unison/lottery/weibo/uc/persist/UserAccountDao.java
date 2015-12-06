package com.unison.lottery.weibo.uc.persist;

import java.util.List;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.UserQueryCondition;

/**
 * @Description: 
 * 		用户账户操作接口
 * 		该接口所有操作只更改存储在Redis中的数据，
 * 		不更改原大V彩mysql数据库数据
 * @author 江浩翔   
 * @date 2013-10-16 下午12:05:42 
 * @version V1.0
 */
public interface UserAccountDao extends BaseDao<WeiboUser>{

	void saveWeiboUser(WeiboUser weiboUser) ;
	
	void updateAll(WeiboUser weiboUser) ;
	
	void updateNickname(WeiboUser weiboUser) ;
	
	List<WeiboUser> querryWeiboUser(UserQueryCondition userQueryCondition) ;

	WeiboUser get(long weiboUserId);
	
	void saveSinaWeiboUid(final WeiboUser weiboUser)  ;

	void updateSinaToken(WeiboUser weiboUser, String sinaWeiboToken) ;

	void updateHeadImage(WeiboUser weiboUser);

	void updateWeixinToken(WeiboUser weiboUser, String token);

	void updateQQConnectToken(WeiboUser weiboUser, String token);
}
