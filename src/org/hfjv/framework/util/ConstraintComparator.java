package org.hfjv.framework.util;

import java.util.Comparator;

import org.hfjv.framework.core.constraint.Constraint;

/**
 * <p>
 * A custom comparator to compare the Constraint objects used inside HFJV
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
@SuppressWarnings("rawtypes")
public class ConstraintComparator implements Comparator
{
	/**
	 * <p>
	 * A class level instance of this class to be used as a Singleton
	 * </p>
	 */
	private static ConstraintComparator _instance = null;

	/**
	 * <p>
	 *  A private constructor used for singleton
	 * </p>
	 */
	private ConstraintComparator() {}

	/**
	 * <p>
	 * A static method to hand over the single copy of this class's instance
	 * </p>
	 * 
	 * @return
	 * 		a single copy of this class's instance
	 */
	public static ConstraintComparator getInstance()
	{
		if(null==_instance)
		{
			_instance = new ConstraintComparator();
		}

		return _instance;
	}

	/**
	 * <p>
	 * An overridden method to compare the two objects
	 * </p>
	 * 
	 * @param o1
	 * 		the first object to be compared
	 * 
	 * @param o2
	 * 		the second object to be compared
	 * 
	 * @return
	 * 		an integer value returning the result of comparison (0 means equal, 1 means 
	 * 		obj1 > obj2, and -1 for obj1 < obj2)
	 */
	public int compare(Object o1, Object o2)
	{
		Constraint constraintObj1 = (Constraint) o1;
		Constraint constraintObj2 = (Constraint) o2;

		int insertionOrder1 = constraintObj1.getInsertionOrder();
		int insertionOrder2 = constraintObj2.getInsertionOrder();

		if(insertionOrder1 > insertionOrder2)
		{
			return 1;
		}
		else if(insertionOrder1 < insertionOrder2)
		{
			return -1;
		}

		return 0;
	}
}