package com.xindian.awaits;

import java.util.List;

public class Query
{
	private String sql;

	private List<Object> values;

	private int type;

	public String getSql()
	{
		return sql;
	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public List<Object> getValues()
	{
		return values;
	}

	public void setValues(List<Object> values)
	{
		this.values = values;
	}

}
