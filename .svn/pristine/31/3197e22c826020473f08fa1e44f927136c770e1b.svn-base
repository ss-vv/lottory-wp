package com.xhcms.lottery.commons.persist.service;

import java.util.List;
import java.util.Map;

import com.xhcms.commons.lang.Paging;

/**
 * 联赛颜色管理
 * @author Wang Lei
 *
 */
public interface MatchColorService {
	/**
	 * 根据玩法返回在售的赛事对应的颜色列表
	 * @param playId
	 * @return
	 */
	List<String> findMatchColorOnSale(String playId);
	
	/**
	 * 查找已有颜色联赛名称和颜色列表
	 * @param lotteryId
	 * @param paging
	 */
	void listColoured(String lotteryId,Paging paging);
	
	/**
	 * 查询无颜色联赛名称列表
	 * @param lotteryId
	 * @param paging
	 */
	void listNoColor(String lotteryId,Paging paging);
	
	/**
	 * 添加一个新的联赛颜色
	 * @param lotteryId
	 * @param color
	 * @param matchId
	 * @return
	 */
	int saveColor(String lotteryId,String color,Long matchId);
	
	/**
	 * 跟新一个联赛颜色
	 * @param color
	 * @param matchColorId
	 * @return
	 */
	int updateColor(String color,Long matchColorId);
	/**
	 * 更新一个联赛的短名称
	 * @param color
	 * @param matchColorId
	 * @return
	 */
	int updateShortName(String shortName,Long matchColorId);
	
	/**
	 * 插入一条赛事短名称记录，颜色值为默认
	 * @param league_name
	 * @param league_name_for_short
	 * @param lotteryId
	 * @param color 默认值：#339933
	 * @return
	 */
	Long saveMatchColor(String leagueName, String leagueNameForShort, String lotteryId,String color);
	
	/**
	 * 加载联赛名，联赛颜色对应List
	 * 
	 * Map的key 联赛名；value 联赛颜色
	 * 
	 * @return
	 */
	Map<String,String> loadLeagueNameColorMap();

	Map<String, String> loadLeagueNameShortName();

	Long saveColor(String lotteryId, String color, Long id,
			String shortLeagueName);

	int updateColor(String color, Long id, String shortLeagueName);
}
