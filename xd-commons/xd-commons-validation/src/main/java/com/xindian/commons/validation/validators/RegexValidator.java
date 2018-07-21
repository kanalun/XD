package com.xindian.commons.validation.validators;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.utils.AnnotationUtils;
import com.xindian.commons.validation.Validator;
import com.xindian.commons.validation.ValidatorException;
import com.xindian.commons.validation.Validation.OperatorType;

/**
 * 使用java正则表达式验证字符串是否符合规范
 * 
 * @author Elva
 * @date 2011-2-8
 * @version 1.0
 */
public class RegexValidator implements Validator
{
	private static Logger logger = LoggerFactory.getLogger(RegexValidator.class);

	public static String ANNOTATION_PATTERNS = "patterns";

	public static String ANNOTATION_OPERATOR = "operator";

	@Override
	public boolean validate(Object value, Annotation annotation) throws ValidatorException
	{
		String[] regexs = AnnotationUtils.getValue(ANNOTATION_PATTERNS, String[].class, annotation);
		if (regexs != null)
		{
			OperatorType disjunctionType = AnnotationUtils.getValue(ANNOTATION_OPERATOR,
					OperatorType.class, annotation);
			boolean matches = false;
			if (disjunctionType == OperatorType.AND)
			{
				for (String p : regexs)
				{
					matches = Pattern.matches(p, value.toString());
					if (matches)
					{
						logger.debug("matches");
						continue;
					} else
					{
						logger.debug("unMatches");
						return false;
					}
				}
				return true;// ALL matches
			} else
			{
				for (String p : regexs)
				{
					matches = Pattern.matches(p, value.toString());
					if (matches)//
					{
						return true;// 只要其中一个匹配
					}
				}
				return false;// 全部不匹配
			}
		} else
		{
			throw new ValidatorException("RegexValidator ,regexs Patterns can not be null");
		}
	}

	public static void main(String args[])
	{
		// logger.debug(Pattern.matches("\\\\", "\\"));
		// regex.matcher(value);
	}
}
