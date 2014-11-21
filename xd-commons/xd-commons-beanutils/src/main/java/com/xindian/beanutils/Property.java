package com.xindian.beanutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface Property
{
	/**
	 * 注解的位置
	 * 
	 * @author Elva
	 * @date 2011-3-16
	 * @version 1.0
	 */
	public static enum AnnotationPosition
	{
		ON_FIELD, ON_WRITE_METHOD, ON_READ_METHOD, ON_INDEX_READ_METHOD, ON_INDEX_WRITE_METHOD
	}

	/**
	 * @return
	 */
	public String getName();

	/**
	 * @return
	 */
	public boolean isFieldProperty();

	/**
	 * 得到该属性的Field
	 * 
	 * @return 返回该属性的Field,否则返回null
	 */
	public Field getField();

	/**
	 * @param <A>
	 * @return
	 */
	public Method getWriteMethod();

	/**
	 * @param <A>
	 * @return
	 */
	public Method getReadMethod();

	/**
	 * 从Field/ReadMethod/WriteMethod获得注解,顺序是
	 * 
	 * @param <A>
	 * @return
	 */
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass);

	/**
	 * @param <A>
	 * @return
	 */
	public <A extends Annotation> A getReadMethodAnnotation(Class<A> annotationClass);

	/**
	 * @param <A>
	 * @return
	 */
	public <A extends Annotation> A getWriteMethodAnnotation(Class<A> annotationClass);

	/**
	 * @return
	 */
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);

	/**
	 * @return
	 */
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass, AnnotationPosition[] annotationAts);

	/**
	 * 获得该属性的setter方法,如果没有setter
	 * 
	 * @return
	 */
	public Setter getSetter() throws NoSetterException;

}
