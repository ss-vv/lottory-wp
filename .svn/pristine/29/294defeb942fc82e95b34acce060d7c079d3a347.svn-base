package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.AdvertisementPO;

public interface AdvertisementDao extends Dao<AdvertisementPO>{

	List<AdvertisementPO> getAllAd(Paging p);
	
	Integer getCountAd();
	
	void updateAd(Long id,Integer status);
	
	AdvertisementPO getAdById(Long id);
	
	void deleteAd(Object id);
	
	void updateAd(AdvertisementPO ad);
	
	AdvertisementPO getDisplayAd();
	
}
