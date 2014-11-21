package test2;

public class Client
{

	public static void main(String[] args)
	{
		// assert false : "error";

		haveAuth();
		haveNoAuth();
	}

	public static void doMethod(TableDAO dao)
	{
		dao.create();
		dao.query();
		dao.update();
		dao.delete();
	}

	public static void haveAuth()
	{
		TableDAO tDao = TableDAOFactory.getAuthInstance(new AuthProxy("张三"));
		doMethod(tDao);
	}

	public static void haveNoAuth()
	{
		TableDAO tDao = TableDAOFactory.getAuthInstance(new AuthProxy("李四"));
		doMethod(tDao);
	}
}
