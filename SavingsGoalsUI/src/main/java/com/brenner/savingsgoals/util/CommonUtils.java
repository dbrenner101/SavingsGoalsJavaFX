package com.brenner.savingsgoals.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {
    
    public static final SimpleDateFormat STD_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat ISO_FORMT = new SimpleDateFormat("yyyy-MM-dd");
    
    private static final DecimalFormat TWO_DECIMAL_PLACES = new DecimalFormat("###.##");
    
    public static Date parseStdFormatDateString(String dateString) throws ParseException {
        return STD_FORMAT.parse(dateString);
    }
    
    public static String formatDateToStdString(Date date) {
        return STD_FORMAT.format(date);
    }
    
    public static String roundToTwoDecimals(Float number) {
        return TWO_DECIMAL_PLACES.format(number);
    }
    
    public static String formatAsCurrency(Float number) {
        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(number);
    }
}
