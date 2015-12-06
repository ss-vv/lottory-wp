package com.unison.lottery.api.activity.bo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.ActivityNotifyResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.persist.dao.ActivityNotifyDao;
import com.xhcms.lottery.commons.persist.entity.ActivityNotifyPO;

public class ActivityNotifyBOImpl implements ActivityNotifyBO {
	
	@Autowired
	private IStatusRepository statusRepository;

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private ActivityNotifyDao activityNotifyDao;

	@Override
	@Transactional
	public ActivityNotifyResponse listActivityNotify(
			String firstResult) {
		ActivityNotifyResponse response=new ActivityNotifyResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.ActivityNotify.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.ActivityNotify.FAIL);
		response.setReturnStatus(failStatus);
		try{
			if(StringUtils.isNotBlank(firstResult)){
				List<ActivityNotifyPO> notifies=activityNotifyDao.listWithPage(firstResult);
				response.setNotifies(notifies);
				response.setReturnStatus(succStatus);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			response.setReturnStatus(failStatus);
		}
		return response;
	}

}
