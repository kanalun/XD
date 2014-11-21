package com.xindian.awaits;

import java.util.HashMap;
import java.util.Map;

public class TypeTable
{
	/**
	 * <pre>
	 * 	Java 类型	JDBC 类型
	 * 	String 	VARCHAR 或 LONGVARCHAR 
	 * 	java.math.BigDecimal 	NUMERIC 
	 * 	boolean 	BIT 
	 * 	byte 	TINYINT 
	 * 	short 	SMALLINT 
	 * 	int 	INTEGER 
	 * 	long 	BIGINT 
	 * 	float 	REAL 
	 * 	double 	DOUBLE 
	 * 	byte[] 	VARBINARY 或 LONGVARBINARY 
	 * 	java.sql.Date 	DATE 
	 * 	java.sql.Time 	TIME 
	 * 	java.sql.Timestamp 	TIMESTAMP
	 * </pre>
	 */
	public Map<Class<?>, String> mx = new HashMap<Class<?>, String>();

	public String getSQLType(Class<?> javaType, int length)
	{
		return mx.get(javaType);
	}

	public void init()
	{

		mx.put(byte[].class, "VARBINARY");

		mx.put(java.lang.Boolean.class, "BIT");
		mx.put(java.lang.Byte.class, "TINYINT");
		mx.put(java.lang.Character.class, "CHAR");
		mx.put(java.lang.Short.class, "SMALLINT");
		mx.put(java.lang.Integer.class, "INTEGER");
		mx.put(java.lang.Long.class, "BIGINT");
		mx.put(java.lang.Float.class, "REAL");
		mx.put(java.lang.Double.class, "DOUBLE");

		mx.put(Boolean.TYPE, "BIT");
		mx.put(Byte.TYPE, "TINYINT");
		mx.put(Character.TYPE, "CHAR");
		mx.put(Short.TYPE, "SMALLINT");
		mx.put(java.lang.Integer.TYPE, "INTEGER");
		mx.put(Long.TYPE, "BIGINT");
		mx.put(Float.TYPE, "REAL");
		mx.put(Double.TYPE, "DOUBLE");

		mx.put(java.math.BigDecimal.class, "NUMERIC");
		mx.put(java.lang.String.class, "VARCHAR");
		mx.put(java.sql.Date.class, "DATE");
		mx.put(java.sql.Time.class, "TIME");
		mx.put(java.sql.Timestamp.class, "TIMESTAMP");
		mx.put(java.util.Date.class, "DATETIME");
	}
}
