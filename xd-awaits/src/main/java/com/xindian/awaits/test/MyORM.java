package com.xindian.awaits.test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.dbutils.ResultSetHandler;

import com.xindian.awaits.TableMeta;
import com.xindian.awaits.annotation.Column;
import com.xindian.awaits.annotation.Entity;
import com.xindian.awaits.annotation.Id;
import com.xindian.awaits.annotation.Table;
import com.xindian.tg.beans.Account;
import com.xindian.tg.dao.jdbc.DBRocker;
import com.xindian.tg.utils.PrintUtils;

/**
 * 废弃的测试代码
 * 
 * @author Elva
 * 
 */
@Deprecated
public class MyORM
{
	@Deprecated
	public static <T> Serializable save(T o)
	{
		Class<?> t = o.getClass();
		System.out.println(getTableName(t));
		return null;
	}

	@Deprecated
	protected static String getTableName(Class<?> t)
	{
		boolean isEntity = t.isAnnotationPresent(Entity.class);
		boolean isTable = t.isAnnotationPresent(Table.class);
		if (isEntity && isTable)
		{
			Table table = t.getAnnotation(Table.class);
			return table.name();
		}
		return null;
	}

	@Deprecated
	protected static String getId(Class<?> t)
	{
		boolean isEntity = t.isAnnotationPresent(Entity.class);
		boolean isTable = t.isAnnotationPresent(Table.class);
		if (isEntity && isTable)
		{
			Table table = t.getAnnotation(Table.class);

			return table.name();
		}
		return null;
	}

	@Deprecated
	protected static Set<Column> allColumns(Class<?> t)
	{
		Field[] fields = t.getFields();
		Set<Column> set = new HashSet<Column>();
		for (int i = 0; i < fields.length; i++)
		{
			boolean isColumn = fields[i].isAnnotationPresent(Column.class);
			if (isColumn)
			{
				set.add(fields[i].getAnnotation(Column.class));
			}
		}
		return set;
	}

	@Deprecated
	protected static Set<String> allColumnNames(Class<?> t)
	{
		Field[] fields = t.getDeclaredFields();
		Set<String> set = new HashSet<String>();
		// PrintUtils.p(fields.length);
		for (int i = 0; i < fields.length; i++)
		{
			boolean otherFlag = fields[i].isAnnotationPresent(Column.class);
			if (otherFlag)
			{
				PrintUtils.pln(fields[i].getGenericType());
				set.add(fields[i].getAnnotation(Column.class).name());
			}
		}
		return set;
	}

	@Deprecated
	protected static Set<Field> allColumnFields(Class<?> t)
	{
		Field[] fields = t.getFields();
		Set<Field> set = new HashSet<Field>();
		for (int i = 0; i < fields.length; i++)
		{
			boolean isColumn = fields[i].isAnnotationPresent(Column.class);
			if (isColumn)
			{
				set.add(fields[i]);
			}
		}
		return set;
	}

	@Deprecated
	protected static Set name(Class<?> t)
	{
		// 把JavaEyer这一类有利用到@Name的全部方法保存到Set中去
		Method[] method = t.getMethods();
		Set<Method> set = new HashSet<Method>();
		for (int i = 0; i < method.length; i++)
		{
			boolean otherFlag = method[i].isAnnotationPresent(Column.class);
			if (otherFlag)
			{
				set.add(method[i]);
			}
		}
		return set;
	}

	@Deprecated
	public static String getIdCName(Class<?> clazz)
	{
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			boolean isId = fields[i].isAnnotationPresent(Id.class);
			boolean isc = fields[i].isAnnotationPresent(Column.class);
			if (isId && isc)
			{
				return fields[i].getAnnotation(Column.class).name();
			}
		}
		return null;
	}

	@Deprecated
	public static <T> T get2(Serializable id, final Class<T> clazz)
	{
		String sql = "SELECT * FROM " + getTableName(clazz) + " WHERE " + getIdCName(clazz) + " = " + id;
		return DBRocker.query(sql, new ResultSetHandler<T>()
		{
			@Override
			public T handle(ResultSet rs) throws SQLException
			{
				try
				{
					T a = clazz.newInstance();
					ResultSetMetaData rm = rs.getMetaData();
					int c = rm.getColumnCount();
					for (int i = 0; i < c; i++)
					{
						String cname = rm.getColumnName(i);
						String ctype = rm.getColumnClassName(i);

					}
					return a;
				} catch (InstantiationException e)
				{
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	@Deprecated
	public static <T> T get(Serializable id, final Class<T> clazz)
	{
		String sql = "SELECT * FROM " + getTableName(clazz) + " WHERE " + getIdCName(clazz) + " = " + id;
		return DBRocker.query(sql, new ResultSetHandler<T>()
		{
			@Override
			public T handle(ResultSet rs) throws SQLException
			{
				try
				{
					T a = clazz.newInstance();
					ResultSetMetaData rm = rs.getMetaData();
					int c = rm.getColumnCount();
					for (int i = 0; i < c; i++)
					{
						String cname = rm.getColumnName(i);
						String ctype = rm.getColumnClassName(i);

					}
					return a;
				} catch (InstantiationException e)
				{
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	@Deprecated
	public <T> T get(TableMeta<T> tableElement, ResultSet rs)
	{
		T a = null;
		try
		{
			a = tableElement.getClazz().newInstance();
			{

			}
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return a;
	}

	/**
	 * ERORO
	 * 
	 * @param args
	 */

	public static void main(String[] args)
	{
		Account account = new Account();

		// save(new Account());
		PrintUtils.pln(allColumnNames(account.getClass()));
		PrintUtils.pln(get(121212, Account.class));

	}
}
