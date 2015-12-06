package com.unison.lottery.pm.service.impl;

import java.math.BigInteger;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.pm.data.CountResult;
import com.unison.lottery.pm.persist.BetSchemeDao;
import com.unison.lottery.pm.persist.PMGrantAccountDao;
import com.unison.lottery.pm.persist.PMRechargeGrantDao;
import com.unison.lottery.pm.service.CountService;

public class CountServiceImpl implements CountService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private PMRechargeGrantDao pMRechargeGrantDao;
	@Autowired
	private PMGrantAccountDao pMGrantAccountDao;
	@Autowired
	private BetSchemeDao bSchemeDao;

	@Override
	@Transactional
	public void count() {
		String sdata = "'13418905832','13418894630','13418848197','13418846707','13418798446','13418777542','13418751330','13418600636','13418586467','13418568253','13418479787','13418459518','13581797190','13418425625','13418416139','13418047926','13417994340','13417980970','13417972806','13417886128','13417724123','13417568233','13417558588','13417494716','13417381461','13417345620','13417258128','13417188923','13417052797','13417048800','13416945382','13416841503','13416816816','13416725693','13416677366','13416607872','13416512351','13416401274','13416386383','13416371784','13415950864','13415835798','13415773790','13415764477','13415725064','13415703477','13415682386','13415571366','13415454573','13415382103','13415132743','13414863067','13414711069','13414516749','13414383315','13414222084','13414176669','13414102244','13413961765','13413874517','13413806974','13413805738','13413730700','13413710018','13413701454','13413575318','13413466653','13413334441','13413317407','13413268986','13413259129','13413216545','13413102156','13413024276','13412792535','13412694388','13412481673','13412476199','13412060093','13411909724','13411795134','13411740935','13411674107','13411321852','13411292639','13411244814','13411041358','13411026019','13411002114','13410931163','13410882019','13410822380','13410693159','13410536745','13581909765','13410469246','13410255051','13410126069','13410073351','13410047955','13410035728','13409966033','13400930337'";
		List<CountResult> results = pMRechargeGrantDao.countRegistNum(sdata);
		if(null != results && results.size() > 0) {
			for(CountResult cr : results) {
				List<Long> userIds = pMRechargeGrantDao.getUserIdByMobile(cr.getMobile());
				String ids = "";
				if(null != userIds && userIds.size() > 0) {
					for(Long userId : userIds) {
						ids = ids +userId +",";
					}
				}
				ids = ids.substring(0,ids.length()-1);
				CountResult crech = pMGrantAccountDao.getRecharge(ids);
				if(crech != null) {
					cr.setRechargeNum(crech.getNum());
					cr.setRechargeAmount(crech.getAmount());
				}
				CountResult cbet = pMGrantAccountDao.getBet(ids);
				if(cbet != null) {
					cr.setBetNum(cbet.getNum());
					cr.setBetAmount(cbet.getAmount());
				}
			}
		}
		
		log.info("results num={}" + results.size());
		if(null != results && results.size() > 0) {
			for(CountResult r :results) {
				System.out.println(r.getMobile()+"|"+r.getRegistNum()+"|"+r.getRechargeNum()+"|"+r.getRechargeAmount()+"|"+r.getBetNum()+"|"+r.getBetAmount());
			}
		}
		
	}
	
	
	@Override
	@Transactional
	public void updateAmount(String date) {
		List<BigInteger> ids = bSchemeDao.findByIsShow(date);
		if(null != ids && ids.size() > 0) {
			log.info("show scheme num={}", ids.size());
			for(BigInteger id : ids) {
				bSchemeDao.updateAmount(id.longValue());
			}
			
		}
	}
	
}
