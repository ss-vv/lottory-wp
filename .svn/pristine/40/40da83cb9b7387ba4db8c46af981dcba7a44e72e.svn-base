package com.unison.lottery.weibo.service.impl;

import java.util.Calendar;
import java.util.Random;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.nosql.LotteryBetDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.service.BetMatchNumService;
import com.unison.lottery.weibo.util.BetIncrmentNum;
import com.unison.lottery.weibo.util.BetNumConfig;
@Service
public class BetMatchNumServiceImpl implements BetMatchNumService {

	private Logger log = LoggerFactory.getLogger(BetMatchNumServiceImpl.class);
	@Autowired
	private LotteryBetDao lotteryBetDao;

	@Transactional
	@Override
	public void AddJCZQBetNum() {
		String h_time = BetNumConfig.getValue("jczq_week_high_time");
		String stop_time = BetNumConfig.getValue("jczq_stop_time");
		String h_num = BetNumConfig.getValue("jczq_week_high_incre");
		String l_num = BetNumConfig.getValue("jczq_week_low_incre");
		String weekend_h_time = BetNumConfig.getValue("jczq_weekend_high_time");
		String weekend_h_num = BetNumConfig.getValue("jczq_weekend_high_incre");
		String weekend_l_num = BetNumConfig.getValue("jczq_weekend_low_incre");

		int num = BetIncrmentNum.getIncrementNum("jczq", h_time, h_num, l_num,
				stop_time, weekend_h_time, weekend_h_num, weekend_l_num);
		String jczqnum = lotteryBetDao.getString(Keys.getJCZQKey());
		if (StringUtil.isBlank(jczqnum)) {
			lotteryBetDao.set(Keys.getJCZQKey(), num + "");
		} else {
			lotteryBetDao.set(Keys.getJCZQKey(),
					(Integer.parseInt(jczqnum) + num) + "");
		}
		log.info("jczq num is {}", num);
	}

	@Transactional
	@Override
	public void AddJCLQBetNum() {
		String h_time = BetNumConfig.getValue("jclq_high_time");
		String stop_time = BetNumConfig.getValue("jclq_stop_time");
		String h_num = BetNumConfig.getValue("jclq_high_incre");
		String l_num = BetNumConfig.getValue("jclq_low_incre");
		int num = BetIncrmentNum.getIncrementNum("jclq", h_time, h_num, l_num,
				stop_time, null, null, null);
		String jczqnum = lotteryBetDao.getString(Keys.getJCLQKey());
		if (StringUtil.isBlank(jczqnum)) {
			lotteryBetDao.set(Keys.getJCLQKey(), num + "");
		} else {
			lotteryBetDao.set(Keys.getJCLQKey(),
					(Integer.parseInt(jczqnum) + num) + "");
		}
		log.info("JCLQ num is {}", num);
		log.info("JCLQ total is {}", jczqnum);

	}

	@Transactional
	@Override
	public void AddBJDCBetNum() {
		String h_time = BetNumConfig.getValue("bjdc_week_high_time");
		String stop_time = BetNumConfig.getValue("bjdc_stop_time");
		String h_num = BetNumConfig.getValue("bjdc_week_high_incr");
		String l_num = BetNumConfig.getValue("bjdc_week_low_incr");
		String weekend_h_time = BetNumConfig.getValue("bjdc_weekend_high_time");
		String weekend_h_num = BetNumConfig.getValue("jczq_weekend_high_incre");
		String weekend_l_num = BetNumConfig.getValue("bjdc_weekend_low_incr");

		int num = BetIncrmentNum.getIncrementNum("bjdc", h_time, h_num, l_num,
				stop_time, weekend_h_time, weekend_h_num, weekend_l_num);
		String jczqnum = lotteryBetDao.getString(Keys.getBJDCKey());
		if (StringUtil.isBlank(jczqnum)) {
			lotteryBetDao.set(Keys.getBJDCKey(), num + "");
		} else {
			lotteryBetDao.set(Keys.getBJDCKey(),
					(Integer.parseInt(jczqnum) + num) + "");
		}
		log.info("BJDC num is {}", num);
		log.info("BJDC total is {}", jczqnum);

	}

	@Transactional
	@Override
	public void AddCTZQBetNum() {
		String h_time = BetNumConfig.getValue("ctzq_high_time");
		String h_num = BetNumConfig.getValue("ctzq_high_incr");
		String l_num = BetNumConfig.getValue("ctzq_low_incr");

		int num = BetIncrmentNum.getIncrementNum("ctzq", h_time, h_num, l_num,
				null, null, null, null);
		String jczqnum = lotteryBetDao.getString(Keys.getCTZQKey());
		if (StringUtil.isBlank(jczqnum)) {
			lotteryBetDao.set(Keys.getCTZQKey(), num + "");
		} else {
			lotteryBetDao.set(Keys.getCTZQKey(),
					(Integer.parseInt(jczqnum) + num) + "");
		}
		log.info("CTZQ num is {}", num);
		log.info("CTZQ total is {}", jczqnum);

	}

	@Transactional
	@Override
	public void AddSSQBetNum() {
		int num = getIncreNum();
		String jczqnum = lotteryBetDao.getString(Keys.getSSQKey());
		if (StringUtil.isBlank(jczqnum)) {
			lotteryBetDao.set(Keys.getSSQKey(), num + "");
		} else {
			lotteryBetDao.set(Keys.getSSQKey(),
					(Integer.parseInt(jczqnum) + num) + "");
		}
		log.info("SSQ num is {}", num);
		log.info("SSQ total is {}", jczqnum);

	}

	private int getIncreNum() {
		Calendar now = Calendar.getInstance();
		Calendar begin = (Calendar) now.clone();
		Calendar end = (Calendar) now.clone();
		begin.set(Calendar.HOUR_OF_DAY, 1);
		begin.set(Calendar.MINUTE, 00);
		end.set(Calendar.HOUR_OF_DAY, 9);
		end.set(Calendar.MINUTE, 00);
		if (now.after(begin) && now.before(end)) {

			return 0;

		} else {

			Random r = new Random();
			int t = r.nextInt(9);
			log.debug("SSQ increment num is {}", t);
			if (t == 6) {
				return 1;
			}

		}

		return 0;
	}

}
