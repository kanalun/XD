package com.xindian.mvc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.commons.utils.Validator;
import com.xindian.mvc.ActionContext;
import com.xindian.mvc.ActionContextManager;
import com.xindian.mvc.MVC;
import com.xindian.mvc.MappingFilter;
import com.xindian.mvc.PathTranslator;
import com.xindian.mvc.config.Config;
import com.xindian.mvc.exception.MVCException;
import com.xindian.mvc.result.ProtocolParserFactory;
import com.xindian.mvc.result.ResultProtocolParser;

/**
 * Servlet Filter implementation class MVCDispatcher
 */
public class MVCDispatcher implements Filter
{
	private static Logger logger = LoggerFactory.getLogger(MVCDispatcher.class);

	private String ext = "do";

	private String encoding = "UTF-8";

	private String tempDir = "";

	private ServletContext context;

	private void config(Config cfg) throws InstantiationException, IllegalAccessException
	{
		logger.info("MVCDispatcher Config Init_ing:[" + cfg + "]...");
		// init ext
		if (Validator.isBlank(cfg.ext()))
		{
			// ERROR
			throw new MVCException("后缀不能为空...");
		} else
		{
			ext = cfg.ext();
			logger.info("	ACTION ext:" + ext);
		}
		// init encoding
		if (Validator.isBlank(cfg.encoding()))
		{
			throw new MVCException("encoding不能为空...");
		} else
		{
			encoding = cfg.encoding();
			logger.info("	ACTION encoding:" + encoding);
			ActionContextManager.getSingleton().setEncoding(encoding);
		}
		// init tempDir
		if (Validator.isBlank(cfg.fileUploadTempDir()))
		{
			throw new MVCException("tempDir不能为空...");
		} else
		{
			tempDir = cfg.fileUploadTempDir();
			ActionContextManager.getSingleton().setTempDir(tempDir);
			logger.info("	ACTION tempDir:" + tempDir);
		}
		// init actions
		if (cfg.actions() != null && cfg.actions().length > 0)
		{
			logger.info("	ADD Actions ...");
			for (Class<?> actionType : cfg.actions())
			{
				MVC.getSingleton().addAction(actionType);//
			}
		} else
		{
			logger.warn("	NO ACTION :");
			// throw new MVCException("没有设定Action...");
		}

		// init PathTranslators
		if (cfg.pathTranslators() != null && cfg.pathTranslators().length > 0)
		{
			for (Class<?> pathTranslator : cfg.pathTranslators())
			{
				Object pt = pathTranslator.newInstance();
				if (pt instanceof PathTranslator)
				{
					MVC.getSingleton().addPathTranslator((PathTranslator) (pt));//
				} else
				{
					throw new MVCException("PathTranslator 错误");
				}
			}
		} else
		{
			throw new MVCException("没有设定PathTranslator");
		}
		// init MappingFilter
		if (cfg.mappingFilters() != null && cfg.mappingFilters().length > 0)
		{
			for (Class<?> mappingFilter : cfg.mappingFilters())
			{
				Object mf = mappingFilter.newInstance();
				if (mf instanceof MappingFilter)
				{
					MVC.getSingleton().addMappingFilter((MappingFilter) (mf));
				} else
				{
					throw new MVCException("MappingFilter 错误");
				}
			}
		} else
		{
			throw new MVCException("没有设定MappingFilter");
		}
		// init ResultProtocolPaser
		if (cfg.resultProtocols() != null && cfg.resultProtocols().length > 0)
		{
			for (Class<?> resultProtocol : cfg.resultProtocols())
			{
				Object rp = resultProtocol.newInstance();
				if (rp instanceof ResultProtocolParser)
				{
					ProtocolParserFactory.getSingleton().registeResultProtocol((ResultProtocolParser) (rp));
				} else
				{
					throw new MVCException("ResultProtocolPaser 错误");
				}
			}
		} else
		{
			throw new MVCException("没有设定 ResultProtocolPaser");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		this.context = fConfig.getServletContext();// test
		String config = fConfig.getInitParameter("config");
		try
		{
			Class<?> c = Class.forName(config);
			Object o = c.newInstance();
			if (o instanceof Config)
			{
				config((Config) o);
			} else
			{
				throw new MVCException("配置类找不到...");
			}
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}

		// Enumeration<String> s = fConfig.getInitParameterNames();

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		ActionContext actionContext = ActionContextManager.getSingleton().createActionContext(this.context, request, response);
		try
		{
			if (MVC.getSingleton().doAction(actionContext))
			{
				// DO_NOTHING
			} else
			{
				chain.doFilter(request, response);
			}
		} finally
		{
			ActionContextManager.getSingleton().destoryActionContext(actionContext);
		}
	}



	@Override
	public void destroy()
	{
		/*
		 * 
		 * if (isMultipart((HttpServletRequest) request)) { MultipartRequest
		 * multipartRequest = new MultipartRequest((HttpServletRequest) request,
		 * "D:\\temp", 1024 1024, "UTF-8"); actionContext =
		 * ActionContext.create(this.context, multipartRequest,
		 * (HttpServletResponse) response); } else { actionContext =
		 * ActionContext.create(this.context, (HttpServletRequest) request,
		 * (HttpServletResponse) response); }
		 */
	}

}
