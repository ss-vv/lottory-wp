package com.davcai.lottery.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.dao.RedEnvalopeDao;
import com.davcai.lottery.service.RedEnvalopeService;
import com.unison.lottery.api.redEnvalope.model.EnvalopeGrabHistory;
import com.unison.lottery.api.redEnvalope.model.RedEnvalope;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopeGrabRecordPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopePO;
import com.xhcms.lottery.utils.DateUtils;

public class RedEnvalopeServiceImpl implements RedEnvalopeService{
	@Autowired
	private RedEnvalopeDao redEnvalopeDao;
	@Override
	public long addRedEnvalope(Long redEnvalopeAmount, Integer envalopeNum, Long uid, long[] redArray) {
		RedEnvalopePO redEnvalopePO = new RedEnvalopePO();
		redEnvalopePO.setCreateTime(new Date());
		redEnvalopePO.setEnvalopeNum(envalopeNum);
		redEnvalopePO.setRedEnvalopeAmount(redEnvalopeAmount);
		redEnvalopePO.setLtUserId(uid);
		redEnvalopePO.setSmallEnvalope(ArrayUtils.toString(redArray));
		redEnvalopePO.setStatus("0");
		
		long envalopeId = redEnvalopeDao.saveEnvalope(redEnvalopePO);
		
		return envalopeId;
	}
	@Override
	public RedEnvalope findRedEnvalopeById(Long envalopeId) {
		RedEnvalopePO redEnvalopePO = redEnvalopeDao.queryEnvalopeById(envalopeId);
		if(redEnvalopePO!=null){
			RedEnvalope redEnvalope = new RedEnvalope();
			try {
				BeanUtils.copyProperties(redEnvalope, redEnvalopePO);
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return redEnvalope;
		}
		return null;
	}
	public RedEnvalopeDao getRedEnvalopeDao() {
		return redEnvalopeDao;
	}
	public void setRedEnvalopeDao(RedEnvalopeDao redEnvalopeDao) {
		this.redEnvalopeDao = redEnvalopeDao;
	}
	@Override
	public void deleteRedEnvalopeById(long envalopeId) {
		redEnvalopeDao.deleteRedEnvalopeById(envalopeId);
	}
	@Override
	public void updateRedEnvalope(RedEnvalope redEnvalope,
			Long smallRedEnvalopeAmount, Long userId) {
		RedEnvalopePO redEnvalopePO = new RedEnvalopePO();
		try {
			BeanUtils.copyProperties(redEnvalopePO,redEnvalope);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		redEnvalopeDao.update(redEnvalopePO);
		logGrabRecord(redEnvalope.getEnvalopeId(), userId, smallRedEnvalopeAmount, new Date());
	}
	
	private void logGrabRecord(Long envalopeId, Long ltUserId, Long smallRedEnvalopeAmount,
			Date date) {
		RedEnvalopeGrabRecordPO record = new RedEnvalopeGrabRecordPO();
		record.setLtUserId(ltUserId);
		record.setEnvalopeId(envalopeId);
		record.setEnvalopeAmount(smallRedEnvalopeAmount);
		record.setCreateTime(date);
		
		redEnvalopeDao.save(record);
	}
	@Override
	public Map<String, Object> findRedEnvalopeRecordById(Long envalopeId, Long userId, String grabAmount) {
		
		List<RedEnvalopeGrabRecordPO> recordPOs = redEnvalopeDao.queryRedEnvalopeGrabHistory(envalopeId);
		List<EnvalopeGrabHistory> envalopeGrabHistories = null;
		Map<String, Object> redData = new HashMap<String, Object>();
		if(recordPOs!=null){
			envalopeGrabHistories = new ArrayList<EnvalopeGrabHistory>();
			Iterator<RedEnvalopeGrabRecordPO> iterator = recordPOs.iterator();
			while(iterator.hasNext()){
				RedEnvalopeGrabRecordPO recordPO = iterator.next();
				if(recordPO.getLtUserId().equals(userId)){
					grabAmount = recordPO.getEnvalopeAmount().toString();
					redData.put("2", grabAmount);
				}
				EnvalopeGrabHistory envalopeGrabHistory = new EnvalopeGrabHistory(
						recordPO.getId(), recordPO.getImageUrl(), recordPO.getNickName(), 
						DateFormatUtils.format(recordPO.getCreateTime(),"HH:mm:ss"), recordPO.getEnvalopeAmount());
				envalopeGrabHistories.add(envalopeGrabHistory);
			}
			redData.put("1", envalopeGrabHistories);
		}
		return redData;
	}
	@Override
	public List<RedEnvalopePO> findAllInvalidEvalope() {
		
		return redEnvalopeDao.queryAllInvalidEnvalope();
	}
	@Override
	public void updateRedEnvalope(RedEnvalopePO redEnvalopePO) {
		redEnvalopeDao.update(redEnvalopePO);
	}
	
	

}
