/*
 * CQSSBetStrategy.java
 *
 * @date 2013-1-4
 * @author Bob Yang, 张得斌
 * 
 * Copyright © 2013 All Rights Reserved.结信网络技术服务(上海)有限公司 版权所有 
 */

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
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.cqss.CQSSBetOption;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.util.BetStrategy;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PlayType;

/**
 * 重庆时时彩拆票。
 * 
 * <pre>
 * ﻿重庆时时彩前后台参数定义
 * 五星通选:  CQSS_5X_TX    (前台代码不予区分单复式, 后台代码需要拆分为多条单式五星通选。) 格式：123,123,123,123,123
 * 五星直选:  CQSS_5X_FS    (前台代码不予区分单复式, 后台代码需要区分五星直选单式与五星直选复式) 格式：123,123,123,123,123
 * 三星直选:  CQSS_3X_FS    (前台代码不予区分单复式, 后台代码需要区分三星直选单式与三星直选复式)
 * 三星和值:  CQSS_3X_HZ    (后台代码需要循环拆票，计算每一个和值的注数。支持多注方式，如：12;13)
 * 三星组三:  CQSS_3X_Z3_FS (中民支持2位数字，不用拆分)
 * 组三直选:  CQSS_3X_Z3_DS (后台代码需要将前台投注号码进行组合成组三单式投注格式)
 * 三星组六:  CQSS_3X_Z6_FS (前台代码不予区分单复式, 后台代码需要把3位数字变为单式)
 * 二星直选:  CQSS_2X_FS    (前台代码不予区分单复式, 后台代码需要区分二星直选单式与二星直选复式)
 * 二星和值:  CQSS_2X_HZ    (后台代码需要循环拆票，计算每一个和值的注数。如：12;13)
 * 二星组选:  CQSS_2X_ZX_ZH (前台代码不予区分单复式, 后台代码需要区分二星组选单式与二星组选复式)
 * 一星标选:  CQSS_1X_DS    
 * 大小单双:  CQSS_DXDS     (前台代码不予区分单复式, 后台代码需要将复式要拆分为单式的. 格式为: <十位>,<个位>)
 * 
 * 同位上的号码不需要分隔符,万千百十个位之间的号码需要逗号分割,每注号码之间使用分号分割.
 * </pre>
 * 
 * @author Bob Yang, 张得斌
 */
public class CQSSBetStrategy implements BetStrategy {
	private static final int MAX_NOTES_PER_TICKET_FOR_SINGLE = 5;
	private static final int MAX_MULTIPLE = 99;
	private static final int MAX_MONEY_PER_TICKET = 20000;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean match(String playId) {
		PlayType playType = PlayType.valueOfLcPlayId(playId);
		return playType.isCQSS();
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
		bet.setNote(computeTotalNotesFromTickets(tickets));
		bet.setMaxBonus(computeMaxBonus(tickets));
		return bet;
	}

	/**
	 * 拆分出最少的票。同时计算每张票的注数。 高频彩-重庆时时彩 拆票规则：
	 * <ul>
	 * <li>1、一星，如果大于5注，要拆为多张，但不能做合并，因为一星单式中重复数字不能出票。
	 * <li>2、如果倍数大于99倍，要拆为多张同内容的票。
	 * <li>3、大小单双,复式要拆分为单式的. 格式为: <十位>,<个位>
	 * <li>4、二星和值、三星和值需要循环拆票，计算每一个和值的注数。
	 * <li>5、二星直选，接口支持单、复式，不需要拆分。
	 * <li>6、三星组六，如果只有三个数字，要修改玩法为“三星组六单式”，否则出票失败。
	 * <li>7、 客户端的"组三直选", 因为包括单式和复式,所以需要拆分为多个组三单式。
	 * 格式如：12,5　表示按位分割的组三选项，拆开为"15;25"，展开为115,225 两注组三单式．
	 * <li>8、 五星通选，需要拆分为多条单式五星通选。
	 * <li>9、 五星直选，单个五星直选要用单式玩法。
	 * </ul>
	 * 注意:
	 * <ul>
	 * <li>客户端的二星组选，使用接口的“二星组选组合”。对于单个的情况可能需要用“二星组选单式”。
	 * <li>客户端没有：二星组合
	 * <li>中民的 组合 相当于其他家的复选。
	 * <li>组选 一定有两个相同的数字。
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
			case CQSS_DXDS:  // 大小单双. 完成
				tickets.addAll(splitDXDS(scheme, bcs));
				break;
			case CQSS_1X_DS: // 一星单式. 完成
				tickets.addAll(split1X(bcs, scheme));
				break;
			case CQSS_2X_DS: // 二星直选单式. 完成
				tickets.addAll(normalSplit(scheme, bcs));
				break;
			case CQSS_2X_FS: // 二星直选复式. 完成
				tickets.addAll(split2XFS(bcs, scheme));
				break;
			case CQSS_2X_ZH: // 二星组合
				unSupported(playType);
				break;
			case CQSS_2X_HZ: // 二星直选和值. 完成
				tickets.addAll(split2XHZ(bcs, scheme));
				break;
			case CQSS_2X_ZX_DS: // 二星组选单式
				unSupported(playType);
				break;
			case CQSS_2X_ZX_ZH: // 二星组选组合
				tickets.addAll(split2XZXZH(bcs, scheme));
				break;
			case CQSS_2X_ZX_FZ: // 二星组选分组
			case CQSS_2X_ZX_HZ: // 二星组选和值
			case CQSS_2X_ZX_BD: // 二星组选包胆
				unSupported(playType);
				break;
			case CQSS_3X_DS: // 三星单式
				tickets.addAll(normalSplit(scheme, bcs));
				break;
			case CQSS_3X_FS: // 三星复式. 完成
				tickets.addAll(split3XFS(scheme, bcs));
				break;
			case CQSS_3X_ZH: 	// 三星组合
			case CQSS_3X_ZH_FS: // 三星组合复式
				unSupported(playType);
				break;
			case CQSS_3X_HZ: 	// 三星直选和值. 完成
				tickets.addAll(split3XHZ(scheme, bcs));
				break;
			case CQSS_3X_Z3_DS: // 三星组三单式. 完成
				tickets.addAll(split3XZ3DS(scheme, bcs));
				break;
			case CQSS_3X_Z3_FS: // 三星组三复式. 完成
				tickets.addAll(normalSplit(scheme, bcs));
				break;
			case CQSS_3X_Z6_FS: // 三星组六复式. 完成
				tickets.addAll(split3XZ6FS(scheme, bcs));
				break;
			case CQSS_3X_ZX_HZ: // 三星组选合值
			case CQSS_3X_ZX_BD: // 三星组选包胆
			case CQSS_3X_Z6_DS: // 三星组六单式
				unSupported(playType);
				break;
			case CQSS_5X_TX: // 五星通选. 完成
				tickets.addAll(split5XTX(scheme, bcs));
				break;
			case CQSS_5X_DS: // 五星单式. 完成
				tickets.addAll(normalSplit(scheme, bcs));
				break;
			case CQSS_5X_FS: // 五星复式. 完成
				tickets.addAll(split5XFS(scheme, bcs));
				break;
			case CQSS_5X_ZH: // 五星组合. 暂不支持
				unSupported(playType);
				break;
			default:
				logger.error("Unknown playType '{}' for cqssc split tickets.",
						playType);
				break;
			}
		}
		return tickets;
	}

	/**
	 * 5星通选，支持复式，如：123,123,123,123,123 和 12345 的形式。
	 */
	private List<Ticket> split5XTX(BetScheme scheme,
			List<DigitalBetContent> bcs) throws BetException {
		List<Ticket> tickets = new LinkedList<Ticket>();
		for (DigitalBetContent bc:bcs){
			List<StringBuilder> buf = new LinkedList<StringBuilder>();
			List<String> codes = splitFS2DSCombination(scheme, bc, bc.getCode(), buf);
			List<String> ts = groupEvery(MAX_NOTES_PER_TICKET_FOR_SINGLE, codes);
			for (String code : ts){
				tickets.addAll(composeTickets(scheme, bc, code));
			}
		}
		return tickets;
	}

	/**
	 * 以组合方式拆分复式为单式。输入格式为：123,123,123,123。有多少逗号就有多少 M*N*L*K项。
	 * 输出格式为：11111。
	 * @param bc 投注项
	 * @param buf 存放拆分投注内容串的缓存 
	 * @return 如果是递归结束条件，返回票对象，否则返回 .
	 */
	private List<String> splitFS2DSCombination(BetScheme scheme, DigitalBetContent bc, 
			String code, List<StringBuilder> buf) throws BetException {
		List<String> codes = null;
		code = code.trim();
		if (code.indexOf(',') == -1){
			codes = new LinkedList<String>();
			if (buf.size()>0){
				for (StringBuilder b : buf){
					for (int i=0; i<code.length(); i++){
						StringBuilder nb = new StringBuilder();
						nb.append(b).append(code.charAt(i));
						//codes.addAll(composeTickets(scheme, bc, nb.toString()));
						codes.add(nb.toString());
					}
				}
			}else{ // 只需一层递归的情况
				//codes.addAll(composeTickets(scheme, bc, code));
				codes.add(code);
			}
			return codes;
		}
		int firstCommaIndex = code.indexOf(',');
		String head = code.substring(0, firstCommaIndex);
		String tail = code.substring(Math.min(code.length(), firstCommaIndex+1));
		List<StringBuilder> newBuf = new LinkedList<StringBuilder>();
		for (int i=0; i<head.length(); i++){
			if (buf.size()>0){
				for (StringBuilder b : buf){
					StringBuilder nb = new StringBuilder(b.toString());
					nb.append(head.charAt(i));
					newBuf.add(nb);
				}
			}else{
				StringBuilder nb = new StringBuilder();
				nb.append(head.charAt(i));
				newBuf.add(nb);
			}
		}
		return splitFS2DSCombination(scheme, bc, tail, newBuf);
	}

	/**
	 * 二星组选组合.<br/>
	 * 如果是2位数字，就改玩法类型为 二星组选单式。
	 */
	private List<Ticket> split2XZXZH(List<DigitalBetContent> bcs, BetScheme scheme) throws BetException {
		LinkedList<Ticket> tickets = new LinkedList<Ticket>();
		for (DigitalBetContent bc : bcs) {
			if (bc.getCode().length()==2){
				DigitalBetContent cbc = new DigitalBetContent();
				BeanUtils.copyProperties(bc, cbc);
				cbc.setPlayId(PlayType.CQSS_2X_ZX_DS.getPlayId());
				tickets.addAll(composeTickets(scheme, cbc, cbc.getCode()));
			}else{
				tickets.addAll(composeTickets(scheme, bc, bc.getCode()));
			}
		}
		return tickets;
	}

	private List<Ticket> split2XFS(List<DigitalBetContent> bcs,
			BetScheme scheme) throws BetException {
		return splitFS2DS(scheme, bcs, PlayType.CQSS_2X_DS);
	}

	private void unSupported(PlayType playType) throws BetException {
		throw new BetException("Unsupported playType: " + playType);
	}

	/**
	 * 三星和值:  CQSS_3X_HZ    (后台代码需要循环拆票，计算每一个和值的注数。支持多注方式，如：12;13)
	 */
	private List<Ticket> split3XHZ(BetScheme scheme, List<DigitalBetContent> bcs) throws BetException {
		return splitToOneNote(scheme, bcs);
	}
	
	private List<Ticket> splitToOneNote(BetScheme scheme, List<DigitalBetContent> bcs) throws BetException {
		LinkedList<Ticket> tickets = new LinkedList<Ticket>();
		for (DigitalBetContent bc : bcs) {
			for (String code : bc.getCode().split(";")){
				tickets.addAll(composeTickets(scheme, bc, code));
			}
		}
		return tickets;
	}

	/**
	 * 三星直选:  CQSS_3X_FS    (前台代码不予区分单复式, 后台代码需要区分三星直选单式与三星直选复式)
	 */
	private List<Ticket> split3XFS(BetScheme scheme, List<DigitalBetContent> bcs) throws BetException {
		return splitFS2DS(scheme, bcs, PlayType.CQSS_3X_DS);
	}

	/**
	 * 五星直选:  CQSS_5X_FS    (前台代码不予区分单复式, 后台代码需要区分五星直选单式与五星直选复式)
	 */
	private List<Ticket> split5XFS(BetScheme scheme, List<DigitalBetContent> bcs) throws BetException {
		return splitFS2DS(scheme, bcs, PlayType.CQSS_5X_DS);
	}

	/**
	 * 复式拆为单式，应用"复式玩法变单式玩法"规则，即把“1,2,3”变为“123”的形式，并且设置对应的单式玩法类型。
	 * 
	 * @param scheme 方案
	 * @param bcs 投注内容
	 * @param dsPlayType 复式对应的单式玩法
	 * @return 最后要出的票
	 * @throws BetException 拆分异常
	 */
	private List<Ticket> splitFS2DS(BetScheme scheme, List<DigitalBetContent> bcs, PlayType dsPlayType) throws BetException{
		LinkedList<DigitalBetContent> changedBCs = new LinkedList<DigitalBetContent>();
		for (DigitalBetContent bc : bcs) {
			DigitalBetContent cbc = new DigitalBetContent();
			BeanUtils.copyProperties(bc, cbc);
			if (onlyOneDigitInAllPos(bc.getCode())){
				cbc.setPlayId(dsPlayType.getPlayId());
				// 去掉","合为一注单式
				cbc.setCode(bc.getCode().replaceAll(",", "").trim());
			}
			changedBCs.add(cbc);
		}
		return normalSplit(scheme, changedBCs);
	}
	
	/**
	 * 是否所有位上都只有一个数字。
	 * @param code 复式投注内容
	 * @return true 所有位上都只有一个数字，或者code为空串。
	 */
	private boolean onlyOneDigitInAllPos(String code) {
		String[] digits = code.split(",");
		boolean onlyOne = true;
		for (String d : digits){
			if (d.trim().length()>1){
				onlyOne = false;
				break;
			}
		}
		return onlyOne;
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
	 * 按照“1星”方式拆票. 1、一星，如果大于5注，要拆为多张，但不能做合并，因为一星单式中重复数字不能出票。<br/>
	 * 格式：1;2;3
	 */
	private List<Ticket> split1X(List<DigitalBetContent> bcList,
			BetScheme scheme) throws BetException {
		LinkedList<Ticket> tickets = new LinkedList<Ticket>();
		for (DigitalBetContent bc : bcList) {
			List<String> codes = splitToMultiNotes(bc.getCode());
			for (String code : codes) {
				tickets.addAll(composeTickets(scheme, bc, code));
			}
		}
		return tickets;
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

	/**
	 * 如果倍数大于99倍或者钱数大于2万，拆为多张内容相同的票。
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
				throw new BetException("money of single multiple ticket can not be more than 20k.", AppCode.EXCEED_MAX_NOTE);
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
		
		BetCodeValidator codeValidator = new CQSSBetCodeValidator();
		codeValidator.valid(code, playType);

		Ticket ticket = new Ticket();
		ticket.setIssue(request.getIssue());
		ticket.setPlayId(bc.getPlayId());
		BetOption betOptionForNotes = new CQSSBetOption(playType, code);
		int notesOfOption = betOptionForNotes.getNotes();
		ticket.setNote(notesOfOption * multiple);
		ticket.setCode(code);
		ticket.setCreatedTime(scheme.getCreatedTime());
		ticket.setActualCode(code);
		ticket.setMultiple(multiple);
		int expectBonus = CQSSBonusComputer.computeExpectBonus(playType,
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

	/**
	 * 按照“三星组三”投注协议拆票, 客户端的"组三直选", 因为包括单式和复式,所以需要拆分为多个组三单式.<br/>
	 * <br/>
	 * 格式如：12,5　表示按位分割的组三选项，展开为115,225 两注组三单式．
	 * 
	 * @param bcs 一条投注内容
	 * @param scheme 方案
	 */
	private List<Ticket> split3XZ3DS(BetScheme scheme, List<DigitalBetContent> bcs)
			throws BetException {
		LinkedList<Ticket> tickets = new LinkedList<Ticket>();
		for (DigitalBetContent bc : bcs) {
			String betContent = bc.getCode();
			String[] placeDigits = betContent.split(",");
			if (placeDigits != null && placeDigits.length == 2) {
				for (int i = 0; i < placeDigits[0].length(); i++) {
					Character tensDigit = placeDigits[0].charAt(i);
					for (int j = 0; j < placeDigits[1].length(); j++) {
						Character onesDigit = placeDigits[1].charAt(j);
						if (!onesDigit.equals(tensDigit)) {
							StringBuilder codeBuilder = new StringBuilder();
							codeBuilder.append(tensDigit).append(tensDigit).append(onesDigit);
							tickets.addAll(composeTickets(scheme, bc, codeBuilder.toString()));
						}
					}
				}
			}else{
				throw new BetException("Wrong bet format: " + betContent + ", playId: " + bc.getPlayId());
			}
		}
		return tickets;
	}

	/**
	 * 按照“三星组六”投注协议拆票, 如果只有三个数字，要修改玩法为“三星组六单式”，否则出票失败。
	 * 
	 * @param bcs
	 */
	private List<Ticket> split3XZ6FS(BetScheme scheme, List<DigitalBetContent> bcs)
			throws BetException {
		LinkedList<Ticket> tickets = new LinkedList<Ticket>();
		for (DigitalBetContent bc : bcs){
			String code = bc.getCode().trim();
			int clen = code.length();
			if (clen < 3) {
				throw new BetException("Wrong code format: " + code + ", playId: " + bc.getPlayId());
			}
			if (clen == 3){
				DigitalBetContent changedBC = new DigitalBetContent();
				BeanUtils.copyProperties(bc, changedBC);
				changedBC.setPlayId(PlayType.CQSS_3X_Z6_DS.getPlayId());
				tickets.addAll(composeTickets(scheme, changedBC, code));
			}else{
				tickets.addAll(composeTickets(scheme, bc, code));
			}
		}
		return tickets;
	}
	
	/**
	 * 二星和值
	 */
	private List<Ticket> split2XHZ(List<DigitalBetContent> bcs,
			BetScheme scheme) throws BetException {
		return splitToOneNote(scheme, bcs);
	}

	/**
	 * 大小单双. 把"1234,1234"拆分为"11;12;13;14..."的格式。
	 */
	private List<Ticket> splitDXDS(BetScheme scheme,
			List<DigitalBetContent> bcs) throws BetException {
		int step = MAX_NOTES_PER_TICKET_FOR_SINGLE;
		LinkedList<Ticket> tickets = new LinkedList<Ticket>();
		List<String> dsList = new LinkedList<String>();
		for (DigitalBetContent bc : bcs){
			String code = bc.getCode();
			dsList.addAll(permutate(code));
		}
		List<String> groups = groupEvery(step, dsList);
		for (String theGroup : groups) {
			tickets.addAll(composeTickets(scheme, bcs.get(0), theGroup));
		}
		return tickets;
	}

	/**
	 * 按照每step个元素分为一组的方式分组，元素间用";"连接。
	 * @param step 每组最大元素个数
	 * @param dsList 被分组的列表
	 * @return 分组后的数组。
	 */
	private List<String> groupEvery(int step, List<String> dsList) {
		List<String> grouped = new LinkedList<String>();
		StringBuilder builder = new StringBuilder();
		String sep = ";";
		for (int i=0; i<dsList.size(); i++) {
			if (i>0 && i%step == 0) {
				grouped.add(builder.toString());
				builder.delete(0, builder.length());
			}
			if (builder.length()>0){
				builder.append(sep);
			}
			builder.append(dsList.get(i));
		}
		if (builder.length()>0){
			grouped.add(builder.toString());
		}
		return grouped;
	}

	/**
	 * 排列"1234,1234"为"List[11,12,13,14...]"的数组。
	 * @param code 固定格式的串，格式为："1234,1234"
	 * @return 全排列数字串. 如果 code 中没有逗号，返回原串。
	 * @throws BetException 如果 code 中的分隔符多于1.
	 */
	private List<String> permutate(String code) throws BetException {
		LinkedList<String> permulation = new LinkedList<String>();
		String[] parts = code.split(",");
		if (parts.length <2){
			permulation.add(code);
			return permulation;
		}
		if (parts.length>2){
			throw new BetException("Wrong format, more than 1 seperator: " + code + ", for DXDS");
		}
		for (int i=0; i<parts.length; i++){
			parts[i] = parts[i].trim();
		}
		String first = parts[0];
		String second = parts[1];
		for (int i=0; i<first.length(); i++) {
			char fc = first.charAt(i);
			for (int j=0; j<second.length(); j++) {
				char sc = second.charAt(j);
				permulation.add(""+fc+sc);
			}
		}
		return permulation;
	}
}
