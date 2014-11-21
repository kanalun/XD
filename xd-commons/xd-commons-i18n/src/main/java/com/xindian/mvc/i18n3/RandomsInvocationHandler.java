package com.xindian.mvc.i18n3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.xindian.mvc.i18n3.Randoms.RandomFixedLengthString;
import com.xindian.mvc.i18n3.Randoms.RandomInt;
import com.xindian.mvc.i18n3.Randoms.RandomString;
import com.xindian.mvc.utils.RandomUtils;

/**
 * 随机方法
 * 
 * @author Elva
 * @date 2011-2-20
 * @version 1.0
 */
public class RandomsInvocationHandler implements InvocationHandler
{
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		@SuppressWarnings("rawtypes")
		Class returnType = method.getReturnType();
		if (returnType.equals(Integer.class) || returnType.equals(Integer.TYPE))
		{
			if (method.isAnnotationPresent(RandomInt.class))
			{
				RandomInt randomInt = method.getAnnotation(RandomInt.class);
				return RandomUtils.randomInt(randomInt.min(), randomInt.max());
			} else
			{
				return RandomUtils.randomInt();
			}
		} else if (returnType.equals(String.class))
		{
			if (method.isAnnotationPresent(RandomFixedLengthString.class))
			{
				RandomFixedLengthString randomString = method.getAnnotation(RandomFixedLengthString.class);
				if (randomString.length() > 0)
				{
					return RandomUtils.randomString2(randomString.length());

				} else
				{
					return RandomUtils.randomString();
				}

			} else if (method.isAnnotationPresent(RandomString.class))
			{
				RandomString randomString = method.getAnnotation(RandomString.class);
				return RandomUtils.randomString(randomString.minLenght(), randomString.maxLength());
			}
		}
		return null;
	}

	public static <T> T newInstance(Class<T> type)
	{
		return newProxyInstance(type, new RandomsInvocationHandler());
	}

	protected static <T> T newProxyInstance(Class<T> type, InvocationHandler handler)
	{
		return type.cast(Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class<?>[] { type }, handler));
	}

}
