package com.xindian.mvc.test;
import com.xindian.mvc.ActionContextManager;
import com.xindian.mvc.ExclamationMarkPathTranslator;
import com.xindian.mvc.GetterSetterMappingFilter;
import com.xindian.mvc.MVC;
import com.xindian.mvc.result.ForwardProtocolParser;
import com.xindian.mvc.result.ProtocolParserFactory;
import com.xindian.mvc.result.RedirectProtocolParser;
import com.xindian.mvc.result.StringResultProtocolParser;
import com.xindian.mvc.test.TestActionContextListener;
import com.xindian.mvc.test.actions.TestAction;
import com.xindian.mvc.test.actions.TestAop;
import com.xindian.mvc.test.actions.TestFileUpload;
import com.xindian.mvc.test.actions.TestMethods;
import com.xindian.mvc.test.actions.TestResults;

public class MVCDispatcherTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	// TEST
	private void test()
	{
		ActionContextManager.getSingleton().addGlobalActionContextListener(new TestActionContextListener());

		ProtocolParserFactory.getSingleton()//
				.registeResultProtocol(new ForwardProtocolParser())//
				.registeResultProtocol(new RedirectProtocolParser())//
				.registeResultProtocol(new StringResultProtocolParser());

		MVC.getSingleton().addPathTranslator(new ExclamationMarkPathTranslator(ext));

		// 阻挡所有set/set开头的请求
		MVC.getSingleton().addMappingFilter(new GetterSetterMappingFilter());

		//
		MVC.getSingleton().addAction(TestAction.class)//
				.addAction(TestAop.class)//
				.addAction(TestResults.class)//
				.addAction(TestFileUpload.class)//
				.addAction(TestMethods.class);
	}
}
