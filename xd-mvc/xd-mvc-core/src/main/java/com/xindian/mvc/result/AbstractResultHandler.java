package com.xindian.mvc.result;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.exception.PowerlessException;

/**
 * 抽象了对结果的一般处理:设置contentType,
 * 
 * 设置字符集,设置缓冲区,设置添加头信息,
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public abstract class AbstractResultHandler implements ResultHandler
{
	private static Logger logger = LoggerFactory.getLogger(AbstractResultHandler.class);

	// protected static String DEFAULT_CONTENT_TYPE = "text/plain";

	protected static String DEFAULT_CONTENT_TYPE = null;

	private static final String EXPIRATION_DATE;

	static
	{
		// Generate expiration date that is one year from now in the past
		GregorianCalendar expiration = new GregorianCalendar();
		expiration.roll(Calendar.YEAR, -1);
		SimpleDateFormat httpDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		EXPIRATION_DATE = httpDate.format(expiration.getTime());
	}

	/**
	 * 关闭浏览器缓存
	 * 
	 * @param response
	 */
	protected void closeCache(HttpServletResponse response)
	{
		// HTTP/1.1 + IE extensions
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, " + "post-check=0, pre-check=0");
		// HTTP/1.0
		response.setHeader("Pragma", "no-cache");
		// Last resort for those that ignore all of the above
		response.setHeader("Expires", EXPIRATION_DATE);
	}

	@Override
	public void doResult(ActionContext actionContext, Object result) throws PowerlessException, IOException, ServletException
	{
		if (result instanceof AbstractResult<?>)
		{
			HttpServletResponse response = ActionContext.getResponse();
			String contentType = null;
			AbstractResult<?> rs = ((AbstractResult<?>) result);

			// set contentType
			contentType = rs.getContentType();// 优先选择Result中的设置
			if (contentType != null)
			{
				logger.debug("Set ContentType:[" + contentType + "],it is from result[" + rs + "]");
				response.setContentType(contentType);
			} else
			{
				contentType = response.getContentType();// 其次是判断response是否已经设置了内容类型
				if (contentType == null)
				{
					response.setContentType(DEFAULT_CONTENT_TYPE);// 使用默认的设置
					logger.debug("Set ContentType:[" + DEFAULT_CONTENT_TYPE + "],it is from DEFAULT[" + rs + "]");
				} else
				{
					logger.debug("Set ContentType:[" + contentType + "],it is from response[" + response + "]");
				}
			}
			// set CharacterEncoding
			String encoding = rs.getCharacterEncoding();
			if (encoding != null)
			{
				logger.debug("Set CharacterEncoding:[" + encoding + "],it is from result[" + rs + "]");
				response.setCharacterEncoding(encoding);
			} else
			{
				// 其次是判断response是否已经设置了内容编码
				encoding = ActionContext.getContext().getEncoding();// 不能判内容默认,因为默认很可能是错误的,
				if (encoding != null)
				{
					response.setCharacterEncoding(ActionContext.getContext().getEncoding());// 使用系统默认的设置
					logger.debug("Set CharacterEncoding:[" + encoding + "],it is from setting of ActionContext["
							+ ActionContext.getContext() + "]");
				} else
				{
					logger.debug("Set CharacterEncoding:[" + response.getCharacterEncoding() + "],it is from response["
							+ response + "]");
				}
				// 将默认编码放回去
				rs.setCharacterEncoding(encoding);
			}
			// setHeader
			if (rs.getHeaders() != null)
			{
				Iterator<String> hds = rs.getHeaders().keySet().iterator();
				while (hds.hasNext())
				{
					String hdName = hds.next();
					Object hdValue = rs.getHeaders().get(hdName);
					if (hdValue instanceof String)
					{
						response.addHeader(hdName, (String) hdValue);
					} else if (hdValue instanceof Integer)
					{
						response.addIntHeader(hdName, (Integer) hdValue);
					} else if (hdValue instanceof Long)
					{
						response.addDateHeader(hdName, (Long) hdValue);
					}
				}
			}
			// Status
			if (rs.getStatus() != null)
			{
				response.setStatus(rs.getStatus());
				logger.debug("Response set Status [" + rs.getStatus() + "]");
			}
			// add cookies
			if (rs.getCookies() != null)
			{
				for (Cookie cookie : rs.getCookies())
				{
					response.addCookie(cookie);
				}
			}
			// close cache
			if (!rs.isBrowserCache())
			{
				closeCache(response);
				logger.debug("BrowserCache Closed!");
			}

		}
	}
}
