package com.unison.lottery.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import freemarker.template.utility.StringUtil;

/**
 * Title:连接servlet，发送请求包，获得返回包 Description: Copyright: Copyright (c) 2005
 * Company: XGLL
 * 
 * @author xfy
 * @version 1.0
 */
public class TestActiveityCallBack {
	public static final String SERVLET_URL = "http://58.83.235.156/bill_platform_server/syncOrderLz"; // servlet的url
	public static final String url = "http://localhost:8082/lottery-api/syncActivityOrNotice";
	private HttpURLConnection conn = null; // servlet连接对象

	String resultinfo = "";
	int resultcode = -1;

	int connCount = 5; // 如果连接不上最多连接5次

	/**
	 * 构造函数
	 */
	public TestActiveityCallBack(String url) {
		int num = connCount;
		// 此处循环connCount次，如果连接失败，可以多连接connCount次
		while (num != 0) {
			if (conn == null) {
				conn = createHttpUrlConn(url);
			} else {
				break;
			}
			num--;
		}
	}

	/**
	 * 判断连接是否取得成功
	 * 
	 * @return boolean 成功返回true，失败返回false
	 */
	public boolean isGetUrlConn() {
		return (conn == null) ? false : true;
	}

	/**
	 * 
	 * @param url
	 *            String
	 * @return HttpURLConnection
	 */

	/**
	 * 返回发送的url连接对象
	 * 
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	private HttpURLConnection createHttpUrlConn(String url) {
		try {
			return (HttpURLConnection) new URL(url).openConnection();
		} catch (IOException ex) {
			resultinfo = "取得serlvet连接对象失败！";
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置conn对象
	 * 
	 * @throws ProtocolException
	 */
	private void setHttpUrlConnConfig() throws ProtocolException {
		conn.addRequestProperty("content-type", "text/xml;charset=UTF-8");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
	}

	/**
	 * 发送xml信息
	 * 
	 * @param value
	 *            String,要发送的数据
	 * @return boolean
	 */
	private void writeInfo(String value) throws IOException {
		// System.out.println(value);
		// 发送xml字符串
		OutputStream outStream = conn.getOutputStream();
		// xp.output(outStream);
		PrintWriter out = new PrintWriter(outStream);
		out.print(value);
		out.flush();
		out.close();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String line;

		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		reader.close();
		conn.disconnect();
	}

	/**
	 * 销毁对象
	 */
	public void dispose() {
		if (conn != null) {
			conn.disconnect();
		}
		conn = null;
	}

	// get/set方法
	public HttpURLConnection getConn() {
		return conn;
	}

	public void setConn(HttpURLConnection conn) {
		this.conn = conn;
	}

	public static void main(String args[]) {
		String url = "http://localhost:8082/lottery-api/syncLiveScores";
		StringBuffer sBuffer = new StringBuffer(url).append("?");
		sBuffer.append("type=3&");
		sBuffer.append("matchId=201501201006");
		TestActiveityCallBack syncOrderTest = new TestActiveityCallBack(sBuffer.toString());
		
		try {
			syncOrderTest.setHttpUrlConnConfig();
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}
//		try {
//			syncOrderTest.writeInfo(sBuffer.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}

