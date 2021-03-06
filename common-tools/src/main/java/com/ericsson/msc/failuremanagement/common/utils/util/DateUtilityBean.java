package com.ericsson.msc.failuremanagement.common.utils.util;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date utility containing Date-related static methods.
 */
@Local
@Stateless
public class DateUtilityBean {
    /**
     * Convert a Date/Time object into an application-appropriate string of the
     * UK form (24-hour clock): "dd/mm/yyyy hh:mm"
     *
     * @param dateTime The date object to covert into a string.
     * @return Date time string in the UK locale ("dd/mm/yyyy hh:mm").
     */
    public String formatDateAsString(Date dateTime) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.UK);
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT,
                Locale.UK);
        if (dateTime == null) {
            System.out.println("Date time null :(");
        }
        String dateTimeString = dateFormat.format(dateTime) + " " + timeFormat.format(dateTime);
        //
        // java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
        // "dd/MM/yyyy, HH:mm");
        //
        // String date = sdf.format(dateTimeString);
        return dateTimeString;
    }

    public Timestamp formatDateStringAsTimestamp(String inputDateString) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(inputDateString);
        } catch (ParseException e) {
            return null;
        }
        Timestamp timestamp = new Timestamp(parsedDate.getTime());

        return timestamp;
    }
}
