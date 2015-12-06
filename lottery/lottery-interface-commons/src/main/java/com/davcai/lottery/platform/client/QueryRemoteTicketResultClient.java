package com.davcai.lottery.platform.client;

import java.util.List;

import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;

public interface QueryRemoteTicketResultClient {

	YuanChengChuPiaoOrderTicketResultResponse queryTicketResult(List<String> remoteTickets);
}
