package com.xindian.commons.beanutils;

import java.util.Map;

public interface PropertyAccessor
{
	/**
	 * Extracts and returns the property of the given name from the given target object.
	 * 
	 * @param target
	 *            the object to get the property from
	 * @param name
	 *            the name of the property to get
	 * @return the current value of the given property in the given object
	 * @exception OgnlException
	 *                if there is an error locating the property in the given object
	 */
	Object getProperty(Map context, Object target, Object name);

	/**
	 * Sets the value of the property of the given name in the given target object.
	 * 
	 * @param target
	 *            the object to set the property in
	 * @param name
	 *            the name of the property to set
	 * @param value
	 *            the new value for the property
	 * @exception OgnlException
	 *                if there is an error setting the property in the given object
	 */
	void setProperty(Map context, Object target, Object name, Object value);
}
