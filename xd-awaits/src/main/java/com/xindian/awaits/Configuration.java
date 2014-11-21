package com.xindian.awaits;

import java.util.HashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.awaits.exception.AwaitsException;
import com.xindian.awaits.exception.MappingException;
import com.xindian.awaits.mysql.SQLBuilderMySQLImpl;
import com.xindian.awaits.sql.SQLBuilder;

/**
 * 系统配置类
 * 
 * @author Elva
 * 
 */
public class Configuration
{
	private static Logger logger = LoggerFactory.getLogger(Configuration.class);

	private DataSource dataSource;

	/** SQL构造器 */
	private SQLBuilder sqlBuilder = new SQLBuilderMySQLImpl();

	private HashMap<Class<?>, TableMeta<?>> ts = new HashMap<Class<?>, TableMeta<?>>();

	/** SessionFactory */
	private SessionFactory sessionFactory;

	/** 类型转换工厂 */
	private ConverterFactory converterFactory;

	/** 映射元数据提供 */
	private MappingMetaProvider mappingMetaProvider;

	/** 运行时映射,如果开启这个,用户不需在程序初始化的时候addClass,又需要的时候程序会自动加载映射信息 */
	private boolean runtimeMapping = false;

	public SessionFactory buildSessionFactory() throws AwaitsException
	{
		if (dataSource == null)
		{
			throw new AwaitsException("Can not build sessonFactory, Cause:the datasource not bean set");
		}
		if (sessionFactory == null)
			sessionFactory = new SessionFactory(this, dataSource, ts);
		return sessionFactory;
	}

	/**
	 * 设置数据源
	 * 
	 * @param dataSource
	 * @return 返回self
	 */
	public Configuration setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		return this;
	}

	/**
	 * 
	 * @param persistentClass
	 * @return
	 * @throws MappingException
	 */
	public TableMeta<?> getTableMeta(Class<?> persistentClass) throws MappingException
	{
		TableMeta<?> tableMeta = ts.get(persistentClass);
		if (tableMeta == null && runtimeMapping)
		{
			this.addClass(persistentClass);
			tableMeta = ts.get(persistentClass);
		}
		return tableMeta;
	}

	public Configuration reomveClass(Class<?> persistentClass) throws MappingException
	{
		ts.remove(persistentClass);
		logger.info("Reading mappings from resource: " + persistentClass);
		return this;
	}

	public Configuration addClass(Class<?> persistentClass) throws MappingException
	{
		TableMeta<?> tableMeta = TableMeta.getTableMeta(persistentClass);

		// logger.debug("\n" + tableMeta.getDDL());
		ts.put(persistentClass, tableMeta);
		logger.info("Reading mappings from resource: " + persistentClass);
		return this;
	}

	public boolean isRuntimeMapping()
	{
		return runtimeMapping;
	}

	public void setRuntimeMapping(boolean runtimeMapping)
	{
		this.runtimeMapping = runtimeMapping;
	}

	public SQLBuilder getSqlBuilder()
	{
		return sqlBuilder;
	}

	public void setSqlBuilder(SQLBuilder sqlBuilder)
	{
		this.sqlBuilder = sqlBuilder;
	}
}
