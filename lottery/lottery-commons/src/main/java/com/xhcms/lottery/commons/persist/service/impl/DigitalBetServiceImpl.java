package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.HfBetContentDao;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.HfBetContentPO;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.commons.persist.service.PhantomService;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.POUtils;

/**
 * 数字彩投注服务。
 * 
 * @author Yang Bo
 */
public class DigitalBetServiceImpl extends BetSchemeBaseServiceImpl implements DigitalBetService {
	// private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private BetResolver betResolver;
    
    @Autowired
    private IssueInfoDao issueInfoDao;
    
	@Autowired
	private HfBetContentDao hfBetContentDao;
	
	@Autowired
	private BetPartnerDao betPartnerDao;
	
	@Autowired
	private PhantomService phantomService;
	
	/**
	 * 直接投注。
	 */
	@Override
	@Transactional
	public BetScheme bet(DigitalBetRequest betRequest) throws BetException {
		BetScheme scheme = prepareBet(betRequest);
		return betConfirm(scheme);
	}

	// 获取一期的信息
	private IssueInfoPO getIssueInfo(String lotteryId, String issueNumber) throws BetException {
		IssueInfoPO issueInfo = issueInfoDao.findByLotteryIdAndIssueNumber(lotteryId, issueNumber);
		if (issueInfo == null){
			throw new BetException("Can not find issue by lotteryId=" + 
					lotteryId + ", issueNumber=" + issueNumber,
					AppCode.INVALID_ISSUE_NUMBER);
		}
		return issueInfo;
	}

	/**
	 * 验证投注是否有效。
	 * <li>期号是否为开售，时间是否有效(先不检查了)。
	 * <li>投注的钱数是否有效。
	 * 
	 * @param betContent 投注内容
	 * @throws BetException 方案不合法
	 */
	private void validateBet(BetScheme scheme, IssueInfoPO issueInfo) throws BetException {
		if(scheme.getTotalAmount() <= 0 || scheme.getBuyAmount() <= 0 || 
		           scheme.getBuyAmount() > scheme.getTotalAmount()) {
			throw new BetException("Bet Scheme amounts is invalid!", AppCode.INVALID_AMOUNT);
		}
		// 添加时间检查
		if (issueInfo.getZMCloseTime().compareTo(new Date()) < 0){
			throw new BetException("Bet Scheme contains closed issue: "+issueInfo.getIssueNumber(), 
					AppCode.INVALID_ISSUE_OFFTIME);
		}
	}
	
	@SuppressWarnings("unchecked")
	public BetScheme makeBetScheme(DigitalBetRequest betRequest, IssueInfoPO issueInfo) throws BetException {
		String lotteryId = betRequest.getLotteryId();
		BetScheme scheme = new BetScheme();
		scheme.setIssueNumber(issueInfo.getIssueNumber());
		scheme.setFollowSchemePrivacy(EntityStatus.PRIVACY_PUBLIC);
		scheme.setSponsorId(betRequest.getUserId());
        scheme.setAfterTaxBonus(Constants.ZERO);
        scheme.setExpectBonus(Constants.ZERO);
        scheme.setPreTaxBonus(Constants.ZERO);
        scheme.setShowScheme(Constants.NOT_SHOW_SCHEME);
        scheme.setFollowedRatio(0);
        scheme.setFollowedSchemeId(-1L);
        scheme.setRecommendation(0);
		scheme.setLotteryId(lotteryId);
		scheme.setPlayId(betRequest.getPlayType().getPlayId());
		scheme.setMatchNumber(0);
		scheme.setMatchs(Collections.EMPTY_LIST);
		scheme.setMultiple(betRequest.getMultiple());
		scheme.setOfftime(issueInfo.getStopTimeForUser());
		scheme.setPassTypes(Collections.EMPTY_LIST);
		scheme.setPassTypeIds(StringUtils.EMPTY);
		scheme.setCreatedTime(new Date());
		List<DigitalBetContent> hfBetContent = makeBetContent(betRequest);
		scheme.setDigitalBetContents(hfBetContent);
		scheme.setDigitalBetRequest(betRequest);
		scheme.setChannel(betRequest.getChannel());
		scheme.setPartner(betRequest.getPartner());
		return scheme;
	}

	// 生成高频彩投注内容列表，一个 HFBetContent 对应一条投注选项。
	private List<DigitalBetContent> makeBetContent(DigitalBetRequest betRequest) {
		LinkedList<DigitalBetContent> hfBets = new LinkedList<DigitalBetContent>();
		List<PlayType> playTypes = betRequest.getPlayTypeList();
		int index = 0;
		for (String oneBet : betRequest.getBetContents()){
			DigitalBetContent hfBet = new DigitalBetContent();
			hfBet.setCode(oneBet);
			hfBet.setLotteryId(betRequest.getLotteryId());
			if (playTypes != null && playTypes.size()>0){
				hfBet.setPlayId(playTypes.get(index).getPlayId());
			}else{
				hfBet.setPlayId(betRequest.getPlayType().getPlayId());
			}
			hfBet.setIssueNumber(betRequest.getIssue());
			ChooseType chooseType = betRequest.getChooseType();
			if (chooseType != null){
				hfBet.setChooseType(chooseType.getIndex());
			}
			hfBets.add(hfBet);
			index++;
		}
		return hfBets;
	}

	private BetScheme saveBet(long userId, BetScheme scheme, List<Ticket> tickets, IssueInfoPO issueInfo) {
		UserPO userPO = userDao.get(userId);
		BetSchemePO spo = saveBetScheme(userPO, scheme, tickets.size());
        saveTickets(scheme, tickets);
        saveDigitalBetContent(scheme, issueInfo);
        BetPartnerPO partnerPO = saveBetPartners(spo, userPO.getUsername());
        frozenMoney(spo, partnerPO);
        phantomService.onBetScheme(scheme);
        return scheme;
	}
	
	private void frozenMoney(BetSchemePO schemePO, BetPartnerPO partnerPO) {
        BigDecimal frozenAmount = new BigDecimal(schemePO.getTotalAmount());
        accountService.betFrozen(schemePO.getSponsorId(), frozenAmount, partnerPO.getId());
	}

	/**
	 *  保存投注单
	 */
	private BetSchemePO saveBetScheme(UserPO userPO, BetScheme scheme, int ticketsCount) {
        BetSchemePO spo = new BetSchemePO();
        BeanUtils.copyProperties(scheme, spo);
        spo.setSponsorId(userPO.getId());
        spo.setSponsor(userPO.getUsername());
        spo.setType(EntityType.BETTING_ALONE);
        spo.setCreatedTime(scheme.getCreatedTime());
        spo.setPrivacy(EntityStatus.PRIVACY_SECRET);
        spo.setWinNote(0);
        spo.setTicketNote(0);
        spo.setCancelNote(0);
        spo.setTicketCount(ticketsCount);
        spo.setSaleStatus(EntityStatus.SCHEME_STOP);
        spo.setStatus(EntityStatus.TICKET_ALLOW_BUY);
        spo.setPartnerCount(1);
        spo.setLotteryId(playDao.get(scheme.getPlayId()).getLotteryId());
        phantomService.updateSchemeStatus(spo);
        betSchemeDao.save(spo);
        scheme.setId(spo.getId());
        return spo;
	}

	/**
	 *  保存彩票信息
	 */
	private void saveTickets(BetScheme scheme, List<Ticket> tickets) {
		for(Ticket t: tickets){            
            ticketDao.save(copyToTicketPO(t, scheme.getId(), scheme.getCreatedTime()));
        }	
	}

	private BetPartnerPO saveBetPartners(BetSchemePO scheme, String userName) {
        BetPartnerPO ppo = new BetPartnerPO();
        ppo.setScheme(scheme);
        ppo.setUserId(scheme.getSponsorId());
        ppo.setUsername(userName);
        ppo.setBetAmount(scheme.getTotalAmount());
        ppo.setCreatedTime(scheme.getCreatedTime());
        betPartnerDao.save(ppo);
        return ppo;
	}

	@Override
	@Transactional
	public List<Ticket> listTicketsOfScheme(long schemeId) {
		int allStatus = -1;
		List<TicketPO> ticketsPO = this.ticketDao.findByScheme(schemeId, allStatus);
		LinkedList<Ticket> tickets = new LinkedList<Ticket>();
		for ( TicketPO ticketPO : ticketsPO ) {
			Ticket ticketDTO = new Ticket();
			BeanUtils.copyProperties(ticketPO, ticketDTO);
			tickets.add(ticketDTO);
		}
		return tickets;
	}

	@Override
	@Transactional
	public List<DigitalBetContent> findHfBetContent(Long schemeId) {
		LinkedList<DigitalBetContent> bets = new LinkedList<DigitalBetContent>();
		List<HfBetContentPO> betsPO = hfBetContentDao.findHfBetContent(schemeId);
		for (HfBetContentPO po : betsPO) {
			DigitalBetContent bet = new DigitalBetContent();
			BeanUtils.copyProperties(po, bet);
			bets.add(bet);
		}
		return bets;
	}

	@Override
	@Transactional(readOnly=true)
	public List<BetPartner> findBetPartners(long schemeId) {
		List<BetPartnerPO> partners = betPartnerDao.findBySchemeId(schemeId);
		return POUtils.copyBeans(partners, BetPartner.class);
	}

	/**
	 * 准备投注方案。
	 */
	@Override
	@Transactional(readOnly=true)
	public BetScheme prepareBet(DigitalBetRequest betRequest) throws BetException {
		String lotteryId = betRequest.getLotteryId();
		String issueNumber = betRequest.getIssue();
		IssueInfoPO issueInfo = getIssueInfo(lotteryId, issueNumber);
		BetScheme scheme = makeBetScheme(betRequest, issueInfo);
		Bet bet = betResolver.resolve(scheme);
	    scheme.setMaxBonus(new BigDecimal(bet.getMaxBonus()));
	    scheme.setBetNote(bet.getNote());
	    scheme.setBuyAmount(bet.getNote() * 2);
	    scheme.setTotalAmount(bet.getNote() * 2);
	    validateBet(scheme, issueInfo);
	    return scheme;
	}

	/**
	 * 确认投注。
	 */
	@Override
	@Transactional
	public BetScheme betConfirm(BetScheme scheme) throws BetException {
		Bet bet = betResolver.resolve(scheme);
		String lotteryId = scheme.getLotteryId();
		String issueNumber = scheme.getIssueNumber();
		IssueInfoPO issueInfo = getIssueInfo(lotteryId, issueNumber);
		BetScheme betScheme = saveBet(scheme.getSponsorId(), scheme, bet.getTickets(), issueInfo);
		
		return betScheme;
	}

	
	/**
	 * 拷贝数字彩投注方案给指定用户。
	 * @return null 如果拷贝失败
	 */
	@Override
	@Transactional
	public BetScheme copySchemeToUser(Long userId, BetScheme srcScheme,
			int multiple) {
		
		List<DigitalBetContent> digitBetContentsCopy = copyDigitalBetContents(srcScheme);
		BetScheme copyScheme 	  = copyScheme(userId, srcScheme, digitBetContentsCopy, multiple);
		List<TicketPO> srcTickets = ticketDao.findByScheme(srcScheme.getId(), -1);
    	List<Ticket> ticketCopy   = copyTickets(srcTickets, multiple);
		updateSchemeByTickets(copyScheme, srcScheme, ticketCopy, multiple);
		try{
			createBetScheme(copyScheme, ticketCopy);
		}catch(BetException be) {
			return null;
		}
        return copyScheme;
	}

	
	List<DigitalBetContent> copyDigitalBetContents ( BetScheme srcScheme ) {
    	List<DigitalBetContent> digitBetContents = srcScheme.getDigitalBetContents();
    	LinkedList<DigitalBetContent> digitBetContentsCopy = new LinkedList<DigitalBetContent>();
    	for (DigitalBetContent dbc : digitBetContents){
    		DigitalBetContent copy = new DigitalBetContent();
    		BeanUtils.copyProperties(dbc, copy);
    		copy.setSchemeId(0L);
    		digitBetContentsCopy.add(copy);
    	}
    	return digitBetContentsCopy;
	}

	
	private BetScheme copyScheme(Long userId, BetScheme srcScheme, List<DigitalBetContent> digitBetContentsCopy, int multiple) {
		
    	BetScheme copyScheme = new BetScheme();
    	BeanUtils.copyProperties(srcScheme, copyScheme);
    	copyScheme.setId(0L);
    	copyScheme.setShowScheme(Constants.NOT_SHOW_SCHEME);
    	copyScheme.setFollowedSchemeId(0L);
    	copyScheme.setSponsorId(userId);
    	UserPO user = userDao.get(userId);
    	copyScheme.setSponsor(user.getUsername());
        copyScheme.setMatchNumber(0);
        copyScheme.setDigitalBetContents(digitBetContentsCopy);
        copyScheme.setMultiple(multiple);
        copyScheme.setCreatedTime(new Date());
        return copyScheme;
	}

	
	private void updateSchemeByTickets(BetScheme copyScheme, BetScheme srcScheme, List<Ticket> ticketCopy, int multiple) {
    	int totalAmount = 0; 	// 方案总金额
    	int buyAmount = 0;	 	// 购买金额
    	int betNote = 0;	 	// 方案注数, 注意，不能看原始方案倍数，而要看每张票自己的倍数。
    	
    	for (Ticket tc : ticketCopy){
    		totalAmount += tc.getMoney();
    		betNote += tc.getNote();
    	}
    	buyAmount = totalAmount;
        
        double maxBonus = (srcScheme.getMaxBonus().doubleValue() / srcScheme.getMultiple() ) * multiple;
        double expectBonus = (srcScheme.getExpectBonus().doubleValue() / srcScheme.getMultiple()) * multiple;
        copyScheme.setMaxBonus(new BigDecimal(maxBonus));
        copyScheme.setExpectBonus(new BigDecimal(expectBonus));

        copyScheme.setTotalAmount(totalAmount);
        copyScheme.setBuyAmount(buyAmount);
        copyScheme.setBetNote(betNote);

        copyScheme.setPreTaxBonus(BigDecimal.ZERO);
        copyScheme.setAfterTaxBonus(BigDecimal.ZERO);
	}

	
	private void createBetScheme(BetScheme copyScheme, List<Ticket> ticketCopy) throws BetException {
		String lotteryId = copyScheme.getLotteryId();
		String issueNumber = copyScheme.getIssueNumber();
		IssueInfoPO issueInfo = getIssueInfo(lotteryId, issueNumber);
		saveBet(copyScheme.getSponsorId(), copyScheme, ticketCopy, issueInfo);
	}

	/**
	 * 单式投注:  SSQ_DS  格式：01,02,16,19,27,31|01
	 * 复式投注:  SSQ_FS  格式：01,02,03,16,19,21,23,25,33|01,09
	 * 胆拖投注:  SSQ_DT  格式：01,02,03@04,05,06,07,08|01,09, 胆码最少1个,最多5个,拖码最多20个,必须是复式方案
	 */
	@Override
	public PlayType deduceSSQPlayType(String bet) {
		if (bet.contains("@")) {
			return PlayType.SSQ_DT;
		}
		String[] redBlue = bet.split("\\|");
		String red = redBlue[0];
		String blue = redBlue[1];
		if (blue.split(",").length>1 || red.split(",").length>6) {
			return PlayType.SSQ_FS;
		}
		return PlayType.SSQ_DS;
	}
}
