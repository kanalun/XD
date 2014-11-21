package com.xindian.mvc.convention;

/**
 * @author Elva
 * @date 2011-3-6
 * @version 1.0
 */
public interface ActionConventionFilter
{
	/**
	 * 过滤
	 * 
	 * @return 返回true表示不过滤,返回false表示过滤
	 */
	boolean filter(Class<?> type);
}
