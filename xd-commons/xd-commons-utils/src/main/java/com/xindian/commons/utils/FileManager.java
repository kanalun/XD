package com.xindian.commons.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileManager
{
	private static Map files = Collections.synchronizedMap(new HashMap());

	protected static boolean reloadingConfigs = true;

	private FileManager()
	{
	}

	public static void setReloadingConfigs(boolean reloadingConfigs)
	{
		FileManager.reloadingConfigs = reloadingConfigs;
	}

	public static boolean isReloadingConfigs()
	{
		return reloadingConfigs;
	}

	public static boolean fileNeedsReloading(String fileName)
	{
		FileRevision revision = (FileRevision) files.get(fileName);

		if ((revision == null) && reloadingConfigs)
		{
			// no revision yet and we keep the revision history, so
			// the file needs to be loaded for the first time
			return true;
		} else if (revision == null)
		{
			return false;
		}

		if (revision.getFile() != null)
		{
			return revision.getLastModified() < revision.getFile().lastModified();
		} else
		{
			return false;
		}
	}

	/**
	 * Loads opens the named file and returns the InputStream
	 * 
	 * @param fileName
	 *            - the name of the file to open
	 * @return an InputStream of the file contents or null
	 * @throws IllegalArgumentException
	 *             if there is no file with the given file name
	 */
	public static InputStream loadFile(String fileName, Class clazz)
	{
		URL fileUrl = ClassLoaderUtils.getResource(fileName, clazz);
		return loadFile(fileUrl);
	}

	/**
	 * Loads opens the named file and returns the InputStream
	 * 
	 * @param fileUrl
	 *            - the URL of the file to open
	 * @return an InputStream of the file contents or null
	 * @throws IllegalArgumentException
	 *             if there is no file with the given file name
	 */
	public static InputStream loadFile(URL fileUrl)
	{
		if (fileUrl == null)
		{
			return null;
		}

		String fileName = fileUrl.toString();
		InputStream is;

		try
		{
			is = fileUrl.openStream();

			if (is == null)
			{
				throw new IllegalArgumentException("No file '" + fileName + "' found as a resource");
			}
		} catch (IOException e)
		{
			throw new IllegalArgumentException("No file '" + fileName + "' found as a resource");
		}

		if (isReloadingConfigs())
		{
			File file = new File(fileUrl.getFile());
			long lastModified;

			if (!file.exists() || !file.canRead())
			{
				file = null;
			}

			if (file != null)
			{
				lastModified = file.lastModified();
				files.put(fileName, new FileRevision(file, lastModified));
			} else
			{
				// Never expire a non-file resource
				files.put(fileName, new FileRevision());
			}
		}

		return is;
	}

	private static class FileRevision
	{
		private File file;
		private long lastModified;

		public FileRevision()
		{
		}

		public FileRevision(File file, long lastUpdated)
		{
			this.file = file;
			this.lastModified = lastUpdated;
		}

		public File getFile()
		{
			return file;
		}

		public void setLastModified(long lastModified)
		{
			this.lastModified = lastModified;
		}

		public long getLastModified()
		{
			return lastModified;
		}
	}
}
