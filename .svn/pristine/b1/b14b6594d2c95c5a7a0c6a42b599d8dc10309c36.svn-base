package com.unison.lottery.weibo.web.action.pc.alipaylogin;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.alipay.AlipayBean;
import com.unison.lottery.weibo.web.alipay.alipaycore.AlipaySubmit;

public class AlipayAction extends BaseAction{

	private static final long serialVersionUID = -6851911838896457849L;

	@Autowired
	private AlipayBean alipayBean;
	private String return_url;
	private Map<String,String> alipayMap;
	private String referer;
	public String execute(){
		
		fillMap();
		String sign=AlipaySubmit.buildRequestMysign(alipayMap, alipayBean);
		alipayBean.setSign(sign);

		return SUCCESS;
	}

	private void fillMap(){
		alipayMap=new HashMap<String,String>();
		alipayMap.put("service", alipayBean.getService());
		alipayMap.put("partner", alipayBean.getParenter());
		alipayMap.put("_input_charset", alipayBean.getInput_charset());
		String return_url=alipayBean.getReturn_url();
        if(!StringUtil.isBlank(referer)){
        	return_url+="?referer="+referer;
        	this.return_url=return_url;
        }
		alipayMap.put("return_url", return_url);
		alipayMap.put("target_service", alipayBean.getTarget_service());
		
	}

	public AlipayBean getAlipayBean() {
		return alipayBean;
	}

	public void setAlipayBean(AlipayBean alipayBean) {
		this.alipayBean = alipayBean;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	
	
	
}
