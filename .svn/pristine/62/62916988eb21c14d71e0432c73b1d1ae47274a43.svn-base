package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchInfoService;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchInfoService;
import com.unison.lottery.weibo.data.vo.FutureMatchVO;
import com.unison.lottery.weibo.utils.LotteryIdMatchData;
import com.unison.lottery.weibo.web.action.BaseAction;

public class FutureMatchAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private long teamId;

	private List<FutureMatchVO> data = new ArrayList<FutureMatchVO>();

	@Autowired
	private MatchInfoService matchInfoService;
	
	@Autowired
	private BBMatchInfoService bbMatchInfoService;
	
	private int size = 4; // 默认显示的未来赛事记录条数
	
	private String lotteryType;

	public String execute() {
		try {
			if (LotteryIdMatchData.isZC(lotteryType)) {
				data = matchInfoService.getFutureMatchs(teamId, size);
			} else if (LotteryIdMatchData.isLC(lotteryType)) {
				data = bbMatchInfoService.getFutureMatchs(teamId, size);
			}
		} catch (Exception e) {
			log.error("加载彩种={}, 球队={}，未来赛事数据异常.", new Object[]{lotteryType, teamId});
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public List<FutureMatchVO> getData() {
		return data;
	}
	
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
}
