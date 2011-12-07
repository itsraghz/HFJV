package org.hfjv.framework.core.constraint.length;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_LENGTH_MIN;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_LENGTH_MIN;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_LENGTH_MIN;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_MINLENGTH_CONSTRAINT;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;

/**
 * <p>
 *  A constraint to evaluate the minimum length of the field's value
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class MinLengthConstraint extends LengthConstraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = -3407970350100273297L;

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
	public MinLengthConstraint ()
	{
		super(HFJV_INSERT_ORDER_MINLENGTH_CONSTRAINT,
				HFJV_FIELD_CONSTRAINT_LENGTH_MIN,
				HFJV_KEY_ERROR_CODE_LENGTH_MIN,
				HFJV_KEY_ERROR_MSG_LENGTH_MIN);
	}

	@Override
	public void evaluate(Field field) throws ValidatorException
	{
		super.evaluate(field);

		String value = getFieldValue(field);

		int digitLength = (null!=value) ? value.trim().length() : 0;

		String targetValue = (String) getNarrowedValueToCheck(field);

		BigDecimal valueToCheck = BigDecimal.valueOf(Long.valueOf(targetValue)); 

		BigDecimal bigDecimalDigitLength = new BigDecimal(digitLength);

		if(bigDecimalDigitLength.compareTo(valueToCheck)==-1)
		{
			String errorMsg = "Value of field '"+ field.getDisplayName()
							+ "' should contain minimum of "	+ valueToCheck + " characters";

			throw ExceptionHelper.getValidatorException(field, this, errorMsg);
		}
	}
}