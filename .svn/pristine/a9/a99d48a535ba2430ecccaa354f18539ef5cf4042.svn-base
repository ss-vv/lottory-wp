package com.xhcms.lottery.commons.persist.service.impl;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.RegistCode;
import com.xhcms.lottery.commons.persist.dao.RegistrationCodeDao;
import com.xhcms.lottery.commons.persist.entity.RegistCodePO;
import com.xhcms.lottery.commons.persist.service.RegistrationCodeService;
import com.xhcms.lottery.lang.RegistCodeStatus;
import com.xhcms.lottery.utils.BeanUtilsTools;
import com.xhcms.lottery.utils.DateUtils;

public class RegistrationCodeServiceImpl implements RegistrationCodeService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RegistrationCodeDao registrationCodeDao;
	
	@Override
	@Transactional
	public RegistCodeStatus findAndUpdateRegistrationCodeStatus(String code) {
		if(StringUtils.isBlank(code)){
			return RegistCodeStatus.UNVALID; 
		}
		RegistCodePO regist = registrationCodeDao.getRegistrationCodeWithCode(code);
		return updateStatus(regist);
	}

	private RegistCodeStatus updateStatus(RegistCodePO regist) {
		if(regist!=null){
			if(new Date().before(regist.getEndTime())){//在有效时间内
				if(regist.getStatus()==RegistCodeStatus.VALID.getStatus() 
						&& regist.getUserId() < 1){//状态有效且未被使用
					return RegistCodeStatus.VALID;//返回有效
				}
				if(regist.getStatus()!=RegistCodeStatus.USED.getStatus()
						&& regist.getUserId() > 0){//被使用
					regist.setStatus(RegistCodeStatus.USED.getStatus());
					return RegistCodeStatus.USED;
				}
			} else {//不在有效时间内
				if(regist.getStatus()==RegistCodeStatus.VALID.getStatus()){//状态有效,需要更新
					if(regist.getUserId() < 1){//未使用,则超期
						regist.setStatus(RegistCodeStatus.OVERDUE.getStatus());
						return RegistCodeStatus.OVERDUE;//返回超期
					} else {
						regist.setStatus(RegistCodeStatus.USED.getStatus());
						return RegistCodeStatus.USED;
					}
				}
			}
		}
		return RegistCodeStatus.UNVALID;//返回无效
	}
	
	/**
	 * 更新邀请码状态
	 */
	@Override
	@Transactional
	public void updateRegistrationCodeStatus(String code, RegistCodeStatus status, long userId){
		RegistCodePO regist=registrationCodeDao.getRegistrationCodeWithCode(code);
		if(regist!=null){
			regist.setUsedTime(new Date());
			regist.setStatus(status.getStatus());
			regist.setUserId(userId);
		}
	}

	@Override
	@Transactional
	public List<RegistCode> listByPagger(Paging paging, int status, int vid) {
		 List<RegistCodePO> pos = registrationCodeDao.listByPagger(paging, status, vid);
		 List<RegistCode> registCodes = new ArrayList<>();
		 for (RegistCodePO registCodePO : pos) {
			 updateStatus(registCodePO);
			 RegistCode r = new RegistCode();
			 BeanUtilsTools.copyNotNullProperties(registCodePO, r, null);
			 registCodes.add(r);
		 }
		 return registCodes; 
	}

	@Override
	@Transactional
	public void genereateCodeAndSave(long genvid, int codeMaxSize, int expiryDay) {
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < codeMaxSize; i++) {
			RegistCodePO codePO = new RegistCodePO(genvid);
			codePO.setCreateTime(new Date());
			codePO.setStatus(RegistCodeStatus.VALID.getStatus());
			codePO.setEndTime(DateUtils.getLastDay(new Date(), -expiryDay));
			codePO.setCode(generateCode(map));
			codePO.setUserId(-1);
			registrationCodeDao.save(codePO);
		}
	}
	
	private String generateCode(Map<String, String> map){
		int i=0;
		while(i<10) {
			String s = md5(String.valueOf(new Date().getTime()+Math.random()*2.563 + Math.random()*125.12));
			String code = s.substring(0, 4) + s.substring(5, 9);
			RegistCodePO regist = registrationCodeDao.getRegistrationCodeWithCode(code);
			if(null != regist || null !=map.get(code)){
				i++;
				continue;
			} else {
				map.put(code, code);
				logger.info("生成邀请码：code="+code);
				return code;
			}
		}
		return "";//十次都失败了，返回空
	}
	
	public static String md5(String string) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
        'e', 'f' };
        try {
       	 byte[] bytes = string.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] updateBytes = messageDigest.digest();
            int len = updateBytes.length;
            char myChar[] = new char[len * 2];
            int k = 0;
            for (int i = 0; i < len; i++) {
                byte byte0 = updateBytes[i];
                myChar[k++] = hexDigits[byte0 >>> 4 & 0x0f];
                myChar[k++] = hexDigits[byte0 & 0x0f];
            }
            return new String(myChar);
        }catch (Exception e) {
			// TODO: handle exception
       	 return null;
		}
    }
}
