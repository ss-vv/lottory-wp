package com.xhcms.lottery.pb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.pb.dao.PartnerBetDao;
import com.xhcms.lottery.pb.dao.PartnerTicketDao;
import com.xhcms.lottery.pb.exception.IssueException;
import com.xhcms.lottery.pb.exception.PartnerBetException;
import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.po.PartnerBetPO;
import com.xhcms.lottery.pb.po.PartnerTicketPO;
import com.xhcms.lottery.pb.service.PartnerBetService;
import com.xhcms.lottery.pb.xml.model.BetReq;
import com.xhcms.lottery.pb.xml.model.BetResp;

@Service
public class PartnerBetServiceImpl implements PartnerBetService {
	Logger logger = LoggerFactory.getLogger(PartnerBetServiceImpl.class);
	@Autowired
	IssueService issueService;
	@Autowired
	DigitalBetService digitalBetService;
	@Autowired
	UserDao userDao;
	@Autowired
	PartnerBetDao partnerBetDao;
	@Autowired
	PartnerTicketDao partnerTicketDao;
	@Autowired
	TicketDao ticketDao;

	@Override
	@Transactional
	public List<BetResp> betSSQ(BetReq betReq) throws IssueException,PartnerBetException{
		return betDigitalLottery(betReq);
	}
	@Override
	@Transactional
	public List<BetResp> betFC3D(BetReq betReq) throws IssueException,PartnerBetException{
		return betDigitalLottery(betReq);
	}
	
	@Override
	public List<BetResp> errorResult(BetReq betReq,int errorCode){
		List<BetResp> betResps = new ArrayList<BetResp>();
		BetResp betResp = new BetResp();
		betResp.setResult(errorCode);
		betResp.setTicketId(-1);
		betResp.setLotteryType(betReq.getLotteryType());
		betResp.setPlayType(betReq.getPlayType());
		betResps.add(betResp);
		return betResps;
	}
	
	@Override
	public List<Ticket> getTickets(Long schemeId) {
		List<TicketPO> ticketPOlist = ticketDao.findByScheme(schemeId, -1);
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket = null;
		for (TicketPO tpo : ticketPOlist) {
			ticket = new Ticket();
			BeanUtils.copyProperties(tpo, ticket);
			tickets.add(ticket);
		}
		return tickets;
	}

	/**
	 * 创建数字彩投注请求
	 * 
	 * @param betContent
	 * @return
	 * @throws IssueException 
	 */
	private DigitalBetRequest makeDigitalBetRequest(BetReq betReq) throws IssueException {
		String betContent = betReq.getBetContent();
		String partnerId = betReq.getPartnerId();
		int multiple = betReq.getMultiple();
		String playType = betReq.getPlayType();
		String lotteryType  = betReq.getLotteryType(); 
		
		DigitalBetRequest betRequst = new DigitalBetRequest();
		betRequst.setMultiple(multiple);
		betRequst.setLotteryId(lotteryType);
		LinkedList<String> contents = new LinkedList<String>();
		contents.add(betContent);
		betRequst.setBetContents(contents);
		
		List<PlayType> playTypes = new LinkedList<PlayType>();
		if(LotteryId.SSQ.name().equals(lotteryType)){
			playTypes.add(digitalBetService.deduceSSQPlayType(betContent));
		} else if (LotteryId.FC3D.name().equals(lotteryType)){
			playTypes.add(PlayType.valueOfLcPlayId(playType));
		}
		betRequst.setPlayTypeList(playTypes);
		if (playTypes.size() > 0) {
			betRequst.setPlayType(playTypes.get(0));
		}
		IssueInfoPO issue = issueService.getCurrentSalingIssueByLotteryId(
				lotteryType, new Date());
		if(null == issue){ //如果期号为空抛出异常
			throw new IssueException("期号异常",Constants.BET_ISSUE_ERROR);
		}
		betRequst.setIssue(issue.getIssueNumber());
		betRequst.setChooseType(ChooseType.valueOfIndex(EntityType.HAND_PICK));
		betRequst.setBetType(0);// 代购
		betRequst.setChannel(Constants.CHANNEL);
		betRequst.setPartner(partnerId);
		return betRequst;
	}
	/**
	 * 福彩3D和双色球通用投注方法
	 * @param betReq
	 * @return
	 * @throws IssueException
	 * @throws PartnerBetException
	 */
	private List<BetResp> betDigitalLottery(BetReq betReq) throws IssueException,PartnerBetException{
		PartnerBetPO partnerBetPO = partnerBetDao.findPartnerBetPOByUuid(betReq.getUuid());
		if (null == partnerBetPO) {
			DigitalBetRequest betRequst = makeDigitalBetRequest(betReq);
			UserPO userPO = userDao.getUserByUsername(betReq.getPartnerId());
			if (userPO == null) {
				return errorResult(betReq, Constants.BET_OTHER_ERROR);
			} else {
				betRequst.setUserId(userPO.getId());
				BetScheme betScheme = digitalBetService.prepareBet(betRequst);
				if(betReq.getBetMoney() != betScheme.getBuyAmount()){ //商户计算的投注金额与来彩计算的投注金额不一致,抛出异常，回滚
					throw new PartnerBetException("投注金额不正确",Constants.BET_MONEY_ERROR);
				}
				betScheme = digitalBetService.betConfirm(betScheme);
				if (betScheme != null) {
					partnerBetPO = new PartnerBetPO();
					partnerBetPO.setUserId(userPO.getId());
					partnerBetPO.setCreateTime(new Date());
					partnerBetPO.setPartnerUserId(betReq.getPartnerUserId());
					partnerBetPO.setUuid(betReq.getUuid());
					partnerBetPO.setSchemeId(betScheme.getId());
					partnerBetDao.save(partnerBetPO); //保存商户投注信息
					
					//组织响应消息
					List<BetResp> betResps = new ArrayList<BetResp>();
					List<Ticket> tickets = this.getTickets(betScheme.getId());
					for (Ticket ticket : tickets) {
						PartnerTicketPO partnerTicketPO = new PartnerTicketPO();
						partnerTicketPO.setCreateTime(new Date());
						partnerTicketPO.setSchemeId(ticket.getSchemeId());
						partnerTicketPO.setStatus(Constants.WAIT_DRAW_TICKET_INFORM);
						partnerTicketPO.setTicketId(ticket.getId());
						partnerTicketDao.save(partnerTicketPO);
						
						BetResp betResp = new BetResp();
						BeanUtils.copyProperties(betReq, betResp);
						betResp.setTicketId(ticket.getId());
						betResp.setResult(Constants.BET_SUCCESS);
						betResp.setIssueNumber(ticket.getIssue());
						betResp.setMoney(ticket.getMoney());
						betResps.add(betResp);
					}
					return betResps;
				} else {
					return errorResult(betReq, Constants.BET_OTHER_ERROR);
				}
			}
		} else {
			return errorResult(betReq, Constants.BET_UUID_ERROR);
		}
	}
}
