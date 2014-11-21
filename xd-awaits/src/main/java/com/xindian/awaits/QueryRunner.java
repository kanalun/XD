package com.xindian.awaits;

import java.sql.Connection;

public class QueryRunner
{
	private boolean active;

	private Connection connection;

	public QueryRunner(Connection connection)
	{
		this.connection = connection;
	}

	public void run(Query query)
	{

	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

}
