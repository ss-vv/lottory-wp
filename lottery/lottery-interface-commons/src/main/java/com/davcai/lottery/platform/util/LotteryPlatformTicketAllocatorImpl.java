package com.davcai.lottery.platform.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.davcai.lottery.platform.util.model.PriorityListAndTicketsToAllocate;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.conf.SystemConfImpl;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;
@Component
public class LotteryPlatformTicketAllocatorImpl implements
		ILotteryPlatformAllocator<Ticket> {
	
	@Autowired
	private SystemConf systemConf;
	
	 protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ILotteryPlatformPriorityService lotteryPlatformPriorityService;
	/**将票列表按权重分配到各出票平台
	 * 注意：如果票的platformBetCode为空，则不参与按权重分配，而是指定给尊奥,因为这些票都是在多平台上线前生成的，只能由尊奥出票
	 */
	@Override
	public Map<String, List<Ticket>> allocateByPriority(List<Ticket> tickets) {
		if(null==tickets||tickets.isEmpty()){
			return null;
		}
		// 先按lotteryId分组
		Map<LotteryId,List<Ticket>> ticketsGroupByLotteryId=groupByLotteryId(tickets);
		//列表的每一项是一个map，map的key是彩票平台id，value是分配给该彩票平台的票列表
		List<Map<String, List<Ticket>>> readyToReturnList=new ArrayList<Map<String, List<Ticket>>>();
		if(null!=ticketsGroupByLotteryId&&!ticketsGroupByLotteryId.isEmpty()){
			
			for(LotteryId lotteryId:ticketsGroupByLotteryId.keySet()){
				
				
				
				/**********************************************************************************************/
				//远程出票限制
				yuanChengChuPiaoLimit(ticketsGroupByLotteryId, readyToReturnList);
				/**********************************************************************************************/
				
				
				//根据接口名和彩种id，列出对应的参加权重分配的彩票平台
				List<LotteryPlatformPriority> priorityList = lotteryPlatformPriorityService.loadAll(LotteryInterfaceName.orderTicket, lotteryId);
				//找到对应彩种id的待分配ticket列表
				List<Ticket> ticketsToAllocate=ticketsGroupByLotteryId.get(lotteryId);
				//将ticket中platformBetCode为空的挑拣出来，不参与按权重分配，而是指定给尊奥,因为这些票都是在多平台上线前生成的，只能由尊奥出票；
//				extractToZunAoTickets(readyToReturnList, ticketsToAllocate);
				//对特殊的票进行处理
				Map<String,PriorityListAndTicketsToAllocate> map=processSpecialTickets(ticketsToAllocate,priorityList);
				Map<String, List<Ticket>> ticketsAllocatedByLotteryId=null;
				if(null!=map&&!map.isEmpty()){
					for( PriorityListAndTicketsToAllocate priorityListAndTicketsToAllocate:map.values()){
						if(null!=priorityListAndTicketsToAllocate){
							ticketsAllocatedByLotteryId=allocateByPriority(priorityListAndTicketsToAllocate.getPriorityList(),priorityListAndTicketsToAllocate.getTicketsToAllocate());
							readyToReturnList.add(ticketsAllocatedByLotteryId);
						}
					}
				}
				//将剩余的票按权重分配到所有可用的彩票平台
				ticketsAllocatedByLotteryId=allocateByPriority(priorityList,ticketsToAllocate);
				
				readyToReturnList.add(ticketsAllocatedByLotteryId);
			}
		}
		
		return makeResultFromReadyToReturnList(readyToReturnList);
	}

	/**
	 * 远程出票限制
	 * @param ticketsGroupByLotteryId
	 * @param readyToReturnList
	 */
	private void yuanChengChuPiaoLimit(Map<LotteryId, List<Ticket>> ticketsGroupByLotteryId,
			List<Map<String, List<Ticket>>> readyToReturnList) {
		// 远程的票 分配限制
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH)+1;
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		int today = rightNow.get(Calendar.DAY_OF_MONTH);
		int startTime = systemConf.getIntegerValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_START_TIME);
		int stopTime = systemConf.getIntegerValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_STOP_TIME);						
		// 2015724OK  当天分配达到限制时 保存            年月日OK   当天是否给 远程分票的标志位
		String ToDay = systemConf.findValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_FLAG);
		String NowDate = year+""+month+""+today+"OK";
		Map<String, List<Ticket>> yuanchengTickets = new HashMap<>();
		List<Ticket> yuanchengTicket = new ArrayList<>();
		if(hour >= startTime  && hour <= stopTime && !NowDate.equals(ToDay))//满足时间时给远程出票分配票
		{
			List<Ticket> ticketJCZQ = ticketsGroupByLotteryId.get(LotteryId.JCZQ);
			for(Ticket t : ticketJCZQ) //挑选符合条件的票分给远程出票
			{
				int totalMoney =systemConf.getIntegerValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_TOTAL_MONEY);
				int maxBonus = systemConf.getIntegerValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_MAXBONUS);
				long currentMoney = Long.parseLong(systemConf.findValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_CURRENT_MONEY)); 
				
				if(t.getMoney()+currentMoney < totalMoney && t.getExpectBonus().intValue() < maxBonus)// 最大金额限制，最大理论奖金限制
				{
					yuanchengTicket.add(t);
					logger.debug("给远程出票平台分配了一张票{}", t);
					//更新totalMoney
					systemConf.updateLongValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_CURRENT_MONEY, currentMoney + t.getMoney()+"");
				}
				else
				{
					if(currentMoney >= totalMoney) //关闭
					{
						//达到分配极限 当天不再分配 设置标志OK
						systemConf.updateStrinfValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_FLAG,year+""+month+""+today+"OK");
						//清零累计记录
						systemConf.updateLongValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_CURRENT_MONEY, 0+"");
					}
					
				}
			}

			ticketJCZQ.removeAll(yuanchengTicket);//移除给远程出票的票
			ticketsGroupByLotteryId.put(LotteryId.JCZQ, ticketJCZQ);//放回剩下的票
			
			// 平台ID：远程出票  ，   符合条件的票
			yuanchengTickets.put(LotteryPlatformId.YUAN_CHENG_CHU_PIAO, yuanchengTicket);//给远程出票的票
			//加入准备好的列表 分配完成
			readyToReturnList.add(yuanchengTickets);
		}
		else
		{
			if(hour >= stopTime)
			{
				//达到 晚上10点当天不再分配 设置标志OK
				systemConf.updateStrinfValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_FLAG,year+""+month+""+today+"OK");
				//清零累计记录
				systemConf.updateLongValueByKey(SystemConfImpl.YUANCHENGCHUPIAO_CURRENT_MONEY, 0+"");
			}
		}
	}

	/**
	 * 对一些特殊的票进行处理：
	 * 1、将ticket中platformBetCode为空的挑拣出来，不参与按权重分配，而是指定给尊奥,因为这些票都是在多平台上线前生成的，只能由尊奥出票；
	 * 2、如果lotteryPlatformId不为空,则按权重分配时，需要把该lotteryPlatformId对应的LotteryPlatformPriority排除，再分配;
	 * 3、对于投注额大于阙值的票，按新的投注比例进行分配
	 * @param ticketsToAllocate,票列表，所有票都是同一个彩种
	 * @param priorityList
	 * @return
	 */
	private Map<String, PriorityListAndTicketsToAllocate> processSpecialTickets(
			List<Ticket> ticketsToAllocate,
			List<LotteryPlatformPriority> priorityList) {
	
		return AllocatorUtil.mapSpecialTickets(ticketsToAllocate,priorityList,systemConf.shouldArrangeBigTicket(),systemConf.getBigTicketMoneyThreshold());
	}

	private void extractToZunAoTickets(
			List<Map<String, List<Ticket>>> readyToReturnList,
			List<Ticket> ticketsToAllocate) {
		List<Ticket> ticketsToZunAo=filte(ticketsToAllocate);
		if(null!=ticketsToZunAo&&!ticketsToZunAo.isEmpty()){
			Map<String, List<Ticket>> ticketsToZunAoMap=new HashMap<String, List<Ticket>>();
			ticketsToZunAoMap.put(LotteryPlatformId.ZUN_AO, ticketsToZunAo);
			readyToReturnList.add(ticketsToZunAoMap);
		}
	}
	
	private List<Ticket> filte(List<Ticket> ticketsToAllocate) {
		return AllocatorUtil.extractZunAoTickets(ticketsToAllocate);
	}
	/**
	 * 将各彩种id分配的结果整合起来
	 * @param readyToReturnList
	 * @return
	 */
	private Map<String, List<Ticket>> makeResultFromReadyToReturnList(
			List<Map<String, List<Ticket>>> readyToReturnList) {
		Map<String, List<Ticket>> result=new HashMap<String, List<Ticket>>();
		if(null!=readyToReturnList&&!readyToReturnList.isEmpty()){
			for(Map<String, List<Ticket>> readyToReturn:readyToReturnList){
				if(null!=readyToReturn&&!readyToReturn.isEmpty()){
					for(Entry<String, List<Ticket>> entry:readyToReturn.entrySet()){
						if(null!=entry){
							if(result.containsKey(entry.getKey())){
								result.get(entry.getKey()).addAll(entry.getValue());
							}
							else{
								result.put(entry.getKey(), entry.getValue());
							}
						}
						
					}
				}
				
			}
		}
		return result;
	}
	/**
	 * 根据权重分给各个彩票平台
	 * @param priorityList
	 * @param ticketsToAllocate
	 * @return
	 */
	private Map<String, List<Ticket>> allocateByPriority(
			List<LotteryPlatformPriority> priorityList,
			List<Ticket> ticketsToAllocate) {
		List<LotteryPlatformPriority> newPriorityList=new ArrayList<LotteryPlatformPriority>();
		for(LotteryPlatformPriority priority:priorityList){
			if(priority.getPriority()>0){
				newPriorityList.add(priority);
			}
		}
		
		Map<String,Integer> shouldArrangeToPriorityList=AllocatorUtil.computeShouldArrangeNumber(newPriorityList,ticketsToAllocate.size());
		
		return arrangeToMap(ticketsToAllocate,shouldArrangeToPriorityList);
	}
	
	private Map<String, List<Ticket>> arrangeToMap(
			List<Ticket> ticketsToAllocate,
			Map<String, Integer> shouldArrangeToPriorityList) {
		return AllocatorUtil.arrangeTicketsToMap(ticketsToAllocate,shouldArrangeToPriorityList);
	}
	private Map<LotteryId, List<Ticket>> groupByLotteryId(List<Ticket> tickets) {
		
		return TicketGroupUtil.groupByLotteryId(tickets);
	}
	public ILotteryPlatformPriorityService getLotteryPlatformPriorityService() {
		return lotteryPlatformPriorityService;
	}
	public void setLotteryPlatformPriorityService(
			ILotteryPlatformPriorityService lotteryPlatformPriorityService) {
		this.lotteryPlatformPriorityService = lotteryPlatformPriorityService;
	}

}
