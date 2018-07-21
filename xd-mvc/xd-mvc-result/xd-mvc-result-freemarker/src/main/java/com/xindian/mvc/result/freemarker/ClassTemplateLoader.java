package com.xindian.mvc.result.freemarker;

import java.net.URL;

import com.xindian.commons.utils.ClassLoaderUtils;

import freemarker.cache.URLTemplateLoader;

/**
 * Use Classload path such as "com/kan/mvc/template/t.ftl" to Load Template
 * 
 * @date 2011-1-16
 * @version 1.0
 */
public class ClassTemplateLoader extends URLTemplateLoader
{
	protected URL getURL(String name)
	{
		return ClassLoaderUtils.getResource(name, getClass());
	}
}
