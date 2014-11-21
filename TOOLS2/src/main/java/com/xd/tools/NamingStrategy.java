package com.xd.tools;

/**
 * @author QCC
 * 
 *         2014-4-10
 */
public interface NamingStrategy
{
	public String propertyToColumnName(String propertyName);

	/**
	 * @param propertyName
	 * @return
	 */
	public String columnNameToProperty(String columnName);

	public String classToTableName(String className);

	public String tableNameToClass(String tableName);

}
