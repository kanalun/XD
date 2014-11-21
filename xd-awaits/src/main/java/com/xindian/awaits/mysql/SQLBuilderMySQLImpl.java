package com.xindian.awaits.mysql;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.xindian.awaits.ColumnMeta;
import com.xindian.awaits.Criteria;
import com.xindian.awaits.Inserter;
import com.xindian.awaits.Order;
import com.xindian.awaits.Query;
import com.xindian.awaits.Restriction;
import com.xindian.awaits.TableMeta;
import com.xindian.awaits.TypeTable;
import com.xindian.awaits.Updator;
import com.xindian.awaits.sql.SQLBuilder;
import com.xindian.awaits.validate.Validator;

/**
 * 实现了MYSQL SQL查询的构造
 * 
 * @author Elva
 * 
 */
public class SQLBuilderMySQLImpl implements SQLBuilder
{
	private final static String SELECT = "SELECT ";

	private final static String FROM = " FROM ";

	private final static String WHERE = " WHERE ";

	private final static String ORDER_BY = " ORDER BY ";

	private final static String LIMIT = " LIMIT ";

	private final static String UPDATE = "UPDATE ";

	private final static String SET = " SET ";

	private final static String INSERT_INTO = "INSERT INTO ";

	private final static String VALUES = "VALUES";

	private final static String NOT_NULL = " NOT NULL ";

	private final static String AUTO_INCREMENT = " AUTO_INCREMENT ";

	private final static String AND = " AND ";

	@Override
	public Query buildInserter(Inserter inserter)
	{
		StringBuffer sql = new StringBuffer();// SQL
		return null;
	}

	@Override
	public Query buildCountQuery(Criteria<?> criteria)
	{
		Query query = new Query();

		List<Object> values = new Vector<Object>();// 参数列表

		StringBuffer sql = new StringBuffer();// SQL

		sql.append(SELECT);

		sql.append("COUNT(*)");// SELECTIONs

		sql.append(FROM);

		sql.append(criteria.getTableName());

		if (Validator.notEmpty(criteria.getRestrictions()))
		{
			sql.append(WHERE);
			boolean firstFlg = true;
			List<Restriction> restrictions = criteria.getRestrictions();
			for (Restriction restriction : restrictions)
			{
				if (firstFlg)
				{
					sql.append(restriction.getSql());
					firstFlg = false;
				} else
				{
					sql.append(" AND ").append(restriction.getSql());
				}
				values.addAll(Arrays.asList(restriction.getValues()));
			}
		}
		query.setSql(sql.toString());
		query.setValues(values);
		return query;
	}

	@Override
	public Query buildQuery(Criteria<?> criteria)
	{
		Query query = new Query();

		List<Object> values = new Vector<Object>();// 参数列表

		StringBuffer sql = new StringBuffer();// SQL

		sql.append(SELECT);

		sql.append("*");// SELECTIONs

		sql.append(FROM);

		sql.append(criteria.getTableName());

		if (Validator.notEmpty(criteria.getRestrictions()))
		{
			sql.append(WHERE);
			boolean firstFlg = true;
			List<Restriction> restrictions = criteria.getRestrictions();
			for (Restriction restriction : restrictions)
			{
				if (firstFlg)
				{
					sql.append(restriction.getSql());
					firstFlg = false;
				} else
				{
					sql.append(" AND ").append(restriction.getSql());
				}
				values.addAll(Arrays.asList(restriction.getValues()));
			}
		}
		if (Validator.notEmpty(criteria.getOrderBys()))
		{
			sql.append(ORDER_BY);
			boolean firstFlg2 = true;
			for (Order order : criteria.getOrderBys())
			{
				if (firstFlg2)
				{
					sql.append(order.getSubSql());
					firstFlg2 = false;
				} else
				{
					sql.append(" , ").append(order.getSubSql());
				}
			}
		}
		if (criteria.getFirstResult() >= 0 && criteria.getMaxResults() >= 0)// firstResult
																			// &&
																			// maxResults
		{
			sql.append(LIMIT).append("?").append(" , ").append("?");
			values.add(criteria.getFirstResult());
			values.add(criteria.getMaxResults());

		} else if (criteria.getFirstResult() < 0 && criteria.getMaxResults() >= 0)// maxResults
																					// ONLY
		{
			sql.append(LIMIT).append("?");
			values.add(criteria.getMaxResults());

		} else if (criteria.getFirstResult() >= 0 && criteria.getMaxResults() < 0)// firstResult
																					// ONLY
		{
			sql.append(LIMIT).append("?").append(" , ").append(Long.MAX_VALUE);
			values.add(criteria.getFirstResult());
		}// else DO NOT APPEND LIMIT CLAUSE

		query.setSql(sql.toString());
		query.setValues(values);
		return query;
	}

	@Override
	public Query buildUpdatorQuery(Updator<?> updator)
	{
		Query query = new Query();
		List<Object> values = new Vector<Object>();// 参数列表
		query.setValues(values);
		StringBuffer sql = new StringBuffer();// SQL

		sql.append(UPDATE)//
				.append(updator.getTableName())//
				.append(SET);

		StringBuffer a = new StringBuffer();
		int i = 0;
		for (String c : updator.getChangeMap().keySet())
		{
			a.append(c).append(" = ? ");
			query.getValues().add(updator.getChangeMap().get(c));
			if (i < updator.getChangeMap().keySet().size() - 1)
			{
				a.append(",");
			}
			i++;
		}

		sql.append(a);
		if (Validator.notEmpty(updator.getRestrictions()))
		{
			sql.append(WHERE);
			boolean firstFlg = true;
			for (Restriction restriction : updator.getRestrictions())
			{
				if (firstFlg)
				{
					sql.append(restriction.getSql());
					firstFlg = false;
				} else
				{
					sql.append(AND).append(restriction.getSql());
				}
				query.getValues().addAll(Arrays.asList(restriction.getValues()));
			}
		}
		query.setSql(sql.toString());
		return query;
	}

	// FIXME
	private String getColumnDDL(TypeTable typeTable, ColumnMeta columnMeta)
	{
		StringBuffer sb = new StringBuffer();

		String x = typeTable.getSQLType(columnMeta.getFieldType(), columnMeta.getLength());

		if (columnMeta.getLength() > 0)
		{
			x += "(" + columnMeta.getLength() + ")";
		}
		sb.append(columnMeta.getColumnName()).append("   ").append(x);
		if (!columnMeta.isNullable() || columnMeta.isId())
		{
			sb.append(NOT_NULL);
		}
		if (columnMeta.isId())
		{
			sb.append(AUTO_INCREMENT);
		}
		// id bigint not null auto_increment,
		return sb.toString();
	}

	@Override
	public String buildTableDDL(TypeTable typeTable, TableMeta<?> tableMeta)
	{
		StringBuffer ddl = new StringBuffer();
		ddl.append("CREATE TABLE ").append(tableMeta.getTableName()).append("\n(\n");
		for (ColumnMeta columnMeta : tableMeta.getColumnMetas())
		{
			ddl.append("	" + getColumnDDL(typeTable, columnMeta) + ",\n");
		}
		if (tableMeta.getIdColumnMeta() != null)
		{
			ddl.append(" 	PRIMARY KEY (" + tableMeta.getIdColumnMeta().getColumnName() + ")");
		}
		ddl.append("\n)");

		return ddl.toString();
	}
}
