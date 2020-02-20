package com.kshrd.configuration.utility;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.HOUR;

public class DateExpire {
    public static Date getDateExpire(int minute){
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.add(Calendar.MINUTE, minute); // adds minute
        return cal.getTime();
    }
}
