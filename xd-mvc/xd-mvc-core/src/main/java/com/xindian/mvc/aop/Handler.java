package com.xindian.mvc.aop;

import com.xindian.mvc.exception.VoteException;

/**
 * AOP方法接口
 * 
 * 注:该功能还需完善
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public interface Handler
{
	// public void excute(String... params) throws VoteException;
	public void execute() throws VoteException;
}
