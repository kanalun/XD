package com.xindian.mvc.i18n.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Elva
 * @date 2011-2-6
 * @version 1.0
 */
public class TextTag extends BodyTagSupport implements ParamParent
{
	private static Logger logger = LoggerFactory.getLogger(TextTag.class);

	private static final long serialVersionUID = 1L;

	private String key;

	private String params;

	private List<String> paramsList;

	public TextTag()
	{
		super();
		paramsList = new ArrayList<String>();
		init();
		logger.debug("I18NTag Constructed");
	}

	private void init()
	{
		key = null;
		params = null;
		logger.debug("I18NTag init");
	}

	public int doStartTag() throws JspException
	{
		logger.debug("I18NTag doStartTag");
		paramsList.clear();
		return EVAL_PAGE;
	}

	@Override
	public int doAfterBody() throws JspException
	{
		logger.debug("I18NTag doAfterBody");
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException
	{
		logger.debug("I18NTag doEndTag");
		try
		{
			JspWriter out = pageContext.getOut();
			out.print("<br/>Hello! " + key + "		" + params);
			for (String p : paramsList)
			{
				// out.print(pageContext.findAttribute("qcc"));
				out.print("<br/>" + p);
			}
		} catch (Exception e)
		{
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}

	@Override
	public void release()
	{
		logger.debug("I18NTag release");
		init();
		paramsList.clear();
		paramsList = null;
		super.release();
	}

	public void setKey(String key)
	{
		logger.debug("I18NTag setKey:[" + key + "]");
		this.key = key;
	}

	public void setParams(String params)
	{
		logger.debug("I18NTag setParams:[" + params + "]");
		this.params = params;
	}

	@Override
	public void addParameter(String name, String value)
	{
		logger.debug("I18NTag addParameter:[" + value + "]");
		paramsList.add(value);
	}

}
