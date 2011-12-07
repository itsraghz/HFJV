package org.hfjv.framework.core.validator.assembler;

import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_BASEKEY_FIELD;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_BASEKEY_WITH_SEPARATOR;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_CONSTRAINT_EXCLUDE_CHARS;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_DEPENDENT;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_DEPENDENT_VALUE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_ERRORCODE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_ERRORDESC;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_GLOBAL;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_FIELD_TYPE;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_KEY_SEPARATOR;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.HFJV_MODULE_WITH_BASEKEY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.core.constraint.exclude.ExcludeCharsConstraint;
import org.hfjv.framework.core.constraint.mandatory.MandatoryConstraint;
import org.hfjv.framework.core.constraint.type.TypeConstraint;
import org.hfjv.framework.core.exception.ErrorDetails;
import org.hfjv.framework.core.field.Field;
import org.hfjv.framework.util.GlobalUtil;
import org.hfjv.framework.util.CollectionUtil;
import org.hfjv.framework.util.PropertyUtil;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 * This is a core helper class of HFJV engine as it assembles all the nuts and bolts
 * of the engine by reading the properties file
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValidatorAssembler
{
	/**
	 * <p>
	 * A private class level logger instance
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
																getLogger(ValidatorAssembler.class);

	/**
	 * <p>
	 * A class level private instance of this class.
	 * </p>
	 */
	private static ValidatorAssembler _instance = null;

	/**
	 * <p>
	 * A private class level instance of <tt>PropertyUtil</tt>
	 * </p>
	 */
	private static final PropertyUtil _propertyUtil =
																			PropertyUtil.getInstance();

	/**
	 * <p>
	 * A private class level <tt>HashMap</tt> to hold the field and its
	 * name. 
	 * </p>
	 * <p>A field name is typically represented along with the module for 
	 * distinguishing, as the same field may need to get validated differently for
	 * different modules
	 * </p>
	 */
	private static HashMap<String,Field> fieldNameMap =
																	new HashMap<String,Field>();

	/**
	 * <p>
	 * A class level <tt>ArrayList</tt> to hold the list of modules configured
	 * </p>
	 */
	public static ArrayList<String> listOfModules =
																			new ArrayList<String>();

	/**
	 * <p>
	 * A class level <tt>HashMap</tt> to hold the module and the list of fields for
	 * each module
	 * </p>
	 */
	public static LinkedHashMap<String, ArrayList<String>> moduleFieldsMap =
											new LinkedHashMap<String, ArrayList<String>>();

	/**
	 * <p>
	 * A class level <tt>HashMap</tt> to hold the field and list of constraints for
	 * each field
	 * </p>
	 */
	public static LinkedHashMap<Field, ArrayList<Constraint>> fieldConstraintMap =
											new LinkedHashMap<Field, ArrayList<Constraint>>();

	/**
	 * <p>
	 * A class level <tt>LinkedHashMap</tt> to hold the module and the list of
	 * fields with constraints
	 * </p>
	 */
	public static LinkedHashMap<String, LinkedHashMap<Field,ArrayList<Constraint>>>
		moduleFieldsConstraintListMap = new
			LinkedHashMap<String, LinkedHashMap<Field,ArrayList<Constraint>>>();

	/**
	 * <p>
	 * A class level <tt>LinkedHashMap</tt> to hold the global constraints configured
	 * </p>
	 */
	public static LinkedHashMap<String, String> globalConstraintMap =
													new LinkedHashMap<String, String>();

	/**
	 * <p>
	 * A class level constant to hold the <tt>characters to be excluded globally</tt>
	 * configured
	 * </p>
	 */
	public static final String KEY_GLOBAL_EXCLUDE_CHARS =
							HFJV_BASEKEY_WITH_SEPARATOR 	+ HFJV_FIELD_GLOBAL
							+ HFJV_KEY_SEPARATOR 
							+ HFJV_FIELD_CONSTRAINT_EXCLUDE_CHARS ;

	/**
	 * <p>
	 * A class level <tt>LinkedHashMap</tt> to store the <tt>errorCode</tt> and
	 * <tt>errorDesc</tt> declared globally (common to the entire application)
	 * </p>
	 */
	public static LinkedHashMap<String,String> globalErrorMap =
												new LinkedHashMap<String, String>();

	/**
	 * <p>
	 *  A class level<tt>LinkedHashMap</tt> to store the <tt>errorCode</tt> and
	 *  <tt>errorDesc</tt> declared for an entire module
	 *  </p>
	 */
	public static LinkedHashMap<String,String> globalModuleErrorMap =
												new LinkedHashMap<String, String>();

	/**
	 * <p>
	 * A class level <tt>LinkedHashMap</tt> to store the <tt>errorCode</tt> and
	 * <tt>errorDesc</tt> declared for a specific module at the global level
	 * </p>
	 * <p>
	 * Example: <tt>hfjv.global.mandatoryConstraint.errorCode</tt>
	 * </p>
	 */
	 public static LinkedHashMap<String, String> moduleSpecificErrorMap =
														new LinkedHashMap<String, String>();


	/**
	 * <p>A private Constructor for Singleton</p>
	 */
	private ValidatorAssembler() {}

	/**
	 * <p>
	 * A class level method to hand over a single copy of an instance of this class
	 * </p>
	 * 
	 * @return
	 * 			the single instance of this class
	 */
	public static ValidatorAssembler getInstance()
	{
		if(null==_instance)
		{
			_instance = new ValidatorAssembler();
		}

		return _instance;
	}

	static
	{
		initGlobalConstraintMap();
		initListOfModules();
		initModuleFieldsMap();
		initFieldConstraintList();
	}

	/**
	 * <p>
	 * This method initializes HFJV with the list of modules configured
	 * </p>
	 */
	private static void initListOfModules()
	{
		final String THIS_METHOD_NAME = "initListOfModules() - ";

		logger.enter(THIS_METHOD_NAME);

		String moduleNames = _propertyUtil.getProperty(HFJV_MODULE_WITH_BASEKEY);

		logger.debug(THIS_METHOD_NAME + "moduleNames : "+moduleNames);

		listOfModules = GlobalUtil.getListOfTokens(moduleNames);

		logger.info(THIS_METHOD_NAME + " moduleList : "+listOfModules);

		logger.exit(THIS_METHOD_NAME);

	}

	/**
	 * <p>
	 * This method initializes the map to hold the global constraints configured
	 * </p>
	 */
	public static void initGlobalConstraintMap()
	{
		final String THIS_METHOD_NAME = "initGlobalConstraintMap() - ";

		logger.enter(THIS_METHOD_NAME);

		String globalFieldKey = HFJV_BASEKEY_WITH_SEPARATOR +
																	HFJV_FIELD_GLOBAL;

		ArrayList<String> globalKeysList = _propertyUtil.
					getSpecificKeysAsList(globalFieldKey);

		/* Validate the list for a safer side */
		if(CollectionUtil.isInvalidList(globalKeysList))
		{
			printGlobalConstraintMapStats();
			return;
		}

		String globalFieldProp = null;
		String globalFieldValue = null;

		for(String globalKey : globalKeysList)
		{
			globalFieldProp = getFieldProp(globalKey);

			globalFieldValue = _propertyUtil.getProperty(globalKey);

			logger.debug(THIS_METHOD_NAME + " key : "+globalFieldProp
															+ ", value : "+globalFieldValue);

			/*
			 * Add it only if the key has a 'valid' value, else continue
			 * with the next global key if any!
			 *
			 * No point in adding an empty key!
			 */
			if(StringUtil.isInvalidString(globalFieldValue))
			{
				continue;
			}

			/** Customized Error Code and Description -- Begin **/

			if(globalFieldProp.equalsIgnoreCase(HFJV_FIELD_ERRORCODE))
			{
				String globalFieldPropConstraint = getFieldProp(globalKey, true);

				logger.debug(THIS_METHOD_NAME +
								", [CustomError] globalFieldPropConstraint : "+ globalFieldPropConstraint);

				/* Constraint next string matches with any of the configured modules! */
				if(ValidationKeysAssembler.getFieldConstraintList().contains(globalFieldPropConstraint))
				{
					globalModuleErrorMap.put(globalFieldPropConstraint, globalFieldValue);
				}
				else /* It should be for global.errorCode only */
				{
					globalErrorMap.put(globalFieldProp, globalFieldValue);
				}
			}

			/** Customized Error Code and Description -- End **/

			globalConstraintMap.put(globalKey, globalFieldValue);
		}

		printGlobalConstraintMapStats();

		logger.info(THIS_METHOD_NAME + " globalModuleErrorMap : "+globalModuleErrorMap);
		logger.info(THIS_METHOD_NAME + " globalErrorMap : "+globalErrorMap);

		/* 
		 * For sure, the globalConstraintMap would contain some entries by now.
		 * You can act on the globalConstraints now.
		 */
		actOnGlobalConstraints();

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 * This method performs the global  constraints as per the configuration
	 * </p>
	 */
	private static void actOnGlobalConstraints()
	{
		final String THIS_METHOD_NAME = "actOnGlobalConstraints() - ";

		logger.enter(THIS_METHOD_NAME);

		actOnGlobalExcludeChars();

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 * This method performs the evaluation on the characters to be excluded globally
	 * </p>
	 */
	private static void actOnGlobalExcludeChars()
	{
		final String THIS_METHOD_NAME = "actOnGlobalExcludeChars() - ";

		logger.enter(THIS_METHOD_NAME);

		if(isGlobalExcludedCharsPresent())
		{
			String globalExcludedChars =
				globalConstraintMap.get(KEY_GLOBAL_EXCLUDE_CHARS);

			if(StringUtil.isValidString(globalExcludedChars))
			{
				ArrayList<String> globalExcludedCharsList =
					GlobalUtil.getListOfTokens(globalExcludedChars);

				Constraint.setGlobalExcludedCharsList(globalExcludedCharsList);
			}
			else
			{
				logger.info(THIS_METHOD_NAME + " Looks like the global"
						+ " ExcludeChars is configured but empty!");
			}
		}
		else
		{
			logger.info(THIS_METHOD_NAME + " Looks like there are no global"
					+ " ExcludeChars configured!");
		}

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 * This method returns a true/false based on whether are there any characters present
	 * to be excluded globally or not, as per the configuration
	 * </p>
	 * 
	 * @return
	 * 			a true/false based on the configuration
	 */
	private static boolean isGlobalExcludedCharsPresent()
	{
		return globalConstraintMap.containsKey(KEY_GLOBAL_EXCLUDE_CHARS);
	}

	/**
	 * <p>
	 * This method prints the global constraint map for the debugging and statistics
	 * information
	 * </p>
	 */
	private static void printGlobalConstraintMapStats()
	{
		logger.info(StringUtil.getRepeatedChars("%",50));

		/*
		 * Just to adjust the 3 digit value of the ending fillerChar
		 * is less than 1 of starting (7 & 6)
		 */
		logger.info(StringUtil.getRepeatedChars("%",7)
			+ " Total # of global entries are : " 	+ globalConstraintMap.size() + "  "
			+ StringUtil.getRepeatedChars("%", 6));

		logger.info(StringUtil.getRepeatedChars("%", 50));

		if(!CollectionUtil.isEmptyMap(globalConstraintMap))
		{
			logger.debug(" globalConstraintMap : "+globalConstraintMap);
		}
	}

	/**
	 * <p>
	 * This method initializes the map to hold the module and list of fields
	 * </p>
	 */
	private static void initModuleFieldsMap()
	{
		final String THIS_METHOD_NAME = "initModuleFieldsMap() - ";

		logger.enter(THIS_METHOD_NAME);

		ArrayList<String> fieldsOfModule = null;

		for(String moduleName : listOfModules)
		{
			fieldsOfModule = getListOfFields(moduleName);
			adjustModuleSpecificErrorMap(moduleName);
			moduleFieldsMap.put(moduleName, fieldsOfModule);
		}

		logger.info(THIS_METHOD_NAME + "moduleFieldsMap --> "+moduleFieldsMap);

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 * This method evaluates the module specific error map based on configuration
	 * </p>
	 * 
	 * @param moduleName
	 * 				the module name whose error codes need to be investigated
	 */
	private static void adjustModuleSpecificErrorMap(String moduleName)
	{
		final String THIS_METHOD_NAME = "adjustModuleSpecificErrorMap() - ";

		logger.enter(THIS_METHOD_NAME);

		/** Customized Error Code and Description -- Begin **/

		String moduleNameBase = getKeyForModule(moduleName);

		logger.debug(THIS_METHOD_NAME + " moduleNameBase : "+moduleNameBase);

		String keyForModuleName = moduleNameBase + HFJV_KEY_SEPARATOR;

		logger.debug(THIS_METHOD_NAME + " keyForModule Name : "+keyForModuleName);

		ArrayList<String> keyList = _propertyUtil.getSpecificKeysAsList(keyForModuleName);

		logger.debug(THIS_METHOD_NAME + " keyList : "+ keyList);

		String fieldType = null;

		String propValue = null;

		ArrayList<String> fieldConstraintList = ValidationKeysAssembler.getFieldConstraintList();

		logger.debug(THIS_METHOD_NAME + " fieldConstraintList : "+fieldConstraintList);

		String fieldConstraintErrorCode = keyForModuleName;

		for(String fieldConstraint : fieldConstraintList)
		{
			fieldConstraintErrorCode = keyForModuleName;

			fieldConstraintErrorCode += fieldConstraint + HFJV_KEY_SEPARATOR 
																	+ HFJV_FIELD_ERRORCODE;

			logger.debug(THIS_METHOD_NAME 
												+ " fieldConstraintErrorCode : "+fieldConstraintErrorCode);

			propValue = _propertyUtil.getProperty(fieldConstraintErrorCode);

			logger.debug(THIS_METHOD_NAME + " propValue : "+propValue);

			fieldType = getFieldProp(fieldConstraintErrorCode, true);

			logger.debug(THIS_METHOD_NAME + " fieldType : "+fieldType);

			if(StringUtil.isValidString(propValue))
			{
				String moduleNameAlone = getFieldProp(keyForModuleName, true);
				logger.debug(THIS_METHOD_NAME 
													+ " moduleNameAlone : "+moduleNameAlone);
				moduleSpecificErrorMap.put(moduleNameAlone+"-"+fieldType, propValue);
			}
		}

		logger.info(THIS_METHOD_NAME 
									+ " module specific Error Map --> "+moduleSpecificErrorMap);

		/** Customized Error Code and Description -- End **/

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 * This method retrieves the list of fields configured for a specified module
	 * </p>
	 * 
	 * @param moduleName
	 * 				the module whose fields needs to be retrieved
	 * 
	 * @return
	 * 				 a list of fields configured for the given module
	 */
	private static ArrayList<String> getListOfFields(String moduleName)
	{
		final String THIS_METHOD_NAME = "getListOfFields() - ";

		logger.enter(THIS_METHOD_NAME + " moduleName="+moduleName);

		ArrayList<String> listOfFields = new ArrayList<String>();

		String fieldKeyForModule = getKeyForModule(moduleName, true);

		String fieldsOfModule = _propertyUtil.getProperty(fieldKeyForModule);

		listOfFields = GlobalUtil.getListOfTokens(fieldsOfModule);

		logger.debug(THIS_METHOD_NAME 
						+ "fieldsOfModule ["+moduleName	+"] : "+fieldsOfModule);

		logger.exit(THIS_METHOD_NAME);

		return listOfFields;
	}

	/**
	 * <p>
	 * This method gives the key for the given module, so as to use it for fetching the
	 * module specific attributes
	 * </p>
	 * 
	 * @param moduleName
	 * 					the module whose key needs to be returned
	 * 
	 * @return
	 * 				the key for the module
	 */
	private static String getKeyForModule(String moduleName)
	{
		return getKeyForModule(moduleName, false);
	}

	/**
	 * <p>
	 * This method gives the key for the given module for fetching the module specific
	 * attributes. The <tt>includeField</tt> determines whether or not to include the 
	 * <tt>.fields</tt> with the key for module.
	 * </p>
	 * 
	 * @param moduleName
	 * 			the module whose key needs to be returned
	 * 
	 * @param includeField
	 * 			a boolean value determines whether or not the <tt>.fields</tt> to be
	 * 			appended with the module name to be returned
	 * 
	 * @return
	 * 			the key for the given module
	 */
	private static String getKeyForModule(String moduleName, boolean includeField)
	{
		String keyForFieldsOfModule = HFJV_BASEKEY_WITH_SEPARATOR
							+ moduleName;

		if(includeField)
		{
			keyForFieldsOfModule += HFJV_KEY_SEPARATOR
							+ HFJV_BASEKEY_FIELD;
		}

		return keyForFieldsOfModule;
	}

	/**
	 * <p>
	 * This method returns the key for the specified field of the module
	 * </p>
	 * 
	 * @param fieldName
	 * 			the field whose key needs to be returned
	 * 
	 * @param moduleName
	 * 			the module which the field is associated with
	 * 
	 * @return
	 * 			the key for given field of a  module
	 */
	private static String getKeyForFieldOfModule(String fieldName, String moduleName)
	{
		String keyForModule = getKeyForModule(moduleName);

		String keyForFieldOfModule = keyForModule + HFJV_KEY_SEPARATOR 
												+ fieldName + HFJV_KEY_SEPARATOR;

		return keyForFieldOfModule;
	}

	/**
	 * <p>
	 * This method initializes the list of constraints for all the fields for all modules
	 * </p>
	 */
	private static void initFieldConstraintList()
	{
		final String THIS_METHOD_NAME = "initFieldConstraintList() - ";

		logger.enter(THIS_METHOD_NAME);

		Set<String> keySet = moduleFieldsMap.keySet();

		ArrayList<String> fieldsList = null;

		String nameOfField = null;

		Field fieldObj = null;

		String keyForFieldOfModule = null;

		ArrayList<String> listOfFieldProps = new ArrayList<String>();

		Constraint constraintObj = null;

		ArrayList<Constraint> fieldConstraintList = null;

		LinkedHashMap<Field, ArrayList<Constraint>> tempFieldConstraintMap = null;

		for(String moduleName : keySet)
		{
			fieldsList = moduleFieldsMap.get(moduleName);

			/** for every module, reset this else it will be a mess */
			tempFieldConstraintMap = new LinkedHashMap<Field, ArrayList<Constraint>>();

			/** No point in proceeding further if the list is null or empty */
			if(CollectionUtil.isInvalidList(fieldsList))
			{
				continue;
			}

			logger.debug(THIS_METHOD_NAME + "[CUSTOM-FIELDLIST] fieldsList : "+fieldsList);

			for(String fieldName : fieldsList)
			{
				nameOfField = moduleName + GlobalUtil.MODULE_FIELD_SEPARATOR
																	+ fieldName;

				fieldConstraintList = new ArrayList<Constraint>();

				fieldObj = new Field(nameOfField);

				fieldObj.setDisplayName(GlobalUtil.getDisplayName(nameOfField));

				keyForFieldOfModule = getKeyForFieldOfModule(fieldName, moduleName);

				logger.debug(THIS_METHOD_NAME + " {keyForFieldOfModule} for ["
						+ fieldName +"] of "+moduleName+" is : " + keyForFieldOfModule);

				listOfFieldProps = _propertyUtil.getSpecificKeysAsList(keyForFieldOfModule);

				logger.debug(THIS_METHOD_NAME + "listOfFieldProps for "
															+ fieldName + " : "+listOfFieldProps);

				/* 
				 * Check if the fieldName has any valid field props
				 * configured.
				 *
				 * It may be possible that it might have got just listed
				 * in the 'XYZ.<moduleName>.fields=a,b,c'
				 * 
				 * BUT the actual field may NOT have got configured with
				 * any values down.
				 */
				if(CollectionUtil.isInvalidList(listOfFieldProps))
				{
					logger.info(THIS_METHOD_NAME + " SKIPPING the field ['"
													+ fieldName + "'], as it looks like it is NOT "
													+ " configured with any values for validation.");

					continue;
				}

				updateDependentFields(fieldObj, moduleName, fieldName);

				adjustFieldNameMap(nameOfField, fieldObj);

				String valueOfFieldProp = null;

				String fieldPropType = null;

				boolean isTypeConstraintAddedForField = false;

				for(String fieldPropName : listOfFieldProps)
				{
					valueOfFieldProp = _propertyUtil.getProperty(fieldPropName);

					logger.debug(THIS_METHOD_NAME + "\t\t{key,value} : "
												+fieldPropName+", ["+valueOfFieldProp+"]");

					/** For efficient implementation */
					if(StringUtil.isInvalidString(valueOfFieldProp))
					{
						/* Continue with the next fieldProp */
						continue;
					}

					fieldPropType = getFieldProp(fieldPropName);

					/** == Update Custom Error Code for Field : START ===== */

					/* do it if there are any fields */
					if(fieldPropType.equalsIgnoreCase(HFJV_FIELD_ERRORCODE)
																	||
						fieldPropType.equalsIgnoreCase(HFJV_FIELD_ERRORDESC))
					{
						logger.debug(" [Custom] Error Code!!!, "+
							"fieldPropType="+fieldPropType);

						/*
						 * As the errorCode, errorDesc would be the last part of
						 * the key, should have to manipulate to get the
						 * actual type here!
						 */
						String fieldPropTypeConstraint = getFieldProp(fieldPropName, true);

						logger.debug(THIS_METHOD_NAME
													+ "[Custom] Error Code, fieldPropTypeConstraint="
													+ fieldPropTypeConstraint);

						ErrorDetails errorInfoObj = fieldObj.getErrorInfoMap().
                                                                                    get(fieldPropTypeConstraint);

						/* If this is the first time, the errorInfo may not exist */
						if(null==errorInfoObj)
						{
							errorInfoObj = new ErrorDetails();
						}

						/* Set the error code/desc accordingly */
						/* Best advantage: Whatever has been configured, that only will be set */
						if(fieldPropType.equalsIgnoreCase(HFJV_FIELD_ERRORCODE))
						{
							errorInfoObj.setErrorCode(valueOfFieldProp);
						}
							
						if(fieldPropType.equalsIgnoreCase(HFJV_FIELD_ERRORDESC))
						{
							errorInfoObj.setErrorDesc(valueOfFieldProp);
						}

						/** Finally set it back to the field's map */
						fieldObj.getErrorInfoMap().put(fieldPropTypeConstraint, errorInfoObj);

						/** Most importantly, it should skip the rest of the steps!*/
						continue;
					}

					/** === Update Custom Error Code for Field : END === */

					/*
					 * If it is 'dependentField' or 'value' (for
					 * 'dependentField.value'),  just skip it and proceed with
					 * next iteration. Because, the dependentField list are
					 * evaluated earlier. Check updateDependentFields() method
					 */
					if((fieldPropType.equalsIgnoreCase(HFJV_FIELD_DEPENDENT))
																	||
						(fieldPropType.equalsIgnoreCase(HFJV_FIELD_DEPENDENT_VALUE)))
					{
						logger.debug(THIS_METHOD_NAME +
							" dependent/value field. Hence, omitting!");

						continue;
					}

					if(fieldPropType.equalsIgnoreCase(HFJV_FIELD_TYPE))
					{
						fieldObj.setType(valueOfFieldProp);

						logger.debug(THIS_METHOD_NAME + " setting the type "
							+"'"+fieldPropType+"' of field ["+	fieldObj.getName()+"]");

						/* If it is a number type, add a TypeConstraint to it */
						if(GlobalUtil.getNumberTypeList().contains(valueOfFieldProp))
						{
							if(!isTypeConstraintAddedForField)
							{
								logger.debug(THIS_METHOD_NAME + " constraintObj is "
									+ "of number type. Adding a TypeConstraint prior to it.");

								Constraint typeConstraintObj = new TypeConstraint();
								fieldConstraintList.add(typeConstraintObj);

								isTypeConstraintAddedForField = true;
							}
						}
					}
					else
					{
						constraintObj = getConstraintForFieldProp(fieldPropType);

						/**
						 * In case if the field is optional, it *should*
						 * have been specified as 'mandatoryConstraint=No'.
						 *
					 	 * But this means, we don't need to include any
						 * other constraints including the mandatoryConstraint.
						 * Clear the constraintList for the field if any included earlier.
						 */
						if(constraintObj instanceof MandatoryConstraint)
						{
							logger.debug(THIS_METHOD_NAME
								+ "constraint is of type MandatoryConstraint");

							if(valueOfFieldProp.equalsIgnoreCase("NO") ||
								valueOfFieldProp.equalsIgnoreCase("N"))
							{
								logger.debug(THIS_METHOD_NAME
																	+" setting deferredEvaluation to true!");

								fieldObj.setDeferredEvaluation(true);

								/* we need other constraints, just proceed with the next constraint if any */
								continue;
							}
						}
						
						/** 
						 * Just to facilitate the validation of few mandatory fields having the 
						 * excludeCharsConstraint even if the dependentField does NOT have the
						 * specified value (on which this field will become mandatory)
						 */
						if(constraintObj instanceof ExcludeCharsConstraint)
						{
							fieldObj.setExcludedCharsSet(true);
						}

						constraintObj.setValueToCheck(valueOfFieldProp);

						fieldConstraintList.add(constraintObj);
					}
				}

				logger.info(THIS_METHOD_NAME + StringUtil.getRepeatedChars("#", 50));
				logger.info(THIS_METHOD_NAME + " Field Name : "+nameOfField);
				logger.info(THIS_METHOD_NAME + " ConstraintList : "+fieldConstraintList);
				/** Custom Error Info - START */
				logger.info(THIS_METHOD_NAME + " Field Error Info : "
																						+fieldObj.getErrorInfoMap());
				/** Custom Error Info - END */
				logger.info(THIS_METHOD_NAME + StringUtil.getRepeatedChars("#",50));

				/* adding the verifiable flag to true just at the final stage */
				fieldObj.setVerifiable(true);

				/** Preserve the state for later use at repeated executions */
				fieldObj.preserveState();

				logger.info(THIS_METHOD_NAME + "[#@#@] field's state is preserved");

				fieldConstraintMap.put(fieldObj, fieldConstraintList);

				/* for storing this in a global moduleFieldsConstraintListMap, it gets reset to 
				 * a new and empty map at the beginning of the outer for loop which 
				 * iterates for the total list of modules configured in the properties file
				 */
				tempFieldConstraintMap.put(fieldObj, fieldConstraintList);

				/** Helps for all the future validations, in DefaultValidator */
				moduleFieldsConstraintListMap.put(moduleName, tempFieldConstraintMap);
			}
		}

		logger.debug(StringUtil.getRepeatedChars("=",50));
		logger.debug(" *** fieldConstraintMap **** : "+fieldConstraintMap);
		logger.debug(StringUtil.getRepeatedChars("=",50));

		logger.debug(StringUtil.getRepeatedChars("&",50));
		logger.debug(" *** moduleFieldsConstraintListMap **** : "+
																		moduleFieldsConstraintListMap);
		logger.debug(StringUtil.getRepeatedChars("&",50));

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * <p>
	 * This method updates the dependent fields information if any, of the given field
	 * and module combination
	 * </p>
	 * 
	 * @param fieldObj
	 * 					the field instance whose dependent fields needs to be updated
	 * 
	 * @param moduleName
	 * 					the module with which the field is associated with
	 * 
	 * @param fieldName
	 * 					the name of the field configured
	 */
	private static void updateDependentFields(Field fieldObj,
														String moduleName, String fieldName)
	{
		final String THIS_METHOD_NAME = "updateDependentFields() - ";

		logger.enter(THIS_METHOD_NAME + "field="+fieldObj.getName()
				+", moduleName="+moduleName+", fieldName="+fieldName);

		String moduleWithFieldName = HFJV_BASEKEY_WITH_SEPARATOR
							+ moduleName + HFJV_KEY_SEPARATOR 	+ fieldName;

		String dependentFieldKey = moduleWithFieldName
									+ HFJV_KEY_SEPARATOR + HFJV_FIELD_DEPENDENT;

		logger.debug(THIS_METHOD_NAME + "dependentFieldKey for ["
					+ moduleWithFieldName +"] : "+dependentFieldKey);

		String dependentFieldNames = _propertyUtil.getProperty(dependentFieldKey);

		logger.debug(THIS_METHOD_NAME + "dependentFieldNames : "
																			+ dependentFieldNames);

		ArrayList<String> dependentFieldNameList =
														GlobalUtil.getListOfTokens(dependentFieldNames);

		logger.debug(THIS_METHOD_NAME + "dependentFieldNameList : "
																	+ dependentFieldNameList);

		/** No point in continuing further if the list is null or empty */
		if(CollectionUtil.isInvalidList(dependentFieldNameList))
		{
			return;
		}

		String dependentFieldNameWithModule = moduleName +
																GlobalUtil.MODULE_FIELD_SEPARATOR;

		Field dependentFieldObj = null;

		String dependentFieldValue = null;

		String dependentValueNameKey = dependentFieldKey
								+ HFJV_KEY_SEPARATOR;

		LinkedHashMap<String, String> dependentFieldValueMap =
														new LinkedHashMap<String,String>();

		/** For having multiple dependencies */
		String dependentFieldNameWithModuleLocal = "";
		String dependentValueNameKeyLocal = "";

		for(String dependentFieldName : dependentFieldNameList)
		{
			dependentFieldNameWithModuleLocal = dependentFieldNameWithModule 
																				 + dependentFieldName;

			logger.debug(THIS_METHOD_NAME + "dependentFieldNameWithModule : "
														+ dependentFieldNameWithModuleLocal);

			dependentFieldObj = getFieldNameMap().get(
																	dependentFieldNameWithModuleLocal);

			logger.debug(THIS_METHOD_NAME + "dependentFieldObj : "
																			+ dependentFieldObj);

			if(null!=dependentFieldObj)
			{
				dependentValueNameKeyLocal = dependentValueNameKey
								+ dependentFieldName 	+ HFJV_KEY_SEPARATOR
								+ HFJV_FIELD_DEPENDENT_VALUE;

				logger.debug(THIS_METHOD_NAME + "dependentValueNameKey : "+
								 										dependentValueNameKeyLocal);

				dependentFieldValue = _propertyUtil.getProperty(
																			dependentValueNameKeyLocal);

				logger.debug(THIS_METHOD_NAME + "dependentFieldValue : "
																		+ dependentFieldValue);

				dependentFieldObj.setDependentFieldValue(dependentFieldValue);

				dependentFieldValueMap.put(dependentFieldNameWithModuleLocal, 
																				dependentFieldValue);
			}
		}

		fieldObj.setDependentFieldValueMap(dependentFieldValueMap);

		logger.debug(THIS_METHOD_NAME + "(**) field after dependency (**) : "
																	+ fieldObj.toLongString());

		logger.debug(THIS_METHOD_NAME + "(**) dependentFieldValueMap (**) : "
																+ dependentFieldValueMap);

		logger.exit(THIS_METHOD_NAME);
	}

	/**
	 * @return the fieldNameMap
	 */
	public static HashMap<String, Field> getFieldNameMap() {
		return fieldNameMap;
	}

	/**
	 * @param fieldNameMap the fieldNameMap to set
	 */
	public static void setFieldNameMap(HashMap<String, Field> fieldNameMap) {
		ValidatorAssembler.fieldNameMap = fieldNameMap;
	}

	/**
	 * @return the listOfModules
	 */
	public static ArrayList<String> getListOfModules() {
		return listOfModules;
	}

	/**
	 * @param listOfModules the listOfModules to set
	 */
	public static void setListOfModules(ArrayList<String> listOfModules) {
		ValidatorAssembler.listOfModules = listOfModules;
	}

	/**
	 * @return the moduleFieldsMap
	 */
	public static LinkedHashMap<String, ArrayList<String>> getModuleFieldsMap() {
		return moduleFieldsMap;
	}

	/**
	 * @param moduleFieldsMap the moduleFieldsMap to set
	 */
	public static void setModuleFieldsMap(
			LinkedHashMap<String, ArrayList<String>> moduleFieldsMap) {
		ValidatorAssembler.moduleFieldsMap = moduleFieldsMap;
	}

	/**
	 * @return the fieldConstraintMap
	 */
	public static LinkedHashMap<Field, ArrayList<Constraint>> getFieldConstraintMap() {
		return fieldConstraintMap;
	}

	/**
	 * @param fieldConstraintMap the fieldConstraintMap to set
	 */
	public static void setFieldConstraintMap(
			LinkedHashMap<Field, ArrayList<Constraint>> fieldConstraintMap) {
		ValidatorAssembler.fieldConstraintMap = fieldConstraintMap;
	}

	/**
	 * @return the moduleFieldsConstraintListMap
	 */
	public static LinkedHashMap<String, LinkedHashMap<Field, 
								ArrayList<Constraint>>> getModuleFieldsConstraintListMap() {
		return moduleFieldsConstraintListMap;
	}

	/**
	 * @param moduleFieldsConstraintListMap the moduleFieldsConstraintListMap to set
	 */
	public static void setModuleFieldsConstraintListMap(
			LinkedHashMap<String, LinkedHashMap<Field, 
									ArrayList<Constraint>>> moduleFieldsConstraintListMap) {
		ValidatorAssembler.moduleFieldsConstraintListMap = moduleFieldsConstraintListMap;
	}

	/**
	 * @return the globalConstraintMap
	 */
	public static LinkedHashMap<String, String> getGlobalConstraintMap() {
		return globalConstraintMap;
	}

	/**
	 * @param globalConstraintMap the globalConstraintMap to set
	 */
	public static void setGlobalConstraintMap(
			LinkedHashMap<String, String> globalConstraintMap) {
		ValidatorAssembler.globalConstraintMap = globalConstraintMap;
	}

	/**
	 * <p>
	 * This method returns the field property from a given key for field with module
	 * </p>
	 * 
	 * @param fieldOfModuleKey
	 * 					the key of the given key and module combination
	 * 
	 * @return
	 * 				the property from the key of the field, module combination
	 */
	private static String getFieldProp(String fieldOfModuleKey)
	{
		return getFieldProp(fieldOfModuleKey, false);
	}

	/**
	 * <p>
	 * This method returns the field property from a given key for a field with module
	 * </p>
	 * 
	 * @param fieldOfModuleKey
	 * 					the key of the given key and module combination
	 * 
	 * @param notLast
	 * 					a boolean value to determine whether or not the last parameter
	 * 
	 * @return
	 * 					the property from the key of the field, module combination
	 */
	private static String getFieldProp(String fieldOfModuleKey, boolean notLast)
	{
		final String THIS_METHOD_NAME = "getFieldProp() - ";

		logger.enter(THIS_METHOD_NAME + " fieldOfModuleKey="+fieldOfModuleKey);

		String fieldProp = null;

		int lastIndexOfSeparator = fieldOfModuleKey.lastIndexOf(
																	HFJV_KEY_SEPARATOR);

		int actualLastIndexOfSeparator = lastIndexOfSeparator;

		/** Custom Error Code - START */
		if(notLast)
		{
			lastIndexOfSeparator = fieldOfModuleKey.lastIndexOf(
				HFJV_KEY_SEPARATOR, lastIndexOfSeparator-1);
		}
		/** Custom Error Code - END */

		if(lastIndexOfSeparator!=-1)
		{
			if(notLast)
			{
				fieldProp = fieldOfModuleKey.substring(lastIndexOfSeparator+1,
								actualLastIndexOfSeparator);
			}
			else
			{
				fieldProp = fieldOfModuleKey.substring(lastIndexOfSeparator+1);
			}
		}

		logger.debug(THIS_METHOD_NAME + "fieldProp --> "+fieldProp);

		logger.exit(THIS_METHOD_NAME);

		return fieldProp;
	}

	/**
	 * <p>
	 * This methods returns the <tt>Constraint</tt> instance of the specified field
	 * property
	 * </p>
	 * 
	 * @param fieldProp
	 * 			the field property that contains the constraint information
	 * 
	 * @return
	 * 			an instance of the appropriate <tt>Constraint</tt>
	 */
	public static Constraint getConstraintForFieldProp(String fieldProp)
	{
		final String THIS_METHOD_NAME = "getConstraintForFieldProp() - ";

		logger.enter(THIS_METHOD_NAME + " fieldProp="+fieldProp);

		Constraint constraintObj = null;

		String className = null;

		className = ValidationKeysAssembler.getFieldConstraintTypeNameMap().
																				get(fieldProp);

		logger.debug(THIS_METHOD_NAME + "className : "+className);

		constraintObj = ValidationKeysAssembler.getConstraintTypeInstance(className);

		logger.debug(THIS_METHOD_NAME + " [%%] constraintObj [%%] : "+constraintObj);

		logger.exit(THIS_METHOD_NAME);

		return constraintObj;
	}

	/**
	 * <p>
	 * This method adds the specified name and field instance to the fieldNameMap
	 * </p>
	 * 
	 * @param name
	 * 					the name of the field
	 * 
	 * @param fieldObj
	 * 					the field instance to be associated with the name in the map
	 */
	private static void adjustFieldNameMap(String name, Field fieldObj)
	{
		fieldNameMap.put(name,fieldObj);
	}

	/**
	 * @return the globalErrorMap
	 */
	public static LinkedHashMap<String, String> getGlobalErrorMap() {
		return globalErrorMap;
	}

	/**
	 * @param globalErrorMap the globalErrorMap to set
	 */
	public static void setGlobalErrorMap(
			LinkedHashMap<String, String> globalErrorMap) {
		ValidatorAssembler.globalErrorMap = globalErrorMap;
	}

	/**
	 * @return the globalModuleErrorMap
	 */
	public static LinkedHashMap<String, String> getGlobalModuleErrorMap() {
		return globalModuleErrorMap;
	}

	/**
	 * @param globalModuleErrorMap the globalModuleErrorMap to set
	 */
	public static void setGlobalModuleErrorMap(
			LinkedHashMap<String, String> globalModuleErrorMap) {
		ValidatorAssembler.globalModuleErrorMap = globalModuleErrorMap;
	}

	/**
	 * @return the moduleSpecificErrorMap
	 */
	public static LinkedHashMap<String, String> getModuleSpecificErrorMap() {
		return moduleSpecificErrorMap;
	}

	/**
	 * @param moduleSpecificErrorMap the moduleSpecificErrorMap to set
	 */
	public static void setModuleSpecificErrorMap(
			LinkedHashMap<String, String> moduleSpecificErrorMap) {
		ValidatorAssembler.moduleSpecificErrorMap = moduleSpecificErrorMap;
	}
}