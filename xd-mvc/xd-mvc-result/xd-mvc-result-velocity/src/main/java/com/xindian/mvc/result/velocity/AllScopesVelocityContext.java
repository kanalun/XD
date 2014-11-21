package com.xindian.mvc.result.velocity;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.AbstractContext;

/**
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class AllScopesVelocityContext extends VelocityContext
{
	// Object action;

	AbstractContext[] chainedContexts;

	public AllScopesVelocityContext(/* Object action, */AbstractContext[] chainedContexts)
	{
		/* this.action = action; */
		this.chainedContexts = chainedContexts;
	}

	public boolean internalContainsKey(Object key)
	{
		boolean contains = super.internalContainsKey(key);
		// first let's check to see if we contain the requested key
		if (contains)
		{
			return true;
		}
		//
		/**
		 * <code>if (action != null && key instanceof String)
		{
			Object object = null;
			try
			{
				// TODO USE GETTER/SETTER To Access The Filed
				Field field = action.getClass().getDeclaredField((String) key);
				field.setAccessible(true);
				object = field.get(action);
				if (object != null)
				{
					return true;
				}
			} catch (SecurityException e)
			{
				// e.printStackTrace();
			} catch (NoSuchFieldException e)
			{
				// DO_NOTING
			} catch (IllegalAccessException e)
			{
				// e.printStackTrace();
			}
		}</code>
		 */
		// if we still haven't found it, le's search through our chained
		// contexts
		if (chainedContexts != null)
		{
			for (int index = 0; index < chainedContexts.length; index++)
			{
				if (chainedContexts[index].containsKey(key))
				{
					return true;
				}
			}
		}

		// nope, i guess it's really not here
		return false;
	}

	public Object internalGet(String key)
	{
		// first, let's check to see if have the requested value
		if (super.internalContainsKey(key))
		{
			return super.internalGet(key);
		}
		// still no luck? let's look against the action
		/**
		 * <code>
		if (action != null && key instanceof String)
		{
			Object object = null;
			try
			{
				// TODO USE GETTER/SETTER To Access The Filed
				Field field = action.getClass().getDeclaredField((String) key);
				field.setAccessible(true);
				object = field.get(action);
				if (object != null)
				{
					return object;
				}
			} catch (SecurityException e)
			{
				// e.printStackTrace();
			} catch (NoSuchFieldException e)
			{
				// DO_NOTING
			} catch (IllegalAccessException e)
			{
				// e.printStackTrace();
			}
		}</code>
		 */
		// finally, if we're chained to other contexts, let's look in them
		if (chainedContexts != null)
		{
			for (int index = 0; index < chainedContexts.length; index++)
			{
				if (chainedContexts[index].containsKey(key))
				{
					return chainedContexts[index].internalGet(key);
				}
			}
		}
		// nope, i guess it's really not here
		return null;
	}

}
