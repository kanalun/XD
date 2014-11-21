package com.xd.tools.db.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.xd.tools.db.generator.XMLCommentReader.EntityMeta;
import com.xd.tools.db.meta.ColumnMeta;
import com.xd.tools.db.meta.ExportedKeyMeta;
import com.xd.tools.db.meta.ImportedKeyMeta;
import com.xd.tools.db.meta.IndexMeta;
import com.xd.tools.db.meta.TableMeta;

/**
 * 数据库文档生成器
 * 
 * @author QCC
 */
public class DatabaseDocumentsGenerator
{
	/**
	 * print all tables to HTML files
	 * 
	 * @param tableMetas
	 * @param writer
	 * @throws IOException
	 */
	public static void printTableMetas(List<TableMeta> tableMetas, Writer writer) throws IOException
	{
		for (TableMeta tableMeta : tableMetas)
		{
			DatabaseDocumentsGenerator.printTableMeta(tableMeta, writer);
			writer.flush();
		}
	}

	/**
	 * 将所有"表"打印
	 * 
	 * @param tableMeta
	 * @throws IOException
	 */
	public static void printTableMeta(TableMeta tableMeta, Writer writer) throws IOException
	{
		// StringBuffer stringBuffer = new StringBuffer();
		// 忽略_AUD表
		if (tableMeta.tableName.endsWith("_aud"))
		{
			// return stringBuffer;
		}

		// 表头:名称
		writer.append("<h2>" + tableMeta.tableName + "</h2>\n");

		if (tableMeta.tableName.endsWith("_aud"))
		{
			writer.append("<table class='audTable'>");
		} else
		{
			writer.append("<table>");
		}

		// 表头
		writer.append("<tr>");
		writer.append("<th class='tableName' colSpan='10'><a href='db/" + tableMeta.tableName + ".md' target='_blank'>"
				+ tableMeta.tableName + "</a></th>");
		writer.append("</tr>");

		// 字段头
		writer.append("<tr>");
		writer.append("<th class='tt_columnName'>" + "序号" + "</th>");
		writer.append("<th class='tt_columnName'>" + "字段名称" + "</th>");
		writer.append("<th class='tt_columnSize'>" + "类型(长度/精度)" + "</th>");
		writer.append("<th class='tt_ableName'>" + "PK/FK" + "</th>");
		writer.append("<th class='tt_ableName'>" + "非空" + "</th>");
		writer.append("<th class='tt_ableName'>" + "默认" + "</th>");
		writer.append("<th class='tt_ableName'>" + "内部" + "</th>");
		writer.append("<th class='tt_ableName'>" + "更新" + "</th>");
		writer.append("<th class='tt_index'>" + "索引" + "</th>");
		writer.append("<th class='tt_des'>" + "业务说明" + "</th>");
		writer.append("</tr>");

		int c = 1;// 序号
		for (ColumnMeta columnMeta : tableMeta.columns)
		{
			writer.append("<tr>");
			writer.append("<td class='order " + "" + " '>" + c++ + "</td>");

			String pkclass = "";
			if (columnMeta.isPrimaryKey)
			{
				pkclass = "pk";
			}
			// 序号
			writer.append("<td class='columnName " + pkclass + " '>" + columnMeta.columnName + "</td>");
			String type = columnMeta.typeName.toLowerCase();
			if (columnMeta.precision != 0)
			{
				if (columnMeta.scale == 0)
				{
					type = type + "(" + columnMeta.precision + ")";
				} else
				{
					type = type + "(" + columnMeta.precision + "/" + columnMeta.scale + ")";
				}

			} else
			{
				type = type + "(" + columnMeta.columnSize + ")";
			}

			writer.append("<td class='type'>" + type + "</td>");

			String pkFK = "";

			if (columnMeta.isPrimaryKey)
			{
				pkFK = "PK";
			}
			if (columnMeta.isImportedKey)
			{
				pkFK += " FK";
			}

			String exportClass = "";
			if (columnMeta.isExportedKey)
			{
				exportClass = "exportpk";
			}
			// PK FK
			writer.append("<td class='tableName " + exportClass + " ' title='" + pkFK + "' >" + pkFK + "</td>");

			String notNULL = "";
			if (columnMeta.nullable == 0)
			{
				notNULL = "Y";
			}

			writer.append("<td class='IS_NULLABLE'>" + notNULL + "</td>");

			String defaultValue = columnMeta.defaultValue == null ? "" : columnMeta.defaultValue;
			writer.append("<td class='defaultValue'>" + defaultValue + "</td>");

			// 是否为内部
			if (columnMeta.columnName.equals("creatorHasView") || columnMeta.columnName.equals("examinerHasView"))
			{
				writer.append("<td class='defaultValue'>" + "Y" + "</td>");
			} else
			{
				writer.append("<td class='defaultValue'>" + "" + "</td>");
			}
			// 是否可以更新
			if (columnMeta.columnName.equals("id") || columnMeta.columnName.equals("created")
					|| columnMeta.columnName.equals("tenantId") || columnMeta.columnName.equals("creator_id"))// creator_id
			{
				writer.append("<td class='defaultValue' title='无法更新'>" + "N" + "</td>");
			} else if (columnMeta.columnName.equals("logo") || columnMeta.columnName.equals("password")
					|| columnMeta.columnName.equals("serial"))
			{
				writer.append("<td class='defaultValue notice' title='注意'>" + "NOTICE" + "</td>");
			} else if (columnMeta.columnName.equals("lastModified") || columnMeta.columnName.equals("lastModifiedUser_id")
					|| columnMeta.columnName.equals("currentExamine_id") || columnMeta.columnName.equals("hasEnd")
					|| columnMeta.columnName.equals("totalFileSpaceSize") || columnMeta.columnName.equals("usedfileSpaceSize")
					|| columnMeta.columnName.equals("trackTimes") || columnMeta.columnName.equals("pinyin")
					|| columnMeta.columnName.equals("lastTrackTime"))
			{
				writer.append("<td class='defaultValue' title='由程序完成'> " + "P" + "</td>");
			} else if (columnMeta.columnName.equals("attachmentCount") || columnMeta.columnName.equals("attachmentCount"))
			{
				// Redundant
				writer.append("<td class='defaultValue notice' title='冗余字段'>" + "R" + "</td>");
			} else
			{
				writer.append("<td class='defaultValue'>" + "" + "</td>");
			}

			String indexName = "";
			String indexCount = "";
			if (columnMeta.indexMetas != null)
			{
				if (columnMeta.indexMetas.size() > 0)
				{
					indexCount = "" + columnMeta.indexMetas.size();
				}
				for (IndexMeta indexMeta : columnMeta.indexMetas)
				{
					indexName = indexName + "\n" + indexMeta.INDEX_NAME;
				}
			}
			writer.append("<td class='tableName' title='" + indexName + "'>" + indexCount + "</td>");

			// TODO 这里很粗暴啊
			try
			{
				EntityMeta entityMeta = XMLCommentReader.getEntityFieldMeta(tableMeta.tableName);
				String columComment = entityMeta.getFieldMeta(columnMeta.columnName).comment;
				columnMeta.comment = columComment;
			} catch (Exception e)
			{

			}
			if (columnMeta.comment == null)
			{
				writer.append("<td class='comment'>" + "" + "</td>");
			} else
			{
				writer.append("<td class='comment' style='word-break:break-all;max-width: 300px'>" + columnMeta.comment + "</td>");
			}
			writer.append("</tr>");
		}
		writer.append("</table>");

		// System.out.println("" + stringBuffer);
		// return stringBuffer;
		// File file = new File(BASE_DIR + "" + tableMeta.tableName + ".md");
		// Writer out = new FileWriter(file);
		// BufferedWriter bufferedWriter = new BufferedWriter(out);
		// bufferedWriter.write(stringBuffer.toString());
		// bufferedWriter.close();
		// System.out.println("" + tableMeta.TABLE_SCHEM);
	}

	/**
	 * @param tableMeta
	 * @throws IOException
	 */
	public static void printTableMetaDetail(TableMeta tableMeta, String detailDir) throws IOException
	{
		if (tableMeta.tableName.endsWith("_aud"))
		{
			return;
		}
		StringBuffer stringBuffer = new StringBuffer();
		//
		stringBuffer.append("##字段说明" + "\n");
		if (tableMeta.tableName.endsWith("_aud"))
		{
			stringBuffer.append("<table class='audTable'>");
		} else
		{
			stringBuffer.append("<table>");
		}

		// 表头
		stringBuffer.append("<tr>");
		stringBuffer.append("<th class='tableName' colSpan='9'>" + tableMeta.tableName + "</th>");
		stringBuffer.append("</tr>");

		// 字段头
		stringBuffer.append("<tr>");
		stringBuffer.append("<th class='tt_columnName'>" + "序号" + "</th>");
		stringBuffer.append("<th class='tt_columnName'>" + "字段名称" + "</th>");
		stringBuffer.append("<th class='tt_columnSize'>" + "数据类型(长度/精度)" + "</th>");
		stringBuffer.append("<th class='tt_ableName'>" + "IMPORTED_KEYS" + "</th>");
		stringBuffer.append("<th class='tt_ableName'>" + "EXPORTED_KEYS" + "</th>");
		stringBuffer.append("<th class='tt_ableName'>" + "非空" + "</th>");
		stringBuffer.append("<th class='tt_ableName'>" + "默认值" + "</th>");
		stringBuffer.append("<th class='tt_index'>" + "索引" + "</th>");
		stringBuffer.append("<th class='tt_des'>" + "业务说明" + "</th>");
		stringBuffer.append("</tr>");

		int c = 1;// 序号
		for (ColumnMeta columnMeta : tableMeta.columns)
		{
			stringBuffer.append("<tr>");
			stringBuffer.append("<td class='order " + "" + " '>" + c++ + "</td>");

			String pkclass = "";
			if (columnMeta.isPrimaryKey)
			{
				pkclass = "pk";
			}
			// 序号
			stringBuffer.append("<td class='columnName " + pkclass + " '>" + columnMeta.columnName + "</td>");
			String type = columnMeta.typeName.toLowerCase();
			if (columnMeta.precision != 0)
			{
				type = type + "(" + columnMeta.columnSize + "/" + columnMeta.precision + ")";
			} else
			{
				type = type + "(" + columnMeta.columnSize + ")";
			}

			stringBuffer.append("<td class='type'>" + type + "</td>");

			String pkFK = "";

			if (columnMeta.isPrimaryKey)
			{
				pkFK = "PK";
			}
			if (columnMeta.isImportedKey)
			{
				pkFK += " FK";
			}

			String exportClass = "";
			if (columnMeta.isExportedKey)
			{
				exportClass = "exportpk";
			}

			String importedKeys = "";
			if (columnMeta.importedKeys != null && columnMeta.importedKeys.size() > 0)
			{
				importedKeys = "<ol>";
				for (ImportedKeyMeta importedKeyMeta : columnMeta.importedKeys)
				{
					importedKeys = importedKeys + "<li title='" + importedKeyMeta.FK_NAME + "'><a href='"
							+ importedKeyMeta.PKTABLE_NAME + ".md'>" + importedKeyMeta.PKTABLE_NAME + "</a>."
							+ importedKeyMeta.PKCOLUMN_NAME + "</li>";
				}
				importedKeys = importedKeys + "</ol>";
			}

			// PK FK
			stringBuffer.append("<td class='tableName " + exportClass + " ' title='" + "-" + "' >" + importedKeys + "</td>");

			String exportKyes = "";
			if (columnMeta.exportedKeys != null && columnMeta.exportedKeys.size() > 0)
			{
				exportKyes = "<ol>";
				for (ExportedKeyMeta exportedKeyMeta : columnMeta.exportedKeys)
				{
					exportKyes = exportKyes + "<li title='" + exportedKeyMeta.FK_NAME + "'><a href='"
							+ exportedKeyMeta.FKTABLE_NAME + ".md'>" + exportedKeyMeta.FKTABLE_NAME + "</a>."
							+ exportedKeyMeta.FKCOLUMN_NAME + "</li>";
				}
				exportKyes = exportKyes + "</ol>";
			}

			// PK FK
			stringBuffer.append("<td class='tableName " + exportClass + " ' title='" + "-" + "' >" + exportKyes + "</td>");

			String notNULL = "";
			if (columnMeta.nullable == 0)
			{
				notNULL = "Y";
			}

			stringBuffer.append("<td class='IS_NULLABLE'>" + notNULL + "</td>");

			String defaultValue = columnMeta.defaultValue == null ? "" : columnMeta.defaultValue;
			stringBuffer.append("<td class='defaultValue'>" + defaultValue + "</td>");

			String indexCount = "";
			String indexName = "";
			if (columnMeta.indexMetas.size() > 0)
			{
				indexCount = "" + columnMeta.indexMetas.size();
			}
			if (columnMeta.indexMetas != null && columnMeta.indexMetas.size() > 0)
			{
				indexName = "<ol>";
				for (IndexMeta indexMeta : columnMeta.indexMetas)
				{
					indexName = indexName + "<li>" + indexMeta.INDEX_NAME + "</li>";
				}
				indexName = indexName + "</ol>";
			}

			stringBuffer.append("<td class='tableName' title='" + indexCount + "'>" + indexName + "</td>");
			stringBuffer.append("<td class='tableName'>" + "" + "</td>");
			stringBuffer.append("</tr>");
		}
		stringBuffer.append("</table>");
		// System.out.println("" + stringBuffer);

		File file = new File(detailDir + "/" + tableMeta.tableName + ".md");
		Writer out = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(out);
		bufferedWriter.write(stringBuffer.toString());
		bufferedWriter.write("<link type='text/css' rel='stylesheet' href='../doc-files/db.css'>");
		bufferedWriter.close();
		// System.out.println("" + tableMeta.TABLE_SCHEM);
	}
}
