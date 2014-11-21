package com.xindian.beanutils;

import java.util.Map;

/**
 * This interface defines methods for setting and getting a property from a
 * target object. A "property" in this case is a named data value that takes the
 * generic form of an Object---the same definition as is used by beans. But the
 * operational semantics of the term will vary by implementation of this
 * interface: a bean-style implementation will get and set properties as beans
 * do, by reflection on the target object's class, but other implementations are
 * possible, such as one that uses the property name as a key into a map.
 * 
 * <p>
 * An implementation of this interface will often require that its target
 * objects all be of some particular type. For example, the MapPropertyAccessor
 * class requires that its targets all implement the java.util.Map interface.
 * 
 * <p>
 * Note that the "name" of a property is represented by a generic Object. Some
 * implementations may require properties' names to be Strings, while others may
 * allow them to be other types---for example, ArrayPropertyAccessor treats
 * Number names as indexes into the target object, which must be an array.
 * 
 * @author Luke Blanshard (blanshlu@netscape.net)
 * @author Drew Davidson (drew@ognl.org)
 */
public interface PropertyAccessor
{
	/**
	 * Extracts and returns the property of the given name from the given target
	 * object.
	 * 
	 * @param target
	 *            the object to get the property from
	 * @param name
	 *            the name of the property to get
	 * @return the current value of the given property in the given object
	 * @exception OgnlException
	 *                if there is an error locating the property in the given
	 *                object
	 */
	Object getProperty(Map context, Object target, Object name);

	/**
	 * Sets the value of the property of the given name in the given target
	 * object.
	 * 
	 * @param target
	 *            the object to set the property in
	 * @param name
	 *            the name of the property to set
	 * @param value
	 *            the new value for the property
	 * @exception OgnlException
	 *                if there is an error setting the property in the given
	 *                object
	 */
	void setProperty(Map context, Object target, Object name, Object value);
}
