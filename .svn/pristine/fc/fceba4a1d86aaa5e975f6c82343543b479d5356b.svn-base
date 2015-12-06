package com.unison.lottery.weibo.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.unison.lottery.weibo.common.service.WeiboMatchIdService;
import com.unison.lottery.weibo.data.LotteryLetter;
import com.unison.lottery.weibo.data.MatchIdInfo;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

@Service
public class WeiboMatchIdServiceImpl implements WeiboMatchIdService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String prefix = "20";
	
	private static final String complement = "0";
	
	@Autowired
	private QTMatchidDao qtMatchidDao;
	
	@Transactional
	@Override
	public List<MatchIdInfo> reversionAndCheckMatchId(WeiboMsg weiboMsg) {
		List<String> matchStrList = analyzeMatchIdBaseWeiboMsg(weiboMsg);
		List<MatchIdInfo> matchInfoList = reversionMatchId(matchStrList);
		List<MatchIdInfo> rs = new ArrayList<MatchIdInfo>();
		//查询数据库通过大V彩赛事ID获取球探赛事ID
		if(null != matchInfoList) {
			for(MatchIdInfo idInfo : matchInfoList) {
				String lcMatchId = idInfo.getMatchId();
				if(StringUtils.isBlank(lcMatchId)) {
					continue;
				}
				long qtMatchId = qtMatchidDao.findQTMatchId(lcMatchId);
				if(qtMatchId > 0) {
					idInfo.setQtMatchId(qtMatchId);
					rs.add(idInfo);
				}
			}
		}
		return rs;
	}
	
	
	//拆分并反解传统足球赛事串,格式(1404212:前五位是期号，后两位表示赛事序号，在构建时赛事序号不足两位时是加0补全的,
	//反解时需要判断；反解赛事ID默认使用14场的玩法)
	protected String split14MatchStr(String matchStr) {
		if(StringUtils.isBlank(matchStr) ||
				matchStr.length() != 7) {
			log.error("非法的传统足彩赛事串，长度必须为7位.");
			return null;
		}
		String issueId = matchStr.substring(0, 5);
		String matchSeq = matchStr.substring(5);
		if(matchSeq.startsWith(complement)) {
			matchSeq = matchSeq.substring(1);
		}
		String rs = issueId + PlayType.CTZC_14 + matchSeq;
		log.debug("赛事串={},反解得到的比赛ID={}", new Object[]{matchStr, rs});
		return rs;
	}
	
	/**
	 * 反解赛事串集合到比赛ID
	 * @param weiboMsg
	 * @return
	 */
	protected List<MatchIdInfo> reversionMatchId(List<String> matchStrList) {
		List<MatchIdInfo> matchIdList = new ArrayList<MatchIdInfo>();
		for(String matchStr : matchStrList) {
			MatchIdInfo matchIdInfo = new MatchIdInfo();
			String matchId = null;
			if(matchStr.startsWith(LotteryLetter.JZ.name())) {
				matchId = prefix + matchStr.substring(2);
				matchIdInfo.setLottery(LotteryId.JCZQ.name());
			} else if(matchStr.startsWith(LotteryLetter.JL.name())) {
				matchId = prefix + matchStr.substring(2);
				matchIdInfo.setLottery(LotteryId.JCLQ.name());
			} else if(matchStr.startsWith(LotteryLetter.CZ.name())) {
				matchId = split14MatchStr(matchStr);
				matchIdInfo.setLottery(LotteryId.CTZC.name());
			}
			matchIdInfo.setMatchId(matchId);
			matchIdList.add(matchIdInfo);
		}
		return matchIdList;
	}
	
	/**
	 * 解析微博对象中出现的赛事串集合
	 * @param weiboMsg
	 * @return
	 */
	public List<String> analyzeMatchIdBaseWeiboMsg(WeiboMsg weiboMsg){
		List<String> matchIdList = new ArrayList<String>();
		if(null != weiboMsg &&
				StringUtils.isNotBlank(weiboMsg.getContent())) {
			String content = weiboMsg.getContent();
			String matchPattern = "\\$.*?\\(([a-zA-Z]+\\d+)\\)\\$";
			Pattern pattern = Pattern.compile(matchPattern);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				String str = matcher.group(1);
				matchIdList.add(str);
			}
		}
		log.info("从微博内容={},中解析出的赛事={}", new Object[]{
				weiboMsg.getContent(), matchIdList});
		return matchIdList;
	}
	
}