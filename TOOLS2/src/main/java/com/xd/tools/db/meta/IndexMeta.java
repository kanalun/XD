package com.xd.tools.db.meta;

/**
 * String COLUMN_NAME = indexSet.getString("COLUMN_NAME");
 * 
 * String INDEX_NAME = indexSet.getString("INDEX_NAME");
 * 
 * boolean NON_UNIQUE = indexSet.getBoolean("NON_UNIQUE");
 * 
 * short TYPE = indexSet.getShort("TYPE");
 * 
 * short ORDINAL_POSITION = indexSet.getShort("ORDINAL_POSITION");
 * 
 * @author QCC
 * 
 */
public class IndexMeta implements DatabaseMeta
{
	public String INDEX_NAME;

	public IndexMeta(String INDEX_NAME)
	{
		this.INDEX_NAME = INDEX_NAME;
	}
}