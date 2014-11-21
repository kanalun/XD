package com.xindian.mvc.result.freemarker;

import java.lang.reflect.Field;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * SimpleHash wrap for the Action object
 * 
 * 这个代码已经被废弃了 使用 ActionHashModel
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
@Deprecated
@SuppressWarnings("serial")
public class ActionHashModel3 extends SimpleHash
{
	private final Object action;

	public ActionHashModel3(ObjectWrapper wrapper, Object action)
	{
		super(wrapper);
		this.action = action;
	}

	public TemplateModel get(String key) throws TemplateModelException
	{
		// Lookup in default scope
		TemplateModel model = super.get(key);
		if (model != null)
		{
			return model;
		}
		Object object = null;
		try
		{
			// TODO USE GETTER/SETTER To Access The Filed
			Field field = action.getClass().getDeclaredField(key);
			field.setAccessible(true);
			object = field.get(action);
		} catch (SecurityException e)
		{
			// e.printStackTrace();
			throw new TemplateModelException(e);
		} catch (NoSuchFieldException e)
		{
			// DO_NOTING
		} catch (IllegalAccessException e)
		{
			// e.printStackTrace();
			throw new TemplateModelException(e);
		}
		return wrap(object);
	}
}
