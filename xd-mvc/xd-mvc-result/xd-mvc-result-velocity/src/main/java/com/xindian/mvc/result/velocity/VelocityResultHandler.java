package com.xindian.mvc.result.velocity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.AbstractContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.util.SimplePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.utils.ClassLoaderUtils;
import com.xindian.mvc.ActionContext;
import com.xindian.mvc.result.AbstractResultHandler;
import com.xindian.mvc.result.ResultHandler;

/**
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class VelocityResultHandler extends AbstractResultHandler implements ResultHandler
{
	private static Logger logger = LoggerFactory.getLogger(VelocityResultHandler.class);

	/***
	 * The context key for the HTTP action object.
	 */
	public static final String ACTION = "act";

	/***
	 * The context key for the HTTP request object.
	 */
	public static final String REQUEST = "req";

	/***
	 * The context key for the HTTP response object.
	 */
	public static final String RESPONSE = "res";

	/***
	 * The context key for the HTTP request parm object.
	 */
	public static final String PARAMETERS = "par";

	/**
	 * The context key for the HTTP session object.
	 */
	public static final String SESSION = "ses";

	/**
	 * The context key for the HTTP applaciont object.
	 */
	public static final String APPLICATION = "app";

	/**
	 * The context key for the HTTP cookie object.
	 */
	public static final String COOKIES = "coo";

	/**
	 * to reuse the VelocityWriter
	 */
	private static SimplePool writerPool = new SimplePool(40);

	private VelocityManager velocityManager;

	private static VelocityEngine velocityEngine;

	static
	{
		InputStream ps = ClassLoaderUtils.getResourceAsStream("com/kan/mvc/result/velocity/velocity.properties",
				VelocityResultHandler.class);
		Properties properties = new Properties();
		try
		{
			properties.load(ps);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		velocityEngine = new VelocityEngine(properties);
		velocityEngine.setApplicationAttribute("javax.servlet.ServletContext", ActionContext.getServletContext());
		// Object.class.getClassLoader().getResourceAsStream("velocity.properties");
	}

	@Override
	public void doResult(ActionContext actionContext, Object result) throws IOException, ServletException
	{
		if (result instanceof Velocity)
		{
			super.doResult(actionContext, result);//

			String location = ((Velocity) result).getTemplateLocation();
			String decoding = ((Velocity) result).getTemplateDecoding();

			{// FIXME this should be run once
				// velocityEngine.setApplicationAttribute("javax.servlet.ServletContext",
				// ActionContext.getServletContext());
			}
			// ACTION
			BeanVelocityContext actionVelocityContext = new BeanVelocityContext(ActionContext.getContext().getAction());

			// REQUEST
			HttpRequestVelocityContext requestVelocityContext = new HttpRequestVelocityContext(ActionContext.getRequest());

			// SESSION
			HttpSessionVelocityContext sessionVelocityContext = new HttpSessionVelocityContext(ActionContext.getSession(false),
					ActionContext.getRequest());

			// PARAMETERS
			HttpRequestParametersVelocityContext parametersVelocityContext = new HttpRequestParametersVelocityContext(
					ActionContext.getRequest());

			// APPLIACTION
			ServletContextVelocityContext servletContextVelocityContext = new ServletContextVelocityContext(
					ActionContext.getServletContext());

			// MEARGE ALL SCOPES
			AllScopesVelocityContext allScropes = new AllScopesVelocityContext(new AbstractContext[] {//
					actionVelocityContext, // 1,最先从Aciont取出
							requestVelocityContext,// 2,request.attr
							sessionVelocityContext, // 3,//session.attr
							servletContextVelocityContext // 4,applaction.attr
					});
			// USE act/req/ses/par/...
			allScropes.put(ACTION, actionVelocityContext);
			allScropes.put(REQUEST, requestVelocityContext);
			allScropes.put(SESSION, sessionVelocityContext);
			allScropes.put(PARAMETERS, parametersVelocityContext);
			allScropes.put(APPLICATION, servletContextVelocityContext);

			// File file = new File(".");
			// System.out.println(file.getAbsolutePath());
			// System.out.println(location + "  " + decoding);
			VelocityWriter velocityWriter = null;
			try
			{
				Template template = velocityEngine.getTemplate(location, decoding);
				logger.debug("GET Velocity Template NAME:[" + template.getName() + "]");

				HttpServletResponse response = ActionContext.getResponse();
				String encoding = response.getCharacterEncoding();// 这个应该已经才在前面设置了的

				velocityWriter = (VelocityWriter) writerPool.get();
				if (velocityWriter == null)
				{
					velocityWriter = new VelocityWriter(new OutputStreamWriter(response.getOutputStream(), encoding), 4 * 1024,
							true);
					logger.debug("NEW A VelocityWriter:[" + velocityWriter + "]");
				} else
				{
					velocityWriter.recycle(new OutputStreamWriter(response.getOutputStream(), encoding));
					logger.debug("RECYCLE A VelocityWriter:[" + velocityWriter + "]");
				}
				template.merge(allScropes, velocityWriter);
			} catch (ResourceNotFoundException e)
			{
				// 500
				e.printStackTrace();
				// couldn't find the template
			} catch (ParseErrorException e)
			{
				// 500
				e.printStackTrace();
				// syntax error: problem parsing the template
			} catch (MethodInvocationException e)
			{
				// 500
				e.printStackTrace();
				// something invoked in the template
				// threw an exception
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				if (velocityWriter != null)
				{
					try
					{
						velocityWriter.flush();
					} catch (IOException e)
					{
						// do nothing
						e.printStackTrace();
					}
					velocityWriter.recycle(null);
					writerPool.put(velocityWriter);
				}
			}
		}
	}
}
