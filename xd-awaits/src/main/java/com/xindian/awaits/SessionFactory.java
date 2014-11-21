package com.xindian.awaits;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import com.xindian.awaits.exception.DBAccessException;

public class SessionFactory
{
	private HashMap<Class<?>, TableMeta<?>> tableMetas;

	private DataSource dataSource;

	private Configuration configuration;

	boolean close = false;

	SessionFactory(Configuration configuration, DataSource dataSource, HashMap<Class<?>, TableMeta<?>> tableMetas)
	{
		this.dataSource = dataSource;
		this.tableMetas = tableMetas;
		this.configuration = configuration;
	}

	public Session openSession(Connection connection) throws DBAccessException
	{
		preventClose();
		Session session = new Session(configuration, connection, tableMetas);
		return session;
	}

	public Session openSession(String area) throws DBAccessException
	{
		preventClose();
		Session session = null;
		try
		{
			session = new Session(configuration, this.dataSource.getConnection(), tableMetas);
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new DBAccessException(e);
		}
		return session;
	}

	public Session openSession() throws DBAccessException
	{
		preventClose();
		Session session = null;
		try
		{
			session = new Session(configuration, this.dataSource.getConnection(), tableMetas);
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new DBAccessException(e);
		}
		return session;
	}

	private void preventClose() throws DBAccessException
	{
		if (close)
		{
			throw new DBAccessException("This SessionFactory Is Closed");
		}
	}

	public void close() throws DBAccessException
	{
		// TODO Clean Up The Connections
		close = true;
	}

	public boolean isClosed()
	{
		return close;
	}
}
