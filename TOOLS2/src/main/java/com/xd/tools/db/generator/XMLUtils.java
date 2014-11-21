package com.xd.tools.db.generator;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XMLUtils
{
	public static void write(Writer writer, Document document) throws IOException
	{
		XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
		xmlWriter.setEscapeText(false);
		xmlWriter.write(document);
		writer.close();
	}

	public static void print(Document document) throws IOException
	{
		StringWriter writer = new StringWriter();
		write(writer, document);
		String returnValue = writer.toString();
		System.out.println("" + returnValue);
	}
}
