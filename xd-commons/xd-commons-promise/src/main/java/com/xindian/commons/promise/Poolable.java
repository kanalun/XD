package com.xindian.commons.promise;

/**
 * 可以池化的对象,在某些情况下,
 * 
 * 系统提供的对象池功能,
 * 
 * 使用该接口对频繁创建的重型对象进行池化以提高效率
 * 
 * 注意:该池化接口不一定会被使用
 * 
 * 比如:对于同时实现Singleton接口的单例对象,系统会优先选择使用单例,该接口是无效的
 * 
 * @author Elva
 * @date 2011-2-21
 * @version 1.0
 */
public interface Poolable
{
	/**
	 * 初始化对象
	 */
	public void init();

	/**
	 * 
	 */
	public void destory();
}
