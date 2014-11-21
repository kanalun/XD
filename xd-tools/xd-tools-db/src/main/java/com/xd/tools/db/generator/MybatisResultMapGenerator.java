package com.xd.tools.db.generator;

import java.io.IOException;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;

import com.xd.tools.AllscoreNamingStrategy;
import com.xd.tools.Constants;
import com.xd.tools.NamingStrategy;
import com.xd.tools.OrcaleTypes;
import com.xd.tools.db.meta.ColumnMeta;
import com.xd.tools.db.meta.TableMeta;
import com.xd.tools.db.mybatis.ResultMapMeta;
import com.xd.tools.db.mybatis.ResultMeta;

/**
 * @author qiucongcong 2014-4-10
 */
public class MybatisResultMapGenerator
{
	private static String msg = " -___-!!!请注意:由QCC DBtools 生成,你应该本着负责的精神仔细检查一遍,bala!bala!";

	public static void main(String args[]) throws IOException
	{
		ResultMapMeta resultMapMeta = new ResultMapMeta();
		resultMapMeta.id = "ElifePrOrderMapper";
		resultMapMeta.type = "com.allscore.dto.ElifePrOrderMapper";
		ResultMeta resultMeta = new ResultMeta();
		resultMeta.column = "MERCHANTID";
		resultMeta.property = "merchantId";
		resultMapMeta.resultMetas.add(resultMeta);
		Document document = new DOMDocument();
		resultMap(document.addElement("mapper"), resultMapMeta);
		XMLUtils.print(document);
	}
	
	// ResultMapMeta resultMapMeta = new ResultMapMeta();
	// resultMapMeta.id = "PosOrderInfoMapper";
	// resultMapMeta.type = "com.allscore.dto.PosOrderInfoMapper";
	// ResultMeta resultMeta = new ResultMeta();
	// resultMeta.column = "MERCHANTID";
	// resultMeta.property = "merchantId";
	// resultMapMeta.resultMetas.add(resultMeta);
	// Document document = new DOMDocument();
	// resultMap(document.addElement("mapper"), resultMapMeta);
	// XMLUtils.print(document);

	/**
	 * 生成resultMap
	 * 
	 * @param resultMapMeta
	 * @throws IOException
	 */
	public static void resultMap(Element mapperElement, ResultMapMeta resultMapMeta) throws IOException
	{
		Element resultMapElement = new DOMElement("resultMap");
		resultMapElement.addAttribute("id", resultMapMeta.id);
		resultMapElement.addAttribute("type", resultMapMeta.type);
		if (resultMapMeta.autoMapping != null)
		{
			resultMapElement.addAttribute("autoMapping", resultMapMeta.autoMapping);
		}
		if (resultMapMeta.extends_ != null)
		{
			resultMapElement.addAttribute("extends", resultMapMeta.extends_);
		}

		for (ResultMeta resultMeta : resultMapMeta.resultMetas)
		{
			Element resultElement = resultMapElement.addElement("result");
			resultElement.addAttribute("property", resultMeta.property);
			resultElement.addAttribute("column", resultMeta.column);
			if (resultMeta.javaType != null)
			{
				resultElement.addAttribute("javaType", resultMeta.javaType);
			}
			if (resultMeta.jdbcType != null)
			{
				resultElement.addAttribute("jdbcType", resultMeta.jdbcType);
			}
		}
		mapperElement.addComment("映射MAP" + msg);
		mapperElement.add(resultMapElement);
	}

	static boolean comment = true;

	/**
	 * 生成SELCT 片段
	 * 
	 * @param document
	 * @param tableMeta
	 * @throws IOException
	 */
	public static void selectSQLFragment(Element mapperElement, TableMeta tableMeta)
	{
		Element sqlElement = new DOMElement("sql");
		sqlElement.addAttribute("id", tableMeta.tableName + "_COLUMNS");
		for (int i = 0; i < tableMeta.columns.size() - 1; i++)
		{
			ColumnMeta columnMeta = tableMeta.columns.get(i);
			if (comment)
			{
				sqlElement.addComment(i + " " + columnMeta.comment);
			}
			sqlElement.addText(columnMeta.columnName + ",");
		}
		sqlElement.addText(tableMeta.columns.get((tableMeta.columns.size() - 1)).columnName + Constants.CRLF);
		mapperElement.addComment("SELECT 片段,包含了所有字段" + msg);
		mapperElement.add(sqlElement);
	}

	/**
	 * 生成 WHERE 片段
	 * 
	 * @param tableMeta
	 * @return
	 */
	public static Element queryWhereSQLFragment(TableMeta tableMeta)
	{
		Element sqlElement = new DOMElement("sql");
		sqlElement.addAttribute("id", "whereFragment");
		sqlElement.addText(" WHERE ");
		queryWhere(sqlElement, tableMeta);
		return sqlElement;
	}

	private static String _selectSQLFragment(String prefx, TableMeta tableMeta)
	{
		if (prefx == null)
		{
			prefx = "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < tableMeta.columns.size() - 1; i++)
		{
			ColumnMeta columnMeta = tableMeta.columns.get(i);
			stringBuffer.append(prefx + columnMeta.columnName + ",");
		}
		stringBuffer.append(prefx + tableMeta.columns.get((tableMeta.columns.size() - 1)).columnName + Constants.CRLF);
		return stringBuffer.toString();
	}

	private static Element __insertColumnNames(TableMeta tableMeta)
	{
		NamingStrategy namingStrategy = new AllscoreNamingStrategy();
		// <trim prefix="(" suffix=")" suffixOverrides=",">
		Element trimElement = new DOMElement("trim");
		trimElement.addAttribute("prefix", "(");
		trimElement.addAttribute("suffix", ")");
		trimElement.addAttribute("suffixOverrides", ",");

		for (int i = 0; i < tableMeta.columns.size() - 1; i++)
		{
			ColumnMeta columnMeta = tableMeta.columns.get(i);
			__insertNamesIf(columnMeta, namingStrategy, trimElement);
		}
		return trimElement/* .asXML() */;
	}

	private static void __insertNamesIf(ColumnMeta columnMeta, NamingStrategy namingStrategy, Element trimElement)
	{
		// if (columnMeta.nullable == 0)
		{
			Element ifElement = new DOMElement("if");
			String property = namingStrategy.columnNameToProperty(columnMeta.columnName);
			String testStament = "" + property + " != null";
			if (OrcaleTypes.getType(columnMeta.typeName).equals(String.class))
			{
				testStament = testStament + " and " + property + " != ''";
			}
			ifElement.addAttribute("test", testStament);
			String text = columnMeta.columnName + ",";
			ifElement.setText(text);
			trimElement.add(ifElement);
		} // else
		{
			// stringBuffer.append(columnMeta.columnName + "," +
			// Constants.CRLF);
		}
	}

	private static Element __insertColumnValues(TableMeta tableMeta)
	{
		NamingStrategy namingStrategy = new AllscoreNamingStrategy();
		// <trim prefix="(" suffix=")" suffixOverrides=",">
		Element trimElement = new DOMElement("trim");
		trimElement.addAttribute("prefix", "VALUES (");
		trimElement.addAttribute("suffix", ")");
		trimElement.addAttribute("suffixOverrides", ",");

		for (int i = 0; i < tableMeta.columns.size() - 1; i++)
		{
			ColumnMeta columnMeta = tableMeta.columns.get(i);
			__insertValueIf(columnMeta, namingStrategy, trimElement);
		}
		return trimElement/* .asXML() */;
	}

	private static void __insertValueIf(ColumnMeta columnMeta, NamingStrategy namingStrategy, Element trimElement)
	{
		// if (columnMeta.nullable == 0)
		{
			Element ifElement = new DOMElement("if");
			String property = namingStrategy.columnNameToProperty(columnMeta.columnName);
			String testStament = "" + property + " != null";
			if (OrcaleTypes.getType(columnMeta.typeName).equals(String.class))
			{
				testStament = testStament + " and " + property + " != ''";
			}
			ifElement.addAttribute("test", testStament);
			String text = __warpProperty(columnMeta, namingStrategy) + ",";
			ifElement.setText(text);
			trimElement.add(ifElement);
		} // else
		{
			// stringBuffer.append(columnMeta.columnName + "," +
			// Constants.CRLF);
		}
	}

	private static String __warpProperty(String propertyName)
	{
		return "#{" + propertyName + "}";
	}

	private static String __warpProperty(ColumnMeta columnMeta, NamingStrategy namingStrategy)
	{
		String property = namingStrategy.columnNameToProperty(columnMeta.columnName);
		String jdbcType = OrcaleTypes.getJdbcType(columnMeta.typeName);// TODO
		if (jdbcType == null)
		{
			return "#{" + property + "}";
		} else
		{
			return "#{" + property + ", jdbcType=" + jdbcType + "}";
		}
	}

	/**
	 * @param tableMeta
	 * @return
	 */
	public static Element insert(TableMeta tableMeta)
	{
		String sql = "INSERT INTO " + tableMeta.tableName + Constants.CRLF + " (";
		Element sqlElement = new DOMElement("insert");
		sqlElement.addAttribute("id", "insert");
		sqlElement.addText(sql);
		__appendAllColumns(tableMeta, sqlElement);
		sqlElement.addText(") VALUES (");
		__appendAllColumValues(tableMeta, sqlElement);
		sqlElement.addText(")");
		return sqlElement;
	}

	private static void __appendAllColumns(TableMeta tableMeta, Element element)
	{
		for (int i = 0; i < tableMeta.columns.size() - 1; i++)
		{
			if (comment)
			{
				element.addComment(i + "");
			}
			element.addText(tableMeta.columns.get(i).columnName + "," + Constants.CRLF);
		}
		element.addText(tableMeta.columns.get((tableMeta.columns.size() - 1)).columnName + Constants.CRLF);
	}

	private static void __appendAllColumValues(TableMeta tableMeta, Element element)
	{
		NamingStrategy namingStrategy = new AllscoreNamingStrategy();
		for (int i = 0; i < tableMeta.columns.size() - 1; i++)
		{
			String property = __warpProperty(tableMeta.columns.get(i), namingStrategy);
			element.addText(property + "," + Constants.CRLF);
		}
		String last = __warpProperty((tableMeta.columns.get((tableMeta.columns.size() - 1))), namingStrategy);
		element.addText(last + Constants.CRLF);
	}

	/**
	 * 选择性插入
	 * 
	 * @param mapperElement
	 * @param tableMeta
	 * @return
	 */
	public static Element insertSelective(Element mapperElement, TableMeta tableMeta)
	{
		String sql = "INSERT INTO " + tableMeta.tableName + Constants.CRLF + " ";
		Element sqlElement = new DOMElement("insert");
		sqlElement.addAttribute("id", "insertSelective");
		sqlElement.addText(sql);
		sqlElement.add(__insertColumnNames(tableMeta));
		sqlElement.add(__insertColumnValues(tableMeta));
		mapperElement.add(sqlElement);
		return sqlElement;
	}

	/**
	 * @param mapperElement
	 * @param tableMeta
	 * @return
	 * @throws IOException
	 */
	public static boolean getByPrimaryKey(Element mapperElement, TableMeta tableMeta)
	{
		if (tableMeta.primaryKey == null)
		{
			return false;
		}
		Set<String> idSet = tableMeta.primaryKey.COLUMN_NAMES;
		if (idSet.size() <= 0)
		{
			return false;
		}
		String[] ids = idSet.toArray(new String[0]);
		String sql = "SELECT <include refid=\"" + tableMeta.tableName + "_COLUMNS" + "\"/> FROM " + tableMeta.tableName
				+ " WHERE " + ids[0] + " = #{0}";
		for (int i = 1; i < ids.length; i++)
		{
			sql = sql + " AND " + ids[i] + " = #{" + i + "} ";
		}
		Element sqlElement = new DOMElement("select");
		sqlElement.addAttribute("id", "getByPrimaryKey");
		mapperElement.addComment("通过主键获得数据" + msg);
		mapperElement.add(sqlElement);
		sqlElement.setText(sql);
		return true;
	}

	public static boolean deleteByPrimaryKey(Element mapperElement, TableMeta tableMeta)
	{
		if (tableMeta.primaryKey == null)
		{
			return false;
		}
		Set<String> idSet = tableMeta.primaryKey.COLUMN_NAMES;
		if (idSet.size() <= 0)
		{
			return false;
		}
		String[] ids = idSet.toArray(new String[0]);
		String sql = "DELETE FROM " + tableMeta.tableName + " WHERE " + ids[0] + " = #{0}";
		for (int i = 1; i < ids.length; i++)
		{
			sql = sql + " AND " + ids[i] + " = #{" + i + "} ";
		}
		Element sqlElement = new DOMElement("delete");
		sqlElement.addAttribute("id", "deleteByPrimaryKey");
		mapperElement.addComment("通过主键获得数据" + msg);
		mapperElement.add(sqlElement);
		sqlElement.setText(sql);
		return true;
	}

	/**
	 * TODO 未完待续
	 * 
	 * @param mapperElement
	 * @param tableMeta
	 * @return
	 */
	public static boolean query(Element mapperElement, TableMeta tableMeta)
	{
		Element selectElement = new DOMElement("select");
		selectElement.addAttribute("id", "query");

		selectElement.addComment("START PAGE HEAD");
		Element max1 = new DOMElement("if");
		max1.addAttribute("test", "max!=null");
		max1.addText("SELECT * FROM (");
		selectElement.add(max1);

		Element first1 = new DOMElement("if");
		first1.addAttribute("test", "first!=null");
		first1.addText("SELECT * FROM (");
		selectElement.add(first1);

		selectElement.addText("SELECT " + _selectSQLFragment("t.", tableMeta) + ", row_number() OVER(ORDER BY null) " //
				+ "AS \"row_number\" FROM " + tableMeta.tableName + " ");
		selectElement.addComment("END OF PAGE HEAD");
		/*
		 * selectElement.addText("SELECT t.*, row_number() OVER(ORDER BY null) "
		 * // + "AS \"row_number\" FROM " + tableMeta.tableName + " ");
		 */
		// TODO 这里加入WHERE子句
		selectElement.addComment("STAET WHERE");
		selectElement.addText(" WHERE ");
		queryWhere(selectElement, tableMeta);
		selectElement.addComment("END OF WHERE");
		// END OF WHERE

		selectElement.addComment("START PAGE FOOT");
		Element first2 = new DOMElement("if");
		first2.addAttribute("test", "first!=null");
		first2.addText(" ) p WHERE p.\"row_number\" &gt; #{first}");
		selectElement.add(first2);

		Element max12 = new DOMElement("if");
		max12.addAttribute("test", "max!=null");
		max12.addText(") q WHERE rownum &lt;= #{max}");
		selectElement.add(max12);

		selectElement.addComment("END OF PAGE FOOT");

		mapperElement.add(selectElement);

		return true;
	}

	private static void queryWhere(Element appendElement, TableMeta tableMeta)
	{
		NamingStrategy namingStrategy = new AllscoreNamingStrategy();
		for (int i = 0; i < tableMeta.columns.size(); i++)
		{
			ColumnMeta columnMeta = tableMeta.columns.get(i);
			Element ifElement = new DOMElement("if");
			String property = namingStrategy.columnNameToProperty(columnMeta.columnName);
			String testStament = "" + property + " != null";
			if (OrcaleTypes.getType(columnMeta.typeName).equals(String.class))
			{
				testStament = testStament + " and " + property + " != ''";
			}
			ifElement.addAttribute("test", testStament);
			String text = "";
			if (i == 0)
			{
				text = columnMeta.columnName + " = #{" + property + "}";
			} else
			{
				text = "AND " + columnMeta.columnName + " = #{" + property + "}";
			}
			ifElement.setText(text);
			appendElement.add(ifElement);
		}
	}

	public static void update(Element mapperElement, TableMeta tableMeta)
	{
		String sql = "UPDATE " + tableMeta.tableName + " SET ";
	}
}
