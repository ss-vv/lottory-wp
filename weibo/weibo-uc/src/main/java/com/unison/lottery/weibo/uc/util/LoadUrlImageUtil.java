package com.unison.lottery.weibo.uc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadUrlImageUtil {
	private static Logger logger = LoggerFactory.getLogger(LoadUrlImageUtil.class);
	public static File getUrlImage(String u) throws IOException{
		URL url = new URL(u);
		File outFile = new File("./tmp.jpg");
		OutputStream os = new FileOutputStream(outFile);
		InputStream is = url.openStream();
		byte[] buff = new byte[1024];
		while(true) {
			int readed = is.read(buff);
			if(readed == -1) {
				break;
			}
			byte[] temp = new byte[readed];
			System.arraycopy(buff, 0, temp, 0, readed);
			os.write(temp);
		}
		is.close(); 
		os.close();
		return outFile;
	}
	
	public static String saveHeadImageFile(File image){
		String rootPath = "/data/weibo/images/";
		String storageDir = getStorageDirectory();
		String realDir = rootPath + storageDir;
		
		if(new File(realDir).exists() == false){
			new File(realDir).mkdirs();
		}
		String fileName = getStorageFileName();
		
		logger.info("存储目录：" + realDir);
		File storageFile = new File(realDir, fileName);
		try {
			Thumbnails.of(image).size(180, 180)
					.outputFormat("jpg").toFile(storageFile);
			return "http://images.davcai.com" + storageDir + fileName;
		} catch (IOException e) {
			logger.error("上传失败",e);
			return "";
		}
	}
	private static String getStorageFileName() {
		String extendName = ".jpg";
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssssss");
		String time = format.format(date);
		// 时间+随机数+拓展名
		return time + "_" + (int) (Math.random() * 1000) + extendName + "!180x180"
				+ extendName;
	}
	private static String getStorageDirectory() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String time = format.format(date);
		return "/head/" + time + "/";
	}
}
