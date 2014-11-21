package com.xindian.mvc.result.velocity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.util.SimplePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.Mapping;
import com.xindian.mvc.exception.MVCException;

/**
 * Manages the environment for Velocity result types
 * 
 * Velocity管理器
 */
public class VelocityManager
{
	private static final Logger logger = LoggerFactory.getLogger(VelocityManager.class);
	/**
	 * The context key for the HTTP action object.
	 */
	public static final String ACTION = "act";

	/**
	 * The context key for the HTTP request object.
	 */
	public static final String REQUEST = "req";

	/**
	 * The context key for the HTTP response object.
	 */
	public static final String RESPONSE = "res";

	/**
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
	 * 
	 */
	private VelocityEngine velocityEngine;

	/**
	 * Names of contexts that will be chained on every request
	 */
	private String[] chainedContextNames;

	/**
	 * 
	 */
	private Properties velocityProperties;

	/**
	 * 
	 */
	private String customConfigFile;

	/**
	 * to reuse the VelocityWriter
	 */
	private static SimplePool writerPool = new SimplePool(40);

	/**
	 * 返回一个Template
	 * 
	 * @param location
	 *            支持"协议"方式,file://d:/d.vm
	 * 
	 *            class://
	 * @return
	 */
	public Template getTemplate(String location)
	{
		//
		return null;
	}

	private VelocityManager()
	{
	}

	private static VelocityManager instance;

	/**
	 * retrieve an instance to the current VelocityManager
	 */
	public synchronized static VelocityManager getInstance()
	{
		if (instance == null)
		{
			instance = new VelocityManager();
		}
		return instance;
	}

	/**
	 * @return a reference to the VelocityEngine used by <b>all</b> struts
	 *         velocity thingies with the exception of directly accessed *.vm
	 *         pages
	 */
	public VelocityEngine getVelocityEngine()
	{
		return velocityEngine;
	}

	protected VelocityContext[] prepareChainedContexts(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			Map<?, ?> extraContext)
	{
		return null;
	}

	/**
	 * init
	 * 
	 * @param context
	 */
	public synchronized void init(ServletContext context)
	{
		if (velocityEngine == null)
		{
			velocityEngine = createVelocityEngine(context);
		}
	}

	/**
	 * 从 ServletContext中获得配置
	 * 
	 * @param context
	 * @return
	 */
	public Properties loadConfiguration(ServletContext context)
	{
		if (context == null)
		{
			String gripe = "Error attempting to create a loadConfiguration from a null ServletContext!";
			logger.error(gripe);
			throw new IllegalArgumentException(gripe);
		}

		Properties properties = new Properties();

		// now apply our systemic defaults, then allow user to override
		applyDefaultConfiguration(context, properties);

		String defaultUserDirective = properties.getProperty("userdirective");

		/**
		 * if the user has specified an external velocity configuration file,
		 * 
		 * we'll want to search for it in the following order
		 * 
		 * 1. relative to the context path
		 * 
		 * 2. relative to /WEB-INF
		 * 
		 * 3. in the class path
		 */
		String configfile;

		if (customConfigFile != null)
		{
			configfile = customConfigFile;
		} else
		{
			configfile = "velocity.properties";
		}

		configfile = configfile.trim();

		InputStream in = null;
		String resourceLocation = null;

		try
		{
			if (context.getRealPath(configfile) != null)
			{
				// 1. relative to context path, i.e. /velocity.properties
				String filename = context.getRealPath(configfile);

				if (filename != null)
				{
					File file = new File(filename);

					if (file.isFile())
					{
						resourceLocation = file.getCanonicalPath() + " from file system";
						in = new FileInputStream(file);
					}

					// 2. if nothing was found relative to the context path,
					// search relative to the WEB-INF directory
					if (in == null)
					{
						file = new File(context.getRealPath("/WEB-INF/" + configfile));

						if (file.isFile())
						{
							resourceLocation = file.getCanonicalPath() + " from file system";
							in = new FileInputStream(file);
						}
					}
				}
			}

			// 3. finally, if there's no physical file, how about something in
			// our classpath
			if (in == null)
			{
				in = VelocityManager.class.getClassLoader().getResourceAsStream(configfile);
				if (in != null)
				{
					resourceLocation = configfile + " from classloader";
				}
			}

			// if we've got something, load 'er up
			if (in != null)
			{
				logger.info("Initializing velocity using " + resourceLocation);
				properties.load(in);
			}
		} catch (IOException e)
		{
			logger.warn("Unable to load velocity configuration " + resourceLocation, e);
		} finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
				}
			}
		}

		// overide with programmatically set properties
		if (this.velocityProperties != null)
		{
			Iterator keys = this.velocityProperties.keySet().iterator();
			while (keys.hasNext())
			{
				String key = (String) keys.next();
				properties.setProperty(key, this.velocityProperties.getProperty(key));
			}
		}

		String userdirective = properties.getProperty("userdirective");

		if ((userdirective == null) || userdirective.trim().equals(""))
		{
			userdirective = defaultUserDirective;
		} else
		{
			userdirective = userdirective.trim() + "," + defaultUserDirective;
		}

		properties.setProperty("userdirective", userdirective);

		// for debugging purposes, allows users to dump out the properties that
		// have been configured
		if (logger.isDebugEnabled())
		{
			logger.debug("Initializing Velocity with the following properties ...");

			for (Iterator iter = properties.keySet().iterator(); iter.hasNext();)
			{
				String key = (String) iter.next();
				String value = properties.getProperty(key);

				if (logger.isDebugEnabled())
				{
					logger.debug("    '" + key + "' = '" + value + "'");
				}
			}
		}

		return properties;
	}

	public void setChainedContexts(String contexts)
	{
		// we expect contexts to be a comma separated list of classnames
		StringTokenizer st = new StringTokenizer(contexts, ",");
		List<String> contextList = new ArrayList<String>();

		while (st.hasMoreTokens())
		{
			String classname = st.nextToken();
			contextList.add(classname);
		}
		if (contextList.size() > 0)
		{
			String[] chainedContexts = new String[contextList.size()];
			contextList.toArray(chainedContexts);
			this.chainedContextNames = chainedContexts;
		}
	}

	/**
	 * <p/>
	 * Instantiates a new VelocityEngine.
	 * </p>
	 * <p/>
	 * The following is the default Velocity configuration
	 * </p>
	 * 
	 * <pre>
	 *  resource.loader = file, class
	 *  file.resource.loader.path = real path of webapp
	 *  class.resource.loader.description = Velocity Classpath Resource Loader
	 *  class.resource.loader.class = org.apache.struts2.views.velocity.StrutsResourceLoader
	 * </pre>
	 * <p/>
	 * this default configuration can be overridden by specifying a
	 * struts.velocity.configfile property in the struts.properties file. the
	 * specified config file will be searched for in the following order:
	 * </p>
	 * <ul>
	 * <li>relative to the servlet context path</li>
	 * <li>relative to the WEB-INF directory</li>
	 * <li>on the classpath</li>
	 * </ul>
	 * 
	 * @param context
	 *            the current ServletContext. may <b>not</b> be null
	 */
	protected VelocityEngine createVelocityEngine(ServletContext context) throws MVCException
	{
		if (context == null)
		{
			String gripe = "Error attempting to create a new VelocityEngine from a null ServletContext!";
			logger.error(gripe);
			throw new IllegalArgumentException(gripe);
		}
		Properties p = loadConfiguration(context);

		VelocityEngine velocityEngine = new VelocityEngine();

		// Set the velocity attribute for the servlet context
		// if this is not set the webapp loader WILL NOT WORK
		velocityEngine.setApplicationAttribute(ServletContext.class.getName(), context);
		try
		{
			velocityEngine.init(p);
		} catch (Exception e)
		{
			String gripe = "Unable to instantiate VelocityEngine!";
			throw new MVCException(gripe, e);
		}

		return velocityEngine;
	}

	/**
	 * once we've loaded up the user defined configurations, we will want to
	 * apply Struts specification configurations.
	 * <ul>
	 * <li>if Velocity.RESOURCE_LOADER has not been defined, then we will use
	 * the defaults which is a joined file, class loader for unpackaed wars and
	 * a straight class loader otherwise</li>
	 * <li>we need to define the various Struts custom user directives such as
	 * #param, #tag, and #bodytag</li>
	 * </ul>
	 * 
	 * @param context
	 * @param p
	 */
	private void applyDefaultConfiguration(ServletContext context, Properties p)
	{
		// ensure that caching isn't overly aggressive

		/**
		 * Load a default resource loader definition if there isn't one present.
		 * Ben Hall (22/08/2003)
		 */
		if (p.getProperty(Velocity.RESOURCE_LOADER) == null)
		{
			p.setProperty(Velocity.RESOURCE_LOADER, "strutsfile, strutsclass");
		}

		/**
		 * If there's a "real" path add it for the strutsfile resource loader.
		 * If there's no real path and they haven't configured a loader then we
		 * change resource loader property to just use the strutsclass loader
		 * Ben Hall (22/08/2003)
		 */
		if (context.getRealPath("") != null)
		{
			p.setProperty("strutsfile.resource.loader.description", "Velocity File Resource Loader");
			p.setProperty("strutsfile.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			p.setProperty("strutsfile.resource.loader.path", context.getRealPath(""));
			p.setProperty("strutsfile.resource.loader.modificationCheckInterval", "2");
			p.setProperty("strutsfile.resource.loader.cache", "true");
		} else
		{
			// remove strutsfile from resource loader property
			String prop = p.getProperty(Velocity.RESOURCE_LOADER);
			if (prop.indexOf("strutsfile,") != -1)
			{
				prop = replace(prop, "strutsfile,", "");
			} else if (prop.indexOf(", strutsfile") != -1)
			{
				prop = replace(prop, ", strutsfile", "");
			} else if (prop.indexOf("strutsfile") != -1)
			{
				prop = replace(prop, "strutsfile", "");
			}

			p.setProperty(Velocity.RESOURCE_LOADER, prop);
		}

		/**
		 * Refactored the Velocity templates for the Struts taglib into the
		 * classpath from the web path. This will enable Struts projects to have
		 * access to the templates by simply including the Struts jar file.
		 * Unfortunately, there does not appear to be a macro for the class
		 * loader keywords Matt Ho - Mon Mar 17 00:21:46 PST 2003
		 */
		p.setProperty("strutsclass.resource.loader.description", "Velocity Classpath Resource Loader");
		p.setProperty("strutsclass.resource.loader.class", "org.apache.struts2.views.velocity.StrutsResourceLoader");
		p.setProperty("strutsclass.resource.loader.modificationCheckInterval", "2");
		p.setProperty("strutsclass.resource.loader.cache", "true");

		// components
		StringBuilder sb = new StringBuilder();

		String directives = sb.toString();

		String userdirective = p.getProperty("userdirective");
		if ((userdirective == null) || userdirective.trim().equals(""))
		{
			userdirective = directives;
		} else
		{
			userdirective = userdirective.trim() + "," + directives;
		}

		p.setProperty("userdirective", userdirective);
	}

	private void addDirective(StringBuilder sb, Class clazz)
	{
		sb.append(clazz.getName()).append(",");
	}

	private static final String replace(String string, String oldString, String newString)
	{
		if (string == null)
		{
			return null;
		}
		// If the newString is null, just return the string since there's
		// nothing to replace.
		if (newString == null)
		{
			return string;
		}
		int i = 0;
		// Make sure that oldString appears at least once before doing any
		// processing.
		if ((i = string.indexOf(oldString, i)) >= 0)
		{
			// Use char []'s, as they are more efficient to deal with.
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			// Replace all remaining instances of oldString with newString.
			while ((i = string.indexOf(oldString, i)) > 0)
			{
				buf.append(string2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(string2, j, string2.length - j);
			return buf.toString();
		}
		return string;
	}

	protected Template getTemplate(VelocityEngine velocity, Mapping mapping, String location, String encoding) throws Exception
	{
		// 文档位置相对于namespace的位置
		if (!location.startsWith("/"))// 不使用root相对,对路径
		{
			location = mapping.getNamespace() + "/" + location;// 相对于
		}
		Template template = velocity.getTemplate(location, encoding);

		return template;
	}

	String defaultEncoding;

	protected String getEncoding(String templateLocation)
	{
		String encoding = defaultEncoding;
		if (encoding == null)
		{
			encoding = System.getProperty("file.encoding");
		}
		if (encoding == null)
		{
			encoding = "UTF-8";
		}
		return encoding;
	}

	public void write(Context context, Template template, Writer writer) throws IOException
	{
		write(context, template, writer, DEFAULT_BUFFFER_SIZE);
	}

	private static int DEFAULT_BUFFFER_SIZE = 1024 * 8;// 8k

	public void write(Context context, Template template, OutputStream out, String encoding, int bufferSize) throws IOException
	{
		Writer writet = new OutputStreamWriter(out, encoding);
		write(context, template, writet, bufferSize);
	}

	public void write(Context context, Template template, OutputStream out, String encoding) throws IOException
	{
		Writer writet = new OutputStreamWriter(out, encoding);
		write(context, template, writet, DEFAULT_BUFFFER_SIZE);
	}

	/**
	 * 将模型和模板合并后写入指定的writer,这个Writer会被
	 * 
	 * @param context
	 * @param template
	 * @param writer
	 * @param bufferSize
	 * @throws IOException
	 */
	public void write(Context context, Template template, Writer writer, int bufferSize) throws IOException
	{
		VelocityWriter velocityWriter = (VelocityWriter) writerPool.get();
		if (velocityWriter == null)
		{
			velocityWriter = new VelocityWriter(writer, bufferSize, true);
			logger.debug("NEW A VelocityWriter:[" + velocityWriter + "]");
		} else
		{
			velocityWriter.recycle(writer);
			logger.debug("RECYCLE A VelocityWriter:[" + velocityWriter + "]");
		}
		template.merge(context, velocityWriter);
		if (velocityWriter != null)
		{
			try
			{
				velocityWriter.flush();
			} catch (IOException e)
			{
				// do nothing
				e.printStackTrace();
			} finally
			{
				velocityWriter.recycle(null);
				writerPool.put(velocityWriter);
			}
		}
	}

	/**
	 * @return the velocityProperties
	 */
	public Properties getVelocityProperties()
	{
		return velocityProperties;
	}

	/**
	 * @param velocityProperties
	 *            the velocityProperties to set
	 */
	public void setVelocityProperties(Properties velocityProperties)
	{
		this.velocityProperties = velocityProperties;
	}
}
