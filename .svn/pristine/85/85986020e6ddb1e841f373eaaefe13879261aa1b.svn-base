package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BBMatch;
import com.xhcms.lottery.commons.data.BetSchemePreset;
import com.xhcms.lottery.commons.data.FBMatch;
import com.xhcms.lottery.commons.data.PresetResult;
import com.xhcms.lottery.commons.data.PresetResultPerPlayId;
import com.xhcms.lottery.commons.data.bonus.BonusPerNote;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.BetPartnerPresetDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemePresetDao;
import com.xhcms.lottery.commons.persist.dao.CTBetContentDao;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.dao.TicketPresetDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPresetPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePresetPO;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.entity.TicketPresetPO;
import com.xhcms.lottery.commons.persist.service.PresetAwardService;
import com.xhcms.lottery.commons.util.MatchResults;
import com.xhcms.lottery.commons.util.OptionUtils;
import com.xhcms.lottery.commons.util.PrizesResolver;
import com.xhcms.lottery.commons.utils.CTFBMatchResultUtil;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.lang.PresetAward;
import com.xhcms.lottery.lang.PresetCheckStatus;
import com.xhcms.lottery.utils.NumberUtils;
import com.xhcms.lottery.utils.POUtils;

/**
 * 预兑奖
 * @author Wang Lei
 *
 */
public class PresetAwardServiceImpl implements PresetAwardService {

	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private PrizesResolver prizesResolver;
	@Autowired
	private TicketPresetDao ticketPresetDao;
	@Autowired
	private BetPartnerDao betPartnerDao;
	@Autowired
    private BetPartnerPresetDao betPartnerPresetDao;
	@Autowired
	private FBMatchDao fBMatchDao;
	@Autowired
	private FBMatchPlayDao fBMatchPlayDao;
	@Autowired
	private BBMatchDao bBMatchDao;
	@Autowired
	private BBMatchPlayDao bBMatchPlayDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
	private BetMatchDao betMatchDao;
	@Autowired
	private BetSchemePresetDao betSchemePresetDao;
	
	@Autowired
	private IssueInfoDao issueInfoDao;
	
	@Autowired
	private CTBetContentDao ctBetContentDao;
	
	@Autowired
	private CTFBMatchDao ctfbMatchDao;
	@Autowired
	private CTFBMatchResultUtil ctfbMatchResultUtil;
	
	@Override
	@Transactional
	public void checkPresetAward(){
		//TODO 核对预兑奖方案
	}
	
	@Override
	@Transactional
	public List<BetSchemePreset> list(BetSchemePreset betSchemePreset,Date from, Date to,Paging paging){
		return POUtils.copyBeans(betSchemePresetDao.list(betSchemePreset, from, to,paging), BetSchemePreset.class);
	}

	@Override
	@Transactional(readOnly=true)
	/** 查找竞彩足球可预兑奖方案id集合 */
	public List<Long> findAllowPrizesFB(List<Long> matchIds){
		if(null == matchIds || matchIds.isEmpty()){
			return new ArrayList<Long>();
		}
		List<Object[]> list = betSchemePresetDao.findAllowPrizesJC(matchIds, Constants.JCZQ);
		return filterMatch(list, matchIds);
	}
	
	@Override
	@Transactional(readOnly=true)
	/** 查找竞彩足球可预兑奖方案id集合 */
	public List<Long> findAllowPrizesBB(List<Long> matchIds) {
		if(null == matchIds || matchIds.isEmpty()){
			return new ArrayList<Long>();
		}
		List<Object[]> list = betSchemePresetDao.findAllowPrizesJC(matchIds, Constants.JCLQ);
		return filterMatch(list, matchIds); 
	}
	
	private List<Long> filterMatch(List<Object[]> list , List<Long> matchIds){
		List<Long> result = new ArrayList<Long>();
		for(Object[] o : list){
			Long schemeId = Long.parseLong(o[1].toString());
			boolean in = true;
			for(BetMatchPO match : betMatchDao.findBySchemeId(schemeId)){
				if(!matchIds.contains(match.getMatchId())){
					in = false;
					break;
				}
			}
			if(in){
				result.add(schemeId);
			}
		}
		return result;
	}
	
	/** 检查预兑奖源方案和票 */
	@Transactional
	private List<TicketPO> checkScheme(Long schemeId, PresetAward presetPrizesStatus){
		BetSchemePO schemePO = betSchemeDao.get(schemeId);
		if(null == schemePO || schemePO.getStatus() != EntityStatus.TICKET_BUY_SUCCESS){
			log.error("源方案不存在或已被处理，处理结束，schemeId：{}", schemeId);
			return null;
		}
		int presetStatus = presetPrizesStatus.getValue();
		if(schemePO.getIsPresetAward() != presetStatus){
			log.error("源方案预兑奖状态应为:{}, 实际为:{}，处理结束，schemeId：" + schemeId, presetStatus+" "
					+presetPrizesStatus.getName(), schemePO.getIsPresetAward());
			throw new RuntimeException("源方案预兑奖状态应为:" +presetStatus+" "+presetPrizesStatus.getName()+
					", 实际为:"+schemePO.getIsPresetAward()+"，处理结束，schemeId：" + schemeId);
		}
		
		/**
		 * by lei.li@davcai.com
		 * 修改预兑奖逻辑，只要出票成功就可以预兑奖
		 * 1.如果方案中有一张票已经“中奖未派奖”则会重新对张票进行预派奖;
		 * 2.如果方案中票全是“出票成功”状态，则直接对方案中所有票进行预兑奖
		 * 3.说明：因为方案中不会出现有一张票票是“已派奖”（派奖操作是对整个方案进行的），而其他票的状态还未达到最终状态
		 */
		List<Integer> status = new ArrayList<Integer>();
		status.add(EntityStatus.TICKET_BUY_SUCCESS);
		status.add(EntityStatus.TICKET_NOT_AWARD);
		status.add(EntityStatus.TICKET_NOT_WIN);
		List<TicketPO> poList =  ticketDao.findByScheme(schemeId, status);
		if(null == poList || poList.isEmpty() || schemePO.getTicketCount() != poList.size()) {
			log.warn("源方案票已被兑奖，处理结束，schemeId：{}", schemeId);
		}
		
		return poList;
	}
	
	@Override
	@Transactional
	public void cancelPresetPrizes(List<Long> id){
		for(Long schemeId : id){
			List<TicketPO> ticketPOs = checkScheme(schemeId, PresetAward.Is_PresetAward);
			if(null == ticketPOs || ticketPOs.isEmpty()){
				return;
			}
			BetSchemePO betSchemePO = betSchemeDao.get(schemeId);
			int presetStatus = PresetAward.Not_PresetAward.getValue();
			betSchemePO.setIsPresetAward(presetStatus);
			BetSchemePresetPO betSchemePresetPO = betSchemePresetDao.get(schemeId);
			if(null == betSchemePresetPO){
				throw new RuntimeException("预兑奖方案不存在！scheme:"+schemeId);
			}
			if(betSchemePresetPO.getStatus() == EntityStatus.TICKET_AWARDED){
				throw new RuntimeException("不能撤销已派奖预兑奖方案！scheme:"+schemeId);
			}
			betSchemePresetDao.delete(betSchemePresetPO);
			for(TicketPO t : ticketPOs){
				ticketPresetDao.deleteById(t.getId());
				t.setIsPresetAward(presetStatus);
			}
			for(BetPartnerPresetPO part : betPartnerPresetDao.findBySchemeId(schemeId)){
				betPartnerPresetDao.delete(part);
			}
		}
	}
	
	/** 预兑奖 */
	@Override
	@Transactional
	public boolean presetPrizes(Long schemeId, MatchResults matchResults) {
		log.info("开始创建方案{}", schemeId);
		List<TicketPO> sourceTickets = checkScheme(schemeId, PresetAward.Not_PresetAward);
		if(null == sourceTickets || sourceTickets.isEmpty()){
			return false;
		}
		List<TicketPresetPO> pTickerList = POUtils.copyBeans(sourceTickets, TicketPresetPO.class);
		// ticket status
		log.info("开始兑奖方案{}", schemeId);
		if(!prizesResolver.prizesByTickets(pTickerList, matchResults)){
			log.info("方案不能预兑奖{}", schemeId);
			return false;
		}
		
		savePrizes(pTickerList, sourceTickets);
		return true;
	}
	
	/** 保存预兑奖方案和票 */
	@Transactional
	private BetSchemePO savePrizes(List<TicketPresetPO> pTickerList, List<TicketPO> sourceTickets){
		log.info("pTickerList.size={}",pTickerList.size());
		Long schemeId = pTickerList.get(0).getSchemeId();
		BetSchemePresetPO pSchemePO = new BetSchemePresetPO();
		BetSchemePO schemePO = betSchemeDao.get(schemeId);
		
		List<BetPartnerPO> betPartnerList = betPartnerDao.findBySchemeId(schemeId);
		List<BetPartnerPresetPO> parPOs = POUtils.copyBeans(betPartnerList, BetPartnerPresetPO.class);
		BeanUtils.copyProperties(schemePO, pSchemePO);
		int checkStatus = PresetCheckStatus.NOT_CHECK.getValue();
		int presetStatus = PresetAward.Is_PresetAward.getValue();
		int winNote = 0;			// 中奖注数

		BigDecimal schemeAfterTaxBonus = BigDecimal.ZERO;
		BigDecimal schemePreTaxBonus = BigDecimal.ZERO;
		for(TicketPresetPO pt : pTickerList){
			log.info("pt={}",pt);
			pt.setCheckStatus(checkStatus);
			pt.setStatus(getStatusByBonus(pt.getAfterTaxBonus()));
			schemeAfterTaxBonus = schemeAfterTaxBonus.add(pt.getAfterTaxBonus()).setScale(2, RoundingMode.DOWN);
			schemePreTaxBonus = schemePreTaxBonus.add(pt.getPreTaxBonus()).setScale(2, RoundingMode.DOWN);
			if(pt.getNote() >0 && pt.getStatus() == EntityStatus.TICKET_NOT_AWARD ){
				winNote += pt.getWinNotes();
				pt.setMessage("彩票已中奖，但未派奖");
			}else{
				pt.setMessage("彩票未中奖");
			}
			ticketPresetDao.save(pt);
			
			for(int m=0; m<sourceTickets.size(); m++){
				TicketPO tPO = sourceTickets.get(m);
				if(null != tPO.getId() && tPO.getId().equals(pt.getId())) {
					sourceTickets.get(m).setStatus(pt.getStatus());
					sourceTickets.get(m).setAfterTaxBonus(pt.getAfterTaxBonus());
					sourceTickets.get(m).setPreTaxBonus(pt.getPreTaxBonus());
					sourceTickets.get(m).setMessage(pt.getMessage());
				}
			}
		}
		for(TicketPO t : sourceTickets){
			t.setIsPresetAward(presetStatus);
			ticketDao.update(t);
		}
		for(BetPartnerPresetPO part : parPOs){
			betPartnerPresetDao.save(part);
		}
		pSchemePO.setWinNote(winNote);
		pSchemePO.setCheckStatus(checkStatus);
		pSchemePO.setStatus(getStatusByBonus(schemeAfterTaxBonus));
		pSchemePO.setPreTaxBonus(schemePreTaxBonus);
		pSchemePO.setAfterTaxBonus(schemeAfterTaxBonus);
		
		 // 计算投注人奖金
        if(schemeAfterTaxBonus.compareTo(BigDecimal.ZERO) == 1){
        	presetPreAward(pSchemePO);
        }
		betSchemePresetDao.save(pSchemePO);
		
		schemePO.setWinNote(pSchemePO.getWinNote());
		schemePO.setStatus(pSchemePO.getStatus());
		schemePO.setAfterTaxBonus(pSchemePO.getAfterTaxBonus());
		schemePO.setPreTaxBonus(pSchemePO.getPreTaxBonus());
		schemePO.setWinNote(pSchemePO.getWinNote());
		schemePO.setIsPresetAward(presetStatus);
		betSchemeDao.save(schemePO);
		return schemePO;
	}

	 /**
     * 预兑奖预处理，更新 BetPartnerPreset.
     * @param betSchemePresetPo 准备兑奖的方案
     */
    private void presetPreAward(BetSchemePresetPO betSchemePo){
    	BigDecimal bonus = betSchemePo.getAfterTaxBonus();
    	BigDecimal commission = BigDecimal.ZERO;
    	
    	// 计算发起人分成，合买
    	if(betSchemePo.getType() == EntityType.BETTING_PARTNER){
    		commission = bonus.multiply(NumberUtils.percent(betSchemePo.getShareRatio())).setScale(2, RoundingMode.DOWN);
    		if(NumberUtils.gt(commission, BigDecimal.ZERO)){
    			bonus = bonus.subtract(commission);
    		}
    	}
    	
    	// 计算发起人分成，跟单
    	if(betSchemePo.getType() == EntityType.BETTING_FOLLOW){
    		//当税后奖金大于本金时才给晒单人佣金
    		if(bonus.compareTo(new BigDecimal(betSchemePo.getTotalAmount()))==1){
    			BetSchemePO showScheme = betSchemeDao.get(betSchemePo.getFollowedSchemeId());
    			commission = bonus.multiply(NumberUtils.percent(showScheme.getFollowedRatio())).setScale(2, RoundingMode.DOWN);
    			//计算后的佣金大于0就派发佣金
    			if(NumberUtils.gt(commission, BigDecimal.ZERO)){
    				bonus = bonus.subtract(commission);
    			}
    		}
    	}
    	
    	// 计算发起人分成后的奖金分配
    	BigDecimal eachBonus = bonus.divide(new BigDecimal(betSchemePo.getTotalAmount()), 16, RoundingMode.HALF_UP);
    	List<BetPartnerPresetPO> partners = betPartnerPresetDao.findBySchemeId(betSchemePo.getId());
    	List<BetPartnerPO> betPartnerList = betPartnerDao.findBySchemeId(betSchemePo.getId());
    	for(BetPartnerPresetPO betPartner: partners){
    		BigDecimal actualAward = eachBonus.multiply(new BigDecimal(betPartner.getBetAmount())).setScale(2, RoundingMode.HALF_UP);
    		betPartner.setWinAmount(actualAward);
    		for(BetPartnerPO PO : betPartnerList) {
    			if(null != PO && PO.getId().equals(betPartner.getId())) {
    				PO.setWinAmount(betPartner.getWinAmount());
    			}
    		}
    		if(betSchemePo.getType()!=EntityType.BETTING_PARTNER)
    			betPartner.setCommission(commission);
    	}
    }
    
	private int getStatusByBonus(BigDecimal afterTaxBonus){
		if(null ==afterTaxBonus || afterTaxBonus.compareTo(BigDecimal.ZERO) == 0){
			return EntityStatus.TICKET_NOT_WIN;
		}
		return EntityStatus.TICKET_NOT_AWARD;
	}
	
	@Override
	@Transactional(readOnly=true)
	public MatchResults computeFBMatchResults(List<Long> matchIds) {
		if(null == matchIds || matchIds.isEmpty()){
			return null;
		}
		
		List<FBMatchPO> fbMatchPOs = fBMatchDao.find(new HashSet<Long>(matchIds));
		Map<Long, FBMatch> fbMatchMap = new HashMap<Long, FBMatch>();
		Map<Long, String> codeMap = new HashMap<Long, String>();
		List<Long> cancelMatchIds = new ArrayList<Long>();
		for(FBMatchPO fbpo : fbMatchPOs){
			String code = fbpo.getCode();
			Long matchId = fbpo.getId();
			
			codeMap.put(matchId, code);
			FBMatch fBMatch = new FBMatch();
			BeanUtils.copyProperties(fbpo, fBMatch);
			fbMatchMap.put(matchId, fBMatch);
			if(fbpo.getStatus() == EntityStatus.MATCH_CANCEL) {
				cancelMatchIds.add(matchId);
			}
		}
		
		List<FBMatchPlayPO> fbMatchPlayPOs = fBMatchPlayDao.findByMatchId(matchIds);
		Map<String, String> fBMatchWinOptMap = new HashMap<String, String>();
		for(FBMatchPlayPO matchPlay : fbMatchPlayPOs){
			Long matchId = matchPlay.getMatchId();
			if(cancelMatchIds.contains(matchId)) {
				continue;
			}
			String playId = matchPlay.getPlayId();
			int sortPlayId = Integer.parseInt(playId.substring(0,2));
			FBMatch fBMatch = fbMatchMap.get(matchId);
			
			String key = codeMap.get(matchId) + playId;
			String mixkey = key + MixPlayType.valueOfPlayType(PlayType.valueOfLcPlayId(playId)).name();
			String winOpt = OptionUtils.zcWinOption(sortPlayId, fBMatch.getConcedePoints(), fBMatch.getHalfScorePreset(), fBMatch.getScorePreset());
			// 普通玩法中奖选项
			fBMatchWinOptMap.put(key, winOpt);
			// 混合过关中奖选项
			fBMatchWinOptMap.put(mixkey, winOpt);
		}
		List<String> cancelMatchCodeList = fBMatchDao.filterCancelMatch(matchIds);
		
		MatchResults matchResults = new MatchResults();
		matchResults.setfBMatchWinOptMap(fBMatchWinOptMap);
		matchResults.setCancelMatchCodeList(cancelMatchCodeList);
		return matchResults;
	}
	
	@Override
	@Transactional(readOnly=true)
	public MatchResults computeBBMatchResults(List<Long> matchIds) {
		if(null == matchIds || matchIds.isEmpty()){
			return null;
		}
		
		List<BBMatchPO> bbMatchPOs = bBMatchDao.find(new HashSet<Long>(matchIds));
		Map<Long, BBMatch> bbMatchMap = new HashMap<Long, BBMatch>();
		Map<Long, String> codeMap = new HashMap<Long, String>();
		List<Long> cancelMatchIds = new ArrayList<Long>();
		for(BBMatchPO bbpo : bbMatchPOs){
			String code = bbpo.getCode();
			Long matchId = bbpo.getId();
			
			codeMap.put(matchId, code);
			BBMatch bBMatch = new BBMatch();
			BeanUtils.copyProperties(bbpo, bBMatch);
			bbMatchMap.put(matchId, bBMatch);
			if(bbpo.getStatus() == EntityStatus.MATCH_CANCEL) {
				cancelMatchIds.add(matchId);
			}
		}
		
		List<BBMatchPlayPO> bbMatchPlayPOs = bBMatchPlayDao.findByMatchId(matchIds);
		Map<String, String> bBMatchWinOptMap = new HashMap<String, String>();
		for(BBMatchPlayPO matchPlay : bbMatchPlayPOs){
			Long matchId = matchPlay.getMatchId();
			if(cancelMatchIds.contains(matchId)) {
				continue;
			}
			
			String playId = matchPlay.getPlayId();
			int sortPlayId = Integer.parseInt(playId.substring(0,2));
			BBMatch bBMatch = bbMatchMap.get(matchId);
			
			String key = codeMap.get(matchId) + playId;
			String mixkey = key + MixPlayType.valueOfPlayType(PlayType.valueOfLcPlayId(playId)).name();
			if(Constants.PLAY_06_LC_2.equals(playId) || Constants.PLAY_09_LC_2.equals(playId) ){
				bBMatchWinOptMap.put(key , bBMatch.getFinalScorePreset());
				// 混合过关中奖选项
				bBMatchWinOptMap.put(mixkey ,  bBMatch.getFinalScorePreset());
			}else{
				String winOpt =  OptionUtils.lcWinOption(sortPlayId, 0, matchPlay.getDefaultScore(), bBMatch.getFinalScorePreset());
				bBMatchWinOptMap.put(key , winOpt);
				// 混合过关中奖选项
				bBMatchWinOptMap.put(mixkey , winOpt);
			}
		}
		List<String> cancelMatchCodeList = bBMatchDao.filterCancelMatch(matchIds);
		
		MatchResults matchResults = new MatchResults();
		matchResults.setbBMatchWinOptMap(bBMatchWinOptMap);
		matchResults.setCancelMatchCodeList(cancelMatchCodeList);
		return matchResults;
	}

	@Override
	@Transactional
	public List<PresetResult> presetCTZC(List<String> issueNumbers) {
		if(null==issueNumbers||issueNumbers.isEmpty()){
			return new ArrayList<PresetResult>();
		}
		List<PresetResult> presetResultList=new ArrayList<PresetResult>();
		for(String issueNumber:issueNumbers){
			presetResultList.add(presetCTZC(issueNumber));
		}
		return presetResultList;
	}

	@Override
	@Transactional
	public PresetResult presetCTZC(String issueNumber) {
		if(StringUtils.isBlank(issueNumber)){
			return new PresetResult();
		}
		PresetResult presetResult=new PresetResult();
		//根据期号查找对应的期信息列表
		List<IssueInfoPO> issueInfoList=issueInfoDao.findByIssueNumber(issueNumber);
		//迭代期信息列表的每一项
		if(null!=issueInfoList&&!issueInfoList.isEmpty()){
			List<PresetResultPerPlayId> presetResultPerPlayIdList=new ArrayList<PresetResultPerPlayId>();
			presetResult.setPresetResultPerPlayIdList(presetResultPerPlayIdList);
			for(IssueInfoPO issueInfo:issueInfoList){
				handleOneIssueInfo(issueNumber, presetResultPerPlayIdList,
						issueInfo);
			}
		}
		return presetResult;
	}

	private void handleOneIssueInfo(String issueNumber,
			List<PresetResultPerPlayId> presetResultPerPlayIdList,
			IssueInfoPO issueInfo) {
		PresetResultPerPlayId presetResultPerPlayId;
		presetResultPerPlayId=new PresetResultPerPlayId();
		presetResultPerPlayId.setPlayId(issueInfo.getPlayId());
		BonusPerNote bonusPerNote = getBonusPerNote(issueInfo);
		if(null!=bonusPerNote){//检查期信息是否合法(预兑奖中奖信息是否能解析出单注奖金)
				//检查期和玩法对应的比赛赛果是否都填全了
			if(checkMatchResultIsOK(issueInfo)){
				//根据期号和玩法id找到这一期的所有比赛，组成赛果列表,针对取消的比赛要做特殊处理
				List<String> ctfbMatchResult=computeCTFBMatchResult(issueNumber, issueInfo);
			
				//更新期信息的中奖号码
				updateBonusCode(issueInfo, ctfbMatchResult);
				
				//根据期号和玩法id找到所有可预兑奖的方案
				List<Long> betSchemeIdList = findCTZCScheme(issueInfo);
				if(null!=betSchemeIdList&&!betSchemeIdList.isEmpty()){
					presetCTZCSchemes(issueNumber, issueInfo,
							presetResultPerPlayId, bonusPerNote, betSchemeIdList,ctfbMatchResult);
				}
				else{
					presetResultPerPlayId.setSucc(false);
					presetResultPerPlayId.setMessage("没有找到期对应的可预兑奖方案id列表");
				}
			}

			else{
				presetResultPerPlayId.setSucc(false);
				presetResultPerPlayId.setMessage(issueNumber+"期该玩法对应的比赛赛果没有填全");
			}
			
		}
		else{//如不合法不做处理，并返回相应信息
			presetResultPerPlayId.setSucc(false);
			presetResultPerPlayId.setMessage("期信息中没有包含预设单注奖金");
		}
		presetResultPerPlayIdList.add(presetResultPerPlayId);
	}


	private boolean checkMatchResultIsOK(IssueInfoPO issueInfo) {
		boolean result=false;
		Long count=ctfbMatchDao.countHaveMatchResultByIssueNumberAndPlayId(issueInfo.getIssueNumber(),issueInfo.getPlayId());
		if(countIsRight(count,issueInfo.getPlayId())){
			result=true;
		}
		return result;
	}

	private boolean countIsRight(Long count, String playId) {
		boolean result=false;
		switch(playId){
		 case Constants.PLAY_24_ZC_14:{result=(count==14);break;}
		 case Constants.PLAY_25_ZC_R9:{result=(count==14);break;}
		 case Constants.PLAY_26_ZC_BQ:{result=(count==6);break;}
		 case Constants.PLAY_27_ZC_JQ:{result=(count==4);break;}
		 default:{result=false;break;}
		}
		return result;
	}

	private void presetCTZCSchemes(String issueNumber, IssueInfoPO issueInfo,
			PresetResultPerPlayId presetResultPerPlayId,
			BonusPerNote bonusPerNote, List<Long> betSchemeIdList, List<String> ctfbMatchResult) {
		presetResultPerPlayId.setTotalSchemes(betSchemeIdList.size());
		
		
		if(null!=ctfbMatchResult&&!ctfbMatchResult.isEmpty()){
			//对每一个方案的所有票进行遍历，与赛果列表比对，将状态改为中奖未派奖或未中奖，并对方案的状态进行修改(只要有一张票中奖了，就是中奖未派奖；否则是未中奖),修改方案的中奖奖金
			presetCTZCTicketAndSavePrizes(presetResultPerPlayId, bonusPerNote,
					betSchemeIdList, ctfbMatchResult);
		}
		else{
			presetResultPerPlayId.setSucc(false);
			presetResultPerPlayId.setMessage("期包含的比赛无法计算赛果");
		}
	}

	private void updateBonusCode(IssueInfoPO issueInfo,
			List<String> ctfbMatchResult) {
		String bonusCode=makeBonusCode(ctfbMatchResult);
		if(StringUtils.isNotBlank(bonusCode)){
			issueInfo.setBonusCode(bonusCode);
		}
	}

	private String makeBonusCode(List<String> ctfbMatchResult) {
		String result=null;
		if(null!=ctfbMatchResult&&!ctfbMatchResult.isEmpty()){
			result=StringUtils.join(ctfbMatchResult, "");
		}
		return result;
	}

	private void presetCTZCTicketAndSavePrizes(
			PresetResultPerPlayId presetResultPerPlayId,
			BonusPerNote bonusPerNote, List<Long> betSchemeIdList,
			List<String> ctfbMatchResult) {
		for(Long betSchemeId:betSchemeIdList){
			List<TicketPO> sourceTickets = checkScheme(betSchemeId, PresetAward.Not_PresetAward);
			if(null == sourceTickets || sourceTickets.isEmpty()){
				presetResultPerPlayId.addSkipScheme();
				break;
			}
			List<TicketPresetPO> presetTicketList = POUtils.copyBeans(sourceTickets, TicketPresetPO.class);
			log.info("开始兑奖方案{}", betSchemeId);
			checkIfTicketAwardAndComputeBonus(presetTicketList,bonusPerNote,presetResultPerPlayId,ctfbMatchResult);
			savePrizes(presetTicketList, sourceTickets,presetResultPerPlayId);
			
		}
	}

	private void savePrizes(List<TicketPresetPO> presetTickerList,
			List<TicketPO> sourceTickets,
			PresetResultPerPlayId presetResultPerPlayId) {
		log.info("开始savePrizes");
		BetSchemePO betSchemePO = savePrizes(presetTickerList, sourceTickets);
		if(null!=betSchemePO){
			if(betSchemePO.getStatus()==EntityStatus.TICKET_NOT_AWARD){//中奖未派奖
				presetResultPerPlayId.addCanAwardScheme();
			}
			else if(betSchemePO.getStatus()==EntityStatus.TICKET_NOT_WIN){//未中奖
				presetResultPerPlayId.addNotAwardScheme();
			}
		}
		log.info("savePrizes结束");
	}
	
	/**
	 * 判断票是否中奖
	 * @param presetTickeTList
	 * @param bonusPerNote
	 * @param presetResultPerPlayId
	 * @param ctfbMatchResult
	 */
	private boolean checkIfTicketAwardAndComputeBonus(
			List<TicketPresetPO> presetTickeTList, BonusPerNote bonusPerNote,
			PresetResultPerPlayId presetResultPerPlayId, List<String> ctfbMatchResult) {
		log.info("开始检查票是否中奖");
		log.info("bonusPerNote={}",bonusPerNote);
		boolean isWin;
		boolean schemeIsWin=false;
		for (TicketPresetPO ticketPresetPO : presetTickeTList) {
			log.info("ticketPresetPO={}",ticketPresetPO);
			isWin = false;
			int note=1;
			String[] codes = ticketPresetPO.getCode().split(",");
			int hitCount=0;
			for (int i = 0; i < ctfbMatchResult.size(); i++) {
				String code = codes[i];
				String result = ctfbMatchResult.get(i);
				log.info("code={}",code);
				log.info("ctfbMatchResult[{}]={}",i,result);
				String[] cs = code.split("");
				for (int j = 1; j < cs.length; j++) {//j从1开始，因为cs[0]="";
					if(result.equals(cs[j])){//命中
						hitCount++;
						break;
					} else if("*".equals(result)){//比赛取消
						hitCount++;
						note = note*(cs.length-1);
						break;
					}
				}
			}
			double afterTaxBonus = 0;
			double preTaxBonus=0;
			log.info("hitCount={}",hitCount);
			if(hitCount == 13 && PlayType.CTZC_14.getPlayId().equals(ticketPresetPO.getPlayId())){//14场二等奖
				afterTaxBonus = (bonusPerNote.getSecondBonus()>=10000?bonusPerNote.getSecondBonus()*0.8:bonusPerNote.getSecondBonus());
				preTaxBonus=bonusPerNote.getSecondBonus();
				isWin = true;
			} else if((hitCount == 14 && PlayType.CTZC_14.getPlayId().equals(ticketPresetPO.getPlayId())) //14场一等奖
					|| (hitCount == 9 && PlayType.CTZC_R9.getPlayId().equals(ticketPresetPO.getPlayId())) //任九
					|| (hitCount == 12 && PlayType.CTZC_BQ.getPlayId().equals(ticketPresetPO.getPlayId())) //6场半全
					|| (hitCount == 8 && PlayType.CTZC_JQ.getPlayId().equals(ticketPresetPO.getPlayId()))){ //4场进球
				afterTaxBonus = (bonusPerNote.getFirstBonus()>=10000?bonusPerNote.getFirstBonus()*0.8:bonusPerNote.getFirstBonus());
				preTaxBonus=bonusPerNote.getFirstBonus();
				isWin = true;
			}
			if(isWin){
				schemeIsWin=true;
				int multiple=ticketPresetPO.getMultiple();//倍数
				ticketPresetPO.setStatus(EntityStatus.TICKET_NOT_AWARD);
				ticketPresetPO.setAfterTaxBonus(BigDecimal.valueOf(afterTaxBonus*note*multiple));
				ticketPresetPO.setPreTaxBonus(BigDecimal.valueOf(preTaxBonus*note*multiple));
				ticketPresetPO.setWinNotes(note*multiple);
			} else {
				ticketPresetPO.setStatus(EntityStatus.TICKET_NOT_WIN);
				ticketPresetPO.setAfterTaxBonus(BigDecimal.ZERO);
				ticketPresetPO.setPreTaxBonus(BigDecimal.ZERO);
				
			}
		}
		log.info("检查票是否中奖结束");
		return schemeIsWin;
	}

	private List<String> computeCTFBMatchResult(String issueNumber,
			IssueInfoPO issueInfo) {
		List<CTFBMatchPO> ctfbMatchs = ctfbMatchDao.findByIssueNoAndPlayId(issueNumber, issueInfo.getPlayId());
		return makeCTFBMatchResultList(ctfbMatchs,issueInfo.getPlayId());
	}

	private List<Long> findCTZCScheme(IssueInfoPO issueInfo) {
		List<Long> betSchemeIdList=betSchemePresetDao.findAllowPrizesCTZC(issueInfo.getIssueNumber(),issueInfo.getPlayId());
		return betSchemeIdList;
	}

	private BonusPerNote getBonusPerNote(IssueInfoPO issueInfo) {
		BonusPerNote bonusPerNote=BonusPerNote.parse(issueInfo.getPresetBonusInfo());
		return bonusPerNote;
	}

	/**
	 * 根据玩法id和比赛赛果，生成赛果列表
	 * @param ctfbMatchs
	 * @param playId 
	 * @return
	 */
	private List<String> makeCTFBMatchResultList(List<CTFBMatchPO> ctfbMatchs, String playId) {
		return ctfbMatchResultUtil.makeMatchResultList(ctfbMatchs,playId);
	}

}
