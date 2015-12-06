package com.unison.lottery.weibo.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.MatchDao;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.common.service.TagService;
import com.unison.lottery.weibo.common.service.WeiboMatchIdService;
import com.unison.lottery.weibo.data.MatchIdInfo;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.lang.LotteryBall;
import com.unison.lottery.weibo.lang.WeiboType;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.service.ShareOrderTemplateService;
import com.unison.lottery.weibo.service.WeiboSchemeService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.utils.LotteryUtil;
import com.unison.thrift.client.bet.BetSchemeClient;
import com.unison.thrift.scheme.service.gen.BetSchemeData;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.weibo.SchemeHandle;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.BetPartnerService;
import com.xhcms.lottery.commons.persist.service.WeiboService;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class WeiboSchemeServiceImpl implements WeiboSchemeService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BetSchemeClient betSchemeClient;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private WeiboService weiboService;
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private RelationshipService relationshipService;
	
	@Autowired
	private MatchDao matchDao;
	
	@Autowired
	private WeiboMatchIdService matchIdService;
	
	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private ShareOrderTemplateService orderTemplateService;
	@Autowired
	private TagService tagService;
	@Autowired
	private BetPartnerService betPartnerService;
	
	private long loginUserId;
	private long betRecordId;
	
	private Long failRetry = 60L;		//失败重试的次数
	private long internalTime = 10000L;	//10秒重试一次

	@Override
	public void publish(SchemeHandle schemeHandle) {
		long schemeId = schemeHandle.getSchemeId(); 
		BetSchemeData betSchemeData = betSchemeClient.getSchemeById(schemeId);
		
		if (null != betSchemeData && betSchemeData.getSchemeId() > 0) {
			deliverAsyncReq(schemeHandle);
		} else {
			logger.error("异步发布方案微博，schemeId={}, 查询方案信息为空。",schemeId);
			postRetry(schemeHandle);
		}
	}
	
	private boolean deliverAsyncReq(SchemeHandle schemeHandle) {
		long schemeId = schemeHandle.getSchemeId(); 
		boolean isBetRecordShowScheme = schemeHandle.isBetRecordShowScheme();
		String weiboContent = schemeHandle.getWeiboContent();
		this.loginUserId = schemeHandle.getLoginUserId();
		this.betRecordId = schemeHandle.getBetRecordId();
		BetSchemeData betSchemeData = betSchemeClient.getSchemeById(schemeId);
		
		boolean result = false;
		if (null != betSchemeData && betSchemeData.getSchemeId() > 0) {
			result = true;
			if(isBetRecordShowScheme) {
				int type = betSchemeData.getType();
				boolean alreadyShow = betSchemeData.isAlreadyShow();
				if(type == EntityType.BETTING_PARTNER) {
					publishWeiboWithPartnerScheme(schemeHandle);
				} else if(type == EntityType.BETTING_ALONE) {
					logger.debug("starting show scheme:schememId={}, alreadyShow={}", 
							betSchemeData.getSchemeId(), alreadyShow);
					if(alreadyShow) {
						long postId = messageService.getWeiboIdByShowSchemeId(schemeId);
						//如果方案已晒但是未找到晒单方案微博，发起晒单微博
						if(postId < 0) {
							publishWeiboWithAloneScheme(schemeId, null);
						} else {
							String forwardWeiboContent = orderTemplateService.forwardWeiboTpl();
							forwardSchemeWeibo(schemeId, betSchemeData.getSponsorId(),
									forwardWeiboContent, betSchemeData.getLotteryId());
						}
					} else {
						String tpl = orderTemplateService.schemeStatus(betSchemeData.getStatus());
						publishWeiboWithAloneScheme(schemeId, tpl);
					}
				} else if(type == EntityType.BETTING_FOLLOW && !alreadyShow) {
					logger.debug("follow scheme pubish weibo:schememId={}", betSchemeData.getSchemeId());
					publishWeiboWithFollowScheme(betSchemeData, weiboContent);
				} else {
					logger.info("对方案ID={}，发起晒单操作，方案类型={},无法匹配！", new Object[] {
							schemeId, type });
				}
			} else {
				publishWeiboWithAloneScheme(schemeId, schemeHandle.getShowSchemeSlogan());
			}
		}
		return result;
	}
	
	/**
	 * 异步发微博失败尝试
	 * 如果能取到微博方案ID的方案信息则表示尝试成功，退出尝试
	 * */
	private void postRetry(SchemeHandle schemeHandle) {
		Long schemeId = schemeHandle.getSchemeId();
		synchronized (schemeId) {
			Long retry = 0L;//当前尝试的次数
			boolean result = false;
			long beginTime = System.currentTimeMillis();
			long currTime = 0L;
			while(retry < failRetry) {
				long internal = (currTime = System.currentTimeMillis()) - beginTime;
				if(internal >= internalTime) {
					retry = retry + 1;
					beginTime = currTime;
				} else {
					continue;
				}
				
				logger.info("publish retry:异步发布方案微博，无法获取方案ID={},正在重试...，当前尝试次数={}, 失败可尝试次数={}",
						new Object[]{schemeId, retry, failRetry});
				try {
					result = deliverAsyncReq(schemeHandle);
				} catch (Exception e) {
					logger.error("异步重试发微博异常，退出重试，方案ID={}，异常信息=", schemeId, e);
					break;
				}
				logger.info("publish retry:方案ID={},当前尝试次数={},结果={}",
						new Object[]{schemeId, retry, result});
				if(result) {
					break;
				}
			}
			logger.info("对异步发布方案微博进行重试，总共尝试次数={},尝试结果={}", retry, result);
		}
	}

	@Override
	public void publishWeiboWithFollowScheme(BetSchemeData po,String followWeiboContent) {
		logger.info("进入用户‘跟单’晒此单处理流程...当前跟单方案信息={}", po);
		WeiboMsg weiboMsg = new WeiboMsg();
		weiboMsg.setType(WeiboType.FOLLOW.getType()); //跟单微博 设置为跟单微博
		if(null != po) {
			long lotteryUserId = po.getSponsorId();
			long followedSchemeId = po.getFollowedSchemeId();
			//查找跟单的原方案
			BetSchemeData followedScheme = betSchemeClient.getSchemeById(followedSchemeId);
			logger.info("查找到跟单方案ID={},的原方案ID={},原方案信息={}",
					new Object[]{po.getSchemeId(), followedSchemeId, followedScheme});
			
			if(null != followedScheme) {
				//跟单微博用户
				WeiboUser followWeiboUser = userAccountService.findWeiboUserByLotteryUid(lotteryUserId + "");
				
				//原方案发起人对应微博用户
				long followSponsorId = followedScheme.getSponsorId();
				WeiboUser sourceSpWeiboUser = userAccountService.findWeiboUserByLotteryUid(followSponsorId + "");
				
				if(null == followWeiboUser || followWeiboUser.getId() <= 0) {
					logger.error("跟单方案ID={},跟单大V彩用户ID={},不是微博用户", 
							new Object[]{po.getSchemeId(), lotteryUserId});
				} else {
					if(sourceSpWeiboUser == null || sourceSpWeiboUser.getId() < 1){
						logger.error("大V彩用户id={}, 不是微博用户！", followSponsorId);
						
						//跟单的原方案发起人不是微博用户，则直接发晒单微博
						weiboMsg.setOwnerId(followWeiboUser.getWeiboUserId());
						weiboMsg.setSchameId(po.getSchemeId());
						weiboMsg.setContent("我发起了晒单...");
						WeiboMsgVO vo = messageService.publish(weiboMsg);
						if(null != vo) {
							messageService.addShowSchemeToWeiboTimeline(po.getSchemeId(), vo.getId());
							matchDao.addToLotteryRealFollowTimeline(LotteryId.get(po.getLotteryId()), vo.getId(), vo.getCreateTime());
							matchDao.addToLotteryRealFollowTimeline(LotteryId.UNKNOWN,  vo.getId(), vo.getCreateTime());
							betSchemeClient.updateBetSchemePublishShow(po.getSchemeId());
						}
					} else {
						logger.info("查找方案发起人ID={},对应微博用户ID={}", followSponsorId, sourceSpWeiboUser.getId());
						weiboMsg.setSchameId(po.getSchemeId());
						//找到原方案的晒单微博ID
						long sourcePostId = messageService.getWeiboIdByShowSchemeId(followedSchemeId);
						if(sourcePostId > 0) {
							weiboMsg.setOwnerId(followWeiboUser.getWeiboUserId());
							weiboMsg.setPostId(sourcePostId);
							if(StringUtils.isBlank(followWeiboContent)){
								weiboMsg.setContent(getFollowTpl(sourceSpWeiboUser.getWeiboUserId(), 
										sourceSpWeiboUser.getNickName(), followedSchemeId));
							} else {
								weiboMsg.setContent(followWeiboContent);
							}
							WeiboMsgVO vo = messageService.forward(weiboMsg);
							if(null != vo) {
								messageService.addShowSchemeToWeiboTimeline(po.getSchemeId(), vo.getId());
//								matchDao.addToLotteryRealFollowTimeline(LotteryId.get(po.getLotteryId()), vo.getId(), vo.getCreateTime());
//								matchDao.addToLotteryRealFollowTimeline(LotteryId.UNKNOWN,  vo.getId(), vo.getCreateTime());
								betSchemeClient.updateBetSchemePublishShow(po.getSchemeId());
							}
						} else {
							logger.error("未找到原方案ID={},对应晒单微博", followedSchemeId);
						}
					}
				}
			}
		}
	}

	@Override
	public void publishWeiboWithPartnerScheme(SchemeHandle schemeHandle) {
		logger.info("当前合买方案信息={}", schemeHandle);
		WeiboMsg weiboMsg = new WeiboMsg();
		weiboMsg.setType(WeiboType.GROUP.getType());
		weiboMsg.setBetRecordId(betRecordId);
		long sponsorId = schemeHandle.getSponsorId();
		BetPartner sponsorBetPartner = betPartnerService.findGroupSponsorRecord(schemeHandle.getSchemeId(), sponsorId);
		if(null != sponsorBetPartner && sponsorBetPartner.getId() > 0) {
			//到此找到了合买发起人的微博ID
			long postId = messageService.getWeiboIdByGroupBuyRecordId(sponsorBetPartner.getId());
			if(null != schemeHandle && postId > 0) {
				WeiboUser weiboUser = userAccountService.findWeiboUserByLotteryUid(loginUserId + "");
				weiboMsg.setPostId(postId);
				weiboMsg.setOwnerId(weiboUser.getWeiboUserId());
				int buyAmount = schemeHandle.getBuyAmount();
				String content = schemeHandle.getWeiboContent() + " 认购金额：" + buyAmount + "元！";
				weiboMsg.setContent(content);
				//转发微博
				messageService.forward(weiboMsg);
			}
		}
	}
	
	protected String getFollowTpl(long weiboUserId, String weiboUserNickname, 
			long followedSchemeId) {
		String userHref = "@%s";
		return String.format("我跟了  " + userHref +" 发起的晒单方案；", weiboUserNickname);
	}
	
	/**
	 * 处理代购类型方案发微博
	 * @param schemeId	方案ID
	 * @param slogan	晒单宣言
	 * @return
	 */
	protected boolean processSendPost(long schemeId, String slogan) {
		boolean processResult = false;
		WeiboMsg weiboMsg = new WeiboMsg();
		long wbUserId = 0;
		long lcUserId = 0;
		
		logger.info("开始处理异步发布新微博命令。");
		long start = System.currentTimeMillis();
		List<String> matchIdList = new ArrayList<String>();
		// 处理
		try {
			BetSchemePO scheme = weiboService.getById(schemeId);
			if(scheme == null){
				logger.error("错误, 方案id={}, 方案不存在！",schemeId);
				return false;
			}
			lcUserId = scheme.getSponsorId();
			List<BetMatchPO> matchs = weiboService.findMatchsById(schemeId);
		
			// 检查用户是否是大V彩微博用户 , 并且给微博赋予微博用户ID
			WeiboUser weiboUser = userAccountService.findWeiboUserByLotteryUid(lcUserId + "");
			if(weiboUser == null || weiboUser.getId() < 1){
				logger.error("大V彩用户id={}, 不是微博用户！", lcUserId);
				end(start);
				return false;
			}
			wbUserId = weiboUser.getWeiboUserId();
			weiboMsg.setOwnerId(wbUserId);
			weiboMsg.setFrom(Constant.WeiboFrom.LC_SD);
			if(EntityType.BETTING_PARTNER == scheme.getType()) {
				weiboMsg.setType(WeiboType.GROUP.getType());
			} else {
				weiboMsg.setType(WeiboType.SHOW_SCHEME.getType());
			}
			weiboMsg.setSchameId(scheme.getId());
			weiboMsg.setBetRecordId(this.betRecordId);
			if(matchs != null && !matchs.isEmpty()) {
				String weiboContent = null;
				if(StringUtils.isNotBlank(slogan)) {
					weiboContent = slogan;
				} else {
					BigDecimal returnRation = scheme.getMaxBonus().divide(
							new BigDecimal(scheme.getTotalAmount()), 1, RoundingMode.DOWN);
					weiboContent = orderTemplateService.getShowSchemeTpl(
							LotteryUtil.getName(scheme.getLotteryId()), 
							scheme.getTotalAmount(), scheme.getMaxBonus(), returnRation, scheme.getType());
				}
				weiboMsg.setContent(weiboContent);
				for(BetMatchPO betMatch : matchs) {
					long matchId = betMatch.getMatchId();
					String matchStr = lotteryService.getMatchContent(matchId+"", 
							scheme.getLotteryId());
					matchIdList.add(matchStr);
				}
			}
			
			processResult = betSchemeClient.updateBetSchemePublishShow(schemeId);
			if(processResult) {
				WeiboMsgVO weiboMsgVO = messageService.publishWithoutScheme(weiboMsg);
				if(EntityType.BETTING_ALONE == scheme.getType() || 
						EntityType.BETTING_FOLLOW == scheme.getType()) {
					messageService.addShowSchemeToWeiboTimeline(scheme.getId(), weiboMsgVO.getId());
				} else if(EntityType.BETTING_PARTNER == scheme.getType()) {
					messageService.addGroupBuySchemeToWeiboTimeline(this.betRecordId, weiboMsgVO.getId());
				}
				
				if(matchIdList.size() > 0) {
					messageService.addToMatchTimeline(weiboMsgVO, scheme.getLotteryId(), matchIdList);
				}
				
				handlePostFollowers(weiboMsg);
				
				incrWeiboShowNumber(weiboMsg);
			} else {
				logger.error("无法更新方案ID={}，的is_show_scheme或is_publish_show字段。");
			}
		} catch (Exception e) {
			logger.error("处理发微博失败！大V彩用户id={}, 微博用户i{}", lcUserId, wbUserId,e);
		}
		end(start);
		
		logger.info("处理代购类型方案发微博,方案ID={}，result={}", schemeId, processResult);
		
		return processResult;
	}
	
	private void handlePostFollowers(WeiboMsg weiboMsg) {
		long ownerId = weiboMsg.getOwnerId();
		long postId = weiboMsg.getId();
		try {
			List<Long> followerList = relationshipService.myFollowers(ownerId);
			
			double score = Double.valueOf(weiboMsg.getCreateTime());
			messageService.postRecomRealToFollowers(followerList,
					score, postId);
		} catch (Exception e) {
			logger.error("处理将用户发的‘推荐/实单微博推给粉丝’失败！微博用户id={}, 微博id={}", ownerId,
					postId, e);
		}
	}
	
	/**
	 * 1.自增当前比赛发布推荐微博的用户数
	 * 2.比赛晒单微博数+1
	 * @param weiboMsg
	 */
	private void incrWeiboShowNumber(WeiboMsg weiboMsg) {
		List<MatchIdInfo> matchIdList = matchIdService.reversionAndCheckMatchId(weiboMsg);
		if(null != matchIdList) {
			long ownerId = weiboMsg.getOwnerId();
			for(MatchIdInfo info : matchIdList) {
				LotteryBall ball = LotteryBall.ball(info.getLottery());
				if(null != ball && info.getQtMatchId() > 0) {
					if(ownerId > 0) {
						matchDao.addToPublishRecomAndShowUsers(info.getQtMatchId(), ball.getValue(), ownerId);
					}
					matchDao.addToMatchShowSchemeNumber(info.getQtMatchId(), ball.getValue());
				}
			}
		}
	}
	
	private void end(long start){
		// 记录用时
		long end = System.currentTimeMillis();
		logger.debug("处理发布命令结束，耗时：{}秒", (end-start)/1000.0);
	}

	@Override
	public void publishWeiboWithAloneScheme(long schemeId, String slogan) {
		logger.info("发布 schemeId = {} 的“代购类型”实单！",schemeId);
		processSendPost(schemeId, slogan);
	}
	
	@Override
	public void forwardSchemeWeibo(long schemeId, long sponsorId,
			String forwardWeiboContent, String lotteryId) {
		WeiboMsg weiboMsg = new WeiboMsg();
		weiboMsg .setSchameId(schemeId);
		long postId = messageService.getWeiboIdByShowSchemeId(schemeId);
		if(postId > 0) {
			String ownerId = userAccountService.getWeiboUidByLotteryUid(sponsorId+"");
			weiboMsg.setOwnerId(Long.valueOf(ownerId));
			weiboMsg.setPostId(postId);
			weiboMsg.setContent(forwardWeiboContent);
			WeiboMsgVO vo = messageService.forward(weiboMsg);
			if(null != vo) {
				messageService.addShowSchemeToWeiboTimeline(schemeId, vo.getId());
				//转发晒单，不再把微博加入晒单时间线
//				matchDao.addToLotteryRealFollowTimeline(LotteryId.get(lotteryId), vo.getId(), vo.getCreateTime());
//				matchDao.addToLotteryRealFollowTimeline(LotteryId.UNKNOWN,  vo.getId(), vo.getCreateTime());
				betSchemeClient.updateBetSchemePublishShow(schemeId);
			}
		} else {
			logger.error("转发方案ID={} 的微博，找不到微博信息.", schemeId);
		}
	}
}
