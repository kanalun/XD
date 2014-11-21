package com.xindian.awaits;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.exception.MappingException;

public class MappingMetaProviderImpl implements MappingMetaProvider
{
	MappingMetaProviderImpl()
	{

	}

	private static Logger logger = LoggerFactory.getLogger(MappingMetaProviderImpl.class);

	private HashMap<Class<?>, TableMeta<?>> ts = new HashMap<Class<?>, TableMeta<?>>();

	private boolean runtimeMapping = false;// 运行时映射,如果开启这个,用户不需在程序初始化的时候addClass,又需要的时候程序会自动加载映射信息

	public TableMeta<?> reomveClass(Class<?> persistentClass) throws MappingException
	{
		logger.info("Reading mappings from resource: " + persistentClass);
		return ts.remove(persistentClass);
	}

	public void addClass(Class<?> persistentClass) throws MappingException
	{
		TableMeta<?> tableMeta = TableMeta.getTableMeta(persistentClass);
		// logger.debug("\n" + tableMeta.getDDL());
		ts.put(persistentClass, tableMeta);
		logger.info("Reading mappings from resource: " + persistentClass);
	}

	public TableMeta<?> getTableMeta(Class<?> persistentClass) throws MappingException
	{
		TableMeta<?> tableMeta = ts.get(persistentClass);
		if (tableMeta == null && runtimeMapping)
		{
			this.addClass(persistentClass);
			tableMeta = ts.get(persistentClass);
		}
		return tableMeta;
	}
}
