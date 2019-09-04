package com.abb.rockpaperscissor.db;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by Abarajithan
 */
public class DateConverter {

    @TypeConverter
    public Date fromTimestamp(Long ts) {
        return (ts == null) ? null : new Date(ts);
    }

    @TypeConverter
    public Long fromDate(Date date) {
        return (date == null) ? null : date.getTime();
    }

}
