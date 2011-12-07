package org.hfjv.framework.core.constraint.exclude;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_EXCLUDECHARS;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_EXCLUDECHARS;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_EXCLUDE_CHARS;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_EXCLUDECHARS_CONSTRAINT;


import java.io.Serializable;
import java.util.ArrayList;

import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.util.GlobalUtil;


/**
 * <p>
 * A constraint to evaluate the value of a field against the list of 
 * configured characters to be excluded.
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ExcludeCharsConstraint extends ExcludeConstraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = -6864458564025601493L;

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
	public ExcludeCharsConstraint()
	{
		super(HFJV_INSERT_ORDER_EXCLUDECHARS_CONSTRAINT,
			HFJV_FIELD_CONSTRAINT_EXCLUDE_CHARS,
			HFJV_KEY_ERROR_CODE_EXCLUDECHARS,
			HFJV_KEY_ERROR_MSG_EXCLUDECHARS);
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);

		ArrayList<String> excludedCharsList =
					GlobalUtil.getListOfTokens(valueToCheck);

		Constraint.actOnExcludedChars(field, this, excludedCharsList);
	}
}