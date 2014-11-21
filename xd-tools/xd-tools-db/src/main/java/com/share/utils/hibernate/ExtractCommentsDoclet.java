package com.share.utils.hibernate;

import java.io.FileWriter;
import java.io.IOException;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

/**
 * @author QCC
 */
public class ExtractCommentsDoclet extends Doclet
{
	public static boolean start(RootDoc root)
	{
		for (ClassDoc c : root.classes())
		{
			print(c.qualifiedName(), c.commentText());
			for (FieldDoc f : c.fields(false))
			{
				print(f.qualifiedName(), f.commentText());
			}
			for (MethodDoc m : c.methods(false))
			{
				print(m.qualifiedName(), m.commentText());
				if (m.commentText() != null && m.commentText().length() > 0)
				{
					for (ParamTag p : m.paramTags())
						print(m.qualifiedName() + "@" + p.parameterName(), p.parameterComment());
					for (Tag t : m.tags("return"))
					{
						if (t.text() != null && t.text().length() > 0)
							print(m.qualifiedName() + "@return", t.text());
					}
				}
			}
		}
		return true;
	}

	private static void print(String name, String comment)
	{
		if (comment != null && comment.length() > 0)
		{
			try
			{
				new FileWriter(name + ".txt").append(comment).close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}