package org.hfjv.framework.core.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.constraint.exclude.ExcludeCharsConstraint;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.core.validator.assembler.ValidatorAssembler;
import org.hfjv.framework.util.ConstraintComparator;
import org.hfjv.framework.util.CollectionUtil;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 * The class which will be executing the validation for all the fields of a 
 * specific module being requested for
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValidateExecutor
{
	/**
 	 * <p>
	 * A private class lever logger instance of this class
	 * </p>
 	 */
	private static Logger logger = LoggerFactory.getInstance().
						getLogger(ValidateExecutor.class);

	/**
	 * <p>
	 * This method validates all the fields of the given module
	 * </p>
	 * 
	 * @param moduleName
	 * 					the module whose fields to be validated
	 * 
	 * @throws ValidatorException
	 * 					any exceptions thrown during the validation
	 */
	@SuppressWarnings("unchecked")
	public static void validate(String moduleName)
	throws ValidatorException
	{
		final String THIS_METHOD_NAME = "validate() - ";

		logger.enter(THIS_METHOD_NAME);

		if(StringUtil.isInvalidString(moduleName))
		{
			throw new ValidatorException("Module name cannot be null!");
		}

		/** Filter and get the fieldList (with Constraint) for the required module */
		LinkedHashMap<Field, ArrayList<Constraint>> fieldConstraintListMap =
				ValidatorAssembler.getModuleFieldsConstraintListMap().get(moduleName);

		if(null==fieldConstraintListMap || fieldConstraintListMap.size() <= 0)
		{
			logger.info(THIS_METHOD_NAME + "fieldConstraintListMap is null or empty!");
			return;
		}

		logger.debug(StringUtil.getRepeatedChars("/#", 100));
		logger.debug("fieldConstraintListMap ----> "+fieldConstraintListMap);
		logger.debug(StringUtil.getRepeatedChars("/#", 100));


		Set<Field> keySet = fieldConstraintListMap.keySet();

		ArrayList<Constraint> constraintList = null;

		for(Field field : keySet)
		{
			logger.debug(THIS_METHOD_NAME + StringUtil.getRepeatedChars("%", 25));
			logger.debug(THIS_METHOD_NAME + " evaluating field - "+field.getName());
			logger.debug(THIS_METHOD_NAME + StringUtil.getRepeatedChars("%", 25));

			logger.debug(THIS_METHOD_NAME + " evaluating dependentFields");

			/**
		 	 * If the earlier execution (if any) of a field had thrown a
		 	 * ValidatorException and due to which the field's state would not
	 		 * have got restored.
			 *
			 * Hence, restore at the initial stage. Remember, the field's state
			 * would have got preserved in the ValidatorAssembler at the
			 * initial stage as it would NOT accidentally reset the fields to
			 * false if this is the very very first attempt of execution!
			 *
			 * If the previous execution was successful, it would have got
	 		 * restored after the evaluation of all constraints for the field. And
			 * it would be a duplicate action, nevertheless no issues! :)
			 */

			field.restoreState();

			logger.info(THIS_METHOD_NAME + " [**] Initial field state restoration");

			logger.info(THIS_METHOD_NAME + " [**] restoredState : "	
																				+field.toPreservedStateString());

			/* ================= State Preserving - START  ================== */

			/*
			 * Preserving the original state before evaluating
			 * Later, after evaluation these fields would be reset
			 */
			field.preserveState();

			logger.info(THIS_METHOD_NAME + " [**] preservation before evaluation");

			logger.info(THIS_METHOD_NAME + " [**] preservedState : "
												+field.toPreservedStateString());

			/* ================= State Preserving - END  ================== */

			Constraint.evaluateDependentFields(moduleName, field);

			logger.debug(THIS_METHOD_NAME + " Field ("+field.getName()
																+	 ") isVerifiable ? "+field.isVerifiable());

			/*
			 * If the field does NOT need to be verified, break the
			 * inner loop for the further constraint lists for the same field
			 * and continue with the next field in the outer for loop
			 *
			 *    --- for when this code was in the inner loop
		  	 *
			 * If this field is NOT needed to be verified, skip this
			 * and proceed with the next field in the list!
			 *
			 *    --- for when it is in outer for loop (at present)
			 *
			 *  New constraint added for excludedCharsSet.
			 *  Look at the next if condition below for detailed explanation
			 */
			if(!field.isVerifiable() && !field.isExcludedCharsSet())
			{
				continue;
			}

			logger.debug(THIS_METHOD_NAME + "updating deferredEvaluation");

			Constraint.updateDeferredEvaluationField(field);

			logger.debug(THIS_METHOD_NAME + "isDeferredEvlaluation of field : "
															+ field.isDeferredEvaluation());

			/**
			 * After the 'deferredEvaluation' has been updated, we can
			 * now take a call on it. If still it is true, we can safely
			 * skip this field and move on to the next field.
			 *
			 * Take a conditional call on the field's specific excludedCharsSet
			 * flag. If an optional field has got a constraint on excludedCharsSet,
			 * it should be processed further for evaluation (as it might
			 * mostly have only one ExcludedChars constraint).
			 */
			logger.debug(THIS_METHOD_NAME + " isExcludedCharsSet of field : "
																+ field.isExcludedCharsSet());

			if(field.isDeferredEvaluation() && !field.isExcludedCharsSet())
			{
				continue;
			}

			/**
			 * A special case needs an attention.
			 *
			 * If the field is an optional field but has an excludedChars flag
			 * set, due to the changes done on the earlier two if statements let
			 * the field go through the entire set of constraints configured in the
		 	 * properties file, which is not needed as it gives an inappropriate
		 	 * and rather an invalid error message.
			 *
			 *  Example : The field being (incomingPassword) an int type field,
			 *  it says "The value 'null' passed is not a valid number",
			 *  because it has got the constraints (ExcludedCharsConstraint, TypeConstraint,
			 *  MaxRangeConstraint etc.,)
			 *
			 *  The actual value was null (it was not passed any value).
			 *
			 *  1. The ExcludeChars constraint does NOT do anything if the value is
			 *     null.
			 *  2. Next comes the TypeConstraint in the order and it shows the
			 *     aforesaid error, which is invalid!
			 *
			 *  Hence, the below constraint and modification.
			 *
			 *  Note:
			 *  =====
			 *  Do this only if the field's value is empty or null
			 *  If the field contains some valid value, ideally al
			 *  the constraints should be opened!
			 */

			if(!field.isVerifiable() && field.isExcludedCharsSet() &&
					StringUtil.isInvalidString(field.getValue()))
			{
				logger.info(THIS_METHOD_NAME + " special case - field is NOT"
					+ " verifiable BUT its excludedCharsSet. Assigning a new"
					+ " constraintList (with only ExcludeChars) for this iteration");

				constraintList = new ArrayList<Constraint>();
				constraintList.add(new ExcludeCharsConstraint());
			}
			else
			{
				logger.debug(THIS_METHOD_NAME + "adjusting constraints for the field");

				constraintList = fieldConstraintListMap.get(field);

				logger.info(THIS_METHOD_NAME + " |*| constraintList at first ---> "
							+ constraintList);

				/* for the safety sake */
				if(!CollectionUtil.isValidList(constraintList))
				{
					continue;
				}

				Collections.sort(constraintList, ConstraintComparator.getInstance());

				logger.info(THIS_METHOD_NAME + " |*| constraintList after sorting --> "
						+ constraintList);
			}

			logger.info(THIS_METHOD_NAME + " pre-evaluating field ");

			Constraint.preEvaluateField(field);

			logger.info(THIS_METHOD_NAME + " actual evaluation starts ");

			for(Constraint constraint : constraintList)
			{
				logger.debug(THIS_METHOD_NAME + " constraintlist type - " +
						constraint.getClass().getSimpleName());
				
				logger.debug(THIS_METHOD_NAME + " constraintlist valueToCheck - " +
						constraint.getValueToCheck());

				logger.debug(THIS_METHOD_NAME + " <--> Evaluating starts <--> ");

				constraint.evaluate(field);

				logger.debug(THIS_METHOD_NAME + " <--> Evaluating finished <--> ");
			}

			/* ============= State Restoring - START ============ */

			/**
			 * Restoring the preserved states of a field
			 */
			field.restoreState();

			logger.info(THIS_METHOD_NAME + " {##} restoration after evaluation");

			logger.info(THIS_METHOD_NAME + " {##} restoredState : "+
																		field.toPreservedStateString());

			/* ============= State Restoring - END ============ */

		}

		logger.exit(THIS_METHOD_NAME);
	}
	
	/**
	 * <p>
	 * This method resets all the fields after one round of execution.
	 * </p>
	 * <p>
	 * At times it gets called just before a ValidatorException is thrown
	 * back to the caller. It is just to ensure that the values of the fields
	 * are NOT cached as it may affect the further executions.
	 * </p>
	 * 
	 * @param moduleName
	 *							the module whose all fields values are to be reset
	 */
	public static void resetAllFields(String moduleName)
	{
		final String THIS_METHOD_NAME = "resetAllFields() - ";

		logger.enter(THIS_METHOD_NAME);

		/** Filter and get the fieldList (with Constraint) for the required module */
		LinkedHashMap<Field, ArrayList<Constraint>> fieldConstraintListMap =
				ValidatorAssembler.getModuleFieldsConstraintListMap().get(moduleName);

		Set<Field> keySet = fieldConstraintListMap.keySet();

		for(Field field : keySet)
		{
			/** Reset the field to its original state */
			field.reset();

			logger.debug(THIS_METHOD_NAME + " Value of field '"+field.getName()
							+"' is reset!");
		}

		logger.info(THIS_METHOD_NAME + " Value of all the fields of the module '"
						+ moduleName +"' is reset!");

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 *
	 * @param fieldConstraintListMap
	 * @param moduleName
	 * @throws ValidatorException
         */
	public void validate(LinkedHashMap<Field, ArrayList<Constraint>> 
												fieldConstraintListMap, String moduleName)
	throws ValidatorException
	{
		throw new ValidatorException("Not yet implemented!");
	}
}