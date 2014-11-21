package com.xd.tools.db.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 代表了一列,其中包含了以下信息,这部分信息来自JDBC文档
 * </p>
 * 
 * <pre>
 * 	TABLE_CAT String => table catalog (may be null) 
 * 	TABLE_SCHEM String => table schema (may be null) 
 * 	TABLE_NAME String => table name 
 * 	COLUMN_NAME String => column name 
 * 	DATA_TYPE int => SQL type from java.sql.Types 
 * 	TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified 
 * 	COLUMN_SIZE int => column size. 
 * 	BUFFER_LENGTH is not used. 
 * 	DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable. 
 * 	NUM_PREC_RADIX int => Radix (typically either 10 or 2) 
 * 	NULLABLE int => is NULL allowed. 
 * 	columnNoNulls - might not allow NULL values 
 * 	columnNullable - definitely allows NULL values 
 * 	columnNullableUnknown - nullability unknown 
 * 	REMARKS String => comment describing column (may be null) 
 * 	COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null) 
 * 	SQL_DATA_TYPE int => unused 
 * 	SQL_DATETIME_SUB int => unused 
 * 	CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column 
 * 	ORDINAL_POSITION int => index of column in table (starting at 1) 
 * 	IS_NULLABLE String => ISO rules are used to determine the nullability for a column. 
 * 	YES --- if the parameter can include NULLs 
 * 	NO --- if the parameter cannot include NULLs 
 * 	empty string --- if the nullability for the parameter is unknown 
 * 	SCOPE_CATLOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF) 
 * 	SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF) 
 * 	SCOPE_TABLE String => table name that this the scope of a reference attribure (null if the DATA_TYPE isn't REF) 
 * 	SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF) 
 * 	IS_AUTOINCREMENT String => Indicates whether this column is auto incremented 
 * 	YES --- if the column is auto incremented 
 * 	NO --- if the column is not auto incremented 
 * 	empty string --- if it cannot be determined whether the column is auto incremented parameter is unknown
 * </pre>
 * 
 * 
 * @author qiucongcong
 */
public class ColumnMeta implements DatabaseMeta
{
	/** COLUMN_NAME */
	public String columnName;

	/** 列类型 */
	public String typeName;

	/** 是否为主键 */
	public boolean isPrimaryKey;

	/** 是否为外键:导入 */
	public boolean isImportedKey;

	/** 外键名称 */
	public List<ImportedKeyMeta> importedKeys = new ArrayList<ImportedKeyMeta>();;

	/** 导出键:被引用外键的 */
	public boolean isExportedKey;

	public List<ExportedKeyMeta> exportedKeys = new ArrayList<ExportedKeyMeta>();;

	/** COLUMN_DEF */
	public String defaultValue;

	/** 备注 */
	public String comment;

	/** 主键名称 */
	public String primaryKeyName;

	/** 外键名称 */
	public String foreignKeyName;

	/** 列大小 */
	public int columnSize;

	/** 位数 */
	public int precision;

	/** 小数位数 :NUM_PREC_RADIX */
	public int scale;

	/** 是否可以为空 */
	public int nullable;

	public List<IndexMeta> indexMetas = new ArrayList<IndexMeta>();

	@Override
	public String toString()
	{
		return "ColumnMeta [columnName=" + columnName + ", TYPE_NAME=" + typeName + ", isPK=" + isPrimaryKey
				+ ", hasImportedKey=" + isImportedKey + ", isExportedKey=" + isExportedKey + ", defaultValue=" + defaultValue
				+ ", comment=" + comment + ", pk_name=" + primaryKeyName + ", fk_name=" + foreignKeyName + ", columnSize="
				+ columnSize + ", DECIMAL_DIGITS=" + precision + ", IS_NULLABLE=" + nullable + "]";
	}
}