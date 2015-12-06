package com.unison.weibo.admin.commons.upload;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class FileUploadUtil {

	public static String getRealIP(HttpServletRequest httpRequest) {
		String realIP = "unknown";
		String ips = httpRequest.getHeader("X-Forwarded-For");
		if (StringUtils.isNotBlank(ips)) {
			String[] ipList = ips.split(",");
			if (null != ipList && ipList.length > 0) {
				for (String ip : ipList) {
					if (StringUtils.isNotBlank(ip)
							&& ip.equalsIgnoreCase("unknown")) {
						realIP = ip;
						break;
					}
				}
			}
		} else {
			realIP = httpRequest.getRemoteAddr();
		}
		return realIP;
	}

}