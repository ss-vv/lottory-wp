package com.xhcms.lottery.pb.service;

import java.util.List;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.pb.exception.IssueException;
import com.xhcms.lottery.pb.exception.PartnerBetException;
import com.xhcms.lottery.pb.xml.model.BetReq;
import com.xhcms.lottery.pb.xml.model.BetResp;

public interface PartnerBetService {
	/**
	 * 商户投注双色球
	 * @param betReq
	 * @throws IssueException 
	 */
	List<BetResp> betSSQ(BetReq betReq) throws IssueException,PartnerBetException;
	
	/**
	 * 商户投注福彩3D
	 * @param bet
	 * @throws IssueException
	 */
	List<BetResp> betFC3D(BetReq betReq) throws IssueException,PartnerBetException;
	
	public List<BetResp> errorResult(BetReq betReq,int errorCode);
	
	/**
	 * 根据方案id获取彩票信息
	 * 
	 * @param schemeId
	 * @return
	 */
	public List<Ticket> getTickets(Long schemeId) ;
}
