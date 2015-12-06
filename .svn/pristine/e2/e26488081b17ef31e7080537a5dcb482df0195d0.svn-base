package com.unison.weibo.admin.action.league;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.weibo.admin.action.BaseAction;
import com.unison.weibo.admin.action.app.model.GroupParam;
import com.unison.weibo.admin.service.ActivityDaoWeiboAdmin;
import com.unison.weibo.admin.service.FootballLeagueService;
import com.xhcms.lottery.commons.persist.entity.ActivityPO;


import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.WinningNewDao;
import com.unison.lottery.weibo.common.service.WinningNewService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WinningNew;
import com.unison.lottery.weibo.data.vo.WinningNewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class footballLeagueAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 123423L;

	@Autowired
	private FootballLeagueService leagueDao;
	
	private LeagueInfoPO leagueinfoPO = new LeagueInfoPO();
	private PageRequest pageRequest = new PageRequest();
	private int page;	
	private String appIdList;
	
	
	String key;//字段名
	String newdata;//新的数据
	
	String source;
	
	
	private PageResult<LeagueInfoPO> pageResult;
	
	private PageResult<GroupParam> groupPageResult;
	
	private String groupid;
	
	private String content;
	
	public footballLeagueAction()
	{
		pageRequest.setPageSize(5);
	}
	
	@Override
	public String execute() throws Exception {
		if(page < 1){
			page = 1 ;
		}
		pageRequest.setPageIndex(page);
		
		List<LeagueInfoPO> leagueInfoPOs = leagueDao.findAll(pageRequest);
			
		if(leagueInfoPOs != null && leagueInfoPOs.size() > 0){
			pageResult = new PageResult<LeagueInfoPO>();
			pageResult.setResults(leagueInfoPOs);
		}
		return SUCCESS;
	}
	/**
	 * 详情
	 * @return
	 */
	public String footballleagueinfo()
	{
		LeagueInfoPO leafootball = leagueDao.findLagueByID(leagueinfoPO.getId());
		setData(leafootball);
		return SUCCESS;
	}
	
	/**
	 * 编辑
	 * @return
	 */
	public String footballleagueedit()
	{
		System.out.println(leagueinfoPO.getId()+":"+key+":"+newdata);//测试
		leagueDao.footballleagueEdit(leagueinfoPO.getId(),key,newdata);
		return SUCCESS;
	}
	
	
	public FootballLeagueService getLeagueDao() {
		return leagueDao;
	}
	public void setLeagueDao(FootballLeagueService leagueDao) {
		this.leagueDao = leagueDao;
	}
	public LeagueInfoPO getLeagueinfoPO() {
		return leagueinfoPO;
	}
	public void setLeagueinfoPO(LeagueInfoPO leagueinfoPO) {
		this.leagueinfoPO = leagueinfoPO;
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
	public PageResult<LeagueInfoPO> getPageResult() {
		return pageResult;
	}
	public void setPageResult(PageResult<LeagueInfoPO> pageResult) {
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

	
}
