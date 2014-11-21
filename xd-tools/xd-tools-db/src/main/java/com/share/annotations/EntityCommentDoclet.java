package com.share.annotations;

import java.io.ByteArrayOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.share.utils.hibernate.DOM;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

/**
 * 抽取Entity中的文档注释
 * 
 * @author QCC
 */
public class EntityCommentDoclet extends Doclet
{
	private static boolean debug = false;

	public static Document newDom(String rootName)
	{
		Document doc = null;
		try
		{
			DocumentBuilder dombuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = dombuilder.newDocument();
			// doc.setXmlStandalone(true);
		} catch (Exception e)
		{
			return null;
			// throw new XmlException(e.getMessage(), e);
		}
		Element root = doc.createElement(rootName);
		doc.appendChild(root);
		System.out.println("" + root.toString());
		return doc;
	}

	public static boolean start(RootDoc root)
	{
		DOM ROOT = DOM.newDom("classes");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (ClassDoc clazzDoc : root.classes())
		{
			DOM clazz = ROOT.addElement("class");
			clazz.setAttribute("name", clazzDoc.qualifiedName());

			print(clazzDoc.qualifiedName(), clazzDoc.commentText());

			for (FieldDoc f : clazzDoc.fields(false))
			{
				if ("serialVersionUID".equals(f.name()))
				{
					continue;
				}
				clazz.addElement("field").setAttribute("name", f.name()).setAttribute("comment", f.commentText());
				print(f.qualifiedName(), f.commentText());
			}
			if (debug)
			{
				for (MethodDoc m : clazzDoc.methods(false))
				{
					print(m.qualifiedName(), m.commentText());
					if (m.commentText() != null && m.commentText().length() > 0)
					{
						for (ParamTag p : m.paramTags())
							print(m.qualifiedName() + "@" + p.parameterName(), p.parameterComment());
						for (Tag t : m.tags("return"))
						{
							if (t.text() != null && t.text().length() > 0)
								print(m.qualifiedName() + "@return", t.text());
						}
					}
				}
			}
		}
		ROOT.write(baos);
		String str = baos.toString();
		System.out.println("" + str);
		return true;
	}

	private static void print(String name, String comment)
	{
		if (comment != null && comment.length() > 0)
		{
			System.out.println(name + ":" + comment);
		}
	}
}