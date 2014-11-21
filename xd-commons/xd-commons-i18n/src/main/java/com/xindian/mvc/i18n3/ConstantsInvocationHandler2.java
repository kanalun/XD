package com.xindian.mvc.i18n3;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.conversion.ConversionException;
import com.xindian.mvc.conversion.ConverterFactory;
import com.xindian.mvc.i18n3.Constants.DefaultBigDecimalArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultBigDecimalValue;
import com.xindian.mvc.i18n3.Constants.DefaultBigIntegerArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultBigIntegerValue;
import com.xindian.mvc.i18n3.Constants.DefaultBooleanArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultBooleanValue;
import com.xindian.mvc.i18n3.Constants.DefaultByteArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultByteValue;
import com.xindian.mvc.i18n3.Constants.DefaultCharArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultCharValue;
import com.xindian.mvc.i18n3.Constants.DefaultClassArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultClassValue;
import com.xindian.mvc.i18n3.Constants.DefaultDoubleArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultDoubleValue;
import com.xindian.mvc.i18n3.Constants.DefaultEnumArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultEnumValue;
import com.xindian.mvc.i18n3.Constants.DefaultFloatArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultFloatValue;
import com.xindian.mvc.i18n3.Constants.DefaultIntArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultIntValue;
import com.xindian.mvc.i18n3.Constants.DefaultLongArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultLongValue;
import com.xindian.mvc.i18n3.Constants.DefaultShortArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultShortValue;
import com.xindian.mvc.i18n3.Constants.DefaultStringArrayValue;
import com.xindian.mvc.i18n3.Constants.DefaultStringMapValue;
import com.xindian.mvc.i18n3.Constants.DefaultStringValue;
import com.xindian.mvc.i18n3.LocalizableResource.DefaultLocale;
import com.xindian.mvc.i18n3.LocalizableResource.Gloabal;
import com.xindian.mvc.i18n3.LocalizableResource.Key;
import com.xindian.mvc.i18n3.LocalizableResource.Resource;
import com.xindian.mvc.i18n3.Messages.DefaultMessage;
import com.xindian.mvc.i18n3.Messages.EscapeXml;
import com.xindian.mvc.utils.LocaleUtils;
import com.xindian.mvc.utils.ReadOnlyHashMap;
import com.xindian.mvc.utils.TypeUtils;

/**
 * @author Elva
 * @date 2011-2-4
 * @version 1.0
 * @param <T>
 */
@SuppressWarnings("unchecked")
public final class ConstantsInvocationHandler2<T extends Constants> implements InvocationHandler, LocaleProvider
{
	private static Logger logger = LoggerFactory.getLogger(ConstantsInvocationHandler2.class);

	/** EMPTY TYPE CONVERTION CONTEXT */
	private static final Map<String, Object> EMPTY_CONTEXT = Collections.EMPTY_MAP;

	/** 常量缓存,默认情况下对没有参数的方法做缓存 */
	private Map<String, Object> constantsCache = new ConcurrentHashMap<String, Object>();

	/** ResourceProvider为常量提供资源支持 */
	// private static ResourceProvider resourceProvider =
	// DefaultResourceProvider.INSTANCE;
	// private TextProvider textProvider = DefaultTextProvider.INSTANCE;
	private volatile LocaleProvider localeProvider;

	/** 常量接口类型 */
	private Class<T> type;

	/** 资源名称 */
	private String resourceName;

	/** 默认本地信息 */
	private Locale defaultLocale = Locale.getDefault();

	/** 资源编码 */
	private String resourceEncode;

	/** 资源是否共享 */
	private boolean share;

	private boolean hasResource;

	/** 是否为全局资源/如果是true.其他组件可以通过其他访问到这个资源 */
	private boolean global = false;

	public ConstantsInvocationHandler2(Class<T> type)
	{
		this.type = type;

		if (type.isAnnotationPresent(DefaultLocale.class))
		{
			DefaultLocale defaultLocale = type.getAnnotation(DefaultLocale.class);
			this.defaultLocale = LocaleUtils.localeFromString(defaultLocale.value(), null);
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
		if (resourceName == null || resourceName.trim().length() <= 0)
		{
			resourceName = type.getName();
		}

		// resourceProvider.addResource(resourceName);

		logger.debug("Constants Resource Name:[" + resourceName + "]");
	}

	private String cacheKey(Object proxy, Method method, Object[] args, Locale locale)
	{
		String key = method + "_" + locale;
		return key;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		Locale locale = null;

		if (localeProvider != null)
		{
			locale = localeProvider.getLocale();
		}
		if (locale == null && defaultLocale != null)
		{
			locale = defaultLocale;
		}
		// if (args == null || args.length == 0)
		// {
		// String cackeKey = cacheKey(proxy, method, args);
		// }
		Class<?> returnType = method.getReturnType();

		logger.debug("Constants Method:[" + method + "] Return Type:[" + returnType + "]" + "args:[" + args + "]");

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
		ResourceBundle resourceBundle = null;
		String text = null;
		try
		{
			if (hasResource)
			{
				resourceBundle = ResourceBundle.getBundle(resourceName, locale, Thread.currentThread().getContextClassLoader());
				text = resourceBundle.getString(textKey);
				locale = resourceBundle.getLocale();

			}
		} catch (MissingResourceException ex)
		{
			hasResource = false;
		}
		if (text == null)
		{
			text = defaultMessage;
		}
		Object value = null;
		if (text == null)
		{
			value = getDefaultValue(method);
		} else
		{
			try
			{
				// TODO,将格式化参数加入到上下文中,做好缓存
				value = ConverterFactory.convert(EMPTY_CONTEXT, returnType, text);// convert
																					// text
																					// to
																					// method's
																					// returnType
			} catch (ConversionException e)
			{
				e.printStackTrace();// TODO
			}
			// 检查返回值是否兼容
			// 如果不兼容
			if (value == null || !TypeUtils.isCompatibleType(value, returnType))// 没有值,或者值和返回类型不兼容
			{
				value = getDefaultValue(method);
				logger.debug("---------------没有找到合适的资源使用类定义的默认值[" + value + "]");
			}
		}
		if (value == null)
		{
			throw new ConstantsInvocationException("Can not find text key;[" + textKey + "]");
		}
		if (method.isAnnotationPresent(EscapeXml.class) && value instanceof CharSequence && returnType.equals(String.class))
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
	protected Object getDefaultValue(Method method)// TODO 抛出异常
	{
		Class returnType = method.getReturnType();
		Object value = null;
		if (returnType.isArray())
		{
			Class componentType = returnType.getComponentType();// Array
																// elementType

			if (componentType.equals(String.class))// String Array
			{
				if (method.isAnnotationPresent(DefaultStringArrayValue.class))
				{
					DefaultStringArrayValue defaultValue = method.getAnnotation(DefaultStringArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Integer.class) || componentType.equals(Integer.TYPE))// Int
																									// Array
			{
				if (method.isAnnotationPresent(DefaultIntArrayValue.class))
				{
					DefaultIntArrayValue defaultValue = method.getAnnotation(DefaultIntArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Double.class) || componentType.equals(Double.TYPE))// Double
																								// Array
			{
				if (method.isAnnotationPresent(DefaultDoubleArrayValue.class))
				{
					DefaultDoubleArrayValue defaultValue = method.getAnnotation(DefaultDoubleArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Long.class) || componentType.equals(Long.TYPE))// Long
																							// Array
			{
				if (method.isAnnotationPresent(DefaultLongArrayValue.class))
				{
					DefaultLongArrayValue defaultValue = method.getAnnotation(DefaultLongArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Boolean.class) || componentType.equals(Boolean.TYPE))// Boolean
																									// Array
			{
				if (method.isAnnotationPresent(DefaultBooleanArrayValue.class))
				{
					DefaultBooleanArrayValue defaultValue = method.getAnnotation(DefaultBooleanArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Float.class) || componentType.equals(Float.TYPE))// Float
																								// Array
			{
				if (method.isAnnotationPresent(DefaultFloatArrayValue.class))
				{
					DefaultFloatArrayValue defaultValue = method.getAnnotation(DefaultFloatArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Character.class) || componentType.equals(Character.TYPE))// Character
																										// Array
			{
				if (method.isAnnotationPresent(DefaultCharArrayValue.class))
				{
					DefaultCharArrayValue defaultValue = method.getAnnotation(DefaultCharArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Short.class) || componentType.equals(Short.TYPE))// Short
																								// Array
			{
				if (method.isAnnotationPresent(DefaultShortArrayValue.class))
				{
					DefaultShortArrayValue defaultValue = method.getAnnotation(DefaultShortArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Byte.class) || componentType.equals(Byte.TYPE))// Byte
																							// Array
			{
				if (method.isAnnotationPresent(DefaultByteArrayValue.class))
				{
					DefaultByteArrayValue defaultValue = method.getAnnotation(DefaultByteArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(Class.class))// Class Array
			{
				if (method.isAnnotationPresent(DefaultClassArrayValue.class))
				{
					DefaultClassArrayValue defaultValue = method.getAnnotation(DefaultClassArrayValue.class);
					value = defaultValue.value();
				}
			} else if (componentType.equals(BigDecimal.class))// BigDecimal
																// Array
			{
				if (method.isAnnotationPresent(DefaultBigDecimalArrayValue.class))
				{
					DefaultBigDecimalArrayValue defaultValue = method.getAnnotation(DefaultBigDecimalArrayValue.class);
					String[] values = defaultValue.value();
					BigDecimal[] bigDecimals = (BigDecimal[]) Array.newInstance(BigDecimal.class, values.length);
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
					DefaultBigIntegerArrayValue defaultValue = method.getAnnotation(DefaultBigIntegerArrayValue.class);
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
					DefaultEnumArrayValue defaultValue = method.getAnnotation(DefaultEnumArrayValue.class);
					String[] values = defaultValue.value();
					Object[] enums = (Object[]) Array.newInstance(componentType, values.length);
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
					DefaultStringValue defaultValue = method.getAnnotation(DefaultStringValue.class);
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
			} else if (returnType.equals(Integer.TYPE) || returnType.equals(Integer.class))// Integer
			{
				if (method.isAnnotationPresent(DefaultIntValue.class))
				{
					DefaultIntValue defaultValue = method.getAnnotation(DefaultIntValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Double.TYPE) || returnType.equals(Double.class))// Double
			{
				if (method.isAnnotationPresent(DefaultDoubleValue.class))
				{
					DefaultDoubleValue defaultValue = method.getAnnotation(DefaultDoubleValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Long.TYPE) || returnType.equals(Long.class))// Long
			{
				if (method.isAnnotationPresent(DefaultLongValue.class))
				{
					DefaultLongValue defaultValue = method.getAnnotation(DefaultLongValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Boolean.TYPE) || returnType.equals(Boolean.class))// Boolean
			{
				if (method.isAnnotationPresent(DefaultBooleanValue.class))
				{
					DefaultBooleanValue defaultValue = method.getAnnotation(DefaultBooleanValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Float.TYPE) || returnType.equals(Float.class))// float
			{
				if (method.isAnnotationPresent(DefaultFloatValue.class))
				{
					DefaultFloatValue defaultValue = method.getAnnotation(DefaultFloatValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Short.TYPE) || returnType.equals(Short.class))// Short
			{
				if (method.isAnnotationPresent(DefaultShortValue.class))
				{
					DefaultShortValue defaultValue = method.getAnnotation(DefaultShortValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Byte.TYPE) || returnType.equals(Byte.class))// byte
			{
				if (method.isAnnotationPresent(DefaultByteValue.class))
				{
					DefaultByteValue defaultValue = method.getAnnotation(DefaultByteValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Character.TYPE) || returnType.equals(Character.class))// char
			{
				if (method.isAnnotationPresent(DefaultCharValue.class))
				{
					DefaultCharValue defaultValue = method.getAnnotation(DefaultCharValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(Class.class))// Class
			{
				if (method.isAnnotationPresent(DefaultClassValue.class))
				{
					DefaultClassValue defaultValue = method.getAnnotation(DefaultClassValue.class);
					value = defaultValue.value();
				}
			} else if (returnType.equals(BigDecimal.class))// BigDecimal
			{
				if (method.isAnnotationPresent(DefaultBigDecimalValue.class))
				{
					DefaultBigDecimalValue defaultValue = method.getAnnotation(DefaultBigDecimalValue.class);
					value = new BigDecimal(defaultValue.value());
				}
			} else if (returnType.equals(BigInteger.class))// BigInteger
			{
				if (method.isAnnotationPresent(DefaultBigIntegerValue.class))
				{
					DefaultBigIntegerValue defaultValue = method.getAnnotation(DefaultBigIntegerValue.class);
					value = new BigInteger(defaultValue.value());
				}
			} else if (returnType.equals(SafeHtml.class))// SafeHtml
			{
				if (method.isAnnotationPresent(DefaultStringValue.class))
				{
					DefaultStringValue defaultValue = method.getAnnotation(DefaultStringValue.class);
					SafeHtml s = new SafeHtml(defaultValue.value());
					return s;
				}
			} else if (returnType.equals(URL.class))// URL
			{
				if (method.isAnnotationPresent(DefaultStringValue.class))
				{
					DefaultStringValue defaultValue = method.getAnnotation(DefaultStringValue.class);
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
					DefaultEnumValue defaultValue = method.getAnnotation(DefaultEnumValue.class);
					Enum e = Enum.valueOf(returnType, defaultValue.value());
					return e;
				}
			} else if (Map.class.isAssignableFrom(returnType))// Map FIXME
			{
				if (method.isAnnotationPresent(DefaultStringMapValue.class))
				{
					DefaultStringMapValue defaultValue = method.getAnnotation(DefaultStringMapValue.class);
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

	public LocaleProvider getLocaleProvider()
	{
		return localeProvider;
	}

	public void setLocaleProvider(LocaleProvider localeProvider)
	{
		this.localeProvider = localeProvider;
	}

	@Override
	public Locale getLocale()
	{
		return defaultLocale;
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
		System.out.println(Integer.TYPE.isInstance(a));

		System.out.println(TypeUtils.isCompatibleType(1 + "", String.class));

	}

	public static <T extends Constants> T newInstance(Class<T> type)
	{
		return newProxyInstance(type, new ConstantsInvocationHandler2<T>(type));
	}

	protected static <T> T newProxyInstance(Class<T> type, InvocationHandler handler)
	{
		return type.cast(Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class<?>[] { type }, handler));
	}

}