package com.xd.tools.db.mybatis;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;

import com.xd.tools.db.generator.MybatisResultMapGenerator;
import com.xd.tools.db.generator.XMLUtils;
import com.xd.tools.db.meta.TableMeta;

/**
 * @author QCC
 */
public class MybatisMapper
{
	private Document document;

	private MybatisMapper(String namespace)
	{
		document = new DOMDocument();
		document.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
		document.addElement("mapper").addAttribute("namespace", namespace);
	}

	public static MybatisMapper newInstance(String namespace)
	{
		return new MybatisMapper(namespace);
	}

	public void resultMap(ResultMapMeta resultMapMeta) throws IOException
	{
		MybatisResultMapGenerator.resultMap(document.getRootElement(), resultMapMeta);
	}

	public void selectSQLFragment(TableMeta tableMeta) throws IOException
	{
		MybatisResultMapGenerator.selectSQLFragment(document.getRootElement(), tableMeta);
	}

	public void insertSelective(TableMeta tableMeta) throws IOException
	{
		MybatisResultMapGenerator.insertSelective(document.getRootElement(), tableMeta);
	}

	public void insert(TableMeta tableMeta)
	{
		Element insertElement = MybatisResultMapGenerator.insert(tableMeta);
		document.getRootElement().add(insertElement);
	}

	/**
	 * @param tableMeta
	 * @throws IOException
	 */
	public void getByPrimaryKey(TableMeta tableMeta)
	{
		MybatisResultMapGenerator.getByPrimaryKey(document.getRootElement(), tableMeta);
	}

	public void deleteByPrimaryKey(TableMeta tableMeta)
	{
		MybatisResultMapGenerator.deleteByPrimaryKey(document.getRootElement(), tableMeta);
	}

	public void query(TableMeta tableMeta)
	{
		MybatisResultMapGenerator.query(document.getRootElement(), tableMeta);
	}

	public void print() throws IOException
	{
		XMLUtils.print(document);
	}
}
