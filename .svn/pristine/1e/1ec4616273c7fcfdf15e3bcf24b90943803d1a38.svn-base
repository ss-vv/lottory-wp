package com.xhcms.lottery.alarm.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.alarm.data.ShiTiDianTicketStatus;
import com.xhcms.lottery.alarm.service.SendEmailService;
import com.xhcms.lottery.alarm.service.SendSmsService;
import com.xhcms.lottery.alarm.service.TicketMonitorService;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class TicketMonitorServiceImpl implements TicketMonitorService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BetSchemeDao betSchemeDao;

	@Autowired
	private SendEmailService sendEmailService;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	private String emailReceiver;

	private String phoneReceiver;
	
	private long thresholdValue;
	
	@Autowired
	private TicketDao ticketDao;
	
	private List<String> availableShiTiDian;

	private String datePattern="yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private SystemConf systemConf;
	
	@Override
	@Transactional
	public void monitorTicketStatus(long thresholdValue,long intervalValue) {
		this.thresholdValue = thresholdValue;
		List<BetSchemePO> list = getBetSchemePOsWithThresholdValue(thresholdValue, intervalValue);
		if(list.size() > 0){
			logger.info("发现异常票！");
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("发现出票异常,schemaId=[");
			for (BetSchemePO betSchemePO : list) {
				logger.info("shcemaId={}",betSchemePO.getId());
				sBuilder.append(betSchemePO.getId() + ",");
			}
			sBuilder.delete(sBuilder.length() -1, sBuilder.length());
			sBuilder.append("], 总计：" + list.size() + "条!");
			String sms = sBuilder.toString();
			
			//发sms
			Set<String> phoneNums = getReceptPhonesNumss();
			if(null != phoneNums){
				sendSmsService.send(sms,phoneNums);
			} else {
				logger.error("出票异常信息接收者 手机号 配置错误!");
			}
			
			//发email
			Set<String> receptEmails = getReceptEmails();
			if(null != receptEmails&&canSend()){
				sendEmailService.send(sms,receptEmails ,"出票异常");
			} else {
				logger.error("出票异常信息接收者Email配置错误!");
			}
		} else {
			logger.info("无异常票！");
		}
	}
	
	private Set<String> getReceptPhonesNumss(){
		if(StringUtils.isBlank(phoneReceiver)){
			return null;
		} else {
			Set<String> set = new HashSet<String>();
			String [] a = phoneReceiver.split(",");
			for (String s : a) {
				set.add(s);
			}
			return set;
		}
	}
	private Set<String> getReceptEmails(){
		if(StringUtils.isBlank(emailReceiver)){
			return null;
		} else {
			Set<String> set = new HashSet<String>();
			String [] a = emailReceiver.split(",");
			for (String s : a) {
				set.add(s);
			}
			return set;
		}
	}
	
	private List<BetSchemePO> getBetSchemePOsWithThresholdValue(long thresholdValue,long intervalValue){
		long now = new Date().getTime();
		long from = now - thresholdValue * 1000 - intervalValue * 1000;
		long to = now - thresholdValue * 1000;
		List<BetSchemePO> list =  betSchemeDao.findByStatusAndNotSaleStatus(EntityStatus.TICKET_ALLOW_BUY,
					EntityStatus.SCHEME_ON_SALE, new Date(from),new Date(to));
		list.addAll(betSchemeDao.findByStatusAndNotSaleStatus(EntityStatus.TICKET_REQUIRED,
					EntityStatus.SCHEME_ON_SALE, new Date(from),new Date(to)));
		return filterBetSchemePO(list); 
	}

	private List<BetSchemePO> filterBetSchemePO(List<BetSchemePO> list){
		if(list.size() < 1){
			return list;
		}
		Map<Long,BetSchemePO> map = new HashMap<Long, BetSchemePO>();
		for (BetSchemePO betSchemePO : list) {
			map.put(betSchemePO.getId(), betSchemePO);
		}
		
		List<BetSchemePO> needAlarmList = new ArrayList<BetSchemePO>();
		for (Long id : map.keySet()) {
			BetSchemePO bet = map.get(id);
			
			Date createTime = bet.getCreatedTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(createTime);
			
			int weekDay = cal.get(Calendar.DAY_OF_WEEK);
			
			if(LotteryId.JCLQ.name().equals(bet.getLotteryId())){ // 竞彩篮球处理
				if(weekDay == 4 || weekDay == 5){//星期三、四
					filterIn730Clock(needAlarmList, bet);
				} else { // 其他星期
					filterIn9Clock(needAlarmList, bet);
				}
			} else if(LotteryId.JCZQ.name().equals(bet.getLotteryId())){ // 竞彩足球处理
				filterIn9Clock(needAlarmList, bet);
			} else {
				needAlarmList.add(bet);
			}
		}
		return needAlarmList;
	}
	
	/**
	 * 7:30 后出票处理
	 * @param needAlarmList
	 * @param bet
	 */
	private void filterIn730Clock(List<BetSchemePO> needAlarmList,BetSchemePO bet){
		Date createTime = bet.getCreatedTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(createTime);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		
		long nowTime = new Date().getTime();
		if(hour < 7 || (hour == 7 && minute <= 30 )){ // 7:30 以前发的单
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 7);
			c.set(Calendar.MINUTE, 30);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);  // 时间当天 07:30:00
			if(nowTime - c.getTime().getTime() >= thresholdValue * 1000){//当前时间 - 07:30:00  相差时间大于阀值，说明出票异常
				needAlarmList.add(bet);
			}
		} else {
			needAlarmList.add(bet);
		}
	}
	
	/**
	 * 9点后出票处理
	 * @param needAlarmList
	 * @param bet
	 */
	private void filterIn9Clock(List<BetSchemePO> needAlarmList,BetSchemePO bet){
		Date createTime = bet.getCreatedTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(createTime);
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		long nowTime = new Date().getTime();
		
		if(hour < 9){ // 9点以前发的单
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 9);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);  // 时间当天 09:00:00
			if(nowTime - c.getTime().getTime() >= thresholdValue * 1000){//当前时间 - 09:00:00  相差时间大于阀值，说明出票异常
				needAlarmList.add(bet);
			}
		} else {
			needAlarmList.add(bet);
		}
	}
	
	public String getEmailReceiver() {
		return emailReceiver;
	}
	
	@Value("${email.receiver}")
	public void setEmailReceiver(String emailReceiver) {
		this.emailReceiver = emailReceiver;
	}

	public String getPhoneReceiver() {
		return phoneReceiver;
	}
	
	@Value("${phone.receiver}")
	public void setPhoneReceiver(String phoneReceiver) {
		this.phoneReceiver = phoneReceiver;
	}

	@Override
	@Transactional
	public void monitorShiTiDianTicketStatus() {
		ShiTiDianTicketStatus shiTiDianTicketStatus = computeStatus();
		Date minEntrustDeadLine=min(shiTiDianTicketStatus.getFbMinEntrustDeadLine(),shiTiDianTicketStatus.getBbMinEntrustDeadLine(),shiTiDianTicketStatus.getCtfbMinEntrustDeadLine());
		String mailContent=makeMailContent(shiTiDianTicketStatus.getTotal(),shiTiDianTicketStatus.getTotalMoney(),minEntrustDeadLine);
		//发email
		Set<String> receptEmails = getReceptEmails();
		if(null != receptEmails && StringUtils.isNotBlank(mailContent)&&canSend()){
			sendEmailService.send(mailContent,receptEmails ,"有可出的实体票，请登录后台处理");
		} else {
			logger.error("实体出票监控信息接收者Email配置错误!");
		}
	}

	private boolean canSend() {//判断是否可以发送邮件
		return systemConf.canSendMonitorEmail();
	}

	private ShiTiDianTicketStatus computeStatus() {
		
		Object[] fbResult=ticketDao.findShiTiDianFBMinEntrustDeadLine(availableShiTiDian);//票中竞彩足球比赛最早截止期
		Date fbMinEntrustDeadLine=null;
		BigDecimal totalMoneyFB=null;
		BigInteger totalFB=null;
		if(null!=fbResult&&fbResult.length==3){
			fbMinEntrustDeadLine=(Date) fbResult[0];
			totalMoneyFB=(BigDecimal) fbResult[1];
			if(null==totalMoneyFB){
				totalMoneyFB=BigDecimal.ZERO;
			}
			totalFB=(BigInteger) fbResult[2];
		}
		Object[] bbResult=ticketDao.findShiTiDianBBMinEntrustDeadLine(availableShiTiDian);//票中竞彩篮球比赛最早截止期
		Date bbMinEntrustDeadLine=null;
		BigDecimal totalMoneyBB=null;
		BigInteger totalBB=null;
		if(null!=bbResult&&bbResult.length==3){
			bbMinEntrustDeadLine=(Date) bbResult[0];
			totalMoneyBB=(BigDecimal) bbResult[1];
			if(null==totalMoneyBB){
				totalMoneyBB=BigDecimal.ZERO;
			}
			totalBB=(BigInteger) bbResult[2];
		}
		
		Object[] ctzcResult=ticketDao.findShiTiDianCtfbMinEntrustDeadLine(availableShiTiDian);//票中传统足球比赛最早截止期
		Date ctfbMinEntrustDeadLine=null;
		BigDecimal totalMoneyCtfb=null;
		BigInteger totalCTZC=null;
		if(null!=ctzcResult&&ctzcResult.length==3){
			ctfbMinEntrustDeadLine=(Date) ctzcResult[0];
			totalMoneyCtfb=(BigDecimal) ctzcResult[1];
			if(null==totalMoneyCtfb){
				totalMoneyCtfb=BigDecimal.ZERO;
			}
			totalCTZC=(BigInteger)ctzcResult[2];
		}
		ShiTiDianTicketStatus shiTiDianTicketStatus=null;
		if(null!=totalMoneyFB&&null!=totalMoneyBB&&null!=totalMoneyCtfb
				&&null!=totalFB&&null!=totalBB&&null!=totalCTZC){
			shiTiDianTicketStatus=new ShiTiDianTicketStatus();
			shiTiDianTicketStatus.setBbMinEntrustDeadLine(bbMinEntrustDeadLine);
			shiTiDianTicketStatus.setCtfbMinEntrustDeadLine(ctfbMinEntrustDeadLine);
			shiTiDianTicketStatus.setFbMinEntrustDeadLine(fbMinEntrustDeadLine);
			shiTiDianTicketStatus.setTotal(totalFB.add(totalBB).add(totalCTZC));
			shiTiDianTicketStatus.setTotalMoney(totalMoneyFB.add(totalMoneyBB).add(totalMoneyCtfb));
		}
		
		return shiTiDianTicketStatus;
	}

	private String makeMailContent(BigInteger total, BigDecimal totalMoney,
			Date minEntrustDeadLine) {
		if(null!=total&&null!=totalMoney&&null!=minEntrustDeadLine){
			SimpleDateFormat sdf=new SimpleDateFormat(datePattern);
			String dateStr=sdf.format(minEntrustDeadLine);
			return String.format("可出票总数:%d\r\n可出票总金额:%d元\r\n最早截止时间:%s\r\n", total,totalMoney.toBigIntegerExact(),dateStr);
		}
		return null;
	}

	private Date min(Date fbMinEntrustDeadLine, Date bbMinEntrustDeadLine,
			Date ctfbMinEntrustDeadLine) {
		Date min=null;
		min=min(fbMinEntrustDeadLine,bbMinEntrustDeadLine);
		min=min(min,ctfbMinEntrustDeadLine);
		return min;
	}

	private Date min(Date fbMinEntrustDeadLine, Date bbMinEntrustDeadLine) {
		if(null==fbMinEntrustDeadLine){
			return bbMinEntrustDeadLine;
		}
		if(null==bbMinEntrustDeadLine){
			return fbMinEntrustDeadLine;
		}
		return fbMinEntrustDeadLine.before(bbMinEntrustDeadLine)?fbMinEntrustDeadLine:bbMinEntrustDeadLine;
	}

	public List<String> getAvailableShiTiDian() {
		return availableShiTiDian;
	}

	public void setAvailableShiTiDian(List<String> availableShiTiDian) {
		this.availableShiTiDian = availableShiTiDian;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

}
