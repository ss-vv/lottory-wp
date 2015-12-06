package com.xhcms.lottery.commons.persist.service;

import java.util.List;
import java.util.Map;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.dc.data.Match;

/**
 * @desc 猜冠军服务
 * @createTime 2014-5-22
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public interface CGJTeamService {
	
	/**
	 * 更新猜冠军队伍信息，包含字段：赔率，投注截止时间，比赛开始时间
	 * @param data
	 * @param year 年份
	 * @param playType 玩法类型
	 */
	void updateCGJTeam(List<Match> data, String year, String playType);
	
	/**
	 * 根据年份和玩法类型，将当前玩法不是year的赛季的都重置为非当前
	 * @param year
	 * @param playType
	 */
	void resetCgjTeamCurrentSeason(String year, String playType);
	
	/**
	 * 根据投注队伍的序号，获取队伍数据
	 * @param code
	 * @param playId
	 * @return
	 */
	List<CGJTeam> listTeamsByCode(List<String> code, String playId);
	
	/**
	 * 根据方案ID获取猜冠军投注内容
	 * @param schemeId
	 * @return
	 */
	List<CGJTeam> listTeamsBySchemeId(long schemeId);
	
	/**
	 * 获取指定玩法，当前赛季的队伍数据
	 * @param playId	<p>参考 PlayType</p>
	 * @return
	 */
	Map<String, List<CGJTeam>> listTeamsCurrentSeason(String playId);
}