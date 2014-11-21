package com.xindian.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.exception.ErrorCodeException;
import com.xindian.mvc.exception.PowerlessException;

/**
 * 感叹号路径翻译:http://www.domin.com/PackageName/ActionName!Method.EXT?[queryString]
 * TODO 这个东东需要"测试"
 * 
 * 存在已知的错误!
 * 
 * @author Elva
 * 
 */
public class ExclamationMarkPathTranslator implements PathTranslator
{
	private static Logger logger = LoggerFactory.getLogger(ExclamationMarkPathTranslator.class);

	public ExclamationMarkPathTranslator(String ext)
	{
		setExt(ext);
	}

	public ExclamationMarkPathTranslator()
	{
	}

	private String ext = "do";

	@Override
	public Mapping translate(String requestURI) throws PowerlessException, ErrorCodeException
	{
		String p = ActionContext.getRequest().getServletPath();

		String[] path = p.split("/"); // requestURI.split("/");

		String namespace = null;

		String actionName = null;

		String methodName = null;

		for (String pathPart : path)
		{
			// System.out.println(pathPart);
			if (pathPart.endsWith("." + ext))
			{
				try
				{
					namespace = p.substring(0, p.indexOf(pathPart) - 1);
					if (namespace == null || namespace.length() == 0)
					{
						namespace = "/";
					}
				} catch (IndexOutOfBoundsException exception)
				{
					namespace = "/";
				}
				try
				{
					actionName = pathPart.substring(0, pathPart.indexOf("!"));
					methodName = pathPart.substring(pathPart.indexOf("!") + 1, pathPart.indexOf("."));
				} catch (IndexOutOfBoundsException exception)
				{
					actionName = pathPart.substring(0, pathPart.indexOf("." + ext));
					methodName = null;
				}

				// System.out.print("PACKAGE = " + requestURI.substring(0,
				// requestURI.indexOf(pathPart)));

				// System.out.print(" ACTION = " + actionName);

				// System.out.println(" METHOD = " + methodName);
				break;
			}
		}
		if (actionName == null)// actionName是必要条件
		{
			return null;
		}
		Mapping mapping = new Mapping(namespace, actionName, methodName);
		logger.debug("TRANSLATE URI =[" + p + "] TO MAPPING:" + mapping);
		return mapping;
	}

	public void setExt(String ext)
	{
		logger.debug("setExt:" + ext);
		synchronized (ext)
		{
			this.ext = ext;
		}
	}

	public String getExt()
	{
		return ext;
	}
}
