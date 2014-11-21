package com.xindian.awaits;

import java.util.List;
import java.util.Set;

import javax.sql.rowset.Predicate;

public interface CriteriaQuery<T>
{
	CriteriaQuery<T> distinct(boolean distinct);

	List<Order> getOrderList();

	List<Object> getParameters();

	CriteriaQuery<T> groupBy(Expression<?>... grouping);

	CriteriaQuery<T> groupBy(java.util.List<Expression<?>> grouping);

	CriteriaQuery<T> having(Expression<java.lang.Boolean> restriction);

	CriteriaQuery<T> having(Predicate... restrictions);

	CriteriaQuery<T> multiselect(List<Selection<?>> selectionList);

	CriteriaQuery<T> multiselect(Selection<?>... selections);

	CriteriaQuery<T> orderBy(List<Order> o);

	CriteriaQuery<T> orderBy(Order... o);

	CriteriaQuery<T> select(Selection<? extends T> selection);

	CriteriaQuery<T> where(Expression<Boolean> restriction);

	CriteriaQuery<T> where(Predicate... restrictions);
}
