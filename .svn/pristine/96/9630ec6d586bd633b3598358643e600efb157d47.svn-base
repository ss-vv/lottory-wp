package com.davcai.lottery.push.common.odds.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.push.common.model.LotteryType;
import com.davcai.lottery.push.common.model.OddsType;
import com.davcai.lottery.push.common.odds.dao.JCZQOddsMessageDao;
import com.davcai.lottery.push.common.redis.RedisClient;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;

/**
 * 
 * @author peng
 *
 * @since 2015年3月3日下午3:25:55
 */
@Service
public class JCZQOddsMessageServiceImpl implements JCZQOddsMessageService {

	private static final String PLAYING_CODE_ID = "playingCodeId";
	@Autowired
	private JCZQOddsMessageDao jCZQOddsMessageDao;
	@Autowired
	private RedisClient redisClient;
	@Override
	public List<Map<String, String>> findFbEuroOdds(String corpId) {
		// TODO Auto-generated method stub
		List<Map<String, String>> oddslList = null;
		if(StringUtils.isNotBlank(corpId)){
			//根据在售赛程查询赔率
			List<Object[]> opOddsInfoPOs = jCZQOddsMessageDao.queryMatchCorpOdds(corpId);
			if(!opOddsInfoPOs.isEmpty()){
				oddslList = new ArrayList<>();
				Map<String, String> data = null;
				for(Object[] odds:opOddsInfoPOs){
					FbMatchOpOddsInfoPO oddsInfoPO = (FbMatchOpOddsInfoPO) odds[0];
					String euroOdds = oddsInfoPO.getEuroOdds();
					if(StringUtils.isNotBlank(euroOdds)){
						data = new HashMap<String, String>();
						String[] oddsData = euroOdds.split("!");
						data.put("data", oddsData[0].substring(0,oddsData[0].lastIndexOf(","))+","+oddsData[oddsData.length-2].substring(0,oddsData[oddsData.length-2].lastIndexOf(",")));
						Date matchTime = (Date) odds[2];
						data.put(PLAYING_CODE_ID, DateFormatUtils.format(matchTime, "yyyyMMddHHmmss")+"-"+odds[1]);
						redisClient.addOdds(data.get(PLAYING_CODE_ID),data.get("data"),corpId,OddsType.europe,LotteryType.JCZQ);
						oddslList.add(data);
					}
				}
			}
		}
		return oddslList;
	}

}
