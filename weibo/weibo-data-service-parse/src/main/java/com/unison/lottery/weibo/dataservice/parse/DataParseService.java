package com.unison.lottery.weibo.dataservice.parse;

import java.util.Date;
import java.util.List;

import com.unison.lottery.weibo.dataservice.parse.model.BBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBCurrentDayOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeagueData;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOdds;
import com.unison.lottery.weibo.dataservice.parse.model.BBTeamData;
import com.unison.lottery.weibo.dataservice.parse.model.CurrentDayOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFChangeData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFDetail;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFResultData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.FBEvent;
import com.unison.lottery.weibo.dataservice.parse.model.FBLeagueData;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchidData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTeamData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTechnicData;

public interface DataParseService {

	/**
	 * 获取足球赔率
	 */
	CurrentDayOddsData getCurrentDayOdds();

	CurrentDayOddsData getCurrentDayOddsAndSaveFile();
	
	/**
	 * 历史盘口接口
	 */

	
	CurrentDayOddsData getFBChangeOddsHistoryAndSaveFile(Date date);

	CurrentDayOddsData getFBChangeOddsHistory(Date date);
	
	
	/**
	 * 足球赔率30秒变化接口
	 */

	CurrentDayOddsData getFBChangeOdds30SecAndSaveFile();
	
	CurrentDayOddsData getFBChangeOdds();
	
	
	
	
	/**
	 * 百家欧赔接口
	 */
	FBBjEuropeOddsData getFBBjEuropeOddsAndSaveFile(String dateNum,Date date,String minNum);
	
	FBBjEuropeOddsData getFBBjEuropeOdds(String dateNum,Date date,String minNum);
	

	/**
	 * 赛程赛果
	 * http://dxbf.bet007.com/BF_XML.aspx?date=yyyy-MM-dd (带时间)
	 */
	FBBFResultData getFBBfResultAndSaveFile(Date date);
	
	FBBFResultData getFBBfResult(Date date);
	
	
	/**
	 * 获取英超本赛季的赛程

	 * http://bf.bet007.com/BF_XML.aspx?sclassid=36
	 */
	
	FBBFResultData getFBBfYingChaoAndSaveFile(String sclassid);
	FBBFResultData getFBBfYingChao(String sclassid);
	
	
	/**
	 * 联赛杯赛
	 */

	FBLeagueData getFBLeagueAndSaveFile() ;
	
	FBLeagueData getFBLeague();
	
	
	/**
	 * 球队资料
	 */

	FBTeamData getFBTeamAndSaveFile();
	
	FBTeamData getFBTeam();
	
	/**
	 * 篮球球队信息
	 * @return
	 */
	BBTeamData getBBTeam();
	
	/**
	 * 彩票赛事与球探网的关联表
	 */
	FBMatchidData getFBMatchidAndSaveFile();
	FBMatchidData getFBMatchid();
	
	
	/**
	 * 一天内比赛的技术统计
	 */

	FBTechnicData getFBTechnicAndSaveFile();
	FBTechnicData getFBTechnic() ;
	
	
	/**
	 * 按赛程ID查该比赛的数据
	 * http://dxbf.bet007.com/BF_XMLByID.aspx?id=比赛ID集
	 */
	
	FBBFResultData getFBBFResultByIDAndSaveFile(String id);
	FBBFResultData getFBBFResultByID(String id);
	
	
	
	
	/**
	 * 当天比分数据(js解析)
	 */
	FBBFData getFBBFDataAndSaveFile();
	FBBFData getFBBFData();
	/**
	 * 20秒内变化数据 (实时更新) 
		
	 */
	FBBFChangeData getFBBF20SecChangeAndSaveFile();
	FBBFChangeData getFBBF20SecChange();
	
	/**
	 * 150秒内变化数据，
	 */
	FBBFChangeData getFBBF150SecChangeAndSaveFile();
	FBBFChangeData getFBBF150SecChange();
	
	/**
	 * 当天比赛的入球、红黄牌事件(js解析)
	 */
	FBBFDetail getFBBFDetailAndSaveFile();
	FBBFDetail getFBBFDetail() ;
	
	/**
	 * 比赛详细事件接口（历史数据）
	 * http://dxbf.bet007.com/Event_XML.aspx?date=yyyy-mm-dd
	 */
	FBEvent getFBEventAndSaveFile(Date date);
	FBEvent getFBEvent(Date date);

	/**
	 * 按日期获取篮球的赛程赛果，《篮球比分接口.doc》中的“三、赛程赛果”
	 */
	List<BBMatchInfoData> getBBMatchInfo(Date date);
	
	/**
	 * 联赛id和赛季，获取篮球的赛程赛果，《篮球比分接口.doc》中的“三、赛程赛果”
	 */
	List<BBMatchInfoData> getBBMatchInfo(String sclassId, String season);

	/**
	 * 获取篮球今日比赛信息
	 */
	List<BBMatchInfoData> getBBMatchInfoToday();

	/**
	 * 获取篮球即时比赛信息
	 */
	List<BBMatchInfoData> getBBMatchInfoRealtime();
	
	/**
	 * 篮球联赛信息
	 * @return
	 */
	BBLeagueData getBBLeague();	
	
	/**
	 * 篮球赔率接口
	 *http://nba.bet007.com/odds/OddsData/oddsBsk.txt
	 */
	
	BBCurrentDayOddsData getBBCurrentDayOdds();
	
	
	
	/**
	 * 篮球30S变化赔率接口
	 *http://nba.bet007.com/odds/OddsData/ch_oddsBsk.xml
	 */
	
	BBCurrentDayOddsData getBBChangeOdds30Sec();
	
	
	/**
	 * 篮球历史盘口：
	  http://nba.bet007.com/odds/OddsData/overData.aspx?date= yyyy-MM-dd 
	 */
	
	BBCurrentDayOddsData getBBChangeOddsHistory(Date date);
	
	/**
	 * 篮球百家欧赔接口
		http://data.nowgoal.com/1x2/fbdatacn.aspx
	 */
	
	BBBjEuropeOddsData getBBBjEuropeOdds(String dateNum,Date date,String minNum);

	/**
	 * 获取篮球历史赔率
	 * @param date 日期
	 * @return
	 */
	BBOdds getBBOddsHistory(Date date);

	/**
	 * 获取篮球今日赔率
	 */
	BBOdds getBBOdds();

	/**
	 * 获取篮球即时变化赔率
	 */
	BBOdds getBBOddsRealtime();

	public abstract Date convertGMT8ToBeijingTime(String dateTimeStr);

	public abstract Date convertGMT0ToBeijingTime(String dateTimeStr);

}
