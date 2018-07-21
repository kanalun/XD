package com.xindian.coal.propertie;

import java.io.Serializable;
import java.util.List;

/**
 * @author Elva
 * @date 2014-5-23
 * @version 1.0
 */
public class PropertiesMeta implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String localFile;

	private String encode;

	private List<PropertiesMeta> metas;
}
