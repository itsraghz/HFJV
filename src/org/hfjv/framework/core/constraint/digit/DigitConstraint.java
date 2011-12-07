package org.hfjv.framework.core.constraint.digit;

import java.io.Serializable;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.constraint.type.TypeConstraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.util.GlobalUtil;

/**
 * <p>
 *  A super class for all the digit based constraints
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class DigitConstraint extends Constraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = -9170631941627316558L;
	
	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
																		getLogger(DigitConstraint.class);	
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
	public DigitConstraint(int insertionOrder, String name,
			String errorCodeKey, String errorMsgKey)
	{
		super(insertionOrder, name, errorCodeKey, errorMsgKey);
	}
	
	@Override
	public void selfEvaluate() throws ValidatorException
	{
		final String THIS_METHOD_NAME = "selfEvaluate() - ";
		
		logger.enter(THIS_METHOD_NAME);
		
		/**
		 * As part of self evaluation, the DigitConstraint needs to
		 * ensure that the value assigned to it should be a valid number.
		 * 
		 * As already it is taken care by  TypeConstraint's evaluateType()
		 * method, reusing the same. 
		 * 
		 * However, we need a Field instance to be passed to evaluateType()
		 * method. For which, a new field instance is created with the value
		 * of this constraint (name and value). As that is what going to be
		 * returned to the caller for the ValidatorException.
		 */
		Field fieldForEvlauation = new Field(this.getName());
		fieldForEvlauation.setDisplayName(this.getName());
		fieldForEvlauation.setValue(this.getValueToCheck());
		
		logger.info(THIS_METHOD_NAME + "Before passing on to evaluateType(), this -> "
												+ this.toString());
		
		fieldForEvlauation.setType(GlobalUtil.DATATYPE_NUMBER_BIGINT);
		
		TypeConstraint.evaluateType(fieldForEvlauation, this.valueToCheck,this);
		
		logger.exit(THIS_METHOD_NAME);
	}
}