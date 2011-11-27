package org.hfjv.framework.core.validator.assembler;

import static org.hfjv.framework.core.constants.ValidatorKeyConstants.ARRAY_CONSTRAINT_CLASS_NAME;
import static org.hfjv.framework.core.constants.ValidatorKeyConstants.ARRAY_FIELD_CONSTRAINT;

import java.util.ArrayList;
import java.util.HashMap;

import org.hfjv.framework.Logger;
import org.hfjv.framework.LoggerFactory;
import org.hfjv.framework.core.constraint.Constraint;
import org.hfjv.framework.util.GlobalUtil;
import org.hfjv.framework.util.StringUtil;

/**
 * <p>
 * A class to assemble all the validation related keys while preparing the HFJV engine
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValidationKeysAssembler
{
	/**
	 * <p>
	 * A private class level logger instance of this class
	 * </p>
	 */
	private static Logger logger = LoggerFactory.getInstance().
													getLogger(ValidationKeysAssembler.class);

	/**
	 * <p>
	 * A class level <tt>ArrayList<tt> to hold the list of <tt>Constraint</tt>
	 * objects of a field
	 * </p>
	 */
	public static final ArrayList<String> fieldConstraintList =
														new ArrayList<String>();
	/**
	 * <p>
	 * A class level <tt>ArrayList</tt> to hold the list of constraint classes
	 * </p>
	 */
	@SuppressWarnings("rawtypes")
	public static final ArrayList<Class> constraintClassList =
														new ArrayList<Class>();

	/**
	 * <p>
	 * A class level <tt>ArrayList</tt> to hold the list of constraint class names
	 * </p>
	 */
	public static final ArrayList<String> constraintClassNameList =
														new ArrayList<String>();

	/**
	 * <p>
	 * A class level <tt>HashMap&lt;String,String&gt;</tt> to hold the field and
	 * constraint type names
	 * </p>
	 */
	public static final HashMap<String,String> fieldConstraintTypeNameMap =
														new HashMap<String,String>();

	/**
	 * <p>
	 * A class level <tt>instance</tt> of this class, for a singleton purpose
	 * </p>
	 */
	public static ValidationKeysAssembler _instance = null;

	/**
	 * <p>
	 *  A private default constructor, for a singleton purpose
	 * </p>
	 */
	private ValidationKeysAssembler(){}

	/**
	 * <p>
	 * A class level factory method to provide a single instance of this class
	 * </p>
	 * @return
	 */
	public static ValidationKeysAssembler getInstance()
	{
		if(null==_instance)
		{
			_instance = new  ValidationKeysAssembler ();
		}

		return _instance;
	}

	static
	{
		initFieldConstraintList();
		initConstraintClassNameList();
		initFieldConstraintTypeNameMap();

		logger.info(StringUtil.getRepeatedChars("/*/", 75));

		logger.info("["+ GlobalUtil.getAppName() +
										"] -- ValidationKeys are assembled successfully! --");

		logger.info(StringUtil.getRepeatedChars("/*/", 75));
	}

	/**
	 * <p>
	 * A class level initializer method to populate all the list of available constraints
	 * <p>
	 */
	public static void initFieldConstraintList()
	{
		for (String fieldConstraint : ARRAY_FIELD_CONSTRAINT)
		{
			fieldConstraintList.add(fieldConstraint);
		}
	}

	/**
	 * <p>
	 * A class level initializer method to populate all the list of available constraint names
	 * <p>
	 */
	public static void initConstraintClassNameList()
	{
		for(String constraintClassName : ARRAY_CONSTRAINT_CLASS_NAME)
		{
			constraintClassNameList.add(constraintClassName);
		}
	}

	/**
	 * <p>
	 * A class level initializer method to populate all the map of available constraints
	 * for all the configured fields
	 * <p>
	 */
	public static void initFieldConstraintTypeNameMap()
	{
		String className = null;
		
		String appName = GlobalUtil.getAppName();

		for(int navigator=0; navigator<fieldConstraintList.size(); navigator++)
		{
			className = constraintClassNameList.get(navigator);
			fieldConstraintTypeNameMap.put(fieldConstraintList.get(navigator), className);
		}

		logger.info(StringUtil.getRepeatedChars("*", 60));

		logger.info("["+ appName + "] There are #"
							+ fieldConstraintTypeNameMap.size() + " constraints loaded");

		logger.info(StringUtil.getRepeatedChars("*", 60));

	}

	/**
	 * <p>
	 * A class level method to return the instance of the constraint type being passed
	 * </p>
	 * 
	 * @param className
	 * 				a constraint class whose instance needs to be created
	 * 
	 * @return
	 * 			a constraint instance being instantiated newly based on the constraint type
	 */
	public static Constraint getConstraintTypeInstance(String className)
	{
		Constraint constraintObj = null;

		boolean isError = false;

		Exception exceptionObj = null;

		try {
			constraintObj = (Constraint) Class.forName(className).newInstance();
		}catch(InstantiationException e) {
			exceptionObj = e;
			isError = true;
		}catch(IllegalAccessException e) {
			exceptionObj = e;
			isError = true;
		}catch(ClassNotFoundException e) {
			exceptionObj = e;
			isError = true;
		}

		if(isError)
		{
			exceptionObj.printStackTrace();
			GlobalUtil.stopExecutionWithError(exceptionObj.getMessage());
		}

		return constraintObj;
	}

	/**
	 * @return the fieldConstraintList
	 */
	public static ArrayList<String> getFieldConstraintList() {
		return fieldConstraintList;
	}

	/**
	 * @return the constraintClassList
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList<Class> getConstraintClassList() {
		return constraintClassList;
	}

	/**
	 * @return the constraintClassNameList
	 */
	public static ArrayList<String> getConstraintClassNameList() {
		return constraintClassNameList;
	}

	/**
	 * @return the fieldConstraintTypeNameMap
	 */
	public static HashMap<String, String> getFieldConstraintTypeNameMap() {
		return fieldConstraintTypeNameMap;
	}
}