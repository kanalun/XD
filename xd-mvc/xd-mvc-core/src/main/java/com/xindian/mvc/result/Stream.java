package com.xindian.mvc.result;

import java.io.InputStream;

/**
 * 
 * @author Elva
 * @date 2011-1-19
 * @version 1.0
 */
public final class Stream extends AbstractResult<Stream> implements Resultable
{
	private InputStream inputStream;

	public Stream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	/**
	 * 强制下载,该方法等价于:this.setContentType("application/x-download").setFileName(
	 * fileName)(事实上,就是这么做的)
	 * 
	 * @param fileName
	 * @return
	 */
	public Stream forceToDownload(String fileName)
	{
		setContentType("application/x-download")//
				.setFileName(fileName);//
		return this;
	}

	/**
	 * 设置下载文件名,
	 * 
	 * @param fileName
	 * @return
	 */
	public Stream setFileName(String fileName)
	{
		if (fileName != null)
		{
			addHeader("Content-Disposition", "attachment;filename=" + fileName);
		} else
		{
			removeFileName();
		}
		// ActionContext.getContext().getResponse().addHeader("Content-Disposition",
		// "attachment;filename=" + fileName);
		return this;
	}

	public Stream removeFileName()
	{
		removeHeader("Content-Disposition");
		// ActionContext.getContext().getResponse().addHeader("Content-Disposition",
		// "attachment;filename=" + fileName);
		return this;
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return StreamResultHandler.class;
	}

	public InputStream getStream()
	{
		return inputStream;
	}

}
