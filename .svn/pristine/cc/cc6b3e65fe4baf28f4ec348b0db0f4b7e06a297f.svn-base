package com.xhcms.lottery.commons.utils.internal;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.fc3d.FC3DBetOption;
import com.xhcms.lottery.commons.exception.JXRuntimeException;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.util.BetStrategy;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PlayType;

/**
 * 福彩3D拆票。
 * <pre>
 * 福彩3D前后台参数定义
 * 单式投注:  FC3D_ZXDS	格式：0,3,8;1,7,9
 * 复式投注:  FC3D_ZXFS  	格式：01,2,3;5,02,3
 * 直选和值:  FC3D_ZXHZ  	格式：5;8
 * 组选单式:  FC3D_ZX_DS  	格式：3,3,8;3,8,9(组三单式和组六单式)
 * 组三复式:  FC3D_Z3FS  	格式：1,2,9(c[2/3]*2=6注)
 * 组三和值:  FC3D_Z3HZ  	格式：5;8
 * 组六复式:  FC3D_Z6FS  	格式：0,1,2,3;2,7,8,9
 * 组六和值:  FC3D_Z6HZ  	格式：3;7
 * 单选包号:  FC3D_DXBH  	格式：1,2,3(3*3*3注)
 * </pre>
 */
public class FC3DBetStrategy implements BetStrategy {
	private static final int MAX_NOTES_PER_TICKET_FOR_SINGLE = 5;
	private static final int MAX_MULTIPLE = 50;  // 中民的接口最多支持50倍
	private static final int MAX_MONEY_PER_TICKET = 20000;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean match(String playId) {
		PlayType playType = PlayType.valueOfLcPlayId(playId);
		return playType.is3D();
	}
	
	@Override
	public Bet resolve(BetScheme scheme) {
		Bet bet = new Bet();
		List<Ticket> tickets;
		try {
			tickets = splitTickets(scheme);
		} catch (BetException e) {
			throw new JXRuntimeException(e.getMessage(), e.getErrorCode());
		}
		bet.addTickets(tickets);
		// 按投注倍数计算方案总数据
		bet.setNote(computeTotalNotesFromTickets(tickets));
		bet.setMaxBonus(computeMaxBonus(tickets));
		return bet;
	}

	/**
	 * 拆分出最少的票。同时计算每张票的注数。 拆票规则：
	 * <ul>
	 * <li>1、单式，如果大于5注，要拆为多张
	 * <li>2、如果倍数大于50倍，要拆为多张同内容的票。
	 * </ul>
	 * 
	 * @param scheme 投注方案对象
	 * @return 拆分得到的票
	 * @throws BetException
	 */
	private List<Ticket> splitTickets(BetScheme scheme) throws BetException {
		Map<PlayType, List<DigitalBetContent>> bcGroup = groupBetContents(scheme);
		List<Ticket> tickets = new LinkedList<Ticket>();
		for (Map.Entry<PlayType, List<DigitalBetContent>> entry : bcGroup.entrySet()) {
			List<DigitalBetContent> bcs = entry.getValue();
			PlayType playType = entry.getKey();
			switch (playType) {
				case FC3D_ZXDS: // 直选单式
					tickets.addAll(normalSplit(scheme, bcs));
					break;
				case FC3D_ZX_DS://组选单式
				case FC3D_ZXFS: // 直选复式
				case FC3D_ZXHZ: // 直选和值
				case FC3D_Z3HZ:	//组三和值
				case FC3D_Z6HZ:	//组六和值
				case FC3D_Z3FS:	// 组三复式
				case FC3D_Z6FS:	// 组六复式
				case FC3D_DXBH:	//单选包号
					tickets.addAll(splitToFS(scheme, bcs));
					break;
				default:
					logger.error("Unknown playType '{}' for cqssc split tickets.", playType);
					break;
			}
		}
		return tickets;
	}

	/**
	 * 一般拆分，只使用“最大五注”规则。
	 */
	private List<Ticket> normalSplit(BetScheme scheme,
			List<DigitalBetContent> bcs) throws BetException {
		List<Ticket> tickets = new LinkedList<Ticket>();
		for (DigitalBetContent bc : bcs){
			for (String splitted : splitToMultiNotes(bc.getCode())){
				tickets.addAll(composeTickets(scheme, bc, splitted));
			}
		}
		return tickets;
	}

	private List<Ticket> splitToFS(BetScheme scheme,
			List<DigitalBetContent> bcs) throws BetException {
		List<Ticket> tickets = new LinkedList<Ticket>();
		for (DigitalBetContent bc : bcs){
			for (String splitted : splitCodeWithFS(bc.getCode())){
				tickets.addAll(composeTickets(scheme, bc, splitted));
			}
		}
		return tickets;
	}
	

	/**
	 * 按照玩法类型对高频投注内容分类。
	 * 
	 * @return key: 玩法类型, value: 投注内容。
	 */
	private Map<PlayType, List<DigitalBetContent>> groupBetContents(BetScheme scheme) {
		Map<PlayType, List<DigitalBetContent>> group = new HashMap<PlayType, List<DigitalBetContent>>();
		for (DigitalBetContent bc : scheme.getDigitalBetContents()) {
			PlayType pt = PlayType.valueOfLcPlayId(bc.getPlayId());
			if (group.containsKey(pt)) {
				group.get(pt).add(bc);
			} else {
				LinkedList<DigitalBetContent> bcList = new LinkedList<DigitalBetContent>();
				bcList.add(bc);
				group.put(pt, bcList);
			}
		}
		return group;
	}

	private int computeTotalNotesFromTickets(List<Ticket> tickets) {
		int totalNotes = 0;
		for (Ticket ticket : tickets) {
			totalNotes += ticket.getNote();
		}
		return totalNotes;
	}

	private int computeMaxBonus(List<Ticket> tickets) {
		int maxBonus = 0;
		for (Ticket t : tickets) {
			maxBonus += t.getExpectBonus().intValue();
		}
		return maxBonus;
	}

	/**
	 * 拆分出多注. 对于"xxx;xxx;xxx"的多注形式，按照5注一票的规则拆分。
	 */
	public List<String> splitToMultiNotes(String code) {
		String[] notes = code.split(";");
		LinkedList<String> splitted = new LinkedList<String>();
		StringBuilder oneTicketOptions = new StringBuilder();
		for (int i = 0; i < notes.length; i++) {
			if (i > 0 && i % MAX_NOTES_PER_TICKET_FOR_SINGLE == 0) {
				splitted.add(oneTicketOptions.toString().trim());
				oneTicketOptions.delete(0, oneTicketOptions.length());
			}
			if (oneTicketOptions.length() > 0) {
				oneTicketOptions.append(";");
			}
			oneTicketOptions.append(notes[i].trim());
		}
		if (oneTicketOptions.length() > 0) {
			splitted.add(oneTicketOptions.toString().trim());
		}
		return splitted;
	}
	
//	public List<String> splitToMultiNotesWithOneTicketOneCode(String code) {
//		String[] notes = code.split(";");
//		LinkedList<String> splitted = new LinkedList<String>();
//		for (int i = 0; i < notes.length; i++) {
//			splitted.add(notes[i]);
//		}
//		return splitted;
//	}

	public List<String> splitCodeWithFS(String code) {
		String[] notes = code.split(";");
		LinkedList<String> splitted = new LinkedList<String>();
		for (int i = 0; i < notes.length; i++) {
			splitted.add(notes[i]);
		}
		return splitted;
	}
	
	/**
	 * 如果倍数大于50倍或者钱数大于2万，拆为多张内容相同的票。
	 * @param scheme
	 * @param bc
	 * @param code
	 * @return
	 * @throws BetException
	 */
	private List<Ticket> composeTickets(BetScheme scheme, DigitalBetContent bc, String code) throws BetException {
		List<Ticket> ts = new LinkedList<Ticket>();
		Ticket ticket = composeOneTicket(scheme, bc, code);
		int tMulti = ticket.getMultiple();
		int tMoney = ticket.getMoney();
		int singleMoney = tMoney / tMulti; 			 // 单倍金额
		
		if (tMulti < MAX_MULTIPLE && tMoney < MAX_MONEY_PER_TICKET){
			ts.add(ticket);
		}else{
			if (tMulti == 1 && tMoney > MAX_MONEY_PER_TICKET){
				throw new BetException("money of single multiple ticket can not be more than 20k.", AppCode.BET_TICKET_EXCEED_20K);
			}
			int totalMulti = scheme.getMultiple();
			int minMulti = Math.min(MAX_MULTIPLE, MAX_MONEY_PER_TICKET / singleMoney);
			for (int m = Math.min(minMulti, totalMulti); totalMulti > 0; totalMulti -= m){
				m = Math.min(m, totalMulti);
				Ticket t = copyTicketWithMultiple(ticket, m);
				ts.add(t);
			}
		}
		return ts;
	}
	
	/**
	 * 拷贝一个新的票，并且设置新的倍数、总注数、钱数。
	 * @param source 原始票
	 * @param multiple 指定的倍数
	 * @return 新票，有新的倍数。
	 */
	private Ticket copyTicketWithMultiple(Ticket source, int multiple){
		int tMulti = source.getMultiple();
		int singleNotes = source.getNote() / tMulti; // 单倍注数
		Ticket t = new Ticket();
		BeanUtils.copyProperties(source, t);
		t.setMultiple(multiple);
		t.setNote(singleNotes * multiple);
		t.setMoney(t.getNote() * 2);
		return t;
	}
	
	/**
	 * 构造一张票。不考虑倍数限制。
	 * @param scheme 方案
	 * @param bc 投注内容，但只是参考玩法类型
	 * @param code 投注内容
	 * @return 一个或多个票对象。
	 * @throws BetException 如果投注内容不合法
	 */
	@SuppressWarnings("unchecked")
	private Ticket composeOneTicket(BetScheme scheme, DigitalBetContent bc, String code)
			throws BetException {
		DigitalBetRequest request = scheme.getDigitalBetRequest();
		int multiple = scheme.getMultiple();
		PlayType playType = PlayType.valueOfLcPlayId(bc.getPlayId());
		
		BetCodeValidator codeValidator = new FC3DBetCodeValidator();
		codeValidator.valid(code, playType);

		Ticket ticket = new Ticket();
		ticket.setIssue(request.getIssue());
		ticket.setPlayId(bc.getPlayId());
		BetOption betOptionForNotes = new FC3DBetOption(playType, code);
		int notesOfOption = betOptionForNotes.getNotes();
		ticket.setNote(notesOfOption * multiple);
		ticket.setCode(code);
		ticket.setCreatedTime(scheme.getCreatedTime());
		ticket.setActualCode(code);
		ticket.setMultiple(multiple);
		int expectBonus = FC3DBonusComputer.computeExpectBonus(playType,
				ticket.getNote());
		ticket.setExpectBonus(BigDecimal.valueOf(expectBonus));
		ticket.setMoney(ticket.getNote() * Constants.MONEY_PER_NOTE);
		ticket.setOdds("0.0");
		ticket.setMatches(Collections.EMPTY_LIST);
		ticket.setPassTypeId(StringUtils.EMPTY);
		ticket.setStatus(EntityStatus.TICKET_INIT);
		ticket.setPassTypeId(StringUtils.EMPTY);
		ticket.setNumber(StringUtils.EMPTY);

		return ticket;
	}
}