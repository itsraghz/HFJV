package org.hfjv.framework.core.exception;

/**
 * <p>
 * An <tt>Exception</tt> class, which would be thrown by HFJV
 * when it finds an exception while evaluating a field for any of the
 * configured values
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ValidatorException extends Exception
{
	/**
	 * <p>
	 * An IDE (eclipse) generated serialVersionUID
	 * </p>
	 */
	private static final long serialVersionUID = -4797640983220629118L;

	/**
	 * <p>
	 * Holds the name of the field which had failed in validation
	 * </p>
	 */
	String fieldName;

	/**
	 * <p>
	 * A variable to hold the <tt>errorCode</tt>
	 * </p>
	 */
	String errorCode;

	/**
	 * <p>
	 * A variable to hold the <tt>error description</tt>
	 * </p>
 	 */
	String errorDesc;

	/**
	 * <p>
	 * A throwable instance to hold the root cause of an exception
	 * </p>
 	 */
	Throwable cause;

	/**
	 * <p>
	 * An overridden two argument constructor
	 * </p>
	 * 
	 * @param errorCode
	 * 						the error code to be assigned
	 * 
	 * @param errorDesc
	 * 						the error message to be configured
	 */
	public ValidatorException(String errorCode, String errorDesc)
	{
		super(errorDesc);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	/**
	 * <p>
	 * An overridden, three argument constructor
	 * </p>
	 * 
	 * @param errorCode
	 * 						the error code to be assigned
	 * 
	 * @param errorDesc
	 * 						the error message to be configured
	 * 
	 * @param cause
	 * 						the root cause of an exception
	 */
	public ValidatorException(String errorCode, String errorDesc, Throwable cause)
	{
		super(errorDesc, cause);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.cause = cause;
	}

	/**
	 * <p>
	 * An overridden two argument constructor
	 * </p>
	 * 
	 * @param cause
	 * 						the root cause of an exception
	 * 
	 * @param message
	 * 						the error message to be set into the exception object
	 */
	public ValidatorException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * <p>
	 * An overridden one argument constructor
	 * </p>
	 * 
	 * @param cause
	 * 						the root cause of an exception
	 */
	public ValidatorException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * <p>
	 * An overridden one argument constructor
	 * </p>
	 * 
	 * @param errorDesc
	 * 				the error description to be set
	 */
	public ValidatorException(String errorDesc)
	{
		super(errorDesc);
	}

	/**
	 * <p>
	 * An overridden default constructor
	 * </p>
	 */
	public ValidatorException()
	{
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * @param cause the cause to set
	 */
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
}