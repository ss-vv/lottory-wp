package com.xhcms.lottery.dc.persist.persister;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.SsqInfo;
import com.xhcms.lottery.commons.persist.dao.SsqIssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.SsqInfoPO;
import com.xhcms.lottery.dc.core.Persister;

public class SsqIssueInfoImpl implements Persister<SsqInfo>{
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SsqIssueInfoDao ssqIssueInfoDao;
	
	@Override
	@Transactional
	public void persist(List<SsqInfo> ssqInfos) {
		logger.info("开始执行ssq info 数据入库任务...");
		for (SsqInfo ssqInfo : ssqInfos) {
			String in = ssqInfo.getIssueNumber();
			if(StringUtils.isNotBlank(in)){
				SsqInfoPO s = ssqIssueInfoDao.getSsqInfoPOByIssueNum(in);
				if(null == s){
					try {
						s = new SsqInfoPO();
						BeanUtils.copyProperties(s, ssqInfo);
						ssqIssueInfoDao.save(s);
						logger.info("保存一条新双色球信息：issueNum={}",s.getIssueNumber());
					} catch (IllegalAccessException e) {
						logger.info("双色球 issueNum={},信息保存失败，复制属性出错！",s.getIssueNumber(),e);
					} catch (InvocationTargetException e) {
						logger.info("双色球 issueNum={},信息保存失败，复制属性出错！",s.getIssueNumber(),e);
					}
				} else {
					logger.info("一条已存在的双色球信息：issueNum={}",s.getIssueNumber());
				}
			}
		}
	}

}
