package com.xindian.commons.i18n;

import java.util.List;

/**
 * TODO 为资源添加名字空间
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public interface TextProvider
{
	String getText(String key);

	String getText(String key, List args);

	String getText(String key, Object... args);

	String getText(String key, String defaultValue);

	String getText(String key, String defaultValue, List args);

	String getText(String key, String defaultValue, Object... args);
}
