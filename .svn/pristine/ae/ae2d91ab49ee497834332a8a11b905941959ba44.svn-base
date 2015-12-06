/**
 * 
 */
package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.service.AwardService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * @author zhuyongli
 *
 */
public class AwardServiceImpl implements AwardService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private BetMatchDao betMatchDao;
	@Autowired
	private FBMatchDao fBMatchDao;
	private TicketService ticketService;

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> findByCreatTime(String playId, Date createdTime, boolean isawarded) {
		List<Integer> status = new ArrayList<Integer>();
		if(isawarded) {
			status.add(EntityStatus.TICKET_NOT_WIN);
			status.add(EntityStatus.TICKET_NOT_AWARD);
		} else {
			status.add(EntityStatus.TICKET_BUY_SUCCESS);
		}
		List<TicketPO> ticketPOs = ticketDao.find(playId, createdTime, status);
				
		List<Ticket> tickets = new ArrayList<Ticket>();
		if(null != ticketPOs && ticketPOs.size() > 0) {
			for(TicketPO po : ticketPOs) {
				Ticket ticket = new Ticket();
				BeanUtils.copyProperties(po, ticket);
				tickets.add(ticket);
			}
		}
		
		return tickets;
	}
	
	@Override
	@Transactional
	public void award(List<Long> ids) {
		Map<Long, Ticket> ts = new HashMap<Long, Ticket>();
		List<TicketPO> ticketPOs = ticketDao.find(ids);
		if(null != ticketPOs && ticketPOs.size() > 0) {
			for(TicketPO po : ticketPOs) {
				Long schemeId = po.getSchemeId();
				String[] code = po.getCode().split("-");
				if(code.length > 0) {
					int matchNum = 0;
					BigDecimal atbonus = new BigDecimal(2);
					for(int i=0; i<code.length; i++) {
						
						String matchCode = code[i].split(":")[0].substring(0, 4);
						String play = code[i].split(":")[1];
						BetMatchPO betMatchPO = betMatchDao.find(schemeId, matchCode);
						FBMatchPO fbMatchPO = getFBMatchPO(betMatchPO);
						boolean isWin = false;
						if(null != fbMatchPO) {
							isWin = isWin(play, betMatchPO, fbMatchPO);
						}
						if(isWin) {
							matchNum += 1;
							atbonus = atbonus.multiply(new BigDecimal(betMatchPO.getOdds())).setScale(2, BigDecimal.ROUND_HALF_UP );
						}
						
					}
					if(matchNum == code.length) {
						po.setStatus(EntityStatus.TICKET_NOT_AWARD);
						po.setAfterTaxBonus(atbonus);
						po.setMessage("彩票已中奖，但未派奖！");
						po.setPreTaxBonus(atbonus);
					} else {
						po.setStatus(EntityStatus.TICKET_NOT_WIN);
					}
				}
				
				Ticket ticket = new Ticket();
				BeanUtils.copyProperties(po, ticket);
				
				ts.put(po.getId(), ticket);
			}
			
			ticketService.prize(ts);
		}
	}
	
	/**
	 * 取得赛事信息
	 * @param schemeId
	 * @param matchCode
	 * @return
	 */
	private FBMatchPO getFBMatchPO(BetMatchPO betMatchPO) {
		FBMatchPO fbMatchPO = null;
		if(null != betMatchPO) {
			Long matchId = betMatchPO.getMatchId();
			fbMatchPO = fBMatchDao.get(matchId);
		}
		
		return fbMatchPO;
	}
	
	/**
	 * 判断是否中奖
	 * @return
	 */
	private boolean isWin(String play, BetMatchPO betMatchPO, FBMatchPO fbMatchPO) {
		boolean iswin = false;
		if(play.equals("spf")) {
			int betContent = Integer.parseInt(betMatchPO.getCode().substring(4));
			int homeScore = Integer.parseInt(fbMatchPO.getScore().split(":")[0]) + betMatchPO.getDefaultScore().intValue();
			int guestScore = Integer.parseInt(fbMatchPO.getScore().split(":")[1]);
			int result = -1;
			if(homeScore > guestScore) {
				result = 3;
			} else if(homeScore == guestScore) {
				result = 1;
			} else if(homeScore < guestScore) {
				result = 0;
			}
			if(result != -1 && betContent == result) {
				iswin = true; 
			}
			
			//要改善，还不太对
		} else if(play.equals("bf")) {
			int homeScore = Integer.parseInt(fbMatchPO.getScore().split(":")[0]);
			int guestScore = Integer.parseInt(fbMatchPO.getScore().split(":")[1]);
			int bhScore = Integer.parseInt(betMatchPO.getCode().substring(4));
			int bgScore = Integer.parseInt(betMatchPO.getCode().substring(5));
			if(homeScore > guestScore && bhScore > bgScore) {
				iswin = true; 
			} else if(homeScore == guestScore && bhScore == bgScore) {
				iswin = true; 
			} else if(homeScore < guestScore && bhScore < bgScore) {
				iswin = true; 
			}

		} else if(play.equals("jqs")) {
			int bScore = Integer.parseInt(betMatchPO.getCode().substring(4, 6));
			int homeScore = Integer.parseInt(fbMatchPO.getScore().split(":")[0]);
			int guestScore = Integer.parseInt(fbMatchPO.getScore().split(":")[1]);
			if(bScore == 7 && (homeScore+guestScore) >= 7) {
				iswin = true;
			} else if(bScore == (homeScore+guestScore)) {
				iswin = true;
			}
			
		} else if(play.equals("bqc")) {
			String bScore = betMatchPO.getCode().substring(4, 6);
			int hhScore = Integer.parseInt(fbMatchPO.getHalfScore().split(":")[0]);
			int hgScore = Integer.parseInt(fbMatchPO.getHalfScore().split(":")[1]);
			int nhScore = Integer.parseInt(fbMatchPO.getScore().split(":")[0]) - Integer.parseInt(fbMatchPO.getHalfScore().split(":")[0]);
			int ngScore = Integer.parseInt(fbMatchPO.getScore().split(":")[1]) - Integer.parseInt(fbMatchPO.getHalfScore().split(":")[1]);
			int hs = -1;
			if(hhScore > hgScore) {
				hs = 3;
			} else if(hhScore == hgScore) {
				hs = 1;
			} else if(hhScore < hgScore) {
				hs = 0;
			}
			int ns = -1;
			if(nhScore > ngScore) {
				ns = 3;
			} else if(nhScore == ngScore) {
				ns = 1;
			} else if(nhScore < ngScore) {
				ns = 0;
			}
			
			if((String.valueOf(hs)+String.valueOf(ns)).equals(bScore)) {
				iswin = true;
			}
		} else {
			logger.info("玩法不存在！");
		}
		
		return iswin;
	}
	
}
