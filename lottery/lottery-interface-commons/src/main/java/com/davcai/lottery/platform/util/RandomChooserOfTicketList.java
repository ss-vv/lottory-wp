package com.davcai.lottery.platform.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.xhcms.lottery.commons.data.Ticket;

public class RandomChooserOfTicketList implements IRandomChooser {
	
	private List<Ticket> ticketsToAllocate;

	public RandomChooserOfTicketList(List<Ticket> ticketsToAllocate) {
		this.ticketsToAllocate=ticketsToAllocate;
	}

	@Override
	public List<Ticket> randomChoose(Integer numberToChoose) {
		List<Ticket> result=new ArrayList<Ticket>();
		if(null!=numberToChoose&&numberToChoose>0&&null!=ticketsToAllocate&&ticketsToAllocate.size()>0&&numberToChoose<=ticketsToAllocate.size()){
			Random random=new Random();
			int index=-1;
			for(int i=1;i<=numberToChoose;i++){
				index = random.nextInt(ticketsToAllocate.size());
				result.add(ticketsToAllocate.remove(index));
			}
		}
		return result;
	}

}
