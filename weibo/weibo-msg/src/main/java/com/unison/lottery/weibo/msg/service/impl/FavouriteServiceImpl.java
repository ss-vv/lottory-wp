package com.unison.lottery.weibo.msg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.FavouriteDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.impl.BaseServiceImpl;
import com.unison.lottery.weibo.data.Favourite;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.FavoriteService;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.utils.DateUtil;

@Service
public class FavouriteServiceImpl extends BaseServiceImpl implements FavoriteService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private MessageService messageService;
	@Autowired
	private FavouriteDao favouriteDao;

	@Override
	public boolean add(long userId, long postId) {
		if(favouriteDao.save(userId, postId) > 0){
			log.info("用户={} , 收藏微博={} 成功！",userId, postId);
			messageService.increaseFavoriateCount(postId);
			return true;
		}
		log.info("用户={} , 收藏微博={} 已收藏！",userId, postId);
		return false;
	}

	@Override
	public PageResult<Favourite> list(long userId, PageRequest pageRequest) {
		List<Favourite> favourites = new ArrayList<Favourite>();
		try {
			PageResult<Tuple> result = descListSortedSetByPageRequestWithScores(Keys.favorites(userId), pageRequest);
			Map<Long, Double> maps = new HashMap<Long, Double>();
			Long[] ids = new Long[result.getResults().size()];
			int i=0;
			for(Tuple tuple : result.getResults()){
				Long id = Long.parseLong(tuple.getElement());
				maps.put(id, tuple.getScore());
				ids[i] = id;
				i++;
			}
			for(WeiboMsgVO weibo : messageService.checkLikeSetFavoriate(userId, messageService.listPost(ids, pageRequest)).getResults()){
				Favourite favourite = new Favourite(); 
				favourite.setWeiboMsg(weibo);
				favourite.setFavouriteTimeFmt(DateUtil.format(maps.get(weibo.getId()).longValue()));
				favourites.add(favourite);
			}
		} catch (Exception e) {
			log.error("list Error ! uid = {} ", userId, e);
		}
		return new PageResult<Favourite>(pageRequest, favourites);
	}

	@Override
	public boolean cancel(long userId, long postId) {
		if(favouriteDao.delete(userId, postId) > 0){
			messageService.increaseFavoriateCountDown(postId);
			log.info("用户={} , 取消收藏={} 成功！",userId, postId);
			return true;
		}
		log.info("用户={} , 取消收藏={} 未收藏！",userId, postId);
		return false;
	}

	public void setFavouriteDao(FavouriteDao favouriteDao) {
		this.favouriteDao = favouriteDao;
	}

	@Override
	protected BaseDao<?> getBaseDao() {
		return favouriteDao;
	}

}
