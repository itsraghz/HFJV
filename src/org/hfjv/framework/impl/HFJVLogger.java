package org.hfjv.framework.impl;

import static org.hfjv.framework.LoggerConstants.LOG_LEVEL_DEBUG;

import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.hfjv.framework.Logger;
import org.hfjv.framework.util.PropertyUtil;
import org.hfjv.framework.util.StringUtil;


/**
 * <p>
 * An implementation class of the <tt>Logger</tt>
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class HFJVLogger implements Logger
{
	/**
	 * <p>
	 * An instance of <tt>Log4J</tt> logger
	 * </p>
	 */
	private org.apache.log4j.Logger log4jLogger = null;

	/**
	 * <p>
	 * A class level instance of <tt>Properties</tt> to back up the configuration values
	 * </p>
	 */
	static Properties properties = null;

	/**
	 * <p>
	 * A class level constant to represent the <tt>HFJV Logger file</tt>
	 * </p>
	 */
	static final String LOG4J_PROPERTIES_FILE = "hfjv-logger.properties";

	static
	{
		properties = PropertyUtil.getProperties(LOG4J_PROPERTIES_FILE, false);
		PropertyUtil.checkAndListDetails(properties, LOG4J_PROPERTIES_FILE);

		PropertyConfigurator.configure(properties);

		System.out.println(StringUtil.getRepeatedChars("=", 70));

		System.out.println(StringUtil.getRepeatedChars("=", 6)
					+ " " + LOG4J_PROPERTIES_FILE  	+ " file has been configured "
					+ StringUtil.getRepeatedChars("=", 6));

		System.out.println(StringUtil.getRepeatedChars("=", 70));

	}

	/**
	 * <p>
	 * An overridden one argument constructor
	 * </p>
	 * 
	 * @param name
	 * 			the name of the logger
	 */
	public HFJVLogger(String name)
	{
		log4jLogger = org.apache.log4j.Logger.getLogger(name);
	}

	/**
	 * <p>
	 * A method to emit the more detailed level of information
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void debug(Object msg)
	{
		log4jLogger.debug(msg);
	}

	/**
	 * <p>
	 * A method to emit the more detailed level of information
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 * 
	 * @param cause
	 * 		the root cause of the exception, used in the catch blocks
	 */
	public void debug(Object msg, Throwable cause)
	{
		log4jLogger.debug(msg, cause);
	}
	
	/**
	 * <p>
	 * A method to emit the fine grained level of information, for a precise logging
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void info(Object msg)
	{
		log4jLogger.info(msg);
	}

	/**
	 * <p>
	 * A method to emit the fine grained level of information, for a precise logging
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 * 
	 * @param cause
	 * 		the root cause of an exception, used inside the catch blocks
	 */
	public void info(Object msg, Throwable cause)
	{
		log4jLogger.info(msg, cause);
	}
	
	/**
	 * <p>
	 * A method to emit the higher level of information, like warnings
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void warn(Object msg)
	{
		log4jLogger.warn(msg);
	}

	/**
	 * <p>
	 * A method to emit the higher level of information, like warnings
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 * 
	 * @param cause
	 * 		the root cause of an exception, used inside the catch block
	 */
	public void warn(Object msg, Throwable cause)
	{
		log4jLogger.warn(msg, cause);
	}

	/**
	 * <p>
	 * A method to emit the exceptional level of information, only at a critical level
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void fatal(Object msg)
	{
		log4jLogger.fatal(msg);
	}

	/**
	 * <p>
	 * A method to emit the exceptional level of information, only at a critical level
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 * 
	 * @param cause
	 * 		the root cause of an exception
	 */
	public void fatal(Object msg, Throwable cause)
	{
		log4jLogger.fatal(msg, cause);
	}

	/**
	 * <p>
	 * A method to emit the JVM, container related errors which are NOT recoverable
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void error(Object msg)
	{
		log4jLogger.error(msg);
	}

	/**
	 * <p>
	 * A method to emit the JVM, container related errors which are NOT recoverable
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 * 
	 * @param cause
	 * 		the root cause of exception
	 */
	public void error(Object msg, Throwable cause)
	{
		log4jLogger.error(msg, cause);
	}

	/**
	 * <p>
	 * This method gives the level of logger configured
	 * </p>
	 * 
	 * @return
	 * 		the logger level as a <tt>String</tt>
	 */
	public String getLevel()
	{
		Level level = log4jLogger.getLevel();

		String levelInString = (null!=level) ? level.toString() : "null";

		return levelInString;
	}

	/**
	 * <p>
	 * This method gives the name of logger configured
	 * </p>
	 * 
	 * @return
	 * 		the name of the logger as a <tt>String</tt>
	 */
	public String getName()
	{
		return log4jLogger.getName();
	}

	@Override
	public String toString()
	{
		return log4jLogger.toString();
	}

	/**
	 * <p>
	 * A method used for logging the entry of a method
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void enter(Object msg)
	{
		log4jLogger.debug(">>>> " + msg + " >>>> ENTER");
	}

	/**
	 * <p>
	 * A method used for logging the entry of a method
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 * 
	 * @param params
	 * 		the list of parameter, value combination of the method
	 */
	public void enter(Object msg, Object[] params)
	{
		throw new UnsupportedOperationException("Yet to be implemented");
	}

	/**
	 * <p>
	 * A method used for logging the exit of a method
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void exit(Object msg)
	{
		log4jLogger.debug("<<<< " + msg + " <<<< EXIT");
	}

	/**
	 * <p>
	 * A method used for logging the exit of a method
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 * 
	 * @param params
	 * 		the list of parameter, value combination of the method 
	 */
	public void exit(Object msg, Object[] params)
	{
		throw new UnsupportedOperationException("Yet to be implemented");
	}

	/**
	 * <p>
	 * This method will confirm whether or not the <tt>debug</tt> level
	 * is enabled for the root logger or not
	 * </p>
	 * 
	 * @return
	 * 		a true/false indicating the status
	 */
	public boolean isDebugEnabled()
	{
		return getLevel().equalsIgnoreCase(LOG_LEVEL_DEBUG);
	}
}