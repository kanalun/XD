package com.xindian.awaits.id;

import java.io.Serializable;
import java.util.Properties;

import com.xindian.awaits.Session;
import com.xindian.awaits.exception.DBAccessException;

public class Assigned implements IdentifierGenerator
{
	private String entityName;

	@Override
	public Serializable generate(Session session, Object object) throws DBAccessException
	{
		return null;
	}

	public Serializable generate2(Session session, Object obj) throws DBAccessException
	{
		final Serializable id = session.get(entityName, obj).getIdentifier(obj, session.getEntityMode());

		if (id == null)
		{
			throw new IdentifierGenerationException("ids for this class must be manually assigned before calling save(): "
					+ entityName);
		}

		return id;
	}

	public void configure(Type type, Properties params, Dialect d) throws MappingException
	{
		entityName = params.getProperty(ENTITY_NAME);
		if (entityName == null)
		{
			throw new MappingException("no entity name");
		}
	}
}
