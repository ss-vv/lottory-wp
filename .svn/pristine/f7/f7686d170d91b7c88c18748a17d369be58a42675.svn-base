package com.xhcms.lottery.admin.persist.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.platform.util.model.NameAndValue;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.xhcms.lottery.admin.persist.service.LotteryPlatformPriorityService;
import com.xhcms.lottery.commons.persist.dao.LotteryPlatformPriorityDao;
import com.xhcms.lottery.commons.persist.entity.LotteryPlatformPriorityPO;

public class LotteryPlatformPriorityServiceImpl implements
		LotteryPlatformPriorityService {
	@Autowired
	private LotteryPlatformPriorityDao lotteryPlatformPriorityDao;
	private List<String> shiTiDianLotteryPlatformIdList;//实体店id列表
	
	public List<String> getShiTiDianLotteryPlatformIdList() {
		return shiTiDianLotteryPlatformIdList;
	}

	public void setShiTiDianLotteryPlatformIdList(
			List<String> shiTiDianLotteryPlatformIdList) {
		this.shiTiDianLotteryPlatformIdList = shiTiDianLotteryPlatformIdList;
	}

	public List<String> getShiTiDianLotteryIdList() {
		return shiTiDianLotteryIdList;
	}

	public void setShiTiDianLotteryIdList(List<String> shiTiDianLotteryIdList) {
		this.shiTiDianLotteryIdList = shiTiDianLotteryIdList;
	}

	private List<String> shiTiDianLotteryIdList;//由实体店处理的彩种id列表
	
	@Override
	@Transactional(readOnly=true)
	public List<LotteryPlatformPriority> listShiTiDianPriorityList(String lotteryId, String lotteryPlatformId) {
		List<LotteryPlatformPriorityPO> poList=lotteryPlatformPriorityDao.listShiTiDianPriorityList(shiTiDianLotteryPlatformIdList,shiTiDianLotteryIdList,lotteryId,lotteryPlatformId);
		if(null!=poList&&!poList.isEmpty()){
			List<LotteryPlatformPriority> result=new ArrayList<LotteryPlatformPriority>();
			for(LotteryPlatformPriorityPO po:poList){
				
				LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
				try {
					BeanUtils.copyProperties(lotteryPlatformPriority, po);
					result.add(lotteryPlatformPriority);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
			}
			return result;
		}
		return null;
	}

	public LotteryPlatformPriorityDao getLotteryPlatformPriorityDao() {
		return lotteryPlatformPriorityDao;
	}

	public void setLotteryPlatformPriorityDao(LotteryPlatformPriorityDao lotteryPlatformPriorityDao) {
		this.lotteryPlatformPriorityDao = lotteryPlatformPriorityDao;
	}

	@Override
	@Transactional
	public void updatePriority(Long id, int priority, int priorityOfBigTicket) {
		
			lotteryPlatformPriorityDao.updatePriorityAndPriorityOfBigTicket(id,priority,priorityOfBigTicket);
		
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<NameAndValue> listShiTiDianAliasNameList() {
		List<NameAndValue> result=new ArrayList<NameAndValue>();
		NameAndValue all=new NameAndValue();
		all.setName("所有实体店");
		all.setValue("all");
		result.add(all);
		List<Object[]> aliasNameList=lotteryPlatformPriorityDao.listShiTiDianAliasNameList(shiTiDianLotteryPlatformIdList);
		if(null!=aliasNameList&&!aliasNameList.isEmpty()){
			for(Object[] aliasName:aliasNameList){
				NameAndValue lotteryPlatformAliasName=new NameAndValue();
				lotteryPlatformAliasName.setName((String)aliasName[0]);
				lotteryPlatformAliasName.setValue((String)aliasName[1]);
				result.add(lotteryPlatformAliasName);
			}
		}
		return result;
	}

	@Override
	public List<NameAndValue> listShiTiDianLotteryNameList() {
		List<NameAndValue> result=new ArrayList<NameAndValue>();
		NameAndValue all=new NameAndValue();
		all.setName("所有彩种");
		all.setValue("all");
		result.add(all);
		if(null!=this.shiTiDianLotteryIdList&&!this.shiTiDianLotteryIdList.isEmpty()){
			for(String lotteryId:shiTiDianLotteryIdList){
				NameAndValue nameAndValue=new NameAndValue();
				nameAndValue.setValue(lotteryId);
				String name="";
				switch(lotteryId){
					case "JCZQ":{name="竞彩足球";break;}
					case "JCLQ":{name="竞彩篮球";break;}
					case "CTZC":{name="传统足彩";break;}
					case "JX11":{name="江西十一选五";break;}
					case "CQSS":{name="重庆时时彩";break;}
					case "SSQ":{name="双色球";break;}
					case "BJDC":{name="北京单场";break;}
					case "BDSF":{name="北单胜负";break;}
					default:{name="未知";break;}
				}
				nameAndValue.setName(name);
				result.add(nameAndValue);
			}
		}
		return result;
	}

	@Override
	@Transactional
	public boolean batchUpdatePriority(List<Long> selectedIds,
			List<Integer> selectedPrioritys,
			List<Integer> selectedPriorityOfBigTickets) {
		boolean result=false;
		if(null!=selectedIds&&null!=selectedPrioritys&&null!=selectedPriorityOfBigTickets
				&&selectedIds.size()==selectedPrioritys.size()
				&&selectedPrioritys.size()==selectedPriorityOfBigTickets.size()
				&&selectedIds.size()>0){
			
			for(int i=0;i<selectedIds.size();i++){
				lotteryPlatformPriorityDao.updatePriorityAndPriorityOfBigTicket(selectedIds.get(i),selectedPrioritys.get(i),selectedPriorityOfBigTickets.get(i));
			}
			result=true;
		}
		return result;
	}

}
