package com.xhcms.lottery.admin.persist.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.persist.service.exception.BetSchemeServiceException;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.dao.WinDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.entity.WinPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.commons.persist.service.WinListService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.NumberUtils;
import com.xhcms.lottery.utils.POUtils;
import com.xhcms.lottery.utils.PagingUtils;
import com.xhcms.lottery.utils.ResultUtils;

public class BetSchemeServiceImpl implements BetSchemeService {
	private Pattern spliter = Pattern.compile(",");
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
    private FBMatchDao fbMatchDao;
    @Autowired
    private FBMatchPlayDao fbMatchPlayDao;
    @Autowired
    private BBMatchDao bbMatchDao;
    @Autowired
    private BBMatchPlayDao bbMatchPlayDao;
	@Autowired
	private BetMatchDao betMatchDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private BetPartnerDao betPartnerDao;
	@Autowired
	private WinDao winDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private WinListService winListService;
	
	@Override
	@Transactional(readOnly = true)
	public void listBetScheme(Paging paging, Date from, Date to, int status, String lotteryId, Long schemeId, String sponsor, String playId, String passType) {
		List<BetSchemePO> list = betSchemeDao.find(paging, from, to, status, lotteryId, schemeId, sponsor, playId, passType);
		List<BetScheme> rets = new ArrayList<BetScheme>(list.size());
		BetScheme b = null;
		for (BetSchemePO po : list) {
			b = new BetScheme();
			BeanUtils.copyProperties(po, b);
			int updatedStatus = changeStatusByTicketSendResult(po);
			b.setStatus(updatedStatus);
			rets.add(b);
		}
		paging.setResults(rets);
	}
	
	/**
	 * 检查 required 状态的 scheme 是否有“送票失败”的情况，即没有接到平台返回的“000”接收成功消息的票。
	 * @param schemePO 被检查的方案
	 * @return 方案状态。如果方案有“未被成功接收”的票，就返回 
	 */
	private int changeStatusByTicketSendResult(BetSchemePO schemePO) {
		if (schemePO.getStatus()!=EntityStatus.TICKET_REQUIRED){
			return schemePO.getStatus();
		}
		List<TicketPO> tickets = ticketDao.findByScheme(schemePO.getId(), -1);
		boolean find = false;
		for (TicketPO ticket : tickets){
			if (ticket.getStatus() == EntityStatus.TICKET_INIT && // 未出票
					ticket.getActualStatus() != EntityStatus.TICKET_ACTUAL_STATUS_SUCCESS){
				find = true;
				break;
			}
		}
		if (find){
			return EntityStatus.SCHEME_FAIL_TO_SEND;
		}
		return schemePO.getStatus();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> listTicket(long schemeId) {
		List<TicketPO> list = ticketDao.findByScheme(schemeId, -1);
		List<Ticket> rets = new ArrayList<Ticket>();
		Ticket tk = null;
		for (TicketPO po : list) {
			tk = new Ticket();
			BeanUtils.copyProperties(po, tk);
			rets.add(tk);
		}
		return rets;
	}

	@Override
	@Transactional
	public void award(int operator, List<Long> id) {
	    Date date = new Date();
		List<BetSchemePO> pos = betSchemeDao.find(id);
		
		Set<Long> sid = new HashSet<Long>();
		for (BetSchemePO betSchemePo : pos) {
		    // 验证状态
		    if(EntityStatus.TICKET_NOT_AWARD != betSchemePo.getStatus()){
		        continue;
		    }
		    
		    // 发起人分成
		    BigDecimal totalBonus = betSchemePo.getAfterTaxBonus();
		    //合买
		    if(betSchemePo.getType() == EntityType.BETTING_PARTNER){
		        BigDecimal share = totalBonus.multiply(NumberUtils.percent(betSchemePo.getShareRatio())).setScale(2, RoundingMode.DOWN);
		        if(share.compareTo(BigDecimal.ZERO) == 1){
		        	BetPartnerPO partnerPo = betPartnerDao.get(betSchemePo.getId(), betSchemePo.getSponsorId());
		    		accountService.giveCommission(operator, betSchemePo.getSponsorId(), 
		    				share, partnerPo.getId());
		    		
//		            accountService.devide(operator, betSchemePo.getSponsorId(), share, 0L, "发起合买中奖分成");
//		            totalBonus = totalBonus.subtract(share);
		        }
		    }
		    //跟单
		    if(betSchemePo.getType() == EntityType.BETTING_FOLLOW){
		    	giveCommission(operator, betSchemePo);
		    }
		    
		    // 给合买人派奖
		    HashMap<Long, WinPO> wins = new HashMap<Long, WinPO>();
	        for (BetPartnerPO ppo : betPartnerDao.findBySchemeId(betSchemePo.getId())) {
	            WinPO win = wins.get(ppo.getId());
	            
	            if(win == null){
	                win = new WinPO();
	                win.setUserId(ppo.getUserId());
	                win.setSchemeId(betSchemePo.getId());
	                win.setAmount(new BigDecimal(ppo.getBetAmount()));
	                win.setBonus(ppo.getWinAmount());
	                win.setCreatedTime(date);
	            }else{
	                win.setAmount(win.getAmount().add(new BigDecimal(ppo.getBetAmount())));
	                win.setBonus(win.getBonus().add(ppo.getWinAmount()));
	            }
	            wins.put(ppo.getId(), win);
	            accountService.devide(operator, ppo.getUserId(), ppo.getWinAmount(), ppo.getId(), "派奖");
	        }
	        for(WinPO wpo: wins.values()){
	            winDao.save(wpo);
	        }
	        
	        // 修改状态
	        betSchemePo.setStatus(EntityStatus.TICKET_AWARDED);
	        betSchemePo.setAwardTime(new Date());
	        sid.add(betSchemePo.getId());

			// 计算用户战绩
	        if(betSchemePo.getType() == EntityType.BETTING_PARTNER || 
	        		betSchemePo.getShowScheme() == EntityType.SHOW_SCHEME){
				userScoreService.countUserScore(betSchemePo);
			}
			// 计算用户晒单跟单中奖榜
			if (betSchemePo.getShowScheme() == EntityType.SHOW_SCHEME || 
					betSchemePo.getType() == EntityType.BETTING_FOLLOW) {
				winListService.countWinList(betSchemePo);
			}
        }
		
		ticketDao.updateStatusByScheme(sid, EntityStatus.TICKET_NOT_AWARD, EntityStatus.TICKET_AWARDED, "派奖");
	}

	/**
	 * 给佣金
	 * @param operatorId 管理员id
	 * @param schemePo 被处理的方案
	 */
	private void giveCommission(Integer operatorId, BetSchemePO schemePo) {
		BigDecimal totalBonus = schemePo.getAfterTaxBonus();
		//当税后奖金大于本金时才给晒单人佣金
    	if(totalBonus.compareTo(new BigDecimal(schemePo.getTotalAmount()))==1){
    		BetSchemePO showScheme = betSchemeDao.get(schemePo.getFollowedSchemeId());
    		BigDecimal ratio = NumberUtils.percent(showScheme.getFollowedRatio());
	    	BigDecimal commission = totalBonus.multiply(ratio).setScale(2, RoundingMode.DOWN);
	    	//计算后的佣金大于0就派发佣金
	    	if(commission.compareTo(BigDecimal.ZERO) == 1){
	    		BetPartnerPO partnerPo = betPartnerDao.get(schemePo.getId(), schemePo.getSponsorId());
	    		accountService.giveCommission(operatorId, showScheme.getSponsorId(), 
	    				commission, partnerPo.getId());
	    	}
    	}
	}

	@Override
	@Transactional
	public void cancel(int operator, long schemeId) {
		BetSchemePO spo = betSchemeDao.get(schemeId);
		Assert.notNull(spo, AppCode.SCHEME_NOT_EXIST);
		Assert.eq(spo.getTicketNote(), 0, AppCode.SCHEME_UNUSUAL_TICKET_NOTE);
		
		if(spo.getStatus() != EntityStatus.TICKET_REQUIRED && spo.getStatus() != EntityStatus.TICKET_ALLOW_BUY && spo.getStatus() != EntityStatus.TICKET_INIT){
		    throw new XHRuntimeException(AppCode.SCHEME_UNUSUAL_OPERATE);
		}

		List<Long> sids = new ArrayList<Long>();
		sids.add(schemeId);
		//修改票状态
		ticketDao.cancelTicketsBySchemeIds(spo.getStatus(), sids);
		//修改方案状态
		spo.setStatus(EntityStatus.TICKET_SCHEME_CANCEL);
		//设置投注失败注数
		spo.setCancelNote(spo.getBetNote());
		//如果是合买，需要将方案置为停止（满员未扣款）
		if(spo.getType()==EntityType.BETTING_PARTNER){
			spo.setSaleStatus(EntityStatus.SCHEME_STOP);
		}
	}
	
    @Override
    @Transactional(readOnly = true)
    public BetScheme getScheme(Long id) {
        BetSchemePO po = betSchemeDao.get(id);
        
        BetScheme s = new BetScheme();
        BeanUtils.copyProperties(po, s);
        
        return s;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BetMatch> listMatch(Long schemeId) {
        BetSchemePO spo = betSchemeDao.get(schemeId);
        List<BetMatchPO> pos = betMatchDao.findBySchemeId(spo.getId());
        HashMap<Long, PlayMatch> matchs = new HashMap<Long, PlayMatch>(pos.size());
        List<BetMatch> ms = new ArrayList<BetMatch>(pos.size());
        for(BetMatchPO po: pos){
            PlayMatch pm = new PlayMatch();
            pm.setId(po.getId());
            pm.setSchemeId(po.getSchemeId());
            pm.setMatchId(po.getMatchId());
            pm.setOdds(po.getOdds());
            pm.setBetCode(po.getCode().substring(4));
            //加胆码
            pm.setSeed(po.isSeed());

            matchs.put(po.getMatchId(), pm);
            ms.add(pm);
        }
        
        if(Constants.JCZQ.equals(spo.getLotteryId())){
            // 竞彩足球
            List<FBMatchPlayPO> fs = fbMatchPlayDao.find(spo.getPlayId(), matchs.keySet());
            HashMap<Long, FBMatchPlayPO> fMap = new HashMap<Long, FBMatchPlayPO>(fs.size());
            for(FBMatchPlayPO f: fs){
                fMap.put(f.getMatchId(), f);
            }
            
            for(FBMatchPO m: fbMatchDao.find(matchs.keySet())){
                PlayMatch pm = matchs.get(m.getId());
                FBMatchPlayPO p = fMap.get(m.getId());
                if(pm != null && p != null){
                    toPlayMatch(m, p, pm);
                }
            }
        } else if(Constants.JCLQ.equals(spo.getLotteryId())) {
            // 竞彩篮球
            List<BBMatchPlayPO> fs = bbMatchPlayDao.find(spo.getPlayId(), matchs.keySet());
            HashMap<Long, BBMatchPlayPO> fMap = new HashMap<Long, BBMatchPlayPO>(fs.size());
            for(BBMatchPlayPO f: fs){
                fMap.put(f.getMatchId(), f);
            }
            
            for(BBMatchPO m: bbMatchDao.find(matchs.keySet())){
                PlayMatch pm = matchs.get(m.getId());
                BBMatchPlayPO p = fMap.get(m.getId());
                if(pm != null && p != null){
                    toPlayMatch(m, p, pm);
                }
            }
        }
        return ms;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BetPartner> listPartner(Long schemeId) {
        List<BetPartnerPO> partners = betPartnerDao.findBySchemeId(schemeId);
        List<BetPartner> ps = new ArrayList<BetPartner>(partners.size());
        for(BetPartnerPO po: partners){
            BetPartner p = new BetPartner();
            BeanUtils.copyProperties(po, p);
            ps.add(p);
        }
        
        return ps;
    }

    /**
     * 竞彩足球
     * @param po
     * @param mpo
     * @param pm
     * @return
     */
    private PlayMatch toPlayMatch(FBMatchPO po, FBMatchPlayPO mpo, PlayMatch pm){
        pm.setCnCode(po.getCnCode());
        pm.setCode(po.getCode());
        pm.setName(po.getName());
        pm.setConcedePoints(String.valueOf(po.getConcedePoints()));
        pm.setEntrustDeadline(po.getEntrustDeadline());
        pm.setScore1(po.getHalfScore());
        pm.setPlayingTime(po.getPlayingTime());
        pm.setResult(mpo.getWinOption());
        //过关是根据打票那一刻的奖金算的，由于没有记录用户投注时的全部赔率，所以用该玩法最终的赔率显示
        //足球单关分两种情况
        //1，胜平负、总进球数、半全场胜平负这三种玩法目前来说是浮动奖金，也就是根据最后一次赔率来算奖金
        //2，比分玩法是固定奖金，是根据打票时的赔率算奖金
        //篮球单关目前同样是两种情况
        //1，胜负、大小分、让分这三种玩法是浮动奖金，根据最后次赔率算
        //2，胜分差玩法是固定奖金，是根据打票时算
		String pid = mpo.getPlayId();
		if (Constants.PLAY_01_ZC_1.equals(pid) || Constants.PLAY_03_ZC_1.equals(pid)
				|| Constants.PLAY_04_ZC_1.equals(pid)) {
			pm.setResultOdd(String.valueOf(mpo.getWinBonus().divide(new BigDecimal(2))));
		} else {
			pm.setResultOdd(ResultUtils.resolveOdds(mpo.getPlayId(), mpo.getWinOption(),
					spliter.split(mpo.getOptions()), spliter.split(mpo.getOdds())));
		}
        pm.setScore(po.getScore());
        pm.setStatus(po.getStatus());
        
        return pm;
    }
    
    /**
     * 竞彩篮球
     * @param po
     * @param mpo
     * @param pm
     * @return
     */
    private PlayMatch toPlayMatch(BBMatchPO po, BBMatchPlayPO mpo, PlayMatch pm){
        pm.setCnCode(po.getCnCode());
        pm.setCode(po.getCode());
        pm.setName(po.getName());
        pm.setConcedePoints(String.valueOf(mpo.getDefaultScore()));
        pm.setEntrustDeadline(po.getEntrustDeadline());
        pm.setScore1(po.getQuarter1());
        pm.setScore2(po.getQuarter2());
        pm.setScore3(po.getQuarter3());
        pm.setScore4(po.getQuarter4());
        pm.setPlayingTime(po.getPlayingTime());
        pm.setResult(mpo.getWinOption());
        String pid = mpo.getPlayId();
		if (Constants.PLAY_06_LC_1.equals(pid) || Constants.PLAY_07_LC_1.equals(pid)
				|| Constants.PLAY_09_LC_1.equals(pid)) {
			pm.setResultOdd(String.valueOf(mpo.getWinBonus().divide(new BigDecimal(2))));
		} else {
			pm.setResultOdd(ResultUtils.resolveOdds(mpo.getPlayId(), mpo.getWinOption(),
					spliter.split(mpo.getOptions()), spliter.split(mpo.getOdds())));
		}
        pm.setScore(po.getFinalScore());
        pm.setStatus(po.getStatus());
        
        return pm;
    }

	@Override
	@Transactional(readOnly=true)
	public void listOnSaleShowingBetScheme(Paging paging, int status,
			String lotteryId, Long schemeId, String sponsor, String playId,
			String passType) {
		
		List<BetSchemePO> list = betSchemeDao.findOnSaleShowingScheme(
				paging, status, lotteryId, schemeId, sponsor, playId, passType);
		
		PagingUtils.makeCopyAndSetPaging(list, paging, BetScheme.class);
	}
	
	@Override
	@Transactional(readOnly=true)
	public void listOnSaleGroupbuyBetScheme(Paging paging, int status,
			String lotteryId, Long schemeId, String sponsor, String playId,
			String passType) {
		List<BetSchemePO> list = betSchemeDao.findOnGroupbuyScheme(paging, status, lotteryId, 
				schemeId, sponsor, playId, passType);
		PagingUtils.makeCopyAndSetPaging(list, paging, BetScheme.class);
	}

	@Override
	@Transactional(readOnly=true)
	public void listRecommendedBetScheme(Paging paging, boolean isOnSale) {
		
		List<BetSchemePO> list = betSchemeDao.findRecommended(paging, isOnSale);
		
		PagingUtils.makeCopyAndSetPaging(list, paging, BetScheme.class);
	}

	@Override
	@Transactional(readOnly=true)
	public void listRecommendedBetScheme(Paging paging, boolean isOnSale,
			int type) {
		List<BetSchemePO> list = betSchemeDao.findRecommended(paging, isOnSale, type);
		PagingUtils.makeCopyAndSetPaging(list, paging, BetScheme.class);
	}

	@Override
	@Transactional
	public void recommendScheme(Long schemeId) throws BetSchemeServiceException {
		BetSchemePO scheme = betSchemeDao.get(schemeId);
		if (scheme == null){
			throw new BetSchemeServiceException("没有方案号为“"+schemeId+"”的记录。");
		}
		
		if (scheme.getType() == EntityStatus.BETSCHEME_TYPE_BUY &&
				scheme.getShowScheme() != Constants.SHOW_SCHEME){
			throw new BetSchemeServiceException("该代购方案没有晒单，不能设置为推荐方案！id="+schemeId);
		}
		
		if(scheme.getType() == EntityStatus.BETSCHEME_TYPE_BUY ||
				scheme.getType() == EntityStatus.BETSCHEME_TYPE_GROUPBUY){
			scheme.setRecommendation(Constants.RECOMMEND);
			betSchemeDao.update(scheme);
		}
	}

	@Override
	@Transactional
	public void cancelRecommendScheme(Long schemeId) throws BetSchemeServiceException {
		BetSchemePO scheme = betSchemeDao.get(schemeId);
		if (scheme == null){
			throw new BetSchemeServiceException("没有方案号为“"+schemeId+"”的记录。");
		}
		scheme.setRecommendation(Constants.NOT_RECOMMEND);
		betSchemeDao.update(scheme);
	}

	@Override
	@Transactional(readOnly=true)
	public List<BetScheme> listFollowingScheme(Long showingSchemeId) {

		List<BetSchemePO> list = betSchemeDao.findFollowSchemes(showingSchemeId);
		
		return POUtils.copyBeans(list, BetScheme.class);
	}

	/**
	 * 重置方案为可出票状态
	 */
	@Override
	@Transactional
	public void setToAllowBuy(int operatorId, long schemeId) {
		BetSchemePO schemePO = betSchemeDao.get(schemeId);
		Assert.notNull(schemePO, AppCode.SCHEME_NOT_EXIST);
		
		if(schemePO.getStatus() != EntityStatus.TICKET_REQUIRED){
		    throw new XHRuntimeException(AppCode.SCHEME_UNUSUAL_OPERATE);
		}

		List<Long> sid = new ArrayList<Long>();
		sid.add(schemeId);
		//修改方案状态
		schemePO.setStatus(EntityStatus.TICKET_ALLOW_BUY);
	}

	@Override
	@Transactional
	public void setToSchemeBuySuccess(int operatorId, long schemeId) {
		BetSchemePO schemePO = betSchemeDao.get(schemeId);
		Assert.notNull(schemePO, AppCode.SCHEME_NOT_EXIST);
		
		if(schemePO.getStatus() != EntityStatus.TICKET_ALLOW_BUY){
		    throw new XHRuntimeException(AppCode.SCHEME_UNUSUAL_OPERATE);
		}
		
		List<Long> sid = new ArrayList<Long>();
		sid.add(schemeId);
		schemePO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
	}
}
