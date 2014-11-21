package com.xindian.mvc.result;

/**
 * 返回的是可读的结果:即:文本类型
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public abstract class ReadableResult<T extends ReadableResult<T>> extends AbstractResult<T> implements Resultable
{

}
