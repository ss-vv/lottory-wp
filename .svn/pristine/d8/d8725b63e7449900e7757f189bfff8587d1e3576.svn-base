package com.unison.lottery.wap.action;

import org.apache.commons.lang.StringUtils;
import com.xhcms.ucenter.action.BaseAction;

/**
 * @author lei.li@unison.net.cn
 */
public class BallotIndexAction extends BaseAction{
	
	private static final long serialVersionUID = 7861315462015474321L;
	
	private String lottery;
	
 	public String switchLotteryBallot() {
 		if(StringUtils.isBlank(lottery)) {
 			return "wf";
 		}
 		return lottery;
 	}
 	
	public void setLottery(String lottery) {
		this.lottery = lottery;
	}
}