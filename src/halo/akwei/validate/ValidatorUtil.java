package halo.akwei.validate;

import java.util.Date;

import halo.akwei.util.DataUtil;

public class ValidatorUtil {

	/**
	 * 检查字符串长度范围
	 * 
	 * @param value 待检查内容
	 * @param canEmpty 是否可以为null
	 * @param minLen 最小边界
	 * @param maxLen 最大边界
	 * @return
	 */
	public static boolean testStringRange(String value, boolean canEmpty, int minLen, int maxLen) {
		if (DataUtil.isEmpty(value)) {
			if (canEmpty) {
				return true;
			}
			return false;
		}
		String v = value.trim();
		if (v.length() >= minLen && v.length() <= maxLen) {
			return true;
		}
		return false;
	}

	/**
	 * 检查是否是手机号码
	 * 
	 * @param value
	 * @return
	 */
	public static boolean testMobile(String value) {
		if (value.length() != 11) {
			return false;
		}
		return true;
	}

	/**
	 * 检查日志是否在给定日期之前
	 * 
	 * @param value
	 * @param compare
	 * @return
	 */
	public static boolean testBeforDate(Date value, Date compare) {
		if (value.getTime() <= compare.getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查日期是否在给定日期之后
	 * 
	 * @param value
	 * @param compare
	 * @return
	 */
	public static boolean testAfterDate(Date value, Date compare) {
		if (value.getTime() >= compare.getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查日期范围
	 * 
	 * @param value
	 * @param min 最小日期
	 * @param max 最大日期
	 * @return
	 */
	public static boolean testDateRange(Date value, Date min, Date max) {
		if (value.getTime() >= min.getTime() && value.getTime() < max.getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 检查数字范围
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean testLongRange(long value, long min, long max) {
		if (value >= min && value <= max) {
			return true;
		}
		return false;
	}

	/**
	 * 检查数字范围
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean testIntRange(int value, int min, int max) {
		if (value >= min && value <= max) {
			return true;
		}
		return false;
	}
}