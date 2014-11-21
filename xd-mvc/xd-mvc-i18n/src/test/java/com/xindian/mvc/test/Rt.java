package com.xindian.mvc.test;

import com.xindian.mvc.i18n3.Random;

/**
 * 随机测试
 * 
 * @author Elva 2014-10-30
 */
public class Rt
{
	public static void main(String args[])
	{
		RandomsTest randomsTest = Random.create(RandomsTest.class);
		System.out.println("" + randomsTest.randomInt());
		System.out.println("" + randomsTest.randomInt());
		System.out.println("" + randomsTest.randomInt());
		System.out.println("str:" + randomsTest.randomString());
	}
}
