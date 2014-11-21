package com.xindian.mvc.authz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Authz
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		HttpSession session = request.get().getSession(false);
		session.getAttribute("Envlement");
	}

	static ThreadLocal<HttpServletRequest> request;

}
