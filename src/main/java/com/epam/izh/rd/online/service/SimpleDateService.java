package com.epam.izh.rd.online.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleDateService implements DateService {

    /**
     * Метод парсит дату в строку
     *
     * @param localDate дата
     * @return строка с форматом день-месяц-год(01-01-1970)
     */
    @Override
    public String parseDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        return localDate.format(formatter);
    }

    /**
     * Метод парсит строку в дату
     *
     * @param string строка в формате год-месяц-день часы:минуты (1970-01-01 00:00)
     * @return дата и время
     */
    @Override
    public LocalDateTime parseString(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                string = string.replace(string.charAt(i), 'T');
            }
        }

        return LocalDateTime.parse(string);
    }

    /**
     * Метод конвертирует дату в строку с заданным форматом
     *
     * @param localDate исходная дата
     * @param formatter формат даты
     * @return полученная строка
     */
    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        return localDate.format(formatter);
    }

    /**
     * Метод получает следующий високосный год
     *
     * @return високосный год
     */
    @Override
    public long getNextLeapYear() {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int value = 0;

        for (int i = 1; i < 5; i++) {
            value = year + i;
            if (((value % 100) == 0 && (value % 400) == 0) || ((value % 4) == 0 && (value % 100) != 0)) {
                break;
            }
        }

        return value;
    }

    /**
     * Метод считает число секунд в заданном году
     *
     * @return число секунд
     */
    @Override
    public long getSecondsInYear(int year) {
        long value = 0L;

        if (((year % 100) == 0 && (year % 400) == 0) || ((year % 4) == 0 && (year % 100) != 0)) {
            value = 366L * 24L * 60L * 60L;
        } else {
            value = 365L * 24L * 60L * 60L;
        }
        return value;
    }


}
