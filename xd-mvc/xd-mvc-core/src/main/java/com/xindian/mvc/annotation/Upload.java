package com.xindian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xindian.mvc.utils.FileItemSaver;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Upload
{
	long maxSize() default 1024 * 10;// 这个文件最大

	String[] contentTypes() default {};// 被接受的contentTypes

	String tempDir() default "";//

	boolean cleanUp() default false;// 是否自动清理该文件在tmp

	Class<? extends FileItemSaver> saver() default FileItemSaver.class;
}
