package org.hfjv.framework;

import org.hfjv.framework.impl.HFJVLogger;

/**
 * <p>
 * A factory class for the Logger to provide a single and default implementation
 * of the Logger interface whenever needed by clients.
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class LoggerFactory
{

	public static LoggerFactory _instance = null;

	private LoggerFactory() {}

	public static LoggerFactory getInstance()
	{
		if(null==_instance)
		{
			_instance = new LoggerFactory();
		}

                return _instance;
	}

	public Logger getLogger(String name)
	{
		return new HFJVLogger(name);
	}

	@SuppressWarnings("rawtypes")
	public Logger getLogger(Class clazz)
	{
		return new HFJVLogger(clazz.getName());
	}

}