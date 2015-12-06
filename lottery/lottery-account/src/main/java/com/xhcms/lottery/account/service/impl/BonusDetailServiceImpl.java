package com.xhcms.lottery.account.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.laicai.util.Combination;
import com.laicai.util.Visitor;
import com.xhcms.lottery.account.service.BonusDetailException;
import com.xhcms.lottery.account.service.BonusDetailService;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.bonus.BonusDetail;
import com.xhcms.lottery.commons.data.bonus.MatchOddsPair;
import com.xhcms.lottery.commons.data.bonus.MatchOddsTicket;
import com.xhcms.lottery.commons.data.bonus.SupposeHit;
import com.xhcms.lottery.commons.data.bonus.WinMatch;
import com.xhcms.lottery.commons.data.bonus.WinTicketDetail;
import com.xhcms.lottery.commons.utils.internal.JCBetStrategy;

/**
 * 计算奖金明细。
 * 
 * @author Yang Bo
 */
public class BonusDetailServiceImpl implements BonusDetailService {
//	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public BonusDetail computeBonusDetail(BetScheme scheme) throws BonusDetailException {
		scheme.getPlayId();
		JCBetStrategy jcStrategy = new JCBetStrategy();
		List<BonusDetail> detailsOnPassType = new LinkedList<BonusDetail>();
		
		for (String passType : scheme.getPassTypes()){
			BetScheme unitScheme = composeUnitScheme(scheme, passType);
			Bet bet = jcStrategy.resolve(unitScheme);
			List<Ticket> tickets = bet.getTickets();
			BonusDetail bonusDetail  = resolveBonusDetail(tickets, unitScheme);
			detailsOnPassType.add(bonusDetail);
		}
		return mergeDetails(detailsOnPassType, scheme.getMultiple());
	}

	/** 合并多个过关类型的奖金明细，并加上倍数 */
	private BonusDetail mergeDetails(List<BonusDetail> detailsOnPassType, int multiple) {
		BonusDetail mergedDetails = new BonusDetail();
		for (int i=0; i<detailsOnPassType.size(); i++){
			BonusDetail detail = detailsOnPassType.get(i);
			mergedDetails.getPassTypes().addAll(detail.getPassTypes());
			List<SupposeHit> mergedHits = mergeSupposeHits(mergedDetails.getSupposeHits(), 
					detail.getSupposeHits(), multiple);
			mergedDetails.setSupposeHits(mergedHits);
		}
		return mergedDetails;
	}

	private List<SupposeHit> mergeSupposeHits(List<SupposeHit> hits1,
			List<SupposeHit> hits2, int multiple) {
		LinkedList<SupposeHit> mergedHits = new LinkedList<SupposeHit>();
		LinkedList<SupposeHit> unionHits = new LinkedList<SupposeHit>();
		unionHits.addAll(hits1);
		unionHits.addAll(hits2);
		
		List<Integer> deleted = new ArrayList<Integer>(unionHits.size());
		for(int i=0; i<unionHits.size(); i++){
			if (deleted.contains(i)){
				continue;
			}
			SupposeHit hit = unionHits.get(i);
			deleted.add(i);
			int hitCount = hit.getHitCount();
			SupposeHit sameHit = findSameCountHitAndDelete(hitCount, unionHits, deleted);
			if (sameHit != null){
				SupposeHit mergedSupposeHit = new SupposeHit();
				mergedSupposeHit.setHitCount(hitCount);
				mergedSupposeHit.getMinDetails().addAll(hit.getMinDetails());
				mergedSupposeHit.getMinDetails().addAll(sameHit.getMinDetails());
				mergedSupposeHit.setMinBonus(hit.getMinBonus().add(sameHit.getMinBonus()));

				mergedSupposeHit.getMaxDetails().addAll(hit.getMaxDetails());
				mergedSupposeHit.getMaxDetails().addAll(sameHit.getMaxDetails());
				mergedSupposeHit.setMaxBonus(hit.getMaxBonus().add(sameHit.getMaxBonus()));
				addMultiple(mergedSupposeHit, multiple);
				mergedHits.add(mergedSupposeHit);
			}else{
				addMultiple(hit, multiple);
				mergedHits.add(hit);
			}
		}
		return mergedHits;
	}

	private void addMultiple(SupposeHit hit, int multiple) {
		hit.setMinBonus(hit.getMinBonus().multiply(new BigDecimal(multiple)));
		hit.setMaxBonus(hit.getMaxBonus().multiply(new BigDecimal(multiple)));
	}

	private SupposeHit findSameCountHitAndDelete(int hitCount, List<SupposeHit> hits, List<Integer> deleted) {
		for (int i=0; i<hits.size(); i++) {
			SupposeHit theHit = hits.get(i);
			if (theHit.getHitCount() == hitCount && !deleted.contains(i)){
				deleted.add(i);
				return theHit;
			}
			
		}
		return null;
	}

	/** 
	 * 计算所有票的奖金明细 
	 * @throws BonusDetailException 
	 */
	private BonusDetail resolveBonusDetail(List<Ticket> tickets, BetScheme unitScheme) throws BonusDetailException {
		int minHitCount = minHitCount(unitScheme);
		int maxHitCount = maxHitCount(unitScheme);
		BonusDetail bonusDetail = new BonusDetail();
		bonusDetail.getPassTypes().add(unitScheme.getPassTypes().get(0));
		String originalPassType = unitScheme.getPassTypes().get(0);
		List<MatchOddsTicket> expandedTickets = expandTicketsToM1(tickets, unitScheme.getMatchs(), originalPassType);
		List<MatchOddsPair> matchesSelected = uniqueMatches(expandedTickets);
		List<String> minMatchList = sortMinMatches(matchesSelected);
		List<String> maxMatchList = sortMaxMatches(matchesSelected);
		for (int hit=minHitCount; hit <= maxHitCount; hit++) {
			SupposeHit supposeHit = computeSupposeHit(hit, expandedTickets, minMatchList, maxMatchList);
			bonusDetail.getSupposeHits().add(supposeHit);
		}
		return bonusDetail;
	}

	private List<MatchOddsPair> uniqueMatches(
			List<MatchOddsTicket> tickets) {
		Set<MatchOddsPair> mopSet = new HashSet<MatchOddsPair>();
		for (MatchOddsTicket t :  tickets){
			mopSet.addAll(t.getMatchOddsPairs());
		}
		return Arrays.asList(mopSet.toArray(new MatchOddsPair[1]));
	}

	
	private class MatchOddsPairComparator implements Comparator<MatchOddsPair>{
		private boolean isMin = true;
		
		public MatchOddsPairComparator(boolean isMin){
			this.isMin = isMin;
		}
		@Override
		public int compare(MatchOddsPair pair1, MatchOddsPair pair2) {
			BigDecimal bonus1 = null, bonus2 = null;
			if (isMin){
				bonus1 = minOdds(pair1.getOdds());
				bonus2 = minOdds(pair2.getOdds());
			}else{
				bonus1 = maxOdds(pair1.getOdds());
				bonus2 = maxOdds(pair2.getOdds());
			}
			return bonus1.compareTo(bonus2);
		}
	}
	
	private List<String> sortMaxMatches(List<MatchOddsPair> matchesSelected) {
		boolean isMin = false;
		Collections.sort(matchesSelected, new MatchOddsPairComparator(isMin));
		if (!isMin) {
			Collections.reverse(matchesSelected);
		}
		return extractMatches(matchesSelected);
	}

	private List<String> extractMatches(List<MatchOddsPair> moPairs) {
		LinkedList<String> matches = new LinkedList<String>();
		for (MatchOddsPair pair : moPairs) {
			matches.add(pair.getMatch());
		}
		return matches;
	}

	private List<String> sortMinMatches(List<MatchOddsPair> matchesSelected) {
		boolean isMin = true;
		Collections.sort(matchesSelected, new MatchOddsPairComparator(isMin));
		return extractMatches(matchesSelected);
	}

	/** 
	 * 展开 m@n 为 m@1 的 MatchOddsTicket 
	 * @param originalPassType 
	 * @throws BonusDetailException 
	 */
	private List<MatchOddsTicket> expandTicketsToM1(List<Ticket> tickets, List<BetMatch> betMatches, String originalPassType) throws BonusDetailException {
		LinkedList<MatchOddsTicket> expandedMOPairs = new LinkedList<MatchOddsTicket>();
		for (Ticket theTicket : tickets) {
			expandedMOPairs.addAll(expandOneTicket(theTicket, betMatches, originalPassType));
		}
		computeMinMaxBonus(expandedMOPairs);
		return expandedMOPairs;
	}

	/** 
	 * 展开 m@n 为 m@1 的 MatchOddsTicket 
	 * @param originalPassType 
	 * @throws BonusDetailException 
	 */
	private List<MatchOddsTicket> expandOneTicket(Ticket theTicket, List<BetMatch> betMatches, String originalPassType) throws BonusDetailException {
		MatchOddsTicket moTicket = ticketToMO(theTicket, betMatches, originalPassType);
		int [] mn = parseMN(moTicket.getPassType());
		if (mn[1] == 1){
			return Arrays.asList(moTicket);
		}
		int mnCode = mn[0]*1000 + mn[1];
		LinkedList<MatchOddsTicket> expandedMOTickets = new LinkedList<MatchOddsTicket>();
		switch(mnCode){
		case 3003:
			expandedMOTickets.addAll(combineMatch(3, 2, moTicket));
			break;
		case 3004:
			expandedMOTickets.addAll(combineMatch(3, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(3, 2, moTicket));
			break;
		case 4004:
			expandedMOTickets.addAll(combineMatch(4, 3, moTicket));
			break;
		case 4005:
			expandedMOTickets.addAll(combineMatch(4, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(4, 3, moTicket));
			break;
		case 4006:
			expandedMOTickets.addAll(combineMatch(4, 2, moTicket));
			break;
		case 4011:
			expandedMOTickets.addAll(combineMatch(4, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(4, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(4, 2, moTicket));
			break;
		case 5005:
			expandedMOTickets.addAll(combineMatch(5, 4, moTicket));
			break;
		case 5006:
			expandedMOTickets.addAll(combineMatch(5, 5, moTicket));
			expandedMOTickets.addAll(combineMatch(5, 4, moTicket));
			break;
		case 5010:
			expandedMOTickets.addAll(combineMatch(5, 2, moTicket));
			break;
		case 5016:
			expandedMOTickets.addAll(combineMatch(5, 5, moTicket));
			expandedMOTickets.addAll(combineMatch(5, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(5, 3, moTicket));
			break;
		case 5020:
			expandedMOTickets.addAll(combineMatch(5, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(5, 2, moTicket));
			break;
		case 5026:
			expandedMOTickets.addAll(combineMatch(5, 5, moTicket));
			expandedMOTickets.addAll(combineMatch(5, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(5, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(5, 2, moTicket));
			break;
		case 6006:
			expandedMOTickets.addAll(combineMatch(6, 5, moTicket));
			break;
		case 6007:
			expandedMOTickets.addAll(combineMatch(6, 6, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 5, moTicket));
			break;
		case 6015:
			expandedMOTickets.addAll(combineMatch(6, 2, moTicket));
			break;
		case 6020:
			expandedMOTickets.addAll(combineMatch(6, 3, moTicket));
			break;
		case 6022:
			expandedMOTickets.addAll(combineMatch(6, 6, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 5, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 4, moTicket));
			break;
		case 6035:
			expandedMOTickets.addAll(combineMatch(6, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 2, moTicket));
			break;
		case 6042:
			expandedMOTickets.addAll(combineMatch(6, 6, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 5, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 3, moTicket));
			break;
		case 6050:
			expandedMOTickets.addAll(combineMatch(6, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 2, moTicket));
			break;
		case 6057:
			expandedMOTickets.addAll(combineMatch(6, 6, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 5, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(6, 2, moTicket));
			break;
		case 7007:
			expandedMOTickets.addAll(combineMatch(7, 6, moTicket));
			break;
		case 7008:
			expandedMOTickets.addAll(combineMatch(7, 7, moTicket));
			expandedMOTickets.addAll(combineMatch(7, 6, moTicket));
			break;
		case 7021:
			expandedMOTickets.addAll(combineMatch(7, 5, moTicket));
			break;
		case 7035:
			expandedMOTickets.addAll(combineMatch(7, 4, moTicket));
			break;
		case 7120:
			expandedMOTickets.addAll(combineMatch(7, 7, moTicket));
			expandedMOTickets.addAll(combineMatch(7, 6, moTicket));
			expandedMOTickets.addAll(combineMatch(7, 5, moTicket));
			expandedMOTickets.addAll(combineMatch(7, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(7, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(7, 2, moTicket));
			break;
		case 8008:
			expandedMOTickets.addAll(combineMatch(8, 7, moTicket));
			break;
		case 8009:
			expandedMOTickets.addAll(combineMatch(8, 8, moTicket));
			expandedMOTickets.addAll(combineMatch(8, 7, moTicket));
			break;
		case 8028:
			expandedMOTickets.addAll(combineMatch(8, 6, moTicket));
			break;
		case 8056:
			expandedMOTickets.addAll(combineMatch(8, 5, moTicket));
			break;
		case 8070:
			expandedMOTickets.addAll(combineMatch(8, 4, moTicket));
			break;
		case 8247:
			expandedMOTickets.addAll(combineMatch(8, 8, moTicket));
			expandedMOTickets.addAll(combineMatch(8, 7, moTicket));
			expandedMOTickets.addAll(combineMatch(8, 6, moTicket));
			expandedMOTickets.addAll(combineMatch(8, 5, moTicket));
			expandedMOTickets.addAll(combineMatch(8, 4, moTicket));
			expandedMOTickets.addAll(combineMatch(8, 3, moTicket));
			expandedMOTickets.addAll(combineMatch(8, 2, moTicket));
			break;
		default:
			throw new BonusDetailException("Wrong pass type: " + mnCode);
		}
		return expandedMOTickets;
	}

	private List<MatchOddsTicket> combineMatch(int m, int n, MatchOddsTicket moTicket) throws BonusDetailException {
		if (m != moTicket.getMatchOddsPairs().size()){
			throw new BonusDetailException("m is not equals to matches in ticket. m="+ m +",n="+n);
		}
		final List<MatchOddsPair> moPairs = moTicket.getMatchOddsPairs();
		final List<MatchOddsTicket> expandedMOT = new LinkedList<MatchOddsTicket>();
		final MatchOddsTicket originalMOTicket = moTicket;
		
		Visitor visitor = new Visitor() {
			@Override
			public void visit(int[] combination) {
				MatchOddsTicket t = new MatchOddsTicket();
				t.setPassType(originalMOTicket.getPassType());
				t.setOriginalPassType(originalMOTicket.getOriginalPassType());
				for (int index : combination) {
					t.getMatchOddsPairs().add(moPairs.get(index));
				}
				expandedMOT.add(t);
			}
			@Override
			public int getTotal() {
				return expandedMOT.size();
			}
		};
		Combination.generateWithAlgorithmL(m, n, visitor);
		
		return expandedMOT;
	}

	private int[] parseMN(String passType) {
		String[] mnParts = passType.split("@");
		int mn[] = new int[2];
		mn[0] = Integer.parseInt(mnParts[0]);
		mn[1] = Integer.parseInt(mnParts[1]);
		return mn;
	}

	private MatchOddsTicket ticketToMO(Ticket theTicket, List<BetMatch> betMatches, String originalPassType) throws BonusDetailException {
		String[] matches = parseMatches(theTicket);
		return parseMOTicket(matches, theTicket, betMatches, originalPassType);
	}

	private MatchOddsTicket parseMOTicket(String[] matches, Ticket theTicket, List<BetMatch> betMatches, String originalPassType) throws BonusDetailException {
		MatchOddsTicket moTicket = new MatchOddsTicket();
		moTicket.setPassType(theTicket.getPassTypeId());
		moTicket.setOriginalPassType(originalPassType);
		LinkedList<MatchOddsPair> moPairs = new LinkedList<MatchOddsPair>();
		for (int i=0; i<matches.length; i++) {
			String matchNo = matches[i];
			MatchOddsPair matchOddsPair = new MatchOddsPair();
			matchOddsPair.setMatch(matchNo);
			// 解析赔率
			matchOddsPair.getOdds().addAll(oddsFromMatch(matchNo, betMatches));
			moPairs.add(matchOddsPair);
		}
		moTicket.setMatchOddsPairs(moPairs);
		return moTicket;
	}

	private List<BigDecimal> oddsFromMatch(String matchNo, 
			List<BetMatch> betMatches) throws BonusDetailException {
		for (BetMatch match : betMatches) {
			if (match.getCode().substring(0, 4).equals(matchNo)){
				return oddStringToBigDecimals(match.getOdds());
			}
		}
		throw new BonusDetailException("Can not find odds for match: " + matchNo
				+ "\nBetMatches: " + betMatches);
	}

	private List<BigDecimal> oddStringToBigDecimals(String odds) {
		String[] oddsParts = odds.split(",");
		LinkedList<BigDecimal> oddsInBD = new LinkedList<BigDecimal>();
		for (String o : oddsParts) {
			oddsInBD.add(new BigDecimal(o));
		}
		return oddsInBD;
	}

	/** 
	 * 分解赔率串。赔率格式有：
	 * <ol>
	 * 	<li> 普通：3.500-2.950-3.100-4.350-2.850
	 *  <li> 多选：2.020-1.930-1.830-2.850A2.420-1.630-2.800A2.700-2.820A2.850-2.100
	 *  <li> 预设分值：1.77A1.77-1.77@181.5B195.5
	 *  <li> 混合：BRQSPF@7-047:[胜=1.980]/BRQSPF@7-049:[平=3.100]/BRQSPF@7-051:[胜=1.930]/SPF@7-053:[胜=2.560]/SPF@7-054:[负=2.400]/BRQSPF@7-055:[负=2.420]
	 * </ol>
	 * @param odds 赔率串
	 * @param playId 
	 * @return
	 */
//	private String[] splitOdds(String odds, String playId) {
//		PlayType playType = PlayType.valueOfLcPlayId(playId);
//		if (playType == PlayType.JCZQ_HH || playType == PlayType.JCLQ_HH){
//			return odds.split("/");
//		}else{
//			String[] parts = odds.split("@");
//			return parts[0].split("-");
//		}
//	}

	/** 
	 * 解析赔率。赔率格式串遵守的是大V彩内部赔率格式。
	 * 赔率格式有：
	 * <ol>
	 * 	<li> 普通：3.500
	 *  <li> 多选：2.850A2.420
	 *  <li> 混合：BRQSPF@7-047:[胜=1.980,平=2.0]
	 *  <li> 混合带预设值：SPF@4-003:[胜=1.3,负=1.1^+2.6]
	 * </ol>
	 * @param anOdds 一场比赛的赔率串
	 * @return 所有选项的赔率值。
	 * @throws BonusDetailException 解析失败
	 */
//	private List<BigDecimal> parseOdds(String anOdds, String playId) throws BonusDetailException {
//		PlayType playType = PlayType.valueOfLcPlayId(playId);
//		if (playType == PlayType.JCZQ_HH || playType == PlayType.JCLQ_HH){
//			Pattern pattern = Pattern.compile(".*\\[(.*)\\^?.*?\\]");
//			Matcher matcher = pattern.matcher(anOdds);
//			if (matcher.matches()){
//				String oddsPart = matcher.group(1);
//				String[] allOption = oddsPart.split(",");
//				LinkedList<BigDecimal> bigDecimalOdds = new LinkedList<BigDecimal>();
//				for (String option : allOption) {
//					String[] parts = option.split("=");
//					bigDecimalOdds.add(new BigDecimal(parts[1]));
//				}
//				return bigDecimalOdds;
//			}else{
//				throw new BonusDetailException("Odds of HH is invalid: " + anOdds);
//			}
//		}else{
//			String[] odds = anOdds.split("A");
//			return toBigDecimals(odds);
//		}
//	}

//	private List<BigDecimal> toBigDecimals(String[] odds) {
//		LinkedList<BigDecimal> bigDecimalOdds = new LinkedList<BigDecimal>();
//		for (String anOdds : odds) {
//			bigDecimalOdds.add(new BigDecimal(anOdds));
//		}
//		return bigDecimalOdds;
//	}

	/**
	 * 解析投注的比赛场次，场次号为4位数字。混合投注格式也天然支持，因为混合格式为：<br/>
	 * 701510:SPF-100131:BRQSPF-1002346:JQS
	 */
	private String[] parseMatches(Ticket theTicket){
		String code = theTicket.getCode();
		String[] matches = code.split("-");
		for (int i=0; i<matches.length; i++) {
			String aMatch = matches[i];
			matches[i] = aMatch.substring(0, 4);
		}
		return matches;
	}
	
	/** 
	 * 计算命中场数为hit的奖金明细.
	 * @param hit 假设命中的场次数。
	 * @param tickets 已经展开为m@1形式的票。
	 */
	private SupposeHit computeSupposeHit(int hit, List<MatchOddsTicket> tickets, 
			List<String> minMatchList, List<String> maxMatchList) {
		List<String> minHitMatches = minMatchList.subList(0, hit);
		List<MatchOddsTicket> hitMinTickets = filterHitTickets(tickets, minHitMatches);
		List<String> maxHitMatches = maxMatchList.subList(0, hit);
		List<MatchOddsTicket> hitMaxTickets = filterHitTickets(tickets, maxHitMatches);
		SupposeHit hitDetail = createSupposeHit(hit, hitMinTickets, hitMaxTickets); 
		return hitDetail;
	}

	private SupposeHit createSupposeHit(int hit, List<MatchOddsTicket> hitMinTickets, List<MatchOddsTicket> hitMaxTickets) {
		SupposeHit supposeHit = new SupposeHit();
		supposeHit.setHitCount(hit);
		
		boolean isMin = true;
		supposeHit.setMinBonus(sumMinBonus(hitMinTickets));
		supposeHit.setMinDetails(winDetailsFromMOTicket(hitMinTickets, isMin));
		
		isMin = false;
		supposeHit.setMaxBonus(sumMaxBonus(hitMaxTickets));
		supposeHit.setMaxDetails(winDetailsFromMOTicket(hitMaxTickets, isMin));
		
		return supposeHit;
	}

	private BigDecimal sumMaxBonus(List<MatchOddsTicket> hitMaxTickets) {
		BigDecimal bonus = BigDecimal.ZERO;
		for (MatchOddsTicket ticket : hitMaxTickets){
			bonus = bonus.add(ticket.getMaxBonus());
		}
		return bonus;
	}

	private BigDecimal sumMinBonus(List<MatchOddsTicket> hitMinTickets) {
		BigDecimal bonus = BigDecimal.ZERO;
		for (MatchOddsTicket ticket : hitMinTickets){
			bonus = bonus.add(ticket.getMinBonus());
		}
		return bonus;
	}

	private List<WinTicketDetail> winDetailsFromMOTicket(List<MatchOddsTicket> moTicketList, boolean isMin) {
		List<WinTicketDetail> wtList = new LinkedList<WinTicketDetail>();
		for (MatchOddsTicket moTicket : moTicketList) {
			WinTicketDetail winDetail = new WinTicketDetail();
			winDetail.setPassType(moTicket.getOriginalPassType());
			LinkedList<WinMatch> winMatches = new LinkedList<WinMatch>();
			for (MatchOddsPair moPair : moTicket.getMatchOddsPairs()){
				WinMatch wm = new WinMatch();
				wm.setMatchId(moPair.getMatch());
				List<BigDecimal> oddsList = moPair.getOdds();
				BigDecimal odds = isMin ? minOdds(oddsList) : maxOdds(oddsList);
				wm.setBonus(odds);
				winMatches.add(wm);
			}
			winDetail.setWinMatches(winMatches);
			wtList.add(winDetail);
		}
		return wtList;
	}

	/**
	 * 计算每张票的所有最大最小奖金，会乘以2.
	 */
	private void computeMinMaxBonus(List<MatchOddsTicket> hitTickets) {
		for (MatchOddsTicket t : hitTickets) {
			BigDecimal min = BigDecimal.ONE;
			BigDecimal max = BigDecimal.ONE;
			List<MatchOddsPair> moPairs = t.getMatchOddsPairs();
			for (MatchOddsPair mo : moPairs) {
				min = min.multiply(minOdds(mo.getOdds()));
				max = max.multiply(maxOdds(mo.getOdds()));
			}
			t.setMinBonus(min.multiply(BigDecimal.valueOf(2)));
			t.setMaxBonus(max.multiply(BigDecimal.valueOf(2)));
		}
	}

	private BigDecimal maxOdds(List<BigDecimal> odds) {
		BigDecimal max = odds.get(0);
		for (BigDecimal o : odds){
			if (o.compareTo(max)==1){
				max = o;
			}
		}
		return max;
	}

	private BigDecimal minOdds(List<BigDecimal> odds) {
		BigDecimal min = odds.get(0);
		for (BigDecimal o : odds){
			if (o.compareTo(min)==-1){
				min = o;
			}
		}
		return min;
	}

	/**
	 * 只返回比赛全部在 hitMatchList 中的票。
	 */
	private List<MatchOddsTicket> filterHitTickets(List<MatchOddsTicket> tickets, 
			List<String> hitMatchList) {
		LinkedList<MatchOddsTicket> filtered = new LinkedList<MatchOddsTicket>();
		for (MatchOddsTicket ticket : tickets) {
			if (matchesOfTicketAllInList(hitMatchList, ticket)){
				filtered.add(ticket);
			}
		}
		return filtered;
	}

	private boolean matchesOfTicketAllInList(List<String> hitMatchList,
			MatchOddsTicket ticket) {
		for (MatchOddsPair pair : ticket.getMatchOddsPairs()){
			if (!hitMatchList.contains(pair.getMatch())){
				return false;
			}
		}
		return true;
	}

	private int maxHitCount(BetScheme unitScheme) {
		return unitScheme.getMatchs().size();
	}

	private int minHitCount(BetScheme unitScheme) {
//		String pass = unitScheme.getPassTypes().get(0);
//		String[] mn = pass.split("@");
//		return Integer.parseInt(mn[0]);
		return 2;
	}

	/**
	 * 生成单倍、单一过关方式的方案。
	 * @param scheme 要计算奖金明细的原始方案
	 * @param passType 过关类型
	 * @return
	 */
	private BetScheme composeUnitScheme(BetScheme scheme, String passType) {
		BetScheme unitScheme = new BetScheme();
		String [] ignoredProperties = new String[]{"afterTaxBonusReturnRatio", "maxBonusReturnRatio"};
		BeanUtils.copyProperties(scheme, unitScheme, ignoredProperties);
		unitScheme.setPassTypeIds(passType);
		unitScheme.setPassTypes(Arrays.asList(passType));
		return unitScheme;
	}

}
