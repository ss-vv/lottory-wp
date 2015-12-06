package com.unison.lottery.weibo.mq;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.nosql.MatchDao;
import com.unison.lottery.weibo.common.service.WeiboMatchIdService;
import com.unison.lottery.weibo.data.MatchIdInfo;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.lang.LotteryBall;
import com.unison.lottery.weibo.lang.WeiboType;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.RecommendSchemeMessage;
import com.xhcms.lottery.lang.LotteryId;

/**
 * @desc 处理异步发送‘推荐微博’操作
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-31
 * @version 1.0
 */
public class AsyncPublishRecommendHandler implements
		MessageHandler<RecommendSchemeMessage> {

	private static final long serialVersionUID = -5764055835469889778L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MessageService messageService;

	@Autowired
	private RelationshipService relationshipService;
	
	@Autowired
	private MatchDao matchDao;

	@Autowired
	private WeiboMatchIdService matchIdService;
	
	@Override
	public void handle(RecommendSchemeMessage cmd) {
		logger.debug("开始处理异步发布命令：{}", cmd);
		long start = System.currentTimeMillis();
		try {
			String weiboContent = cmd.getWeiboContent();
			long schemeId = cmd.getSchemeId();
			long weiboUserId = cmd.getWeiboUserId();
			WeiboMsg weiboMsg = new WeiboMsg();
			weiboMsg.setContent(weiboContent);
			weiboMsg.setSchameId(schemeId);
			weiboMsg.setOwnerId(weiboUserId);
			weiboMsg.setType(WeiboType.RECOMMEND.getType());

			WeiboMsgVO vo = messageService.publish(weiboMsg);
			if(null != vo && vo.getId() > 0) {
				messageService.addRecommendSchemeToWeiboTimeline(schemeId, vo.getId());
				matchDao.addToLotteryRecommendTimeline(LotteryId.get(cmd.getLotteryId()), vo.getId(), vo.getCreateTime());
				matchDao.addToLotteryRecommendTimeline(LotteryId.UNKNOWN, vo.getId(), vo.getCreateTime());
			} else {
				logger.error("保存推荐方案到微博索引错误：recommendSchemeId={}, weiboId={}", 
						schemeId, vo.getId());
				logger.error("处理异步发推荐微博命令:{}, 发布返回结果为空.", new Object[] { cmd });
			}

			handlePostFollowers(weiboMsg);
			
			incrRecommendAndShowSchemeNumber(weiboMsg);
		} catch (Exception e) {
			logger.error("处理异步发布‘推荐微博’命令失败！", e);
		}
		// 记录用时
		long end = System.currentTimeMillis();
		logger.debug("处理异步发布命令结束，耗时：{}秒", (end - start) / 1000.0);
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
	 * 2.自增比赛推荐微博数
	 * @param weiboMsg
	 */
	private void incrRecommendAndShowSchemeNumber(WeiboMsg weiboMsg) {
		List<MatchIdInfo> matchIdList = matchIdService.reversionAndCheckMatchId(weiboMsg);
		if(null != matchIdList) {
			long ownerId = weiboMsg.getOwnerId();
			for(MatchIdInfo info : matchIdList) {
				LotteryBall ball = LotteryBall.ball(info.getLottery());
				if(null != ball && info.getQtMatchId() > 0) {
					if(ownerId > 0) {
						matchDao.addToPublishRecomAndShowUsers(info.getQtMatchId(), ball.getValue(), ownerId);
					}
					matchDao.addToMatchRecommendNumber(info.getQtMatchId(), ball.getValue());
				}
			}
		}
	}
	
}
