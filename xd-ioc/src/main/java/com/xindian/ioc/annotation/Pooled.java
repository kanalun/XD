package com.xindian.ioc.annotation;

/**
 * 池化对象,
 * 
 * 需要为池化对象设定一个init 和 destory方法
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
public @interface Pooled
{
	// 最多情况下对象池中有多少个该对象
	public int maxCount();

	public int minCount();

	public boolean usePool() default true;
}
