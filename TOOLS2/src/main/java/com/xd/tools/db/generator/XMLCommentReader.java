package com.xd.tools.db.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 使用
 * 
 * @author QCC
 */
public class XMLCommentReader
{
	private static List<EntityMeta> entityMetas = new ArrayList<XMLCommentReader.EntityMeta>();

	public static class EntityFieldMeta
	{
		String name;
		String comment;

		public EntityFieldMeta(String name, String comment)
		{
			this.name = name;
			this.comment = comment;
		}
	}

	public static class EntityMeta
	{
		String packageName;
		String name;
		String comment;
		List<EntityFieldMeta> fieldMetas;

		public EntityMeta(String fullName, String comment)
		{
			int sp = fullName.lastIndexOf(".");
			this.packageName = fullName.substring(0, sp);
			this.name = fullName.substring(sp + 1);
			this.comment = comment;
		}

		public boolean addFieldMeta(EntityFieldMeta fieldMeta)
		{
			if (fieldMetas == null)
			{
				fieldMetas = new ArrayList<XMLCommentReader.EntityFieldMeta>();
			}
			return fieldMetas.add(fieldMeta);
		}

		public EntityFieldMeta getFieldMeta(String columName)
		{
			if (fieldMetas == null)
			{
				return null;
			}
			for (EntityFieldMeta entityFieldMeta : fieldMetas)
			{
				if (entityFieldMeta.name.equalsIgnoreCase(columName))
				{
					return entityFieldMeta;
				}
			}
			return null;
		}
	}

	public static EntityMeta getEntityFieldMeta(String table)
	{
		for (EntityMeta entityMeta : entityMetas)
		{
			if (entityMeta.name.toLowerCase().equals("" + table.toLowerCase()))
			{
				return entityMeta;
			}
		}
		return null;
	}

	public static void parseXml01(String fileName)
	{
		try
		{
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(fileName));
			Element rootElement = document.getRootElement();
			// 获取子节点
			List<Element> classElements = rootElement.elements("class");
			if (classElements != null)
			{
				for (Element classElemetn : classElements)
				{
					EntityMeta entityMeta = new EntityMeta(classElemetn.attributeValue("name"),
							classElemetn.attributeValue("comment"));
					entityMetas.add(entityMeta);
					List<Element> fieldElements = classElemetn.elements("field");
					for (Element fieldElement : fieldElements)
					{
						String fieldName = fieldElement.attributeValue("name");
						String fieldComment = fieldElement.attributeValue("comment");
						entityMeta.addFieldMeta(new EntityFieldMeta(fieldName, fieldComment));
					}
					System.out.println("calsNname：" + classElemetn.attributeValue("name"));
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		parseXml01("D:/phoneRecharge.xml");
		EntityMeta entityMeta = getEntityFieldMeta("phonerechargeorder");
		String x = entityMeta.getFieldMeta("ispName").comment;
		System.out.print(x);
	}
}
