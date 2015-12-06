package com.unison.lottery.weibo.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.unison.lottery.weibo.data.WeiboTag;
import com.unison.lottery.weibo.lang.SpecialTag;
import com.xhcms.lottery.commons.data.BetPartner;

/**
 * @desc 标签管理
 * @author lei.li@unison.net.cn
 * @createTime 2014-5-5
 * @version 1.0
 */
public interface TagService {

	WeiboTag getById(String tagId);
	
	/**
	 * 判断是否存在指定名称的静态标签
	 * @param weiboTag
	 * @return
	 */
	WeiboTag exists(WeiboTag weiboTag);
	
	/**
	 * 增加微博标签
	 * @param weiboTag
	 * @return
	 */
	WeiboTag addTag(WeiboTag weiboTag);
	
	/**
	 * 根据彩种创建标签
	 * @param lotteryId
	 * @return
	 */
	WeiboTag createTagByLottery(String lotteryId);
	
	/**
	 * 根据微博类型创建微博标签
	 * @param type
	 * @return
	 */
	WeiboTag createTagByWeiboType(String type);
	
	/**
	 * 根据方案状态创建微博标签
	 * @param weiboType 微博类型
	 * @param betPartner 投注记录
	 * @return
	 */
	WeiboTag createTagBySchemeStatus(String weiboType, BetPartner betPartner);
	
	Set<String> findDynamicTagList(String min, String max);
	
	Long removeTagToDynamicList(String tagId);
	
	String editTag(String tagId, Map<String, String> hash);
	
	void updateGroupProgress(long tagId, double totalAmount, double purchasedAmount);
	
	void updateGroupProgressByGroupScheme(long schemeId, double totalAmount, double purchasedAmount);
	
	List<WeiboTag> getTagListByWeiboId(long weiboId);
	
	void updateWeiboTag(long postId, String sourceTagName, SpecialTag destTag);
	
	/**
	 * 根据方案状态更新标签状态
	 * @param schemeStatus	方案状态
	 * @param type	方案类型
	 * @param offtime	方案截止时间
	 * @param bonus TODO
	 * @param tagId
	 */
	void updateTagForSchemeStatus(int schemeStatus, int type, long offtime, double bonus, String tagId);
}