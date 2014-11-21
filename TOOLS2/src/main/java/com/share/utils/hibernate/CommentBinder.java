package com.share.utils.hibernate;

import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.cfg.Ejb3Column;
import org.hibernate.mapping.PersistentClass;

import com.share.annotations.Comment;

public class CommentBinder
{
	public static void bindTableComment(final XClass clazzToProcess, final PersistentClass persistentClass)
	{
		if (clazzToProcess.isAnnotationPresent(Comment.class))
		{
			final String tableComment = clazzToProcess.getAnnotation(Comment.class).value();
			persistentClass.getTable().setComment(tableComment);
		}
	}

	public static void bindColumnComment(final XProperty property, final Ejb3Column[] columns)
	{
		if (null != columns)
		{
			if (property.isAnnotationPresent(Comment.class))
			{
				final String comment = property.getAnnotation(Comment.class).value();
				for (final Ejb3Column column : columns)
				{
					column.getMappingColumn().setComment(comment);
				}
			}
		}
	}

	public static void bindColumnComment(final XProperty property, final Ejb3Column column)
	{
		if (null != column)
		{
			if (property.isAnnotationPresent(Comment.class))
			{
				final String comment = property.getAnnotation(Comment.class).value();
				column.getMappingColumn().setComment(comment);
			}
		}
	}
}
