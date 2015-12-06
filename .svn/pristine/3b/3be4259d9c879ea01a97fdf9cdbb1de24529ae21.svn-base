package com.unison.lottery.pm.voucher;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.entity.VoucherPMPO;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherPMService;
import com.xhcms.lottery.commons.persist.service.VoucherService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

/**
 *  生成优惠卷任务
 * @author Wang Lei
 */
public class CreateVoucherJob extends Job{
	
	protected  Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	AccountService accountService;
	@Autowired
	VoucherService voucherService;
	@Autowired
	VoucherUserService voucherUserService;
	@Autowired
	VoucherPMService voucherPMService;
	
	private Long voucherPMId = 1L;
	
	@Override
	public void execute() throws Exception {
		VoucherPMPO voupm = voucherPMService.get(voucherPMId);
		checkPMValidTime(voupm);
		List<Long> ids = accountService.listAllId();
		if(ids==null){
			log.error("生成优惠卷失败！用户帐户id列表为空！");
			return;
		}
		Set<String> vids =new HashSet<String>();
		vids.add("CZS_50000_5058");
		vids.add("CZS_10000_1000");
		vids.add("CZS_5000_500");
		vids.add("CZS_1000_100");
		vids.add("CZS_100_8");
		Map<String, VoucherPO> voucherMaps =voucherService.listByIds(vids);
		log.info("开始处理:{}活动。",voupm.getName());
		int count=0;
		for(Long acId:ids){
			try {
				Account ac = accountService.getAccount(acId);
				if(ac != null){
					count += voucherUserService.createRechargeVoucher(voupm, ac, voucherMaps);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.info("{}活动插入活动子表完毕，共成功插入{}条。",voupm.getName(),count);
		log.info("结束处理:{}活动。",voupm.getName());
	}
	
	private void checkPMValidTime(VoucherPMPO voupm){
		if(voupm==null){
			throw new RuntimeException("处理优惠劵活动失败！未找到id="+voucherPMId+"的活动！");
		}
		Date now = new Date();
		if(voupm.getPmBeginTime().after(now)){
			String msg = "优惠劵活动"+voupm.getName()+"！未到开始时间！开始时间="+voupm.getPmBeginTime();
			throw new RuntimeException(msg);
		}
		if(now.after(voupm.getPmEndTime())){
			String msg = "优惠劵活动"+voupm.getName()+"！已结束！结束时间="+voupm.getPmEndTime();
			throw new RuntimeException(msg);
		}
	}
}
