package com.sso.common.server.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class DateTimeUtils {
    private static final Logger log = LoggerFactory.getLogger(DateTimeUtils.class);

    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
    public static final long MILLISECONDS_IN_SECOND = 1000L;
    public static final DateTimeZone TZ_UTC = DateTimeZone.UTC;
    public static final Calendar CALENDAR_UTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    public static final long ONE_DAY_IN_SEC = TimeUnit.DAYS.toSeconds(1);
    public static final long ONE_MIN_IN_SEC = TimeUnit.MINUTES.toMillis(1);

    public static Date getCurrentGMTDate() {
        Calendar calendar = Calendar.getInstance(UTC_TIME_ZONE);
        return calendar.getTime();
    }

    public static Date getCurrentGMTDateWithoutTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.parse(dateFormat.format(getCurrentGMTDate()));
    }

    public static Date getGMTDateWithoutTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(UTC_TIME_ZONE);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static TimeZone getMSKTimeZone() {
        TimeZone msk = TimeZone.getTimeZone("Europe/Moscow");
        msk.setRawOffset(3 * 60 * 60 * 1000);
        return msk;
    }

    public static long toSeconds(DateTime value) {
        log.debug(">>toSeconds {}", value);

        if (value == null) {
            return 0L;
        }

        log.debug("<<toSeconds");
        return value.getMillis() / MILLISECONDS_IN_SECOND;
    }

    public static DateTime timestampToDateTime(Timestamp ts) {
        log.debug(">>timestampToDateTime {}", ts);

        return new DateTime(ts, TZ_UTC);
    }

    public static LocalDate dateToLocalDate(java.sql.Date d) {
        log.debug(">>dateToLocalDate {}", d);

        return new LocalDate(d, TZ_UTC);
    }

    public static DateTime dateTimeFromSeconds(Long seconds) {
        log.debug(">>dateTimeFromSeconds {}", seconds);

        if(seconds==null) {
            return null;
        }

        log.debug("<<dateTimeFromSeconds");
        return new DateTime(seconds * MILLISECONDS_IN_SECOND, TZ_UTC);
    }

    public static DateTime dateTimeFromMilliSeconds(Long milliSeconds) {
        log.debug(">>dateTimeFromMilliSeconds {}", milliSeconds);

        if(milliSeconds==null) {
            return null;
        }

        log.debug("<<dateTimeFromMilliSeconds");
        return new DateTime(milliSeconds, TZ_UTC);
    }

    public static Long getTimeInSecFromLocalDate(LocalDate date) {
        log.debug(">>getTimeInSecFromLocalDate {}", date);

        Long timeInSecFromLocalDate =
            date != null ? (date.toDate().getTime() / MILLISECONDS_IN_SECOND) : null;

        log.debug("<<getTimeInSecFromLocalDate");
        return timeInSecFromLocalDate;
    }

    public static Long getBeginOfDateInSecond(Long date) {
        log.debug(">>getBeginOfDateInSecond {}", date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        log.debug("<<getBeginOfDateInSecond");
        return calendar.getTimeInMillis() / MILLISECONDS_IN_SECOND;
    }

    public static List<Long> getLastWeekDateList(Long dateInSecond) {
        log.debug(">>getLastWeekDateList {}", dateInSecond);

        final int SEVEN_DAYS = 7;
        List<Long> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long dateInSeconds = calendar.getTimeInMillis() / MILLISECONDS_IN_SECOND;
        for (int i = 0; i < SEVEN_DAYS; i++) {
            dateList.add(dateInSeconds);
            dateInSeconds -= ONE_DAY_IN_SEC;
            if (dateInSecond != null && dateInSecond > dateInSeconds) {
                break;
            }
        }

        log.debug("<<getLastWeekDateList");
        return dateList;
    }

    public static LocalDate getLocalDateFromSeconds(long seconds) {
        log.debug(">>getLocalDateFromSeconds {}", seconds);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(seconds * MILLISECONDS_IN_SECOND);

        log.debug("<<getLocalDateFromSeconds");
        return LocalDate.fromCalendarFields(calendar);
    }

    public static long getDateFromString(String movesDate) {
        log.debug(">>getDateFromString {}", movesDate);

        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS");
        try {
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = df.parse(movesDate);
        } catch (ParseException e) {
            log.warn("problem appear during a date parsing", e);
            throw new RuntimeException("Error parse date", e);
        }

        log.debug("<<getDateFromString");
        return date.getTime();
    }

    public static LocalDate getLocalDateFromString(String movesDate) {
        log.debug(">>getLocalDateFromString {}", movesDate);

        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = df.parse(movesDate);
        } catch (ParseException e) {
            log.warn("problem appear during a date parsing", e);
            throw new RuntimeException("Error parse date", e);
        }

        log.debug("<<getLocalDateFromString");
        return LocalDate.fromDateFields(date);
    }

    public static String getStringFromDate(Long date) {
        log.debug(">>getStringFromDate {}", date);

        log.debug("getStringFromDate({})", date);

        final String DATE_FORMAT = "yyyy-MM-dd";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String dateStr = df.format(calendar.getTime());

        log.debug("getStringFromDate has completed");

        log.debug("<<getStringFromDate");
        return dateStr;
    }

    public static long getSecondFromString(String time) {
        log.debug(">>getSecondFromString {}", time);

        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            date = df.parse(time);
        } catch (ParseException e) {
            log.warn("Error: ", e);
            log.warn("problem appear during a date parsing {}", time);
        }

        log.debug("<<getSecondFromString");
        return date.getTime() / MILLISECONDS_IN_SECOND;
    }

    public static Long getStartOfPeriod(DateTime end, Long duration) {
        if (duration==null) return null;
        if (end==null) {
            end = DateTime.now();
        }
        return end.getMillis()/1000 - duration;
    }
}
