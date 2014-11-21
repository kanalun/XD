package com.xindian.mvc.result.freemarker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.MVCException;
import com.xindian.mvc.result.AbstractResultHandler;
import com.xindian.mvc.result.ResultHandler;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * Handler从FreemarkerManager中获得模板<br/>
 * 
 * 然后将获得的数据跟这个模板合并<br/>
 * 
 * 这里的大部分代码可能来自于 FreemakerServlet
 * 
 * @author Elva
 * 
 */
public class FreemarkerResultHandler extends AbstractResultHandler implements ResultHandler
{
	private static String DEFAULT_CONTENT_TYPE = "text/html";

	private static FreemarkerManager freemarkerManager;

	private static Logger logger = LoggerFactory.getLogger(FreemarkerResultHandler.class);

	protected void preTemplateProcess(Template template, HttpServletResponse response)
	{
		if (response.getContentType() == null)// 没有设置result中
		{
			String contentType = (String) template.getCustomAttribute("content_type");

			if (contentType == null)
			{
				contentType = DEFAULT_CONTENT_TYPE;
			}
			String encoding = template.getEncoding();

			if (encoding != null)
			{
				contentType = contentType + "; charset=" + encoding;
			}
			logger.debug("NO ContentType In Result,Set ContentType(In Template) [" + contentType + "]");
			response.setContentType(contentType);
		}
	}

	/**
	 * <code>	
	public void doFreemarker(FreemarkerManager freemarkerManager, ActionContext actionContext, Freemarker rmk)
			throws IOException, ServletException
	{
		String temp = (String) rmk.getFtl();
		try
		{
			HttpServletResponse response = ActionContext.getResponse();

			// Get Template
			Template template = freemarkerManager.getTemplate(temp);

			// build Model
			TemplateModel templateModel = freemarkerManager.createModel(actionContext.getAction(), ActionContext
					.getServletContext(), ActionContext.getRequest(), response);

			// do before
			preTemplateProcess(template, response);

			// process
			template.process(templateModel, response.getWriter());

		} catch (TemplateModelException e)
		{
			e.printStackTrace();
			throw new MVCException("ERROR: TemplateModelException...", e);
		} catch (TemplateException e)
		{
			e.printStackTrace();
			throw new MVCException("ERROR: TemplateModelException...", e);
		}
	}

	public void doTemplate(Template template)
	{

	}</code>
	 */

	@SuppressWarnings("static-access")
	@Override
	public void doResult(ActionContext actionContext, Object result) throws IOException, ServletException
	{
		// FIXME 感觉这样不是很好
		if (freemarkerManager == null)
		{
			try
			{
				freemarkerManager = FreemarkerManager.getSingleton(actionContext.getServletContext());
			} catch (TemplateException e)
			{
				logger.error("Can not get a FreemarkerManager...");
				throw new MVCException("ERROR:Can not get a FreemarkerManager...", e);
			}
		}
		if (result instanceof Freemarker)
		{
			super.doResult(actionContext, result);

			Freemarker rmk = (Freemarker) result;
			String temp = (String) rmk.getFtl();
			try
			{
				HttpServletResponse response = ActionContext.getResponse();

				// Get Template
				Template template = freemarkerManager.getTemplate(temp);

				// build Model
				TemplateModel templateModel = freemarkerManager.createModel(actionContext.getAction(),
						ActionContext.getServletContext(), ActionContext.getRequest(), response);

				// do before
				preTemplateProcess(template, response);

				// process
				template.process(templateModel, response.getWriter());
				return;
			} catch (TemplateModelException e)
			{
				e.printStackTrace();
				throw new MVCException("ERROR: TemplateModelException...", e);
			} catch (TemplateException e)
			{
				e.printStackTrace();
				throw new MVCException("ERROR: TemplateModelException...", e);
			}
		}
		if (result instanceof Template)
		{
			try
			{
				HttpServletResponse response = ActionContext.getResponse();

				Template template = (Template) result;

				TemplateModel templateModel = freemarkerManager.createModel(actionContext.getAction(),
						ActionContext.getServletContext(), ActionContext.getRequest(), response);

				preTemplateProcess(template, response);

				template.process(templateModel, actionContext.getResponse().getWriter());

			} catch (TemplateModelException e)
			{
				e.printStackTrace();
			} catch (TemplateException e)
			{
				e.printStackTrace();
			}
		}
		return;
	}
}
