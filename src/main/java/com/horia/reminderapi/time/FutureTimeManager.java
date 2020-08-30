package com.horia.reminderapi.time;

import java.util.Calendar;
import java.util.Date;

public class FutureTimeManager {

    private static Calendar calendar = Calendar.getInstance();

    public static Date getFutureIncrementedBySeconds(int seconds) {
        calendar.clear();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, seconds);
        System.out.println(calendar.getTime());
        return calendar.getTime();
    }

}
