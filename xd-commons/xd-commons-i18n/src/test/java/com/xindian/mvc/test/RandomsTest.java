package com.xindian.mvc.test;

import com.xindian.commons.i18n.Random;

/**
 * 随机测试
 * 
 * @author Elva 2014-10-30
 */
public class RandomsTest
{
	public static void main(String args[])
	{
		MyRandoms myRandoms = Random.create(MyRandoms.class);
		System.out.println("" + myRandoms.randomInt());
		System.out.println("" + myRandoms.randomInt());
		System.out.println("" + myRandoms.randomInt());
		System.out.println("str:" + myRandoms.randomString());
		System.out.println("str:" + myRandoms.randomFixedLengthString());
	}
}
