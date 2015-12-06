package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchInfoService;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchInfoService;
import com.unison.lottery.weibo.data.vo.FightHistoryVO;
import com.unison.lottery.weibo.utils.LotteryIdMatchData;
import com.unison.lottery.weibo.web.action.BaseAction;

public class FightHistoryAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private long homeTeamId;

	private long guestTeamId;

	private List<FightHistoryVO> data = new ArrayList<FightHistoryVO>();

	@Autowired
	private MatchInfoService matchInfoService;

	private int size = 5; // 默认显示的交战历史记录条数

	private String lotteryType;

	@Autowired
	private BBMatchInfoService bbMatchInfoService;

	public String execute() {
		try {
			if (LotteryIdMatchData.isZC(lotteryType)) {
				data = matchInfoService.getFightHistoryMatchs(homeTeamId,
						guestTeamId, size);
			} else if (LotteryIdMatchData.isLC(lotteryType)) {
				data = bbMatchInfoService.getFightHistoryMatchs(homeTeamId,
						guestTeamId, null, new Date(), size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public List<FightHistoryVO> getData() {
		return data;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public long getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public long getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}
}
