package com.beacon.zohaib.beacon.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Zohaib on 3/1/2018.
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
