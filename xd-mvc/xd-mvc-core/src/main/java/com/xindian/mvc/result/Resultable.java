package com.xindian.mvc.result;

/**
 * 可以作为结果被返回的,用户可以实现这个接口来扩展返回结果的类型
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public interface Resultable
{
	/**
	 * 返回该Resultable的处理器
	 * 
	 * @return
	 */
	Class<? extends ResultHandler> getHandler();
}
