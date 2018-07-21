package com.xindian.commons.utils;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * 基于Javassist的方法参数名称获得工具
 * 
 * @author Elva
 * @date 2011-5-22
 * @version 1.0
 */
public abstract class MethodUtils
{
	/**
	 * 获取方法参数名称，按给定的参数类型匹配方法
	 * 
	 * TODO 超类
	 * 
	 * @param clazz
	 * @param method
	 * @param paramTypes
	 * @return
	 * @throws NotFoundException
	 *             如果类或者方法不存在
	 * @throws MissingLVException
	 *             如果最终编译的class文件不包含局部变量表信息
	 */
	public static String[] getMethodParamNames(Class<?> clazz, String method,
			Class<?>... paramTypes) throws NotFoundException, MissingLVException
	{
		ClassPool pool = ClassPool.getDefault();
		CtClass cc;
		try
		{
			cc = pool.get(clazz.getName());
		} catch (Exception e)
		{
			e.printStackTrace();
			pool.insertClassPath(new ClassClassPath(clazz));
			cc = pool.get(clazz.getName());
		}
		String[] paramTypeNames = new String[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++)
		{
			paramTypeNames[i] = paramTypes[i].getName();
			// TODO
			// pool.insertClassPath(new ClassClassPath(paramTypes[i]));
		}
		CtMethod cm = cc.getDeclaredMethod(method, pool.get(paramTypeNames));
		return getMethodParamNames(cm);
	}

	/**
	 * 获取方法参数名称，匹配同名的某一个方法
	 * 
	 * @param clazz
	 * @param method
	 * @return
	 * @throws NotFoundException
	 *             如果类或者方法不存在
	 * @throws MissingLVException
	 *             如果最终编译的class文件不包含局部变量表信息
	 */
	public static String[] getMethodParamNames(Class<?> clazz, String method)
			throws NotFoundException, MissingLVException
	{
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get(clazz.getName());
		CtMethod cm = cc.getDeclaredMethod(method);
		return getMethodParamNames(cm);
	}

	/**
	 * 获取方法参数名称
	 * 
	 * @param cm
	 * @return
	 * @throws NotFoundException
	 * @throws MissingLVException
	 *             如果最终编译的class文件不包含局部变量表信息
	 */
	protected static String[] getMethodParamNames(CtMethod cm) throws NotFoundException,
			MissingLVException
	{
		CtClass cc = cm.getDeclaringClass();
		MethodInfo methodInfo = cm.getMethodInfo();

		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
				.getAttribute(LocalVariableAttribute.tag);
		if (attr == null)
		{
			throw new MissingLVException(cc.getName());
		}
		String[] paramNames = new String[cm.getParameterTypes().length];
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		for (int i = 0; i < paramNames.length; i++)
		{
			paramNames[i] = attr.variableName(i + pos);
		}
		return paramNames;
	}

	/**
	 * 在class中未找到局部变量表信息<br>
	 * 使用编译器选项 javac -g:{vars}来编译源文件
	 */
	public static class MissingLVException extends Exception
	{
		private static final long serialVersionUID = 1L;
		private final static String msg = "class:%s 不包含局部变量表信息，请使用编译器选项 javac -g:{vars}来编译源文件。";

		public MissingLVException(String clazzName)
		{
			super(String.format(msg, clazzName));
		}
	}
}
