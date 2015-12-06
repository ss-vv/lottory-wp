package com.xhcms.lottery.commons.utils.internal;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.laicai.util.Combination;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTBetRequest;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.data.ctfb.CTBetOption;
import com.xhcms.lottery.commons.data.ctfb.ExpandingR9Visitor;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.util.BetStrategy;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 传统足彩拆票策略器。
 * 拆票并按接口需要的实际出票投注格式组织票的内容。
 * <p>
 *<br/>  前台、后台格式：<期号>;<选项>;<胆码下标（从0开始）>|<期号>;<选项>;<胆码>
 *<br/> 	胆码、选项内部用逗号分隔；
 *<br/>  任九没选的场次用减号代替。
 *<br/>  示例：
 *<br/> 	14场胜负：
 *<br/> 		12139;310,3,3,3,3,3,3,3,3,3,3|12102;310,3,3,3,3,3,3,3,3,3,3,1,1,0
 *<br/> 	任选九： 
 *<br/> 		12139;-,-,3,3,3,3,3,3,1,0,3,-,-;2,3,5|12102;-,-,3,3,3,3,3,3,1,0,3,-,-;1,2
 * </p>
 * @author Yang Bo
 */
public class CTBetStrategy implements BetStrategy {

	@Override
	public boolean match(String playId) {
		PlayType playType = PlayType.valueOfLcPlayId(playId);
		if (null != playType && 
				playType.getLotteryId() == LotteryId.CTZC) {
			return true;
		}
		return false;
	}

	@Override
	public Bet resolve(BetScheme scheme) {
		Bet bet = new Bet();
		List<Ticket> tickets;
		try {
			tickets = splitTickets(scheme);
		} catch (BetException e) {
			throw new IllegalArgumentException(e);
		}
		bet.addTickets(tickets);
		// 按投注倍数计算方案总数据
        bet.setNote( computeTotalNotesFromTickets(tickets) );
        bet.setMaxBonus(0);
		return bet;
	}

	private List<Ticket> splitTickets(BetScheme scheme) throws BetException {
		ChooseType chooseType = scheme.getCtBetRequest().getChooseType();
		PlayType playType = scheme.getCtBetRequest().getPlayType();
		// 单式拆票
		if (chooseType == ChooseType.MACHINE){
			int maxNotesPerTicket = 5;
			if (playType == PlayType.CTZC_BQ){
				maxNotesPerTicket = 4;
			}
			return splitSingleSelectedMatches(scheme, maxNotesPerTicket);
		}
		// 复式拆票
		if (playType == PlayType.CTZC_R9){
			return splitR9MultiSelectedMatches(scheme);
		}else{
			return splitMultiSelectedMatches(scheme);
		}
	}

	/**
	 * 拆分复式任九投注的比赛。拆分为9场选择，并考虑胆，最多只能有8个胆。
	 * 
	 * <pre>
	 * 选择的比赛大于9场？（也可以不判断，因为不大于9场不可能大于2万）
	 *      否：报错，不应该出现。
	 *      是：取第一场比赛A作为操作候选。
	 *           1、展开去掉A后的所有可能票，并计算注数。
	 *           2、展开以A做胆的每个选项的可能票，并计算注数。
	 *           3、上面两步之和为拆分比赛A的结果。
	 *           4、递归拆分1、2中的候选票。
	 * 
	 * </pre>
	 * @throws BetException 拆分异常
	 */
	private List<Ticket> splitR9MultiSelectedMatches(BetScheme scheme) throws BetException {
		List<CTBetContent> bcs = scheme.getCtBetContents();
		List<Ticket> splittedTickets = new LinkedList<Ticket>();
		PlayType playType = scheme.getCtBetRequest().getPlayType();
		ChooseType chooseType = scheme.getCtBetRequest().getChooseType();
		for (CTBetContent bc : bcs) {
			String[] betGroup = bc.getCode().split(",");
			String dan = bc.getSeed();
			CTBetOption betOpt = new CTBetOption(playType, chooseType, bc.getCode(), dan);
			int[][] selCountAndIndexArray = betOpt.createSelCountAndIndexArray();
			int[] selCountArray = selCountAndIndexArray[0];
			int[] selIndexArray = selCountAndIndexArray[1];
			int validCount = betOpt.r9ValidCount();
			ExpandingR9Visitor visitor = new ExpandingR9Visitor(selCountArray, selIndexArray, dan, betGroup);
			Combination.generateWithAlgorithmL(validCount , 9, visitor);
			List<String> expanded = visitor.getExpandedCodes();
			for (String code : expanded) {
				List<CTBetContent> splitBC = new LinkedList<CTBetContent>();
				CTBetContent bcCopy = new CTBetContent();
				BeanUtils.copyProperties(bc, bcCopy);
				bcCopy.setCode(code);
				int total = getSingleBCMoney(bcCopy);
				if (total > Constants.MAX_MONEY_PER_TICKET) {
					splitBC.addAll( splitBCLargerThan20K(bcCopy) );
				}else{
					splitBC.add(bcCopy);
				}
				printCB(splitBC);
				for (CTBetContent sbc : splitBC) {
					int tm = getBCTotalMoney(sbc, scheme.getMultiple());
					if (tm > Constants.MAX_MONEY_PER_TICKET){
						splittedTickets.addAll( splitToSingleUnit(sbc, scheme) );
					}else{
						splittedTickets.add(makeTicket(scheme, sbc));
					}
				}
			}
		}
		return splittedTickets;
	}

	/**
	 * 普通复式拆票，每票的投注金额都不能超过：2万，要考虑倍数。
	 * 拆票方法：
	 * 1、如果单倍小于2万，那么只需要减少倍数。
	 * 2、如果单倍大于2万，那么先拆成单倍N票，再对每张票拆分，得到M票，总票数为N*M。
	 * @return 拆完的票，倍数、投注内容都可能被改变了。
	 * @throws BetException 如果拆票异常。
	 */
	private List<Ticket> splitMultiSelectedMatches(BetScheme scheme) throws BetException {
		LinkedList<Ticket> splittedTickets = new LinkedList<Ticket>();
		List<CTBetContent> bcList = scheme.getCtBetContents();
		for (CTBetContent bc : bcList){ 
			int totalBCMoney = getBCTotalMoney(bc, scheme.getMultiple());
			if (totalBCMoney > Constants.MAX_MONEY_PER_TICKET){
				int singleBCMoney = getSingleBCMoney(bc);
				// 拆分大于2万复式、多倍投注为小于2万、多倍的投注 
				if (singleBCMoney > Constants.MAX_MONEY_PER_TICKET){
					List<CTBetContent> bcSplitted = splitBCLargerThan20K(bc);
					printCB(bcSplitted);
					for (CTBetContent bcS : bcSplitted){
						int tm = getBCTotalMoney(bcS, scheme.getMultiple());
						if (tm > Constants.MAX_MONEY_PER_TICKET){
							splittedTickets.addAll(splitToSingleUnit(bcS, scheme));
						}else{
							splittedTickets.add(makeTicket(scheme, bcS));
						}
					}
				}else{
					// 拆分成单倍的票
					splittedTickets.addAll(splitToSingleUnit(bc, scheme));
				}
			}else{
				splittedTickets.add(makeTicket(scheme, bc));
			}
		}
		return splittedTickets;
	}

	private void printCB(List<CTBetContent> bcSplitted) {
		for (CTBetContent bc : bcSplitted) {
			System.out.println(bc.getCode());
		}
	}

	/**
	 * 变化倍数，拆分为小于2万的票，保证票数最少。
	 * @param bc
	 * @param scheme
	 * @return
	 * @throws BetException 
	 */
	private List<Ticket> splitToSingleUnit(CTBetContent bc, BetScheme scheme) throws BetException {
		int singleBCMoney = getSingleBCMoney(bc);
		if (singleBCMoney > Constants.MAX_MONEY_PER_TICKET){
			throw new BetException("singleUnit must less than 20k!");
		}
		int maxMultiPerTicket = (int) Math.floor(Constants.MAX_MONEY_PER_TICKET / (float)singleBCMoney);
		maxMultiPerTicket = Math.min(99, maxMultiPerTicket);
		LinkedList<Ticket> tickets = new LinkedList<Ticket>();
		int origMult = scheme.getMultiple();
		int remainMulti = origMult;
		while(remainMulti > 0){
			if (remainMulti - maxMultiPerTicket >= 0){
				scheme.setMultiple(maxMultiPerTicket);
			}else{
				scheme.setMultiple(remainMulti);
			}
			tickets.add(makeTicket(scheme, bc));
			remainMulti -= maxMultiPerTicket;
		}
		scheme.setMultiple(origMult);
		return tickets;
	}
	
	/**
	 * 拆分单倍超过2万的投注内容对象，保持倍数不变。
	 * 
	 * @param bc 		大于2万的投注内容
	 * @return 			小于2万的投注内容（单倍）
	 * @throws BetException 拆分异常
	 */
	private List<CTBetContent> splitBCLargerThan20K(CTBetContent bc) throws BetException {
		LinkedList<CTBetContent> cbList = new LinkedList<CTBetContent>();
		int singleBCMoney = getSingleBCMoney(bc);
		if (singleBCMoney <= Constants.MAX_MONEY_PER_TICKET){
			cbList.add(bc);
			return cbList;
		}
		SplittedCodes splittedCodes = splitCode(bc);
		CTBetContent bc1 = new CTBetContent();
		BeanUtils.copyProperties(bc, bc1);
		bc1.setCode(splittedCodes.getFirst());
		cbList.addAll(splitBCLargerThan20K(bc1));
		if (splittedCodes.getLast() != null) {
			CTBetContent bc2 = new CTBetContent();
			BeanUtils.copyProperties(bc, bc2);
			bc2.setCode(splittedCodes.getLast());
			cbList.addAll(splitBCLargerThan20K(bc2));
		}
		return cbList;
	}
	

	/**
	 * 找到第一个可以拆分的项，拆分为两部分，第一部分肯定是单选，剩下的可能是多选。
	 */
	private SplittedCodes splitCode(CTBetContent bc) throws BetException {
		String code  = bc.getCode();
		if (code.contains(";")) {
			throw new BetException("Composite Ticket should not contain single note seperator ';'.");
		}
		String[] parts = code.split(",");
		for (int i=0; i < parts.length; i++) {
			String p = parts[i].trim();
			if (p.length() > 1){
				String head = p.substring(0, 1);
				String tail = p.substring(1);
				StringBuilder b = new StringBuilder();
				for (int j=0; j<i; j++){
					b.append(parts[j]).append(',');
				}
				StringBuilder first = new StringBuilder(b);
				StringBuilder last = new StringBuilder(b);
				first.append(head);
				last.append(tail);
				int j=i+1;
				while(j<parts.length){
					first.append(",").append(parts[j]);
					last.append(",").append(parts[j]);
					j++;
				}
				return new SplittedCodes(first.toString(), last.toString(), i);
			}
		}
		// 没有复式就只返回前半部分
		return new SplittedCodes(code, null, -1);
	}

	/**
	 * 被拆分的投注代码。主要是为了包含拆分点的下标。
	 * @author yangbo
	 */
	private class SplittedCodes {
		private String first;
		private String last;
		private int indexOfSplit;
		
		public SplittedCodes(String first, String last, int indexOfSplit){
			this.first = first;
			this.last = last;
			this.indexOfSplit = indexOfSplit;
		}
		public String getFirst(){
			return this.first;
		}
		public String getLast(){
			return this.last;
		}
		@SuppressWarnings("unused")
		public int getIndexOfSplit(){
			return this.indexOfSplit;
		}
	}
	
	
	private int getSingleBCMoney(CTBetContent bc) throws BetException {
		PlayType playType = PlayType.valueOfLcPlayId(bc.getPlayId());
		CTBetOption betOptionForNotes = new CTBetOption(playType, 
				ChooseType.valueOfIndex(bc.getChooseType()), bc.getCode(), bc.getSeed());
		return betOptionForNotes.getMoney();
	}

	private int getBCTotalMoney(CTBetContent bc, int multiple) throws BetException {
		return getSingleBCMoney(bc) * multiple;
	}

	private List<Ticket> splitSingleSelectedMatches(BetScheme scheme,
			int maxNotesPerTicket) throws BetException {
		LinkedList<Ticket> splittedTickets = new LinkedList<Ticket>();
		int index = 0;
		LinkedList<CTBetContent> bcList = new LinkedList<CTBetContent>();
		for (CTBetContent bc : scheme.getCtBetContents()) {
			bcList.add(bc);
			index++;
			if (index >= maxNotesPerTicket) {
				splittedTickets.add( composeTicket(scheme,bcList) );
				index = 0;
				bcList.clear();
			}
		}
		if (bcList.size()>0){
			splittedTickets.add( composeTicket(scheme,bcList) );
		}
		return splittedTickets;
	}

	/**
	 * 多条投注内容合并为一张票。
	 * @param scheme 所属方案
	 * @param bcList 要合并到一张票中的投注内容。
	 * @return 票
	 * @throws BetException
	 */
	private Ticket composeTicket(BetScheme scheme, LinkedList<CTBetContent> bcList) throws BetException {
		CTBetContent bc = bcList.get(0);
		Ticket t = makeTicket(scheme, bc);
		int multi = scheme.getMultiple();
		StringBuilder c = new StringBuilder(t.getCode());
		int notes = t.getNote();
		if (bcList.size()>1){
			CTBetRequest request = scheme.getCtBetRequest();
			for (int i=1; i<bcList.size(); i++){
				CTBetContent b = bcList.get(i);
				c.append(";").append(b.getCode());
				CTBetOption betOptionForNotes = new CTBetOption(request.getPlayType(), 
						request.getChooseType(), b.getCode(), b.getSeed());
				notes += betOptionForNotes.getNotes() * multi;
			}
		}
		t.setCode(c.toString());
		t.setActualCode(c.toString());
		t.setNote(notes);
		t.setMoney(notes * Constants.MONEY_PER_NOTE);
		return t;
	}

	/**
	 * 用投注内容生成一张票
	 * @param scheme 所属方案
	 * @param bc 所属投注内容
	 * @return 票
	 * @throws BetException 
	 */
	@SuppressWarnings("unchecked")
	private Ticket makeTicket(BetScheme scheme, CTBetContent bc) throws BetException {
		CTBetRequest request = scheme.getCtBetRequest();
		int multiple = scheme.getMultiple();
		PlayType playType = request.getPlayType();

		Ticket ticket = new Ticket();
		ticket.setIssue(bc.getIssueNumber());
		ticket.setPlayId(scheme.getPlayId());
		String betOption = bc.getCode();
		CTBetOption betOptionForNotes = new CTBetOption(playType, request.getChooseType(), betOption, bc.getSeed());
		int notesOfOption = betOptionForNotes.getNotes();
		ticket.setNote(notesOfOption * multiple);
		ticket.setCode(betOption);
		ticket.setCreatedTime(scheme.getCreatedTime());
		ticket.setActualCode(betOption);
		ticket.setMultiple(multiple);
		ticket.setExpectBonus(BigDecimal.ZERO);		// 需要重构为方法
		ticket.setMoney(ticket.getNote() * Constants.MONEY_PER_NOTE );
		ticket.setOdds(StringUtils.EMPTY);			// 需要重构为方法
		ticket.setMatches(Collections.EMPTY_LIST);	// 需要重构为方法，实际就是投注选项
		ticket.setStatus(EntityStatus.TICKET_INIT);
		ticket.setPassTypeId(betOptionForNotes.getBetType());
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
