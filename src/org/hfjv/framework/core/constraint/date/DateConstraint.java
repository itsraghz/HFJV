package org.hfjv.framework.core.constraint.date;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_CODE_DATE;
import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_ERROR_MSG_DATE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_DATE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_INSERT_ORDER_DATE_CONSTRAINT;


import java.text.ParseException;
import java.util.Date;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.util.DateUtil;
import org.hfjv.framework.util.StringUtil;



/**
 * <p>
 * A constraint to deal with the <tt>Date</tt> values
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class DateConstraint extends Constraint
{

	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
															getLogger(DateConstraint.class);
	/**
	 * <p>
	 * An overridden,  no argument, default public constructor
	 * </p>
	 */
	public DateConstraint ()
	{
		super(HFJV_INSERT_ORDER_DATE_CONSTRAINT,
									HFJV_FIELD_CONSTRAINT_DATE,
									HFJV_KEY_ERROR_CODE_DATE,
									HFJV_KEY_ERROR_MSG_DATE);
	}

	/**
	 * @Override
	 * <p>
	 * TODO: Half the way and not tested. Needs to be improved later.
	 * </p>
	 */
	public void evaluate(Field field)
	throws ValidatorException
	{
		//Date value = (Date) getNarrowedFieldValue(field);

		String actualValueOfField = (null!=field) ? field.getValue() : null;

		String errorMsg = null;

		if(!StringUtil.isValidString(actualValueOfField))
		{
			errorMsg = "Value of the field '"+ field.getDisplayName()
																		+"' should NOT be null";

			throw ExceptionHelper.getValidatorException(field, this, errorMsg);
		}

		String formatToCheck = this.getValueToCheck();

		Date dateObj = null;

		try
		{
			dateObj = DateUtil.getDateMatchingWithExactPattern(
								actualValueOfField, formatToCheck);
		}catch(ParseException parseException) {
			logger.error(parseException.getMessage());
			errorMsg = "Value '"+ actualValueOfField + "' of the field '"
				+ field.getDisplayName() + " is not a valid date. It should "
				+ " be in the format '" + formatToCheck + "'";

			throw ExceptionHelper.getValidatorException(field, this, errorMsg);
		}

		if(null==dateObj)
		{
		}
	}
}