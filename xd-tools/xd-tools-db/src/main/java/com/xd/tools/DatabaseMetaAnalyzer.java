package com.xd.tools;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xd.tools.db.meta.ColumnMeta;
import com.xd.tools.db.meta.ExportedKeyMeta;
import com.xd.tools.db.meta.ImportedKeyMeta;
import com.xd.tools.db.meta.IndexMeta;
import com.xd.tools.db.meta.PrimaryKeyMeta;
import com.xd.tools.db.meta.TableMeta;

/**
 * 数据库Meta分析器
 * 
 * @author Elva
 * @date 2013-4-22
 * @version 1.0.0
 */
public class DatabaseMetaAnalyzer
{
	/**
	 * Orcale数据字典 <code>
	select
    	A.*,  B.*
	from
	    user_tab_columns A,user_col_comments B
	where
	    A.table_name = B.table_name
	    and A.column_name = B.column_name
	    and A.table_name LIKE 'TEST_%'
	 * </code>
	 * 
	 * <code>	
	select
    A.table_name, A.column_name 字段名,A.data_type 数据类型,A.data_length 长度,A.data_precision 整数位,
    A.data_scale 小数位,A.nullable 允许空值,A.data_default 缺省值,B.comments 备注
	from
	    user_tab_columns A,user_col_comments B
	where
	    A.table_name = B.table_name
	    and A.column_name = B.column_name
	    and A.table_name LIKE 'TEST_%'
	  </code>
	 */

	private static final String ORACLE_COLUMN_META_SQL2 = "select A.*,  B.* FROM USER_TAB_COLUMNS A,USER_COL_COMMENTS B "
			+ "	WHERE  A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME AND A.TABLE_NAME = ?";

	private static final String ORACLE_COLUMN_META_SQL = "SELECT A.COLUMN_NAME AS COLUMN_NAME,"
			+ "  B.COMMENTS AS REMARKS FROM USER_TAB_COLUMNS A,USER_COL_COMMENTS B "
			+ "	WHERE A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME AND A.TABLE_NAME = ?";

	// ORACLE 字段
	private static final String ORACLE_COLUMS_SQL = "SELECT A.column_name COLUMN_NAME,A.data_type TYPE_NAME,A.data_length COLUMN_SIZE,A.data_precision NUM_PREC_RADIX, "
			+ " A.data_scale DECIMAL_DIGITS ,DECODE(A.nullable,'Y', 1, 0) NULLABLE,A.data_default COLUMN_DEF,B.comments REMARKS "
			+ "from user_tab_columns A,user_col_comments B where A.table_name = B.table_name  and A.column_name = B.column_name   and A.table_name = ?";

	// ORACLE 表备注
	private static final String ORACLE_COMMENTS_OF_TABLE = "SELECT COLUMN_NAME,REMARKS FROM USER_COL_COMMENTS WHERE TABLE_NAME = ?";

	/**
	 * 获得 Oracle列
	 * 
	 * @param tableName
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private static final ResultSet getOracleColumns(String tableName, Connection connection)
			throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(ORACLE_COLUMS_SQL);
		statement.setString(1, tableName);
		ResultSet resultSet = statement.executeQuery();
		return resultSet;
	}

	private static final ResultSet getOracleColumnsComments(String tableName, Connection connection)
			throws SQLException
	{
		return null;
	}

	// select * from sys.dba_sequences where SEQUENCE_NAME like '%SEQUENCE'
	/** 查询ORACLE SEQUENCE */
	private static final String ORACLE_SEQUENCE_SQL = "select SEQUENCE_OWNER,SEQUENCE_NAME,MIN_VALUE,MAX_VALUE,INCREMENT_BY,CYCLE_FLAG,ORDER_FLAG,CACHE_SIZE,LAST_NUMBER from sys.dba_sequences";

	public static enum DBType
	{
		ORACLE("oracle.jdbc.driver.OracleDriver"), //

		MYSQL("com.mysql.jdbc.Driver");//

		String driverClass;

		private DBType(String dirverClass)
		{
			this.driverClass = dirverClass;
		}

	}

	/**
	 * 暂时还未实现 <code>
	 * Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.1.2/test7?useUnicode=true&amp;characterEncoding=utf-8";
			String username = "qcc";
			String password = "";
			conn = DriverManager.getConnection(url, username, password); </code>
	 * 
	 * 
	 * @param dbType
	 * @param host
	 * @param port
	 * @param dbName
	 * @param username
	 * @param password
	 * @return
	 */
	@Deprecated
	private static Connection getConnection(DBType dbType, String host, Integer port,
			String dbName, String username, String password)
	{
		int defaultPort = -1;
		String dirverClass = null;
		switch (dbType)
		{
		case ORACLE:
			dirverClass = "oracle.jdbc.driver.OracleDriver";
			break;
		case MYSQL:
			dirverClass = "com.mysql.jdbc.Driver";
			break;
		default:
			break;
		}
		Connection conn = null;
		try
		{
			Class.forName(dbType.driverClass);// com.mysql.jdbc.Driver
			String url = "jdbc:oracle:thin:@192.168.88.98:1521:trantest";
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	private static Connection getOracleConnection_()
	{
		Connection conn = null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");// com.mysql.jdbc.Driver
			String url = "jdbc:oracle:thin:@192.168.88.98:1521:trantest";
			String username = "checkdb";
			String password = "checkdb";
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	private static Connection getMySQL5Connection()
	{
		Connection conn = null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");// com.mysql.jdbc.Driver
			String url = "jdbc:mysql://localhost/phonerecharge?useUnicode=true&amp;characterEncoding=utf-8";
			String username = "root";
			String password = "";
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	private static void printTableMetas(List<TableMeta> tableMetas)
	{
		int i = 0;
		AllscoreNamingStrategy namingStrategy = new AllscoreNamingStrategy();
		for (TableMeta tableMeta : tableMetas)
		{
			String tableName = tableMeta.tableName;
			System.out.println(++i + " Table: " + tableName + " Class:"
					+ namingStrategy.tableNameToClass(tableName));
		}
	}

	/**
	 * 打印数据库信息,包括是否支持排序,驱动和数据库版本信息等
	 * 
	 * @param dataBaseMetaData
	 * @throws SQLException
	 */
	public static void printDatabaseMetaData(DatabaseMetaData dataBaseMetaData) throws SQLException
	{
		System.out.println("------------------打印数据库信息-----------------");
		// System.out.println("allProceduresAreCallable:" +
		// dataBaseMetaData.allProceduresAreCallable());
		// System.out.println("getCatalogSeparator:" +
		// dataBaseMetaData.getCatalogSeparator());
		// System.out.println("getCatalogTerm:" +
		// dataBaseMetaData.getCatalogTerm());
		// System.out.println("getDatabaseMajorVersion:" +
		// dataBaseMetaData.getDatabaseMajorVersion());
		// System.out.println("getDatabaseMinorVersion:" +
		// dataBaseMetaData.getDatabaseMinorVersion());
		// System.out.println("getDatabaseProductName:" +
		// dataBaseMetaData.getDatabaseProductName());
		// System.out.println("getDatabaseProductVersion:" +
		// dataBaseMetaData.getDatabaseProductVersion());
		// System.out.println("getDefaultTransactionIsolation:" +
		// dataBaseMetaData.getDefaultTransactionIsolation());
		// System.out.println("getDriverMajorVersion:" +
		// dataBaseMetaData.getDriverMajorVersion());
		// System.out.println("getDriverMinorVersion:" +
		// dataBaseMetaData.getDriverMinorVersion());
		// System.out.println("getDriverMinorVersion:" +
		// dataBaseMetaData.getDriverName());
		// System.out.println("getDriverMinorVersion:" +
		// dataBaseMetaData.getDriverVersion());
		// System.out.println("allTablesAreSelectable:" +
		// dataBaseMetaData.allTablesAreSelectable());

		Method[] ms = DatabaseMetaData.class.getMethods();
		for (Method m : ms)
		{
			if (m.getParameterTypes().length == 0)
			{
				try
				{
					System.out.println(m.getName() + ":"
							+ m.invoke(dataBaseMetaData, new Object[0]));
				} catch (Exception e)
				{
				}
			}
		}
	}

	/** 是否打印信息 */
	private static boolean PRINT_LOG = true;

	/** 是否为Oracle数据库 */
	private static boolean oracle = false;

	private static void println(String message)
	{
		if (PRINT_LOG)
		{
			System.out.println(message);
		}
	}

	/**
	 * @param schemaPattern
	 *            比如: CHECKDB
	 * @param tableNamePattern
	 *            表名字匹配模式 "NPAY_AGENT_%"
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static List<TableMeta> getTableMetas(final String schemaPattern,
			final String tableNamePattern) throws IOException
	{
		StringBuffer logBuffer = new StringBuffer();

		List<TableMeta> tableMetas = new ArrayList<TableMeta>();
		Connection connection = getMySQL5Connection();
		try
		{
			String catalog = connection.getCatalog(); // catalog 其实也就是数据库名
			DatabaseMetaData dataBaseMetaData = connection.getMetaData();

			ResultSet tablesResultSet = dataBaseMetaData.getTables(catalog, schemaPattern,
					tableNamePattern, new String[] { "TABLE" });
			// TABLE
			while (tablesResultSet.next())
			{
				String tableName = tablesResultSet.getString("TABLE_NAME");
				// System.out.println("-------" + tableName);
				TableMeta tableMeta = new TableMeta(tableName);
				tableMeta.TABLE_SCHEM = tablesResultSet.getString("TABLE_SCHEM");
				tableMeta.REMARKS = tablesResultSet.getString("REMARKS");
				tableMeta.TABLE_CAT = tablesResultSet.getString("TABLE_CAT");
				tableMetas.add(tableMeta);
			}
			tablesResultSet.close();

			//
			for (TableMeta tableMeta : tableMetas)
			{
				String tableName = tableMeta.tableName;
				println("\n------------" + tableName + "-------------------");
				logBuffer.append("\n------------" + tableName + "------------\n");

				ResultSet columnsResultSet = null;
				try
				{
					println("---COLUMNS:");
					if (!oracle)
					{
						columnsResultSet = dataBaseMetaData.getColumns(catalog, schemaPattern,
								tableName, null);
					} else
					{
						columnsResultSet = getOracleColumns(tableName, connection);
					}
					int cCount = 0;
					while (columnsResultSet != null && columnsResultSet.next())
					{
						cCount++;
						// COL
						ColumnMeta columnMeta = new ColumnMeta();
						columnMeta.columnName = columnsResultSet.getString("COLUMN_NAME");
						columnMeta.columnSize = columnsResultSet.getInt("COLUMN_SIZE");
						columnMeta.typeName = columnsResultSet.getString("TYPE_NAME");
						columnMeta.defaultValue = columnsResultSet.getString("COLUMN_DEF");
						columnMeta.precision = columnsResultSet.getInt("NUM_PREC_RADIX");
						columnMeta.scale = columnsResultSet.getInt("DECIMAL_DIGITS");// DECIMAL_DIGITS

						// String nullable =
						// columnsResultSet.getString("NULLABLE");
						// if ("Y".equals(nullable))
						// {
						columnMeta.nullable = columnsResultSet.getInt("NULLABLE");
						// } else
						// {
						// columnMeta.NULLABLE = 0;
						// /}
						columnMeta.comment = columnsResultSet.getString("REMARKS");
						tableMeta.columns.add(columnMeta);
						println("   |-" + columnMeta.columnName);
						logBuffer.append("   |-" + columnMeta.columnName + "\n");
					}
					// println("----------" + cCount);
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					if (columnsResultSet != null)
					{
						columnsResultSet.close();
					}
				}
				// INDEX
				ResultSet indexSet = null;
				try
				{
					println("---INDEX:");
					indexSet = dataBaseMetaData.getIndexInfo(catalog, schemaPattern, tableName,
							false, true);
					while (indexSet.next())
					{
						String COLUMN_NAME = indexSet.getString("COLUMN_NAME");
						String INDEX_NAME = indexSet.getString("INDEX_NAME");
						boolean NON_UNIQUE = indexSet.getBoolean("NON_UNIQUE");
						short TYPE = indexSet.getShort("TYPE");
						short ORDINAL_POSITION = indexSet.getShort("ORDINAL_POSITION");

						println("   |-INDEX_NAME:[" + INDEX_NAME + "] COLUMN_NAME:[" + COLUMN_NAME
								+ "]");
						for (ColumnMeta columnMeta : tableMeta.columns)
						{
							if (COLUMN_NAME != null && COLUMN_NAME.equals(columnMeta.columnName))
							{
								columnMeta.indexMetas.add(new IndexMeta(INDEX_NAME));
							}
						}
					}
				} catch (Exception e)
				{
					println("catalog:[" + catalog + "] schemaPattern:[" + schemaPattern
							+ "]  tableName:[" + tableName + "]");
					e.printStackTrace();
				} finally
				{
					if (indexSet != null)
					{
						indexSet.close();
					}
				}
				// PK
				ResultSet primaryKeyResultSet = null;
				try
				{
					println("---PRIMARY_KEY:");
					// PK
					primaryKeyResultSet = dataBaseMetaData.getPrimaryKeys(catalog, schemaPattern,
							tableName);
					while (primaryKeyResultSet.next())
					{
						String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
						String primaryKeyName = primaryKeyResultSet.getString("PK_NAME");
						println("   |-" + primaryKeyName + " [" + primaryKeyColumnName + "]");
						//
						if (tableMeta.primaryKey == null)
						{
							tableMeta.primaryKey = new PrimaryKeyMeta(primaryKeyName);
						}
						tableMeta.primaryKey.COLUMN_NAMES.add(primaryKeyColumnName);

						// 把PK放到列上去
						for (ColumnMeta columnMeta : tableMeta.columns)
						{
							// 列上关联主键信息
							if (primaryKeyColumnName.equals(columnMeta.columnName))
							{
								columnMeta.isPrimaryKey = true;
								columnMeta.primaryKeyName = primaryKeyName;
							}
						}
					}
					println("---PRIMARY_KEY:" + tableMeta.primaryKey);
					primaryKeyResultSet.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					if (primaryKeyResultSet != null)
					{
						primaryKeyResultSet.close();
					}
				}

				// 导入(外键约束):
				ResultSet importedKeyResultSet = null;
				try
				{
					println("---IMPORTED_KEY(外键约束):");
					importedKeyResultSet = dataBaseMetaData.getImportedKeys(catalog, schemaPattern,
							tableName);
					// FK
					while (importedKeyResultSet.next())
					{
						String FKTABLE_NAME = importedKeyResultSet.getString("FKTABLE_NAME");
						String FKCOLUMN_NAME = importedKeyResultSet.getString("FKCOLUMN_NAME");
						String FK_NAME = importedKeyResultSet.getString("FK_NAME");
						String PKTABLE_NAME = importedKeyResultSet.getString("PKTABLE_NAME");
						String PKCOLUMN_NAME = importedKeyResultSet.getString("PKCOLUMN_NAME");

						println("   |-" + FK_NAME + ":[" /*
														 * + FKTABLE_NAME + "."
														 */+ FKCOLUMN_NAME + " -> " + PKTABLE_NAME
								+ "." + PKCOLUMN_NAME + "]");

						// println(" ---" + primaryKeyColumnName);
						for (ColumnMeta columnMeta : tableMeta.columns)
						{
							if (FKCOLUMN_NAME.equals(columnMeta.columnName))
							{
								columnMeta.isImportedKey = true;
								ImportedKeyMeta importedKeyMeta = new ImportedKeyMeta(FK_NAME);
								importedKeyMeta.FKTABLE_NAME = FKTABLE_NAME;
								importedKeyMeta.FKCOLUMN_NAME = FKCOLUMN_NAME;
								importedKeyMeta.FK_NAME = FK_NAME;
								importedKeyMeta.PKTABLE_NAME = PKTABLE_NAME;
								importedKeyMeta.PKCOLUMN_NAME = PKCOLUMN_NAME;
								columnMeta.importedKeys.add(importedKeyMeta);
							}
						}
					}
					importedKeyResultSet.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					if (importedKeyResultSet != null)
					{
						importedKeyResultSet.close();
					}
				}
				// FK(被别的表引用)
				ResultSet exportedKeyResultSet = null;
				try
				{
					println("---EXPORTED_KEY(被别的表引用):");

					exportedKeyResultSet = dataBaseMetaData.getExportedKeys(catalog, schemaPattern,
							tableName);
					// FK
					while (exportedKeyResultSet.next())
					{
						String PKTABLE_NAME = exportedKeyResultSet.getString("PKTABLE_NAME");
						String PKCOLUMN_NAME = exportedKeyResultSet.getString("PKCOLUMN_NAME");
						String FKTABLE_NAME = exportedKeyResultSet.getString("FKTABLE_NAME");
						String FKCOLUMN_NAME = exportedKeyResultSet.getString("FKCOLUMN_NAME");
						String FK_NAME = exportedKeyResultSet.getString("FK_NAME");
						// println(" ---" + primaryKeyColumnName);
						println("   |-" + FK_NAME + ":[" + PKCOLUMN_NAME + " <- " + FKTABLE_NAME
								+ "." + FKCOLUMN_NAME + "]");

						for (ColumnMeta columnMeta : tableMeta.columns)
						{
							if (FKCOLUMN_NAME.equals(columnMeta.columnName))
							{
								columnMeta.isExportedKey = true;
								ExportedKeyMeta exportedKeyMeta = new ExportedKeyMeta(FK_NAME);
								exportedKeyMeta.FKTABLE_NAME = FKTABLE_NAME;
								exportedKeyMeta.FKCOLUMN_NAME = FKCOLUMN_NAME;
								exportedKeyMeta.FK_NAME = FK_NAME;
								exportedKeyMeta.PKTABLE_NAME = PKTABLE_NAME;
								exportedKeyMeta.PKCOLUMN_NAME = PKCOLUMN_NAME;
								columnMeta.exportedKeys.add(exportedKeyMeta);
							}
						}
					}
					exportedKeyResultSet.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					if (exportedKeyResultSet != null)
					{
						exportedKeyResultSet.close();
					}
				}
				println("------------END OF " + tableName + "------------");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return tableMetas;
	}
}
