package org.apache.log4j.net;

import org.apache.log4j.spi.LoggingEvent;

public class LoggingData implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	public String projectName;

	LoggingEvent event;

	public LoggingData(String projectName, LoggingEvent loggingEvent)
	{
		this.projectName = projectName;
		this.event = loggingEvent;
	}
}
