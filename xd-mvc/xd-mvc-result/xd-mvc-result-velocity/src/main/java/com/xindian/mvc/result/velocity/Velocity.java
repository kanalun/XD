package com.xindian.mvc.result.velocity;

import org.apache.velocity.Template;

import com.xindian.mvc.result.ReadableResult;
import com.xindian.mvc.result.ResultHandler;
import com.xindian.mvc.result.Resultable;

/**
 * 返回一个Velocity视图
 * 
 * @author Elva
 * @date 2011-1-18
 * @version 1.0
 */
public class Velocity extends ReadableResult<Velocity> implements Resultable
{
	private String templateDecoding = "UTF-8";// 这个相当于配置文件中的input.encoding?

	private String templateLocation;

	public Velocity(String template)
	{
		this.templateLocation = template;
		setContentType("text/html");
		setCharacterEncoding("UTF-8");
	}

	protected Velocity(Template template)
	{
		setContentType("text/html");
		setCharacterEncoding("UTF-8");
	}

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return VelocityResultHandler.class;
	}

	/**
	 * 设置模板解码"字符集名称",<br/>
	 * 
	 * 对于个别模板,编码和系统不一样,举个例子:如果是是GBK或者其他什么代码,<br/>
	 * 
	 * 但是运行环境是UTF-8,可能就会造成模板乱码,<br/>
	 * 
	 * 使用这个方法设置正确的解码字符集,可以避免这种情况的发生<br/>
	 * 
	 * 我们建议模板使用统一的字符集编码<br/>
	 * 
	 * 如果这个为NULL,会使用默认的解码字符集,<br/>
	 * 
	 * 在velocty的配置文件中修改<br/>
	 * 
	 * @param decoding
	 *            字符集名称
	 * @return
	 */
	public Velocity setTemplateDecoding(String decoding)
	{
		this.templateDecoding = decoding;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getTemplateDecoding()
	{
		return templateDecoding;
	}

	/**
	 * 
	 * @return
	 */
	public String getTemplateLocation()
	{
		return templateLocation;
	}

	/**
	 * 
	 * @param templateLocation
	 */
	public Velocity setTemplateLocation(String templateLocation)
	{
		this.templateLocation = templateLocation;
		return this;
	}

}
