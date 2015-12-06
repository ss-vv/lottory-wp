package com.unison.lottery.api.protocol.request.xml.model;

import java.io.BufferedInputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap; 

import javax.servlet.ServletInputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.utils.ProtocolUtils;
import com.unison.lottery.api.lang.JAXBContextHolder;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement
public class MethodRequest {
	private static final Logger logger=LoggerFactory.getLogger(MethodRequest.class);

	@XmlAttribute(name="IDCard")
	public String IDCard;

	@XmlAttribute
	public String greetings;
	
	@XmlAttribute
	public String name;
	
	@XmlAttribute
	public String nickname;
	
	@XmlAttribute
	public String nickName;
	
	@XmlAttribute
	public String sex;
	
	@XmlAttribute
	public Date birthday;
	
	@XmlAttribute
	public String phoneNumber;
	
	@XmlAttribute
	public String clientVersion;

	@XmlAttribute
	public String validId;

	@XmlAttribute
	public String channel;
	
	
	@XmlAttribute 
	public String  modelName;
	
	
	@XmlAttribute
	public String groupBy;
	
	@XmlAttribute
	public Boolean showGroup;
	
	@XmlAttribute
	public Integer perPage;
	
	@XmlAttribute
	public Integer page;
		
	@XmlAttribute
	public String password;
	
	@XmlAttribute(name="username")
	public String userName;
	
	

	@XmlAttribute
	public Integer resultCountPerPage;

	

	@XmlElement
	public Param param;

	

	@XmlAttribute
	public String advice;
	
	
	
	@XmlElement
	public byte[] binaryAttachment;
	
	@XmlAttribute
	public Integer binaryAttachmentSize;
	
	
	public HashMap<String,String> parsedLines;


	@XmlAttribute
	public Integer point;

	

	@XmlAttribute
	public String type;

	@XmlAttribute
	public String queryName;

	

	@XmlAttribute
	public String deviceId;

	@XmlAttribute
	public String realName;

	@XmlAttribute
	public String accountBank;

	@XmlAttribute
	public String city;

	@XmlAttribute
	public String withdrawPassword;

	@XmlAttribute
	public String bankID;
	
	@XmlAttribute
	public String province;

	@XmlAttribute
	public String verifyCode;

	@XmlAttribute
	public String oldPassword;

	@XmlAttribute
	public String newPassword;

	@XmlAttribute
	public String actionType;

	@XmlAttribute
	public BigDecimal amount;

	@XmlAttribute
	public String playId;

	@XmlAttribute
	public String leagueShortName;

	@XmlAttribute
	public String firstResult;

	@XmlAttribute
	public String schemeId;

	@XmlAttribute
	public String lotteryId;

	@XmlAttribute
	public String multiple;

	@XmlAttribute
	public String betNote;

	@XmlAttribute
	public String passType;

	@XmlAttribute
	public String isShow;

	@XmlAttribute
	public String showType;

	@XmlAttribute
	public String followedRatio;

	@XmlAttribute
	public String betContent;

	@XmlAttribute
	public String betType;
	
	@XmlAttribute
	public Integer chooseType;
	
	@XmlAttribute
	public String matchDate;
	
	@XmlAttribute
	public String betSchemeID;

	@XmlAttribute
	public BigDecimal rechargeAmount;

	@XmlAttribute
	public String rechargeCardType;

	@XmlAttribute
	public String rechargeCardNumber;

	@XmlAttribute
	public String rechargeCardPass;
	
	@XmlAttribute
	public String displayMode;

	@XmlAttribute
	public String filter;
	
	//期号
	@XmlAttribute
	public String issueNumber;
	
	//优惠劵类型
	@XmlAttribute
	public String voucherType;
	
	//用户优惠劵ID
	@XmlAttribute
	public String voucherUserId;
	
	//每页条数 
	@XmlAttribute
	public Integer perpageCount;
	
	@XmlAttribute
	public String imsi;
	
	@XmlAttribute
	public String imei;
	
	@JsonProperty("groupid")
	@XmlAttribute
	public String groupid;
	
	@XmlAttribute
	public String packageName;
	
	@XmlAttribute
	public String imageUrl;
	
	@XmlAttribute
	public String uid;  
	
	@XmlAttribute
	public String platType;  
	
	@XmlAttribute
	public String userId;

	@XmlAttribute
	public String buyAmount; //认购金额

	@XmlAttribute
	public String floorAmount;//保底金额

	@XmlAttribute
	public String banner;//轮播内容版本号

	@XmlAttribute
	public String oddsType;

	@XmlAttribute
	public String corpId;

	@XmlAttribute
	public String time;
	
	@XmlAttribute
	public String subType;//比赛 1-结束, 0-未结束 

	@XmlAttribute
	public String matchType;// 0--足球 , 1--篮球

	@XmlAttribute
	public Long matchId;

	@XmlTransient
	public Long redEnvalopeAmount;

	@XmlTransient
	public Integer envalopeNum;

	@XmlTransient
	public Long envalopeId;

	@XmlTransient
	public String hxUserName;
	
	public static MethodRequest parse(ServletInputStream inputStream) {
		MethodRequest methodRequest = null;
		if (null != inputStream) {		
			JAXBContext context;
			try {
				BufferedInputStream bis=new BufferedInputStream(inputStream);
				
				String requestStr=ProtocolUtils.readStrings(bis);
				logger.debug("requestStr="+requestStr);
				if(StringUtils.isNotEmpty(requestStr)){
					context = JAXBContextHolder.getInstance(MethodRequest.class);
					Unmarshaller um = context.createUnmarshaller();
					methodRequest = (MethodRequest) um.unmarshal(new StringReader(requestStr.trim()));
				}
				
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return methodRequest;
	}
	
	public static MethodRequest parseFromPlainText(
			ServletInputStream inputStream) {
		MethodRequest methodRequest=null;
		int byteCountOfBinaryAttachment=-1;
		try {
			BufferedInputStream bis=new BufferedInputStream(inputStream);
			methodRequest=new MethodRequest();
			methodRequest.parsedLines=new HashMap<String,String>();
			String lineToParse=null;
			while((lineToParse=ProtocolUtils.readString(bis).trim())!=null){
				if(lineToParse.contains(ProtocolUtils.MARK_4_PARSE_LINE)){
					String[] splits=lineToParse.split(ProtocolUtils.MARK_4_PARSE_LINE);
					if(null!=splits&&splits.length==2){
						methodRequest.parsedLines.put(splits[0].trim(), splits[1].trim());
						if(splits[0].equalsIgnoreCase(ProtocolUtils.SIZE_OF_ATTACHMENT)){
							byteCountOfBinaryAttachment=Integer.parseInt(splits[1].trim());
							break;
						}
					}
					
				}
			}
			bis.read();//去掉上一步遗留的\n
			if(byteCountOfBinaryAttachment>0){
				byte[] binaryAttachment=new byte[byteCountOfBinaryAttachment];
				int count=0;
				//byte[] buffer=new byte[]
				int offsetForRecive=0;
				while((count=bis.read(binaryAttachment, 0+offsetForRecive, byteCountOfBinaryAttachment-offsetForRecive))!=0){
					offsetForRecive+=count;
				}
				logger.debug("byteCountOfBinaryAttachment="+byteCountOfBinaryAttachment);
				logger.debug("offsetForRecive="+offsetForRecive);
				if(byteCountOfBinaryAttachment==offsetForRecive){
					methodRequest.binaryAttachment=binaryAttachment;
				//	methodRequest.setBinaryAttachment(binaryAttachment);
					/*//测试时存文件
					File file=new File("E:\\workspace\\clt_server(new)\\爱情买卖.wav");
					FileOutputStream fos=new FileOutputStream(file);
					fos.write(methodRequest.binaryAttachment);
					fos.close();*/
				}
				
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return methodRequest;
	}

	public static MethodRequest parseJson(ServletInputStream inputStream) {
		MethodRequest methodRequest = null;
		if (null != inputStream) {
			try {
				BufferedInputStream bis = new BufferedInputStream(inputStream);
				String requestStr = ProtocolUtils.readStrings(bis);
				logger.debug("requestStr=" + requestStr);
				if (StringUtils.isNotEmpty(requestStr)) {
					ObjectMapper mapper = new ObjectMapper();
					methodRequest = mapper.readValue(requestStr, MethodRequest.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return methodRequest;
	}
	
	public String getStringValueFromParsedLine(String key) {
		String result=null;
		if(null!=parsedLines){
			result=parsedLines.get(key);
		}
		return result;
	}

	public Date getDateValueFromParsedLine(String key, String datePattern) {
		Date result=null;
		if(null!=parsedLines){
			try {
				if(parsedLines.containsKey(key)){
					result=new SimpleDateFormat(datePattern).parse(parsedLines.get(key));
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result=null;
			}
		}
		return result;
	}

	public Integer getIntValueFromParsedLine(String key) {
		Integer result=null;
		if(null!=parsedLines){
			try {
				result=Integer.parseInt(parsedLines.get(key));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result=null;
			}
		}
		return result;
	}

}
