package ru.qmbo.warranty.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DataService class.
 *
 * @author Victor Egorov (qrioflat@gmail.com).
 * @version 0.1
 * @since 08.09.2022
 */
@Log4j2
@Service
public class DateUtility {

    /**
     * Pars date or return current date.
     *
     * @param date the date
     * @return the date
     */
    public static Date parsDateOrCurrent(final String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final Date result = calendar.getTime();
        if (date != null && !date.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                result.setTime(dateFormat.parse(date).getTime());
            } catch (ParseException e) {
                log.error("Data parsing error. {}", e.getMessage());
            }
        }
        return result;
    }

    /**
     * Calculate build date by serial number.
     *
     * @param serialNumber the serial number
     * @return the date
     */
    public static Date calcBuildDate(final String serialNumber) {
        Date result = null;
        try {
            int year = Integer.parseInt(serialNumber.substring(9, 11));
            int week = Integer.parseInt(serialNumber.substring(11, 13));
            Calendar calendar = Calendar.getInstance();
            calendar.setMinimalDaysInFirstWeek(4);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
            calendar.add(Calendar.YEAR, year);
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if (calendar.compareTo(Calendar.getInstance()) <= 0) {
                result = calendar.getTime();
            }
        } catch (NumberFormatException e) {
            log.warn("Convert serial number to build date error. {}", e.getMessage());
        }
        return result;
    }
}
