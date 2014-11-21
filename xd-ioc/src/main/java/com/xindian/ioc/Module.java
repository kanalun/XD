package com.xindian.ioc;

/**
 * 模块相当于一个包,
 * 
 * 其中配置Bean的信息,我们可以将对象注册到模块中去,
 * 
 * 等到需要使用Bean的时候从模块中获得该Bean
 * 
 * @author Elva
 * @date 2011-3-5
 * @version 1.0
 */
public interface Module
{
	/**
	 * 根据beanName获得组装后的对象
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name);
}
