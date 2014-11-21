package com.share.utils.hibernate;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

public class SchemaExportTool extends Configuration
{
	private static final long serialVersionUID = 1L;

	private static final String RESOURCE_PATTERN = "/**/*.class";

	private static final String PACKAGE_INFO_SUFFIX = ".package-info";

	private static final TypeFilter[] ENTITY_TYPE_FILTERS = new TypeFilter[] { new AnnotationTypeFilter(Entity.class, false),
			new AnnotationTypeFilter(Embeddable.class, false), new AnnotationTypeFilter(MappedSuperclass.class, false) };

	private final ResourcePatternResolver resourcePatternResolver;

	public SchemaExportTool()
	{
		this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(new PathMatchingResourcePatternResolver());
	}

	public static void main(final String[] args)
	{
		try
		{
			final Properties p = new Properties();
			p.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			final SchemaExportTool cfg = new SchemaExportTool();
			cfg.addProperties(p);
			// cfg.setNamingStrategy(new ImprovedMyNamingStrategy());
			cfg.scanPackage("com.allscore.phone.recharge.testentity");// ,
																		// "com.share.authority.domain",
																		// "com.share.utils.domain"

			final SchemaExport se = new SchemaExport(cfg);
			if (null != args && args.length > 1)
			{
				if ("-f".equals(args[0]))
				{
					se.setOutputFile(args[1]);
				} else
				{
					se.setOutputFile("create_table.sql");
				}
			} else
			{
				se.setOutputFile("D:/create_table.sql");
			}
			se.setDelimiter(";");
			// se.drop(false, false);
			se.create(false, false);
		} catch (final Exception e)
		{
			e.printStackTrace();
		}

	}

	private SchemaExportTool scanPackage(final String... packagesToScan)
	{
		try
		{
			for (final String pkg : packagesToScan)
			{
				final String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
				final Resource[] resources = this.resourcePatternResolver.getResources(pattern);
				final MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
				for (final Resource resource : resources)
				{
					if (resource.isReadable())
					{
						final MetadataReader reader = readerFactory.getMetadataReader(resource);
						final String className = reader.getClassMetadata().getClassName();
						if (matchesEntityTypeFilter(reader, readerFactory))
						{
							addAnnotatedClass(this.resourcePatternResolver.getClassLoader().loadClass(className));
						} else if (className.endsWith(PACKAGE_INFO_SUFFIX))
						{
							addPackage(className.substring(0, className.length() - PACKAGE_INFO_SUFFIX.length()));
						}
					}
				}
			}
			return this;
		} catch (final IOException ex)
		{
			throw new MappingException("Failed to scan classpath for unlisted classes", ex);
		} catch (final ClassNotFoundException ex)
		{
			throw new MappingException("Failed to load annotated classes from classpath", ex);
		}
	}

	/**
	 * Check whether any of the configured entity type filters matches the
	 * current class descriptor contained in the metadata reader.
	 */
	private boolean matchesEntityTypeFilter(final MetadataReader reader, final MetadataReaderFactory readerFactory)
			throws IOException
	{
		for (final TypeFilter filter : ENTITY_TYPE_FILTERS)
		{
			if (filter.match(reader, readerFactory))
			{
				return true;
			}
		}
		return false;
	}

}
