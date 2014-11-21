package com.xindian.mvc.conversion;

import java.util.Map;

/**
 * 类型转换器统一的接口
 * 
 * @author Elva
 * @date 2011-2-10
 * @version 1.0
 */
public interface Converter
{
	/**
	 * 从上下文中取出一个Boolean来说明使用是否用
	 */
	public final static String CONTEXT_OPTION_USE_DEFAULT_VALUE_KEY = "use.default.values.to.avoid.throwing.exceptions";

	/** 时区ID键 */
	public final static String CONTEXT_TIME_ZONE_ID_KEY = "timeZone.id";

	/** 时区键 */
	public final static String CONTEXT_TIME_ZONE_KEY = "timeZone";

	/** 取得本地信息的上下文键 */
	public final static String CONTEXT_LOCALE_KEY = "locale";

	/** 相当于参数中的TargetType,要被转换的类型 */
	public final static String CONTEXT_TARGET_TYPE_KEY = "TargetType";

	/** 请使用:CONTEXT_DATE_TIME_PATTERN_ARRAY_KEY */
	@Deprecated
	public final static String CONTEXT_DATE_TIME_PATTERN_KEY = "format";

	/** 请使用:CONTEXT_DATE_TIME_PATTERN_ARRAY_KEY */
	public final static String CONTEXT_DATE_TIME_PATTERN_ARRAY_KEY = "formatArray";

	/** 上下文中获得默认值(只有在转化失败,配置中支持上下文中获取默认值,且存在默认值的时候) */
	public final static String CONTEXT_DEFAULT_VALUE_KEY = "defaultValue";

	/** 分隔符(应该是String类型) */
	public final static String CONTEXT_DELIMITER_KEY = "defaultDelimiter";

	/** 分隔符(应该是String类型) */
	public final static String CONTEXT_STRING_TRIM_KEY = "String_trim";

	public final static String CONTEXT_ONLY_FIRST_TO_STRING_FLAG_KEY = "only_First_To_String";

	/** 是否在子元素类型转换失败的时候报错,如果否,则忽略那些无法格式转化的元素 */
	public final static String CONTEXT_ERROR_WHEN_CON_CONVERT_FAIL_FLAG_KEY = "only_First_To_String";

	public static final String CONTEXT_ELEMENT_TYPE_KEY = "elementType";

	/**
	 * @param context
	 *            存放一些参数的上下文
	 * @param targetType
	 *            要被转换的确定类型
	 * @param value
	 *            要被转换的对象
	 * @return 返回转换后的对象
	 * @throws ConversionException
	 */
	public Object convert(Map<String, Object> context, final Class targetType, final Object sourceValue)
			throws ConversionException;
}
