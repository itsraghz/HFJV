package org.hfjv.framework.core.constraint.value;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_VALUE_NOTSTART;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_VALUE_NOTSTART;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_VALUE_NOTSTART;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_VALUERANGE_CONSTRAINT;

import java.util.ArrayList;

import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.util.GlobalUtil;

/**
 * <p>
 * A constraint to evaluate the field's value NOT to start with any of the
 * characters configured
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValueNotStartWithConstraint extends ValueConstraint
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
	public ValueNotStartWithConstraint()
	{
		super(HFJV_INSERT_ORDER_VALUERANGE_CONSTRAINT,
								HFJV_FIELD_CONSTRAINT_VALUE_NOTSTART,
								HFJV_KEY_ERROR_CODE_VALUE_NOTSTART,
								HFJV_KEY_ERROR_MSG_VALUE_NOTSTART);
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);

		String actualValueOfField = getFieldValue(field);

		ArrayList<String> valueList =
						GlobalUtil.getListOfTokens(valueToCheck);

		/* Value may contain a sign bit! */
		actualValueOfField = GlobalUtil.excludeSignBit(actualValueOfField);

		for(String valueToCheck : valueList)
		{
			/**  Any of the values can contain a Range! -- Do Later */
			if(actualValueOfField.startsWith(valueToCheck))
			{
				String errorMsg = " Value '" + actualValueOfField
											+ "' of field '" + field.getDisplayName()
											+ "' should NOT start with '" + valueToCheck + "'";

				throw ExceptionHelper.getValidatorException(field, this, errorMsg);
			}
		}
	}
}