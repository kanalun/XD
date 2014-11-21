package com.xindian.mvc.test.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.anticrawler.IpBlacklist;
import com.xindian.mvc.ActionContext;
import com.xindian.mvc.annotation.Forbidden;
import com.xindian.mvc.result.Forward;
import com.xindian.mvc.utils.Validator;

/**
 * 管理反爬虫系统
 * 
 * @author Elva
 * @date 2011-1-21
 * @version 1.0
 */
public class AntiCrawlerAction
{
	private static Logger logger = LoggerFactory.getLogger(AntiCrawlerAction.class);

	private final static String PAGE_ANTI_CRAWLER = "AntiCrawler.jsp";

	String ip2BlackList;

	String message = "Add";

	String ipRegexp;

	// 增加IP过滤规则
	public Object addIptoBlackList()
	{
		// logger.debug("IP IS" + ip2BlackList);
		if (Validator.isBlank(ip2BlackList))
		{
			logger.debug("IP IS NULL");
			return new Forward(PAGE_ANTI_CRAWLER).addParameter("msg", "请输入IP地址..").setURLEncode("UTF-8")
					.addAttribute("qcc", "qcccc");
		}
		if (!Validator.isIPAddress(ip2BlackList))
		{
			logger.debug("[" + ip2BlackList + "] IS NOT A IP");
			return new Forward(PAGE_ANTI_CRAWLER).addParameter("msg", ip2BlackList + "不是一个有效的IP地址,请输入正确的IP地址...").setURLEncode(
					"UTF-8");
		}
		IpBlacklist ipBlacklist = (IpBlacklist) ActionContext.getServletContext().getAttribute("balckList");
		if (ipBlacklist == null)
		{
			ipBlacklist = new IpBlacklist();
			ActionContext.getServletContext().setAttribute("balckList", ipBlacklist);
		}

		if (ipBlacklist.isInIpBackList(ip2BlackList))
		{
			logger.debug("IP IS" + ip2BlackList + "已经存在了");
			return new Forward(PAGE_ANTI_CRAWLER).addParameter("msg", ip2BlackList + "已经存在了").setURLEncode("UTF-8");
		}
		if (ipBlacklist.addIp(ip2BlackList))
		{
			logger.debug("IP IS" + ip2BlackList);
			return new Forward(PAGE_ANTI_CRAWLER).addParameter("msg", ip2BlackList + "已经添加到黑名单...").setURLEncode("UTF-8");
		} else
		{
			return new Forward(PAGE_ANTI_CRAWLER).addParameter("msg", "加入IP黑名单失败..").setURLEncode("UTF-8");
		}
	}

	// 删除IP过滤规则
	@Forbidden
	public Object removeIptoBlackList()
	{
		return null;
	}

	public String getIp2BlackList()
	{
		return ip2BlackList;
	}

	public void setIp2BlackList(String ip2BlackList)
	{
		this.ip2BlackList = ip2BlackList;
	}

}
