package com.xindian.mvc.result;

import java.util.HashMap;
import java.util.Map;

import com.xindian.commons.utils.Validator;

/**
 * 管理和存放返回结果协议解析器<br/>
 * 
 * 它是程序性单例,你可以再这里注册自己扩展的解析器 <br/>
 * 
 * 返回的数据中很可能包含协议导致返回的数据无法被正确的处理 协议要做的事情是很明确的
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public class ProtocolParserFactory
{
	private Map<String, ResultProtocolParser> protocols = new HashMap<String, ResultProtocolParser>();

	private ProtocolParserFactory()
	{
	}

	private static ProtocolParserFactory singleton = new ProtocolParserFactory();

	public static ProtocolParserFactory getSingleton()
	{
		return singleton;
	}

	public static String getProtocol(String resultString)
	{
		try
		{
			return resultString.substring(0, resultString.indexOf("://"));
		} catch (StringIndexOutOfBoundsException e)
		{
			return null;
		}
	}

	/**
	 * 获得文本协议内容
	 * 
	 * @param result
	 * @return
	 */
	public static String getContent(String result)
	{
		try
		{
			return result.substring(result.indexOf("://") + 3, result.length());
		} catch (StringIndexOutOfBoundsException e)
		{
			return null;
		}
	}

	public ProtocolParserFactory registeResultProtocol(ResultProtocolParser protocol)
	{
		synchronized (protocols)
		{
			protocols.put(protocol.getProtocolName(), protocol);
		}
		return this;
	}

	public ProtocolParserFactory addResultProtocol(ResultProtocolParser protocol)
	{
		synchronized (protocols)
		{
			protocols.put(protocol.getProtocolName(), protocol);
		}
		return this;
	}

	/**
	 * 解析包含
	 * 
	 * @param result
	 *            包含协议和内容在内的字符串 string://someString
	 * @return 可以解析返回解析结果,否则返回null
	 */
	public Resultable parseResultable(String result)
	{
		String p = getProtocol(result);
		String c = getContent(result);
		if (Validator.allNotBlank(p, c))
		{
			ResultProtocolParser protocol = protocols.get(p);
			if (protocol != null)
			{
				return protocol.parse(c);
			}
		}
		return null;
	}

	/**
	 * 将协议解析成"返回结果"
	 * 
	 * @param p
	 *            协议名称比如:string
	 * @param c
	 *            内容比如:someString
	 * @return 可以解析返回解析结果,否则返回null
	 */
	public Resultable parseResultable(String p, String c)
	{
		if (Validator.allNotBlank(p, c))
		{
			ResultProtocolParser protocol = protocols.get(p);
			if (protocol != null)
				return protocol.parse(c);
		}
		return null;
	}

	/**
	 * 根据协议名称返回
	 * 
	 * @param protocol
	 * @return
	 */
	public ResultProtocolParser getProtocolParserByProtocol(String protocol)
	{
		return protocols.get(protocol);
	}

	public ResultProtocolParser getProtocolParserByResultString(String result)
	{
		String p = getProtocol(result);
		return protocols.get(p);
	} // 字符串的魅力

	public static void main(String args[])
	{
		System.out.println("协议: " + getProtocol("http:D//index.jsp"));// 不是协议
		System.out.println("协议: " + getProtocol("forward://index.jsp"));
		System.out.println("协议: " + getProtocol("file://index.jsp"));
		System.out.println("协议: " + getProtocol("stream://index.jsp"));
		System.out.println("协议: " + getProtocol("red://index.jsp"));
		System.out.println("协议: " + getProtocol("json://index.jsp"));
		System.out.println("协议: " + getProtocol("ftl://index.ftl"));
		System.out.println("协议: " + getContent("vm://index.vm"));
		System.out.println("协议: " + getProtocol("http://index.vm"));// 注意到了吗?
	}
}
