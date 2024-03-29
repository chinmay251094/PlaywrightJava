package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateTimeUtils {
    private static final String format = "MM/d/yyyy";

    private DateTimeUtils() {
    }

    public static String getDateTime() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH-mm-ss");
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }

    public static String getSystemDate() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMMMMM dd, yyyy");
        Date resultdate = new Date(yourmilliseconds);
        return String.valueOf(sdf.format(resultdate));
    }

    public static String addDays() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MMMMMMM dd, yyyy");

        c.setTime(new Date());
        c.add(Calendar.DATE, 10);
        return dateOnly.format(c.getTime());
    }


    public static String getFirstDayOfWeek() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }

        return monday.format(formatter);
    }

    public static String getFirstDayOfLastWeek() {
        LocalDate today = LocalDate.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }

        return monday.format(formatter);
    }

    public static String getLastDayOfWeek() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }

        return sunday.format(formatter);
    }

    public static String getLastDayOfLastWeek() {
        LocalDate today = LocalDate.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return sunday.format(formatter);
    }

    public static String getFirstDayOfMonth() {
        LocalDate today = LocalDate.now().withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }

        return monday.format(formatter);
    }

    public static String getFirstDayOfYear() {
        LocalDate date = LocalDate.of(2021, Month.JANUARY, 01);
        LocalDate firstDayOfYear = date.with(TemporalAdjusters.firstDayOfYear());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return firstDayOfYear.format(formatter);
    }

    public static Date convertStringToDate(String dates) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.parse(dates);
    }
}

