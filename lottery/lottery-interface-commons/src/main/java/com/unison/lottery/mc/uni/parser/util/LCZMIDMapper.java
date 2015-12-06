package com.unison.lottery.mc.uni.parser.util;

import org.apache.commons.lang.StringUtils;

import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 接口、大V彩之间的 lotteryId, playId 转换类。
 * @author Wang Lei
 *
 */
public class LCZMIDMapper implements IDMapper {

	/**
	 *  从中民lotteryId得到大V彩玩法枚举对象。
	 *  @return UNKOWN 如果没有对应的玩法id。
	 */
	public PlayType getLCPlayTypeFromPlatformLotteryId(String platformLotteryId) throws IDMapperException {
		if (StringUtils.isBlank(platformLotteryId)){
			throw new IDMapperException("platformLotteryId is blank!");
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.SF14)){
			return PlayType.CTZC_14;
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.SFR9)){
			return PlayType.CTZC_R9;
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.BQ6C)){
			return PlayType.CTZC_BQ;
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.JQ4C)){
			return PlayType.CTZC_JQ;
		}
		
		
		if(platformLotteryId.equals(ZMInterfaceConstants.SPF)){
			return PlayType.BJDC_SPF;
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.JQS)){
			return PlayType.BJDC_JQS;
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.SXDS)){
			return PlayType.BJDC_SXDS;
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.BF)){
			return PlayType.BJDC_BF;
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.BQC)){
			return PlayType.BJDC_BQC;
		}
		if(platformLotteryId.equals(ZMInterfaceConstants.SF)){
			return PlayType.BJDC_SF;
		}
		
		return PlayType.UNKNOWN;
	}

	/**
	 * 从中民lotteryId转换为大V彩的lotteryId。
	 */
	public String getLCLotteryIdFromPlatformLotteryId(String platformLotteryId) {
		String result=null;
		if(StringUtils.isNotBlank(platformLotteryId)){
			if(platformLotteryId.equals(ZMInterfaceConstants.JX11X5)){
				result=Constants.JX11;
			}
			if(platformLotteryId.equals(ZMInterfaceConstants.ZQSSC)){
				result=Constants.CQSS;
			}
			if(platformLotteryId.equals(ZMInterfaceConstants.SF14)){
				result=Constants.CTZC;
			}
			if(platformLotteryId.equals(ZMInterfaceConstants.SFR9)){
				result=Constants.CTZC;
			}
			if(platformLotteryId.equals(ZMInterfaceConstants.BQ6C)){
				result=Constants.CTZC;
			}
			if(platformLotteryId.equals(ZMInterfaceConstants.JQ4C)){
				result=Constants.CTZC;
			}
			if(platformLotteryId.equals(ZMInterfaceConstants.SSQ)){
				result=Constants.SSQ;
			}
			if(platformLotteryId.equals(ZMInterfaceConstants.FC3D)){
				result=Constants.FC3D;
			}
			if(platformLotteryId.equals(ZMInterfaceConstants.SPF)){
				
				result=Constants.BJDC;
			}
            if(platformLotteryId.equals(ZMInterfaceConstants.JQS)){
				
				result=Constants.BJDC;
			}
            if(platformLotteryId.equals(ZMInterfaceConstants.SXDS)){
            	
            	result=Constants.BJDC;
            }
            if(platformLotteryId.equals(ZMInterfaceConstants.BF)){
            	
            	result=Constants.BJDC;
            }
            if(platformLotteryId.equals(ZMInterfaceConstants.BQC)){
            	
            	result=Constants.BJDC;
            }
            if(platformLotteryId.equals(ZMInterfaceConstants.SF)){
            	
            	result=Constants.BJDC;
            }
           
		}
		return result;
	}

}
