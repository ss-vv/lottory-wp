package com.davcai.lottery.platform.util;

import java.util.List;

import com.xhcms.lottery.commons.data.Ticket;

public interface IRandomChooser {

	List<Ticket> randomChoose(Integer numberToChoose);

}
