package com.unison.lottery.weibo.web.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.exception.LogicException;

/**
 * 微博上传类
 * @author Wang Lei
 *
 */
public class FileUpload {
	private static Logger log = LoggerFactory.getLogger(FileUpload.class);
	/** 根路径 */
	private static String basePath = ServletActionContext.getServletContext()
			.getRealPath("/");
	//定义允许上传的文件扩展名
	static final HashMap<String, String> extMap = new HashMap<String, String>(){
		private static final long serialVersionUID = -4613691788549216348L;
		{
			put("image", "gif,jpg,jpeg,png,bmp");
			put("flash", "swf,flv");
			put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		}
	};
	
	/**
	 * 上传微博图片
	 * @param file
	 * @param uploadContentType
	 * @param uploadFileName
	 * @return 图片路径
	 */
	public static String uploadWeiboImage(File file,	String uploadContentType, String uploadFileName){
		checkParameters(file, uploadContentType, uploadFileName);
		String fileExtName =  uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1, uploadFileName.length());
		checkExtdName("image", fileExtName);
		String path = WeiboWebConfig.getValue("image.weibo.directory");
		log.info("path={}",path);
		String url = WeiboWebConfig.getValue("image.weibo.url");
		return url + uploadImg(file, path, fileExtName);
	}
	
	public static String uploadHeadImage(File file,	String uploadContentType, String uploadFileName){
		//TODO
		return null;
	}
	
	public static String uploadFile(File file,	String uploadContentType, String uploadFileName){
		//TODO
		return null;
	}
	
	private static String uploadImg(File file, String path, String fileExtName){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		if(path.indexOf("\\") != -1){
			path += ymd + "\\";
		}else{
			path += ymd + "/";
		}
		String fileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtName;
		// 原图全路径
		String watermarkFilePath = path + fileName;
		log.info("watermarkFilePath={}",watermarkFilePath);
		// 缩略图全路径
		String thumbFilePath = watermarkFilePath + "!thumbFile." + fileExtName;
		log.info("thumbFilePath={}",thumbFilePath);
		// 正常图全路径
		String customFilePath = watermarkFilePath + "!custom." + fileExtName;
		log.info("customFilePath={}",customFilePath);
		
		try {
			// 创建目录
			File saveDirFile = new File(watermarkFilePath);
			if (!saveDirFile.getParentFile().exists()) {
				saveDirFile.getParentFile().mkdirs();
			}
			// 创建缩略图
			Thumbnails.of(file)
				.size(110, 110)
				.outputFormat(fileExtName)
				.outputQuality(0.9f)
				.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/images/mark.gif")),1f)
				.toFile(thumbFilePath);
			// 创建正常图
			Thumbnails.of(file)
				.size(480, 500)
				.outputFormat(fileExtName)
				.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/images/mark.gif")),1f)
				.toFile(customFilePath);
			// 创建原图 加水印
			Thumbnails.of(file)
				.scale(1f)
				.toFile(watermarkFilePath);
			return ymd + "/" + fileName + "!custom." + fileExtName;
		} catch (Exception e) {
			log.error("上传失败！",e);
		}
		return null;
	}
	
	private static void checkExtdName(String type, String fileExt){
		if(!Arrays.<String>asList(extMap.get(type).split(",")).contains(fileExt)){
			throw new LogicException("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(type) + "格式。");
		}
	}
	
	private static void checkParameters(File imgFile,String uploadContentType, String uploadFileName){
		if(imgFile == null){
			throw new LogicException("上传失败！上传文件为空！");
		}
		if(StringUtils.isBlank(uploadContentType)){
			throw new LogicException("上传失败！文件为空！");
		}
		if(StringUtils.isBlank(uploadFileName) || uploadFileName.lastIndexOf(".") == -1){
			throw new LogicException("上传失败！上传文件名不正确！");
		}
		return;
	}
}
