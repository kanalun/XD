package com.xd.tools.db.mybatis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiucongcong 2014-4-11
 */
public class ResultMapMeta
{
	public String type;

	public String id;

	public String autoMapping;

	public String extends_;

	public List<ResultMeta> resultMetas = new ArrayList<ResultMeta>();

	public ResultMapMeta()
	{
	}
}