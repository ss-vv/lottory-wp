package com.xhcms.lottery.dc.feed.web.action.upload;

import java.io.File;
import java.util.HashSet;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.dc.feed.web.action.BaseAction;
import com.xhcms.lottery.utils.ReadFile;

/**
 * 文件上传action,返回json
 * @author Wang Lei
 *
 */
public class AjaxUploadAction extends BaseAction{
	
	private static final long serialVersionUID = -4616671710968518044L;

	private File upload;
    private String uploadFileName;
    private String playId;
    private Data data;
    private String oldContent;
    
    public String execute(){
    	try {
    		HashSet<String> result = ReadFile.readTxt(upload,oldContent,playId);
    		if(null!=result && !result.isEmpty()){
    			data = Data.success(result);
    		}else{
    			data = Data.failure("请上传正确的文件！");
    		}
		} catch (Exception e) {
			data = Data.failure(e.getMessage());
			e.printStackTrace();
		}
    	return SUCCESS;
    }

    public Data getData() {
        return data;
    }

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getOldContent() {
		return oldContent;
	}

	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}
    
}
