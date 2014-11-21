package com.xindian.awaits;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.exception.DBAccessException;
import com.xindian.awaits.sql.SQLBuilder;

public class Updator<T>
{
	private Connection connection;

	private static Logger logger = LoggerFactory.getLogger(Updator.class);

	private HashMap<Class<?>, TableMeta<?>> tableMetas;

	private Map<String, Object> changeMap = new HashMap<String, Object>();

	public Map<String, Object> getChangeMap()
	{
		return changeMap;
	}

	public List<Restriction> getRestrictions()
	{
		return restrictions;
	}

	public Class<T> getClazz()
	{
		return clazz;
	}

	private List<Restriction> restrictions = new Vector<Restriction>();

	private Class<T> clazz;

	public Updator<T> set(String cname, Object value)
	{
		changeMap.put(cname, value);
		return this;
	}

	public static <T> Updator<T> get(Class<T> type, Connection connection, SQLBuilder sqlBuilder,
			HashMap<Class<?>, TableMeta<?>> tableMetas)
	{
		Updator<T> updator = new Updator<T>();
		updator.clazz = type;
		updator.connection = connection;
		updator.tableMetas = tableMetas;
		updator.sqlBuilder = sqlBuilder;
		return updator;
	}

	private SQLBuilder sqlBuilder;

	public int update() throws DBAccessException
	{
		Query query = sqlBuilder.buildUpdatorQuery(this);
		return DBHelper.update(connection, query.getSql(), query.getValues().toArray(new Object[0]));
	}

	public Updator<T> add(Restriction restriction)
	{
		restrictions.add(restriction);
		return this;
	}

	public String getTableName()
	{
		return tableMetas.get(clazz).getTableName();
	}
	/*
	 * protected String buildSql() { StringBuffer sql = new StringBuffer();
	 * sql.append(UPDATE)// .append(tableMetas.get(clazz).getTableName())//
	 * .append(SET);
	 * 
	 * StringBuffer a = new StringBuffer(); int i = 0; for (String c :
	 * changeMap.keySet()) { a.append(c).append(" = ? ");
	 * values.add(changeMap.get(c)); if (i < changeMap.keySet().size() - 1) {
	 * a.append(","); } i++; }
	 * 
	 * sql.append(a); if (Validator.notEmpty(restrictions)) { sql.append(WHERE);
	 * boolean firstFlg = true; for (Restriction restriction : restrictions) {
	 * if (firstFlg) { sql.append(restriction.getSql()); firstFlg = false; }
	 * else { sql.append(" AND ").append(restriction.getSql()); }
	 * values.addAll(Arrays.asList(restriction.getValues())); } } return
	 * sql.toString(); }
	 */

}
