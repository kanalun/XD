package com.xindian.commons.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SystemOutValidationHandler implements ValidationHandler
{
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String name, Class<T> valueType, Annotation annotation)
	{
		try
		{
			return (T) annotation.getClass().getMethod(name).invoke(annotation, new Object[0]);

		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void handleError(Object bean, Field field, Annotation ant, String message)
	{
		System.out.print("Bean:[" + bean.getClass().getSimpleName() + "] Field:[" + field.getName() + "] Message:[" + message
				+ "]	Ant[" + getValue("value", Long.class, ant) + "]");
		/*
		 * try {
		 * 
		 * System.out.print("Bean:[" + bean.getClass().getSimpleName() +
		 * "] Field:[" + field.getName() + "] Message:[" + message + "]	Ant[" +
		 * ant.getClass().getMethod("value").invoke(ant, null) + "]"); } catch
		 * (SecurityException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (NoSuchMethodException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (IllegalArgumentException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (InvocationTargetException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}
}
