package com.xindian.mvc.result.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.utils.FileManager;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 管理Freemarker模板的
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public class FreemarkerManager
{
	private static Logger logger = LoggerFactory.getLogger(FreemarkerManager.class);

	private static FreemarkerManager singleton = new FreemarkerManager();

	private FreemarkerManager()
	{
		logger.debug("FreemarkerManager init_ed");
	}

	/**
	 * Returns the {@link freemarker.template.Configuration} object used by this
	 * handler. Please don't forget that
	 * {@link freemarker.template.Configuration} is not thread-safe when you
	 * modify it.
	 */
	private Configuration configuration;

	public static FreemarkerManager getSingleton(ServletContext servletContext) throws TemplateException
	{
		if (singleton.configuration == null)
		{
			singleton.configuration = singleton.getConfiguration(servletContext);
		}
		return singleton;
	}

	// Result
	public static final String KEY_RESULT = "Result";
	// Action
	public static final String KEY_ACTION = "Action";

	/*-------------------HTTP Scope------------------*/
	public static final String KEY_REQUEST = "Request";
	public static final String KEY_SESSION = "Session";
	public static final String KEY_APPLICATION = "Application";
	public static final String KEY_REQUEST_PARAMETERS = "RequestParameters";
	// Cookie
	public static final String KEY_COOKIES = "Cookies";

	public static final String KEY_INCLUDE = "include_page";
	public static final String KEY_REQUEST_PRIVATE = "__FreeMarkerServlet.Request__";
	public static final String KEY_APPLICATION_PRIVATE = "__FreeMarkerServlet.Application__";
	public static final String KEY_JSP_TAGLIBS = "JspTaglibs";

	// Note these names start with dot, so they're essentially invisible from
	// a freemarker script.
	private static final String ATTR_REQUEST_MODEL = ".freemarker.Request2";
	private static final String ATTR_REQUEST_PARAMETERS_MODEL = ".freemarker.RequestParameters2";
	private static final String ATTR_SESSION_MODEL = ".freemarker.Session";
	private static final String ATTR_APPLICATION_MODEL = ".freemarker.Application2";
	private static final String ATTR_JSP_TAGLIBS_MODEL = ".freemarker.JspTaglibs2";

	// 把FM的配置信息存放在servletContext中
	public static final String CONFIG_SERVLET_CONTEXT_KEY = "freemarker.Configuration";
	public static final String KEY_EXCEPTION = "exception";

	public static synchronized Configuration getConfiguration(ServletContext servletContext) throws TemplateException
	{
		Configuration config = (Configuration) servletContext.getAttribute(CONFIG_SERVLET_CONTEXT_KEY);
		if (config == null)
		{
			config = createConfiguration(servletContext);
			// store this configuration in the servlet context
			servletContext.setAttribute(CONFIG_SERVLET_CONTEXT_KEY, config);
		}
		config.setWhitespaceStripping(true);
		return config;
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public Template getTemplate(String name) throws IOException
	{
		return configuration.getTemplate(name);
	}

	private BeansWrapper beansWrapper;

	public BeansWrapper getBeansWrapper()
	{
		if (beansWrapper == null)
		{
			beansWrapper = BeansWrapper.getDefaultInstance();
			logger.debug("use default BeansWrapper");
		}
		return beansWrapper;
	}

	public void setBeansWrapper(BeansWrapper beansWrapper)
	{
		logger.debug("set BeansWrapper:[" + beansWrapper + "]");
		this.beansWrapper = beansWrapper;
	}

	protected ObjectWrapper getObjectWrapper()
	{
		return configuration.getObjectWrapper();
	}

	/**
	 * The default template loader is a MultiTemplateLoader which includes a
	 * ClassTemplateLoader and a WebappTemplateLoader (and a FileTemplateLoader
	 * depending on the init-parameter 'TemplatePath').
	 * <p/>
	 * The ClassTemplateLoader will resolve fully qualified template includes
	 * that begin with a slash. for example /com/company/template/common.ftl
	 * <p/>
	 * The WebappTemplateLoader attempts to resolve templates relative to the
	 * web root folder
	 */
	protected static TemplateLoader getTemplateLoader(ServletContext servletContext)
	{
		// construct a FileTemplateLoader for the init-param 'TemplatePath'
		FileTemplateLoader templatePathLoader = null;

		String templatePath = servletContext.getInitParameter("TemplatePath");
		if (templatePath == null)
		{
			templatePath = servletContext.getInitParameter("templatePath");
		}
		if (templatePath != null)
		{
			try
			{
				templatePathLoader = new FileTemplateLoader(new File(templatePath));
			} catch (IOException e)
			{
				logger.error("Invalid template path specified: " + e.getMessage(), e);
			}
		}

		// presume that most apps will require the class and webapp template
		// loader
		// if people wish to

		List<TemplateLoader> tls = new ArrayList<TemplateLoader>();

		// ADD CONFIGED LOADER
		if (templatePathLoader != null)
		{
			tls.add(templatePathLoader);
		}

		// ADD WebappTemplateLoader
		String templateRoot = servletContext.getInitParameter("templateRoot");
		if (templateRoot == null)
		{
			templateRoot = servletContext.getInitParameter("TemplateRoot");
		}
		if (templateRoot == null)
		{
			templateRoot = "/";
		}
		logger.debug("templateRoot:--------" + templateRoot);
		tls.add(new WebappTemplateLoader(servletContext, templateRoot));

		// ADD ClassTemplateLoader
		ClassTemplateLoader ctl = new ClassTemplateLoader();
		tls.add(ctl);

		// ADD FileSystemTemplateLoader
		FileSystemTemplateLoader fstl = new FileSystemTemplateLoader();
		tls.add(fstl);

		// ADD ProtocolTemplateLoader
		ProtocolTemplateLoader prototl = new ProtocolTemplateLoader();
		prototl.addTemplateLoader("file", fstl);
		prototl.addTemplateLoader("class", ctl);
		tls.add(prototl);

		// MERGE ALL TemplateLoader
		MultiTemplateLoader loader = new MultiTemplateLoader(tls.toArray(new TemplateLoader[0]));
		return loader;

		/**
		 * <code>
		  return
		  templatePathLoader != null ? 
		  		new MultiTemplateLoader(new TemplateLoader[] 
		 				{ templatePathLoader, 
		  						new WebappTemplateLoader(servletContext), 
		  						new ClassTemplateLoader() })
		 				
		  				: new MultiTemplateLoader(new TemplateLoader[]
		  						{ new WebappTemplateLoader(servletContext),
		  								new ClassTemplateLoader() });
		  
		  </code>
		 */
	}

	static int mruMaxStrongSize;

	static String defaultEncoding;

	/**
	 * Create the instance of the freemarker Configuration object.
	 * <p/>
	 * this implementation
	 * <ul>
	 * <li>obtains the default configuration from
	 * Configuration.getDefaultConfiguration()
	 * <li>sets up template loading from a ClassTemplateLoader and a
	 * WebappTemplateLoader
	 * <li>sets up the object wrapper to be the BeansWrapper
	 * <li>loads settings from the classpath file /freemarker.properties
	 * </ul>
	 * 
	 * @param servletContext
	 */
	protected static Configuration createConfiguration(ServletContext servletContext) throws TemplateException
	{
		Configuration configuration = new Configuration();

		configuration.setTemplateLoader(getTemplateLoader(servletContext));

		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

		// FIXME
		configuration.setObjectWrapper(new BeansWrapper());

		if (mruMaxStrongSize > 0)
		{
			configuration.setSetting(freemarker.template.Configuration.CACHE_STORAGE_KEY, "strong:" + mruMaxStrongSize);
		}

		if (defaultEncoding != null)
		{
			configuration.setDefaultEncoding(defaultEncoding);
		}
		loadSettings(servletContext, configuration);
		return configuration;
	}

	/**
	 * Load the settings from the /freemarker.properties file on the classpath
	 * 
	 * @see freemarker.template.Configuration#setSettings for the definition of
	 *      valid settings
	 */
	protected static void loadSettings(ServletContext servletContext, Configuration configuration)
	{
		InputStream in = null;
		try
		{
			in = FileManager.loadFile("freemarker.properties", FreemarkerManager.class);

			if (in != null)
			{
				Properties p = new Properties();
				p.load(in);
				configuration.setSettings(p);
			}
		} catch (IOException e)
		{
			logger.error("Error while loading freemarker settings from /freemarker.properties", e);
		} catch (TemplateException e)
		{
			logger.error("Error while loading freemarker settings from /freemarker.properties", e);
		} finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException io)
				{
					logger.warn("Unable to close input stream", io);
				}
			}
		}
	}

	protected HttpRequestParametersHashModel createRequestParametersHashModel(HttpServletRequest request)
	{
		return new HttpRequestParametersHashModel(request);
	}

	void initializeSessionAndInstallModel(HttpServletRequest request, HttpServletResponse response,
			HttpSessionHashModel sessionModel, HttpSession session) throws ServletException, IOException
	{
		session.setAttribute(ATTR_SESSION_MODEL, sessionModel);
		initializeSession(request, response);
	}

	/**
	 * Called when servlet detects in a request processing that
	 * application-global (that is, ServletContext-specific) attributes are not
	 * yet set. This is a generic hook you might use in subclasses to perform a
	 * specific action on first request in the context. By default it does
	 * nothing.
	 * 
	 * @param request
	 *            the actual HTTP request
	 * @param response
	 *            the actual HTTP response
	 */
	protected void initializeServletContext(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
	}

	/**
	 * Called when servlet detects in a request processing that session-global
	 * (that is, HttpSession-specific) attributes are not yet set. This is a
	 * generic hook you might use in subclasses to perform a specific action on
	 * first request in the session. By default it does nothing. It is only
	 * invoked on newly created sessions; it is not invoked when a replicated
	 * session is reinstantiated in another servlet container.
	 * 
	 * @param request
	 *            the actual HTTP request
	 * @param response
	 *            the actual HTTP response
	 */
	protected void initializeSession(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
	}

	/**
	 * 
	 * @param action
	 * @param servletContext
	 * @param request
	 * @param response
	 * @return
	 * @throws TemplateModelException
	 */
	protected TemplateModel createModel(Object action, ServletContext servletContext, HttpServletRequest request,
			HttpServletResponse response) throws TemplateModelException
	{
		return createModel(getObjectWrapper(), action, servletContext, request, response);
	}

	/**
	 * 
	 * @param wrapper
	 * @param action
	 * @param servletContext
	 * @param request
	 * @param response
	 * @return
	 * @throws TemplateModelException
	 */
	protected TemplateModel createModel(ObjectWrapper wrapper, Object action, ServletContext servletContext,
			final HttpServletRequest request, final HttpServletResponse response) throws TemplateModelException
	{
		try
		{
			// CREATE ActionHashModel
			ActionHashModel actionHashModel = new ActionHashModel(action, getBeansWrapper());

			// AllScopesHashModel
			AllScopesHashModel params = new AllScopesHashModel(wrapper, actionHashModel, servletContext, request);

			// Create hash model wrapper for SERVLET CONTEXT (the application)
			ServletContextHashModel servletContextModel = (ServletContextHashModel) servletContext
					.getAttribute(ATTR_APPLICATION_MODEL);
			if (servletContextModel == null)
			{
				servletContextModel = new ServletContextHashModel(servletContext, wrapper);
				servletContext.setAttribute(ATTR_APPLICATION_MODEL, servletContextModel);
				TaglibFactory taglibs = new TaglibFactory(servletContext);
				servletContext.setAttribute(ATTR_JSP_TAGLIBS_MODEL, taglibs);
				initializeServletContext(request, response);
			}
			params.putUnlistedModel(KEY_APPLICATION, servletContextModel);
			params.putUnlistedModel(KEY_APPLICATION_PRIVATE, servletContextModel);
			params.putUnlistedModel(KEY_JSP_TAGLIBS, (TemplateModel) servletContext.getAttribute(ATTR_JSP_TAGLIBS_MODEL));

			// Create hash model wrapper for SESSION
			HttpSessionHashModel sessionModel;
			HttpSession session = request.getSession(false);
			if (session != null)
			{
				sessionModel = (HttpSessionHashModel) session.getAttribute(ATTR_SESSION_MODEL);
				if (sessionModel == null || sessionModel.isOrphaned(session))
				{
					sessionModel = new HttpSessionHashModel(session, wrapper);
					initializeSessionAndInstallModel(request, response, sessionModel, session);
				}
			} else
			{
				sessionModel = new HttpSessionHashModel(request, wrapper);
			}
			params.putUnlistedModel(KEY_SESSION, sessionModel);

			// Create hash model wrapper for REQUEST
			HttpRequestHashModel requestModel = (HttpRequestHashModel) request.getAttribute(ATTR_REQUEST_MODEL);
			if (requestModel == null || requestModel.getRequest() != request)
			{
				requestModel = new HttpRequestHashModel(request, wrapper);
				request.setAttribute(ATTR_REQUEST_MODEL, requestModel);
				request.setAttribute(ATTR_REQUEST_PARAMETERS_MODEL, createRequestParametersHashModel(request));
			}
			params.putUnlistedModel(KEY_REQUEST, requestModel);
			params.putUnlistedModel(KEY_INCLUDE, new IncludePage(request, response));
			params.putUnlistedModel(KEY_REQUEST_PRIVATE, requestModel);

			// Create hash model wrapper for REQUEST PARAMETERS
			HttpRequestParametersHashModel requestParametersModel = (HttpRequestParametersHashModel) request
					.getAttribute(ATTR_REQUEST_PARAMETERS_MODEL);
			params.putUnlistedModel(KEY_REQUEST_PARAMETERS, requestParametersModel);
			params.putUnlistedModel(KEY_ACTION, actionHashModel);

			// Create hash model wrapper for COOKIES
			HttpCookiesHashModel cookiesHashModel = new HttpCookiesHashModel(wrapper, request);
			params.put(KEY_COOKIES, cookiesHashModel);
			return params;
		} catch (ServletException e)
		{
			throw new TemplateModelException(e);
		} catch (IOException e)
		{
			throw new TemplateModelException(e);
		}
	}
}
