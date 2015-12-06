package com.unison.weibo.admin.action.league;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.weibo.admin.action.BaseAction;
import com.unison.weibo.admin.action.app.model.GroupParam;
import com.unison.weibo.admin.service.BasketBallService;

public class basketballLeagueAction  extends BaseAction {

	
	@Autowired
	private BasketBallService basketBallDao;
	
	private PageRequest pageRequest = new PageRequest();
	private int page;	
	private String appIdList;
	
	BasketBallLeagueInfoPO basketballleague = new BasketBallLeagueInfoPO();
	
	String key;//字段名
	String newdata;//新的数据
	
	String source;
	
	private PageResult pageResult ;
	
	private PageResult<GroupParam> groupPageResult;
	
	private String groupid;
	
	private String content;
	
	public basketballLeagueAction()
	{
		pageRequest.setPageSize(5);
	}
	
	/**
	 * 篮球联赛
	 * @return
	 */
	public String basketballleagueedit()
	{
		basketBallDao.basketballleagueEdit(basketballleague.getLeagueId(), key, newdata);
		return SUCCESS;
	}
	
	/**
	 * 查询详情
	 * @return
	 */
	public String basketballleagueinfo()
	{
		BasketBallLeagueInfoPO baskball = basketBallDao.findLagueByID(basketballleague.getLeagueId());
		setData(baskball);
		return SUCCESS;
	}
	
	public String execute() throws Exception {
		if(page < 1){
			page = 1 ;
		}
		pageRequest.setPageIndex(page);
		
		List<BasketBallLeagueInfoPO> basketballleagues = basketBallDao.findAll(pageRequest);
			
		if(basketballleagues != null && basketballleagues.size() > 0){
			pageResult = new PageResult<BasketBallLeagueInfoPO>();
			pageResult.setResults(basketballleagues);
		}
		
		return SUCCESS;
	}



	public BasketBallService getBasketBallDao() {
		return basketBallDao;
	}

	public void setBasketBallDao(BasketBallService basketBallDao) {
		this.basketBallDao = basketBallDao;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getAppIdList() {
		return appIdList;
	}

	public void setAppIdList(String appIdList) {
		this.appIdList = appIdList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNewdata() {
		return newdata;
	}

	public void setNewdata(String newdata) {
		this.newdata = newdata;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public PageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

	public PageResult<GroupParam> getGroupPageResult() {
		return groupPageResult;
	}

	public void setGroupPageResult(PageResult<GroupParam> groupPageResult) {
		this.groupPageResult = groupPageResult;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BasketBallLeagueInfoPO getBasketballleague() {
		return basketballleague;
	}

	public void setBasketballleague(BasketBallLeagueInfoPO basketballleague) {
		this.basketballleague = basketballleague;
	}
	
}
