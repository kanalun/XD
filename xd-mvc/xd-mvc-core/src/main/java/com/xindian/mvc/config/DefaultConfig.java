package com.xindian.mvc.config;

import java.util.ArrayList;

import com.xindian.mvc.ExclamationMarkPathTranslator;
import com.xindian.mvc.GetterSetterMappingFilter;
import com.xindian.mvc.result.ErrorProtocolParser;
import com.xindian.mvc.result.ForwardProtocolParser;
import com.xindian.mvc.result.RedirectProtocolParser;
import com.xindian.mvc.result.StringResultProtocolParser;
//import com.xindian.mvc.result.freemarker.FreemarkerResultProtocolParser;
//import com.xindian.mvc.result.velocity.VelocityResultProtocolParser;

/**
 * 系统默认配置
 * 
 * @author Elva
 * 
 */
public abstract class DefaultConfig implements Config
{
	@Override
	public abstract Class<?>[] actions();

	@Override
	public int uploadPoolSize()
	{
		return 1024 * 1024 * 2;
	}

	@Override
	public String encoding()
	{
		return "UTF-8";
	}

	public String ext()
	{
		return "do";
	}

	@Override
	public String fileUploadTempDir()
	{
		return System.getProperty("java.io.tmpdir");
	}

	@Override
	public Class<?>[] pathTranslators()
	{
		return new Class<?>[] { ExclamationMarkPathTranslator.class };
	}

	@Override
	public Class<?>[] mappingFilters()
	{
		return new Class<?>[] { GetterSetterMappingFilter.class };
	}

	// TODO 听过读取配置文件
	@Override
	public Class<?>[] resultProtocols()
	{
		ArrayList<Class<?>> parsers = new ArrayList<Class<?>>();

		Class<?> freemarkerResultProtocolParserClass = null;
		try
		{
			freemarkerResultProtocolParserClass = Class
					.forName("com.xindian.mvc.result.freemarker.FreemarkerResultProtocolParser");
		} catch (Exception e)
		{
		}
		Class<?> velocityResultProtocolParserClass = null;
		try
		{
			velocityResultProtocolParserClass = Class.forName("com.xindian.mvc.result.velocity.VelocityResultProtocolParser");
		} catch (Exception e)
		{

		}
		parsers.add(ForwardProtocolParser.class);
		parsers.add(RedirectProtocolParser.class);
		parsers.add(StringResultProtocolParser.class);
		parsers.add(ErrorProtocolParser.class);
		if (freemarkerResultProtocolParserClass != null)
		{
			parsers.add(freemarkerResultProtocolParserClass);
		}
		if (velocityResultProtocolParserClass != null)
		{
			parsers.add(velocityResultProtocolParserClass);
		}
		return parsers.toArray(new Class<?>[0]);

		// return new Class<?>[] { ForwardProtocolParser.class,
		// RedirectProtocolParser.class, StringResultProtocolParser.class,
		// ErrorProtocolParser.class, freemarkerResultProtocolParserClass,
		// velocityResultProtocolParserClass };
	}

}
