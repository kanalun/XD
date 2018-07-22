package com.xindian.commons.i18n;

import java.io.Serializable;
import java.util.Locale;

/**
 * 向系统提供自定义Locale信息
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 */
public interface LocaleProvider extends Serializable
{
	/**
	 * 返回本地信息,如果没有返回
	 * 
	 * @return
	 */
	Locale getLocale();
}
