package com.unison.lottery.weibo.web.action.pc.ajax;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;

import com.unison.lottery.weibo.data.UploadFileResult;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.util.WeiboWebConfig;

public class AjaxUploadHeadImageAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private File image;    //和在JS中指定的fileObjName的值相同
    private String imageFileName;    //[fileName]FileName    获得上传文件的名称
    private String imageContentType;//[fileName]ContentType  获得上传文件的类型
	
    /** 上传的文件类型拓展名 （不含 '.' 字符） */
	private String uploadExtdName;
	private int imageWidth = Integer.parseInt(WeiboWebConfig
			.getValue("head.image.width"));
	private int imageHeight = Integer.parseInt(WeiboWebConfig
			.getValue("head.image.height"));
	private UploadFileResult data = new UploadFileResult();
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public String execute(){
		if(null == image){
			data.fail("上传失败,请刷新页面后重试");
			return SUCCESS;
		}
		
		String rootPath = WeiboWebConfig.getValue("head.image.rootPath");
		String storageDir = getStorageDirectory();
		String realDir = rootPath + storageDir;
		
		if(new File(realDir).exists() == false){
			new File(realDir).mkdirs();
		}
		String fileName = getStorageFileName();
		
		logger.info("存储目录：" + realDir);
		File storageFile = new File(realDir, fileName);
		try {
			Thumbnails.of(image).size(imageWidth, imageHeight)
					.outputFormat(uploadExtdName).toFile(storageFile);
			data.setImageURL("http://images.davcai.com" + storageDir + fileName);
			data.setDesc("上传成功");
			data.setSuccess(true);
		} catch (IOException e) {
			data.fail("上传失败");
			logger.error("上传失败",e);
		}
		return SUCCESS;
	}
	private String getStorageDirectory() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String time = format.format(date);
		return WeiboWebConfig.getValue("head.image.directory") + time + "/";
	}
	private String getStorageFileName() {
		String extendName = "." + uploadExtdName;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssssss");
		String time = format.format(date);
		// 时间+随机数+拓展名
		return time + "_" + (int) (Math.random() * 1000) + extendName + "!"
				+ imageWidth + "x" + imageHeight + extendName;
	}
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
		this.uploadExtdName = imageFileName.substring(imageFileName
				.indexOf(".") + 1);
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public UploadFileResult getData() {
		return data;
	}

	public void setData(UploadFileResult data) {
		this.data = data;
	}
}	
