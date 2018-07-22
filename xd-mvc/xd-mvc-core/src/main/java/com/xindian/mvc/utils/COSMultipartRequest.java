package com.xindian.mvc.utils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.oreilly.servlet.MultipartRequest;

/**
 * 使用COS包装HTTPRequest,以支持文件上传
 */
public class COSMultipartRequest extends HttpServletRequestWrapper
{
	private MultipartRequest multipartRequest;

	public COSMultipartRequest(HttpServletRequest request, String tempPath, int maxFileSize,
			String encoding) throws IOException
	{
		super(request);
		this.multipartRequest = new MultipartRequest(request, tempPath, maxFileSize, encoding);
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public String getContentType(String fileName)
	{
		return multipartRequest.getContentType(fileName);
	}

	/**
	 * 得到文件名为fileName的文件
	 * 
	 * @param fileName
	 * @return
	 */
	public File getFile(String fileName)
	{
		return multipartRequest.getFile(fileName);
	}

	/**
	 * 
	 * @return
	 */
	public Enumeration<String> getFileNames()
	{
		return multipartRequest.getFileNames();
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getFilesystemName(String name)
	{
		return multipartRequest.getFilesystemName(name);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public String getOriginalFileName(String name)
	{
		return multipartRequest.getOriginalFileName(name);
	}

	@Override
	public String getParameter(String name)
	{
		String value = super.getParameter(name);
		if (value == null)
		{
			value = multipartRequest.getParameter(name);
		}
		return value;
	}

	@Override
	public Enumeration<String> getParameterNames()
	{
		return multipartRequest.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name)
	{
		String[] values = super.getParameterValues(name);
		if (values == null)
		{
			values = multipartRequest.getParameterValues(name);
		}
		return values;
	}

	@Override
	public Map<String, Object> getParameterMap()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> names = getParameterNames();
		while (names.hasMoreElements())
		{
			String name = names.nextElement();
			String[] values = getParameterValues(name);
			map.put(name, (values != null && values.length == 1) ? values[0] : values);
		}
		Enumeration<String> fileNames = multipartRequest.getFileNames();
		while (fileNames.hasMoreElements())
		{
			String fileName = fileNames.nextElement();
			if (fileName != null && !"".equals(fileName))
			{
				FileItem fileItem = new FileItem();
				fileItem.setFileName(fileName);
				fileItem.setFile(multipartRequest.getFile(fileName));
				fileItem.setContentType(multipartRequest.getContentType(fileName));
				fileItem.setFilesystemName(multipartRequest.getFilesystemName(fileName));
				fileItem.setOriginalFileName(multipartRequest.getOriginalFileName((fileName)));
				map.put(fileName, fileItem);
				// System.out.println("	fileName = " + fileName);
				// System.out.println("	OriginalFileName = " +
				// multipartRequest.getOriginalFileName(fileName));
				// System.out.println("	ContentType = " +
				// multipartRequest.getContentType(fileName));
				// System.out.println("	FilesystemName = " +
				// multipartRequest.getFilesystemName(fileName));
				// System.out.println("	NOWIN = " +
				// multipartRequest.getFile(fileName).getAbsolutePath());
				// System.out.println();
			}
		}
		return map;
	}
}
