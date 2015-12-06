package com.unison.lottery.mc.uni.parser.util;

import java.util.ArrayList;
import java.util.List;

import com.xhcms.lottery.lang.LotteryId;

/**
 *	尊傲赛程接口中玩法编号对应关系
 * @author lei.li@davcai.com
 */
public enum ZMPlayCode {
	
	JZ_SPF_DG("1", "01_ZC_1", LotteryId.JCZQ.name().toLowerCase()),
	JZ_BF_DG("2", "02_ZC_1", LotteryId.JCZQ.name().toLowerCase()),
	JZ_JQS_DG("3", "03_ZC_1", LotteryId.JCZQ.name().toLowerCase()),
	JZ_BQC_DG("4", "04_ZC_1", LotteryId.JCZQ.name().toLowerCase()),
	JZ_BRQSPF_DG("9", "80_ZC_1", LotteryId.JCZQ.name().toLowerCase()),
	
	JL_SF_DG("1", "07_LC_1", LotteryId.JCLQ.name().toLowerCase()),
	JL_RFSF_DG("2", "06_LC_1", LotteryId.JCLQ.name().toLowerCase()),
	JL_SFC_DG("3", "08_LC_1", LotteryId.JCLQ.name().toLowerCase()),
	JL_DXF_DG("4", "09_LC_1", LotteryId.JCLQ.name().toLowerCase());
	
	private String code;
	private String playType;
	private String lottery;

	private ZMPlayCode(String code, String playType, String lottery) {
		this.code = code;
		this.playType = playType;
		this.lottery = lottery;
	}

	/**
	 * 根据不支持的code，求出支持的玩法id
	 * @param codes
	 * @param zmLottery
	 * @return
	 */
	public static List<String> supportPlayType(String[] codes, String zmLottery) {
		List<String> supportPlayIdList = new ArrayList<String>();
		
		for(ZMPlayCode zmPlayCode : ZMPlayCode.values()) {
			boolean result = false;
			if(null!=codes&&codes.length>0){
				for(String code : codes) {
					if(code.equals(zmPlayCode.getCode())) {
						result = true;
						break;
					}
				}
			}
			
			if(!result && zmPlayCode.getLottery().equals(zmLottery)) {
				supportPlayIdList.add(zmPlayCode.getPlayType());
			}
		}
		return supportPlayIdList;
	}
	
	public static ZMPlayCode findPlayType(String code, String zmLottery) {
		ZMPlayCode playType = null;
		for(ZMPlayCode zmPlayCode : ZMPlayCode.values()) {
			if(code.equals(zmPlayCode.getCode()) 
					&& zmLottery.equals(zmPlayCode.getLottery())) {
				playType = zmPlayCode;
			}
		}
		return playType;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public String getLottery() {
		return lottery;
	}

	public void setLottery(String lottery) {
		this.lottery = lottery;
	}
}