package com.abb.rockpaperscissor.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Abarajithan
 */
public class DateUtil {

    public static String format(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault()).format(date);
    }

}
