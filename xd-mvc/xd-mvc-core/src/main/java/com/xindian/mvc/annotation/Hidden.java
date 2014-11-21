package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将Action隐藏
 * 
 * @author Elva
 * @date 2011-3-3
 * @version 1.0
 */
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Hidden
{

}
