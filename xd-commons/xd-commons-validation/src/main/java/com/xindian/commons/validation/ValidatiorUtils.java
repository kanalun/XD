package com.xindian.commons.validation;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import com.xindian.commons.validation.Tools.Email;
import com.xindian.commons.validation.Tools.ISBN;
import com.xindian.commons.validation.Validation.FixedLength;
import com.xindian.commons.validation.Validation.MaxLength;
import com.xindian.commons.validation.Validation.MinLength;
import com.xindian.commons.validation.Validation.NotEmpty;
import com.xindian.commons.validation.Validation.NotNull;
import com.xindian.commons.validation.Validation.OperatorType;
import com.xindian.commons.validation.Validation.RegexValidator;
import com.xindian.commons.validation.Validation.Required;
import com.xindian.commons.validation.validators.FixedLengthValidator;
import com.xindian.commons.validation.validators.MaxLengthValidator;
import com.xindian.commons.validation.validators.MinLengthValidator;
import com.xindian.commons.validation.validators.NotEmptyValidator;
import com.xindian.commons.validation.validators.NotNullValidator;

public class ValidatiorUtils
{
	static class A
	{
		@MinLength(value = 2, action = "", handler = SystemOutValidationHandler.class, message = "输入的值必须>2")
		@MaxLength(value = 4, closedInterval = false)
		public String[] x = new String[] { "123" };

		@MaxLength(value = 2, action = "", handler = SystemOutValidationHandler.class, message = "${值必须是...}")
		@MinLength(value = 10, action = "", handler = SystemOutValidationHandler.class, message = "key://key")
		public String x8;

		@MinLength(value = 10, action = "", handler = SystemOutValidationHandler.class, message = "string://key://text")
		public boolean x89;

		@MinLength(value = 10, action = "", handler = SystemOutValidationHandler.class, message = "text://key://key")
		public boolean x892;

		@NotNull
		@Email
		@NotEmpty(trim = true)
		public String emali = "     ";

		@RegexValidator(patterns = { "^qcc$", "bcc", "acc" }, operator = OperatorType.OR)
		public String rx = "qcc";
	}

	public static void main(String args[])
	{
		validate2(new A());
	}

	public static boolean validate2(Object bean)// 验证bean是否符合注解
	{
		Field field;
		try
		{
			field = bean.getClass().getField("rx");
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations)
			{
				boolean isConstraint = annotation.annotationType().isAnnotationPresent(Constraint.class);
				if (isConstraint)
				{
					Class<? extends Validator> validatorType = annotation.annotationType().getAnnotation(Constraint.class)
							.validatedBy();
					Validator validator = validatorType.newInstance();
					System.out.println("---" + validator);
					if (validatorType != null)
					{
						System.out.println("---" + validator.validate(field.get(bean), annotation));
					}
				}
			}
		} catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * 验证bean的所有属性
	 * 
	 * @param bean
	 * @return
	 */
	public static boolean validate(Object bean)// 验证bean是否符合注解
	{
		try
		{
			ValidatorFactory.addValidator(NotEmpty.class, new NotEmptyValidator());

			ValidatorFactory.addValidator(NotNull.class, new NotNullValidator());

			ValidatorFactory.addValidator(Required.class, new NotNullValidator());

			ValidatorFactory.addValidator(FixedLength.class, new FixedLengthValidator());

			ValidatorFactory.addValidator(MinLength.class, new MinLengthValidator());

			ValidatorFactory.addValidator(MaxLength.class, new MaxLengthValidator());

			ValidatorFactory.addValidator(RegexValidator.class, new com.xindian.commons.validation.validators.RegexValidator());

			Field field = bean.getClass().getField("rx");
			Annotation[] annotations = field.getAnnotations();

			for (Annotation annotation : annotations)
			{
				Validator validator = ValidatorFactory.getValidator(annotation.annotationType());
				if (validator != null)
				{
					System.out.println("---" + validator.validate(field.get(bean), annotation));
				}
			}

			// MinLength minLength = field.getAnnotation(MinLength.class);

			// Annotation annotation = minLength;

			// System.out.println(annotation.annotationType());

			// Class<? extends ValidationHandler> c[] = minLength.handler();

			// String message = minLength.message();

			// c[0].newInstance().handleError(bean, field, minLength, message);

		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}/*
		 * catch (InstantiationException e) { e.printStackTrace(); }
		 */catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public static Map<Field, String> a(Object bean)// 取回非法属性和消息
	{
		File f = null;

		f.length();

		ISBN aIsbn;
		return null;
	}
}
