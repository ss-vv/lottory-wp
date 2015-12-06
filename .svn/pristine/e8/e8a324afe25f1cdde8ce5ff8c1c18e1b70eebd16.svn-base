package com.xhcms.lottery.admin.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.PrintableFile;
import com.xhcms.lottery.commons.persist.dao.PrintableFileDao;
import com.xhcms.lottery.commons.persist.entity.PrintableFilePO;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class PrintableFileUtil {
	
	private String lineEnding;
	private String encoding="GBK";
	private String datePattern="yyyyMMdd";
	private String dataStoreDirPath;
	private String downloadUrlPre;
	@Autowired
	private PrintableFileDao printableFileDao;
	
	public PrintableFile createFile(List<String> printBetContents, Date now, List<Long> printableTicketIds,String lotteryId,String playId, String lotteryPlatformId) {
		PrintableFile result=null;
		if (null != printBetContents && null != now
				&& !printBetContents.isEmpty() && null != printableTicketIds
				&& !printableTicketIds.isEmpty()) {
			
			String fileName = makeFileName(printableTicketIds, now,lotteryId,playId, lotteryPlatformId);
			String filePath =makeFilePath(fileName);
			String downloadUrl=makeDownloadUrl(fileName);
			if(StringUtils.isNotBlank(fileName)&&StringUtils.isNotBlank(filePath)&&StringUtils.isNotBlank(downloadUrl)){
				File file=new File(filePath);
				
				try {					
					FileUtils.writeLines(file, encoding, printBetContents, lineEnding);
					result=new PrintableFile();
					result.setCreateTime(now);
					result.setFileName(fileName);
					result.setFilePath(filePath);
					result.setFileUrl(downloadUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		return result;
	}


	private String makeDownloadUrl(String fileName) {
		return downloadUrlPre+"/"+fileName;
	}

	private String makeFilePath(String fileName) {
		
		return dataStoreDirPath+File.separator+fileName;
	}

	private String makeFileName(List<Long> printableTicketIds, Date now, String lotteryId, String playId, String lotteryPlatformId) {
//		Collections.sort(printableTicketIds);
//		Long min=printableTicketIds.get(0);
//		Long max=printableTicketIds.get(printableTicketIds.size()-1);
		SimpleDateFormat sdf=new SimpleDateFormat(datePattern);
		String dateTime=sdf.format(now);
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		List<PrintableFilePO> printableFilePOs = printableFileDao.findByDate(lotteryPlatformId,c.getTime());
		return getShiTiDianCNName(lotteryPlatformId)+"_"+lotteryId+"_"+dateTime+"____"+(printableFilePOs.size()+1)+".txt";
	}
	
	private static String getShiTiDianCNName(String lotteryPlatformId){
		switch (lotteryPlatformId) {
		case LotteryPlatformId.CHANGCHUN_SHI_TI_DIAN:
			return "天津站15015";
		case LotteryPlatformId.CHANGCHUN_SHI_TI_DIAN2:
			return "长春站13967";
		case LotteryPlatformId.CHANGCHUN_60005:
			return "长春站71116";
		case LotteryPlatformId.SHANXI_00001:
			return "山西11112";
		case LotteryPlatformId.CHANGCHUN_02857:
			return "长春站13968";
		case LotteryPlatformId.KUAI_SU_CHU_PIAO:
			return "快速出票";
		default:
			return "";
		}
	}
	
	public String getLineEnding() {
		return lineEnding;
	}

	public void setLineEnding(String lineEnding) {
		this.lineEnding = lineEnding;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}


	public String getDataStoreDirPath() {
		return dataStoreDirPath;
	}


	public void setDataStoreDirPath(String dataStoreDirPath) {
		this.dataStoreDirPath = dataStoreDirPath;
	}


	public String getDownloadUrlPre() {
		return downloadUrlPre;
	}


	public void setDownloadUrlPre(String downloadUrlPre) {
		this.downloadUrlPre = downloadUrlPre;
	}

}
