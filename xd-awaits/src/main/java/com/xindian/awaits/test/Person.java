package com.xindian.awaits.test;

import java.util.Date;

import com.xindian.awaits.annotation.Column;
import com.xindian.awaits.annotation.Entity;
import com.xindian.awaits.annotation.GeneratedValue;
import com.xindian.awaits.annotation.GenerationType;
import com.xindian.awaits.annotation.Id;
import com.xindian.awaits.annotation.Table;

/**
 * 示例用BEAN
 * 
 * @author Elva
 * 
 */
@Entity
@Table(name = "person")
public class Person
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected Long id;

	@Column(name = "name", length = 50, insertable = true)
	protected String name;

	@Column(name = "birthday")
	protected Date birthday;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

}