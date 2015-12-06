package com.xhcms.lottery.admin.web.action.preset;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.service.ZMJoinQtMatchService;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetSchemePreset;
import com.xhcms.lottery.commons.data.PresetResult;
import com.xhcms.lottery.commons.persist.service.PresetAwardService;
import com.xhcms.lottery.commons.util.MatchResults;
import com.xhcms.lottery.lang.LotteryId;

/**
 * 预兑奖竞彩足球篮球方案
 * @author Wang Lei
 *
 */
public class AjaxPresetAction extends BaseAction {
	private static final long serialVersionUID = 5235943196152554334L;
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private PresetAwardService presetAwardService;
	@Autowired
	private ZMJoinQtMatchService zmJoinQtMatchService;
	
	private String sponsor;
	private Long schemeId;
	private String lotteryId;
	private List<Long> id;
	private BetSchemePreset betSchemePreset;
	private List<BetSchemePreset> betSchemePresets;
	private Data data = Data.success("");
	private String issueNumber;
	
	/**
	 * 预兑奖传统足彩方案
	 */
	public String presetCTZC() {
		PresetResult presetResult = presetAwardService.presetCTZC(issueNumber);
		data.setData(presetResult);
		return SUCCESS;
	}
	/**
	 * 预兑奖竞彩足球方案
	 */
	public String presetFb() {
		int success = 0;
		int skip = 0;
		List<Long> list = new ArrayList<Long>();
		try {
			List<Object[]> matchScoreData = zmJoinQtMatchService.findMatchInfoByDavcaiMatchId(id, LotteryId.JCZQ);
			id = filterValidMatchWithScore(matchScoreData, LotteryId.JCZQ);
			
			if(null != id && id.size() > 0) {
				list = presetAwardService.findAllowPrizesFB(id);
				log.info("根据竞彩足球赛事{}，找到{}个竞彩足球可预兑奖方案。", id,list.size());
			}
			if(null !=list  && !list.isEmpty()){
				log.info("准备对方案进行预兑奖.schemeIds={}", list);
				MatchResults matchResults = presetAwardService.computeFBMatchResults(id);
				for(Long schemeId : list){
					try {
						if(presetAwardService.presetPrizes(schemeId, matchResults)){
							success++;
						}else{
							skip++;
						}
					} catch (Exception e) {
						log.error("预兑奖方案出错！schemeid: {}, :{}",schemeId,e);
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			log.error("预兑奖方案出错！{}",e);
		}finally{
			// 方案总数，成功数，跳过，失败
			int[] result = new int[]{list.size(), success, skip, list.size() - success - skip};
			log.info("结束处理竞彩足球预兑奖！\\n总数：{}，成功：{}，跳过：" + result[2] + "，失败：" + result[3] ,result[0] ,result[1]);
			data.setData(result);
		}
	    return SUCCESS;
	}
	
	/**
	 * 预兑奖竞彩篮球方案
	 */
	public String presetBb() {
		int success = 0;
		int skip = 0;
		List<Long> list = new ArrayList<Long>();
		try {
			List<Object[]> matchScoreData = zmJoinQtMatchService.findMatchInfoByDavcaiMatchId(id, LotteryId.JCLQ);
			id = filterValidMatchWithScore(matchScoreData, LotteryId.JCLQ);
			
			if(null != id && id.size() > 0) {
				list = presetAwardService.findAllowPrizesBB(id);
				log.info("根据竞彩篮球赛事{}，找到{}个竞彩篮球可预兑奖方案。", id,list.size());
			}
			if(null !=list  && !list.isEmpty()) {
				log.info("准备对方案进行预兑奖.schemeIds={}", list);
				MatchResults matchResults = presetAwardService.computeBBMatchResults(id);
				for(Long schemeId : list){
					try {
						if(presetAwardService.presetPrizes(schemeId, matchResults)){
							success++;
						}else{
							skip++;
						}
					} catch (Exception e) {
						log.error("预兑奖方案出错！schemeid: {}, :{}",schemeId,e);
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			data.setSuccess(false);
			log.error("预兑奖方案出错！{}",e);
		}finally{
			// 方案总数，成功数，跳过，失败
			int[] result = new int[]{list.size(), success, skip, list.size() - success - skip};
			log.info("结束处理竞彩篮球预兑奖！\\n总数：{}，成功：{}，跳过：" + result[2] + "，失败：" + result[3] ,result[0] ,result[1]);
			data.setData(result);
		}
	    return SUCCESS;
	}
	
	public String cancel(){
		try {
			if(null != id && !id.isEmpty()){
				presetAwardService.cancelPresetPrizes(id);
				data.setData("撤销成功！");
			}else{
				data.setData("方案号不能为空！");
			}
		} catch (Exception e) {
			String info = "撤销预兑奖方案出错！" + e.getMessage();
			data.setSuccess(false);
			data.setData(info);
			log.error(info + "{}",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 如果所选赛事没有参考比分，则忽略该场比赛的兑奖
	 * @param matchScoreData
	 * @param lotteryId
	 * @return
	 */
	private List<Long> filterValidMatchWithScore(List<Object[]> matchScoreData, LotteryId lotteryId) {
		List<Long> ids = new ArrayList<Long>();
		
		if(null != matchScoreData && matchScoreData.size() > 0) {
			for(Object[] data : matchScoreData) {
				if(null != data) {
					if(LotteryId.JCZQ.equals(lotteryId)) {
	    				if(null != data && data.length == 3) {
	    					BigInteger matchId = (BigInteger) data[0];
	    					String score = (String) data[1];
	    					String halfScore = (String) data[2];
	    					if(null != matchId && matchId.longValue() > 0 &&
	    							StringUtils.isNotBlank(score) && StringUtils.isNotBlank(halfScore) &&
	    							score.split(":").length == 2 && halfScore.split(":").length == 2) {
	    						ids.add(matchId.longValue());
	    					} else {
	    						log.warn("非法预兑奖竞彩足球赛事ID={},参考score={}, 参考halfSocre={}, 不满足条件，被忽略.",
	    								new Object[]{matchId, score, halfScore});
	    					}
	    				}
	    			} else if(LotteryId.JCLQ.equals(lotteryId)) {
	    				if(null != data && data.length == 2) {
	    					BigInteger matchId = (BigInteger) data[0];
	    					String score = (String) data[1];
	    					if(null != matchId && matchId.longValue() > 0 &&
	    							StringUtils.isNotBlank(score) && score.split(":").length == 2) {
	    						ids.add(matchId.longValue());
	    					} else {
	    						log.warn("非法预兑奖竞彩篮球赛事ID={},参考score={}, 不满足条件，被忽略.",
	    								new Object[]{matchId, score});
	    					}
	    				}
	    			}
				}
			}
		}
		log.info("兑奖过滤后的赛事ID={}", ids);
		return ids;
	}
	
    public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public BetSchemePreset getBetSchemePreset() {
		return betSchemePreset;
	}

	public void setBetSchemePreset(BetSchemePreset betSchemePreset) {
		this.betSchemePreset = betSchemePreset;
	}

	public List<BetSchemePreset> getBetSchemePresets() {
		return betSchemePresets;
	}

	public void setBetSchemePresets(List<BetSchemePreset> betSchemePresets) {
		this.betSchemePresets = betSchemePresets;
	}

	public List<Long> getId() {
		return id;
	}

	public void setId(List<Long> id) {
		this.id = id;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
}
