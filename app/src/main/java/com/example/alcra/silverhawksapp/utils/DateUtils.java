package com.example.alcra.silverhawksapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alcra on 27/05/2018.
 */

public class DateUtils {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String MONTH_FORMAT = "MM";

    public static String getFormatedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

        return simpleDateFormat.format(date);
    }

    public static String getMonth(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MONTH_FORMAT, Locale.getDefault());

        return simpleDateFormat.format(date);
    }
}
