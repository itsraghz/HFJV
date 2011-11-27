package org.hfjv.framework;

/**
 * <p>
 * The Logger interface for the HFJV application
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public interface Logger
{
	/**
	 * <p>
	 * A method to emit the more detailed level of information
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void debug(Object msg);

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
	public void debug(Object msg, Throwable cause);

	/**
	 * <p>
	 * A method to emit the fine grained level of information, for a precise logging
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void info(Object msg);

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
	public void info(Object msg, Throwable cause);

	/**
	 * <p>
	 * A method to emit the higher level of information, like warnings
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void warn(Object msg);

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
	public void warn(Object msg, Throwable cause);

	/**
	 * <p>
	 * A method to emit the exceptional level of information, only at a critical level
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void fatal(Object msg);

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
	public void fatal(Object msg, Throwable cause);

	/**
	 * <p>
	 * A method to emit the JVM, container related errors which are NOT recoverable
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void error(Object msg);

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
	public void error(Object msg, Throwable cause);

	/**
	 * <p>
	 * This method gives the level of logger configured
	 * </p>
	 * 
	 * @return
	 * 		the logger level as a <tt>String</tt>
	 */
	public String getLevel();

	/**
	 * <p>
	 * This method gives the name of logger configured
	 * </p>
	 * 
	 * @return
	 * 		the name of the logger as a <tt>String</tt>
	 */
	public String getName();
	
	/**
	 * <p>
	 * A method used for logging the entry of a method
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void enter(Object msg);

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
	public void enter(Object msg, Object[] params);

	/**
	 * <p>
	 * A method used for logging the exit of a method
	 * </p>
	 * 
	 * @param msg
	 * 		the message to be emitted
	 */
	public void exit(Object msg);

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
	public void exit(Object msg, Object[] params);

	/**
	 * <p>
	 * This method will confirm whether or not the <tt>debug</tt> level
	 * is enabled for the root logger or not
	 * </p>
	 * 
	 * @return
	 * 		a true/false indicating the status
	 */
	public boolean isDebugEnabled();
}