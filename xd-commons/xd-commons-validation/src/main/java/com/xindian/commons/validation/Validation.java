package com.xindian.commons.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xindian.commons.validation.validators.FixedLengthValidator;
import com.xindian.commons.validation.validators.MaxLengthValidator;
import com.xindian.commons.validation.validators.MinLengthValidator;
import com.xindian.commons.validation.validators.NotEmptyValidator;
import com.xindian.commons.validation.validators.NotNullValidator;

/**
 * 包含了基本的验证器
 * 
 * @author Elva
 * @date 2011-2-7
 * @version 1.0
 */
public interface Validation
{
	/**
	 * 固定长度的可用:String,Array,File,Collection(List,Map,Set...)
	 * 
	 * 以及拥有length/getSize()
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Constraint(validatedBy = FixedLengthValidator.class)
	public @interface FixedLength
	{
		long value();

		String action() default "";// 如果不符合规则,如何操作,ftl://fx.ftl

		Class<? extends ValidationHandler>[] handler() default ValidationHandler.class;// 用来处理

		String message() default "";
	}

	/**
	 * 对于String[],Collection<String>等的元素进行长度验证
	 * 
	 * @author Elva
	 * @date 2011-2-10
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ElementFixedLength
	{
		long value();

		/**
		 * 需要被验证的index,如果为负数表示验证每一个元素
		 * 
		 * @return
		 */
		int[] index() default -1;

		String action() default "";// 如果不符合规则,如何操作,ftl://fx.ftl

		Class<? extends ValidationHandler>[] handler() default ValidationHandler.class;// 用来处理

		String message() default "";
	}

	/**
	 * 最大长度:String,Array,File,Collection(List,Map,Set...)
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Constraint(validatedBy = MaxLengthValidator.class)
	public @interface MaxLength
	{
		/**
		 * 最大长度
		 * 
		 * @return
		 */
		long value();

		/**
		 * 是否关闭区间,默认为是
		 * 
		 * @return
		 */
		boolean closedInterval() default true;

		String action() default "";// 如果不符合规则,如何操作,ftl://fx.ftl

		Class<? extends ValidationHandler>[] handler() default ValidationHandler.class;// 用来处理

		String message() default "";
	}

	/**
	 * 最小长度:String,Array,File,Collection(List,Map,Set...)
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Constraint(validatedBy = MinLengthValidator.class)
	public static @interface MinLength
	{
		long value();

		boolean closedInterval() default true;// 是否关闭区间 使用:>/>=

		String action() default "";// 如果不符合规则,如何操作,ftl://fx.ftl

		Class<? extends ValidationHandler>[] handler() default ValidationHandler.class;// 用来处理

		String message() default "";
	}

	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Constraint(validatedBy = NotNullValidator.class)
	public @interface Required// NotNull
	{
	}

	/**
	 * 不为空:可用于任何类型的验证
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = NotNullValidator.class)
	@Documented
	public @interface NotNull
	{
	}

	/** 检查不为空:1,String长度大于零;2,数组类型有0个以上元素,3,Coll有0个以上的元素 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Constraint(validatedBy = NotEmptyValidator.class)
	public @interface NotEmpty
	{
		boolean trim() default false;
	}

	/**
	 * 数据注入的时候如果无法进行类型转换
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ConversionErrorValidator// 验证数据类型是否被正确转换
	{
	}

	/**
	 * 自定义的验证器
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Deprecated
	public @interface CustomValidator
	{
		Class<? extends Validator> validator();// 验证器
	}

	/**
	 * 正则表达式验证器,用于字符串类型
	 * 
	 * @author Elva
	 * @date 2011-2-7
	 * @version 1.0
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Constraint(validatedBy = com.xindian.commons.validation.validators.RegexValidator.class)
	public @interface RegexValidator
	{
		String[] patterns();//

		OperatorType operator() default OperatorType.AND;// 连接符,OR,AND
	}

	public static enum OperatorType
	{
		AND, OR
	}
}
