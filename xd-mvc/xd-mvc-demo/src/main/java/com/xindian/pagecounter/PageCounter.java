package com.xindian.pagecounter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1,保证只能有一个线程对数据进行刷入操作,
 * 
 * 2,在刷入的同时,其他线程还能进行计数
 * 
 * @author Elva
 * @date 2011-1-23
 * @version 1.0
 */
public class PageCounter
{
	private final static Map<String, Page> queues0 = new ConcurrentHashMap<String, Page>();

	private final static Map<String, Page> queues1 = new ConcurrentHashMap<String, Page>();

	private final ReentrantLock queues0Lock = new ReentrantLock();

	private final Condition canCount = queues0Lock.newCondition();

	private final ReentrantLock queues1Lock = new ReentrantLock();

	private final Condition notFull = queues1Lock.newCondition();

	public void recode(String url)
	{

	}

	public synchronized void flush()
	{
		queues0Lock.lock();
	}

	static class Page
	{
		private String url;

		private volatile int count;

		@Override
		public int hashCode()
		{
			return super.hashCode();
		}
	}
}
