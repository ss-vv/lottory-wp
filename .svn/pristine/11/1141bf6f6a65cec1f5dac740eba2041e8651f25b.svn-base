package com.unison.lottery.pm.voucher;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.PMGrantVoucherRecordService;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.persist.entity.VoucherPMPO;
import com.xhcms.lottery.commons.persist.service.VoucherPMService;
import com.xhcms.lottery.commons.persist.service.VoucherService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

/**
 * 2013.6月彩金卷天天送活动task
 * @author Wang Lei
 *
 */
public class JuneVoucherTask  extends Job{
	protected  Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	VoucherService voucherService;
	@Autowired
	VoucherUserService voucherUserService;
	@Autowired
	VoucherPMService voucherPMService;
	@Autowired
	PMGrantVoucherRecordService pMGrantVoucherRecordService;

	/**
	 * 活动id
	 */
	private Long voucherPMId;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void execute() throws Exception {
		VoucherPMPO voupm = voucherPMService.get(voucherPMId);
		log.info("开始处理:{}活动。",voupm.getName());
		checkPMValidityDate(voupm);
		
		// 日期调整为前一天
		Date day = new Date();
		day = reduceOneDate(day);
		
		// 计算前一天的符合条件的数据
		List<Object[]> userTotalBetAmountList = pMGrantVoucherRecordService.findUserJCZQBetByDate(day);
		log.info("{}日数据计算完毕,还有{}位用户需要派发优惠劵。",sdf.format(day),userTotalBetAmountList.size());
		int count=0;
		for(Object[] o : userTotalBetAmountList){
			Long userId = ((BigInteger) o[0]).longValue();
			Long amount = ((BigDecimal) o[1]).longValue();
			Long grantTypeId = voupm.getGrantType().getId();
			String voucherId =  matchingVoucher(amount);
			if(StringUtils.isNotBlank(voucherId)){
				try {
					voucherUserService.createVoucerAndRecord( userId, voucherId, grantTypeId, day);
					count++;
				} catch (Exception e) {
					log.error("创建优惠劵失败！用户：{},日期：{}，活动id：{}",new Object[]{userId , sdf.format(day), voupm.getId() });
					log.error(e.getMessage());
				}
			}
		}
		log.info("{}活动插入活动子表完毕，共成功插入{}条。",voupm.getName(),count);
		log.info("结束处理:{}活动。",voupm.getName());
	}
	
	/**
	 * 匹配优惠劵
	 * @param amount
	 * @return
	 */
	private String matchingVoucher(Long amount){
		if(amount >= 200 && amount <= 499){
			return "SCJ_10";
		}
		if(amount >= 500 && amount <= 999){
			return "SCJ_20";
		}
		if(amount >= 1000 && amount <= 1999){
			return "SCJ_50";
		}
		if(amount >= 2000 && amount <= 4999){
			return "SCJ_100";
		}
		if(amount >= 5000 && amount <= 9999){
			return "SCJ_200";
		}
		if(amount >= 10000){
			return "SCJ_500";
		}
		return null;
	}
	
	/**
	 * 减一天
	 * @param day
	 * @return
	 */
	private Date reduceOneDate(Date day){
		Calendar time=Calendar.getInstance();
		time.setTime(day);
		time.add(Calendar.DAY_OF_MONTH, - 1);
		return time.getTime();
	}
	
	/**
	 * 检查活动是否在有效期内
	 * @param voupm
	 */
	private void checkPMValidityDate(VoucherPMPO voupm){
		if(voupm==null){
			throw new RuntimeException("处理优惠劵活动失败！未找到id="+voucherPMId+"的活动！");
		}
		Date now = new Date();
		
		Calendar startTime=Calendar.getInstance();
		startTime.setTime(voupm.getPmBeginTime());
		startTime.add(Calendar.DAY_OF_MONTH, + 1);
		
		Calendar endTime=Calendar.getInstance();
		endTime.setTime(voupm.getPmEndTime());
		endTime.add(Calendar.DAY_OF_MONTH, + 1);
		
		if(startTime.getTime().after(now)){
			String msg = "优惠劵活动:"+voupm.getName()+"！未到开始时间！开始时间:"+ sdf.format(startTime.getTime());
			throw new RuntimeException(msg);
		}
		if(now.after(endTime.getTime())){
			String msg = "优惠劵活动:"+voupm.getName()+"！已结束！结束时间:"+ sdf.format(endTime.getTime()) ;
			throw new RuntimeException(msg);
		}
	}

	public Long getVoucherPMId() {
		return voucherPMId;
	}

	public void setVoucherPMId(Long voucherPMId) {
		this.voucherPMId = voucherPMId;
	}
}
