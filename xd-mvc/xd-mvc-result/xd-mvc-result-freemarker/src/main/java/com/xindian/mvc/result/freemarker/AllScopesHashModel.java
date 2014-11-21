package com.xindian.mvc.result.freemarker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * wrap all things to sent to the template
 * 
 * @author Elva
 * @date 2011-1-16
 * @version 1.0
 */
public class AllScopesHashModel extends SimpleHash
{
	private static final long serialVersionUID = 1L;

	private final ObjectWrapper wrapper;

	private transient final ServletContext context;

	private transient final HttpServletRequest request;

	private/* transient */final Map<String, TemplateModel> unlistedModels = new HashMap<String, TemplateModel>();

	private/* transient */final ActionHashModel actionHashModel;

	/**
	 * 
	 * Creates a new instance of @See AllScopesHashModel for handling a single
	 * HTTP servlet request.
	 * 
	 * @param wrapper
	 *            the object wrapper to use
	 * @param context
	 *            the servlet context of the web application
	 * @param request
	 *            the HTTP servlet request being processed
	 */
	public AllScopesHashModel(ObjectWrapper wrapper, ActionHashModel actionHashModel, ServletContext context,
			HttpServletRequest request)
	{
		super(wrapper);
		this.actionHashModel = actionHashModel;
		this.wrapper = wrapper;
		this.context = context;
		this.request = request;
	}

	/**
	 * Stores a model in the hash so that it doesn't show up in <tt>keys()</tt>
	 * and <tt>values()</tt> methods. Used to put the Application, Session,
	 * Request, RequestParameters and JspTaglibs objects.
	 * 
	 * @param key
	 *            the key under which the model is stored
	 * @param model
	 *            the stored model
	 */
	public void putUnlistedModel(String key, TemplateModel model)
	{
		unlistedModels.put(key, model);
	}

	public TemplateModel get(String key) throws TemplateModelException
	{
		// Lookup in page scope
		TemplateModel model = super.get(key);
		if (model != null)
		{
			return model;
		}
		// Look in unlisted models
		model = (TemplateModel) unlistedModels.get(key);
		if (model != null)
		{
			return model;
		}
		// Look in action models
		if (actionHashModel != null)
		{
			model = actionHashModel.get(key);
			if (model != null)
			{
				return model;
			}
		}
		// Lookup in request scope
		Object obj = request.getAttribute(key);
		if (obj != null)
		{
			return wrapper.wrap(obj);
		}

		// Lookup in session scope
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			obj = session.getAttribute(key);
			if (obj != null)
			{
				return wrapper.wrap(obj);
			}
		}
		// Lookup in application scope
		obj = context.getAttribute(key);
		if (obj != null)
		{
			return wrapper.wrap(obj);
		}

		// return wrapper's null object (probably null).
		return wrapper.wrap(null);
	}
}
