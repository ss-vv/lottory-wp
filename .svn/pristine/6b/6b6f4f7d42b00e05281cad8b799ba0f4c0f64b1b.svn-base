package com.unison.lottery.weibo.dataservice.crawler.parse.realtime;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class an {
	public static class a {
		String a;
		String b;
		int c;

		public String a() {
			return this.a;
		}

		public void a(int paramInt) {
			this.c = paramInt;
		}

		public void a(String paramString) {
			this.a = paramString;
		}

		public String b() {
			return this.b;
		}

		public void b(String paramString) {
			this.b = paramString;
		}

		public int c() {
			return this.c;
		}
	}

	public an() {
	}

	// public static int a(Context context, float f1)
	// {
	// return (int)(0.5F + f1 *
	// context.getResources().getDisplayMetrics().density);
	// }

	public static int a(String paramString, int paramInt) {
		try {
			int i = Integer.parseInt(paramString.trim());
			paramInt = i;

		} catch (Exception localException) {

		}
		return paramInt;
	}

	public static long a(String paramString, long paramLong) {
		try {
			long l = Long.parseLong(paramString.trim());
			paramLong = l;

		} catch (Exception localException) {
			// break label12;
		}
		return paramLong;
	}

	// public static View a(Context context, com.bet007.mobile.score.i.a a1,
	// com.bet007.mobile.score.f.a a2)
	// {
	// String s;
	// String s1;
	// String s2;
	// String s3;
	// String s4;
	// String s5;
	// s = a1.e();
	// s1 = a1.f();
	// s2 = a1.g();
	// s3 = a1.h();
	// s4 = a1.i();
	// s5 = a1.j();
	// if(!s.equals("") || !s1.equals("")) goto _L2; else goto _L1
	// _L1:
	// View view3 = LayoutInflater.from(context).inflate(0x7f030016, null);
	// _L4:
	// return view3;
	// _L2:
	// TextView textview;
	// ImageView imageview;
	// View view1;
	// LayoutInflater layoutinflater = LayoutInflater.from(context);
	// int i1;
	// View view;
	// ImageView imageview1;
	// View view2;
	// if(a1.b())
	// i1 = 0x7f030014;
	// else
	// i1 = 0x7f030000;
	// view = layoutinflater.inflate(i1, null);
	// textview = (TextView)view.findViewById(0x7f070026);
	// imageview = (ImageView)view.findViewById(0x7f070027);
	// imageview1 = (ImageView)view.findViewById(0x7f070029);
	// view1 = view.findViewById(0x7f070028);
	// view2 = view.findViewById(0x7f07002a);
	// if(z.d())
	// view2.setBackgroundResource(0x7f0a0067);
	// if(a1.c())
	// view2.setVisibility(0);
	// else
	// view2.setVisibility(8);
	// if(a2 == null)
	// imageview1.setVisibility(8);
	// if(s.equals(""))
	// break; /* Loop/switch isn't completed */
	// textview.setVisibility(0);
	// imageview.setVisibility(8);
	// textview.setText(s);
	// if(z.d())
	// {
	// textview.setBackgroundColor(Color.parseColor("#161F23"));
	// textview.setTextColor(Color.parseColor("#666666"));
	// } else
	// {
	// String s6;
	// String s7;
	// if(!s2.equals(""))
	// s6 = s2;
	// else
	// s6 = "#FFFFFF";
	// textview.setBackgroundColor(Color.parseColor(s6));
	// if(!s3.equals(""))
	// s7 = s3;
	// else
	// s7 = "#000000";
	// textview.setTextColor(Color.parseColor(s7));
	// }
	// _L6:
	// imageview1.setOnClickListener(new ap(a2, a1, view));
	// view.setOnClickListener(new aq(context, s4, s5));
	// view3 = view;
	// if(true) goto _L4; else goto _L3
	// _L3:
	// if(s1.equals("")) goto _L6; else goto _L5
	// _L5:
	// textview.setVisibility(8);
	// if(z.d())
	// view1.setVisibility(0);
	// com.bet007.mobile.score.image.a.b.b().a(imageview, s1);
	// goto _L6
	// }

	public static String a(float f1) {
		String s = (new StringBuilder()).append(f1).append("").toString();
		if (s.indexOf(".") > 0)
			s = s.replaceAll("0+?$", "").replaceAll("[.]$", "");
		return s;
	}

	public static String a(Object paramObject) {
		String str = paramObject.toString();
		try {
			Double.parseDouble(str.trim());
			if (str.indexOf(".") != -1) {
				if (str.length() - str.indexOf(".") == 2)
					str = str + "0";

			}
		} catch (Exception localException) {
			while (true) {
				str = "";
				str = str + ".00";
				continue;
			}
		}
		return str;
	}

	public static String a(Object obj, Object obj1, String s, boolean flag,
			boolean flag1) {
		String s1;
		if (flag) {
			int i1 = d(obj, obj1);
			if (i1 > 0)
				s1 = (new StringBuilder()).append("<font color=\"")
						.append(n("red")).append("\">").append(obj)
						.append("</font>").append(s).append("<font color=\"")
						.append(n("blue")).append("\">").append(obj1)
						.append("</font>").toString();
			else if (i1 < 0)
				s1 = (new StringBuilder()).append("<font color=\"")
						.append(n("blue")).append("\">").append(obj)
						.append("</font>").append(s).append("<font color=\"")
						.append(n("red")).append("\">").append(obj1)
						.append("</font>").toString();
			else if (flag1)
				s1 = (new StringBuilder()).append("<font color=\"")
						.append(n("green")).append("\">").append(obj)
						.append("</font>").append(s).append("<font color=\"")
						.append(n("green")).append("\">").append(obj1)
						.append("</font>").toString();
			else
				s1 = (new StringBuilder()).append(obj).append(s).append(obj1)
						.toString();
		} else {
			s1 = (new StringBuilder()).append(obj).append(s).append(obj1)
					.toString();
		}
		return s1;
	}

	public static String a(String paramString) {
		String localObject;
		localObject = "";
		if (!(paramString == null) && !(paramString.trim().equals(""))) {
			try {
				String str = URLEncoder.encode(paramString, "GB2312");
				localObject = str;
			} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
				localUnsupportedEncodingException.printStackTrace();
				localObject = "";
			}
		}
		return localObject;
	}

	public static String a(String s, int i1, String s1, boolean flag) {
		if (s.length() < i1) {
			int j1 = i1 - s.length();
			while (j1 < i1) {
				String s2;
				if (flag)
					s2 = (new StringBuilder()).append(s1).append(s).toString();
				else
					s2 = (new StringBuilder()).append(s).append(s1).toString();
				j1++;
				s = s2;
			}
		}
		return s;
	}

	public static String a(String paramString1, String paramString2) {
		try {
			if (paramString1.length() == 14) {
				Date localDate = new SimpleDateFormat("yyyyMMddHHmmss")
						.parse(paramString1);
				String str = new SimpleDateFormat(paramString2)
						.format(localDate);
				paramString1 = str;
			}
		} catch (Exception localException) {
			paramString1 = "";
		}
		return paramString1;
	}

	public static String a(String s, String s1, int i1, boolean flag) {
		int j1 = i1 - s.length();
		if (i1 > 0) {
			StringBuffer stringbuffer = new StringBuffer();
			if (!flag)
				stringbuffer.append(s);
			for (int k1 = 0; k1 < j1; k1++)
				stringbuffer.append(s1);

			if (flag)
				stringbuffer.append(s);
			s = stringbuffer.toString();
		}
		return s;
	}

	public static String a(String s, String s1, String s2, String s3, String s4) {
		String s5 = null;
		if (!s.equals(";;;;")) {
			String as[] = s.split(";", -1);

			if (as.length >= 5) {
				s5 = "";
				if (!as[0].equals(""))
					s5 = (new StringBuilder()).append(s5)
							.append(as[0].replace(",", "分钟[")).append("],")
							.toString();
				if (!as[1].equals(""))
					s5 = (new StringBuilder()).append(s5).append("两回合[")
							.append(as[1]).append("],").toString();
				if (!as[2].equals(""))
					s5 = (new StringBuilder())
							.append(s5)
							.append(as[2].replace("1,", "120分钟[").replace("2,",
									"加时[")).append("],").toString();
				if (!as[3].equals(""))
					s5 = (new StringBuilder()).append(s5).append("点球[")
							.append(as[3]).append("],").toString();
				if (as[4].equals("1")) {
					StringBuilder stringbuilder1 = (new StringBuilder())
							.append(s5);
					StringBuilder stringbuilder;
					// if(!com.bet007.mobile.score.common.z.b())
					s2 = s1;
					s5 = stringbuilder1.append(s2).append("赢").toString();
				}
				if (as[4].equals("2")) {
					StringBuilder stringbuilder = (new StringBuilder())
							.append(s5);
					// if(!com.bet007.mobile.score.common.z.b())
					s4 = s3;
					s5 = stringbuilder.append(s4).append("赢").toString();
				}
			} else {
				s5 = s;
			}
		}
		return s5;
	}

	// public static void a(Context context)
	// {
	// ((Vibrator)context.getSystemService("vibrator")).vibrate(500L);
	// }

	// public static void a(Context context, TextView textview, int i1, int j1,
	// int k1, int l1)
	// {
	// Drawable drawable = null;
	// Resources resources = context.getResources();
	// Drawable drawable1;
	// Drawable drawable2;
	// Drawable drawable3;
	// if(i1 != 0)
	// drawable1 = resources.getDrawable(i1);
	// else
	// drawable1 = null;
	// if(j1 != 0)
	// drawable2 = resources.getDrawable(j1);
	// else
	// drawable2 = null;
	// if(k1 != 0)
	// drawable3 = resources.getDrawable(k1);
	// else
	// drawable3 = null;
	// if(l1 != 0)
	// drawable = resources.getDrawable(l1);
	// a(textview, drawable1, drawable2, drawable3, drawable);
	// }

	// public static void a(Context context, ae ae1)
	// {
	// int i1 = 0;
	// NotificationManager notificationmanager =
	// (NotificationManager)context.getSystemService("notification");
	// Notification notification = new Notification(0x7f0200d2, (new
	// StringBuilder()).append(com.bet007.mobile.score.common.af.a(context,
	// 0x7f0900bf)).append("\u65B0\u6D88\u606F").toString(),
	// System.currentTimeMillis());
	// notification.flags = 16;
	// Intent intent = new Intent(context,
	// com/bet007/mobile/score/activity/main/Score_MainActivity);
	// intent.setFlags(0x14000000);
	// PendingIntent pendingintent = PendingIntent.getActivity(context, 0,
	// intent, 0x8000000);
	// RemoteViews remoteviews = new RemoteViews(context.getPackageName(),
	// 0x7f0300e2);
	// remoteviews.setTextViewText(0x7f07007b, ae1.m);
	// remoteviews.setTextViewText(0x7f07007c, ae1.l);
	// remoteviews.setTextViewText(0x7f070081, ae1.o);
	// remoteviews.setTextViewText(0x7f070082, ae1.p);
	// if(ae1.j == 3)
	// {
	// remoteviews.setTextViewText(0x7f07033a, ar.c(ae1.n));
	// remoteviews.setTextColor(0x7f07033a, ar.g(ae1.n));
	// } else
	// {
	// remoteviews.setTextViewText(0x7f07033a, ae1.k);
	// remoteviews.setTextColor(0x7f07033a, Color.parseColor("#ff3300"));
	// }
	// if(ae1.j == 1)
	// {
	// remoteviews.setViewVisibility(0x7f070084, 8);
	// remoteviews.setViewVisibility(0x7f070085, 8);
	// int j1;
	// if(b(ae1.w) > 0)
	// j1 = 0;
	// else
	// j1 = 4;
	// remoteviews.setViewVisibility(0x7f07033b, j1);
	// if(b(ae1.x) <= 0)
	// i1 = 4;
	// remoteviews.setViewVisibility(0x7f07033c, i1);
	// remoteviews.setTextViewText(0x7f07033b, ae1.w);
	// remoteviews.setTextViewText(0x7f07033c, ae1.x);
	// } else
	// {
	// remoteviews.setViewVisibility(0x7f070084, 0);
	// remoteviews.setViewVisibility(0x7f070085, 0);
	// remoteviews.setViewVisibility(0x7f07033b, 8);
	// remoteviews.setViewVisibility(0x7f07033c, 8);
	// remoteviews.setTextViewText(0x7f070084, ae1.u);
	// remoteviews.setTextViewText(0x7f070085, ae1.v);
	// }
	// notification.contentView = remoteviews;
	// notification.contentIntent = pendingintent;
	// notificationmanager.notify(c, notification);
	// c = 1 + c;
	// if(c >= 0x1eb759)
	// c = 0x1eb74f;
	// }

	// public static void a(Context context, ag ag1)
	// {
	// f((new
	// StringBuilder()).append("AddSound begin:").append(ag1.toString()).toString());
	// try
	// {
	// if(ag1.a())
	// b = MediaPlayer.create(context, RingtoneManager.getDefaultUri(2));
	// else
	// b = MediaPlayer.create(context,
	// com.bet007.mobile.score.common.z.a(context, ag1.a(), ag1.b(), ag1.c()));
	// b.setOnCompletionListener(new ao());
	// b.setAudioStreamType(3);
	// b.setLooping(false);
	// b.start();
	// }
	// catch(Exception exception)
	// {
	// f((new
	// StringBuilder()).append("AddSound exp:").append(exception.toString()).toString());
	// }
	// }

	// public static void a(Context context, w w1)
	// {
	// NotificationManager notificationmanager =
	// (NotificationManager)context.getSystemService("notification");
	// Notification notification = new Notification(0x7f0200d2, (new
	// StringBuilder()).append(com.bet007.mobile.score.common.af.a(context,
	// 0x7f0900bf)).append("\u65B0\u6D88\u606F").toString(),
	// System.currentTimeMillis());
	// notification.flags = 16;
	// Intent intent = new Intent(context,
	// com/bet007/mobile/score/activity/main/Score_MainActivity);
	// intent.setFlags(0x14000000);
	// PendingIntent pendingintent = PendingIntent.getActivity(context, 0,
	// intent, 0x8000000);
	// RemoteViews remoteviews = new RemoteViews(context.getPackageName(),
	// 0x7f030008);
	// remoteviews.setImageViewResource(0x7f070079, 0x7f0200d2);
	// remoteviews.setTextViewText(0x7f07007b, w1.n());
	// remoteviews.setTextViewText(0x7f07007c, a(w1.d(), "MM-dd"));
	// remoteviews.setTextViewText(0x7f070081, w1.g());
	// remoteviews.setTextViewText(0x7f070082, w1.h());
	// remoteviews.setTextViewText(0x7f07007e, w.a(w1.b(), w1.e()));
	// remoteviews.setTextViewText(0x7f07007f, w1.f());
	// remoteviews.setTextViewText(0x7f070084, w1.i());
	// remoteviews.setTextViewText(0x7f070085, w1.j());
	// notification.contentView = remoteviews;
	// notification.contentIntent = pendingintent;
	// notificationmanager.notify(c, notification);
	// c = 1 + c;
	// if(c >= 0x1eb759)
	// c = 0x1eb74f;
	// }

	// public static void a(Context context, String s, String s1)
	// {
	// String s2 = ScoreApplication.a(context, s1, "");
	// if(!(new
	// StringBuilder()).append(",").append(s2).append(",").toString().contains((new
	// StringBuilder()).append(",").append(s).append(",").toString()))
	// {
	// if(!s2.equals(""))
	// s = (new StringBuilder()).append(s2).append(",").append(s).toString();
	// ScoreApplication.b(context, s1, s);
	// }
	// }

	// public static void a(Context context, String s, String s1, boolean flag)
	// {
	// if(s1 != null && !s1.equals(""))
	// {
	// Intent intent = d(context, s1);
	// if(intent != null)
	// context.startActivity(intent);
	// else
	// if(s == null || s.equals(""))
	// com.bet007.mobile.score.common.am.a(context,
	// com.bet007.mobile.score.common.af.a(0x7f09012d));
	// else
	// a(context, s, flag);
	// } else
	// {
	// a(context, s, flag);
	// }
	// }

	// private static void a(Context context, String s, boolean flag)
	// {
	// if(s != null && !s.equals(""))
	// if(s.endsWith(".apk"))
	// {
	// if(flag)
	// {
	// (new
	// h(context)).b(com.bet007.mobile.score.common.af.a(0x7f09012a)).a(com.bet007.mobile.score.common.af.a(0x7f0900d1),
	// new com.bet007.mobile.score.common.ar(s,
	// context)).a(com.bet007.mobile.score.common.af.a(0x7f0900d2),
	// null).a().show();
	// } else
	// {
	// Intent intent1 = new Intent("android.intent.action.VIEW");
	// intent1.setData(Uri.parse(s));
	// context.startActivity(intent1);
	// }
	// } else
	// {
	// Intent intent = new Intent();
	// intent.putExtra("url", s);
	// intent.setClass(context,
	// com/bet007/mobile/score/activity/main/WebViewActivity);
	// context.startActivity(intent);
	// }
	// }

	// public static void a(View view, int i1, int j1)
	// {
	// if(z.d())
	// view.setBackgroundResource(j1);
	// else
	// view.setBackgroundResource(i1);
	// }

	// public static void a(TextView textview, int i1, int j1)
	// {
	// if(z.d())
	// textview.setTextColor(ScoreApplication.a().getResources().getColorStateList(j1));
	// else
	// textview.setTextColor(ScoreApplication.a().getResources().getColorStateList(i1));
	// }

	// private static void a(TextView textview, Drawable drawable, Drawable
	// drawable1, Drawable drawable2, Drawable drawable3)
	// {
	// if(drawable != null)
	// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight());
	// if(drawable2 != null)
	// drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(),
	// drawable2.getIntrinsicHeight());
	// if(drawable1 != null)
	// drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(),
	// drawable1.getIntrinsicHeight());
	// if(drawable3 != null)
	// drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(),
	// drawable3.getIntrinsicHeight());
	// textview.setCompoundDrawables(drawable, drawable1, drawable2, drawable3);
	// }

	// public static void a(TextView textview, String s, String s1, boolean
	// flag)
	// {
	// a(textview, s, s1, flag, false);
	// }

	// public static void a(TextView textview, String s, String s1, boolean
	// flag, boolean flag1)
	// {
	// int i1;
	// String s2;
	// String s3;
	// if(flag)
	// i1 = e(s1, s);
	// else
	// i1 = d(s1, s);
	// s2 = n("black");
	// if(i1 > 0)
	// s3 = "#339900";
	// else
	// if(i1 < 0)
	// s3 = "#cc3300";
	// else
	// s3 = s2;
	// if(flag1)
	// s = aa.c(s);
	// textview.setText(s);
	// textview.setTextColor(Color.parseColor(s3));
	// }

	public static boolean a() {
		boolean flag;
		String as[] = { "" };
		String s = "";
		String s1 = "";
		String s2 = "";
		int i1;
		flag = false;
		// if(ScoreApplication.p.equals(""))
		// break MISSING_BLOCK_LABEL_149;
		// as = ScoreApplication.p.split("\\!", -1);
		// s = ScoreApplication.U;
		// s1 = ScoreApplication.B;
		s2 = (new StringBuilder()).append(s).append("^").append(s1).toString();
		i1 = 0;

		while (i1 >= as.length) {

			if (!as[i1].equals((new StringBuilder()).append(s).append("^")
					.toString())
					&& !as[i1].equals((new StringBuilder()).append("^")
							.append(s1).toString()) && !as[i1].equals(s2)) {
				flag = true;

			}

			i1++;
		}

		return flag;
	}

	public static boolean a(char c1) {
		boolean flag;
		if (c1 >= '0' && c1 <= '9')
			flag = true;
		else
			flag = false;
		return flag;
	}

	// public static boolean a(Context context, String s)
	// {
	// boolean flag = false;
	// PackageInfo packageinfo1 = context.getPackageManager().getPackageInfo(s,
	// 0);
	// PackageInfo packageinfo = packageinfo1;
	// _L2:
	// if(packageinfo != null && b(packageinfo.versionName, "1.3.0") > 0)
	// flag = true;
	// return flag;
	// android.content.pm.PackageManager.NameNotFoundException
	// namenotfoundexception;
	// namenotfoundexception;
	// namenotfoundexception.printStackTrace();
	// packageinfo = null;
	// if(true) goto _L2; else goto _L1
	// _L1:
	// }

	public static boolean a(Object paramObject1, Object paramObject2) {
		boolean bool = false;
		if (!(paramObject1 == null) && !(paramObject2 == null)) {

			try {
				int i = Integer.parseInt(paramObject1.toString().trim());
				int j = Integer.parseInt(paramObject2.toString().trim());
				if (i == j)
					bool = true;
			} catch (Exception localException) {
			}
		}
		return bool;
	}

	public static boolean a(String paramString, boolean paramBoolean) {
		try {
			boolean bool = Boolean.parseBoolean(paramString.trim());
			paramBoolean = bool;

		} catch (Exception localException) {

		}
		return paramBoolean;
	}

//	private static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
//			throws Exception {
//		SecureRandom localSecureRandom = new SecureRandom();
//		DESKeySpec localDESKeySpec = new DESKeySpec(paramArrayOfByte2);
//		SecretKey localSecretKey = SecretKeyFactory.getInstance("DES")
//				.generateSecret(localDESKeySpec);
//		Cipher localCipher = Cipher.getInstance("DES");
//		localCipher.init(1, localSecretKey, localSecureRandom);
//		return localCipher.doFinal(paramArrayOfByte1);
//	}

	// public static int b(Context context, float f1)
	// {
	// return (int)(0.5F + f1 /
	// context.getResources().getDisplayMetrics().density);
	// }

	public static int b(String paramString) {
		int i = 0;
		try {
			int j = Integer.parseInt(paramString.trim());
			i = j;

		} catch (Exception localException) {

		}
		return i;
	}

	public static int b(String s, String s1) {
		String as[];
		String as1[];
		int j1;
		int k1 = 0;
		as = s.split("\\.", -1);
		as1 = s1.split("\\.", -1);
		int i1;
		if (as.length > as1.length)
			i1 = as.length;
		else
			i1 = as1.length;
		j1 = 0;
		while (j1 < i1) {
			if (as.length <= j1 || as1.length <= j1) {

				if (as.length <= j1 && as1.length > j1)
					k1 = -1;
				else
					k1 = 1;

				return k1;
			} else {

				if (b(as[j1]) <= b(as1[j1])) {

					if (b(as[j1]) >= b(as1[j1])) {

						j1++;

						k1 = 0;

						return k1;
					} else {

						k1 = -1;

						return k1;
					}
				} else {

					k1 = 1;
				}
			}

		}

		return k1;
	}

	// public static void b(Context context)
	// {
	// NotificationManager notificationmanager =
	// (NotificationManager)context.getSystemService("notification");
	// Notification notification = new Notification(0x7f0200d2, (new
	// StringBuilder()).append(com.bet007.mobile.score.common.af.a(context,
	// 0x7f0900bf)).append("\u6B63\u5728\u8FD0\u884C").toString(),
	// System.currentTimeMillis());
	// notification.flags = 2 | notification.flags;
	// Intent intent = new Intent("android.intent.action.MAIN");
	// intent.setFlags(0x200000);
	// intent.addFlags(0x100000);
	// intent.addCategory("android.intent.category.LAUNCHER");
	// intent.setClass(context,
	// com/bet007/mobile/score/activity/main/Score_MainActivity);
	// PendingIntent pendingintent = PendingIntent.getActivity(context, 0,
	// intent, 0x8000000);
	// RemoteViews remoteviews = new RemoteViews(context.getPackageName(),
	// 0x7f0300e3);
	// Object aobj[] = new Object[1];
	// aobj[0] = new Date();
	// remoteviews.setTextViewText(0x7f07033d, String.format("%1$tR", aobj));
	// notification.contentView = remoteviews;
	// notification.contentIntent = pendingintent;
	// notificationmanager.notify(d, notification);
	// }

	// public static void b(Context context, String s)
	// {
	// String as[];
	// ArrayList arraylist;
	// int i1;
	// as = s.split("\\!", -1);
	// arraylist = new ArrayList();
	// i1 = 0;
	// _L3:
	// String as1[];
	// if(i1 >= as.length)
	// break MISSING_BLOCK_LABEL_400;
	// as1 = as[i1].split("\\^", -1);
	// if(as1.length >= 4) goto _L2; else goto _L1
	// _L1:
	// return;
	// _L2:
	// NotificationManager notificationmanager =
	// (NotificationManager)context.getSystemService("notification");
	// Notification notification = new Notification(0x7f0200d2, (new
	// StringBuilder()).append(com.bet007.mobile.score.common.af.a(context,
	// 0x7f0900bf)).append("\u65B0\u63A8\u8350").toString(),
	// System.currentTimeMillis());
	// notification.flags = 16;
	// Intent intent = null;
	// PendingIntent pendingintent;
	// RemoteViews remoteviews;
	// if(as1[0].equals("2") && !as1[3].equals(""))
	// {
	// intent = new Intent("android.intent.action.VIEW");
	// intent.setData(Uri.parse(as1[3]));
	// } else
	// if(as1[0].equals("1"))
	// intent = d(context, "com.chinaway.android.lottery");
	// if(as1[0].equals("0") || intent == null)
	// {
	// intent = new Intent(context,
	// com/bet007/mobile/score/activity/main/Score_MainActivity);
	// intent.setFlags(0x14000000);
	// }
	// pendingintent = PendingIntent.getActivity(context, 0, intent, 0x8000000);
	// remoteviews = new RemoteViews(context.getPackageName(), 0x7f0300e3);
	// if(as1[1].equals(""))
	// remoteviews.setTextViewText(0x7f070338,
	// com.bet007.mobile.score.common.af.a(context, 0x7f0900bf));
	// else
	// remoteviews.setTextViewText(0x7f070338, as1[1]);
	// if(as1[2].equals(""))
	// remoteviews.setViewVisibility(0x7f070339, 8);
	// remoteviews.setTextViewText(0x7f070339, as1[2]);
	// notification.contentView = remoteviews;
	// notification.contentIntent = pendingintent;
	// notificationmanager.notify(c, notification);
	// c = 1 + c;
	// if(c >= 0x1eb759)
	// c = 0x1eb74f;
	// arraylist.add(new ag(true, false, false));
	// i1++;
	// goto _L3
	// if(arraylist.size() > 0)
	// a(context, (ag)arraylist.get(-1 + arraylist.size()));
	// goto _L1
	// }

	// public static void b(Context context, String s, String s1)
	// {
	// ScoreApplication.b(context, s1, (new
	// StringBuilder()).append(",").append(ScoreApplication.a(context, s1,
	// "")).append(",").toString().replace((new
	// StringBuilder()).append(",").append(s).append(",").toString(),
	// ",").replaceAll("(^,|,$)", ""));
	// }

	// public static void b(View view, int i1, int j1)
	// {
	// int k1 = view.getPaddingBottom();
	// int l1 = view.getPaddingTop();
	// int i2 = view.getPaddingRight();
	// int j2 = view.getPaddingLeft();
	// a(view, i1, j1);
	// view.setPadding(j2, l1, i2, k1);
	// }

	public static boolean b(char c1) {
		boolean flag;
		if (c1 >= 'A' && c1 <= 'Z' || c1 >= 'a' && c1 <= 'z')
			flag = true;
		else
			flag = false;
		return flag;
	}

	public static boolean b(Object paramObject1, Object paramObject2) {
		boolean bool1 = false;
		if (!(paramObject1 == null) && !(paramObject2 == null)) {
			{

				try {
					boolean bool2 = paramObject1.toString().trim()
							.equals(paramObject2.toString().trim());
					bool1 = bool2;
				} catch (Exception localException) {
				}
			}

		}
		return bool1;
	}

	private static byte[] b(byte abyte0[], byte abyte1[]) throws Exception {
//		SecureRandom securerandom = new SecureRandom();
//		DESKeySpec deskeyspec = new DESKeySpec(abyte1);
//		javax.crypto.SecretKey secretkey = SecretKeyFactory.getInstance("DES")
//				.generateSecret(deskeyspec);
//		Cipher cipher = Cipher.getInstance("DES");
//		cipher.init(2, secretkey, securerandom);
//		return cipher.doFinal(abyte0);
		return null;
	}

	public static float c(String paramString) {
		float f1 = 0.0F;
		try {
			float f2 = Float.parseFloat(paramString.trim());
			f1 = f2;

		} catch (Exception localException) {

		}
		return f1;
	}

	// public static String c(Context context, String s)
	// {
	// String s1 = "";
	// try
	// {
	// f((new
	// StringBuilder()).append("\u539F\u6587\uFF1A").append(s).toString());
	// PublicKey publickey = c(context);
	// Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	// cipher.init(1, publickey);
	// s1 = (new
	// String(com.bet007.mobile.score.common.a.c(cipher.doFinal(s.getBytes()),
	// 0), "UTF-8")).replaceAll("\\n", "");
	// f((new
	// StringBuilder()).append("\u5BC6\u6587\uFF1A").append(s1).toString());
	// }
	// catch(Exception exception) { }
	// return s1;
	// }

	public static String c(String s, String s1) {
		return (new StringBuilder()).append("<font color=\"").append(n(s))
				.append("\">").append(s1).append("</font>").toString();
	}

	// private static PublicKey c(Context context)
	// throws Exception
	// {
	// java.io.InputStream inputstream =
	// context.getResources().getAssets().open("cert.der");
	// return
	// CertificateFactory.getInstance("X.509").generateCertificate(inputstream).getPublicKey();
	// }

	public static boolean c(Object paramObject1, Object paramObject2) {
		boolean bool = false;
		if (!(paramObject1 == null) && !(paramObject2 == null)) {

			try {
				int i = Integer.parseInt(paramObject1.toString().trim());
				int j = Integer.parseInt(paramObject2.toString().trim());
				if (i > j)
					bool = true;
			} catch (Exception localException) {
			}
		}
		return bool;
	}

	public static double d(String paramString) {
		double d1 = 0.0D;
		try {
			double d2 = Double.parseDouble(paramString.trim());
			d1 = d2;

		} catch (Exception localException) {

		}
		return d1;
	}

	public static int d(Object paramObject1, Object paramObject2) {
		int i = 0;
		if (!(paramObject1 == null) && !(paramObject2 == null)) {

			try {
				float f1 = Float.parseFloat(paramObject1.toString().trim());
				float f2 = Float.parseFloat(paramObject2.toString().trim());
				if (f1 > f2)
					i = 1;
				else if (f1 < f2)
					i = -1;
			} catch (Exception localException) {
			}
		}
		return i;
	}

	// private static Intent d(Context context, String s)
	// {
	// Intent intent = null;
	// PackageInfo packageinfo1 = context.getPackageManager().getPackageInfo(s,
	// 0);
	// PackageInfo packageinfo = packageinfo1;
	// _L2:
	// if(packageinfo != null)
	// {
	// intent = context.getPackageManager().getLaunchIntentForPackage(s);
	// intent.setFlags(0x10000000);
	// }
	// return intent;
	// android.content.pm.PackageManager.NameNotFoundException
	// namenotfoundexception;
	// namenotfoundexception;
	// packageinfo = null;
	// if(true) goto _L2; else goto _L1
	// _L1:
	// }

	// public static String d(String s, String s1)
	// {
	// String s2;
	// try
	// {
	// s2 = new String(com.bet007.mobile.score.common.a.c(a(s.getBytes(),
	// s1.getBytes()), 0), "UTF-8");
	// }
	// catch(Exception exception)
	// {
	// s2 = "";
	// }
	// return s2;
	// }

	public static int e(Object paramObject1, Object paramObject2) {
		int i = 0;
		if (!(paramObject1 == null) && !(paramObject2 == null)) {

			try {
				float f1 = Float.parseFloat(paramObject1.toString().trim());
				float f2 = Float.parseFloat(paramObject2.toString().trim());
				float f3 = Math.abs(f1);
				float f4 = Math.abs(f2);
				if (f3 > f4)
					i = 1;
				else if (f3 < f4)
					i = -1;
			} catch (Exception localException) {
			}
		}
		return i;
	}

	public static String e(String paramString) {
		String str = "";
		try {
			float f = Float.parseFloat(paramString.trim());
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = Float.valueOf(f);
			str = String.format("%.2f", arrayOfObject);

		} catch (Exception localException) {

		}
		return str;
	}

	public static String e(String paramString1, String paramString2) {
		String str = null;
		if (!(paramString1 == null))

		{

			try {
				// str = new String(b(a.a(paramString1, 0),
				// paramString2.getBytes()));
			} catch (Exception localException) {
				str = "";
			}
		}
		return str;
	}

	public static void f(String s) {
	}

	public static void g(String s) {
	}

	public static boolean h(String s) {
		boolean flag;
		if (s == null || s.equals("") || s.equals("NETWORK_ERROR")
				|| s.equals("SERVER_ERROR"))
			flag = false;
		else
			flag = true;
		return flag;
	}

	public static boolean i(String s) {
		boolean flag;
		if (s == null || s.equals("NETWORK_ERROR") || s.equals("SERVER_ERROR"))
			flag = false;
		else
			flag = true;
		return flag;
	}

	public static boolean j(String s) {
		boolean flag;
		if (s == null || s.equals(""))
			flag = false;
		else
			flag = true;
		return flag;
	}

	public static a k(String s) {
		a a1;
		try {
			a1 = new a();
			Date date = (new SimpleDateFormat("yyyyMMddHHmmss")).parse(s);
			String as[] = new String[7];
			as[0] = "\u65E5";
			as[1] = "\u4E00";
			as[2] = "\u4E8C";
			as[3] = "\u4E09";
			as[4] = "\u56DB";
			as[5] = "\u4E94";
			as[6] = "\u516D";
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int i1 = -1 + calendar.get(7);
			a1.c = i1;
			a1.a = as[i1];
			a1.b = a[i1];
		} catch (ParseException parseexception) {
			parseexception.printStackTrace();
			a1 = null;
		}
		return a1;
	}

	public static Date l(String paramString) {
		Date localObject;
		try {
			if (paramString.length() != 14) {
				localObject = new Date();
			} else {
				Date localDate = new SimpleDateFormat("yyyyMMddHHmmss")
						.parse(paramString);
				localObject = localDate;
			}
		} catch (Exception localException) {
			localObject = new Date();
		}
		return localObject;
	}

	// public static JSONObject m(String s)
	// throws JSONException
	// {
	// String s1 = android.os.Build.VERSION.SDK;
	// JSONObject jsonobject;
	// if(s1 != null && s1.matches("\\d+") && Integer.parseInt(s1) < 8)
	// jsonobject = new JSONObject(new com.bet007.mobile.score.common.ae(s));
	// else
	// jsonobject = new JSONObject(s);
	// return jsonobject;
	// }

	public static String n(String s) {
		String s1;
		// if(s.equals("red"))
		// {
		// if(z.d())
		s1 = "#880000";
		// else
		// s1 = "#FF0000";
		// } else
		// if(s.equals("blue"))
		// {
		// if(z.d())
		// s1 = "#3468af";
		// else
		// s1 = "#0000FF";
		// } else
		// if(s.equals("yellow"))
		// {
		// if(z.d())
		// s1 = "#FFFAC5";
		// else
		// s1 = "#242100";
		// } else
		// if(s.equals("black"))
		// {
		// if(z.d())
		// s1 = "#666666";
		// else
		// s1 = "#000000";
		// } else
		// if(s.equals("white"))
		// {
		// if(z.d())
		// s1 = "#666666";
		// else
		// s1 = "#FFFFFF";
		// } else
		// if(s.equals("orange"))
		// {
		// if(z.d())
		// s1 = "#883600";
		// else
		// s1 = "#FF6600";
		// } else
		// if(s.equals("green"))
		// s1 = "#006600";
		// else
		// if(s.equals("indexscore"))
		// {
		// if(z.d())
		// s1 = "#8F6F18";
		// else
		// s1 = "#FFFF00";
		// } else
		// if(s.equals("matchtime"))
		// {
		// if(z.d())
		// s1 = "#517551";
		// else
		// s1 = "#666666";
		// } else
		// if(s.equals("halfscore"))
		// s1 = "#4295DF";
		// else
		// if(s.equals("tip"))
		// {
		// if(z.d())
		// s1 = "#666666";
		// else
		// s1 = "#24456e";
		// } else
		// if(s.equals("goingscore"))
		// {
		// if(z.d())
		// s1 = "#666666";
		// else
		// s1 = "#2D5C91";
		// } else
		// if(s.equals("img_tip"))
		// s1 = "#AA763A";
		// else
		// if(s.equals("guess_needcount"))
		// s1 = "#FF6666";
		// else
		// if(s.equals("red_guess_num_2"))
		// s1 = "#CC0000";
		// else
		// if(s.equals("red_guess_num"))
		// s1 = "#FF3300";
		// else
		// if(s.equals("blue_guess_num"))
		// s1 = "#3399FF";
		// else
		// if(s.equals("green_guess_num"))
		// s1 = "#339900";
		// else
		// if(s.equals("guess_username"))
		// {
		// if(z.d())
		// s1 = "#666666";
		// else
		// s1 = "#004B7E";
		// } else
		// {
		// s1 = "";
		// }
		return s1;
	}

	public static int o(String s) {
		int i1;
		if (s.equals("matchtime")) {
			// if(z.d())
			i1 = 0xff517551;
			// else
			i1 = 0xff666666;
		} else if (s.equals("halfscore"))
			i1 = 0xff4295df;
		else
			i1 = 0;
		return i1;
	}

	static String a[];

	static int c = 0x1eb74f;
	static int d = 0x4823ba5a;

	static {
		String as[] = new String[7];
		as[0] = "#518ed2";
		as[1] = "#e8811a";
		as[2] = "#949720";
		as[3] = "#8d6dd6";
		as[4] = "#53ac98";
		as[5] = "#ffcc66";
		as[6] = "#cc00ff";
		a = as;
	}
}
