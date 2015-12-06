package com.xhcms.lottery.pb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VelocityUtil {
	private static Logger logger = LoggerFactory.getLogger(VelocityUtil.class);
	private static VelocityEngine ve;

	public static VelocityEngine getVelocityEngine() {
		if (null == ve) {
			try {
				String path = VelocityUtil.class.getClassLoader().getResource("").toURI().getPath();
				System.out.println(path);
				File file = new File(path + "velocity4job.properties");
				FileInputStream fis = null;
				fis = new FileInputStream(file);
				Properties prop = new Properties();
				prop.load(fis);
				ve = new VelocityEngine(prop);
				return ve;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				logger.error("不能加载velocity4job.properties文件...", e);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Problem loading Properties file ...", e);
				return null;
			}  catch (Exception e) {
				e.printStackTrace();
				logger.error("Problem initializing Velocity ...", e);
				return null;
			} 
		} else {
			return ve;
		}
	}
	
	public static String merge(VelocityContext velocityContext, String path){
		Template vTemplate = VelocityUtil.getVelocityEngine().getTemplate(path);
		StringWriter writer = new StringWriter();
		vTemplate.merge(velocityContext, writer);
		String contentXml = writer.toString();
		return contentXml;
	}
}
