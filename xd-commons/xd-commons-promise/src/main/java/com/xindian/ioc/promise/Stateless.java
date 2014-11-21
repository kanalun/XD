package com.xindian.ioc.promise;

/**
 * 无状态的,无状态的对象
 * 
 * 1,对象属性只能为final类型或者没有属性
 * 
 * 2,用类的方法操作对象,对象的状态不发生改变
 * 
 * 这样的对象可以以单例存在,而不用担心线程安全等问题
 * 
 * @author Elva
 * @date 2011-2-21
 * @version 1.0
 */
public interface Stateless
{

}
