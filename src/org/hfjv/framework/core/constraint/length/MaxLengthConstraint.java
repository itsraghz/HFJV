package org.hfjv.framework.core.constraint.length;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_LENGTH_MAX;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_LENGTH_MAX;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_LENGTH_MAX;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_MAXLENGTH_CONSTRAINT;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;


/**
 * <p>
 *  A constraint to evaluate the maximum length of the field's value
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class MaxLengthConstraint extends LengthConstraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = -5155129094679908632L;
	

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
	public MaxLengthConstraint()
	{
		super(HFJV_INSERT_ORDER_MAXLENGTH_CONSTRAINT,
				HFJV_FIELD_CONSTRAINT_LENGTH_MAX,
				HFJV_KEY_ERROR_CODE_LENGTH_MAX,
				HFJV_KEY_ERROR_MSG_LENGTH_MAX);
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

		if(bigDecimalDigitLength.compareTo(valueToCheck)==1)
		{
			String errorMsg = "Value of field '"+ field.getDisplayName()
					+ "' can contain only a maximum of "	+ valueToCheck + " characters";

			throw ExceptionHelper.getValidatorException(field, this, errorMsg);
		}
	}
}