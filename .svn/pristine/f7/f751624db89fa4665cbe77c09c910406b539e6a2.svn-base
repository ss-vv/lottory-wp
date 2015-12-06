package com.unison.lottery.weibo.common.nosql;

import java.util.List;
import com.unison.lottery.weibo.data.UserSetLottery;

/**
 * 用户彩种相关NoSQL数据访问。
 * 
 * @author lei.li, bob.yang
 */
public interface LotteryDao extends BaseDao<String> {

	/**
	 * 保存用户彩种设置
	 * @param uid
	 * @param lottery
	 * @param score
	 */
	void saveLotterySet(String uid, String lottery, double score);
	
	/**
	 * 加载指定用户的彩种配置数据
	 * @param uid
	 * @return
	 */
	List<UserSetLottery> loadLotterySetForUser(String uid, long start, long end);
	
	/**
	 * 移除用户彩种设置
	 * @param uid
	 */
	void removeLotterySetForUser(String uid);
}