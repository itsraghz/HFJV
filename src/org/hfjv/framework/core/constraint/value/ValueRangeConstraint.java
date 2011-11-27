package org.hfjv.framework.core.constraint.value;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_VALUE_RANGE;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_VALUE_RANGE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_VALUE_RANGE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_VALUERANGE_CONSTRAINT;

import java.math.BigInteger;
import java.util.ArrayList;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.helper.TypeConvertor;
import org.hfjv.framework.util.GlobalUtil;
import org.hfjv.framework.util.CollectionUtil;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 * A constraint to evaluate the field's value agasint the range of values configured
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValueRangeConstraint extends ValueConstraint
{

	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
							getLogger(ValueRangeConstraint.class);

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
	public ValueRangeConstraint()
	{
		super(HFJV_INSERT_ORDER_VALUERANGE_CONSTRAINT,
				HFJV_FIELD_CONSTRAINT_VALUE_RANGE,
				HFJV_KEY_ERROR_CODE_VALUE_RANGE,
				HFJV_KEY_ERROR_MSG_VALUE_RANGE);
	}

	public static void evaluate(String rangeSeparatedValue) throws ValidatorException
	{
		if(!StringUtil.isValidString(rangeSeparatedValue) ||
			rangeSeparatedValue.indexOf(GlobalUtil.VALUE_RANGE_SEPARATOR)==-1)
		{
			throw new ValidatorException("The range value should be in the "
						+" format '<minVal>:<maxVal>'");
		}
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);

		validateValueRange(field, this, valueToCheck);
	}

	/**
	 * <p>
	 * This method validates the field's value against the range of values configured
	 * </p>
	 * 
	 * <p>
	 * The reason for keeping this as a separate method is it is reused across.
	 * One another place of calling is the <tt>Constraint.isValueMatching()</tt>
	 *  method
	 * </p>
	 *
	 * @param field
	 * 				the field whose value to be validated
	 * 
	 * @param targetValue
	 * 				the range of values configured
	 * 
	 * @throws ValidatorException
	 * 				any exceptions during validation
 	 */
	public static void validateValueRange(Field field, Constraint constraint,
								String targetValue)
	throws ValidatorException
	{
		final String THIS_METHOD_NAME = "validateValueRange() - ";

		logger.enter(THIS_METHOD_NAME);

		/** 
		 * There is a possibility that the ValueRangeConstraint
		 * may get used for any kind of data (String, int, hexadecimal etc.,)
		 * though it gives an impression that it suits well for integer/numeric
		 * 
		 * Hence dividing it based on the data types
		 */
		String dataTypeOfField = field.getType();
		
		/* For a safer side */
		if(!StringUtil.isValidString(dataTypeOfField))
		{
			dataTypeOfField = GlobalUtil.DATATYPE_STRING;
		}
			
		if (GlobalUtil.isAllowedNumberType(dataTypeOfField))
		{
			ValidateValueRangeNumeric(field, constraint, targetValue);
		}
		else if (dataTypeOfField.equalsIgnoreCase(GlobalUtil.DATATYPE_DATE))
		{
			throw new ValidatorException("Presently no support for date values!");
		}
		else /* Rest everything will be of type String (STRING, HEXADECIMAL) */
		{
			ValidateValueRangeString(field, constraint, targetValue);
		}
		
		logger.exit(THIS_METHOD_NAME);
	}
	
	/**
	 * <p>
	 * This method will validate the value against the range of <tt>String</tt>
	 * values.
	 * </p>
	 * 
	 * @param field
	 * 				the field whose value to be validated
	 * 
	 * @param constraint
	 * 				the constraint that has the error code and message configured
	 * 
	 * @param targetValue
	 * 				the target range of values to be checked against the field's value
	 * 
	 * @throws ValidatorException
	 * 				the exception to be thrown during the evaluation
	 */
	private static void ValidateValueRangeString(Field field, 
														Constraint constraint, String targetValue)
	throws ValidatorException
	{
		
		final String THIS_METHOD_NAME = "ValidateValueRangeString() - ";

		logger.enter(THIS_METHOD_NAME);
		
		String sourceValue = getFieldValue(field);

		if(!StringUtil.isValidString(sourceValue))
		{
			throw new ValidatorException(field.getDisplayName()
							+ " value cannot be null");
		}

		ArrayList<String> rangeValueList =
													GlobalUtil.getRangeValuesInList(targetValue);

		if(!CollectionUtil.isValidList(rangeValueList))
		{
			throw new ValidatorException("Range value for " + field.getDisplayName()
					+ " is not properly configured to validate!");
		}
		
		int sourceValueInt = (int) sourceValue.charAt(0);

		BigInteger rangeValueLong1 = new BigInteger(String.valueOf(sourceValueInt));
		
		String minValStr = rangeValueList.get(0);
		String maxValStr = rangeValueList.get(1);
		
		int minValInt = (int) minValStr.charAt(0);
		int maxValInt = (int) maxValStr.charAt(0);
		
		BigInteger minVal1 = new BigInteger(String.valueOf(minValInt));
		BigInteger maxVal1 = new BigInteger(String.valueOf(maxValInt));

		if(rangeValueLong1.compareTo(minVal1) == -1 ||
				rangeValueLong1.compareTo(maxVal1) == 1)
		{
			String errorMsg = " Value '"+sourceValue + "' of the field '"
										+ field.getDisplayName() + "' should be between "
										+ minValStr + " and " + maxValStr;

			throw ExceptionHelper.getValidatorException(field, constraint, errorMsg);
		}
		
		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 * This method will validate the value against the range of <tt>numeric</tt>
	 * values.
	 * </p>
	 * 
	 * @param field
	 * 				the field whose value to be validated
	 * 
	 * @param constraint
	 * 				the constraint that has the error code and message configured
	 * 
	 * @param targetValue
	 * 				the target range of values to be checked against the field's value
	 * 
	 * @throws ValidatorException
	 * 				the exception to be thrown during the evaluation
	 */
	private static void ValidateValueRangeNumeric(Field field, 
														Constraint constraint, String targetValue)
	throws ValidatorException
	{
		final String THIS_METHOD_NAME = "ValidateValueRangeNumeric() - ";
		
		logger.enter(THIS_METHOD_NAME);
		
		String sourceValueActual = getFieldValue(field);
		
		//value may contain a sign bit!
		String sourceValue = GlobalUtil.excludeSignBit(sourceValueActual); 
		
		if(!StringUtil.isValidString(sourceValue))
		{
			throw new ValidatorException(field.getDisplayName()
				+ " value cannot be null");
		}
		
		BigInteger rangeValueLong1 = TypeConvertor.getBigInteger(sourceValue);
		
		ArrayList<String> rangeValueList =
												GlobalUtil.getRangeValuesInList(targetValue);
		
		if(!CollectionUtil.isValidList(rangeValueList))
		{
			throw new ValidatorException("Range value for " + field.getDisplayName()
					+ " is not properly configured to validate!");
		}
		
		String rangeValue1 = rangeValueList.get(0);
		String rangeValue2 = rangeValueList.get(1);
		
		/** 
		 * Exclude + Sign Bit for both the ranges, otherwise BigInteger
		 * throws a ClassCastException. However, a - sign is allowed.
		 * 
		 * Check Javadoc for more info
		 */
		rangeValue1 = GlobalUtil.excludeSignBitPlus(rangeValue1);
		rangeValue2 = GlobalUtil.excludeSignBitPlus(rangeValue2);
		
		BigInteger minVal1 = TypeConvertor.getBigInteger(rangeValueList.get(0));
		BigInteger maxVal1 = TypeConvertor.getBigInteger(rangeValueList.get(1));
		
		if(rangeValueLong1.compareTo(minVal1) == -1 ||
					rangeValueLong1.compareTo(maxVal1) == 1)
		{
			String errorMsg = " Value '"+sourceValue + "' of the field '"
										+ field.getDisplayName() + "' should be between "
										+ minVal1 + " and " + maxVal1;
			
			throw ExceptionHelper.getValidatorException(field, constraint, errorMsg);
		}
		
		logger.exit(THIS_METHOD_NAME);
	}	
}