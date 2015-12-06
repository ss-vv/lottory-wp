package com.davcai.lottery.platform.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.davcai.lottery.platform.util.exception.IllegalPriorityListException;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.davcai.lottery.platform.util.model.OnePriorityAndRemainPriorityList;
import com.davcai.lottery.platform.util.model.PriorityListAndTicketsToAllocate;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.PlayType;

public class AllocatorUtil {

	private static final String ONLY_TO_ZUN_AO = "onlyToZunAo";

	/**
	 *  计算应该给每个彩票平台的票数，使用递归算法
	 * @param priorityList
	 * @param totalTicketNumber
	 * @return
	 */
	public static Map<String, Integer> computeShouldArrangeNumber(
			List<LotteryPlatformPriority> priorityList, int totalTicketNumber) {
		Map<String, Integer> result=null;
		
		if(null==priorityList||priorityList.isEmpty()){
			return null;
		}
		if(priorityListHaveRepeatedItem(priorityList)){
			throw new IllegalPriorityListException("权重列表中含有重复项");
		}
		List<LotteryPlatformPriority> copyOfPriorityList=new ArrayList<LotteryPlatformPriority>();
		for(LotteryPlatformPriority priority:priorityList){
			copyOfPriorityList.add(priority);
		}
		result=new HashMap<String, Integer>();
		if(totalTicketNumber>0){
			
			int numberShouldArrange=0;
			double totalPriority = computeTotalPriority(copyOfPriorityList);
			//获取某一项及剩余项列表
			OnePriorityAndRemainPriorityList onePriorityAndRemainPriorityList=makeOnePriorityAndRemainPriorityList(copyOfPriorityList);
			
			if (null != onePriorityAndRemainPriorityList) {
				//取一项，分配
				numberShouldArrange = arrangeOnePriority(totalTicketNumber,
						result, totalPriority, onePriorityAndRemainPriorityList);
				// 剩下的项，分配
				arrangeRemainPriorityList(totalTicketNumber, result,
						numberShouldArrange, onePriorityAndRemainPriorityList);
			}
		}
		else{
			for(LotteryPlatformPriority priority:copyOfPriorityList){
				if(null!=priority.getLotteryPlatformId()){
					result.put(priority.getLotteryPlatformId(), 0);
				}
				
			}
		}
		return result;
	}

	/**
	 * 检查
	 * @param priorityList
	 * @return
	 */
	private static boolean priorityListHaveRepeatedItem(
			List<LotteryPlatformPriority> priorityList) {
		boolean result =false;
		String lotteryPlatformId=priorityList.get(0).getLotteryPlatformId();
		if(priorityList.size()>1){
			List<LotteryPlatformPriority> subList = priorityList.subList(1, priorityList.size());
			for(LotteryPlatformPriority priorityItem:subList){
				if(StringUtils.equals(lotteryPlatformId, priorityItem.getLotteryPlatformId())){
					result=true;
					break;
				}
			}
		}
		
		return result;
	}

	private static void arrangeRemainPriorityList(int totalTicketNumber,
			Map<String, Integer> result, int numberShouldArrange,
			OnePriorityAndRemainPriorityList onePriorityAndRemainPriorityList) {
		Map<String, Integer> shouldArrangeNumberMap = computeShouldArrangeNumber(
				onePriorityAndRemainPriorityList.getSubPriorityList(),
				totalTicketNumber - numberShouldArrange);
		if(null!=shouldArrangeNumberMap){
			result.putAll(shouldArrangeNumberMap);
		}
		
	}

	private static int arrangeOnePriority(int totalTicketNumber,
			Map<String, Integer> result, double totalPriority,
			OnePriorityAndRemainPriorityList onePriorityAndRemainPriorityList) {
		int numberShouldArrange;
		double initArrangeNumber = (totalTicketNumber * onePriorityAndRemainPriorityList
				.getOnePriority().getPriority()) / totalPriority;
		numberShouldArrange = new BigDecimal(initArrangeNumber)
				.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		result.put(onePriorityAndRemainPriorityList.getOnePriority()
				.getLotteryPlatformId(), numberShouldArrange);
		return numberShouldArrange;
	}

	private static OnePriorityAndRemainPriorityList makeOnePriorityAndRemainPriorityList(
			List<LotteryPlatformPriority> priorityList) {
		//如果权重相等，则随机取一项；如果权重不相等，则取权重最大的一项
		LotteryPlatformPriority priority=null;
		List<LotteryPlatformPriority> subPriorityList=null;
		if(priorityIsNotSame(priorityList)){
			//将priorityList按照权重从大到小排序
			Comparator<? super LotteryPlatformPriority> c=new Comparator<LotteryPlatformPriority>(){

				@Override
				public int compare(LotteryPlatformPriority o1,
						LotteryPlatformPriority o2) {
					return o2.getPriority()-o1.getPriority();
				}
				
			};
			Collections.sort(priorityList, c);
			priority = priorityList.get(0);
			if(priorityList.size()>1){
				subPriorityList = priorityList.subList(1, priorityList.size());
			}
			else{
				subPriorityList=null;
			}
			
		}
		else{
			int size=priorityList.size();
			Random random=new Random();
			int index=random.nextInt(size);
			priority = priorityList.remove(index);
			if(priorityList.size()>=1){
				subPriorityList=priorityList;
			}
			else{
				subPriorityList=null;
			}
		}
		OnePriorityAndRemainPriorityList onePriorityAndRemainPriorityList=new OnePriorityAndRemainPriorityList();
		onePriorityAndRemainPriorityList.setOnePriority(priority);
		onePriorityAndRemainPriorityList.setSubPriorityList(subPriorityList);
		return onePriorityAndRemainPriorityList;
	}

	private static boolean priorityIsNotSame(
			List<LotteryPlatformPriority> priorityList) {
		boolean result=false;
		int priority=priorityList.get(0).getPriority();
		for(LotteryPlatformPriority priorityItem:priorityList){
			if(priorityItem.getPriority()!=priority){
				result=true;
				break;
			}
		}
		return result;
	}

	private static double computeTotalPriority(
			List<LotteryPlatformPriority> priorityList) {
		double totalPriority=0;
		for(LotteryPlatformPriority priority:priorityList){
			totalPriority=totalPriority+priority.getPriority();
		}
		return totalPriority;
	}

	public static Map<String, List<Ticket>> arrangeTicketsToMap(
			List<Ticket> ticketsToAllocate,
			Map<String, Integer> shouldArrangeToPriorityList) {
		Map<String, List<Ticket>> result=new HashMap<String, List<Ticket>>();
		if(null==ticketsToAllocate||ticketsToAllocate.isEmpty()||null==shouldArrangeToPriorityList||shouldArrangeToPriorityList.isEmpty()){
			return null;
		}
		IRandomChooser randomChooser=new RandomChooserOfTicketList(ticketsToAllocate);
		for(Entry<String, Integer> entry:shouldArrangeToPriorityList.entrySet()){
			List<Ticket> tickets=randomChooser.randomChoose(entry.getValue());
			if(null!=tickets&&!tickets.isEmpty()){
				for(Ticket ticket:tickets){
					ticket.setLotteryPlatformId(entry.getKey());
				}
				result.put(entry.getKey(), tickets);
			}
		}
		return result;
	}

	/**
	 * @param ticketsToAllocate
	 * @return
	 */
	public static List<Ticket> extractZunAoTickets(
			List<Ticket> ticketsToAllocate) {
		if(null==ticketsToAllocate||ticketsToAllocate.isEmpty()){
			return null;
		}
		List<Ticket> result=new ArrayList<Ticket>();
		Iterator<Ticket> it = ticketsToAllocate.iterator();
		Ticket ticket=null;
		while(it.hasNext()){
			ticket=it.next();
			if(null==ticket.getPlatformBetCodeMap()||ticket.getPlatformBetCodeMap().isEmpty()){
				ticket.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
				result.add(ticket);
				it.remove();
			}
		}
		return result;
	}

	/**
	 * 对一些特殊的票进行处理：
	 * 1、将ticket中platformBetCode为空的挑拣出来，不参与按权重分配，如果不是竞彩足球、竞彩篮球和传统足彩，指定给尊奥,因为这些票都是在多平台上线前生成的，只能由尊奥出票；
	 * 2、如果lotteryPlatformId不为空,则按权重分配时，需要把该lotteryPlatformId对应的LotteryPlatformPriority排除，再分配
	 * @param ticketsToAllocate 所有票都是同一个彩种
	 * @param priorityList
	 * @return
	 */
	public static Map<String, PriorityListAndTicketsToAllocate> mapSpecialTickets(
			List<Ticket> ticketsToAllocate,
			List<LotteryPlatformPriority> priorityList,boolean shouldArrangeBigTicket,Integer bigTicketMoneyThreshold) {
		Map<String, PriorityListAndTicketsToAllocate> result=null;
		if(haveTicketToPrecess(ticketsToAllocate, priorityList)){
			LotteryId lotteryId=ticketsToAllocate.get(0).getLotteryId();
			result=new HashMap<String, PriorityListAndTicketsToAllocate>();
			Iterator<Ticket> it = ticketsToAllocate.iterator();
			Ticket ticket=null;
			List<Ticket> bigTicketsToAllocate=new ArrayList<Ticket>();
			List<LotteryPlatformPriority> bigTicketPriorityList=new ArrayList<LotteryPlatformPriority>();
			if(shouldArrangeBigTicket(shouldArrangeBigTicket,
					bigTicketMoneyThreshold)){//检查是否需要按照阙值分票
				initBigTicketPriorityList(priorityList,bigTicketPriorityList);
			}
			while(it.hasNext()){
				ticket=it.next();
				if(null==ticket.getPlatformBetCodeMap()||ticket.getPlatformBetCodeMap().isEmpty()){//platformBetCode为空
					if (shouldArrangeToZunAo(lotteryId)) {// 如果不是竞彩足球、竞彩篮球和传统足彩，指定给尊奥
						arrangeToZunAO(result, it, ticket);
					}
					
				}
//				else if(StringUtils.isNotBlank(ticket.getLotteryPlatformId())){//lotteryPlatformId不为空,使用其他的出票平台按权重分配
//					arrangeToOther(priorityList, result, it, ticket);
//					
//				}
				else{
					tryArrangeBigTicket(bigTicketMoneyThreshold, it, ticket,
							bigTicketsToAllocate, shouldArrangeBigTicket,bigTicketPriorityList);
					
				}
			}
			if(haveTicketToPrecess(bigTicketsToAllocate, bigTicketPriorityList)){
				result = prepareResult4BigTicket(bigTicketsToAllocate,
						bigTicketPriorityList);
			}
		}
		return result; 
	}

	private static Map<String, PriorityListAndTicketsToAllocate> prepareResult4BigTicket(
			List<Ticket> bigTicketsToAllocate,
			List<LotteryPlatformPriority> bigTicketPriorityList) {
		Map<String, PriorityListAndTicketsToAllocate> result;
		result=new HashMap<String, PriorityListAndTicketsToAllocate>();
		PriorityListAndTicketsToAllocate bigTicketAndPriorityListToAllocate=new PriorityListAndTicketsToAllocate();
		bigTicketAndPriorityListToAllocate.setTicketsToAllocate(bigTicketsToAllocate);
		bigTicketAndPriorityListToAllocate.setPriorityList(bigTicketPriorityList);
		result.put("big_ticket_to_allocate", bigTicketAndPriorityListToAllocate);
		return result;
	}

	private static boolean haveTicketToPrecess(List<Ticket> bigTicketsToAllocate,
			List<LotteryPlatformPriority> bigTicketPriorityList) {
		return null!=bigTicketsToAllocate&&!bigTicketsToAllocate.isEmpty()&&null!=bigTicketPriorityList&&!bigTicketPriorityList.isEmpty();
	}

	private static void tryArrangeBigTicket(Integer bigTicketMoneyThreshold,
			Iterator<Ticket> it, Ticket ticket,
			List<Ticket> bigTicketsToAllocate, boolean shouldArrangeBigTicket, List<LotteryPlatformPriority> bigTicketPriorityList) {
		if (shouldArrangeBigTicket
				&& ticket.getMoney() >= bigTicketMoneyThreshold
				&& (ticket.getLotteryId().equals(LotteryId.JCLQ) || ticket
						.getLotteryId().equals(LotteryId.JCZQ))
				&&null!=bigTicketPriorityList&&!bigTicketPriorityList.isEmpty()) {// 投注额大于等于阙值,并且是竞彩足球或者竞彩篮球，而且打开了按金额阙值分票的开关,给大额票的权重列表非空
			bigTicketsToAllocate.add(ticket);
			it.remove();
		}
	}

	private static void initBigTicketPriorityList(
			List<LotteryPlatformPriority> priorityList, List<LotteryPlatformPriority> bigTicketPriorityList) {
		for(LotteryPlatformPriority priority:priorityList){
			if(priority.getPriorityOfBigTicket()>0){
				LotteryPlatformPriority newPriority=new LotteryPlatformPriority();
				try {
					BeanUtils.copyProperties(newPriority, priority);
					newPriority.setPriority(newPriority.getPriorityOfBigTicket());//使用给大额票的分配比例
					bigTicketPriorityList.add(newPriority);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}
	}

	private static boolean shouldArrangeBigTicket(
			boolean shouldArrangeBigTicket, Integer bigTicketMoneyThreshold) {
		return shouldArrangeBigTicket&&null!=bigTicketMoneyThreshold&&bigTicketMoneyThreshold>0;
	}


	private static void arrangeToOther(
			List<LotteryPlatformPriority> priorityList,
			Map<String, PriorityListAndTicketsToAllocate> result,
			Iterator<Ticket> it, Ticket ticket) {
		if(result.containsKey(ticket.getLotteryPlatformId())){
			result.get(ticket.getLotteryPlatformId()).getTicketsToAllocate().add(ticket);
		}
		else{
			PriorityListAndTicketsToAllocate otherPriorityListAndTicketsToAllocate=new PriorityListAndTicketsToAllocate();
			List<Ticket> ticketsToAllocateToOther=new ArrayList<Ticket>();
			ticketsToAllocateToOther.add(ticket);
			otherPriorityListAndTicketsToAllocate.setTicketsToAllocate(ticketsToAllocateToOther);
			List<LotteryPlatformPriority> otherPriorityList=new ArrayList<LotteryPlatformPriority>();
			
			for(LotteryPlatformPriority priority:priorityList){
				if(null!=priority&&!StringUtils.equals(priority.getLotteryPlatformId(), ticket.getLotteryPlatformId())){
					otherPriorityList.add(priority);
				}
			}
			if(null!=otherPriorityList&&!otherPriorityList.isEmpty()){//可能平台列表中没有除票的平台id指定的之外的平台，所以要判断一下,只有在有其他平台的情况下，才加入map
				otherPriorityListAndTicketsToAllocate.setPriorityList(otherPriorityList);
				result.put(ticket.getLotteryPlatformId(), otherPriorityListAndTicketsToAllocate);
			}
			
		}
		if(result.containsKey(ticket.getLotteryPlatformId())){
			it.remove();
		}
	}

	private static boolean shouldArrangeToZunAo(LotteryId lotteryId) {
		return lotteryId != LotteryId.CTZC
				&& lotteryId != LotteryId.JCLQ
				&& lotteryId != LotteryId.JCZQ;
	}

	private static void arrangeToZunAO(
			Map<String, PriorityListAndTicketsToAllocate> result,
			Iterator<Ticket> it, Ticket ticket) {
		ticket.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);

		if (result.containsKey(ONLY_TO_ZUN_AO)) {
			result.get(ONLY_TO_ZUN_AO).getTicketsToAllocate()
					.add(ticket);
		} else {
			PriorityListAndTicketsToAllocate onlyZunAoPriorityListAndTicketsToAllocate=new PriorityListAndTicketsToAllocate();
			List<Ticket> ticketsToAllocateToZunAo=new ArrayList<Ticket>();
			ticketsToAllocateToZunAo.add(ticket);
			onlyZunAoPriorityListAndTicketsToAllocate.setTicketsToAllocate(ticketsToAllocateToZunAo);
			List<LotteryPlatformPriority> onlyZunAoPriorityList=new ArrayList<LotteryPlatformPriority>();
			
			LotteryPlatformPriority zunAoPriority=new LotteryPlatformPriority();
			zunAoPriority.setInterfaceName(LotteryInterfaceName.orderTicket.toString());
			zunAoPriority.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
			PlayType playType=PlayType.valueOfLcPlayId(ticket.getPlayId());
			if(null!=playType){
				
				zunAoPriority.setLotteryId(playType.getLotteryId().toString());
			}
			
			zunAoPriority.setPriority(1);
			onlyZunAoPriorityList.add(zunAoPriority);
			
			onlyZunAoPriorityListAndTicketsToAllocate.setPriorityList(onlyZunAoPriorityList);
			result.put(ONLY_TO_ZUN_AO, onlyZunAoPriorityListAndTicketsToAllocate);
		}
		
		it.remove();
	}

}
