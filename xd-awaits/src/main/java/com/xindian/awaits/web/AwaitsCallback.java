package com.xindian.awaits.web;

import com.xindian.awaits.Session;
import com.xindian.awaits.exception.DBAccessException;

public interface AwaitsCallback<T>
{
	T doInAwaits(Session session) throws DBAccessException;
}