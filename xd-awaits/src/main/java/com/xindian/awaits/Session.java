package com.xindian.awaits;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.TableMeta.ColumnFilter;
import com.xindian.awaits.annotation.GenerationType;
import com.xindian.awaits.exception.DBAccessException;
import com.xindian.awaits.sql.ResultCallBack;
import com.xindian.awaits.sql.SQLUtils;

public class Session
{
	private static Logger logger = LoggerFactory.getLogger(Session.class);

	private Connection connection;

	private MappingMetaProvider mappingMetaProvider;

	private HashMap<Class<?>, TableMeta<?>> tableMetas;

	private Configuration configuration;

	/**
	 * Read an Entity From DB by it's Id
	 * 
	 * @param <T>
	 * @param id
	 * @param clazz
	 *            Entity Type
	 * @return
	 * @throws DBAccessException
	 */
	public <T> T get(Serializable id, final Class<T> clazz) throws DBAccessException
	{
		final TableMeta<?> tableMeta = tableMetas.get(clazz);
		if (tableMeta == null)
		{
			throw new DBAccessException("Can not Mapping Meta for Class Type:" + clazz
					+ " Cause:the type not bean mapped in Config");
		}
		// final TableMeta<?> tableMeta = configuration.getTableMeta(clazz);

		List<T> res = createCriteria(clazz).add(Restrictions.eq(tableMeta.getIdColumnName(), id)).setMaxResults(1).list();
		if (res.isEmpty())
		{
			return null;
		}
		return res.get(0);
	}

	public boolean update(Object object) throws DBAccessException
	{
		final TableMeta<?> tableMeta = tableMetas.get(object.getClass());
		if (tableMeta == null)
		{
			throw new DBAccessException("Can not Mapping Meta for Class Type:" + object.getClass()
					+ " Cause:the type not bean mapped in Config");
		}
		Updator<?> updator = createUpdater(object.getClass());

		Set<ColumnMeta> columnMetas = tableMeta.getColumnMetas();
		for (ColumnMeta columnMeta : columnMetas)
		{
			if (columnMeta.isUpdateable() && !columnMeta.isId())
			{
				updator.set(columnMeta.getColumnName(), columnMeta.getValueFromObject(object));
			}
		}
		updator.add(Restrictions.eq(tableMeta.getIdColumnName(), tableMeta.getIdValue(object)));
		return updator.update() == 1;
		//
	}

	public <T> T query(String sql, ResultCallBack<T> callBack, Object... params) throws DBAccessException
	{
		return DBHelper.query(connection, sql, callBack, params);
	}

	public int update(String sql, Object... params) throws DBAccessException
	{
		return DBHelper.update(connection, sql, params);
	}

	/**
	 * List All Entities From DB By Designated Type
	 * 
	 * @param <T>
	 * @param clazz
	 *            Designated Type
	 * @return
	 * @throws DBAccessException
	 */
	public <T> List<T> list(final Class<T> clazz) throws DBAccessException
	{
		final TableMeta<?> tableMeta = tableMetas.get(clazz);
		if (tableMeta == null)
		{
			throw new DBAccessException("Can not Mapping Meta for Class Type:" + clazz
					+ " Cause:the type not bean mapped in Config");
		}
		List<T> res = createCriteria(clazz).list();
		return res;
	}

	public int count(final Class<?> clazz) throws DBAccessException
	{
		final TableMeta<?> tableMeta = tableMetas.get(clazz);
		if (tableMeta == null)
		{
			throw new DBAccessException("Can not Mapping Meta for Class Type:" + clazz
					+ " Cause:the type not bean mapped in Config");
		}
		return createCriteria(clazz).count();
	}

	/**
	 * List Some Entities From DB By Designated Type
	 * 
	 * @param <T>
	 * @param start
	 *            Start index of the result ,if this value less than 0(<0),it
	 *            will be ignore
	 * @param length
	 *            Max length of the result ,if this value less than 0(<0),it
	 *            will be ignore
	 * @param clazz
	 * @return
	 * @throws DBAccessException
	 */
	public <T> List<T> list(int start, int length, final Class<T> clazz) throws DBAccessException
	{
		final TableMeta<?> tableMeta = tableMetas.get(clazz);
		if (tableMeta == null)
		{
			throw new DBAccessException("Can not Mapping Meta for Class Type:" + clazz
					+ " Cause:the type not bean mapped in Config");
		}
		List<T> res = createCriteria(clazz).setFirstResult(start).setMaxResults(length).list();
		return res;
	}

	/**
	 * Delete An Entity From The DB(BY HIS ID !!!)
	 * 
	 * @param object
	 * @return
	 * @throws DBAccessException
	 */
	public boolean delete(Object object) throws DBAccessException
	{
		TableMeta<?> tableMeta = tableMetas.get(object.getClass());
		if (tableMeta == null)
		{
			throw new DBAccessException("Can not Mapping Meta for Class Type:" + object.getClass()
					+ " Cause:the type not bean mapped in Config");
		}
		String sql = "DELETE FROM " + tableMeta.getTableName() + " WHERE " + tableMeta.getIdColumnName() + " = ?";
		return DBHelper.update(connection, sql, tableMeta.getIdValue(object)) == 1;
	}

	/**
	 * Delete An Entity From The DB
	 * 
	 * @param object
	 * @return
	 * @throws DBAccessException
	 */
	public <T> boolean delete(Serializable id, final Class<T> clazz) throws DBAccessException
	{
		TableMeta<?> tableMeta = tableMetas.get(clazz);
		if (tableMeta == null)
		{
			throw new DBAccessException("Can not Mapping Meta for Class Type:" + clazz
					+ " Cause:the type not bean mapped in Config");
		}
		String sql = "DELETE FROM " + tableMeta.getTableName() + " WHERE " + tableMeta.getIdColumnName() + " = ?";
		return DBHelper.update(connection, sql, id) == 1;
	}

	protected TableMeta<?> getTableMeta(Object o) throws DBAccessException
	{
		TableMeta<?> tableMeta = tableMetas.get(o.getClass());
		if (tableMeta == null)
		{
			throw new DBAccessException("Can not Mapping Meta for Object,+" + o + " Cause:the type not bean mapped in Config");
		}
		return tableMeta;
	}

	private static final String INSERT_INTO = "INSERT INTO ";

	private static final String VALUES = "VALUES";

	private static final String GET_ID_SQL = "SELECT LAST_INSERT_ID()";

	/**
	 * Persist an Object
	 * 
	 * @param o
	 * @return Id of the persisted object
	 * @throws DBAccessException
	 */
	public Serializable save(Object o) throws DBAccessException
	{
		TableMeta<?> tableMeta = this.getTableMeta(o);

		if (!tableMeta.isCompatible(o))
		{
			throw new DBAccessException("Object " + o + "IS NOT Compatible FOR This  TableMeta Type" + tableMeta.getClazz());//
		}
		ColumnMeta idColumnMeta = tableMeta.getIdColumnMeta();

		GenerationType idGenerationType = idColumnMeta.getGenerationType();

		Serializable id = null;

		ColumnFilter idFilter = ColumnFilter.ALL;

		if (idGenerationType.equals(GenerationType.ASSIGNED))
		{
			id = tableMeta.getIdValue(o);
		} else if (idGenerationType.equals(GenerationType.IDENTITY))// 如果ID由数据库生成,则插入语句忽略ID
		{
			idFilter = ColumnFilter.FALSE;
		}
		// FIXME
		Map<String, Object> map = tableMeta.getTableMap(o, idFilter, ColumnFilter.ALL, ColumnFilter.TRUE, ColumnFilter.ALL);

		// FIXME prevent empty
		String columnNames = SQLUtils.nameArray(map.keySet().toArray(new String[0]));

		String sql = INSERT_INTO + tableMeta.getTableName() + "(" + columnNames + ") VALUES("
				+ SQLUtils.questionMarks(map.keySet().size()) + ")";

		Object[] values = null;
		if (!map.values().isEmpty())
		{
			values = map.values().toArray();
		}
		if (DBHelper.update(connection, sql, values) == 1)
		{
			// /FIXME USE G
			if (idGenerationType.equals(GenerationType.IDENTITY))// 如果ID由数据库生成,则插入语句忽略ID
			{
				id = DBHelper.query(connection, GET_ID_SQL, new ResultCallBack<Serializable>()
				{
					@Override
					public Serializable dealWithResult(ResultSet rs) throws SQLException
					{
						if (rs.next())
							return (Serializable) rs.getObject(1);
						return null;
					}
				});
			}
			return id;
			// RETURN THE ID
		}
		// throw new DBAccessException("Can not save Object,+" + o +
		// " Cause:the type not bean mapped before");
		return null;
	}

	Session(Configuration configuration, Connection connection, HashMap<Class<?>, TableMeta<?>> tableMetas)
	{
		this.tableMetas = tableMetas;
		this.connection = connection;
		this.configuration = configuration;
	}

	/**
	 * begin a Transaction(Make sure your database supports transactions)
	 * 
	 * @return
	 * @throws DBAccessException
	 */
	public Transaction beginTransaction() throws DBAccessException
	{
		Transaction transaction = new Transaction(this.connection);
		transaction.begin();
		return transaction;
	}

	/**
	 * Create an Criteria
	 * 
	 * @param <T>
	 * @param persistentClass
	 * @return
	 */
	public <T> Criteria<T> createCriteria(Class<T> persistentClass)
	{
		Criteria<T> criteria = Criteria.get(persistentClass, this.connection, TableMeta.getTableMeta(persistentClass),
				configuration.getSqlBuilder());
		return criteria;
	}

	/**
	 * Create an Updater
	 * 
	 * @param <T>
	 * @param persistentClass
	 * @return
	 */
	public <T> Updator<T> createUpdater(Class<T> persistentClass)
	{
		Updator<T> updator = Updator.get(persistentClass, this.connection, configuration.getSqlBuilder(), tableMetas);
		return updator;
	}

	/**
	 * Get the JDBC connection of this Session.<br>
	 * <br>
	 * If the session is using aggressive collection release (as in a CMT
	 * environment), it is the application's responsibility to close the
	 * connection returned by this call. Otherwise, the application should not
	 * close the connection.
	 * 
	 * @return the JDBC connection in use by the <tt>Session</tt>
	 * @throws DBAccessException
	 *             if the <tt>Session</tt> is disconnected
	 */
	public Connection connection() throws DBAccessException
	{
		if (closed)
		{
			throw new DBAccessException("the Session is disconnected");
		}
		return this.connection;
	}

	private boolean closed;

	/**
	 * End the session by releasing the JDBC connection and cleaning up. It is
	 * not strictly necessary to close the session but you must at least
	 * disconnect it.
	 * 
	 * @return the connection provided by the application or null.
	 * @throws DBAccessException
	 *             Indicates problems cleaning up.
	 */
	public Connection close() throws DBAccessException
	{
		try
		{
			this.connection.close();
			this.connection = null;
			this.closed = true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new DBAccessException();
		}
		return this.connection;
	}
}
