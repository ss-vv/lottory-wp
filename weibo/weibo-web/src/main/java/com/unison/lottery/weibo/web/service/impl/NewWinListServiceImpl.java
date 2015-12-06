package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.common.nosql.Top5RecommendDao;
import com.unison.lottery.weibo.data.Top5Recommend;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.NewWinListService;
import com.xhcms.lottery.commons.data.ShowWinListVo;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.FollowWinListDao;
import com.xhcms.lottery.commons.persist.dao.ShowWinListDao;
@Service
public class NewWinListServiceImpl implements NewWinListService{

	@Autowired
	private ShowWinListDao showWinListDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
    private FollowWinListDao followWinListDao;
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
	private Top5RecommendDao top5RecommendDao;
	/**
	 * 晒单助人7日奖金
	 * @return
	 */
	@Override
	@Transactional
	public List<ShowWinListVo> findShowWinList(String key) {
		return findWinList(key);
	}
	private List<ShowWinListVo> findWinList(String key){
		List<String> keys=top5RecommendDao.lrange(key, 0, -1);
		return find7DayShowWinPrize(keys);
	}
	private List<ShowWinListVo> find7DayShowWinPrize(List<String> keys){
		 List<ShowWinListVo> showWinListVo=new ArrayList<ShowWinListVo>();
		if(keys!=null && keys.size()>0){
			String []arrayKey=(String[])keys.toArray(new String[keys.size()]);
			List<Top5Recommend> top5Recommends=top5RecommendDao.hashList(arrayKey);
			if(top5Recommends!=null && top5Recommends.size()>0){
				  for(Top5Recommend top:top5Recommends){
					  ShowWinListVo vo=new ShowWinListVo();
					  findWeiboUserInfo(top,vo);
					  showWinListVo.add(vo);
				  }
			}
		}
	   return showWinListVo;	
	}
	
	private void findWeiboUserInfo(Top5Recommend recommend,ShowWinListVo showVo){
		if(recommend!=null){
			WeiboUser w=userAccountService.findWeiboUserByLotteryUid(recommend.getUserId()+"");
			showVo.setWeiboUserId(w.getWeiboUserId());
			showVo.setNickName(w.getNickName());
			showVo.setHeadImageURL(w.getHeadImageURL());
		    showVo.setBonus_(recommend.getBonus());
		    if("showSchemeHelp".equals(recommend.getTopType())){
		    	showVo.setScheme("晒单"+recommend.getCountOfRecommend()+"单");
		    	showVo.setFollowSchemeCount("跟单"+recommend.getCountOfHit()+"单");
		    	showVo.setTopType("showSchemeHelp");
		    }else if("showSchemeShenglv".equals(recommend.getTopType())){
		    	showVo.setScheme("开奖"+recommend.getCountOfRecommend()+"单");
		    	showVo.setWinRate(Double.parseDouble(recommend.getRatio()+""));
		    	showVo.setWinSchemeCount("盈利"+recommend.getCountOfHit()+"单");
		    	showVo.setTopType("showSchemeShenglv");
		    }else if("followSchemeBonus".equals(recommend.getTopType())){
		    	showVo.setWinCount("中奖"+recommend.getCountOfHit()+"单");
		    	showVo.setFollowSchemeCount("跟单"+recommend.getCountOfRecommend()+"单");
		    	showVo.setTopType("followSchemeBonus");
		    }else if("followSchemeShenglv".equals(recommend.getTopType())){
		    	showVo.setScheme("跟单"+recommend.getCountOfRecommend()+"单");
		    	showVo.setWinSchemeCount("盈利"+recommend.getCountOfHit()+"单");
		    	showVo.setWinRate(Double.parseDouble(recommend.getRatio()+""));
		    	showVo.setTopType("followSchemeShenglv");
		    }
		}
	}
	/**
	 * 跟单中奖 7日
	 * @return
	 */
	@Override
	@Transactional
	public List<ShowWinListVo> findFollowWinList(String key) {
	
		return findWinList(key);
	}
	/**
	 * 晒单助人50单
	 * @return
	 */
	@Override
	@Transactional
	public List<ShowWinListVo> findMatch50ShowSchemeWinList(String key) {
		return findWinList(key);
	}
	/**
	 * 跟单中奖50单
	 * @return
	 */
	@Override
	@Transactional
	public List<ShowWinListVo> findMatch50FollowSchemeWinList(String key) {
		return findWinList(key);
	}
	 /**
     * 7日内晒单盈利率
     * @return
     */
	@Override
	@Transactional
	public List<ShowWinListVo> findDay7ShowSchemeYingLiLv(String key) {
		return findWinList(key);
	}

    /**
     * 50单晒单盈利率
     * @return
     */
	@Override
	@Transactional
	public List<ShowWinListVo> findMatch50ShowSchemeYingLiLv(String key) {
		return findWinList(key);
	}

    /**
     * 7日内跟单盈利率
     * @return
     */
	@Override
	@Transactional
	public List<ShowWinListVo> findDay7FollowSchemeYingLilv(String key) {
		
		return findWinList(key);
	}

    /**
     * 50单跟单胜率
     * @return
     */
	@Override
	@Transactional
	public List<ShowWinListVo> findMatch50FollowSchemeYingLiLv(String key) {
	
		return findWinList(key);
	} 
}
