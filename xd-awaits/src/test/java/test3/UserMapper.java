package test3;

public interface UserMapper
{
	@Select("SELECT * FROM User WHERE userName = #{userName} AND password = #{password}")
	User getUser(String userName, String password);

	@Select("SELECT * FROM User WHERE city = #{address.city} AND password = #{userName}")
	List<User> findUsers(Address address, String userName);
	
	
	@Select("SELECT * FROM User WHERE city = #{context.address.city} AND password = #{context.userName}")
	List<User> findUsers(Map context);
	
	{
		EnhanceMapper<UserMapper> mapper = session.enhanceMapper(UserMapper.class);
		List<User> users = mapper.setFirst(10) //分页
			.setMax(50).mapper()//返回具体的UserMapper
			.findUsers(aAddress,"qcc");
		
	}
	
}
