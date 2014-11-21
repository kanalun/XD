package com.xd.tools.db.generator;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.xd.tools.db.generator.XMLCommentReader.EntityMeta;
import com.xd.tools.db.meta.ColumnMeta;
import com.xd.tools.db.meta.TableMeta;

/**
 * 生成数据库的XML描述模型
 * 
 * @author QCC
 */
public class DatabaseXMLMetaGenerator
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

	}

	public static void appendTableMeta(TableMeta tableMeta, Element tableElements)
	{
		EntityMeta entityMeta = XMLCommentReader.getEntityFieldMeta(tableMeta.tableName);

		Element tableElement = new DOMElement("table");
		tableElement.addAttribute("name", tableMeta.tableName);
		if (tableMeta.primaryKey != null)
		{
			tableElement.addAttribute("primaryKeyName", tableMeta.primaryKey.PK_NAME);
			tableElement.addAttribute("primaryKeyColumns", tableMeta.primaryKey.COLUMN_NAMES.toString());
		}
		for (int i = 0; i < tableMeta.columns.size() - 1; i++)
		{
			ColumnMeta columnMeta = tableMeta.columns.get(i);
			appendColumn(columnMeta, tableElement);
			try
			{
				String columComment = entityMeta.getFieldMeta(columnMeta.columnName).comment;
				columnMeta.comment = columComment;
			} catch (Exception e)
			{
			}
		}
		tableElements.add(tableElement);
	}

	public static void appendColumn(ColumnMeta columnMeta, Element tableElement)
	{
		Element columnElement = tableElement.addElement("column");
		columnElement.addAttribute("name", columnMeta.columnName);
		columnElement.addAttribute("type", columnMeta.typeName);
		columnElement.addAttribute("defaultValue", columnMeta.defaultValue);
		columnElement.addAttribute("columnSize", columnMeta.columnSize + "");
		columnElement.addAttribute("isPrimaryKey", columnMeta.isPrimaryKey + "");
		if (columnMeta.precision != 0)
		{
			columnElement.addAttribute("precision", columnMeta.precision + "");
		}
		if (columnMeta.scale != 0)
		{
			columnElement.addAttribute("scale", columnMeta.scale + "");
		}
	}
}
