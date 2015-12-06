package com.xhcms.lottery.account.web.action.bet.cgj;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.account.service.BetSchemeService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CGJBetRequest;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.persist.service.CGJTeamService;
import com.xhcms.lottery.commons.persist.service.PlayService;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

public class BetAction extends BaseAction {
	private static final long serialVersionUID = -1950448841763089050L;
	private Logger log = LoggerFactory.getLogger(getClass());

	private String lotteryId; // 彩种id
	private String multiple; // 方案倍数
	private String playId; // 玩法id
	private String code; // 投注内容：使用逗号分隔
	private String odds; // 投注内容对应赔率，使用逗号分隔

	private BetScheme schemeView;
	
	@Autowired
    private PlayService playService;
	
	@Autowired
    private BetSchemeService betSchemeService;
	
	@Autowired
	private CGJTeamService cgjTeamService;

	/**
	 * 准备投注
	 */
	public String execute() {
		BetScheme betSession = (BetScheme) request.getSession().getAttribute(
				Constants.BET_IN_SESSION);
		if (isGet() && betSession != null) {
			schemeView = betSession;
			
			return SUCCESS;
		}
		if (!allParamsNotBlank()) {
			return INPUT;
		}
		log.info("准备投注猜冠军玩法:lotteryUserId={}, username={}," +
				"lotteryId={}, multiple={}, playId={}, code={}, odds={}",
				new Object[]{getUserId(), getUsername(), 
				lotteryId, multiple, playId, code, odds});

		CGJBetRequest cgjBetRequest = new CGJBetRequest();
		List<BetMatch> matchs = cgjBetRequest.makeBetMatch(playId, code, odds);
		if(null == matchs || matchs.size() == 0) {
			throw new XHRuntimeException(getErrorText(AppCode.INVALID_BET_CODE));
		}
		schemeView = new BetScheme();
		schemeView.setMatchs(matchs);
		schemeView.setMultiple(Integer.parseInt(multiple));
		schemeView.setLotteryId(lotteryId);
		schemeView.setPlayId(playId);
		schemeView.setPassTypeIds(PlayType.UNKNOWN.getShortPlayStr());
		schemeView.setCreatedTime(new Date());
		
        Play play = playService.get(playId);
        if (play != null) {
        	schemeView.setLotteryId(play.getLotteryId());
        }
        int returnCode = betSchemeService.checkMatchAndFillScheme(schemeView,playId);
        if (returnCode != 0) {
            throw new XHRuntimeException(getErrorText(returnCode));
        }
        
        // 截止时间
        if (new Date().after(schemeView.getMatchs().get(0).getEntrustDeadline())) {
            addActionError(getText("bet.after.Offtime"));
            return INPUT;
        }
        List<CGJTeam> cgjTeamList = cgjTeamService.listTeamsByCode(Arrays.asList(code.split(",")), playId);
        schemeView.setCgjTeams(cgjTeamList);
        
		request.getSession().setAttribute(Constants.BET_IN_SESSION, schemeView);
		return SUCCESS;
	}

	private boolean allParamsNotBlank() {
		if (StringUtils.isBlank(this.lotteryId)) {
			addActionError(getText("bet.lottery_id.Null"));
			return false;
		}
		if (StringUtils.isBlank(this.playId)) {
			addActionError(getText("bet.playId.Null"));
			return false;
		}
		if (StringUtils.isBlank(this.multiple)) {
			addActionError(getText("bet.multiple.Null"));
			return false;
		}
		if (StringUtils.isBlank(this.code)) {
			return false;
		}
		if (StringUtils.isBlank(this.odds)) {
			return false;
		}
		return true;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public BetScheme getSchemeView() {
		return schemeView;
	}

	public void setSchemeView(BetScheme schemeView) {
		this.schemeView = schemeView;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

}
