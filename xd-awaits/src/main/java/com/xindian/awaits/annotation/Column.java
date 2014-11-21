package com.xindian.awaits.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Elva
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column
{
	String name();

	/** 是否允许为空,默认为允许(true) */
	boolean nullable() default true;

	/** 是否可更新 */
	boolean updateable() default true;

	/** 更新备注:备注字段,生成文档时可以 */
	String updateComment() default "";

	/** 唯一,表示该字段要求唯一 */
	boolean unique() default false;

	/** 是否可以插入 */
	boolean insertable() default true;

	/** 默认长度 */
	int length() default 255;

	/** 自定义 */
	String columnDefinition() default "";

	// Class<?> type() default Object.class;

	boolean secondaryTable() default false;

	int precision() default 0;

	int scale() default 0;

	/** 数据库备注 */
	String comment() default "";
}
