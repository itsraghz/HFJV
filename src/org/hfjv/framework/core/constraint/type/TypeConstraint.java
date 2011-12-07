package org.hfjv.framework.core.constraint.type;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_TYPE;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_TYPE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_TYPE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_TYPE_CONSTRAINT;

import java.io.Serializable;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.helper.TypeConvertor;
import org.hfjv.framework.util.GlobalUtil;
import org.hfjv.framework.util.NumberUtil;

/**
 * <p>
 * A constraint to evaluate the data type of the field's value against
 * the configured value
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class TypeConstraint extends Constraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = -7459485624338077865L;
	
	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
																				getLogger(TypeConstraint.class);

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
	public TypeConstraint()
	{
		super(HFJV_INSERT_ORDER_TYPE_CONSTRAINT,
			HFJV_FIELD_CONSTRAINT_TYPE,
			HFJV_KEY_ERROR_CODE_TYPE,
			HFJV_KEY_ERROR_MSG_TYPE);
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);

		TypeConstraint.evaluateType(field, field.getValue(), this);
	}

	/**
	 * <p>
	 * This method evaluates the type of the field against the configured value
	 * </p>
	 * 
	 * @param field
	 * 				the field to be evaluated
	 * @param value
	 * 				the value to be evaluated
	 * @param ValidatorException
	 * 				any exceptions during validation
	 */
	public static void evaluateType(Field field, String value, Constraint constraintObj)
	throws ValidatorException
	{
		String fieldType = field.getType();

		String errorMsg = "Specified value '"+value + "' of the field '" 
											+ field.getDisplayName()+ "' is not a valid number";

		/* check if it is a number type */
		if(GlobalUtil.isAllowedNumberType(fieldType))
		{
			if(NumberUtil.isInvalidNumber(value, fieldType))
			{
				throw ExceptionHelper.getValidatorException(field, constraintObj, errorMsg);
			}

			Number numberValue = TypeConvertor.getNumber(value);

			boolean isValueMinusOne = NumberUtil.isValueMinusOne(value);

			if(numberValue.equals(-1) && !isValueMinusOne)
			{
				throw ExceptionHelper.getValidatorException(field, constraintObj, errorMsg);
			}
		}
	}

	@Override
	public void selfEvaluate() throws ValidatorException
	{
		final String THIS_METHOD_NAME = "selfEvaluate() - ";
		
		logger.enter(THIS_METHOD_NAME);
		
		//Method Body
		
		logger.exit(THIS_METHOD_NAME);
	}
}