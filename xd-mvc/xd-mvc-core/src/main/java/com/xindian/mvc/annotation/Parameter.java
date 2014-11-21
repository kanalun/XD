package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter
{
	String name() default "";

	String defaultValue() default "";

	String getter() default "";

	String setter() default "";

	String[] format() default "";

	String defaultLocale() default "";

	String defaultTimeZone() default "";

	Origin[] origin() default Origin.PARAMETER;
}
