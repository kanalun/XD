package com.xindian.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://www.domin.com/PackageName/ActionName?action=Method.EXT?[queryString]
 * 
 * http://www.domin.com/ActionName?action=Method.EXT?[queryString]
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public class NormalPathTranslator implements PathTranslator
{
	private Logger logger = LoggerFactory.getLogger(NormalPathTranslator.class);

	private String methodName = "action";

	public NormalPathTranslator(String methodName)
	{
		this.methodName = methodName;
	}

	public NormalPathTranslator()
	{
		// EMPTY
	}

	@Override
	public Mapping translate(String requestURI)
	{
		String p = ActionContext.getRequest().getServletPath();
		String actionName = p.substring(p.lastIndexOf("/") + 1);
		if (actionName == null)
		{
			return null;
		}
		String namespace = p.substring(0, p.lastIndexOf(actionName) - 1);
		if (namespace == null || namespace.trim().length() == 0)
		{
			namespace = "/";
		}
		String method = ActionContext.getRequest().getParameter(methodName);
		if (method == null || method.trim().length() == 0)
		{
			method = null;
		}
		Mapping mapping = new Mapping(namespace, actionName, method);
		logger.debug("TRANSLATE URI =[" + p + "] TO MAPPING:" + mapping);
		return mapping;
	}

	public static void main(String args[])
	{
		// NormalPathTranslator normalPathTranslator = new
		// NormalPathTranslator();
		// System.out.println(normalPathTranslator.translate(""));
	}
}
