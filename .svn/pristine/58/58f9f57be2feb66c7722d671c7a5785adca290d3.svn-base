package com.unison.lottery.api.protocol.status;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.protocol.status.service.IStatusService;
import com.xhcms.lottery.commons.persist.entity.ReturnStatusPO;


@Transactional
public class StatusRepository implements IStatusRepository {
	
	private Map<String,ReturnStatus> mapForSystemKey;

	@Autowired
	private IStatusService statusService;
	
	
	public void init() throws IllegalAccessException, InvocationTargetException{
		List<ReturnStatusPO> returnStatuses= statusService.loadAllStatus();
		if(null!=returnStatuses&&!returnStatuses.isEmpty()){
			initMapForSystemKey(returnStatuses);
		}
	}

	private void initMapForSystemKey(List<ReturnStatusPO> returnStatuses) throws IllegalAccessException, InvocationTargetException {
		if(null!=this.mapForSystemKey&&!this.mapForSystemKey.isEmpty()){
			this.mapForSystemKey.clear();
		}
		if(null==this.mapForSystemKey){
			this.mapForSystemKey=new HashMap<String,ReturnStatus>();
			
		}
		for(ReturnStatusPO returnStatusPO:returnStatuses){
			if(StringUtils.isNotEmpty(returnStatusPO.getSystemKey())){
				String key=returnStatusPO.getSystemKey();
				this.mapForSystemKey.put(key, createFromPO(returnStatusPO));
			}
		}
		
	}

	private ReturnStatus createFromPO(ReturnStatusPO returnStatusPO) throws IllegalAccessException, InvocationTargetException {
		ReturnStatus result=new ReturnStatus();
		BeanUtils.copyProperties(result, returnStatusPO);
		return result;
	}

	public ReturnStatus getSystemStatusBySystemKey(String systemKey) {
		ReturnStatus result=null;
		if(null!=this.mapForSystemKey&&!this.mapForSystemKey.isEmpty()&&StringUtils.isNotEmpty(systemKey)){
			result=this.mapForSystemKey.get(systemKey);
		}
		return result;
	}
	
}
