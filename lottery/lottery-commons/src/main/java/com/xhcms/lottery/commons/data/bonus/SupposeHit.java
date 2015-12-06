package com.xhcms.lottery.commons.data.bonus;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 假设命中某些场次时的奖金情况。
 * 
 * @author Yang Bo
 *
 */
public class SupposeHit {
	
	/** 假设命中的场次数 */
	private int hitCount;
	
	/** 过关类型，如: 2@1， 中奖注数 */
	private Map<String, Integer> passTypeWinNotes;
	
	private BigDecimal minBonus;
	private BigDecimal maxBonus;
	private List<WinTicketDetail> minDetails = new LinkedList<WinTicketDetail>();
	private List<WinTicketDetail> maxDetails = new LinkedList<WinTicketDetail>();
	
	public Map<String, Integer> getPassTypeWinNotes() {
		return passTypeWinNotes;
	}

	public void setPassTypeWinNotes(Map<String, Integer> passTypeWinNotes) {
		this.passTypeWinNotes = passTypeWinNotes;
	}

	public BigDecimal getMinBonus() {
		return minBonus;
	}

	public void setMinBonus(BigDecimal minBonus) {
		this.minBonus = minBonus;
	}

	public BigDecimal getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(BigDecimal maxBonus) {
		this.maxBonus = maxBonus;
	}

	public List<WinTicketDetail> getMinDetails() {
		return minDetails;
	}

	public void setMinDetails(List<WinTicketDetail> minDetails) {
		this.minDetails = minDetails;
	}

	public List<WinTicketDetail> getMaxDetails() {
		return maxDetails;
	}

	public void setMaxDetails(List<WinTicketDetail> maxDetails) {
		this.maxDetails = maxDetails;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
