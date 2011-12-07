package org.hfjv.framework.util;

/**
 * <p>
 * An utility class to serve the common and frequent needs of <tt>String</tt>
 * related operations.
 * </p>
 *
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class StringUtil
{
	/**
	 * <p>
 	 * This method returns whether the input <tt>String</tt> is valid or not.
	 * </p>
	 *
	 * <p>
	 * The validity is the logical AND-ing of both <tt>Non-Null</tt> and <tt>Non-Empty</tt>
	 * </p>
	 *
	 * @param strToCheck
	 *		the input String to be checked
	 *
	 * @return
	 *		a boolean value (true/false) indicating the status
	 */
	public static boolean isValidString(String strToCheck)
	{
		return !isNull(strToCheck) && !isEmpty(strToCheck);
	}

	/**
	 * <p>
 	 * This method returns whether the input <tt>String</tt> is empty or not.
	 * </p>
	 *
	 * @param strToCheck
	 *		the input String to be checked
	 *
	 * @return
	 *		a boolean value (true/false) indicating the status
	 */
	public static boolean isEmpty(String strToCheck)
	{
		return (0==strToCheck.trim().length());
	}

	/**
	 * <p>
 	 * This method returns whether the input <tt>String</tt> is null or not.
	 * </p>
	 *
	 * @param strToCheck
	 *		the input String to be checked
	 *
	 * @return
	 *		a boolean value (true/false) indicating the status
	 */
	public static boolean isNull(String strToCheck)
	{
		return (null==strToCheck);
	}

	/**
	 * <p>
	 * A method to add a piece of string to another string, provided
	 * if the <tt>dataToBeAdded</tt> is valid.
	 * </p>
	 *
	 * @param strToBeAdded
	 *		the existing <tt>String</tt> to be appended with  the new data
	 *
	 * @param strToAppend
	 *		the new <tt>String</tt> to append
	 *
	 * @return
	 *		the concatenated <tt>String</tt> if the <tt>strToAppend</tt> is valid,
	 * 		else <tt>strToBeAdded</tt>
	 */
	public static String addIfValid(String strToBeAdded, String strToAppend)
	{
		StringBuilder sb = new StringBuilder(strToBeAdded);

		if(isValidString(strToAppend))
		{
			sb.append(strToAppend);
		}

		return sb.toString();
	}

	/**
	 * <p>
	 * A method to add a piece of string to another string with a delimiter
	 * at the end, provided if the <tt>strToBeAdded</tt> is valid.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b>
	 * <ul>
	 *  <li>
	 *	The <tt>delimiter</tt> is <i>not</i> validated.
	 *  </li>
	 *  <li>
	 *	This method in turn uses the {@link #addIfValid(String,String)} method
	 *    to add the <tt>strToBeAdded</tt>
	 *  </li>
	 * </ul>
	 * </p>
	 *
	 * @param strToBeAdded
	 *		the existing <tt>String</tt> to be appended with  the new data
	 *
	 * @param strToAppend
	 *		the new <tt>String</tt> to append
	 *
	 * @param delimiter
	 *		the delimiter to be added after adding the <tt>strToAppend</tt>
	 *
	 * @return
	 *		the concatenated <tt>String</tt> if the <tt>strToAppend</tt> is valid,
	 * 		else <tt>strToBeAdded</tt>
	 */
	public static String addIfValidAndAppendDelim(String strToBeAdded, String strToAppend,
						String delimiter)
	{
		StringBuilder sb = new StringBuilder(addIfValid(strToBeAdded, strToAppend));

		sb.append(delimiter);

		return sb.toString();
	}

	/**
	 * <p>
	 * A method to add a piece of string to another string, provided
	 * if the <tt>dataToBeAdded</tt> is valid.
	 * </p>
	 *
	 * @param strBldrToBeAdded
	 *		the existing <tt>StringBuilder</tt> to be appended with
	 *		the new data
	 *
	 * @param strToAppend
	 *		the new <tt>String</tt> to append
	 *
	 */
	public static void addIfValid(StringBuilder strBldrToBeAdded, String strToAppend)
	{
		if(isValidString(new String(strBldrToBeAdded)))
		{
			strBldrToBeAdded.append(strToAppend);
		}
	}


	/**
	 * <p>
	 * A method to add a piece of string to a <tt>StringBuilder</tt> along
	 * with a delimiter at the end, provided if the <tt>strToBeAdded</tt> is valid.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b>
	 * <ul>
	 *  <li>
	 *	The <tt>delimiter</tt> is <i>not</i> validated.
	 *  </li>
	 *  <li>
	 *	This method in turn uses the {@link #addIfValid(StringBuilder,String)} method
	 *    to add the <tt>strToBeAdded</tt>
	 *  </li>
	 * </ul>
	 * </p>
	 *
	 * @param strBldrToBeAdded
	 *		the existing <tt>StringBuilder</tt> to be appended with  the new data
	 *
	 * @param strToAppend
	 *		the new <tt>String</tt> to append
	 *
	 * @param delimiter
	 *		the delimiter to be added after adding the <tt>strToAppend</tt>
	 *
	 * @return
	 *		the concatenated <tt>String</tt> if the <tt>strToAppend</tt> is valid,
	 * 		else <tt>strToBeAdded</tt>
	 */
	public static String addIfValidAndAppendDelim(StringBuilder strBldrToBeAdded, String strToAppend,
						String delimiter)
	{
		addIfValid(strBldrToBeAdded, strToAppend);

		strBldrToBeAdded.append(delimiter);

                return strBldrToBeAdded.toString();
	}

	/**
	 * <p>
	 * This method returns whether the input array  of <tt>String</tt>s are null or not.
	 * </p>
	 *
	 * @param arrayOfStrings
	 *		the input array of Strings to be checked
	 *
	 * @return
	 *		a boolean value (true/false) indicating the status
	 *
 	 */
	public static boolean isNullStrArray(String[] arrayOfStrings)
	{
		return (null==arrayOfStrings);
	}

	/**
	 * <p>
	 * This method returns whether the input array  of <tt>String</tt>s are empty or not.
	 * </p>
	 *
	 * @param arrayOfStrings
	 *		the input array of Strings to be checked
	 *
	 * @return
	 *		a boolean value (true/false) indicating the status
	 *
 	 */
	public static boolean isEmptyStrArray(String[] arrayOfStrings)
	{
		return (0==arrayOfStrings.length);
	}

	/**
	 * <p>
	 * This method returns whether the input array  of <tt>String</tt>s are valid or not.
	 * </p>
	 *
	 * <p>
	 * The validity is the logical AND-ing of both <tt>Non-Null</tt> and <tt>Non-Empty</tt>
	 * </p>
	 *
	 * @param arrayOfStrings
	 *		the input array of Strings to be checked
	 *
	 * @return
	 *		a boolean value (true/false) indicating the status
	 *
 	 */
	public static boolean isValidStrArray(String[] arrayOfStrings)
	{
		return !isNullStrArray(arrayOfStrings) && !isEmptyStrArray(arrayOfStrings);
	}

	/**
	 * <p>
	 * This method adds a <tt>delimiter</tt> to every member of an array of <tt>String</tt>s.
	 * </p>
	 *
	 * <p>
	 *  <b>Note:</b> This method does <i>not</i> add a delimiter to the last argument. If needed,
	 *  you can use the other method {@link #addDelimiterToEachMember(String[], String, boolean)}
 	 *  by specifying <tt>true</tt> to the third and last parameter.
	 * </p>
	 *
	 * @param arrayOfStrings
	 *		the input array of <tt>String</tt>s need to be added with a delimiter
	 *
	 * @param delimiter
	 *		the delimiter to be added to every member of the array of Strings
	 *
	 * @return
	 *		the modified <tt>String</tt> with a delimiter after each member
	 *
	 * @see {@link #addDelimiterToEachMember(String[], String, boolean)}
	 */
	public static String addDelimiterToEachMember(String[] arrayOfStrings, String delimiter)
	{
		return addDelimiterToEachMember(arrayOfStrings, delimiter, false);
	}

	/**
	 * <p>
	 * This method adds a <tt>delimiter</tt> to every member of an array of <tt>String</tt>s.
	 * </p>
	 *
	 * <p>
	 *  <b>Note:</b> <br>
	 *   <ul>
	 *	<li>
	 *	  This parameter <tt>appendAtLast</tt> determines whether or not to add the
	 *	  delimiter to the last argument.
	 *	</li>
	 *	<li>
	 *	  This method returns <tt>null</tt> if the input <tt>arrayOfStrings</tt> is invalid!
	 *	</li>
	 *   </ul>
	 * </p>
	 *
	 * @param arrayOfStrings
	 *		the input array of <tt>String</tt>s need to be added with a delimiter
	 *
	 * @param delimiter
	 *		the delimiter to be added to every member of the array of Strings
	 *
 	 * @param appendAtLast
	 *	 	a <tt>boolean</tt> parameter which says whether or not to add the delimiter
	 * 		to the last member of the array
	 *
	 * @return
	 *		the modified <tt>String</tt> with a delimiter after each member
	 */
	public static String addDelimiterToEachMember(String[] arrayOfStrings, String delimiter, boolean appendAtLast)
	{
		StringBuilder appendedStrBldr = new StringBuilder();

		if(!isValidStrArray(arrayOfStrings))
		{
			return null;
		}

		String tempStrInLoop = null;
		boolean isLastIteration = false;
		boolean canAppend = true;

		for(int strCounter=0; strCounter < arrayOfStrings.length; strCounter++)
		{
			tempStrInLoop = arrayOfStrings[strCounter];
			appendedStrBldr.append(tempStrInLoop);

			canAppend = true; /* for every other iteration it should be set */

			isLastIteration = (strCounter==arrayOfStrings.length-1);

			if(isLastIteration)
			{
				if(!appendAtLast)
				{
					canAppend = false;
				}
			}

			if(canAppend)
			{
				appendedStrBldr.append(delimiter);
			}
		}

		return appendedStrBldr.toString();
	}

	/**
 	 * <p>
	 * An utility method to get a character (or set of characters) when
	 * they need to be printed repeatedly
	 * </p>
	 *
	 * @param str
	 *		the character or string to be printed
	 *
	 * @param numberOfTimes
	 * 		the number of times the character/string to be repeated
	 *
	 * @return
	 *		the String having the passed argument <tt>str</tt> repeated
	 *		<tt>numberOfTimes</tt>
	 */
	public static String getRepeatedChars(String str, int numberOfTimes)
	{
		if(numberOfTimes<0)
		{
			return str;
		}

		StringBuilder sb = new StringBuilder();

		for(int i=0; i< numberOfTimes; i++)
		{
			sb.append(str);
		}

		return sb.toString();
	}

	/**
	 * <p>
	 * An utility method to find out whether the string passed is just
	 * having the same <tt>charToFind</tt> filled with.
	 * </p>
	 *
	 * @param str
 	 * 		the input string to be evaluated
	 *
	 * @param charToFind
	 *		the character to be found for match
	 *
	 * @return
	 *		a true/false indicating the status
	 */
	public static boolean isValueFilledWithSameChar(String str, char charToFind)
	{
		boolean sameCharRepeats = true;

		if(isValidString(str) && charToFind!=' ')
		{
			for(int i=0; i< str.trim().length(); i++)
			{
				if(!(str.charAt(i)==charToFind))
				{
					sameCharRepeats=false;
					break;
				}
			}
		}

		return sameCharRepeats;
	}

	/**
	 * <p>
	 * This method returns true if the passed <tt>strToCheck</tt> is invalid, 
	 * false otherwise. It complements the functionality of {@link #isValidString(String)}
	 * method.
	 * </p>
	 * 
	 * @param strToCheck
	 * 				the input string element to be evaluated
	 * 
	 * @return
	 * 			true if invalid; false otherwise
	 */
	public static boolean isInvalidString(String strToCheck)
	{
		return !isValidString(strToCheck);
	}

	/**
	 * <p>
	 * This method returns true if the passed <tt>strArrayToCheck</tt> is invalid, 
	 * false otherwise. It complements the functionliaty of {@link #isValidStrArray(String[])}
	 * method
	 * </p>
	 * 
	 * @param strArrayToCheck
	 * 				the input string array to be evaluated
	 * 
	 * @return
	 * 			true if invalid; false otherwise
	 */
	public static boolean isInvliadStrArray(String[] strArrayToCheck)
	{
		return !isValidStrArray(strArrayToCheck);
	}
	
}