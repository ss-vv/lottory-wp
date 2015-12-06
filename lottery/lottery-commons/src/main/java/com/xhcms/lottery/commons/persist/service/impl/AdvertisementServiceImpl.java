package com.xhcms.lottery.commons.persist.service.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.dao.AdvertisementDao;
import com.xhcms.lottery.commons.persist.entity.AdvertisementPO;
import com.xhcms.lottery.commons.persist.service.AdvertisementService;
@Service
public class AdvertisementServiceImpl implements AdvertisementService{

	@Autowired
	private AdvertisementDao adDao;
	@Transactional
	@Override
	public void displayAd(Paging p) {
		 Integer count=adDao.getCountAd();
		 p.setTotalCount(count);
		 List<AdvertisementPO> list= adDao.getAllAd(p);
		 p.setResults(list);
		
	}
	@Transactional
	@Override
	public void addAd(AdvertisementPO ad) {
		ad.setCreatedTime(new Date());
		ad.setUpdateTime(new Date());
		AdvertisementPO nad=new AdvertisementPO();
		try {
			BeanUtils.copyProperties(nad, ad);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    adDao.save(nad);
		
	}
	@Transactional
	@Override
	public boolean putOnAd(Long id) {
		boolean flag=false;
		
		try {
			adDao.updateAd(id, 0);
			flag=true;
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return flag;
	}
	@Transactional
	@Override
	public boolean getOffAd(Long id) {
		boolean flag=false;
	      try {
			adDao.updateAd(id, -1);
			flag=true;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		 return flag;
	}
	@Transactional
	@Override
	public boolean deleteAd(Long id) {
		boolean flag=false;
		AdvertisementPO adpo=null;
		try {
			adpo=adDao.getAdById(id);
			if(adpo!=null){
				
				File file=new File(adpo.getPicPath());
				if(file.exists()){
					file.delete();
				}
				adDao.deleteAd(adpo);
				flag=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	@Transactional
	@Override
	public boolean updateAd(AdvertisementPO ad) {
		boolean flag=false;
		AdvertisementPO nad=new AdvertisementPO();
		try {
			BeanUtils.copyProperties(nad, ad);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			AdvertisementPO adpo=adDao.getAdById(ad.getId());
			if(adpo!=null&& !ad.getPicPath().equals(adpo.getPicPath())){
				
			 File file=new File(adpo.getPicPath());
			 if(file.exists()){
				 file.delete();
			 }
			}
			nad.setUpdateTime(new Date());
			adDao.updateAd(nad);
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	@Transactional
	@Override
	public AdvertisementPO getAdById(Long id) {
	
		return adDao.get(id);
	}
	@Transactional
	@Override
	public AdvertisementPO getAd() {
	
		return adDao.getDisplayAd();
	}


}
