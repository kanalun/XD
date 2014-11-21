package com.xindian.mvc.annotation;

import com.xindian.ioc.annotation.Scope;

/**
 * 可以讲session,application..中的数据注入到Action中去
 * 
 * @author Elva
 * @date 2011-3-9
 * @version 1.0
 */
public @interface ScopedAttribute
{
	Scope scope();
}
