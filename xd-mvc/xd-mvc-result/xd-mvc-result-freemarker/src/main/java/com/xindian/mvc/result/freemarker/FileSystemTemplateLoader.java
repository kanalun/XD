package com.xindian.mvc.result.freemarker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import freemarker.cache.TemplateLoader;

/**
 * Use file path such as "D://file.ftl" to Load Template from the local
 * FileSystem
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class FileSystemTemplateLoader implements TemplateLoader
{
	@SuppressWarnings("unchecked")
	@Override
	public Object findTemplateSource(final String fileName) throws IOException
	{
		try
		{
			return AccessController.doPrivileged(new PrivilegedExceptionAction()
			{
				public Object run() throws IOException
				{
					File source = new File(fileName);
					if (!source.isFile())
					{
						return null;
					}
					return source;
				}
			});
		} catch (PrivilegedActionException e)
		{
			e.printStackTrace();
			throw (IOException) e.getException();
		}
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException
	{
		// DO_NOTING
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getLastModified(final Object templateSource)
	{
		return ((Long) (AccessController.doPrivileged(new PrivilegedAction()
		{
			public Object run()
			{
				return new Long(((File) templateSource).lastModified());
			}
		}))).longValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Reader getReader(final Object templateSource, final String encoding) throws IOException
	{
		try
		{
			return (Reader) AccessController.doPrivileged(new PrivilegedExceptionAction()
			{
				public Object run() throws IOException
				{
					if (!(templateSource instanceof File))
					{
						throw new IllegalArgumentException("templateSource is a: " + templateSource.getClass().getName());
					}
					return new InputStreamReader(new FileInputStream((File) templateSource), encoding);
				}
			});
		} catch (PrivilegedActionException e)
		{
			throw (IOException) e.getException();
		}
	}
}
