package org.hfjv.framework.util;

import java.math.BigDecimal;

/**
 * <p>
 * An utility class to deal with the numeric values
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class NumberUtil
{

	/**
	 * <p>
	 * This method returns whether or not the passed numeric value in String is a valid
	 * number
	 * </p>
	 *
	 * @param str
	 * 			the numeric value passed as a <tt>java.lang.String</tt>
	 * 
	 * @return
	 * 		a true/false indicating the status
	 */
	public static boolean isValidNumber(String str)
	{
		/* by default it takes for the longest possible number */
		return isValidNumber(str, GlobalUtil.DATATYPE_NUMBER_BIGDECIMAL);
	}

	/**
	 * <p>
	 * This method returns whether the passed numeric value as <tt>String<tt> with
	 * its specific data type is a valid number or not
	 * </p>
	 *
	 * @param str
	 * 			the numeric value passed as a <tt>java.lang.String</tt>
	 * 
	 * @param type
	 * 			the data type of the value being passed
	 * 
	 * @return
	 * 			a true/false indicating the status
	 */
	@SuppressWarnings("unused")
	public static boolean isValidNumber(String str, String type)
	{
		if(!StringUtil.isValidString(str) || !StringUtil.isValidString(type))
		{
			return false;
		}

		/** What if the value contains a sign digit at first? a "-" or "+" ? */
		str = GlobalUtil.excludeSignBit(str);

		boolean isValidNumber = true;

		try {
			if(type.equalsIgnoreCase(GlobalUtil.DATATYPE_NUMBER_BYTE))
			{
				Byte byteVal = Byte.parseByte(str);
			}
			else if(type.equalsIgnoreCase(GlobalUtil.DATATYPE_NUMBER_SHORT))
			{
				Short shortVal = Short.parseShort(str);
			}
			else if(type.equalsIgnoreCase(GlobalUtil.DATATYPE_NUMBER_INT))
			{
				Integer intVal = Integer.parseInt(str);
			}
			else if(type.equalsIgnoreCase(GlobalUtil.DATATYPE_NUMBER_LONG))
			{
				Long longVal = Long.parseLong(str);
			}
			else if(type.equalsIgnoreCase(GlobalUtil.DATATYPE_NUMBER_BIGDECIMAL))
			{
				BigDecimal bd = new BigDecimal(str);
			}
		}catch(NumberFormatException nfe) {
			isValidNumber = false;
		}

		return isValidNumber;

	}

	/**
	 * <p>
	 * This is a generic and common method to ensure whether
	 * the actual String equivalent of the number passed was of <tt>-1</tt>
	 * variant.
	 * </p>
	 *
	 * <p>
	 *  The actual value could be any of <tt>-1</tt>, <tt>-1L</tt> etc., Hence,
	 * this method just checks whether the first two characters of the
	 * passed input <tt>StringVal</tt> are <tt>'-'</tt> and <tt>'1'</tt>.
	 * If so, it returns true, else returns false.
	 * </p>
	 *
	 * <p>
	 * <b>Usage : </b> This method can be used in the convention of <tt>String</tt>
	 * equivalent to the actual number value while ensuring that the actual
	 * input is NOT any variant of <tt>-1</tt> as discussed above. This is
	 * because, most of the specific <tt>getXXX()</tt> methods of
	 * <tt>TypeConvertor</tt> returns <tt>-1</tt> as the default value if
	 * there are any exceptions during the conversion of the type.
	 * </p>
	 *
	 * @param stringVal
	 * 		the <tt>String</tt> equivalent of a number
	 *
	 * @return
	 *		true or fales depends on the actual value
	 */
	public static  boolean isValueMinusOne(String stringVal)
	{
		if(!StringUtil.isValidString(stringVal))
		{
			return false;
		}

		boolean isMinusOne = false;

		int strLength = stringVal.length();

		/* Would like to validate only -1 or -1L or -1l */
		if(strLength < 2 || strLength > 3)
		{
			return false;
		}

		boolean isFirstCharMinusSymbol = stringVal.charAt(0)=='-';
		boolean isSecondCharNumberOne = stringVal.charAt(1)=='1';

		if(isFirstCharMinusSymbol && isSecondCharNumberOne)
		{
			isMinusOne = true;
		}

		/* In case there is a suffix of 'L' or 'l' to -1 ? */

		if(strLength==3)
		{
			String charString = ""+stringVal.charAt(2);

			if(!charString.equalsIgnoreCase("L"))
			{
				isMinusOne = false;
			}
		}

		return isMinusOne;
	}
}