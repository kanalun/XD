package com.xindian.mvc;

/**
 * 
 * 因为常规的 package-info.java在eclipse中无法正常创建(-),
 * 
 * 所以用户可以在包下放一个PackageInfo类,然后在PackageInfo类中对包的配置;
 * 
 * 系统会在初始化的时候读取PackageInfo的信息,创建PackageMeta
 * 
 * @author Elva
 * @date 2011-3-9
 * @version 1.0
 */
public interface IPackageInfo
{
	// 是否继承父包的配置
	boolean extendsParent();
}
