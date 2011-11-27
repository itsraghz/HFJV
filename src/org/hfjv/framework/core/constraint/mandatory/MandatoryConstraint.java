package org.hfjv.framework.core.constraint.mandatory;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_NULL;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_NULL;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_MANDATORY;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_MANDATORY_CONSTRAINT;

import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 * A constraint to ensure the field's value is mandatory and it should
 * neither be <tt>null</tt> nor <tt>empty</tt>
 * </p>
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class MandatoryConstraint extends Constraint
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
	public MandatoryConstraint()
	{
		super(HFJV_INSERT_ORDER_MANDATORY_CONSTRAINT,
			HFJV_FIELD_CONSTRAINT_MANDATORY,
			HFJV_KEY_ERROR_CODE_NULL,
			HFJV_KEY_ERROR_MSG_NULL);
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);

		String value = getFieldValue(field);

		boolean isValidValueToCheck = StringUtil.isValidString(valueToCheck);

		if(isValidValueToCheck)
		{
			boolean isValidCharConfiguredForWord = valueToCheck.equalsIgnoreCase("Yes");

			boolean isValidCharConfiguredForChar = valueToCheck.equalsIgnoreCase("Y");

			boolean isValidForChars = isValidCharConfiguredForWord || isValidCharConfiguredForChar;

			if(isValidForChars)
			{
				if(!StringUtil.isValidString(value))
				{
					String errorMsg = "Value of the field '" + field.getDisplayName()
																		+ "' should NOT be null or empty";

					throw ExceptionHelper.getValidatorException(field, this, errorMsg);
				}
			}
		}
	}
}