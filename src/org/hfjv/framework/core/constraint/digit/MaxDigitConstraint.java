package org.hfjv.framework.core.constraint.digit;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_DIGIT_MAX;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_DIGIT_MAX;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_DIGIT_MAX;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_MAXDIGIT_CONSTRAINT;

import java.math.BigInteger;

import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.util.GlobalUtil;

/**
 * <p>
 * A constraint to evaluate the maximum number of digits
 * of a field's value being passed
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class MaxDigitConstraint extends DigitConstraint
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
	public MaxDigitConstraint()
	{
		super(HFJV_INSERT_ORDER_MAXDIGIT_CONSTRAINT,
				HFJV_FIELD_CONSTRAINT_DIGIT_MAX,
				HFJV_KEY_ERROR_CODE_DIGIT_MAX,
				HFJV_KEY_ERROR_MSG_DIGIT_MAX);
	}

	@Override
	public void evaluate(Field field) throws ValidatorException
	{
		super.evaluate(field);

		String actualValue = getFieldValue(field);

		/** What if the value contains a sign digit at first ? a "-" or "+" ? */
		String value = GlobalUtil.excludeSignBit(actualValue);

		BigInteger length = new BigInteger(null!=value ? ""+value.length() : "0");

		BigInteger valueToCheck = (BigInteger) getNarrowedValueToCheck(field);

		if(length.compareTo(valueToCheck)==1)
		{
			String errorMsg = "Value '" + actualValue +"' of the field '"
							+ field.getDisplayName() + "' should be only upto "
							+ valueToCheck + " digits";

			throw ExceptionHelper.getValidatorException(field, this, errorMsg);
		}
	}

}