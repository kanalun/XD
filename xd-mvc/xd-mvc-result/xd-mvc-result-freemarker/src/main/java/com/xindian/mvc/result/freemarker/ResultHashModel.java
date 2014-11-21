package com.xindian.mvc.result.freemarker;

import java.util.Map;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author Elva
 * @date 2011-1-27
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResultHashModel extends SimpleHash
{
	/**
	 * 
	 * @param map
	 * @param wrapper
	 */
	public ResultHashModel(Map<String, Object> map, ObjectWrapper wrapper)
	{
		super(map, wrapper);
	}

	/**
	 * 
	 * @param map
	 */
	public ResultHashModel(Map<String, Object> map)
	{
		super(map);
	}

	public ResultHashModel()
	{
		// DO_NOTHING
	}

	@Override
	public TemplateModel get(String key) throws TemplateModelException
	{
		return super.get(key);
	}
}
