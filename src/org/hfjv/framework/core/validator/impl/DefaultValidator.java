package org.hfjv.framework.core.validator.impl;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.validator.ValidateExecutor;
import org.hfjv.framework.core.validator.Validator;

/**
 * <p>
 * A default implementation class of <tt>Validator</tt> interface
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class DefaultValidator implements Validator
{

	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
															getLogger(DefaultValidator.class);

	/**
	 * <p>
	 * An implementation of the <tt>validate</tt> method
	 * </p>
	 * 
	 * @param moduleName
	 * 			the module that needs to be validated
	 */
	public void validate(String moduleName)
	throws ValidatorException
	{
		final String THIS_METHOD_NAME = "validate() - ";

		logger.enter(THIS_METHOD_NAME);

		ValidatorException _exception = null;

		try {
			ValidateExecutor.validate(moduleName);
		}catch(ValidatorException validatorException) {
			logger.error(THIS_METHOD_NAME + " ValidatorException occurred!");
			logger.error(THIS_METHOD_NAME + " Exception message : "
								+ validatorException.getMessage());
			logger.error(THIS_METHOD_NAME + " Error in Field : "
								+ validatorException.getFieldName());
			logger.error(THIS_METHOD_NAME + " Error Code : "
								+ validatorException.getErrorCode());
			logger.error(THIS_METHOD_NAME + " Error Desc : "
								+ validatorException.getErrorDesc());
			_exception = validatorException;

		} finally {
			/** Just to clear off all the values else they would be cached! */
			ValidateExecutor.resetAllFields(moduleName);
		}

		if(null!=_exception)
		{
			throw _exception;
		}

		logger.exit(THIS_METHOD_NAME);
	}
}