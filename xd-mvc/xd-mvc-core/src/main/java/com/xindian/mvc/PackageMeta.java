package com.xindian.mvc;

import java.util.List;

import com.xindian.mvc.aop.Handler;
import com.xindian.mvc.result.ResultHandler;

/**
 * 对包配置,是生成的信息
 * 
 * @author Elva
 * @date 2011-3-9
 * @version 1.0
 */
public class PackageMeta
{
	/**
	 * 返回包的返回结果
	 * 
	 * @return
	 */
	List<ResultMeta> getResults()
	{
		return null;
	}

	/** AOP */
	private Class<? extends Handler>[] before;

	private Class<? extends Handler>[] after;

	private List<ResultMeta> results;

	private ForbiddenMeta forbiddenMeta;

	private Class<? extends ResultHandler> defaultResultHandlerType;
}
