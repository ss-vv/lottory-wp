package com.unison.lottery.weibo.mq;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

/**
 *
 * @author baoxing.peng
 * @since 2015年2月3日下午4:25:27
 */
public class CrawlerFbZqCompanyOddsHandle implements XHMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7388346570224562546L;
	private Long bsId;
	private String oddsId;
	private String companyId;
	private String qtBsId;
	
	public CrawlerFbZqCompanyOddsHandle(Long bsId,String oddsId,String companyId,String qtBsId){
		this.bsId = bsId;
		this.oddsId = oddsId;
		this.companyId = companyId;
		this.qtBsId = qtBsId;
	}
	public Long getBsId() {
		return bsId;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, 
				ToStringStyle.MULTI_LINE_STYLE);
	}
	public void setBsId(Long bsId) {
		this.bsId = bsId;
	}
	public String getOddsId() {
		return oddsId;
	}
	public void setOddsId(String oddsId) {
		this.oddsId = oddsId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getQtBsId() {
		return qtBsId;
	}
	public void setQtBsId(String qtBsId) {
		this.qtBsId = qtBsId;
	}
	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void setPriority(int arg0) {

	}
	
	
}
