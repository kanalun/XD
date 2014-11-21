package com.xindian.mvc.result;

import java.io.IOException;

import javax.servlet.ServletException;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.PowerlessException;

/**
 * 该方法用来处理Action返回的结果.<br/>
 * 注意:请确保实现该接口的类在单例多线程环境下可安全运行
 * 
 * @author Elva
 * 
 */
public interface ResultHandler
{
	/**
	 * dealWith the Result
	 * 
	 * @param actionContext
	 *            请求上下文
	 * @param result
	 *            Acton 方法返回的值
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doResult(ActionContext actionContext, Object result) throws PowerlessException, IOException, ServletException;
}
