package com.xindian.awaits.id;

import java.io.Serializable;

import com.xindian.awaits.Session;
import com.xindian.awaits.exception.DBAccessException;

public interface IdentifierGenerator
{

	/**
	 * Generate a new identifier.
	 * 
	 * @param session
	 * @param object
	 *            the entity or toplevel collection for which the id is being
	 *            generated
	 * 
	 * @return a new identifier
	 * @throws DBAccessException
	 */
	public Serializable generate(Session session, Object object) throws DBAccessException;

}
