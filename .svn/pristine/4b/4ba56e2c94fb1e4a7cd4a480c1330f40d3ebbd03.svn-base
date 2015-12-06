package com.unison.lottery.weibo.msg.service;

import java.util.List;
import com.unison.lottery.weibo.data.UserSetLottery;
import com.unison.lottery.weibo.data.WeiboUser;

/**
 * @desc 用户彩种设置
 * @author lei.li@unison.net.cn
 * @createTime 2013-11-25
 * @version 1.0
 */
public interface UserLotterySetService {
	
	void save(String uid, String lottery, double score);
	
	/**
	 * 保存用户彩种设置
	 * @param uid	用户ID
	 * @param lotteryList	彩种列表
	 * @param scoreList		排序字段列表
	 */
	void save(String uid, List<String> lotteryList, List<Double> scoreList);
	
	/**
	 * 获取指定用户彩种配置信息
	 * @param uid
	 * @return
	 */
	List<UserSetLottery> loadAllLotterySetForUser(String uid, long start, long end);

	void followMatchs(String uid, List<String> lotteryList);
	
	/**
	 * 列出用户关注的彩种用户列表和关注关系
	 * @param weiboUserId
	 * @return
	 */
	List<WeiboUser> listUserLotteryRelation(long weiboUserId);
}
