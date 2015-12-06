package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetRecord;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Order;
import com.xhcms.lottery.commons.data.PMWeekWinnersRecord;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.OrderDao;
import com.xhcms.lottery.commons.persist.dao.RechargeDao;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.OrderPO;
import com.xhcms.lottery.commons.persist.entity.PMWeekWinnersRecordPO;
import com.xhcms.lottery.commons.persist.entity.RechargePO;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.GetPresetSchemeService;
import com.xhcms.lottery.commons.persist.service.PMWeekBonusService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.utils.PagingUtils;
import com.xhcms.lottery.utils.ResultTool;

@Transactional
public class AccountQueryServiceImpl implements AccountQueryService {
    @Autowired
    private BetPartnerDao betPartnerDao;
    @Autowired
    private WithdrawDao withdrawDao;
    @Autowired
    private RechargeDao rechargeDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GetPresetSchemeService getPresetSchemeService;
    @Autowired
    private PMWeekBonusService pMWeekBonusService;
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal[] sumBonus(Long schemeId, Long userId) {
        BigDecimal[] sums = { BigDecimal.ZERO, BigDecimal.ZERO };
        Object[] o = betPartnerDao.sumBonus(schemeId, userId);

        if (o != null) {
            for (int i = 0; i < 2; i++) {
                if (o[i] != null) {
                    sums[i] = new BigDecimal(o[i].toString());
                }
            }
        }
        return sums;
    }

    @Override
    @Transactional(readOnly = true)
    public void listWithdraw(Long userId, Date begin, Date end, int status, Paging paging) {
        if(end != null){
            end = DateUtils.addDays(end, 1);
        }
        List<WithdrawPO> list = withdrawDao.find(userId, null, begin, end, status, paging);
        List<Withdraw> results = new ArrayList<Withdraw>(list.size());
        Withdraw wh = null;
        for (WithdrawPO po : list) {
        	wh = new Withdraw();
            BeanUtils.copyProperties(po, wh);
            results.add(wh);
        }
        paging.setResults(results);
    }

    @Override
    @Transactional(readOnly = true)
    public void listRecharge(Long userId, Date begin, Date end, int status, Paging paging) {
        if(end != null){
            end = DateUtils.addDays(end, 1);
        }
        List<RechargePO> list = rechargeDao.find(userId, null, begin, end, status, paging);
        List<Recharge> results = new ArrayList<Recharge>();
        Recharge rh = null;
        for (RechargePO po : list) {
            rh = new Recharge();
            BeanUtils.copyProperties(po, rh);
            results.add(rh);
        }
        paging.setResults(results);
    }

    @Override
    @Transactional(readOnly = true)
	public List<BetRecord> listBetHistory(String lotteryId, Long userId, Date begin, Date end, int status,  int firstResult,int maxResult,int type,int showScheme) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		List<BetPartnerPO> list = betPartnerDao.find(lotteryId, userId, begin, end, status, firstResult,maxResult,type,showScheme);
		
		List<BetRecord> results = new ArrayList<BetRecord>(list.size());
		for (BetPartnerPO po : list) {
			BetRecord br = new BetRecord();
			BetSchemePO spo = po.getScheme();
			
			br.setId(spo.getId());
			br.setBetAmount(po.getBetAmount());
			br.setCreatedTime(po.getCreatedTime());
			br.setPlayId(spo.getPlayId());
			br.setSaleStatus(spo.getSaleStatus());
			br.setSponsorId(spo.getSponsorId());
			br.setSponsor(spo.getSponsor());
			lotteryId = spo.getLotteryId();
			if(Constants.JX11.equals(lotteryId)){
				// TODO 设置高频彩期号
			}else if(Constants.CTZC.equals(lotteryId)){
				// TODO 设置传统足彩期号
			}
			br.setLotteryId(lotteryId);
			br.setStatus(spo.getStatus());
			if (po.getWinAmount()!=null){
				br.setWinAmount(po.getWinAmount());
			}
			br.setIssue(df.format(spo.getCreatedTime()));
			br.setType(spo.getType());
			br.setShowScheme(spo.getShowScheme());
			
			BetScheme s=new BetScheme();
			BeanUtils.copyProperties(spo, s);
			br.setProgress(ResultTool.progress(s));
			
			results.add(br);
		}
		
		return results;
	}

    @Override
    @Transactional(readOnly = true)
	public void listBetHistory(String lotteryId, Long userId, Date begin, Date end, int status, Paging paging,int type,int showScheme) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		List<BetPartnerPO> list = betPartnerDao.find(lotteryId, userId, begin, end, status, paging,type,showScheme);
		
		List<BetRecord> results = new ArrayList<BetRecord>(list.size());
		for (BetPartnerPO po : list) {
			BetRecord br = new BetRecord();
			BetSchemePO spo = getPresetSchemeService.getRightSchemeByPO(po.getScheme());
			
			PMWeekWinnersRecordPO recordPO = pMWeekBonusService.getWinnerRecordBySid(spo.getId());
			if(null != recordPO){
				PMWeekWinnersRecord record = new PMWeekWinnersRecord();
				BeanUtils.copyProperties(recordPO, record);
				br.setRecord(record);
			}
			
			br.setId(spo.getId());
			br.setBetAmount(po.getBetAmount());
			br.setCreatedTime(po.getCreatedTime());
			br.setPlayId(spo.getPlayId());
			br.setSaleStatus(spo.getSaleStatus());
			br.setSponsorId(spo.getSponsorId());
			br.setSponsor(spo.getSponsor());
			br.setLotteryId(spo.getLotteryId());
			br.setStatus(spo.getStatus());
			br.setIsPublishShow(spo.getIsPublishShow());
			if (po.getWinAmount()!=null){
				br.setWinAmount(po.getWinAmount());
			}
			br.setIssue(df.format(spo.getCreatedTime()));
			br.setType(spo.getType());
			br.setShowScheme(spo.getShowScheme());
			
			BetScheme s=new BetScheme();
			BeanUtils.copyProperties(spo, s);
			br.setProgress(ResultTool.progress(s));
			
			results.add(br);
		}
		
		paging.setResults(results);
	}
    
	@Override
	@Transactional(readOnly = true)
	public void listJournal(Paging paging, Long userId,int type, Date from, Date to) {
		List<OrderPO> orderPOs=orderDao.list(paging, userId, type, from, to);
		if(orderPOs!=null)
			PagingUtils.makeCopyAndSetPaging(orderPOs, paging, Order.class);
	}

    @Override
    @Transactional(readOnly = true)
    public void listBetRecordByDuration(Long userId,String lotteryId,int duration,int status, Paging paging,int type,int showScheme){
    	List<BetRecord> results = listBetRecord(userId, lotteryId, duration, status, paging, type, showScheme);
		paging.setResults(results);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BetRecord> listBetRecord(Long userId,String[] lotteryId,int duration,int status, Paging paging,int type,int showScheme){
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    	List<BetPartnerPO>	 list = betPartnerDao.find(userId, lotteryId, duration, status,  paging, type, showScheme);
		List<BetRecord> results = new ArrayList<BetRecord>(list.size());
		for (BetPartnerPO po : list) {
			BetRecord br = new BetRecord();
			BetSchemePO spo = po.getScheme();
			
			br.setId(spo.getId());
			br.setBetAmount(po.getBetAmount());
			br.setCreatedTime(po.getCreatedTime());
			br.setPlayId(spo.getPlayId());
			br.setSaleStatus(spo.getSaleStatus());
			br.setSponsorId(spo.getSponsorId());
			br.setSponsor(spo.getSponsor());
			br.setLotteryId(spo.getLotteryId());
			br.setStatus(spo.getStatus());
			if (po.getWinAmount()!=null){
				br.setWinAmount(po.getWinAmount());
			}
			br.setIssue(df.format(spo.getCreatedTime()));
			br.setType(spo.getType());
			br.setShowScheme(spo.getShowScheme());
			
			BetScheme s=new BetScheme();
			BeanUtils.copyProperties(spo, s);
			br.setProgress(ResultTool.progress(s));
			
			results.add(br);
		}
    	return results;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BetRecord> listBetRecord(Long userId,String lotteryId,int duration,int status, Paging paging,int type,int showScheme){
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    	List<BetPartnerPO>	 list = betPartnerDao.find(userId, lotteryId, duration, status,  paging, type, showScheme);
		List<BetRecord> results = new ArrayList<BetRecord>(list.size());
		for (BetPartnerPO po : list) {
			BetRecord br = new BetRecord();
			BetSchemePO spo = po.getScheme();
			
			br.setId(spo.getId());
			br.setBetAmount(po.getBetAmount());
			br.setCreatedTime(po.getCreatedTime());
			br.setPlayId(spo.getPlayId());
			br.setSaleStatus(spo.getSaleStatus());
			br.setSponsorId(spo.getSponsorId());
			br.setSponsor(spo.getSponsor());
			br.setLotteryId(spo.getLotteryId());
			br.setStatus(spo.getStatus());
			if (po.getWinAmount()!=null){
				br.setWinAmount(po.getWinAmount());
			}
			br.setIssue(df.format(spo.getCreatedTime()));
			br.setType(spo.getType());
			br.setShowScheme(spo.getShowScheme());
			
			BetScheme s=new BetScheme();
			BeanUtils.copyProperties(spo, s);
			br.setProgress(ResultTool.progress(s));
			
			results.add(br);
		}
    	return results;
    }
    
    @Transactional(readOnly=true)
	@Override
	public List<BigInteger> findRechargeUserId() {
		return rechargeDao.findRechargeUserId();
	}
    
}