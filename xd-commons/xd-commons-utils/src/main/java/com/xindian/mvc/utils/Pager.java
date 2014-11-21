package com.xindian.mvc.utils;

import java.util.List;

/**
 * 分页
 * 
 * @author Elva
 * @date 2011-3-22
 * @version 1.0
 * @param <T>
 */
public class Pager<T>
{
	private static final long serialVersionUID = 1L;

	/** 当前页面INDEX从0开始 */
	protected int startIndex;

	private List<T> data;

	private int totalSize;// 总的记录数

	/** 页面包含的记录数 */
	protected int pageSize = 0;

	// 实际页面包含的记录数量
	// protected int pageSize;

	public int getTotalSize()
	{
		return totalSize;
	}

	/**
	 * 返回分页的起始index,从0开始
	 * 
	 * @return
	 */
	public int getStartIndex()
	{
		return startIndex;
	}

	/**
	 * 设置分页的起始Index
	 * 
	 * @param startIndex
	 */
	public void setStartIndex(int startIndex)
	{
		this.startIndex = startIndex;
	}

	public void setTotalSize(int totalSize)
	{
		this.totalSize = totalSize;
	}

	public int getDefaultPageSize()
	{
		return pageSize;
	}

	public void setDefaultPageSize(int defaultPageSize)
	{
		this.pageSize = defaultPageSize;
	}

	//
	public boolean hasNext()
	{
		return true;
	}

	public boolean hasIndexOf(int pageIndex)
	{
		return true;
	}

	// 下一页
	public int nextPageNumber()
	{
		return -1;
	}

	/**
	 * 一共有多少页
	 * 
	 * @return
	 */
	public int howManyPages()
	{
		// this.rowCount % pageSize == 0 ? this.rowCount / pageSize :
		// this.rowCount / pageSize + 1;
		return totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
	}

	/**
	 * 返回该页的数据
	 * 
	 * @return
	 */
	public List<T> getData()
	{
		return data;
	}

	public void setData(List<T> data)
	{
		this.data = data;
	}

}
