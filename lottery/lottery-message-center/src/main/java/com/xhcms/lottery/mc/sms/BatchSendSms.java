package com.xhcms.lottery.mc.sms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatform;
import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatformResult;
/**
 * 批量发送短信工具
 * @author 95
 *
 */
public class BatchSendSms {
	
	private static final int MAX_NUMBER_OF_PHONE_TO_SEND_PER_LOOP = 1000;//每轮最大发送手机号数量

	public static void main(String[] args) {
		String filePath=args[0];
		String data="";
		if(StringUtils.isNotBlank(filePath)){
			File file=new File(filePath);
			if(file.exists()){
				try {
					List<String> lines = FileUtils.readLines(file);
					if(null!=lines&&!lines.isEmpty()){
						System.out.println("总共读取"+lines.size()+"行");
						AspireSmsPlatform aspireSmsPlatform=new AspireSmsPlatform();
						aspireSmsPlatform.setUserId("030000008");
						aspireSmsPlatform.setPassword("aaa@111");
						aspireSmsPlatform.setRequestUrl("http://111.13.19.27/smsbill/mt");
						aspireSmsPlatform.setTemplateId("030000008_002");
						aspireSmsPlatform.setSignature("【大V彩】");
						List<List<String>> toSendPhones=splitToSendPhones(lines);
						if(null!=toSendPhones&&!toSendPhones.isEmpty()){
							System.out.println("需要发"+toSendPhones.size()+"轮");
							int i=1;
							for(List<String> toSendPhoneList:toSendPhones){
								System.out.println("第"+i+"轮开始");
								if(null!=toSendPhoneList&&!toSendPhoneList.isEmpty()){
									String phone = makePhone(toSendPhoneList);
									
									AspireSmsPlatformResult result = aspireSmsPlatform.send(phone, null, data);
									System.out.println("调用卓望短信平台结果是:"+result);
								}else{
									System.out.println("无需处理");
								}
								System.out.println("第"+i+"轮结束");
								i++;
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	private static String makePhone(List<String> toSendPhoneList) {
		return StringUtils.join(toSendPhoneList, ",");
	}

	private static List<List<String>> splitToSendPhones(List<String> lines) {
		List<List<String>> result=new ArrayList<List<String>>();
		int size=lines.size();
		if(size<=MAX_NUMBER_OF_PHONE_TO_SEND_PER_LOOP){
			result.add(lines);
		}
		else if(size>MAX_NUMBER_OF_PHONE_TO_SEND_PER_LOOP){
			int index=1;
			List<String> toSendPhones=null;
			for(String line :lines){
				if(index<MAX_NUMBER_OF_PHONE_TO_SEND_PER_LOOP){
					if(index==1){
						toSendPhones=new ArrayList<String>(MAX_NUMBER_OF_PHONE_TO_SEND_PER_LOOP);
					}
					addPhone(toSendPhones, line);
					index++;
				}
				else if(index==MAX_NUMBER_OF_PHONE_TO_SEND_PER_LOOP){
					addPhone(toSendPhones, line);
					index=1;
					result.add(toSendPhones);
					toSendPhones=null;
				}
			}
			if(null!=toSendPhones&&!toSendPhones.isEmpty()){//将剩余的加入
				result.add(toSendPhones);
			}
		}
		return result;
	}

	private static void addPhone(List<String> toSendPhones, String line) {
		if(StringUtils.isNotBlank(line)){
			toSendPhones.add(line);
		}
	}

}
