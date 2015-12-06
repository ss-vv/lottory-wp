package com.unison.lottery.weibo.web.upload.progress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceProgressListener implements ProgressListener {
	private HttpSession session;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public ResourceProgressListener(HttpServletRequest request) {
		session = request.getSession();
		ResourceFileUploadStatus newUploadStatus = new ResourceFileUploadStatus();
		session.setAttribute("currentUploadStatus", newUploadStatus);
	}
	@Override
	public void update(long readedBytes, long totalBytes, int currentItem) {
		logger.debug("[Upload] readedBytes: {}, totalBytes: {}", readedBytes, totalBytes);
		ResourceFileUploadStatus status = (ResourceFileUploadStatus) session.getAttribute("currentUploadStatus");
	    status.setReadedBytes(readedBytes);
	    status.setTotalBytes(totalBytes);
	    status.setCurrentItem(currentItem);
	}
}
