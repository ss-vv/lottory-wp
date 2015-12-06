package com.unison.lottery.weibo.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.newsextract.dao.NewsDao;
import com.unison.lottery.newsextract.data.ExtractNews;
import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.xhcms.commons.job.Job;

public class AutoPublishWeiboNewsTask extends Job {
	private static final Logger logger = LoggerFactory
			.getLogger(AutoPublishWeiboNewsTask.class);
	
	@Autowired
	NewsDao newsDaoImpl;
	@Autowired
	MessageService messageService;
	@Override
	protected void execute() throws Exception {
		logger.info("开始自动发微博----");
		List<ExtractNews> news = newsDaoImpl.getUnpublishNews(NewsType.FootBall);
		for (ExtractNews extractNews : news) {
			if(StringUtils.isBlank(extractNews.getContent())){
				newsDaoImpl.moveUnpublishToPublish(extractNews);
				continue;
			}
			WeiboMsg weiboMsg = new WeiboMsg();
			weiboMsg.setOwnerId(46);
			weiboMsg.setTitle(extractNews.getTitle());
			String content  = extractNews.getContent().replace("<p>", "");
			weiboMsg.setContent(content.replace("</p>", "\n"));
			weiboMsg.setCreateTime(new Date().getTime());
			if(null != extractNews.getSource()){
				int a = extractNews.getSource().indexOf(">");
				String b = extractNews.getSource().substring(a + 1);
				a = b.indexOf("<");
				b = b.substring(0, a);
				weiboMsg.setFrom(b);
			}
			messageService.publish(weiboMsg);
			newsDaoImpl.moveUnpublishToPublish(extractNews);
		}
	}
}
