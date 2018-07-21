package com.xindian.coal.propertie;

import java.io.Serializable;

/**
 * @author Elva
 * @date 2014-5-23
 * @version 1.0
 */
public class PropertieMeta implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String comment;

	private String key;

	private String value;

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "PropertieMeta [comment=" + comment + ", key=" + key + ", value=" + value + "]";
	}
}