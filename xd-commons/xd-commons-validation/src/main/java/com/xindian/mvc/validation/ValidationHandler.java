package com.xindian.mvc.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public interface ValidationHandler
{
	public void handleError(Object bean, Field field, Annotation annotation, String message);
}
