package org.hfjv.framework.core.exception;

import java.io.Serializable;

/**
 * <p>
 * A POJO class used to carry the error information like
 * <tt>errorCode</tt> and <tt>errorDesc</tt> for the custom
 * error information
 * </p>
 *
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class ErrorDetails implements Serializable
{
	/**
	 * <p>
	 * An IDE (eclipse) generated serialVersionUID
	 * </p>
	 */
	private static final long serialVersionUID = -8956703022428978282L;

	/**
	 * <p>
	 * A variable to hold the <tt>error code</tt> for an exception
	 * </p>
	 */
	private String errorCode;

	/**
	 * <p>
	 * A variable to hold the <tt>error description</tt> for an exception
	 * </p>
	 */
	private String errorDesc;

	/**
	 * <p>
	 * A zero-arg default constructor
	 * </p>
	 */
	public ErrorDetails() {}

	/**
	 * <p>
	 * An overloaded one-argument constructor which accepts the <tt>errorCode</tt>
	 * </p>
	 *
	 * @param errorCode
	 * 		the actual error code to be set
	 */
	public ErrorDetails(String errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * <p>
	 * A two-argument constructor which accepts the <tt>errorCode</tt> and
	 * the <tt>error description</tt>
	 * </p>
	 *
	 * @param errorCode
	 * 		the error code to be set
	 *
	 * @param errorDesc
	 * 		the error description to be populated
	 */
	public ErrorDetails(String errorCode, String errorDesc)
	{
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	@Override
	public String toString()
	{
		return "[ErrorDetails] errorCode="+this.errorCode
			+", errorDesc="+this.errorDesc;
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

}