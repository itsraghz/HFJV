package org.hfjv.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hfjv.framework.core.exception.ValidatorException;

/**
 * <p>
 * An utility class to deal with all the date related functionalities
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class DateUtil
{

	/**
	 * <p>
	 * A class level constant to hold the simple date pattern
	 * </p>
	 */
	public static final String DATE_PATTERN_SIMPLE = "yyyy-MM-dd";

	/**
	 * <p>
	 * A class level constant to hold the date pattern with time
	 * </p>
	 */
	public static final String DATE_PATTERN_WITH_TIME = "yyyy-MM-dd hh:mm:ss";

	/**
	 * <p>
	 * A class level constant to mark a default date pattern (simple date patten)
	 * </p>
	 */
	public static final String DEFAULT_DATE_PATTERN = DATE_PATTERN_SIMPLE;

	/**
	 * <p>
	 * A class level constant to hold the simple date output format
	 * </p>
	 */
	public static final String DATE_OUTPUT_FORMAT_SIMPLE = "yyyyMMdd";


	/**
	 * <p>
	 * A class level constant to hold the date pattern with time output format
	 * </p>
	 */
	public static final String DATE_OUTPUT_FORMAT_WITH_TIME = 
																									"yyyyMMddhhmmss";

	/**
	 * <p>
	 * A class level constant to hold the default date output format
	 * </p>
	 */
	public static final String DEFAULT_DATE_OUTPUT_FORMAT =
															DATE_OUTPUT_FORMAT_WITH_TIME;

	/**
	 * <p>
	 * A class level constant to hold the date separator character
	 * </p>
	 */
	public static final String SEPARATOR_TIME_FORMAT = ":" ;

	/**
	 * <p>
	 * This method returns the date equivalent of the input date in string format
	 *</p>
	 *
	 * @param fieldName
	 * 			the fieldName to be returned in the exception if any
	 * 
	 * @param dateInString
	 * 			the input date value represented as <tt>String</tt>
	 * 
	 * @return
	 * 			the date equivalent value after parsing
	 * 
	 * @throws
	 * 			ValidatorException in case of any exception while parsing
	 */
	public static Date getDate(String fieldName, String dateInString)
	throws ValidatorException
	{
		return getDate(fieldName, dateInString, DEFAULT_DATE_PATTERN);
	}

	/**
	 * <p>
	 * This method returns the suitable date format for the string equivalent date
	 * passed as an argument
	 *</p>
	 *
	 * @param dateInString
	 * 		the date value represented as a <tt>String</tt>
	 * 
	 * @return
	 * 		the date equivalent value
	 */
	public static String getSuitableDateFormat(String dateInString)
	{
		String suitableDateFormat = DEFAULT_DATE_PATTERN;

		if(StringUtil.isValidString(dateInString))
		{
			if(dateInString.indexOf(SEPARATOR_TIME_FORMAT) != -1)
			{
				suitableDateFormat = DATE_PATTERN_WITH_TIME;
			}
		}

		return suitableDateFormat;
	}

	/**
	 * <p>
	 * This method returns the date instance as a result of parsing the date value 
	 * represented as a <tt>String</tt>
	 *</p>
	 *
	 * @param fieldName
	 * 		the fieldName to be used in case of any exceptions
	 * 
	 * @param dateInString
	 * 		the date value represented as a <tt>String</tt>
	 * 
	 * @param dateFormat
	 * 		the date format to be considered while parsing the date in String format
	 * 
	 * @return
	 * 		a <tt>java.Util.Date</tt> instance with the parsed date value
	 * 
	 * @throws ValidatorException
	 * 		any exceptions during validation
	 */
	public static Date getDate(String fieldName, String dateInString,
																	String dateFormat)
	throws ValidatorException
	{
		if(!StringUtil.isValidString(dateFormat))
		{
			dateFormat = DEFAULT_DATE_PATTERN;
		}

		/*
		 * get the suitable dateFormat
	 	 * even if the dateInString does contain only date value,
		 * the dateFormat will remain as it is after execution!
	 	 */
		dateFormat = getSuitableDateFormat(dateInString);

		Date dateObj = null;

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		try {
			dateObj = sdf.parse(dateInString);
		}catch(ParseException parseException) {
			throw new ValidatorException(fieldName + " is an unparsable date."
					+ "The format should be in yyyy-mm-dd hh:mm:ss. The time"
					+ "field is optional");
		}

		/* For GC */
		sdf = null;
		
		return dateObj;
	}

	/**
	 * <p>
	 * This method returns the present system date
	 * </p>
	 * 
	 * @return
	 * 		a <tt>java.util.Date</tt> representing the present System date
	 */
	public static Date getPresentDate()
	{
		return new Date();
	}

	/**
	 * <p>
	 * This method returns the custom date value with the passed arguments applied to
	 * the present System date
	 * </p>
	 * 
	 * @param field
	 * 		a field or component (day/month/year) in the date value
	 * 
	 * @param differenceAmount
	 * 		an integral value mentioning the difference units
	 * 
	 * @return
	 * 		a new <tt>java.util.Date</tt> with the difference applied to the
	 * 		field mentioned with the present system date
	 */
	public static Date getCustomDate(int field, int differenceAmount)
	{
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.add(field, differenceAmount);

		Date dateObj = calendar.getTime();

		return dateObj;
	}


	/**
	 * <p>
	 * This method returns a custom date with the different year as that of the
	 * <tt>diffInYears</tt> value from the present system date
	 * </p>
	 * 
	 * @param diffInYears
	 * 		the unit of difference to be applied to the year component of present system
	 * 		date
	 * 
	 * @return
	 * 		a new <tt>java.util.Date</tt> with the difference amount applied to the
	 * 		year component of the present system date
	 */
	public static Date getCustomDateWithDiffYear(int diffInYears)
	{
		return getCustomDate(Calendar.YEAR, diffInYears);
	}


	/**
	 * <p>
	 * This method returns a custom date with the different month as that of the
	 * <tt>diffInMonths</tt> value from the present system date
	 * </p>
	 * 
	 * @param diffInMonths
	 * 		the unit of difference to be applied to the month component of present system
	 * 		date
	 * 
	 * @return
	 * 		a new <tt>java.util.Date</tt> with the difference amount applied to the
	 * 		month component of the present system date
	 */
	public static Date getCustomDateWithDiffMonth(int diffInMonths)
	{
		return getCustomDate(Calendar.MONTH, diffInMonths);
	}

	/**
	 * <p>
	 * This method returns a custom date with the different day as that of the
	 * <tt>diffInDays</tt> value from the present system date
	 * </p>
	 * 
	 * <p>
	 * As of now, it supports different Day of Month!
	 * </p>
	 * 
	 * @param diffInDays
	 * 		the unit of difference to be applied to the day component of present system
	 * 		date
	 * 
	 * @return
	 * 		a new <tt>java.util.Date</tt> with the difference amount applied to the
	 * 		day component of the present system date
	 */

	public static Date getCustomDateWithDiffDay(int diffInDays)
	{
		return getCustomDate(Calendar.DAY_OF_MONTH, diffInDays);
	}

	/**
	 * <p>
	 * This method returns the custom date output with the default date output format
	 * for the passed <tt>java.util.Date</tt> object
	 * </p>
	 * 
	 * @param dateObj
	 * 		the input date object as <tt>java.util.Date</tt>
	 * 
	 * @return
	 * 		a custom date format output in <tt>String</tt>
	 */
	public static String getCustomDateOutput(Date dateObj)
	{
		return getCustomDateOutput(dateObj, DEFAULT_DATE_OUTPUT_FORMAT);
	}

	/**
	 * <p>
	 * This method returns the custom date output with the specified date output format
	 * for the passed <tt>java.util.Date</tt> object
	 * </p>
	 * 
	 * @param dateObj
	 * 		the input date object as <tt>java.util.Date</tt>
	 * 
	 * @param outputDatePattern
	 * 		the format to be applied to the date output
	 * 
	 * @return
	 * 		a custom date format output in <tt>String</tt>
	 */	
	public static String getCustomDateOutput(Date dateObj,
																		String outputDatePattern)
	{
		String outputDateValue = null;

		if(null==dateObj)
		{
			return outputDateValue;
		}

		if(!StringUtil.isValidString(outputDatePattern))
		{
			outputDatePattern = DEFAULT_DATE_OUTPUT_FORMAT;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(outputDatePattern);
		
		outputDateValue = sdf.format(dateObj);
		
		/* For GC */
		sdf = null;

		return outputDateValue;
	}

	/**
	 * <p>
	 * This method parses the date value passed in the form of <tt>String</tt>
	 * matching exactly with the dateFormat being passed
	 * </p>
	 *
	 * @param dateInString
	 * 		the input date in the form of String
	 *
	 * @param dateFormat
	 * 		the date format to parse the input String
	 *
	 * @return
	 *		an instance of <tt>java.util.Date</tt> object having the
	 *		the parsed Date of input String
	 *
	 * @throws ParseException
	 * 		any exceptions while parsing the input String
	 */
	public static Date getDateMatchingWithExactPattern(String dateInString,
																	String dateFormat)
	throws ParseException
	{
		Date dateObj = null;

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		dateObj = sdf.parse(dateInString);
		
		/* For GC */
		sdf = null;		

		return dateObj;
	}
}