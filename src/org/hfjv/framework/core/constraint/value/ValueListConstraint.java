package org.hfjv.framework.core.constraint.value;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_VALUE_LIST;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_VALUE_LIST;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_VALUE_LIST;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_VALUELIST_CONSTRAINT;

import java.io.Serializable;
import java.util.ArrayList;

import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.util.GlobalUtil;

/**
 * <p>
 * A constraint to evaluate a field's value against the list of values configured
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValueListConstraint extends ValueConstraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = 5147698753243358742L;

	/**
	 * <p>
	 *  An overloaded, four argument constructor
	 * </p>
	 * 
	 * @param insertionOrder
	 * 				the insertion order of the constraint
	 * 
	 * @param name
	 * 				the name of the constraint
	 * 
	 * @param errorCodeKey
	 * 				the key to error code configured
	 * 
	 * @param errorMsgKey
	 * 				the key to error message configured
	 */
	public ValueListConstraint()
	{
		super(HFJV_INSERT_ORDER_VALUELIST_CONSTRAINT,
										HFJV_FIELD_CONSTRAINT_VALUE_LIST,
										HFJV_KEY_ERROR_CODE_VALUE_LIST,
										HFJV_KEY_ERROR_MSG_VALUE_LIST);
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);

		String actualValueOfField = getFieldValue(field);

		ArrayList<String> valueList =
						GlobalUtil.getListOfTokens(valueToCheck);

		int indexOfRangeSeparator = -1;

		boolean isValidValue = true;
		boolean isError = false;
		
		ValueRangeConstraint valueRangeConstraint = null;

		for(String valueToCheck : valueList)
		{
			indexOfRangeSeparator = valueToCheck.indexOf(
													GlobalUtil.VALUE_RANGE_SEPARATOR);

			if(indexOfRangeSeparator != -1)
			{
				/* One of the values is Range based */
				valueRangeConstraint = new ValueRangeConstraint();
				valueRangeConstraint.setValueToCheck(valueToCheck);

				try {
					valueRangeConstraint.evaluate(field);
				}catch(ValidatorException validatorException) {
					isValidValue = false;
					isError = true;
				}

				/*
				 * Reset the indexOfRangeSeparator to -1 so that it would NOT
				 * affect the next element in the list
		 		 */
				indexOfRangeSeparator = -1;

				if(!isError)
				{
					isValidValue = true;
				}
				
				valueRangeConstraint = null;
			}
			else
			{
				if(!valueToCheck.equals(actualValueOfField))
				{
					isValidValue = false;
				}
				else
				{
					isValidValue = true;
				}
			}

			if(isValidValue)
			{
				break;
			}

			/* Reset isError to false for next value in the list */
			isError = false;
		}

		if(!isValidValue)
		{
			throw getValidatorException(field, actualValueOfField, valueList.toString());
		}
	}

	public ValidatorException getValidatorException(Field field,
					String actualValueOfField, String value)
	{
            String errorMsg = " Value '"+actualValueOfField
									                    + "' of the field '" + field.getDisplayName()
									                    + "' should be within the list " + value;

            return ExceptionHelper.getValidatorException(field, this, errorMsg);
	}
}