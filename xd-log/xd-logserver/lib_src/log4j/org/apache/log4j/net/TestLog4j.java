package org.apache.log4j.net;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class TestLog4j
{
	public static void main(String[] args)
	{
		DOMConfigurator.configure(TestLog4j.class.getResource("/log4j.xml"));
		Logger remoteLogger = LogManager.getLogger("testRemote");
		remoteLogger.info("blabla");
	}
}