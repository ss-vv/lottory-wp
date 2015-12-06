package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.Top5Recommend;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.Top5RecommendService;
import com.xhcms.lottery.commons.data.Top5RecommendVo;
import com.xhcms.lottery.commons.persist.dao.Top5RecommendDBDao;
import com.xhcms.lottery.commons.persist.entity.Top5RecommendPO;

@Service
public class Top5RecommendServiceImpl implements Top5RecommendService{

	@Autowired
	private Top5RecommendDBDao top5RecommendDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
    private com.unison.lottery.weibo.common.nosql.Top5RecommendDao resTop5RecommendDao;
	 //findWeiboUserByLotteryUid
	@Override
	@Transactional
	public List<Top5RecommendVo> findTop5Rcommend(String key,String topType,String dimension) {
		List<Top5RecommendVo> topList=new ArrayList<Top5RecommendVo>();
		//从redis 中查数据
		 findTop5RecommendFormRedis(key,topList);
		 if(topList.size()<=0){
			//从数据库中查数据
			findTop5RecommendFormDB(topType,dimension,topList);
		 }
		
		return topList;
	}
	
	private void findTop5RecommendFormDB(String topType,String dimension,List<Top5RecommendVo> list){
		List<Top5RecommendPO> top=top5RecommendDao.generalFindTop5Recommend(topType, dimension);
		if(top!=null && top.size()>0){
			for(Top5RecommendPO t:top){
				Top5RecommendVo vo=new Top5RecommendVo();
				BeanUtils.copyProperties(t, vo);
				WeiboUser w=userAccountService.findWeiboUserByLotteryUid(t.getUserId()+"");
				vo.setNickName(w.getNickName());
		        vo.setWeiboUserId(w.getWeiboUserId());
				vo.setHeadImageURL(w.getHeadImageURL());
				list.add(vo);
				
			}
			
		}
		
	}
	private void findTop5RecommendFormRedis(String key,List<Top5RecommendVo> list){
		List<String> keys=resTop5RecommendDao.lrange(key, 0, -1);
		if(keys!=null && keys.size()>0){
			String []arrayKey=(String[])keys.toArray(new String[keys.size()]);
			List<Top5Recommend> top5Recommends=resTop5RecommendDao.hashList(arrayKey);
            if(top5Recommends!=null && top5Recommends.size()>0){
            	for(Top5Recommend t:top5Recommends){
            		Top5RecommendVo tvo=new Top5RecommendVo();
            		tvo.setCountOfHit(t.getCountOfHit());
            		tvo.setCountOfRecommend(t.getCountOfRecommend());
            		tvo.setDimension(t.getDimension());
            		tvo.setId(t.getId());
            		tvo.setRatio(Double.parseDouble(t.getRatio()+""));
            		tvo.setUserId(t.getUserId());
                    //BeanUtils.copyProperties(t, tvo);
                    WeiboUser w=userAccountService.findWeiboUserByLotteryUid(t.getUserId()+"");
                    tvo.setNickName(w.getNickName());
                    tvo.setWeiboUserId(w.getWeiboUserId());
                    tvo.setHeadImageURL(w.getHeadImageURL());
                    list.add(tvo);
            	}
            	
            }
		}
		
	} 

}
