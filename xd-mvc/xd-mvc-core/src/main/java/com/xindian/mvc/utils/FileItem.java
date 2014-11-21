package com.xindian.mvc.utils;

import java.io.File;

/**
 * 文件上传代理
 * 
 * @author Elva
 * 
 */
public class FileItem
{
	private File file;// 上传之后的文件,一般存在设定的目录中

	private String contentType;

	private String fileName;// 远程文件名,如果不存在为null

	private String originalFileName;

	private String filesystemName;

	/**
	 * 上传之后的文件,一般存在设定的临时文件夹中
	 * 
	 * @return
	 */
	public File getFile()
	{
		return file;
	}

	/**
	 * 
	 * @param file
	 */
	public void setFile(File file)
	{
		this.file = file;
	}

	/**
	 * 上传文件的contentType
	 * 
	 * @return
	 */
	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	/**
	 * 上传文件的文件名,如果不存在返回null
	 * 
	 * @return
	 */
	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * 
	 * @return
	 */
	public String getOriginalFileName()
	{
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName)
	{
		this.originalFileName = originalFileName;
	}

	/**
	 * 
	 * @return
	 */
	public String getFilesystemName()
	{
		return filesystemName;
	}

	public void setFilesystemName(String filesystemName)
	{
		this.filesystemName = filesystemName;
	}

}
