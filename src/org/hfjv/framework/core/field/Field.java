package org.hfjv.framework.core.field;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Set;

import org.hfjv.framework.core.exception.ErrorDetails;
import org.hfjv.framework.core.validator.assembler.ValidatorAssembler;
import org.hfjv.framework.util.CollectionUtil;

/**
 * <p>
 * A POJO (Plain Old Java Object) class representing a field to be evaluated in a
 * Java application.
 * </p>
 * 
 * @author M Raghavan alias Saravanan
 * @since HFJV 1.0, 15 July 2011, Friday
 */
public class Field implements Serializable
{
	/**
	 * <p>
	 * An IDE (eclipse) generated serialVersionUID
	 * </p>
	 */
	private static final long serialVersionUID = -5311480923139284451L;

	/**
	 *<p>
	 * A variable to hold the <tt>name</tt> of a field
	 *</p>
 	 */
	String name;

	/**
	 * <p>
	 * A variable to hold the actual value of the field
	 * </p>
	 */
	String value;

	/**
	 * <p>
	 * A variable to hold the data type of the field
	 * </p>
	 */
	String type;

	/**
	 * <p>
	 * A variable to hold the short/trimmed version of the name , which will be
	 * useful while displaying. Generally a field's name is captured in combination
	 * with the module name, for serving the validation nature.
	 * </p>
	 */
	String displayName;

	/**
	 * <p>
	 * This property goes in hand with the <tt>dependentFieldList</tt>
	 * property, wherein the <tt>dependentFieldList</tt> defines the list of
	 * <tt>field</tt>s and this property holds the proper <tt>value</tt>
	 * upon which the <tt>MandatoryConstraint</tt> would be considered.
	 * </p>
         */
	String dependentFieldValue;

	/**
	 * <p>
	 * replacement of both dependentFieldList and dependentFieldValue
 	 * as it lacks in having more than one different value for dependentFieldValue.
	 * </p>
	 * <p>
	 * Generally, a single field's different values can determine the
	 * validity of two different fields. For example, if emergencyAction=0,
	 * emergencyAnnouncement should be mandatory, whereas if emergencyAction=1,
	 * emergencyAttendant should be mandatory. In the existing design, there
	 * was only one place (dependentFieldValue) and it got replaced due to which
	 * a bug arose! :(
	 * </p>
	 */
	LinkedHashMap<String, String> dependentFieldValueMap;

	/**
	 * <p>
	 * A boolean variable to hold whether or not the dependentFields of the 
	 * field is evaluated or not. It is used to decide whether to consider the field's
	 * candidature for validation further or not.
	 * </p>
	 */
	boolean dependentFieldEvaluated = false;

	/**
	 * <p>
	 * A boolean variable to indicate whether or not this field is verifiable. This gets
	 * dynamically updated as per the related configuration aspects
	 * </p>
	 */
	boolean verifiable = false;


	/**
	 * <p>
	 * Used for a specific case wherein a field's mandatoryConstraint has
	 * been specified as 'No' (optional field), the framework will just
	 * exclude the MandatoryConstraint in the list and include all the other constraints
	 * by setting this flag to true.
	 * </p>
	 *
	 * <p>
	 * This flag gets cleared when the field has some value (mandatory to
	 * check only for non-null) and then the rest of the Constraints will be
	 * in action from thereon.
	 * </p>
	 *
	 * <p>
	 * Initially, it will be set to <tt>false</tt>
	 * </p>
	 */
	boolean deferredEvaluation = false;


	/**
	 * <p>
	 * This field is used to re-apply the lock to the fields every time
	 * it is evaluated, but conditionally to the fields whose 'deferredEvaluation'
	 * flag had got updated during the course of time.
	 * </p>
	 *
	 * <p>
	 * Without which it would be difficult to distinguish the regular fields
	 * whose 'deferredEvaluation' to 'true' at first and then 'false' at a
	 * later point of time (when the field carries a 'valid' value) and the
	 * fields which had never been considered for 'deferredEvaluation' at all.
	 * </p>
	 *
	 * <p>
	 * This field helps to set the 'deferredEvaluation' flag to 'true'
	 * when the corresponding field gets a 'valid' non-null and non-empty
	 * value. This field is also set to 'true' during the updation. Based on
	 * which the 'deferredEvaluation' flag will be reset at a later point of
	 * time when an invalid value (null or empty) comes to the field which by
	 * nature is 'optional' [hence (mandatoryConstraint=no) has been specified in
	 * the configuration]
	 * </p>
	 */
	boolean deferredEvaluationFlagUpdated = false;

	/**
	 * <p>
	 * A variable to hold the formatting to be applied for the field's value
	 * </p>
	 * 
	 * <p>
	 * <b>Note:</b> As of now considered <b>only</b> for <tt>date</tt> fields
	 * </p>
	 */
	String format;

	/**
	 * <p>
	 * A boolean variable used for preserving and restoring the state of 
	 * <tt>deferredEvaluation</tt> value of a <tt>Field</tt>
	 * </p>
	 */
	boolean _preservedDeferredEvaluation = false;
	
	/**
	 * <p>
	 * A boolean variable used for preserving and restoring the updated status of 
	 * <tt>deferredEvaluation</tt> value of a <tt>Field</tt>
	 * </p>
	 */
	boolean _preservedDeferredEvaluationFlagUpdated = false;
	
	/**
	 * <p>
	 * A boolean variable used for preserving and restoring the state of 
	 * <tt>dependentEvaluation</tt> value of a <tt>Field</tt>
	 * </p>
	 */
	boolean _preservedDependentFieldEvaluated = false;
	
	/**
	 * <p>
	 * A boolean variable used for preserving and restoring the updated state of 
	 * <tt>dependentEvaluation</tt> value of a <tt>Field</tt>
	 * </p>
	 */
	boolean _preservedVerifiable = false;

	/**
	 * <p>
	 * A flag to relax the rules only for the excludeChars. Reason being
	 * there are cases wherein a particular field would be optional until
	 * and unless its dependent field has a specific value. In such case,
	 * this field would NEVER be evaluated regardless of its value and
	 * that value would be carried forward for the further operations.
	 * </p>
	 *
	 * <p>
	 * Just to avoid this, this flag is introduced and it is evaluated
	 * along with the deferredEvaluation flag in the DefaultValidator's
	 * validate() method just before the list of constraints are evaluated.
	 * </p>
	 *
	 * <p>
	 * This flag would be set in ValidatorAssembler's initFieldConstraintList()
	 * method while grabbing the constraints for the particular field.
	 * </p>
	 */
	boolean excludedCharsSet = false;

	/**
	 * <p>
	 * A <tt>LinkedHashMap</tt> to store the error information with the
	 * <tt>field constraint</tt> as a key and the instance of <tt>ErrorDetails</tt>
	 * as a value.
	 * </p>
	 *
	 * <p>
	 * A field may have different kind of constraint and for each constraint it may
	 * have a separate custom error code and error description in the form of
	 * <tt>ErrorDetails</tt>.
	 * </p>
	 */
	private LinkedHashMap<String, ErrorDetails> errorInfoMap =
							new LinkedHashMap<String, ErrorDetails>();


	/**
	 * <p>
	 * A life cycle method which gets called after the validation of
	 * a field is completed. It is needed as it should NOT be cached for
	 * the further/subsequent invocations.
	 * </p>
	 */
	public void reset()
	{
		this.value = null;
	}

	/**
	 * <p>
	 * This method preserves the <tt>state</tt> of the field instance, for a later need
	 * </p>
	 */

	public void preserveState()
	{
		this._preservedDeferredEvaluation = this.deferredEvaluation;
		this._preservedDeferredEvaluationFlagUpdated =
							this.deferredEvaluationFlagUpdated;
		this._preservedDependentFieldEvaluated = this.dependentFieldEvaluated;
		this._preservedVerifiable = this.verifiable;
	}

	/**
	 * <p>
	 *  This method restores the <tt>state</tt> of the field instance, which was
	 *  preserved earlier
	 * </p>
	 */
	public void restoreState()
	{
		this.deferredEvaluation = this._preservedDeferredEvaluation;
		this.deferredEvaluationFlagUpdated = this._preservedDeferredEvaluationFlagUpdated ;
		this.dependentFieldEvaluated = this._preservedDependentFieldEvaluated;
		this.verifiable = this._preservedVerifiable;
	}

	/**
	 * <p>
	 * An overridden, one argument constructor
	 * </p>
	 * 
	 * @param name
	 * 						the name of the field instance
	 */
	public Field(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the dependentFieldEvaluated
	 */
	public boolean isDependentFieldEvaluated() {
		return dependentFieldEvaluated;
	}

	/**
	 * @param dependentFieldEvaluated the dependentFieldEvaluated to set
	 */
	public void setDependentFieldEvaluated(boolean dependentFieldEvaluated) {
		this.dependentFieldEvaluated = dependentFieldEvaluated;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean equals = false;

		if(this==obj)
		{
			return true;
		}

		if(null==obj)
		{
			return false;
		}

		if(obj instanceof Field && obj.getClass()==getClass())
		{
			Field fieldObj = (Field) obj;

			if(fieldObj.hashCode()==obj.hashCode())
			{
				equals = true;
			}
		}

		return equals;
	}
	
	@Override
	public int hashCode()
	{
		int hashCodeOfName = (null!=getName()) ? getName().hashCode() : 0;

		int hashCode = (hashCodeOfName + 7 ) * 31;

		return hashCode;
	}

	@Override
	public String toString()
	{
		return "[Field] "
						+ " name="+this.getName()
						+ ", type="+this.getType()
						+ ", value="+this.getValue();
	}

	/**
	 * <p>
	 * This method is used for logging purposes, as it gives more detailed
	 * information of the field instance
	 * </p>
	 * @return
	 */
	public String toLongString()
	{
		return "[Field] "
			+ " name="+this.getName()
			+ ", type="+this.getType()
			+ ", value="+this.getValue()
			+ ", verifiable="+this.isVerifiable()
			+ ", deferredEvaluation="+this.deferredEvaluation
			+ ", deferredEvaluationFlagUpdated="
				+this.isDeferredEvaluationFlagUpdated()
			+ ", dependentFieldEvaluated="+this.isDependentFieldEvaluated()
			+ ", dependentFieldValue="+this.getDependentFieldValue()
			+ ", format="+this.getFormat()
			+ ", dependentFieldList size="
			// + (null!=dependentFieldList?dependentFieldList.size():0)
			+ (null!=dependentFieldValueMap?dependentFieldValueMap.size():0)
			+ "). "
			+ getDependentFieldToString();
	}

	/**
	 * <p>
	 * This method gives the state of the field with preserved state information
	 * </p>
	 * 
	 * @return
	 * 			the field's state in the form of <tt>String</tt>
	 */
	public String toPreservedStateString()
	{
		return "[Field-State] "
			+ "[name="+this.getName()
			+ ", verifiable="+this.isVerifiable()
			+ ", deferredEvaluation="+this.deferredEvaluation
			+ ", deferredEvaluationFlagUpdated="
				+this.isDeferredEvaluationFlagUpdated()
			+ ", dependentFieldEvaluated="+this.isDependentFieldEvaluated();
	}

	/**
	 * <p>
	 * This method returns of the <tt>toString</tt> content of the dependentField
	 * of this field
	 * </p>
	 * 
	 * @return
	 * 			the <tt>String</tt> representation of the dependentField
	 */
	public String getDependentFieldToString()
	{
		StringBuilder sb = new StringBuilder();

		LinkedHashMap<String, String> dependentFieldValuesMap =
						this.getDependentFieldValueMap();

		if(CollectionUtil.isValidMap(dependentFieldValuesMap))
		{
			sb.append(" #$# <DependentField> --> ");

			Set<String> keySet = dependentFieldValuesMap.keySet();

			Field dependentField = null;

			for(String key : keySet)
			{
				dependentField = ValidatorAssembler.getFieldNameMap().get(key);

				sb.append(dependentField.toLongString());
			}

			sb.append(" #$# ");
		}

		return sb.toString();
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the dependentFieldValue
	 */
	public String getDependentFieldValue() {
		return dependentFieldValue;
	}

	/**
	 * @param dependentFieldValue the dependentFieldValue to set
	 */
	public void setDependentFieldValue(String dependentFieldValue) {
		this.dependentFieldValue = dependentFieldValue;
	}

	/**
	 * @return the dependentFieldValueMap
	 */
	public LinkedHashMap<String, String> getDependentFieldValueMap() {
		return dependentFieldValueMap;
	}

	/**
	 * @param dependentFieldValueMap the dependentFieldValueMap to set
	 */
	public void setDependentFieldValueMap(
			LinkedHashMap<String, String> dependentFieldValueMap) {
		this.dependentFieldValueMap = dependentFieldValueMap;
	}

	/**
	 * @return the verifiable
	 */
	public boolean isVerifiable() {
		return verifiable;
	}

	/**
	 * @param verifiable the verifiable to set
	 */
	public void setVerifiable(boolean verifiable) {
		this.verifiable = verifiable;
	}

	/**
	 * @return the deferredEvaluation
	 */
	public boolean isDeferredEvaluation() {
		return deferredEvaluation;
	}

	/**
	 * @param deferredEvaluation the deferredEvaluation to set
	 */
	public void setDeferredEvaluation(boolean deferredEvaluation) {
		this.deferredEvaluation = deferredEvaluation;
	}

	/**
	 * @return the deferredEvaluationFlagUpdated
	 */
	public boolean isDeferredEvaluationFlagUpdated() {
		return deferredEvaluationFlagUpdated;
	}

	/**
	 * @param deferredEvaluationFlagUpdated the deferredEvaluationFlagUpdated to set
	 */
	public void setDeferredEvaluationFlagUpdated(
			boolean deferredEvaluationFlagUpdated) {
		this.deferredEvaluationFlagUpdated = deferredEvaluationFlagUpdated;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the _preservedDeferredEvaluation
	 */
	public boolean is_preservedDeferredEvaluation() {
		return _preservedDeferredEvaluation;
	}

	/**
	 * @param _preservedDeferredEvaluation the _preservedDeferredEvaluation to set
	 */
	public void set_preservedDeferredEvaluation(boolean _preservedDeferredEvaluation)
	{
		this._preservedDeferredEvaluation = _preservedDeferredEvaluation;
	}

	/**
	 * @return the _preservedDeferredEvaluationFlagUpdated
	 */
	public boolean is_preservedDeferredEvaluationFlagUpdated() {
		return _preservedDeferredEvaluationFlagUpdated;
	}

	/**
	 * @param _preservedDeferredEvaluationFlagUpdated 
	 * 					the _preservedDeferredEvaluationFlagUpdated to set
	 */
	public void set_preservedDeferredEvaluationFlagUpdated(
			boolean _preservedDeferredEvaluationFlagUpdated) {
		this._preservedDeferredEvaluationFlagUpdated = 
										_preservedDeferredEvaluationFlagUpdated;
	}

	/**
	 * @return the _preservedDependentFieldEvaluated
	 */
	public boolean is_preservedDependentFieldEvaluated() {
		return _preservedDependentFieldEvaluated;
	}

	/**
	 * @param _preservedDependentFieldEvaluated 
	 * 					the _preservedDependentFieldEvaluated to set
	 */
	public void set_preservedDependentFieldEvaluated(
			boolean _preservedDependentFieldEvaluated) {
		this._preservedDependentFieldEvaluated = _preservedDependentFieldEvaluated;
	}

	/**
	 * @return the _preservedVerifiable
	 */
	public boolean is_preservedVerifiable() {
		return _preservedVerifiable;
	}

	/**
	 * @param _preservedVerifiable the _preservedVerifiable to set
	 */
	public void set_preservedVerifiable(boolean _preservedVerifiable) {
		this._preservedVerifiable = _preservedVerifiable;
	}

	/**
	 * @return the excludedCharsSet
	 */
	public boolean isExcludedCharsSet() {
		return excludedCharsSet;
	}

	/**
	 * @param excludedCharsSet the excludedCharsSet to set
	 */
	public void setExcludedCharsSet(boolean excludedCharsSet) {
		this.excludedCharsSet = excludedCharsSet;
	}

	/**
	 * @return the errorInfoMap
	 */
	public LinkedHashMap<String, ErrorDetails> getErrorInfoMap() {
		return errorInfoMap;
	}

	/**
	 * @param errorInfoMap the errorInfoMap to set
	 */
	public void setErrorInfoMap(LinkedHashMap<String, ErrorDetails> errorInfoMap) {
		this.errorInfoMap = errorInfoMap;
	}
}