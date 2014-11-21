package com.xindian.mvc.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自动缓存数据重加载
 * 
 * @author Winter Lau
 */
public class ICacheHelper
{
	private static Logger log = LoggerFactory.getLogger(ICacheHelper.class);

	final static CacheUpdater threadPool = new CacheUpdater();

	static class CacheUpdater extends ThreadPoolExecutor
	{
		private List<String> runningThreadNames = new Vector<String>();

		public CacheUpdater()
		{
			super(0, 20, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), new ThreadPoolExecutor.DiscardOldestPolicy());
		}

		public void execute(Thread command)
		{
			if (runningThreadNames.contains(command.getName()))
			{
				log.warn(command.getName() + " ===================> Running.");
				return;
			}
			log.info(command.getName() + " ===================> Started.");
			super.execute(command);
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t)
		{
			super.afterExecute(r, t);
			Thread thread = (Thread) r;
			runningThreadNames.remove(thread.getName());
			log.info(thread.getName() + " ===================> Finished.");
		}

	}

	private final static String CACHE_GLOBAL = "icache-global";

	/**
	 * 获取缓存数据
	 * 
	 * @param region
	 * @param key
	 * @param invoker
	 * @param args
	 * @return
	 */
	public static Object get(final String region, final Serializable key, final ICacheInvoker invoker, final Object... args)
	{
		// 1. 从正常缓存中获取数据
		Object data = CacheManager.get(region, key);
		if (data == null)
		{
			final String globalKey = key + "@" + region;
			// 2. 从全局二级缓存中获取数据
			data = CacheManager.get(CACHE_GLOBAL, globalKey);
			// 2.1 取不到为第一次运行
			if (data == null)
			{
				if (invoker != null)
				{
					data = invoker.callback(args);
					if (data != null)
					{
						CacheManager.set(region, key, (Serializable) data);
						CacheManager.set(CACHE_GLOBAL, globalKey, (Serializable) data);
					}
				}
			}
			// 2.2 如果取到了则执行自动更新数据策略
			else if (invoker != null)
			{
				String threadName = "CacheUpdater-" + region + "-" + key;
				// g_ThreadPool.execute(
				new Thread(threadName)
				{
					public void run()
					{
						Object result = invoker.callback(args);
						if (result != null)
						{
							CacheManager.set(region, key, (Serializable) result);
							CacheManager.set(CACHE_GLOBAL, globalKey, (Serializable) result);
						}
					}
				}.start();
				// );
			}
		}
		return data;
	}

}
