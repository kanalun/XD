package com.xindian.beanutils;

import java.util.Map;
import java.util.Set;

/**
 * Implementation of PropertyAccessor that uses numbers and dynamic subscripts
 * as properties to index into Lists.
 */
public class SetPropertyAccessor extends ObjectPropertyAccessor implements PropertyAccessor
{
	public Object getProperty(Map context, Object target, Object name)
	{
		Set set = (Set) target;

		if (name instanceof String)
		{
			Object result;

			if (name.equals("size"))
			{
				result = new Integer(set.size());
			} else
			{
				if (name.equals("iterator"))
				{
					result = set.iterator();
				} else
				{
					if (name.equals("isEmpty"))
					{
						result = set.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
					} else
					{
						result = super.getProperty(context, target, name);
					}
				}
			}
			return result;
		}

		throw new NoSuchPropertyException(target, name);
	}
}
