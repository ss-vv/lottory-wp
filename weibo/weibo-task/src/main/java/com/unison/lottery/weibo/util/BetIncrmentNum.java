package com.unison.lottery.weibo.util;

import java.util.Calendar;
import java.util.Random;

public class BetIncrmentNum {

	public static int getIncrementNum(String matchtype, String week_high_time,
			String week_high_incr, String week_low_incr, String stoptime,
			String weekend_high_time, String weekend_high_incr,
			String weekend_low_incr) {

		Calendar c = Calendar.getInstance();
		Calendar begin = (Calendar) c.clone();
		Calendar end = (Calendar) c.clone();
		int week = c.get(Calendar.DAY_OF_WEEK) - 1;
		String beginarray = "";
		String high_incre = "";
		String low_incre = "";
		if ("jczq".equals(matchtype) || "bjdc".equals(matchtype)) {
			if (week >= 1 && week <= 5) {
				beginarray = week_high_time;
				high_incre = week_high_incr;
				low_incre = week_low_incr;
			} else {
				beginarray = week_high_time;
				high_incre = week_high_incr;
				low_incre = week_low_incr;
			}
		} else if ("jclq".equals(matchtype)) {
			beginarray = week_high_time;
			high_incre = week_high_incr;
			low_incre = week_low_incr;

		} else if ("ctzq".equals(matchtype)) {
			beginarray = week_high_time;
			high_incre = week_high_incr;
			low_incre = week_low_incr;

		}
		Random r = new Random();
		String[] stop = null;
		if (stoptime != null) {
			stoptime = "01:00-09:00";
			stop = stoptime.split("-");
		}

		String[] h_time_arr = beginarray.split(",");

		if (h_time_arr.length >= 1) {
			for (int i = 0; i < h_time_arr.length; i++) {

				String[] begin_end = h_time_arr[i].split("-");
				String b_hour = begin_end[0].split(":")[0];
				String b_min = begin_end[0].split(":")[1];
				String e_hour = begin_end[1].split(":")[0];
				String e_min = begin_end[1].split(":")[1];

				begin.set(Calendar.HOUR_OF_DAY,
						b_hour != null ? Integer.parseInt(b_hour) : 19);
				begin.set(Calendar.MINUTE,
						b_min != null ? Integer.parseInt(b_min) : 00);
				end.set(Calendar.HOUR_OF_DAY,
						e_hour != null ? Integer.parseInt(e_hour) : 23);
				end.set(Calendar.MINUTE,
						e_min != null ? Integer.parseInt(e_min) : 00);
				if (c.after(begin) && c.before(end)) {
					int num = r.nextInt(9);//high_incre
					return num == 6 ? 1 : 0;
				} else {

					String[] tmp = stop[0].split(":");
					String[] e_tmp = stop[1].split(":");
					begin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmp[0]));
					begin.set(Calendar.MINUTE, Integer.parseInt(tmp[1]));
					end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(e_tmp[0]));
					end.set(Calendar.MINUTE, Integer.parseInt(e_tmp[1]));
					if (c.after(begin) && c.before(end)) {
						return 0;
					}
				}
			}

		}
		int num = 0;
		if ("ctzq".equals(matchtype)) {

			int tmp = r.nextInt(9);
			if (tmp == 6) {
				return 1;
			}
		}
		num = r.nextInt(9);//Integer.parseInt(low_incre)
		return num==6?1:0;

	}
}
