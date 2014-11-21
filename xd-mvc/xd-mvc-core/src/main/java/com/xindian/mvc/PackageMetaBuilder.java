package com.xindian.mvc;

/**
 * 
 * @author Elva
 * @date 2011-3-9
 * @version 1.0
 */
public class PackageMetaBuilder
{
	public final static String PACKAGE_INFO_CLASS_NAME = "PackageInfo";

	public PackageMeta getPackageMeta(ActionMeta<?> actionMeta)
	{
		String infoClass = actionMeta.getActionClass().getPackage().getName() + "." + PACKAGE_INFO_CLASS_NAME;
		try
		{
			Class t = Class.forName(infoClass);
			// t.getAnnotation(annotationClass);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
