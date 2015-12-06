package com.unison.weibo.admin.action.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.weibo.admin.action.BaseAction;
import com.unison.weibo.admin.action.app.hx.api.EasemobChatGroups;
import com.unison.weibo.admin.action.app.hx.api.HX_sendMassage;
import com.unison.weibo.admin.action.app.model.GroupParam;
import com.unison.weibo.admin.action.app.model.Notice;
import com.unison.weibo.admin.service.ActivityDaoWeiboAdmin;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.persist.dao.ActivityDao;
import com.xhcms.lottery.commons.persist.entity.ActivityPO;

public class AppActivityAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private ActivityDaoWeiboAdmin activityDaoWeiboAdmin;
	
	private ActivityPO activityPO = new ActivityPO();
	
	private PageRequest pageRequest = new PageRequest();
	private int page;	
	private String appIdList;
	private PageResult<ActivityPO> pageResult;
	
	private PageResult<GroupParam> groupPageResult;
	
	private String groupid;
	
	private String content;
	
	public AppActivityAction() {
		pageRequest.setPageSize(5);
	}
	
	public String findGroupids(){
		ObjectNode groupIds = EasemobChatGroups.getAllChatgroupids();
		if(groupIds != null && groupIds.get("data").size() >= 0){
			List<GroupParam> alList = new ArrayList<GroupParam>();
			JsonNode allGroup = groupIds.get("data");
			GroupParam groupParam = null;
			for(int i = 0 ; i < allGroup.size() ; i++){
				groupParam = new GroupParam();
				groupParam.name = allGroup.get(i).get("groupname").toString();
				groupParam.groupid = allGroup.get(i).get("groupid").toString();
				alList.add(groupParam);
			}
			ObjectMapper mapper = new ObjectMapper();
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(alList);
				super.setData(jsonString);
			} catch (JsonGenerationException e) {
				super.setData(Data.failure(null));
				e.printStackTrace();
			} catch (JsonMappingException e) {
				super.setData(Data.failure(null));
				e.printStackTrace();
			} catch (IOException e) {
				super.setData(Data.failure(null));
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String sendContentToGroup(){
		JsonNode datas = null;
		if(StringUtils.equals("ALL", groupid)){
			ObjectNode groupIds = EasemobChatGroups.getAllChatgroupids();
			if(groupIds != null && groupIds.get("data").size() >= 0){
				datas = groupIds.get("data");
			 }
		} else {
			groupid = groupid.substring(1, groupid.length() - 1);
		}
		Notice notice = new Notice();
		notice.type="2";
		notice.content = content;
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(notice);
			HX_sendMassage hx_sendMassage = new HX_sendMassage();
			hx_sendMassage.sendMsg2Groups(jsonString,datas,groupid);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String deleteActivity() {
		List<Long> list = new ArrayList<Long>();
		if(StringUtils.isNotBlank(appIdList)) {
			List<String> idList = Arrays.asList(appIdList.split(","));
			for(String topicId : idList) {
				if(StringUtils.isNotBlank(topicId)) {
					list.add(Long.parseLong(topicId));
				}
			}
			try {
				if(list.size() > 0){
					String oldMaxVersion = activityDao.findMaxVersion();
					if(StringUtils.isNotBlank(oldMaxVersion)){
						activityDao.updateAllVersion(Integer.valueOf(oldMaxVersion),list);
					}
				}
			} catch (Throwable th) {
				super.setData(Data.failure(null));
				th.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String putOnApp() {
		List<Long> list = new ArrayList<Long>();
		if(StringUtils.isNotBlank(appIdList)) {
			List<String> idList = Arrays.asList(appIdList.split(","));
			for(String topicId : idList) {
				if(StringUtils.isNotBlank(topicId)) {
					list.add(Long.parseLong(topicId));
				}
			}
			try {
				if(list.size() > 0){
					String oldMaxVersion = activityDao.findMaxVersion();
					if(StringUtils.isNotBlank(oldMaxVersion)){
						activityDao.updatePutOnListVersion(Integer.valueOf(oldMaxVersion),list);
					}
				}
			} catch (Throwable th) {
				super.setData(Data.failure(null));
				th.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	
	public String putOffApp() {
		List<Long> list = new ArrayList<Long>();
		if(StringUtils.isNotBlank(appIdList)) {
			List<String> idList = Arrays.asList(appIdList.split(","));
			for(String topicId : idList) {
				if(StringUtils.isNotBlank(topicId)) {
					list.add(Long.parseLong(topicId));
				}
			}
			try {
				if(list.size() > 0){
					String oldMaxVersion = activityDao.findMaxVersion();
					if(StringUtils.isNotBlank(oldMaxVersion)){
						activityDaoWeiboAdmin.updatePutOffListVersion(Integer.valueOf(oldMaxVersion),list);
					}
				}
			} catch (Throwable th) {
				super.setData(Data.failure(null));
				th.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 下移
	 * @return
	 */
	public String putDownApp() {
		List<Long> list = new ArrayList<Long>();
		if(StringUtils.isNotBlank(appIdList)) {
			List<String> idList = Arrays.asList(appIdList.split(","));
			for(String topicId : idList) {
				if(StringUtils.isNotBlank(topicId)) {
					list.add(Long.parseLong(topicId));
				}
			}
			try {
				if(list.size() > 0){
					String oldMaxVersion = activityDao.findMaxVersion();
					if(StringUtils.isNotBlank(oldMaxVersion)){
						activityDaoWeiboAdmin.updatePutDownListVersuib(Integer.valueOf(oldMaxVersion),list);
					}
				}
			} catch (Throwable th) {
				super.setData(Data.failure(null));
				th.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 上移
	 * @return
	 */
	public String putUpApp() {
		List<Long> list = new ArrayList<Long>();
		if(StringUtils.isNotBlank(appIdList)) {
			List<String> idList = Arrays.asList(appIdList.split(","));
			for(String topicId : idList) {
				if(StringUtils.isNotBlank(topicId)) {
					list.add(Long.parseLong(topicId));
				}
			}
			try {
				if(list.size() > 0){
					String oldMaxVersion = activityDao.findMaxVersion();
					if(StringUtils.isNotBlank(oldMaxVersion)){
						activityDaoWeiboAdmin.updatePutUpListVersuib(Integer.valueOf(oldMaxVersion),list);
					}
				}
			} catch (Throwable th) {
				super.setData(Data.failure(null));
				th.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 置顶
	 * @return
	 */
	public String putUpUpApp() {
		List<Long> list = new ArrayList<Long>();
		if(StringUtils.isNotBlank(appIdList)) {
			List<String> idList = Arrays.asList(appIdList.split(","));
			for(String topicId : idList) {
				if(StringUtils.isNotBlank(topicId)) {
					list.add(Long.parseLong(topicId));
				}
			}
			try {
				if(list.size() > 0){
					String oldMaxVersion = activityDao.findMaxVersion();
					if(StringUtils.isNotBlank(oldMaxVersion)){
						activityDaoWeiboAdmin.updatePutUpUpListVersuib(Integer.valueOf(oldMaxVersion),list);
					}
				}
			} catch (Throwable th) {
				super.setData(Data.failure(null));
				th.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	@Override
	public String execute() throws Exception {
		List<ActivityPO> activityPOs = activityDaoWeiboAdmin.findAll();
		if(activityPOs != null && activityPOs.size() > 0){
			pageResult = new PageResult<ActivityPO>();
			pageResult.setResults(activityPOs);
		}
		return SUCCESS;
	}
	
	/**
	 * 推送消息
	 * @return
	 */
	public String sendActivityApp()
	{
		return SUCCESS;
	}
	
	public String createActivity() {
		if(!isValidActivity(activityPO)) {
			super.setData(Data.failure(null));
		} else {
			try {
				activityPO.setStatus(1); //有先设置成无效
				activityDao.createActivity(activityPO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String getActivityById(){
		if(null != activityPO && activityPO.getId() != null) {
			ActivityPO activityPO1 = activityDao.getActivityById(activityPO.getId());
			super.setData(activityPO1);
		}
		return SUCCESS;
	}
	
	private boolean isValidActivity(ActivityPO activityPO) {
		boolean ret = true;
		if(null == activityPO || StringUtils.isBlank(activityPO.getTitle()) || StringUtils.isBlank(activityPO.getImageURL())) {
			ret = false;
		}
		return ret;
	}
	
	public ActivityPO getActivityPO() {
		return activityPO;
	}

	public void setActivityPO(ActivityPO activityPO) {
		this.activityPO = activityPO;
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

	public PageResult<ActivityPO> getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult<ActivityPO> pageResult) {
		this.pageResult = pageResult;
	}

	public String getAppIdList() {
		return appIdList;
	}

	public void setAppIdList(String appIdList) {
		this.appIdList = appIdList;
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
}
