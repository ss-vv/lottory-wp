package com.xhcms.lottery.commons.persist.dao.impl;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;


import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.lang.Partner;
import com.xhcms.lottery.commons.persist.dao.ClientUpdateInfoDao;
import com.xhcms.lottery.commons.persist.entity.ClientUpdateInfoPO;




public class ClientUpdateInfoDaoImpl extends DaoImpl<ClientUpdateInfoPO> implements ClientUpdateInfoDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6541906248805415645L;
	
	private String splitMarkInClientVersion="-";

	public ClientUpdateInfoDaoImpl() {
        super(ClientUpdateInfoPO.class);
    }

	@Override
	public ClientUpdateInfoPO findByClientVersion(String clientVersion,String channel) {
		ClientUpdateInfoPO result=null;
		if(StringUtils.isNotBlank(clientVersion)){
			String platform=decidePlatform(clientVersion);
			if(StringUtils.isBlank(channel)){
				channel=Partner.DAVCAI;
			}
			if(StringUtils.isNotBlank(platform)){
				Criteria c = createCriteria()
		                .add(Restrictions.eq("platform", platform))
		                .add(Restrictions.eq("channel", channel));
		                
				ClientUpdateInfoPO currentUpdateInfoPO=(ClientUpdateInfoPO) c.uniqueResult();
				if(null!=currentUpdateInfoPO){
					if(!currentUpdateInfoPO.getVersion().equalsIgnoreCase(clientVersion)){
						result=currentUpdateInfoPO;
					}
				}
			}
		}
		return result;
	}

	private String decidePlatform(String clientVersion) {
		String result=null;
		if(StringUtils.isNotBlank(clientVersion)){
			int positon=StringUtils.indexOf(clientVersion, this.splitMarkInClientVersion);
			if(positon>0){
				result=StringUtils.left(clientVersion, positon).toLowerCase();
			}
		}
		return result;
	}

	public String getSplitMarkInClientVersion() {
		return splitMarkInClientVersion;
	}

	public void setSplitMarkInClientVersion(String splitMarkInClientVersion) {
		this.splitMarkInClientVersion = splitMarkInClientVersion;
	}

}
