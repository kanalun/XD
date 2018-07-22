package com.xd.tools;

/**
 * 数据库命名策略
 * 
 * @author qiucongcong
 * 
 *         2014-4-10
 */
public class AllscoreNamingStrategy implements NamingStrategy
{
	// 前缀后缀 暂时没用
	private String tablePrefix = "";

	public String propertyToColumnName(String propertyName)
	{
		StringBuffer after = new StringBuffer();
		for (int i = 0; i < propertyName.length(); i++)
		{
			char ch = propertyName.charAt(i);
			if (Character.isUpperCase(ch) && i != 0 && i < (propertyName.length() - 1))
			{
				after.append("_");
			}
			after.append(Character.toUpperCase(ch));
		}
		return after.toString();
	}

	public String columnNameToProperty(String columnName)
	{
		StringBuffer after = new StringBuffer();
		boolean nextUpcase = false;
		for (int i = 0; i < columnName.length(); i++)
		{
			char ch = columnName.charAt(i);
			if (ch == '_')
			{
				nextUpcase = true;
			} else
			{
				if (nextUpcase)
				{
					after.append(Character.toUpperCase(ch));
					nextUpcase = false;
				} else
				{
					after.append(Character.toLowerCase(ch));
				}
			}
		}
		return after.toString();
	}

	public String classToTableName(String className)
	{
		StringBuffer after = new StringBuffer();
		for (int i = 0; i < className.length(); i++)
		{
			char ch = className.charAt(i);
			if (Character.isUpperCase(ch) && i != 0 && i < (className.length() - 1))
			{
				after.append("_");
			}
			after.append(Character.toUpperCase(ch));
		}
		return after.toString();
	}

	public String tableNameToClass(String tableName)
	{
		StringBuffer after = new StringBuffer();
		boolean nextUpcase = true;
		for (int i = 0; i < tableName.length(); i++)
		{
			char ch = tableName.charAt(i);
			if (ch == '_')
			{
				nextUpcase = true;
			} else
			{
				if (nextUpcase)
				{
					after.append(Character.toUpperCase(ch));
					nextUpcase = false;
				} else
				{
					after.append(Character.toLowerCase(ch));
				}
			}
		}
		return after.toString();
	}

	// ---for test
	public static void main2(String[] args)
	{
		AllscoreNamingStrategy namingStrategy = new AllscoreNamingStrategy();
		String tableName = namingStrategy.classToTableName("PosOrderInfoPay");
		String className = namingStrategy.tableNameToClass(tableName);
		String propertyName = namingStrategy.columnNameToProperty("CARD_NO");
		String columnName = namingStrategy.propertyToColumnName("propertyName");
		System.out.println(tableName);
		System.out.println(className);
		System.out.println(propertyName);
		System.out.println(columnName);

		for (String col : testColNames)
		{
			String ppName = namingStrategy.columnNameToProperty(col);
			System.out.println(ppName);
		}
	}

	public static void main(String[] args)
	{
		String a = "2013-05-05 10:20:60";
		String b = "2013-05-05";
		System.out.println("" + a.compareTo(b));
	}

	public static String[] testColNames = { "INNER_ORDERID", "ORDER_INFO_ID", "MERCHANTID",
			"TERMINALID", "TRANSACTION_NO", "TRANSACTION_DATE", "PAY_AMOUNT", "CURRENCY",
			"CARD_NO", "CARD_TYPE", "BANK", "BANK_NO", "SETTLEMENT_DATE", "BATCH_NO",
			"DESCRIPTION", "CREATE_DATE", "STATUS", "TRANSACTION_TYPE", "CONTACT_NAME", "PHONE",
			"REFUND_DATE", "RETURNABLE_AMT", "ORI_PAY_AMOUNT", "NOTICE_STATUS", "NEXT_NOTICE_TIME",
			"NOTICE_TIMES" };
}
