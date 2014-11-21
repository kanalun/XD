package com.xindian.awaits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.xindian.awaits.sql.SQLUtils;

public class Restrictions
{
	public static Restriction eq(String columnName, Object value)
	{
		return new SimpleRestriction(columnName, value, "=");
	}

	public static Restriction ne(String columnName, Object value)
	{
		return new SimpleRestriction(columnName, value, "<>");
	}

	public static Restriction gt(String columnName, Object value)
	{
		return new SimpleRestriction(columnName, value, ">");
	}

	public static Restriction ge(String columnName, Object value)
	{
		return new SimpleRestriction(columnName, value, ">=");
	}

	public static Restriction lt(String columnName, Object value)
	{
		return new SimpleRestriction(columnName, value, "<");
	}

	public static Restriction le(String columnName, Object value)
	{
		return new SimpleRestriction(columnName, value, "<=");
	}

	public static Restriction like(String columnName, Object value)
	{
		return new SimpleRestriction(columnName, value, "LIKE");
	}

	public static Restriction sql(String sql, Object... values)
	{
		return new SqlRestriction(sql, values);
	}

	public static Restriction isNull(String columnName)
	{
		return new IsNullRestriction(columnName);
	}

	public static Restriction isNotNull(String columnName)
	{
		return new IsNotNullRestriction(columnName);
	}

	public static Restriction in(String columnName, Object[] values)
	{
		return new InRestriction(columnName, values);
	}

	public static Restriction in(String columnName, Collection<?> values)
	{
		return new InRestriction(columnName, values.toArray());
	}

	public static Restriction and(Restriction r1, Restriction r2)
	{
		return new AndRestriction(r1, r2);
	}

	public static Restriction or(Restriction r1, Restriction r2)
	{
		return new OrRestriction(r1, r2);
	}

	public static Restriction between(String columnName, Object r1, Object r2)
	{
		return new BetweenRestriction(columnName, r1, r2);
	}

	/**
	 * Apply an "equal" constraint to two properties
	 */
	public static PropertyExpression eqProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, "=");
	}

	/**
	 * Apply a "not equal" constraint to two properties
	 */
	public static PropertyExpression neProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, "<>");
	}

	/**
	 * Apply a "less than" constraint to two properties
	 */
	public static PropertyExpression ltProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, "<");
	}

	/**
	 * Apply a "less than or equal" constraint to two properties
	 */
	public static PropertyExpression leProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, "<=");
	}

	/**
	 * Apply a "greater than" constraint to two properties
	 */
	public static PropertyExpression gtProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, ">");
	}

	/**
	 * Apply a "greater than or equal" constraint to two properties
	 */
	public static PropertyExpression geProperty(String propertyName, String otherPropertyName)
	{
		return new PropertyExpression(propertyName, otherPropertyName, ">=");
	}

	private static class BetweenRestriction implements Restriction
	{
		private String columnName;
		private Object lo;
		private Object hi;

		public BetweenRestriction(String columnName, Object lo, Object hi)
		{
			this.columnName = columnName;
			this.lo = lo;
			this.hi = hi;
		}

		@Override
		public Object[] getValues()
		{
			return new Object[] { lo, hi };
		}

		@Override
		public String getSql()
		{
			return columnName + " BETWEEN " + "?" + " AND " + "?";
		}
	}

	private static class OrRestriction implements Restriction
	{
		Restriction r1;
		Restriction r2;

		public OrRestriction(Restriction r1, Restriction r2)
		{
			this.r1 = r1;
			this.r2 = r2;
		}

		public String getSql()
		{
			return "( " + r1.getSql() + " OR " + r2.getSql() + " )";
		}

		@Override
		public Object[] getValues()
		{
			List<Object> list = new ArrayList<Object>(Arrays.asList(r1.getValues()));

			list.addAll(Arrays.asList(r2.getValues()));

			Object[] c = list.toArray();

			return c;
		}
	}

	private static class AndRestriction implements Restriction
	{
		Restriction r1;
		Restriction r2;

		public AndRestriction(Restriction r1, Restriction r2)
		{
			this.r1 = r1;
			this.r2 = r2;
		}

		public String getSql()
		{
			return "( " + r1.getSql() + " AND " + r2.getSql() + " )";
		}

		@Override
		public Object[] getValues()
		{
			List<Object> list = new ArrayList<Object>(Arrays.asList(r1.getValues()));

			list.addAll(Arrays.asList(r2.getValues()));

			Object[] c = list.toArray();

			return c;
		}
	}

	private static class SimpleRestriction implements Restriction
	{
		private String operator;

		private String columnName;

		private Object value;

		public SimpleRestriction(String columnName, Object value, String operator)
		{
			this.operator = " " + operator + " ";
			this.columnName = columnName;
			this.value = value;
		}

		public String getSql()
		{
			return columnName + operator + "?";
		}

		@Override
		public Object[] getValues()
		{
			return new Object[] { value };
		}
	}

	private static class InRestriction implements Restriction
	{
		static final String operator = " IN ";

		private String columnName;

		private Object[] values;

		public InRestriction(String columnName, Object[] values)
		{
			this.columnName = columnName;
			this.values = values;
		}

		public String getSql()
		{
			return columnName + operator + "( " + SQLUtils.questionMarks(values.length) + " )";
		}

		@Override
		public Object[] getValues()
		{
			return values;
		}
	}

	private static class IsNullRestriction implements Restriction
	{
		static final String operator = " IS NULL ";

		private String columnName;

		public IsNullRestriction(String columnName)
		{
			this.columnName = columnName;
		}

		public String getSql()
		{
			return columnName + operator;
		}

		@Override
		public Object[] getValues()
		{
			return new Object[0];
		}

	}

	private static class IsNotNullRestriction implements Restriction
	{
		static final String operator = " IS NOT NULL ";

		private String columnName;

		public IsNotNullRestriction(String columnName)
		{
			this.columnName = columnName;
		}

		public String getSql()
		{
			return columnName + operator;
		}

		@Override
		public Object[] getValues()
		{
			return new Object[0];
		}
	}

	private static class SqlRestriction implements Restriction
	{
		private String sql;

		private Object[] p;

		public SqlRestriction(String sql, Object... parm)
		{
			this.sql = sql;
			this.p = parm;
		}

		public String getSql()
		{
			return "( " + sql + " )";
		}

		@Override
		public Object[] getValues()
		{
			return p;
		}
	}

	public static class PropertyExpression implements Restriction
	{
		private final String propertyName;
		private final String otherPropertyName;
		private final String op;

		protected PropertyExpression(String propertyName, String otherPropertyName, String op)
		{
			this.propertyName = propertyName;
			this.otherPropertyName = otherPropertyName;
			this.op = op;
		}

		@Override
		public String getSql()
		{
			return propertyName + " " + getOp() + " " + otherPropertyName;
		}

		@Override
		public Object[] getValues()
		{
			return new Object[0];
		}

		public String toString()
		{
			return propertyName + " " + getOp() + " " + otherPropertyName;
		}

		public String getOp()
		{
			return op;
		}

	}

}
