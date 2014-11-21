package com.xindian.beanutils;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.test.User;

/**
 * 
 * @author Elva
 * @date 2011-3-16
 * @version 1.0
 */
public class DefaultProperty implements Property
{
	private static Logger logger = LoggerFactory.getLogger(DefaultProperty.class);

	private Class<?> type;

	private Class<?> stopClass;

	private Field field;

	private Boolean isField = null;

	private PropertyDescriptor propertyDescriptor;

	private static final AnnotationPosition[] DEFAULT_ANNOTATION_AT = new AnnotationPosition[] { AnnotationPosition.ON_FIELD,
			AnnotationPosition.ON_WRITE_METHOD };

	@Override
	public Field getField()
	{
		if (field == null && isField == null)
		{
			String pName = propertyDescriptor.getName();
			try
			{
				return BeanUtilsTest.getField(type, pName);
			} catch (SecurityException e)
			{
				e.printStackTrace();
			}
		}
		return field;
	}

	@Override
	public Method getReadMethod()
	{
		return propertyDescriptor.getReadMethod();
	}

	@Override
	public Method getWriteMethod()
	{
		return propertyDescriptor.getWriteMethod();
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass, AnnotationPosition[] annotationAts)
	{
		for (AnnotationPosition at : annotationAts)
		{
			if (at == AnnotationPosition.ON_FIELD)
			{
				Field fd = getField();
				if (fd != null)
				{
					if (fd.isAnnotationPresent(annotationClass))
					{
						return true;
					}
				}
				continue;
			}
			if (at == AnnotationPosition.ON_WRITE_METHOD)
			{
				Method writeMethod = getWriteMethod();
				if (writeMethod != null)
				{
					if (writeMethod.isAnnotationPresent(annotationClass))
					{
						return true;
					}
				}
			}
			if (at == AnnotationPosition.ON_READ_METHOD)
			{
				Method readMethod = getReadMethod();
				if (readMethod != null)
				{
					if (readMethod.isAnnotationPresent(annotationClass))
					{
						return true;
					}
				}
			}
			if (at == AnnotationPosition.ON_INDEX_READ_METHOD)
			{
				if (propertyDescriptor instanceof IndexedPropertyDescriptor)
				{
					Method indexReadMethod = ((IndexedPropertyDescriptor) propertyDescriptor).getIndexedReadMethod();
					if (indexReadMethod != null)
					{
						if (indexReadMethod.isAnnotationPresent(annotationClass))
						{
							return true;
						}
					}

				}
				continue;
			}
			if (at == AnnotationPosition.ON_INDEX_WRITE_METHOD)
			{
				if (propertyDescriptor instanceof IndexedPropertyDescriptor)
				{
					Method indexWriteMethod = ((IndexedPropertyDescriptor) propertyDescriptor).getIndexedWriteMethod();
					if (indexWriteMethod != null)
					{
						if (indexWriteMethod.isAnnotationPresent(annotationClass))
						{
							return true;
						}
					}
				}
				continue;
			}
		}
		return false;
	}

	public Annotation[] getAnnotations(AnnotationPosition[] ats)
	{
		ArrayList<Annotation> ass = new ArrayList<Annotation>();

		for (AnnotationPosition at : ats)
		{
			if (at == AnnotationPosition.ON_FIELD)
			{
				Field fd = getField();
				if (fd != null)
				{
					Annotation[] f = fd.getAnnotations();
					for (Annotation a : f)
					{
						ass.add(a);
					}
				}
				continue;
			}
			if (at == AnnotationPosition.ON_WRITE_METHOD)
			{
				Method writeMethod = getWriteMethod();
				if (writeMethod != null)
				{
					Annotation[] f = writeMethod.getAnnotations();
					for (Annotation a : f)
					{
						ass.add(a);
					}
				}
			}
			if (at == AnnotationPosition.ON_READ_METHOD)
			{
				Method readMethod = getReadMethod();
				if (readMethod != null)
				{
					Annotation[] f = readMethod.getAnnotations();
					for (Annotation a : f)
					{
						ass.add(a);
					}
				}
			}
			if (at == AnnotationPosition.ON_INDEX_READ_METHOD)
			{
				if (propertyDescriptor instanceof IndexedPropertyDescriptor)
				{
					Method indexReadMethod = ((IndexedPropertyDescriptor) propertyDescriptor).getIndexedReadMethod();
					if (indexReadMethod != null)
					{
						Annotation[] f = indexReadMethod.getAnnotations();
						for (Annotation a : f)
						{
							ass.add(a);
						}
					}

				}
				continue;
			}
			if (at == AnnotationPosition.ON_INDEX_WRITE_METHOD)
			{
				if (propertyDescriptor instanceof IndexedPropertyDescriptor)
				{
					Method indexWriteMethod = ((IndexedPropertyDescriptor) propertyDescriptor).getIndexedWriteMethod();
					if (indexWriteMethod != null)
					{
						Annotation[] f = indexWriteMethod.getAnnotations();
						for (Annotation a : f)
						{
							ass.add(a);
						}
					}

				}
				continue;
			}
		}
		return ass.toArray(new Annotation[0]);
	}

	@Override
	public <A extends Annotation> A getReadMethodAnnotation(Class<A> annotationClass)
	{
		Method readMethod = getReadMethod();
		if (readMethod != null)
		{
			return readMethod.getAnnotation(annotationClass);
		}
		return null;
	}

	@Override
	public <A extends Annotation> A getWriteMethodAnnotation(Class<A> annotationClass)
	{
		Method writeMethod = getWriteMethod();
		if (writeMethod != null)
		{
			return writeMethod.getAnnotation(annotationClass);
		}
		return null;
	}

	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass)
	{
		return getAnnotation(annotationClass, DEFAULT_ANNOTATION_AT);
	}

	public <A extends Annotation> A getAnnotation(Class<A> annotationClass, AnnotationPosition[] as)
	{
		return null;
	}

	@Override
	public Setter getSetter() throws NoSetterException
	{
		return null;
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass)
	{
		return false;
	}

	@Override
	public String getName()
	{
		return propertyDescriptor.getName();
	}

	@Override
	public boolean isFieldProperty()
	{
		if (isField == null)
		{
			field = getField();
			if (field != null)
			{
				isField = true;
			} else
			{
				isField = false;
			}
		}
		return isField;
	}

	public DefaultProperty(PropertyDescriptor propertyDescriptor, Class<?> type, Class<?> stopClass)
	{
		this.type = type;
		this.propertyDescriptor = propertyDescriptor;
		this.stopClass = stopClass;
	}

	public static void main(String args[]) throws IntrospectionException
	{
		User bean = new User();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
		logger.debug("beanInfo" + beanInfo.getClass());
		PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();
		DefaultProperty defaultProperty = new DefaultProperty(ps[0], bean.getClass(), Object.class);
		logger.debug("beanInfo"
				+ defaultProperty.getAnnotations(new AnnotationPosition[] { AnnotationPosition.ON_FIELD,
						AnnotationPosition.ON_WRITE_METHOD }).length);
	}
}
