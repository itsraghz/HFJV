package org.hfjv.framework.core.constraint.sign;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_SIGN;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_SIGN;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_SIGN;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_SIGN_CONSTRAINT;

import java.io.Serializable;
import java.math.BigInteger;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.util.GlobalUtil;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 * A constraint to evaluate the type of the field's value against the
 * type being configured
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class SignConstraint extends Constraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = 659643959003552513L;
	
	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
																			getLogger(SignConstraint.class);

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
	public SignConstraint()
	{
		super(HFJV_INSERT_ORDER_SIGN_CONSTRAINT,
			HFJV_FIELD_CONSTRAINT_SIGN,
			HFJV_KEY_ERROR_CODE_SIGN,
			HFJV_KEY_ERROR_MSG_SIGN);
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);

		String actualValue = getFieldValue(field);

    	/** What if the value contains a sign digit at first? a "-" or "+" ? */
		String signBitExcludedValue = GlobalUtil.excludeSignBit(actualValue);

		String fieldType = field.getType();

		/** check if it is a number type */
		/* if(GlobalUtil.getNumberTypeList().contains(fieldType)
				&& StringUtil.isValidString(signBitExcludedValue))*/
		if(StringUtil.isValidString(signBitExcludedValue))
		{
			//long numberValue = (Long) getNarrowedFieldValue(field);

			/* Looks like NOT needed as the number gets narrowed down below */
			//actualValue = (String) getNarrowedFieldValue(field);

			/**
	 		 * If there is a + prefix, BigInteger throws an error.
			 *
			 * 	Overall Java SDK, considers only the negative (minus)
			 *	symbol as a prefix for any numeric value!
			 *
			 * As it would change the meaning of the value during conversion,
			 * if it is already negative, do it only for +ve values.
			 */
			if(actualValue.charAt(0)=='+')
			{
				actualValue = signBitExcludedValue;
			}

			BigInteger numberValueBigInt = null;

			/*
		 	 * There are two possibilities for the signConstraint.
		 	 * As the signConstraint is used ONLY for numeric values and
			 * at the moment, there are two possibilities -
			 *
			 *		1. Decimal
			 *		2. HexaDecimal
			 *
			 * As of now, only explicit data type is hexadecimal. Rest all
			 * are by default decimal. Hence, decimal is (base 10) taken
			 * in the else statement.
		 	 */
			if(fieldType.equalsIgnoreCase("hexadecimal"))
			{
				/*
				 * Here it throws a ParseException if the number does NOT
				 * fall in the range of 0-9, A-F, a-f.
				 *
				 * To avoid that we ensure it is a valid hex number
				 */
				if(!GlobalUtil.isValidHexString(signBitExcludedValue))
				{
					String errorMsg = "Value '"+actualValue + "' of the field '"
							+ field.getDisplayName()
							+ "' should be a valid hexadecimal number";

					throw ExceptionHelper.getValidatorException(field, this, errorMsg);
				}

				numberValueBigInt = (BigInteger) new BigInteger(actualValue, 16);
			}
			else
			{
				/*
				 * Here it throws a ParseException if the number does NOT
				 * seem to be a proper number.
			 	 *
				 * To avoid that we ensure it is a valid number
				 */
				if(!GlobalUtil.isValidNumber(signBitExcludedValue))
				{
					String errorMsg = "Value '"+actualValue + "' of the field '"
									+ field.getDisplayName() + "' should be a valid number";

					throw ExceptionHelper.getValidatorException(field, this, errorMsg);
				}

				numberValueBigInt = (BigInteger) new BigInteger(actualValue, 10);
			}

			BigInteger bigIntegerZero = new BigInteger("0");

			if(StringUtil.isValidString(valueToCheck))
			{
				if(valueToCheck.equalsIgnoreCase("+"))
				{
					if(numberValueBigInt.compareTo(bigIntegerZero)==-1)
					{
						String errorMsg = "Value '"+actualValue + "' of the field '"
										+ field.getDisplayName()+ "' should be a positive number";

						throw ExceptionHelper.getValidatorException(field, this, errorMsg);
					}
				}

				else if(valueToCheck.equalsIgnoreCase("-"))
				{
					if(numberValueBigInt.compareTo(bigIntegerZero)==1)
					{
						String errorMsg = "Value '"+actualValue + "' of the field '"
									+ field.getDisplayName()+ "' should be a negative number";

						throw ExceptionHelper.getValidatorException(field, this, errorMsg);
					}
				}

				/**
				 * Special case, if at all any value should NOT have any of
				 * the signs ('+' or '-'), people can use 'noSign'
				 */
				else if(valueToCheck.equalsIgnoreCase("noSign"))
				{
					//actualValue = (String) getNarrowedFieldValue(field);
					
					BigInteger actualValueBI = (BigInteger) getNarrowedFieldValue(field);
					actualValue = actualValueBI.toString();

					String errorMsg = "Value '"+actualValue + "' of the field '"
								+ field.getDisplayName()
								+ "' should NOT contain any sign";

					if((actualValue.charAt(0)=='+') || (actualValue.charAt(0)=='-'))
					{
						throw ExceptionHelper.getValidatorException(field, this, errorMsg);
					}
				}

				/* Additional fix -- if anything else comes as a value */
				else
				{
					String errorMsg = field.getDisplayName()
						+ " - Invalid value to check['"+valueToCheck+"]. Configure it properly";

					throw ExceptionHelper.getValidatorException(field, this, errorMsg);
				}
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