package com.unison.lottery.api.dav;

import com.unison.lottery.api.login.hx.comm.Constants;
import com.unison.lottery.api.protocol.common.VONames;
import com.xhcms.commons.util.Text;

public class DavUtil {
	
	public  static void  main(String[] args){
		String userId="14568";
		String hxUserName = Text.MD5Encode(userId + Constants.APPKEY);
		String hxUserPassword=Text.MD5Encode(userId + VONames.HX_USE_STRINE);
		System.out.println("hxUserName="+hxUserName);
		System.out.println("hxUserPassword="+hxUserPassword);
	}

}
