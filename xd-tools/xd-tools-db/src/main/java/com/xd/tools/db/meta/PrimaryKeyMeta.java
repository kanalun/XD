package com.xd.tools.db.meta;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 主键
 * 
 * @author QCC
 */
public class PrimaryKeyMeta
{
	public PrimaryKeyMeta(String PK_NAME)
	{
		this.PK_NAME = PK_NAME;
	}

	public String PK_NAME;

	public Set<String> COLUMN_NAMES = new LinkedHashSet<String>();

	public static void addColumn(String columName)
	{

	}

	@Override
	public String toString()
	{
		return "PrimaryKey [PK_NAME=" + PK_NAME + ", COLUMN_NAMES=" + COLUMN_NAMES + "]";
	}

}