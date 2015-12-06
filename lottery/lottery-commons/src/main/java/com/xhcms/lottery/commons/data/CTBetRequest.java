package com.xhcms.lottery.commons.data;

import java.util.LinkedList;
import java.util.List;

import com.xhcms.lottery.commons.data.ctfb.CTBetContent;

/**
 * 传统足球下注请求对象。
 * 
 * @author Yang Bo
 */
public class CTBetRequest extends BetRequest {

	private ChooseType chooseType;
	
	/**
	 * 生成本次投注请求所代表的投注内容对象列表。<br/><br/>
	 * <b>注意：本方法不会设置 issueId 和 schemeId。</b>
	 * @return 投注内容列表
	 */
	public List<CTBetContent> makeBetContents(){
		LinkedList<CTBetContent> betContentsOfScheme = new LinkedList<CTBetContent>();
		for (String content : getBetContents() ) {
			CTBetContent betContent = new CTBetContent();
			String[] parts = content.split(";");
			betContent.setCode(parts[1]);
			betContent.setLotteryId(getLotteryId());
			betContent.setPlayId(getPlayType().getPlayId());
			betContent.setIssueNumber(parts[0]);
			betContent.setChooseType(chooseType.getIndex());
			betContentsOfScheme.add(betContent);
		}
		return betContentsOfScheme;
	}

	public ChooseType getChooseType() {
		return chooseType;
	}

	public void setChooseType(ChooseType chooseType) {
		this.chooseType = chooseType;
	}
}
