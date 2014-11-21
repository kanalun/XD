package com.xindian.deploy.resclear;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.tomcat.util.res.StringManager;

/**
 * clear
 * 
 * @author QCC
 */
public class AppleReferencesThreadsClear implements ResourceClear
{
	protected static final StringManager sm = StringManager.getManager(org.apache.catalina.core.Constants.Package);

	private ClassLoader classLoader;

	public AppleReferencesThreadsClear(ClassLoader classLoader)
	{
		this.classLoader = classLoader;
	}

	/*
	 * Get the set of current threads as an array.
	 */
	private Thread[] getThreads()
	{
		// Get the current thread group
		ThreadGroup tg = Thread.currentThread().getThreadGroup();
		// Find the root thread group
		while (tg.getParent() != null)
		{
			tg = tg.getParent();
		}

		int threadCountGuess = tg.activeCount() + 50;
		Thread[] threads = new Thread[threadCountGuess];
		int threadCountActual = tg.enumerate(threads);
		// Make sure we don't miss any threads
		while (threadCountActual == threadCountGuess)
		{
			threadCountGuess *= 2;
			threads = new Thread[threadCountGuess];
			// Note tg.enumerate(Thread[]) silently ignores any threads that
			// can't fit into the array
			threadCountActual = tg.enumerate(threads);
		}
		return threads;
	}

	@Override
	public void clear()
	{
		Thread[] threads = getThreads();
		// Iterate over the set of threads
		for (Thread thread : threads)
		{
			if (thread != null)
			{
				ClassLoader ccl = thread.getContextClassLoader();
				if (ccl == classLoader)
				{
					// Don't warn about this thread
					if (thread == Thread.currentThread())
					{
						continue;
					}
					// JVM controlled threads
					ThreadGroup tg = thread.getThreadGroup();
					if (tg != null && JVM_THREAD_GROUP_NAMES.contains(tg.getName()))
					{
						// HttpClient keep-alive threads
						if (clearReferencesHttpClientKeepAliveThread && thread.getName().equals("Keep-Alive-Timer"))
						{
							thread.setContextClassLoader(parent);
							log.debug(sm.getString("webappClassLoader.checkThreadsHttpClient"));
						}

						// Don't warn about remaining JVM controlled threads
						continue;
					}

					// Skip threads that have already died
					if (!thread.isAlive())
					{
						continue;
					}

					// TimerThread can be stopped safely so treat separately
					// "java.util.TimerThread" in Sun/Oracle JDK
					// "java.util.Timer$TimerImpl" in Apache Harmony and in IBM
					// JDK
					if (thread.getClass().getName().startsWith("java.util.Timer") && clearReferencesStopTimerThreads)
					{
						clearReferencesStopTimerThread(thread);
						continue;
					}

					if (isRequestThread(thread))
					{
						log.error(sm.getString("webappClassLoader.warnRequestThread", contextName, thread.getName()));
					} else
					{
						log.error(sm.getString("webappClassLoader.warnThread", contextName, thread.getName()));
					}

					// Don't try an stop the threads unless explicitly
					// configured to do so
					if (!clearReferencesStopThreads)
					{
						continue;
					}

					// If the thread has been started via an executor, try
					// shutting down the executor
					try
					{

						// Runnable wrapped by Thread
						// "target" in Sun/Oracle JDK
						// "runnable" in IBM JDK
						// "action" in Apache Harmony
						Object target = null;
						for (String fieldName : new String[] { "target", "runnable", "action" })
						{
							try
							{
								Field targetField = thread.getClass().getDeclaredField(fieldName);
								targetField.setAccessible(true);
								target = targetField.get(thread);
								break;
							} catch (NoSuchFieldException nfe)
							{
								continue;
							}
						}

						// "java.util.concurrent" code is in public domain,
						// so all implementations are similar
						if (target != null && target.getClass().getCanonicalName() != null
								&& target.getClass().getCanonicalName().equals("java.util.concurrent.ThreadPoolExecutor.Worker"))
						{
							Field executorField = target.getClass().getDeclaredField("this$0");
							executorField.setAccessible(true);
							Object executor = executorField.get(target);
							if (executor instanceof ThreadPoolExecutor)
							{
								((ThreadPoolExecutor) executor).shutdownNow();
							}
						}
					} catch (SecurityException e)
					{
						log.warn(sm.getString("webappClassLoader.stopThreadFail", thread.getName(), contextName), e);
					} catch (NoSuchFieldException e)
					{
						log.warn(sm.getString("webappClassLoader.stopThreadFail", thread.getName(), contextName), e);
					} catch (IllegalArgumentException e)
					{
						log.warn(sm.getString("webappClassLoader.stopThreadFail", thread.getName(), contextName), e);
					} catch (IllegalAccessException e)
					{
						log.warn(sm.getString("webappClassLoader.stopThreadFail", thread.getName(), contextName), e);
					}

					// This method is deprecated and for good reason. This is
					// very risky code but is the only option at this point.
					// A *very* good reason for apps to do this clean-up
					// themselves.
					thread.stop();
				}
			}
		}
	}

}
