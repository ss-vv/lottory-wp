package com.xhcms.lottery.account.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.cache.SchemeCache;
import com.unison.lottery.weibo.common.service.SchemeService;
import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.account.service.BetSchemeService;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Commission;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.OrderPO;
import com.xhcms.lottery.commons.persist.entity.RecommendUserPO;
import com.xhcms.lottery.commons.persist.service.GetPresetSchemeService;
import com.xhcms.lottery.commons.persist.service.impl.BetSchemeBaseServiceImpl;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.PagingUtils;

public class BetSchemeServiceImpl extends BetSchemeBaseServiceImpl implements BetSchemeService {
	
	private static final Logger log = LoggerFactory.getLogger(BetSchemeServiceImpl.class);
	
	@Autowired
	private GetPresetSchemeService getPresetSchemeService;
	
	@Autowired
	private SchemeCache schemeCache;
	
	@Autowired
	private com.xhcms.lottery.commons.persist.service.BetSchemeService bsService;
	
	@Autowired
	private SchemeService schemeService;
    
	/**
	 * 更新实单方案缓存:虽然是更新但是事物是只读的，因为不需要更新数据库
	 * @param schemeId
	 * @param displayMode
	 * @return
	 */
	@Transactional(readOnly=true)
	@Override
	public boolean updateRealSchemeCache(long schemeId, int displayMode) {
		if(schemeId > 0 && displayMode >= 0) {
			schemeService.updateRealSchemeCache(schemeId, displayMode);
		} else {
			log.error("更新方案缓存:schemeId={}, displayMode={},无效", schemeId, displayMode);
		}
		return false;
	}
	
    /**
     * 确认投注，目前只支持竞彩，数字彩用 DigitalBetService.
     */
    @Override
    @Transactional(readOnly = true)
    public void confirmScheme(BetScheme scheme) {
    	LotteryId schemeLotteryId = LotteryId.valueOf(scheme.getLotteryId());
    	if (schemeLotteryId != LotteryId.JCLQ && schemeLotteryId != LotteryId.JCZQ&&schemeLotteryId !=LotteryId.BJDC){
    		log.error("ac.BetSchemeServiceImp 只支持JCLQ和JCZQ，不支持：{}", schemeLotteryId);
    		throw new XHRuntimeException(AppCode.BET_FAIL_SSO_ERROR);
    	}
        List<BetMatch> matchs = scheme.getMatchs();
        List<BetMatch> ms = new ArrayList<BetMatch>();
        Map<Long, PlayMatch> maps = new HashMap<Long, PlayMatch>();
        Map<Long, List<PlayMatch>> jcmaps = new HashMap<Long, List<PlayMatch>>();
        for (BetMatch m : matchs) {
            PlayMatch pm = new PlayMatch();
            BeanUtils.copyProperties(m, pm);
            if (StringUtils.isBlank(pm.getPlayId())){
            	pm.setPlayId(scheme.getPlayId());
            }
            //北京单场格式是3位  其余的是4位
            if (Constants.BJDC.equals(scheme.getLotteryId())){
            	
            	pm.setBetCode(m.getCode().substring(3));
            }else{
            	
            	pm.setBetCode(m.getCode().substring(4));
            }
            
            pm.setOdds(m.getOdds());
            if(!PlayType.isOneMatchMutiPlayMixBet(scheme.getPlayId())) {
            	maps.put(m.getMatchId(), pm);
            } else {
            	if(null == jcmaps.get(m.getMatchId()) || jcmaps.get(m.getMatchId()).size() <= 0) {
            		List<PlayMatch> playML = new ArrayList<PlayMatch>();
            		playML.add(pm);
            		jcmaps.put(m.getMatchId(), playML);
            	} else {
            		jcmaps.get(m.getMatchId()).add(pm);
            	}
            }
            
            ms.add(pm);
        }
        
        Set<Long> ids = new HashSet<Long>(matchs.size());
        for (BetMatch match : matchs) {
            ids.add(match.getMatchId());
        }
        if (Constants.JCZQ.equals(scheme.getLotteryId())) {
            // 竞彩足球
            for (FBMatchPO po : fbMatchDao.find(ids)) {
            	List<PlayMatch> list = new ArrayList<PlayMatch>();
            	if(PlayType.isOneMatchMutiPlayMixBet(scheme.getPlayId())) {
                	list = jcmaps.get(po.getId());
                } else {
                	PlayMatch p = maps.get(po.getId());
                	list.add(p);
                }
            	Iterator<PlayMatch> iter = list.iterator();
				while(iter.hasNext()) {
					PlayMatch p = iter.next();
					if (p != null) {
						p.setName(po.getName());
						p.setCnCode(po.getCnCode());
						p.setConcedePoints(String.valueOf(po.getConcedePoints()));
						p.setDefaultScore(po.getConcedePoints());
					}
            	}
            }
        }else if(Constants.BJDC.equals(scheme.getLotteryId())){
        	//北京单场
        	 Map<Long, BJDCMatchPlayPO> bjdcps = new HashMap<Long, BJDCMatchPlayPO>();
        	 //取让球数
        	 Map<String, BJDCMatchPlayPO> bjdc = new HashMap<String, BJDCMatchPlayPO>();
        	 
        	 for (BJDCMatchPlayPO po : bjdcMatchPlayDao.findByMatches(maps.values())) {
        		 bjdcps.put(po.getMatchId(), po);
        		 bjdc.put(po.getId(), po);
             }
        	 for (BJDCMatchPO po : bjdcMatchDao.find(ids)) {
                 PlayMatch p = maps.get(po.getId());
                 if (p != null) {
                     p.setName(po.getName());
                     p.setCnCode(po.getCnCode());
                     if(p.getPlayId().equals(Constants.PLAY_01_BD_SPF)||p.getPlayId().equals(Constants.PLAY_06_BD_SF)){
                    	  BJDCMatchPlayPO tm=bjdc.get((p.getMatchId()+p.getPlayId()));
                          
                          int r=(int)tm.getConcedePoints().doubleValue()*100/100;
                          //让球数
                          p.setConcedePoints(r+"");
                     }
                 }
             }
        	
        } else {
            // 竞彩篮球
            Map<Long, BBMatchPlayPO> bps = new HashMap<Long, BBMatchPlayPO>();
            for (BBMatchPlayPO po : bbMatchPlayDao.findByMatchId(ids)) {
                bps.put(po.getMatchId(), po);
            }

            for (BBMatchPO po : bbMatchDao.find(ids)) {
            	List<PlayMatch> list = new ArrayList<PlayMatch>();
            	if(PlayType.isOneMatchMutiPlayMixBet(scheme.getPlayId())) {
                	list = jcmaps.get(po.getId());
                } else {
                	PlayMatch p = maps.get(po.getId());
                	list.add(p);
                }
            	Iterator<PlayMatch> iter = list.iterator();
            	while(iter.hasNext()) {
            		PlayMatch p = iter.next();
            		BBMatchPlayPO ppo = bps.get(po.getId());
            		if (p != null && ppo != null) {
            			p.setName(po.getName());
            			p.setCnCode(po.getCnCode());
            			p.setConcedePoints(String.valueOf(ppo.getDefaultScore()));
            			p.setDefaultScore(ppo.getDefaultScore());
            		}
            	}
            }
        }
        scheme.setMatchs(ms);
    }
    
	@Override
	@Transactional(readOnly = true)
	public List<Ticket> listTicket(long schemeId, long userId,int displayMode) {
		BetSchemePO spo = betSchemeDao.get(schemeId);
		Assert.notNull(spo, AppCode.SCHEME_NOT_EXIST);
		if (checkUser(spo, userId, displayMode)) {
			return getPresetSchemeService.findTicketsByBetSchemePO(spo);
		}
		return new ArrayList<Ticket>();
	}
    
    
    private boolean checkUser(BetSchemePO spo_follow, Long userId, int displayMode){
    	//不晒单
        if(spo_follow.getShowScheme() == EntityType.DONT_SHOW_SCHEME && 
        		spo_follow.getType() != EntityType.BETTING_FOLLOW && displayMode!=EntityType.DISPLAY_GROUPBUY ){
            return (spo_follow.getSponsorId().equals(userId));
        }
        //晒单
        long sponsorId;
        long schemeId;
        int privacy=-1;
        BetSchemePO spo = new BetSchemePO();
        
        if(spo_follow.getType() == EntityType.BETTING_FOLLOW ){//跟单时，取主单发起人和权限
        	spo=betSchemeDao.get(spo_follow.getFollowedSchemeId());
        	sponsorId = spo.getSponsorId();
        	privacy = spo_follow.getFollowSchemePrivacy();//取跟单权限
        	schemeId=spo_follow.getFollowedSchemeId();
        }else{//代购方案并且不是本人的，或者是合买方案
        	if (spo_follow.getType() == EntityType.BETTING_PARTNER && displayMode==EntityType.DISPLAY_GROUPBUY){//合买 晒单
        		privacy = spo_follow.getPrivacy();
        	}else{//代购、晒单
        		privacy = spo_follow.getFollowSchemePrivacy();
        	}
        	schemeId = spo_follow.getId();
        	spo = spo_follow;
        	sponsorId = spo_follow.getSponsorId();
        }
        switch(privacy){
            case EntityStatus.PRIVACY_PUBLIC:
                return true;
            case EntityStatus.PRIVACY_SOLD:
                return  userId == sponsorId || spo.getStatus()==EntityStatus.TICKET_NOT_WIN  || spo.getStatus()==EntityStatus.TICKET_NOT_AWARD ||spo.getStatus()==EntityStatus.TICKET_AWARDED ;
            case EntityStatus.PRIVACY_FOLLOW_PUBLIC:
                return userId == sponsorId || (null != betPartnerDao.get(spo.getId(), userId)) || (null !=betSchemeDao.findFollowSchemesByUser(schemeId, userId)) ;
            case EntityStatus.PRIVACY_FOLLOW:
                return userId == sponsorId || (spo.getOfftime().before(new Date()) && null != betPartnerDao.get(spo.getId(), userId)) || (spo.getOfftime().before(new Date()) && (null!=betSchemeDao.findFollowSchemesByUser(schemeId, userId))) ;
            case EntityStatus.PRIVACY_SECRET:
                return (userId == sponsorId);
            default:
                return false;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isRecommendUser(BetScheme scheme){
    	boolean isRecommend = false;
    	List<RecommendUserPO> reUser = recommendUserDao.getRecommendUser(scheme);
    	if(reUser!=null&&reUser.size()>0){
    		isRecommend = true;
    	}
    	return isRecommend;
    }
    
	@Override
	@Transactional
	public void findMyJoinFollowList(Paging paging, int status,
			String lotteryId, Long schemeId, long sponsorid, String playId,
			String passType,Date from,Date to) {
		if(status==0){
			status=-1;
		}
		List<BetSchemePO> betSchemePOs=betSchemeDao.findMyJoinFollowScheme(paging, status, lotteryId, schemeId, sponsorid, playId, passType,from,to);
		List<BetScheme> betSchemes=new ArrayList<BetScheme>();
		BetScheme betScheme;
		if(betSchemePOs!=null)
			for(BetSchemePO betSchemePO:betSchemePOs){
				betScheme=new BetScheme();
				BeanUtils.copyProperties(betSchemePO, betScheme);
				betSchemes.add(betScheme);
			}
		paging.setResults(betSchemes);
	}

	@Override
	@Transactional
	public void findMyLaunchFollowList(Paging paging, int status,
			String lotteryId, Long schemeId, long sponsorid, String playId,
			String passType,Date from,Date to) {
		if(status==0){
			status=-1;
		}
		List<BetSchemePO> betSchemePOs=betSchemeDao.findLaunchShowingScheme(paging, status, lotteryId, schemeId, sponsorid, playId, passType,from,to);
		PagingUtils.makeCopyAndSetPaging(betSchemePOs, paging, BetScheme.class);
	}
	
	@Override
	@Transactional(readOnly = true)
	public void listCommissionOutByUserId(Long userId,Date startDate, Date endDate, Paging paging){
		List<Object[]> objects = betSchemeDao.findCommissionOutList(paging, userId, startDate, endDate);
		if(objects==null)
			return;
		Map<Long, Object[]> ids = new HashMap<Long, Object[]>();
		for(Object[] object:objects){
			ids.put(Long.parseLong(object[1].toString()), object);
		}
		List<OrderPO> orderPOs=orderDao.getByRelatedIds(EntityType.ORDER_COMMISSION_ADD, ids.keySet());
		if(orderPOs==null){
			paging.setTotalCount(0);
			return;
		}
		List<Commission> commissions=new ArrayList<Commission>();
		Commission c;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(OrderPO op:orderPOs){
			c=new Commission();
			Object[] o=ids.get(op.getRelatedId());
			if(o!=null){
				c.setCommission(op.getAmount());
				c.setSchemeId(Long.parseLong(o[0].toString()));
				c.setSponsor(op.getUsername());
				try {
					c.setCreatedTime(df.parse(o[2].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.setFollowSchemeId(Long.parseLong(o[3].toString()));
				commissions.add(c);
			}
		}
		paging.setResults(commissions);
	}

	@Override
	@Transactional(readOnly = true)
	public void listCommissionInByUserId(Long userId,
			Date startDate, Date endDate, Paging paging) {
		List<OrderPO> orderPOs=orderDao.listCommission(paging, userId, startDate, endDate);
		if(orderPOs==null)
			return;
		Map<Long, OrderPO> ids = new HashMap<Long, OrderPO>();
		for(OrderPO op:orderPOs){
			ids.put(op.getRelatedId(),  op);
		}
		List<Object[]> betSchemePOs=betSchemeDao.findBetSchemeListByPartnerIds(ids.keySet());
		List<Commission> commissions=new ArrayList<Commission>();
		Commission c;
		for(Object[] bs:betSchemePOs){
			c=new Commission();
			OrderPO op = ids.get(bs[2]);
			if(op != null) {
				c.setCommission(op.getAmount());
				c.setCreatedTime(op.getCreatedTime());
				c.setSponsor(bs[0].toString());
				c.setSchemeId(Long.parseLong(bs[3].toString()));
				c.setFollowSchemeId(Long.parseLong(bs[1].toString()));
				commissions.add(c);
			}
		}
		paging.setResults(commissions);
	}

	@Override
	@Transactional(readOnly = true)
	public void findMyJoinGroupbuyList(Paging paging, int status,
			String lotteryId, Long sponsorId, String playId, String passType,
			Date from, Date to) {
		if(status==0){
			status=-1;
		}
		List<BetSchemePO> betSchemePOs= betSchemeDao.findMyJoinGroupbuyScheme(paging, status, lotteryId, sponsorId, playId, passType, from, to);
		PagingUtils.makeCopyAndSetPaging(betSchemePOs, paging, BetScheme.class);
	}

	@Override
	@Transactional(readOnly = true)
	public void findMyLaunchGroupbuyList(Paging paging, int status,
			String lotteryId, long sponsorid, String playId, String passType,
			Date from, Date to) {
		if(status==0){
			status=-1;
		}
		List<BetSchemePO> betSchemePOs = betSchemeDao.findLaunchGroupbuyScheme(paging, status, lotteryId, sponsorid, playId, passType, from, to);
		PagingUtils.makeCopyAndSetPaging(betSchemePOs, paging, BetScheme.class);
	}

	@Override
	public void computeMinAndMaxBonus(BetScheme scheme, Bet bet) {
		bsService.computeMinAndMaxBonus(scheme,bet);
		
	}
}
