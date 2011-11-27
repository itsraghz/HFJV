package org.hfjv.framework.core.constants;

import java.util.HashMap;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.util.MessageUtil;

/**
 * <p>
 *  A constants used for the configured messages, used across the application.
 *  It is used to hold the <tt>keys</tt> configured in the properties file.
 * </p>
 * 
 * @author Raghavan alias Saravanan M
 * @since HFJV 1.0, 15 July 2011, Friday
 *
 */
public class MessageConstants
{
	/**
	 * <p>
	 * A class level final logger instance for the logging
	 * </p>
	 */
	private static final Logger logger = LoggerFactory.getInstance().
																		getLogger(MessageConstants.class);
	
	/* ================ Name for each Error global ============= */

	public static final String HFJV_KEY_GLOBAL_ERROR_KEY =
						"hfjv.global.errorCode";

	public static final String HFJV_KEY_GLOBAL_ERROR_DESC =
						"hfjv.global.errorDesc";

	public static final String KEY_GLOBAL_ERROR_KEY =
						"hfjv.global.error.code";


	public static final String KEY_GLOBAL_ERROR_DESC =
						"hfjv.global.error.msg";


	public static final String GLOBAL_ERROR_KEY_VALUE =
					MessageUtil.getProperty(KEY_GLOBAL_ERROR_KEY);


	public static final String GLOBAL_ERROR_MSG_VALUE =
					MessageUtil.getProperty(KEY_GLOBAL_ERROR_DESC);

	/* ============= Name for each Error type ===================== */

	public static final String NAME_CONSTRAINT_NULL = "check_null";

	public static final String NAME_CONSTRAINT_INVALID = "check_invalid";

	/* ================== Keys for each errors =================== */

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>null</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_NULL =
						"hfjv.error.mandatory.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>null</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_NULL =
						"hfjv.error.mandatory.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>invalid</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_TYPE =
						"hfjv.error.type.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>invalid</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_TYPE =
						"hfjv.error.type.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>lesser than minLength</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_LENGTH_MIN =
						"hfjv.error.minLength.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>lesser than minLength</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_LENGTH_MIN =
						"hfjv.error.minLength.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>greater than maxLength</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_LENGTH_MAX =
						"hfjv.error.maxLength.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>greater than maxLength</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_LENGTH_MAX =
						"hfjv.error.maxLength.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>not any of the list of values</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_VALUE_LIST =
						"hfjv.error.valueList.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>not of any of the list of values</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_VALUE_LIST =
						"hfjv.error.valueList.msg";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>not any of the range of values</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_VALUE_RANGE =
						"hfjv.error.valueRange.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>not of any of the range of values</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_VALUE_RANGE =
						"hfjv.error.valueRange.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>lesser than minDigit</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_DIGIT_MIN =
						"hfjv.error.minDigit.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>lesser than minDigit</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_DIGIT_MIN =
						"hfjv.error.minDigit.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>greater than maxDigit</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_DIGIT_MAX =
						"hfjv.error.maxDigit.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>greater than maxDigit</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_DIGIT_MAX =
						"hfjv.error.maxDigit.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>having any of the characters to be excluded</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_EXCLUDECHARS =
						"hfjv.error.excludeChars.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>having any of the characters to be excluded</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_EXCLUDECHARS  =
						"hfjv.error.excludeChars.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>NOT having a sign of value</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_SIGN =
						"hfjv.error.sign.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>NOT having a sign of value</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_SIGN  =
						"hfjv.error.sign.msg";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>having a set of characters where it should NOT
	 * start with</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_VALUE_NOTSTART =
						"hfjv.error.valueNotStart.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>having a set of characters where it should NOT
	 * start with</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_VALUE_NOTSTART  =
						"hfjv.error.valueNotStart.msg";


	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>NOT having a specific value for each of its
 	 * character/digit</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_VALUE_EACHDIGIT =
						"hfjv.error.valueEachDigit.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>NOT having a specific value for each of its
 	 * character/digit</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_VALUE_EACHDIGIT  =
						"hfjv.error.valueEachDigit.msg";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error code</tt> when the
	 * input value is <tt>NOT having a proper date format configured</tt>
	 */
	public static final String HFJV_KEY_ERROR_CODE_DATE =
						"hfjv.error.date.code";

	/**
     * <p>
	 * A property to hold the <tt>key</tt> for the <tt>error message</tt> when the
	 * input value is <tt>NOT having a proper date format configured</tt>
	 */
	public static final String HFJV_KEY_ERROR_MSG_DATE  =
						"hfjv.error.date.msg";

	public static final String[] ARRAY_NAME_CONSTRAINT =
		new String[]
		{
			HFJV_KEY_ERROR_CODE_NULL,
			HFJV_KEY_ERROR_CODE_TYPE,
			HFJV_KEY_ERROR_CODE_LENGTH_MIN,
			HFJV_KEY_ERROR_CODE_LENGTH_MAX,
			HFJV_KEY_ERROR_CODE_VALUE_LIST,
			HFJV_KEY_ERROR_CODE_VALUE_RANGE,
			HFJV_KEY_ERROR_CODE_DIGIT_MIN,
			HFJV_KEY_ERROR_CODE_DIGIT_MAX,
			HFJV_KEY_ERROR_CODE_EXCLUDECHARS,
			HFJV_KEY_ERROR_CODE_SIGN,
			HFJV_KEY_ERROR_CODE_VALUE_NOTSTART,
			HFJV_KEY_ERROR_CODE_VALUE_EACHDIGIT
		};


	public static final String[] ARRAY_NAME_ERROR_KEY =
		new String[]
		{
			HFJV_KEY_ERROR_CODE_NULL,
			HFJV_KEY_ERROR_CODE_TYPE,
			HFJV_KEY_ERROR_MSG_LENGTH_MIN,
			HFJV_KEY_ERROR_MSG_LENGTH_MAX,
			HFJV_KEY_ERROR_MSG_VALUE_LIST,
			HFJV_KEY_ERROR_MSG_VALUE_RANGE,
			HFJV_KEY_ERROR_MSG_DIGIT_MIN,
			HFJV_KEY_ERROR_MSG_DIGIT_MAX,
			HFJV_KEY_ERROR_CODE_EXCLUDECHARS,
			HFJV_KEY_ERROR_MSG_SIGN,
			HFJV_KEY_ERROR_MSG_VALUE_NOTSTART,
			HFJV_KEY_ERROR_MSG_VALUE_EACHDIGIT
		};


	/* ============== Map having the name and keys ==================== */

	public static final HashMap<String, String> errorKeyMap =
																					new HashMap<String,String>();

	static
	{
		initErrorKeyMap();
	}

	public static void initErrorKeyMap()
	{
		final String THIS_METHOD_NAME = "initErrorKeyMap() - ";
		
		for(int i=0; i < ARRAY_NAME_CONSTRAINT.length; i++)
		{
			errorKeyMap.put(ARRAY_NAME_CONSTRAINT[i], ARRAY_NAME_ERROR_KEY[i]);
		}

		logger.info(THIS_METHOD_NAME + "Error Key, names are successfully mapped!");
	}

	public static HashMap<String, String> getErrorKeyMap()
	{
		return errorKeyMap;
	}

}