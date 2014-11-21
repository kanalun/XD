package com.xindian.awaits;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.xindian.awaits.exception.DBAccessException;

/**
 * DB Transaction Support
 * 
 * @author Elva
 * 
 */
public class Transaction
{
	private Connection connection;

	private boolean wasRolledBack = false;

	private boolean wasCommitted = false;

	private boolean isActive = false;

	private final boolean autoCommit;// FIXME

	Transaction(Connection connection)
	{
		this(connection, true);
	}

	private final String name;

	Transaction(Connection connection, boolean autoCommit)
	{
		synchronized (id)
		{
			id++;
		}
		this.name = "Savepoint_" + id;

		this.autoCommit = autoCommit;
		this.connection = connection;
	}

	private static Integer id = 0;

	private Savepoint savepoint;

	public void begin() throws DBAccessException
	{
		try
		{
			connection.setAutoCommit(false);
			savepoint = connection.setSavepoint(name);
			isActive = true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new DBAccessException("can not start this transaction", e);
		}
	}

	public void commit() throws DBAccessException
	{
		try
		{
			connection.commit();
			wasCommitted = true;
			connection.setAutoCommit(autoCommit);// 恢复原来的事务设置
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new DBAccessException("can not commit this transaction", e);
		} finally
		{
			isActive = false;
		}

	}

	public void rollback() throws DBAccessException
	{
		try
		{
			connection.rollback(savepoint);
			wasRolledBack = false;
			connection.setAutoCommit(autoCommit);// 恢复原来的事务设置
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new DBAccessException("can not rollback this transaction", e);
		} finally
		{
			isActive = false;
		}
	}

	public boolean wasRolledBack() throws DBAccessException
	{
		return wasRolledBack;
	}

	public boolean wasCommitted() throws DBAccessException
	{
		return wasCommitted;
	}

	public boolean isActive() throws DBAccessException
	{
		return isActive;
	}

	int timeoutSeconds;

	public void setTimeout(int seconds)
	{
		this.timeoutSeconds = seconds;
	}
}
