package com.xindian.mvc.test;

import java.util.List;
import java.util.Map;

public class Address
{
	private String city;

	private String zip;

	private String address;

	private double lat;

	private double lon;

	Map<String, Address> map;

	List list;

	private User user;

	public List getList()
	{
		return list;
	}

	public void setList(List list)
	{
		this.list = list;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public double getLat()
	{
		return lat;
	}

	public void setLat(double lat)
	{
		this.lat = lat;
	}

	public double getLon()
	{
		return lon;
	}

	public void setLon(double lon)
	{
		this.lon = lon;
	}

	public Map<String, Address> getMap()
	{
		return map;
	}

	public void setMap(Map<String, Address> map)
	{
		this.map = map;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

}
