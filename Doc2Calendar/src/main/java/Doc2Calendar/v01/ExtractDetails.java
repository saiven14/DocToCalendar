package Doc2Calendar.v01;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to extract the event details. Keywords, regex etc. are
 * used to capture the relevant details (mainly summary, date, time,
 * description.) in order to create a calendar entry. This is a basic code,
 * enhancements need to be made to suit more complex applications.
 * 
 * @author saiven
 *
 */

public class ExtractDetails {

	public static StringBuffer EventsGetter(ArrayList<String> doclist) {

		StringBuffer icsbuild = new StringBuffer();
		boolean descflag = false;
		String summary = "";
		String day = "";
		String month = "";
		String year = "";
		String time = "";
		String time_off_set = "";
		String hour = "";
		String minute = "";
		String second = "00";
		int count = 0;

		icsbuild.append("BEGIN:VCALENDAR");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("VERSION:2.0");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("PRODID:-DocToCalendar");
		icsbuild.append(System.getProperty("line.separator"));

		for (String list_line : doclist) {
			if (list_line.isEmpty()) {
				continue;
			}
			if (list_line.toLowerCase().contains("topic")) {
				descflag = false;
				if (!descflag && count != 0) {
					icsbuild.append("LOCATION:Online");
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("ORGANIZER;");
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("STATUS:CONFIRMED");
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("TRANSP:OPAQUE");
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("BEGIN:VALARM");
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("ACTION:DISPLAY");
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("DESCRIPTION:" + summary);
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("TRIGGER:-PT15M");
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("END:VALARM");
					icsbuild.append(System.getProperty("line.separator"));
					icsbuild.append("END:VEVENT");
					icsbuild.append(System.getProperty("line.separator"));
				}
				count++;
				icsbuild.append("BEGIN:VEVENT");
				icsbuild.append(System.getProperty("line.separator"));
				icsbuild.append("SUMMARY:" + list_line);
				icsbuild.append(System.getProperty("line.separator"));
				summary = list_line;
			} else if (list_line.toLowerCase().contains("date")) {
				descflag = false;
				final String regexyear = "\\d\\d\\d\\d";
				final Pattern pattern = Pattern.compile(regexyear, Pattern.MULTILINE);
				final Matcher matcher = pattern.matcher(list_line);
				while (matcher.find()) {

					for (int i = 1; i <= matcher.groupCount(); i++) {

					}
					year = matcher.group(0).replaceAll("[^0-9]", "");
				}

				final String regexmonth = "[^\\d]\\d[^\\d]";
				final Pattern pattern1 = Pattern.compile(regexmonth, Pattern.MULTILINE);
				final Matcher matcher1 = pattern1.matcher(list_line);
				while (matcher1.find()) {

					for (int i = 1; i <= matcher1.groupCount(); i++) {

					}
					day = matcher1.group(0).replaceAll("[^0-9]", "");
				}

				final String regexmonth2 = "[^\\d]\\d\\d[^\\d]";
				final Pattern pattern2 = Pattern.compile(regexmonth2, Pattern.MULTILINE);
				final Matcher matcher2 = pattern2.matcher(list_line);
				while (matcher2.find()) {

					for (int i = 1; i <= matcher2.groupCount(); i++) {

					}
					day = String.format("%02d", Integer.parseInt(matcher2.group(0).replaceAll("[^0-9]", "").trim()));
				}

				if (list_line.toLowerCase().contains("jan"))
					month = "01";
				else if (list_line.toLowerCase().contains("feb"))
					month = "02";
				else if (list_line.toLowerCase().contains("mar"))
					month = "03";
				else if (list_line.toLowerCase().contains("apr"))
					month = "04";
				else if (list_line.toLowerCase().contains("may"))
					month = "05";
				else if (list_line.toLowerCase().contains("jun"))
					month = "06";
				else if (list_line.toLowerCase().contains("jul"))
					month = "07";
				else if (list_line.toLowerCase().contains("aug"))
					month = "08";
				else if (list_line.toLowerCase().contains("sep"))
					month = "09";
				else if (list_line.toLowerCase().contains("oct"))
					month = "10";
				else if (list_line.toLowerCase().contains("nov"))
					month = "11";
				else if (list_line.toLowerCase().contains("dec"))
					month = "12";

			} else if (list_line.toLowerCase().contains("time")) {
				descflag = false;
				final String regextime = "[^+|^\\d]\\d[:]\\d\\d";
				final Pattern pattern = Pattern.compile(regextime, Pattern.MULTILINE);
				final Matcher matcher = pattern.matcher(list_line);
				while (matcher.find()) {

					for (int i = 1; i <= matcher.groupCount(); i++) {

					}
					time = matcher.group(0);
				}

				final String regextime2 = "[^+|^\\d]\\d\\d[:]\\d\\d";
				final Pattern pattern1 = Pattern.compile(regextime2, Pattern.MULTILINE);
				final Matcher matcher1 = pattern1.matcher(list_line);
				while (matcher1.find()) {

					for (int i = 1; i <= matcher1.groupCount(); i++) {

					}
					time = matcher1.group(0);
				}

				final String regextime3 = "[+]\\d\\d[:]\\d\\d";
				final Pattern pattern2 = Pattern.compile(regextime3, Pattern.MULTILINE);
				final Matcher matcher2 = pattern2.matcher(list_line);
				while (matcher2.find()) {

					for (int i = 1; i <= matcher2.groupCount(); i++) {

					}
					time_off_set = matcher2.group(0);
				}

				if (list_line.toLowerCase().contains("pm")) {
					hour = String.format("%02d", Integer.parseInt(time.substring(0, time.indexOf(":")).trim()) + 12);
				} else {
					hour = String.format("%02d", Integer.parseInt(time.substring(0, time.indexOf(":")).trim()));
				}
				minute = String.format("%02d", Integer.parseInt(time.substring(time.indexOf(":") + 1).trim()));
				String endhour = String.format("%02d", Integer.parseInt(hour) + 1);

				icsbuild.append("DTSTART;VALUE=DATE-TIME:" + year + month + day + "T" + hour + minute + second);
				icsbuild.append(System.getProperty("line.separator"));
				icsbuild.append("DTEND;VALUE=DATE-TIME:" + year + month + day + "T" + endhour + minute + second);
				icsbuild.append(System.getProperty("line.separator"));
				icsbuild.append("DTSTAMP;VALUE=DATE-TIME:"
						+ LocalDateTime.now().toString().replaceAll("[^0-9,T]", "").substring(0, 15));
				icsbuild.append(System.getProperty("line.separator"));
				icsbuild.append("UID:Doc2Calendar-" + count);
				icsbuild.append(System.getProperty("line.separator"));

			} else {
				if (descflag) {
					icsbuild.append(" " + list_line);
					icsbuild.append(System.getProperty("line.separator"));
				} else {
					icsbuild.append("DESCRIPTION:" + list_line);
					icsbuild.append(System.getProperty("line.separator"));
					descflag = true;
				}

			}

		}

		icsbuild.append("LOCATION:Online");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("ORGANIZER;");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("STATUS:CONFIRMED");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("TRANSP:OPAQUE");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("BEGIN:VALARM");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("ACTION:DISPLAY");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("DESCRIPTION:" + summary);
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("TRIGGER:-PT15M");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("END:VALARM");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("END:VEVENT");
		icsbuild.append(System.getProperty("line.separator"));
		icsbuild.append("END:VCALENDAR");

		return icsbuild;

	}
}
