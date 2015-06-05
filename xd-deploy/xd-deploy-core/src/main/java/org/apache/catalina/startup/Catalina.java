/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.startup;

/**
 * Startup/Shutdown shell program for Catalina. The following command line
 * options are recognized:
 * <ul>
 * <li><b>-config {pathname}</b> - Set the pathname of the configuration file to
 * be processed. If a relative path is specified, it will be interpreted as
 * relative to the directory pathname specified by the "catalina.base" system
 * property. [conf/server.xml]
 * <li><b>-help</b> - Display usage information.
 * <li><b>-nonaming</b> - Disable naming support.
 * <li><b>configtest</b> - Try to test the config
 * <li><b>start</b> - Start an instance of Catalina.
 * <li><b>stop</b> - Stop the currently running instance of Catalina. </u>
 * 
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
public class Catalina
{
	protected ClassLoader parentClassLoader = Catalina.class.getClassLoader();
	/**
	 * Use await.
	 */
	protected boolean await = false;

	/**
	 * Set the shared extensions class loader.
	 * 
	 * @param parentClassLoader
	 *            The shared extensions class loader.
	 */
	public void setParentClassLoader(ClassLoader parentClassLoader)
	{
		this.parentClassLoader = parentClassLoader;
	}

	public ClassLoader getParentClassLoader()
	{
		if (parentClassLoader != null)
		{
			return (parentClassLoader);
		}
		return ClassLoader.getSystemClassLoader();
	}

	public void setAwait(boolean b)
	{
		await = b;
	}

	public boolean isAwait()
	{
		return await;
	}

	/**
	 * Use shutdown hook flag.
	 */
	protected boolean useShutdownHook = true;

	/**
	 * Shutdown hook.
	 */
	protected Thread shutdownHook = null;

	/**
	 * Start a new server instance.
	 */
	public void start()
	{
		long t1 = System.nanoTime();

		long t2 = System.nanoTime();
		if (await)
		{

		}
		System.out.print("START");
	}

	/**
	 * Start a new server instance.
	 */
	public void load()
	{
		long t1 = System.nanoTime();
		long t2 = System.nanoTime();
		System.out.print("LOAD");
	}
}
