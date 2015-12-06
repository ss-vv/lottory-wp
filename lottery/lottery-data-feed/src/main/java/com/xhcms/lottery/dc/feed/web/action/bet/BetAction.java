package com.xhcms.lottery.dc.feed.web.action.bet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;
import com.xhcms.lottery.commons.persist.service.BetMatchRecService;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.feed.web.action.BaseAction;
import com.xhcms.lottery.lang.LotteryId;

/**
 * @desc 投注页面
 * @author lei.li@unison.net.cn
 * @createTime 2013-12-04
 * @version 1.0
 */
public class BetAction extends BaseAction {

	private static final long serialVersionUID = -8700565177834871349L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private String lottery;

	private String playId;

	private String schemeId;

	@Autowired
	private BetMatchRecService matchRecService;

	@Autowired
	private BetSchemeRecService betSchemeRecService;

	private List<BetMatchRecPO> betMatchList;
    //从推荐过来
    private BetMatchRecPO bmrpo;
	private String passTypeIds;

	private int multiple;
	
	private String issueNumber;
	
	@Autowired
	IssueService issueService;
	
	public String execute() {
		// 如果当前传递的彩种不被支持默认显示“竞彩足球、不让球胜平负”数据,
		// 为了URL统一，玩法ID在前台页面使用的是全小写
		if (StringUtils.isBlank(lottery)
				|| !BetLotteryOfSupport.isSupported(lottery)) {
			lottery = LotteryId.JCZQ.name().toLowerCase();
			logger.info("指定的彩种:{}无效，请求访问使用默认彩种:{}", new Object[] { lottery,
					LotteryId.JCZQ.name() });
		}
		getSchemeBetMatchs(schemeId);
		getRecommend();
		
		return SUCCESS;
	}

	private void getSchemeBetMatchs(String schemeId) {
		if (StringUtils.isNotBlank(schemeId)) {
			try {
				Long id = Long.valueOf(schemeId);
				betMatchList = matchRecService.findBySchemeId(id);
				// 方案串关
				BetScheme scheme = betSchemeRecService.viewRecScheme(id);
				passTypeIds = scheme.getPassTypeIds();
				multiple = scheme.getMultiple();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void getRecommend(){
		if(bmrpo!=null &&bmrpo.getMatchId()!=null){
			betMatchList=new ArrayList<BetMatchRecPO>();
			betMatchList.add(bmrpo);
		}
		
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}

	public String getLottery() {
		return lottery;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public List<BetMatchRecPO> getBetMatchList() {
		return betMatchList;
	}

	public String getPassTypeIds() {
		return passTypeIds;
	}

	public int getMultiple() {
		return multiple;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public BetMatchRecPO getBmrpo() {
		return bmrpo;
	}

	public void setBmrpo(BetMatchRecPO bmrpo) {
		this.bmrpo = bmrpo;
	}
}