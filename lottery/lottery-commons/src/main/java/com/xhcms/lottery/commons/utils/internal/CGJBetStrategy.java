package com.xhcms.lottery.commons.utils.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.util.BetStrategy;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;

/**
 * 竞彩足球猜冠军玩法
 * 
 * lei.li
 */
public class CGJBetStrategy implements BetStrategy {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean match(String playId) {
		return Pattern.matches("^[a-z]+$", playId);
	}

	@Override
	public Bet resolve(BetScheme scheme) {
		Bet bet = new Bet();
		if (null != scheme.getMatchs() && scheme.getMatchs().size() > 0) {
			BetMatch betMatch = scheme.getMatchs().get(0);
			bet.setPlayId(scheme.getPlayId());
			List<Ticket> tickets = new ArrayList<Ticket>();
			try {
				tickets = splitTickets(scheme, betMatch.getCode());
				bet.setTickets(tickets);
			} catch (BetException e) {
				logger.error("对方案={}, 拆票异常={}", scheme, e);
				e.printStackTrace();
			}
			bet.setNote(computeTotalNotesFromTickets(tickets));
		} else {
			logger.error("无法获取方案的投注内容, 方案信息={}", scheme);
		}
		return bet;
	}

	private List<Ticket> splitTickets(BetScheme scheme, String code)
			throws BetException {
		List<Ticket> tickets = composeTickets(scheme, code);
		return tickets;
	}

	/**
	 * 如果倍数大于99倍或者钱数大于2万，拆为多张内容相同的票。
	 * 
	 * @param scheme
	 * @param code
	 * @return
	 * @throws BetException
	 */
	private List<Ticket> composeTickets(BetScheme scheme, String code)
			throws BetException {
		List<Ticket> ts = new LinkedList<Ticket>();
		Ticket ticket = composeOneTicket(scheme, code);
		int tMulti = ticket.getMultiple();
		int tMoney = ticket.getMoney();
		int singleMoney = tMoney / tMulti; // 单倍金额
		int max = Math.min(99, 20000 / singleMoney); // 该票最大有效倍数
		
		if (tMulti < max && tMoney < 20000) {
			ts.add(ticket);
		} else {
			//如果是单倍且方案金额超过2万则抛出异常
			if (tMulti == 1 && tMoney > 20000) {
				throw new BetException(
						"money of single multiple ticket can not be more than 20k.",
						AppCode.BET_TICKET_EXCEED_20K);
			}
			
			int totalMulti = scheme.getMultiple();
			int minMulti = Math.min(99, 20000 / singleMoney);
			for (int m = Math.min(minMulti, totalMulti); totalMulti > 0; totalMulti -= m) {
				m = Math.min(m, totalMulti);
				Ticket t = copyTicketWithMultiple(ticket, m);
				ts.add(t);
			}
		}
		return ts;
	}

	/**
	 * 拷贝一个新的票，并且设置新的倍数、总注数、钱数。
	 * 
	 * @param source
	 *            原始票
	 * @param multiple
	 *            指定的倍数
	 * @return 新票，有新的倍数。
	 */
	private Ticket copyTicketWithMultiple(Ticket source, int multiple) {
		int singleNotes = source.getNote() / source.getMultiple(); // 单倍注数
		Ticket t = new Ticket();
		BeanUtils.copyProperties(source, t);
		t.setMultiple(multiple);
		t.setNote(singleNotes * multiple);
		t.setMoney(t.getNote() * 2);
		return t;
	}

	/**
	 * 构造一张票。不考虑倍数限制。
	 * 
	 * @param scheme
	 *            方案
	 * @param code
	 *            投注内容
	 * @return 一个或多个票对象。
	 * @throws BetException
	 *             如果投注内容不合法
	 */
	@SuppressWarnings("unchecked")
	private Ticket composeOneTicket(BetScheme scheme, String code)
			throws BetException {
		int multiple = scheme.getMultiple();
		Ticket ticket = new Ticket();
		ticket.setPlayId(scheme.getPlayId());

		if (StringUtils.isNotBlank(code)) {
			String[] options = code.split(",");
			if (null != options && options.length > 0) {
				Set<String> optionSet = new HashSet<String>();
				for (int i = 0; i < options.length; i++) {
					if (StringUtils.isNotBlank(options[i])) {
						optionSet.add(options[i]);
					}
				}
				ticket.setNote(optionSet.size() * multiple);
			}
		}

		ticket.setCode(code);
		ticket.setActualCode(code);
		ticket.setMultiple(multiple);
		ticket.setCreatedTime(scheme.getCreatedTime());

		ticket.setMoney(ticket.getNote() * Constants.MONEY_PER_NOTE);
		ticket.setOdds("0.0");
		ticket.setMatches(Collections.EMPTY_LIST);
		ticket.setPassTypeId(StringUtils.EMPTY);
		ticket.setStatus(EntityStatus.TICKET_INIT);
		ticket.setPassTypeId(StringUtils.EMPTY);
		ticket.setNumber(StringUtils.EMPTY);

		return ticket;
	}
	
	private int computeTotalNotesFromTickets(List<Ticket> tickets) {
		int totalNotes = 0;
		for (Ticket ticket : tickets) {
			totalNotes += ticket.getNote();
		}
		return totalNotes;
	}
}