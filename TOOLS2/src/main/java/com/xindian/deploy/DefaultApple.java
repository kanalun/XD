package com.xindian.deploy;

/**
 * @author QCC
 */
public class DefaultApple implements Apple
{
	/** apple's name */
	String name;

	/** apple' dev code */
	String code;

	/** README file path */
	String readme;

	/** changelog.file path */
	String changelog;

	/** apple's version */
	AppleVersion version;

	String appleClassPath;

	// -------------
	int status;
}
