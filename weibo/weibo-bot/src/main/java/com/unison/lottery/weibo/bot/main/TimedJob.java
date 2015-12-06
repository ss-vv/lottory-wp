package com.unison.lottery.weibo.bot.main;

import org.apache.log4j.Logger;

import weibo4j.Account;
import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import com.unison.lottery.weibo.bot.model.SinaWeiboUser;

public class TimedJob {

	private static Logger log = Logger.getLogger(TimedJob.class.getName());

	public void work() {
		try {
			SinaWeiboUser sinaWeiboUser = new SinaWeiboUser();
			sinaWeiboUser.setAccessToken("2.00R_h5SC0O4aZPa9976e41672Dg3tB");
			sinaWeiboUser.setUid("2104126071");
			String token = sinaWeiboUser.getAccessToken();
			Account account = new Account();
			account.setToken(token);
			String uid = sinaWeiboUser.getUid();
			log.info("我的uid:" + uid);
			log.info("====最新微博====");
			Timeline tm = new Timeline();
			tm.client.setToken(token);
			StatusWapper status;
			status = tm.getHomeTimeline();
			for (Status s : status.getStatuses()) {
				log.info("<<" + s.getUser().getName() + ">> : " + s.getText());
				log.info("----------------------------------");
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
