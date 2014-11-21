package com.xindian.awaits.web;

import java.io.Serializable;
import java.util.List;

import javax.sql.DataSource;

import com.xindian.awaits.Configuration;
import com.xindian.awaits.Session;
import com.xindian.awaits.SessionFactory;
import com.xindian.awaits.exception.DBAccessException;

//ERROR
public class AwaitsDaoSupport
{
	private static Configuration configuration = new Configuration();

	private DataSource dataSource;

	public DataSource getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
		configuration.setDataSource(dataSource);
		sessionFactory = configuration.buildSessionFactory();
	}

	private SessionFactory sessionFactory;

	public Session getSession() throws DBAccessException
	{
		return sessionFactory.openSession();
	}

	public Serializable save(Object object) throws DBAccessException
	{
		Session session = getSession();
		Serializable id = session.save(object);
		session.close();
		return id;
	}

	public <T> void execute(AwaitsCallback<T> awaitsCallback) throws DBAccessException
	{
		Session session = getSession();
		awaitsCallback.doInAwaits(session);
		session.close();
	}

	public <T> T get(Serializable id, Class<T> clazz) throws DBAccessException
	{
		Session session = getSession();
		T t = session.get(id, clazz);
		session.close();
		return t;
	}

	public <T> List<T> list(Class<T> clazz) throws DBAccessException
	{
		Session session = getSession();
		List<T> ts = getSession().list(clazz);
		session.close();
		return ts;
	}

	public <T> boolean delete(T object) throws DBAccessException
	{
		return getSession().delete(object);
	}

	public <T> boolean delete(Serializable id, Class<T> clazz) throws DBAccessException
	{
		Session session = getSession();
		boolean flag = getSession().delete(id, clazz);
		session.close();
		return flag;
	}

	public <T> boolean update(T object)
	{
		Session session = getSession();
		boolean flag = session.update(object);
		session.close();
		return flag;
	}

}
