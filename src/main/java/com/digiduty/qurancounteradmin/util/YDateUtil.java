package com.digiduty.qurancounteradmin.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class YDateUtil {
    public static Date stringToddMMyyyy(String stringDate) {
        if (StringUtils.isNotEmpty(stringDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate = LocalDate.parse(stringDate, formatter);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return date;
        }
        return null;
    }

    public static Date stringToddMMyyyyForwardSlash(String stringDate) {
        if (StringUtils.isNotEmpty(stringDate)) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy");
                LocalDateTime dateTime = LocalDateTime.parse(stringDate, inputFormatter);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate = dateTime.format(outputFormatter);
                return stringToddMMyyyy(formattedDate);
            } catch (DateTimeParseException e) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yy");
                try {
                    LocalDate localDate = LocalDate.parse(stringDate, formatter);
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    return date;
                } catch (DateTimeParseException ex) {
                    System.out.println("Could not parse the date: " + ex.getMessage());
                }
            }
        }
        return null;
    }
}
