package com.unison.lottery.weibo.common.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.HotRecommendDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.HotRecommendService;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class HotRecommendServiceImpl extends BaseServiceImpl implements
		HotRecommendService {

	@Autowired
	private HotRecommendDao hotRecommendDao;
	
	@Autowired
	private LotteryService lotteryService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected BaseDao<?> getBaseDao() {
		return hotRecommendDao;
	}
	
	@Override
	public PageResult<WeiboMsgVO> query(long uid, PageRequest pageRequest) {
		String key = Keys.HOTRECOMMEND;
		PageResult<WeiboMsg> modelResult = listSortedSetDesc(key,
				WeiboMsg.class, pageRequest);
		PageResult<WeiboMsgVO> voResult = fillVOPageResult(modelResult,
				WeiboMsgVO.class);
		//检查话题对应的微博是否被自己“收藏”和“赞”过
		voResult = checkFavoriateAndLike(uid, voResult);
		return voResult;
	}

	@Override
	public void createRecommendIndexOfDate(RecentDateType recentDateType) {
		//遍历彩种
		List<LotteryId> list = Arrays.asList(LotteryId.values());
		for(LotteryId lottery : list) {
			//查询彩种对应的微博彩种用户ID
			String lotteryIdUid = Keys.lotteryIdUid(lottery.name());
			String weiboUserId = hotRecommendDao.getString(lotteryIdUid);
			String userRecommendsKey = Keys.userRecommendsKey(weiboUserId);
			log.info("获取彩种={}, 微博用户={},推荐列表.", 
					new Object[]{lottery.name(), weiboUserId});
			if(StringUtils.isNotBlank(userRecommendsKey) && StringUtils.isNotBlank(weiboUserId)) {
				//获取彩种用户的推荐微博列表 
				Set<String> recommendSets = lotteryService.lotteryUserRecommend(weiboUserId, recentDateType);
				log.info("微博用户={},推荐列表={}", new Object[]{weiboUserId, recommendSets});
				
				if(null != recommendSets && recommendSets.size() > 0) {
					long start = System.currentTimeMillis();
					for(String weiboId : recommendSets) {
						WeiboMsg weiboMsg = hotRecommendDao.get(weiboId);
						if(null != weiboMsg) {
							hotRecommendDao.zadd(weiboMsg.getCommentCount(), Keys.HOTRECOMMEND, weiboId);
						}
					}
					long end = System.currentTimeMillis();
					log.info("创建热门推荐索引耗时(毫秒s)={}", (end-start));
				}
			}
		}
	}
}
