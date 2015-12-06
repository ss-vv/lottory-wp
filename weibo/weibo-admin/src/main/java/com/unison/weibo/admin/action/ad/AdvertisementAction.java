package com.unison.weibo.admin.action.ad;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.weibo.admin.action.BaseAction;
import com.xhcms.commons.lang.Data;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Advertisement;
import com.xhcms.lottery.commons.persist.service.AdvertisementService;

public class AdvertisementAction extends BaseAction{

	private static final long serialVersionUID = 5764572804995168989L;
	Logger log = LoggerFactory.getLogger(getClass());
	private Paging p;
	private Integer page=0;
	private Advertisement ad;
	private String hrefLink;
	private String picPath;
	private Integer status;
	@Autowired
	private AdvertisementService adService;
	
	public AdvertisementAction(){
		if(p==null){
			
			p=new Paging();
		}
		p.setMaxResults(5);
		
	}
	//分页显示
	public String showAd(){
		if(page>0){
			p.setPageNo(page);	
		}
		adService.displayAd(p);
		return SUCCESS;
	}
	//添加
	public String addAd(){
		if(valid()){
			
			adService.addAd(ad);
			
		}else{
			
			super.setData(Data.failure(null));
		}
		return SUCCESS;
	}
	//下架广告
	public String getOffAd(){
		boolean flag=adService.getOffAd(ad.getId());
		setData(flag);
		return SUCCESS;
	}
	//上架广告
	public String putOnAd(){
		boolean flag=adService.putOnAd(ad.getId());
		setData(flag);
		return SUCCESS;
	}
	//删除
	public String deleteAd(){
		boolean flag=adService.deleteAd(ad.getId());
		setData(flag);
		return SUCCESS;
	}
	//更新
	public String updateAd(){
		boolean flag=adService.updateAd(ad);
		setData(flag);
		return SUCCESS;
	}
	//异步获取一个ad
	public String getAdById(){
		setData(adService.getAdById(ad.getId()));
		return SUCCESS;
	}
	private boolean valid(){
		boolean flag=false;
		if(StringUtils.isNotBlank(ad.getHrefLink())&&StringUtils.isNotBlank(ad.getPicPath())&&ad.getStatus()!=null){
			
			flag=true;
		}
		
		return flag;
	}

	public Paging getP() {
		return p;
	}
	public void setP(Paging p) {
		this.p = p;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public AdvertisementService getAdService() {
		return adService;
	}
	public void setAdService(AdvertisementService adService) {
		this.adService = adService;
	}
	public String getHrefLink() {
		return hrefLink;
	}
	public void setHrefLink(String hrefLink) {
		this.hrefLink = hrefLink;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Advertisement getAd() {
		return ad;
	}
	public void setAd(Advertisement ad) {
		this.ad = ad;
	}

	
}
