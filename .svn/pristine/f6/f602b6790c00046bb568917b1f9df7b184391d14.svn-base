package com.xhcms.lottery.pb.util;

import java.util.ArrayList;
import java.util.List;

public class SSQGenerateUtil {
	// 用来保存产生的每注双色球号码
	private List<int[]> ballList;
	// 保存一注号码的数组
	private int[] ball;
	// 红色球号码总和的开始区域
	private int START;
	// 红色球号码总和的结束区域
	private int END;

	/**
	 * 构造方法，注数，默认1注
	 */
	public SSQGenerateUtil() {
		int number = 1; //默认1注
		this.init();
		// 完成ballList的初始化
		ballList = new ArrayList<int[]>();
		for (int i = 0; i < number; i++) {
			// 初始化ball
			ball = new int[7];
			// 产生一注号码
			ball = this.createBall(ball);
			while (true) {
				int count = 0;
				// 将所有的号码去掉最后一个号码后相加,用来验证是否在指定的区域内
				for (int j = 0; j < ball.length - 1; j++) {
					count += ball[j];
				}
				// 如果产生的号码不在指定的区域内，重新产生号码
				if (!(count > START && count < END)) {
					ball = this.createBall(ball);
					// 否则，退出while，将ball添加到list之中
				} else {
					break;
				}
			}
			ball = this.sort(ball);
			// 将这注产生的号码添加到list之中
			ballList.add(ball);
		}
	}
	
	/**
	 * 返回双色球字符串
	 * @return
	 */
	public static String getSSQ(){
		List<String> l = new SSQGenerateUtil().getBallString();
		if(l.size() > 0){
			return convertSSQBetStrategy(l.get(0).trim());
		} else {
			return null;
		}
	}
	
	/**
	 * 将“01 02 03 04 05 06 07”转换成“01,02,03,04,05,06|07”
	 * @param betContent
	 * @return
	 */
	private static String convertSSQBetStrategy(String betContent) {
		String[] balls = betContent.split(" ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			if (i < 5) {
				sb.append(balls[i] + ",");
			} else if (i == 5) {
				sb.append(balls[i] + "|");
			} else {
				sb.append(balls[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 产生双色球的七个号码
	 * 
	 * @param ball
	 * @return
	 */
	public int[] createBall(int[] ball) {
		for (int i = 0; i < ball.length; i++) {
			// 默认最后一个号码保留给蓝色球
			if (i < ball.length - 1) {
				// 如果是红色球，则验证每个产生的号码是否已经存在。
				ball = this.validateBall(ball, i);
			} else {
				// 如果是蓝色球，直接产生1--16之间的随机数
				ball[i] = (int) Math.round(Math.random() * 15 + 1);
			}
		}
		// 产生号码完毕，返回这注产生的号码。
		return ball;
	}

	/**
	 * 每次只产生一个红色号码，并验证此号码是否已经存在， 如果存在，重新产生号码，直到没有重复号码。
	 * 
	 * @param ball
	 * @param index
	 *            产生红色球数组的下标，即第几个号码。
	 * @return
	 */
	public int[] validateBall(int[] ball, int index) {
		// 产生一个1--33的随机数
		int random = (int) Math.round(Math.random() * 32 + 1);
		while (true) {
			int i = 0;
			for (; i < index; i++) {
				// 如果存在重复数字
				if (random == ball[i]) {
					// 重新产生号码
					random = (int) Math.round(Math.random() * 32 + 1);
					// 并且跳出for循环，进入while循环
					i = index + 1;
				}
			}
			// 如果验证完了所有号码，那把这个号码插入数组,并返回
			if (i == index) {
				ball[index] = random;
				return ball;
			}
		}
	}

	/**
	 * 显示产生的双色球号码
	 * 
	 */
	private List<String> getBallString() {
		List<String> list = new ArrayList<String>(); 
		StringBuilder sb = new StringBuilder();
		for (int[] ball : ballList) {
			for (int i = 0; i < ball.length; i++) {
				// 如果号码不足两位数，在前面添加"0"显示。
				sb.append(((ball[i] < 10) ? ("0" + ball[i]) : ball[i]) + " ");
			}
			list.add(sb.toString());
			sb.delete( 0, sb.length());
		}
		return list;
	}

	/**
	 * 完成初始化，制定双色球号码的 开始和结束区域
	 * 默认 26-149 防止号码集中在过大或过小区域
	 */
	private void init() {
		this.START = 26;
		this.END = 149;
	}

	/**
	 * 对号码进行排序,只对最后一个号码以前的 号码进行排序
	 * 
	 * @param ball
	 * @return
	 */
	private int[] sort(int[] ball) {
		for (int i = 0; i < ball.length - 1; i++) {
			for (int j = 0; j < ball.length - i - 2; j++) {
				if (ball[j] > ball[j + 1]) {
					int t = ball[j];
					ball[j] = ball[j + 1];
					ball[j + 1] = t;
				}
			}
		}
		return ball;
	}
}
