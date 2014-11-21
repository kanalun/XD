package com.xindian.awaits;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.xindian.awaits.exception.DBAccessException;
import com.xindian.awaits.sql.ResultCallBack;
import com.xindian.awaits.sql.SQLBuilder;

/**
 * 
 * @author Elva
 * 
 * @param <T>
 */
public class Criteria<T>
{
	private List<Restriction> restrictions = new Vector<Restriction>();

	private List<Order> orderbys = new Vector<Order>();

	private Integer firstResult = -1;//

	private Integer maxResults = -1;// <=0 RP NO RES

	private String tableName;

	private Class<T> clazz;

	private Connection connection;

	private SQLBuilder sqlBuilder;

	private TableMeta<T> tableMeta;

	/**
	 * 静态工厂方法
	 * 
	 * @param <T>
	 * @param clazz
	 * @param connection
	 * @param tableMeta
	 * @param sqlBuilder
	 * @return
	 */
	static <T> Criteria<T> get(Class<T> clazz, Connection connection, TableMeta<T> tableMeta, SQLBuilder sqlBuilder)
	{
		Criteria<T> criteria = new Criteria<T>(clazz, connection, tableMeta, sqlBuilder);
		return criteria;
	}

	private Criteria(Class<T> clazz, Connection connection, TableMeta<T> tableMeta, SQLBuilder sqlBuilder)
	{
		this.sqlBuilder = sqlBuilder;
		this.connection = connection;
		this.tableMeta = tableMeta;
		this.clazz = clazz;
	}

	public List<Restriction> getRestrictions()
	{
		return restrictions;
	}

	public List<Order> getOrderBys()
	{
		return orderbys;
	}

	public Integer getFirstResult()
	{
		return firstResult;
	}

	public Integer getMaxResults()
	{
		return maxResults;
	}

	public Criteria<T> add(Restriction restriction)
	{
		restrictions.add(restriction);
		return this;
	}

	public Criteria<T> addOrder(Order order)
	{
		orderbys.add(order);
		return this;
	}

	public Criteria<T> setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
		return this;
	}

	public Criteria<T> setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
		return this;
	}

	public String getTableName() throws DBAccessException
	{
		if (tableName == null)
		{
			if (tableMeta == null)
			{
				throw new DBAccessException("Can not list " + clazz + " Cause:the type not bean mapped");
			}
			tableName = tableMeta.getTableName();
		}
		return tableName;
	}

	/**
	 * 返回唯一的结果
	 * 
	 * @return 如果没有结果返回NULL,结果唯一返回这个唯一的结果,否则(结果>1)抛出异常
	 * @throws DBAccessException
	 */
	public T uniqueResult() throws DBAccessException
	{
		List<T> list = list();
		if (list.size() > 1)
		{
			throw new DBAccessException("The Result Set is Not Unique!");
		}
		if (list.isEmpty())
		{
			return null;
		}
		return list.get(0);
	}

	public int count() throws DBAccessException
	{
		Query query = sqlBuilder.buildCountQuery(this);
		return DBHelper.query(connection, query.getSql(), new ResultCallBack<Integer>()
		{
			@Override
			public Integer dealWithResult(ResultSet rs) throws SQLException
			{
				if (rs.next())
				{
					return rs.getInt(1);
				}
				return 0;
			}
		}, query.getValues().toArray());
	}

	/**
	 * 执行查询列出所有查询得到的结果列表
	 * 
	 * @return
	 * @throws DBAccessException
	 */
	public List<T> list() throws DBAccessException
	{
		Query query = sqlBuilder.buildQuery(this);

		return DBHelper.query(connection, query.getSql(), new ResultCallBack<List<T>>()
		{
			@Override
			public List<T> dealWithResult(ResultSet rs) throws SQLException
			{
				return list(tableMeta, rs);
			}
		}, query.getValues().toArray());
	}

	// FIXME
	protected static <T> List<T> list(TableMeta<T> tableMeta, ResultSet rs) throws DBAccessException
	{
		Set<ColumnMeta> mts = tableMeta.getColumnMetas();
		try
		{
			List<T> list = new ArrayList<T>();
			while (rs.next())
			{
				T a = tableMeta.newObject();
				if (a != null)
				{
					for (ColumnMeta mt : mts)
					{
						DBHelper.callSetter(a, mt.getPropertyDescriptor(), rs.getObject(mt.getColumnName()));
					}
					list.add(a);
				}
			}
			return list;
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new DBAccessException(e);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new DBAccessException(e);
		}
	}
}
