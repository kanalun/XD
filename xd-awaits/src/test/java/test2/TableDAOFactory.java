package test2;

import net.sf.cglib.proxy.Enhancer;

public class TableDAOFactory
{
	public static TableDAO getAuthInstance(AuthProxy authProxy)
	{
		Enhancer en = new Enhancer();
		// 进行代理
		en.setSuperclass(TableDAO.class);
		en.setCallback(authProxy);
		// 生成代理实例
		return (TableDAO) en.create();
	}
}
