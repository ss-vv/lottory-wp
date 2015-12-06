package com.unison.lottery.api.checkupdate.bo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.CheckUpdateResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.persist.dao.ClientUpdateInfoDao;
import com.xhcms.lottery.commons.persist.entity.ClientUpdateInfoPO;

public class CheckUpdateBOImpl implements CheckUpdateBO {
	
	@Autowired
	private IStatusRepository statusRepository;

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private ClientUpdateInfoDao clientUpdateInfoDao;

	@Override
	@Transactional
	public CheckUpdateResponse checkUpdate(User user) {
		CheckUpdateResponse response=new CheckUpdateResponse();
		ReturnStatus needUpdateStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.CheckUpdate.NEED_UPDATE);
		ReturnStatus notNeedUpdateStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.CheckUpdate.NOT_NEED_UPDATE);
		response.setReturnStatus(notNeedUpdateStatus);
		try{
			if(null!=user
					&&StringUtils.isNotBlank(user.getClientVersion())){
				ClientUpdateInfoPO clientUpdateInfoPO=clientUpdateInfoDao.findByClientVersion(user.getClientVersion(),user.getChannel());
				if(null!=clientUpdateInfoPO){
					response.setReturnStatus(needUpdateStatus);
					response.setUpdateDescription(clientUpdateInfoPO.getUpdateDescription());
					response.setUpdateType(clientUpdateInfoPO.getUpdateType());
					response.setUpdateUrl(clientUpdateInfoPO.getUpdateUrl());
					response.setVersion(clientUpdateInfoPO.getVersion());
					
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			response.setReturnStatus(notNeedUpdateStatus);
		}
		return response;
	}

}
