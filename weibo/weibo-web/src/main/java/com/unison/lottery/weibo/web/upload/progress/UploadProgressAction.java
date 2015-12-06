package com.unison.lottery.weibo.web.upload.progress;

import com.unison.lottery.weibo.web.action.BaseAction;

public class UploadProgressAction extends BaseAction{
	private static final long serialVersionUID = 3425365047898462225L;
	private ResourceFileUploadStatus resourceFileUploadStatus;
	private boolean delete;
	public String getProgress(){
		resourceFileUploadStatus = (ResourceFileUploadStatus)request.getSession().getAttribute("currentUploadStatus");
		return SUCCESS;
	}
	public String delProgress(){
		request.getSession().setAttribute("currentUploadStatus",null);
		delete = true;
		return SUCCESS;
	}
	public ResourceFileUploadStatus getResourceFileUploadStatus() {
		return resourceFileUploadStatus;
	}
	public void setResourceFileUploadStatus(
			ResourceFileUploadStatus resourceFileUploadStatus) {
		this.resourceFileUploadStatus = resourceFileUploadStatus;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
}
