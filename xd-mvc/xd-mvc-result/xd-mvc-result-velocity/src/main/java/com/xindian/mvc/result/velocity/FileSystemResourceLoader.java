package com.xindian.mvc.result.velocity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 * 直接利用文件路径在文件系统中<br/>
 * 
 * 来搜索资源文件<br/>
 * 
 * D:\\velocity\\test.vm
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class FileSystemResourceLoader extends ResourceLoader
{
	@Override
	public long getLastModified(Resource resource)
	{
		File file = new File(resource.getName());
		if (file.canRead())
		{
			return file.lastModified();
		} else
		{
			return 0;
		}
	}

	@Override
	public InputStream getResourceStream(String name) throws ResourceNotFoundException
	{
		InputStream result = null;

		if (name == null || name.length() == 0)
		{
			throw new ResourceNotFoundException("FileSystemResourceLoader: No template name provided");
		}
		try
		{
			result = new FileInputStream(name);
			return result;
		} catch (FileNotFoundException e)
		{
			throw new ResourceNotFoundException("FileSystemResourceLoader: can not find file at[" + name + "]");
		}
	}

	@Override
	public void init(ExtendedProperties configuration)
	{
		if (log.isTraceEnabled())
		{
			log.trace("FileSystemResourceLoader: initialization complete.");
		}
		// DO_NOTHING
	}

	@Override
	public boolean isSourceModified(Resource resource)
	{
		File cachedFile = getCachedFile(resource.getName());
		if (!cachedFile.exists())
		{
			/* file has been moved and/or deleted */
			return true;
		}
		/* then (and only then) do we compare the last modified values */
		return (cachedFile.lastModified() != resource.getLastModified());

	}

	private File getCachedFile(String fileName)
	{
		return new File(fileName);
	}
}
