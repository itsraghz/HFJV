package org.hfjv.framework.core.validator.factory;

import org.hfjv.framework.core.validator.Validator;
import org.hfjv.framework.core.validator.impl.DefaultValidator;

/**
 * <p>
 * A factory class to provide an implementation class for the <tt>Validator</tt>
 * of HFJV
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValidatorFactory
{
	/**
	 * <p>
	 * A private class level instance of the implementation class of the <tt>Validator</tt>
	 * </p>
	 */
	public static Validator _instance = null;

	/**
	 * A private no argument constructor for singleton purpose
	 */
	private ValidatorFactory () {}

	/**
	 * <p>
	 * A class level method to provide the single copy of an implementation class 
	 * of the <tt>Validator</tt>
	 * </p>
	 * @return
	 */
	public static Validator getInstance()
	{
		if(null==_instance)
		{
			_instance = new DefaultValidator();
		}

		return _instance;
	}
}