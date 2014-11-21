package com.xindian.mvc.cache;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

/**
 * Velocity模板上用于控制缓存的指令
 * 
 * 该类必须在 velocity.properties 中配置
 * userdirective=com.xindian.mvc.cache.CacheDirective
 * 
 * @author Winter Lau
 * @date 2009-3-16 下午04:40:19
 */
public class CacheDirective extends Directive
{
	private final static Map<String, String> bodyTemplates = new Hashtable<String, String>();

	@Override
	public String getName()
	{
		return "cache";
	}

	@Override
	public int getType()
	{
		return BLOCK;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException,
			ResourceNotFoundException, ParseErrorException, MethodInvocationException
	{
		// 获得缓存信息
		SimpleNode snRegion = (SimpleNode) node.jjtGetChild(0);
		String region = (String) snRegion.value(context);
		SimpleNode snKey = (SimpleNode) node.jjtGetChild(1);
		Serializable key = (Serializable) snKey.value(context);

		Node body = node.jjtGetChild(2);
		// 检查内容是否有变化
		String tplKey = key + "@" + region;
		String bodyTpl = body.literal();
		String oldBodyTpl = bodyTemplates.get(tplKey);
		String cacheHtml = CacheManager.get(String.class, region, key);
		if (cacheHtml == null || !StringUtils.equals(bodyTpl, oldBodyTpl))
		{
			StringWriter sw = new StringWriter();
			body.render(context, sw);
			cacheHtml = sw.toString();
			CacheManager.set(region, key, cacheHtml);
			bodyTemplates.put(tplKey, bodyTpl);
		}
		writer.write(cacheHtml);
		return true;
	}
}
