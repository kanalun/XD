package com.xd.tools;

import java.math.BigDecimal;
import java.util.Date;

import com.xd.tools.db.meta.ColumnMeta;

/**
 * 
 <table style="" border="1" cellpadding="0">
 * <tbody>
 * <tr>
 * <td >
 * <p align="center">
 * <span style="color: #000000;"><strong><span
 * style="font-size: 12pt; font-family: 宋体;" lang="EN-US">Java </span> </strong>
 * <strong><span style="font-size: 12pt; font-family: 宋体;">数据类型</span> </strong>
 * </span>
 * </p>
 * </td>
 * <td >
 * <p align="center">
 * <span style="color: #000000;"><strong><span
 * style="font-size: 12pt; font-family: 宋体;" lang="EN-US">JDBC </span> </strong>
 * <strong><span style="font-size: 12pt; font-family: 宋体;">数据类型</span> </strong>
 * </span>
 * </p>
 * </td>
 * <td >
 * <p align="center">
 * <span style="color: #000000;"><strong><span
 * style="font-size: 12pt; font-family: 宋体;" lang="EN-US">Oracle SQL </span>
 * </strong> <strong><span style="font-size: 12pt; font-family: 宋体;">数据类型（<span
 * lang="EN-US">8i </span> 版）</span> </strong> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">boolean</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">BIT</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">byte</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">TINYINT</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">short</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">SMALLINT</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">int</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">INTEGER</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">long</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">BIGINT</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">double</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">FLOAT</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">float</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">REAL</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">double</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">DOUBLE</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">java.math.BigDecimal</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMERIC</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">java.math.BigDecimal</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">DECIMAL</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">String</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">CHAR</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">CHAR</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">String</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">VARCHAR</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">VARCHAR2</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">String</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">LONGVARCHAR</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">LONG</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">java.sql.Date</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">DATE</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">DATE</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">java.sql.Time</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">TIME</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">DATE</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">java.sql.Timestamp</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">TIMESTAMP</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">DATE</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">byte[]</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">BINARY</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">NUMBER</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">byte[]</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">VARBINARY</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">RAW</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">byte[]</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">LONGVARBINARY</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">LONGRAW</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">java.sql.Blob</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">BLOB</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">BLOB</span> </span>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">java.sql.Clob</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">CLOB</span> </span>
 * </p>
 * </td>
 * <td>
 * <p align="left">
 * <span style="font-size: 12pt; font-family: 宋体;" lang="EN-US"><span
 * style="color: #000000;">CLOB</span> </span>
 * </p>
 * </td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @author qiucongcong
 * 
 *         2014-4-10
 */
public class OrcaleTypes
{
	static class Unknow
	{

	}

	public static Class<?> getType(String orcaleName)
	{
		if ("VARCHAR2".equalsIgnoreCase(orcaleName))
		{
			return String.class;
		}
		if ("VARCHAR".equalsIgnoreCase(orcaleName))
		{
			return String.class;
		}
		if ("NUMBER".equalsIgnoreCase(orcaleName))
		{
			return BigDecimal.class;
		}
		if ("DATE".equalsIgnoreCase(orcaleName))
		{
			return Date.class;
		}
		if ("CHAR".equalsIgnoreCase(orcaleName))
		{
			return String.class;
		}
		return Unknow.class;
	}

	public static String getJdbcType(ColumnMeta columnMeta)
	{
		// TODO
		return "TODO";
	}

	public static String getJdbcType(String orcaleType)
	{
		if ("VARCHAR2".equalsIgnoreCase(orcaleType))
		{
			return "VARCHAR";
		}
		if ("VARCHAR".equalsIgnoreCase(orcaleType))
		{
			return "VARCHAR";
		}
		if ("NUMBER".equalsIgnoreCase(orcaleType))
		{
			return "NUMERIC";
		}
		if ("DATE".equalsIgnoreCase(orcaleType))
		{
			return "DATE";
		}
		if ("CHAR".equalsIgnoreCase(orcaleType))
		{
			return "CAHR";
		}
		return "";
	}
}
