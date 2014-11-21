package com.xd.testentity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.share.annotations.Comment;

/**
 * 测试实体
 * 
 * @author QCC
 */
@Entity
public class TestEntity
{
	/** ID */
	@Comment("IDIDIIDIDIDi")
	private String id;

	/** 这是测试注释:用户名 */
	private String username;

	/** 这是实体2,密码 */
	private String password;

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @return the id
	 */
	@Id
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @hibernate.property length = "40"
	 * 
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

}
