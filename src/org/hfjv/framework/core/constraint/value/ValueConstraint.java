package org.hfjv.framework.core.constraint.value;

import java.io.Serializable;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;

/**
 * <p>
 * A super class for all the value based constraints
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValueConstraint extends Constraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = 7983574592311302708L;
	
	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
																		getLogger(ValueConstraint.class);

	/**
	 * <p>
	 *  An overloaded, four argument constructor
	 * </p>
	 * 
	 * @param insertionOrder
	 * 				the insertion order of the constraint
	 * @param name
	 * 				the name of the constraint
	 * @param errorCodeKey
	 * 				the key to error code configured
	 * @param errorMsgKey
	 * 				the key to error message configured
	 */
	public ValueConstraint(int insertionOrder, String name,
				String errorCodeKey,  String errorMsgKey)
	{
		super(insertionOrder, name, errorCodeKey, errorMsgKey);
	}

	@Override
	public void evaluate(Field field)
	throws ValidatorException
	{
		super.evaluate(field);
	}

	@Override
	public void selfEvaluate() throws ValidatorException
	{
		final String THIS_METHOD_NAME = "selfEvaluate() - ";
		
		logger.enter(THIS_METHOD_NAME);
		
		//Method Body
		
		logger.exit(THIS_METHOD_NAME);
	}
}