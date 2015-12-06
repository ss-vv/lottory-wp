package com.xhcms.lottery.pb.xml.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Body {
	private IssueInfoReq issueInfoReq;
	private IssueInfoResp issueInfoResp;
	
	private List<BetReq> betReq;
	private List<BetResp> betResp;
	private List<TicketReq> ticketReq;
	private List<TicketResp> ticketResp;
	private List<WithdrawReq> withdrawReq;
	private List<WithdrawResp> withdrawResp;
	private List<WithdrawResultReq> withdrawResultReq;
	private List<WithdrawResultResp> withdrawResultResp;
	
	public IssueInfoReq getIssueInfoReq() {
		return issueInfoReq;
	}
	@XmlElement(name="issueInfoReq")
	public void setIssueInfoReq(IssueInfoReq issueInfoReq) {
		this.issueInfoReq = issueInfoReq;
	}
	
	public IssueInfoResp getIssueInfoResp() {
		return issueInfoResp;
	}
	@XmlElement(name="issueInfoResp")
	public void setIssueInfoResp(IssueInfoResp issueInfoResp) {
		this.issueInfoResp = issueInfoResp;
	}
	public List<BetResp> getBetResp() {
		return betResp;
	}
	@XmlElement(name="betResp")
	public void setBetResp(List<BetResp> betResp) {
		this.betResp = betResp;
	}
	public List<BetReq> getBetReq() {
		return betReq;
	}
	@XmlElement(name="betReq")
	public void setBetReq(List<BetReq> betReq) {
		this.betReq = betReq;
	}
	public List<TicketReq> getTicketReq() {
		return ticketReq;
	}
	@XmlElement(name="ticketReq")
	public void setTicketReq(List<TicketReq> ticketReq) {
		this.ticketReq = ticketReq;
	}
	public List<TicketResp> getTicketResp() {
		return ticketResp;
	}
	@XmlElement(name="ticketResp")
	public void setTicketResp(List<TicketResp> ticketResp) {
		this.ticketResp = ticketResp;
	}
	public List<WithdrawReq> getWithdrawReq() {
		return withdrawReq;
	}
	@XmlElement(name="withdrawReq")
	public void setWithdrawReq(List<WithdrawReq> withdrawReq) {
		this.withdrawReq = withdrawReq;
	}
	public List<WithdrawResp> getWithdrawResp() {
		return withdrawResp;
	}
	@XmlElement(name="withdrawResp")
	public void setWithdrawResp(List<WithdrawResp> withdrawResp) {
		this.withdrawResp = withdrawResp;
	}
	public List<WithdrawResultReq> getWithdrawResultReq() {
		return withdrawResultReq;
	}
	@XmlElement(name="withdrawResultReq")
	public void setWithdrawResultReq(List<WithdrawResultReq> withdrawResultReq) {
		this.withdrawResultReq = withdrawResultReq;
	}
	public List<WithdrawResultResp> getWithdrawResultResp() {
		return withdrawResultResp;
	}
	@XmlElement(name="withdrawResultResp")
	public void setWithdrawResultResp(List<WithdrawResultResp> withdrawResultResp) {
		this.withdrawResultResp = withdrawResultResp;
	}
}
