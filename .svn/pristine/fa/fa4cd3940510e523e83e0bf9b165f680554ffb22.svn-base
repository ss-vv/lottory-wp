package com.xhcms.lottery.mc.persist.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List; 
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.lang.OddAndActualOdd;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.PsDao;
import com.xhcms.lottery.commons.persist.dao.PuDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.PsPO;
import com.xhcms.lottery.commons.persist.entity.PuPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.service.BetOddsService;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.commons.persist.service.PhantomService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.mc.persist.service.TicketService;

@Transactional
public class PhantomServiceImpl implements PhantomService {

	@Autowired
	private PsDao psDao;
	@Autowired
	private PuDao puDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private BetSchemeService betService;
	@Autowired
	private DigitalBetService digitalBetService;
	
	@Autowired
	private BetOddsService betOddsService;

	private long suid = -1; // for cache

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Transactional(readOnly = true)
	public boolean isDoll(long uid, PlayType pt) {
		if (!isValidPlayType(pt)) {
			return false;
		}
		PuPO pu = puDao.findByUid(uid);
		if (pu != null && pu.getUt() == PuPO.DOLL) {
			return true;
		}
		return false;
	}

	@Override
	public BetScheme onBetScheme(BetScheme scheme) {
		PlayType pt = PlayType.valueOfLcPlayId(scheme.getPlayId());
		if (!isValidPlayType(pt)) {
			return scheme;
		}
		
		boolean isGenPhantomScheme = isGeneratePhantomScheme(scheme.getType(), scheme.getLotteryId());
		if (isDoll(scheme.getSponsorId(), pt) && isGenPhantomScheme) {
			BetSchemeBaseService betServiceForCopy = chooseBetServiceForCopy(scheme);
			BetScheme shadow = betServiceForCopy.copySchemeToUser(getSUID(),
					scheme, 1);
			if (shadow == null) {
				logger.error("Generate phantom bet-scheme failed!");
			} else {
				psDao.addDollShadowPair(scheme.getId(), shadow.getId());
			}
		}
		return scheme;
	}

	private BetSchemeBaseService chooseBetServiceForCopy(BetScheme scheme) {
		PlayType playType = PlayType.valueOfLcPlayId(scheme.getPlayId());
		if (playType.isJX11()) {
			return digitalBetService;
		} else {
			return betService;
		}
	}

	/**
	 * 交易完成时，如果shadow方案状态为交易成功，执行“拷贝操作”，否则 doll 方案不能出票，自动撤单。
	 * 对于shadown方案，获取对应doll方案的票，设置他们的状态为对应s-ticket的交易状态，拷贝票的赔率；
	 * 对doll方案执行改方案状态的动作，扣款留给da的deduct task。
	 */
	@Override
	public void onSchemeBuyComplete(BetSchemePO scheme) {
		logger.debug("onSchemeBuyComplete called.");

		PlayType pt = PlayType.valueOfLcPlayId(scheme.getPlayId());
		if (!isValidPlayType(pt)) {
			return;
		}

		if (isShadow(scheme.getSponsorId())) {
			Long sid = scheme.getId();
			int shadowStatus = scheme.getStatus();
			BetSchemePO doll = getDollByShadow(sid);
			if (shadowStatus == EntityStatus.TICKET_BUY_SUCCESS) {
				List<TicketPO> shadowTickets = ticketDao.findByScheme(sid, -1);
				Map<Long, Ticket> idTicketMap = copyShadowTicketsToDoll(sid,
						shadowTickets);
				if (idTicketMap.size() > 0) {
					changeStatusToRequired(doll);
					ticketService.check(idTicketMap); // 记录ticket出票信息，修改方案状态
				} else {
					logger.error("can not copy shadow tickets to doll!");
				}
			} else if (shadowStatus == EntityStatus.TICKET_BUY_FAIL) {
				logger.info("cancel doll({}) by shadow({}).", doll.getId(), sid);
				cancelBetScheme(doll.getId());
			} else {
				logger.error(
						"onSchemeBuyComplete get shadow({}) in transient state({}).",
						sid, shadowStatus);
			}
		}
	}

	/**
	 * 修改方案和票的状态为“已请求出票”
	 * 
	 * @param doll
	 */
	@Transactional
	private void changeStatusToRequired(BetSchemePO doll) {
		doll.setStatus(EntityStatus.TICKET_REQUIRED);
		List<TicketPO> tickets = ticketDao.findByScheme(doll.getId(), -1);
		for (TicketPO t : tickets) {
			t.setStatus(EntityStatus.TICKET_REQUIRED);
		}
	}

	/**
	 * Doll Ticket 的状态和 shadow 不一样，改为"已出票"。
	 * 
	 * @param sid
	 * @param shadowTickets
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<Long, Ticket> copyShadowTicketsToDoll(long sid,
			List<TicketPO> shadowTickets) {
		BetSchemePO doll = getDollByShadow(sid);
		if (doll == null) {
			logger.error("can not find d-scheme by s-scheme: {}", sid);
			return Collections.EMPTY_MAP;
		}
		HashMap<Long, Ticket> dollIdTickets = new HashMap<Long, Ticket>();
		List<TicketPO> dtickets = ticketDao.findByScheme(doll.getId(), -1);
		for (TicketPO dt : dtickets) {
			try {
				TicketPO st = findShadowTicket(shadowTickets, dt);
				Ticket dtCopy = new Ticket();
				BeanUtils.copyProperties(dt, dtCopy);
				// 模拟接口的值
				dtCopy.setPlayId(st.getPlayId());
				dtCopy.setLotteryId(PlayType.valueOfLcPlayId(st.getPlayId())
						.getLotteryId());
				dtCopy.setActualOdds(st.getActualOdds());
				dtCopy.setOdds(st.getOdds());
				dtCopy.setActualStatus(st.getActualStatus());
				dtCopy.setStatus(st.getStatus());
				// 设置一个唯一的随机值
				dtCopy.setNumber(randTicketNum());
				dtCopy.setPlatformId(RandomUtils.nextLong());
				dollIdTickets.put(dtCopy.getId(), dtCopy);
			} catch (ServiceException e) {
				logger.error("can not find s-ticket for d-ticket: {}",
						dt.getId());
			}
		}
		return dollIdTickets;
	}

	private String randTicketNum() {
		StringBuilder b = new StringBuilder("20");
		while (b.length() < 20) {
			b.append(RandomUtils.nextLong());
		}
		b.setLength(20);
		return b.toString();
	}

	private void cancelBetScheme(Long schemeId) {
		BetSchemePO spo = betSchemeDao.get(schemeId);
		if (spo == null) {
			logger.error("d scheme {} not exists.", schemeId);
			return;
		}
		if (spo.getTicketNote() != 0) {
			logger.error("d scheme {} has successfully sended notes > 0: {}",
					schemeId, spo.getTicketNote());
			return;
		}

		if (spo.getStatus() != EntityStatus.TICKET_REQUIRED
				&& spo.getStatus() != EntityStatus.TICKET_ALLOW_BUY
				&& spo.getStatus() != EntityStatus.TICKET_INIT) {
			logger.error("d scheme status invalid: {}" + spo.getStatus());
		}

		List<Long> sids = new ArrayList<Long>();
		sids.add(schemeId);
		// 修改票状态
		ticketDao.cancelTicketsBySchemeIds(spo.getStatus(), sids);
		// 修改方案状态
		spo.setStatus(EntityStatus.TICKET_SCHEME_CANCEL);
		// 设置投注失败注数
		spo.setCancelNote(spo.getBetNote());
		// 如果是合买，需要将方案置为停止（满员未扣款）
		if (spo.getType() == EntityType.BETTING_PARTNER) {
			spo.setSaleStatus(EntityStatus.SCHEME_STOP);
		}
	}

	@Override
	public void onSchemePrized(BetSchemePO scheme) {
		PlayType pt = PlayType.valueOfLcPlayId(scheme.getPlayId());
		if (!isValidPlayType(pt)) {
			return;
		}

		if (!isShadow(scheme.getSponsorId())) {
			return;
		}
		try {
			Map<Long, Ticket> dollTickets = copyDollTicketsFromPrizedShadow(scheme
					.getId());
			ticketService.prize(dollTickets);
		} catch (Exception e) {
			logger.error("can not make d tickets from s.", e);
		}
	}

	private Map<Long, Ticket> copyDollTicketsFromPrizedShadow(long shadowId)
			throws ServiceException {
		List<TicketPO> shadowTickets = ticketDao.findByScheme(shadowId, -1);
		PsPO ps = psDao.findDollByShadow(shadowId);
		BetSchemePO doll = betSchemeDao.get(ps.getDid());
		List<TicketPO> dollTickets = ticketDao.findByScheme(doll.getId(), -1);

		HashMap<Long, Ticket> idTicketMap = new HashMap<Long, Ticket>();

		for (TicketPO dtp : dollTickets) {
			Ticket dTicket = new Ticket();
			TicketPO stp = findShadowTicket(shadowTickets, dtp);
			BigDecimal mul = new BigDecimal(dtp.getMultiple());

			BeanUtils.copyProperties(dtp, dTicket);
			dTicket.setStatus(stp.getStatus());
			dTicket.setActualStatus(stp.getActualStatus());
			dTicket.setPreTaxBonus(stp.getPreTaxBonus().multiply(mul));
			dTicket.setAfterTaxBonus(stp.getAfterTaxBonus().multiply(mul));
			idTicketMap.put(dTicket.getId(), dTicket);
		}
		return idTicketMap;
	}

	private TicketPO findShadowTicket(List<TicketPO> shadowTickets,
			TicketPO dollTicketPO) throws ServiceException {
		for (TicketPO tp : shadowTickets) {
			if (tp.getPlayId().equals(dollTicketPO.getPlayId())
					&& isStringSame(tp.getPassTypeId(),
							dollTicketPO.getPassTypeId())
					&& isStringSame(tp.getIssue(), dollTicketPO.getIssue())
					&& tp.getActualCode().equals(dollTicketPO.getActualCode())) {
				return tp;
			}
		}
		throw new ServiceException("No similar s-ticket for d-ticket: "
				+ dollTicketPO);
	}

	private boolean isStringSame(String ref, String compared) {
		if (StringUtils.isEmpty(ref)) {
			if (StringUtils.isEmpty(compared)) {
				return true;
			} else {
				return false;
			}
		}
		if (StringUtils.isEmpty(compared)) {
			return false;
		}
		return ref.equals(compared);
	}

	public boolean isShadow(long userId) {
		return getSUID() == userId;
	}

	@Override
	public long getSUID() {
		if (suid == -1) {
			PuPO su = puDao.getSU();
			if (su == null) {
				logger.error("Can not get su id !");
			} else {
				suid = su.getUid();
			}
		}
		return suid;
	}

	@Override
	public BetSchemePO getDollByShadow(long sid) {
		PsPO ps = psDao.findDollByShadow(sid);
		return betSchemeDao.get(ps.getDid());
	}

	@Override
	public BetSchemePO getShadowByDoll(long did) {
		PsPO ps = psDao.findShadowByDoll(did);
		return betSchemeDao.get(ps.getSid());
	}

	// 允许执行 Phantom 操作的彩种id
	private LotteryId[] validLotteryId = new LotteryId[] {
			LotteryId.JCZQ, LotteryId.JCLQ,		//竞彩 
			LotteryId.BJDC, LotteryId.BDSF,		//北单
			LotteryId.JX11,	LotteryId.CQSS, 	//高频彩
			LotteryId.SSQ,	 	//福彩
			LotteryId.CTZC						//传统
	};

	/**
	 * 对某种玩法是否允许执行 Phantom 方案。
	 */
	private boolean isValidPlayType(PlayType playType) {
		LotteryId lid = playType.getLotteryId();
		for (LotteryId vid : validLotteryId) {
			if (lid == vid) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateSchemeStatus(BetSchemePO scheme) {
		PlayType pt = PlayType.valueOfLcPlayId(scheme.getPlayId());
		logger.debug("pt={}",pt);
		if (isValidPlayType(pt) && isDoll(scheme.getSponsorId(), pt)) {
			logger.debug("isValidPlayType(pt) && isDoll(scheme.getSponsorId(), pt)");
			if (isGeneratePhantomScheme(scheme.getType(), scheme.getLotteryId())) {
				logger.debug("isGeneratePhantomScheme(scheme.getType(), scheme.getLotteryId())");
				scheme.setStatus(EntityStatus.TICKET_INIT);
			} else {
				logger.debug("is not GeneratePhantomScheme");
				scheme.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
			}
		} else {
			logger.debug("is not ValidPlayType or doll");
			scheme.setStatus(EntityStatus.TICKET_ALLOW_BUY);
		}
	}

	@Override
	public void updateTicketStatus(BetSchemePO scheme) {
		PlayType pt = PlayType.valueOfLcPlayId(scheme.getPlayId());
		if (isValidPlayType(pt) && isDoll(scheme.getSponsorId(), pt)) {
			scheme.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
		}
	}

	/**
	 * 除了特殊用户发起的竞彩足球和竞彩篮球的“代购或跟单”方案，不真实出票
	 * 其他类型的方案都走Shadow方案的出票
	 */
	@Override
	public boolean isGeneratePhantomScheme(int schemeType, String lotteryId) {
		if(LotteryId.JCZQ.name().equals(lotteryId) ||
				LotteryId.JCLQ.name().equals(lotteryId)) {
			if (schemeType == EntityType.BET_TYPE_ALONE
					|| schemeType == EntityType.BET_TYPE_FOLLOW) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void updateVirtualSchemeTicketStatusSucc(TicketPO ticketPO,
			BetScheme scheme) {
		if(canAutoTicketSuccess(scheme.getPlayId(), scheme.getSponsorId(), 
				scheme.getType(), scheme.getLotteryId())) {
			ticketPO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
			updateOddsOfSpecialScheme(ticketPO, scheme);
		}
	}
	
	@Override
	public boolean canAutoTicketSuccess(String playId, long sponsorId, 
			int schemeType, String lotteryId) {
		PlayType pt = PlayType.valueOfLcPlayId(playId);
		if (isValidPlayType(pt) && isDoll(sponsorId, pt)) {
			if (!isGeneratePhantomScheme(schemeType, lotteryId)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void updateOddsOfSpecialScheme(TicketPO ticketPO, BetScheme scheme) {
		OddAndActualOdd oddResult = betOddsService.convert(ticketPO, scheme);
		if(null!=oddResult){
			ticketPO.setOdds(oddResult.getOdds());
			ticketPO.setActualOdds(oddResult.getActualOdds());//由于投注记录展示时会用到actualOdds，故在这里设置
			ticketPO.setDavcaiOdds(oddResult.getOdds());
		}
	}
	
	@Override
	public OddAndActualOdd convert(TicketPO ticketPO, BetScheme scheme) {
		OddAndActualOdd oddResult = betOddsService.convert(ticketPO, scheme);
		return oddResult;
	}

	@Transactional(readOnly=true)
	@Override
	public List<BigInteger> listUser() {
		return puDao.listUser();
	}
}
