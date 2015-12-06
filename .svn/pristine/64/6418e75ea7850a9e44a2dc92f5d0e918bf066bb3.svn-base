package com.unison.lottery.weibo.mq;

import java.util.List;

import com.xhcms.commons.mq.XHMessage;

public class PushOddsMessageHandle implements XHMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2898672150566100637L;
	private List<OddsData> oddsDatas;
	private int oldCount;
	
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName();
	}

	@Override
	public void setPriority(int arg0) {
		// TODO Auto-generated method stub

	}

	public List<OddsData> getOddsDatas() {
		return oddsDatas;
	}

	public void setOddsDatas(List<OddsData> oddsDatas) {
		this.oddsDatas = oddsDatas;
	}

	public int getOldCount() {
		return oldCount;
	}

	public void setOldCount(int oldCount) {
		this.oldCount = oldCount;
	}
	
	

}