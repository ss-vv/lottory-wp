package com.davcai.lottery.platform.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.xhcms.lottery.commons.persist.dao.LotteryPlatformPriorityDao;
import com.xhcms.lottery.commons.persist.entity.LotteryPlatformPriorityPO;
import com.xhcms.lottery.lang.LotteryId;

@Component
public class LotteryPlatformPriorityServiceImpl implements
		ILotteryPlatformPriorityService {
	@Autowired
	private LotteryPlatformPriorityDao lotteryPlatformPriorityDao;

	@Override
	@Transactional(readOnly=true)
	public List<LotteryPlatformPriority> loadAll(
			LotteryInterfaceName lotteryInterfaceName, LotteryId lotteryId) {
		if(null==lotteryInterfaceName||lotteryId==null){
			return null;
		}
		List<LotteryPlatformPriority> result=null;
		List<LotteryPlatformPriorityPO> poList=lotteryPlatformPriorityDao.findByLotteryInterfaceNameAndLotteryId(lotteryInterfaceName.toString(),lotteryId.toString());
		if(null!=poList&&!poList.isEmpty()){
			result=new ArrayList<LotteryPlatformPriority>();
			LotteryPlatformPriority lotteryPlatformPriority=null;
			for(LotteryPlatformPriorityPO po:poList){
				lotteryPlatformPriority=new LotteryPlatformPriority();
				try {
					BeanUtils.copyProperties(lotteryPlatformPriority, po);
					result.add(lotteryPlatformPriority);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public LotteryPlatformPriority findOneByInterfaceName(
			LotteryInterfaceName lotteryInterfaceName, String lotteryId) {
		if(null==lotteryInterfaceName){
			return null;
		}
		LotteryPlatformPriorityPO po=lotteryPlatformPriorityDao.findOneByInterfaceName(lotteryInterfaceName.toString(), lotteryId);
		LotteryPlatformPriority lotteryPlatformPriority=null;
		if(null!=po){
			lotteryPlatformPriority = new LotteryPlatformPriority();
			try {
				BeanUtils.copyProperties(lotteryPlatformPriority, po);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		return lotteryPlatformPriority;
	}

}
