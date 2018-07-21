package com.xindian.mvc.test;

import com.xindian.commons.i18n.Randoms;

/**
 * 测试随机
 * 
 * @author Elva
 * @date 2011-2-20
 * @version 1.0
 */
public interface RandomsTest extends Randoms
{
	/**
	 * 每一次调用都会返回一个随机值
	 * 
	 * @return
	 */
	@RandomInt(min = 1, max = 10)
	int randomInt();

	@RandomString(minLenght = 1, maxLength = 2, in = { 'a', 'b', 'c', '白', '痴' }, ins = { "a-z", "A-Z", "0-9" })
	String randomString();

	@RandomFixedLengthString(length = 10, in = { 'a', 'b', 'c', '白', '痴' }, ins = { "a-z", "A-Z", "0-9" })
	String randomFixedLengthString();
}
