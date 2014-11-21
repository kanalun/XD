package com.xd.tools.classgenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xd.tools.Constants;

/**
 * @author QCC
 */
public class ClassGenerator implements Constants
{
	/**	 */
	boolean formart = true;

	public String generate(String packageName, String className, List<FieldMeta> fields)
	{
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("package " + packageName + " ;");
		if (formart)
		{
			stringBuffer.append(CRLF);
		}
		if (fields != null)
		{
			Set<String> imports = new HashSet<String>();
			for (FieldMeta fieldMeta : fields)
			{
				imports.add(fieldMeta.typePackageName + "." + fieldMeta.typeName);
			}
			for (String pkgName : imports)
			{
				if (pkgName != null && !pkgName.trim().equals("") && !pkgName.equals("java.lang"))
				{
					stringBuffer.append("import " + pkgName + " ;");
					if (formart)
					{
						stringBuffer.append(CRLF);
					}
				}
			}
		}

		stringBuffer.append("public class " + className + "{");
		if (formart)
		{
			stringBuffer.append(CRLF);
		}
		// --start
		if (fields != null)
		{
			for (FieldMeta fieldMeta : fields)
			{
				// fieldMeta.comment = "TEST";
				if (fieldMeta.comment != null)
				{
					// 注释
					stringBuffer.append("	/**" + fieldMeta.comment + "*/");
					if (formart)
					{
						stringBuffer.append(CRLF);
					}
				}
				stringBuffer.append("	" + fieldMeta.typeName + " " + fieldMeta.name);
				// TODO 默认赋值?
				stringBuffer.append(";");
				if (formart)
				{
					stringBuffer.append(CRLF);
				}
			}
		}
		// --END
		stringBuffer.append("}");
		if (formart)
		{
			stringBuffer.append(CRLF);
		}
		return stringBuffer.toString();
	}
}
