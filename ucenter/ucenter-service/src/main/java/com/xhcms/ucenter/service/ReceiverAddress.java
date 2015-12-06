package com.xhcms.ucenter.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ReceiverAddress implements Serializable{
	private static final long serialVersionUID = 9044900275940546822L;
	private List<String> addresses;

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}
	
	public void setAddressesStr(String addressesStr){
		if(StringUtils.isNotBlank(addressesStr)){
			String[] splits = addressesStr.split(",");
			if(null!=splits&&splits.length>0){
				addresses=new ArrayList<String>();
				for(String split:splits){
					addresses.add(split);
				}
			}
		}
	}
}
