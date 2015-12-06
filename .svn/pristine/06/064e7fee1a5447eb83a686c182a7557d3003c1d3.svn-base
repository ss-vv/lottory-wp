package com.xhcms.lottery.dc.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateFormatUtils;

import com.xhcms.lottery.utils.TextUtils;

/**
 * 赔率基础类.
 * @author Yang Bo
 */
public class OddsBase {

	protected String code;		// 比赛场次（数字形式）
	protected long matchId;		// 比赛id
	protected String playId;	// 玩法id
	private Date offtime;		// 停售时间。比赛开始时间-1分钟
	protected String options;	// 选项
	private int status;
	private List<String> odds = new ArrayList<String>();
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取ID
	 * 
	 * @return matchId + playId
	 */
	public String getId() {
		return getMatchId() + playId;
	}

	public long getMatchId() {
		if (matchId <= 0) {
			matchId = Long.parseLong(DateFormatUtils.format(offtime, "yyyyMMdd"))*10000 + Long.parseLong(code);
		}
		return matchId;
	}

	/**
	 * 获得赔率
	 * 
	 * @return 赔率1,赔率2...
	 */
	public String getOdds() {
		if(null!=odds&&!odds.isEmpty()){
			return TextUtils.arrayToString(odds);
		}
		else{
			return "";
		}
		
	}

	public List<String> getOddsList() {
		return odds;
	}

	/**
	 * 添加一个赔率，会按照 000.00 模式 格式化.
	 * @param odd
	 */
	public void addOdd(String odd) {
		BigDecimal num = new BigDecimal(odd);
		odds.add(String.format("%.2f", num));
	}

	public void setOdds(List<String> odds){
		this.odds = odds;
	}
	
	public void setOptions(String options) {
		this.options = options;
	}

	public String getOptions() {
		return options;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public Date getOfftime() {
		return offtime;
	}

	public void setOfftime(Date offtime) {
		this.offtime = offtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
