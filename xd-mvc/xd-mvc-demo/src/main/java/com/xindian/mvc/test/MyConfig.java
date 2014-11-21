package com.xindian.mvc.test;

import com.xindian.ioc.test.IoCUserAction;
import com.xindian.mvc.ExclamationMarkPathTranslator;
import com.xindian.mvc.GetterSetterMappingFilter;
import com.xindian.mvc.NormalPathTranslator;
import com.xindian.mvc.config.Config;
import com.xindian.mvc.config.DefaultConfig;
import com.xindian.mvc.test.actions.AntiCrawlerAction;
import com.xindian.mvc.test.actions.I18NAction;
import com.xindian.mvc.test.actions.TestAction;
import com.xindian.mvc.test.actions.TestAop;
import com.xindian.mvc.test.actions.TestFileUpload;
import com.xindian.mvc.test.actions.TestMethods;
import com.xindian.mvc.test.actions.TestResults;
import com.xindian.mvc.test.actions.UserAction;

public class MyConfig extends DefaultConfig implements Config
{
	@Override
	public Class<?>[] actions()
	{
		return new Class<?>[] { //
		TestAop.class,//
				TestAction.class,//
				TestResults.class, //
				TestFileUpload.class,//
				TestMethods.class, //
				UserAction.class,//
				AntiCrawlerAction.class, //
				IoCUserAction.class,//
				I18NAction.class //
		};
	}

	@Override
	public String encoding()
	{
		return "UTF-8";
	}

	@Override
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
		return new Class<?>[] { ExclamationMarkPathTranslator.class, NormalPathTranslator.class };
	}

	@Override
	public Class<?>[] mappingFilters()
	{
		return new Class<?>[] { GetterSetterMappingFilter.class };
	}

	/*
	 * @Override public Class<?>[] resultProtocols() { return new Class<?>[] {
	 * ForwardProtocolParser.class, RedirectProtocolParser.class,
	 * StringResultProtocolParser.class, FreemarkerResultProtocolParser.class,
	 * ErrorProtocolParser.class }; }
	 */

	public static void main(String args[])
	{
		System.out.print(new MyConfig().fileUploadTempDir());
	}
}
