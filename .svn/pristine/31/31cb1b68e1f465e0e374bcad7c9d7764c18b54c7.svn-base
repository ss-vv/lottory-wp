package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.data.KellyFormula;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBOddsEuroDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBEuroOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsEuroPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBEuroOddsPO;
import com.unison.lottery.weibo.data.service.store.persist.service.KellyFormulaService;

@Service
public class KellyFormulaServiceImpl implements KellyFormulaService {

	@Autowired
	private FBEuroOddsDao euroOddsDao;
	
	@Autowired
	private BBOddsEuroDao bbOddsEuroDao;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Transactional
	@Override
	public KellyFormula calFBArgRatio(long qtMatchId) {
		List<FBEuroOddsPO> euroOddList = euroOddsDao.findEuropeOddsList(qtMatchId);
		KellyFormula kellyFormula = new KellyFormula();
		if(null != euroOddList && euroOddList.size() > 0) {
			int scale = 3;
			BigDecimal cnt = new BigDecimal(euroOddList.size());
			BigDecimal init = new BigDecimal(1);
			BigDecimal homeOddCount = new BigDecimal(0);
			BigDecimal drawOddCount = new BigDecimal(0);
			BigDecimal guestOddCount = new BigDecimal(0);
			
			BigDecimal argHomeOddPer = null;
			BigDecimal argDrawOddPer = null;
			BigDecimal argGuestOddPer = null;
			
			for(FBEuroOddsPO po : euroOddList) {
				BigDecimal initHomeWinOdds = po.getCurHomeWinOdds();
				BigDecimal initDrawOdds = po.getCurDrawOdds();
				BigDecimal initGuestWinOdds = po.getCurGuestWinOdds();
				//赔率倒数之和
				BigDecimal oddReciprocalCnt = init.divide(initHomeWinOdds, scale, BigDecimal.ROUND_DOWN)
						.add(init.divide(initDrawOdds, scale, BigDecimal.ROUND_DOWN))
						.add(init.divide(initGuestWinOdds, scale, BigDecimal.ROUND_DOWN));
				
				//赔付率（返还率）
				BigDecimal lossRatio = init.divide(oddReciprocalCnt, scale, BigDecimal.ROUND_DOWN);
				
				//主胜率
				BigDecimal homePercent = lossRatio.divide(initHomeWinOdds, scale, BigDecimal.ROUND_DOWN);
				BigDecimal drawPercent = lossRatio.divide(initDrawOdds, scale, BigDecimal.ROUND_DOWN);
				BigDecimal guestPercent = lossRatio.divide(initGuestWinOdds, scale, BigDecimal.ROUND_DOWN);
				
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				log.debug("赔率公司={},赔付率={}", new Object[]{po.getCorpId(), lossRatio});
				log.debug("赔率公司={},主胜率={}", new Object[]{po.getCorpId(), homePercent});
				log.debug("赔率公司={},平率={}", new Object[]{po.getCorpId(), drawPercent});
				log.debug("赔率公司={},负率={}", new Object[]{po.getCorpId(), guestPercent});
				log.debug(".................................");
				
				homeOddCount = homeOddCount.add(homePercent);
				drawOddCount = drawOddCount.add(drawPercent);
				guestOddCount = guestOddCount.add(guestPercent);
			}
			argHomeOddPer = homeOddCount.divide(cnt, scale, BigDecimal.ROUND_DOWN);
			argDrawOddPer = drawOddCount.divide(cnt, scale, BigDecimal.ROUND_DOWN);
			argGuestOddPer = guestOddCount.divide(cnt, scale, BigDecimal.ROUND_DOWN);
			kellyFormula.setArgWin(argHomeOddPer);
			kellyFormula.setArgFlat(argDrawOddPer);
			kellyFormula.setArgNegative(argGuestOddPer);
			
			log.debug("平均主胜率={}", argHomeOddPer);
			log.debug("平均平率={}", argDrawOddPer);
			log.debug("平均负率={}", argGuestOddPer);
		}
		return kellyFormula;
	}

	@Transactional
	@Override
	public KellyFormula calBBArgRatio(long qtMatchId) {
		List<BBOddsEuroPO> bbOddsEuroList = bbOddsEuroDao.findEuropeOddsList(qtMatchId);
		KellyFormula kellyFormula = new KellyFormula();
		if(null != bbOddsEuroList && bbOddsEuroList.size() > 0) {
			int scale = 3;
			BigDecimal cnt = new BigDecimal(bbOddsEuroList.size());
			BigDecimal init = new BigDecimal(1);
			BigDecimal homeOddCount = new BigDecimal(0);
			BigDecimal guestOddCount = new BigDecimal(0);
			
			BigDecimal argHomeOddPer = null;
			BigDecimal argGuestOddPer = null;
			
			for(BBOddsEuroPO po : bbOddsEuroList) {
				BigDecimal initHomeWinOdds = po.getRealtimeHomeWinOdds();
				BigDecimal initGuestWinOdds = po.getRealtimeGuestWinOdds();
				//赔率倒数之和
				BigDecimal oddReciprocalCnt = init.divide(initHomeWinOdds, scale, BigDecimal.ROUND_DOWN)
						.add(init.divide(initGuestWinOdds, scale, BigDecimal.ROUND_DOWN));
				
				//赔付率（返还率）
				BigDecimal lossRatio = init.divide(oddReciprocalCnt, scale, BigDecimal.ROUND_DOWN);
				
				//主胜率
				BigDecimal homePercent = lossRatio.divide(initHomeWinOdds, scale, BigDecimal.ROUND_DOWN);
				BigDecimal guestPercent = lossRatio.divide(initGuestWinOdds, scale, BigDecimal.ROUND_DOWN);
				
				log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				log.debug("赔率公司={},赔付率={}", new Object[]{po.getCorpId(), lossRatio});
				log.debug("赔率公司={},胜率={}", new Object[]{po.getCorpId(), homePercent});
				log.debug("赔率公司={},负率={}", new Object[]{po.getCorpId(), guestPercent});
				log.debug(".................................");
				
				homeOddCount = homeOddCount.add(homePercent);
				guestOddCount = guestOddCount.add(guestPercent);
			}
			argHomeOddPer = homeOddCount.divide(cnt, scale, BigDecimal.ROUND_DOWN);
			argGuestOddPer = guestOddCount.divide(cnt, scale, BigDecimal.ROUND_DOWN);
			kellyFormula.setArgWin(argHomeOddPer);
			kellyFormula.setArgNegative(argGuestOddPer);
			
			log.debug("平均主胜率={}", argHomeOddPer);
			log.debug("平均负率={}", argGuestOddPer);
		}
		return kellyFormula;
	}

	@Override
	public BigDecimal lossRatio(BigDecimal... odds) {
		BigDecimal init = new BigDecimal(1);
		int scale = 3;
		//赔率倒数之和
		BigDecimal oddReciprocalCnt = new BigDecimal(0);
		for(int i=0; i<odds.length; i++) {
			BigDecimal odd = odds[i];
			oddReciprocalCnt = oddReciprocalCnt.add(init.divide(odd, scale, BigDecimal.ROUND_DOWN));
		}
		//赔付率（返还率）
		BigDecimal lossRatio = init.divide(oddReciprocalCnt, scale, BigDecimal.ROUND_DOWN);
		return lossRatio;
	}

}
