package com.xindian.mvc.utils;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.ActionContextDestoryListener;

/**
 * 删除 ActionContext对应的临时文件
 * 
 * @author Elva
 * 
 */
public class FileItemCleanUpListener implements ActionContextDestoryListener
{
	@Override
	public void onActionContextDestoryed(ActionContext context)
	{
		// TODO 删除 ActionContext对应的文件
	}
}
