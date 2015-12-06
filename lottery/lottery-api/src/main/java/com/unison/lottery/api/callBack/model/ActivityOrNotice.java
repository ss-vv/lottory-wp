package com.unison.lottery.api.callBack.model;

import java.io.BufferedInputStream;

import javax.servlet.ServletInputStream;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.unison.lottery.api.framework.utils.ProtocolUtils;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ActivityOrNotice {
	
	static Logger logger = LoggerFactory.getLogger(ActivityOrNotice.class);
	
	public String type; // 1(活动),2(公告),4(更新群方案)

	public String content; //公告内容
	
	public String endTime;//"截止时间"

	public String activityUrl;//"活动地址Url"

	public String activityId;//活动Id
	
	public String userId;//"用户id",

	public String nickName;//"昵称",

	public String subtType;//"0(大v)和1(普通)",

	public String  schemeId;//"方案Id",

     public String status;//"方案状态0：未出票 1：可出票 2：已请求出票 3：已成功出票 4：出票失败 5：未中奖 6：中奖未派奖 7：已派奖 8：流标 9：撤单"

	public String host;//主队名字

	public String guest;//客队名字

	public String url;//视频直播的地址

	public String info;//赛事信息，比如亚洲杯决赛

	public String imageUrl;
	

  
	public static ActivityOrNotice parseJson(ServletInputStream inputStream) {
		ActivityOrNotice activityOrNotice = null;
		if (null != inputStream) {
			try {
				BufferedInputStream bis = new BufferedInputStream(inputStream);
				String requestStr = ProtocolUtils.readStrings(bis);
				logger.debug("requestStr=" + requestStr);
				if (StringUtils.isNotEmpty(requestStr)) {
					ObjectMapper mapper = new ObjectMapper();
					activityOrNotice = mapper.readValue(requestStr, ActivityOrNotice.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return activityOrNotice;
	}
}
