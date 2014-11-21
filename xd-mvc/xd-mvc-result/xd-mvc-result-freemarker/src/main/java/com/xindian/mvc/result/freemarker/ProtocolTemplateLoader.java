package com.xindian.mvc.result.freemarker;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xindian.mvc.utils.Validator;

import freemarker.cache.TemplateLoader;

/**
 * 使用协议访问模板资源
 * 
 * <pre>
 * file://D://file.ftl
 * 
 * class://com.kan.mvc.template.t.ftl TODO
 * 
 * class://com/kan/mvc/template/t.ftl TODO
 * 
 * http://192.192.1.2/t/file.ftl TODO
 * 
 * ftp://192.192.1.2/t/file.ftl TODO
 * 
 * zip://com/kan/mvc/template/t.ftl TODO
 * </pre>
 * 
 * @author Elva
 * 
 */
public class ProtocolTemplateLoader implements TemplateLoader
{
	// 存放协议和TemplateLoader的地方
	// Collections.synchronizedMap(new HashMap<String, TemplateLoader>());
	private final Map<String, TemplateLoader> protocoledTemplateLoaders = new ConcurrentHashMap<String, TemplateLoader>();

	/**
	 * 添加一个TemplateLoader
	 * 
	 * @param protocol
	 *            TemplateLoader对应的协议
	 * @param templateLoader
	 *            TemplateLoader对应的协议
	 */
	public void addTemplateLoader(String protocol, TemplateLoader templateLoader)
	{
		protocoledTemplateLoaders.put(protocol, templateLoader);
	}

	/**
	 * 
	 * @param protocol
	 */
	public void removeTemplateLoader(String protocol)
	{
		protocoledTemplateLoaders.remove(protocol);
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException
	{
		((MultiSource) templateSource).close();
	}

	@Override
	public Object findTemplateSource(String nameWithProtocol) throws IOException
	{
		String p = getProtocol(nameWithProtocol);
		String c = getContent(nameWithProtocol);
		// System.out.println("P: " + p + "	C: " + c);

		if (Validator.allNotBlank(p, c))
		{
			TemplateLoader loader = protocoledTemplateLoaders.get(p);
			// System.out.println(loader);
			if (Validator.notNull(loader))
			{
				Object source = loader.findTemplateSource(c);
				if (source != null)
				{
					return new MultiSource(source, loader);
				}
			}
		}
		return null;
	}

	@Override
	public long getLastModified(Object templateSource)
	{
		return ((MultiSource) templateSource).getLastModified();
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException
	{
		return ((MultiSource) templateSource).getReader(encoding);
	}

	public void clear()
	{
		protocoledTemplateLoaders.clear();
	}

	/**
	 * 深度清理,包含子元素
	 */
	@Deprecated
	protected void deepClear()
	{
		for (TemplateLoader t : protocoledTemplateLoaders.values())
		{
			if (t instanceof ProtocolTemplateLoader)
			{
				((ProtocolTemplateLoader) t).deepClear();
			}
		}
		protocoledTemplateLoaders.clear();
	}

	//
	private static String getProtocol(String nameWithProtocol)
	{
		try
		{
			return nameWithProtocol.substring(0, nameWithProtocol.indexOf("://"));
		} catch (StringIndexOutOfBoundsException e)
		{
			return null;
		}
	}

	private static String getContent(String nameWithProtocol)
	{
		try
		{
			return nameWithProtocol.substring(nameWithProtocol.indexOf("://") + 3, nameWithProtocol.length());
		} catch (StringIndexOutOfBoundsException e)
		{
			return null;
		}
	}

	private static final class MultiSource
	{
		private final Object source;
		private final TemplateLoader loader;

		MultiSource(Object source, TemplateLoader loader)
		{
			this.source = source;
			this.loader = loader;
		}

		long getLastModified()
		{
			return loader.getLastModified(source);
		}

		Reader getReader(String encoding) throws IOException
		{
			return loader.getReader(source, encoding);
		}

		void close() throws IOException
		{
			loader.closeTemplateSource(source);
		}

		public boolean equals(Object o)
		{
			if (o instanceof MultiSource)
			{
				MultiSource m = (MultiSource) o;
				return m.loader.equals(loader) && m.source.equals(source);
			}
			return false;
		}

		public int hashCode()
		{
			return loader.hashCode() + 31 * source.hashCode();
		}

		public String toString()
		{
			return source.toString();
		}
	}
}
