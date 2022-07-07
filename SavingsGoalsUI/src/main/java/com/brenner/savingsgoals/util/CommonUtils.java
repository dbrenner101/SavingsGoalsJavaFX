package com.brenner.savingsgoals.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class holding helper methods used across scenes
 */
public class CommonUtils {
    
    /** Supports date formatting and parsing in a MM/dd/yyyy format. */
    public static final SimpleDateFormat STD_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    
    /** Supports date formatting and parsing in a yyyy-MM-dd format. */
    public static final SimpleDateFormat ISO_FORMT = new SimpleDateFormat("yyyy-MM-dd");
    
    /** Supports truncating float values to 2 decimal places. */
    private static final DecimalFormat TWO_DECIMAL_PLACES = new DecimalFormat("###.##");
    
    /**
     * Parses date strings to date objects using the STD_FORMAT
     *
     * @param dateString String representation of a date
     * @return The date object
     * @throws ParseException Thrown when the string cannot be parsed to a date object
     */
    public static Date parseStdFormatDateString(String dateString) throws ParseException {
        return STD_FORMAT.parse(dateString);
    }
    
    /**
     * Converts the date object to a string in the STD_FORMAT
     *
     * @param date The date to format
     * @return The string representation of the date
     */
    public static String formatDateToStdString(Date date) {
        return STD_FORMAT.format(date);
    }
    
    /**
     * Truncates the Float value to two decimal places.
     *
     * @param number The Float to truncate
     * @return The string representation of the number with 2 decimal places
     */
    public static String roundToTwoDecimals(Float number) {
        return TWO_DECIMAL_PLACES.format(number);
    }
    
    /**
     * Formats the number as local currency
     *
     * @param number Number to convert
     * @return Current representation
     */
    public static String formatAsCurrency(Float number) {
        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(number);
    }
}
