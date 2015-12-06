package com.xhcms.lottery.dc.feed.web.action.matchplay;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.Action;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.data.BJDCMatchsViewResult;
import com.xhcms.lottery.commons.persist.service.BJDCMatchService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 异步获取北单赛程信息（投注页面展现）
 * 
 * @date 下午9:00:53
 * @author lei.li@unison.net.cn
 * @version 2.0
 */
public class AjaxBJDCMatchListAction implements Action {

	final static Logger logger = LoggerFactory
			.getLogger(AjaxBJDCMatchListAction.class);

	private Data data;
	private String playId;
	private String issueNumber;
	
	@Autowired
	private BJDCMatchService bjdcMatchService;

	@Override
	public String execute() {
		logger.debug("find bjdc matchs:playId={}, issueNumber={}",
				playId, issueNumber);
		
		String lotteryId = LotteryId.BJDC.name();
		try {
			if(StringUtils.isBlank(playId) || null == PlayType.valueOfLcPlayId(playId)) {
				data = Data.failure(null);
				return SUCCESS;
			}
			
			BJDCMatchsViewResult result = bjdcMatchService.findMatchs(issueNumber, 
					lotteryId, playId);
			data = Data.success(result);
		} catch (Exception e) {
			data = Data.failure(null);
			logger.error("find matchs error:{}", e);
		}
		return SUCCESS;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}
	
	public Data getData() {
		return data;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
}