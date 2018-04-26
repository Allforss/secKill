package com.sukidesu.server.common.utils;


import com.sukidesu.server.common.Constants.Constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * LocalDateTime 工具类
 */
public class LocalDateTimeUtils {

    private static final int MONTH_NUM = 12;

    /**
     * Date转换为LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        if (Objects.isNull(date)){
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param time
     * @return
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        if (Objects.isNull(time)){
            return null;
        }
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定时间的毫秒
     *
     * @param time
     * @return
     */
    public static long getMilliByTime(LocalDateTime time) {
        if (Objects.isNull(time)){
            return 0L;
        }
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定时间的秒
     *
     * @param time
     * @return
     */
    public static long getSecondsByTime(LocalDateTime time) {
        if (Objects.isNull(time)){
            return 0L;
        }
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取指定时间的指定格式
     *
     * @param time LocalDateTime时间
     * @param pattern 格式
     * @return String
     */
    public static String format(LocalDateTime time, String pattern) {
        if (Objects.isNull(time)){
            return Constants.EMPTY_STRING;
        }
        if (Objects.isNull(pattern)){
            pattern = Constants.DateFormat.YYYY_MM_DD;
        }
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前时间的指定格式
     *
     * @param pattern 格式
     * @return String
     */
    public static String formatNow(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    /**
     * 获取两个时间的差  field参数为ChronoUnit.*
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param field     单位(年月日时分秒)
     * @return long型时间差
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {

        if (Objects.isNull(startTime) || Objects.isNull(endTime)){
            return 0L;
        }
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * MONTH_NUM + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 获取一天的开始时间，2017-09-01 00:00:00
     *
     * @param time
     * @return LocalDateTime
     */
    public static LocalDateTime getDayStart(LocalDateTime time) {

        if (Objects.isNull(time)){
            return null;
        }
        return time.withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 获取一天的结束时间，2017-09-01 23:59:59.999999999
     * @param time
     * @return LocalDateTime
     */
    public static LocalDateTime getDayEnd(LocalDateTime time) {
        if (Objects.isNull(time)){
            return null;
        }
        return time.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

}
