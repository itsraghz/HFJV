package org.hfjv.framework.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * An utility class to deal with the <tt>Collection</tt> values
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class CollectionUtil
{

	/**
	 * <p>
	 * A method to check whether the <tt>list</tt> is valid or not.
	 *</p>
	 *
	 * <p>
	 * The validity is the logical AND-ing of both <tt>Non-Null</tt>
	 * and <tt>Non-Empty</tt>
	 * </p>
	 *
 	 * @param listToCheck
	 * 		a <tt>List</tt> which needs to be checked
	 *
	 * @return
	 * 		a boolean value (true/false) indicating the status
	 */
	public static boolean isValidList(List<? extends Object> listToCheck)
	{
		return !isNullList(listToCheck) && !isEmptyList(listToCheck);
	}

	/**
	 * <p>
	 * A method to check whether the <tt>list</tt> is empty or not.
	 *</p>
	 *
	 *
 	 * @param listToCheck
	 * 		a <tt>List</tt> which needs to be checked
	 *
	 * @return
	 * 		a boolean value (true/false) indicating the status
	 */
	public static boolean isEmptyList(List<? extends Object> listToCheck)
	{
		return (listToCheck.size()<=0);
	}

	/**
	 * <p>
	 * A method to check whether the <tt>list</tt> is null or not.
	 *</p>
	 *
	 *
 	 * @param listToCheck
	 * 		a <tt>List</tt> which needs to be checked
	 *
	 * @return
	 * 		a boolean value (true/false) indicating the status
	 */
	public static boolean isNullList(List<? extends Object> listToCheck)
	{
		return (null==listToCheck);
	}

	/**
	 * <p>
	 * A method to check whether the <tt>map</tt> is valid or not.
	 *</p>
	 *
	 * <p>
	 * The validity is the logical AND-ing of both <tt>Non-Null</tt>
	 * and <tt>Non-Empty</tt>
	 * </p>
	 *
 	 * @param mapToCheck
	 * 		a <tt>Map</tt> which needs to be checked
	 *
	 * @return
	 * 		a boolean value (true/false) indicating the status
	 */
	public static boolean isValidMap(Map<? extends Object, ? extends Object> mapToCheck)
	{
		return !isNullMap(mapToCheck) && !isEmptyMap(mapToCheck);
	}

	/**
	 * <p>
	 * A method to check whether the <tt>map</tt> is empty or not.
	 *</p>
	 *
	 *
 	 * @param mapToCheck
	 * 		a <tt>Map</tt> which needs to be checked
	 *
	 * @return
	 * 		a boolean value (true/false) indicating the status
	 */
	public static boolean isEmptyMap(Map<? extends Object, ? extends Object> mapToCheck)
	{
		return (mapToCheck.size()<=0);
	}

	/**
	 * <p>
	 * A method to check whether the <tt>map</tt> is null or not.
	 *</p>
	 *
	 *
 	 * @param mapToCheck
	 * 		a <tt>Map</tt> which needs to be checked
	 *
	 * @return
	 * 		a boolean value (true/false) indicating the status
	 */
	public static boolean isNullMap(Map<? extends Object, ? extends Object> mapToCheck)
	{
		return (null==mapToCheck);
	}

	/**
	 * <p>
	 *  An utility method used for debugging and printing. This method gives a String
	 *  equivalent of all map values each in a separate line
	 * </p>
	 * 
	 * @param mapToPrint
	 * 		the map whose values need to be given back a separate entries in each line
	 * 
	 * @return
	 * 		the String equivalent of map entries each in a separate line
	 */
	public static String getMapEntriesInSeparateLines(
				Map<? extends Object, ? extends Object> mapToPrint)
	{
		Iterator<? extends Object> iteratorForMap =
								getIteratorForMap(mapToPrint);

		StringBuilder sb = new StringBuilder();

		Object key = null;

		while(iteratorForMap.hasNext())
		{
			key = iteratorForMap.next();

			sb.append(key+"="+mapToPrint.get(key));
		}

		return sb.toString();
	}

	/**
	 * <p>
	 * This method returns the <tt>Iterator</tt> of a passed map instance
	 * </p>
	 * 
	 * @param mapObj
	 * 		A map instance whose iterator needs to be returned
	 * 
	 * @return
	 * 		an instance of <tt>Iterator</tt>
	 */
	public static Iterator<? extends Object> getIteratorForMap(
					Map<? extends Object, ? extends Object> mapObj)
	{
		Set<? extends Object> keySet = mapObj.keySet();

		return keySet.iterator();
	}


	/**
	 * <p>
	 * This method returns the <tt>Iterator</tt> of the <tt>list</tt> object
	 * being passed
	 * </p>
	 * 
	 * @param listObject
	 * 		the list instance whose iterator needs to be returned
	 * 
	 * @return
	 * 		the <tt>Iterator</tt> of the list instance
	 */
	public static Iterator<? extends Object> getIteratorForList(
					List<? extends Object> listObj)
	{
		return listObj.iterator();
	}
	
	/**
	 * <p>
	 * This method returns true if the passed <tt>listToEvaluate</tt> is invalid.
	 * It complements the functionality of {@link #isValidList(List)}
	 *</p>
	 *
	 *
 	 * @param listToEvaluate
	 * 		a <tt>List</tt> which needs to be checked
	 *
	 * @return
	 * 		true if invalid; false otherwise
	 */
	public static boolean isInvalidList(List<? extends Object> listToEvaluate)
	{
		return !isValidList(listToEvaluate);
	}
	
	/**
	 * <p>
	 * This method returns true if the passed <tt>mapToEvaluate</tt> is invalid.
	 * It complements the functionality of {@link #isValidMap(Map)}
	 *</p>
	 *
 	 * @param mapToEvaluate
	 * 		a <tt>Map</tt> which needs to be evaluated
	 *
	 * @return
	 * 		true if invalid; false otherwise
	 */
	public static boolean isInvalidMap(Map<? extends Object, ? extends Object> mapToEvaluate)
	{
		return !isValidMap(mapToEvaluate);
	}
}