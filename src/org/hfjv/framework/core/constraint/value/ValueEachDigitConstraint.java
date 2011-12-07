package org.hfjv.framework.core.constraint.value;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_VALUE_EACHDIGIT;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_VALUE_EACHDIGIT;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_VALUE_EACHDIGIT;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_VALUE_EACHDIGIT_CONSTRAINT;

import java.io.Serializable;
import java.util.ArrayList;

import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.util.GlobalUtil;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 *  A constraint to evaluate each digit of the field's value against
 *  the list of characters configured for the field
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValueEachDigitConstraint extends ValueConstraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = 7693692107133015081L;

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
	public ValueEachDigitConstraint()
	{
		super(HFJV_INSERT_ORDER_VALUE_EACHDIGIT_CONSTRAINT,
									HFJV_FIELD_CONSTRAINT_VALUE_EACHDIGIT,
									HFJV_KEY_ERROR_CODE_VALUE_EACHDIGIT,
									HFJV_KEY_ERROR_MSG_VALUE_EACHDIGIT);
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);

		String actualValueOfField = getFieldValue(field);

		ArrayList<String> valueList =
						GlobalUtil.getListOfTokens(valueToCheck);

		/* Exclude the sign bit if any */
		String signBitExcludedValue = GlobalUtil.excludeSignBit(actualValueOfField);

		if(!doesEachDigitMatch(field, signBitExcludedValue, valueList))
		{
			String errorMsg = "Each digit in the value '" + actualValueOfField
					+ "' of the field '" + field.getDisplayName()+ "' should be of " + valueList;

			throw ExceptionHelper.getValidatorException(field, this, errorMsg);
		}
	}

	/**
	 * <p>
	 * This method checks whether each digit of the field's value matches with
	 * the list of values configured
	 * </p>
	 * 
	 * @param strVal
	 * 					the actual value of the field
	 * 
	 * @param valueList
	 * 					the configured list of values to be checked against
	 * 
	 * @return
	 * 				a boolean true/false depends on the status
	 */
	private static boolean doesEachDigitMatch(Field field, String strVal,
								ArrayList<String> valueList)
	{
		boolean doesEachDigitMatch = true;

		if(StringUtil.isInvalidString(strVal))
		{
			return false;
		}

		char eachCharOfStr = ' ' ;
		char digitValueToBeChecked = ' ';

		boolean doesMatchWithOneOfValues = false;
		
		int indexOfRangeSeparator = -1;
		ValueRangeConstraint valueRangeConstraint = null;
		
		/* Used in ValueRangeConstraint */
		Field tempFieldForValueRange = new Field(field.getName());
		
		tempFieldForValueRange.setDisplayName(field.getDisplayName());
		
		boolean isError = false;
		
		//10101 => [0,1]
		for(int i=0; i < strVal.length(); i++)
		{
			eachCharOfStr = strVal.charAt(i); //1,0,1,0,1

			/** Reset for each digit */
			doesMatchWithOneOfValues = false;

			for(String valueToCheck : valueList) //[1,0]
			{
				if(!StringUtil.isValidString(valueToCheck))
				{
					continue;
				}
				
				/**
				 * Enhancement : What if one of the values is a ValueRange?
				 */
				indexOfRangeSeparator = valueToCheck.indexOf(
															GlobalUtil.VALUE_RANGE_SEPARATOR);
				
				/* One of the values is ValueRange based */
				if(indexOfRangeSeparator != -1)
				{
					valueRangeConstraint = new ValueRangeConstraint();
					valueRangeConstraint.setValueToCheck(valueToCheck);
					
					/* 
					 * Here we should check each digit of the actual Field's value.
					 * 
					 * Hence, cloning the field with the different value each time
					 * as per iteration
					 */
					tempFieldForValueRange.setValue(String.valueOf(eachCharOfStr));

					try {
						valueRangeConstraint.evaluate(tempFieldForValueRange);
					}catch(ValidatorException validatorException) {
						doesMatchWithOneOfValues = false;
						isError = true;
					}

					/*
					 * Reset the indexOfRangeSeparator to -1 so that it would NOT
					 * affect the next element in the list
			 		 */
					indexOfRangeSeparator = -1;
					
					valueRangeConstraint = null;

					if(!isError)
					{
						doesMatchWithOneOfValues = true;
						break;
					}
					
					/* Reset isError to false for next value in the list */
					isError = false;					
				}
				else /* It is a scalar value */
				{
					digitValueToBeChecked = valueToCheck.charAt(0); //1,0

					/** If matches with any digit, set the flag to true and break! */
					if(eachCharOfStr == digitValueToBeChecked)
					{
						doesMatchWithOneOfValues = true;
						break;
					}
				}
			}

			/*
			 * Evaluate the flag! It should be true.
			 *
			 * If it is still false, then this particular digit does NOT
	 	 	 * match with any of the values in the valueList.
		 	 *
			 * Break the loop and return false!
			 */
			if(!doesMatchWithOneOfValues)
			{
				doesEachDigitMatch = false;
				break;
			}
		}
		
		/* Clean up */
		tempFieldForValueRange = null;
		valueRangeConstraint = null;

		return doesEachDigitMatch;
     }
}