package com.xindian.coal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Elva
 * @date 2014-5-23
 * @version 1.0
 */
public class TestReadResource
{
	public static void main(String args[]) throws IOException, URISyntaxException
	{
		String jarFilePath = "D:/APP/lib/aether-impl-1.11.jar";
		readProperties(jarFilePath);
	}

	/**
	 * 读取Jar中的配置信息,包含注释
	 * 
	 * TODO 需要严格规范属性文件的格式
	 * 
	 * @param jarFilePath
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void readProperties(String jarFilePath) throws IOException, URISyntaxException
	{
		JarFile jfile = new JarFile(jarFilePath);
		Enumeration<JarEntry> files = jfile.entries();
		while (files.hasMoreElements())
		{
			JarEntry entry = files.nextElement();
			if (entry.getName().endsWith(".properties"))
			{
				InputStream inputStream = jfile.getInputStream(entry);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				String line = bufferedReader.readLine();
				String comment = "";
				while (line != null)
				{
					if (line.startsWith("#"))
					{
						comment = comment + "\n" + line;
					} else if (line.indexOf('=') > 0)
					{
						PropertieMeta propertieMeta = new PropertieMeta();
						propertieMeta.comment = comment;
						propertieMeta.key = line.substring(0, line.lastIndexOf('='));
						propertieMeta.value = line.substring(line.lastIndexOf('=') + 1, line.length());
						System.out.println("PropertieMeta:" + propertieMeta);
						// System.out.println("CONFIG:" + line);
						comment = "";
					}
					line = bufferedReader.readLine();
				}
			}
			System.out.println(entry.getName());
		}
	}

	private static void testtt(String jarFilePath) throws IOException
	{
		JarFile jfile = new JarFile(jarFilePath);
		Enumeration<JarEntry> files = jfile.entries();
		while (files.hasMoreElements())
		{
			JarEntry entry = files.nextElement();
			if (entry.getName().endsWith(".properties"))
			{
				InputStream inputStream = jfile.getInputStream(entry);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				String line = bufferedReader.readLine();
				String comment = "";
				while (line != null)
				{
					if (line.startsWith("#"))
					{
						comment = comment + "\n" + line;
					} else if ("".equals(line.trim()))
					{
						System.out.println("EMPTY");
						line = bufferedReader.readLine();
						continue;
					} else if (line.indexOf('=') > 0)
					{
						System.out.println("COMMENT:" + comment);
						System.out.println("CONFIG:" + line);
						comment = "";
					}
					line = bufferedReader.readLine();
				}
			}
			System.out.println(entry.getName());
		}
	}
}
