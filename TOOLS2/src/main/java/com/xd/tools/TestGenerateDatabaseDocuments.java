package com.xd.tools;

import java.beans.IntrospectionException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.xd.tools.db.generator.DatabaseDocumentsGenerator;
import com.xd.tools.db.generator.DatabaseXMLMetaGenerator;
import com.xd.tools.db.generator.XMLCommentReader;
import com.xd.tools.db.meta.TableMeta;

/**
 * @author QCC
 * 
 *         2014-4-4
 */
public class TestGenerateDatabaseDocuments
{
	public static void main(String args[]) throws IllegalArgumentException, SecurityException, IntrospectionException,
			IllegalAccessException, InvocationTargetException, IOException, NoSuchFieldException
	{
		XMLCommentReader.parseXml01("D:/phoneRecharge.xml");
		// System.out.print(x);
		System.out.println("---------------生成文档MD-----------------");
		String schemaPattern = "CHECKDB";
		String tableNamePattern = "%";
		// tableNamePattern = "AGRMT_SIGNED_TER_BILL";
		FileWriter fileWriter = new FileWriter("D:/PHONE_RECHARGE.md");
		List<TableMeta> tableMetas = DatabaseMetaAnalyzer.getTableMetas(schemaPattern, tableNamePattern);
		DatabaseDocumentsGenerator.printTableMetas(tableMetas, fileWriter);
		fileWriter.close();

		//
		StringWriter writer = new StringWriter();
		Document document = new DOMDocument();
		Element tables = document.addElement("tables");
		for (TableMeta tableMeta : tableMetas)
		{
			DatabaseXMLMetaGenerator.appendTableMeta(tableMeta, tables);
		}

		XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
		xmlWriter.write(document);
		String returnValue = writer.toString();
		writer.close();
		System.out.println("" + returnValue);

	}
}
