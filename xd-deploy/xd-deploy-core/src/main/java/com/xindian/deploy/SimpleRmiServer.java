package com.xindian.deploy;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * CRMRMIServer服务
 * 
 * @author Elva
 * @date 2011-7-1
 * @version 1.0.0
 */
public class SimpleRmiServer extends BasicModule
{
	private static Logger log = LoggerFactory.getLogger(SimpleRmiServer.class);

	private static ConfigurableApplicationContext context;

	public SimpleRmiServer()
	{
		super("SimpleRmiServer");
	}

	/**
	 * @return
	 */
	public static ConfigurableApplicationContext getContext()
	{
		if (context == null)
		{
			context = new ClassPathXmlApplicationContext("applicationContext-crm-rmi-server.xml");

			// context.getb
			// ReminderServer reminderServer = new ReminderServer();
			/*
			 * try { reminderServer.start();
			 * ReminderServer.test(reminderServer); } catch (SchedulerException
			 * e) { e.printStackTrace(); }
			 */
		}
		return context;
	}

	/**
	 * 向spring的beanFactory动态地装载bean
	 * 
	 * @param configLocationString
	 *            要装载的bean所在的xml配置文件位置。 spring配置中的contextConfigLocation，同样支持诸如
	 *            "/WEB-INF/ApplicationContext-*.xml"的写法。
	 */
	public void loadBean(String configLocationString)
	{
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) getContext()
				.getBeanFactory());
		beanDefinitionReader.setResourceLoader(getContext());
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getContext()));
		try
		{
			String[] configLocations = new String[] { configLocationString };
			for (int i = 0; i < configLocations.length; i++)
			{
				beanDefinitionReader.loadBeanDefinitions(getContext().getResources(configLocations[i]));
			}
		} catch (BeansException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * start server by configuration in bean.xml
	 */
	private void startByCfg()
	{
		synchronized (this)
		{
			getContext();
		}
		if (log.isInfoEnabled())
		{
			log.info("application context 's display name :{}", context.getDisplayName());
			log.info("CRMRMIServer initialized successfully!");
		}
	}

	@Override
	public void start()
	{
		long startTime = System.currentTimeMillis();
		startByCfg();
		log.info("CRM服务启动耗时:" + (System.currentTimeMillis() - startTime) + "(ms)");
	}

	@Override
	public void stop()
	{

	}

	/**
	 * 
	 * main method for start a rmi server
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		new SimpleRmiServer().start();
		Object lock = new Object();
		synchronized (lock)
		{
			lock.wait();
		}
	}

}