package com.xindian.mvc.result.velocity;

import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.xindian.commons.utils.ClassLoaderUtils;

/**
 * Loads resource from the Thread's context ClassLoader.
 */
public class ClassResourceLoader extends ClasspathResourceLoader
{
	public synchronized InputStream getResourceStream(String name) throws ResourceNotFoundException
	{
		if ((name == null) || (name.length() == 0))
		{
			throw new ResourceNotFoundException("No template name provided");
		}
		if (name.startsWith("/"))
		{
			name = name.substring(1);
		}
		try
		{
			return ClassLoaderUtils.getResourceAsStream(name, ClassResourceLoader.class);
		} catch (Exception e)
		{
			throw new ResourceNotFoundException(e.getMessage());
		}
	}
}
