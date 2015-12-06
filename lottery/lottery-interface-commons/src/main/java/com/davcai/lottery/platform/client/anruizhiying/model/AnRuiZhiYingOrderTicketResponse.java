package com.davcai.lottery.platform.client.anruizhiying.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AnRuiZhiYingOrderTicketResponse extends AnRuiZhiYingResponse{
	
	public static final int EMPTY_RESPONSE = -9995;
	private int status;
	private String desc;
	
	public AnRuiZhiYingOrderTicketResponse() {
	}
	
	public AnRuiZhiYingOrderTicketResponse(String responseStr) {
		if(StringUtils.isNotBlank(responseStr)){
			try{
				this.status=Integer.parseInt(responseStr);
				switch(status){
					case 0:{this.desc="投注成功";break;}
					case -1:{this.desc="投注失败";break;}
					case -2:{this.desc="参数数据加密不一致";break;}
					case -3:{this.desc="参数不完整";break;}
					case -4:{this.desc="渠道ID不存在";break;}
					case -11:{this.desc="应用程序异常";break;}
					case -13:{this.desc="参数错误";break;}
					case -14:{this.desc="参数格式不正确";break;}
					case -15:{this.desc="文件没找到";break;}
					case -19:{this.desc="其它未知错误";break;}
					case -20:{this.desc="网站平台交易流水号重复";break;}
					case -2001:{this.desc="额度不足";break;}
					case -2017:{this.desc="投注帐户异常";break;}
					case -3003:{this.desc="彩种不支持多期追号";break;}
					case -3004:{this.desc="彩种不支持该玩法";break;}
					case -3301:{this.desc="商品没找到";break;}
					case -3302:{this.desc="商品不在销售状态";break;}
					case -3303:{this.desc="商品已停售";break;}
					case -3304:{this.desc="上一奖期未开奖";break;}
					case -3305:{this.desc="单式上传，商品提前半小时停售";break;}
					case -3306:{this.desc="单式上传发起合买，商品提前40分钟停售";break;}
					case -3401:{this.desc="比赛没找到";break;}
					case -3402:{this.desc="比赛没在销售状态";break;}
					case -3403:{this.desc="比赛场次已停售";break;}
					case -3404:{this.desc="有暂停的比赛";break;}
					case -3405:{this.desc="有未开售的比赛";break;}
					case -3406:{this.desc="有延期的比赛";break;}
					case -3407:{this.desc="有取消的比赛";break;}
					case -3408:{this.desc="投注比赛不支持过关方式";break;}
					case -3409:{this.desc="有无效的比赛";break;}
					case -3410:{this.desc="对阵场次号重复";break;}
					case -3411:{this.desc="对阵场次号不能为空";break;}
					case -3412:{this.desc="赛事编号不能为空";break;}
					case -3413:{this.desc="赛事名称不能为空";break;}
					case -3414:{this.desc="主队名称不能为空";break;}
					case -3415:{this.desc="客队名称不能为空";break;}
					case -3501:{this.desc="投注内容格式不正确";break;}
					case -3502:{this.desc="投注场次超过限制";break;}
					case -3503:{this.desc="玩法不支持胆拖方式投注";break;}
					case -3504:{this.desc="投注场次与过关数不符";break;}
					case -3505:{this.desc="单张票投注金额不能超过20000元";break;}
					case -3506:{this.desc="玩法投注注数验证失败";break;}
					case -3507:{this.desc="交易投注注数验证失败";break;}
					case -3508:{this.desc="上传文件没有找到";break;}
					case -3509:{this.desc="上传文件的格式不正确";break;}
					case -3510:{this.desc="彩票店没有开始销售";break;}
					case -3511:{this.desc="多期订单类型无法识别";break;}
					case -3512:{this.desc="多期订单信息与类型不匹配";break;}
					case -3513:{this.desc="原始订单没找到";break;}
					case -3514:{this.desc="多期订单中某期追号注数错误";break;}	
					case -3521:{this.desc="交易类型不支持";break;}
					case -3531:{this.desc="该投注接口不支持指定投注方式";break;}
					case -3532:{this.desc="不支持投注类型";break;}
					case -3533:{this.desc="方案份数与投注类型不匹配";break;}
					case -3534:{this.desc="方案金额与投注类型不匹配";break;}
					case -3537:{this.desc="未填写比分";break;}
					case -3538:{this.desc="未填写彩果";break;}
					case -3539:{this.desc="投注内容超出长度限制！";break;}
					case -3541:{this.desc="方案未找到";break;}
					case -3542:{this.desc="方案已处理,不能撤单";break;}
					case -3544:{this.desc="超过最大倍数";break;}
					case -3545:{this.desc="参数超出个数限制";break;}
					case -9996:{this.desc="票列表中playId为空，不允许出票";break;}
					case -9997:{this.desc="票列表中playId与期id不一致，不允许出票";break;}
					case -9998:{this.desc="彩票平台id错误，不允许出票";break;}
					case -9999:{this.desc="票列表为空";break;}
					default:{this.desc="未知错误状态码";break;}
				}
				
			}catch(Exception e){
				e.printStackTrace();
				
			}
			
		}
		else{
			this.status=EMPTY_RESPONSE;
			this.desc="返回字符串为空";
		}
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
