package com.xd.tools.test;

/**
 * @author qiucongcong 2014-4-4
 */
public class User
{
	/**
	 * @column ID
	 * @comment 这是注释
	 * @nullable false
	 * @default "123"
	 * @query like,range
	 */
	String id;

	String username;

	String password;
}
