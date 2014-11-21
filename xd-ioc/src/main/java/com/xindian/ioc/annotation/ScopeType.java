package com.xindian.ioc.annotation;

/**
 * Bean作用域类型
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
public enum ScopeType
{
	// 线程结束,这个对象就被回收
	// 单例:该程序只有一个该类的实例,生命周期为:
	// MODULE:表示在模块中只有一个实例
	THREAD, SINGLETON, MODULE, PROTOTYPE,
}
