package com.unison.weibo.admin.action.weibo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.newsextract.dao.NewsDao;
import com.unison.lottery.newsextract.data.ExtractNews;
import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.weibo.admin.action.BaseAction;

/**
 * 微博新闻
 * @author haoxiang.jiang@unison.com.cn
 * @date 2014-3-13
 */
public class WeiboNewsAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private NewsDao newsDaoImpl;
	private List<ExtractNews> weiboNews;
	private PageRequest pageRequest = new PageRequest();
	private int page;
	private ExtractNews extractNews;
	/** 发布到的用户id */
	private long publishToUserId;
	@Autowired
	private MessageService messageService;
	@Autowired
	UserAccountService userAccountServiceImpl;
	
	List<WeiboUser> lotteryUsers = new ArrayList<WeiboUser>();
	
	private String newsType;
	
	public WeiboNewsAction(){
		pageRequest.setPageSize(5);
	} 
	
	public String execute(){
		if(page < 1){
			page = 1 ;
		}
		if(StringUtils.isBlank(newsType)){
			return SUCCESS;
		}
		if(newsType.endsWith(NewsType.FootBall.name())){
			pageRequest.setPageIndex(page);
			weiboNews = newsDaoImpl.getUnpublishNews(NewsType.FootBall,pageRequest);
			lotteryUsers.add(userAccountServiceImpl.findWeiboUserByNickName("竞彩足球"));
			lotteryUsers.add(userAccountServiceImpl.findWeiboUserByNickName("传统足彩"));
		} else if (newsType.endsWith(NewsType.BasketBall.name())){
			pageRequest.setPageIndex(page);
			weiboNews = newsDaoImpl.getUnpublishNews(NewsType.BasketBall,pageRequest);
			lotteryUsers.add(userAccountServiceImpl.findWeiboUserByNickName("竞彩篮球"));
		}
		return SUCCESS;
	}
	public String updateWeiboNews(){
		if(null == extractNews ||
				StringUtils.isBlank(extractNews.getId()) ||
				StringUtils.isBlank(extractNews.getContent()) || 
				StringUtils.isBlank(extractNews.getTitle()) || 
				StringUtils.isBlank(extractNews.getUrl()) || 
				StringUtils.isBlank(extractNews.getNewsType())){
			this.data.setSuccess(false);
			this.data.setData(extractNews);
			return SUCCESS;
		}
		this.data.setSuccess(newsDaoImpl.updateExtractNews(extractNews));
		this.data.setData(extractNews);
		return SUCCESS;
	}
	public String deleteWeiboNews(){
		if(null == extractNews ||
				StringUtils.isBlank(extractNews.getId())){
			this.data.setSuccess(false);
			return SUCCESS;
		} else {
			newsDaoImpl.moveUnpublishToPublish(extractNews);
			this.data.setSuccess(true);
			return SUCCESS;
		}
	}
	public String publishWeiboNews(){
		if(null == extractNews ||
				StringUtils.isBlank(extractNews.getId()) || 
				publishToUserId < 1 ){
			this.data.setSuccess(false);
			return SUCCESS;
		} else {
			if(StringUtils.isBlank(extractNews.getContent())){
				newsDaoImpl.moveUnpublishToPublish(extractNews);
			}
			WeiboMsg weiboMsg = new WeiboMsg();
			weiboMsg.setOwnerId(publishToUserId);
			weiboMsg.setTitle(extractNews.getTitle());
			weiboMsg.setContent(extractNews.getContent());
			weiboMsg.setCreateTime(new Date().getTime());
			if(null != extractNews.getSource()){
				int a = extractNews.getSource().indexOf(">");
				String b = extractNews.getSource().substring(a + 1);
				a = b.indexOf("<");
				b = b.substring(0, a);
				String content = b; 
				a = extractNews.getSource().indexOf("href=\"");
				b= extractNews.getSource().substring("href=\"".length() + 3);
				a = b.indexOf("\"");
				b = b.substring(0, a);
				weiboMsg.setFrom(content);
				weiboMsg.setFromUrl(b);
			}
			messageService.publishAsLotteryNews(weiboMsg, NewsType.getWithName(extractNews.getNewsType()));
			newsDaoImpl.moveUnpublishToPublish(extractNews);
			this.data.setSuccess(true);
			return SUCCESS;
		}
	}
	public PageRequest getPageRequest() {
		return pageRequest;
	}
	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}
	public List<ExtractNews> getWeiboNews() {
		return weiboNews;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setExtractNews(ExtractNews extractNews) {
		this.extractNews = extractNews;
	}

	public ExtractNews getExtractNews() {
		return extractNews;
	}

	public long getPublishToUserId() {
		return publishToUserId;
	}

	public void setPublishToUserId(long publishToUserId) {
		this.publishToUserId = publishToUserId;
	}

	public List<WeiboUser> getLotteryUsers() {
		return lotteryUsers;
	}

	public void setLotteryUsers(List<WeiboUser> lotteryUsers) {
		this.lotteryUsers = lotteryUsers;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
}	
