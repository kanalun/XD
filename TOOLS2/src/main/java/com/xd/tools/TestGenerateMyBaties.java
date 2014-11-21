package com.xd.tools;

import java.beans.IntrospectionException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.xd.tools.classgenerator.ClassGenerator;
import com.xd.tools.classgenerator.FieldMeta;
import com.xd.tools.db.meta.ColumnMeta;
import com.xd.tools.db.meta.TableMeta;
import com.xd.tools.db.mybatis.MybatisMapper;
import com.xd.tools.db.mybatis.ResultMapMeta;
import com.xd.tools.db.mybatis.ResultMeta;

/**
 * @author QCC
 */
public class TestGenerateMyBaties
{
	public static String BASE_DIR = "D:\\dbg\\";

	/** TO文件存放路径 */
	public static String TO_DIR = "D:\\DTO\\NPAY\\";

	/**
	 * @param tableMeta
	 * @throws IOException
	 */
	private static void print2Class(String className, String content) throws IOException
	{
		File file = new File(BASE_DIR + "" + className + ".java");
		Writer out = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(out);
		bufferedWriter.write(content);
		bufferedWriter.close();
		// System.out.println("" + tableMeta.TABLE_SCHEM);
	}

	/** 索引META */
	public static void main(String args[]) throws IllegalArgumentException, SecurityException, IntrospectionException,
			IllegalAccessException, InvocationTargetException, IOException, NoSuchFieldException
	{
		System.out.println("---------------生成DTO-----------------");
		String packageName = "com.allscore.npayagent.dto";
		String schemaPattern = "CHECKDB";
		// printTableMetas(getTableMetas());
		AllscoreNamingStrategy namingStrategy = new AllscoreNamingStrategy();
		List<TableMeta> tableMetas = DatabaseMetaAnalyzer.getTableMetas(schemaPattern, "ELIFE_%");
		ClassGenerator classGenerator = new ClassGenerator();
		for (TableMeta tableMeta : tableMetas)
		{
			// 表名转类名
			String className = namingStrategy.tableNameToClass(tableMeta.tableName);
			List<FieldMeta> fields = new ArrayList<FieldMeta>();

			// 生成TO
			for (ColumnMeta columnMeta : tableMeta.columns)
			{
				FieldMeta fieldMeta = new FieldMeta();
				fieldMeta.name = namingStrategy.columnNameToProperty(columnMeta.columnName);
				Class<?> javaType = OrcaleTypes.getType(columnMeta.typeName);
				fieldMeta.typeName = javaType.getSimpleName();
				fieldMeta.typePackageName = javaType.getPackage().getName();
				if (columnMeta.comment == null)
				{
					if (columnMeta.precision != 0)
					{
						if (columnMeta.scale != 0)
						{
							fieldMeta.comment = "" + columnMeta.typeName + "(" + columnMeta.precision + "," + columnMeta.scale
									+ ")";
						} else
						{
							fieldMeta.comment = "" + columnMeta.typeName + "(" + columnMeta.precision + ")";
						}
					} else
					{
						fieldMeta.comment = "" + columnMeta.typeName + "(" + columnMeta.columnSize + ")";
					}

				}
				fields.add(fieldMeta);
			}
			String to = classGenerator.generate(packageName, className, fields);
			System.out.println("" + to);
			// print2Class(className, to);

			// ------------------------ 生成MybatisMapper
			ResultMapMeta resultMapMeta = new ResultMapMeta();
			resultMapMeta.id = className;
			resultMapMeta.type = packageName + "." + className;
			for (ColumnMeta columnMeta : tableMeta.columns)
			{
				ResultMeta resultMeta = new ResultMeta();
				resultMeta.column = columnMeta.columnName;
				resultMeta.property = namingStrategy.columnNameToProperty(columnMeta.columnName);
				resultMapMeta.resultMetas.add(resultMeta);
			}
			MybatisMapper mapper = MybatisMapper.newInstance("com.allscore.npayagent.dto");
			mapper.resultMap(resultMapMeta);
			mapper.selectSQLFragment(tableMeta);
			mapper.insertSelective(tableMeta);
			mapper.insert(tableMeta);
			mapper.getByPrimaryKey(tableMeta);
			mapper.deleteByPrimaryKey(tableMeta);
			mapper.query(tableMeta);
			mapper.print();
		}
	}
}
