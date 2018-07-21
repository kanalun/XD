package com.xindian.commons.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * 产生随机XX的工具类
 * 
 * @author Elva
 */
public abstract class RandomUtils
{
	private static char CHARS[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'r', 's', 't', 'u', 'v', 'w', 'y', 'z' };

	private static char EN[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'u',
			'v', 'w', 'y', 'z' };

	private static char NON_VOWELS[] = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n',
			'p', 'q', 'r', 's', 't', 'v', 'w', 'z' };

	private static char VOWELS[] = { 'a', 'e', 'i', 'o', 'u', 'y' };

	private static Random random = new Random();

	public static char randomCharOrNumber()
	{
		return EN[randomInt(0, EN.length - 1)];
	}

	/**
	 * 随机子字符
	 * 
	 * @return
	 */
	public static char randomChar()
	{
		return CHARS[randomInt(0, CHARS.length - 1)];
	}

	/**
	 * 随机元音字符
	 * 
	 * @return
	 */
	public static char randomVowel()
	{
		return VOWELS[randomInt(0, VOWELS.length - 1)];
	}

	/**
	 * 随机辅音字符
	 * 
	 * @return
	 */
	public static char randomNonVowel()
	{
		return NON_VOWELS[randomInt(0, NON_VOWELS.length - 1)];
	}

	/**
	 * java.io.tmpdir下随机创建零时文件夹
	 * 
	 * @return
	 * @throws Exception
	 */
	public static File randomTempDir() throws Exception
	{
		File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "oasetest"
				+ File.separator + randomString(12));
		file.mkdirs();
		file.deleteOnExit();
		return file;
	}

	/**
	 * 创建一个临时文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static File randomTempFile() throws Exception
	{
		File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "oase_"
				+ randomString(6));
		file.createNewFile();
		file.deleteOnExit();
		return file;
	}

	public static File randomBinaryFile(int aSize) throws Exception
	{
		File file = randomTempFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(randomBytes(aSize));
		fos.close();
		return file;
	}

	public static File randomTextFile(int aSize) throws Exception
	{
		File file = randomTempFile();
		PrintWriter pw = new PrintWriter(new FileOutputStream(file));
		pw.write(randomString(aSize));
		pw.close();
		return file;
	}

	public static byte[] randomBytes(int aSize)
	{
		return randomBlob(aSize);
	}

	public static byte[] randomBlob(int aSize)
	{
		byte[] retval = new byte[aSize];
		for (int i = 0; i < retval.length; i++)
		{
			retval[i] = randomByte();
		}
		return retval;
	}

	public static byte randomByte()
	{
		return (byte) random.nextInt();
	}

	public static double randomDouble()
	{
		return random.nextLong();
	}

	public static int randomInt()
	{
		return random.nextInt();
	}

	public static int randomInt(int min, int max)
	{
		return (int) ((Math.random() * (max + 1 - min)) + min);
	}

	public static long randomLong()
	{
		return random.nextLong();
	}

	public static long randomLong(long min, long max)
	{
		return (long) ((Math.random() * (max + 1L - min)) + min);
	}

	public static String randomName(int length)
	{
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++)
		{
			sb.append(i % 2 == 0 ? randomNonVowel() : randomVowel());
		}
		return sb.toString();
	}

	/**
	 * 当前线程随机休眠,单位毫秒
	 * 
	 * @param min
	 * @param max
	 * @throws InterruptedException
	 */
	public static void randomSleep(long min, long max) throws InterruptedException
	{
		Thread.sleep(randomLong(min, max));
	}

	public static String randomString(int length)
	{
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++)
		{
			sb.append(randomChar());
		}
		return sb.toString();
	}

	public static String randomString2(int length)
	{
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++)
		{
			sb.append(randomCharOrNumber());
		}
		return sb.toString();
	}

	public static String randomString(int minLength, int maxLength)
	{
		if (minLength < 0)
		{
			minLength = 0;
		}
		if (maxLength < 0)
		{
			maxLength = 0;
		}
		int length = randomInt(minLength, maxLength);
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++)
		{
			sb.append(randomCharOrNumber());
		}
		return sb.toString();
	}

	public static String randomString()
	{
		int i = randomInt(1, 256);
		return randomString2(i);
	}
}
