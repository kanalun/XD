package com.xindian.awaits.test;

import com.xindian.awaits.DAOSupport;
import com.xindian.awaits.annotation.UseSQL;

/**
 * 申明式DAO
 * 
 * @author Elva
 * @date 2011-3-14
 * @version 1.0
 */
public interface TestDao extends DAOSupport
{
	@UseSQL("select * from table where id = :id and name = :name")
	public String getString(String id, String name);
}
