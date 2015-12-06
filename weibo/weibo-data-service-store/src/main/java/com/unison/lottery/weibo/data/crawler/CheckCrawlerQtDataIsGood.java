package com.unison.lottery.weibo.data.crawler;

import java.io.File;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.unison.lottery.weibo.dataservice.commons.crawler.util.EmailSend;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;

/**
 * 检测数据抓取是否正常，如不正常发送邮件
 * 
 * @author baoxing.peng
 *
 * @since 2015年1月23日上午9:29:04
 */
public class CheckCrawlerQtDataIsGood {
	private static final String TOMCAT_ACCESS_PATCH = "tomcat_accessPatch";

	public static void main(String[] args) {
		try {
			File file = new File("size.t"); // 读取文件
			if (!file.exists()) {
				file.createNewFile();
			}
			String size = FileUtils.readFileToString(file, "utf-8"); // 读取上一次存储的记录访问tomcat的文件的大小
			File file2 = new File(
					SystemPropertiesUtil.getPropsValue(TOMCAT_ACCESS_PATCH)
							+ "/localhost_access_log."
							+ DateFormateUtil.getStringOfDate("yyyy-MM-dd",
									new Date()) + ".txt");
			long accessSize = 0;
			if(file2.exists()){
				accessSize = FileUtils.sizeOf(file2);
			}
			FileUtils.write(file, String.valueOf(accessSize),false);
			long size1 = makeInteger(size);
			if (size1 == accessSize) { // 本次文件大小与上次记录的文件大小相同，表名抓取服务出现问题，发送邮件
				sendMail();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Long makeInteger(String size) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(size)){
			return Long.valueOf(size);
		}
		return 0L;
	}

	private static void sendMail() {
		EmailSend emailSend = new EmailSend(
				SystemPropertiesUtil.getPropsValue("davcaiEmailName"),
				SystemPropertiesUtil.getPropsValue("davcaiEmailPass"));
		String[] userList = SystemPropertiesUtil.getPropsValue("userMailList")
				.split(",");
		if (userList != null && userList.length > 0) {
			try {
				emailSend.send(userList, "125抓取球探数据警报",
						"125接收抓取球探数据的tomcat已经有十分钟没有接收到数据了，请登录125服务器进行查看");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
