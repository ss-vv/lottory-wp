package com.unison.lottery.itf;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.velocity.VelocityContext;

import com.unison.lottery.itf.OrderNotifyProcessResult.ReturnTicket;

/**
 * 交易结果通知的处理结果。
 * 
 * @author Yang Bo
 */
public class OrderNotifyProcessResult extends ProcessResult<ReturnTicket> {

	public void addResult(long ticketId, long platformId){
		ReturnTicket returnTicket = new ReturnTicket(ticketId, platformId);
		getReturnResults().add(returnTicket);
	}

	@Override
	protected VelocityContext prepareContext() {
		VelocityContext ctx = new VelocityContext();
		ctx.put("returnTickets", getReturnResults());
		return ctx;
	}
	
	/**
	 * 响应结果对象
	 * @author Yang Bo
	 */
	public class ReturnTicket {
		private long ticketId;
		private long platformId;
		
		public ReturnTicket(long ticketId, long platformId) {
			super();
			this.ticketId = ticketId;
			this.platformId = platformId;
		}

		public long getPlatformId() {
			return platformId;
		}

		public void setPlatformId(long platformId) {
			this.platformId = platformId;
		}

		public long getTicketId() {
			return ticketId;
		}

		public void setTicketId(long ticketId) {
			this.ticketId = ticketId;
		}
		
		public String toString(){
			return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
		}
	}
}
