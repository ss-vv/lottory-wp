package com.xhcms.lottery.commons.persist.service;

import java.util.List;
import com.xhcms.lottery.commons.data.BetScheme;

/**
 * @desc 推荐方案
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-28
 * @version 1.0
 */
public interface BetSchemeRecService {

	/**
	 * 根据推荐信息，返回一个BetScheme对象，注意推荐信息中的
	 * passTypes,multiple, money可为空
	 * @param playId
	 * @param betMatchs
	 * @param passTypes
	 * @param multiple
	 * @return
	 */
	BetScheme getScheme(String playId, String betMatchs,
			String passTypes, int money, int multiple, String bonus);
	
	void saveBetScheme(BetScheme scheme, long sponsorId, String content, String annotations);
	
	/**
	 * 拆分推荐方案赛事内容
	 * @param betMatchs
	 * 赛事串格式:201404022302-23021-false-sf,201404022303-23031-false-sf
	 * @return
	 */
	List<Long> splitBetMatchs(String betMatchs);
	
	BetScheme viewRecScheme(Long id);
	
	/**
	 * 根据方案ID获取串关内容
	 * @param id
	 * @return
	 */
	String getPlayType(Long id);
}