package com.unison.lottery.weibo.data.service.store.persist.service;

import java.util.List;
import com.unison.lottery.weibo.data.service.store.data.BBAsianOddResult;
import com.unison.lottery.weibo.data.service.store.data.BBAsianOddVO;
import com.unison.lottery.weibo.data.service.store.data.BBEuropeOddVO;
import com.unison.lottery.weibo.data.service.store.data.BBMatchScore;
import com.unison.lottery.weibo.data.service.store.data.BBOddsBigSmallResult;
import com.unison.lottery.weibo.data.service.store.data.BBOddsBigSmallVO;

/**
 * @desc 篮球赛事数据服务：查询比分、欧赔、亚盘数据
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-13
 * @version 1.0
 */
public interface BBMatchDataService {

	/**
	 * 根据赛事ID获取赛事比分结果，同时也包含完整的赛事数据内容
	 * @param lcMatchId	大V彩赛事ID
	 * @return
	 */
	public BBMatchScore getBBMatch(String lcMatchId);
	
	/**
	 * 获取篮球对战赛事的欧赔指数
	 * @param lcMatchId 大V彩赛事ID
	 * @return
	 */
	List<BBEuropeOddVO> bbEuropeOddList(String lcMatchId);
	
	/**
	 * 获取篮球对战赛事的亚盘水位(取值:“篮球让分赔率”)
	 * @param lcMatchId
	 * @return
	 */
	List<BBAsianOddVO> bbAsianOddList(String lcMatchId);
	
	/**
	 * 获取篮球对战赛事的亚盘水位(取值:“篮球让分赔率”),包含主客队名称
	 * @param lcMatchId
	 * @return
	 */
	BBAsianOddResult bbAsianOddResult(String lcMatchId);
	
	/**
	 * 根据大V彩赛事ID查询篮球大小球盘口
	 * @param lcMatchId
	 * @return
	 */
	List<BBOddsBigSmallVO> bbBigSmallOddList(String lcMatchId);
	
	/**
	 * 包含主客队名称的篮球大小球盘口
	 * @param lcMatchId
	 * @return
	 */
	BBOddsBigSmallResult bbBigSmallOddResult(String lcMatchId);
}