package com.unison.lottery.weibo.data.crawler.service.store.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtOddsCompanyModel;

/**
 * 抓取 赔率公司数据
 * @author guixiangcui
 *
 */
public interface OddsCompanyStoreDao {

	/**
	 * 抓取赔率公司
	 * 其中包括欧赔（17家）亚赔/大小（12）家
	 */
	 void crawlerOdddCompany();
	 
	 /**
	  * 存储赔率公司
	  */
	 void storeOdddCompany(List<QtOddsCompanyModel> oddsCompanyModels) throws Exception;
	 
	 /**
	  * 存储亚配数据
	 * @param ou 
	  */
	 void storeAsianOddDatas(List<QtMatchOpOddsModel> OpOddsData, Qt_fb_match_oddsType ou);
}
