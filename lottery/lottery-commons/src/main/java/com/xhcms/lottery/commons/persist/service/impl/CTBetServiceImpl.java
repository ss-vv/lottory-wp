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
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTBetRequest;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.dao.CTBetContentDao;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.CTBetContentPO;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.CTBetService;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;

/**
 * 传统足球投注服务。
 * 未使用
 * @author Yang Bo
 */
public class CTBetServiceImpl implements CTBetService {

	@Autowired
	private BetResolver betResolver;
	
	@Autowired
	private IssueInfoDao issueInfoDao;
	
	@Autowired
	private CTBetContentDao cTBetContentDao;
	
	@Override
	@Transactional
	public BetScheme prepareScheme(CTBetRequest betRequest) throws BetException {
		BetScheme scheme = new BetScheme();
		scheme.setMultiple(betRequest.getMultiple());
		scheme.setCtBetRequest(betRequest);
		scheme.setLotteryId(betRequest.getLotteryId());
		scheme.setPlayId(betRequest.getPlayType().getPlayId());
		// 设置方案的投注内容
		prepareBetContent(betRequest, scheme);
		// 停售时间
		prepareOfftime(betRequest, scheme);
		ensureCurrentTimeBeforeOfftime(scheme);
		// 过关方式
		preparePasstype(betRequest, scheme);
		// 拆票
		Bet bet = betResolver.resolve(scheme);
		// 创建时间
		scheme.setCreatedTime(new Date());
		// 理论奖金数
		scheme.setMaxBonus(BigDecimal.ZERO);
        scheme.setTotalAmount(bet.getNote() * 2);
        scheme.setBetNote(bet.getNote());
        // 倍数
        scheme.setMultiple(betRequest.getMultiple());
		return scheme;
	}
	
	private void prepareBetContent(CTBetRequest betRequest, BetScheme scheme) {
		scheme.setCtBetContents(betRequest.makeBetContents());
	}

	private void prepareOfftime(CTBetRequest betRequest, BetScheme scheme) {
		String issueNumber = betRequest.getIssue();
		IssueInfoPO issuePO = issueInfoDao.findByLotteryIdAndIssueNumber(
				LotteryId.CTZC.toString(), issueNumber);
		scheme.setOfftime(issuePO.getCloseTime());
	}

	private void ensureCurrentTimeBeforeOfftime(BetScheme scheme) throws BetException {
		if (new Date().before(scheme.getOfftime())){
			throw new BetException("Issue offtime has passed.", AppCode.INVALID_ISSUE_OFFTIME);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void preparePasstype(CTBetRequest betRequest, BetScheme scheme) {
        scheme.setPassTypes(Collections.EMPTY_LIST);
	}

	@Override
	public void confirmScheme(BetScheme scheme) throws BetException {
		// TODO Auto-generated method stub
	}

	public BetScheme buyScheme(BetScheme scheme) throws BetException {
		// TODO
		return null;
	}
	
	/**
	 * 用投注请求、期信息创建一个投注方案。
	 * TODO: 可以重构，抽出共用代码。
	 * 
	 * @param userId
	 * @param betContent
	 * @param issueInfo
	 * @return
	 * @throws BetException
	 */
	@SuppressWarnings("unchecked")
	public BetScheme makeBetScheme(long userId, CTBetRequest betContent, IssueInfoPO issueInfo) throws BetException {
		String lotteryId = betContent.getLotteryId();
		BetScheme scheme = new BetScheme();
		scheme.setFollowSchemePrivacy(EntityStatus.PRIVACY_PUBLIC);
		scheme.setSponsorId(userId);
        scheme.setAfterTaxBonus(Constants.ZERO);
        scheme.setExpectBonus(Constants.ZERO);
        scheme.setPreTaxBonus(Constants.ZERO);
        scheme.setShowScheme(Constants.NOT_SHOW_SCHEME);
        scheme.setFollowedRatio(0);
        scheme.setFollowedSchemeId(-1L);
        scheme.setRecommendation(0);
		scheme.setLotteryId(lotteryId);
		scheme.setPlayId(betContent.getPlayType().getPlayId());
		scheme.setMatchNumber(0);
		scheme.setMatchs(Collections.EMPTY_LIST);
		scheme.setMultiple(betContent.getMultiple());
		scheme.setOfftime(issueInfo.getCloseTime());
		scheme.setPassTypes(Collections.EMPTY_LIST);
		scheme.setPassTypeIds(StringUtils.EMPTY);
		scheme.setCreatedTime(new Date());
		List<CTBetContent> ctBetContents = makeCtBetContent(betContent);
		scheme.setCtBetContents(ctBetContents);
		scheme.setCtBetRequest(betContent);
		return scheme;
	}

	// 生成传统足彩投注内容列表
	private List<CTBetContent> makeCtBetContent(CTBetRequest betContent) {
		LinkedList<CTBetContent> ctBets = new LinkedList<CTBetContent>();
		for (String oneBet : betContent.getBetContents()){
			CTBetContent ctBet = new CTBetContent();
			ctBet.setCode(oneBet);
			ctBet.setLotteryId(betContent.getLotteryId());
			ctBet.setPlayId(betContent.getPlayType().getPlayId());
			ctBet.setIssueNumber(betContent.getIssue());
			ctBet.setChooseType(betContent.getChooseType().getIndex());
			ctBets.add(ctBet);
		}
		return ctBets;
	}
	
	@Override
	@Transactional
	public List<CTBetContent> findCtBetContent(Long schemeId) {
		LinkedList<CTBetContent> bets = new LinkedList<CTBetContent>();
		List<CTBetContentPO> betsPO = cTBetContentDao.findCtBetContent(schemeId);
		for (CTBetContentPO po : betsPO) {
			CTBetContent bet = new CTBetContent();
			BeanUtils.copyProperties(po, bet);
			bets.add(bet);
		}
		return bets;
	}

}
