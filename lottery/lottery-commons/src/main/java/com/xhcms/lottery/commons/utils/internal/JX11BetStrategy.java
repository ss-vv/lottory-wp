/**
 * 
 */
package com.xhcms.lottery.commons.utils.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.laicai.util.Combination;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.jx11.JX11BetOption;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.util.BetStrategy;
import com.xhcms.lottery.commons.utils.internal.JX11BonusComputer.JX11Bonus;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PlayType;

/**
 * 江西11选5的拆票策略器。
 * 不需要因为倍数拆票，因为界面和接口都限制最大99倍了。
 * @author yangbo
 */
public class JX11BetStrategy implements BetStrategy {

	private static final int MAX_NOTES_PER_TICKET_FOR_SINGLE = 5;	// 单式，一张票最大的注数
	
	@Override
	public boolean match(String playId) {
		PlayType playType = PlayType.valueOfLcPlayId(playId);
		return playType.isJX11();
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
        bet.setMaxBonus( computeMaxBonus(tickets) );
		return bet;
	}
	
	/**
	 * 拆分出最少的票。同时计算每张票的注数。
	 * 高频彩-江西11选5 拆票规则：
	 * <ul>
	 * <li>前一方式，单式只支持一票一注号码，要根据指定注数拆票。
	 * <li>单式下注对应“机选”方式，接口只允许一票5注，而界面上允许大于5注，最大99注，所以单式的投注，大于5注的要拆为多张票，每张票的倍数都一样。
	 * <li>倍数，接口允许一票最大99倍，界面上也只允许最大99倍，所以不需要因为倍数拆票
	 * <li>手选方式，任选、组选、直选玩法，所有情况都可以只出一张票（因为前端倍数最大也就 只允许99倍）
	 * <li>胆拖方式，同手选方式，只出一张票就可以
	 * <li>机选方式，任选、直选、组选玩法，注数大于5注的，都要按照5注一票的方式拆分为多张 票，所有票的倍数都一样。
	 * </ul>
	 * 
	 * @param scheme 投注方案对象
	 * @return 拆分得到的票
	 * @throws BetException
	 */
	private List<Ticket> splitTickets(BetScheme scheme) throws BetException {
		DigitalBetRequest jx11BetRequest = scheme.getDigitalBetRequest();
		ChooseType chooseType = jx11BetRequest.getChooseType();
		PlayType playType = jx11BetRequest.getPlayType();
		if (playType == PlayType.JX11_R1) {
			return splitByR1(scheme);
		}
		if (playType == PlayType.JX11_R8) {
			return splitByR8(scheme);
		}
		if (chooseType == ChooseType.MACHINE) {
			return splitByMachineChooseType(scheme);
		}
		return splitByOneTicketOneBetContent(scheme);
	}

	private List<Ticket> splitByR8(BetScheme scheme) throws BetException {
		DigitalBetRequest betRequest = scheme.getDigitalBetRequest();
		ChooseType chooseType = betRequest.getChooseType();
		List<String> ticketContents = new ArrayList<String>();
		if (chooseType == ChooseType.MANUAL){
			// 复式拆成单式
			for (String betContent : betRequest.getBetContents()) {
				List<String> bet = expandR8Manual(betContent);
				bet = mergeOptionsIntoTicket(bet);
				ticketContents.addAll(bet);
			}
		} else if (chooseType == ChooseType.DAN){
			// 胆拖拆成单式
			ticketContents = expandR8Dan(betRequest.getBetContents().get(0));
			ticketContents = mergeOptionsIntoTicket(ticketContents);
		} else if (chooseType == ChooseType.MACHINE){
			ticketContents = betRequest.getBetContents();
		}
		return makeTicketsWithTicketContents(ticketContents, scheme);
	}

	/**
	 * 胆拖拆成单式. 输入：(01,02,03,04)05,06,07,08,09
	 * @throws BetException 如果格式错误。 
	 */
	private List<String> expandR8Dan(String option) throws BetException{
		Pattern p = Pattern.compile("\\((.*)\\)(.*)");
		Matcher m = p.matcher(option);
		if (m.matches()){
			// 展开拖码
			String dan = m.group(1);
			int danCount = dan.split(",").length;
			String[] tuo = m.group(2).split(",");
			JX11CombinVistor v = new JX11CombinVistor(tuo);
			Combination.generateWithAlgorithmL(tuo.length, 8-danCount, v);
			List<String> options = v.getDigitsOptions();
			for (int i=0; i<options.size(); i++){
				options.set(i, dan+","+options.get(i));
			}
			return options;
		}
		throw new BetException("R8 Dan option has wrong format: " + option);
	}

	/**
	 * 复式拆成单式. 输入：01,02,03,04,05,06,07,08,09
	 * @throws BetException 
	 */
	private List<String> expandR8Manual(String option) throws BetException {
		String[] digits = option.split(",");
		if (digits.length<8){
			throw new BetException("R8 must has at least 8 digits.");
		}
		
		JX11CombinVistor visitor = new JX11CombinVistor(digits);
		Combination.generateWithAlgorithmL(digits.length, 8, visitor);
		return visitor.getDigitsOptions();
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
		for (Ticket t : tickets){
			maxBonus += t.getExpectBonus().intValue();
		}
		return maxBonus;
	}

	// 按照“前1”方式拆票
	private List<Ticket> splitByR1(BetScheme scheme) throws BetException {
		DigitalBetRequest request = scheme.getDigitalBetRequest();
		List<String> ticketContents = collectR1TicketContents(request.getBetContents());
		return makeTicketsWithTicketContents(ticketContents, scheme);
	}

	/**
	 *  按照机选方式拆票，对于除了“前一”之外的其他玩法，需要合并5注为一票。
	 */
	private List<Ticket> splitByMachineChooseType(BetScheme scheme) throws BetException {
		DigitalBetRequest jx11BetRequest = scheme.getDigitalBetRequest();
		List<String> bets = jx11BetRequest.getBetContents();
		List<String> mergedBets = mergeOptionsIntoTicket(bets);
		return makeTicketsWithTicketContents(mergedBets, scheme);
	}

	// 5注合并为1票
	private List<String> mergeOptionsIntoTicket(List<String> bets) {
		LinkedList<String> mergedBets = new LinkedList<String>();
		StringBuilder oneTicketOptions = new StringBuilder();
		for (int i=0; i<bets.size(); i++) {
			if ( i>0 && i % MAX_NOTES_PER_TICKET_FOR_SINGLE == 0 ) {
				mergedBets.add(oneTicketOptions.toString());
				oneTicketOptions.delete(0, oneTicketOptions.length());
			}
			if (oneTicketOptions.length()>0) {
				oneTicketOptions.append(";");
			}
			oneTicketOptions.append(bets.get(i));
		}
		if (oneTicketOptions.length()>0) {
			mergedBets.add(oneTicketOptions.toString());
		}
		return mergedBets;
	}

	// 按照一个投注记录一张票方式投注。适合于“手选、胆选”方式。
	private List<Ticket> splitByOneTicketOneBetContent(BetScheme scheme) throws BetException {
		DigitalBetRequest jx11BetRequest = scheme.getDigitalBetRequest();
		List<String> bets = jx11BetRequest.getBetContents();
		return makeTicketsWithTicketContents(bets, scheme);
	}

	/**
	 * 一条"票信息"对应一张票。
	 * @param ticketContents 票信息，一条记录对应一张票
	 * @param scheme 方案
	 * @return 票列表
	 * @throws BetException 如果投注选项有错误
	 */
	private List<Ticket> makeTicketsWithTicketContents(List<String> ticketContents, BetScheme scheme) throws BetException {
		LinkedList<Ticket> ticketsSplitted = new LinkedList<Ticket>();
		for (String betOption : ticketContents) {
			Ticket ticket = composeTicket(scheme, betOption);
			ticketsSplitted.add(ticket);
		}
		return ticketsSplitted;
	}

	// 对前一手选方式，要拆分逗号分隔的数字。
	private List<String> collectR1TicketContents(List<String> betContents) {
		List<String> betDigits = new LinkedList<String>();
		for (String oneNote : betContents){
			betDigits.addAll(Arrays.asList(oneNote.split(",")));
		}
		return betDigits;
	}

	/**
	 * @param scheme 方案
	 * @param betOption 一条投注选项
	 * @return 票
	 * @throws BetException 如果投注选项有错误
	 */
	@SuppressWarnings("unchecked")
	private Ticket composeTicket(BetScheme scheme, String betOption) throws BetException {
		DigitalBetRequest request = scheme.getDigitalBetRequest();
		int multiple = request.getMultiple();
		PlayType playType = request.getPlayType();

		Ticket ticket = new Ticket();
		ticket.setIssue(request.getIssue());
		ticket.setPlayId(scheme.getPlayId());
		BetOption betOptionForNotes = new JX11BetOption(playType, request.getChooseType(), betOption);
		int notesOfOption = betOptionForNotes.getNotes();
		ticket.setNote(notesOfOption * multiple);
		ticket.setCode(addD2D3Flag(betOption, playType, request.getChooseType()));
		ticket.setCreatedTime(scheme.getCreatedTime());
		ticket.setActualCode(betOption);
		ticket.setMultiple(multiple);
		int expectBonus = JX11BonusComputer.computeExpectBonus(playType, ticket.getNote());
		ticket.setExpectBonus(BigDecimal.valueOf(expectBonus));
		ticket.setMoney(ticket.getNote() * Constants.MONEY_PER_NOTE );
		ticket.setOdds(""+JX11Bonus.valueOfPlayType(playType));
		ticket.setMatches(Collections.EMPTY_LIST);
		ticket.setPassTypeId(StringUtils.EMPTY);
		ticket.setStatus(EntityStatus.TICKET_INIT);
		ticket.setPassTypeId(StringUtils.EMPTY);
		ticket.setNumber(StringUtils.EMPTY);

		return ticket;
	}

	/**
	 * 前二、前三直选根据是单式还是复式追加分号";"，单式追加分号。
	 * @param betOption 投注选项
	 * @param playType 玩法类型
	 * @param chooseType 
	 * @return 修改后的投注选项
	 */
	private String addD2D3Flag(String betOption, PlayType playType, ChooseType chooseType) {
		if ((playType == PlayType.JX11_D2 || playType == PlayType.JX11_D3)){ 
			switch(chooseType){
			case MACHINE:
				if (!betOption.endsWith(";")){
					return betOption+";";
				}
				break;
			case MANUAL:
				// 复式变单式
				if (!betOption.contains(",")){
					return betOption + ";";
				}
				if (betOption.endsWith(";")){
					return betOption.substring(0, betOption.length()-1);
				}
				break;
			}
		}
		return betOption;
	}
}
