package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchInfoService;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchInfoService;
import com.unison.lottery.weibo.data.vo.RecentMatchVO;
import com.unison.lottery.weibo.utils.LotteryIdMatchData;
import com.unison.lottery.weibo.web.action.BaseAction;

public class RecentMatchAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/** 获取几条近期赛事 ,默认最近10场 */
	private int size = 10;

	private List<RecentMatchVO> data = new ArrayList<RecentMatchVO>();

	private long teamId;

	@Autowired
	private MatchInfoService matchInfoService;
	
	@Autowired
	private BBMatchInfoService bbMatchInfoService;

	private String lotteryType;

	public String execute() {
		try {
			if (LotteryIdMatchData.isZC(lotteryType)) {
				data = matchInfoService.getRecentMatchs(teamId, size);
			} else if (LotteryIdMatchData.isLC(lotteryType)) {
				data = bbMatchInfoService.getRecentMatchs(teamId, size);
			}
		} catch (Exception e) {
			log.error("加载彩种={}, 球队={}，最近赛事数据异常.", new Object[]{lotteryType, teamId});
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<RecentMatchVO> getData() {
		return data;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
}
