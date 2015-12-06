package com.unison.lottery.weibo.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.web.service.ShowBetSchemeService;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;

@Service
public class ShowBetSchemeServiceImpl implements ShowBetSchemeService{
    
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Transactional
	@Override
	public Integer geteShowBetSchemeNum() {
		String begin="";
		String end="";
		Calendar now=Calendar.getInstance();
		int hour=now.get(Calendar.HOUR_OF_DAY);
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 00);
		now.set(Calendar.SECOND, 00);
		if(hour>=9){
			
			begin=formateDate(now.getTime());
			now.add(Calendar.DATE,1);
			end=formateDate(now.getTime());
		}else{
			end=formateDate(now.getTime());
			now.add(Calendar.DATE, -1);
			begin=formateDate(now.getTime());
		}
		return betSchemeDao.getShowBetShemeNum(begin,end);
	}
	private String formateDate(Date d){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);		
	}

}
