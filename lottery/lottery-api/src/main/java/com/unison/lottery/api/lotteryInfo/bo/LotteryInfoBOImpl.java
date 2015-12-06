package com.unison.lottery.api.lotteryInfo.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.service.MatchNameService;
import com.unison.lottery.api.lang.DateUtil;
import com.unison.lottery.api.lotteryInfo.util.BonusResultConvert;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.response.model.BonusResultResponse;
import com.unison.lottery.api.protocol.response.model.LotteryResultResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BBMatchPlay;
import com.xhcms.lottery.commons.data.FBMatchPlay;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.entity.BBMatchResult;
import com.xhcms.lottery.commons.persist.entity.QTMatchPO;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.conf.LeagueColorConf;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

public class LotteryInfoBOImpl implements LotteryInfoBO {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IStatusRepository status;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
 	private LeagueColorConf initLeagueName; 
	
	@Autowired
	private IssueService issueService;
	@Autowired
	private MatchNameService matchNameService;
	@Override
	@Transactional(readOnly = true)
	public BonusResultResponse queryLotteryInfo(User user) {
		BonusResultResponse response = new BonusResultResponse();
		ReturnStatus succStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.LotteryInfo.BONUS_RESULT_SUCC);
		ReturnStatus failStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.LotteryInfo.BONUS_RESULT_FAIL);

		response.setStatus(failStatus.getStatusCodeForClient());
		response.setDesc(failStatus.getDescForClient());
		response.setReturnStatus(failStatus);
		
		try {
			String nowStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			//竞彩开奖查询
			Map<String, List<Object[]>> m = matchService.listLotteryInfo();
			//高频，时时开奖查询
			Integer[] issueStatus = new Integer[]{
					com.xhcms.lottery.lang.Constants.ISSUE_STATUS_AWARD,
					com.xhcms.lottery.lang.Constants.ISSUE_STATUS_SOLD};
			IssueInfo cqssIssue = issueService.findLatestBallotIssue(Constants.CQSS, issueStatus);
			IssueInfo jx11Issue = issueService.findLatestBallotIssue(Constants.JX11, issueStatus);
			IssueInfo ssqIssue = issueService.findLatestBallotIssue(Constants.SSQ, issueStatus);
			
			List<Object[]> JCZQList = m.get(Constants.JCZQ);
			List<Object[]> JCLQList = m.get(Constants.JCLQ);
			List<Item> list = new ArrayList<Item>();
			if(null != JCZQList) {
				list.add(parseBBAndFBOfBonusResult(JCZQList, nowStr, Constants.JCZQ));
			}
			if(null != JCLQList) {
				list.add(parseBBAndFBOfBonusResult(JCLQList, nowStr, Constants.JCLQ));
			}
			if(null != cqssIssue) {
				list.add(parseHFBonusResult(cqssIssue));
			}
			if(null != jx11Issue) {
				list.add(parseHFBonusResult(jx11Issue));
			}
			if(null != ssqIssue) {
				list.add(parseHFBonusResult(ssqIssue));
			}
			
			//查询传统足彩开奖
	        findCTZCLotteryInfo(list);
	        
			response.setList(list);
			response.setStatus(succStatus.getStatusCodeForClient());
			response.setDesc(succStatus.getDescForClient());
			response.setReturnStatus(succStatus);
		} catch (Exception e) {
			logger.error("查询开奖信息时出错,用户：{}, 错误信息：{}",
					new Object[] { user, e.getMessage() });
		}
		
		return response;
	}
	
	//查询彩种开奖信息
	private void findCTZCLotteryInfo(List<Item> list) {
		String[] playIdList = new String[]{
				PlayType.CTZC_14.getShortPlayStr(),
	        	PlayType.CTZC_R9.getShortPlayStr(),
	        	PlayType.CTZC_BQ.getShortPlayStr(),
	        	PlayType.CTZC_JQ.getShortPlayStr()};
		for(String playId : playIdList) {
			IssueInfo issueInfo = issueService.
					findLatestBallotIssue(LotteryId.CTZC.name(), new Integer[]{
						com.xhcms.lottery.lang.Constants.ISSUE_STATUS_SOLD,
						com.xhcms.lottery.lang.Constants.ISSUE_STATUS_AWARD}, 
						playId);
			Item item = new Item();
			item.lotteryId = Constants.CTZC;
			item.issueNumber = issueInfo.getIssueNumber();
			item.playId = issueInfo.getPlayId();
			
			String bonusCode = issueInfo.getBonusCode();
			bonusCode = BonusResultConvert.lotteryBonusCodeConvertBySplit(bonusCode, "");
			item.result = bonusCode;
			list.add(item);
		}
	}

	private Item parseBBAndFBOfBonusResult(List<Object[]> list, String nowStr, String lotteryId) {
		Item item = new Item();
		item.lotteryId = lotteryId;
		item.issueNumber = nowStr;
		item.resultCount = 0;
		
		if(list.size() > 0) {
			for(Object[] arr : list) {
				item.issueNumber = String.valueOf(arr[0]);
				item.resultCount = Integer.parseInt(String.valueOf(arr[1]));
			}
		}
		return item;
	}
	
	private Item parseHFBonusResult(IssueInfo issue) {
		Item item = new Item();
		item.lotteryId = issue.getLotteryId();
		item.issueNumber = issue.getIssueNumber();
		String bonusCode = issue.getBonusCode();
		bonusCode = BonusResultConvert.lotteryBonusCodeConvert(bonusCode);
		item.result = bonusCode;
		return item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LotteryResultResponse lotteryResult(User user, Map<String, String> m) {
		LotteryResultResponse response = new LotteryResultResponse();
		ReturnStatus succStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.LotteryInfo.LOTTERY_RESULT_SUCC);
		ReturnStatus failStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.LotteryInfo.LOTTERY_RESULT_FAIL);
		
		response.setStatus(failStatus.getStatusCodeForClient());
		response.setDesc(failStatus.getDescForClient());
		response.setReturnStatus(failStatus);
		if(null != m && (3 == m.size() || 4 == m.size())) {
			try {
				String lotteryId = m.get("lotteryId");
				String matchDateStr = m.get("matchDate");
				String pageStr = m.get("page");
				
				Paging paging = new Paging();
				int page = Integer.parseInt(pageStr);
				paging.setPageNo(page);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date from = sdf.parse(matchDateStr + " 00:00:00");
				Date to = sdf.parse(matchDateStr + " 23:59:59");
				
				List<BBMatchResult> bbMatchResults = null;
				List<QTMatchPO> fbMatchResults = null;
				List<Item> list = new ArrayList<Item>();
				
				sdf = new SimpleDateFormat("MM-dd HH:mm");
				if(Constants.JCZQ.equals(lotteryId)) {
					matchService.listFBMatch(paging, from, to);
					fbMatchResults = (List<QTMatchPO>) paging.getResults();
					for(QTMatchPO res : fbMatchResults) {
						Item item = new Item();
						item.lotteryId = Constants.JCZQ;
						item.issueNumber = sdf.format(res.getPlayingTime());
						item.matchId = res.getCnCode();
						String shortNameLeague = res.getLeagueName();
						
						item.leagueShortName = shortNameLeague;
						item.host = res.getHomeTeamName();
						item.guest = res.getGuestTeamName();
						item.leagueColor = res.getColor();
						item.result = res.getScore();
						item.halfResult = res.getHalfScore();
						Map<String, FBMatchPlay> fbMatchPlay = res.getPlays();
						FBMatchPlay fbMatch = fbMatchPlay.get(Constants.PLAY_01_ZC_1);
						String winOption = null;
						if(fbMatch != null){
							winOption = fbMatch.getWinOption();
						}
						item.concedeResult = winOption;
						item.concedePoints = String.valueOf(res.getConcedePoints());
						
						list.add(item);
					}
					response.setList(list);
				} else if(Constants.JCLQ.equals(lotteryId)) {
					matchService.listBBMatch(paging, from, to);
					bbMatchResults = (List<BBMatchResult>) paging.getResults();
					for(BBMatchResult res : bbMatchResults) {
						Item item = new Item();
						item.lotteryId = Constants.JCLQ;
						item.issueNumber = sdf.format(res.getPlayingTime());
						item.matchId = res.getCnCode();
						
						item.leagueShortName = res.getLeagueName();
						item.host = matchNameService.getTeamShortNameByLeagueIdAndTeamName(item.leagueShortName, res.getHomeTeamName());
						item.guest =  matchNameService.getTeamShortNameByLeagueIdAndTeamName(item.leagueShortName, res.getGuestTeamName());
						item.leagueColor = res.getColor();
						item.result = res.getFinalScore();
						
						Map<String, BBMatchPlay> bbMatchPlay = res.getPlays();
						BBMatchPlay bbMatch = bbMatchPlay.get(Constants.PLAY_06_LC_2);
						String winOption = null;
						if(bbMatch != null){
							winOption = bbMatch.getWinOption();
							item.concedePoints = String.valueOf(bbMatch.getDefaultScore());
						}
						item.concedeResult = winOption;
						bbMatch = bbMatchPlay.get(Constants.PLAY_09_LC_1);
						item.dxf = winOption;
						
						list.add(item);
					}
					response.setList(list);
				} else if(Constants.JX11.equals(lotteryId) ||
						Constants.CQSS.equals(lotteryId)) {
					Integer[] issueStatus = new Integer[]{Constants.ISSUE_STATUS_SOLD, 
							Constants.ISSUE_STATUS_AWARD};
					if(StringUtils.isBlank(matchDateStr)) {
						matchDateStr = DateUtil.format(new Date(), "yyyy-MM-dd");
					}
					List<IssueInfo> ballotIssueList = issueService.findBBallotRecords(paging, 
							lotteryId, issueStatus,matchDateStr);
					if(null != ballotIssueList && ballotIssueList.size() > 0) {
						for(IssueInfo issue : ballotIssueList) {
							Item item = new Item();
							item.lotteryId = lotteryId;
							item.issueNumber = issue.getIssueNumber();
							String bonusCode = BonusResultConvert.lotteryBonusCodeConvert(issue.getBonusCode());
							item.result = bonusCode;
							Date prizeTime = issue.getPrizeTime();
							if(null != prizeTime) {
								item.stopDate = DateUtil.format(prizeTime, "yyyy-MM-dd");
								item.stopTime = DateUtil.format(prizeTime, "HH:mm");
							}
							list.add(item);
						}
					}
					response.setList(list);
				} else if(Constants.SSQ.equals(lotteryId)) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					Integer[] issueStatus = new Integer[]{Constants.ISSUE_STATUS_SOLD, Constants.ISSUE_STATUS_AWARD};
					List<IssueInfo> ballotIssueList = issueService.findBBallotRecords(paging, lotteryId, issueStatus);
					if(null != ballotIssueList) {
						SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
						for(IssueInfo issue : ballotIssueList) {
							Item item = new Item();
							item.lotteryId = Constants.SSQ;
							item.issueNumber = issue.getIssueNumber();
							item.result = issue.getBonusCode();
							Date prizeTime = issue.getPrizeTime();
							if(null != prizeTime) {
								item.stopDate = sdf.format(prizeTime);
								item.stopTime = sdfTime.format(prizeTime);
							}
							list.add(item);
						}
					}
					response.setList(list);
				} else if(Constants.CTZC.equals(lotteryId)) {
					String playId = m.get("playId");
					if(StringUtils.isBlank(playId)) {
						playId = PlayType.CTZC_R9.getShortPlayStr();
					}
					Integer[] issueStatus = new Integer[]{Constants.ISSUE_STATUS_SOLD, Constants.ISSUE_STATUS_AWARD};
					List<IssueInfo> ballotIssueList = issueService.findBBallotRecordsOfPlayId(paging, lotteryId, issueStatus, playId);
					if(null != ballotIssueList) {
						for(IssueInfo issue : ballotIssueList) {
							Item item = new Item();
							item.lotteryId = Constants.CTZC;
							item.issueNumber = issue.getIssueNumber();
							String bonusCode = BonusResultConvert.lotteryBonusCodeConvertBySplit(issue.getBonusCode(), "");
							item.result = bonusCode;
							Date prizeTime = issue.getPrizeTime();
							if(null != prizeTime) {
								item.stopDate = DateUtil.format(prizeTime, DateUtil.SHORT_PT);
								item.stopTime = DateUtil.format(prizeTime, "HH:mm");
							}
							list.add(item);
						}
					}
					response.setList(list);
				}
				
				response.setPageNo(page);
				response.setTotalPage(paging.getPageCount());
				response.setStatus(succStatus.getStatusCodeForClient());
				response.setDesc(succStatus.getDescForClient());
				response.setReturnStatus(succStatus);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("查询彩种开奖详情时出错,用户：{}, 错误信息：{}",
						new Object[] { user, e.getMessage() });
			}
		}
		return response;
	}
}