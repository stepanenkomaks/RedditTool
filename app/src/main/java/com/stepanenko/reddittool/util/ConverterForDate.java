package com.stepanenko.reddittool.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ConverterForDate {

    public static String convert(Long createdAt) {
        long timeDifference = new Date().getTime() - createdAt * 1000;

        if (timeDifference > 0 && timeDifference< TimeUnit.HOURS.toMillis(1))
            return TimeUnit.MILLISECONDS.toMinutes(timeDifference) + " minutes ago";
        else if (timeDifference > TimeUnit.HOURS.toMillis(1) && timeDifference < TimeUnit.DAYS.toMillis(1))
            return TimeUnit.MILLISECONDS.toHours(timeDifference) + " hours ago";
        else
            return TimeUnit.MILLISECONDS.toDays(timeDifference) + " days ago";
    }
}
