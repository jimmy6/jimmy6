package com.j6.framework.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.j6.framework.vo.Time;

/**
 * This is the date utilitise.
 * 
 */
public class DateUtil {

	public static Calendar setTime(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date getStartDateOfWeek(Date date) {
		Calendar calendarStartDate = DateUtil.setTime(date);
		Integer noOfDayOfWeek = calendarStartDate.get(Calendar.DAY_OF_WEEK) - 1;
		calendarStartDate.add(Calendar.DATE, -noOfDayOfWeek);
		// J.printPositif(noOfDayOfWeek+"..."+calendarStartDate.getTime());

		return calendarStartDate.getTime();
	}

	/**
	 * add time to date.
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date addTime(Date date, Time time) {
		Calendar calendar = setTime(date);
		calendar.add(Calendar.SECOND, time.getSecond());
		calendar.add(Calendar.MINUTE, time.getMinute());
		calendar.add(Calendar.HOUR, time.getHour());
		return calendar.getTime();
	}

	//
	// public static Date getExpDate(Date date, int month) {
	//    	
	// Calendar calendar = setTime(date);
	// calendar.add(Calendar.MONTH, month + 1);
	// calendar.set(Calendar.DAY_OF_MONTH, 1);
	// calendar.add(Calendar.DAY_OF_MONTH, -1);
	// return DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);
	// }

	public static Date formatDateByTime(Date date, int hour, int minute, int second, int milisecond) {
		Calendar calendar = setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, milisecond);

		return calendar.getTime();
	}

	public static Date formatDateToEndTime(Date date) {
		return formatDateByTime(date, 23, 59, 59, 999);
	}

	/**
	 * erase all time. set all time fields to 0.
	 * 
	 * @param date
	 * @return
	 */
	public static Date truncateTime(Date date) {
		return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * Return those date in dates which is between fromDate and toDate.
	 * 
	 * @return
	 */
	public static List<Date> getDateBetween(List<Date> dates, Date fromDate, Date toDate) {
		List<Date> retDateList = new ArrayList<Date>();

		for (Date date : dates) {
			if (fromDate.equals(date) || toDate.equals(date) || date.after(fromDate) && date.before(toDate)) {
				retDateList.add(date);
			}
		}

		if (retDateList.size() > 1) {
			Collections.sort(retDateList);
		}
		return retDateList;
	}

	/**
	 * Calculate Age base on date of birth until onDate.
	 * 
	 * @param dob
	 * @return
	 */
	public static int countAge(Date dob, Date onDate) {

		// Create a calendar object with today's date
		Calendar today = Calendar.getInstance();
		today.setTime(DateUtil.truncateTime(onDate));
		Calendar calendarDob = Calendar.getInstance();
		calendarDob.setTime(DateUtil.truncateTime(dob));

		// Get age based on year
		int age = today.get(Calendar.YEAR) - calendarDob.get(Calendar.YEAR);

		// Add the tentative age to the date of birth to get this year's birthday
		calendarDob.add(Calendar.YEAR, age);

		// If this year's birthday has not happened yet, subtract one from age
		if (today.before(calendarDob)) {
			age--;
		}
		return age;
	}

	/**
	 * Not Consider time. Just compare date.
	 * 
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isBetweenDate(Date date, Date startDate, Date endDate) {
		Date clonedDate = (Date) date.clone();
		clonedDate = formatDateByTime(clonedDate, 0, 0, 0, 0);

		Date clonedStartDate = (Date) startDate.clone();
		Date clonedEndDate = (Date) endDate.clone();

		clonedStartDate = formatDateByTime(clonedStartDate, 0, 0, 0, 0);
		clonedEndDate = formatDateByTime(clonedEndDate, 23, 59, 59, 999);

		return (clonedDate.equals(clonedStartDate) || clonedDate.after(clonedStartDate))
				&& (clonedDate.equals(clonedEndDate) || clonedDate.before(clonedEndDate));
	}

	/**
	 * consider time and date.
	 * 
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isBetweenDateTime(Date date, Date startDate, Date endDate) {
		return (date.equals(startDate) || date.after(startDate)) && (date.equals(endDate) || date.before(endDate));
	}

	// public static void main(String[] a){
	// //test isBetweenDate
	// J.printPositif(isBetweenDate(formatDateByTime(new Date(),0,0,0,0), new Date(), new Date()));//true
	// J.printPositif(isBetweenDate(formatDateByTime(new Date(),23,59,59,998), new Date(), new Date()));//true
	// J.printPositif(isBetweenDate(new Date(), new Date(), new Date()));//true
	//		
	// Calendar calendar = setTime(new Date());
	// calendar.set(Calendar.DAY_OF_MONTH, 1);
	// J.printPositif(isBetweenDate(calendar.getTime(), new Date(), new Date()));//false
	//		
	// }

	public static boolean isSaturday(Date date) {
		Calendar cal = setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}

	public static boolean isSunday(Date date) {
		Calendar cal = setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	public static Date addDate(Date date, int noOfDays) {
		Calendar cal = setTime(date);
		cal.add(Calendar.DATE, noOfDays);
		return cal.getTime();
	}

	public static long getDaysBetween2Dates(Date dateFrom, Date DateTo) {
		long msDateFrom = DateUtil.truncateTime(dateFrom).getTime();
		long msDateTo = DateUtil.truncateTime(DateTo).getTime();

		return (msDateTo - msDateFrom) / (24 * 3600 * 1000) + 1;
	}

	/**
	 * 
	 * @param year
	 * @param month
	 *            0-11
	 * @return
	 */
	public static List<String> getDayInWeekForMonthIn2Characters(int year, int month) {
		SimpleDateFormat df = new SimpleDateFormat("EE");
		List<String> list = new ArrayList<String>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);

		while (cal.get(Calendar.MONTH) == month) {
			list.add(df.format(cal.getTime()));

			cal.add(Calendar.DATE, 1);
		}

		return list;
	}

	public static List<String> listMonthIn3Characters() {
		SimpleDateFormat df = new SimpleDateFormat("MMM");
		List<String> list = new ArrayList<String>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 0);

		while (cal.get(Calendar.MONTH) < 12) {
			list.add(df.format(cal.getTime()));

			if (cal.get(Calendar.MONTH) == 11)
				break;
			cal.add(Calendar.MONTH, 1);
		}

		return list;
	}
	
	public static void main(String a[]){
		SimpleDateFormat dateFormat = new SimpleDateFormat("E");
		System.out.println(dateFormat.format(new Date()));
		
	}
}
