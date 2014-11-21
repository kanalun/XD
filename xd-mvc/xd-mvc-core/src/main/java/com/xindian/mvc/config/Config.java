package com.xindian.mvc.config;

public interface Config
{
	public String ext();

	public int uploadPoolSize();

	public String encoding();

	public String fileUploadTempDir();

	public Class<?>[] actions();

	public Class<?>[] pathTranslators();

	public Class<?>[] mappingFilters();

	public Class<?>[] resultProtocols();

}
