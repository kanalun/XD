package com.xindian.awaits;

public class Order
{
	private String order = "ASC";

	private String columnName;

	public static Order asc(String columnName)
	{
		return new Order(columnName, "ASC");
	}

	public static Order desc(String columnName)
	{
		return new Order(columnName, "DESC");
	}

	private Order(String columnName, String order)
	{
		this.order = order;
		this.columnName = columnName;
	}

	public String getSubSql()
	{
		return this.columnName + " " + this.order;
	}
}
