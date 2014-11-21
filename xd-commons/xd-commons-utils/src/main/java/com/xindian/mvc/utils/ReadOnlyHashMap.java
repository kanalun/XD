package com.xindian.mvc.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 只读的HashMap,你无法使用默认的Map方法对其写入数据
 * 
 * @author Elva
 * @date 2011-2-20
 * @version 1.0
 * @param <K>
 * @param <V>
 */
public class ReadOnlyHashMap<K, V> extends HashMap<K, V>
{
	private static final long serialVersionUID = 1L;

	@Override
	public void clear()
	{
		throw new UnsupportedOperationException(
				"this map is read only,if you want to clear this map ,use clear(boolean write) instead");
	}

	/**
	 * 
	 * @param write
	 */
	public void clear(boolean write)
	{
		super.clear();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return super.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return super.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return super.entrySet();
	}

	@Override
	public Set<K> keySet()
	{
		return super.keySet();
	}

	/**
	 * 
	 */
	public V put(K key, V value)
	{
		throw new UnsupportedOperationException("this map is read only");
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param write
	 * @return
	 */
	public V put(K key, V value, boolean write)
	{
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		throw new UnsupportedOperationException("this map is read only");
	}

	/**
	 * 
	 * @param m
	 * @param write
	 */
	public void putAll(Map<? extends K, ? extends V> m, boolean write)
	{
		super.putAll(m);
	}
}
