package com.xindian.mvc;

import com.xindian.mvc.exception.ErrorCodeException;
import com.xindian.mvc.exception.PowerlessException;

/**
 * 路径翻译器,将合适请求的路径翻译成到Action的映射,
 * 
 * 注意:系统不会为每个请求生成一个PathTranslator实例,
 * 
 * 所以请保证该接口的实现可以再"单例多线程"的环境下很好的运行,
 * 
 * 注:这种方法不是很好,它返回的Mapping是唯一的,
 * 
 * 对路径的匹配功能比较简单,无法模糊匹配
 * 
 * 系统以后可能会使用逆向查找方式,根据路径查找,
 * 
 * 比如:使用正则表达式或者通配符之类匹配的返回 ACTION
 * 
 * @author Elva
 * 
 */
public interface PathTranslator
{
	/**
	 * 抛出PowerlessException异常或者返回NULL表示"路径翻译器"对该路径无能为力,
	 * 
	 * 此时,系统将会尝试将工作交给下一个"翻译器"!
	 * 
	 * 否则返回处理后的映射
	 * 
	 * @param requestURI
	 * @return
	 * @throws PowerlessException
	 *             表示无法处理,系统将会尝试将工作交给下一个"翻译器"!
	 * @throws ErrorCodeException
	 *             抛出异常,比如403,表示该路径无法访问,404表示资源不存在...
	 * 
	 *             抛出这个异常之后,控制权就交给"异常处理系统"了,即,不会再接着处理,比如继续翻译,调用Action等
	 */
	Mapping translate(String requestURI) throws PowerlessException, ErrorCodeException;//
}
