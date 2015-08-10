package rpg.server.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 时间相关工具
 * 
 */
public class DateUtil extends DateUtils {
	// 日期格式字符串
	private static final String dateformatter = "yyyyMMdd";

	private static final String dayformatter = "yyyy-MM-dd";

	private static final String timeformatter_minute = "HH:mm";

	private static final String timeformatter_second = "HH:mm:ss";

	private static final String datetimeformatter = "yyyy-MM-dd HH:mm:ss";

	private static final String timeformatter_ms = "mm:ss";
	/** 一天的毫秒数 */
	public static final long DAY = 24 * 60 * 60 * 1000;
	/** 一小时的毫秒数 */
	public static final long HOUR = 60 * 60 * 1000;
	/** 一分钟的毫秒数 */
	public static final long MINUTE = 60 * 1000;
	/** 一秒钟的毫秒数 */
	public static final long SEC = 1000;

	/**
	 * 获取DateFormat实例<br>
	 * 返回一个格式为yyyy-MM-dd HH:mm:ss的实例
	 * 
	 * @return DateFormat实例
	 */
	public static DateFormat getDateTimeFormatter() {
		return new SimpleDateFormat(datetimeformatter);
	}

	/**
	 * 格式日期<br>
	 * 转换为yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param date
	 *            日期
	 * @return 格式yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateTime(Date date) {
		return new SimpleDateFormat(datetimeformatter).format(date);
	}

	/**
	 * 格式化日期时间 转换为yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param time
	 *            时间
	 * @return 格式yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateTime(long time) {
		return new SimpleDateFormat(datetimeformatter).format(time);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @return 格式yyyyMMdd
	 */
	public static String formatDate(Date date) {
		return new SimpleDateFormat(dateformatter).format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param time
	 *            时间
	 * @return string类型时间,格式为yyyyMMdd
	 */
	public static String formatDate(long time) {
		return new SimpleDateFormat(dateformatter).format(time);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @return 格式yyyy-MM-dd
	 */
	public static String formatDay(Date date) {
		return new SimpleDateFormat(dayformatter).format(date);
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 *            时间
	 * @return string类型时间,格式为yyyy-MM-dd
	 */
	public static String formatDay(long time) {
		return new SimpleDateFormat(dayformatter).format(time);
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 *            时间
	 * @return string类型时间,格式为HH:mm
	 */
	public static String formatTime(long time) {
		return new SimpleDateFormat(timeformatter_minute).format(time);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @return 格式HH:mm
	 */
	public static String formatTime(Date date) {
		return new SimpleDateFormat(timeformatter_minute).format(date);
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 *            时间
	 * @return string类型时间,格式为HH:mm:ss
	 */
	public static String formatTimeSecond(long time) {
		return new SimpleDateFormat(timeformatter_second).format(time);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @return 格式HH:mm:ss
	 */
	public static String formatTimeSecond(Date date) {
		return new SimpleDateFormat(timeformatter_second).format(date);
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 *            时间
	 * @return string类型时间,格式为mm:ss
	 */
	public static String formatTimeMS(long time) {
		return new SimpleDateFormat(timeformatter_ms).format(time);
	}

	/**
	 * 解析指定字符串表示的日期<br>
	 * 
	 * @param source
	 *            字符串,格式为yyyy-MM-dd HH:mm:ss
	 * @return Date实例
	 */
	public static Date parseDataTime(String source) {
		try {
			return new SimpleDateFormat(datetimeformatter).parse(source);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 根据表达式计算开始时间,时间以毫秒形式标识
	 * 
	 * @param exp
	 *            时间表达式<br />
	 *            格式：年-月-日-星期-时-分
	 * @param period
	 *            重复/间隔时间，以分为单位
	 * @return 时间
	 */
	public static long nextTime(String exp, int period) {
		return nextDate(exp, period).getTime();
	}

	/**
	 * 根据表达式计算开始时间，时间以Date格式标识
	 * 
	 * @param exp
	 *            时间表达式<br />
	 *            格式：年-月-日-星期-时-分
	 * @param period
	 *            重复/间隔时间，以分为单位
	 * @return 时间
	 */
	public static Date nextDate(String exp, int period) {
		Date time;
		try {
			String[] timer = exp.split("-");

			// Get the User provided Time
			Calendar userCal = Calendar.getInstance();
			// Get System Calendar Date
			Calendar sys = Calendar.getInstance();
			if ("*".equals(timer[0]))
				userCal.set(Calendar.YEAR, sys.get(java.util.Calendar.YEAR));
			else
				userCal.set(Calendar.YEAR, Integer.parseInt(timer[0]));
			if ("*".equals(timer[1]))
				userCal.set(Calendar.MONTH, sys.get(java.util.Calendar.MONTH));
			else
				userCal.set(Calendar.MONTH, Integer.parseInt(timer[1]));
			if ("*".equals(timer[2]))
				userCal.set(Calendar.DAY_OF_MONTH,
						sys.get(Calendar.DAY_OF_MONTH));
			else
				userCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(timer[2]));
			if ("*".equals(timer[3]))
				userCal.set(Calendar.DAY_OF_WEEK,
						sys.get(java.util.Calendar.DAY_OF_WEEK));
			else
				userCal.set(Calendar.DAY_OF_WEEK,
						Integer.parseInt(timer[3]) + 1);
			if ("*".equals(timer[4]))
				userCal.set(Calendar.HOUR_OF_DAY,
						sys.get(java.util.Calendar.HOUR_OF_DAY));
			else
				userCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timer[4]));
			if ("*".equals(timer[5]))
				userCal.set(Calendar.MINUTE, sys.get(java.util.Calendar.MINUTE));
			else
				userCal.set(Calendar.MINUTE, Integer.parseInt(timer[5]));
			userCal.set(Calendar.SECOND, 0);
			// Compare the two dates.
			while (userCal.getTime().getTime() < sys.getTime().getTime()) {
				// Time has passed. Next Occur Time
				userCal.add(Calendar.MINUTE, period);
			}

			// Set the time object
			time = userCal.getTime();
		} catch (Exception ex) {
			ex.printStackTrace();
			time = new Date();
		}
		return time;
	}

	/**
	 * 获取本周第一天
	 * 
	 * @return 周初字符串,格式yyyy-MM-dd
	 */
	public static String beginingOfWeek() {
		Calendar c = Calendar.getInstance();
		int plus = 0;
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			plus = 0;
		} else if (dayOfWeek == 0) {// 这是周日（判断为上一周）
			plus = -6;
		} else {
			plus = 1 - dayOfWeek;
		}
		c.add(Calendar.DATE, plus);
		return new SimpleDateFormat(dayformatter).format(c.getTime());
	}

	/**
	 * 获取当前月份值<br>
	 * 从1开始
	 * 
	 * @return 月份
	 */
	public static int month() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取月初字符串
	 * 
	 * @param month
	 *            月份,1开始
	 * @return 月初字符串,格式yyyyMMdd
	 */
	public static String beginingOfMonth(int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(dateformatter).format(c.getTime());
	}

	/**
	 * 获取月份最后一天
	 * 
	 * @param month
	 *            月份,1开始
	 * @return 月末符串,格式yyyyMMdd
	 */
	public static String endOfMonth(int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat(dateformatter).format(c.getTime());
	}

	/**
	 * 获取当天的0点
	 * 
	 * @return 日期
	 */
	public static Date startOfDay() {
		Calendar c = Calendar.getInstance(); // 得到当前日期和时间
		c.set(Calendar.HOUR_OF_DAY, 0); // 把当前时间小时变成０
		c.set(Calendar.MINUTE, 0); // 把当前时间分钟变成０
		c.set(Calendar.SECOND, 0); // 把当前时间秒数变成０
		c.set(Calendar.MILLISECOND, 0); // 把当前时间毫秒变成０
		return c.getTime(); // 创建当天的0时0分0秒一个date对象
	}

	/**
	 * 获得当天24:00时间
	 * 
	 * @return 日期
	 */
	public static Date endOfDay() {
		Calendar c = Calendar.getInstance(); // 得到当前日期和时间
		c.set(Calendar.HOUR_OF_DAY, 23); // 把当前时间小时变成０
		c.set(Calendar.MINUTE, 59); // 把当前时间分钟变成０
		c.set(Calendar.SECOND, 59); // 把当前时间秒数变成０
		c.set(Calendar.MILLISECOND, 999); // 把当前时间毫秒变成０
		return c.getTime(); // 创建当天的0时0分0秒一个date对象
	}

	/**
	 * 获取指定时间的0点
	 * 
	 * @param time
	 *            时间,格式为yyyy-MM-dd HH:mm:ss
	 * @return 日期
	 */
	public static Date startOfDay(String time) {
		Date now = parseDataTime(time);
		Calendar c = Calendar.getInstance(); // 得到当前日期和时间
		c.setTime(now);
		c.set(Calendar.HOUR_OF_DAY, 0); // 把当前时间小时变成０
		c.set(Calendar.MINUTE, 0); // 把当前时间分钟变成０
		c.set(Calendar.SECOND, 0); // 把当前时间秒数变成０
		c.set(Calendar.MILLISECOND, 0); // 把当前时间毫秒变成０
		return c.getTime(); // 创建当天的0时0分0秒一个date对象
	}

	/**
	 * 获取指定时间的0点
	 * 
	 * @param time
	 *            时间
	 * @return 日期
	 */
	public static Date startOfDay(long time) {
		return startOfDay(formatDateTime(time));
	}

	/**
	 * 取得指定时间的long型数据
	 * 
	 * @param day
	 *            天
	 * @param hour
	 *            小时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return 时间
	 */
	public static long getTime(int day, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance(); // 得到当前日期和时间
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + day);
		c.set(Calendar.HOUR_OF_DAY, hour); // 设置小时
		c.set(Calendar.MINUTE, minute); // 设置分钟
		c.set(Calendar.SECOND, second); // 设置秒数
		c.set(Calendar.MILLISECOND, 0); // 把当前时间毫秒变成０
		return c.getTime().getTime();
	}

	/**
	 * 取得指定时间的long型数据
	 * 
	 * @param hour
	 *            小时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @return 时间
	 */
	public static long getTime(int hour, int minute, int second) {
		Calendar c = Calendar.getInstance(); // 得到当前日期和时间
		c.set(Calendar.HOUR_OF_DAY, hour); // 设置小时
		c.set(Calendar.MINUTE, minute); // 设置分钟
		c.set(Calendar.SECOND, second); // 设置秒数
		c.set(Calendar.MILLISECOND, 0); // 把当前时间毫秒变成０
		return c.getTime().getTime();
	}

	/**
	 * 取得当前是周几<br/>
	 * 1：周日 2：周一 3：周二 ... 7：周六
	 * 
	 * @return 周几
	 */
	public static int getWeek() {
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 比较当前时间是否在指定两个时间之间
	 * 
	 * @param start
	 *            开始时间,格式yyyy-MM-dd HH:mm:ss
	 * @param end
	 *            结束时间,格式yyyy-MM-dd HH:mm:ss
	 * @return 是否在时间之间
	 */
	public static boolean between(String start, String end) {
		Date d1 = parseDataTime(start);
		Date d2 = parseDataTime(end);
		Date now = new Date();
		if (d1.before(now) && now.before(d2)) {
			return true;
		}

		return false;
	}

	/**
	 * 比较当前时间是否在指定两个时间之间
	 * 
	 * @param start
	 *            开始时间的毫秒数。
	 * @param end
	 *            结束时间的毫秒数。
	 * @return 是否在时间之间
	 */
	public static boolean between(long start, long end) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTimeInMillis(start);
		Calendar endCal = Calendar.getInstance();
		endCal.setTimeInMillis(end);
		Calendar now = Calendar.getInstance();
		if (startCal.before(now) && now.before(endCal)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取指定两个时间的间隔天数<br>
	 * 不分起止时间的数序.注意:本方法是按照过了0:00就算一天来计算间隔天数
	 * 
	 * @param time1
	 *            时间1。
	 * @param time2
	 *            时间2。
	 * @return 指定两个时间的间隔天数,不分起止时间的数序,返回正数
	 */
	public static int getIntervalDays(long time1, long time2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(time2);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		return Math
				.abs((int) ((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / DAY));
	}

	/**
	 * 比较两个时间是否为同一天
	 * 
	 * @param timeA
	 *            时间A
	 * @param timeB
	 *            时间B
	 * @return 是否为同一天
	 */
	public static boolean isSameDay(long ta, long tb) {
		return formatDate(ta).equals(formatDate(tb));
	}

	/**
	 * 格式化时间戳
	 * 
	 * @param time
	 *            时间
	 * @param pattern
	 *            格式
	 * @return 制定格式的时间字符串
	 */
	public static String formatTime(long time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);

		return format.format(new Date(time));
	}

	/**
	 * 根据时分秒配置 获取今天配置时间点
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param pattern
	 *            字符串格式
	 * @return 时间
	 * @throws ParseException
	 */
	public static long formatDateStr(String dateStr, String pattern)
			throws ParseException {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(pattern);
		return bartDateFormat.parse(dateStr).getTime();
	}
}
