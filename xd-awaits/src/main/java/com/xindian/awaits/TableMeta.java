package com.xindian.awaits;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.annotation.Table;
import com.xindian.awaits.exception.AwaitsException;

/**
 * 表的元数据,表示对象和表的映射关系,存储了映射元数据,提供了表和类映射的基本方法
 * 
 * @author Elva
 * 
 * @param <T>
 */
public class TableMeta<T>
{
	private static Logger logger = LoggerFactory.getLogger(TableMeta.class);

	private Set<ColumnMeta> columnMetas = new HashSet<ColumnMeta>();

	private ColumnMeta idColumnMeta;

	private String tableName;

	private Class<T> clazz;

	private TableMeta()
	{

	}

	public Serializable getIdValue(Object object)
	{
		try
		{
			PropertyDescriptor idPropertyDescriptor = getIdColumnMeta().getPropertyDescriptor();
			Method readId = idPropertyDescriptor.getReadMethod();
			Object idValue = readId.invoke(object, new Object[0]);
			return (Serializable) idValue;
		} catch (IntrospectionException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
		}
		return null;
	}

	// 检查类型,及设定的值是否符合注解中的定义
	/**
	 * check Compatible
	 */
	public boolean isCompatible(Object o) throws AwaitsException
	{
		if (!o.getClass().equals(this.getClazz()))
		{
			logger.debug("类型不同");
			return false;
			// throw new ORMException("NOT THE SAME TYPE:" + o);
		}
		for (ColumnMeta columnMeta : columnMetas)
		{
			try
			{
				Method readColum = columnMeta.getPropertyDescriptor().getReadMethod();
				Object value = readColum.invoke(o, new Object[0]);

				if (!columnMeta.isNullable() && value == null)
				{
					return false;
				}
			} catch (IntrospectionException e)
			{
				throw new AwaitsException("无法得到调用方法", e);
			} catch (Exception e)
			{
				throw new AwaitsException(" 无法调用", e);
			}
		}
		return true;
	}

	private ColumnMeta getColumnMetaByFieldName(String fieldName)
	{
		for (ColumnMeta columnMeta : columnMetas)
		{
			if (columnMeta.getFieldName().equals(fieldName))
			{
				return columnMeta;
			}
		}
		return null;
	}

	private ColumnMeta getColumnMetaByColumnName(String columnName)
	{
		for (ColumnMeta columnMeta : columnMetas)
		{
			if (columnMeta.getColumnName().equals(columnName))
			{
				return columnMeta;
			}
		}
		return null;
	}

	public static <T> TableMeta<T> getTableMeta(Class<T> clazz)
	{
		TableMeta<T> tableMeta = new TableMeta<T>();
		boolean isTable = clazz.isAnnotationPresent(Table.class);
		if (isTable)
		{
			Table table = clazz.getAnnotation(Table.class);
			tableMeta.tableName = table.name();
			if ("".equals(tableMeta.tableName))
			{
				tableMeta.tableName = clazz.getSimpleName();
			}
			tableMeta.clazz = clazz;
		} else
		{
			return null;// NOT A TABLE
		}
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			ColumnMeta columnElement = ColumnMeta.get(fields[i], tableMeta);
			if (columnElement != null)
			{
				tableMeta.columnMetas.add(columnElement);
			}
		}
		return tableMeta;
	}

	public ColumnMeta getIdColumnMeta()
	{
		if (idColumnMeta == null)
		{
			for (ColumnMeta c : columnMetas)
			{
				if (c.isId())
				{
					idColumnMeta = c;
					break;
				}
			}
		}
		return idColumnMeta;
	}

	/**
	 * new an Empty Object by this meta data
	 * 
	 * @return
	 */
	public T newObject()
	{
		try
		{
			return clazz.newInstance();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private T newObject(Map<String, Object> data)
	{
		try
		{
			return clazz.newInstance();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get the TableName
	 * 
	 * @return
	 */
	public String getTableName()
	{
		return tableName;
	}

	/**
	 * 
	 * @return
	 */
	public Class<T> getClazz()
	{
		return clazz;
	}

	public String getIdColumnName()
	{
		return getIdColumnMeta().getColumnName();
	}

	/**
	 * Get all ColumnMetas Including The Id ColumnMeta
	 * 
	 * @return
	 * 
	 */
	public Set<ColumnMeta> getColumnMetas()
	{
		return columnMetas;
	}

	protected Map<String, Object> getTableMap(Object o) throws AwaitsException
	{
		Map<String, Object> s = new HashMap<String, Object>();

		Set<ColumnMeta> cs = this.getColumnMetas();

		for (ColumnMeta c : cs)
		{
			s.put(c.getColumnName(), c.getValueFromObject(o));
		}
		return s;
	}

	// 过滤器,ALL表示不过滤,返回所有;TRUE表示对值为true的进行保留;FALSE表示对值为false的保留
	enum ColumnFilter
	{
		ALL, TRUE, FALSE
	}

	private boolean filter(boolean x, ColumnFilter filter)
	{
		if (filter.equals(ColumnFilter.TRUE))
		{
			return x;
		} else if (filter.equals(ColumnFilter.FALSE))
		{
			return !x;
		} else
		{
			return true;
		}
	}

	protected Map<String, Object> getTableMap(Object o, ColumnFilter id, ColumnFilter nullable, ColumnFilter insertable,
			ColumnFilter updateable) throws AwaitsException
	{
		Map<String, Object> s = new HashMap<String, Object>();

		Set<ColumnMeta> cs = this.getColumnMetas();

		for (ColumnMeta c : cs)
		{
			// 对列进行过滤,对
			boolean flag = filter(c.isId(), id) && filter(c.isNullable(), nullable) && filter(c.isInsertable(), insertable)
					&& filter(c.isUpdateable(), updateable);
			if (flag)
			{
				s.put(c.getColumnName(), c.getValueFromObject(o));
			}

		}
		return s;
	}

	private StringBuffer ddl;

	// FIXME
	public String getDDL()
	{
		if (ddl == null)
		{
			ddl = new StringBuffer();
			ddl.append("CREATE TABLE ").append(getTableName()).append("\n(\n");
			for (ColumnMeta columnMeta : columnMetas)
			{
				ddl.append("	" + columnMeta.getColumnDDL() + ",\n");
			}
			if (getIdColumnMeta() != null)
			{
				ddl.append(" 	PRIMARY KEY (" + getIdColumnMeta().getColumnName() + ")");
			}
			ddl.append("\n)");
		}
		return ddl.toString();
	}
}
