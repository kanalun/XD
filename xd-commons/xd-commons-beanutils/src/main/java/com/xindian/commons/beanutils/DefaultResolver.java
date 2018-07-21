package com.xindian.commons.beanutils;

public class DefaultResolver
{
	private static final char NESTED = '.';
	private static final char MAPPED_START = '(';
	private static final char MAPPED_END = ')';
	private static final char INDEXED_START = '[';
	private static final char INDEXED_END = ']';

	/**
	 * Default Constructor.
	 */
	public DefaultResolver()
	{
	}

	/**
	 * Return the index value from the property expression or -1.
	 * 
	 * @param expression
	 *            The property expression
	 * @return The index value or -1 if the property is not indexed
	 * @throws IllegalArgumentException
	 *             If the indexed property is illegally formed or has an invalid (non-numeric) value.
	 */
	public int getIndex(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return -1;
		}
		for (int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if (c == NESTED || c == MAPPED_START)
			{
				return -1;
			} else if (c == INDEXED_START)
			{
				int end = expression.indexOf(INDEXED_END, i);
				if (end < 0)
				{
					throw new IllegalArgumentException("Missing End Delimiter");
				}
				String value = expression.substring(i + 1, end);
				if (value.length() == 0)
				{
					throw new IllegalArgumentException("No Index Value");
				}
				int index = 0;
				try
				{
					index = Integer.parseInt(value, 10);
				} catch (Exception e)
				{
					throw new IllegalArgumentException("Invalid index value '" + value + "'");
				}
				return index;
			}
		}
		return -1;
	}

	/**
	 * Return the map key from the property expression or <code>null</code>.
	 * 
	 * @param expression
	 *            The property expression
	 * @return The index value
	 * @throws IllegalArgumentException
	 *             If the mapped property is illegally formed.
	 */
	public String getKey(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return null;
		}
		for (int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if (c == NESTED || c == INDEXED_START)
			{
				return null;
			} else if (c == MAPPED_START)
			{
				int end = expression.indexOf(MAPPED_END, i);
				if (end < 0)
				{
					throw new IllegalArgumentException("Missing End Delimiter");
				}
				return expression.substring(i + 1, end);
			}
		}
		return null;
	}

	/**
	 * Return the property name from the property expression.
	 * 
	 * @param expression
	 *            The property expression
	 * @return The property name
	 */
	public String getProperty(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return expression;
		}
		for (int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if (c == NESTED)
			{
				return expression.substring(0, i);
			} else if (c == MAPPED_START || c == INDEXED_START)
			{
				return expression.substring(0, i);
			}
		}
		return expression;
	}

	/**
	 * Indicates whether or not the expression contains nested property expressions or not.
	 * 
	 * @param expression
	 *            The property expression
	 * @return The next property expression
	 */
	public boolean hasNested(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return false;
		} else
		{
			return (remove(expression) != null);
		}
	}

	/**
	 * Indicate whether the expression is for an indexed property or not.
	 * 
	 * @param expression
	 *            The property expression
	 * @return <code>true</code> if the expresion is indexed, otherwise <code>false</code>
	 */
	public boolean isIndexed(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return false;
		}
		for (int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if (c == NESTED || c == MAPPED_START)
			{
				return false;
			} else if (c == INDEXED_START)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicate whether the expression is for a mapped property or not.
	 * 
	 * @param expression
	 *            The property expression
	 * @return <code>true</code> if the expresion is mapped, otherwise <code>false</code>
	 */
	public boolean isMapped(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return false;
		}
		for (int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if (c == NESTED || c == INDEXED_START)
			{
				return false;
			} else if (c == MAPPED_START)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Extract the next property expression from the current expression.
	 * 
	 * @param expression
	 *            The property expression
	 * @return The next property expression
	 */
	public String next(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return null;
		}
		boolean indexed = false;
		boolean mapped = false;
		for (int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if (indexed)
			{
				if (c == INDEXED_END)
				{
					return expression.substring(0, i + 1);
				}
			} else if (mapped)
			{
				if (c == MAPPED_END)
				{
					return expression.substring(0, i + 1);
				}
			} else
			{
				if (c == NESTED)
				{
					return expression.substring(0, i);
				} else if (c == MAPPED_START)
				{
					mapped = true;
				} else if (c == INDEXED_START)
				{
					indexed = true;
				}
			}
		}
		return expression;
	}

	/**
	 * Remove the last property expresson from the current expression.
	 * 
	 * @param expression
	 *            The property expression
	 * @return The new expression value, with first property expression removed - null if there are no more expressions
	 */
	public String remove(String expression)
	{
		if (expression == null || expression.length() == 0)
		{
			return null;
		}
		String property = next(expression);
		if (expression.length() == property.length())
		{
			return null;
		}
		int start = property.length();
		if (expression.charAt(start) == NESTED)
		{
			start++;
		}
		return expression.substring(start);
	}

	public static void main(String args[])
	{
		DefaultResolver defaultResolver = new DefaultResolver();
		String s = "a.b.c.d.e.(f(fd))(a)";
		// System.out.println(defaultResolver.hasNested(s));
		// System.out.println(defaultResolver.next(s));
		// defaultResolver.remove(defaultResolver.next(s));
		System.out.println(defaultResolver.next(s));
		String r = defaultResolver.remove(s);
		while (r != null)
		{
			System.out.println(defaultResolver.next(r));
			r = defaultResolver.remove(r);
		}
		// System.out.println(defaultResolver.remove(s));
		// System.out.println(defaultResolver.next(s));
	}
}
