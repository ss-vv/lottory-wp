package com.unison.lottery.pm.jms.handle;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.data.LevelUser;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.BonusHandle;
import com.xhcms.lottery.commons.persist.entity.PMWeekWinnersRecordPO;
import com.xhcms.lottery.commons.persist.service.PMWeekBonusService;

/**
 * ‘中奖加奖’活动消息处理类
 * 1.给奖池增加金额
 * 2.用户申请领取奖池奖金，给用户配发奖金
 * @author lei.li@davcai.com
 */
public class BonusHandler implements MessageHandler<BonusHandle> {

	private static final long serialVersionUID = -4300938849239120574L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PMWeekBonusService pMWeekBonusService;
	@Autowired
	private LevelUser levelUser;
	private final int expertJoinLimit = 200;//特殊用户优先加奖的奖池阀值

	@Override
	public void handle(BonusHandle bh) {
		if (bh == null
				|| (bh.getWinnersRecordId() == null && bh.getAmount() == null)
				|| (bh.getWinnersRecordId() == null && bh.getAmount()
						.compareTo(BigDecimal.ONE) == -1)) {
			return;
		}
		if (bh.getWinnersRecordId() == null
				&& bh.getAmount().compareTo(BigDecimal.ZERO) == 1) {//补充奖池金额
			logger.info("start BonusPool recharge!");
			try {
				pMWeekBonusService.addBonus(bh.getAmount());
			} catch (Exception e) {
				logger.error("BonusPool recharge exception:", e);
			}
			logger.info("end BonusPool recharge!");
		} else if (bh.getWinnersRecordId() != null
				&& bh.getWinnersRecordId() > 0) {//用户申请领奖
			logger.info("BonusPool: user start award!");
			try {
				Double bonusPool = pMWeekBonusService.getBonusPool();
				if(bonusPool > expertJoinLimit) {
					boolean result = scanExpertAccount();
					if(result) {
						pMWeekBonusService.subtractBonus(bh.getWinnersRecordId());
					} else {
						logger.error("特殊账号={},优先加奖失败！无法发给普通用户加奖.", levelUser.getUserList());
					}
				} else if(bonusPool > 0 && bonusPool <= expertJoinLimit) {
					pMWeekBonusService.subtractBonus(bh.getWinnersRecordId());
				}
			} catch (Exception e) {
				logger.error("BonusPool:user award exception:", e);
			}
			logger.info("BonusPool: user end award!");
		}
	}

	/**首先扫描专家号**/
	private boolean scanExpertAccount() {
		List<Long> userList = levelUser.getUserList();
		boolean result = true;
		if(null != userList && userList.size() > 0) {
			List<PMWeekWinnersRecordPO> resultList = pMWeekBonusService.findCanAwardRecordsByUsers(userList);
			if(null != resultList) {
				for(PMWeekWinnersRecordPO po : resultList) {
					boolean rs = pMWeekBonusService.subtractBonus(po.getId());
					logger.info("优先加奖用户ID={},结果={}", po.getUserId(), rs);
					if(!rs) {
						result = rs; 
						break;
					}
				}
			}
		}
		return result;
	}
}
