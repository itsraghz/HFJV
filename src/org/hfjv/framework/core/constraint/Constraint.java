package org.hfjv.framework.core.constraint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.exclude.ExcludeCharsConstraint;
import org.hfjv.framework.core.constraint.mandatory.MandatoryConstraint;
import org.hfjv.framework.core.constraint.type.TypeConstraint;
import org.hfjv.framework.core.exception.ErrorDetails;
import org.hfjv.framework.core.exception.ValidatorException;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.core.validator.assembler.ValidatorAssembler;
import org.hfjv.framework.helper.ExceptionHelper;
import org.hfjv.framework.helper.TypeConvertor;
import org.hfjv.framework.util.GlobalUtil;
import org.hfjv.framework.util.CollectionUtil;
import org.hfjv.framework.util.DateUtil;
import org.hfjv.framework.util.MessageUtil;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 * A constraint is the heart of the HFJV engine.
 * </p>
 *
 * <p>
 * At the outset, a Constraint represents a kind of evaluation to be done on a field's value.
 * It stands as a base for all different kind of constraints like DigitConstraint, LengthConstraint,
 * ValueConstraint at a higher level.
 * </p>
 *
 * <p>
 * Each level has got some sub levels as well. For example, ValueConstraint has got two
 * sub levels like ValueRangeConstraint, ValueListConstraint etc., The ValueRangeConstraint is for
 * checking the value of a field against a range a range of values, for example 1-10
 * means from one to ten. The ValueListConstraint is for checking the value of the field
 * against the list of specified values say, <tt>1,2,5</tt> etc.,
 * </p>
 *
 * <p>
 * In case, if the validation fails in any of the constraints, a <tt>ValidatorException</tt> is
 * thrown back to the client with an appropriate error message.
 * </p>
 *
 * @author M Raghavan alias Saravanan M
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public  abstract class Constraint implements Serializable
{
	/**
	 * <p>
	 * An IDE (Eclipse) generated <tt>serialVersionUID</tt>
	 * </p>
	 */
	private static final long serialVersionUID = -7274372137353422799L;

	/**
	 * <p>
	 * A private, class level logger instance
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().getLogger(Constraint.class);

	/**
	 * <p>
	 *   A field to indicate the order of insertion/evaluation of constraints for a field. Based on
	 * this field's value, the constraints are sorted out, before actually starting with the evaluation.
	 * </p>
	 */
	protected int insertionOrder;

	/**
	 *  <p>
	 *   Holds the value to be checked against the actual value of the field.
	 * </p>
	 * <p>
	 *  All of them are treated as <tt>String</tt> in the configuration level, but internally
	 *  at the time of evaluation, they are narrowed down the appropriate data type based on the
	 *  <tt>type</tt> property of the corresponding <tt>Field</tt>.
	 *  </p>
	 */
	protected String valueToCheck = null;

	/**
	 * <p>
	 *  A class level <tt>ArrayList</tt> to hold the set of characters to be excluded at a
	 *  global level. The actual value is specified as a CSV (Comma Separated Values) in the
	 *  form of <tt>String</tt> in the configuration file, but during the startup, they are converted
	 *  into an <tt>ArrayList</tt> and populated into this property.
	 * </p>
	 */
	static ArrayList<String> globalExcludedCharsList = null;

	/**
	 * <p>
	 *  An instance of <tt>ErrorDetails</tt> to hold the error related information 
	 *  like <tt>errorCode</tt> and <tt>errorDescription</tt>
	 * </p>
	 */
	private ErrorDetails errorDetails = null;

	/**
	 * <p>
	 *  A private property to hold the <tt>name</tt> of the constraint. It is used in 
	 *  the custom error messages.
	 * </p>
	 */
	private String name = null;

	/**
	 * <p>
	 * An overloaded, four-argument constructor having the <tt>insertionOrder</tt>
	 * to be assigned
	 * </p>
	 *
	 * @param insertionOrder
	 *            the order of insertion value
	 *
	 * @param name
	 *		the name of the kind of constraint
	 *
	 * @param errorCodeKey
	 *		a key to the <tt>errorCode</tt>
	 *
	 * @param errorMsgKey
	 * 		a key to the <tt>errorMsg</tt>
	 */
	public Constraint(int insertionOrder, String name, String errorCodeKey,
                                 String errorMsgKey)
	{
		this.insertionOrder = insertionOrder;
		this.name = name;

		String errorCode = MessageUtil.getDefaultErrorMsg(errorCodeKey);
		String errorMsg = MessageUtil.getDefaultErrorMsg(errorMsgKey);

		ErrorDetails errorDetails = new ErrorDetails(errorCode, errorMsg);

		this.setErrorInfo(errorDetails);
	}

	/**
	 * <p>
	 *  A static method to determine whether the <tt>globalExcludedCharsList</tt> is
	 *  valid or not
	 * </p>
	 */
	private static boolean isGlobalExcludedCharsListValid()
	{
	      return CollectionUtil.isValidList(globalExcludedCharsList);
	}

	/**
	 * <p>
	 * This method returns the narrowed down value of the <tt>field</tt>
	 * </p>
	 * <p>
	 * As it stands as a generic method, it returns the value also in the generic form
	 * i.e., <tt>java.lang.Object</tt>. It has to be type-casted to the appropriate data
	 * type at the caller's end.
	 * </p>
	 *
	 * @param field
	 * 		the field whose value to be narrowed down
	 *
	 * @return
	 *		the narrowed down (specific) value of the field but in a generic
	 *		<tt>java.lang.Object</tt> form
	 */
	public Object getNarrowedValueToCheck(Field field)
	throws ValidatorException
	{
		return getNarrowedValue(field, this.valueToCheck);
	}

	/**
	 * <p>
	 * This method returns the narrowed down (specific) value of the passed
	 * input <tt>targetValue</tt>, depends on the <tt>type</tt>, which the other
	 * input <tt>field</tt> carries.
	 * </p>
	 *
	 * @param  field
	 * 		the field instance having the proper <tt>type</tt> for narrowing
	 *		down the value
	 *
	 * @param  targetValue
	 *		the actual value to be narrowed down
	 *
	 * @return
	 *		a generic <tt>java.lang.Object</tt> representing the narrowed down
	 *		value of the <tt>targetValue</tt>
	 *
	 * @throws   ValidatorException
	 *		in case of any exceptions during the operation
	 */
	private Object getNarrowedValue(Field field, String targetValue)
	throws ValidatorException
	{
		Object object = null;

        String type = field.getType();

        if(type.equalsIgnoreCase(GlobalUtil.DATATYPE_STRING) || 
        		type.equalsIgnoreCase(GlobalUtil.DATATYPE_HEXADECIMAL))
        {
            object = targetValue;
		}
		else if (GlobalUtil.isAllowedNumberType(field))
		{
			TypeConstraint.evaluateType(field, targetValue, this);

			object = getNarrowedNumberValue(field, targetValue);
		}
		else if (type.equalsIgnoreCase(GlobalUtil.DATATYPE_DATE))
		{
			/**
			 * yet to be implemented later,
			 * as of now returning the actual value
			 */
			//object = getFieldValue(field);
			Date dateValue = DateUtil.getDate(field.getDisplayName(), field.getValue());
			object = dateValue;
		}

		return object;
	}

	/**
	 * <p>
	 * This method gives the narrowed down (down-casted) <tt>number</tt> value
	 * specific to the <tt>type</tt> of the <tt>field</tt> instance
	 * </p>
	 *
	 * @param field
	 *		the field instance having the proper <tt>type</tt> for
	 * 		narrowing down the value
	 *
	 * @param targetValue
	 *		the actual value to be narrowed down
	 *
	 * @return
	 *		a generic <tt>java.lang.Object</tt> representing the narrowed
	 *		down numeric value of the <tt>targetValue</tt>
	 * @throws ValidatorException
	 *		in case of any exceptions during the operation
	 */
	public Number getNarrowedNumberValue(Field field, String targetValue)
	throws ValidatorException
	{
		Number numberValue = TypeConvertor.getNumber(targetValue);

		return numberValue;
	}

	public String getValueToCheck()
	{
		return valueToCheck;
	}

	public void setValueToCheck(String valueToCheck)
	{
		this.valueToCheck = valueToCheck;
	}

	/**
	 * <p>
	 * This method gives the narrowed down (specific) value of the
	 * <tt>field</tt> instance
	 * </p>
	 *
	 * @param field
	 *		the field whose value to be narrowed down and sent back
	 *
	 * @return
	 *		the narrowed down value (specific) of the field in the form
	 *		of <tt>java.lang.Object</tt>
	 *
	 * @throws ValidatorException
	 *		in case of any exceptions during the operation
	 */
	public Object getNarrowedFieldValue(Field field)
	throws ValidatorException
	{
		if(null==field || StringUtil.isInvalidString(field.getValue()))
		{
			return null;
		}

		return getNarrowedValue(field, field.getValue());
	}

	/**
	 * <p>
	 * This method returns the value of the <tt>field</tt> being passed. It
	 * returns <tt>null</tt> if the <tt>field</tt> is <tt>null</tt>.
	 * </p>
	 *
	 * @param field
	 * 		the field whose value is to be returned
	 *
	 * @return
	 * 		the value of the field in <tt>java.lang.String</tt> if valid,
	 * 		<tt>null</tt> if the field itself is found to be <tt>null</tt>
	 */
	public static String getFieldValue(Field field)
	{
		return ((null!=field) ? field.getValue() : null);
	}

	public static ArrayList<String> getGlobalExcludedCharsList()
	{
		return globalExcludedCharsList;
	}

	public static void setGlobalExcludedCharsList(ArrayList<String> excludedCharsList)
	{
		Constraint.globalExcludedCharsList = excludedCharsList;
	}


	/**
	 * <p>
	 * This is the actual method that validates the field and its value.
	 * Being the method of the parent/base class, it does nothing for the time
	 * being but printing the actual value to be evaluated.
	 * </p>
	 *
	 * @param field
	 * 		the field instance whose value to be evaluated
	 *
	 * @throws ValidatorException
	 * 		any exceptions during validation
	 */
	public void evaluate(Field field) throws ValidatorException
	{
		/**
		 * Time being doing nothing.
		 *
		 * But later something can be added
		 * as all of the child classes now delegate at first to
		 * super.evaluate()
	 	 */

		logger.info("Constraint, evaluate() - fieldValue=["
			+ getFieldValue(field) + "], valueToCheck["+ valueToCheck+"]");
	}
	
	/**
	 * <p>
	 * A method to validate (self evaluation) of the <tt>constraint</tt> instance
	 * wherein the subclasses can use this method to do a self-check on the <tt>valueToCheck</tt>
	 * based on its own type.
	 * </p>
	 * <p>
	 * It will be useful to prevent any unauthorized, not-allowed value being configured
	 * for a <tt>Constraint</tt>
	 * </p>
	 * 
	 * @throws ValidatorException 
	 * 						any exceptions during the validation
	 */
	public abstract void selfEvaluate() throws ValidatorException;
	
	/**
	 * <p>
	 * This method evaluates the constraint's value and throws a 
	 * <tt>ValidatorException</tt> if the value is <tt>invalid</tt> (null or empty)
	 * </p>
	 * 
	 * @throws ValidatorException
	 * 					if the value is found invalid, a validatorException is thrown
	 */
	public void evaluateConstraintForNonNullValue()
	throws ValidatorException
	{
		if(StringUtil.isInvalidString(this.valueToCheck))
		{
			throw new ValidatorException("value of the constraint '"+this.getName()
															+"' should not be left null or empty");
		}
	}

	/**
	 * <p>
	 * This method is invoked prior to the <tt>evaluate</tt> method
	 * as a pre-requisite. It is more like a filter being applied for
	 * the evaluate method.
	 * </p>
	 *
	 * <p>
	 * It is useful when any evaluation to be done globally. Example,
	 * globalExcludeChars (like '?', '#' etc.,)
	 * </p>
	 *
	 * @param field
	 * 		the field instance whose value to be evaluated
	 *
	 * @throws ValidatorException
	 * 		any exceptions during validation
	 */
	public static void preEvaluateField(Field field)
	throws ValidatorException
	{
		final String THIS_METHOD_NAME = "preEvaluateField() - ";

		logger.enter(THIS_METHOD_NAME);

		if(null==field)
		{
			throw new ValidatorException("field cannot be null");
		}

		logger.info(THIS_METHOD_NAME + " investigating on the global "
				+"excluded characters if any..");

		boolean isGlobalExcludedCharsVisited = false;

		if(isGlobalExcludedCharsListValid())
		{
			isGlobalExcludedCharsVisited = true;

			actOnExcludedChars(field, globalExcludedCharsList);
		}

		if(!isGlobalExcludedCharsVisited)
		{
			logger.info(THIS_METHOD_NAME + " No global excludeChars are "
				+ " configured to act on the input values..");
		}

		logger.exit(THIS_METHOD_NAME);

	}

	/**
	 * <p>
	 * A method that evaluates the value of the <tt>field</tt> by ensuring that
	 * it does NOT have any of the <tt>characters to be excluded</tt>
	 * </p>
	 *
	 * @param field
	 * 		the field instance whose value to be evaluated
	 *
	 * @param excludedCharsList
	 * 		the list of characters
	 *
	 * @throws ValidatorException
	 * 		if the value of the <tt>field</tt> contains any of the
	 * 		characters present in the <tt>excludedCharsList</tt>
	 */
	protected static void actOnExcludedChars(Field field,
					ArrayList<String> excludedCharList)
	throws ValidatorException
	{
		actOnExcludedChars(field, new ExcludeCharsConstraint(), excludedCharList);
	}


	/**
	 * <p>
	 * A method that evaluates the value of the <tt>field</tt> by ensuring that
	 * it does NOT have any of the <tt>characters to be excluded</tt>
	 * </p>
	 *
	 * @param field
	 * 		the field instance whose value to be evaluated
	 *
	 * @param excludedCharsConstraint
	 * 		an instance of <tt>ExcludeCharsConstraint</tt> to have the
	 * 		error code related information from the appropriate instance
	 *
	 * @param excludedCharList
	 * 		the list of characters which should be excluded in the input
	 *
	 * @throws ValidatorException
	 * 		if the value of the <tt>field</tt> contains any of the
	 * 		characters present in the <tt>excludedCharsList</tt>
	 */
	protected static void actOnExcludedChars(Field field,
							ExcludeCharsConstraint excludedCharsConstraint,
							ArrayList<String> excludedCharList)
	throws ValidatorException
	{
		Character excludedCharacter;

		String value = getFieldValue(field);

		/**
		 * Ideally do nothing! No need to proceed further
		 * with the constraint! :)
		 */
		if(StringUtil.isInvalidString(value))
		{
			return;
		}

		for(String excludedChar : excludedCharList)
		{
			excludedCharacter = excludedChar.charAt(0);

			/**
			 * Even if any of the chars present, throw an error!
			 */
			boolean isExcludedCharsSameAsValue =
																value.equalsIgnoreCase(excludedChar);

			boolean isValueFullOfExcludedChar =
				StringUtil.isValueFilledWithSameChar(value,excludedCharacter);

			int indexOfCharToBeExcluded = value.indexOf(excludedCharacter);

			boolean isExcludedCharPresentAnywhere = (indexOfCharToBeExcluded!=-1);

			if(isExcludedCharsSameAsValue || isValueFullOfExcludedChar
				|| isExcludedCharPresentAnywhere)
			{
				String errorMsg = "The specified value '"
					+ value + "' for the field '" + field.getDisplayName() + "' is not allowed";

				throw ExceptionHelper.getValidatorException(field,
								excludedCharsConstraint, errorMsg);
			}
		}
	}

	/**
	 * <p>
	 * This method evaluates the dependent fields of a field, if any
	 *</p>
	 */
	public static void evaluateDependentFields(String moduleName, Field field)
	throws ValidatorException
	{
		final String THIS_METHOD_NAME = "evaluateDependentFields() - ";

		logger.enter(THIS_METHOD_NAME);

		if(null==field)
		{
			throw new ValidatorException("Field cannot be null!");
		}

		logger.debug(THIS_METHOD_NAME + "evaluating the field -> "
						+ field.getName());

		LinkedHashMap<String,String> dependentFieldValueMap =
															field.getDependentFieldValueMap();

		if(!CollectionUtil.isValidMap(dependentFieldValueMap))
		{
			return;
		}

		String actualValueOfDependentField = null;

		String dependentValue = null;

		Set<String> keySet = dependentFieldValueMap.keySet();
		
		Field dependentField = null;

		HashMap<String,Field> fieldNameMap = ValidatorAssembler.getFieldNameMap();

		/** for handling the multiple dependencies */
		boolean tempValueMatchesWithAllDependentFields = false;
		boolean tempValueMatchesFlagUpdated = false;

		for(String key : keySet)
		{
			if(StringUtil.isValidString(key))
			{
				dependentField = fieldNameMap.get(key);

				logger.info(THIS_METHOD_NAME + "evaluate in Constraint : "
					+ "dependentField -> ["+dependentField.getName()+"]");

				actualValueOfDependentField = dependentField.getValue();

				logger.debug(THIS_METHOD_NAME + "evaluate in Constraint : "
					+ "actualValueOfDependentField -> "
					+ actualValueOfDependentField);

				dependentValue = dependentFieldValueMap.get(key);

				logger.debug(THIS_METHOD_NAME + "evaluate in Constraint : "
					+ "dependentValue -> "
					+ dependentValue);

				if((StringUtil.isValidString(actualValueOfDependentField)) &&
					isValueMatching(dependentField, dependentValue))
				{
					logger.info(THIS_METHOD_NAME + "value matches");

					tempValueMatchesWithAllDependentFields = true;
					tempValueMatchesFlagUpdated = true;
				}
				else
				{
					//field.setVerifiable(false);

					/** Handling multiple dependencies */
					/*
					 * If any of the dependentField's value does NOT match,
					 * setting the boolean flag to false and 'breaking' from
					 * from the loop iteration.
					 *
					 * The reason for resetting the flag to 'false' is that
					 * it might have got set to 'true' due to the previous
					 * iterations that has got the matching values of
					 * dependentFields
					 */
					logger.info(THIS_METHOD_NAME + "value does NOT match."
												+ " Skipping the rest of dependentFields if any");
					
					tempValueMatchesWithAllDependentFields = false;
					break;
				}
			}
		}
		if(tempValueMatchesWithAllDependentFields)
		{
			if(!field.isDependentFieldEvaluated())
			{
				logger.info(THIS_METHOD_NAME + "all the dependnetFields values"
												+ " are matching. Making the field verifiable!");

				field.setDependentFieldEvaluated(true);
				addMandatoryConstraint(moduleName, field);
				field.setVerifiable(true);
			}
		}

		/**
		 * Conditionally the verifiable field of a field is set to false
		 * if the actualValue of the dependentField does NOT match with the
		 * specified valueToCheck.
		 */
		else
		{
			String category = (tempValueMatchesFlagUpdated ? "Some" : "None");
			
			logger.info(THIS_METHOD_NAME + category + " of the dependentFields "
							+ "value are NOT matching. Making the field non-verifiable!");

			field.setVerifiable(false);
		}

		/* No more needed! Just for making it eligible for GC! :) */
		fieldNameMap = null;

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 *
	 *</p>
	 */
	public static boolean isValueMatching(Field field, String targetValue)
	throws ValidatorException
	{
		final String THIS_METHOD_NAME = "isValueMatching() - ";

		logger.enter(THIS_METHOD_NAME);

		String srcValue = field.getValue();

		logger.debug(THIS_METHOD_NAME + " srcValue="+srcValue);
		logger.debug(THIS_METHOD_NAME + " targetValue="+targetValue);

		boolean isSrcStringValid = StringUtil.isValidString(srcValue);

		boolean isTargetStringValid = StringUtil.isValidString(targetValue);

		if(!isSrcStringValid || !isTargetStringValid)
		{
			return false;
		}

		logger.debug(THIS_METHOD_NAME + "parsing the target value for matching");

		boolean isMatching = true;

		ArrayList<String> listOfTargetValues = new ArrayList<String>();

		int indexOfRangeSeparator = targetValue.indexOf(GlobalUtil.MODULE_FIELD_SEPARATOR);

		int indexOfCommaSeparator = targetValue.indexOf(",");

		if(indexOfRangeSeparator != -1)
		{
			logger.debug(THIS_METHOD_NAME + "target value is of a Range");
			listOfTargetValues = GlobalUtil.getRangeValuesInList(targetValue);
			return true;
		}
		else if (indexOfCommaSeparator != -1)
		{
			logger.debug(THIS_METHOD_NAME + "target value is of a list");
			listOfTargetValues = GlobalUtil.getListOfTokens(targetValue);
		}
		else
		{
			logger.debug(THIS_METHOD_NAME + "target value is atomic");
			listOfTargetValues.add(targetValue);
		}

		logger.debug(THIS_METHOD_NAME + "target value List -> "+listOfTargetValues);

		isMatching = listOfTargetValues.contains(srcValue);

		logger.debug(THIS_METHOD_NAME + "isMatching ? "+isMatching);

		logger.exit(THIS_METHOD_NAME);

		return isMatching;
	}

	/**
	 *  <p>
	 *  This method will add a <tt>MandatoryConstraint</tt> to the existing
	 *  list of constraints for a <tt>Field</tt> passed as an argument
	 * </p>
	 * @param moduleName
	 * 				the module name to fetch the constraint list from the registry
	 * 
	 * @param field
	 * 				the field object whose constraint list needs to be inserted with a
	 * 				new <tt>MandatoryConstraint</tt>
	 *
	 */
	private static void addMandatoryConstraint(String moduleName, Field field)
	{
		final String THIS_METHOD_NAME = "addMandatoryConstraint() - ";

		logger.enter(THIS_METHOD_NAME);

		MandatoryConstraint mandatoryConstraintObj = new MandatoryConstraint();

		mandatoryConstraintObj.setValueToCheck("Yes");

		logger.debug(THIS_METHOD_NAME + " field.getName() -> "+field.getName());
		logger.debug(THIS_METHOD_NAME + " moduleName -> "+moduleName);

		/* ============================================= */
		LinkedHashMap<Field,ArrayList<Constraint>> moduleFieldConstraintListMap =
			ValidatorAssembler.getModuleFieldsConstraintListMap().get(moduleName);

		ArrayList<Constraint> listOfExistingConstraints = moduleFieldConstraintListMap.get(field);

		/* ============================================= */

		logger.debug(THIS_METHOD_NAME + " listOfExistingConstraints -> "
																				+ listOfExistingConstraints);

		ArrayList<Constraint> newListOfConstraints = new ArrayList<Constraint>();

		newListOfConstraints.add(0, mandatoryConstraintObj);

		/*
		 * No need, as the type constraint got already added in the
		 * initFieldConstraintList() method of ValidatorAssembler class
	   	 */
		//listOfExistingConstraints.add(1, new TypeConstraint());
		newListOfConstraints.addAll(listOfExistingConstraints);

		//listOfExistingConstraints.add(0, mandatoryConstraintObj);
		listOfExistingConstraints = null;

		moduleFieldConstraintListMap.put(field, newListOfConstraints);

		ValidatorAssembler.getModuleFieldsConstraintListMap().
			put(moduleName, moduleFieldConstraintListMap);

		logger.debug(THIS_METHOD_NAME + "newListOfConstraints -> " +
			(ValidatorAssembler.getModuleFieldsConstraintListMap().get(moduleName)));

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 * This  method will update the deferredEvaluation flag of a field instance
	 * </p>
	 */
	public static void updateDeferredEvaluationField(Field field)
	{
		final String THIS_METHOD_NAME = "updateDeferredEvaluationField() - ";

		logger.enter(THIS_METHOD_NAME);

		/** If the field is null, do nothing! */
		if(null==field)
		{
			return;
		}

		boolean isDeferredFieldUpdated = false;

		/*
		 * Generally, the deferredEvaluation flag gets set only when the
		 * mandatoryConstraint=no has been set against a field.
		 *
		 * Check if the field has got a 'valid' value in it.
		 * If so, its time to update the deferredEvaluation flag to false,
		 * so that the rest of the constraints would be evaluated for the field's value.
		 *
		 * This field is re-evaluated in the DefaultValidator's validate() method,
		 * where this method is invoked from.
	 	 *
		 * Do this only when the field's deferredEvaluation is true!
		 *
		 * Modification
		 * ------------
		 * 	don't evaluate for a 'valid' value as at times the field
		 *	may not contain a valid value but still it needs to go
		 * 	through the mandatoryConstraint.
		 *
		 *	Hence, condition has been adjusted to check with the
		 *	dependentFieldEvaluated flag of the Field.
		 */
		if(field.isDeferredEvaluation()
				&&
			(StringUtil.isValidString(field.getValue()) ||
				field.isDependentFieldEvaluated()))
		{
			field.setDeferredEvaluation(false);
			field.setDeferredEvaluationFlagUpdated(false);
			isDeferredFieldUpdated = true;
		}

		/*
		 * If there is no check on 'deferredEvaluationFlagUpdated', it would
	 	 * otherwise update all the normal fields (which does NOT have
		 * mandatoryConstraint=no) as well.
		 *
		 * Again, do this only when the deferredEvaluationFlagUpdated is true
		 * AND when the field has got an 'invalid' value. --> only when
		 * the purpose of configuring 'mandatoryConstraint=no' for the field
		 * is saved :)
	 	 *
		 * Generally, if the field has got a valid value, why should you
		 * set its deferredEvaluation to true? -- Does not make sense!
		 */
		else if (field.isDeferredEvaluationFlagUpdated() &&
				StringUtil.isInvalidString(field.getValue()))
		{
			field.setDeferredEvaluation(true);
		}

		logger.info(THIS_METHOD_NAME + "deferredEvaluation of {"
				+ field.getName() +"} has "
				+ (isDeferredFieldUpdated ? "" : "NOT")
				+ " been updated "
				+ (isDeferredFieldUpdated ? " to 'false'" : ""));

		logger.exit(THIS_METHOD_NAME);
	}
	
	@Override
	public String toString()
	{
		return " Name : "+this.getName() 
				+ ", Value : " + this.getValueToCheck()
				+ " [hashCode] :: "+this.hashCode();
	}

	/* Getter and Setter for errorDetails */
	public ErrorDetails getErrorInfo()
	{
		return errorDetails;
	}

	public void setErrorInfo(ErrorDetails errorDetails)
	{
		this.errorDetails = errorDetails;
	}

	/* Getter and Setter for insertionOrder */
	public int getInsertionOrder()
	{
		return insertionOrder;
	}

	public void setInsertionOrder(int insertionOrder)
	{
		this.insertionOrder =  insertionOrder;
	}

	/* Getter and Setter for name */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}