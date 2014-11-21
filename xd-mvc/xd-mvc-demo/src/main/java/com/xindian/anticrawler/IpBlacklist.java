package com.xindian.anticrawler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.xindian.mvc.utils.Validator;

/**
 * IP黑名单
 * 
 * @author Elva
 */
public class IpBlacklist implements Iterable<String>
{
	Set<String> set = new HashSet<String>();

	public boolean addIp(String ip)
	{
		if (Validator.isIPAddress(ip))
		{
			return set.add(ip);
		}
		return false;
	}

	public boolean isInIpBackList(String ip)
	{
		return set.contains(ip);
	}

	public boolean removeIp(String ip)
	{
		return set.remove(ip);
	}

	@Override
	public Iterator<String> iterator()
	{
		return set.iterator();
	}
}
