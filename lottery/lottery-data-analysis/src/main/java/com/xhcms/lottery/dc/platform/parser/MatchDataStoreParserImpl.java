package com.xhcms.lottery.dc.platform.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchModel;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchesResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCPlayOddsModel;
import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiLotteryIdToDavcaiLotteryIdUtil;
import com.davcai.lottery.platform.client.qiutan.QiutanJCMatch;
import com.davcai.lottery.platform.client.qiutan.QiutanJCMatchInfo;
import com.davcai.lottery.platform.client.zunao.model.ZunAoJCMatchesResponse;
import com.davcai.lottery.platform.constants.AnRuiZhiYingLotteryPlayType;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.QtMatchBaseMQModel;
import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.task.ticket.OpenSaleIntercessor;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.DateUtils;
import com.xhcms.lottery.utils.WeekUtil;

/**
 * 将不同的平台的响应赛程数据转换成统一的赛程存储格式
 * @author haoxiang.jiang@davcai.com
 * @date 2015年1月13日 下午2:31:54
 */
@Service
public class MatchDataStoreParserImpl implements IMatchDataStoreParser {
	// 开售时间处理器
	@Autowired
	MatchColorDao matchColorDao;
	
	@Autowired
	MessageSender messageSender;
	
	@Autowired
	private OpenSaleIntercessor openSaleIntercessor;
	private int forwardMinutes = 15;//提前15分钟
	private int offtimeMinutes = 1;//提前1分钟
	
	private List<Match> parseByZhongMinResp(LotteryPlatformBaseResponse resp,LotteryId lotteryId){
		if(resp instanceof ZunAoJCMatchesResponse){
			ZunAoJCMatchesResponse z = (ZunAoJCMatchesResponse)resp;
			return z.getMatches();
		} else {
			return new ArrayList<Match>();
		}
	}
	private List<Match> parseByAnRuiResp(LotteryPlatformBaseResponse resp,LotteryId lotteryId){
		if(resp instanceof AnRuiZhiYingJCMatchesResponse){
			AnRuiZhiYingJCMatchesResponse response = (AnRuiZhiYingJCMatchesResponse)resp;
			List<AnRuiZhiYingJCMatchModel> anRuimatches = response.getMatches();
			return composeMatchesFromAnRuiResp(anRuimatches,lotteryId);
		} else {
			return new ArrayList<Match>();
		}
	}
	private List<Match> composeMatchesFromAnRuiResp(List<AnRuiZhiYingJCMatchModel> anRuimatches,LotteryId lotteryId) {
		List<Match> laicaiMatches = new ArrayList<Match>();
		for (AnRuiZhiYingJCMatchModel match : anRuimatches) {
			Match m = new Match();
			m.setAnruiMatchId(Long.parseLong(match.getMatchId()));
			m.setCnCode(match.getGameNo());
			m.setCode(WeekUtil.getWeekNumByCnName(m.getCnCode().substring(0,2)) + m.getCnCode().substring(2));
			m.setGuestTeam(match.getAwayName());
			m.setHomeTeam(match.getHomeName());
			m.setLeague(match.getGameName());
			m.setLongLeagueName(match.getGameName());
			if(LotteryId.JCZQ.name().equals(lotteryId.name())){//足球
				m.setName(m.getHomeTeam() + " VS " + m.getGuestTeam());
			} else if(LotteryId.JCLQ.name().equals(lotteryId.name())){//篮球
				m.setName(m.getGuestTeam() + " VS " + m.getHomeTeam());
			}
			Date matchtime= DateUtils.getDateByFormatString(match.getGameTime(),DateUtils.defaultPattern);
			m.setEntrustDeadline(openSaleIntercessor.parseEntrustDeadline(lotteryId.name(), getJcEntrustTime(matchtime)));
			m.setOfftime(getJcOffTime(matchtime));
			m.setPlayingTime(matchtime);
			String matchId = DateUtils.formatShort(m.getOfftime()).replace("-", "") + m.getCode(); 
			m.setMatchId(Long.parseLong(matchId));
			m.setPlayIds(calSupportPlayIds(match, m));
			laicaiMatches.add(m);
		}
		return laicaiMatches;
	}
	/**
	 * 根据“安瑞竞彩数据模型”分析支持玩法(仅计算单关)
	 * 同时会根据支持玩法对m设置让分数据
	 * @param match
	 * @param m
	 * @return 支持的玩法列表
	 */
	private List<String> calSupportPlayIds(AnRuiZhiYingJCMatchModel match,Match m){
		List<String> playIds = new ArrayList<String>();
		for (AnRuiZhiYingJCPlayOddsModel playOdds : match.getPlayOdds()) {
			String ltId = playOdds.getLotteryId();
			String laicaiPlayType = AnRuiLotteryIdToDavcaiLotteryIdUtil.translate(ltId);
			PlayType p = PlayType.valueOfLcPlayId(laicaiPlayType);
			if("1".equals(playOdds.getGgSaleStatus())){//过关销售中，提取让球让分数据
				if(AnRuiZhiYingLotteryPlayType.JCZQ_RQSPF.equals(ltId)){//竞彩足球让球
					m.setConcedePoints((int)Float.parseFloat((playOdds.getGgRq())));
				} else if(AnRuiZhiYingLotteryPlayType.JCLQ_RFSF.equals(ltId)){//竞彩篮球让分过关
					m.setConcedeScorePass(Float.parseFloat(playOdds.getGgRq()));
				} else if(AnRuiZhiYingLotteryPlayType.JCLQ_DXF.equals(ltId)){//竞彩篮球大小分过关
					m.setGuestScorePass(Float.parseFloat(playOdds.getGgRq()));
				}
			}
			//单关固定
			String dgPlayId = p.getPlayIdWithPass(true);
			if("0".equals(playOdds.getDgGdSaleStatus())){//未开售
			} else if("1".equals(playOdds.getDgGdSaleStatus())){//销售中
				playIds.add(dgPlayId);
				if(AnRuiZhiYingLotteryPlayType.JCLQ_RFSF.equals(ltId)){//竞彩篮球让分
					m.setConcedeScore(Float.parseFloat(playOdds.getGgRq()));
				} else if(AnRuiZhiYingLotteryPlayType.JCLQ_DXF.equals(ltId)){//竞彩篮球大小分
					m.setGuestScore(Float.parseFloat(playOdds.getGgRq()));
				}
			} else if("2".equals(playOdds.getDgGdSaleStatus())){//停售
			} else if("-1".equals(playOdds.getDgGdSaleStatus())){//暂停销售
			} else if("-2".equals(playOdds.getDgGdSaleStatus())){//比赛取消
			} else if("-3".equals(playOdds.getDgGdSaleStatus())){//延期
			}
		}
		return playIds;
	}
	@Override
	@Transactional
	public List<Match> parseMatches(LotteryPlatformBaseResponse resp,
			String platformId,LotteryId lotteryId) {
		if(LotteryPlatformId.AN_RUI_ZHI_YING.equals(platformId)){
			return parseByAnRuiResp(resp,lotteryId);
		} else if(LotteryPlatformId.ZUN_AO.equals(platformId)){
			return parseByZhongMinResp(resp,lotteryId);
		} else if(LotteryPlatformId.QIU_TAN.equals(platformId)){
			return parseByQiutanResp(resp,lotteryId);
		} else {
			return new ArrayList<Match>();
		}
	}
	
	private void sendQiuTanMatchToMQ(QiutanJCMatch qiutanJCMatch){
		QtMatchBaseMQModel qtMatchBaseMQModel = new QtMatchBaseMQModel();
		qtMatchBaseMQModel.setBsId(qiutanJCMatch.getScheduleid());
		qtMatchBaseMQModel.setLeagueId(qiutanJCMatch.getSclass());
		qtMatchBaseMQModel.setMatchTime(DateUtils.getDateByFormatString(qiutanJCMatch.getMatchtime(),DateUtils.yyyyMMddhhmmss));
		qtMatchBaseMQModel.setHomeTeamName(qiutanJCMatch.getHometeam());
		qtMatchBaseMQModel.setGuestTeamName(qiutanJCMatch.getGuestteam());
		qtMatchBaseMQModel.setColor(qiutanJCMatch.getColor());
		qtMatchBaseMQModel.setJingcaiId(qiutanJCMatch.getMatchid());
		messageSender.send(qtMatchBaseMQModel);
	}
	
	
	private List<Match> parseByQiutanResp(LotteryPlatformBaseResponse resp,
			LotteryId lotteryId) {
		if(resp instanceof QiutanJCMatchInfo){
			List<QiutanJCMatch> qiutanJCMatchs = ((QiutanJCMatchInfo)resp).getMatches();
			List<Match> laicaiMatches = new ArrayList<Match>();
			for (QiutanJCMatch qiutanJCMatch: qiutanJCMatchs) {
				sendQiuTanMatchToMQ(qiutanJCMatch);//发送到消息队列
				Match m = new Match();
				m.setCnCode(qiutanJCMatch.getMatchid());
				m.setCode(WeekUtil.getWeekNumByCnName(m.getCnCode().substring(0,2)) + m.getCnCode().substring(2));
				m.setGuestTeam(qiutanJCMatch.getGuestteam());
				m.setHomeTeam(qiutanJCMatch.getHometeam());
				if(StringUtils.isBlank(qiutanJCMatch.getSclass())){
					qiutanJCMatch.setSclass("");
				}
				String longLeagueName = matchColorDao.findLongByShort(qiutanJCMatch.getSclass().trim());
				m.setLeague(StringUtils.isBlank(longLeagueName)?qiutanJCMatch.getSclass().trim():longLeagueName);
				m.setLongLeagueName(longLeagueName);
				if(LotteryId.JCZQ.name().equals(lotteryId.name())){//足球
					m.setName(m.getHomeTeam() + " VS " + m.getGuestTeam());
				} else if(LotteryId.JCLQ.name().equals(lotteryId.name())){//篮球
					m.setName(m.getGuestTeam() + " VS " + m.getHomeTeam());
				}
				Date matchtime= DateUtils.getDateByFormatString(qiutanJCMatch.getMatchtime(),DateUtils.yyyyMMddhhmmss);
				m.setEntrustDeadline(openSaleIntercessor.parseEntrustDeadline(lotteryId.name(), getJcEntrustTime(matchtime)));
				m.setOfftime(getJcOffTime(matchtime));
				m.setPlayingTime(matchtime);
				String matchId = DateUtils.formatShort(m.getOfftime()).replace("-", "") + m.getCode(); 
				m.setMatchId(Long.parseLong(matchId));
				m.setConcedePoints(Integer.valueOf(qiutanJCMatch.getPolygoal()));
				m.setPlayIds(calSupportPlayIds(qiutanJCMatch));
				laicaiMatches.add(m);
			}
			return laicaiMatches;
		}
		return new ArrayList<Match>();
	}
	
	private List<String> calSupportPlayIds(QiutanJCMatch qiutanJCMatch) {
		List<String> playIds = new ArrayList<String>();
		String[] typeids = qiutanJCMatch.getHidetypeid().split(",");
		List<String> typeids2 = calNotSupportPlayIdsByOdds(qiutanJCMatch);
		HashMap<String, String> hashMap=new HashMap<String, String>();
		hashMap.put("101", PlayType.JCZQ_SPF.getPlayIdWithPass(true));
		hashMap.put("102", PlayType.JCZQ_BF.getPlayIdWithPass(true));
		hashMap.put("103", PlayType.JCZQ_ZJQS.getPlayIdWithPass(true));
		hashMap.put("104", PlayType.JCZQ_BQC.getPlayIdWithPass(true));
		hashMap.put("105", PlayType.JCZQ_BRQSPF.getPlayIdWithPass(true));
		if(typeids.length > 0){
			for (String s : typeids) {
				if(null != hashMap.get(s)){
					hashMap.remove(s);
				}
			}
		}
		//根据赔率在计算一次玩法支持情况，以防止赔率为0情况
		if(!typeids2.isEmpty()){
			for (String s : typeids2) {
				if(null != hashMap.get(s)){
					hashMap.remove(s);
				}
			}
		}
		if(hashMap.size() > 0){
			for (String string : hashMap.keySet()) {
				playIds.add(hashMap.get(string));
			}
		}
		return playIds;
	}
	
	private List<String> calNotSupportPlayIdsByOdds(QiutanJCMatch qiutanJCMatch){
		List<String> notSupportList = new ArrayList<>();
		//让球胜平负
		String wl0 = qiutanJCMatch.getWl0();
		String wl1 = qiutanJCMatch.getWl1();
		String wl3 = qiutanJCMatch.getWl3();
		if(new BigDecimal(wl0).equals(BigDecimal.ZERO)
				|| new BigDecimal(wl1).equals(BigDecimal.ZERO)
				|| new BigDecimal(wl3).equals(BigDecimal.ZERO)){
			notSupportList.add("101");
		}
		//比分
		if(BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw10()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw20()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw21()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw30()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw31()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw32()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw40()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw41()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw42()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw50()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw51()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw52()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSw5()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSd00()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSd11()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSd22()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSd33()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSd4()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl01()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl02()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl12()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl03()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl13()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl23()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl04()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl14()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl24()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl05()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl15()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl25()))
				|| BigDecimal.ZERO.equals(new BigDecimal(qiutanJCMatch.getSl5()))){
			notSupportList.add("102");
		}
		//进球数
		String t0 = qiutanJCMatch.getT0();
		String t1 = qiutanJCMatch.getT1();
		String t2 = qiutanJCMatch.getT2();
		String t3 = qiutanJCMatch.getT3();
		String t4 = qiutanJCMatch.getT4();
		String t5 = qiutanJCMatch.getT5();
		String t6 = qiutanJCMatch.getT6();
		String t7 = qiutanJCMatch.getT7();
		if(new BigDecimal(t0).equals(BigDecimal.ZERO)
				|| new BigDecimal(t1).equals(BigDecimal.ZERO)
				|| new BigDecimal(t2).equals(BigDecimal.ZERO)
				|| new BigDecimal(t3).equals(BigDecimal.ZERO)
				|| new BigDecimal(t4).equals(BigDecimal.ZERO)
				|| new BigDecimal(t5).equals(BigDecimal.ZERO)
				|| new BigDecimal(t6).equals(BigDecimal.ZERO)
				|| new BigDecimal(t7).equals(BigDecimal.ZERO)){
			notSupportList.add("103");
		}
		//半全场
		String ht33 = qiutanJCMatch.getHt33();
		String ht31 = qiutanJCMatch.getHt31();
		String ht30 = qiutanJCMatch.getHt30();
		String ht13 = qiutanJCMatch.getHt13();
		String ht11 = qiutanJCMatch.getHt11();
		String ht10 = qiutanJCMatch.getHt10();
		String ht03 = qiutanJCMatch.getHt03();
		String ht01 = qiutanJCMatch.getHt01();
		String ht00 = qiutanJCMatch.getHt00();
		if(BigDecimal.ZERO.equals(ht33)
				|| BigDecimal.ZERO.equals(new BigDecimal(ht31))
				|| BigDecimal.ZERO.equals(new BigDecimal(ht30))
				|| BigDecimal.ZERO.equals(new BigDecimal(ht13))
				|| BigDecimal.ZERO.equals(new BigDecimal(ht11))
				|| BigDecimal.ZERO.equals(new BigDecimal(ht10))
				|| BigDecimal.ZERO.equals(new BigDecimal(ht03))
				|| BigDecimal.ZERO.equals(new BigDecimal(ht01))
				|| BigDecimal.ZERO.equals(new BigDecimal(ht00))){
			notSupportList.add("104");
		}
			
		//胜平负
		String sf0 = qiutanJCMatch.getSf0();
		String sf1 = qiutanJCMatch.getSf1();
		String sf3 = qiutanJCMatch.getSf3();
		if(new BigDecimal(sf0).equals(BigDecimal.ZERO)
				|| new BigDecimal(sf1).equals(BigDecimal.ZERO)
				|| new BigDecimal(sf3).equals(BigDecimal.ZERO)){
			notSupportList.add("105");
		}
		return notSupportList;
	}
	
	/**
	 * 根据比赛时间计算offtime
	 * @param matchTime
	 * @return
	 */
	protected Date getJcOffTime(Date matchTime){
		Calendar c = Calendar.getInstance();
		c.setTime(matchTime);
		c.add(Calendar.MINUTE, -offtimeMinutes);
		return c.getTime();
	}
	/**
	 * 根据比赛时间计算代理截至出票时间
	 * @param matchTime
	 * @return
	 */
	protected Date getJcEntrustTime(Date matchTime){
		Calendar c = Calendar.getInstance();
		c.setTime(matchTime);
		c.add(Calendar.MINUTE, -forwardMinutes);
		return c.getTime();
	}
	public OpenSaleIntercessor getOpenSaleIntercessor() {
		return openSaleIntercessor;
	}
	public void setOpenSaleIntercessor(OpenSaleIntercessor openSaleIntercessor) {
		this.openSaleIntercessor = openSaleIntercessor;
	}
}
