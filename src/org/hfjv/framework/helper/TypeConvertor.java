package org.hfjv.framework.helper;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * A helper class to convert the data types of fields and values 
 * used inside HFJV
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class TypeConvertor
{
	/**
	 * <p>
	 * A class level constant for the default integer value
	 * </p>
	 */
	public static final int DEFAULT_INT =  -1;

	/**
	 * <p>
	 * A class level constant for the default long value
	 * </p>
	 */
	public static final long DEFAULT_LONG = -1;

	/**
	 * <p>
	 * This method returns the equivalent integer value from its string representation
	 * </p>
	 * 
	 * @param str
	 * 		a numeric value represented as a <tt>String</tt>
	 * 
	 * @return
	 * 		an equivalent integer value
	 */
	public static int getInt(String str)
	{
		return getInt(str, DEFAULT_INT);
	}

	/**
	 * <p>
	 * This method returns the equivalent integer value from its string representation
	 * </p>
	 * 
	 * @param str
	 * 		a numeric value represented as a <tt>String</tt>
	 * 
	 * @param defaultInt
	 * 		the default integer value to be considered in case of a parsing exception
	 * 
	 * @return
	 * 		an equivalent integer value
	 */
	public static int getInt(String str, int defaultInt)
	{
		int intVal;

		try {
			intVal = Integer.parseInt(str);
		}catch(NumberFormatException nfe) {
			intVal = defaultInt;
		}

		return intVal;
	}

	/**
	 * <p>
	 * This method returns the equivalent long value from its string representation
	 * </p>
	 * 
	 * @param str
	 * 		a numeric value represented as a <tt>String</tt>
	 * 
	 * @return
	 * 		an equivalent long value
	 */
	public static long getLong(String str)
	{
		return getLong(str, DEFAULT_LONG);
	}

	/**
	 * <p>
	 * This method returns the equivalent long value from its string representation
	 * </p>
	 * 
	 * @param str
	 * 		a numeric value represented as a <tt>String</tt>
	 * 
	 * @param defaultLong
	 * 		the default value to be considered in case of an exception while parsing
	 * 
	 * @return
	 * 		an equivalent long value
	 */
	public static long getLong(String str, long defaultLong)
	{
		long longVal;

		try {
			longVal = Long.parseLong(str);
		}catch(NumberFormatException nfe) {
			longVal= defaultLong;
		}

		return longVal;
	}

	/**
	 * <p>
	 * This method gives a <tt>Number</tt> equivalent of a <tt>String</tt> 
	 * representation
	 * </p>
	 * 
	 * @param str
	 * 			the numeric value represented as <tt>String</tt>
	 * 
	 * @return
	 * 		an equivalent value as <tt>Number</tt>
	 */
	public static Number getNumber(String str)
	{
		Number numberVal = -1;

		try {
			numberVal = new BigInteger(str);
		}catch(NumberFormatException nfe) {
			numberVal = -1;
		}

		return numberVal;
	}

	/**
	 * <p>
	 * This method returns the <tt>BigDecimal</tt> equivalent value represented as
	 * <tt>String</tt>
	 * </p>
	 * 
	 * @param str
	 * 		the numeric value represented as <tt>String</tt>
	 * 
	 * @return
	 * 		an equivalent <tt>BigDecimal</tt> value
	 */
	public static BigDecimal getBigDecimal(String str)
	{
		//return ((BigDecimal) getNumber(str));
		return new BigDecimal((BigInteger)getNumber(str));
	}

	/**
	 * <p>
	 * This method returns the <tt>BigInteger</tt> equivalent value represented as
	 * <tt>String</tt>
	 * </p>
	 * 
	 * @param str
	 * 		the numeric value represented as <tt>String</tt>
	 * 
	 * @return
	 * 		an equivalent <tt>BigInteger</tt> value
	 */
	public static BigInteger getBigInteger(String str)
	{
		return ((BigInteger) getNumber(str));
	}
}