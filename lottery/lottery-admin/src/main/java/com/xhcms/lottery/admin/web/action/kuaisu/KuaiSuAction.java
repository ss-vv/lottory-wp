package com.xhcms.lottery.admin.web.action.kuaisu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.yuanchengchupiao.model.PayoutOrIncomeEntry;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.QueryGetInfoListResponse4OneLoop;
import com.opensymphony.xwork2.ActionContext;
import com.xhcms.lottery.admin.persist.service.QueryBalanceService;
import com.xhcms.lottery.admin.persist.service.QueryGetInfoListService;
import com.xhcms.lottery.admin.persist.service.QueryPrizeAmountService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.admin.web.action.BaseListAction;

public class KuaiSuAction extends BaseListAction{
	@Autowired
	private QueryGetInfoListService queryGetInfoListService;
	QueryGetInfoListResponse4OneLoop queryGetInfoListResponse4OneLoop=null;
	
	@Autowired
	private QueryBalanceService queryBalanceService;
	
	Logger log = LoggerFactory.getLogger(getClass());
	private String Page;
	private List<PayoutOrIncomeEntry> result;
	private String state;
	private String balance;
	
	private String returnfrom=null;
	private String returnto=null;
	private String returnstate=null;
	
	public QueryGetInfoListService getQueryGetInfoListService() {
		return queryGetInfoListService;
	}
	public void setQueryGetInfoListService(
			QueryGetInfoListService queryGetInfoListService) {
		this.queryGetInfoListService = queryGetInfoListService;
	}

	public Date StartDateCast(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        String result=df.format(date);  
	         result+=" "+"00:00:00";
	         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
	         try {
				return sdf.parse(result);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	         return new Date();
	}
	public Date StopDateCast(Date date){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        String result=df.format(date);  
	         result+=" "+"23:59:59";
	         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
	         try {
				return sdf.parse(result);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	         return new Date();
	}
	
	public String DateToString(Date date){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	protected void putSession(String key, Object value) {
		ActionContext ctx = ActionContext.getContext();
		ctx.getSession().put(key, value);
	}
	
	protected Object getSession(String key) {
		ActionContext ctx = ActionContext.getContext();
		return ctx.getSession().get(key);
	}
	
	
	public String execute() throws Exception {
		balance=queryBalanceService.queryBalanceResponse4OneLoop().getBalance();
		float bal =Float.valueOf(balance)/100f;
		balance=String.valueOf(bal);
		if(null == from)
			return SUCCESS;
		try {
			  System.out.println(from);
			  System.out.println(to);
			  returnstate=state;
			  int fromLong = (int) (StartDateCast(new Date()).getTime()/1000);
			  if(null != from){
				  returnfrom=DateToString(from);
			      fromLong = (int) (from.getTime() / 1000);
			  }
			  int toLong = (int) (StopDateCast(new Date()).getTime() / 1000);
			  if(null != to){
				  returnto=DateToString(to);
				  toLong =   (int) (to.getTime() / 1000);
			  }
			  
			  setMaxResults(100);
			  paging = wrapPaging();
			  paging.setTotalCount(100000);
			 
			  Page = paging.getPageNo()+"";
			  
			 int truePage =((Integer.valueOf(Page)-1)/50)+1;
			  if(null == getSession(fromLong+"#"+toLong) )
			  {
				  queryGetInfoListResponse4OneLoop= queryGetInfoListService.queryGetInfoListResponse4OneLoop(String.valueOf(truePage),String.valueOf(fromLong),String.valueOf(toLong));
				  putSession(fromLong+"#"+toLong, queryGetInfoListResponse4OneLoop);
			  }
			  else
			  {
				  queryGetInfoListResponse4OneLoop = (QueryGetInfoListResponse4OneLoop) getSession(fromLong+"#"+toLong);
			  }
			  System.out.println(queryGetInfoListResponse4OneLoop);
			  List<PayoutOrIncomeEntry> list =new ArrayList<PayoutOrIncomeEntry>();
			  if(state.equals("1"))
			  {
				  for(int i=(Integer.valueOf(Page)-1)*20;i<(Integer.valueOf(Page)-1)*20+20;i++)
				  {
					  list.add(queryGetInfoListResponse4OneLoop.getIncome().get(i));
				  }
				 this.setResult(list);
			  }
			      
			  else if(state.equals("-1"))
			  {
				  for(int i=(Integer.valueOf(Page)-1)*20;i<(Integer.valueOf(Page)-1)*20+20;i++)
				  {
					  list.add(queryGetInfoListResponse4OneLoop.getPayout().get(i));
				  }
				 this.setResult(list);
			  }
			      
			  System.out.println("result="+result);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;	
	}
	public String queryAmount(){
		return SUCCESS;
	}
	public String getPage() {
		return Page;
	}
	public void setPage(String page) {
		this.Page = page;
	}
	public List<PayoutOrIncomeEntry> getResult() {
		return result;
	}
	public void setResult(List<PayoutOrIncomeEntry> result) {
		this.result = result;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getReturnfrom() {
		return returnfrom;
	}
	public void setReturnfrom(String returnfrom) {
		this.returnfrom = returnfrom;
	}
	public String getReturnto() {
		return returnto;
	}
	public void setReturnto(String returnto) {
		this.returnto = returnto;
	}
	public String getReturnstate() {
		return returnstate;
	}
	public void setReturnstate(String returnstate) {
		this.returnstate = returnstate;
	}
	public QueryBalanceService getQueryBalanceService() {
		return queryBalanceService;
	}
	public void setQueryBalanceService(QueryBalanceService queryBalanceService) {
		this.queryBalanceService = queryBalanceService;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
}
