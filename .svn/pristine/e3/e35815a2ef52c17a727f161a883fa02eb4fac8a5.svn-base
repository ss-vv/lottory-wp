package com.xhcms.lottery.account.web.action.bet.ssq;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 双色球投注action。
 * 
 * @author Bob Yang
 * @version 1.0.0
 */
public class BetAction extends BaseAction {
	private static final long serialVersionUID = -1950448841763089050L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	// in
	private String lottery_id;		// 彩种id
	private String scheme;			// 投注方案内容，包括多条投注项，用';'分隔，格式参考 SSQBetStrategy
	private String multiple;		// 方案倍数
	//private String buy_type;		// 购买类型：agent - 代购
	//private String secret_type;	// 保密设置：open - 公开、end_open - 截止公开、secret - 永久保密
	private String issue;			// 期号
	private String choose_type;		// 投注方式: 作用不大了
	private String play_id;			// 玩法id: 作用不大了
	
	@Autowired
	private DigitalBetService digitalBetService;
	
	// out
	private BetScheme schemeView;
	
	/**
	 * 准备投注
	 */
    public String execute() throws Exception {
    	BetScheme betSession = (BetScheme) request.getSession().getAttribute(Constants.BET_IN_SESSION);
    	//如果不为空，说明用户是在未登录状态下提交的投注，登录之后重新返回投注确认页。
    	if(isGet() && betSession != null){
    		schemeView = betSession;
    		return SUCCESS;
    	}
    	if (!allParamsNotBlank()){
    		return INPUT;
    	}
    	DigitalBetRequest digitBetRequest = makeBetRequest();
		schemeView = digitalBetService.prepareBet(digitBetRequest);
		request.getSession().setAttribute(Constants.BET_IN_SESSION, schemeView);
        return SUCCESS;
    }

	private DigitalBetRequest makeBetRequest() {
		DigitalBetRequest orderBetRequest = new DigitalBetRequest();

		orderBetRequest.setMultiple(Integer.parseInt(this.multiple));
		orderBetRequest.setLotteryId(this.lottery_id);
		orderBetRequest.setPlayType(PlayType.valueOfLcPlayId(play_id));
		String[] bets = this.scheme.split(";");
		LinkedList<String> contents = new LinkedList<String>();
		List<PlayType> playTypes = new LinkedList<PlayType>();
		for (String bet : bets){
			if(StringUtils.isNotBlank(bet)){
				contents.add(bet);
				playTypes.add(digitalBetService.deduceSSQPlayType(bet));
			}
		}
		orderBetRequest.setBetContents(contents);
		orderBetRequest.setPlayTypeList(playTypes);
		orderBetRequest.setIssue(issue);
		// TODO：应该去掉，这个参数没有什么作用了。
		orderBetRequest.setChooseType(ChooseType.valueOfIndex(Integer.parseInt(choose_type)));

		return orderBetRequest;
	}

	private boolean allParamsNotBlank() {
        if (StringUtils.isBlank(this.lottery_id)) {
            addActionError(getText("bet.lottery_id.Null"));
            return false;
        }
        if (StringUtils.isBlank(this.play_id)) {
            addActionError(getText("bet.playId.Null"));
            return false;
        }
        if (StringUtils.isBlank(this.issue)) {
            addActionError(getText("bet.issueNumber.Null"));
            return false;
        }
        if (StringUtils.isBlank(this.choose_type)) {
            addActionError(getText("bet.choose_type.Null"));
            return false;
        }
        if (StringUtils.isBlank(this.scheme)) {
            addActionError(getText("bet.scheme.Null"));
            return false;
        }
        if (StringUtils.isBlank(this.multiple)) {
            addActionError(getText("bet.multiple.Null"));
            return false;
        }
        return true;
	}

	public String getLottery_id() {
		return lottery_id;
	}

	public void setLottery_id(String lottery_id) {
		this.lottery_id = lottery_id;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getChoose_type() {
		return choose_type;
	}

	public void setChoose_type(String choose_type) {
		this.choose_type = choose_type;
	}

	public String getPlay_id() {
		return play_id;
	}

	public void setPlay_id(String play_id) {
		this.play_id = play_id;
	}

	public BetScheme getSchemeView() {
		return schemeView;
	}

	public void setSchemeView(BetScheme schemeView) {
		this.schemeView = schemeView;
	}

}
