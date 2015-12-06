package com.unison.lottery.pm.count;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.CountService;
import com.xhcms.commons.job.Job;

/**
 * 
 * @author yonglizhu
 *
 */
public class UpdateDataTask extends Job {
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CountService countService;
	
	@Override
	protected void execute() throws Exception {
		log.info("更新跟单总金额数据开始 ");
		String from = "2012-02-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = null;
		try {
			fromDate = sdf.parse(from);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (int i=0; i<335; i++) {
			System.out.println(sdf.format(fromDate));
			log.info("数据日期={}", sdf.format(fromDate));
			countService.updateAmount(sdf.format(fromDate));
			fromDate = DateUtils.addDays(fromDate, 1);
		}
		log.info("更新跟单总金额数据结束");
	}

}
