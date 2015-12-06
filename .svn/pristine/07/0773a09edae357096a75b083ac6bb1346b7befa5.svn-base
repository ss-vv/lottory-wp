package com.xhcms.lottery.mc.persist.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.translate.MatchOdds;
import com.xhcms.lottery.commons.client.translate.MultiPlatformBetCodeUtil;
import com.xhcms.lottery.commons.client.translate.Odds;
import com.xhcms.lottery.commons.client.translate.PlatformBetContent;
import com.xhcms.lottery.commons.client.translate.ZMMatchBetContent;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.PrintableFile;
import com.xhcms.lottery.commons.data.PrintableTicket;
import com.xhcms.lottery.commons.data.RemoteTicket;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.exception.JXRuntimeException;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.PlayDao;
import com.xhcms.lottery.commons.persist.dao.PrintableTicketDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.dao.TicketRemoteDao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.PlayPO;
import com.xhcms.lottery.commons.persist.entity.PrintableFilePO;
import com.xhcms.lottery.commons.persist.entity.PrintableTicketPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.PhantomService;
import com.xhcms.lottery.commons.persist.service.PresetAwardService;
import com.xhcms.lottery.commons.persist.service.ShowSchemeService;
import com.xhcms.lottery.commons.utils.YuanChengChuoPiaoOdds2OddsUtil;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.lang.PresetAward;
import com.xhcms.lottery.mc.persist.service.TicketService;
import com.xhcms.lottery.utils.DateUtils;
import com.xhcms.lottery.utils.NumberUtils;
import com.xhcms.lottery.utils.POUtils;
import com.xhcms.lottery.utils.UtilityException;

@Transactional
public class TicketServiceImpl implements TicketService {

	private static final Logger log = LoggerFactory
			.getLogger(TicketServiceImpl.class);

	@Autowired
	private TicketRemoteDao ticketRemoteDao;
	
	@Autowired
	private TicketDao ticketDao;

	@Autowired
	private BetSchemeDao betSchemeDao;

	@Autowired
	private PlayDao playDao;

	@Autowired
	private PresetAwardService presetAwardService;

	@Autowired
	private BetPartnerDao betPartnerDao;

	@Autowired
	private PrintableTicketDao printableTicketDao;

	@Autowired
	private BetSchemeService betSchemeService;
	@Autowired
	private PhantomService pservice;

	@Autowired
	private ShowSchemeService showSchemeService;

	/** 自动派奖的阀值 */
	private int threshold4AutoAward = 100;

	private int defaultOperatorId = 0;// 默认处理员id
	
	@Autowired
	private PrintableTicketDao printTicketDao;

	@Override
	@SuppressWarnings("unchecked")
	public List<Ticket> buyTicket(Long schemeId) {
		// 修改出票状态
		BetSchemePO scheme = betSchemeDao.get(schemeId);
		if (scheme.getStatus() != EntityStatus.TICKET_ALLOW_BUY &&
		// 方案状态“已请求出票”是为处理未成功接票的情况准备的
				scheme.getStatus() != EntityStatus.TICKET_REQUIRED) {
			return Collections.EMPTY_LIST;
		}

		boolean isGroupBuy = (EntityType.BET_TYPE_PARTNER == scheme.getType());
		PlayType pt = PlayType.valueOfLcPlayId(scheme.getPlayId());
		// 只是预防万一
		if (pservice.isDoll(scheme.getSponsorId(), pt) && !isGroupBuy) {
			log.error("Doll scheme want to buyTicket, Not Allowed! sid={}",
					scheme.getId());
			return Collections.EMPTY_LIST;
		}

		scheme.setStatus(EntityStatus.TICKET_REQUIRED);
		List<TicketPO> tickets = ticketDao.findByScheme(schemeId,
				EntityStatus.TICKET_INIT);
		List<Ticket> ts = new ArrayList<Ticket>(tickets.size());

		for (TicketPO ticket : tickets) {
			ticket.setStatus(EntityStatus.TICKET_REQUIRED);
			Ticket t = new Ticket();
			BeanUtils.copyProperties(ticket, t);
			Map<String, String> betCode4LotteryPlatformMap = MultiPlatformBetCodeUtil
					.parse(ticket.getPlatformBetCode());
			t.setPlatformBetCodeMap(betCode4LotteryPlatformMap);
			ts.add(t);
		}

		return ts;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Long> listRequired() {
		Date from = new Date(System.currentTimeMillis() - Constants.ONE_WEEK);
		return ticketDao.findByStatus(EntityStatus.TICKET_REQUIRED, from, null);
	}

	@Override
	public void updateTicketStatus(Map<Integer, List<Long>> map) {
		for (Map.Entry<Integer, List<Long>> e : map.entrySet()) {
			String message = null;
			switch (e.getKey()) {
			case -1:
				message = "金额错误";
				break;
			case -2:
				message = "投注号码错误";
				break;
			case -3:
				message = "倍数错误";
				break;
			case -4:
				message = "余额不足";
				break;
			case -6:
				message = "含有停售或过期场次";
				break;
			}
			if (message != null) {
				ticketDao.updateStatus(e.getValue(),
						EntityStatus.TICKET_REQUIRED,
						EntityStatus.TICKET_BUY_FAIL, message);
			}
		}
	}

	/**
	 * 确认出票信息。tickets不能包含 状态为 NOT_EXISTS 的票。
	 */
	@Override
	public void check(Map<Long, Ticket> tickets) {
		HashMap<Long, BetSchemePO> schemeMap = new HashMap<Long, BetSchemePO>();
		checkAndUpdateTicketsAndPrepareSchemeMap(tickets, schemeMap);
		if (schemeMap.size() == 0) {
			return;
		}
		// 检查方案的出票情况，设置方案状态
		checkAndUpdateStatus4SchemeMap(schemeMap);
	}

	/**
	 * 检查并更新每张票的状态，并且准备好SchemeMap
	 * 
	 * @param tickets
	 * @param schemeMap
	 */
	private void checkAndUpdateTicketsAndPrepareSchemeMap(
			Map<Long, Ticket> tickets, HashMap<Long, BetSchemePO> schemeMap) {
		List<TicketPO> ticketPOList = ticketDao.find(tickets.keySet());

		for (TicketPO ticketPO : ticketPOList) {
			handelOneTicketPO(tickets, schemeMap, ticketPO);
		}
	}
	/** 远程出票
	 * 检查并更新每张票的状态，并且准备好SchemeMap
	 * 
	 * @param tickets
	 * @param schemeMap
	 */
	private void checkAndUpdateTicketsAndPrepareSchemeMap_(
			Map<Long, TicketRemotePO> tickets, HashMap<Long, BetSchemePO> schemeMap) {
		List<TicketPO> ticketPOList = ticketDao.find(tickets.keySet());

		for (TicketPO ticketPO : ticketPOList) {
			handelOneTicketPO_(tickets, schemeMap, ticketPO);
		}
	}

	private void checkAndUpdateStatus4SchemeMap(
			HashMap<Long, BetSchemePO> schemeMap) {
		List<BetSchemePO> bss = betSchemeDao.find(schemeMap.keySet());
		for (BetSchemePO s : bss) {
			schemeMap.put(s.getId(), s);
		}

		Set<Long> schemeIds = schemeMap.keySet();
		List<Object[]> successFailData = ticketDao.sumBoughtNote(schemeIds);
		for (Object[] data : successFailData) {
			Long sid = (Long) data[0];
			int status = ((Number) data[1]).intValue();
			int note = ((Number) data[2]).intValue();

			BetSchemePO spo = schemeMap.get(sid);
			if (status == EntityStatus.TICKET_BUY_SUCCESS && note > 0) {
				spo.setTicketNote(note);
			} else if (status == EntityStatus.TICKET_BUY_FAIL && note > 0) {
				spo.setCancelNote(note);
			}
		}

		for (BetSchemePO spo : schemeMap.values()) {
			if (spo.getBetNote() == spo.getCancelNote() + spo.getTicketNote()) {
				if (spo.getTicketNote() > 0) {
					spo.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
				} else {
					spo.setStatus(EntityStatus.TICKET_BUY_FAIL);
				}
				pservice.onSchemeBuyComplete(spo);
			}
		}
	}

	/**
	 * 针对一个ticketPO处理
	 * 
	 * @param tickets
	 * @param schemeMap
	 * @param ticketPO
	 */
	private void handelOneTicketPO(Map<Long, Ticket> tickets,
			HashMap<Long, BetSchemePO> schemeMap, TicketPO ticketPO) {

		if (statusIsNotTicketRequired(ticketPO)) {
			return;
		}
		Ticket ticket = tickets.get(ticketPO.getId());

		// 检查数据库中的票是否与中民接口返回的票信息匹配
		if (ticketInDataBaseNotMatchTicketInZMInterfaceResult(ticket, ticketPO)) {
			return;
		}
		// 检查 playId 是否一致，不一致就跳过
		try {
			if (!isLotteryIdConsistent(ticketPO, ticket)) {
				return;
			}
		} catch (UtilityException e) {
			log.error("Can not check consistency of playId.", e);
			return;
		}
		modifyPropertyOfTicketPOByTicket(ticketPO, ticket);
		ticketDao.update(ticketPO);
		schemeMap.put(ticketPO.getSchemeId(), null);
	}
	/**远程出票更新表使用
	 * 针对一个ticketPO处理
	 * 
	 * @param tickets
	 * @param schemeMap
	 * @param ticketPO
	 */
	private void handelOneTicketPO_(Map<Long, TicketRemotePO> tickets,
			HashMap<Long, BetSchemePO> schemeMap, TicketPO ticketPO) {
		if (statusIsNotTicketRequired(ticketPO)) {
			return;
		}
		TicketRemotePO ticket = tickets.get(ticketPO.getId());
		/*
		

		// 检查数据库中的票是否与中民接口返回的票信息匹配
		if (ticketInDataBaseNotMatchTicketInZMInterfaceResult(ticket, ticketPO)) {
			return;
		}
		// 检查 playId 是否一致，不一致就跳过
		try {
			if (!isLotteryIdConsistent(ticketPO, ticket)) {
				return;
			}
		} catch (UtilityException e) {
			log.error("Can not check consistency of playId.", e);
			return;
		}
		modifyPropertyOfTicketPOByTicket(ticketPO, ticket);*/
		ticketPO.setMessage(ticket.getMessage());
		if(ticket.getStatus().equals(EntityStatus.REMOTE_TICKET_SUCCESS)){
			ticketPO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
			ticketPO.setFinalStateTime(new Date());
		}else if(ticket.getStatus().equals(EntityStatus.REMOTE_WAIT)){
		}else{
			ticketPO.setStatus(EntityStatus.TICKET_BUY_FAIL);
			ticketPO.setFinalStateTime(new Date());
		}
		ticketDao.update(ticketPO);
		schemeMap.put(ticketPO.getSchemeId(), null);
	}

	private void modifyPropertyOfTicketPOByTicket(TicketPO ticketPO,
			Ticket ticket) {
		ticketPO.setActualStatus(ticket.getActualStatus());
		ticketPO.setNumber(ticket.getNumber());
		ticketPO.setMessage(ticket.getMessage());
		ticketPO.setStatus(ticket.getStatus());
		ticketPO.setOdds(ticket.getOdds());
		ticketPO.setActualOdds(ticket.getActualOdds());
		if (ticket.getStatus() == EntityStatus.TICKET_BUY_SUCCESS
				|| ticket.getStatus() == EntityStatus.TICKET_BUY_FAIL) {
			ticketPO.setFinalStateTime(new Date());
		}
		if (ticket.getExpectBonus() != null) { // 目前只有竞彩有这个值
			ticketPO.setExpectBonus(ticket.getExpectBonus()); // 用SP值计算出来的理论最高奖金
		}
		ticketPO.setPlatformId(ticket.getPlatformId());
	}

	private boolean ticketInDataBaseNotMatchTicketInZMInterfaceResult(
			Ticket ticket, TicketPO ticketPO) {
		boolean result = false;
		if (null == ticket) {
			logError4TicketInDataBatabaseNotMatchTicketInZMInterfaceResult(ticketPO);
			result = true;
		}
		return result;
	}

	/**
	 * @return true 一致，false 不一致
	 */
	private boolean isLotteryIdConsistent(TicketPO ticketPO, Ticket ticket)
			throws UtilityException {
		if (!isTicketLotteryIdShouldNotCheck(ticketPO.getPlayId())) {
			LotteryId lotteryId = PlayType
					.valueOfLcPlayId(ticketPO.getPlayId()).getLotteryId();
			if (lotteryId != ticket.getLotteryId()) {
				log.error(
						"Check ticket lotteryId consistency fail! ticketId={}, poLotteryId={}, zmLotteryId={}",
						new Object[] { ticketPO.getId(), lotteryId,
								ticket.getLotteryId() });
				return false;
			}
		}
		return true;
	}

	/**
	 * 目前竞彩世界杯猜冠军不需检查，其他玩法需要检查
	 * 
	 * @param playId
	 * @return
	 */
	private boolean isTicketLotteryIdShouldNotCheck(String playId) {
		boolean result = PlayType.JCSJBGJ.getShortPlayStr().equals(playId);
		log.info("no check ticket playId, playId={}", playId);

		return result;
	}

	/**
	 * 
	 * @param ticketPO
	 */
	private void logError4TicketInDataBatabaseNotMatchTicketInZMInterfaceResult(
			TicketPO ticketPO) {
		log.error("!!! No Ticket Object for po id: {}", ticketPO.getId());
	}

	private void logError4StatusIsWrong(TicketPO ticketPO) {
		log.error(
				"Ticket {} should has status {} but is {}. This may cause status of Sheme({}) frozen.",
				new Object[] { ticketPO.getId(), EntityStatus.TICKET_REQUIRED,
						ticketPO.getStatus(), ticketPO.getSchemeId() });
	}

	/**
	 * 状态是否不是“已请求出票”
	 * 
	 * @param ticketPO
	 * @return
	 */
	private boolean statusIsNotTicketRequired(TicketPO ticketPO) {
		boolean result = false;
		if (ticketPO.getStatus() != EntityStatus.TICKET_REQUIRED) {
			logError4StatusIsWrong(ticketPO);
			result = true;
		}
		return result;
	}

	@Override
	public void prize(Map<Long, Ticket> tickets) {
		HashMap<Long, BetSchemePO> schemeMap = new HashMap<Long, BetSchemePO>();
		updateBuySuccessTicketsAndInitSchemeMap(tickets, schemeMap);
		if (schemeMap.size() == 0) {
			return;
		}
		addBuyTicketSuccessScheme2Map(schemeMap);
		HashMap<Long, Object[]> dataMap = sumPrizedGroupByScheme(schemeMap);

		checkAndUpdatePrizedScheme(schemeMap, dataMap);

		autoAward(schemeMap);

		showSchemeService.notAwardShowFollowGrant(defaultOperatorId, schemeMap);

	}

	/**
	 * 修改原始预兑奖方案和票的状态
	 */
	private void presetAwardSourceDate(BetSchemePO spo) {
		// 预派奖方案状态置为已派奖
		spo.setStatus(EntityStatus.TICKET_AWARDED);
		spo.setAwardTime(new Date());
		// 将中奖未派奖的预兑奖票状态置为已派奖
		updatePresetTicketStatus(spo.getId());
		// 核对预兑奖方案状态
		checkPresetStatus();
	}

	/**
	 * 核对预兑奖方案状态
	 */
	private void checkPresetStatus() {
		presetAwardService.checkPresetAward();
	}

	/**
	 * 更新已中奖预兑奖票状态为已派奖
	 */
	private void updatePresetTicketStatus(Long schemeId) {
		ticketDao.updateStatusByPresetTicket(schemeId,
				EntityStatus.TICKET_NOT_AWARD, EntityStatus.TICKET_AWARDED,
				"派奖");
	}

	/**
	 * 自动派奖
	 * 
	 * @param sMap
	 */
	private void autoAward(HashMap<Long, BetSchemePO> sMap) {
		List<Long> schemeIdList = getSchemeIdList4AutoAward(sMap);
		if (null != schemeIdList && !schemeIdList.isEmpty()) {

			betSchemeService.award(defaultOperatorId, schemeIdList);

		}

	}

	/**
	 * 取得可以自动派奖的方案id列表
	 * 
	 * @param sMap
	 * @return
	 */
	public List<Long> getSchemeIdList4AutoAward(HashMap<Long, BetSchemePO> sMap) {
		List<Long> result = null;
		if (null != sMap && !sMap.isEmpty()) {
			result = new ArrayList<Long>();
			for (BetSchemePO betSchemePO : sMap.values()) {
				if (shouldAutoAward(betSchemePO)) {
					result.add(betSchemePO.getId());
				}
			}
		}
		return result;
	}

	/**
	 * 只有符合以下条件才可以自动派奖
	 * <p>
	 * 1、状态是中奖未派奖
	 * </p>
	 * <p>
	 * 2、税后奖金大于0并且小于等于指定的值
	 * </p>
	 * 
	 * @param betSchemePO
	 * @return
	 */
	private boolean shouldAutoAward(BetSchemePO betSchemePO) {
		boolean result = null != betSchemePO
				&& EntityStatus.TICKET_NOT_AWARD == betSchemePO.getStatus()
				&& (betSchemePO.getAfterTaxBonus().compareTo(BigDecimal.ZERO) > 0)
				&& (betSchemePO.getAfterTaxBonus().compareTo(
						new BigDecimal(threshold4AutoAward)) <= 0);

		if (!result) {
			log.info(
					"方案ID={}，bonus={}, status={}, 不符合自动派奖的条件:1.bonus > 0 && bonus <= {}; status={}；详情={}",
					new Object[] { betSchemePO.getId(),
							betSchemePO.getAfterTaxBonus(),
							betSchemePO.getStatus(), threshold4AutoAward,
							EntityStatus.TICKET_NOT_AWARD, betSchemePO });
		} else {
			log.info(
					"方案ID={}，bonus={}, threshold4AutoAward={}, 成功加入到自动派奖列表",
					new Object[] { betSchemePO.getId(),
							betSchemePO.getAfterTaxBonus(), threshold4AutoAward });
		}
		return result;
	}

	/**
	 * 检查方案的兑奖情况，设置方案状态
	 * 
	 * @param schemeMap
	 *            包含中奖ticket的方案map
	 * @param dataMap
	 *            每个方案的相关数据，有“总注数、中奖注数、税前奖金、税后奖金”
	 */
	private void checkAndUpdatePrizedScheme(
			HashMap<Long, BetSchemePO> schemeMap,
			HashMap<Long, Object[]> dataMap) {
		for (Map.Entry<Long, Object[]> e : dataMap.entrySet()) {
			BetSchemePO spo = schemeMap.get(e.getKey());
			if (spo != null) {
				Object[] val = e.getValue();
				// 已兑奖注数等于投注注数
				if ((Integer) val[0] == spo.getBetNote()) {// 这里表示所有票都到达最终状态（未中奖或者已中奖或者出票失败）
					int winNote = (Integer) val[1];
					if (winNote > 0) {
						spo.setWinNote((Integer) val[1]);
						spo.setPreTaxBonus(val[2] == null ? BigDecimal.ZERO
								: (BigDecimal) val[2]);
						spo.setAfterTaxBonus(val[3] == null ? BigDecimal.ZERO
								: (BigDecimal) val[3]);
						spo.setStatus(EntityStatus.TICKET_NOT_AWARD);

						// 计算投注人奖金
						if (spo.getAfterTaxBonus().compareTo(BigDecimal.ZERO) == 1) {
							preAward(spo);
						}
						// 处理预派奖方案
						if (spo.getIsPresetAward() == PresetAward.Is_PresetAward
								.getValue()) {
							presetAwardSourceDate(spo);
						}
					} else {
						spo.setWinNote(0);
						spo.setPreTaxBonus(BigDecimal.ZERO);
						spo.setAfterTaxBonus(BigDecimal.ZERO);
						spo.setStatus(EntityStatus.TICKET_NOT_WIN);
					}
					if (spo.getSaleStatus() == EntityStatus.SCHEME_ON_SALE) {
						spo.setSaleStatus(EntityStatus.SCHEME_STOP);
					}
					pservice.onSchemePrized(spo);
				}
			}
		}
	}

	/**
	 * 按方案汇总中奖情况
	 * 
	 * @param sMap
	 * @return
	 */
	private HashMap<Long, Object[]> sumPrizedGroupByScheme(
			HashMap<Long, BetSchemePO> sMap) {
		List<Object[]> data = ticketDao.sumPrized(sMap.keySet());
		HashMap<Long, Object[]> dMap = new HashMap<Long, Object[]>(data.size());
		for (Object[] d : data) {
			Long schemeId = (Long) d[0];
			Object[] val = dMap.get(schemeId);
			if (val == null) {// 初始化中奖汇总情况对象数组
				val = new Object[] { 0, 0, null, null };// 中奖汇总情况对象数组,从0到3，分别代表总注数、中奖注数、税前奖金、税后奖金
				dMap.put(schemeId, val);
			}

			int note = ((Number) d[2]).intValue();// 总注数
			if (note > 0) {
				val[0] = note + (Integer) val[0]; // 总注数累加
				int status = ((Number) d[1]).intValue();// 状态
				if (status == EntityStatus.TICKET_NOT_AWARD) {// 中奖未派奖
					val[1] = note + (Integer) val[1]; // 中奖注数累加
					val[2] = d[3]; // 税前奖金
					val[3] = d[4]; // 税后奖金
				}
			}
		}
		return dMap;
	}

	/**
	 * 依据sMap中的方案id查找状态为出票成功的方案，并放入sMap中
	 * 
	 * @param sMap
	 */
	private void addBuyTicketSuccessScheme2Map(HashMap<Long, BetSchemePO> sMap) {
		List<BetSchemePO> bss = betSchemeDao.find(sMap.keySet());
		for (BetSchemePO s : bss) {
			if (s.getStatus() == EntityStatus.TICKET_BUY_SUCCESS) {
				sMap.put(s.getId(), s);
			}
		}
	}

	/**
	 * 将出票成功的彩票更新状态，并且依据彩票的方案id初始化SchemeMap
	 * 
	 * @param tickets
	 * @param sMap
	 */
	private void updateBuySuccessTicketsAndInitSchemeMap(
			Map<Long, Ticket> tickets, HashMap<Long, BetSchemePO> sMap) {
		if (null != tickets && !tickets.isEmpty()) {
			List<TicketPO> pos = ticketDao.find(tickets.keySet());
			Map<Long, List<TicketPO>> betSchemeIdTicketListMap = change2BetSchemeIdTicketListMap(pos);
			if (null != betSchemeIdTicketListMap
					&& !betSchemeIdTicketListMap.isEmpty()) {
				for (Entry<Long, List<TicketPO>> entrySet : betSchemeIdTicketListMap
						.entrySet()) {
					List<TicketPO> ticketList = entrySet.getValue();
					handleOneTicket(tickets, sMap, ticketList);
				}
			}
		}

	}

	private void handleOneTicket(Map<Long, Ticket> tickets,
			HashMap<Long, BetSchemePO> sMap, List<TicketPO> pos) {
		for (TicketPO po : pos) {
			if (po.getStatus() != EntityStatus.TICKET_BUY_SUCCESS) {
				warn(new Object[] { "prize", "status", po.getId(),
						EntityStatus.TICKET_BUY_SUCCESS, po.getStatus() });
				continue;
			}
			log.debug("Find scheme status for prize ticket: " + po.getId());
			BetScheme betScheme = betSchemeService.getSchemeById(po
					.getSchemeId());
			if (null != betScheme
					&& betScheme.getStatus() != EntityStatus.TICKET_BUY_SUCCESS) {
				log.info(
						"Scheme({}) status is not BUY_SUCCESS so should not change ticket({}) prize status!",
						betScheme.getId(), po.getId());
				continue;
			}
			Ticket t = tickets.get(po.getId());
			if (t == null) {
				continue;
			}
			po.setMessage(t.getMessage());

			if (t.getStatus() != -1) {// 如果通过接口查询出来的status值是-1，就不更新票的状态
				po.setStatus(t.getStatus() == EntityStatus.TICKET_NOT_EXIST ? EntityStatus.TICKET_BUY_FAIL
						: t.getStatus());
			}
			po.setPreTaxBonus(t.getPreTaxBonus());
			po.setAfterTaxBonus(t.getAfterTaxBonus());

			ticketDao.update(po);
			sMap.put(po.getSchemeId(), null);
		}
	}

	private Map<Long, List<TicketPO>> change2BetSchemeIdTicketListMap(
			List<TicketPO> pos) {
		Map<Long, List<TicketPO>> result = new HashMap<Long, List<TicketPO>>();
		if (null != pos && !pos.isEmpty()) {
			for (TicketPO ticketPO : pos) {
				if (result.containsKey(ticketPO.getSchemeId())) {
					result.get(ticketPO.getSchemeId()).add(ticketPO);
				} else {
					List<TicketPO> ticketPOList = new ArrayList<TicketPO>();
					ticketPOList.add(ticketPO);
					result.put(ticketPO.getSchemeId(), ticketPOList);
				}
			}
		}
		return result;
	}

	/**
	 * 兑奖预处理，更新 BetPartner.
	 * 
	 * @param betSchemePo
	 *            准备兑奖的方案
	 */
	private void preAward(BetSchemePO betSchemePo) {
		BigDecimal bonus = betSchemePo.getAfterTaxBonus();
		BigDecimal commission = BigDecimal.ZERO;

		// 计算发起人分成，合买
		if (betSchemePo.getType() == EntityType.BETTING_PARTNER) {
			commission = bonus.multiply(
					NumberUtils.percent(betSchemePo.getShareRatio())).setScale(
					2, RoundingMode.DOWN);
			if (NumberUtils.gt(commission, BigDecimal.ZERO)) {
				bonus = bonus.subtract(commission);
			}
		}

		// 计算发起人分成，跟单
		if (betSchemePo.getType() == EntityType.BETTING_FOLLOW) {
			// 当税后奖金大于本金时才给晒单人佣金
			if (bonus.compareTo(new BigDecimal(betSchemePo.getTotalAmount())) == 1) {
				BetSchemePO showScheme = betSchemeDao.get(betSchemePo
						.getFollowedSchemeId());
				commission = bonus.multiply(
						NumberUtils.percent(showScheme.getFollowedRatio()))
						.setScale(2, RoundingMode.DOWN);
				// 计算后的佣金大于0就派发佣金
				if (NumberUtils.gt(commission, BigDecimal.ZERO)) {
					bonus = bonus.subtract(commission);
				}
			}
		}

		// 计算发起人分成后的奖金分配
		BigDecimal eachBonus = bonus.divide(
				new BigDecimal(betSchemePo.getTotalAmount()), 16,
				RoundingMode.HALF_UP);
		List<BetPartnerPO> partners = betPartnerDao.findBySchemeId(betSchemePo
				.getId());
		for (BetPartnerPO betPartner : partners) {
			BigDecimal actualAward = eachBonus.multiply(
					new BigDecimal(betPartner.getBetAmount())).setScale(2,
					RoundingMode.HALF_UP);
			betPartner.setWinAmount(actualAward);
			if (betSchemePo.getType() != EntityType.BETTING_PARTNER)
				betPartner.setCommission(commission);
		}
	}

	private void warn(Object[] args) {
		log.warn("{}: {} is invalid. ticketId:{}, expected:{}, but was:{}.",
				args);
	}

	@Override
	@Transactional
	public List<Ticket> listRequiredTicket() {
		Date from = new Date(System.currentTimeMillis() - Constants.ONE_WEEK);
		List<Long> tickets = ticketDao.findByStatus(
				EntityStatus.TICKET_REQUIRED, from, null);
		List<TicketPO> ticketsPo = ticketDao.find(tickets);
		return POUtils.copyBeans(ticketsPo, Ticket.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> listBuySuccessTickets() {
		Date from = new Date(System.currentTimeMillis() - Constants.ONE_WEEK);
		List<Long> tickets = ticketDao.findByStatus(
				EntityStatus.TICKET_BUY_SUCCESS, from, null);
		if (tickets.size() > 0) {
			List<TicketPO> ticketsPo = ticketDao.find(tickets);
			return POUtils.copyBeans(ticketsPo, Ticket.class);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public BetScheme getBetSchemeOfTicket(Long tiecketId) {
		TicketPO ticketPO = ticketDao.get(tiecketId);
		BetSchemePO betSchemePO = betSchemeDao.get(ticketPO.getSchemeId());
		BetScheme scheme = new BetScheme();
		BeanUtils.copyProperties(betSchemePO, scheme);
		return scheme;
	}

	@Override
	public List<Ticket> listTicketsOfScheme(Long schemeId) {
		List<TicketPO> tickets = ticketDao.findByScheme(schemeId, -1);
		return POUtils.copyBeans(tickets, Ticket.class);
	}

	@Override
	public void updateTicketStatusForOrder(long ticketId, String statusCode,
			String errorMsg) {
		TicketPO ticketPO = ticketDao.get(ticketId);
		ticketPO.setMessage(errorMsg);
		int actualStatus = -1;
		try {
			actualStatus = Integer.parseInt(statusCode);
		} catch (Exception e) {
			log.warn("statusCode is not Integer: {}", statusCode);
		}
		ticketPO.setActualStatus(actualStatus);
		// 送票成功的不变状态，保持已出票状态。
		if (statusCode != null && isZMSendTicketOKStatus(statusCode)) {
			// 对订单重复的情况，我们认为也是送票成功了的.
			if ("909".equals(statusCode.trim())) {
				log.info(
						"Send ticket get response 909, we treat it as 000: tid={}",
						ticketId);
			}
			return;
		}
		ticketPO.setStatus(EntityStatus.TICKET_INIT);
	}

	/**
	 * 是否是中民接口定义的送票成功状态。<br/>
	 * 对订单重复的情况，我们认为也是送票成功了的。
	 * 
	 * @param statusCode
	 *            送票响应状态码
	 * @return true 成功送票状态；false 送票失败状态
	 */
	@Override
	public boolean isZMSendTicketOKStatus(String statusCode) {
		String s = statusCode.trim();
		if ("000".equals(s) || "909".equals(s) || "0".equals(s)) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Long> listNotPrizeTicketBefore(Date endTime) {
		Date from = new Date(System.currentTimeMillis() - 86400000L * 30);
		return ticketDao.findByStatus(EntityStatus.TICKET_BUY_SUCCESS, from,
				endTime);
	}

	@Override
	public Ticket getTicket(Long ticketId) {
		TicketPO ticketPO = ticketDao.get(ticketId);
		log.debug("获取到的票信息={}", ticketPO);
		Ticket ticket = new Ticket();
		BeanUtils.copyProperties(ticketPO, ticket);
		return ticket;
	}

	@Override
	public void saveTicketStatus(Collection<Ticket> tickets) {
		for (Ticket ticket : tickets) {
			TicketPO ticketPO = ticketDao.get(ticket.getId());
			ticketPO.setStatus(ticket.getStatus());
			if(null != ticket) {
				switch (ticket.getStatus()) {
				case EntityStatus.TICKET_BUY_SUCCESS:
					log.debug("票ID={},出票成功.", ticket.getId());
					ticketPO.setTicketSuccTime(new Date());
					break;
				case EntityStatus.TICKET_NOT_WIN:
				case EntityStatus.TICKET_AWARDED:
					log.debug("票ID={},达到最终状态.", ticket.getId());
					ticketPO.setFinalStateTime(new Date());
					break;
				}
			}
		}
	}

	@Transactional
	@Override
	public boolean physicalStoreTicket(long ticketId) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket t = getTicket(ticketId);
		if (null == t || null == t.getId() || t.getId() <= 0) {
			return false;
		}
		log.info("实体出票:ticketId={}, ticket={}", ticketId, t);
		Assert.isTrue((t.getStatus() == EntityStatus.TICKET_READY_FOR_HANDWORK
				|| t.getStatus() == EntityStatus.TICKET_EXPORTED),
				AppCode.SCHEME_UNUSUAL_OPERATE);
		Long schemeId = t.getSchemeId();
		List<Long> list = new ArrayList<Long>();
		list.add(schemeId);
		List<BetSchemePO> betSchemeList = betSchemeDao.find(list);

		if (null != betSchemeList && betSchemeList.size() == 1) {
			BetSchemePO betSchemePO = betSchemeList.get(0);
			if (EntityStatus.TICKET_INIT == betSchemePO.getStatus()
					|| EntityStatus.TICKET_ALLOW_BUY == betSchemePO.getStatus()
					|| EntityStatus.TICKET_REQUIRED == betSchemePO.getStatus()
					|| EntityStatus.TICKET_BUY_FAIL == betSchemePO.getStatus()) {
				if (betSchemePO.getType() == EntityType.BETTING_PARTNER) {// 合买方案
					BetSchemePO schemePO = betSchemeService.getSchemePOById(t
							.getSchemeId());
					boolean isCanBet = false;
					if (null != schemePO && schemePO.getId() > 0) {
						PlayPO play = playDao.get(schemePO.getPlayId());
						int floorAmount = play.getFloorRatio()
								* schemePO.getTotalAmount() / 100;// 网站保底金额
						// 可跟单金额
						int surplusAmount = schemePO.getTotalAmount()
								- schemePO.getPurchasedAmount();
						if (floorAmount + t.getMoney()
								+ schemePO.getFloorAmount() >= surplusAmount) {
							isCanBet = true;
						}
					}
					if (!isCanBet) {
						boolean rs = betSchemePO.getSaleStatus() == EntityStatus.SCHEME_STOP;
						Assert.isTrue(rs, AppCode.SCHEME_UNUSUAL_OPERATE);
					}
				}
				// 设置方案状态
				betSchemePO.setTicketNote(betSchemePO.getTicketNote()
						+ t.getNote());

				// 保证方案出票成功
				if (betSchemePO.getTicketNote() == betSchemePO.getBetNote()) {
					betSchemePO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
				} else if (betSchemePO.getTicketNote()
						+ betSchemePO.getCancelNote() == betSchemePO
							.getBetNote()) {
					betSchemePO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
				}

				// 设置票的状态
				t.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
				tickets.add(t);
				saveTicketStatus(tickets);
			}
		} else {
			throw new JXRuntimeException("数据异常!");
		}
		return true;
	}
	
	@Transactional
	@Override
	public boolean cancelStoreTicket(List<Long> ticketIds) {
		for (Long id : ticketIds) {
			TicketPO t = ticketDao.findById(id);
			if(null == t || t.getId() <= 0) {
				continue;
			} else if (t.getStatus() != EntityStatus.TICKET_READY_FOR_HANDWORK && t.getStatus() != EntityStatus.TICKET_EXPORTED){
		    	log.info("要撤单的票id={}的status={}不符合要求,跳过....",id,t.getStatus());
				continue;
			} else {
				this.cancelStoreTicket(id);
			}
		}
		return true;
	}
	@Transactional
	@Override
	public boolean cancelStoreTicket(long ticketId) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket t = getTicket(ticketId);
		if (null == t || null == t.getId() || t.getId() <= 0) {
			return false;
		}
		if (t.getStatus() != EntityStatus.TICKET_READY_FOR_HANDWORK && t.getStatus() != EntityStatus.TICKET_EXPORTED) {
			return false;
		}
		Long schemeId = t.getSchemeId();
		List<Long> list = new ArrayList<Long>();
		list.add(schemeId);
		List<BetSchemePO> betSchemeList = betSchemeDao.find(list);
		if (null != betSchemeList && betSchemeList.size() == 1) {
			BetSchemePO betSchemePO = betSchemeList.get(0);
			if (EntityStatus.TICKET_INIT == betSchemePO.getStatus()
					|| EntityStatus.TICKET_ALLOW_BUY == betSchemePO.getStatus()
					|| EntityStatus.TICKET_REQUIRED == betSchemePO.getStatus()
					|| EntityStatus.TICKET_BUY_FAIL == betSchemePO.getStatus()
					|| EntityStatus.TICKET_SCHEME_CANCEL == betSchemePO
							.getStatus()) {
				if (betSchemePO.getType() == EntityType.BETTING_PARTNER) {// 合买方案
					boolean rs = betSchemePO.getSaleStatus() == EntityStatus.SCHEME_STOP;
					Assert.isTrue(rs, AppCode.SCHEME_UNUSUAL_OPERATE);
				}
				int cancelNote = betSchemePO.getCancelNote();
				int betNote = betSchemePO.getBetNote();
				int ticketNote = betSchemePO.getTicketNote();

				// 设置方案状态
				betSchemePO.setCancelNote(cancelNote + t.getNote());
				if (betSchemePO.getCancelNote() == betNote) {
					betSchemePO.setStatus(EntityStatus.TICKET_SCHEME_CANCEL);
				} else if ((betSchemePO.getCancelNote() > 0 && ticketNote == 0)) {
					betSchemePO.setStatus(EntityStatus.TICKET_BUY_FAIL);
				} else if (betSchemePO.getCancelNote() > 0 && ticketNote > 0 &&
						ticketNote + betSchemePO.getCancelNote() == betNote) {
					betSchemePO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
				} else {
					betSchemePO.setStatus(EntityStatus.TICKET_BUY_FAIL);
				}

				// 设置票的状态
				t.setStatus(EntityStatus.TICKET_SCHEME_CANCEL);
				tickets.add(t);
				saveTicketStatus(tickets);
			}
		}
		return true;
	}

	@Override
	public void updateTicketsStatusTo(int status, int actualStatus,
			Collection<Long> tickets) {
		for (Long tid : tickets) {
			TicketPO ticketPO = ticketDao.get(tid);
			ticketPO.setStatus(status);
			ticketPO.setActualStatus(actualStatus);
		}
	}

	@Override
	public void updatePlayMatchByPlatformOdds(List<Ticket> tickets,
			Map<String, PlayMatch> matches) {
		if (tickets == null || tickets.size() == 0 || matches == null
				|| matches.size() == 0) {
			return;
		}
		for (Ticket t : tickets) {
			PlayType playType = PlayType.valueOfLcPlayId(t.getPlayId());
			if (playType.isHH()) {
				try {
					parseTicketOddsForDisplayHH(t, matches);
				} catch (TranslateException e) {
					throw new XHRuntimeException(e);
				}
			} else if (PlayType.JCSJBGJ.getShortPlayStr().equals(t.getPlayId())) {

			} else {
				parseTicketOddsForDisplay(t, matches);
			}
		}
	}

	/**
	 * 用出票中心给出的odds更新ticket对象关联的比赛的赔率、预设分值属性。只针对混合过关玩法，格式为中民新接口的混合过关赔率方式。
	 * 
	 * @param t
	 *            票
	 * @param matches
	 *            和票关联的 PlayMatch 比赛对象。
	 * @throws TranslateException
	 */
	private void parseTicketOddsForDisplayHH(Ticket t,
			Map<String, PlayMatch> matches) throws TranslateException {
		String actualOdds = t.getActualOdds();
		if (StringUtils.isBlank(actualOdds)) {
			return;
		}
		Odds odds = null;
		if (StringUtils.equals(t.getLotteryPlatformId(),
				LotteryPlatformId.ZUN_AO)
				|| StringUtils.isBlank(t.getLotteryPlatformId())) {
			odds = Odds.parseZMOdds(t.getActualOdds());

		} else if (StringUtils.equals(t.getLotteryPlatformId(),
				LotteryPlatformId.AN_RUI_ZHI_YING)) {
			odds = Odds.praseAnRuiOdds(t.getActualOdds(), t.getPlayId());
		}
		List<MatchOdds> moList = odds.getMatchOdds();
		List<PlayMatch> ms = new ArrayList<PlayMatch>(moList.size());
		String[] codeArray = t.getCode().split("-");
		int matchIndex = 0;
		for (String tcode : codeArray) {
			PlayMatch pm = new PlayMatch();
			String code = tcode.substring(0, 4);
			PlayMatch bm = matches.get(code);
			pm.setBetCode(tcode.substring(4));
			pm.setCnCode(bm.getCnCode());
			pm.setCode(code);
			if (!moList.isEmpty()) {
				MatchOdds mo = moList.get(matchIndex);
				PlatformBetContent mbc = ZMMatchBetContent
						.parseMatchBetContentInLaiCaiFormat(tcode,
								t.getPlayId());
				List<String> zmOptions = mbc.splitOptionsInPlatformFormat();
				StringBuilder sb = new StringBuilder();
				for (String zmOpt : zmOptions) {
					String modd = mo.getOddsByBuyOption(zmOpt,
							mbc.getMatchOrTicketPlayType());
					sb.append(modd).append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				pm.setResultOdd(sb.toString());
				String concede = mo.getExtraInfo();
				if (StringUtils.isNotBlank(concede)) {
					pm.setConcedePoints(concede);
					bm.setConcedePoints(concede);
					computationResult(t.getPlayId(), bm);
					pm.setResult(bm.getResult());
				}
			}
			ms.add(pm);
			matchIndex++;
		}
		t.setMatches(ms);

	}

	/**
	 * 用出票中心给出的odds更新ticket对象关联的比赛的赔率、预设分值属性。只针对非混合过关方式。 odds of lt_ticket 的定义：
	 * 对普通竞彩玩法，预设值放在最后，由@隔开，如：1.700-1.700-1.750-1.750@173.5B152.5B190.5B174.5 <br/>
	 * 
	 * @param t
	 *            要更新的票
	 * @param matches
	 */
	private void parseTicketOddsForDisplay(Ticket t,
			Map<String, PlayMatch> matches) {
		String codes = t.getCode();
		String odds = t.getOdds();
		String defaultScore = "";
		PlayType lcPlayType = PlayType.valueOfLcPlayId(t.getPlayId());
		String codeArr[] = ArrayUtils.EMPTY_STRING_ARRAY;
		String oddArr[] = ArrayUtils.EMPTY_STRING_ARRAY;
		String scoreArr[] = ArrayUtils.EMPTY_STRING_ARRAY;

		if (StringUtils.isNotEmpty(codes)) {
			codeArr = codes.split("-");
			if (StringUtils.isNotEmpty(odds)) {
				int scoreIndex = odds.indexOf('@');
				if (scoreIndex > 0) {
					defaultScore = odds.substring(scoreIndex + 1);
					odds = odds.substring(0, scoreIndex);
				}
				oddArr = odds.replace('A', ' ').split("-");
			}
			if (StringUtils.isNotEmpty(defaultScore)) {
				scoreArr = defaultScore.split("B");
			}
		}

		List<PlayMatch> ms = new ArrayList<PlayMatch>(codeArr.length);
		for (int i = 0; i < codeArr.length; i++) {
			String tcode = codeArr[i];
			PlayMatch pm = new PlayMatch();
			String code = "";
			String betCode = "";
			// bjdc
			if (LotteryId.BJDC.equals(lcPlayType.getLotteryId())
					|| LotteryId.BDSF.equals(lcPlayType.getLotteryId())) {
				code = Integer.parseInt(tcode.substring(0, 3)) + "";
				betCode = tcode.substring(3);
			} else {
				code = tcode.substring(0, 4);
				betCode = tcode.substring(4);
			}

			PlayMatch bm = matches.get(code);
			pm.setBetCode(betCode);
			pm.setCnCode(bm.getCnCode());

			pm.setCode(code);
			if (!ArrayUtils.isEmpty(oddArr)) {
				pm.setResultOdd(oddArr[i]);
			}
			if (!ArrayUtils.isEmpty(scoreArr)) {
				pm.setConcedePoints(scoreArr[i]);
				bm.setConcedePoints(scoreArr[i]);
				computationResult(t.getPlayId(), bm);
				pm.setResult(bm.getResult());
			}
			ms.add(pm);
		}
		t.setMatches(ms);
	}

	/**
	 * 计算正确的投注结果<br/>
	 * 篮球让球过关和大小分过关是固定赔率，需要用出票时的分值计算赛果
	 * 
	 * @param play_id
	 * @param bm
	 * @return
	 */
	private void computationResult(String play_id, PlayMatch bm) {
		String result = bm.getResult();
		if (StringUtils.isBlank(result)) {
			return;
		}
		float defaultScore = Float.parseFloat(bm.getConcedePoints());
		String[] scores = bm.getScore().split(":");
		float homeTeamScore = Float.parseFloat(scores[1]);
		float guestTeamScore = Float.parseFloat(scores[0]);

		if (play_id.equals(Constants.PLAY_06_LC_2)) {
			String homeWin = "1";
			String guestWin = "2";
			homeTeamScore = (homeTeamScore + defaultScore);
			result = homeTeamScore > guestTeamScore ? homeWin : guestWin;
		} else if (play_id.equals(Constants.PLAY_09_LC_2)) {
			String big = "1";
			String small = "2";
			result = (homeTeamScore + guestTeamScore) > defaultScore ? big
					: small;
		}
		bm.setResult(result);
	}

	public void setThreshold4AutoAward(int threshold4AutoAward) {
		this.threshold4AutoAward = threshold4AutoAward;
	}

	public int getThreshold4AutoAward() {
		return threshold4AutoAward;
	}

	@Override
	public void updateTicketStatusAndLotteryPlatformIdAndActualCode(Long id,
			int status, int actualStatus, String message,
			String lotteryPlatformId, String actualCode, String number,
			String odds) {
		ticketDao.updateTicketStatusAndLotteryPlatformIdAndActualCode(id,
				status, actualStatus, message, lotteryPlatformId, actualCode,
				number, odds);

	}

	@Transactional(readOnly = true)
	@Override
	public List<Ticket> listPhysicalStoreTicket(Paging paging, Date begin,
			Date end, int status, String lotteryId, Long schemeId, Long ticketId, String lotteryPlatformId) {
		if(null == LotteryPlatformIdEnum.getlotteryPlatformIdEnumById(lotteryPlatformId)){
			log.error("非法的LotteryPlatformId={}",lotteryPlatformId);
			return new ArrayList<Ticket>();
		}
		List<Object> ticketPOAndSchemePOs = ticketDao
				.findShiTiDianTicketsAndOrderByDeadlineWithPaging(paging,
						begin, end, status, lotteryId, schemeId, ticketId, lotteryPlatformId);
		List<Ticket> tickets = new ArrayList<Ticket>();
		if (null != ticketPOAndSchemePOs) {
			TicketPO ticketPO = null;
			BetSchemePO betSchemePO = null;
			for (Object ticketPOAndSchemePO : ticketPOAndSchemePOs) {
				Object[] objArray = (Object[]) ticketPOAndSchemePO;
				ticketPO = (TicketPO) objArray[0];
				betSchemePO = (BetSchemePO) objArray[1];
				Ticket ticket = new Ticket();
				BeanUtils.copyProperties(ticketPO, ticket);
				ticket.setSchemeOfftime(betSchemePO.getOfftime());
				tickets.add(ticket);
			}
		}
		return tickets;
	}
	
	@Override
	public List<Ticket> listPhysicalStoreTicketWithoutPaging(Date from,
			Date to, int state, String lotteryId, Long schemeId, Long ticketId, String lotteryPlatformId) {
		List<Object> ticketPOAndSchemePOs = ticketDao
				.findShiTiDianTicketsAndOrderByDeadline(from, to, state,
						lotteryId, schemeId, ticketId, lotteryPlatformId);
		List<Ticket> tickets = new ArrayList<Ticket>();
		if (null != ticketPOAndSchemePOs) {
			TicketPO ticketPO = null;
			BetSchemePO betSchemePO = null;
			for (Object ticketPOAndSchemePO : ticketPOAndSchemePOs) {
				Object[] objArray = (Object[]) ticketPOAndSchemePO;
				ticketPO = (TicketPO) objArray[0];
				betSchemePO = (BetSchemePO) objArray[1];
				Ticket ticket = new Ticket();
				BeanUtils.copyProperties(ticketPO, ticket);
				ticket.setSchemeOfftime(betSchemePO.getOfftime());
				tickets.add(ticket);
			}
		}
		return tickets;
	}

	@Override
	public List<PrintableFile> listHighSpeedPrintFileByTicketId(Paging paging,
			Date from, Date to, Long ticketId, String lotteryPlatformId) {
		List<PrintableFilePO> printableFilePOs = ticketDao.findHighSpeedPrintFilePagingByTicketId(paging, ticketId, lotteryPlatformId);
		List<PrintableFile> printableFiles = new ArrayList<PrintableFile>();
		if (printableFilePOs != null && !printableFilePOs.isEmpty()) {
			PrintableFile printableFile = null;
			for (PrintableFilePO printableFilePO : printableFilePOs) {
				printableFile = new PrintableFile();
				checkIfHaveWinTicket(printableFilePO);
				BeanUtils.copyProperties(printableFilePO, printableFile);
				printableFiles.add(printableFile);
			}
		}
		setLineColor(printableFiles);
		return printableFiles;
	}

	private void setLineColor(List<PrintableFile> printableFiles){
		String curdate = "";
		int colorIndex=-1;
		for (int i = 0; i < printableFiles.size(); i++) {
			PrintableFile printableFile = printableFiles.get(i);
			String date = DateUtils.formatToyyyyMMdd(printableFile.getCreateTime());
			if (StringUtils.isBlank(curdate) || !curdate.equals(date)) {
				curdate = date;
				colorIndex++;
			}
			if(colorIndex%2==0){
				printableFile.setColor("#DEFCFF");
			} else {
				printableFile.setColor("#E8E5A4");
			}
		}
	}
	
	private void checkIfHaveWinTicket(PrintableFilePO printableFilePO) {
		List<PrintableTicketPO> list = printableFilePO.getPrintableTickets();
		Collection<Long> ids = new HashSet<Long>(); 
		for (PrintableTicketPO printableTicketPO : list) {
			ids.add(printableTicketPO.getTicketId());
		}
		float totalbonus=0;
		int winTicketCount=0;
		List<TicketPO> tickets = ticketDao.find(ids);
		boolean win = false;
		for (TicketPO ticketPO : tickets) {
			if(EntityStatus.TICKET_NOT_AWARD == ticketPO.getStatus() || 
					EntityStatus.TICKET_AWARDED == ticketPO.getStatus() ){
				win = true;
				winTicketCount++;
				totalbonus+=ticketPO.getAfterTaxBonus().floatValue();
			}
		}
		printableFilePO.setWin(win);
		DecimalFormat df = new DecimalFormat("#.00");
		printableFilePO.setTotalBonus(Float.valueOf(df.format(totalbonus)));
		printableFilePO.setWinTicketCount(winTicketCount);
		return ;
	}

	@Override
	public List<PrintableTicket> listPrintableTickets(Paging paging, Date from,
			Date to, int state, String lotteryId, Long schemeId, Long ticketId,
			String playId, String lotteryPlatformId) {
		if(null == LotteryPlatformIdEnum.getlotteryPlatformIdEnumById(lotteryPlatformId)){
			log.error("非法的LotteryPlatformId={}",lotteryPlatformId);
			return new ArrayList<PrintableTicket>();
		}
		List<Object> ticketPOAndPrintableTicketPOs = printableTicketDao
				.findWithPage(paging, from, to, state, lotteryId, schemeId,
						ticketId, playId, lotteryPlatformId);
		List<PrintableTicket> tickets = new ArrayList<PrintableTicket>();
		if (null != ticketPOAndPrintableTicketPOs) {
			TicketPO ticketPO = null;
			PrintableTicketPO printableTicketPO = null;
			BetSchemePO betSchemePO=null;
			for (Object ticketPOAndPrintableTicketPO : ticketPOAndPrintableTicketPOs) {
				Object[] objArray = (Object[]) ticketPOAndPrintableTicketPO;
				ticketPO = (TicketPO) objArray[0];
				printableTicketPO = (PrintableTicketPO) objArray[1];
				betSchemePO=(BetSchemePO) objArray[2];
				PrintableTicket ticket = new PrintableTicket();
				BeanUtils.copyProperties(ticketPO, ticket);
				ticket.setPrintBetContent(printableTicketPO
						.getPrintBetContent());
				ticket.setSchemeOfftime(betSchemePO.getOfftime());
				ticket.setMachineOfftime(betSchemePO.getMachineOfftime());
				ticket.setTicketId(ticketPO.getId());
				tickets.add(ticket);
			}
		}
		return tickets;
	}

	@Override
	public List<String> listAvaiablePlayIds4PrintableTickets(String lotteryId) {
		List<String> result = null;
		List<PlayPO> playList = playDao.find(lotteryId);
		if (null != playList && !playList.isEmpty()) {
			result = new ArrayList<String>();
			result.add(Constants.ALL_PLAY_ID);
			for (PlayPO play : playList) {
				result.add(play.getId());
			}
		}
		return result;
	}

	@Override
	public boolean tickExportSuccess(Long ticketId) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket t = getTicket(ticketId);

		if (null == t || null == t.getId() || t.getId() <= 0) {
			return false;
		}
		if (EntityStatus.TICKET_EXPORTED == t.getStatus()) {
			log.info("实体出票:ticketId={}, ticket={}", ticketId, t);
			Assert.isTrue(
					t.getStatus() == EntityStatus.TICKET_READY_FOR_HANDWORK,
					AppCode.SCHEME_UNUSUAL_OPERATE);
			Long schemeId = t.getSchemeId();
			List<Long> list = new ArrayList<Long>();
			list.add(schemeId);
			List<BetSchemePO> betSchemeList = betSchemeDao.find(list);

			if (null != betSchemeList && betSchemeList.size() == 1) {
				BetSchemePO betSchemePO = betSchemeList.get(0);
				if (EntityStatus.TICKET_INIT == betSchemePO.getStatus()
						|| EntityStatus.TICKET_ALLOW_BUY == betSchemePO
								.getStatus()
						|| EntityStatus.TICKET_REQUIRED == betSchemePO
								.getStatus()
						|| EntityStatus.TICKET_BUY_FAIL == betSchemePO
								.getStatus()) {
					if (betSchemePO.getType() == EntityType.BETTING_PARTNER) {// 合买方案
						BetSchemePO schemePO = betSchemeService
								.getSchemePOById(t.getSchemeId());
						boolean isCanBet = false;
						if (null != schemePO && schemePO.getId() > 0) {
							PlayPO play = playDao.get(schemePO.getPlayId());
							int floorAmount = play.getFloorRatio()
									* schemePO.getTotalAmount() / 100;// 网站保底金额
							// 可跟单金额
							int surplusAmount = schemePO.getTotalAmount()
									- schemePO.getPurchasedAmount();
							if (floorAmount + t.getMoney()
									+ schemePO.getFloorAmount() >= surplusAmount) {
								isCanBet = true;
							}
						}
						if (!isCanBet) {
							boolean rs = betSchemePO.getSaleStatus() == EntityStatus.SCHEME_STOP;
							Assert.isTrue(rs, AppCode.SCHEME_UNUSUAL_OPERATE);
						}
					}
					// 设置方案状态
					betSchemePO.setTicketNote(betSchemePO.getTicketNote()
							+ t.getNote());

					// 保证方案出票成功
					if (betSchemePO.getTicketNote() == betSchemePO.getBetNote()) {
						betSchemePO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
					} else if (betSchemePO.getTicketNote()
							+ betSchemePO.getCancelNote() == betSchemePO
								.getBetNote()) {
						betSchemePO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
					}

					// 设置票的状态
					t.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
					tickets.add(t);
					saveTicketStatus(tickets);
				}
			} else {
				throw new JXRuntimeException("数据异常!");
			}
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public boolean physicalStoreTickeByBatch(List<Long> ticketIds) {
		List<TicketPO> ticketPO  = ticketDao.find(ticketIds);
		for (TicketPO t : ticketPO) {
			if(t.getStatus() == EntityStatus.TICKET_EXPORTED){
				this.physicalStoreTicket(t.getId());
			} else {
				continue;
			}
		}
		return true;
	}

	@Override
	public void checkYuanChengChuPiao(Map<Long, TicketRemotePO> trMap) {
		HashMap<Long, BetSchemePO> schemeMap = new HashMap<Long, BetSchemePO>();
		checkAndUpdateTicketsAndPrepareSchemeMap_(trMap, schemeMap);
		if (schemeMap.size() == 0) {
			return;
		}
		// 检查方案的出票情况，设置方案状态
		checkAndUpdateStatus4SchemeMap(schemeMap);
	}

	@Override
	@Transactional
	public void updateOdds(List<RemoteTicket> ts) {
		List<String> orderIds=new ArrayList<String>();
		for(RemoteTicket t:ts){
			orderIds.add(t.getOrderId());
		}
		List<TicketRemotePO> ticketRemotePOs=ticketRemoteDao.findTicketByOrderIds(orderIds);
		Map<String, Long> map = new HashMap<>();
		for (TicketRemotePO ticketRemotePO : ticketRemotePOs) {
			map.put(ticketRemotePO.getOrderId(), ticketRemotePO.getTicketId());
		}
		for(RemoteTicket t:ts){
			String contentWithOdds = t.getContent();
			Long ticketId = map.get(t.getOrderId());
			TicketPO ticketPO = ticketDao.findById(ticketId);
			Ticket ticket = new Ticket();
			ticket.setActualCode(ticketPO.getActualCode());
			ticket.setPlayId(ticketPO.getPlayId());
			String newOdds = YuanChengChuoPiaoOdds2OddsUtil.convert(ticket, contentWithOdds);
			if(StringUtils.isNotBlank(newOdds)){
				ticketPO.setOdds(newOdds);
				ticketDao.update(ticketPO);//更新TicketPO 赔率
			}
		}
	}
}