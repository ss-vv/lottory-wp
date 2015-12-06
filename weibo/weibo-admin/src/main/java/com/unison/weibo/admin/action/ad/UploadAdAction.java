package com.unison.weibo.admin.action.ad;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.lang.Constant;
import com.unison.weibo.admin.action.BaseAction;
import com.unison.weibo.admin.commons.upload.UploadException;
import com.unison.weibo.admin.commons.upload.UploadHandler;
import com.unison.weibo.admin.config.SystemConfig;

public class UploadAdAction extends BaseAction{

	private static final long serialVersionUID = 3894641809502465886L;
	Logger log = LoggerFactory.getLogger(getClass());

	private File file;
	private String contentType;
	private String fileName;
	
	public UploadAdAction(){
		
		data.setSuccess(false);
	}
	
	public String execute(){
		
		if(null != file && file.length() > 0) {
			boolean isValidFile = UploadHandler.validFile(contentType, fileName);
			if(isValidFile) {
				try {
					String path = SystemConfig.getValue("image.ad.directory");
					String saveName = UploadHandler.getName(fileName);
					String fileSavePath = UploadHandler.saveAs(file, path, saveName);
					
					data.setSuccess(true);
					setData(Constant.AD_IMAGE_PREFIX + fileSavePath);
				} catch (UploadException e) {
					log.error("文件上传异常：{}", e);
					setData(e.getMessage());
					e.printStackTrace();
				}
			} else {
				setData("文件类型不匹配.");
			}
		}
		return SUCCESS;
	}


	public void setUpload(File file) {
		this.file = file;
	}

	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUploadFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
