package com.xindian.ioc.annotation;

/**
 * 在方法上加上注解@PostConstruct，这个方法就会在Bean初始化之后被Spring容器执行
 * 
 * （注：Bean初始化包括，实例化Bean，并装配Bean的属性（依赖注入））。 它的一个典型的应用场景是，
 * 
 * 当你需要往Bean里注入一个其父类中定义的属性，而你又无法复写父类的属性或属性的setter方法时，如
 * 
 * @author Elva
 * @date 2011-1-24
 * @version 1.0
 */
public @interface PostConstruct
{
}
