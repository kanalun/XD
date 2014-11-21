package com.xindian.awaits;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.annotation.Column;
import com.xindian.awaits.annotation.GeneratedValue;
import com.xindian.awaits.annotation.GenerationType;
import com.xindian.awaits.annotation.Id;
import com.xindian.awaits.annotation.Transient;
import com.xindian.awaits.exception.AwaitsException;

/**
 * 列的元数据
 * 
 * @author Elva
 */
public class ColumnMeta
{
	private static Logger logger = LoggerFactory.getLogger(ColumnMeta.class);

	private TableMeta<?> tableMeta;

	/** isId Flag idIs A PK */
	private boolean id;//

	/** 属性名称 */
	private String fieldName;

	/** 字段类型 */
	private Class<?> fieldType;

	/** 列名称 */
	private String columnName;

	private Class<?> columnType;

	/** 是否可以为空 */
	private boolean nullable;

	/** 是否为瞬时字段:不存数据库 */
	private boolean isTransient;

	/** 是否允许插入 */
	private boolean insertable;

	/** 是否允许更新 */
	private boolean updateable;

	/** 数据宽度 */
	private int length;

	/** 数据生成方式 */
	private GenerationType generationType = GenerationType.ASSIGNED;

	public Class<?> getFieldType()
	{
		return fieldType;
	}

	public static ColumnMeta get(Field field, TableMeta<?> tableMeta)
	{
		boolean isColumn = field.isAnnotationPresent(Column.class);
		if (isColumn)
		{
			return new ColumnMeta(field, tableMeta);
		} else
		{
			return null;
		}
	}

	private ColumnMeta(Field field, TableMeta<?> tableMeta)
	{
		this.tableMeta = tableMeta;

		this.id = field.isAnnotationPresent(Id.class);

		this.isTransient = field.isAnnotationPresent(Transient.class);

		this.nullable = field.getAnnotation(Column.class).nullable();

		this.insertable = field.getAnnotation(Column.class).insertable();

		this.updateable = field.getAnnotation(Column.class).updateable();

		this.fieldName = field.getName();

		this.columnName = field.getAnnotation(Column.class).name();

		if (this.columnName == null)
		{
			this.columnName = this.fieldName;
		}

		this.fieldType = field.getType();

		this.length = field.getAnnotation(Column.class).length();

		if (field.isAnnotationPresent(GeneratedValue.class))
		{
			this.generationType = field.getAnnotation(GeneratedValue.class).strategy();
		}
	}

	public boolean isId()
	{
		return id;
	}

	public boolean isNullable()
	{
		return nullable;
	}

	public boolean isInsertable()
	{
		return insertable;
	}

	public boolean isUpdateable()
	{
		return updateable;
	}

	public boolean isTransient()
	{
		return this.isTransient;
	}

	public int getLength()
	{
		return length;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public GenerationType getGenerationType()
	{
		return generationType;
	}

	PropertyDescriptor propertyDescriptor;

	public PropertyDescriptor getPropertyDescriptor() throws IntrospectionException
	{
		if (propertyDescriptor == null)
		{
			String read;
			String write = "set" + DBHelper.capitalize(this.getFieldName());
			if (this.fieldType.equals(Boolean.TYPE) || this.fieldType.equals(Boolean.class))
			{
				read = "is" + DBHelper.capitalize(this.getFieldName());
			} else
			{
				read = "get" + DBHelper.capitalize(this.getFieldName());
			}
			propertyDescriptor = new PropertyDescriptor(this.getFieldName(), tableMeta.getClazz(), read, write);
		}
		// logger.debug("	Field= " + this.getFieldName() + "  ColumnName= " +
		// this.getColumnName() + "  readMethodName= " + read
		// + "  writeMethodName= " + write);
		return propertyDescriptor;
	}

	// 根据这个列的META_DATA从对象中获取数据
	public Object getValueFromObject(Object o) throws AwaitsException
	{
		try
		{
			PropertyDescriptor propertyDescriptor = getPropertyDescriptor();
			Method readId = propertyDescriptor.getReadMethod();
			Object idValue = null;
			idValue = readId.invoke(o, new Object[0]);
			return idValue;
		} catch (Exception e)
		{
			throw new AwaitsException("Can not get value from " + o);
		}
	}

	private static TypeTable typeTable = new TypeTable();

	static
	{
		typeTable.init();
	}

	private String getColumnTypeStr()
	{
		String x = typeTable.getSQLType(fieldType, 1);
		if (length > 0)
		{
			x += "(" + length + ")";
		}
		return x;
	}

	private final static String NOT_NULL = " not null ";

	private final static String AUTO_INCREMENT = " auto_increment ";

	// FIXME
	public String getColumnDDL()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(getColumnName()).append("   ").append(getColumnTypeStr());
		if (!isNullable() || isId())
		{
			sb.append(NOT_NULL);
		}
		if (isId())
		{
			sb.append(AUTO_INCREMENT);
		}
		// id bigint not null auto_increment,
		return sb.toString();
	}

	@Deprecated
	// TEST CODE
	public static void main(String args[]) throws SecurityException, NoSuchFieldException
	{
		logger.debug(byte[].class + "");
	}
}
