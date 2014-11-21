package com.xd.tools.db.mybatis;

import java.util.List;
import java.util.Set;

public class MyBatisGenerator
{
	public static class SQLElement
	{

	}

	public static class SelectColumn
	{
		String name;
		String ali;// 别名
	}

	public static class SQLSelect extends SQLElement
	{
		String tableName;
		boolean DISTINCT;
		List<SelectColumn> columns;
	}

	public static class SQLFrom extends SQLElement
	{
		public static class SelectColumn
		{
			String name;
			String ali;// 别名
		}

		String tableName;
		boolean DISTINCT;

		List<SelectColumn> columns;
	}

	public static class Mapper
	{
		String namespace;
	}

	public static class MapperMethod
	{
		public static enum StatementType
		{
			select, delete, insert, update
		}

		/** 在命名空间中唯一的标识符,可以被用来引用这条语句。 */
		String id;

		/** 将会传入这条语句的参数类的完全限定名或别名 */
		String parameterType;
		StatementType type;
	}

	public static class Select extends MapperMethod
	{

	}

	/**
	 * 生成mapper的Result
	 * 
	 * @author Elva
	 * @date 2014-3-19
	 * @version 1.0.0
	 */
	public static class ResultMap
	{
		public static class Result
		{
			String column;
			boolean isId;
			String jdbcType;
			String property;
		}

		String id;
		String type;
		Set<Result> results;
	}

	public static class Sql
	{
		String id;
		String text;
	}

	/**
	 * @param args
	 * @throws MissingLVException
	 * @throws NotFoundException
	 */
	public static void main(String[] args)
	{

	}

	public void test(String name1, String name2)
	{
	}

}
