package com.xindian.beanutils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Sets values to a particular property.
 */
public interface Setter extends Serializable
{
	/**
	 * Set the property value from the given instance
	 * 
	 * @param target
	 *            The instance upon which to set the given value.
	 * @param value
	 *            The value to be set on the target.
	 */
	public void set(Object target, Object value);

	/**
	 * Optional operation (return null)
	 */
	public Method getMethod();
}
