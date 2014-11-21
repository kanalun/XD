package com.xindian.mvc.result.velocity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 * 这是一个代理类,他包装了常用的
 * 
 * @author Elva
 * @date 2011-1-19
 * @version 1.0
 */
public class MVCResourceLoader extends ResourceLoader
{
	private Map<String, ResourceLoader> resourceLoaders = new HashMap<String, ResourceLoader>();

	@Override
	public InputStream getResourceStream(String source) throws ResourceNotFoundException
	{
		return null;
	}

	@Override
	public long getLastModified(Resource resource)
	{
		return 0;
	}

	@Override
	public void init(ExtendedProperties configuration)
	{

	}

	@Override
	public boolean isSourceModified(Resource resource)
	{
		return false;
	}
}
