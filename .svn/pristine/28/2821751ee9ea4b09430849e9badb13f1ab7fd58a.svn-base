package com.unison.weibo.admin.commons.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unison.weibo.admin.config.SystemConfig;

/**
 * @desc 文件上传的处理程序：如限制文件的大小、扩展名等
 * @author lei.li@unison.net.cn
 * @createTime 2013-12-20
 * @version 1.0
 */
public class UploadHandler {

	private static Logger log = LoggerFactory.getLogger(UploadHandler.class);
	
	public static boolean validFile(String contentType, String fileName) {
		return isLegalExtName(fileName) && isLegalFileType(contentType);
	}
	
	/**
	 * 返回准备存储的文件名称
	 * 格式：根据当前时间生成的随机数_当前传进来的文件名称
	 * @param fileName
	 * @return
	 */
	public static String getName(String fileName) {
		if(StringUtils.isNotBlank(fileName)) {
			long currTime = System.currentTimeMillis();
			Random rd = new Random(currTime);
			long rdm = rd.nextLong();
			StringBuffer buf = new StringBuffer();
			buf.append(currTime).append(rdm).append("_").append(fileName);
			fileName = buf.toString();
		}
		return fileName;
	}
	
	public static boolean isLegalExtName(String fileName) {
		boolean result = false;
		if(StringUtils.isNotBlank(fileName)) {
			for(String ext : getSupportFileType()) {
				if(fileName.toUpperCase().endsWith(ext) ||
					fileName.toLowerCase().endsWith(ext)){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static List<String> getSupportFileType() {
		String fileExtends = SystemConfig.getValue("image.upload.extends");
		if(StringUtils.isNotBlank(fileExtends)) {
			String[] fileExts = fileExtends.split(",");
			List<String> list = Arrays.asList(fileExts);
			return list;
		}
		return Collections.EMPTY_LIST;
	}
	
	public static boolean isLegalFileType(String contentType) {
		//image/jpg
		//image/jpeg
		//image/gif
		return true;
	}
	
	public static boolean isCanAccess(String path) {
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		return file.canWrite();
	}
	
	/**
	 * 
	 * @param tempFile
	 * @param path
	 * @param newFileName
	 * @return 文件访问的URL地址。
	 * @throws UploadException
	 */
	public static String saveAs(File tempFile, String path, String newFileName) 
			throws UploadException {
		FileInputStream input = null;
		FileOutputStream output = null;
		String savePath = null;
		try {
			String sep = DateUtils.formatDate(Calendar.getInstance().getTime(), "yyyyMM");
			savePath = sep + File.separator + newFileName;
			File outFile = new File(path + savePath);
			boolean isCanAccess = isCanAccess(path + sep);
			if(!isCanAccess) {
				throw new UploadException("file upload fail, directory is not accessable of path=" +
						path);
			} else {
				outFile.createNewFile();
				output = new FileOutputStream(outFile);
				
				input = new FileInputStream(tempFile);
				int available = input.available();
				int per = 100 * 1024;//每次复制的数长度(K)
				byte[] cup = new byte[per];
				int readed = 0;
				if(available > per) {
					int after = 0;
					while((after = available - readed) > 0) {
						if(0 == readed || after / per > 0) {
							input.read(cup);
						} else {
							cup = new byte[after];
							input.read(cup);
						}
						readed = readed + cup.length;
						output.write(cup);
						output.flush();
					}
				} else {
					cup = new byte[available];
					input.read(cup);
					output.write(cup);
				}
			}
			return savePath;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("file upload error:", e);
			throw new UploadException("file upload error");
		} finally {
			try {
				if(null != output) {
					output.close();
				}
				if(null != input) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}