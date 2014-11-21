package com.xindian.awaits;

import static java.util.Locale.ENGLISH;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.exception.DBAccessException;
import com.xindian.awaits.sql.ResultCallBack;

public class DBHelper
{
	private static Logger logger = LoggerFactory.getLogger(DBHelper.class);

	public static PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException
	{
		return conn.prepareStatement(sql);
	}

	public static void fillStatement(PreparedStatement stmt, boolean pmdKnownBroken, Object... params) throws SQLException
	{
		if (params == null)
		{
			return;
		}

		ParameterMetaData pmd = null;
		if (!pmdKnownBroken)
		{
			pmd = stmt.getParameterMetaData();
			if (pmd.getParameterCount() < params.length)
			{
				throw new SQLException("Too many parameters: expected " + pmd.getParameterCount() + ", was given "
						+ params.length);
			}
		}
		for (int i = 0; i < params.length; i++)
		{
			if (params[i] != null)
			{
				stmt.setObject(i + 1, params[i]);
			} else
			{
				// VARCHAR works with many drivers regardless
				// of the actual column type. Oddly, NULL and
				// OTHER don't work with Oracle's drivers.
				int sqlType = Types.VARCHAR;
				if (!pmdKnownBroken)
				{
					try
					{
						sqlType = pmd.getParameterType(i + 1);
					} catch (SQLException e)
					{
						pmdKnownBroken = true;
					}
				}
				stmt.setNull(i + 1, sqlType);
			}
		}
	}

	protected static void reThrow(SQLException cause, String sql, Object... params) throws DBAccessException
	{
		String causeMessage = cause.getMessage();
		if (causeMessage == null)
		{
			causeMessage = "";
		}
		StringBuffer msg = new StringBuffer(causeMessage);

		msg.append(" Query: ");
		msg.append(sql);
		msg.append(" Parameters: ");

		if (params == null)
		{
			msg.append("[]");
		} else
		{
			msg.append(Arrays.deepToString(params));
		}

		DBAccessException e = new DBAccessException(msg.toString(), cause);
		// e.setNextException(cause);
		throw e;
	}

	public static void close(Connection conn) throws DBAccessException
	{
		try
		{
			conn.close();
		} catch (SQLException e)
		{
			throw new DBAccessException(e);
		}
	}

	public static void close(Statement stmt) throws DBAccessException
	{
		try
		{
			stmt.close();
		} catch (SQLException e)
		{
			throw new DBAccessException(e);
		}
	}

	public static void close(ResultSet rs) throws DBAccessException
	{
		try
		{
			rs.close();
		} catch (SQLException e)
		{
			throw new DBAccessException(e);
		}
	}

	public static <T> T query(Connection conn, String sql, ResultCallBack<T> resultCallBack, Object... params)
			throws DBAccessException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		T result = null;
		try
		{
			log(sql, params);
			stmt = prepareStatement(conn, sql);
			fillStatement(stmt, true, params);
			rs = stmt.executeQuery();
			result = resultCallBack.dealWithResult(rs);
		} catch (SQLException e)
		{
			reThrow(e, sql, params);
		} finally
		{
			try
			{
				close(rs);
			} finally
			{
				close(stmt);
			}
		}
		return result;
	}

	/**
	 * Returns a String which capitalizes the first letter of the string.
	 */
	public static String capitalize(String name)
	{
		if (name == null || name.length() == 0)
		{
			return name;
		}
		return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
	}

	private static PropertyDescriptor[] propertyDescriptors(TableMeta<?> t) throws SQLException
	{
		try
		{
			List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();

			Set<ColumnMeta> columnMetas = t.getColumnMetas();
			logger.debug("Bean: " + t.getClazz());
			for (ColumnMeta columnMeta : columnMetas)
			{
				PropertyDescriptor propertyDescriptor = null;
				String read;
				String write = "set" + capitalize(columnMeta.getFieldName());
				if (t.equals(Boolean.TYPE))
				{
					read = "is" + capitalize(columnMeta.getFieldName());

				} else
				{
					read = "get" + capitalize(columnMeta.getFieldName());
				}
				propertyDescriptor = new PropertyDescriptor(columnMeta.getFieldName(), t.getClazz(), read, write);
				logger.debug("	Field= " + columnMeta.getFieldName() + "  ColumnName= " + columnMeta.getColumnName()
						+ "  readMethodName= " + read + "  writeMethodName= " + write);
				propertyDescriptors.add(propertyDescriptor);
			}
			return propertyDescriptors.toArray(new PropertyDescriptor[0]);

		} catch (IntrospectionException e)
		{
			throw new SQLException("Bean introspection failed: " + e.getMessage());
		}
	}

	public static void main(String args[]) throws InstantiationException
	{

	}

	public static void callSetter(Object target, PropertyDescriptor prop, Object value) throws SQLException
	{
		Method setter = prop.getWriteMethod();

		if (setter == null)
		{
			return;
		}

		Class<?>[] paramTypes = setter.getParameterTypes();
		try
		{
			// convert types for some popular ones
			if (value != null)
			{
				if (value instanceof java.util.Date)
				{
					if (paramTypes[0].getName().equals("java.sql.Date"))
					{
						value = new java.sql.Date(((java.util.Date) value).getTime());
					} else if (paramTypes[0].getName().equals("java.sql.Time"))
					{
						value = new java.sql.Time(((java.util.Date) value).getTime());
					} else if (paramTypes[0].getName().equals("java.sql.Timestamp"))
					{
						value = new java.sql.Timestamp(((java.util.Date) value).getTime());
					}
				}
			}

			// Don't call setter if the value object isn't the right type
			if (isCompatibleType(value, paramTypes[0]))
			{
				setter.invoke(target, new Object[] { value });
			} else
			{
				Converter converter = ConverterFactory.getConverter(value.getClass(), paramTypes[0]);
				if (converter != null)
				{
					setter.invoke(target, converter.convert(null, value));
				} else
				{
					throw new SQLException("Cannot set " + prop.getName() + ": incompatible types. Type:" + value.getClass()
							+ "  != " + paramTypes[0]);
				}
			}

		} catch (IllegalArgumentException e)
		{
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());

		} catch (IllegalAccessException e)
		{
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());

		} catch (InvocationTargetException e)
		{
			throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * 判断是否为兼容类型
	 * 
	 * @param value
	 * @param type
	 * @return
	 */
	private static boolean isCompatibleType(Object value, Class<?> type)
	{
		// type.isPrimitive();
		// Do object check first, then primitives
		if (value == null || type.isInstance(value))
		{
			return true;

		} else if (type.equals(Integer.TYPE) && Integer.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Long.TYPE) && Long.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Double.TYPE) && Double.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Float.TYPE) && Float.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Short.TYPE) && Short.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Byte.TYPE) && Byte.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Character.TYPE) && Character.class.isInstance(value))
		{
			return true;

		} else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value))
		{
			return true;

		} else
		{
			return false;
		}

	}

	private static void log(String sql, Object... params)
	{
		StringBuffer msg = new StringBuffer();
		msg.append(Arrays.deepToString(params));
		logger.debug("SQL: " + sql + msg);
	}

	public static int update(Connection conn, String sql, Object... params) throws DBAccessException
	{
		PreparedStatement stmt = null;
		try
		{
			log(sql, params);
			stmt = prepareStatement(conn, sql);
			fillStatement(stmt, true, params);
			int change = stmt.executeUpdate();
			return change;
		} catch (SQLException e)
		{
			reThrow(e, sql, params);
			return 0;
		} finally
		{
			close(stmt);
		}
	}
}
