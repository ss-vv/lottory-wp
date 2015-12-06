package com.xhcms.lottery.dc.persist.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.lottery.commons.data.BetResult;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeWithIssueInfoDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithIssueInfoPO;
import com.xhcms.lottery.commons.persist.entity.OrderPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.commons.persist.service.OrderService;
import com.xhcms.lottery.dc.persist.service.TicketService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;

public class TicketServiceImpl implements TicketService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private BetSchemeDao schemeDao;

    @Autowired
    private BetPartnerDao partnerDao;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private BetSchemeBaseService betSchemeBaseService;
    
    @Autowired
    private OrderService orderService;
    
	@Autowired
	private BetSchemeWithIssueInfoDao betSchemeWithIssueInfoDao;
	
	private List<String> JCLotteryIdList;//属于竞彩的彩票id列表

	private List<String> HFLotteryIdList;//属于高频彩的彩票id列表
	
	/** 重庆时时彩 */
	private static final List<String> CQSSLotteryIdList = Arrays.asList(new String[]{LotteryId.CQSS.name()});

	// 传统足彩
	private static final List<String> CTLotteryIdList = Arrays.asList(new String[]{LotteryId.CTZC.name()});
	
	private static final List<String> bdLotteryIdList = Arrays.asList(new String[]{LotteryId.BJDC.name()});

	//福彩的彩票id列表
	private List<String> WFLotteryIdList;

	/** 查找可做扣款逻辑的方案时间间隔 */
	private long listStopSchemeInterval = Constants.ONE_WEEK;

    public long getListStopSchemeInterval() {
		return listStopSchemeInterval;
	}

	public void setListStopSchemeInterval(long listStopSchemeInterval) {
		this.listStopSchemeInterval = listStopSchemeInterval;
	}

	@Transactional(readOnly = true)
    @Override
    public List<Long> listRequiredTicket() {
        Date from = new Date(System.currentTimeMillis() - 86400000L * 20);
        return ticketDao.findZunAoTicketByStatus(EntityStatus.TICKET_REQUIRED, from, null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> listNotPrizeTicket() {
        Date from = new Date(System.currentTimeMillis() - 86400000L * 30);
        return ticketDao.findByStatus(EntityStatus.TICKET_BUY_SUCCESS, from, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> listNotPrizeTicketTest() {
    	Date from = new Date(System.currentTimeMillis() - 86400000L * 30);
    	return ticketDao.findByStatusTest(EntityStatus.TICKET_BUY_SUCCESS, from, null);
    }

    
    @Transactional
    @Override
    public void deductAndReturn(Long schemeId) {
        // 验证方案状态
        BetSchemePO s = schemeDao.get(schemeId);
        if (s.getSaleStatus() != EntityStatus.SCHEME_STOP
                || s.getStatus() == EntityStatus.TICKET_INIT
                || s.getStatus() == EntityStatus.TICKET_ALLOW_BUY
                || s.getStatus() == EntityStatus.TICKET_REQUIRED){
        	if(s.getSaleStatus() != EntityStatus.SCHEME_STOP){
        		logger.error("deductFloor=true, schemeId={}, saleStatus={}, status={}",new Object[]{schemeId,s.getSaleStatus(),s.getStatus()});
        	}
        	return;
        }
        
        Assert.eq(s.getBetNote(), s.getTicketNote() + s.getCancelNote(), AppCode.UNUSUAL_SCHEME_TICKET_AMOUNT);

        // 查询合买人
        int betAmount = 0;
        List<BetPartnerPO> partners = partnerDao.findBySchemeId(schemeId);
        Set<Long> users = new HashSet<Long>();
        for (BetPartnerPO po : partners) {
            users.add(po.getUserId());
            betAmount += po.getBetAmount();
        }


        if(s.getStatus() == EntityStatus.TICKET_BUY_FAIL
                || s.getStatus() == EntityStatus.TICKET_SCHEME_CANCEL
                || s.getStatus() == EntityStatus.TICKET_SCHEME_FLOW){
            // 投注方案的投注金额与合买人投注总金额不符
            if(s.getType()!=EntityType.BETTING_PARTNER )
            	Assert.ge(betAmount, s.getTotalAmount(), AppCode.UNUSUAL_SCHEME_PARTNER_AMOUNT);
            
            // 出票失败、流标、撤单，直接返款
            for (BetPartnerPO p : partners) {
                accountService.betDeductAndReturn(p.getUserId(), BigDecimal.ZERO, new BigDecimal(p.getBetAmount()), p.getId());
            }
            
//            TODO:返还合买发起人的保底金额
            if(s.getFloorAmount()>0)
            	accountService.betFloorDeductAndReturn(s.getSponsorId(), BigDecimal.ZERO, new BigDecimal(s.getFloorAmount()), s.getId());
        }else{
            // 出票成功（可能是部分成功），执行扣款和返还
            Assert.ge(betAmount, s.getTotalAmount(), AppCode.UNUSUAL_SCHEME_PARTNER_AMOUNT);
            // 扣款和返回比率
            BigDecimal deductRatio = new BigDecimal((double) s.getTicketNote() / s.getBetNote(), MathContext.DECIMAL64);

            BigDecimal sum = BigDecimal.ZERO;
            // 对合买人进行扣款和返还
            for (BetPartnerPO p : partners) {
                BigDecimal m = new BigDecimal(p.getBetAmount());
                BigDecimal dm = m.multiply(deductRatio).setScale(2, RoundingMode.HALF_UP);
                sum = sum.add(dm);
                
                accountService.betDeductAndReturn(p.getUserId(), dm, m.subtract(dm), p.getId());
            }
            
            // TODO: 合买扣除、返还保底金额
                if(s.getFloorAmount()>0){
                    OrderPO od = orderService.getByRelated(EntityType.ORDER_BET_FLOOR_RETURN,s.getId(),s.getSponsorId());
                    if(od==null){
    	//            实际需要扣款的总金额
    	            BigDecimal realDeductTotalAmount = new BigDecimal(s.getTotalAmount()).multiply(deductRatio).setScale(2, RoundingMode.HALF_UP);
    	            BigDecimal realDeductFloorAmount = realDeductTotalAmount.subtract(sum); // 实际扣除的保底金额
    	            BigDecimal floorAmount = new BigDecimal(s.getFloorAmount());
    	            
    	            Assert.ge(realDeductFloorAmount, BigDecimal.ZERO, AppCode.UNUSUAL_OPERATE_AMOUNT);// 扣除金额不能为负数
    	            Assert.le(realDeductFloorAmount, floorAmount, AppCode.UNUSUAL_OPERATE_AMOUNT); // 扣除金额要小于保底金额
    	            
    	            // 计算发起人保底资金的扣除额度
    	            accountService.betFloorDeductAndReturn(s.getSponsorId(), realDeductFloorAmount, floorAmount.subtract(realDeductFloorAmount), s.getId());
                	}
                }
        }
        
        // 设置方案投注状态为“满员已扣款”
        s.setSaleStatus(EntityStatus.SCHEME_SETTLEMENT);
        schemeDao.update(s);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> listStopScheme() {
        Date from = new Date(System.currentTimeMillis() - listStopSchemeInterval );
        return schemeDao.findBySaleStatus(EntityStatus.SCHEME_STOP, from, null);
    }

    @Transactional(readOnly = true)
    @Override
    public Map<String, List<Long>> listAllowBuyScheme() {
        Map<String, List<Long>> map = new HashMap<String, List<Long>>();
        Date date = new Date(System.currentTimeMillis() - Constants.ONE_WEEK);
        for(BetSchemePO po: schemeDao.findByStatus(EntityStatus.TICKET_ALLOW_BUY, date)){
            List<Long> s = map.get(po.getLotteryId());
            if(s == null){
                s = new ArrayList<Long>();
                map.put(po.getLotteryId(), s);
            }
            s.add(po.getId());
        }
        
        return map;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> listDeadlinesGroupbuyScheme(int second){
    	//方案截止时间：2周前
        Date from = new Date(System.currentTimeMillis() - (Constants.ONE_WEEK * 2));
        //方案截止时间：second秒钟后
        Date to = new Date(System.currentTimeMillis() + (1000 * second));
        return schemeDao.findDeadlinesGroupbuySchemeIds(from, to);
    }
    
    @Transactional
    @Override
    public void forceBuyTicketAndFlow(Long schemeId){
    	BetSchemePO s = schemeDao.get(schemeId);
    	if(s==null)
    		return;
    	List<Long> ids=new ArrayList<Long>();
		ids.add(schemeId);
    	if(s.getSaleStatus()!=EntityStatus.SCHEME_ON_SALE || s.getType()!=EntityType.BETTING_PARTNER){
    		return;
    	}
    	//强制买票。
    	if(	s.getStatus()==EntityStatus.TICKET_BUY_SUCCESS){
    		logger.info("forceBuyTicket begin, schemeId="+s.getId()+" sponsorId="+s.getSponsorId());
    		//返换全部冻结保底金额
    		accountService.betFloorDeductAndReturn(s.getSponsorId(), new BigDecimal(0), new BigDecimal(s.getFloorAmount()), s.getId());
    		BetResult br = betSchemeBaseService.purchase(s.getSponsorId(), schemeId, s.getTotalAmount()-s.getPurchasedAmount());
    		int status = br.getAppCode();
    		if(status>0){
    			logger.error("forceBuyTicket faild schemeId="+s.getId()+" sponsorId="+s.getSponsorId()+" betSchemeBaseService.purchase return status="+status);
    			throw new RuntimeException("groupbuy  SponsorId="+s.getSponsorId()+" SchemeId="+ s.getId()+" buy remaining amount faild");
    		}
    		logger.info("forceBuyTicket end, schemeId="+s.getId()+" sponsorId="+s.getSponsorId()+" betSchemeBaseService.purchase return status="+status);
    	}else if(s.getStatus()==EntityStatus.TICKET_INIT){
    		logger.info("forceFlowTicket begin, schemeId="+s.getId()+" sponsorId="+s.getSponsorId());
    		//强制流标合买方案
        	schemeDao.forceGroupBuyFlow(schemeId);
	        //强制流标合买票
	        ticketDao.updateStatusByScheme(ids, EntityStatus.TICKET_INIT, EntityStatus.TICKET_SCHEME_FLOW, "合买流标");
	        logger.info("forceFlowTicket end, schemeId="+s.getId()+" sponsorId="+s.getSponsorId());
    	}else if(s.getStatus()==EntityStatus.TICKET_BUY_FAIL){
    		logger.info("forceTicketFailScheme change to SCHEME_STOP begin, schemeId="+s.getId()+" old_saleStatus="+s.getSaleStatus());
    		//将出票失败方案变为停止状态
    		s.setSaleStatus(EntityStatus.SCHEME_STOP);
    		logger.info("forceTicketFailScheme change to SCHEME_STOP end,  schemeId="+s.getId()+" new_saleStatus="+s.getSaleStatus());
    	}
    }

    @Transactional(readOnly=true)
	@Override
	public Map<String, List<BetSchemePO>> listJCAllowBuySchemesGroupByPlayId() {
    	return this.listAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(this.JCLotteryIdList);
	}

    /**
     * 列出尊奥没有中奖信息的ticket。按照lotteryId和期号分组。要求数据库的 issue 字段不为空。
     */
	@Override
	@Transactional(readOnly=true)
	public List<Ticket> listZunAoJCNotPrizeTicketGroupByPlayIdAndDate() {
		
		List<TicketPO> ticketPOs = ticketDao.listZunAoNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(JCLotteryIdList);
		List<Ticket> tickets = copy2NewTicketList(ticketPOs);
		List<TicketPO> hhTicketPOs = ticketDao.listZunAoNotPrizedHHTickets();
		List<Ticket> hhTickets = copy2NewTicketList(hhTicketPOs);
		tickets.addAll(hhTickets);
		return tickets;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Ticket> listHFNotPrizeTicketGroupByPlayIdAndIssue() {
		List<TicketPO> ticketPOs = ticketDao.listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndIssue(HFLotteryIdList);
		List<Ticket> tickets = copy2NewTicketList(ticketPOs);
		return tickets;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Ticket> listCQSSNotPrizeTicketGroupByPlayIdAndIssue() {
		List<TicketPO> ticketPOs = ticketDao.listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndIssue(CQSSLotteryIdList);
		List<Ticket> tickets = copy2NewTicketList(ticketPOs);
		return tickets;
	}

	private List<Ticket> copy2NewTicketList(List<TicketPO> ticketPOs) {
		List<Ticket> tickets = new ArrayList<Ticket>(ticketPOs.size());
		for (TicketPO ticketPO : ticketPOs){
			Ticket ticket = new Ticket();
			BeanUtils.copyProperties(ticketPO, ticket);
			tickets.add(ticket);
		}
		return tickets;
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, List<BetSchemeWithIssueInfoPO>> listHFAllowBuySchemesGroupByPlayId() {
		
		return this.listHFAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(this.HFLotteryIdList);
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, List<BetSchemePO>> listAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(
			List<String> targetLotteryIdList) {
		Map<String, List<BetSchemePO>> map = new HashMap<String, List<BetSchemePO>>();
		if(null!=targetLotteryIdList&&!targetLotteryIdList.isEmpty()){
			Date date = new Date(System.currentTimeMillis() - Constants.ONE_WEEK);
	        for(BetSchemePO po: schemeDao.findByStatusAndLotteryIdList(EntityStatus.TICKET_ALLOW_BUY, date,targetLotteryIdList)){
	            List<BetSchemePO> shcemeIds = map.get(po.getPlayId());
	            if(shcemeIds == null){
	                shcemeIds = new ArrayList<BetSchemePO>();
	                map.put(po.getPlayId(), shcemeIds);
	            }
	            shcemeIds.add(po);
	        }
		}
        
        
		return map;
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, List<BetSchemeWithIssueInfoPO>> listHFAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(
			List<String> targetLotteryIdList) {
		Map<String, List<BetSchemeWithIssueInfoPO>> map = new HashMap<String, List<BetSchemeWithIssueInfoPO>>();
		if(null!=targetLotteryIdList&&!targetLotteryIdList.isEmpty()){
			Date date = new Date(System.currentTimeMillis() - Constants.ONE_WEEK);
			for (BetSchemeWithIssueInfoPO po : betSchemeWithIssueInfoDao
					.findByStatusWithCurrentTime(
							EntityStatus.TICKET_ALLOW_BUY, date,
							targetLotteryIdList)) {
				List<BetSchemeWithIssueInfoPO> shcemeIds = map.get(po
						.getPlayId());
				if (shcemeIds == null) {
					shcemeIds = new ArrayList<BetSchemeWithIssueInfoPO>();
					map.put(po.getPlayId(), shcemeIds);
				}
				shcemeIds.add(po);
			}
		}
        
        
		return map;
	}

	public List<String> getJCLotteryIdList() {
		return JCLotteryIdList;
	}

	public void setJCLotteryIdList(List<String> jCLotteryIdList) {
		JCLotteryIdList = jCLotteryIdList;
	}

	public List<String> getHFLotteryIdList() {
		return HFLotteryIdList;
	}

	public void setHFLotteryIdList(List<String> hFLotteryIdList) {
		HFLotteryIdList = hFLotteryIdList;
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, List<BetSchemePO>> listCTAllowBuySchemesGroupByPlayId() {
		return this.listAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(CTLotteryIdList);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Map<String, List<BetSchemePO>> listBDAllowBuySchemesGroupByPlayId() {
		return this.listAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(bdLotteryIdList);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Ticket> listCTZCNotPrizeTickets() {
		List<TicketPO> ticketPOs = ticketDao.listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(CTLotteryIdList);
		List<Ticket> tickets = copy2NewTicketList(ticketPOs);
		return tickets;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Ticket> listBJDCNotPrizeTickets() {
		List<TicketPO> ticketPOs = ticketDao.listNotPrizeAndLotteryIdInTargetListTicket(bdLotteryIdList);
		List<Ticket> tickets = copy2NewTicketList(ticketPOs);
		return tickets;
	}
	
	public void setWFLotteryIdList(List<String> wFLotteryIdList) {
		WFLotteryIdList = wFLotteryIdList;
	}

	public List<String> getWFLotteryIdList() {
		return WFLotteryIdList;
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, List<BetSchemePO>> listWFAllowBuySchemesGroupByPlayId() {
		return this.listAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(WFLotteryIdList);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Ticket> listWFNotPrizeTickets() {
		List<TicketPO> ticketPOs = ticketDao.listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(WFLotteryIdList);
		List<Ticket> tickets = copy2NewTicketList(ticketPOs);
		return tickets;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Ticket> listNotPrizeTicketWithTargetLotteryPlatformId(
			String lotteryPlatformId) {
		List<TicketPO> ticketPOs = ticketDao.listNotPrizeTicketWithTargetLotteryPlatformId(lotteryPlatformId);
		List<Ticket> tickets = copy2NewTicketList(ticketPOs);
		return tickets;
	}

}
