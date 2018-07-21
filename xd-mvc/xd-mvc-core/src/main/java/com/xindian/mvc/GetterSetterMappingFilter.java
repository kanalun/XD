package com.xindian.mvc;

import com.xindian.mvc.exception.ErrorCodeException;

/**
 * 防止GetSet方法被调用
 * 
 * @author Elva
 */
public class GetterSetterMappingFilter implements MappingFilter
{
	@Override
	public boolean filter(Mapping mapping) throws ErrorCodeException
	{
		if (mapping == null)
			return true;
		if (mapping.getMethodName() != null && mapping.getMethodName().trim().startsWith("set"))
		{
			throw new ErrorCodeException(404,
					"U can not use Setter(which is start as 'set') as an Action method..");
		}
		if (mapping.getMethodName() != null
				&& (mapping.getMethodName().trim().startsWith("get") || mapping.getMethodName()
						.trim().startsWith("is")))
		{
			throw new ErrorCodeException(404, "不能使用get/is开头的方法做为Action的方法!!");
		}
		return true;
	}
}
