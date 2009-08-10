package com.j6.framework.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.j6.framework.util.J;

@Embeddable
public class Time implements Serializable {
	// private static final long serialVersionUID = -9082356106505693557L;

	@Transient
	private Log log = LogFactory.getLog(Time.class);

	@Column(name = "DURATION")
	@org.hibernate.validator.Length(max = 8)
	private String time;

	public Time() {
	}

	public static void main(String a[]) {
		J.printLine("22".matches("[0-9][0-9]"));

		// System.out.println("->exp:00:00:00 actual:" + getCorrectTimeStampFormat(""));
		// System.out.println("2->exp:02:00:00 actual:" + getCorrectTimeStampFormat("2"));
		// System.out.println("22->exp:22:00:00 actual:" + getCorrectTimeStampFormat("22"));
		// System.out.println("22:->exp:22:00:00 actual:" + getCorrectTimeStampFormat("22:"));
		// System.out.println("2:0->exp:02:00:00 actual:" + getCorrectTimeStampFormat("2:0"));
		// System.out.println("22:90->exp:23:30:00 actual:" + getCorrectTimeStampFormat("22:90"));
		// System.out.println("22:59:100->exp:23:00:40 actual:" + getCorrectTimeStampFormat("22:59:100"));
		// System.out.println("22:59:181->exp:23:02:01 actual:" + getCorrectTimeStampFormat("22:59:181"));
		// System.out.println("222222->exp:222222:00:00 actual:" + getCorrectTimeStampFormat("222222"));
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		if (time != null)
			time = time.trim();
		this.time = getCorrectTimeStampFormat(time);
	}

	public int getHour() {
		if (StringUtils.isNotEmpty(time)) {
			String[] splittedTime = time.split(":");
			if (splittedTime.length > 0)
				return Integer.parseInt(splittedTime[0].trim());
		}
		return 0;
	}

	public int getMinute() {
		if (StringUtils.isNotEmpty(time)) {
			String[] splittedTime = time.split(":");
			if (splittedTime.length > 1)
				return Integer.parseInt(splittedTime[1].trim());
		}
		return 0;
	}

	public int getSecond() {
		if (StringUtils.isNotEmpty(time)) {
			String[] splittedTime = time.split(":");
			if (splittedTime.length > 2)
				return Integer.parseInt(splittedTime[2].trim());
		}
		return 0;
	}

	private String getCorrectTimeStampFormat(String time) {
		String corTime = "00:00:00";
		String strHours = "";
		String strMins = "";
		String strSecs = "";

		try {
			if (time.length() > 0) {
				if (time.indexOf(":") > 0) {
					String[] splittedTime = time.split(":");
					if (splittedTime.length == 3) {
						int hours = Integer.parseInt(splittedTime[0]);
						int mins = Integer.parseInt(splittedTime[1]);
						int secs = Integer.parseInt(splittedTime[2]);

						while (secs > 59) {
							secs = secs - 60;
							mins = mins + 1;
						}
						while (mins > 59) {
							mins = mins - 60;
							hours = hours + 1;
						}
						strHours = String.valueOf(hours).length() == 1 ? "0" + String.valueOf(hours) : String
								.valueOf(hours);
						strMins = String.valueOf(mins).length() == 1 ? "0" + String.valueOf(mins) : String
								.valueOf(mins);
						strSecs = String.valueOf(secs).length() == 1 ? "0" + String.valueOf(secs) : String
								.valueOf(secs);

						corTime = strHours + ":" + strMins + ":" + strSecs;
					} else if (splittedTime.length == 2) {
						int hours = Integer.parseInt(splittedTime[0]);
						int mins = Integer.parseInt(splittedTime[1]);

						while (mins > 59) {
							mins = mins - 60;
							hours = hours + 1;
						}
						strHours = String.valueOf(hours).length() == 1 ? "0" + String.valueOf(hours) : String
								.valueOf(hours);
						strMins = String.valueOf(mins).length() == 1 ? "0" + String.valueOf(mins) : String
								.valueOf(mins);

						corTime = strHours + ":" + strMins + ":00";
					} else {
						corTime = splittedTime[0].length() == 1 ? "0" + splittedTime[0] + ":00:00" : splittedTime[0]
								+ ":00:00";
					}
				} else {
					/* only hours */
					corTime = time.length() == 1 ? "0" + time + ":00:00" : time + ":00:00";
				}
			}

		} catch (Exception e) {
			log.warn(time);
			return corTime;
		}
		return corTime;
	}
}
