package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.BonusHandle;
import com.xhcms.lottery.commons.data.weibo.WinAddBonus;
import com.xhcms.lottery.commons.lang.PMWeek;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.PMWeekBonusDao;
import com.xhcms.lottery.commons.persist.dao.PMWeekBonusPoolDao;
import com.xhcms.lottery.commons.persist.dao.PMWeekBonusRecordDao;
import com.xhcms.lottery.commons.persist.dao.PMWeekWinnersRecordDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.PMWeekBonusPO;
import com.xhcms.lottery.commons.persist.entity.PMWeekBonusPoolPO;
import com.xhcms.lottery.commons.persist.entity.PMWeekBonusRecordPO;
import com.xhcms.lottery.commons.persist.entity.PMWeekWinnersRecordPO;
import com.xhcms.lottery.commons.persist.service.GrantService;
import com.xhcms.lottery.commons.persist.service.PMWeekBonusService;

@Service("pMWeekBonusService")
public class PMWeekBonusServiceImpl implements PMWeekBonusService {
	
	@Autowired
	private PMWeekBonusDao pMWeekBonusDao;
	@Autowired
	private PMWeekBonusPoolDao pMWeekBonusPoolDao;
	@Autowired
	private PMWeekBonusRecordDao pMWeekBonusRecordDao;
	@Autowired
	private PMWeekWinnersRecordDao pMWeekWinnersRecordDao;
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
    private GrantService grantBaseService;
    @Autowired
    private MessageSender messageSender;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private SimpleDateFormat ftime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    @Transactional(readOnly=true)
    public Set<Long> findWinSchemeIds(Date beginTime, Date endTime, String[] lotteryIds){
    	return pMWeekWinnersRecordDao.findWinSchemeIds(beginTime, endTime, lotteryIds);
    }
    
    @Override
    @Transactional
    public void insertWinRecord(Long schemeId){
    	if(schemeId!=null){
			BetSchemePO schemePO = betSchemeDao.get(schemeId);
			PMWeekWinnersRecordPO record = new PMWeekWinnersRecordPO();
			record.setCreatedTime(new Date());
			record.setGranttypeId(53L);
			record.setMaxAmount(schemePO.getAfterTaxBonus().divide(new BigDecimal(5),2,BigDecimal.ROUND_DOWN ));
			record.setScheme(schemePO);
			record.setStatus(PMWeek.WinnersRecord.not_prize);
			record.setUserId(schemePO.getSponsorId());
			record.setUserName(schemePO.getSponsor());
			pMWeekWinnersRecordDao.save(record);
		}
    }
    
    @Override
    @Transactional(readOnly=true)
	public void autoAddBonus(){
		PMWeekBonusPO wb = pMWeekBonusDao.get(1L);
		logger.info("addBonus to bonusPool !");
		if(wb!= null && wb.getStatus()==PMWeek.Bonus.valid && wb.getAmount() >0){
			messageSender.send(new BonusHandle(new BigDecimal(wb.getAmount()), PMWeek.BonusRecord.into));
		}
	}
    
    @Override
    @Transactional(readOnly=true)
    public PMWeekWinnersRecordPO getWinnerRecordBySid(Long schemeId){
    	return pMWeekWinnersRecordDao.getBySid(schemeId);
    }
    
    @Override
    @Transactional(readOnly=true)
    public PMWeekWinnersRecordPO getWinnerRecordByid(Long rid){
    	return pMWeekWinnersRecordDao.get(rid);
    }
    
    @Override
    @Transactional
    public void prize(Long userId, Long bonusRecordId){
    	if(bonusRecordId == null || bonusRecordId < 1 || userId == null || userId<0){
    		return;
    	}
    	PMWeekWinnersRecordPO wr = pMWeekWinnersRecordDao.get(bonusRecordId);
    	if(wr == null){
    		logger.error("WinnersRecord rid={}  not found! uid={}", bonusRecordId , userId);
    		return;
    	}
    	if(wr.getStatus() != PMWeek.WinnersRecord.not_prize){
    		logger.error("WinnersRecord's status is wrong! status={} , rid={}", wr.getStatus(), bonusRecordId);
    		return;
    	}
    	Calendar expireTime = Calendar.getInstance();
		expireTime.setTime(wr.getCreatedTime());
		expireTime.add(Calendar.DAY_OF_MONTH, +1);
		if(new Date().after(expireTime.getTime())){
			wr.setStatus(PMWeek.WinnersRecord.expire);
			logger.error("recordId={} , have expired!",bonusRecordId);
			return;
		}
    	messageSender.send(new BonusHandle(bonusRecordId, PMWeek.BonusRecord.prize));
    }
    
    @Override
    @Transactional
    public void addBonus(BigDecimal amount){
		PMWeekBonusPoolPO bonusPool = pMWeekBonusPoolDao.get(1L);
		//bonusPool.setBonusPool(bonusPool.getBonusPool().add(amount));
		bonusPool.setBonusPool(amount);	//重置奖池
		bonusPool.setTotalRecharge(bonusPool.getTotalRecharge().add(amount));
		saveBonusRecord(amount, 1L, PMWeek.BonusRecord.into);
		logger.info("addBonus={}, userId={} success!", amount, 1L);
	}
    
    @Override
    @Transactional
    public boolean subtractBonus(Long recordId){
    	PMWeekWinnersRecordPO winnerRecord = pMWeekWinnersRecordDao.get(recordId);
		if(winnerRecord == null){
			logger.error("recordId={} is not found!",recordId);
			return false;
		}
		Long userId = winnerRecord.getUserId();
		// 是否未派奖
		if(winnerRecord.getStatus() != PMWeek.WinnersRecord.not_prize){
			logger.error("recordId={} status={} status error ! should={}", 
					new Object[]{recordId, winnerRecord.getStatus(),PMWeek.WinnersRecord.not_prize});
			return false;
		}
		// 是否过期
		Calendar expireTime = Calendar.getInstance();
		expireTime.setTime(winnerRecord.getCreatedTime());
		expireTime.add(Calendar.DAY_OF_MONTH, +1);
		logger.info("当前时间：{}，过期时间：{}",ftime.format(new Date().getTime()),ftime.format(expireTime.getTime()));
		if(new Date().after(expireTime.getTime())){
			winnerRecord.setStatus(PMWeek.WinnersRecord.expire);
			logger.error("recordId={} , have expired!",recordId);
			return false;
		}
		// 是否当前用户
		if(!winnerRecord.getUserId().equals(userId)){
			logger.error("userId error! recordId={}, userId should={} but userId={}", 
					new Object[]{recordId, winnerRecord.getUserId(), userId});
			return false;
		}
		PMWeekBonusPoolPO bonusPoolPO = pMWeekBonusPoolDao.get(1L);
    	BigDecimal bonusPool = bonusPoolPO.getBonusPool();
		// 如果奖池奖金小于等于0 
    	if(bonusPool.compareTo(BigDecimal.ZERO)==-1 ||
    			bonusPool.compareTo(BigDecimal.ZERO)==0){
    		logger.info("bonusPool's bonus not enough!");
    		return false;
    	}
		// 派奖
		BigDecimal maxAmount = winnerRecord.getMaxAmount();
		BigDecimal realAmount = BigDecimal.ZERO;
		if(bonusPool.compareTo(maxAmount)==1 || bonusPool.compareTo(maxAmount)==0){
			realAmount = maxAmount;
			bonusPoolPO.setBonusPool(bonusPoolPO.getBonusPool().subtract(realAmount));
		}else{
			realAmount = bonusPoolPO.getBonusPool();
			bonusPoolPO.setBonusPool(BigDecimal.ZERO);
		}
		// 保存奖池
		bonusPoolPO.setTotalAward(bonusPoolPO.getTotalAward().add(realAmount));
		pMWeekBonusPoolDao.save(bonusPoolPO);
		// 保存派奖纪录
		saveBonusRecord(realAmount, userId, PMWeek.BonusRecord.prize);
		// 更新中奖纪录
		winnerRecord.setOperatorTime(new Date());
		winnerRecord.setStatus(PMWeek.WinnersRecord.prize);
		winnerRecord.setRealAmount(realAmount);
		pMWeekWinnersRecordDao.save(winnerRecord);
		// 赠款
		grantBaseService.autoGrant(userId, realAmount, 53L, "中奖加奖");
		logger.info("price success! recordId={}, userId={}, amount={}", new Object[]{recordId, userId, realAmount});
		
		
		//中奖加奖微博推送
		BetSchemePO schemePO = winnerRecord.getScheme();
		if(null != schemePO && schemePO.getId() > 0) {
			WinAddBonus winAddBonus = new WinAddBonus(userId, schemePO.getId(), realAmount);
			messageSender.send(winAddBonus);
			logger.info("用户userId={},中奖加奖记录={},schemeId={}", new Object[]{userId, recordId, schemePO.getId()});
		} else {
			logger.error("用户userId={},中奖加奖记录={},中奖加奖微博无法发出", new Object[]{userId, recordId});
		}
		
		return true;
    }
    
    private void saveBonusRecord(BigDecimal amount, Long userId, int type){
    	PMWeekBonusRecordPO record = new PMWeekBonusRecordPO();
		record.setAmount(amount);
		record.setCreatedTime(new Date());
		record.setType(type);
		record.setUserId(userId);
		pMWeekBonusRecordDao.save(record);
    }

	@Override
	@Transactional
	public Double getBonusPool() {
		return pMWeekBonusPoolDao.get(1L).getBonusPool().doubleValue();
	}

	/**
	 * 查询能进行加奖的中奖纪录
	 */
	@Transactional
	@Override
	public List<PMWeekWinnersRecordPO> findCanAwardRecordsByUsers(Collection<Long> ids) {
		List<PMWeekWinnersRecordPO> records = pMWeekWinnersRecordDao.getRecordsByUsers(ids);
		return records;
	}
}
