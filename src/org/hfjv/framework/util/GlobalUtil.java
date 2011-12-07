package org.hfjv.framework.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.field.Field;

/**
 * <p>
 * A global utility class for the HFJV application
 * </p>
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class GlobalUtil
{
	/**
	 * <p>
	 * A private class level logger instance
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
																getLogger(GlobalUtil.class);

	/**
	 * <p>
	 * A class level constant to hold the application name
	 * </p>
	 */
	public static final String APP_NAME = "HassleFreeJavaValidator";

	/**
	 * <p>
	 * A class level constant to hold the module field separator character 
	 * </p>
	 */
	public static final String MODULE_FIELD_SEPARATOR = "-";

	/**
	 * <p>
	 * A class level constant to hold the value range separator character 
	 * </p>
	 */
	public static final String VALUE_RANGE_SEPARATOR = ":";

	/**
	 * <p>
	 * A class level constant to hold the value list separator character 
	 * </p>
	 */
	public static final String VALUE_LIST_SEPARATOR = "-";

	/**
	 * <p>
	 * A class level constant to hold the <tt>byte</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_NUMBER_BYTE = "byte";

	/**
	 * <p>
	 * A class level constant to hold the <tt>short</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_NUMBER_SHORT = "short";

	/**
	 * <p>
	 * A class level constant to hold the <tt>int</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_NUMBER_INT = "int";

	/**
	 * <p>
	 * A class level constant to hold the <tt>long</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_NUMBER_LONG = "long";

	/**
	 * <p>
	 * A class level constant to hold the <tt>bigdecimal</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_NUMBER_BIGDECIMAL = "bigdecimal";

	/**
	 * <p>
	 * A class level constant to hold the <tt>biginteger</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_NUMBER_BIGINT = "biginteger";
	
	/**
	 * <p>
	 * A class level constant to hold the <tt>String</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_STRING = "string";

	/**
	 * <p>
	 * A class level constant to hold the <tt>hexadecimal</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_HEXADECIMAL = "hexadecimal";
	
	/**
	 * <p>
	 * A class level constant to hold the <tt>date</tt> data type 
	 * </p>
	 */
	public static final String DATATYPE_DATE = "date";
	
	/**
	 * <p>
	 * A class level constant to hold the <tt>+</tt> sign 
	 * </p>
	 */
	public static final String SIGN_PLUS = "+";
	
	/**
	 * <p>
	 * A class level constant to hold the <tt>-</tt> sign 
	 * </p>
	 */
	public static final String SIGN_MINUS = "-";

	/**
	 * <p>
	 * A class level <tt>ArrayList</tt> to hold the list of sign values
	 * </p>
	 */
	public static final ArrayList<String> listOfSigns = new ArrayList<String>();

	/**
	 * <p>
	 * A class level constant to hold the list of <tt>number types</tt>
	 * </p>
	 */
	public static final String[] NUMBER_TYPE_ARRAY =
			new String[] {
				DATATYPE_NUMBER_BYTE,
				DATATYPE_NUMBER_SHORT,
				DATATYPE_NUMBER_INT,
				DATATYPE_NUMBER_LONG,
				DATATYPE_NUMBER_BIGDECIMAL,
				DATATYPE_NUMBER_BIGINT
			};

	/**
	 * <p>
	 * A private class level constant to hold the list of available data types
	 * </p>
	 */
	private static final String[] DATA_TYPE_ARRAY =
			new String[] {
				DATATYPE_DATE,
				DATATYPE_HEXADECIMAL,
				DATATYPE_NUMBER_BIGDECIMAL,
				DATATYPE_NUMBER_BIGINT,
				DATATYPE_NUMBER_BYTE,
				DATATYPE_NUMBER_INT,
				DATATYPE_NUMBER_LONG,
				DATATYPE_NUMBER_SHORT,
				DATATYPE_STRING
		};
	
	/**
	 * <p>
	 * A class level constant to represent the <tt>comma</tt> as a token separator
	 * </p>
	 */
	public static final String TOKEN_SEPARATOR_COMMA = ",";

	/**
	 * <p>
	 * A class level <tt>ArrayList</tt> to hold the <tt>number types</tt>
	 * </p>
	 */
	public static final ArrayList<String> numberTypeList = new ArrayList<String>();

	/**
	 * <p>
	 * A class level <tt>ArrayList</tt> to hold the <tt>available data types</tt>
	 * </p>
	 */
	public static final ArrayList<String> dataTypeList = new ArrayList<String>();

	/**
	 * <p>
	 * This method returns whether or not the passed field's data type is one of the
	 * configured and allowed number types
	 * </p>
	 * 
	 * @param field
	 * 		the field instance whose data type needs to be evaluated
	 */
	public static boolean isAllowedNumberType(Field field)
	{
		return isAllowedNumberType(field.getType());
	}

	/**
	 * <p>
	 * This method returns whether or not the passed field's data type is one of the
	 * configured and allowed number types
	 * </p>
	 * 
	 * @param fieldType
	 * 		the field type that needs to be evaluated
	 */
	public static boolean isAllowedNumberType(String fieldType)
	{
		if(StringUtil.isInvalidString(fieldType))
		{
			return false;
		}

		/* better even if the input is typed with mixed case */
		fieldType = fieldType.toLowerCase();

		return numberTypeList.contains(fieldType);
	}

	static {
		initNumberTypeList();
		initSignList();
		initDataTypeList();
	}

	/**
	 * <p>
	 * This  method initialized the list of signs
	 * </p>
	 */
	public static void initSignList()
	{
		listOfSigns.add(SIGN_PLUS);
		listOfSigns.add(SIGN_MINUS);
	}

	/**
	 * <p>
	 * This method confirms whether nor not the passed the <tt>String</tt> 
	 * value is a valid sign
	 * </p>
	 * 
	 * @param str
	 * 		the value passed as <tt>String</tt>
	 * 
	 * @return
	 * 		a true/false indicating the status
	 */
	public static boolean isValidSign(String str)
	{
		return listOfSigns.contains(str);
	}

	/**
	 * <p>
	 * This method checks and excludes the sign bit if the value has any.
	 * It performs this check only on a valid input value and it returns
 	 * just the value alone.
	 *
	 * @param value
	 *		the input value in the form of <tt>String</tt>, which needs
	 *		to be evaluated for the presence of a sign bit
	 *
	 * @return
	 *		the value which excludes the sign bit if any
	 *
	 */
	public static String excludeSignBit(String value)
	{
		return excludeSignBit(value, null);
	}
	
	/**
	 * <p>
	 * This method checks and excludes the sign bit ("+") if the value has any.
	 * It performs this check only on a valid input value and it returns
 	 * just the value alone.
	 *
	 * @param value
	 *		the input value in the form of <tt>String</tt>, which needs
	 *		to be evaluated for the presence of a sign bit
	 *
	 * @return
	 *		the value which excludes the sign bit if any
	 *
	 */
	public static String excludeSignBitPlus(String value)
	{
		return excludeSignBit(value, SIGN_PLUS);
	}
	
	/**
	 * <p>
	 * This method checks and excludes the sign bit ("-") if the value has any.
	 * It performs this check only on a valid input value and it returns
 	 * just the value alone.
	 *
	 * @param value
	 *		the input value in the form of <tt>String</tt>, which needs
	 *		to be evaluated for the presence of a sign bit
	 *
	 * @return
	 *		the value which excludes the sign bit if any
	 *
	 */
	public static String excludeSignBitMinus(String value)
	{
		return excludeSignBit(value, SIGN_MINUS);
	}
	
	/**
	 * <p>
	 * This method checks and excludes the sign bit if the value has any.
	 * It performs this check only on a valid input value and it returns
 	 * just the value alone.
 	 * </p>
 	 * 
 	 * <p>
 	 * It examines the second parameter (<tt>signCharToRemove</tt>) to 
 	 * determine whether to remove a specific sign character or any sign bit.
 	 * If the <tt>signCharToRemove</tt> is valid and it matches with the value's
 	 * sign bit, it removes the sign character.
 	 * </p>
	 *
	 * @param value
	 *		the input value in the form of <tt>String</tt>, which needs
	 *		to be evaluated for the presence of a sign bit
	 *
	 * @return
	 *		the value which excludes the sign bit if any
	 *
	 */
	private static String excludeSignBit(String value, String signCharToRemove)
	{
		if(StringUtil.isValidString(value))
		{
			String firstChar = String.valueOf(value.charAt(0));
			
			if(GlobalUtil.isValidSign(firstChar))
			{
				/* If signCharToRemove is valid, its for specific sign */
				if(StringUtil.isValidString(signCharToRemove))
				{
					/* Exclude the sign char only if it matches */
					if(firstChar.equalsIgnoreCase(signCharToRemove))
					{
						value = value.substring(1);
					}
				}
				/* If signCharToRemove is null/invalid, generic */
				else 
				{
					value = value.substring(1);
				}
			}
		}

		return value;
	}

	/**
	 * <p>
	 * A class level method to stop the execution with a <tt>RuntimeException</tt>
	 * of the passed error message
	 * </p>
	 */
	public static void stopExecutionWithError(String errorMsg)
	{
		logger.error(errorMsg);
		throw new RuntimeException(errorMsg);
	}

	/**
	 * <p>
	 * This method initializes the number type list
	 * </p>
	 */
	private static void initNumberTypeList()
	{
		for(String numberType : NUMBER_TYPE_ARRAY)
		{
			numberTypeList.add(numberType);
		}
		logger.info(" --|*|-- Number types are initialized! --|*|--");
	}

	/**
	 * <p>
	 * This method initializes the data type list
	 * </p>
	 */
	private static void initDataTypeList()
	{
		for(String numberType : DATA_TYPE_ARRAY)
		{
			dataTypeList.add(numberType);
		}
		logger.info(" --|*|-- All Data types are initialized! --|*|--");
	}
	
	/**
	 * <p>
	 * This method returns the display name (short name) of a field. It strips off the
	 * module name in the passed fieldName
	 * </p>
	 * 
	 * @param fieldName
	 * 		the field name which needs to be looked at for returning the short name
	 * 
	 * @return
	 * 		the short form the field name to be used in displaying
	 */
	public static String getDisplayName(String fieldName)
	{
		String displayName = null;

		if(StringUtil.isInvalidString(fieldName))
		{
			return fieldName;
		}

		int indexOfSeparator = fieldName.indexOf(MODULE_FIELD_SEPARATOR);

		if(indexOfSeparator==-1)
		{
			return fieldName;
		}

		displayName = fieldName.substring(indexOfSeparator+1);

		return displayName;
	}

	/**
	 * <p>
	 * This method returns the list of tokens from the passed input in the form of 
	 * <tt>comma separated value</tt>
	 * </p>
	 * 
	 * @param csvString
	 * 		the input value passed as a <tt>comma separated value</tt>
	 * 
	 * @return
	 * 		a list of values separated out of the delimiter			
	 */
	public static ArrayList<String> getListOfTokens(String csvString)
	{
		return getListOfTokens(csvString, TOKEN_SEPARATOR_COMMA);
	}

	/**
	 * <p>
	 * This method returns the list of tokens from the passed input in the form of 
	 * <tt>comma separated value</tt>
	 * </p>
	 * 
	 * @param tokenSeparatedStr
	 * 		the input value passed as a <tt>comma separated value</tt>
	 * 
	 * @param delimiter
	 * 		the delimiter to parse and extract the tokens
	 * 
	 * @return
	 * 		a list of values separated out of the token	  			
	 */
	public static ArrayList<String> getListOfTokens(
															String tokenSeparatedStr, String delimiter)
	{
		ArrayList<String> listOfTokens = new ArrayList<String>();

		if(StringUtil.isInvalidString(tokenSeparatedStr))
		{
			listOfTokens.add(tokenSeparatedStr);
			return listOfTokens;
		}

		/* Validate and assign a default Token Separator */
		if(StringUtil.isNull(delimiter))
		{
			delimiter = TOKEN_SEPARATOR_COMMA;
		}

		StringTokenizer st = new StringTokenizer(tokenSeparatedStr, delimiter);

		while(st.hasMoreTokens())
		{
			listOfTokens.add(st.nextToken().trim());
		}

		return listOfTokens;
	}

	/**
	 * This method parses the input value and prepares the range value list with
	 * min and max bounds in such a way that the 0th element contains the min bound
	 * and 1st element contains the max bound
	 *
	 * @param rangeSeparatedValue
	 * 			the range value with the separator
	 * 
	 * @return
	 * 			a list of value with min and max bound at 0, 1 index
	 */
	public static ArrayList<String> getRangeValuesInList(String rangeSeparatedValue)
	{
		ArrayList<String> rangeValuesList = new ArrayList<String>();

		if(StringUtil.isInvalidString(rangeSeparatedValue))
		{
			return rangeValuesList;
		}

		int indexOfSeparator = rangeSeparatedValue.indexOf(
							GlobalUtil.VALUE_RANGE_SEPARATOR);

		/* if there is no separator, the value is not properly configured */
		if(indexOfSeparator==-1)
		{
			return rangeValuesList;
		}

		String minVal = rangeSeparatedValue.substring(0, indexOfSeparator);
		String maxVal = rangeSeparatedValue.substring(indexOfSeparator+1);

		rangeValuesList.add(minVal);
		rangeValuesList.add(maxVal);

		return rangeValuesList;
	}

	/**
	 * <p>
	 * This method checks whether the passed <tt>String</tt> is a valid number
	 * or not. It checks for the presence of 0-9 by default.
	 * </p>
	 * <p>
	 * If any digit is falling out of the bounds, it returns false.
	 * </p>
	 * <p>
	 * If needed to validate against any other number other than 0-9, use the
	 * the other method {@link #isValidNumber(String, int, int)}
	 * </p>
	 *
	 * @param numberInStr
	 *		the input number in <tt>String</tt>
	 *
	 * @return
	 *		true/false depends on the status
	 *
	 * @see #isValidNumber(String, int, int)
	 * @see #isValidHexString(String)
	 */
	public static boolean isValidNumber(String numberInStr)
	{
		/* Ascii 48 to 57 -> 0 to 9 digits! */
		return isValidNumber(numberInStr, 48, 57);
	}

	/**
	 * <p>
	 * This method checks whether the passed <tt>String</tt> is a valid number
	 * or not. It validates the input number beteween <tt>startRange</tt> and
	 * <tt>endRange</tt>.
	 * </p>
	 * <p>
	 * If any digit is falling out of the bounds, it returns false.
	 * </p>
	 * <p>
	 * If needed to validate against the number 0-9, use the other method
	 * {@link #isValidNumber(String)}
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> The numbers <tt>startRange</tt> and <tt>endRange</tt>
	 * are <i>inclusive</i>.
	 * </p>
	 *
	 * @param numberInStr
	 *		the input number in <tt>String</tt>
	 *
	 * @param startRange
	 *		the starting number to validate the input number, inclusive
	 *
	 * @param endRange
	 *		the ending number to validate the input number, inclusive
	 *
	 * @return
	 *		true/false depends on the status
	 *
	 * @see #isValidNumber(String)
	 */
	public static boolean isValidNumber(String numberInStr,
						int startRange, int endRange)
	{
		boolean isValidNumber = false;

		if(StringUtil.isInvalidString(numberInStr))
		{
			return false;
		}

		char[] charArray = numberInStr.toCharArray();

		for(char c : charArray)
		{
			isValidNumber = false;

			if(isValidNumber(c, startRange, endRange))
			{
				isValidNumber = true;
				continue;
			}

			if(!isValidNumber)
			{
				break;
			}
		}

		return isValidNumber;
	}

	/**
	 * <p>
	 * This method checks whether the passed character is a valid number or not.
	 * It checks for the presence of 0-9 by default.
	 * </p>
	 * <p>
	 * If any digit is falling out of the bounds, it returns false.
	 * </p>
	 * <p>
	 * If needed to validate against the number 0-9, use the other method
	 * {@link #isValidNumber(char,int,int)}
	 * </p>
	 *
	 * @param numberChar
	 *		the input number in <tt>char</tt>
	 *
	 * @return
	 *		true/false depends on the status
	 *
	 * @see #isValidNumber(char,int,int)
	 * @see #isValidString(String)
	 * @see #isValidHexString(String)
	 */
	public static boolean isValidNumber(char numberChar)
	{
		/* Ascii 48 to 57 -> 0 to 9 digits! */
		return isValidNumber(numberChar, 48, 57);
	}

	/**
	 * <p>
	 * This method checks whether the passed character is a valid number
	 * or not. It validates the input number beteween <tt>startRange</tt> and
	 * <tt>endRange</tt>.
	 * </p>
	 * <p>
	 * If any digit is falling out of the bounds, it returns false.
	 * </p>
	 * <p>
	 * If needed to validate against the number 0-9, use the other method
	 * {@link #isValidNumber(char)}
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> The numbers <tt>startRange</tt> and <tt>endRange</tt>
	 * are <i>inclusive</i>.
	 * </p>
	 *
	 * @param numberChar
	 *		the input number in <tt>char</tt>
	 *
	 * @param startRange
	 *		the starting number to validate the input number, inclusive
	 *
	 * @param endRange
	 *		the ending number to validate the input number, inclusive
	 *
	 * @return
	 *		true/false depends on the status
	 *
	 * @see #isValidNumber(char,int,int)
	 * @see #isValidString(String)
	 * @see #isValidHexString(String)
	 */
	public static boolean isValidNumber(char numberChar,
						int startRange, int endRange)
	{
		boolean isValidNumber = false;

		if(numberChar >= startRange && numberChar <= endRange)
		{
			isValidNumber = true;
		}

		return isValidNumber;
	}

	/**
	 * <p>
	 * This method checks whether the passed character is a valid hexadecimal
	 * number or not. It checks the presence of either 0-9, A-F, a-f.
	 * </p>
	 * <p>
	 * If any digit is falling out of the bounds, it returns false.
	 * </p>
	 *
	 * @param inputStrInHex
	 *		the input number in <tt>String</tt>
	 *
	 * @return
	 *		true/false depends on the status
	 *
	 */
	public static boolean isValidHexString(String inputStrInHex)
	{
		boolean isValidHexString = false;

		if(StringUtil.isInvalidString(inputStrInHex))
		{
			return false;
		}

		char[] charArray = inputStrInHex.toCharArray();

		for(char c : charArray)
		{
			isValidHexString = false;

			if(isValidNumber(c)) /* Ascii 48 to 57 -> 0 to 9 digits */
			{
				isValidHexString = true;
				continue;
			}

			if(c>=65 && c<=70) /* ASCII 65 to 70 -> 'A' to 'F' */
			{
				isValidHexString = true;
				continue;
			}

			if(c>=97 && c<=102) /* ASCII 97 to 102 -> 'a' to 'f' */
			{
				isValidHexString = true;
				continue;
			}

			if(!isValidHexString)
			{
				break;
			}
		}

		return isValidHexString ;
	}

	/**
	 * <p>
	 * This method returns the application name
	 * </p>
	 */
	public static String getAppName()
	{
		return APP_NAME;
	}

	/**
	 * <p>
	 * This method returns the number type list
	 * </p>
	 */
	public static ArrayList<String> getNumberTypeList()
	{
		return numberTypeList;
	}
	
	/**
	 * @return the dataTypeList
	 */
	public static ArrayList<String> getDataTypeList() {
		return dataTypeList;
	}

}