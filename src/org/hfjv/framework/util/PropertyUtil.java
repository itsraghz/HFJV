package org.hfjv.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;

/**
 * <p>
 * An utility class to deal with the configurable properties
 * </p>
 *
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class PropertyUtil
{
	/**
	 * <p>
	 * A class level constant to represent the main application property file
	 * <tt>hfjv.properties</tt>
	 * </p>
	 */
	private static String HFJV_PROPERTY_FILE = "hfjv.properties";
	
	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
																	getLogger(PropertyUtil.class);	

	/**
	 * <p>
	 * A class level <tt>java.util.Properties</tt> instance to hold the configured
	 * properties, to be used inside the application
	 * </p>
	 */
	private static Properties _properties = new Properties();

	/**
	 * <p>
	 * A class level instance of this class, to be used for singleton
	 * </p>
	 */
	private static PropertyUtil _instance = null;

	/**
	 * <p>
	 * A class level <tt>Iterator</tt> object, to be used inside the methods of this
	 * class
	 * </p>
	 */
	private static Iterator<Object> keySetIterator = null;

	/* Singleton */
	/**
	 * <p>
	 * A private constructor for a singleton
	 * </p>
	 */
	private PropertyUtil()
	{
	}

	/**
	 * A method to provide a singleton instance of the class
	 *
 	 * @return
	 * 		a single instance of the class
	 */
	public static PropertyUtil getInstance()
	{
		if(null==_instance)
		{
			_instance = new PropertyUtil();
		}

		return _instance;
	}

	static
	{
		loadProperties();
		initKeySetIterator();
	}

	/**
	 * <p>
	 * A method to load the properties of this class's <tt>Properties</tt> instance
	 * </p>
	 */
	public static void loadProperties()
	{
		_properties = getProperties(HFJV_PROPERTY_FILE, false);

		checkAndListDetails(_properties, HFJV_PROPERTY_FILE);
	}

	/**
	 * <p>
	 * A method to return the <tt>Properties</tt> object for the specified fileName
	 * </p>
	 * 
	 * @param fileName
	 * 		the fileName to be read and whose configured key, value pairs to be
	 * 		returned as <tt>java.util.Properties</tt> instance
	 * 
	 * @return
	 * 		a <tt>java.util.Properties</tt> instance backing up the key, value pairs
	 * 		in the <tt>fileName</tt>
	 */
	public static Properties getProperties(String fileName)
	{
		return getProperties(fileName, true);
	}

	/**
	 * <p>
	 * A method to return the <tt>Properties</tt> object for the specified fileName.
	 * This method attempts to read the properties file in the current classpath
	 * </p>
	 * 
	 * @param fileName
	 * 		the fileName to be read and whose configured key, value pairs to be
	 * 		returned as <tt>java.util.Properties</tt> instance
	 * 
	 * @param listProperties
	 * 		a boolean true/false indicating whether or not to list the properties after
	 * 		being read from the file
	 * 
	 * @return
	 * 		a <tt>java.util.Properties</tt> instance backing up the key, value pairs
	 * 		in the <tt>fileName</tt>
	 */
	public static Properties getProperties(String fileName, boolean listProperties)
	{
		
		final String THIS_METHOD_NAME = "getProperties(fileName, listProperties) - ";	
		
		InputStream is = Thread.currentThread().getContextClassLoader()
																	.getResourceAsStream(fileName);

		Properties _properties = null;

		if(null != is)
		{
			try {
				_properties = new Properties();
				_properties.load(is);
			}catch(IOException ioException) {
				logger.error(THIS_METHOD_NAME + 
						"IOException in loading properties from the file -- [" + fileName + "]");
				//ioException.printStackTrace();
				logger.error(ioException.getMessage(), ioException);
			}
		}
		else
		{
			logger.error(THIS_METHOD_NAME+ "Unable to load the properties file -- ["
								+ fileName +"]");
			System.exit(1);
		}

		if(listProperties)
		{
			checkAndListElements(_properties, fileName);
		}

		return _properties;
	}

	/**
	 * <p>
	 * This method checks for the properties instance not being null or empty and then
	 * lists the elements
	 * </p>
	 * 
	 * @param _properties
	 * 		the properties instance whose values needs to be investigated and printed
	 */
	public static void checkAndListElements(Properties _properties)
	{
		checkAndListElements(_properties, null);
	}

	/**
	 * <p>
	 * This method checks for the properties instance not being null or empty and then
	 * lists the elements along with the <tt>backingFileName</tt> in the output
	 * </p>
	 * 
	 * @param _properties
	 * 		the properties instance whose values needs to be investigated and printed
	 * 
	 * @param backingFileName
	 * 		the fileName to be printed along with the listing for additional information
	 */
	public static void checkAndListElements (Properties _properties,
																				String backingFileName)
	{
		final String THIS_METHOD_NAME = "checkAndListElements() - ";
		
		String backingFileNameToDisplay =
												getBackingFileNameForDisplay(backingFileName);

		if(null != _properties)
		{
			if(_properties.size() <= 0)
			{
				logger.error(THIS_METHOD_NAME + "Looks like the properties file"
					+ backingFileNameToDisplay + " is empty!");
			}
			else
			{
				//TODO: further R & D on redirecting to logger stream
				_properties.list(System.out);
				//_properties.list(logger);
			}
		}
	}

	/**
	 * <p>
	 * This method checks for the properties instance not being null or empty and then
	 * lists the elements
	 * </p>
	 * 
	 * @param _properties
	 * 		the properties instance whose values needs to be investigated and printed
	 */
	public static void checkAndListDetails(Properties _properties)
	{
		checkAndListDetails(_properties, null);
	}

	/**
	 * <p>
	 * This method checks for the properties instance not being null or empty and then
	 * lists the elements along with the backingFileName in the output
	 * </p>
	 * 
	 * @param _properties
	 * 		the properties instance whose values needs to be investigated and printed
	 * 
	 * @param backingFileName
	 * 		the fileName to be printed along with the listing for an additional input
	 */
	public static void checkAndListDetails(Properties _properties,
																					String backingFileName)
	{
		final String THIS_METHOD_NAME = "checkAndListDetails() - ";
		
		String backingFileNameToDisplay =
											getBackingFileNameForDisplay(backingFileName);

		logger.info("");
		logger.info(THIS_METHOD_NAME + " ---------------- [INVESTIGATING "
										+ backingFileNameToDisplay + " ] - START ------------------");

		String errorMsg = null;

		if(null != _properties)
		{
			if(_properties.size() <= 0)
			{
				errorMsg = "Looks like the properties file "
														+ backingFileNameToDisplay + " is empty!";
				logger.error(THIS_METHOD_NAME + errorMsg);
				//System.exit(1);
			}
			else
			{
				int elementSize = _properties.size();
				
				logger.info(THIS_METHOD_NAME + "The properties file "
														+ backingFileNameToDisplay + " is loaded with #"
														+ elementSize + " entries..");
			}
		}
		else
		{
			errorMsg = "Invalid Properties file "+backingFileNameToDisplay;
			GlobalUtil.stopExecutionWithError(errorMsg);
		}

		logger.info(THIS_METHOD_NAME + " ---------------- [INVESTIGATING "
								+ backingFileNameToDisplay + " ] - END ------------------");
		logger.info("") ;

	}

	/**
	 * <p>
	 * This method returns an appropriate value to be used for the backingFileName
	 * in the listing of properties
	 * </p>
	 * 
	 * @param backingFileName
	 * 		the fileName to be investigated for display
	 * 
	 * @return
	 * 		the backingFileName to be displayed
	 */
	public static String getBackingFileNameForDisplay(String backingFileName)
	{
		boolean isBackingFileValid = StringUtil.isValidString(backingFileName);

		String backingFileNameToDisplay = isBackingFileValid ? " {"
					+ backingFileName + "}" : "";

		return backingFileNameToDisplay;
	}

	/**
	 * <p>
	 * This method returns the configured value of the specified key. If the key does 
	 * NOT exist, it will return a <tt>null</tt> by virtue of <tt>java.util.Properties</tt>
	 * as the repository is backed by the Properties instance
	 * </p>
	 * 
	 * @param key
	 * 		the key to look at the repository
	 * 
	 * @return
	 * 		the configured value if any, otherwise <tt>null</tt>
	 */
	public String getProperty(String key)
	{
		return _properties.getProperty(key);
	}

	/**
	 * <p>
	 * This method returns the configured value of the specified key. If the key does 
	 * NOT exist, it will return a <tt>defaultValue</tt> passed as an argument
	 * </p>
	 * 
	 * @param key
	 * 		the key to look at the repository
	 * 
	 * @param defaultValue
	 * 		the default value to be returned if the configured key does NOT exist
	 * 
	 * @return
	 * 		the configured value if any, otherwise <tt>null</tt>
	 */	public String getProperty(String key, String defaultValue)
	{
		String value = _properties.getProperty(key);

		return StringUtil.isValidString(value) ? value : defaultValue;
	}

	 /**
	  * <p>
	  * This method initializes the keySet Iterator
	  * </p>
	  */
	public static void initKeySetIterator()
	{
		final String THIS_METHOD_NAME = "initKeySetIterator() - ";
		
		Set<Object> keys = _properties.keySet();

		Iterator<Object> keySetIterator = keys.iterator();

		if(null==keySetIterator)
		{
			logger.error(THIS_METHOD_NAME + 
										"An issue with preparing with the properties..");
		}

		setKeySetIterator(keySetIterator);
	}

	/**
	 * <p>
	 * This method gets the specific properties whose key is similar to the 
	 * <tt>keyToSearch</tt> , supplied as an argument
	 * </p>
	 * 
	 * @param keyToSearch
	 * 		the key to be searched from the repository
	 * 
	 * @return
	 * 		a map of matching properties and corresponding value
	 */
	public LinkedHashMap<String,  String> getSpecificProperties(String keyToSearch)
	{
		LinkedHashMap<String, String> specificMap =
						new LinkedHashMap<String, String>();

		String tentativeKey = null;
		String value = null;

		while(keySetIterator.hasNext())
		{
			tentativeKey = (String) keySetIterator.next();

			if(tentativeKey.startsWith(keyToSearch))
			{
				value = _properties.getProperty(tentativeKey);
				specificMap.put(tentativeKey, value);
			}
		}

		return specificMap;
	}

	/**
	 * <p>
	 * This method returns the map containing the specified values as a key from 
	 * the repository
	 * </p>
	 * 
	 * @param keyToSearch
	 * 		the key to be searched from the repository
	 * 
	 * @return
	 * 		a map with the matching entries
	 */
	public LinkedHashMap<String,  String> getSpecificValuesAsKeys(String keyToSearch)
	{
		LinkedHashMap<String, String> specificMap =
						new LinkedHashMap<String, String>();

		String tentativeKey = null;
		String value = null;

		while(keySetIterator.hasNext())
		{
			tentativeKey = (String) keySetIterator.next();

			if(tentativeKey.startsWith(keyToSearch))
			{
				value = _properties.getProperty(tentativeKey);
				specificMap.put(value, value);
			}
		}

		return specificMap;
	}

	/**
	 * <p>
	 * This method returns a list of matching keys as that of the <tt>keyToSearch</tt>
	 * </p>
	 * 
	 * @param keyToSearch
	 * 		the key to be searched in the repository
	 * 
	 * @return
	 * 		an <tt>ArrayList</tt> of matching keys
	 */
	public ArrayList<String> getSpecificKeysAsList(String keyToSearch)
	{
		ArrayList<String> specificKeyList = new ArrayList<String>();

		Set<Object> keys = _properties.keySet();

		Iterator<Object> keysIterator = keys.iterator();

		String tentativeKey = null;

		while(keysIterator.hasNext())
		{
			tentativeKey = (String) keysIterator.next();

			if(tentativeKey.startsWith(keyToSearch))
			{
				specificKeyList.add(tentativeKey);
			}
		}

		return specificKeyList;
	}

	public static Iterator<Object> getKeySetIterator()
	{
		return keySetIterator;
	}

	public static void setKeySetIterator(Iterator<Object> keySetIterator)
	{
		PropertyUtil.keySetIterator = keySetIterator;
	}
}