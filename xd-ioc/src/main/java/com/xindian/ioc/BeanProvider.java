package com.xindian.ioc;

/**
 * @author Elva
 * @date 2011-3-5
 * @version 1.0
 */
public interface BeanProvider
{
	/**
	 * 根据beanName获得组装后的对象
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name);
}
