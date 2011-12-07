package org.hfjv.framework.helper;

import static org.hfjv.framework.core.constants.MessageConstants.HFJV_KEY_GLOBAL_ERROR_KEY;
import static org.hfjv.framework.core.constants.MessageConstants.KEY_GLOBAL_ERROR_DESC;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_ERRORCODE;
import static org.hfjv.framework.util.GlobalUtil.MODULE_FIELD_SEPARATOR;

import java.util.LinkedHashMap;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constants.MessageConstants;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.exception.ErrorDetails;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.core.validator.assembler.ValidatorAssembler;
import org.hfjv.framework.util.CollectionUtil;
import org.hfjv.framework.util.MessageUtil;
import org.hfjv.framework.util.PropertyUtil;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 * A helper class for all the exceptions used inside HFJV
 * </p>
 *
 * @author Raghavan alias Saravanan M
 * @since HFJV 1.0, 15 July 2011, Friday
 *
 */
public class ExceptionHelper
{
	/**
 	 * <p>
	 * A private class lever logger instance of this class
	 * </p>
 	 */
	private static Logger logger = LoggerFactory.getInstance().
																getLogger(ExceptionHelper.class);

	/**
 	 * <p>
	 * A private class lever instance of <tt>PropertyUtil</tt> class
	 * </p>
 	 */
	private static PropertyUtil _propertyUtil = PropertyUtil.getInstance();

	/**
	 * <p>
	 * This method returns the error description configured with the key being passed
	 * </p>
	 * 
	 * @param errorDescKey
	 * 				the key for the error description
	 * 
	 * @param defaultErrorMsgKey
	 * 				the key for the default error message
	 * 
	 * @return
	 * 				the error description to be used
	 */
	public static String getErrorDesc(String errorDescKey, String defaultErrorMsgKey)
	{
		boolean isValid = StringUtil.isValidString(errorDescKey);

		return (isValid ? (_propertyUtil.getProperty(errorDescKey)) :
									(MessageUtil.getDefaultErrorMsg(defaultErrorMsgKey)));
	}

	/**
	 * <p>
	 * This method returns an instance of <tt>ValidatorException</tt> based on the
	 * passed arguments
	 * </p>
	 * 
	 * @param field
	 * 			a field instance to pass necessary information for constructing an
	 * 			instance of <tt>ValidatorException</tt>
	 * 
	 * @param constraint
	 * 			a constraint instance to pass necessary information for constructing an
	 * 			instance of <tt>ValidatorException</tt>
	 * 
	 * @return
	 * 			an instance of <tt>ValidatorException</tt>
	 */
	public static ValidatorException getValidatorException(Field field,
					Constraint constraint, String msgFormattedByValidate)
	{

		String errorCode = getFinalErrorCode(constraint, field);

		/**
		 * As of now the errorMsg is NOT customized. So using whatever
	  	 * it was sent from the appropriate constraint Type!
	 	 */
		/* String errorDesc = getFinalErrorDesc(constraint, field);

		return getValidatorException(errorCode, errorDesc); */

		return getPrefilledValidatorException(field, errorCode, msgFormattedByValidate);
	}

	/**
	 * <p>
	 * This method returns the final error code to be used
	 * </p>
	 * 
	 * @param constraint
	 * 		the constraint instance
	 * 
	 * @param field
	 * 		the field instance
	 * 
	 * @return
	 * 		the final error code to be used
	 */
	public static String getFinalErrorCode(Constraint constraint,  Field field)
	{
		final String THIS_METHOD_NAME = "getFinalErrorCode() - ";

		logger.enter(THIS_METHOD_NAME);

		String errorCode = null;

		logger.debug(THIS_METHOD_NAME + " field Long String : "+field.toLongString());

		String fieldName = field.getName();

		int indexOfSeparator = fieldName.indexOf("-");

		String moduleName = null;

		if(indexOfSeparator != -1)
		{
			moduleName = fieldName.substring(0, indexOfSeparator);
		}

		logger.debug(THIS_METHOD_NAME + " moduleName : "+moduleName);

		/**
	 	 * Algorithm:
		 * ----------
		 * 1. If field level is set, take that.
	 	 * 2. Else, consider the specific module level constraint
	 	 * 3. Otherwise, take the global constraint level if configured
	 	 * 4. Else, take the global error code
		 * 5. If all them fail, take the default value of the type of constraint
		 */

		/* 1. Get from field level */
		LinkedHashMap<String, ErrorDetails> errorInfoMapOfField =
									field.getErrorInfoMap();

		if(CollectionUtil.isValidMap(errorInfoMapOfField))
		{
			ErrorDetails errorInfoObj = errorInfoMapOfField.get(constraint.getName());

			if(null!=errorInfoObj)
			{
				errorCode = errorInfoObj.getErrorCode();
			}

			logger.debug(THIS_METHOD_NAME + " errorCode from field level : "
										+ errorCode);
		}

		/* 2. Get from specific module level constraint,
		 * if the errorCode obtained earlier is invalid!
		 */
		if(StringUtil.isInvalidString(errorCode))
		{
			//errorCode = getFrameworkGlobalErrorCodeValue();

			/** constraint.setName() was set during the instantiation */
			errorCode = ValidatorAssembler.getModuleSpecificErrorMap().
						get(moduleName + MODULE_FIELD_SEPARATOR
							+ constraint.getName());

			logger.debug(THIS_METHOD_NAME
				+ " errorCode from specific module level : " + errorCode);
		}

		/* 3. Get from global module level configuration */
		if(StringUtil.isInvalidString(errorCode))
		{
			errorCode = ValidatorAssembler.getGlobalModuleErrorMap().
							get(constraint.getName());

			logger.debug(THIS_METHOD_NAME
				+ " errorCode from global module level : "+errorCode);
		}


		/* 4. Get from global level configuration */
		if(StringUtil.isInvalidString(errorCode))
		{
			logger.debug(THIS_METHOD_NAME + " constraint name as key : "+
								constraint.getName());

			errorCode = ValidatorAssembler.getGlobalErrorMap().get(
							HFJV_FIELD_ERRORCODE);

			logger.debug(THIS_METHOD_NAME
				+ " errorCode from global level : "+errorCode);

		}

		/* 5. Get from the default value of the constraint instance */
		if(StringUtil.isInvalidString(errorCode))
		{
			errorCode = constraint.getErrorInfo().getErrorCode();

			logger.debug(THIS_METHOD_NAME
				+ " errorCode from constraint type level : "+errorCode);

		}

		/**
	 	 * A final check : what if the configured value in the
		 * hfjv-msg.properties also null OR invalid?
		 */
		if(StringUtil.isInvalidString(errorCode))
		{
			logger.info(THIS_METHOD_NAME + " NONE of the hierarchical constraints "
				+ " for errorCode were true. Assigning -1 to errorCode...");

			errorCode = "-1";
		}

		logger.info(THIS_METHOD_NAME + " errorCode : "+errorCode);

		logger.exit(THIS_METHOD_NAME);

                return errorCode;
	}

	/**
	 * <p>
	 * As of now it is NOT used. May be later.
	 * </p>
	 *
	 * @param constraint
	 * @param field
	 * @param msgFormattedByValidate
	 * @return
	 * @deprecated
	 */
	public static String getFinalErrorDesc(Constraint constraint, Field field)
	{
            final String THIS_METHOD_NAME = "getFinalErrorDesc() - ";
            
		String errorDesc = null;

		/**
	 	 * Algorithm:
		 * ----------
		 * 1. If field level is set, take that.
	 	 * 2. Else, consider the specific module level constraint
	 	 * 3. Otherwise, take the global constraint level if configured
	 	 * 4. Else, take the global error code
		 * 5. If all them fail, take the default value of the type of constraint
		 */

		/* 1. Get from field level */
		LinkedHashMap<String, ErrorDetails> errorInfoMapOfField =
									field.getErrorInfoMap();

		if(CollectionUtil.isValidMap(errorInfoMapOfField))
		{
			ErrorDetails errorInfoObj = errorInfoMapOfField.get(constraint.getName());

			if(null!=errorInfoObj)
			{
				errorDesc = errorInfoObj.getErrorDesc();
			}

			logger.debug(THIS_METHOD_NAME + " errorDesc from field level : "
										+ errorDesc);
		}

		/* 2. Get from specific module level constraint,
		 * if the errorCode obtained earlier is invalid!
		 */
		if(StringUtil.isInvalidString(errorDesc))
		{
			//errorCode = getFrameworkGlobalErrorCodeValue();
			errorDesc = null;
		}

		/* 3. Get from global module level configuration */
		if(StringUtil.isInvalidString(errorDesc))
		{
			errorDesc = null;
		}

		/* 4. Get from global level configuration */
		if(StringUtil.isInvalidString(errorDesc))
		{
			errorDesc = getGlobalErrorDescConfigured();
		}

		/* 5. Get from the default value of the constraint instance */
		if(StringUtil.isInvalidString(errorDesc))
		{
			errorDesc = constraint.getErrorInfo().getErrorDesc();
		}

		/** finally populate the value holder */
		errorDesc = MessageUtil.getFormattedValue(
						errorDesc, field.getDisplayName());

		return errorDesc;

	}

	/**
	 *
	 * @param constraint
	 * @return
	 */
	public static String getFinalErrorDesc(Constraint constraint)
	{
		String errorDesc = null;

		/* errorDesc = constraint.getErrorDesc(); */

		if(StringUtil.isInvalidString(errorDesc))
		{
			errorDesc = getFrameworkGlobalErrorDescValue();
		}

		return errorDesc;
	}

	/**
	 *
	 * @return
	 */
	public static boolean isValidGlobalErrorCode()
	{
		return StringUtil.isValidString(getGlobalErrorCodeConfigured());
	}

	public static String getGlobalErrorCodeConfigured()
	{
		return _propertyUtil.getProperty(HFJV_KEY_GLOBAL_ERROR_KEY);
	}

	public static boolean isValidFrameworkGlobalErrorCode()
	{
		return StringUtil.isValidString(getFrameworkGlobalErrorCodeValue());
	}

	public static String getFrameworkGlobalErrorCodeValue()
	{
		return MessageConstants.GLOBAL_ERROR_KEY_VALUE;
	}

	public static boolean isValidGlobalErrorDesc()
	{
		return StringUtil.isValidString(getGlobalErrorDescConfigured());
	}

	public static String getGlobalErrorDescConfigured()
	{
		return _propertyUtil.getProperty(KEY_GLOBAL_ERROR_DESC);
	}

	public static boolean isValidFrameworkGlobalErrorDesc()
	{
		return StringUtil.isValidString(getFrameworkGlobalErrorDescValue());
	}

	public static String getFrameworkGlobalErrorDescValue()
	{
		return MessageConstants.GLOBAL_ERROR_MSG_VALUE;
	}

	/**
	 * <p>
	 * This method gives a new <tt>ValidatorException</tt> instance with 
	 * few pre-filled information based on the passed arguments
	 * </p>
	 * 
	 * @param errorCode
	 * 			the error code to be filled into the ValidatorException instance
	 * 
	 * @param errorDesc
	 * 			the error description to be filled into the ValidatorException instance
	 * 
	 * @return
	 * 			the pre-filled <tt>ValidatorException</tt> instance
	 */
	public static ValidatorException getPrefilledValidatorException(Field field,
					String errorCode,  String errorDesc)
	{
		return instantiateValidatorException(field, errorCode, errorDesc, null);
	}

	/**
	 * <p>
	 * This method instantiates the <tt>ValidatorException</tt> based on the passed
	 * arguments
	 * </p>
	 * 
	 * @param field
	 * 			the field instance to construct a <tt>ValidatorException</tt> instance
	 * 
	 * @param errorCode
	 * 			the error code to construct a <tt>ValidatorException</tt> instance
	 * 
	 * @param errorDesc
	 * 			the error description to construct a <tt>ValidatorException</tt> instance
	 * 
	 * @param exception
	 * 			the exception instance to construct a <tt>ValidatorException</tt> instance
	 * 
	 * @return
	 * 			the constructed <tt>ValidatorException</tt> instance
	 */
	public static ValidatorException instantiateValidatorException(Field field,
				String errorCode, String errorDesc, Exception exception)
	{
		ValidatorException validatorException =
					new ValidatorException(errorCode, errorDesc);

		if(null!=exception)
		{
			validatorException.setCause(exception.getCause());
		}

		validatorException.setFieldName(field.getDisplayName());
		
		return validatorException;
	}
}