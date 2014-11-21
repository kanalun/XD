package com.xindian.beanutils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xindian.beanutils.annotation.Type;
import com.xindian.mvc.exception.MVCException;
import com.xindian.mvc.test.Address;
import com.xindian.mvc.test.User;

public class Bean4
{
	private static DefaultResolver resolver = new DefaultResolver();

	private static Logger logger = LoggerFactory.getLogger(Bean4.class);

	/**
	 * 将Map中的数据填充到BEAN中
	 * 
	 * @param bean
	 * @param map
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void populate(Object bean, Map<String, ?> map)
	{
		for (String name : map.keySet())
		{
			try
			{
				setValue(bean, name, map.get(name));
			} catch (Exception e)
			{
				if (logger.isDebugEnabled())
				{
					//
					e.printStackTrace();
				}
				// DO_NOTHING
			}
		}
	}

	// 1,判断是否为可读,如果或者无法读取(没有getter方法,且不是public的)抛出无法读取异常,

	// 2,得到bean中名称为filedName的属性

	// 3,如果属性对象不存在返回null

	// 4,其他异常照样抛出表示无法读取
	@SuppressWarnings("unchecked")
	public static Object get(Object bean, String name) throws IllegalArgumentException, IllegalAccessException
	{
		if (bean instanceof Map)
		{
			return ((Map) bean).get(name);
		} else if (bean instanceof List)
		{
			return getObjectFromList((List) bean, Integer.parseInt(name));
		} else if (bean.getClass().isArray())
		{
			return getObjectFromArray(bean, Integer.parseInt(name));
		} else
		// 对于普通Bean,使用Getter方法获得对象的属性
		{
			Object fieldValue = null;
			Field field = BeanUtilsTest.getFiled(bean, name);
			if (field != null)
			{
				field.setAccessible(true);
				fieldValue = field.get(bean);// 1,尝试,获取这个引用的对象
			}
			return fieldValue;
		}
	}

	@SuppressWarnings("unchecked")
	public static void set(Object bean, String name, Object value) throws IllegalArgumentException, IllegalAccessException
	{
		logger.debug("SET[" + value.getClass() + "] TO[" + bean.getClass() + "]'s Field[" + name + "]Valie[" + value + "]");

		if (bean instanceof Map<?, ?>)
		{
			((Map) bean).put(name, value);
		} else if (bean instanceof List)
		{
			setObjectToList((List) bean, Integer.parseInt(name), value);
		} else if (bean instanceof Set<?>)
		{
			((Set) bean).add(value);
		} else if (bean instanceof Object[])
		{
			setObjectToArray(bean, Integer.parseInt(name), value);// ERROR
		} else
		{
			Field field = BeanUtilsTest.getFiled(bean, name);
			if (field != null)
			{
				field.setAccessible(true);
				field.set(bean, value);
			}
		}
	}

	/**
	 * 将value放到list的index的位置,如果这个位置有数据, 覆盖这个数据;
	 * 
	 * 如果index>=list.size,在size到index+1的位置,添加空的元素!然后在index的位置添加
	 * 
	 * @param <T>
	 * @param list
	 * @param index
	 * @param value
	 */
	public static <T> void setObjectToList(List<T> list, int index, T value)
	{
		int size = list.size();
		if (index < size)
		{
			list.set(index, value);
		} else
		{
			for (int i = size; i < index + 1; i++)
			{
				list.add(null);
			}
			list.set(index, value);
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param list
	 * @param index
	 * @return
	 */
	public static <T> T getObjectFromList(List<T> list, int index)
	{
		try
		{
			return list.get(index);
		} catch (IndexOutOfBoundsException e)
		{
			return null;
		}
	}

	/**
	 * 在
	 * 
	 * @param array
	 * @param index
	 * @return
	 */
	protected static Object getObjectFromArray(Object array, int index)
	{
		if (array.getClass().isArray())
		{
			try
			{
				return Array.get(array, index);
			} catch (ArrayIndexOutOfBoundsException e)
			{
				return null;
			}
		} else
		{
			throw new MVCException("[" + array.getClass() + "] is not a Array");
		}
	}

	/**
	 * 将value放到数组index上的位置,
	 * 
	 * @param <T>
	 * @param array
	 * @param index
	 * @param value
	 * 
	 * @return 返回修改后的数组:注意这个数组可能不是原来那个数组,
	 * 
	 *         所以你需要将这个返回值重新设定到他的引用上去
	 */
	@SuppressWarnings("unchecked")
	protected static <T> T setObjectToArray(T array, int index, Object value)
	{
		Class<?> arrayType = array.getClass();
		if (arrayType.isArray())
		{
			int len = Array.getLength(array);// 返回指定数组的长度
			if (index < len)
			{
				Array.set(array, index, value);
				return array;
			} else
			{
				Class<?> elementType = arrayType.getComponentType();

				Object newArray = Array.newInstance(elementType, index + 1);// 创建一个指定组件类型和长度的新数组

				System.arraycopy(array, 0, newArray, 0, len);

				Array.set(newArray, index, value);

				return (T) newArray;
			}
		} else
		{
			throw new ClassCastException("传入的参必须是数组!");
		}
	}

	/**
	 * 给定一个类型,创建该类型的对象
	 * 
	 * @param type
	 * @return
	 * @throws InstantiationException
	 *             无法实例化
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T buildObject(Class<T> type) throws InstantiationException, IllegalAccessException
	{
		T object = null;
		if (type.isArray())// 创建一个数组
		{
			Class<?> elementType = type.getComponentType();// 数组元素类型
			if (elementType.isArray())
			{
				throw new InstantiationException("只支持一维数组的创建..");
			} else
			{
				logger.debug("Array of: " + elementType);
				return (T) Array.newInstance(elementType, 1);// 创建一个拥有1个元素,的一位数组
			}
		} else if (type.isInterface())// 是一个接口
		{
			if (type.isAssignableFrom(Map.class))
			{
				logger.debug(type + "是一个接口,且是MAP的接口");
				return (T) new HashMap();
			} else if (type.isAssignableFrom(List.class))
			{
				logger.debug(type + "是一个接口,List");
				return (T) new ArrayList();
			} else if (type.isAssignableFrom(Set.class))
			{
				logger.debug(type + "是一个接口,Set");
				return (T) new HashSet();
			} else
			{
				// 无法创建
				throw new InstantiationException("无法创建类型[" + type + "],他是一个Interface");
			}
		} else if (type.isAnnotation())
		{
			throw new InstantiationException("无法创建类型[" + type + "],他是一个Annotation");

		} else if (type.isEnum())
		{
			// 无法创建
			throw new InstantiationException("无法创建类型[" + type + "],他是一个Enum");
		} else if (type.isPrimitive())
		{
			throw new InstantiationException("无法创建基本类型[" + type + "],他是一个Primitive");
			// 基本类型
		} else
		{
			object = type.newInstance();
			logger.debug("创建普通类型[" + type + "]");
		}
		return object;
	}

	// 根据bean创建一个新的对象引用
	// 新建的对象不会被入这个夫对象
	//
	public static Object createObject(Object bean, String filedName) throws SecurityException, IllegalAccessException,
			NoSuchFieldException, InstantiationException
	{
		Object fieldValue = null;
		Field field = BeanUtilsTest.getFiled(bean, filedName);
		if (field.isAnnotationPresent(Type.class))// 2,如果声明了类型的,数组
		{
			Class<?>[] types = field.getAnnotation(Type.class).value();
			for (Class<?> type : types)
			{
				try
				{
					fieldValue = type.newInstance();// TODO
					if (fieldValue != null)
					{
						logger.debug("1,根据类型注解创建对象[" + type + "]");
						return fieldValue;
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}// ERROR,尝试了所有的类型注解
			throw new InstantiationException("2,杯具发生了,所有的注解的类型都无法创建对象!!");
		} else
		{
			Class<?> type = field.getType();
			if (type.isArray())// 创建一个数组
			{
				Class<?> elementType = type.getComponentType();// 数组元素类型
				if (elementType.isArray())
				{
					throw new MVCException("只支持一维数组的创建..");
				} else
				{
					logger.debug("Array of: " + elementType);
					return Array.newInstance(elementType, 1);// 创建一个拥有1个元素,的一位数组
				}
			} else if (type.isInterface())// 是一个接口
			{
				if (type.isAssignableFrom(Map.class))
				{
					logger.debug("创建类型[" + type + "]的默认实现,且是MAP的接口");
					return new HashMap();
				} else if (type.isAssignableFrom(List.class))
				{
					logger.debug(type + "是一个接口,List");
					return new ArrayList();
				} else if (type.isAssignableFrom(Set.class))
				{
					logger.debug(type + "是一个接口,Set");
					return new HashSet();
				}
				// 无法创建
				throw new MVCException("无法创建类型[" + type + "],他是一个Interface");
			} else if (type.isAnnotation())// 基本不太可能出现
			{
				throw new MVCException("无法创建类型[" + type + "],他是一个Annotation");

			} else if (type.isEnum())
			{
				// 无法创建
			} else if (type.isPrimitive())
			{
				throw new MVCException("无法创建基本类型[" + type + "],他是一个Primitive");
				// 基本类型
			} else
			{
				// 尝试以,判断数组,枚举类型,其他列表类型
				fieldValue = field.getType().newInstance();
				logger.debug("创建普通类型[" + type + "]");
			}
			return fieldValue;
		}
	}

	/**
	 * 创建一个一维数组
	 * 
	 * @param <T>
	 * @param type
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static <T> T[] createArray(Class<T> type, int length)
	{
		if (type.isArray())// 创建一个数组
		{
			Class<?> elementType = type.getComponentType();// 数组元素类型
			if (elementType.isArray())
			{
				throw new MVCException("只支持一维数组的创建..");
			} else
			{
				logger.debug("Array of: " + elementType);
				return (T[]) Array.newInstance(elementType, length);// 创建一个拥有length个元素,的一维数组
			}
		}
		return null;
	}

	/**
	 * Orz这个方法写的太恶心了
	 * 
	 * 解析简单表达值,将值赋到正确的位置,
	 * 
	 * 如果表达式不适合这个bean,抛出异常,立马停止
	 * 
	 * 支持简单表达式:
	 * 
	 * map(key) <br/>
	 * array[1]
	 * 
	 * 
	 * @param bean
	 * @param name
	 * @param value
	 * @throws SecurityException
	 * 
	 * @throws IllegalAccessException
	 *             无法读取或者
	 * @throws NoSuchFieldException
	 *             不存在这样的属性
	 * @throws InstantiationException
	 *             对象无法实例化
	 * 
	 *             对象无法被转换
	 */
	public static void setValue(Object bean, String name, Object value)
	{
		Object root = null;// TODO 为了防止表达式链赋值不成功导致的在bean上添加一些无效的数据

		Object rootName = null;
		try
		{
			while (resolver.hasNested(name))// 有嵌套,不能是基本类型!
			{
				String next = resolver.next(name);
				logger.debug("BEAN TYPE :[" + bean.getClass() + "] THIS:[" + next + "] NAME :[" + name + "]");
				Object nestedBean = null;
				if (resolver.isMapped(next))// 包含了两层:map.key->根据泛型或者注解,创建一个MAP,
				{
					String mapName = resolver.getProperty(next);// map的名称,
					String mapKey = resolver.getKey(next);// map 键的名称

					logger.debug(next + " IS MAPPED:" + mapName + "(" + mapKey + ")");

					nestedBean = get(bean, mapName);// try to get the Map
					if (nestedBean == null) // we should build a map
					{
						nestedBean = createObject(bean, mapName);// create a map

						if (rootName == null)
						{
							rootName = mapName;
						}
						set(bean, mapName, nestedBean);// set Map to bean
					}
					if (!(nestedBean instanceof Map))// not a map
					{
						throw new MVCException("[" + mapName + "] is not a map");
					}

					Field mapField = BeanUtilsTest.getFiled(bean, mapName);
					bean = nestedBean;// now the bean is the map

					nestedBean = get(bean, mapKey);// get key from the map

					if (nestedBean == null)// if the map do not have the value
					{
						// Class<?> type =
						// GenericsUtils.getFieldGenericType(mapField, 1);
						Class<?> type = Help.getElementType(mapField);// 得到map的Value的泛型/或者Elemet
						if (type == Object.class)
						{
							throw new MVCException("元素类型无法确定,无法为你创建对象...");
						}
						nestedBean = buildObject(type);// 创建map中元素对象
						set(bean, mapKey, nestedBean);// put the element to the
														// map
					}
				} else if (resolver.isIndexed(next))// 包含了两层:->p[index],1如果p不存在,创建p,
				{
					String property = resolver.getProperty(next);// Name of
																	// Array
					int index = resolver.getIndex(next);// Array's Index
					Field listField = BeanUtilsTest.getFiled(bean, property);
					if (listField.getType().isArray())
					{
						nestedBean = get(bean, property);// try to get the Array
						if (nestedBean == null)// if the Array does not exist
						{
							Field field = BeanUtilsTest.getFiled(bean, property);
							nestedBean = createArray(field.getType(), index);// create
																				// a
																				// Array
						}
						Object o = bean;//

						bean = nestedBean;// now the bean is the Array!
						//
						nestedBean = getObjectFromArray(bean, index);

						if (nestedBean == null)
						{
							Class<?> elementType = Help.getElementType(listField);// //
																					// bean.getClass().getComponentType()
							nestedBean = buildObject(elementType);
							bean = setObjectToArray(bean, index, nestedBean);
							// logger.debug(((Address[]) bean).length);
							set(o, property, bean);// put the Array back to the
													// bean:这个array很可能不是原来的那个array,所以这个操作室必须的
						}
					} else if (listField.getType().isAssignableFrom(List.class))
					{
						nestedBean = get(bean, property);// try to get the List
						Field field = BeanUtilsTest.getFiled(bean, property);
						if (nestedBean == null)// if the Array does not exist
						{
							nestedBean = buildObject(field.getType());// create
																		// a
																		// List
																		// 4 U
							set(bean, property, nestedBean);
						}
						bean = nestedBean;// now the bean is the List!

						nestedBean = get(bean, index + "");// get the value from
															// the list

						if (nestedBean == null)
						{
							Class<?> elementType = Help.getElementType(field);
							if (elementType == Object.class)
							{
								throw new MVCException("元素类型无法确定,无法为你创建对象...");
							}
							nestedBean = buildObject(elementType);
							set(bean, index + "", nestedBean);
						}
					} else
					{
						throw new MVCException("Property[" + property + ":" + listField.getType() + "] In Bean["
								+ bean.getClass() + "] is not a List nor an Array");
					}

				} else
				{
					// if the next is a bean
					nestedBean = get(bean, next);// get first
					if (nestedBean == null)
					{
						nestedBean = createObject(bean, next);//
						set(bean, next, nestedBean);
					}
				}
				if (nestedBean == null)
				{
					// can not create the nestedBean
					throw new NestedNullException("Null property value for '" + name + "' on bean class '" + bean.getClass()
							+ "'");
				}
				bean = nestedBean;
				name = resolver.remove(name);
			}
			// 这是最后的值
			// 将这个值用转换器写入
			logger.debug("BEAN TYPE :[" + bean.getClass() + "] THIS:[" + name + "] NAME :[" + name + "]");

			// 处理最后一个没有嵌套的值
			if (resolver.isMapped(name))
			{
				String mapName = resolver.getProperty(name);
				String key = resolver.getKey(name);

				logger.debug(name + " IS MAPPED:" + mapName + "(" + key + ")");
				Object map = get(bean, mapName);// get map from the bean
				if (map == null)
				{
					map = createObject(bean, mapName);
				} else if (!(map instanceof Map))
				{
					throw new MVCException(mapName + " is not a map");
				}

				set(bean, mapName, map);// set Map to bean

				Field mapFiled = BeanUtilsTest.getFiled(bean, mapName);//

				Class<?> targetType = Help.getElementType(mapFiled);

				set(map, key, ConvertUtils.convert(value, targetType));// set
																		// the
																		// value
																		// the
																		// map

			} else if (resolver.isIndexed(name))// 包含了两层:->p[index],1如果p不存在,创建p,
			{
				Object nestedBean;
				String property = resolver.getProperty(name);// Name of Array
				int index = resolver.getIndex(name);// Array's Index

				Field listFiled = BeanUtilsTest.getFiled(bean, property);

				logger.debug("Get Filed[" + property + "] From[" + bean.getClass() + "]");

				if (listFiled.getType().isArray())
				{
					nestedBean = get(bean, property);// try to get the Array
					if (nestedBean == null)// if the Array does not exist
					{
						Field field = BeanUtilsTest.getFiled(bean, property);
						nestedBean = createArray(field.getType(), index);// create
																			// a
																			// Array
					}
					// FIXME
					// Class<?> targetType =
					// nestedBean.getClass().getComponentType();
					Class<?> targetType = Help.getElementType(listFiled);

					nestedBean = setObjectToArray(nestedBean, index, ConvertUtils.convert(value, targetType));

					set(bean, property, nestedBean);// put the Array back to the
													// bean

				} else if (listFiled.getType().isAssignableFrom(List.class))
				{
					nestedBean = get(bean, property);// try to get the List
					Field field = BeanUtilsTest.getFiled(bean, property);
					if (nestedBean == null)// if the Array does not exist
					{
						nestedBean = buildObject(field.getType());// create a
																	// List
						set(bean, property, nestedBean);// put the Array back to
														// the bean
					}
					logger.debug("SET:" + nestedBean.getClass());

					Class<?> targetType = Help.getElementType(field);
					if (targetType == Object.class)
					{
						logger.debug("SET:" + nestedBean.getClass());
					}

					set(nestedBean, index + "", ConvertUtils.convert(value, targetType));
				} else
				{
					throw new MVCException("Property[" + property + ":" + listFiled.getType() + "] In Bean[" + bean.getClass()
							+ "] Is Not A List Nor An Array");
				}
			} else
			{
				Field field = BeanUtilsTest.getFiled(bean, name);
				value = ConvertUtils.convert(value, field.getType());
				logger.debug("	NAME " + name + " TYPE " + field.getType() + "	Value " + value);
				set(bean, name, value);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	private static List<String> list = new ArrayList<String>();

	public static void main0(String[] args) throws SecurityException, NoSuchFieldException
	{
		list.add(0, null);
		list.add(1, null);
		setObjectToList(list, 5, "5");
		setObjectToList(list, 7, "7");
		setObjectToList(list, 50, "50");
		setObjectToList(list, 8, "8");

		// list.set(3, null);
		int i = 0;
		for (String s : list)
		{
			System.out.println(i++ + "=" + s);
		}
		// System.out.println(list.size());
	}

	public static void main2(String[] args) throws SecurityException, NoSuchFieldException
	{
		ParameterizedType pt = (ParameterizedType) Bean4.class.getField("list").getGenericType();
		System.out.println(pt.getActualTypeArguments().length);
		System.out.println(pt.getActualTypeArguments()[0]);
	}

	public static void main3(String args[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException, InstantiationException
	{
		User[] users = new User[4];

		users[3] = new User();

		int[] a = { 1, 2, 3, 4 };

		int i = 0;
		users = setObjectToArray(users, 10, new User());
		for (User u : users)
		{
			System.out.println(i++ + "	" + u);
		}
	}

	private static User[][][][][][] us;//

	public static void main7(String args[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException, InstantiationException
	{

		// User[][] us = null;// = new User[5][2];

		// us.getClass().getComponentType().isArray();

		//
		Class<?> t = BeanUtilsTest.getField(Bean4.class, "us").getType();
		int d = 0;
		while (t.isArray())
		{
			d++;
			t = t.getComponentType();
		}

		// Array.get(us, 1).getClass();

		System.out.println(d);

		User user = new User();

		String name = "address.city";

		String ss = "ss";

		String ssValue = "s0";

		String value = "HUZHOU";

		Address address = new Address();

		address.setCity("ddddddddddd");

		// setValue(user, "name[1].map(a)", address);
		// setValue(user, "list[10].list[0]", address);
		// setValue(user, "list[10]", address);
		// setValue(user, "map(huzhou).city", "HUZHOU");
		// Class<?> entityClass = (Class<?>) ((ParameterizedType)

		// System.out.println("	l " +
		// user.getList().get(10).getList().get(0).getClass());

		// System.out.println("	USER:map(huzhou).city: " +
		// user.getMap().get("huzhou").getCity());
		// System.out.println("	USER:map(hangzhou).city: " +
		// user.getMap().get("hangzhou").getMap().get("huzhou"));

		// System.out.println("	USER:map.size: " +
		// user.getMap().values().size());
		// System.out.println("MAP:" + resolver.getProperty("qcc(a)"));
	}

	public static void main(String args[]) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			SecurityException, NoSuchFieldException, InstantiationException
	{
		User user = new User();

		String name1 = "address.city";
		String name2 = "address.city";
		String name3 = "address.city";
		String name4 = "address.city";
		String name5 = "address.city";
		String name6 = "address.city";
		String name7 = "address.city";
		String name8 = "address.city";

		String ss = "ss";

		String ssValue = "s0";

		String value = "HUZHOU";

		Address address = new Address();

		address.setCity("ZHEJIANG");

		// setValue(user, "name[1].map(a)", address);

		setValue(user, "list[10]", address);
		// setValue(user, "list[10].city", "HUZHOU");
		// setValue(user, "map(huzhou).city", "HUZHOU");
		// Class<?> entityClass = (Class<?>) ((ParameterizedType)

		System.out.println("	USER:map(huzhou).city: " + user.getList().get(10).getCity());

		// System.out.println("	USER:map(huzhou).city: " +
		// user.getMap().get("huzhou").getCity());
		// System.out.println("	USER:map(hangzhou).city: " +
		// user.getMap().get("hangzhou").getMap().get("huzhou"));

		// System.out.println("	USER:map.size: " +
		// user.getMap().values().size());
		// System.out.println("MAP:" + resolver.getProperty("qcc(a)"));
	}

}
