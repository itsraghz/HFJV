package org.hfjv.framework.core.constraint.digit;

import org.hfjv.framework.core.constraint.Constraint;

/**
 * <p>
 *  A super class for all the digit based constraints
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class DigitConstraint extends Constraint
{
	/**
	 * <p>
	 *  An overloaded, four argument constructor
	 * </p>
	 * 
	 * @param insertionOrder
	 * 				the insertion order of the constraint
	 * @param name
	 * 				the name of the constraint
	 * @param errorCodeKey
	 * 				the key to error code configured
	 * @param errorMsgKey
	 * 				the key to error message configured
	 */
	public DigitConstraint(int insertionOrder, String name,
			String errorCodeKey, String errorMsgKey)
	{
		super(insertionOrder, name, errorCodeKey, errorMsgKey);
	}
}