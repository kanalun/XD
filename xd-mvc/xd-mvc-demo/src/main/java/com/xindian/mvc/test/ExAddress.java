package com.xindian.mvc.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * 
 * @author Elva
 * @date 2011-1-22
 * @version 1.0
 */
public class ExAddress extends Address
{
	private String ok;

	private int[] intArray;

	PropertyChangeSupport bean = new PropertyChangeSupport(this);

	public ExAddress()
	{
		bean.addPropertyChangeListener("ok", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				System.out.println("OLD:" + evt.getOldValue() + "NEW:" + evt.getNewValue() + "-----OK");
			}
		});
	}

	public String getOk()
	{
		return ok;
	}

	public void setOk(String ok)
	{
		bean.firePropertyChange(new PropertyChangeEvent(this, "ok", getOk(), ok));
		this.ok = ok;
	}

	public int[] getIntArray()
	{
		return intArray;
	}

	public void setIntArray(int[] intArray)
	{
		this.intArray = intArray;
	}

}
