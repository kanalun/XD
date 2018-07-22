package com.xindian.commons.i18n;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.conversion.ConversionException;
import com.xindian.commons.conversion.ConverterFactory;
import com.xindian.commons.i18n.Constants.DefaultBigDecimalArrayValue;
import com.xindian.commons.i18n.Constants.DefaultBigDecimalValue;
import com.xindian.commons.i18n.Constants.DefaultBigIntegerArrayValue;
import com.xindian.commons.i18n.Constants.DefaultBigIntegerValue;
import com.xindian.commons.i18n.Constants.DefaultBooleanArrayValue;
import com.xindian.commons.i18n.Constants.DefaultBooleanValue;
import com.xindian.commons.i18n.Constants.DefaultByteArrayValue;
import com.xindian.commons.i18n.Constants.DefaultByteValue;
import com.xindian.commons.i18n.Constants.DefaultCharArrayValue;
import com.xindian.commons.i18n.Constants.DefaultCharValue;
import com.xindian.commons.i18n.Constants.DefaultClassArrayValue;
import com.xindian.commons.i18n.Constants.DefaultClassValue;
import com.xindian.commons.i18n.Constants.DefaultDoubleArrayValue;
import com.xindian.commons.i18n.Constants.DefaultDoubleValue;
import com.xindian.commons.i18n.Constants.DefaultEnumArrayValue;
import com.xindian.commons.i18n.Constants.DefaultEnumValue;
import com.xindian.commons.i18n.Constants.DefaultFloatArrayValue;
import com.xindian.commons.i18n.Constants.DefaultFloatValue;
import com.xindian.commons.i18n.Constants.DefaultIntArrayValue;
import com.xindian.commons.i18n.Constants.DefaultIntValue;
import com.xindian.commons.i18n.Constants.DefaultLongArrayValue;
import com.xindian.commons.i18n.Constants.DefaultLongValue;
import com.xindian.commons.i18n.Constants.DefaultShortArrayValue;
import com.xindian.commons.i18n.Constants.DefaultShortValue;
import com.xindian.commons.i18n.Constants.DefaultStringArrayValue;
import com.xindian.commons.i18n.Constants.DefaultStringMapValue;
import com.xindian.commons.i18n.Constants.DefaultStringValue;
import com.xindian.commons.i18n.LocalizableResource.DefaultLocale;
import com.xindian.commons.i18n.LocalizableResource.Gloabal;
import com.xindian.commons.i18n.LocalizableResource.Key;
import com.xindian.commons.i18n.LocalizableResource.Resource;
import com.xindian.commons.i18n.Messages.DefaultMessage;
import com.xindian.commons.i18n.Messages.EscapeXml;
import com.xindian.commons.utils.LocaleUtils;
import com.xindian.commons.utils.ReadOnlyHashMap;
import com.xindian.commons.utils.SafeHtml;
import com.xindian.commons.utils.TypeUtils;

/**
 * 对于每一个常量,该类只有一个对象
 * 
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 * @param <T>
 */
public class ConstantsInvocationHandler<T extends Constants> implements InvocationHandler
{
	private static Logger logger = LoggerFactory
			.getLogger(ConstantsInvocationHandler.class);

	private static final Map<String, Object> EMPTY_CONTEXT = /* new ReadOnlyHashMap <String, Object>(); */
	Collections.unmodifiableMap(new HashMap<String, Object>(0));

	/** ResourceProvider为常量提供资源支持 */
	private static ResourceProvider resourceProvider = DefaultResourceProvider.INSTANCE;

	private TextProvider textProvider = DefaultTextProvider.INSTANCE;

	/** 常量缓存,默认情况下对没有参数的方法做缓存 */
	// private Map<String, Object> constantsCache = new ConcurrentHashMap<String, Object>();

	// private LocaleProvider localeProvider;

	// public LocaleProvider getLocaleProvider()
	// {
	// return localeProvider;
	// }
	//
	// public void setLocaleProvider(LocaleProvider localeProvider)
	// {
	// this.localeProvider = localeProvider;
	// }

	/** 类型 */
	private Class<T> type;

	/** 是否为全局资源/如果是true.其他组件可以通过其他访问到这个资源 */
	private boolean global = false;

	private boolean share;

	/** 资源名称 */
	private String resourceName;

	/** 默认本地信息 */
	private String defaultLocale;

	/** 资源编码 */
	private String resourceEncode;

	public ConstantsInvocationHandler(Class<T> type)
	{
		this.type = type;
		if (type.isAnnotationPresent(DefaultLocale.class))
		{
			DefaultLocale defaultLocale = type.getAnnotation(DefaultLocale.class);
			this.defaultLocale = defaultLocale.value();
		}
		if (type.isAnnotationPresent(Gloabal.class))
		{
			global = true;
		}
		if (type.isAnnotationPresent(Resource.class))
		{
			Resource resource = type.getAnnotation(Resource.class);
			share = resource.share();
			resourceName = resource.name();
			resourceEncode = resource.encoding();
		}
		// if U do not assign a resourceName
		if (resourceName == null || resourceName.trim().length() <= 0)
		{
			// it will be the same as the TypeName(通常是接口的完全限定名称)
			resourceName = type.getName();
		}
		resourceProvider.addResource(resourceName);
		logger.debug("Constants Type:[" + type + "] Resource Name:[" + resourceName + "]");
	}

	private String cacheKey(Object proxy, Method method, Object[] args, Locale locale)
	{
		Class<?> returnType = method.getReturnType();
		String key = returnType + "_" + method + "_" + locale;
		return key;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		logger.debug("__ARGS:" + args);
		// if (args == null || args.length == 0)
		// {
		// String cackeKey = cacheKey(proxy, method, args);
		// }
		Class<?> returnType = method.getReturnType();

		logger.debug("Constants Method:[" + method + "] Return Type:[" + returnType + "]"
				+ "args:[" + args + "]");

		String textKey = null;

		if (method.isAnnotationPresent(Key.class))
		{
			Key key = method.getAnnotation(Key.class);
			textKey = key.value();
		}
		if (textKey == null)
		{
			textKey = method.getName();
		}
		String defaultMessage = null;
		if (method.isAnnotationPresent(DefaultMessage.class))
		{
			defaultMessage = method.getAnnotation(DefaultMessage.class).value();
		}
		String text = textProvider.getText(textKey, defaultMessage, args);// getText

		Object value = null;
		if (text == null)
		{
			value = getDefaultValue(method);
		} else
		{
			try
			{
				// TODO,将格式化参数加入到上下文中,做好缓存
				// convert text to method's returnType
				value = ConverterFactory.convert(EMPTY_CONTEXT, returnType, text);
				// value = ConvertUtils.convert(text, returnType);// convert
				// text to method's returnType
			} catch (ConversionException e)
			{
				e.printStackTrace();// TODO
			}
			// 检查返回值是否兼容:如果不兼容
			// TODO isCompatibleType 并没有任何作用;因为返回类型
			// 没有值,或者值和返回类型不兼容
			if (value == null || !TypeUtils.isCompatibleType(value, returnType))
			{
				value = getDefaultValue(method);
				logger.debug("没有找到合适的资源使用类定义的默认值[" + value + "]");
			}
		}
		if (value == null)
		{
			throw new ConstantsInvocationException("Can not find text key;[" + textKey
					+ "]");
		}
		if (method.isAnnotationPresent(EscapeXml.class) && value instanceof CharSequence
				&& returnType.equals(String.class))
		{
			logger.debug("String EscapeXml");
			value = new SafeHtml((CharSequence) value).toString();
		}
		return value;
	}

	/**
	 * 返回方法的默认值
	 * 
	 * @param method
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object getDefaultValue(Method method)// TODO 抛出异常
	{
		@SuppressWarnings("rawtypes")
		Class returnType = method.getReturnType();
		Object value = null;
		if (returnType.isArray())
		{
			// Array elementType
			@SuppressWarnings("rawtypes")
			Class componentType = returnType.getComponentType();
			if (componentType.equals(String.class))// String Array
			{
				if (method.isAnnotationPresent(DefaultStringArrayValue.class))
				{
					DefaultStringArrayValue defaultValue = method
							.getAnnotation(DefaultStringArrayValue.class);
					value = defaultValue.value();
				}
			}
			// Int Array
			else if (componentType.equals(Integer.class)
					|| componentType.equals(Integer.TYPE))
			{
				if (method.isAnnotationPresent(DefaultIntArrayValue.class))
				{
					DefaultIntArrayValue defaultValue = method
							.getAnnotation(DefaultIntArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Double.class)
					|| componentType.equals(Double.TYPE))// Double
															// Array
			{
				if (method.isAnnotationPresent(DefaultDoubleArrayValue.class))
				{
					DefaultDoubleArrayValue defaultValue = method
							.getAnnotation(DefaultDoubleArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Long.class)
					|| componentType.equals(Long.TYPE))// Long
														// Array
			{
				if (method.isAnnotationPresent(DefaultLongArrayValue.class))
				{
					DefaultLongArrayValue defaultValue = method
							.getAnnotation(DefaultLongArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Boolean.class)
					|| componentType.equals(Boolean.TYPE))// Boolean
															// Array
			{
				if (method.isAnnotationPresent(DefaultBooleanArrayValue.class))
				{
					DefaultBooleanArrayValue defaultValue = method
							.getAnnotation(DefaultBooleanArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Float.class)
					|| componentType.equals(Float.TYPE))// Float
														// Array
			{
				if (method.isAnnotationPresent(DefaultFloatArrayValue.class))
				{
					DefaultFloatArrayValue defaultValue = method
							.getAnnotation(DefaultFloatArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Character.class)
					|| componentType.equals(Character.TYPE))// Character
															// Array
			{
				if (method.isAnnotationPresent(DefaultCharArrayValue.class))
				{
					DefaultCharArrayValue defaultValue = method
							.getAnnotation(DefaultCharArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Short.class)
					|| componentType.equals(Short.TYPE))// Short
														// Array
			{
				if (method.isAnnotationPresent(DefaultShortArrayValue.class))
				{
					DefaultShortArrayValue defaultValue = method
							.getAnnotation(DefaultShortArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Byte.class)
					|| componentType.equals(Byte.TYPE))// Long
														// Array
			{
				if (method.isAnnotationPresent(DefaultByteArrayValue.class))
				{
					DefaultByteArrayValue defaultValue = method
							.getAnnotation(DefaultByteArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Class.class))// Class Array
			{
				if (method.isAnnotationPresent(DefaultClassArrayValue.class))
				{
					DefaultClassArrayValue defaultValue = method
							.getAnnotation(DefaultClassArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(BigDecimal.class))// BigDecimal
																// Array
			{
				if (method.isAnnotationPresent(DefaultBigDecimalArrayValue.class))
				{
					DefaultBigDecimalArrayValue defaultValue = method
							.getAnnotation(DefaultBigDecimalArrayValue.class);
					String[] values = defaultValue.value();
					BigDecimal[] bigDecimals = (BigDecimal[]) Array.newInstance(
							BigDecimal.class, values.length);
					for (int i = 0; i < bigDecimals.length; i++)
					{
						bigDecimals[i] = new BigDecimal(values[i]);
					}
					return bigDecimals;
				}
			} else if (componentType.equals(BigInteger.class))// BigInteger
																// Array
			{
				if (method.isAnnotationPresent(DefaultBigIntegerArrayValue.class))
				{
					DefaultBigIntegerArrayValue defaultValue = method
							.getAnnotation(DefaultBigIntegerArrayValue.class);
					String[] values = defaultValue.value();
					BigInteger[] bigIntegers = new BigInteger[values.length];
					// BigInteger[] vs = (BigInteger[])
					// Array.newInstance(BigInteger.class, values.length);
					for (int i = 0; i < bigIntegers.length; i++)
					{
						bigIntegers[i] = new BigInteger(values[i]);
					}
					return bigIntegers;
				}
			} else if (componentType.isEnum())// Enum Array
			{
				if (method.isAnnotationPresent(DefaultEnumArrayValue.class))
				{
					DefaultEnumArrayValue defaultValue = method
							.getAnnotation(DefaultEnumArrayValue.class);
					String[] values = defaultValue.value();
					Object[] enums = (Object[]) Array.newInstance(componentType,
							values.length);
					for (int i = 0; i < enums.length; i++)
					{
						enums[i] = Enum.valueOf(componentType, values[i]);
					}
					return enums;
				}
			}
		} else
		{
			if (returnType.equals(String.class))// String
			{
				if (method.isAnnotationPresent(DefaultStringValue.class))
				{
					DefaultStringValue defaultValue = method
							.getAnnotation(DefaultStringValue.class);
					/**
					 * <code>	
					if (method.isAnnotationPresent(EscapeXml.class))
					{
						value = new SafeHtml(defaultValue.value()).toString();
					} else
					 */
					// {
					value = defaultValue.value();
					// }
				}
			} else if (returnType.equals(Integer.TYPE)
					|| returnType.equals(Integer.class))// Integer
			{
				if (method.isAnnotationPresent(DefaultIntValue.class))
				{
					DefaultIntValue defaultValue = method
							.getAnnotation(DefaultIntValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Double.TYPE) || returnType.equals(Double.class))// Double
			{
				if (method.isAnnotationPresent(DefaultDoubleValue.class))
				{
					DefaultDoubleValue defaultValue = method
							.getAnnotation(DefaultDoubleValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Long.TYPE) || returnType.equals(Long.class))// Long
			{
				if (method.isAnnotationPresent(DefaultLongValue.class))
				{
					DefaultLongValue defaultValue = method
							.getAnnotation(DefaultLongValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Boolean.TYPE)
					|| returnType.equals(Boolean.class))// Boolean
			{
				if (method.isAnnotationPresent(DefaultBooleanValue.class))
				{
					DefaultBooleanValue defaultValue = method
							.getAnnotation(DefaultBooleanValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Float.TYPE) || returnType.equals(Float.class))// float
			{
				if (method.isAnnotationPresent(DefaultFloatValue.class))
				{
					DefaultFloatValue defaultValue = method
							.getAnnotation(DefaultFloatValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Short.TYPE) || returnType.equals(Short.class))// Short
			{
				if (method.isAnnotationPresent(DefaultShortValue.class))
				{
					DefaultShortValue defaultValue = method
							.getAnnotation(DefaultShortValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Byte.TYPE) || returnType.equals(Byte.class))// byte
			{
				if (method.isAnnotationPresent(DefaultByteValue.class))
				{
					DefaultByteValue defaultValue = method
							.getAnnotation(DefaultByteValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Character.TYPE)
					|| returnType.equals(Character.class))// char
			{
				if (method.isAnnotationPresent(DefaultCharValue.class))
				{
					DefaultCharValue defaultValue = method
							.getAnnotation(DefaultCharValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Class.class))// Class
			{
				if (method.isAnnotationPresent(DefaultClassValue.class))
				{
					DefaultClassValue defaultValue = method
							.getAnnotation(DefaultClassValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(BigDecimal.class))// BigDecimal
			{
				if (method.isAnnotationPresent(DefaultBigDecimalValue.class))
				{
					DefaultBigDecimalValue defaultValue = method
							.getAnnotation(DefaultBigDecimalValue.class);
					value = new BigDecimal(defaultValue.value());
				}
			} else if (returnType.equals(BigInteger.class))// BigInteger
			{
				if (method.isAnnotationPresent(DefaultBigIntegerValue.class))
				{
					DefaultBigIntegerValue defaultValue = method
							.getAnnotation(DefaultBigIntegerValue.class);
					value = new BigInteger(defaultValue.value());
				}
			} else if (returnType.equals(SafeHtml.class))// SafeHtml
			{
				if (method.isAnnotationPresent(DefaultStringValue.class))
				{
					DefaultStringValue defaultValue = method
							.getAnnotation(DefaultStringValue.class);
					SafeHtml s = new SafeHtml(defaultValue.value());
					return s;
				}
			} else if (returnType.equals(Locale.class))// Locale
			{
				if (method.isAnnotationPresent(DefaultStringValue.class))
				{
					DefaultStringValue defaultValue = method
							.getAnnotation(DefaultStringValue.class);
					Locale s = LocaleUtils.localeFromString(defaultValue.value(), null);
					return s;
				}
			} else if (returnType.equals(URL.class))// URL
			{
				if (method.isAnnotationPresent(DefaultStringValue.class))
				{
					DefaultStringValue defaultValue = method
							.getAnnotation(DefaultStringValue.class);
					try
					{
						URL s = new URL(defaultValue.value());
						return s;
					} catch (MalformedURLException e)
					{
						e.printStackTrace();
						throw new ConversionException("String can not be a URL");
					}
				}
			} else if (returnType.isEnum())// Enum
			{
				if (method.isAnnotationPresent(DefaultEnumValue.class))
				{
					DefaultEnumValue defaultValue = method
							.getAnnotation(DefaultEnumValue.class);
					Enum e = Enum.valueOf(returnType, defaultValue.value());
					return e;
				}
			} else if (Map.class.isAssignableFrom(returnType))// Map FIXME
			{
				if (method.isAnnotationPresent(DefaultStringMapValue.class))
				{
					DefaultStringMapValue defaultValue = method
							.getAnnotation(DefaultStringMapValue.class);
					String[] ss = defaultValue.value();
					ReadOnlyHashMap map = new ReadOnlyHashMap();
					for (String s : ss)
					{
						String[] sv = s.split(":");
						if (sv != null && sv.length == 2)
						{
							map.put(sv[0], sv[1], true);
						} else
						{
							throw new ConstantsInvocationException("常量错误");
						}
					}
					return map;
				}
			}
		}
		return value;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// System.out.println(TestConstants.class.getGenericInterfaces()[0]);
		// System.out.println("已经加载过" +
		// ManagementFactory.getClassLoadingMXBean().getTotalLoadedClassCount());
		// ManagementFactory.getMemoryMXBean().gc();
		// System.out.println("卸载了:" +
		// ManagementFactory.getClassLoadingMXBean().getUnloadedClassCount());
		// System.out.println("当前加载Clas数量:" +
		// ManagementFactory.getClassLoadingMXBean().getLoadedClassCount());
		// System.out.println("当前加载Clas数量:" +
		// ManagementFactory.getMemoryMXBean().getHeapMemoryUsage());
		// System.out.println("当前加载Clas数量:" +
		// ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());

		// TestConstants testConstants =
		// ConstantsInvocationHandler.newInstance(TestConstants.class);
		// System.out.println(testConstants.message(1, new Date(), "fdsf"));

		Integer a = 1;
		//
		System.out.println(Integer.class.isInstance((Integer) a));
		System.out.println(TypeUtils.isCompatibleType(a, int.class));
		System.out.println(TypeUtils.primitive(int.class));

	}

	public static <T extends Constants> T newInstance(Class<T> type)
	{
		return newProxyInstance(type, new ConstantsInvocationHandler<T>(type));
	}

	protected static <T> T newProxyInstance(Class<T> type, InvocationHandler handler)
	{
		return type.cast(Proxy.newProxyInstance(handler.getClass().getClassLoader(),
				new Class<?>[] { type }, handler));
	}
}
