package org.hfjv.framework.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Properties;

import org.hfjv.framework.core.constants.MessageConstants;

/**
 * <p>
 *  An utility class to deal with the configured error messages
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 *
 */
public class MessageUtil
{
	/**
	 * <p>
	 * A private class level instance of <tt>java.util.Properties</tt>
	 * </p>
	 */
	private static Properties _msgProperties = null;

	/**
	 * <p>
	 * A class level constant to hold the properties file name of the HFJV engine
	 * </p>
	 */
	private static final String HFJV_MSG_PROPERTIES = "hfjv-msg.properties";

	/**
	 * <p>
	 * A class level <tt>HashMap</tt> to hold the key for error code
	 * </p>
	 */
	private static HashMap<String,String> _errorKeyMap = null;

	/**
	 * <p>
	 * A private class level instance of <tt>PropetyUtil</tt> class
	 * </p>
	 */
	private static PropertyUtil _propertyUtil = PropertyUtil.getInstance();

	static
	{
		initMsgProperties();
		initErrorKeyMap();
	}

	/**
	 * <p>
	 * This method initializes the message properties by reading the properties file
	 * </p>
	 */
	public static void initMsgProperties()
	{
		System.out.println("initMsgProperties() - ENTER");

		_msgProperties = PropertyUtil.getProperties(
						HFJV_MSG_PROPERTIES,false);

		System.out.println("msgProperites : "+_msgProperties);

		if(null==_msgProperties)
		{
			System.err.println("Unable to load the file : "+
								HFJV_MSG_PROPERTIES);
			System.exit(1);
		}
		else
		{
			PropertyUtil.checkAndListDetails(_msgProperties,
								HFJV_MSG_PROPERTIES);
		}

		System.out.println("initMsgProperties() - EXIT");
	}

	/**
	 * <p>
	 * This method intializes the error key map
	 * </p>
	 */
	public static void initErrorKeyMap()
	{
		_errorKeyMap = MessageConstants.getErrorKeyMap();
	}

	/**
	 * <p>
	 * This method returns the default error message for the given key
	 * </p>
	 * 
	 * @param key
	 * 		the key for the error message configured
	 * 
	 * @return
	 * 		the configured error message
	 */
	public static String getDefaultErrorMsg(String key)
	{
		return _msgProperties.getProperty(key);
	}

	/**
	 * <p>
	 * This method gets the <tt>formatted</tt> message configured in the
	 * properties file by supplying the values in the <tt>valuesArray</tt>
	 * in order to the <tt>pattern</tt> specified in the message configured
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> As this method indeed calls the
	 * {@link #getFormattedFrameworkMsg(String, Object[])} by passing an empty
	 * <tt>String</tt> to the <tt>valuesArray</tt> (second parameter), it is
	 * suggested to be used ONLY for the messages which does NOT have any
	 * placeholders for dynamic values.
	 * </p>
	 *
	 * @param key
	 *		the key to pick up the raw/configured message with a pattern
	 *
	 * @return
	 * 		the formatted message with the values supplied for all the
	 *		placeholders
	 */
	public static String getFormattedFrameworkMsgWithNoParam(String key)
	{
		return getFormattedFrameworkMsg(key, "");
	}


	/**
	 * <p>
	 * This method gets the default framework oriented <tt>formatted</tt>
	 * message configured in the properties file by supplying the values
	 * in the <tt>valuesArray</tt> in order to the <tt>pattern</tt>
	 * specified in the message configured
	 * </p>
	 *
	 * @param key
	 *		the key to pick up the raw/configured message with a pattern
	 *
	 * @param valuesArray
	 *		the values to be filled in the appropriate place holders
	 *		in order
	 *
	 * @return
	 * 	the formatted message with the values supplied for all the
	 *		place holders
	 */
	public static String getFormattedFrameworkMsg(String key, Object... valuesArray)
	{
		String msgConfigured = _msgProperties.getProperty(key);

		String formattedErrorMsg = MessageFormat.format(msgConfigured, valuesArray);

		return formattedErrorMsg;
	}

	/**
	 * <p>
	 * This method gets the <tt>formatted</tt> message configured in the
	 * properties file by supplying the values in the <tt>valuesArray</tt>
	 * in order to the <tt>pattern</tt> specified in the message configured
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> As this method indeed calls the
	 * {@link #getFormattedMsg(String, Object[])} by passing an empty
	 * <tt>String</tt> to the <tt>valuesArray</tt> (second parameter), it is
	 * suggested to be used ONLY for the messages which does NOT have any
	 * place holders for dynamic values.
	 * </p>
	 *
	 * @param key
	 *		the key to pick up the raw/configured message with a pattern
	 *
	 * @return
	 * 	the formatted message with the values supplied for all the
	 *		place holders
	 */
	public static String getFormattedMsgWithNoParam(String key)
	{
		return getFormattedMsg(key, "");
	}


	/**
	 * <p>
	 * This method gets the <tt>formatted</tt> message configured in the
	 * properties file by supplying the values in the <tt>valuesArray</tt>
	 * in order to the <tt>pattern</tt> specified in the message configured
	 * </p>
	 *
	 * @param key
	 *		the key to pick up the raw/configured message with a pattern
	 *
	 * @param valuesArray
	 *		the values to be filled in the appropriate place holders
	 *		in order
	 *
	 * @return
	 * 	the formatted message with the values supplied for all the
	 *		place holders
	 */
	public static String getFormattedMsg(String key, Object... valuesArray)
	{
		String msgConfigured = _propertyUtil.getProperty(key);

		String formattedErrorMsg = MessageFormat.format(msgConfigured, valuesArray);

		return formattedErrorMsg;
	}

	/**
	 * <p>
	 * This method gets the <tt>formatted</tt> message configured in the
	 * properties file by supplying the values in the <tt>valuesArray</tt>
	 * in order to the <tt>pattern</tt> specified in the message configured
	 * </p>
	 *
	 * @param msgConfigured
	 *		the raw/configured message with a pattern
	 *
	 * @param valuesArray
	 *		the values to be filled in the appropriate place holders
	 *		in order
	 *
	 * @return
	 * 		the formatted message with the values supplied for all the
	 *		place holders
	 */
	public static String getFormattedValue(String msgConfigured, Object... valuesArray)
	{
		String formattedErrorMsg = MessageFormat.format(msgConfigured, valuesArray);

		return formattedErrorMsg;
	}

	/**
	 * <p>
	 * This method returns the property value of the given key
	 * </p>
	 * 
	 * @param key
	 * 		the key to pick up from the configured map
	 * 
	 * @return
	 * 		the value of the property
	 */
	public static String getProperty(String key)
	{
		return _msgProperties.getProperty(key);
	}

	/**
	 * <p>
	 * This method returns the configured message map in the form of 
	 * <tt>java.util.Properties</tt>
	 * </p>
	 * 
	 * @return
	 * 		the <tt>java.util.Properties</tt> instance
	 */
	public static Properties getMsgProperties()
	{
		return _msgProperties;
	}

	/**
	 * <p>
	 * This message sets the message properties
	 * </p>
	 * 
	 * @param properties
	 * 		properties to set
	 */
	public static void setMsgProperties(Properties properties)
	{
		_msgProperties = properties;
	}
	
	/**
	 * @return the _errorKeyMap
	 */
	public static HashMap<String, String> getErrorKeyMap() {
		return _errorKeyMap;
	}


	/**
	 * @param _errorKeyMap the _errorKeyMap to set
	 */
	public static void setErrorKeyMap(HashMap<String, String> _errorKeyMap) {
		MessageUtil._errorKeyMap = _errorKeyMap;
	}

}