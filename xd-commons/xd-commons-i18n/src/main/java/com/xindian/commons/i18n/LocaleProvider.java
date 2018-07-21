package com.xindian.commons.i18n;

import java.util.Locale;

/**
 * 向系统提供自定义Locale信息
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public interface LocaleProvider
{
	/**
	 * 返回本地信息,如果没有返回
	 * 
	 * @return
	 */
	Locale getLocale();
}
