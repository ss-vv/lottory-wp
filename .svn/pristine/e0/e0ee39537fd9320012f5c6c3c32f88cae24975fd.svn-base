package com.unison.lottery.weibo.statistic.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.statistic.constant.ExcelConstant;

public class ExcelDocument {
	private Workbook workbook;
	private Map<Integer, Sheet> sheetMap = new HashMap<Integer, Sheet>();
	private Logger logger = LoggerFactory.getLogger(getClass());
	public ExcelDocument(){
		try {
		    InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("daily-data.xls");
		    workbook = WorkbookFactory.create(inputStream);
			sheetMap.put(Integer.valueOf(ExcelConstant.DAILY_REPORT_INDEX), workbook.getSheetAt(
					ExcelConstant.DAILY_REPORT_INDEX));
			sheetMap.put(Integer.valueOf(ExcelConstant.OFFICIAL_USER_DATA_DETAIL_INDEX), workbook.getSheetAt(
					ExcelConstant.OFFICIAL_USER_DATA_DETAIL_INDEX));
		} catch (IOException e) {
			logger.error("创建daily-data.xls错误！",e);
		} catch (InvalidFormatException e) {
			logger.error("创建daily-data.xls错误！",e);
		}
	}
	public Sheet getWritableSheet(Integer key) {
		return sheetMap.get(key);
	}
	public void save() {
	    FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("daily-data.xls");
			workbook.write(fileOut);
		    fileOut.close();
		} catch (FileNotFoundException e) {
			logger.error("保存daily-data.xls错误！",e);
		} catch (IOException e) {
			logger.error("保存daily-data.xls错误！",e);
		}
	}
	public void saveWithDate(long date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormat.format(new Date(date));
		String dirname = "/data/weibo-statistic/" + dateString.substring(0,7) + "/";
		FileOutputStream fileOut;
		try {
			File dir = new File(dirname);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(dirname + "daily-data-"+dateString+".xls");
			if(!file.exists()){
		        file.createNewFile();
			}
			fileOut = new FileOutputStream(file);
			workbook.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			logger.error("保存"+dirname+"错误！",e);
		} catch (IOException e) {
			logger.error("保存"+dirname+"错误！",e);
		}
	}
	public void setSheetName(int index,String newName){
		workbook.setSheetName(index, newName);
	}
}
