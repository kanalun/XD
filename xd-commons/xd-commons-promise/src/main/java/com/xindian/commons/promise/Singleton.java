package com.xindian.commons.promise;

/**
 * <p>
 * 这个接口没有任何作用
 * </p>
 * 
 * <p>
 * 只是在语意上说明实现它类<strong>应该作为单例模式存在</strong>
 * </p>
 * 
 * 关于如何使用和编写单例请遵照相关的单例编写约定<br/>
 * 
 * 一般情况下,单例意味着ThreadSafe
 * 
 * 对于有些系统,在约定情况下对实现Singleton接口的类使用getSingleton方法进行实例化,
 * 
 * 或者即使不使用getSingleton,该类将以单例的形式出现
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
public interface Singleton
{
	// 必须为Singleton对象 实现 public static T
	// getSingleton()工厂方法,该方法返回对象的单例值,如果不支持,抛出一个异常
}
