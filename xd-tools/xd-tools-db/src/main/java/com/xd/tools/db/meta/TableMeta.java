package com.xd.tools.db.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiucongcong
 */
public class TableMeta implements DatabaseMeta
{
	public String tableName;

	/** TABLE_CAT */
	public String TABLE_CAT;

	/** TABLE_SCHEM */
	public String TABLE_SCHEM;

	/** 表注释 */
	public String REMARKS;

	public PrimaryKeyMeta primaryKey;

	public List<ColumnMeta> columns = new ArrayList<ColumnMeta>();

	public TableMeta(String tableName)
	{
		this.tableName = tableName;
	}
}