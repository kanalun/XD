package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设定一个路径,直接访问方法
 * 
 * TODO 该功能还未实现
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Path
{
	String[] value();
}
