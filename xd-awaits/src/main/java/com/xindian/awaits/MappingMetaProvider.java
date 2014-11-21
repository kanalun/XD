package com.xindian.awaits;

import com.xindian.awaits.exception.MappingException;

public interface MappingMetaProvider
{
	void addClass(Class<?> persistentClass) throws MappingException;

	TableMeta<?> reomveClass(Class<?> persistentClass) throws MappingException;

	TableMeta<?> getTableMeta(Class<?> persistentClass) throws MappingException;
}
