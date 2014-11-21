package com.xindian.mvc;

import com.xindian.mvc.exception.ErrorCodeException;

/**
 * 对请求映射进行过滤成
 * 
 * @author Elva
 * 
 */
public interface MappingFilter
{
	/**
	 * 
	 * 对Mapping进行过滤
	 * 
	 * @param mapping
	 * @return 返回false表示系统拒绝对该mapping的处理,此时控制权交给容器的servlet;
	 * 
	 *         true表示通过过滤,
	 * 
	 *         此外你还能抛出一个错误代码异常,这个
	 * @throws ErrorCodeException
	 *             抛出异常
	 */
	public boolean filter(Mapping mapping) throws ErrorCodeException;
}
