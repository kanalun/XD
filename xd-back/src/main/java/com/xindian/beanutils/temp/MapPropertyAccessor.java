//--------------------------------------------------------------------------
package com.xindian.beanutils.temp;

import java.util.*;

/**
 * Implementation of PropertyAccessor that sets and gets properties by storing
 * and looking up values in Maps.
 * 
 * @author Luke Blanshard (blanshlu@netscape.net)
 * @author Drew Davidson (drew@ognl.org)
 */
public class MapPropertyAccessor implements PropertyAccessor
{
	public Object getProperty(Map context, Object target, Object name) throws OgnlException
	{
		Object result;
		Map map = (Map) target;
		Node currentNode = ((OgnlContext) context).getCurrentNode().jjtGetParent();
		boolean indexedAccess = false;

		if (currentNode == null)
		{
			throw new OgnlException("node is null for '" + name + "'");
		}
		if (!(currentNode instanceof ASTProperty))
		{
			currentNode = currentNode.jjtGetParent();
		}
		if (currentNode instanceof ASTProperty)
		{
			indexedAccess = ((ASTProperty) currentNode).isIndexedAccess();
		}
		if ((name instanceof String) && !indexedAccess)
		{
			if (name.equals("size"))
			{
				result = new Integer(map.size());
			} else
			{
				if (name.equals("keys"))
				{
					result = map.keySet();
				} else
				{
					if (name.equals("values"))
					{
						result = map.values();
					} else
					{
						if (name.equals("isEmpty"))
						{
							result = map.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
						} else
						{
							result = map.get(name);
						}
					}
				}
			}
		} else
		{
			result = map.get(name);
		}
		return result;
	}

	public void setProperty(Map context, Object target, Object name, Object value) throws OgnlException
	{
		Map map = (Map) target;
		map.put(name, value);
	}
}
