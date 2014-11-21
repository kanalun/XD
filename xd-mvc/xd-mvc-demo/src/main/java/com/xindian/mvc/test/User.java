package com.xindian.mvc.test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xindian.beanutils.annotation.Element;
import com.xindian.beanutils.annotation.Type;
import com.xindian.mvc.validation.Validation.Required;

/**
 * 简单的FormBean
 * 
 * 用来测试"嵌套注入"
 * 
 * @author Elva
 * 
 */
public class User
{
	private String name;

	private String password;

	private Date birth;

	private int age;

	private int[] is;// 基本类型数组

	private String[] ss;//

	private Address[] addresses;// "对象类型"数组

	// name = "user.list" value = "s1";
	// name = "user.list" value = "s2"
	// 列表类型使用默认的ArrayList实例化,然后
	@Element(Address.class)
	private List list;

	// 集合,使用默认的HashSet实例化,然后创建
	private Set<String> set;

	private Map<String, Address> map;// MAP

	// 如果有需要被注入数据
	@Type(ExAddress.class)
	private Address address;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Date getBirth()
	{
		return birth;
	}

	public void setBirth(Date birth)
	{
		this.birth = birth;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public Address getAddress()
	{
		return address;
	}

	@Required
	public void setAddress(Address address)
	{
		this.address = address;
	}

	public static void main(String args[])
	{
		// DO_NOTHING
	}

	public int[] getIs()
	{
		return is;
	}

	public void setIs(int[] is)
	{
		this.is = is;
	}

	public String[] getSs()
	{
		return ss;
	}

	public void setSs(String[] ss)
	{
		this.ss = ss;
	}

	public Address[] getAddresses()
	{
		return addresses;
	}

	public void setAddresses(@Element(Address.class) Address[] addresses)
	{
		this.addresses = addresses;
	}

	public List<Address> getList()
	{
		return list;
	}

	public void setList(List<Address> list)
	{
		this.list = list;
	}

	public Set<String> getSet()
	{
		return set;
	}

	public void setSet(Set<String> set)
	{
		this.set = set;
	}

	public Map<String, Address> getMap()
	{
		return map;
	}

	public void setMap(Map<String, Address> map)
	{
		this.map = map;
	}

}