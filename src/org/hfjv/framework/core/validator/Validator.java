package org.hfjv.framework.core.validator;

import org.hfjv.framework.core.exception.ValidatorException;

/**
 * <p>
 * The main interface for validation
 * </p>
 *
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public interface Validator
{

	/**
	 * <p>
	 * A method to validate all the fields of a given module
	 * </p>
	 * 
	 * @param moduleName
	 * 				the module whose fields to be validated
	 * 
	 * @throws ValidatorException
	 * 				any exceptions during validation
	 */
	public void validate(String moduleName) throws ValidatorException;
}