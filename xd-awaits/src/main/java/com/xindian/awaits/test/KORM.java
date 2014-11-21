package com.xindian.awaits.test;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.authc.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.ColumnMeta;
import com.xindian.awaits.TableMeta;
import com.xindian.awaits.annotation.GenerationType;
import com.xindian.awaits.converter.BigIntegerToDateConverter;

@Deprecated
public class KORM
{
	DataSource dataSource;

	private static Logger logger = LoggerFactory.getLogger(KORM.class);

	static HashMap<Class<?>, TableMeta<?>> ts = new HashMap<Class<?>, TableMeta<?>>();

	private KORM()
	{

	}

	static
	{
		ConvertUtils.register(new BigIntegerToDateConverter(), java.util.Date.class);
	}

	protected static <T> List<T> list2(TableMeta<T> tableElement, ResultSet rs)
	{
		try
		{
			Set<ColumnMeta> mts = tableElement.getColumnMetas();
			try
			{
				List<T> list = new ArrayList<T>();
				while (rs.next())
				{
					T a = tableElement.newObject();
					if (a != null)
					{
						for (ColumnMeta mt : mts)
						{
							BeanUtils.setProperty(a, mt.getFieldName(), rs.getObject(mt.getColumnName()));
						}
						list.add(a);
					}
				}
				return list;
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
				return null;
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
				return null;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	protected static <T> T get(TableMeta<T> tableElement, ResultSet rs)
	{
		T bean = tableElement.newObject();
		if (bean != null)// 可以新建
		{
			Set<ColumnMeta> columnMetas = tableElement.getColumnMetas();
			try
			{
				// 注册转换器,再有必要的时候转换器会把原类型转换成目标的类型
				if (rs.next())
				{
					for (ColumnMeta columnMeta : columnMetas)
					{
						// logger.debug("COLUMN_NAME= " +
						// columnMeta.getColumnName() + //
						// "   VALUE= " +
						// rs.getObject(columnMeta.getColumnName()) + //
						// "	TYPE" +
						// rs.getMetaData().getColumnClassName(rs.findColumn(columnMeta.getColumnName()))//
						// );
						BeanUtils.setProperty(bean, columnMeta.getFieldName(), rs.getObject(columnMeta.getColumnName()));
					}
				} else
				{
					bean = null;// THERE IS NO RECORD,REUTNR NULL
				}
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
				return null;
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
				return null;
			} catch (SQLException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return bean;
	}

	@Deprecated
	protected static <T> List<T> list3(TableMeta<T> tableElement, ResultSet rs)
	{
		Set<ColumnMeta> mts = tableElement.getColumnMetas();
		try
		{
			List<T> list = new ArrayList<T>();
			while (rs.next())
			{
				T a = tableElement.newObject();
				if (a != null)
				{
					for (ColumnMeta mt : mts)
					{
						BeanUtils.setProperty(a, mt.getFieldName(), rs.getObject(mt.getColumnName()));
					}
					list.add(a);
				}
			}
			return list;
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
			return null;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	/******************************* 基本操作方法 *********************************************/

	public static <T> T get(Serializable id, final Class<T> clazz)
	{
		TableMeta<?> tableMeta = ts.get(clazz);
		if (tableMeta == null)
		{
			tableMeta = TableMeta.getTableMeta(clazz);
			ts.put(clazz, tableMeta);
		}
		if (tableMeta == null)
		{
			return null;// HANDLE ERROR
		}
		String sql = "SELECT * FROM " + tableMeta.getTableName() + " WHERE " + tableMeta.getIdColumnName() + " = " + id;
		return DBRocker.query(sql, new ResultSetHandler<T>()
		{
			@Override
			public T handle(ResultSet rs) throws SQLException
			{
				return (T) get(ts.get(clazz), rs);
			}
		});
	}

	protected static <T> List<T> list(TableMeta<T> tableElement, ResultSet rs)
	{
		Set<ColumnMeta> mts = tableElement.getColumnMetas();
		try
		{
			List<T> list = new ArrayList<T>();
			while (rs.next())
			{
				T a = tableElement.newObject();
				if (a != null)
				{
					for (ColumnMeta mt : mts)
					{
						BeanUtils.setProperty(a, mt.getFieldName(), rs.getObject(mt.getColumnName()));
					}
					list.add(a);
				}
			}
			return list;
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
			return null;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static <T> List<T> list(final Class<T> clazz)
	{
		TableMeta<?> tableMeta = ts.get(clazz);
		if (tableMeta == null)
		{
			tableMeta = TableMeta.getTableMeta(clazz);
			ts.put(clazz, tableMeta);
		}
		if (tableMeta == null)
		{
			return null;// HANDLE ERROR
		}
		String sql = "SELECT * FROM " + tableMeta.getTableName();
		return DBRocker.query(sql, new ResultSetHandler<List<T>>()
		{
			@Override
			public List<T> handle(ResultSet rs) throws SQLException
			{
				return (List<T>) list(ts.get(clazz), rs);
			}
		});
	}

	public static <T> List<T> list(final Class<T> clazz, int start, int length)
	{
		TableMeta<?> tableMeta = ts.get(clazz);
		if (tableMeta == null)
		{
			tableMeta = TableMeta.getTableMeta(clazz);
			ts.put(clazz, tableMeta);
		}
		if (tableMeta == null)
		{
			return null;// HANDLE ERROR
		}
		String sql = "SELECT * FROM " + tableMeta.getTableName() + " LIMIT ?,?";
		return DBRocker.query(sql, new ResultSetHandler<List<T>>()
		{
			@Override
			public List<T> handle(ResultSet rs) throws SQLException
			{
				return (List<T>) list(ts.get(clazz), rs);
			}
		}, start, length);
	}

	protected static Map<String, Object> getTableMap2(Object o)
	{
		Map<String, Object> s = new HashMap<String, Object>();
		TableMeta<?> tableMeta = ts.get(o.getClass());
		if (tableMeta == null)
		{
			tableMeta = TableMeta.getTableMeta(o.getClass());
			ts.put(o.getClass(), tableMeta);
		}
		Set<ColumnMeta> cs = tableMeta.getColumnMetas();
		for (ColumnMeta c : cs)
		{
			try
			{
				s.put(c.getColumnName(), BeanUtils.getProperty(o, c.getFieldName()));
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			} catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
		}
		return s;
	}

	protected static Map<String, Object> getTableMap(Object o)
	{
		Map<String, Object> s = new HashMap<String, Object>();
		TableMeta<?> tableMeta = ts.get(o.getClass());
		if (tableMeta == null)
		{
			tableMeta = TableMeta.getTableMeta(o.getClass());
			ts.put(o.getClass(), tableMeta);
		}
		Set<ColumnMeta> cs = tableMeta.getColumnMetas();
		for (ColumnMeta c : cs)
		{
			try
			{
				if (c.getGenerationType() == GenerationType.ASSIGNED)
					s.put(c.getColumnName(), BeanUtils.getProperty(o, c.getFieldName()));
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			} catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
		}
		return s;
	}

	public static Serializable save(Object o)
	{
		TableMeta<?> tableMeta = ts.get(o.getClass());
		if (tableMeta == null)
		{
			tableMeta = TableMeta.getTableMeta(o.getClass());
			ts.put(o.getClass(), tableMeta);
		}
		Map<String, Object> map = getTableMap(o);

		String s = DBRocker.nameArray(map.keySet().toArray());

		String s2 = DBRocker.paramArray(map.values().toArray());

		String sql = "INSERT INTO " + tableMeta.getTableName() + "(" + s + ") VALUES(" + s2 + ")";

		// DBRocker.updateThrowsException(connection, sql, parms)
		DBRocker.update(sql);

		return 1;
	}

	public static boolean update(Object o)
	{
		TableMeta<?> tableMeta = ts.get(o.getClass());
		if (tableMeta == null)
		{
			tableMeta = TableMeta.getTableMeta(o.getClass());
			ts.put(o.getClass(), tableMeta);
		}
		Map<String, Object> map = getTableMap(o);

		String s = DBRocker.nameArray(map.keySet().toArray());

		String s2 = DBRocker.paramArray(map.values().toArray());

		for (String n : map.keySet())
		{
			if (map.get(n) != null)
			{
				String a = n + " = '" + map.get(n).toString() + ",";
			}

		}
		return true;
	}

	public static <T> Boolean delete(Serializable id, final Class<T> clazz)
	{
		TableMeta<?> tableMeta = ts.get(clazz);
		if (tableMeta == null)
		{
			tableMeta = TableMeta.getTableMeta(clazz);
			ts.put(clazz, tableMeta);
		}
		if (tableMeta == null)
		{
			// ERROR
		}
		String sql = "DELETE FROM " + tableMeta.getTableName() + " WHERE " + tableMeta.getIdColumnName() + " = " + id;
		return DBRocker.update(sql) == 1;
	}

	public static void main(String args[])
	{
		Account account = new Account();
		account.setUesrId(120L);
		account.setMoney(20.00);
		account.setSaveMoney(6000.00);

		Person partner = new Person();
		partner.setName("小葱葱2");

		// System.out.println(delete(2, Account.class));
		System.out.print(save(partner));
		// PrintUtils.pln(get(18, User.class));
		// PrintUtils.pln(list(User.class));
		PrintUtils.pln(list(Person.class));
		// System.out.print(get(1, Account.class).getUesrId());
	}
}
