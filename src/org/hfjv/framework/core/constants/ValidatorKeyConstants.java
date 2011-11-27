package org.hfjv.framework.core.constants;

/**
 * <p>
 * An utility class to deal with the constants/keys of the properties configured
 * </p>
 *
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValidatorKeyConstants
{
	public static final String HFJV_BASE_KEY = "hfjv";

	public static final String HFJV_KEY_SEPARATOR = ".";

	public static final String HFJV_BASEKEY_WITH_SEPARATOR =
													HFJV_BASE_KEY + HFJV_KEY_SEPARATOR ;

	public static final String HFJV_BASEKEY_MODULE = "modules";

	public static final String HFJV_MODULE_WITH_BASEKEY =
							HFJV_BASEKEY_WITH_SEPARATOR + HFJV_BASEKEY_MODULE;

	public static final String HFJV_BASEKEY_FIELD = "fields";

	public static final String HFJV_FIELD_TYPE = "type";

	public static final String HFJV_FIELD_DEPENDENT = "dependentFields";

	public static final String HFJV_FIELD_DEPENDENT_VALUE = "value";

	public static final String HFJV_FIELD_GLOBAL = "global";

	public static final String HFJV_FIELD_ERRORCODE = "errorCode";

	public static final String HFJV_FIELD_ERRORDESC = "errorDesc";


	/* ========== Insertion Order Constants - START ===================*/

	public static final int HFJV_INSERT_ORDER_MANDATORY_CONSTRAINT = 1;

	public static final int HFJV_INSERT_ORDER_EXCLUDECHARS_CONSTRAINT = 2;

	public static final int HFJV_INSERT_ORDER_TYPE_CONSTRAINT = 3;

	public static final int HFJV_INSERT_ORDER_MINDIGIT_CONSTRAINT = 4;

	public static final int HFJV_INSERT_ORDER_MAXDIGIT_CONSTRAINT = 5;

	public static final int HFJV_INSERT_ORDER_MINLENGTH_CONSTRAINT = 6;

	public static final int HFJV_INSERT_ORDER_MAXLENGTH_CONSTRAINT = 7;

	public static final int HFJV_INSERT_ORDER_SIGN_CONSTRAINT = 8;

	public static final int HFJV_INSERT_ORDER_VALUE_NOTSTART_CONSTRAINT = 9;

	public static final int HFJV_INSERT_ORDER_VALUERANGE_CONSTRAINT = 10;

	public static final int HFJV_INSERT_ORDER_VALUELIST_CONSTRAINT = 11;

	public static final int HFJV_INSERT_ORDER_VALUE_EACHDIGIT_CONSTRAINT = 12;

	public static final int HFJV_INSERT_ORDER_DATE_CONSTRAINT = 13;


	/* ======== Insertion Order Constants - END ============= */

	/* ========== Individual Constraint Types ================= */

	public static final String HFJV_FIELD_CONSTRAINT_LENGTH_MIN = "minLengthConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_LENGTH_MAX = "maxLengthConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_VALUE_LIST = "valueListConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_VALUE_RANGE = "valueRangeConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_MANDATORY = "mandatoryConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_DIGIT_MIN = "minDigitConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_DIGIT_MAX = "maxDigitConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_EXCLUDE_CHARS = "excludeCharsConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_SIGN = "signConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_VALUE_NOTSTART = "valueNotStartWithConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_VALUE_EACHDIGIT = "valueEachDigitConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_TYPE = "typeConstraint";

	public static final String HFJV_FIELD_CONSTRAINT_DATE = "dateConstraint";

	public static final String[] ARRAY_FIELD_CONSTRAINT =
		new String[]
		{
			HFJV_FIELD_CONSTRAINT_LENGTH_MIN,
			HFJV_FIELD_CONSTRAINT_LENGTH_MAX,
			HFJV_FIELD_CONSTRAINT_VALUE_LIST,
			HFJV_FIELD_CONSTRAINT_VALUE_RANGE,
			HFJV_FIELD_CONSTRAINT_MANDATORY,
			HFJV_FIELD_CONSTRAINT_DIGIT_MIN,
			HFJV_FIELD_CONSTRAINT_DIGIT_MAX,
			HFJV_FIELD_CONSTRAINT_EXCLUDE_CHARS,
			HFJV_FIELD_CONSTRAINT_SIGN,
			HFJV_FIELD_CONSTRAINT_VALUE_NOTSTART,
			HFJV_FIELD_CONSTRAINT_VALUE_EACHDIGIT,
			HFJV_FIELD_CONSTRAINT_TYPE,
			HFJV_FIELD_CONSTRAINT_DATE
		};

	private static final String PKG_NAME_BASE = "org.hfjv.framework.core.constraint";

	private static final String PKG_NAME_BASE_WITH_SEPARATOR = PKG_NAME_BASE + HFJV_KEY_SEPARATOR;

	private static final String PKG_NAME_LENGTH = PKG_NAME_BASE_WITH_SEPARATOR + "length";

	private static final String PKG_NAME_LENGTH_WITH_SEPARATOR = PKG_NAME_LENGTH + HFJV_KEY_SEPARATOR;

	private static final String PKG_NAME_VALUE = PKG_NAME_BASE_WITH_SEPARATOR + "value";

	private static final String PKG_NAME_VALUE_WITH_SEPARATOR = PKG_NAME_VALUE + HFJV_KEY_SEPARATOR;

	private static final String PKG_NAME_MANDATORY = PKG_NAME_BASE_WITH_SEPARATOR + "mandatory";

	private static final String PKG_NAME_MANDATORY_WITH_SEPARATOR = PKG_NAME_MANDATORY + HFJV_KEY_SEPARATOR;

	private static final String PKG_NAME_DIGIT = PKG_NAME_BASE_WITH_SEPARATOR + "digit";

	private static final String PKG_NAME_DIGIT_WITH_SEPARATOR = PKG_NAME_DIGIT + HFJV_KEY_SEPARATOR;

	private static final String PKG_NAME_EXCLUDE = PKG_NAME_BASE_WITH_SEPARATOR + "exclude";

	private static final String PKG_NAME_EXCLUDE_WITH_SEPARATOR = PKG_NAME_EXCLUDE + HFJV_KEY_SEPARATOR;

	private static final String PKG_NAME_SIGN = PKG_NAME_BASE_WITH_SEPARATOR + "sign";

	private static final String PKG_NAME_SIGN_WITH_SEPARATOR = PKG_NAME_SIGN + HFJV_KEY_SEPARATOR;

	private static final String PKG_NAME_TYPE = PKG_NAME_BASE_WITH_SEPARATOR + "type";

	private static final String PKG_NAME_TYPE_WITH_SEPARATOR = PKG_NAME_TYPE + HFJV_KEY_SEPARATOR;

	private static final String PKG_NAME_DATE = PKG_NAME_BASE_WITH_SEPARATOR + "date";

	private static final String PKG_NAME_DATE_WITH_SEPARATOR = PKG_NAME_DATE + HFJV_KEY_SEPARATOR;

	public static final String[] ARRAY_CONSTRAINT_CLASS_NAME =
		new String[]
		{
			PKG_NAME_LENGTH_WITH_SEPARATOR + "MinLengthConstraint",
			PKG_NAME_LENGTH_WITH_SEPARATOR + "MaxLengthConstraint",
			PKG_NAME_VALUE_WITH_SEPARATOR + "ValueListConstraint",
			PKG_NAME_VALUE_WITH_SEPARATOR + "ValueRangeConstraint",
			PKG_NAME_MANDATORY_WITH_SEPARATOR + "MandatoryConstraint",
			PKG_NAME_DIGIT_WITH_SEPARATOR  + "MinDigitConstraint",
			PKG_NAME_DIGIT_WITH_SEPARATOR  + "MaxDigitConstraint",
			PKG_NAME_EXCLUDE_WITH_SEPARATOR + "ExcludeCharsConstraint",
			PKG_NAME_SIGN_WITH_SEPARATOR + "SignConstraint",
			PKG_NAME_VALUE_WITH_SEPARATOR + "ValueNotStartWithConstraint",
			PKG_NAME_VALUE_WITH_SEPARATOR + "ValueEachDigitConstraint",
			PKG_NAME_TYPE_WITH_SEPARATOR + "TypeConstraint",
			PKG_NAME_DATE_WITH_SEPARATOR + "DateConstraint"
		};

}