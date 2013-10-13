package halo.akwei.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	/**
	 * 获得毫秒时间中的秒部分
	 * 
	 * @param time
	 * @return
	 */
	public static long getSecond(long time) {
		return time / 1000;
	}

	public static long getCurrentSecond() {
		return getSecond(System.currentTimeMillis());
	}

	/**
	 * 获得当前时区日期开始时间(单位:秒)为00:00:00
	 * 
	 * @param cal
	 * @return
	 */
	public static long getBeginDateSecond(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis() / 1000;
	}

	public static long getBeginDateSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis() / 1000;
	}

	/**
	 * 获得当前日期结束时间(单位:秒)为23:59:59秒时
	 * 
	 * @param cal
	 * @return
	 */
	public static long getEndDateSecond(Calendar cal) {
		long now = getBeginDateSecond(cal);
		return now + 24 * 3600 - 1;
	}

	public static long getEndDateSecond(Date date) {
		long now = getBeginDateSecond(date);
		return now + 24 * 3600 - 1;
	}

	public static long getTimeSecond(String input, String pattern) {
		if (DataUtil.isEmpty(input)) {
			return 0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date = sdf.parse(input);
			return date.getTime() / 1000;
		}
		catch (ParseException e) {
			return 0;
		}
	}

	public static Date getDate(String input, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(input);
		}
		catch (ParseException e) {
			return null;
		}
	}

	public static long getTimeSecondSinceNow(long second) {
		long t = TimeUtil.getCurrentSecond() + second;
		return t;
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		// cal.setTimeInMillis(1371484800000L);
		// System.out.println(cal.getTime());
		System.out.println(TimeUtil.getBeginDateSecond(cal));
	}
}
